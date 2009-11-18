CREATE OR REPLACE PACKAGE BODY BDS_ROLES
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_role
         ( p_role_id                IN  number,             -- role to be deleted
           p_curr_user_name         IN  varchar2,           -- user name of the person deleting the account
           p_out_result            OUT  varchar2            -- Result string
          )
    IS

    /*
    || Overview:        Deletes a given role from the database
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 11-05-2003       rqazi           Created
    ||
    ||
    ||
    ||
    */

   BEGIN

        delete
        from
            bds_role_privileges
        where
            role_id = p_role_id;


        delete
        from
            bds_sys_roles
        where
            role_id = p_role_id;

   END delete_role;

/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE update_role
         ( p_role_id                IN  number,             -- role id of the role is to be updated
           p_role_name              IN  varchar2,           -- role name
           p_role_description       IN  varchar2,           -- role description
           p_role_type              IN  varchar2,           -- role type
           p_select_privileges      IN  varchar2,           -- role select privileges comma seperated
           p_create_privileges      IN  varchar2,           -- role create privileges comma seperated
           p_update_privileges      IN  varchar2,           -- role update privileges comma seperated
           p_delete_privileges      IN  varchar2,           -- role delete privileges comma seperated
           p_curr_user_name         IN  varchar2,           -- user name of the person updating the role
           p_out_result            OUT  varchar2            -- Result string 'Y' for success
          )

    IS

    /*
    || Overview:        Updates a given role in the database
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 11-05-2003       rqazi           Created
    ||
    ||
    ||
    ||
    */

        v_select_array  DBMS_UTILITY.UNCL_ARRAY;
        v_create_array  DBMS_UTILITY.UNCL_ARRAY;
        v_update_array  DBMS_UTILITY.UNCL_ARRAY;
        v_delete_array  DBMS_UTILITY.UNCL_ARRAY;

        v_select_value  varchar2(1);
        v_create_value  varchar2(1);
        v_update_value  varchar2(1);
        v_delete_value  varchar2(1);

        bn_alteast_one_non_null boolean := false;

   BEGIN

        update
            bds_sys_roles
        set
            role_name = p_role_name,
            role_description = p_role_description,
            last_updated_by = p_curr_user_name,
            last_updated_date = sysdate
        where
            role_id = p_role_id;


        delete
        from
            bds_role_privileges
        where
            role_id = p_role_id;

       /*
        INS_SQL('p_role_id ' || p_role_id);
        INS_SQL('p_role_name ' || p_role_name);
        INS_SQL('p_role_description ' || p_role_description);
        INS_SQL('p_role_type ' || p_role_type);

        INS_SQL('v_select_array ' || p_select_privileges);
        INS_SQL('v_create_array ' || p_create_privileges);
        INS_SQL('v_update_value ' || p_update_privileges);
        INS_SQL('v_delete_array ' || p_delete_privileges);
        */

        if (INSTR(p_select_privileges,',') > 0) then
            v_select_array := STRING_UTILS.split_standard(p_select_privileges, ',');
        elsif (LENGTH(p_select_privileges) > 0) then
            v_select_array(1) := p_select_privileges;
        end if;

      
        if (INSTR(p_create_privileges,',') > 0) then
            v_create_array := STRING_UTILS.split_standard(p_create_privileges, ',');
        elsif (LENGTH(p_create_privileges) > 0) then
            v_create_array(1) := p_create_privileges;
        end if;

        if (INSTR(p_update_privileges,',') > 0) then
            v_update_array := STRING_UTILS.split_standard(p_update_privileges, ',');
        elsif (LENGTH(p_update_privileges) > 0) then
            v_update_array(1) := p_update_privileges;
        end if;

        if (INSTR(p_delete_privileges,',') > 0) then
            v_delete_array := STRING_UTILS.split_standard(p_delete_privileges, ',');
        elsif (LENGTH(p_delete_privileges) > 0) then
            v_delete_array(1) := p_delete_privileges;
        end if;
        
        for c in (select privilege_id from bds_sys_privileges where available_to = substr(p_role_type,1,1) or available_to = 'B') loop

            if ( AIMS_UTILS.value_in_table(to_char(c.privilege_id), v_select_array) ) then
                v_select_value := 'Y';
                bn_alteast_one_non_null := true;
            else
                v_select_value := null;
            end if;
        
            if ( AIMS_UTILS.value_in_table(to_char(c.privilege_id), v_create_array) ) then
                v_create_value := 'Y';
                bn_alteast_one_non_null := true;
            else
                v_create_value := null;
            end if;

            if ( AIMS_UTILS.value_in_table(to_char(c.privilege_id), v_update_array) ) then
                v_update_value := 'Y';
                bn_alteast_one_non_null := true;
            else
                v_update_value := null;
            end if;

            if ( AIMS_UTILS.value_in_table(to_char(c.privilege_id), v_delete_array) ) then
                v_delete_value := 'Y';
                bn_alteast_one_non_null := true;
            else
                v_delete_value := null;
            end if;
           
            -- if atlease one non null value is present then only insert
            if (bn_alteast_one_non_null) then
                insert into
                    bds_role_privileges (
                                            role_id,
                                            privilege_id,
                                            if_select_allowed,
                                            if_create_allowed,
                                            if_update_allowed,
                                            if_delete_allowed
                                         )
                values
                                        (
                                            p_role_id,
                                            c.privilege_id,
                                            v_select_value,
                                            v_create_value,
                                            v_update_value,
                                            v_delete_value

                                        );
            end if;

            bn_alteast_one_non_null := false;

        end loop;

   END update_role;

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_ROLES; -- Package Body BDS_ROLES
/

