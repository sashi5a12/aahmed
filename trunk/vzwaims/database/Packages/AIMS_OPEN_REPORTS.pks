CREATE OR REPLACE Package AIMS_OPEN_REPORTS
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application to keep the AIMS_USERS and REPORT_USER tables in sync.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 04-15-2004       rqazi           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE sync_report_account
         ( p_aims_user_id                IN  number,             -- alliance user id whose account is to be updated
           p_trans_type                  IN  varchar2            -- 'A' add  'U' update 'D' delete
          );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_OPEN_REPORTS; -- Package Specification AIMS_OPEN_REPORTS
/

