CREATE OR REPLACE PACKAGE BODY Aims_Utils
IS

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION value_in_table
         (  p_value     VARCHAR2,
            p_table     DBMS_UTILITY.UNCL_ARRAY
         )
    RETURN BOOLEAN IS

    BEGIN
        IF (p_table.COUNT = 0) THEN
            RETURN FALSE;
        ELSE
           FOR i IN 1..p_table.COUNT LOOP
                IF (p_value = p_table(i)) THEN
                    RETURN TRUE;
                END IF;
           END LOOP;
        END IF;
            RETURN FALSE;
    END value_in_table;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE add_two_tables
         (  p_table_in      IN               DBMS_UTILITY.UNCL_ARRAY,
            p_table_in_out  IN OUT NOCOPY    DBMS_UTILITY.UNCL_ARRAY
         )
    IS
        cnt PLS_INTEGER := p_table_in_out.COUNT;
    BEGIN

        IF (p_table_in.COUNT > 0) THEN
           FOR i IN 1..p_table_in.COUNT LOOP
                cnt := cnt + 1;
                p_table_in_out(cnt) := p_table_in(i);
           END LOOP;
        END IF;

    END add_two_tables;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE add_journal_entry
         (  p_apps_id      IN   NUMBER,
            p_alliance_id  IN   NUMBER,
            p_journal_text IN   VARCHAR2,
            p_journal_type IN   VARCHAR2,
            p_created_by   IN   VARCHAR2
         )
     IS


     v_apps_id NUMBER := p_apps_id;
     v_alliance_id  NUMBER := p_alliance_id;

    BEGIN

        IF (p_apps_id = 0) THEN
            v_apps_id := NULL;
        END IF;

        IF (p_alliance_id = 0) THEN
            v_alliance_id := NULL;
        END IF;

        INSERT INTO AIMS_JOURNAL_ENTRIES (
                                          journal_id,
                                          journal_text,
                                          journal_type,
                                          apps_id,
                                          alliance_id,
                                          created_by,
                                          created_date
                                         )
                                  VALUES
                                        (
                                          seq_pk_journal_entries.NEXTVAL,
                                          p_journal_text,
                                          NVL(p_journal_type, 'PR'),
                                          v_apps_id,
                                          v_alliance_id,
                                          NVL(p_created_by, 'system'),
                                          SYSDATE
                                        );
    END add_journal_entry;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_app_with_relations
         (  p_apps_id                IN  NUMBER,            -- app_id of the application and its relations to be deleted
            p_curr_user_name         IN  VARCHAR2           -- user name of the person deleting the application
         )
 IS

    BEGIN

        DELETE FROM AIMS_LOAN_DEVICE_APPS WHERE loan_device_id IN
        (SELECT loan_device_id FROM AIMS_ALLIANCE_LOAN_DEVICES WHERE apps_id = p_apps_id);

        DELETE FROM AIMS_LOAN_DEVICE_NEW_APPS WHERE loan_device_id IN
        (SELECT loan_device_id FROM AIMS_ALLIANCE_LOAN_DEVICES WHERE apps_id = p_apps_id);

        -- Records related to BREW APPS

        DELETE FROM AIMS_BREW_APPS_DEVICES WHERE brew_apps_id = p_apps_id;
        DELETE FROM AIMS_BREW_APPS_J_GEO_SERVICES WHERE brew_apps_id = p_apps_id;
        DELETE FROM AIMS_BREW_NSTL_DATA WHERE brew_apps_id = p_apps_id;
        DELETE FROM AIMS_CATALOGS_DATA WHERE brew_apps_id = p_apps_id;
        DELETE FROM AIMS_BREW_APPS_HISTORY WHERE brew_apps_id = p_apps_id;
        DELETE FROM  AIMS_BREW_MESSAGING_DETAILS WHERE brew_app_id=p_apps_id;
        DELETE FROM AIMS_WORKITEM WHERE MODULE_WORKFLOW_ID IN (SELECT MODULE_WORKFLOW_ID FROM AIMS_MODULE_WORKFLOWS WHERE MODULE_RECORD_ID=p_apps_id); 
        DELETE FROM AIMS_MODULE_WORKFLOWS WHERE MODULE_RECORD_ID=p_apps_id;

        -- Records related to DASHBOARD_APPS
        DELETE FROM AIMS_DASHBOARD_CHANNEL_IDS WHERE dashboard_apps_id = p_apps_id;
        DELETE FROM AIMS_DASHBOARD_APPS_DEVICES WHERE dashboard_apps_id = p_apps_id;
        
        -- Records related to ENTERPRISE APPS

        DELETE FROM AIMS_APPS_CASE_STUDIES WHERE enterprise_apps_id = p_apps_id;
        DELETE FROM AIMS_ENT_APPS_IND_FOCUS WHERE enterprise_apps_id = p_apps_id;
        DELETE FROM AIMS_ENT_APPS_PLATFORMS WHERE enterprise_apps_id = p_apps_id;
        DELETE FROM AIMS_ENT_APPS_REGION WHERE enterprise_apps_id = p_apps_id;
        DELETE FROM AIMS_ENT_APPS_SOL_COMP WHERE enterprise_apps_id = p_apps_id;
        DELETE FROM AIMS_ENT_APPS_SOL_TYPES WHERE enterprise_apps_id = p_apps_id;
        DELETE FROM AIMS_ENT_APPS_SPOTLIGHTS WHERE enterprise_app_id = p_apps_id;
        DELETE FROM AIMS_ENT_APPS_SUB_CATEGORIES WHERE enterprise_apps_id = p_apps_id;
        DELETE FROM AIMS_ENT_APPS_VMS WHERE enterprise_apps_id = p_apps_id;
        DELETE FROM BDS_QUESTION_SCORING WHERE solution_id = p_apps_id;
        DELETE FROM BDS_SOLUTION_COMING_SOON WHERE solution_id = p_apps_id;
        DELETE FROM BDS_SOLUTION_COMPONENTS WHERE solution_id = p_apps_id;
        DELETE FROM BDS_SOLUTION_DEVICE_OPTIONS WHERE solution_id = p_apps_id;
        DELETE FROM BDS_SOLUTION_MARKET_SEGMENTS WHERE solution_id = p_apps_id;
        DELETE FROM BDS_SOLUTION_PARTNERS WHERE solution_id = p_apps_id;
        
        
        -- Records related to VCAST APPS

        DELETE FROM AIMS_VCAST_APPS_J_AGES WHERE vcast_apps_id = p_apps_id;
        DELETE FROM AIMS_VCAST_APPS_J_EDUCATIONS WHERE vcast_apps_id = p_apps_id;
        DELETE FROM AIMS_VCAST_APPS_J_GENDERS WHERE vcast_apps_id = p_apps_id;
        DELETE FROM AIMS_VCAST_APPS_J_INCOMES WHERE vcast_apps_id = p_apps_id;
        DELETE FROM AIMS_VCAST_APPS_J_RACES WHERE vcast_apps_id = p_apps_id;

        -- Records related to WAP APPS

        DELETE FROM AIMS_WAP_APPS_BROWSERS WHERE wap_apps_id = p_apps_id;
        DELETE FROM AIMS_WAP_APPS_FEATURES WHERE wap_apps_id = p_apps_id;
        DELETE FROM AIMS_WAP_APPS_GRAPHICS WHERE wap_apps_id = p_apps_id;
        DELETE FROM AIMS_WAP_APPS_J_LICENSE_TYPE WHERE wap_apps_id = p_apps_id;
        DELETE FROM AIMS_WAP_APPS_J_USER_IN_PARAMS WHERE wap_apps_id = p_apps_id;
        DELETE FROM AIMS_WAP_APPS_PROG_LANGS WHERE wap_apps_id = p_apps_id;
        DELETE FROM AIMS_WAP_APPS_VERSIONS WHERE wap_apps_id = p_apps_id;

        -- Records related AIMS_VZAPPZONE_APPS
        
        DELETE FROM AIMS_VZAPP_BINARY_FIRMWARE WHERE vzappzone_apps_id = p_apps_id;
        DELETE FROM AIMS_VZAPP_BINARIES WHERE vzappzone_apps_id = p_apps_id;

                

        -- Immediate Children of table AIMS_APPS

        DELETE FROM AIMS_ALLIANCE_LOAN_DEVICES WHERE apps_id = p_apps_id;
        DELETE FROM AIMS_ALLIANCE_SL_APPS WHERE apps_id = p_apps_id;
        DELETE FROM AIMS_APP_CERTIFICATIONS WHERE apps_id = p_apps_id;
        DELETE FROM AIMS_APP_NETWORK_URLS WHERE apps_id = p_apps_id;

        DELETE FROM AIMS_APP_PHASES WHERE apps_id = p_apps_id;
        DELETE FROM AIMS_BREW_APPS WHERE brew_apps_id = p_apps_id;
        DELETE FROM AIMS_CASE_STUDIES WHERE apps_id = p_apps_id;
        DELETE FROM AIMS_DASHBOARD_APPS WHERE dashboard_apps_id = p_apps_id;
        DELETE FROM AIMS_ENTERPRISE_APPS WHERE enterprise_apps_id = p_apps_id;
        DELETE FROM AIMS_JAVA_APPS WHERE java_apps_id = p_apps_id;
        DELETE FROM AIMS_JOURNAL_ENTRIES WHERE apps_id = p_apps_id;
        DELETE FROM AIMS_LOAN_DEVICE_APPS WHERE apps_id = p_apps_id;
        UPDATE AIMS_MESSAGES SET parent_message_id = NULL WHERE apps_id = p_apps_id;
        DELETE FROM AIMS_MESSAGES WHERE apps_id = p_apps_id;
        DELETE FROM AIMS_MMS_APPS WHERE mms_apps_id = p_apps_id;
        DELETE FROM AIMS_PROV_APPS_ROLES WHERE apps_id = p_apps_id;
        DELETE FROM AIMS_SMS_APPS WHERE sms_apps_id = p_apps_id;
        DELETE FROM AIMS_TEMP_CATALOG_DATA_MATCH WHERE apps_id = p_apps_id;
        DELETE FROM AIMS_VCAST_APPS WHERE vcast_apps_id = p_apps_id;
        DELETE FROM AIMS_WAP_APPS WHERE wap_apps_id = p_apps_id;
        DELETE FROM AIMS_VZAPPZONE_APPS WHERE VZAPPZONE_apps_id = p_apps_id;
        DELETE FROM AIMS_APPS WHERE apps_id = p_apps_id;


       -- Log the user who deleted the app
        INSERT INTO AIMS_APP_DELETE_LOG (apps_id, aud_user, aud_timestamp)
        VALUES(p_apps_id, p_curr_user_name, SYSDATE);

    END delete_app_with_relations;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_content_with_relations
         (  p_content_id             IN  NUMBER,            -- content_id of the creative content and its relations to be deleted
            p_curr_user_name         IN  VARCHAR2           -- user name of the person deleting the application
         )
 IS

    BEGIN
    
        DELETE FROM AIMS_CONTENT_REQ_CNTS WHERE creative_content_id = p_content_id;     
        DELETE FROM AIMS_CREATIVE_CONTENTS WHERE creative_content_id = p_content_id;

    END delete_content_with_relations;

/* -------------------------------------------------------------------------------------------------------------  */

END Aims_Utils; -- Package Body AIMS_UTILS
/

