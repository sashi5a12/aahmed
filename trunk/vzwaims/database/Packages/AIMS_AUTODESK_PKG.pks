CREATE OR REPLACE PACKAGE Aims_Autodesk_Pkg
  IS

/*
|| All contents are Copyright 2004 Verizon Wireless, Inc.
||
|| Author:              Adnan Makda (amakda)
||
|| File:
||
|| Overview:            Used by AIMS application for AUTODESK web service related transactions.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 10-06-2005       amakda           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_autodesk_ws_url
         ( 
           p_out_string        OUT  VARCHAR2   -- the URL of the InfoSpace Web Service
          );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_autodesk_log
         ( p_apps_id                IN  NUMBER,    -- application id
           p_autodesk_phase_id      IN  VARCHAR2,  -- Autodesk Phase ID
           p_submit_status          IN  VARCHAR2,  -- null not submitted, 'Y' submitted successfully, 'N' submit failed
           p_submit_response        IN  VARCHAR2   -- the response received from Autodesk or the local failure reason
          );
           
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_if_lbs_submitted
         ( p_apps_id                IN  NUMBER,    -- application id
           p_autodesk_phase_id      IN  VARCHAR2,  -- Autodesk Phase ID
           p_out_string             OUT VARCHAR2   -- Result ('Y' OR 'N')
          );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE add_lbs_client_id
         (  p_brew_apps_id     IN   NUMBER,
            p_lbs_client_id   OUT   NUMBER
         );
		            
/* -------------------------------------------------------------------------------------------------------------  */

END Aims_Autodesk_Pkg; -- Package Specification AIMS_AUTODESK_PKG
/

