CREATE OR REPLACE PACKAGE BODY Aims_Infospace_Pkg
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_infospace_log
         ( p_apps_id                IN  NUMBER,    -- application id
           p_submit_type            IN  VARCHAR2,  -- submit type ('20' OR '11')  (i.e. Phase IDs for Submitted DCR, SUNSET)
           p_submit_status          IN  VARCHAR2,  -- null not submitted, 'Y' submitted successfully, 'N' submit failed
           p_submit_response        IN  VARCHAR2   -- the response received from Infospace or the local failure reason
          )
    IS

    /*
    || Overview:        Inserts a record in the infospace log table for each InfoSpace transaction.
    ||
    ||
    || Dependencies:
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

   BEGIN

         INSERT INTO AIMS_INFOSPACE_LOG
            (
                infospace_log_id,
                apps_id,
                submit_type,
                submit_status,
                submit_response,
                submit_date
            )
         VALUES
            (
                seq_pk_infospace_log.NEXTVAL,
                p_apps_id,
                p_submit_type,
                p_submit_status,
                p_submit_response,
                SYSDATE
            );

        COMMIT;
        
   END insert_infospace_log;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_infospace_ws_url
         ( 
           p_out_string        OUT  VARCHAR2   -- the URL of the InfoSpace Web Service
          )
    IS

    /*
    || Overview:        Gets the URL for the InfoSpace Web Service.
    ||
    ||
    || Dependencies:
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

   BEGIN
        
        SELECT
            parameter_value
        INTO
            p_out_string
        FROM
            AIMS_SYS_PARAMETERS
        WHERE
            parameter_name = 'INFOSPACE_WEBSERVICE_URL';
            
   EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_out_string := 'http://vzwdemoappmgr.infospace.com/vzwhandler/default.asmx';
   END get_infospace_ws_url;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_if_dcr_submitted
         ( p_apps_id                IN  NUMBER,    -- application id
           p_submit_type            IN  VARCHAR2,  -- submit type ('20' OR '11')  (i.e. Phase IDs for Submitted DCR, SUNSET)
           p_out_string             OUT VARCHAR2   -- Result ('Y' OR 'N')
          )
    IS

    /*
    || Overview:        Gets the URL for the InfoSpace Web Service.
    ||
    ||
    || Dependencies:
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

   BEGIN
        
        SELECT
            submit_status
        INTO
            p_out_string
        FROM
            AIMS_INFOSPACE_LOG
        WHERE
            apps_id = p_apps_id
            AND submit_type = p_submit_type
            AND submit_status = 'Y';
            
   EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_out_string := 'N';
   END check_if_dcr_submitted;

/* -------------------------------------------------------------------------------------------------------------  */


    PROCEDURE rollback_infospace_log
         ( p_apps_id                IN  NUMBER,    -- application id
           p_alliance_id            IN  NUMBER,    -- alliance_id
           p_submit_type            IN  VARCHAR2,  -- submit type e.g. 20 OR 11  (i.e. Phase IDs for Submitted DCR, SUNSET)
		   p_username               IN  VARCHAR2,  -- user making the rollback
		   p_out_string             OUT VARCHAR2   -- Result ('Y' OR 'N')
          )
    IS

    /*
    || Overview:        Rollsback 'Y' entry to 'R' as the application has been rolled back from 'PENDING DCR' to 'SUBMITTED DCR'.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 05-02-2006       amakda          Created
    ||
    ||
    ||
    ||
    */

            v_allowed_app       NUMBER;
            v_app_version       VARCHAR2(30);
				
   BEGIN

          SELECT
              apps_id, version
          INTO
              v_allowed_app, v_app_version
          FROM 
              AIMS_APPS
          WHERE
              apps_id = p_apps_id 
              AND phase_id = p_submit_type;

		 UPDATE AIMS_APPS
		 SET phase_id = 18
		 WHERE apps_id = p_apps_id
		 AND phase_id = 20;

         UPDATE AIMS_WAP_APPS
		 SET rolled_back_to_pending_dcr = 'Y',
		 version_before_rollback = v_app_version
		 WHERE wap_apps_id = p_apps_id;

		 UPDATE AIMS_INFOSPACE_LOG
		 SET submit_status = 'R'
		 WHERE apps_id = p_apps_id
		 AND submit_type = p_submit_type
		 AND submit_status = 'Y';
		 
		 Aims_Utils.add_journal_entry(p_apps_id, p_alliance_id, 'Application Rolled Back to PENDING DCR from SUBMITTED DCR', 'PR', p_username);
		 
         COMMIT;
		
		 p_out_string := 'Y';

   EXCEPTION
        WHEN NO_DATA_FOUND THEN
		    ROLLBACK;
            p_out_string := 'N';
			        
   END rollback_infospace_log;

/* -------------------------------------------------------------------------------------------------------------  */

END Aims_Infospace_Pkg; -- Package Body AIMS_INFOSPACE_PKG
/

