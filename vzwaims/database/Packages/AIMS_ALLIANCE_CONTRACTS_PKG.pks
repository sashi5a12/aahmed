CREATE OR REPLACE Package AIMS_ALLIANCE_CONTRACTS_PKG
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for alliance contracts/amendment management.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 08-05-2004       rqazi           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE ins_alliance_cont_amendments
         ( p_contract_id            IN  number,             -- alliance_id of the alliance to be deleted
           p_amendment_ids          IN  varchar2,           -- comma seperated list of amendment ids
           p_curr_user_name         IN  varchar2            -- the username of the person creating the amendment
          );

/* -------------------------------------------------------------------------------------------------------------  */

     PROCEDURE ins_cont_level_amend_for_alncs
         ( p_contract_id            IN  number,             -- contract_id
           p_alliance_contract_id   IN  number,             -- alliance_contract_id
           p_curr_user_name         IN  varchar2            -- the username of the person creating the amendment
          );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_ALLIANCE_CONTRACTS_PKG; -- Package Specification AIMS_ALLIANCE_CONTRACTS_PKG
/

