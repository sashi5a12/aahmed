CREATE OR REPLACE Package Body      AIMS_BREW_PROV_APPS
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_prov_roles_from_apps_id
         (
            p_apps_id                   number,
            p_out_prov_sel_roles    OUT TYPES.cursor_type,       -- Result cursor
            p_out_prov_avail_roles  OUT TYPES.cursor_type
         )
    IS

    /*
     Overview:        Used by AIMS application for getting the details of the provisioned app roles


     Dependencies:

     Modification History:
     When             Who             What
    ---------------------------------------
     09-08-2004       rqazi           Created




    */


   BEGIN

        OPEN p_out_prov_sel_roles FOR
            select distinct
                r.role_id,
                r.role_name
            from
                aims_sys_roles r,
                aims_prov_apps_roles ar
            where
                ar.apps_id = p_apps_id
                and ar.role_id = r.role_id
                and r.role_type = 'V'
            order by
                upper(r.role_name);


        OPEN p_out_prov_avail_roles FOR
            select distinct
                r.role_id,
                r.role_name
            from
                aims_sys_roles r
            where
                r.role_type = 'V'
                and r.role_id not in
                (
                    select role_id from aims_prov_apps_roles where apps_id = p_apps_id
                )
            order by
                upper(r.role_name);


   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_prov_sel_roles%ISOPEN) THEN
            CLOSE p_out_prov_sel_roles;
          END IF;
          IF(p_out_prov_avail_roles%ISOPEN) THEN
            CLOSE p_out_prov_avail_roles;
          END IF;
          RAISE;
    END get_prov_roles_from_apps_id;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_prov_roles_from_string
         (
            p_role_ids_string           varchar2,
            p_out_prov_sel_roles    OUT TYPES.cursor_type,       -- Result cursor
            p_out_prov_avail_roles  OUT TYPES.cursor_type
         )
    IS

    /*
     Overview:        Used by AIMS application for getting the details of the provisioned app


     Dependencies:

     Modification History:
     When             Who             What
    ---------------------------------------
     09-08-2004       rqazi           Created

    */
          v_role_ids_string  varchar2(200) := p_role_ids_string;

   BEGIN

        --INS_SQL('before v_role_ids_string --> ' || v_role_ids_string);
        --INS_SQL('length(trim(v_role_ids_string)) --> ' || length(trim(v_role_ids_string)));

        if ((v_role_ids_string is null) or (length(trim(v_role_ids_string)) <= 0))  then
            v_role_ids_string := '0';
        else
            v_role_ids_string := '0,' || v_role_ids_string ;
        end if;

        INS_SQL('after v_role_ids_string --> ' || v_role_ids_string);

        OPEN p_out_prov_sel_roles FOR
            select
                r.role_id,
                r.role_name
            from
                aims_sys_roles r
            where
                r.role_id IN
                (
                    select * from TABLE(cast(in_list(v_role_ids_string) as inListTableType))
                )
                and r.role_type = 'V'
            order by
                upper(r.role_name);


        OPEN p_out_prov_avail_roles FOR
            select
                r.role_id,
                r.role_name
            from
                aims_sys_roles r
            where
                r.role_id not in
                (
                    select * from TABLE(cast(in_list(v_role_ids_string) as inListTableType))
                )
                and r.role_type = 'V'
            order by
                upper(r.role_name);

   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_prov_sel_roles%ISOPEN) THEN
            CLOSE p_out_prov_sel_roles;
          END IF;
          IF(p_out_prov_avail_roles%ISOPEN) THEN
            CLOSE p_out_prov_avail_roles;
          END IF;
          RAISE;
   END get_prov_roles_from_string;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_prov_app_roles
         ( p_apps_id                IN  number,
           p_role_ids_string        IN  varchar2
          )

    IS

    /*
    || Overview:        Updates/Inserts the roles allocated for this provision app
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 09-09-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */


        v_assigned_roles_array  DBMS_UTILITY.UNCL_ARRAY;
        v_role_ids_string  varchar2(200) := trim(p_role_ids_string);

   BEGIN

        /* Delete all records for the given apps_id from aims_prov_apps_roles table before inserting new associations */
        delete
        from
            aims_prov_apps_roles
        where
            apps_id = p_apps_id;

        if (INSTR(v_role_ids_string,',') > 0) then
            v_assigned_roles_array := STRING_UTILS.split_standard(v_role_ids_string, ',');
        elsif (LENGTH(v_role_ids_string) > 0) then
            v_assigned_roles_array(1) := v_role_ids_string;
        end if;


         for i in 1..v_assigned_roles_array.COUNT loop

            insert into
                aims_prov_apps_roles (
                                  apps_id,
                                  role_id
                                )
            values
                                (
                                  p_apps_id,
                                  v_assigned_roles_array(i)
                                );


        end loop;

   END update_prov_app_roles;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_BREW_PROV_APPS; -- Package Body
/

