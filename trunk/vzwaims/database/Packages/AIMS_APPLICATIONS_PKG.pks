CREATE OR REPLACE Package AIMS_APPLICATIONS_PKG
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless Inc.
||
|| Author:              Adnan Makda (amakda)
||
|| File:
||
|| Overview:            Used by AIMS application for application maintenance.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
||
||
||
||
*/
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_access_to_spotlight_page
         (
            p_apps_id                   IN  number,           -- application id
            p_out_result                OUT  varchar2         -- 'Y' indicates 'Access Permitted', 'N' otherwise
          );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_APPLICATIONS_PKG; -- Package Specification AIMS_APPLICATIONS_PKG
/

