CREATE OR REPLACE Package BDS_ACCOUNTS
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
           p_market_segment_id      IN  varchar2,           -- market segment id associated with the user
           p_region_id              IN  varchar2,           -- region id associated with the user
           p_out_result            OUT  varchar2            -- Result string
          );

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_ACCOUNTS; -- Package Specification BDS_ACCOUNTS
/

