CREATE OR REPLACE Package Body      AIMS_EMAIL_GROUPS_PKG
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_email_group
         ( p_email_group_id         IN  number,              -- email_group_id to be deleted
           p_curr_user_name         IN  varchar2             -- user name of the person deleting the email group
          )
    IS

    /*
    || Overview:        Deletes a given email group from the database
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 05-04-2004        rqazi           Created
    ||
    ||
    ||
    ||
    */

   BEGIN

        delete
        from
            aims_email_group_app_categrs
        where
            group_id= p_email_group_id;

        delete
        from
            aims_email_group_contacts
        where
            group_id= p_email_group_id;

        delete
        from
            aims_email_group_contact_types
        where
            group_id= p_email_group_id;

        delete
        from
            aims_email_group_contract_ids
        where
            group_id= p_email_group_id;

        delete
        from
            aims_email_group_contract_stat
        where
            group_id= p_email_group_id;

        delete
        from
            aims_email_group_platforms
        where
            group_id= p_email_group_id;

        delete
        from
            aims_email_group_roles
        where
            group_id= p_email_group_id;

        delete
        from
            aims_email_reminder_groups
        where
            group_id= p_email_group_id;

        delete
        from
            aims_email_group_users
        where
            group_id= p_email_group_id;


        delete
        from
            aims_email_groups
        where
            group_id = p_email_group_id;

   END delete_email_group;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_email_group_records
         (
           p_out_result                 OUT     TYPES.cursor_type
         )
    IS

    /*
    || Overview:        Gives the cursor for the email groups in the database.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 05-04-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

    BEGIN

        OPEN p_out_result FOR
            select
               group_id,
               group_title,
               group_desc,
               group_type,
               to_char(last_email_sent_date, 'MM/DD/YYYY'),
               created_by,
               to_char(created_date, 'MM/DD/YYYY'),
               last_updated_by,
               to_char(last_updated_date, 'MM/DD/YYYY')
            from
               aims_email_groups
            order by
               group_title, group_type;

    EXCEPTION
      WHEN OTHERS THEN
           IF(p_out_result%isopen) THEN
               CLOSE p_out_result;
           END IF;
           RAISE;

    END get_email_group_records;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_email_group_details
         (
           p_group_id                    IN     varchar2,
           p_out_avail_contact_types    OUT     TYPES.cursor_type,
           p_out_contact_types          OUT     TYPES.cursor_type,
           p_out_avail_contract_status  OUT     TYPES.cursor_type,
           p_out_contract_status        OUT     TYPES.cursor_type,
           p_out_avail_contract_ids     OUT     TYPES.cursor_type,
           p_out_contract_ids           OUT     TYPES.cursor_type,
           p_out_avail_platforms        OUT     TYPES.cursor_type,
           p_out_platforms              OUT     TYPES.cursor_type,
           p_out_group_title            OUT     varchar2,
           p_out_group_desc             OUT     varchar2,
           p_out_group_type             OUT     varchar2
         )
    IS

    /*
    || Overview:        Gives the email group detail cursors.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 05-04-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

    BEGIN

        if (p_group_id > 0) then

              select
                 group_title,
                 group_desc,
                 group_type
              into
                  p_out_group_title,
                  p_out_group_desc,
                  p_out_group_type
              from
                  aims_email_groups
              where
                  group_id = p_group_id;

        end if;

        OPEN p_out_avail_contact_types FOR

            select
                name, description
            from the ( select cast( AIMS_EMAIL_GROUPS_PKG_UTILS.all_contact_types() as aimsTableType )
                                from dual )
            where name in (
                        select
                            name
                        from the ( select cast( AIMS_EMAIL_GROUPS_PKG_UTILS.all_contact_types() as aimsTableType )
                                from dual )
                        minus
                        select contact_type  from aims_email_group_contact_types
                                        where group_id = p_group_id
                           );


        OPEN p_out_contact_types FOR
            select
                contact_type,
                contact_desc
            from
                aims_email_group_contact_types
            where
                group_id = p_group_id
            order by contact_type;


        OPEN p_out_avail_contract_status FOR

            select
                name, description
            from the ( select cast( AIMS_EMAIL_GROUPS_PKG_UTILS.all_contract_status() as aimsTableType )
                                from dual )
            where name in (
                        select
                            name
                        from the ( select cast( AIMS_EMAIL_GROUPS_PKG_UTILS.all_contract_status() as aimsTableType )
                                from dual )
                        minus
                        select contract_status  from aims_email_group_contract_stat
                                        where group_id = p_group_id
                           );


        OPEN p_out_contract_status FOR
            select
                contract_status,
                contract_status_desc
            from
                aims_email_group_contract_stat
            where
                group_id = p_group_id
            order by contract_status;

        OPEN p_out_avail_contract_ids FOR
            select
                c.contract_id,
                c.contract_title,
                c.version,
                c.platform_id
            from
                aims_contracts c
            where
                c.contract_id not in (select g.contract_id from aims_email_group_contract_ids g where g.group_id = p_group_id)
            order by c.contract_title;

        OPEN p_out_contract_ids FOR
            select
                c.contract_id,
                c.contract_title,
                c.version,
                c.platform_id
            from
                aims_email_group_contract_ids g,
                aims_contracts c
            where
                g.group_id = p_group_id
                and g.contract_id = c.contract_id
            order by c.contract_title;

        OPEN p_out_avail_platforms FOR
            select
                p.platform_id,
                p.platform_name
            from
                aims_platforms p
            where
                p.platform_id not in (select g.platform_id from aims_email_group_platforms g where g.group_id = p_group_id)
            order by p.platform_name;

        OPEN p_out_platforms FOR
            select
                p.platform_id,
                p.platform_name
            from
                aims_email_group_platforms g,
                aims_platforms p
            where
                g.group_id = p_group_id
                and g.platform_id = p.platform_id
            order by p.platform_name;

    EXCEPTION
      WHEN OTHERS THEN
           IF(p_out_avail_contact_types%isopen) THEN
               CLOSE p_out_avail_contact_types;
           END IF;
           IF(p_out_contact_types%isopen) THEN
               CLOSE p_out_contact_types;
           END IF;
           IF(p_out_avail_contract_status%isopen) THEN
               CLOSE p_out_avail_contract_status;
           END IF;
           RAISE;
           IF(p_out_contract_status%isopen) THEN
               CLOSE p_out_contract_status;
           END IF;
           IF(p_out_avail_contract_ids%isopen) THEN
               CLOSE p_out_avail_contract_ids;
           END IF;
           IF(p_out_contract_ids%isopen) THEN
               CLOSE p_out_contract_ids;
           END IF;
           IF(p_out_avail_platforms%isopen) THEN
               CLOSE p_out_avail_platforms;
           END IF;
           IF(p_out_platforms%isopen) THEN
               CLOSE p_out_platforms;
           END IF;
           RAISE;
    END get_email_group_details;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE create_update_email_group
         (
           p_group_id               IN  number,             -- email group_id to be updated
           p_group_name             IN  varchar2,           -- email group_title to be updated
           p_group_desc             IN  varchar2,           -- email group_desc to be updated
           p_group_type             IN  varchar2,           -- group_type 'A' alliance 'V' verizon
           p_contact_types          IN  varchar2,           -- contact_types comma seperated
           p_contract_status        IN  varchar2,           -- contract_status comma seperated
           p_contract_ids           IN  varchar2,           -- contract_ids comma seperated
           p_platform_ids           IN  varchar2,           -- platform_ids comma seperated
           p_trans_type             IN  varchar2,           -- 'A' add  'U' update
           p_user_name              IN  varchar2            -- user name of the person updating record
         )

    IS

    /*
    || Overview:        Updates a given email_group to the database
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 05-04-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */


        v_contact_type_array    DBMS_UTILITY.UNCL_ARRAY;
        v_contract_status_array DBMS_UTILITY.UNCL_ARRAY;
        v_contract_ids_array    DBMS_UTILITY.UNCL_ARRAY;
        v_platform_ids_array    DBMS_UTILITY.UNCL_ARRAY;

        v_cnt_contact_type      number;
        v_cnt_contract_status   number;
        v_cnt_contract_ids      number;
        v_cnt_platform_ids      number;

        v_contact_type_desc     varchar2(200);
        v_contract_status_desc  varchar2(200);

        v_group_id              number;

   BEGIN

        v_group_id := p_group_id;

        if (p_trans_type = 'U') then
            update
                aims_email_groups
            set
               group_title = p_group_name,
               group_desc = p_group_desc,
               last_updated_by = p_user_name,
               last_updated_date = sysdate
            where
                group_id = v_group_id;
        elsif (p_trans_type = 'A') then
            insert into aims_email_groups (group_id, group_title, group_desc, group_type,
                                            created_by, created_date,last_updated_by, last_updated_date)
            values (SEQ_PK_EMAIL_GROUPS.nextval, p_group_name, p_group_desc, 'V',
                                            p_user_name, sysdate, p_user_name, sysdate)
                     returning group_id into v_group_id;
        end if;


        if (INSTR(p_contact_types,',') > 0) then
            PARSE.delimstring_to_table(p_contact_types, v_contact_type_array, v_cnt_contact_type, ',');
        elsif (LENGTH(p_contact_types) > 0) then
            v_contact_type_array(1) := p_contact_types;
            v_cnt_contact_type := 1;
        end if;

        delete from aims_email_group_contact_types where group_id = v_group_id;

        if (v_cnt_contact_type > 0) then

            for i IN 1..v_contact_type_array.count loop
                if(length(trim(v_contact_type_array(i))) > 0) then
                    begin

                        select a.description into v_contact_type_desc
                            from the ( select cast( AIMS_EMAIL_GROUPS_PKG_UTILS.all_contact_types() as aimsTableType )
                                         from dual ) a
                        where a.name = trim(v_contact_type_array(i));

                        insert into aims_email_group_contact_types(group_id, contact_type, contact_desc)
                            values(v_group_id, v_contact_type_array(i), v_contact_type_desc);

                    exception
                        when dup_val_on_index then
                            null;
                    end;
                end if;
            end loop;

        end if;


        if (INSTR(p_contract_status,',') > 0) then
            PARSE.delimstring_to_table(p_contract_status, v_contract_status_array, v_cnt_contract_status, ',');
        elsif (LENGTH(p_contract_status) > 0) then
            v_contract_status_array(1) := p_contract_status;
            v_cnt_contract_status := 1;
        end if;

        delete from aims_email_group_contract_stat where group_id = p_group_id;

        if (v_cnt_contract_status > 0) then

            for i IN 1..v_contract_status_array.count loop
                if(length(trim(v_contract_status_array(i))) > 0) then
                    begin

                        select a.description into v_contract_status_desc
                            from the ( select cast( AIMS_EMAIL_GROUPS_PKG_UTILS.all_contract_status() as aimsTableType )
                                         from dual ) a
                        where a.name = trim(v_contract_status_array(i));

                        insert into aims_email_group_contract_stat(group_id, contract_status, contract_status_desc)
                            values(v_group_id, v_contract_status_array(i), v_contract_status_desc);

                    exception
                        when dup_val_on_index then
                            null;
                    end;
                end if;
            end loop;

        end if;

        if (INSTR(p_contract_ids,',') > 0) then
            PARSE.delimstring_to_table(p_contract_ids, v_contract_ids_array, v_cnt_contract_ids, ',');
        elsif (LENGTH(p_contract_ids) > 0) then
            v_contract_ids_array(1) := p_contract_ids;
            v_cnt_contract_ids := 1;
        end if;

        delete from aims_email_group_contract_ids where group_id = p_group_id;

        if (v_cnt_contract_ids > 0) then

            for i IN 1..v_contract_ids_array.count loop
                if(length(trim(v_contract_ids_array(i))) > 0) then
                    begin

                        insert into aims_email_group_contract_ids(group_id, contract_id)
                            values(v_group_id, v_contract_ids_array(i));

                    exception
                        when dup_val_on_index then
                            null;
                    end;
                end if;
            end loop;

        end if;

        if (INSTR(p_platform_ids,',') > 0) then
            PARSE.delimstring_to_table(p_platform_ids, v_platform_ids_array, v_cnt_platform_ids, ',');
        elsif (LENGTH(p_platform_ids) > 0) then
            v_platform_ids_array(1) := p_platform_ids;
            v_cnt_platform_ids := 1;
        end if;

        delete from aims_email_group_platforms where group_id = p_group_id;

        if (v_cnt_platform_ids > 0) then

            for i IN 1..v_platform_ids_array.count loop
                if(length(trim(v_platform_ids_array(i))) > 0) then
                    begin
                        insert into aims_email_group_platforms(group_id, platform_id)
                            values(v_group_id, v_platform_ids_array(i));
                    exception
                        when dup_val_on_index then
                            null;
                    end;
                end if;
            end loop;

        end if;

   END create_update_email_group;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_EMAIL_GROUPS_PKG; -- Package Body AIMS_EMAIL_GROUPS_PKG
/

