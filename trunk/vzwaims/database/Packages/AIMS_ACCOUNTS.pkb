
CREATE OR REPLACE Package Body AIMS_ACCOUNTS
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
            aims_audit_trails
        where
            user_id = p_user_id;
        delete
        from
            aims_chat_users
        where
            user_id= p_user_id;
        delete
        from
            aims_email_group_users
        where
            user_id= p_user_id;
        delete
        from
            aims_email_reminder_users
        where
            user_id = p_user_id;
        delete
        from
            aims_notif_opt_in_recipients
        where
            user_id = p_user_id;
        delete
        from
            aims_user_roles
        where
            user_id = p_user_id;
        delete
        from
            aims_vzw_departments
        where
            dept_manager_user_id = p_user_id;
        delete
        from
            aims_bulletin_users
        where
            user_id = p_user_id;
        delete
        from
            aims_users
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
   BEGIN
        delete
        from
            aims_audit_trails
        where
            user_id = p_user_id;
        delete
        from
            aims_chat_users
        where
            user_id= p_user_id;
        delete
        from
            aims_email_group_users
        where
            user_id= p_user_id;
        delete
        from
            aims_email_reminder_users
        where
            user_id = p_user_id;
        delete
        from
            aims_notif_opt_in_recipients
        where
            user_id = p_user_id;
        delete
        from
            aims_user_roles
        where
            user_id = p_user_id;
        delete
        from
            aims_vzw_departments
        where
            dept_manager_user_id = p_user_id;
        delete
        from
            aims_bulletin_users
        where
            user_id = p_user_id;
        delete
        from
            aims_users
        where
            user_id = p_user_id;
   END delete_account;
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE update_account
         ( p_user_id                IN  number,             -- vzw user id whose account is to be updated
           p_user_name              IN  varchar2,           -- vzw user name
           p_password               IN  varchar2,           -- vzw user password
           p_user_account_status    IN  varchar2,           -- vzw user account status
           p_first_name             IN  varchar2,           -- vzw user first name
           p_last_name              IN  varchar2,           -- vzw user last name
           p_email_address          IN  varchar2,           -- vzw email address
           p_assigned_roles         IN  varchar2,           -- vzw assigned roles comma seperated
           p_curr_user_name         IN  varchar2,           -- user name of the person updating the account
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
        AIMS_ACCOUNTS_UTILS.update_account_details(
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
            aims_user_roles
        where
            user_id = p_user_id;
        if (p_assigned_roles != '0') then
            if (INSTR(p_assigned_roles,',') > 0) then
                v_assigned_roles_array := STRING_UTILS.split_standard(p_assigned_roles, ',');
            elsif (LENGTH(p_assigned_roles) > 0) then
                v_assigned_roles_array(1) := p_assigned_roles;
            end if;
            INS_SQL('v_assigned_roles_array ' || p_assigned_roles);
             for i in 1..v_assigned_roles_array.COUNT loop
                insert into
                    aims_user_roles (
                                      user_id,
                                      role_id
                                    )
                values
                                    (
                                      p_user_id,
                                      v_assigned_roles_array(i)
                                    );
            end loop;
       end if;
   END update_account;
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE update_account
         ( p_user_id                IN  number,             -- vzw user id whose account is to be updated
           p_user_name              IN  varchar2,           -- vzw user name
           p_password               IN  varchar2,           -- vzw user password
           p_user_account_status    IN  varchar2,           -- vzw user account status
           p_first_name             IN  varchar2,           -- vzw user first name
           p_last_name              IN  varchar2,           -- vzw user last name
           p_email_address          IN  varchar2,           -- vzw email address
           p_title                  IN  varchar2,           -- vzw title
           p_phone                  IN  varchar2,           -- vzw phone
           p_mobile                 IN  varchar2,           -- vzw mobile
           p_fax                    IN  varchar2,           -- vzw fax
           p_assigned_roles         IN  varchar2,           -- vzw assigned roles comma seperated
           p_curr_user_name         IN  varchar2,           -- user name of the person updating the account
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
        AIMS_ACCOUNTS_UTILS.update_account_details(
                                                    p_user_id,
                                                    p_user_name,
                                                    p_password,
                                                    p_user_account_status,
                                                    p_first_name,
                                                    p_last_name,
                                                    p_email_address,
                                                    p_title,
                                                    p_phone,
                                                    p_mobile,
                                                    p_fax,
                                                    p_curr_user_name
                                                   );
        /* Delete all records for the given user from aims_sys_roles table before inserting new associations */
        delete
        from
            aims_user_roles
        where
            user_id = p_user_id;
        if (p_assigned_roles != '0') then
            if (INSTR(p_assigned_roles,',') > 0) then
                v_assigned_roles_array := STRING_UTILS.split_standard(p_assigned_roles, ',');
            elsif (LENGTH(p_assigned_roles) > 0) then
                v_assigned_roles_array(1) := p_assigned_roles;
            end if;
            INS_SQL('v_assigned_roles_array ' || p_assigned_roles);
             for i in 1..v_assigned_roles_array.COUNT loop
                insert into
                    aims_user_roles (
                                      user_id,
                                      role_id
                                    )
                values
                                    (
                                      p_user_id,
                                      v_assigned_roles_array(i)
                                    );
            end loop;
       end if;
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
        AIMS_ACCOUNTS_UTILS.update_account_details(
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
            aims_user_roles
        where
            user_id = p_user_id;
        if (INSTR(p_assigned_roles,',') > 0) then
            v_assigned_roles_array := STRING_UTILS.split_standard(p_assigned_roles, ',');
        elsif (LENGTH(p_assigned_roles) > 0) then
            v_assigned_roles_array(1) := p_assigned_roles;
        end if;
        INS_SQL('v_assigned_roles_array ' || p_assigned_roles);
         for i in 1..v_assigned_roles_array.COUNT loop
            insert into
                aims_user_roles (
                                  user_id,
                                  role_id
                                )
            values
                                (
                                  p_user_id,
                                  v_assigned_roles_array(i)
                                );
        end loop;
   END update_vzw_account;
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE update_vzw_account
         ( p_user_id                IN  number,             -- vzw user id whose account is to be updated
           p_user_name              IN  varchar2,           -- vzw user name
           p_password               IN  varchar2,           -- vzw user password
           p_user_account_status    IN  varchar2,           -- vzw user account status
           p_first_name             IN  varchar2,           -- vzw user first name
           p_last_name              IN  varchar2,           -- vzw user last name
           p_email_address          IN  varchar2,           -- vzw email address
           p_title                  IN  varchar2,           -- vzw title
           p_phone                  IN  varchar2,           -- vzw phone
           p_mobile                 IN  varchar2,           -- vzw mobile
           p_fax                    IN  varchar2,           -- vzw fax
           p_assigned_roles         IN  varchar2,           -- vzw assigned roles comma seperated
           p_curr_user_name         IN  varchar2,           -- user name of the person updating the account
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
        AIMS_ACCOUNTS_UTILS.update_account_details(
                                                    p_user_id,
                                                    p_user_name,
                                                    p_password,
                                                    p_user_account_status,
                                                    p_first_name,
                                                    p_last_name,
                                                    p_email_address,
                                                    p_title,
                                                    p_phone,
                                                    p_mobile,
                                                    p_fax,
                                                    p_curr_user_name
                                                   );
        /* Delete all records for the given user from aims_sys_roles table before inserting new associations */
        delete
        from
            aims_user_roles
        where
            user_id = p_user_id;
        if (INSTR(p_assigned_roles,',') > 0) then
            v_assigned_roles_array := STRING_UTILS.split_standard(p_assigned_roles, ',');
        elsif (LENGTH(p_assigned_roles) > 0) then
            v_assigned_roles_array(1) := p_assigned_roles;
        end if;
        INS_SQL('v_assigned_roles_array ' || p_assigned_roles);
         for i in 1..v_assigned_roles_array.COUNT loop
            insert into
                aims_user_roles (
                                  user_id,
                                  role_id
                                )
            values
                                (
                                  p_user_id,
                                  v_assigned_roles_array(i)
                                );
        end loop;
   END update_vzw_account;
/* -------------------------------------------------------------------------------------------------------------  */
   PROCEDURE update_vzw_account
         ( p_user_id                IN  number,             -- vzw user id whose account is to be updated
           p_user_name              IN  varchar2,           -- vzw user name
           p_password               IN  varchar2,           -- vzw user password
           p_user_account_status    IN  varchar2,           -- vzw user account status
           p_first_name             IN  varchar2,           -- vzw user first name
           p_last_name              IN  varchar2,           -- vzw user last name
           p_email_address          IN  varchar2,           -- vzw email address
           p_title                  IN  varchar2,           -- vzw title
           p_phone                  IN  varchar2,           -- vzw phone
           p_mobile                 IN  varchar2,           -- vzw mobile
           p_fax                    IN  varchar2,           -- vzw fax
           p_assigned_roles         IN  varchar2,           -- vzw assigned roles comma seperated
           p_curr_user_name         IN  varchar2,           -- user name of the person updating the account
           p_permanently_deleted    IN  varchar2,           -- user permanently deleted flag.
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
        AIMS_ACCOUNTS_UTILS.update_account_details(
                                                    p_user_id,
                                                    p_user_name,
                                                    p_password,
                                                    p_user_account_status,
                                                    p_first_name,
                                                    p_last_name,
                                                    p_email_address,
                                                    p_title,
                                                    p_phone,
                                                    p_mobile,
                                                    p_fax,
                                                    p_curr_user_name,
                                                    p_permanently_deleted
                                                   );
        /* Delete all records for the given user from aims_sys_roles table before inserting new associations */
        delete
        from
            aims_user_roles
        where
            user_id = p_user_id;
        if (INSTR(p_assigned_roles,',') > 0) then
            v_assigned_roles_array := STRING_UTILS.split_standard(p_assigned_roles, ',');
        elsif (LENGTH(p_assigned_roles) > 0) then
            v_assigned_roles_array(1) := p_assigned_roles;
        end if;
        INS_SQL('v_assigned_roles_array ' || p_assigned_roles);
         for i in 1..v_assigned_roles_array.COUNT loop
            insert into
                aims_user_roles (
                                  user_id,
                                  role_id
                                )
            values
                                (
                                  p_user_id,
                                  v_assigned_roles_array(i)
                                );
        end loop;
   END update_vzw_account;
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE get_alliance_contact_ids
         ( p_alliance_id            IN  number,           -- alliance_id
           p_out_contact_ids       OUT  TYPES.cursor_type
         )
    IS
    /*
    || Overview:        Gets the comma seperated list of contact ids belonging to this alliance
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 02-03-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */
        v_contact_id    varchar2(50);
        vt_varchar2     dbms_utility.uncl_array;
        v_cnt           pls_integer := 0;
   BEGIN
        for c in (select tech_contact_id from aims_apps where alliance_id = p_alliance_id) loop
            begin
                insert into aims_distinct_contact_ids (contact_id)
                    values (c.tech_contact_id);
            exception
                when others then
                    null;
            end;
        end loop;
        begin
            insert into aims_distinct_contact_ids(contact_id)
                select
                    bus_contact_id
                from
                    aims_alliances
                where
                    alliance_id = p_alliance_id;
        exception
            when others then
                null;
        end;
        begin
            insert into aims_distinct_contact_ids(contact_id)
                select
                    mktg_pr_contact_id
                from
                    aims_alliances
                where
                    alliance_id = p_alliance_id;
        exception
            when others then
                null;
        end;
        begin
            insert into aims_distinct_contact_ids(contact_id)
                select
                    tech_contact_id
                from
                    aims_alliances
                where
                    alliance_id = p_alliance_id;
        exception
            when others then
                null;
        end;
        begin
            insert into aims_distinct_contact_ids(contact_id)
                select
                    executive_contact_id
                from
                    aims_alliances
                where
                    alliance_id = p_alliance_id;
        exception
            when others then
                null;
        end;
        open p_out_contact_ids for
            select
                contact_id
            from
                aims_contacts
            where
                contact_id in
                    (select contact_id from aims_distinct_contact_ids
                        minus
                     select contact_id from aims_users where alliance_id = p_alliance_id);
   END get_alliance_contact_ids;
/* -------------------------------------------------------------------------------------------------------------  */
END AIMS_ACCOUNTS; -- Package Body AIMS_ACCOUNTS
/

