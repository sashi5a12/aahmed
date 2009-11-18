CREATE OR REPLACE Package AIMS_EMAIL_GROUPS_PKG
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for EMAIL groups maintenance.
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

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_email_group
         ( p_email_group_id         IN  number,              -- email_group_id to be deleted
           p_curr_user_name         IN  varchar2             -- user name of the person deleting the email group
          );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_email_group_records
         (
           p_out_result                 OUT     TYPES.cursor_type
         );

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
         );

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
         );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_EMAIL_GROUPS_PKG; -- Package Specification AIMS_EMAIL_GROUPS_PKG
/

