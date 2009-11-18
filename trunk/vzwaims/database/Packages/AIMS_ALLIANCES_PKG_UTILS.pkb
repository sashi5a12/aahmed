CREATE OR REPLACE PACKAGE BODY Aims_Alliances_Pkg_Utils
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_alliance_relations
         ( p_alliance_id            IN  NUMBER,            -- alliance_id of the alliance relations to be deleted
           p_curr_user_name         IN  VARCHAR2           -- user name of the person deleting the alliance
          )
    IS

    /*
    || Overview:        Deletes a given ALLIANCE relate records from the database
    ||
    ||
    || Dependencies:
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


   BEGIN

        /* Delete alliance related records */

        DELETE FROM AIMS_ALLIANCE_CONTRACT_AMENDS WHERE alliance_contract_id IN
        (SELECT alliance_contract_id FROM AIMS_ALLIANCE_CONTRACTS WHERE alliance_id = p_alliance_id);
        
        DELETE FROM AIMS_CONTENT_REQ_CNTS WHERE creative_content_id IN
        (SELECT creative_content_id FROM AIMS_CREATIVE_CONTENTS WHERE alliance_id = p_alliance_id); 

        DELETE FROM AIMS_LOAN_DEVICE_APPS WHERE loan_device_id IN
        (SELECT loan_device_id FROM AIMS_ALLIANCE_LOAN_DEVICES WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_LOAN_DEVICE_NEW_APPS WHERE loan_device_id IN
        (SELECT loan_device_id FROM AIMS_ALLIANCE_LOAN_DEVICES WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_ALLIANCE_SL_APPS WHERE alliance_sl_id IN
        (SELECT sales_lead_id FROM AIMS_ALLIANCE_SALES_LEADS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_ALLIANCE_SL_PLATFORMS WHERE alliance_sl_id IN
        (SELECT sales_lead_id FROM AIMS_ALLIANCE_SALES_LEADS WHERE alliance_id = p_alliance_id);


        -- Immediate Children of table AIMS_ALLIANCES

        DELETE FROM AIMS_ALLIANCE_CARRIERS WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_CONTRACTS WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_DEVELOPMENTS WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_DEV_TECHS WHERE alliance_id = p_alliance_id;        
        DELETE FROM AIMS_ALLIANCE_FINANCING WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_IND_FOCUS WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_IND_TOP_FOCUS WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_JMA WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_LINES_OF_BUS WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_LOAN_DEVICES WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_MUSIC_PROD_TYPES WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_MUSIC WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_PLATFORMS WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_REGIONS WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_ROLES WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_SALES_LEADS WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_SPOTLIGHTS WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_VZW_REASONS WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_ALLIANCE_WHITE_PAPERS WHERE alliance_id = p_alliance_id;
        --delete from aims_apps where alliance_id = p_alliance_id;
        DELETE FROM AIMS_CASE_STUDIES WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_CREATIVE_CONTENTS WHERE alliance_id = p_alliance_id;
        --delete from aims_enterprise_apps where national_partner_id = p_alliance_id;
        DELETE FROM AIMS_JOURNAL_ENTRIES WHERE alliance_id = p_alliance_id;
        
        UPDATE AIMS_MESSAGES SET parent_message_id = NULL WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_MESSAGES_RECPTS_GRPS where AIMS_MESSAGES_RECPTS_GRPS.MESSAGE_ID in ( select AIMS_MESSAGES.MESSAGE_ID from AIMS_MESSAGES WHERE alliance_id = p_alliance_id );
		DELETE FROM AIMS_MESSAGE_ATT where AIMS_MESSAGE_ATT.MESSAGE_ID in ( select AIMS_MESSAGES.MESSAGE_ID from AIMS_MESSAGES WHERE alliance_id = p_alliance_id );
        DELETE FROM AIMS_MESSAGES WHERE alliance_id = p_alliance_id;
		
        DELETE FROM BDS_PARTNER_MARKET_SEGMENTS WHERE partner_id = p_alliance_id;
        DELETE FROM BDS_SOLUTION_PARTNERS WHERE partner_id = p_alliance_id;
        DELETE FROM AIMS_CONTACT_UPDATE_ALLIANCES WHERE alliance_id = p_alliance_id;
        DELETE FROM AIMS_USER_OFFER WHERE alliance_id = p_alliance_id;

   END delete_alliance_relations;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_alliance_app_relations
         ( p_alliance_id            IN  NUMBER,            -- alliance_id of the alliance relations to be deleted
           p_curr_user_name         IN  VARCHAR2           -- user name of the person deleting the alliance
          )
    IS

    /*
    || Overview:        Deletes a given ALLIANCE relate records from the database
    ||
    ||
    || Dependencies:
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


   BEGIN

        -- Records related to BREW APPS

        DELETE FROM AIMS_BREW_APPS_DEVICES WHERE brew_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_BREW_APPS_J_GEO_SERVICES WHERE brew_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_BREW_NSTL_DATA WHERE brew_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_CATALOGS_DATA WHERE brew_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);
    
        DELETE FROM AIMS_BREW_APPS_HISTORY WHERE brew_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);
        
        DELETE FROM AIMS_BREW_MESSAGING_DETAILS WHERE brew_app_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);
                

        -- Records related to DASHBOARD APPS
        
        DELETE FROM AIMS_DASHBOARD_APPS_DEVICES WHERE dashboard_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);
        
        DELETE FROM AIMS_DASHBOARD_CHANNEL_IDS WHERE dashboard_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);
        
        
        -- Records related to ENTERPRISE APPS

        DELETE FROM AIMS_APPS_CASE_STUDIES WHERE enterprise_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_ENT_APPS_IND_FOCUS WHERE enterprise_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_ENT_APPS_PLATFORMS WHERE enterprise_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_ENT_APPS_REGION WHERE enterprise_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_ENT_APPS_SOL_COMP WHERE enterprise_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_ENT_APPS_SOL_TYPES WHERE enterprise_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_ENT_APPS_SPOTLIGHTS WHERE enterprise_app_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_ENT_APPS_SUB_CATEGORIES WHERE enterprise_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_ENT_APPS_VMS WHERE enterprise_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM BDS_QUESTION_SCORING WHERE solution_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM BDS_SOLUTION_COMPONENTS WHERE solution_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM BDS_SOLUTION_DEVICE_OPTIONS WHERE solution_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM BDS_SOLUTION_MARKET_SEGMENTS WHERE solution_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM BDS_SOLUTION_PARTNERS WHERE solution_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM BDS_SOLUTION_COMING_SOON WHERE solution_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);
        
        
        -- Records related to VCAST APPS

        DELETE FROM AIMS_VCAST_APPS_J_AGES WHERE vcast_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_VCAST_APPS_J_EDUCATIONS WHERE vcast_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_VCAST_APPS_J_GENDERS WHERE vcast_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_VCAST_APPS_J_INCOMES WHERE vcast_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_VCAST_APPS_J_RACES WHERE vcast_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        -- Records related to WAP APPS

        DELETE FROM AIMS_WAP_APPS_BROWSERS WHERE wap_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_WAP_APPS_FEATURES WHERE wap_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_WAP_APPS_GRAPHICS WHERE wap_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_WAP_APPS_J_LICENSE_TYPE WHERE wap_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_WAP_APPS_J_USER_IN_PARAMS WHERE wap_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_WAP_APPS_PROG_LANGS WHERE wap_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_WAP_APPS_VERSIONS WHERE wap_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);


        -- Immediate Children of table AIMS_VZAPPZONE_APPS
        DELETE FROM AIMS_VZAPP_BINARY_FIRMWARE WHERE vzappzone_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);
        DELETE FROM AIMS_VZAPP_BINARIES WHERE vzappzone_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);


        -- Immediate Children of table AIMS_APPS

        --delete from aims_alliance_loan_devices where apps_id in
        --(select apps_id from aims_apps where alliance_id = p_alliance_id);

        DELETE FROM AIMS_ALLIANCE_SL_APPS WHERE apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_APP_CERTIFICATIONS WHERE apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);
        
        DELETE FROM AIMS_APP_NETWORK_URLS WHERE apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);
        
        DELETE FROM AIMS_APP_PHASES WHERE apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_BREW_APPS WHERE brew_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_CASE_STUDIES WHERE apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_DASHBOARD_APPS WHERE dashboard_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);
        
        DELETE FROM AIMS_ENTERPRISE_APPS WHERE enterprise_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_JOURNAL_ENTRIES WHERE apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_LOAN_DEVICE_APPS WHERE apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        --delete from aims_messages where apps_id in
        --(select apps_id from aims_apps where alliance_id = p_alliance_id);

        DELETE FROM AIMS_MMS_APPS WHERE mms_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_PROV_APPS_ROLES WHERE apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_SMS_APPS WHERE sms_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_TEMP_CATALOG_DATA_MATCH WHERE apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_VCAST_APPS WHERE vcast_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_WAP_APPS WHERE wap_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_VZAPPZONE_APPS WHERE vzappzone_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_JAVA_APPS WHERE java_apps_id IN
        (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);
        
        DELETE FROM AIMS_WORKITEM WHERE MODULE_WORKFLOW_ID IN 
            (SELECT MODULE_WORKFLOW_ID FROM AIMS_MODULE_WORKFLOWS WHERE MODULE_RECORD_ID IN
                (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id)); 
        
        DELETE FROM AIMS_MODULE_WORKFLOWS WHERE MODULE_RECORD_ID IN
            (SELECT apps_id FROM AIMS_APPS WHERE alliance_id = p_alliance_id);        
        

   END delete_alliance_app_relations;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_alliance_user_relations
         ( p_alliance_id            IN  NUMBER,            -- alliance_id of the alliance relations to be deleted
           p_curr_user_name         IN  VARCHAR2           -- user name of the person deleting the alliance
          )
    IS

    /*
    || Overview:        Deletes records related to users belonging the given alliance
    ||
    ||
    || Dependencies:
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


   BEGIN


        --delete from aims_alliances where admin_user_id in
        --(select user_id from aims_users where alliance_id = p_alliance_id);

        --delete from aims_alliances where vzw_account_manager in
        --(select user_id from aims_users where alliance_id = p_alliance_id);

        --delete from aims_alliance_contracts where accept_decline_user_id in
        --(select user_id from aims_users where alliance_id = p_alliance_id);

        --delete from aims_alliance_sales_leads where submitted_by in
        --(select user_id from aims_users where alliance_id = p_alliance_id);

        --delete from aims_alliance_white_papers where submitted_by in
        --(select user_id from aims_users where alliance_id = p_alliance_id);

        --delete from aims_apps where app_created_by in
        --(select user_id from aims_users where alliance_id = p_alliance_id);

        DELETE FROM AIMS_AUDIT_TRAILS WHERE user_id IN
        (SELECT user_id FROM AIMS_USERS WHERE alliance_id = p_alliance_id);

        --delete from aims_case_studies where created_user_id in
        --(select user_id from aims_users where alliance_id = p_alliance_id);

        --delete from aims_case_studies where accept_decline_user_id in
        --(select user_id from aims_users where alliance_id = p_alliance_id);

        DELETE FROM AIMS_CHAT_USERS WHERE user_id IN
        (SELECT user_id FROM AIMS_USERS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_EMAIL_GROUP_USERS WHERE user_id IN
        (SELECT user_id FROM AIMS_USERS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_EMAIL_REMINDER_USERS WHERE user_id IN
        (SELECT user_id FROM AIMS_USERS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_NOTIF_OPT_IN_RECIPIENTS WHERE user_id IN
        (SELECT user_id FROM AIMS_USERS WHERE alliance_id = p_alliance_id);

        --delete from aims_users where manager_id in
        --(select user_id from aims_users where alliance_id = p_alliance_id);

        DELETE FROM AIMS_USER_ROLES WHERE user_id IN
        (SELECT user_id FROM AIMS_USERS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_VZW_DEPARTMENTS WHERE dept_manager_user_id IN
        (SELECT user_id FROM AIMS_USERS WHERE alliance_id = p_alliance_id);

        DELETE FROM AIMS_BULLETIN_USERS WHERE user_id IN
        (SELECT user_id FROM AIMS_USERS WHERE alliance_id = p_alliance_id);

        
   END delete_alliance_user_relations;

/* -------------------------------------------------------------------------------------------------------------  */

     PROCEDURE get_alliance_user_ids
         ( p_alliance_id            IN  NUMBER,           -- alliance_id
           p_user_ids              OUT  VARCHAR2          -- comma seperated list of user ids
         )
    IS

    /*
    || Overview:        Gets the comma seperated list of user ids belonging to this alliance
    ||
    ||
    || Dependencies:
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

    vt_varchar2     dbms_utility.uncl_array;
    v_cnt           PLS_INTEGER := 0;

   BEGIN

       FOR c IN (   SELECT
                        user_id
                    FROM
                        AIMS_USERS
                    WHERE
                        alliance_id = p_alliance_id )
       LOOP
            v_cnt := v_cnt + 1;
            vt_varchar2(v_cnt) := c.user_id;
       END LOOP;

       IF (vt_varchar2.COUNT > 0) THEN
            Parse.table_to_delimstring(vt_varchar2, p_user_ids);
       ELSE
            p_user_ids := '0';
       END IF;

   END get_alliance_user_ids;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_alliance_contact_ids
         ( p_alliance_id            IN  NUMBER,           -- alliance_id
           p_contact_ids           OUT  VARCHAR2          -- comma seperated list of contact ids
         )
    IS

    /*
    || Overview:        Gets the comma seperated list of contact ids belonging to this alliance
    ||
    ||
    || Dependencies:
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
        v_contact_id    VARCHAR2(50);
        vt_varchar2     dbms_utility.uncl_array;
        v_cnt           PLS_INTEGER := 0;

   BEGIN

        FOR c IN (   SELECT
                        NVL(tech_contact_id,0) tech_contact_id
                    FROM
                        AIMS_APPS
                    WHERE
                        alliance_id = p_alliance_id )
        LOOP
            v_cnt := v_cnt + 1;
            vt_varchar2(v_cnt) := c.tech_contact_id;
        END LOOP;

        SELECT
            NVL(bus_contact_id, 0)
        INTO
            v_contact_id
        FROM
            AIMS_ALLIANCES
        WHERE
            alliance_id = p_alliance_id;

        v_cnt := v_cnt + 1;
        vt_varchar2(v_cnt) := v_contact_id;

        SELECT
            NVL(mktg_pr_contact_id, 0)
        INTO
            v_contact_id
        FROM
            AIMS_ALLIANCES
        WHERE
            alliance_id = p_alliance_id;

        v_cnt := v_cnt + 1;
        vt_varchar2(v_cnt) := v_contact_id;


        SELECT
            NVL(tech_contact_id, 0)
        INTO
            v_contact_id
        FROM
            AIMS_ALLIANCES
        WHERE
            alliance_id = p_alliance_id;

        v_cnt := v_cnt + 1;
        vt_varchar2(v_cnt) := v_contact_id;


        SELECT
            NVL(executive_contact_id, 0)
        INTO
            v_contact_id
        FROM
            AIMS_ALLIANCES
        WHERE
            alliance_id = p_alliance_id;

        v_cnt := v_cnt + 1;
        vt_varchar2(v_cnt) := v_contact_id;

        IF (vt_varchar2.COUNT > 0) THEN
            Parse.table_to_delimstring(vt_varchar2, p_contact_ids);
        ELSE
            p_contact_ids := '0';
        END IF;

        dbms_output.put_line('Value of p_contact_ids='||p_contact_ids);
   END get_alliance_contact_ids;

/* -------------------------------------------------------------------------------------------------------------  */

     PROCEDURE get_alliance_user_ids
         ( p_alliance_id            IN  NUMBER,                     -- alliance_id
           p_t_user_ids            OUT  dbms_utility.uncl_array     -- table of user ids
         )
    IS

    /*
    || Overview:        Gets the comma seperated list of user ids belonging to this alliance
    ||
    ||
    || Dependencies:
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

    vt_varchar2     dbms_utility.uncl_array;
    v_cnt           PLS_INTEGER := 0;

   BEGIN

       FOR c IN (   SELECT
                        user_id
                    FROM
                        AIMS_USERS
                    WHERE
                        alliance_id = p_alliance_id )
       LOOP
            v_cnt := v_cnt + 1;
            vt_varchar2(v_cnt) := c.user_id;
       END LOOP;

       p_t_user_ids := vt_varchar2;

   END get_alliance_user_ids;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_alliance_contact_ids
         ( p_alliance_id            IN  NUMBER,                     -- alliance_id
           p_t_contact_ids         OUT  dbms_utility.uncl_array     -- table of contact ids
         )
    IS

    /*
    || Overview:        Gets the comma seperated list of contact ids belonging to this alliance
    ||
    ||
    || Dependencies:
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
        v_contact_id    VARCHAR2(50);
        vt_varchar2     dbms_utility.uncl_array;
        v_cnt           PLS_INTEGER := 0;

   BEGIN

        FOR c IN (   SELECT
                        NVL(tech_contact_id,0) tech_contact_id
                    FROM
                        AIMS_APPS
                    WHERE
                        alliance_id = p_alliance_id )
        LOOP
            v_cnt := v_cnt + 1;
            vt_varchar2(v_cnt) := c.tech_contact_id;
        END LOOP;

        SELECT
            NVL(bus_contact_id, 0)
        INTO
            v_contact_id
        FROM
            AIMS_ALLIANCES
        WHERE
            alliance_id = p_alliance_id;

        v_cnt := v_cnt + 1;
        vt_varchar2(v_cnt) := v_contact_id;

        SELECT
            NVL(mktg_pr_contact_id, 0)
        INTO
            v_contact_id
        FROM
            AIMS_ALLIANCES
        WHERE
            alliance_id = p_alliance_id;

        v_cnt := v_cnt + 1;
        vt_varchar2(v_cnt) := v_contact_id;


        SELECT
            NVL(tech_contact_id, 0)
        INTO
            v_contact_id
        FROM
            AIMS_ALLIANCES
        WHERE
            alliance_id = p_alliance_id;

        v_cnt := v_cnt + 1;
        vt_varchar2(v_cnt) := v_contact_id;


        SELECT
            NVL(executive_contact_id, 0)
        INTO
            v_contact_id
        FROM
            AIMS_ALLIANCES
        WHERE
            alliance_id = p_alliance_id;

        v_cnt := v_cnt + 1;
        vt_varchar2(v_cnt) := v_contact_id;


        p_t_contact_ids := vt_varchar2;

   END get_alliance_contact_ids;

/* -------------------------------------------------------------------------------------------------------------  */

END Aims_Alliances_Pkg_Utils; -- Package Body AIMS_ALLIANCES_PKG_UTILS
/
