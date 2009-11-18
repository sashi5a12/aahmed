set escape !

DROP SEQUENCE SEQ_APP_REF_ID
/
DROP SEQUENCE SEQ_CONTRACT_REF_ID
/
ALTER TABLE AIMS_CONTRACTS
DROP (RING_NUMBER,CONTRACT_REF_ID)
/
ALTER TABLE AIMS_CONTRACTS$AUD
DROP (RING_NUMBER,CONTRACT_REF_ID)
/
CREATE OR REPLACE TRIGGER aims_contracts$audtrg
   AFTER INSERT OR DELETE OR UPDATE
   ON aims_contracts
   REFERENCING NEW AS NEW OLD AS OLD
   FOR EACH ROW
DECLARE
   v_operation   VARCHAR2 (10) := NULL;
BEGIN
   IF INSERTING
   THEN
      v_operation := 'INS';
   ELSIF UPDATING
   THEN
      v_operation := 'UPD';
   ELSE
      v_operation := 'DEL';
   END IF;

   IF    INSERTING
      OR UPDATING
   THEN
      INSERT INTO aims_contracts$aud
                  (contract_id, contract_title, version,
                   platform_id, if_negotiable, status,
                   if_attach_to_soln, comments, created_by,
                   created_date, last_updated_by,
                   last_updated_date, document_file_name,
                   document_content_type, expiry_date,IS_BOBO_CONTRACT, 
                   aud_action,aud_timestamp, aud_user)
           VALUES (:NEW.contract_id, :NEW.contract_title, :NEW.version,
                   :NEW.platform_id, :NEW.if_negotiable, :NEW.status,
                   :NEW.if_attach_to_soln, :NEW.comments, :NEW.created_by,
                   :NEW.created_date, :NEW.last_updated_by,
                   :NEW.last_updated_date, :NEW.document_file_name,
                   :NEW.document_content_type, :NEW.expiry_date,:NEW.IS_BOBO_CONTRACT, 
                   v_operation,SYSDATE, USER);
   ELSE
      INSERT INTO aims_contracts$aud
                  (contract_id, contract_title, version,
                   platform_id, if_negotiable, status,
                   if_attach_to_soln, comments, created_by,
                   created_date, last_updated_by,
                   last_updated_date, document_file_name,
                   document_content_type, expiry_date,IS_BOBO_CONTRACT, 
                   aud_action,aud_timestamp, aud_user)
           VALUES (:OLD.contract_id, :OLD.contract_title, :OLD.version,
                   :OLD.platform_id, :OLD.if_negotiable, :OLD.status,
                   :OLD.if_attach_to_soln, :OLD.comments, :OLD.created_by,
                   :OLD.created_date, :OLD.last_updated_by,
                   :OLD.last_updated_date, :OLD.document_file_name,
                   :OLD.document_content_type, :OLD.expiry_date,:OLD.IS_BOBO_CONTRACT, 
                   v_operation,SYSDATE, USER);
   END IF;
END;
/
ALTER TABLE  AIMS_APPS
DROP (APP_REF_ID)
/
ALTER TABLE AIMS_APPS$AUD
DROP (APP_REF_ID)
/
CREATE OR REPLACE TRIGGER AIMS_APPS$audtrg
   AFTER INSERT OR DELETE OR UPDATE
   ON AIMS_APPS
   REFERENCING NEW AS NEW OLD AS OLD
   FOR EACH ROW
DECLARE
   v_operation   VARCHAR2 (10) := NULL;
BEGIN
   IF INSERTING
   THEN
      v_operation := 'INS';
   ELSIF UPDATING
   THEN
      v_operation := 'UPD';
   ELSE
      v_operation := 'DEL';
   END IF;

   IF    INSERTING
      OR UPDATING
   THEN
      INSERT INTO AIMS_APPS$AUD
                  (APPS_ID,
                   TITLE,
                   VERSION,
                   SHORT_DESC,
                   LONG_DESC,
                   ALLIANCE_ID,
                   TECH_CONTACT_ID,
                   CONTRACT_ID,
                   APP_DEPLOYMENTS,
                   IF_PR_RELEASE,
                   APP_PRIORITY,
                   IF_SEND_EMAIL,
                   GIN_NUMBER,
                   CREATED_BY,
                   CREATED_DATE,
                   LAST_UPDATED_BY,
                   LAST_UPDATED_DATE,
                   VZW_APPS_CONTACT_ID,
                   PLATFORM_ID,
                   IF_ON_HOLD,
                   PHASE_ID,
                   APP_CREATED_BY,
                   SUB_CATEGORY_ID,
                   SCREEN_JPEG_FILE_NAME,
                   SCREEN_JPEG_CONTENT_TYPE,
                   ACTIVE_SCREEN_EPS_FILE_NAME,
                   ACTIVE_SCREEN_EPS_CONTENT_TYPE,
                   FLASH_DEMO_FILE_NAME,
                   FLASH_DEMO_CONTENT_TYPE,
                   SPLASH_SCREEN_EPS_FILE_NAME,
                   SPLASH_SCREEN_EPS_CONTENT_TYPE,
                   USER_GUIDE_FILE_NAME,
                   USER_GUIDE_CONTENT_TYPE,
                   FAQ_DOC_FILE_NAME,
                   TEST_PLAN_RESULTS_FILE_NAME,
                   TEST_PLAN_RESULTS_CONTENT_TYPE,
                   FAQ_DOC_CONTENT_TYPE,
                   LANGUAGE,
                   PRODUCT_GROUP_ID,
                   TARGETED_PRODUCTION_DATE,
                   SUBMITTED_DATE,
                   SCREEN_JPEG_2_FILE_NAME,
                   SCREEN_JPEG_3_FILE_NAME,
                   SCREEN_JPEG_4_FILE_NAME,
                   SCREEN_JPEG_5_FILE_NAME,
                   SCREEN_JPEG_2_CONTENT_TYPE,
                   SCREEN_JPEG_3_CONTENT_TYPE,
                   SCREEN_JPEG_4_CONTENT_TYPE,
                   SCREEN_JPEG_5_CONTENT_TYPE,
                   NETWORK_USAGE,
                   AUD_ACTION,
                   AUD_TIMESTAMP,
                   AUD_USER)
           VALUES (:NEW.APPS_ID,
                   :NEW.TITLE,
                   :NEW.VERSION,
                   :NEW.SHORT_DESC,
                   :NEW.LONG_DESC,
                   :NEW.ALLIANCE_ID,
                   :NEW.TECH_CONTACT_ID,
                   :NEW.CONTRACT_ID,
                   :NEW.APP_DEPLOYMENTS,
                   :NEW.IF_PR_RELEASE,
                   :NEW.APP_PRIORITY,
                   :NEW.IF_SEND_EMAIL,
                   :NEW.GIN_NUMBER,
                   :NEW.CREATED_BY,
                   :NEW.CREATED_DATE,
                   :NEW.LAST_UPDATED_BY,
                   :NEW.LAST_UPDATED_DATE,
                   :NEW.VZW_APPS_CONTACT_ID,
                   :NEW.PLATFORM_ID,
                   :NEW.IF_ON_HOLD,
                   :NEW.PHASE_ID,
                   :NEW.APP_CREATED_BY,
                   :NEW.SUB_CATEGORY_ID,
                   :NEW.SCREEN_JPEG_FILE_NAME,
                   :NEW.SCREEN_JPEG_CONTENT_TYPE,
                   :NEW.ACTIVE_SCREEN_EPS_FILE_NAME,
                   :NEW.ACTIVE_SCREEN_EPS_CONTENT_TYPE,
                   :NEW.FLASH_DEMO_FILE_NAME,
                   :NEW.FLASH_DEMO_CONTENT_TYPE,
                   :NEW.SPLASH_SCREEN_EPS_FILE_NAME,
                   :NEW.SPLASH_SCREEN_EPS_CONTENT_TYPE,
                   :NEW.USER_GUIDE_FILE_NAME,
                   :NEW.USER_GUIDE_CONTENT_TYPE,
                   :NEW.FAQ_DOC_FILE_NAME,
                   :NEW.TEST_PLAN_RESULTS_FILE_NAME,
                   :NEW.TEST_PLAN_RESULTS_CONTENT_TYPE,
                   :NEW.FAQ_DOC_CONTENT_TYPE,
                   :NEW.LANGUAGE,
                   :NEW.PRODUCT_GROUP_ID,
                   :NEW.TARGETED_PRODUCTION_DATE,
                   :NEW.SUBMITTED_DATE,
                   :NEW.SCREEN_JPEG_2_FILE_NAME,
                   :NEW.SCREEN_JPEG_3_FILE_NAME,
                   :NEW.SCREEN_JPEG_4_FILE_NAME,
                   :NEW.SCREEN_JPEG_5_FILE_NAME,
                   :NEW.SCREEN_JPEG_2_CONTENT_TYPE,
                   :NEW.SCREEN_JPEG_3_CONTENT_TYPE,
                   :NEW.SCREEN_JPEG_4_CONTENT_TYPE,
                   :NEW.SCREEN_JPEG_5_CONTENT_TYPE,
                   :NEW.NETWORK_USAGE,
                   V_OPERATION,
                   SYSDATE,
                   USER);
   ELSE
      INSERT INTO AIMS_APPS$AUD
                  (APPS_ID,
                   TITLE,
                   VERSION,
                   SHORT_DESC,
                   LONG_DESC,
                   ALLIANCE_ID,
                   TECH_CONTACT_ID,
                   CONTRACT_ID,
                   APP_DEPLOYMENTS,
                   IF_PR_RELEASE,
                   APP_PRIORITY,
                   IF_SEND_EMAIL,
                   GIN_NUMBER,
                   CREATED_BY,
                   CREATED_DATE,
                   LAST_UPDATED_BY,
                   LAST_UPDATED_DATE,
                   VZW_APPS_CONTACT_ID,
                   PLATFORM_ID,
                   IF_ON_HOLD,
                   PHASE_ID,
                   APP_CREATED_BY,
                   SUB_CATEGORY_ID,
                   SCREEN_JPEG_FILE_NAME,
                   SCREEN_JPEG_CONTENT_TYPE,
                   ACTIVE_SCREEN_EPS_FILE_NAME,
                   ACTIVE_SCREEN_EPS_CONTENT_TYPE,
                   FLASH_DEMO_FILE_NAME,
                   FLASH_DEMO_CONTENT_TYPE,
                   SPLASH_SCREEN_EPS_FILE_NAME,
                   SPLASH_SCREEN_EPS_CONTENT_TYPE,
                   USER_GUIDE_FILE_NAME,
                   USER_GUIDE_CONTENT_TYPE,
                   FAQ_DOC_FILE_NAME,
                   TEST_PLAN_RESULTS_FILE_NAME,
                   TEST_PLAN_RESULTS_CONTENT_TYPE,
                   FAQ_DOC_CONTENT_TYPE,
                   LANGUAGE,
                   PRODUCT_GROUP_ID,
                   TARGETED_PRODUCTION_DATE,
                   SUBMITTED_DATE,
                   SCREEN_JPEG_2_FILE_NAME,
                   SCREEN_JPEG_3_FILE_NAME,
                   SCREEN_JPEG_4_FILE_NAME,
                   SCREEN_JPEG_5_FILE_NAME,
                   SCREEN_JPEG_2_CONTENT_TYPE,
                   SCREEN_JPEG_3_CONTENT_TYPE,
                   SCREEN_JPEG_4_CONTENT_TYPE,
                   SCREEN_JPEG_5_CONTENT_TYPE,
                   NETWORK_USAGE,
                   AUD_ACTION,
                   AUD_TIMESTAMP,
                   AUD_USER)
           VALUES (:OLD.APPS_ID,
                   :OLD.TITLE,
                   :OLD.VERSION,
                   :OLD.SHORT_DESC,
                   :OLD.LONG_DESC,
                   :OLD.ALLIANCE_ID,
                   :OLD.TECH_CONTACT_ID,
                   :OLD.CONTRACT_ID,
                   :OLD.APP_DEPLOYMENTS,
                   :OLD.IF_PR_RELEASE,
                   :OLD.APP_PRIORITY,
                   :OLD.IF_SEND_EMAIL,
                   :OLD.GIN_NUMBER,
                   :OLD.CREATED_BY,
                   :OLD.CREATED_DATE,
                   :OLD.LAST_UPDATED_BY,
                   :OLD.LAST_UPDATED_DATE,
                   :OLD.VZW_APPS_CONTACT_ID,
                   :OLD.PLATFORM_ID,
                   :OLD.IF_ON_HOLD,
                   :OLD.PHASE_ID,
                   :OLD.APP_CREATED_BY,
                   :OLD.SUB_CATEGORY_ID,
                   :OLD.SCREEN_JPEG_FILE_NAME,
                   :OLD.SCREEN_JPEG_CONTENT_TYPE,
                   :OLD.ACTIVE_SCREEN_EPS_FILE_NAME,
                   :OLD.ACTIVE_SCREEN_EPS_CONTENT_TYPE,
                   :OLD.FLASH_DEMO_FILE_NAME,
                   :OLD.FLASH_DEMO_CONTENT_TYPE,
                   :OLD.SPLASH_SCREEN_EPS_FILE_NAME,
                   :OLD.SPLASH_SCREEN_EPS_CONTENT_TYPE,
                   :OLD.USER_GUIDE_FILE_NAME,
                   :OLD.USER_GUIDE_CONTENT_TYPE,
                   :OLD.FAQ_DOC_FILE_NAME,
                   :OLD.TEST_PLAN_RESULTS_FILE_NAME,
                   :OLD.TEST_PLAN_RESULTS_CONTENT_TYPE,
                   :OLD.FAQ_DOC_CONTENT_TYPE,
                   :OLD.LANGUAGE,
                   :OLD.PRODUCT_GROUP_ID,
                   :OLD.TARGETED_PRODUCTION_DATE,
                   :OLD.SUBMITTED_DATE,
                   :OLD.SCREEN_JPEG_2_FILE_NAME,
                   :OLD.SCREEN_JPEG_3_FILE_NAME,
                   :OLD.SCREEN_JPEG_4_FILE_NAME,
                   :OLD.SCREEN_JPEG_5_FILE_NAME,
                   :OLD.SCREEN_JPEG_2_CONTENT_TYPE,
                   :OLD.SCREEN_JPEG_3_CONTENT_TYPE,
                   :OLD.SCREEN_JPEG_4_CONTENT_TYPE,
                   :OLD.SCREEN_JPEG_5_CONTENT_TYPE,
                   :OLD.NETWORK_USAGE,
                   V_OPERATION,
                   SYSDATE,
                   USER);
   END IF;
END;
/
ALTER TABLE AIMS_WORKITEM
DROP (ACTION_PAGE_URL)
/
ALTER TABLE AIMS_WORKITEM$AUD
DROP (ACTION_PAGE_URL)
/
CREATE OR REPLACE TRIGGER AIMS_WORKITEM$audtrg
AFTER INSERT  OR  DELETE  OR UPDATE 
ON AIMS_WORKITEM
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
   v_operation   VARCHAR2 (10) := NULL;
BEGIN
   IF INSERTING
   THEN
      v_operation := 'INS';
   ELSIF UPDATING
   THEN
      v_operation := 'UPD';
   ELSE
      v_operation := 'DEL';
   END IF;

   IF    INSERTING
      OR UPDATING
   THEN
      INSERT INTO AIMS_WORKITEM$aud
                  (
                    workitem_id,module_workflow_id,details,
                    workflow_state_id,step_name,entry_date,
                    exit_date,action_taken,status,description,
                    comments,privilege,created_by,created_date,
                    modified_by,modified_date,
                    aud_action,aud_timestamp,aud_user
                  )
           VALUES (
                    :NEW.workitem_id,:NEW.module_workflow_id,:NEW.details,
                    :NEW.workflow_state_id,:NEW.step_name,:NEW.entry_date,
                    :NEW.exit_date,:NEW.action_taken,:NEW.status,:NEW.description,
                    :NEW.comments,:NEW.privilege,:NEW.created_by,:NEW.created_date,
                    :NEW.modified_by,:NEW.modified_date,
                    v_operation, SYSDATE, USER
                  );
   ELSE
      INSERT INTO AIMS_WORKITEM$aud
                  (
                    workitem_id,module_workflow_id,details,
                    workflow_state_id,step_name,entry_date,
                    exit_date,action_taken,status,description,
                    comments,privilege,created_by,created_date,
                    modified_by,modified_date,
                    aud_action,aud_timestamp,aud_user
                  )
           VALUES (
                    :OLD.workitem_id,:OLD.module_workflow_id,:OLD.details,
                    :OLD.workflow_state_id,:OLD.step_name,:OLD.entry_date,
                    :OLD.exit_date,:OLD.action_taken,:OLD.status,:OLD.description,
                    :OLD.comments,:OLD.privilege,:OLD.created_by,:OLD.created_date,
                    :OLD.modified_by,:OLD.modified_date,
                    v_operation, SYSDATE, USER
                  );
   END IF;
END;
/
DROP TABLE AIMS_JAVA_APPS
/
DROP TABLE AIMS_JAVA_APPS$AUD
/



DELETE FROM AIMS_TYPES 
 WHERE TYPE_DEF_ID = 20 
   AND TYPE_ID IN (172,173)
/

DELETE FROM AIMS_TYPES 
 WHERE TYPE_DEF_ID = 9 
   AND TYPE_ID IN (201,202,203,204,205,206,207,208,209,210,211,212)
/    

DELETE FROM AIMS_TYPES 
 WHERE TYPE_DEF_ID = 10 
   AND TYPE_ID IN (301,302)
/

DELETE FROM AIMS_TYPE_DEFS 
WHERE TYPE_DEF_ID IN (9,10,20)
/

DELETE FROM AIMS_APP_SUB_CATEGORIES
 WHERE CATEGORY_ID = 161
   AND SUB_CATEGORY_ID IN (271,272,273,274,275,276,277,278,279,280,281,282,283)
/

DELETE FROM AIMS_APP_CATEGORIES 
 WHERE PLATFORM_ID = 44
   AND CATEGORY_ID IN (161,162,163,164,165,166,167,168,169,170,171)
/

DELETE FROM  AIMS_PLATFORMS
WHERE PLATFORM_ID = 44
/

DELETE FROM AIMS_WORKFLOW_DETAILS
 WHERE WORKFLOW_DETAIL_ID IN (8,10)
/

DELETE FROM AIMS_LIFECYCLE_PHASES
 WHERE PHASE_ID IN (2001,2002,2003,2004,2005,2006,2007,2008,2009)
/
DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2002
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2002
      AND event_id = 2002
/
DELETE FROM aims_events
  WHERE event_id = 2002
/
DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2003
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2003
      AND event_id = 2003
/
DELETE FROM aims_events
  WHERE event_id = 2003
/
DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2004
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2004
      AND event_id = 2004
/
DELETE FROM aims_events
  WHERE event_id = 2004
/
DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2005
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2005
      AND event_id = 2005
/
DELETE FROM aims_events
  WHERE event_id = 2005
/
DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2006
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2006
      AND event_id = 2006
/
DELETE FROM aims_events
  WHERE event_id = 2006
/

DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2007
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2007
      AND event_id = 2007
/
DELETE FROM aims_events
  WHERE event_id = 2007
/

DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2008
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2008
      AND event_id = 2008
/
DELETE FROM aims_events
  WHERE event_id = 2008
/

DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2009
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2009
      AND event_id = 2009
/
DELETE FROM aims_events
  WHERE event_id = 2009
/

DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2010
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2010
      AND event_id = 2010
/
DELETE FROM aims_events
  WHERE event_id = 2010
/

DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2011
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2011
      AND event_id = 2011
/
DELETE FROM aims_events
  WHERE event_id = 2011
/ 

DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2012
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2012
      AND event_id = 2012
/
DELETE FROM aims_events
  WHERE event_id = 2012
/

DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2013
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2013
      AND event_id = 2013
/
DELETE FROM aims_events
  WHERE event_id = 2013
/

DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6)
      AND event_id = 2014
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2014
      AND event_id = 2014
/
DELETE FROM aims_events
  WHERE event_id = 2014
/ 
DELETE FROM aims_event_place_holders
    WHERE place_holder_id IN (1,2,6,2001)
      AND event_id = 2100
/
DELETE FROM aims_event_handlers
    WHERE event_handler_id = 2100
      AND event_id = 2100
/
DELETE FROM aims_events
  WHERE event_id = 2100
/ 
DELETE FROM AIMS_PLACE_HOLDERS
    WHERE PLACE_HOLDER_ID = 2001
/
ALTER TABLE aims_contacts
  DROP (alliance_id)
/
ALTER TABLE aims_contacts$AUD
  DROP (alliance_id)
/
CREATE OR REPLACE TRIGGER aims_contacts$audtrg
AFTER INSERT  OR DELETE  OR UPDATE
ON aims_contacts
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
v_operation VARCHAR2(10) := NULL;
BEGIN
IF INSERTING THEN
       v_operation := 'INS';
    ELSIF UPDATING THEN

v_operation := 'UPD';
    ELSE
       v_operation := 'DEL';
    END IF;

IF INSERTING OR UPDATING THEN

INSERT INTO AIMS_CONTACTS$AUD (
CONTACT_ID,
TYPE,
FIRST_NAME,
LAST_NAME,
TITLE,
EMAIL_ADDRESS,
PHONE,
FAX,
MOBILE,
CREATED_BY,
CREATED_DATE,
LAST_UPDATED_BY,
LAST_UPDATED_DATE,
aud_action,aud_timestamp,aud_user) VALUES (
:new.CONTACT_ID,
:new.TYPE,
:new.FIRST_NAME,
:new.LAST_NAME,
:new.TITLE,
:new.EMAIL_ADDRESS,
:new.PHONE,
:new.FAX,
:new.MOBILE,
:new.CREATED_BY,
:new.CREATED_DATE,
:new.LAST_UPDATED_BY,
:new.LAST_UPDATED_DATE,
v_operation,SYSDATE,USER);

ELSE

INSERT INTO AIMS_CONTACTS$AUD (
CONTACT_ID,
TYPE,
FIRST_NAME,
LAST_NAME,
TITLE,
EMAIL_ADDRESS,
PHONE,
FAX,
MOBILE,
CREATED_BY,
CREATED_DATE,
LAST_UPDATED_BY,
LAST_UPDATED_DATE,
aud_action,aud_timestamp,aud_user) VALUES (
:old.CONTACT_ID,
:old.TYPE,
:old.FIRST_NAME,
:old.LAST_NAME,
:old.TITLE,
:old.EMAIL_ADDRESS,
:old.PHONE,
:old.FAX,
:old.MOBILE,
:old.CREATED_BY,
:old.CREATED_DATE,
:old.LAST_UPDATED_BY,
:old.LAST_UPDATED_DATE,
v_operation,SYSDATE,USER);

END IF;
END;
/

ALTER TABLE aims_sys_roles
  DROP (vzdn_mapping_role_id)
/
ALTER TABLE aims_sys_roles$aud
  DROP (vzdn_mapping_role_id)
/
CREATE OR REPLACE TRIGGER aims_sys_roles$audtrg
AFTER INSERT  OR DELETE  OR UPDATE
ON aims_sys_roles
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
v_operation VARCHAR2(10) := NULL;
BEGIN
IF INSERTING THEN
       v_operation := 'INS';
    ELSIF UPDATING THEN

v_operation := 'UPD';
    ELSE
       v_operation := 'DEL';
    END IF;

IF INSERTING OR UPDATING THEN

INSERT INTO AIMS_SYS_ROLES$AUD (
ROLE_ID,
ROLE_NAME,
ROLE_DESCRIPTION,
ROLE_TYPE,
CREATED_BY,
CREATED_DATE,
LAST_UPDATED_BY,
LAST_UPDATED_DATE,
aud_action,aud_timestamp,aud_user) VALUES (
:new.ROLE_ID,
:new.ROLE_NAME,
:new.ROLE_DESCRIPTION,
:new.ROLE_TYPE,
:new.CREATED_BY,
:new.CREATED_DATE,
:new.LAST_UPDATED_BY,
:new.LAST_UPDATED_DATE,
v_operation,SYSDATE,USER);

ELSE

INSERT INTO AIMS_SYS_ROLES$AUD (
ROLE_ID,
ROLE_NAME,
ROLE_DESCRIPTION,
ROLE_TYPE,
CREATED_BY,
CREATED_DATE,
LAST_UPDATED_BY,
LAST_UPDATED_DATE,
aud_action,aud_timestamp,aud_user) VALUES (
:old.ROLE_ID,
:old.ROLE_NAME,
:old.ROLE_DESCRIPTION,
:old.ROLE_TYPE,
:old.CREATED_BY,
:old.CREATED_DATE,
:old.LAST_UPDATED_BY,
:old.LAST_UPDATED_DATE,
v_operation,SYSDATE,USER);

END IF;
END;
/

DROP TABLE aims_user_offer
/

DROP SEQUENCE seq_aims_user_offer
/

DROP TABLE aims_user_offer$aud 
/

ALTER TABLE aims_contracts DROP
  (html_document             ,
   html_document_file_name   ,
   html_document_content_type ,
   is_click_through_contract)
/

ALTER TABLE aims_contracts$AUD DROP
  (html_document_file_name   ,
   html_document_content_type ,
   is_click_through_contract )
/

CREATE OR REPLACE TRIGGER aims_contracts$audtrg
   AFTER INSERT OR DELETE OR UPDATE
   ON aims_contracts
   REFERENCING NEW AS NEW OLD AS OLD
   FOR EACH ROW
DECLARE
   v_operation   VARCHAR2 (10) := NULL;
BEGIN
   IF INSERTING
   THEN
      v_operation := 'INS';
   ELSIF UPDATING
   THEN
      v_operation := 'UPD';
   ELSE
      v_operation := 'DEL';
   END IF;

   IF    INSERTING
      OR UPDATING
   THEN
      INSERT INTO aims_contracts$aud
                  (contract_id, contract_title, version,
                   platform_id, if_negotiable, status,
                   if_attach_to_soln, comments, created_by,
                   created_date, last_updated_by,
                   last_updated_date, document_file_name,
                   document_content_type, expiry_date,IS_BOBO_CONTRACT, 
                   aud_action,aud_timestamp, aud_user)
           VALUES (:NEW.contract_id, :NEW.contract_title, :NEW.version,
                   :NEW.platform_id, :NEW.if_negotiable, :NEW.status,
                   :NEW.if_attach_to_soln, :NEW.comments, :NEW.created_by,
                   :NEW.created_date, :NEW.last_updated_by,
                   :NEW.last_updated_date, :NEW.document_file_name,
                   :NEW.document_content_type, :NEW.expiry_date,:NEW.IS_BOBO_CONTRACT, 
                   v_operation,SYSDATE, USER);
   ELSE
      INSERT INTO aims_contracts$aud
                  (contract_id, contract_title, version,
                   platform_id, if_negotiable, status,
                   if_attach_to_soln, comments, created_by,
                   created_date, last_updated_by,
                   last_updated_date, document_file_name,
                   document_content_type, expiry_date,IS_BOBO_CONTRACT, 
                   aud_action,aud_timestamp, aud_user)
           VALUES (:OLD.contract_id, :OLD.contract_title, :OLD.version,
                   :OLD.platform_id, :OLD.if_negotiable, :OLD.status,
                   :OLD.if_attach_to_soln, :OLD.comments, :OLD.created_by,
                   :OLD.created_date, :OLD.last_updated_by,
                   :OLD.last_updated_date, :OLD.document_file_name,
                   :OLD.document_content_type, :OLD.expiry_date,:OLD.IS_BOBO_CONTRACT, 
                   v_operation,SYSDATE, USER);
   END IF;
END;
/

DELETE FROM aims_role_privileges
 WHERE privilege_id IN (701,702,801,803,804,805,2001,2002)
/
DELETE FROM aims_sys_privileges
 WHERE privilege_id IN (701,702,801,803,804,805,2001,2002)
/

DELETE FROM aims_menus
 WHERE menu_id IN (101,102)
/

UPDATE   aims_login_content lc
   SET   lc.ignored_path =
            '/loginAllianceContactUpdateAction.do,/accountsSetup.do,/accountsEdit.do'
 WHERE   lc.login_content_id = 1
/

INSERT INTO AIMS_SUB_MENUS (SUB_MENU_ID,
                            SUB_MENU_NAME,
                            SUB_MENU_URL,
                            IMAGE_NAME,
                            SORT_ORDER,
                            CREATED_BY,
                            CREATED_DATE,
                            LAST_UPDATED_BY,
                            LAST_UPDATED_DATE,
                            MENU_ID)
  VALUES   ('38',
            'Users',
            '/aims/accounts.do!?task=view',
            'users',
            '10020',
            'system',
            SYSDATE,
            'system',
            SYSDATE,
            9)
/

INSERT INTO AIMS_SYS_PRIVILEGES (PRIVILEGE_ID,
                                 PRIVILEGE_NAME,
                                 PRIVILEGE_DESCRIPTION,
                                 CREATED_BY,
                                 CREATED_DATE,
                                 LAST_UPDATED_BY,
                                 LAST_UPDATED_DATE,
                                 SUB_MENU_ID,
                                 PRIVILEGE_KEY)
  VALUES   ('168',
            'Users',
            'Manage Users',
            'system',
            SYSDATE,
            'system',
            SYSDATE,
            '38',
            'USERS')
/


INSERT INTO AIMS_ROLE_PRIVILEGES (ROLE_ID,
                                  PRIVILEGE_ID,
                                  IF_SELECT_ALLOWED,
                                  IF_CREATE_ALLOWED,
                                  IF_UPDATE_ALLOWED)
  VALUES   ('98',
            '167',
            'Y',
            'Y',
            'Y')
/

INSERT INTO AIMS_ROLE_PRIVILEGES (ROLE_ID,
                                  PRIVILEGE_ID,
                                  IF_SELECT_ALLOWED,
                                  IF_CREATE_ALLOWED,
                                  IF_UPDATE_ALLOWED)
  VALUES   ('98',
            '168',
            'Y',
            'Y',
            'Y')
/

CREATE OR REPLACE PACKAGE BODY aims_lob_utils
IS

/* -------------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_into_uploaded_documents
         (
           p_document_name          IN  VARCHAR2,  -- name of the document
           p_document_content_type  IN  VARCHAR2,  -- content type of the document
           p_pk_expr_to_table       IN  VARCHAR2,  -- expression in the form pkid = value
           p_to_table_name          IN  VARCHAR2,  -- name of the table in which the record will be updated
           p_to_table_col_name      IN  VARCHAR2,  -- name of the column of the table in which the record will be updated
           p_user_id                IN  VARCHAR2   -- user id of the current user
         )
    IS

    /*
    || Overview:        Inserts a record in the aims_uploaded_documents for the uploaded documents
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 08-13-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        v_col_name          VARCHAR2(100);
        v_col_value         VARCHAR2(100);
        v_equal_pos         NUMBER;

    BEGIN

        v_equal_pos := INSTR(p_pk_expr_to_table, '=');
        v_col_name  := trim(SUBSTR(p_pk_expr_to_table, 1, v_equal_pos - 1));
        v_col_value := trim(SUBSTR(p_pk_expr_to_table, v_equal_pos + 1));

        INSERT INTO aims_uploaded_documents
            (   document_id,
                document_name,
                document_type,
                document_content_type,
                upload_table_name,
                upload_col_name,
                upload_pk_name,
                upload_pk_value,
                created_by,
                created_date
            )
        VALUES
            (   seq_pk_uploaded_documents.NEXTVAL,
                p_document_name,
                p_to_table_col_name,
                p_document_content_type,
                p_to_table_name,
                p_to_table_col_name,
                v_col_name,
                v_col_value,
                p_user_id,
                SYSDATE
             );
    EXCEPTION
        WHEN OTHERS THEN
            NULL;
    END insert_into_uploaded_documents;

/* -------------------------------------------------------------------------------------------------------------------  */

    PROCEDURE copy_lob_from_temp_table
         ( p_temp_table_id          IN  NUMBER,    -- primary key of the temp table
           p_pk_expr_to_table       IN  VARCHAR2,  -- expression in the form pkid = value
           p_to_table_name          IN  VARCHAR2,  -- name of the table in which the record will be updated
           p_to_table_col_name      IN  VARCHAR2,  -- name of the column of the table in which the record will be updated
           p_user_id                IN  VARCHAR2   -- user id of the current user
         )
    IS

    /*
    || Overview:        Copies a lob column value from temp table to a given table.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 12-17-2003       rqazi           Created
    || 09-21-2007       MSQ             Modified. To clone new images for WAP FTP module.
    ||
    ||
    ||
    ||
    */
        sql_update          VARCHAR2(2000);
        v_file              BLOB;
        v_file_name         VARCHAR2(150);
        v_file_content_type VARCHAR2(100);

   BEGIN
        SELECT
            temp_file,
            temp_file_name,
            temp_file_content_type
        INTO
            v_file,
            v_file_name,
            v_file_content_type
        FROM
            aims_temp_files
        WHERE
            temp_file_id = p_temp_table_id
            AND created_by = p_user_id;


        sql_update := sql_update || 'update ';
        sql_update := sql_update ||     p_to_table_name || ' ';
        sql_update := sql_update || 'set ';
        sql_update := sql_update ||     p_to_table_col_name;
        sql_update := sql_update || '   = :1, ';
        sql_update := sql_update ||     p_to_table_col_name || '_file_name ';
        sql_update := sql_update || '   = :2, ';
        sql_update := sql_update ||     p_to_table_col_name || '_content_type ';
        sql_update := sql_update || '   = :3 ';
        sql_update := sql_update || 'where ';
        sql_update := sql_update ||     p_pk_expr_to_table;

        --p('Value of sql_update='||sql_update);
        EXECUTE IMMEDIATE sql_update USING v_file, v_file_name, v_file_content_type;

        DELETE
        FROM
            aims_temp_files
        WHERE
            temp_file_id = p_temp_table_id
            AND created_by = p_user_id;

        insert_into_uploaded_documents
         (
           v_file_name,
           v_file_content_type,
           p_pk_expr_to_table,
           p_to_table_name,
           p_to_table_col_name,
           p_user_id
         );

   END copy_lob_from_temp_table;


   
/* -------------------------------------------------------------------------------------------------------------------  */

    PROCEDURE copy_lob_from_to_table
         ( p_from_table_col_name     IN  varchar2,  -- name of the column of the table which will be selected for update. 
           p_from_table_name         IN  varchar2,  -- name of table which record will be selected for update.
           p_pk_expr_from_table      IN  varchar2,  -- expression in the form pkid = value
           p_to_table_col_name       IN  varchar2,  -- name of the column of the table which record will be updated
           p_to_table_name           IN  varchar2,  -- name of the table which column will be updated
           p_pk_expr_to_table        IN  varchar2   -- expression in the to pkid = value
         )
    IS

    /*
    || Overview:        Copies a lob column value from table to a given table.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 26-03-2003       MSQ           Created
    || 
    ||
    ||
    ||
    ||
    */
        sql_update          varchar2(2000);

   BEGIN

        sql_update := sql_update || 'update ';
        sql_update := sql_update ||     p_to_table_name;
        sql_update := sql_update || ' set ';
        sql_update := sql_update ||    '('||p_to_table_col_name||',';
        sql_update := sql_update ||         p_to_table_col_name||'_file_name,';
        sql_update := sql_update ||         p_to_table_col_name||'_content_type)';
        sql_update := sql_update || '   =  (select ';
        sql_update := sql_update ||         p_from_table_col_name||' ,';
        sql_update := sql_update ||         p_from_table_col_name||'_file_name,';
        sql_update := sql_update ||         p_from_table_col_name||'_content_type';
        sql_update := sql_update || ' from ';
        sql_update := sql_update ||    p_from_table_name;
        sql_update := sql_update || ' where ';
        sql_update := sql_update ||     p_pk_expr_from_table|| ' ) ';
        sql_update := sql_update || ' where ';
        sql_update := sql_update ||     p_pk_expr_to_table;

       execute immediate sql_update;

   END copy_lob_from_to_table;


/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE clone_images
         ( p_clone_from_app_id      IN  NUMBER,    -- application id to clone from
           p_clone_to_app_id        IN  VARCHAR2,  -- application id to clone to
           p_platform_id            IN  VARCHAR2   -- platform id
         )
   IS
        v_faq_doc aims_apps.faq_doc%TYPE;
        v_faq_doc_file_name aims_apps.faq_doc_file_name%TYPE;
        v_faq_doc_content_type aims_apps.faq_doc_content_type%TYPE;

        v_user_guide aims_apps.user_guide%TYPE;
        v_user_guide_file_name aims_apps.user_guide_file_name%TYPE;
        v_user_guide_content_type aims_apps.user_guide_content_type%TYPE;

        v_test_plan_results aims_apps.test_plan_results%TYPE;
        v_test_plan_results_file_name aims_apps.test_plan_results_file_name%TYPE;
        v_test_plan_results_cont_type aims_apps.test_plan_results_content_type%TYPE;

        v_splash_screen_eps aims_apps.splash_screen_eps%TYPE;
        v_splash_screen_eps_file_name aims_apps.splash_screen_eps_file_name%TYPE;
        v_splash_screen_eps_cont_type aims_apps.splash_screen_eps_content_type%TYPE;

        v_screen_jpeg aims_apps.screen_jpeg%TYPE;
        v_screen_jpeg_file_name aims_apps.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_content_type aims_apps.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_2 aims_apps.screen_jpeg%TYPE;
        v_screen_jpeg_2_file_name aims_apps.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_2_content_type aims_apps.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_3 aims_apps.screen_jpeg%TYPE;
        v_screen_jpeg_3_file_name aims_apps.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_3_content_type aims_apps.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_4 aims_apps.screen_jpeg%TYPE;
        v_screen_jpeg_4_file_name aims_apps.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_4_content_type aims_apps.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_5 aims_apps.screen_jpeg%TYPE;
        v_screen_jpeg_5_file_name aims_apps.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_5_content_type aims_apps.screen_jpeg_content_type%TYPE;

        v_flash_demo aims_apps.flash_demo%TYPE;
        v_flash_demo_file_name aims_apps.flash_demo_file_name%TYPE;
        v_flash_demo_content_type aims_apps.flash_demo_content_type%TYPE;

        v_active_screen_eps aims_apps.active_screen_eps%TYPE;
        v_active_screen_eps_file_name aims_apps.active_screen_eps_file_name%TYPE;
        v_active_screen_eps_cont_type aims_apps.active_screen_eps_content_type%TYPE;


  --For BREW
        v_style_guide aims_brew_apps.style_guide%TYPE;
        v_style_guide_file_name aims_brew_apps.style_guide_file_name%TYPE;
        v_style_guide_content_type aims_brew_apps.style_guide_content_type%TYPE;

        v_mktg_slick_sheet aims_brew_apps.mktg_slick_sheet%TYPE;
        v_mktg_slick_sheet_file_name aims_brew_apps.mktg_slick_sheet_file_name%TYPE;
        v_mktg_slick_sheet_cont_type aims_brew_apps.mktg_slick_sheet_content_type%TYPE;

     v_app_logo_bw_small aims_brew_apps.app_logo_bw_small%TYPE;
        v_app_logo_bw_small_file_name aims_brew_apps.app_logo_bw_small_file_name%TYPE;
        v_app_logo_bw_small_cont_type aims_brew_apps.app_logo_bw_small_content_type%TYPE;

     v_app_logo_bw_large aims_brew_apps.app_logo_bw_large%TYPE;
        v_app_logo_bw_large_file_name aims_brew_apps.app_logo_bw_large_file_name%TYPE;
        v_app_logo_bw_large_cont_type aims_brew_apps.app_logo_bw_large_content_type%TYPE;

     v_app_logo_clrsmall aims_brew_apps.app_logo_clrsmall%TYPE;
        v_app_logo_clrsmall_file_name aims_brew_apps.app_logo_clrsmall_file_name%TYPE;
        v_app_logo_clrsmall_cont_type aims_brew_apps.app_logo_clrsmall_content_type%TYPE;

  v_app_logo_clrlarge aims_brew_apps.app_logo_clrlarge%TYPE;
        v_app_logo_clrlarge_file_name aims_brew_apps.app_logo_clrlarge_file_name%TYPE;
        v_app_logo_clrlarge_cont_type aims_brew_apps.app_logo_clrlarge_content_type%TYPE;

  v_clr_pub_logo aims_brew_apps.clr_pub_logo%TYPE;
        v_clr_pub_logo_file_name aims_brew_apps.clr_pub_logo_file_name%TYPE;
        v_clr_pub_logo_cont_type aims_brew_apps.clr_pub_logo_content_type%TYPE;

        v_prog_guide aims_brew_apps.prog_guide%TYPE;
        v_prog_guide_file_name aims_brew_apps.prog_guide_file_name%TYPE;
        v_prog_guide_cont_type aims_brew_apps.prog_guide_content_type%TYPE;

        v_app_title_name aims_brew_apps.app_title_name%TYPE;
        v_app_title_name_file_name aims_brew_apps.app_title_name_file_name%TYPE;
        v_app_title_name_cont_type aims_brew_apps.app_title_name_content_type%TYPE;

        v_brew_presentation aims_brew_apps.brew_presentation%TYPE;
        v_brew_presentation_file_name aims_brew_apps.brew_presentation_file_name%TYPE;
        v_brew_presentation_cont_type aims_brew_apps.brew_presentation_content_type%TYPE;

        v_brew_company_logo aims_brew_apps.company_logo%type;
        v_brew_company_logo_file_name aims_brew_apps.company_logo_file_name%type;
        v_brew_company_logo_cont_type aims_brew_apps.company_logo_content_type%type;

        v_brew_title_shot aims_brew_apps.title_shot%type;
        v_brew_title_shot_file_name aims_brew_apps.title_shot_file_name%type;
        v_brew_title_shot_cont_type aims_brew_apps.title_shot_content_type%type;
        
        v_brew_high_res_spl aims_brew_apps.high_res_splash%type;
        v_brew_high_res_spl_cont_type aims_brew_apps.high_res_splash_content_type%type;
        v_brew_high_res_spl_file_name aims_brew_apps.high_res_splash_file_name%type;
                            
        v_brew_high_res_act aims_brew_apps.high_res_active%type;
        v_brew_high_res_act_cont_type aims_brew_apps.high_res_active_content_type%type;
        v_brew_high_res_act_file_name aims_brew_apps.high_res_active_file_name%type;                
        
        v_brew_splash_screen aims_brew_apps.splash_screen%type;
        v_brew_splash_screen_cont_type aims_brew_apps.splash_screen_content_type%type;
        v_brew_splash_screen_file_name aims_brew_apps.splash_screen_file_name%type;        
                
        v_brew_small_splash aims_brew_apps.small_splash%type;
        v_brew_small_splash_cont_type aims_brew_apps.small_splash_content_type%type;
        v_brew_small_splash_file_name aims_brew_apps.small_splash_file_name%type;        
        
        v_brew_act_screen_1 aims_brew_apps.active_screen_1%type;
        v_brew_act_screen_1_cont_type aims_brew_apps.active_screen_1_content_type%type;
        v_brew_act_screen_1_file_name aims_brew_apps.active_screen_1_file_name%type;        
                
        v_brew_act_screen_2 aims_brew_apps.active_screen_2%type;
        v_brew_act_screen_2_cont_type aims_brew_apps.active_screen_2_content_type%type;
        v_brew_act_screen_2_file_name aims_brew_apps.active_screen_2_file_name%type;        
                
        v_brew_sml_active_screen aims_brew_apps.sml_active_screen%type;
        v_brew_smlactscreen_cont_type aims_brew_apps.sml_active_screen_content_type%type;
        v_brew_smlactscreen_file_name aims_brew_apps.sml_active_screen_file_name%type;        
                
        v_brew_app_act_flash aims_brew_apps.app_actiion_flash%type;
        v_brew_app_act_flash_cont_type aims_brew_apps.app_actiion_flash_content_type%type;
        v_brew_app_act_flash_file_name aims_brew_apps.app_actiion_flash_file_name%type;
                        
        v_brew_app_gif_act aims_brew_apps.app_gif_action%type;
        v_brew_app_gif_act_cont_type aims_brew_apps.app_gif_action_content_type%type;
        v_brew_app_gif_act_file_name aims_brew_apps.app_gif_action_file_name%type;        
                
        v_brew_media_store aims_brew_apps.media_store%type;
        v_brew_media_store_cont_type aims_brew_apps.media_store_content_type%type;        
        v_brew_media_store_file_name aims_brew_apps.media_store_file_name%type;
        
        v_brew_flash_dem_mov aims_brew_apps.flash_demo_movie%type;
        v_brew_flash_dem_mov_cont_type aims_brew_apps.flash_demo_movie_content_type%type;        
        v_brew_flash_dem_mov_file_name aims_brew_apps.flash_demo_movie_file_name%type;
        
        v_brew_dash_scr_img aims_brew_apps.dashboard_scr_img%type;
        v_brew_dash_scr_img_cont_type aims_brew_apps.dashboard_scr_img_content_type%type;        
        v_brew_dash_scr_img_file_name aims_brew_apps.dashboard_scr_img_file_name%type;
                    
  --For Enterprise
        v_presentation aims_enterprise_apps.presentation%TYPE;
        v_presentation_file_name aims_enterprise_apps.presentation_file_name%TYPE;
        v_presentation_content_type aims_enterprise_apps.presentation_content_type%TYPE;


  --For MMS
        v_sample_content aims_mms_apps.sample_content%TYPE;
        v_sample_content_file_name aims_mms_apps.sample_content_file_name%TYPE;
        v_sample_content_content_type aims_mms_apps.sample_content_content_type%TYPE;


        --For SMS
        v_message_flow aims_sms_apps.message_flow%TYPE;
        v_message_flow_file_name aims_sms_apps.message_flow_file_name%TYPE;
        v_message_flow_content_type aims_sms_apps.message_flow_content_type%TYPE;


     --For WAP
        v_screen_shot aims_wap_apps.screen_shot%TYPE;
        v_screen_shot_file_name aims_wap_apps.screen_shot_file_name%TYPE;
        v_screen_shot_content_type aims_wap_apps.screen_shot_content_type%TYPE;

        v_wap_presentation aims_wap_apps.presentation%TYPE;
        v_wap_presentation_file_name aims_wap_apps.presentation_file_name%TYPE;
        v_wap_presentation_cont_type aims_wap_apps.presentation_content_type%TYPE;

        v_wap_product_logo aims_wap_apps.product_logo%TYPE;
        v_wap_product_logo_file_name aims_wap_apps.product_logo_file_name%TYPE;
        v_wap_product_logo_cont_type aims_wap_apps.product_logo_content_type%TYPE;

        v_wap_product_icon aims_wap_apps.product_icon%TYPE;
        v_wap_product_icon_file_name aims_wap_apps.product_icon_file_name%TYPE;
        v_wap_product_icon_cont_type aims_wap_apps.product_icon_content_type%TYPE;

        v_wap_img_medium aims_wap_apps.app_img_medium%TYPE;
        v_wap_img_medium_file_name aims_wap_apps.app_img_medium_file_name%TYPE;
        v_wap_img_medium_content_type aims_wap_apps.app_img_medium_content_type%TYPE;
        
        v_wap_img_potrait aims_wap_apps.app_img_potrait%TYPE;
        v_wap_img_potrait_file_name aims_wap_apps.app_img_potrait_file_name%TYPE;
        v_wap_img_potrait_content_type aims_wap_apps.app_img_potrait_content_type%TYPE;
        
        v_wap_img_landscape aims_wap_apps.app_img_landscape%TYPE;
        v_wap_img_landscape_file_name aims_wap_apps.app_img_landscape_file_name%TYPE;
        v_wap_img_landscape_cont_type aims_wap_apps.app_img_landscape_content_type%TYPE;

        
     --For VCAST
        v_sample_clip_1 aims_vcast_apps.sample_clip_1%TYPE;
        v_sample_clip_1_file_name aims_vcast_apps.sample_clip_1_file_name%TYPE;
        v_sample_clip_1_content_type aims_vcast_apps.sample_clip_1_content_type%TYPE;

        v_sample_clip_2 aims_vcast_apps.sample_clip_2%TYPE;
        v_sample_clip_2_file_name aims_vcast_apps.sample_clip_2_file_name%TYPE;
        v_sample_clip_2_content_type aims_vcast_apps.sample_clip_2_content_type%TYPE;
        
        v_sample_clip_3 aims_vcast_apps.sample_clip_3%TYPE;
        v_sample_clip_3_file_name aims_vcast_apps.sample_clip_3_file_name%TYPE;
        v_sample_clip_3_content_type aims_vcast_apps.sample_clip_3_content_type%TYPE;

     
     -- For VZ_APPS_ZON
        v_app_landing_page aims_vzappzone_apps.app_landing_page%type;
        v_app_landing_page_content_typ aims_vzappzone_apps.app_landing_page_content_type%type;
        v_app_landing_page_file_name aims_vzappzone_apps.app_landing_page_file_name%type;

        v_app_presentation aims_vzappzone_apps.app_presentation%type;
        v_app_presentation_content_typ aims_vzappzone_apps.app_presentation_content_type%type;
        v_app_presentation_file_name aims_vzappzone_apps.app_presentation_file_name%type;

                 
     --For Dashboard
        v_dash_pub_logo aims_dashboard_apps.clr_pub_logo%TYPE;
        v_dash_pub_logo_file_name aims_dashboard_apps.clr_pub_logo_file_name%TYPE;
        v_dash_pub_logo_cont_type aims_dashboard_apps.clr_pub_logo_content_type%TYPE;

        v_dash_title_name aims_dashboard_apps.app_title_name%TYPE;
        v_dash_title_name_file_name aims_dashboard_apps.app_title_name_file_name%TYPE;
        v_dash_title_name_cont_type aims_dashboard_apps.app_title_name_content_type%TYPE;

        v_dash_content_file aims_dashboard_apps.content_zip_file%TYPE;
        v_dash_content_file_file_name aims_dashboard_apps.content_zip_file_file_name%TYPE;
        v_dash_content_file_cont_type aims_dashboard_apps.content_zip_file_content_type%TYPE;

        v_dash_company_logo aims_dashboard_apps.company_logo%TYPE;
        v_dash_company_logo_file_name aims_dashboard_apps.company_logo_file_name%TYPE;
        v_dash_company_logo_cont_type aims_dashboard_apps.company_logo_content_type%TYPE;
        
        v_dash_title_image aims_dashboard_apps.title_image%TYPE;
        v_dash_title_image_file_name aims_dashboard_apps.title_image_file_name%TYPE;
        v_dash_title_image_cont_type aims_dashboard_apps.title_image_content_type%TYPE;
        
              
   BEGIN

        SELECT
            faq_doc, faq_doc_file_name, faq_doc_content_type,
            user_guide, user_guide_file_name, user_guide_content_type,
            test_plan_results, test_plan_results_file_name, test_plan_results_content_type,
            splash_screen_eps, splash_screen_eps_file_name, splash_screen_eps_content_type,
            screen_jpeg, screen_jpeg_file_name, screen_jpeg_content_type,
            screen_jpeg_2, screen_jpeg_2_file_name, screen_jpeg_2_content_type,
            screen_jpeg_3, screen_jpeg_3_file_name, screen_jpeg_3_content_type,
            screen_jpeg_4, screen_jpeg_4_file_name, screen_jpeg_4_content_type,
            screen_jpeg_5, screen_jpeg_5_file_name, screen_jpeg_5_content_type,
            flash_demo, flash_demo_file_name, flash_demo_content_type,
            active_screen_eps, active_screen_eps_file_name, active_screen_eps_content_type
        INTO
            v_faq_doc, v_faq_doc_file_name, v_faq_doc_content_type,
            v_user_guide, v_user_guide_file_name, v_user_guide_content_type,
            v_test_plan_results, v_test_plan_results_file_name, v_test_plan_results_cont_type,
            v_splash_screen_eps, v_splash_screen_eps_file_name, v_splash_screen_eps_cont_type,
            v_screen_jpeg, v_screen_jpeg_file_name, v_screen_jpeg_content_type,
            v_screen_jpeg_2, v_screen_jpeg_2_file_name, v_screen_jpeg_2_content_type,
            v_screen_jpeg_3, v_screen_jpeg_3_file_name, v_screen_jpeg_3_content_type,
            v_screen_jpeg_4, v_screen_jpeg_4_file_name, v_screen_jpeg_4_content_type,
            v_screen_jpeg_5, v_screen_jpeg_5_file_name, v_screen_jpeg_5_content_type,
            v_flash_demo, v_flash_demo_file_name, v_flash_demo_content_type,
            v_active_screen_eps, v_active_screen_eps_file_name, v_active_screen_eps_cont_type
        FROM
            aims_apps
        WHERE
            apps_id = p_clone_from_app_id;

        UPDATE
            aims_apps
        SET
            faq_doc = v_faq_doc,
            faq_doc_file_name = v_faq_doc_file_name,
            faq_doc_content_type = v_faq_doc_content_type,

            user_guide = v_user_guide,
            user_guide_file_name = v_user_guide_file_name,
            user_guide_content_type = v_user_guide_content_type,

            test_plan_results = v_test_plan_results,
            test_plan_results_file_name = v_test_plan_results_file_name,
            test_plan_results_content_type = v_test_plan_results_cont_type,

            splash_screen_eps = v_splash_screen_eps,
            splash_screen_eps_file_name = v_splash_screen_eps_file_name,
            splash_screen_eps_content_type = v_splash_screen_eps_cont_type,

            screen_jpeg = v_screen_jpeg,
            screen_jpeg_file_name = v_screen_jpeg_file_name,
            screen_jpeg_content_type = v_screen_jpeg_content_type,

            screen_jpeg_2 = v_screen_jpeg_2,
            screen_jpeg_2_file_name = v_screen_jpeg_2_file_name,
            screen_jpeg_2_content_type = v_screen_jpeg_2_content_type,

            screen_jpeg_3 = v_screen_jpeg_3,
            screen_jpeg_3_file_name = v_screen_jpeg_3_file_name,
            screen_jpeg_3_content_type = v_screen_jpeg_3_content_type,

            screen_jpeg_4 = v_screen_jpeg_4,
            screen_jpeg_4_file_name = v_screen_jpeg_4_file_name,
            screen_jpeg_4_content_type = v_screen_jpeg_4_content_type,

            screen_jpeg_5 = v_screen_jpeg_5,
            screen_jpeg_5_file_name = v_screen_jpeg_5_file_name,
            screen_jpeg_5_content_type = v_screen_jpeg_5_content_type,



            flash_demo = v_flash_demo,
            flash_demo_file_name = v_flash_demo_file_name,
            flash_demo_content_type = v_flash_demo_content_type,

            active_screen_eps = v_active_screen_eps,
            active_screen_eps_file_name = v_active_screen_eps_file_name,
            active_screen_eps_content_type = v_active_screen_eps_cont_type

        WHERE
            apps_id = p_clone_to_app_id;


        -- For BREW
        IF (p_platform_id = 1) THEN
            SELECT
                style_guide, style_guide_file_name, style_guide_content_type,
                mktg_slick_sheet, mktg_slick_sheet_file_name, mktg_slick_sheet_content_type,
                app_logo_bw_small, app_logo_bw_small_file_name, app_logo_bw_small_content_type,
                app_logo_bw_large, app_logo_bw_large_file_name, app_logo_bw_large_content_type,
                app_logo_clrsmall, app_logo_clrsmall_file_name, app_logo_clrsmall_content_type,
                app_logo_clrlarge, app_logo_clrlarge_file_name, app_logo_clrlarge_content_type,
                clr_pub_logo, clr_pub_logo_file_name, clr_pub_logo_content_type,
                prog_guide, prog_guide_file_name, prog_guide_content_type,
                app_title_name, app_title_name_file_name, app_title_name_content_type,
                brew_presentation, brew_presentation_file_name, brew_presentation_content_type,
                company_logo, company_logo_file_name, company_logo_content_type, 
                title_shot, title_shot_file_name, title_shot_content_type,

                high_res_splash, high_res_splash_file_name, high_res_splash_content_type,    
                high_res_active, high_res_active_file_name, high_res_active_content_type,
                splash_screen, splash_screen_file_name, splash_screen_content_type,
                small_splash, small_splash_file_name, small_splash_content_type,
                active_screen_1, active_screen_1_file_name, active_screen_1_content_type,
                active_screen_2, active_screen_2_file_name, active_screen_2_content_type,
                sml_active_screen, sml_active_screen_file_name, sml_active_screen_content_type,
                app_actiion_flash, app_actiion_flash_file_name, app_actiion_flash_content_type,
                app_gif_action, app_gif_action_file_name, app_gif_action_content_type,
                media_store, media_store_file_name, media_store_content_type,
                flash_demo_movie,flash_demo_movie_file_name,flash_demo_movie_content_type,
                dashboard_scr_img,dashboard_scr_img_file_name,dashboard_scr_img_content_type
        
        
            INTO
                v_style_guide, v_style_guide_file_name, v_style_guide_content_type,
                v_mktg_slick_sheet, v_mktg_slick_sheet_file_name, v_mktg_slick_sheet_cont_type,
                v_app_logo_bw_small, v_app_logo_bw_small_file_name, v_app_logo_bw_small_cont_type,
                v_app_logo_bw_large, v_app_logo_bw_large_file_name, v_app_logo_bw_large_cont_type,
                v_app_logo_clrsmall, v_app_logo_clrsmall_file_name, v_app_logo_clrsmall_cont_type,
                v_app_logo_clrlarge, v_app_logo_clrlarge_file_name, v_app_logo_clrlarge_cont_type,
                v_clr_pub_logo, v_clr_pub_logo_file_name, v_clr_pub_logo_cont_type,
                v_prog_guide, v_prog_guide_file_name, v_prog_guide_cont_type,
                v_app_title_name, v_app_title_name_file_name, v_app_title_name_cont_type,
                v_brew_presentation, v_brew_presentation_file_name, v_brew_presentation_cont_type,
                v_brew_company_logo, v_brew_company_logo_file_name, v_brew_company_logo_cont_type, 
                v_brew_title_shot, v_brew_title_shot_file_name, v_brew_title_shot_cont_type,
                
                v_brew_high_res_spl, v_brew_high_res_spl_file_name, v_brew_high_res_spl_cont_type ,
                v_brew_high_res_act, v_brew_high_res_act_file_name, v_brew_high_res_act_cont_type, 
                v_brew_splash_screen, v_brew_splash_screen_file_name, v_brew_splash_screen_cont_type,
                v_brew_small_splash, v_brew_small_splash_file_name, v_brew_small_splash_cont_type,
                v_brew_act_screen_1, v_brew_act_screen_1_file_name, v_brew_act_screen_1_cont_type, 
                v_brew_act_screen_2, v_brew_act_screen_2_file_name, v_brew_act_screen_2_cont_type, 
                v_brew_sml_active_screen, v_brew_smlactscreen_file_name, v_brew_smlactscreen_cont_type, 
                v_brew_app_act_flash, v_brew_app_act_flash_file_name, v_brew_app_act_flash_cont_type, 
                v_brew_app_gif_act, v_brew_app_gif_act_file_name, v_brew_app_gif_act_cont_type, 
                v_brew_media_store, v_brew_media_store_file_name, v_brew_media_store_cont_type, 
                v_brew_flash_dem_mov, v_brew_flash_dem_mov_file_name, v_brew_flash_dem_mov_cont_type,
                v_brew_dash_scr_img, v_brew_dash_scr_img_file_name, v_brew_dash_scr_img_cont_type
                
                
            FROM
                aims_brew_apps
            WHERE
                brew_apps_id = p_clone_from_app_id;

            UPDATE
                aims_brew_apps
            SET
                style_guide = v_style_guide,
                style_guide_file_name = v_style_guide_file_name,
                style_guide_content_type = v_style_guide_content_type,

                mktg_slick_sheet = v_mktg_slick_sheet,
                mktg_slick_sheet_file_name = v_mktg_slick_sheet_file_name,
                mktg_slick_sheet_content_type = v_mktg_slick_sheet_cont_type,

                app_logo_bw_small = v_app_logo_bw_small,
                app_logo_bw_small_file_name = v_app_logo_bw_small_file_name,
                app_logo_bw_small_content_type = v_app_logo_bw_small_cont_type,

                app_logo_bw_large = v_app_logo_bw_large,
                app_logo_bw_large_file_name = v_app_logo_bw_large_file_name,
                app_logo_bw_large_content_type = v_app_logo_bw_large_cont_type,

                app_logo_clrsmall = v_app_logo_clrsmall,
                app_logo_clrsmall_file_name = v_app_logo_clrsmall_file_name,
                app_logo_clrsmall_content_type = v_app_logo_clrsmall_cont_type,

                app_logo_clrlarge = v_app_logo_clrlarge,
                app_logo_clrlarge_file_name = v_app_logo_clrlarge_file_name,
                app_logo_clrlarge_content_type = v_app_logo_clrlarge_cont_type,

                clr_pub_logo = v_clr_pub_logo,
                clr_pub_logo_file_name = v_clr_pub_logo_file_name,
                clr_pub_logo_content_type = v_clr_pub_logo_cont_type,

                prog_guide = v_prog_guide,
                prog_guide_file_name = v_prog_guide_file_name,
                prog_guide_content_type = v_prog_guide_cont_type,

                app_title_name = v_app_title_name,
                app_title_name_file_name = v_app_title_name_file_name,
                app_title_name_content_type = v_app_title_name_cont_type,

                brew_presentation = v_brew_presentation,
                brew_presentation_file_name = v_brew_presentation_file_name,
                brew_presentation_content_type = v_brew_presentation_cont_type,
    
                company_logo = v_brew_company_logo, 
                company_logo_file_name = v_brew_company_logo_file_name, 
                company_logo_content_type = v_brew_company_logo_cont_type, 

                title_shot = v_brew_title_shot, 
                title_shot_file_name = v_brew_title_shot_file_name, 
                title_shot_content_type = v_brew_title_shot_cont_type,
                
                high_res_splash = v_brew_high_res_spl,
                high_res_splash_file_name = v_brew_high_res_spl_file_name,
                high_res_splash_content_type = v_brew_high_res_spl_cont_type,
                
                high_res_active = v_brew_high_res_act, 
                high_res_active_file_name = v_brew_high_res_act_file_name,
                high_res_active_content_type = v_brew_high_res_act_cont_type, 
                
                splash_screen = v_brew_splash_screen, 
                splash_screen_file_name = v_brew_splash_screen_file_name,
                splash_screen_content_type = v_brew_splash_screen_cont_type,
                
                small_splash = v_brew_small_splash, 
                small_splash_file_name = v_brew_small_splash_file_name,
                small_splash_content_type = v_brew_small_splash_cont_type,
                
                active_screen_1 = v_brew_act_screen_1, 
                active_screen_1_file_name = v_brew_act_screen_1_file_name,    
                active_screen_1_content_type = v_brew_act_screen_1_cont_type, 
                
                active_screen_2 = v_brew_act_screen_2, 
                active_screen_2_file_name = v_brew_act_screen_2_file_name,    
                active_screen_2_content_type = v_brew_act_screen_2_cont_type, 
                
                sml_active_screen = v_brew_sml_active_screen, 
                sml_active_screen_file_name = v_brew_smlactscreen_file_name,    
                sml_active_screen_content_type = v_brew_smlactscreen_cont_type, 
                
                app_actiion_flash = v_brew_app_act_flash, 
                app_actiion_flash_file_name = v_brew_app_act_flash_file_name,    
                app_actiion_flash_content_type = v_brew_app_act_flash_cont_type, 
                
                app_gif_action = v_brew_app_gif_act, 
                app_gif_action_file_name = v_brew_app_gif_act_file_name,    
                app_gif_action_content_type = v_brew_app_gif_act_cont_type, 
                
                media_store = v_brew_media_store, 
                media_store_file_name = v_brew_media_store_file_name,                                
                media_store_content_type = v_brew_media_store_cont_type, 
    
                flash_demo_movie = v_brew_flash_dem_mov, 
                flash_demo_movie_file_name = v_brew_flash_dem_mov_file_name,
                flash_demo_movie_content_type = v_brew_flash_dem_mov_cont_type, 
                
                dashboard_scr_img = v_brew_dash_scr_img, 
                dashboard_scr_img_file_name = v_brew_dash_scr_img_file_name,
                dashboard_scr_img_content_type = v_brew_dash_scr_img_cont_type
                

            WHERE
                brew_apps_id = p_clone_to_app_id;

        END IF;


        -- For Enterprise
        IF (p_platform_id = 5) THEN
            SELECT
                presentation, presentation_file_name, presentation_content_type
            INTO
                v_presentation, v_presentation_file_name, v_presentation_content_type
            FROM
                aims_enterprise_apps
            WHERE
                enterprise_apps_id = p_clone_from_app_id;

            UPDATE
                aims_enterprise_apps
            SET
                presentation = v_presentation,
                presentation_file_name = v_presentation_file_name,
                presentation_content_type = v_presentation_content_type

            WHERE
                enterprise_apps_id = p_clone_to_app_id;

        END IF;


        -- For MMS
        IF (p_platform_id = 3) THEN
            SELECT
                sample_content, sample_content_file_name, sample_content_content_type
            INTO
                v_sample_content, v_sample_content_file_name, v_sample_content_content_type
            FROM
                aims_mms_apps
            WHERE
                mms_apps_id = p_clone_from_app_id;

            UPDATE
                aims_mms_apps
            SET
                sample_content = v_sample_content,
                sample_content_file_name = v_sample_content_file_name,
                sample_content_content_type = v_sample_content_content_type

            WHERE
                mms_apps_id = p_clone_to_app_id;

        END IF;


        -- For SMS
        IF (p_platform_id = 2) THEN
            SELECT
                message_flow, message_flow_file_name, message_flow_content_type
            INTO
                v_message_flow, v_message_flow_file_name, v_message_flow_content_type
            FROM
                aims_sms_apps
            WHERE
                sms_apps_id = p_clone_from_app_id;

            UPDATE
                aims_sms_apps
            SET
                message_flow = v_message_flow,
                message_flow_file_name = v_message_flow_file_name,
                message_flow_content_type = v_message_flow_content_type

            WHERE
                sms_apps_id = p_clone_to_app_id;

        END IF;


        -- For WAP
        IF (p_platform_id = 4) THEN
            SELECT
                screen_shot, screen_shot_file_name, screen_shot_content_type,
                presentation, presentation_file_name, presentation_content_type,
                product_logo, product_logo_file_name, product_logo_content_type,
                product_icon, product_icon_file_name, product_icon_content_type,
                app_img_medium,app_img_medium_file_name,app_img_medium_content_type,
                app_img_potrait,app_img_potrait_file_name,app_img_potrait_content_type,
                app_img_landscape,app_img_landscape_file_name,app_img_landscape_content_type     
            INTO
                v_screen_shot, v_screen_shot_file_name, v_screen_shot_content_type,
                v_wap_presentation, v_wap_presentation_file_name, v_wap_presentation_cont_type,
                v_wap_product_logo, v_wap_product_logo_file_name, v_wap_product_logo_cont_type,
                v_wap_product_icon, v_wap_product_icon_file_name, v_wap_product_icon_cont_type,               
                v_wap_img_medium,v_wap_img_medium_file_name,v_wap_img_medium_content_type,                        
                v_wap_img_potrait,v_wap_img_potrait_file_name,v_wap_img_potrait_content_type,
                v_wap_img_landscape,v_wap_img_landscape_file_name,v_wap_img_landscape_cont_type       
            FROM
                aims_wap_apps
            WHERE
                wap_apps_id = p_clone_from_app_id;

            UPDATE
                aims_wap_apps
            SET
                screen_shot = v_screen_shot,
                screen_shot_file_name = v_screen_shot_file_name,
                screen_shot_content_type = v_screen_shot_content_type,

                presentation = v_wap_presentation,
                presentation_file_name = v_wap_presentation_file_name,
                presentation_content_type = v_wap_presentation_cont_type,

                product_logo = v_wap_product_logo,
                product_logo_file_name = v_wap_product_logo_file_name,
                product_logo_content_type = v_wap_product_logo_cont_type,

                product_icon = v_wap_product_icon,
                product_icon_file_name = v_wap_product_icon_file_name,
                product_icon_content_type = v_wap_product_icon_cont_type,
                
                app_img_medium = v_wap_img_medium,            
                app_img_medium_file_name = v_wap_img_medium_file_name,     
                app_img_medium_content_type = v_wap_img_medium_content_type,
                
                app_img_potrait = v_wap_img_potrait,            
                app_img_potrait_file_name = v_wap_img_potrait_file_name, 
                app_img_potrait_content_type = v_wap_img_potrait_content_type,
                
                app_img_landscape = v_wap_img_landscape,       
                app_img_landscape_file_name = v_wap_img_landscape_file_name,
                app_img_landscape_content_type = v_wap_img_landscape_cont_type
      
            WHERE
                wap_apps_id = p_clone_to_app_id;

        END IF;


        -- For VCAST
        IF (p_platform_id = 6) THEN
            SELECT
                sample_clip_1, sample_clip_1_file_name, sample_clip_1_content_type,
                sample_clip_2, sample_clip_2_file_name, sample_clip_2_content_type,
                sample_clip_3, sample_clip_3_file_name, sample_clip_3_content_type
            INTO
                v_sample_clip_1, v_sample_clip_1_file_name, v_sample_clip_1_content_type,
                v_sample_clip_2, v_sample_clip_2_file_name, v_sample_clip_2_content_type,
                v_sample_clip_3, v_sample_clip_3_file_name, v_sample_clip_3_content_type
            FROM
                aims_vcast_apps
            WHERE
                vcast_apps_id = p_clone_from_app_id;

            UPDATE
                aims_vcast_apps
            SET
                sample_clip_1 = v_sample_clip_1,
                sample_clip_1_file_name = v_sample_clip_1_file_name,
                sample_clip_1_content_type = v_sample_clip_1_content_type,

                sample_clip_2 = v_sample_clip_2,
                sample_clip_2_file_name = v_sample_clip_2_file_name,
                sample_clip_2_content_type = v_sample_clip_2_content_type,

                sample_clip_3 = v_sample_clip_3,
                sample_clip_3_file_name = v_sample_clip_3_file_name,
                sample_clip_3_content_type = v_sample_clip_3_content_type

            WHERE
                vcast_apps_id = p_clone_to_app_id;

        END IF;
  
  
        -- For Dashboard
        IF (p_platform_id = 43) THEN
            SELECT
                clr_pub_logo, clr_pub_logo_file_name, clr_pub_logo_content_type,
                app_title_name, app_title_name_file_name, app_title_name_content_type,                   
                content_zip_file, content_zip_file_file_name, content_zip_file_content_type,
                title_image, title_image_file_name, title_image_content_type,
                company_logo, company_logo_file_name, company_logo_content_type                                        
            INTO
                v_dash_pub_logo, v_dash_pub_logo_file_name, v_dash_pub_logo_cont_type,
                v_dash_title_name, v_dash_title_name_file_name, v_dash_title_name_cont_type,    
                v_dash_content_file, v_dash_content_file_file_name,v_dash_content_file_cont_type,    
                v_dash_title_image, v_dash_title_image_file_name,v_dash_title_image_cont_type,
                v_dash_company_logo, v_dash_company_logo_file_name,v_dash_company_logo_cont_type                
            FROM
                aims_dashboard_apps
            WHERE
                dashboard_apps_id = p_clone_from_app_id;

            UPDATE
                aims_dashboard_apps
            SET
                clr_pub_logo = v_dash_pub_logo,
                clr_pub_logo_file_name = v_dash_pub_logo_file_name,
                clr_pub_logo_content_type = v_dash_pub_logo_cont_type,

                app_title_name = v_dash_title_name,
                app_title_name_file_name = v_dash_title_name_file_name,
                app_title_name_content_type = v_dash_title_name_cont_type,
                       
                content_zip_file = v_dash_content_file,
                content_zip_file_file_name = v_dash_content_file_file_name,
                content_zip_file_content_type = v_dash_content_file_cont_type,   
        

                title_image = v_dash_title_image,
                title_image_file_name = v_dash_title_image_file_name,
                title_image_content_type = v_dash_title_image_cont_type,
                
                company_logo = v_dash_company_logo,
                company_logo_file_name = v_dash_company_logo_file_name,
                company_logo_content_type = v_dash_company_logo_cont_type                      
                
            WHERE
                dashboard_apps_id = p_clone_to_app_id;

        END IF;  
        
        
        -- For VZ_APPS_ZON
     if (p_platform_id = 42) then
            select
                app_landing_page,app_landing_page_content_type,app_landing_page_file_name,
                app_presentation,app_presentation_content_type,app_presentation_file_name
            into
                v_app_landing_page,v_app_landing_page_content_typ,v_app_landing_page_file_name,
                v_app_presentation,v_app_presentation_content_typ,v_app_presentation_file_name
            from
                aims_vzappzone_apps
            where
                vzappzone_apps_id = p_clone_from_app_id;

            update
                aims_vzappzone_apps
            set
                app_landing_page = v_app_landing_page,
                app_landing_page_content_type = v_app_landing_page_content_typ,
                app_landing_page_file_name = v_app_landing_page_file_name,
                app_presentation = v_app_presentation,
                app_presentation_content_type = v_app_presentation_content_typ,
                app_presentation_file_name = v_app_presentation_file_name
            where
                vzappzone_apps_id = p_clone_to_app_id;

        end if;
        

   END clone_images;


/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_single_files
         (
            p_file_column               IN  VARCHAR2,           -- application id
            p_file_name_column          IN  VARCHAR2,           -- application id
            p_table_name                IN  VARCHAR2,           -- application id
            p_pk_column_name            IN  VARCHAR2,           -- application id
            p_pk_id                     IN  NUMBER,           -- application id
            p_out_result                OUT  VARCHAR2           -- 'Y' one of the filter words is used 'N' words are good
          )

    IS
        sql_select VARCHAR2(1000);
    BEGIN
          p_out_result := 'N';


          sql_select := 'select ''Y'' from ' || p_table_name || ' where '
                                || p_pk_column_name || ' = :1 and '
                                || p_file_column || ' is null and '
                                || p_file_name_column || ' is not null ';

          EXECUTE IMMEDIATE sql_select
                                INTO p_out_result USING p_pk_id;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_out_result := 'N';

    END check_single_files;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_uploaded_files
         (
            p_apps_id                IN  NUMBER,           -- application id
            p_out_result            OUT  VARCHAR2         -- 'Y' one of the filter words is used 'N' words are good
          )

    IS

    /*
    || Overview:        Checks if words are from filter words table.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 03-18-2004       rqazi           Created
    ||
    ||
    ||
    ||

FLASH_DEMO
USER_GUIDE
SPLASH_SCREEN_EPS
ACTIVE_SCREEN_EPS
FAQ_DOC
TEST_PLAN_RESULTS
SCREEN_JPEG
SCREEN_JPEG_2
SCREEN_JPEG_3
SCREEN_JPEG_4
SCREEN_JPEG_5

    */


        v_out_result VARCHAR2(1):= 'N';
        v_out_filter_words VARCHAR2(32767);
        v_temp_filter_word VARCHAR2(200);
        v_file_name VARCHAR2(200);
        v_temp_result VARCHAR2(1);
        v_cnt NUMBER;
        v_file_names_array DBMS_UTILITY.UNCL_ARRAY;

   BEGIN
        v_cnt := 0;
        p_out_result := 'N';

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('ACTIVE_SCREEN_EPS', 'ACTIVE_SCREEN_EPS_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Active Screen Shot';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('FAQ_DOC', 'FAQ_DOC_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'FAQ';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('FLASH_DEMO', 'FLASH_DEMO_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Flash Demo of Running App';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('SCREEN_JPEG', 'SCREEN_JPEG_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('SCREEN_JPEG_2', 'SCREEN_JPEG_2_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 2';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('SCREEN_JPEG_3', 'SCREEN_JPEG_3_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 3';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('SCREEN_JPEG_4', 'SCREEN_JPEG_4_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 4';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('SCREEN_JPEG_5', 'SCREEN_JPEG_5_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 5';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('SPLASH_SCREEN_EPS', 'SPLASH_SCREEN_EPS_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Splash Screen Shot';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('TEST_PLAN_RESULTS', 'TEST_PLAN_RESULTS_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Testing Plan And Results';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('USER_GUIDE', 'USER_GUIDE_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'User Guide';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('APP_LOGO_BW_LARGE', 'APP_LOGO_BW_LARGE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo BW Large';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('APP_LOGO_BW_SMALL', 'APP_LOGO_BW_SMALL_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo BW Small';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('APP_LOGO_CLRLARGE', 'APP_LOGO_CLRLARGE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo Color Large';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('APP_LOGO_CLRSMALL', 'APP_LOGO_CLRSMALL_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo Color Small';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('CLR_PUB_LOGO', 'CLR_PUB_LOGO_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'High Resolution Publisher Logo';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('PROG_GUIDE', 'PROG_GUIDE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Programming Guide';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('MKTG_SLICK_SHEET', 'MKTG_SLICK_SHEET_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Marketing Slick Sheet';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('STYLE_GUIDE', 'STYLE_GUIDE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Style Guide for Use of Application Logo';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('APP_TITLE_NAME', 'APP_TITLE_NAME_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Title Image';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('BREW_PRESENTATION', 'BREW_PRESENTATION_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Presentation';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('PRESENTATION', 'PRESENTATION_FILE_NAME', 'AIMS_ENTERPRISE_APPS', 'ENTERPRISE_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Presentation File';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('SAMPLE_CONTENT', 'SAMPLE_CONTENT_FILE_NAME', 'AIMS_MMS_APPS', 'MMS_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Content File';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('MESSAGE_FLOW', 'MESSAGE_FLOW_FILE_NAME', 'AIMS_SMS_APPS', 'SMS_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Message Flow';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('SAMPLE_CLIP_1', 'SAMPLE_CLIP_1_FILE_NAME', 'AIMS_VCAST_APPS', 'VCAST_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Video Clip';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('SAMPLE_CLIP_2', 'SAMPLE_CLIP_2_FILE_NAME', 'AIMS_VCAST_APPS', 'VCAST_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Video Clip 2';
        END IF;

        v_temp_result := 'N';
        AIMS_LOB_UTILS.check_single_files('SAMPLE_CLIP_3', 'SAMPLE_CLIP_3_FILE_NAME', 'AIMS_VCAST_APPS', 'VCAST_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Video Clip 3';
        END IF;

        IF (v_file_names_array.COUNT > 0) THEN
            PARSE.table_to_delimstring(v_file_names_array, p_out_result, ', ');
        ELSE
            p_out_result := '';
        END IF;

   END check_uploaded_files;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_LOB_UTILS; -- Package Body AIMS_LOB_UTILS 
/
set escape off