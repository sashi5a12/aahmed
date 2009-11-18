CREATE OR REPLACE Package Body AIMS_ACCOUNTS_UTILS
IS
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE update_account_details
         ( p_user_id                IN  number,             -- alliance user id whose account is to be updated
           p_user_name              IN  varchar2,           -- alliance user name
           p_password               IN  varchar2,           -- alliance user password
           p_user_account_status    IN  varchar2,           -- alliance user account status
           p_first_name             IN  varchar2,           -- alliance user first name
           p_last_name              IN  varchar2,           -- alliance user last name
           p_email_address          IN  varchar2,           -- alliance email address
           p_curr_user_name         IN  varchar2            -- user name of the person deleting the account
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
   BEGIN
        update
            aims_contacts
        set
            first_name = p_first_name,
            last_name = p_last_name,
            email_address = p_email_address,
            last_updated_by = p_curr_user_name,
            last_updated_date = sysdate
        where
            contact_id = (
                select contact_id from aims_users where user_id = p_user_id
            );
        update
            aims_users
        set
            username = p_user_name,
            password = p_password,
            user_account_status = p_user_account_status,
            last_updated_by = p_curr_user_name,
            last_updated_date = sysdate
        where
            user_id = p_user_id;
   END update_account_details;
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE update_account_details
         ( p_user_id                IN  number,             -- alliance user id whose account is to be updated
           p_user_name              IN  varchar2,           -- alliance user name
           p_password               IN  varchar2,           -- alliance user password
           p_user_account_status    IN  varchar2,           -- alliance user account status
           p_first_name             IN  varchar2,           -- alliance user first name
           p_last_name              IN  varchar2,           -- alliance user last name
           p_email_address          IN  varchar2,           -- alliance email address
           p_title                  IN  varchar2,           -- alliance title
           p_phone                  IN  varchar2,           -- alliance phone
           p_mobile                 IN  varchar2,           -- alliance mobile
           p_fax                    IN  varchar2,           -- alliance fax
           p_curr_user_name         IN  varchar2            -- user name of the person deleting the account
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
   BEGIN
        update
            aims_contacts
        set
            first_name = p_first_name,
            last_name = p_last_name,
            title = p_title,
            phone = p_phone,
            mobile = p_mobile,
            fax = p_fax,
            last_updated_by = p_curr_user_name,
            last_updated_date = sysdate
        where
            contact_id = (
                select contact_id from aims_users where user_id = p_user_id
            );
        update
            aims_users
        set
            password = p_password,
            user_account_status = p_user_account_status,
            last_updated_by = p_curr_user_name,
            last_updated_date = sysdate
        where
            user_id = p_user_id;
   END update_account_details;
/* -------------------------------------------------------------------------------------------------------------  */
PROCEDURE update_account_details
         ( p_user_id                IN  number,             -- alliance user id whose account is to be updated
           p_user_name              IN  varchar2,           -- alliance user name
           p_password               IN  varchar2,           -- alliance user password
           p_user_account_status    IN  varchar2,           -- alliance user account status
           p_first_name             IN  varchar2,           -- alliance user first name
           p_last_name              IN  varchar2,           -- alliance user last name
           p_email_address          IN  varchar2,           -- alliance email address
           p_title                  IN  varchar2,           -- alliance title
           p_phone                  IN  varchar2,           -- alliance phone
           p_mobile                 IN  varchar2,           -- alliance mobile
           p_fax                    IN  varchar2,           -- alliance fax
           p_curr_user_name         IN  varchar2,            -- user name of the person deleting the account
           p_permanently_deleted    IN  varchar2            -- user permanently deleted flag.
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
   BEGIN
        update
            aims_contacts
        set
            first_name = p_first_name,
            last_name = p_last_name,
            title = p_title,
            phone = p_phone,
            mobile = p_mobile,
            fax = p_fax,
            last_updated_by = p_curr_user_name,
            last_updated_date = sysdate
        where
            contact_id = (
                select contact_id from aims_users where user_id = p_user_id
            );
        update
            aims_users
        set
            username=p_user_name,
            password = p_password,
            user_account_status = p_user_account_status,
            last_updated_by = p_curr_user_name,
            last_updated_date = sysdate,
            permanently_deleted = p_permanently_deleted
        where
            user_id = p_user_id;
   END update_account_details;
/* -------------------------------------------------------------------------------------------------------------  */
END AIMS_ACCOUNTS_UTILS; -- Package Body AIMS_ACCOUNTS_UTILS
/
