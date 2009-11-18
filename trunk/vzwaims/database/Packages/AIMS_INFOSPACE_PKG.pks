CREATE OR REPLACE PACKAGE Aims_Infospace_Pkg
  IS

/*
|| All contents are Copyright 2004 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for INFOSPACE web service related transactions.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 11-18-2004       rqazi           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_infospace_ws_url
         ( 
           p_out_string        OUT  VARCHAR2   -- the URL of the InfoSpace Web Service
          );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_infospace_log
         ( p_apps_id                IN  NUMBER,    -- application id
           p_submit_type            IN  VARCHAR2,  -- submit type e.g. 20 OR 11  (i.e. Phase IDs for Submitted DCR, SUNSET)
           p_submit_status          IN  VARCHAR2,  -- null not submitted, 'Y' submitted successfully, 'N' submit failed
           p_submit_response        IN  VARCHAR2   -- the response received from Infospace or the local failure reason
          );
           
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_if_dcr_submitted
         ( p_apps_id                IN  NUMBER,    -- application id
           p_submit_type            IN  VARCHAR2,  -- submit type ('20' OR '11')  (i.e. Phase IDs for Submitted DCR, SUNSET)
           p_out_string             OUT VARCHAR2   -- Result ('Y' OR 'N')
          );
           
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE rollback_infospace_log
         ( p_apps_id                IN  NUMBER,    -- application id
           p_alliance_id            IN  NUMBER,    -- alliance_id
           p_submit_type            IN  VARCHAR2,  -- submit type e.g. 20 OR 11  (i.e. Phase IDs for Submitted DCR, SUNSET)
		   p_username               IN  VARCHAR2,  -- user making the rollback
		   p_out_string             OUT VARCHAR2   -- Result ('Y' OR 'N')
          );

/* -------------------------------------------------------------------------------------------------------------  */

END Aims_Infospace_Pkg; -- Package Specification AIMS_ACCOUNTS
/

