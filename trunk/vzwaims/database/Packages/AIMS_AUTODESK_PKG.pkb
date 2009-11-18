CREATE OR REPLACE PACKAGE BODY Aims_Autodesk_Pkg
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_autodesk_log
         ( p_apps_id                IN  NUMBER,    -- application id
           p_autodesk_phase_id      IN  VARCHAR2,  -- Autodesk Phase ID
           p_submit_status          IN  VARCHAR2,  -- null not submitted, 'Y' submitted successfully, 'N' submit failed
           p_submit_response        IN  VARCHAR2   -- the response received from Autodesk or the local failure reason
          )
    IS

    /*
    || Overview:        Inserts a record in the Autodesk log table for each Autodesk transaction.
    ||
    ||
    || Dependencies:
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

   BEGIN

         INSERT INTO aims_autodesk_log
            (
                autodesk_log_id,
                apps_id,
                lbs_autodesk_phase_id,
                submit_status,
                submit_response,
                submit_date
            )
         VALUES
            (
                seq_pk_autodesk_log.NEXTVAL,
                p_apps_id,
                p_autodesk_phase_id,
                p_submit_status,
                p_submit_response,
                SYSDATE
            );

        COMMIT;
        
   END insert_autodesk_log;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_autodesk_ws_url
         ( 
           p_out_string        OUT  VARCHAR2   -- the URL of the InfoSpace Web Service
          )
    IS

    /*
    || Overview:        Gets the URL for the Autodesk Web Service.
    ||
    ||
    || Dependencies:
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

   BEGIN
        
        SELECT
            parameter_value
        INTO
            p_out_string
        FROM
            aims_sys_parameters
        WHERE
            parameter_name = 'INFOSPACE_WEBSERVICE_URL';
            
   EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_out_string := 'http://vzwdemoappmgr.infospace.com/vzwhandler/default.asmx';
   END get_autodesk_ws_url;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_if_lbs_submitted
         ( p_apps_id                IN  NUMBER,    -- application id
           p_autodesk_phase_id      IN  VARCHAR2,  -- Autodesk Phase ID
           p_out_string             OUT VARCHAR2   -- Result ('Y' OR 'N')
          )
    IS

    /*
    || Overview:        Check if LBS is submitted
    ||
    ||
    || Dependencies:
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

   BEGIN
        
        SELECT
            submit_status
        INTO
            p_out_string
        FROM
            aims_autodesk_log
        WHERE
            apps_id = p_apps_id
            AND lbs_autodesk_phase_id = p_autodesk_phase_id
            AND submit_status = 'Y';
            
   EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_out_string := 'N';
   END check_if_lbs_submitted;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE add_lbs_client_id
         (  p_brew_apps_id     IN   NUMBER,
            p_lbs_client_id   OUT   NUMBER
         )
     IS

    BEGIN

        SELECT SEQ_LBS_CLIENT_IDS.NEXTVAL INTO p_lbs_client_id FROM dual;

        UPDATE aims_brew_apps SET lbs_client_id = p_lbs_client_id WHERE  brew_apps_id = p_brew_apps_id;

    END add_lbs_client_id;
   
/* -------------------------------------------------------------------------------------------------------------  */

END Aims_Autodesk_Pkg; -- Package Body AIMS_AUTODESK_PKG
/

