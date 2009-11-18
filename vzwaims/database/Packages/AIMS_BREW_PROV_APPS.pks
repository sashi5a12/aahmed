CREATE OR REPLACE Package AIMS_BREW_PROV_APPS
  IS

/*
|| All contents are Copyright 2004 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for getting notification, users, message cursor.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 06-03-2004       rqazi           Created
|| 07-05-2004       aimtiaz		   Modified for attachments in insert_messages
||
||
||
*/
/* -------------------------------------------------------------------------------------------------------------  */


    PROCEDURE get_prov_roles_from_apps_id
         (
            p_apps_id                   number,
            p_out_prov_sel_roles    OUT TYPES.cursor_type,       -- Result cursor
            p_out_prov_avail_roles  OUT TYPES.cursor_type
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_prov_roles_from_string
         (
            p_role_ids_string           varchar2,
            p_out_prov_sel_roles    OUT TYPES.cursor_type,       -- Result cursor
            p_out_prov_avail_roles  OUT TYPES.cursor_type
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_prov_app_roles
         ( p_apps_id                IN  number,
           p_role_ids_string        IN  varchar2
          );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_BREW_PROV_APPS; -- Package Specification AIMS_BREW_PROV_APPS
/

