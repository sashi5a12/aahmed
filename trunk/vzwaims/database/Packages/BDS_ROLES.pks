CREATE OR REPLACE Package BDS_ROLES
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by BDS application for SYS ROLES maintenance.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 08-26-2003       rqazi           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_role
         ( p_role_id                IN  number,             -- role to be deleted
           p_curr_user_name         IN  varchar2,           -- user name of the person deleting the account
           p_out_result            OUT  varchar2            -- Result string
          );

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
          );


/* -------------------------------------------------------------------------------------------------------------  */


END BDS_ROLES; -- Package Specification BDS_ROLES
/

