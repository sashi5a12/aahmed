CREATE OR REPLACE Package AIMS_EMAIL_GROUPS_PKG_UTILS
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

    FUNCTION all_contact_types
    RETURN  aimsTableType;
    PRAGMA restrict_references( all_contact_types, wnds, wnps, rnps );

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION all_contract_status
    RETURN  aimsTableType;
    PRAGMA restrict_references( all_contract_status, wnds, wnps, rnps );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_EMAIL_GROUPS_PKG_UTILS; -- Package Specification AIMS_EMAIL_GROUPS_PKG_UTILS
/

