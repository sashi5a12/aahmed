CREATE OR REPLACE Package AIMS_ACCOUNTS_UTILS
  IS
/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS_ACCOUNTS package for user account maintenance.
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
    PROCEDURE update_account_details
         ( p_user_id                IN  number,             -- alliance user id whose account is to be updated
           p_user_name              IN  varchar2,           -- alliance user name
           p_password               IN  varchar2,           -- alliance user password
           p_user_account_status    IN  varchar2,           -- alliance user account status
           p_first_name             IN  varchar2,           -- alliance user first name
           p_last_name              IN  varchar2,           -- alliance user last name
           p_email_address          IN  varchar2,           -- alliance email address
           p_curr_user_name         IN  varchar2            -- user name of the person deleting the account
          );
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
          );
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
           p_curr_user_name         IN  varchar2,           -- user name of the person deleting the account
           p_permanently_deleted    IN  varchar2            -- user permanently deleted flag.
          );
/* -------------------------------------------------------------------------------------------------------------  */
END AIMS_ACCOUNTS_UTILS; -- Package Specification AIMS_ACCOUNTS_UTILS
/