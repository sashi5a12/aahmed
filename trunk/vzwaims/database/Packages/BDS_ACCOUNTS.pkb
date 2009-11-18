CREATE OR REPLACE Package Body BDS_ACCOUNTS
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_vzw_account
         ( p_user_id                IN  number,             -- alliance user whose account is to be deleted
           p_curr_user_name         IN  varchar2,             -- user name of the person deleting the account
           p_out_result            OUT  varchar2            -- Result string
          )
    IS

    /*
    || Overview:        Deletes a given vzw account from the database
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
            bds_user_roles
        where
            user_id = p_user_id;


        delete
        from
            bds_users
        where
            user_id = p_user_id;

   END delete_vzw_account;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_account
         ( p_user_id                IN  number,             -- alliance user whose account is to be deleted
           p_curr_user_name         IN  varchar2,             -- user name of the person deleting the account
           p_out_result            OUT  varchar2            -- Result string
          )
    IS

    /*
    || Overview:        Deletes a given user account from the database
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
    
        v_contact_id    bds_contacts.contact_id%type;
   BEGIN

        p_out_result := 'Y';

		BDS_MVNFORUM.sync_mvnforum_account(p_user_id, 'D');
		
        delete
        from
            bds_user_roles
        where
            user_id = p_user_id;


        delete
        from
            bds_user_regions
        where
            user_id = p_user_id;


        delete
        from
            bds_user_segments
        where
            user_id = p_user_id;


        select
            contact_id
        into
            v_contact_id
        from
            bds_users
        where
           user_id = p_user_id;
           
           
        update bds_users set contact_id = null where user_id = p_user_id;
        
                    
        delete
        from
            bds_users
        where
            user_id = p_user_id;
            
            
        delete
        from
            bds_contacts
        where
            contact_id = v_contact_id;        

   END delete_account;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_account
         ( p_user_id                IN  number,             -- alliance user id whose account is to be updated
           p_user_name              IN  varchar2,           -- alliance user name
           p_password               IN  varchar2,           -- alliance user password
           p_user_account_status    IN  varchar2,           -- alliance user account status
           p_first_name             IN  varchar2,           -- alliance user first name
           p_last_name              IN  varchar2,           -- alliance user last name
           p_email_address          IN  varchar2,           -- alliance email address
           p_select_privileges      IN  varchar2,           -- alliance select privileges comma seperated
           p_create_privileges      IN  varchar2,           -- alliance create privileges comma seperated
           p_update_privileges      IN  varchar2,           -- alliance update privileges comma seperated
           p_delete_privileges      IN  varchar2,           -- alliance delete privileges comma seperated
           p_curr_user_name         IN  varchar2,           -- user name of the person deleting the account
           p_out_result            OUT  varchar2            -- Result string
          )

    IS

    /*
    || Overview:        Updates a given user account to the database
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

        v_role_count    number;
        v_role_id       number;
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

        /* Updates the user and contact details */
        BDS_ACCOUNTS_UTILS.update_account_details(
                                                    p_user_id,
                                                    p_user_name,
                                                    p_password,
                                                    p_user_account_status,
                                                    p_first_name,
                                                    p_last_name,
                                                    p_email_address,
                                                    p_curr_user_name
                                                  );

        select
            count(*)
        into
            v_role_count
        from
            bds_user_roles
        where
           user_id = p_user_id;


        if (v_role_count = 0) then

            insert into
                bds_sys_roles (
                                role_id,
                                role_name,
                                role_description,
                                role_type,
                                created_by,
                                created_date,
                                last_updated_by,
                                last_updated_date
                                )
            values
                               (
                                seq_pk_sys_roles.nextval,
                                p_user_name || '_role',
                                'Roll for Alliance user ' || p_user_name,
                                'A',
                                p_curr_user_name,
                                sysdate,
                                p_curr_user_name,
                                sysdate
                               )
            returning role_id into v_role_id;

            insert into
                bds_user_roles (user_id, role_id)
            values
                (p_user_id, v_role_id);
        else

            select
                role_id
            into
                v_role_id
            from
                bds_user_roles
            where
                user_id = p_user_id
                and rownum = 1;


        end if;





            delete
            from
                bds_role_privileges
            where
                role_id = v_role_id;


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

        for c in (select privilege_id from bds_sys_privileges where available_to <> 'V') loop
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
                                            v_role_id,
                                            c.privilege_id,
                                            v_select_value,
                                            v_create_value,
                                            v_update_value,
                                            v_delete_value

                                        );
            end if;

        end loop;

   END update_account;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_vzw_account
         ( p_user_id                IN  number,             -- vzw user id whose account is to be updated
           p_user_name              IN  varchar2,           -- vzw user name
           p_password               IN  varchar2,           -- vzw user password
           p_user_account_status    IN  varchar2,           -- vzw user account status
           p_first_name             IN  varchar2,           -- vzw user first name
           p_last_name              IN  varchar2,           -- vzw user last name
           p_email_address          IN  varchar2,           -- vzw email address
           p_assigned_roles         IN  varchar2,           -- vzw assigned roles comma seperated
           p_curr_user_name         IN  varchar2,           -- user name of the person updating the account
           p_market_segment_id      IN  varchar2,           -- market segment id associated with the user
           p_region_id              IN  varchar2,           -- region id associated with the user
           p_out_result            OUT  varchar2            -- Result string
          )

    IS

    /*
    || Overview:        Updates a given user account to the database
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


        v_assigned_roles_array  DBMS_UTILITY.UNCL_ARRAY;

   BEGIN

        /* Updates the user and contact details */
        BDS_ACCOUNTS_UTILS.update_account_details(
                                                    p_user_id,
                                                    p_user_name,
                                                    p_password,
                                                    p_user_account_status,
                                                    p_first_name,
                                                    p_last_name,
                                                    p_email_address,
                                                    p_curr_user_name

                                                   );

        /* Delete all records for the given user from aims_sys_roles table before inserting new associations */
        delete
        from
            bds_user_roles
        where
            user_id = p_user_id;

        delete
        from
            bds_user_regions
        where
            user_id = p_user_id;

        delete
        from
            bds_user_segments
        where
            user_id = p_user_id;


        insert into bds_user_regions (user_id, region_id) values (p_user_id, p_region_id);

        insert into bds_user_segments (user_id, segment_id) values (p_user_id, p_market_segment_id);

        if (INSTR(p_assigned_roles,',') > 0) then
            v_assigned_roles_array := STRING_UTILS.split_standard(p_assigned_roles, ',');
        elsif (LENGTH(p_assigned_roles) > 0) then
            v_assigned_roles_array(1) := p_assigned_roles;
        end if;

        INS_SQL('v_assigned_roles_array ' || p_assigned_roles);

         for i in 1..v_assigned_roles_array.COUNT loop

            insert into
                bds_user_roles (
                                  user_id,
                                  role_id
                                )
            values
                                (
                                  p_user_id,
                                  v_assigned_roles_array(i)
                                );


        end loop;
		
		BDS_MVNFORUM.sync_mvnforum_account(p_user_id, 'U');

   END update_vzw_account;

/* -------------------------------------------------------------------------------------------------------------  */


END BDS_ACCOUNTS; -- Package Body BDS_ACCOUNTS
/

