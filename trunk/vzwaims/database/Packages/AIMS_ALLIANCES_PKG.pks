CREATE OR REPLACE Package AIMS_ALLIANCES_PKG
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for alliance maintenance.
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

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_alliance
         ( p_alliance_id            IN  number,             -- alliance_id of the alliance to be deleted
           p_curr_user_name         IN  varchar2           -- user name of the person deleting the alliance
          );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE generate_vendor_id
         ( p_alliance_id            IN  number,             -- alliance_id of the alliance to be deleted
           p_vendor_id             out  number           -- user name of the person deleting the alliance
          );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_uploaded_files
         (
            p_alliance_id            IN  number,          -- alliance id
            p_out_result            OUT  varchar2         -- '' if no problems, comma seperated list of files if problem
          );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_ALLIANCES_PKG; -- Package Specification AIMS_ALLIANCES_PKG
/

