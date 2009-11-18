CREATE OR REPLACE Package AIMS_ACCOUNTS
  IS
/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for user account maintenance.
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
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE delete_vzw_account
         ( p_user_id                IN  number,             -- alliance user whose account is to be deleted
           p_curr_user_name         IN  varchar2,             -- user name of the person deleting the account
           p_out_result            OUT  varchar2            -- Result string
          );
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE delete_account
         ( p_user_id                IN  number,             -- alliance user whose account is to be deleted
           p_curr_user_name         IN  varchar2,             -- user name of the person deleting the account
           p_out_result            OUT  varchar2            -- Result string
          );
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
          );
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
          );
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
          );
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
          );
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
          );
/* -------------------------------------------------------------------------------------------------------------  */
    PROCEDURE get_alliance_contact_ids
         ( p_alliance_id            IN  number,           -- alliance_id
           p_out_contact_ids       OUT  TYPES.cursor_type
         );
/* -------------------------------------------------------------------------------------------------------------  */
END AIMS_ACCOUNTS; -- Package Specification AIMS_ACCOUNTS
/
