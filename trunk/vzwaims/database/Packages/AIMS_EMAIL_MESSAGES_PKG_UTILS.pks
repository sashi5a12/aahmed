CREATE OR REPLACE Package AIMS_EMAIL_MESSAGES_PKG_UTILS
  IS

/*
|| All contents are Copyright Verizon Wireless.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS_EVENTS_PKG_UTILS package
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

    PROCEDURE insert_dist_contact_ids
         (
           p_in_group_id                 IN     number,
           p_in_tbl_contacts             IN     DBMS_UTILITY.UNCL_ARRAY
         );

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_to_recipient_string
        RETURN varchar2;
    PRAGMA restrict_references( get_to_recipient_string, wnds, wnps, rnps );

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_complete_recipient_string
        RETURN varchar2;
    PRAGMA restrict_references( get_complete_recipient_string, wnds, wnps, rnps );

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_contact_id (p_user_id number)
    RETURN  number;
    PRAGMA restrict_references( get_contact_id, wnds, wnps, rnps );

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_admin_to_id (p_str_contact_ids varchar2)
    RETURN  number;

    PRAGMA restrict_references( get_admin_to_id, wnds, wnps, rnps );

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_admin_from_id
    RETURN  number;

    PRAGMA restrict_references( get_admin_from_id, wnds, wnps, rnps );

/* -------------------------------------------------------------------------------------------------------------  */

     PROCEDURE all_group_contacts
         (
           p_in_group_id                 IN     number,
           p_out_result                 OUT     DBMS_UTILITY.UNCL_ARRAY
         );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_EMAIL_MESSAGES_PKG_UTILS; -- Package Specification AIMS_EMAIL_MESSAGES_PKG_UTILS
/

