set escape !

CREATE SEQUENCE SEQ_APP_REF_ID
   START WITH 5000000
/

CREATE SEQUENCE SEQ_CONTRACT_REF_ID
   START WITH 6000000
/

ALTER TABLE AIMS_CONTRACTS
ADD ( RING_NUMBER NUMBER )
/
ALTER TABLE AIMS_CONTRACTS
ADD( CONTRACT_REF_ID NUMBER)
/
ALTER TABLE AIMS_CONTRACTS$AUD
ADD ( RING_NUMBER NUMBER,
      CONTRACT_REF_ID NUMBER )
/

ALTER TABLE aims_users ADD permanently_deleted VARCHAR2(1)
/

ALTER TABLE aims_users$aud ADD permanently_deleted VARCHAR2(1)
/
CREATE OR REPLACE TRIGGER aims_users$audtrg
   AFTER INSERT OR DELETE OR UPDATE
   ON aims_users
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
      INSERT INTO aims_users$aud
                  (user_id, username, PASSWORD,
                   user_type, contact_id, mother_maiden_name,
                   department_id, manager_id, if_hq,
                   user_account_status, user_session_status,
                   if_blocked, last_login_date, created_by,
                   created_date, last_updated_by,
                   last_updated_date, alliance_id,
                   notification_type, is_account_manager,permanently_deleted,
                   aud_action, aud_timestamp, aud_user)
           VALUES (:NEW.user_id, :NEW.username, :NEW.PASSWORD,
                   :NEW.user_type, :NEW.contact_id, :NEW.mother_maiden_name,
                   :NEW.department_id, :NEW.manager_id, :NEW.if_hq,
                   :NEW.user_account_status, :NEW.user_session_status,
                   :NEW.if_blocked, :NEW.last_login_date, :NEW.created_by,
                   :NEW.created_date, :NEW.last_updated_by,
                   :NEW.last_updated_date, :NEW.alliance_id,
                   :NEW.notification_type, :NEW.is_account_manager,:NEW.permanently_deleted,
                   v_operation, SYSDATE, USER);
   ELSE
      INSERT INTO aims_users$aud
                  (user_id, username, PASSWORD,
                   user_type, contact_id, mother_maiden_name,
                   department_id, manager_id, if_hq,
                   user_account_status, user_session_status,
                   if_blocked, last_login_date, created_by,
                   created_date, last_updated_by,
                   last_updated_date, alliance_id,
                   notification_type, is_account_manager,permanently_deleted,
                   aud_action, aud_timestamp, aud_user)
           VALUES (:OLD.user_id, :OLD.username, :OLD.PASSWORD,
                   :OLD.user_type, :OLD.contact_id, :OLD.mother_maiden_name,
                   :OLD.department_id, :OLD.manager_id, :OLD.if_hq,
                   :OLD.user_account_status, :OLD.user_session_status,
                   :OLD.if_blocked, :OLD.last_login_date, :OLD.created_by,
                   :OLD.created_date, :OLD.last_updated_by,
                   :OLD.last_updated_date, :OLD.alliance_id,
                   :OLD.notification_type, :OLD.is_account_manager,:OLD.permanently_deleted,
                   v_operation, SYSDATE, USER);
   END IF;
END;
/

ALTER TABLE  AIMS_APPS
ADD (APP_REF_ID NUMBER)
/
ALTER TABLE  AIMS_APPS$AUD
ADD (APP_REF_ID NUMBER)
/

ALTER TABLE AIMS_WORKITEM
ADD (ACTION_PAGE_URL VARCHAR2(500))
/
ALTER TABLE AIMS_WORKITEM$AUD
ADD (ACTION_PAGE_URL VARCHAR2(500))
/

CREATE OR REPLACE TRIGGER AIMS_WORKITEM$audtrg
   AFTER INSERT OR DELETE OR UPDATE
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

   IF INSERTING OR UPDATING
   THEN
      INSERT INTO AIMS_WORKITEM$aud (workitem_id,
                                     module_workflow_id,
                                     details,
                                     workflow_state_id,
                                     step_name,
                                     entry_date,
                                     exit_date,
                                     action_taken,
                                     status,
                                     description,
                                     comments,
                                     privilege,
                                     created_by,
                                     created_date,
                                     modified_by,
                                     modified_date,
                                     ACTION_PAGE_URL,
                                     aud_action,
                                     aud_timestamp,
                                     aud_user)
        VALUES   (:NEW.workitem_id,
                  :NEW.module_workflow_id,
                  :NEW.details,
                  :NEW.workflow_state_id,
                  :NEW.step_name,
                  :NEW.entry_date,
                  :NEW.exit_date,
                  :NEW.action_taken,
                  :NEW.status,
                  :NEW.description,
                  :NEW.comments,
                  :NEW.privilege,
                  :NEW.created_by,
                  :NEW.created_date,
                  :NEW.modified_by,
                  :NEW.modified_date,
                  :NEW.ACTION_PAGE_URL,
                  v_operation,
                  SYSDATE,
                  USER);
   ELSE
      INSERT INTO AIMS_WORKITEM$aud (workitem_id,
                                     module_workflow_id,
                                     details,
                                     workflow_state_id,
                                     step_name,
                                     entry_date,
                                     exit_date,
                                     action_taken,
                                     status,
                                     description,
                                     comments,
                                     privilege,
                                     created_by,
                                     created_date,
                                     modified_by,
                                     modified_date,
                                     ACTION_PAGE_URL,
                                     aud_action,
                                     aud_timestamp,
                                     aud_user)
        VALUES   (:OLD.workitem_id,
                  :OLD.module_workflow_id,
                  :OLD.details,
                  :OLD.workflow_state_id,
                  :OLD.step_name,
                  :OLD.entry_date,
                  :OLD.exit_date,
                  :OLD.action_taken,
                  :OLD.status,
                  :OLD.description,
                  :OLD.comments,
                  :OLD.privilege,
                  :OLD.created_by,
                  :OLD.created_date,
                  :OLD.modified_by,
                  :OLD.modified_date,
                  :OLD.ACTION_PAGE_URL,
                  v_operation,
                  SYSDATE,
                  USER);
   END IF;
END;
/



CREATE TABLE AIMS_JAVA_APPS (
   JAVA_APPS_ID                     NUMBER,
   RING_NUMBER                      NUMBER,
   CONTENT_RATING_TYPE_ID           NUMBER,
   INFO_URL                         VARCHAR2 (150),
   ENTERPRISE_APP                   VARCHAR2 (1),
   APP_KEYWORD                      VARCHAR2 (50),
   APP_CATEGORY_TYPE_ID             NUMBER,
   APP_SUB_CATEGORY_TYPE_ID         NUMBER,
   CONTRACT_ID                      NUMBER,
   MKTG_RELEASE                     VARCHAR2 (1),
   HR_PUBLISHER_LOGO                BLOB,
   HR_PUBLISHER_LOGO_FILE_NAME      VARCHAR2 (150),
   HR_PUBLISHER_LOGO_CONTENT_TYPE   VARCHAR2 (100),
   CHNL_TITLE_ICON                  BLOB,
   CHNL_TITLE_ICON_FILE_NAME        VARCHAR2 (150),
   CHNL_TITLE_ICON_CONTENT_TYPE     VARCHAR2 (100),
   SPLH_SCREEN_SHOT                 BLOB,
   SPLH_SCREEN_SHOT_FILE_NAME       VARCHAR2 (150),
   SPLH_SCREEN_SHOT_CONTENT_TYPE    VARCHAR2 (100),
   ACTV_SCREEN_SHOT                 BLOB,
   ACTV_SCREEN_SHOT_FILE_NAME       VARCHAR2 (150),
   ACTV_SCREEN_SHOT_CONTENT_TYPE    VARCHAR2 (100),
   APP_SCREEN_SHOT                  BLOB,
   APP_SCREEN_SHOT_FILE_NAME        VARCHAR2 (150),
   APP_SCREEN_SHOT_CONTENT_TYPE     VARCHAR2 (100),
   APP_SCREEN_SHOT_1                BLOB,
   APP_SCREEN_SHOT_1_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_1_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_2                BLOB,
   APP_SCREEN_SHOT_2_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_2_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_3                BLOB,
   APP_SCREEN_SHOT_3_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_3_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_4                BLOB,
   APP_SCREEN_SHOT_4_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_4_CONTENT_TYPE   VARCHAR2 (100),
   FAQ_FILE                         BLOB,
   FAQ_FILE_FILE_NAME               VARCHAR2 (150),
   FAQ_FILE_CONTENT_TYPE            VARCHAR2 (100),
   TECH_CONTACT_ID                  NUMBER,
   TECH_CONTACT_FIRST_NAME          VARCHAR2 (100),
   TECH_CONTACT_LAST_NAME           VARCHAR2 (200),
   TECH_CONTACT_TITLE               VARCHAR2 (200),
   TECH_CONTACT_EMAIL_ADDRESS       VARCHAR2 (200),
   TECH_CONTACT_PHONE               VARCHAR2 (50),
   TECH_CONTACT_MOBILE              VARCHAR2 (50),
   COMPANY_LOGO                     BLOB,
   COMPANY_LOGO_FILE_NAME           VARCHAR2 (150),
   COMPANY_LOGO_CONTENT_TYPE        VARCHAR2 (100),
   APPLICATION_NAME                 VARCHAR2 (200),
   APP_TITLE_NAME                   BLOB,
   APP_TITLE_NAME_FILE_NAME         VARCHAR2 (150),
   APP_TITLE_NAME_CONTENT_TYPE      VARCHAR2 (100),
   PRODUCT_DESCRIPTION              VARCHAR2 (1000),
   APP_SCREEN_SHOT_5                BLOB,
   APP_SCREEN_SHOT_5_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_5_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_6                BLOB,
   APP_SCREEN_SHOT_6_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_6_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_7                BLOB,
   APP_SCREEN_SHOT_7_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_7_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_8                BLOB,
   APP_SCREEN_SHOT_8_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_8_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_9                BLOB,
   APP_SCREEN_SHOT_9_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_9_CONTENT_TYPE   VARCHAR2 (100),
   USING_APPLICATION                CLOB,
   TIPS_AND_TRICKS                  CLOB,
   FAQ                              CLOB,
   TROUBLESHOOTING                  CLOB,
   DEVELOPMENT_COMPANY_DISCLAIMER   CLOB,
   ADDITIONAL_INFORMATION           CLOB,
   APP_CATEGORY_1_TYPE_ID           NUMBER,
   APP_SUB_CATEGORY_1_TYPE_ID       NUMBER,
   APP_CATEGORY_2_TYPE_ID           NUMBER,
   APP_SUB_CATEGORY_2_TYPE_ID       NUMBER,
   CONTENT_TYPE_ID                  NUMBER,
   VZW_PROJECTED_LIVE_DATE          DATE,
   ENTERPRISE_ID                    VARCHAR2 (100),
   NOTES                            VARCHAR2 (1000),
   TAX_CATEGORY_CODE_ID             NUMBER,
   CONTRACT_REF_ID                  NUMBER,
   APP_REF_ID                       NUMBER,
   TITLE                            NVARCHAR2 (400),
   SHORT_DESC                       NVARCHAR2 (400),
   LONG_DESC                        NVARCHAR2 (1000)
)
/

ALTER TABLE AIMS_JAVA_APPS ADD (
  PRIMARY KEY
 (JAVA_APPS_ID)
)
/
ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_APPS_1
 FOREIGN KEY (JAVA_APPS_ID)
 REFERENCES AIMS_APPS (APPS_ID)
)
/
ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_CONTENT_RATING
 FOREIGN KEY (CONTENT_RATING_TYPE_ID)
 REFERENCES AIMS_TYPES (TYPE_ID)
)
/
ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_TAX_CATEGORY
 FOREIGN KEY (TAX_CATEGORY_CODE_ID)
 REFERENCES AIMS_TAX_CATEGORY_CODES (TAX_CATEGORY_CODE_ID)
)
/
ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_CONTENT_TYPE
 FOREIGN KEY (CONTENT_TYPE_ID)
 REFERENCES AIMS_TYPES (TYPE_ID)
)
/
ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_CONTRACT
 FOREIGN KEY (CONTRACT_ID)
 REFERENCES AIMS_CONTRACTS (CONTRACT_ID)
)
/

CREATE INDEX FK_IND_AIMS_JAVA_APPS_CNT_RAT
   ON AIMS_JAVA_APPS (CONTENT_RATING_TYPE_ID)
/

CREATE INDEX FK_IND_AIMS_JAVA_APPS_CAT
   ON AIMS_JAVA_APPS (APP_CATEGORY_TYPE_ID)
/

CREATE INDEX FK_IND_AIMS_JAVA_APPS_SUB_CAT
   ON AIMS_JAVA_APPS (APP_SUB_CATEGORY_TYPE_ID)
/

CREATE INDEX FK_IND_AIMS_JAVA_APPS_CAT1
   ON AIMS_JAVA_APPS (APP_CATEGORY_1_TYPE_ID)
/

CREATE INDEX FK_IND_AIMS_JAVA_APPS_SUB_CAT1
   ON AIMS_JAVA_APPS (APP_SUB_CATEGORY_1_TYPE_ID)
/

CREATE INDEX FK_IND_AIMS_JAVA_APPS_CAT2
   ON AIMS_JAVA_APPS (APP_CATEGORY_2_TYPE_ID)
/

CREATE INDEX FK_IND_AIMS_JAVA_APPS_SUB_CAT2
   ON AIMS_JAVA_APPS (APP_SUB_CATEGORY_2_TYPE_ID)
/

CREATE INDEX FK_IND_AIMS_JAVA_APPS_TAX_CAT
   ON AIMS_JAVA_APPS (TAX_CATEGORY_CODE_ID)
/

CREATE INDEX FK_IND_AIMS_JAVA_APPS_CNT_TYPE
   ON AIMS_JAVA_APPS (CONTENT_TYPE_ID)
/

CREATE INDEX FK_IND_AIMS_JAVA_APPS_CNRT_ID
   ON AIMS_JAVA_APPS (CONTRACT_ID)
/


CREATE TABLE AIMS_JAVA_APPS$AUD (
   JAVA_APPS_ID                     NUMBER,
   RING_NUMBER                      NUMBER,
   CONTENT_RATING_TYPE_ID           NUMBER,
   INFO_URL                         VARCHAR2 (150),
   ENTERPRISE_APP                   VARCHAR2 (1),
   APP_KEYWORD                      VARCHAR2 (50),
   APP_CATEGORY_TYPE_ID             NUMBER,
   APP_SUB_CATEGORY_TYPE_ID         NUMBER,
   CONTRACT_ID                      NUMBER,
   MKTG_RELEASE                     VARCHAR2 (1),
   HR_PUBLISHER_LOGO_FILE_NAME      VARCHAR2 (150),
   HR_PUBLISHER_LOGO_CONTENT_TYPE   VARCHAR2 (100),
   CHNL_TITLE_ICON_FILE_NAME        VARCHAR2 (150),
   CHNL_TITLE_ICON_CONTENT_TYPE     VARCHAR2 (100),
   SPLH_SCREEN_SHOT_FILE_NAME       VARCHAR2 (150),
   SPLH_SCREEN_SHOT_CONTENT_TYPE    VARCHAR2 (100),
   ACTV_SCREEN_SHOT_FILE_NAME       VARCHAR2 (150),
   ACTV_SCREEN_SHOT_CONTENT_TYPE    VARCHAR2 (100),
   APP_SCREEN_SHOT_FILE_NAME        VARCHAR2 (150),
   APP_SCREEN_SHOT_CONTENT_TYPE     VARCHAR2 (100),
   APP_SCREEN_SHOT_1_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_1_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_2_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_2_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_3_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_3_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_4_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_4_CONTENT_TYPE   VARCHAR2 (100),
   FAQ_FILE_FILE_NAME               VARCHAR2 (150),
   FAQ_FILE_CONTENT_TYPE            VARCHAR2 (100),
   TECH_CONTACT_ID                  NUMBER,
   TECH_CONTACT_FIRST_NAME          VARCHAR2 (100),
   TECH_CONTACT_LAST_NAME           VARCHAR2 (200),
   TECH_CONTACT_TITLE               VARCHAR2 (200),
   TECH_CONTACT_EMAIL_ADDRESS       VARCHAR2 (200),
   TECH_CONTACT_PHONE               VARCHAR2 (50),
   TECH_CONTACT_MOBILE              VARCHAR2 (50),
   COMPANY_LOGO_FILE_NAME           VARCHAR2 (150),
   COMPANY_LOGO_CONTENT_TYPE        VARCHAR2 (100),
   APPLICATION_NAME                 VARCHAR2 (200),
   APP_TITLE_NAME_FILE_NAME         VARCHAR2 (150),
   APP_TITLE_NAME_CONTENT_TYPE      VARCHAR2 (100),
   PRODUCT_DESCRIPTION              VARCHAR2 (1000),
   APP_SCREEN_SHOT_5_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_5_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_6_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_6_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_7_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_7_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_8_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_8_CONTENT_TYPE   VARCHAR2 (100),
   APP_SCREEN_SHOT_9_FILE_NAME      VARCHAR2 (150),
   APP_SCREEN_SHOT_9_CONTENT_TYPE   VARCHAR2 (100),
   APP_CATEGORY_1_TYPE_ID           NUMBER,
   APP_SUB_CATEGORY_1_TYPE_ID       NUMBER,
   APP_CATEGORY_2_TYPE_ID           NUMBER,
   APP_SUB_CATEGORY_2_TYPE_ID       NUMBER,
   CONTENT_TYPE_ID                  NUMBER,
   VZW_PROJECTED_LIVE_DATE          DATE,
   ENTERPRISE_ID                    VARCHAR2 (100),
   NOTES                            VARCHAR2 (1000),
   TAX_CATEGORY_CODE_ID             NUMBER,
   CONTRACT_REF_ID                  NUMBER,
   APP_REF_ID                       NUMBER,
   TITLE                            NVARCHAR2 (400),
   SHORT_DESC                       NVARCHAR2 (400),
   LONG_DESC                        NVARCHAR2 (1000),
   AUD_ACTION                       CHAR (3),
   AUD_TIMESTAMP                    DATE,
   AUD_USER                         VARCHAR2 (30)
)
/


INSERT INTO AIMS_PLATFORMS (PLATFORM_ID,
                            PLATFORM_NAME,
                            PLATFORM_DESC,
                            CREATED_BY,
                            CREATED_DATE,
                            LAST_UPDATED_BY,
                            LAST_UPDATED_DATE)
  VALUES   (44,
            'Java',
            'Java',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/
INSERT INTO AIMS_TYPE_DEFS (TYPE_DEF_ID,
                            TYPE_NAME,
                            TYPE_DESCRIPTION,
                            CREATED_BY,
                            CREATED_DATE,
                            LAST_UPDATED_BY,
                            LAST_UPDATED_DATE)
  VALUES   (9,
            'JAVA_CONTENT_RATING',
            'Content Rating for Java Apps',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_TYPE_DEFS (TYPE_DEF_ID,
                            TYPE_NAME,
                            TYPE_DESCRIPTION,
                            CREATED_BY,
                            CREATED_DATE,
                            LAST_UPDATED_BY,
                            LAST_UPDATED_DATE)
  VALUES   (10,
            'JAVA_CONTENT_TYPE',
            'Content Type for Java Apps',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_TYPE_DEFS (TYPE_DEF_ID,
                            TYPE_NAME,
                            TYPE_DESCRIPTION,
                            CREATED_BY,
                            CREATED_DATE,
                            LAST_UPDATED_BY,
                            LAST_UPDATED_DATE)
  VALUES   (20,
            'CONTRACT_RING_NUMBER',
            'Contract Ring Number',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_TYPES (TYPE_ID,
                        TYPE_DEF_ID,
                        TYPE_VALUE,
                        DESCRIPTION,
                        SORT_ORDER,
                        CREATED_BY,
                        CREATED_DATE,
                        LAST_UPDATED_BY,
                        LAST_UPDATED_DATE)
  VALUES   (172,
            20,
            'Ring-2',
            'Ring-2',
            20,
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_TYPES (TYPE_ID,
                        TYPE_DEF_ID,
                        TYPE_VALUE,
                        DESCRIPTION,
                        SORT_ORDER,
                        CREATED_BY,
                        CREATED_DATE,
                        LAST_UPDATED_BY,
                        LAST_UPDATED_DATE)
  VALUES   (173,
            20,
            'Ring-3',
            'Ring-3',
            30,
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/


Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2000, 9, 'C (7+)', 'C (7+)', 1, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2001, 9, 'T (13+)', 'T (13+)', 2, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2002, 9, 'YA (17+)', 'YA (17+)', 3, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2003, 9, 'M (18+)', 'M (18+)', 4, 'system', sysdate, 'system', sysdate)
/

INSERT INTO AIMS_TYPES (TYPE_ID,
                        TYPE_DEF_ID,
                        TYPE_VALUE,
                        DESCRIPTION,
                        SORT_ORDER,
                        CREATED_BY,
                        CREATED_DATE,
                        LAST_UPDATED_BY,
                        LAST_UPDATED_DATE)
  VALUES   (301,
            10,
            'APPLICATION',
            'APPLICATION',
            1,
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_TYPES (TYPE_ID,
                        TYPE_DEF_ID,
                        TYPE_VALUE,
                        DESCRIPTION,
                        SORT_ORDER,
                        CREATED_BY,
                        CREATED_DATE,
                        LAST_UPDATED_BY,
                        LAST_UPDATED_DATE)
  VALUES   (302,
            10,
            'GAME',
            'GAME',
            2,
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/
INSERT INTO AIMS_RESOURCES
   (RESOURCE_ID, FILE_NAME, RESOURCE_CONTENT_TYPE, DESCRIPTION)
 VALUES
   (3, 'Java_Contetnt_Rating.pdf', 'application/pdf', 
   'This document is downloadable via a link in the "Edit Java" interface. This link is next to the Content Rating field')
/  


Insert into AIMS_APP_CATEGORIES
   (CATEGORY_ID, CATEGORY_NAME, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2000, 'Games', 44, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_CATEGORIES
   (CATEGORY_ID, CATEGORY_NAME, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2001, 'Music*', 44, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_CATEGORIES
   (CATEGORY_ID, CATEGORY_NAME, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2002, 'Pictures', 44, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_CATEGORIES
   (CATEGORY_ID, CATEGORY_NAME, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2003, 'Other', 44, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2000, 'Puzzle Games', 'Puzzle Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2001, 'Sports Games', 'Sports Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2002, 'Classic Games', 'Classic Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2003, 'Play and Win', 'Play and Win', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2004, 'TV/Movie/Music Games', 'TV/Movie/Music Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2005, 'Action Games', 'Action Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2006, 'Casino Games', 'Casino Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2007, 'Strategy Games', 'Strategy Games', 2000, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2008, 'True Tones', 'True Tones', 2001, 'system', sysdate, 'system', sysdate)
/
       

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2009, 'Ringtones', 'Ringtones', 2001, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2010, 'Fun Tones', 'Fun Tones', 2001, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2011, 'Music Magazines', 'Music Magazines', 2001, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2012, 'Music Recognition', 'Music Recognition', 2001, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2013, 'Eye Candy', 'Eye Candy', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2014, 'Sports', 'Sports', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2015, 'The Boy`s Club', 'The Boy`s Club', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2016, 'SlideShows', 'SlideShows', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2017, 'My Wallpaper, My Way', 'My Wallpaper, My Way', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2018, 'Something 4 Tha Ladies', 'Something 4 Tha Ladies', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2019, 'For Your Entertainment', 'For Your Entertainment', 2002, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2020, 'Entertainment', 'Entertainment', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2021, 'Travel and Navigation', 'Travel and Navigation', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2022, 'Sports', 'Sports', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2023, 'Weather', 'Weather', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2024, 'Community and Sharing', 'Community and Sharing', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2025, 'E-mail', 'E-mail', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2026, 'Health and Wellness', 'Health and Wellness', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2027, 'Family Life', 'Family Life', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2028, 'Shopping n Style', 'Shopping n Style', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2029, 'Government', 'Government', 2003, 'system', sysdate, 'system', sysdate)
/

Insert into AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (2030, 'Business Tools/Information', 'Business Tools/Information', 2003, 'system', sysdate, 'system', sysdate)
/


INSERT INTO AIMS_WORKFLOW_DETAILS (WORKFLOW_DETAIL_ID,
                                   WORKFLOW_TYPE,
                                   MODULE_TYPE,
                                   MODULE_TYPE_ID,
                                   WORKFLOW_NAME,
                                   WORKFLOW_XML_NAME,
                                   WORKFLOW_DISPLAY_NAME,
                                   DESCRIPTION,
                                   CREATED_BY,
                                   CREATED_DATE)
  VALUES   (8,
            40,
            41,
            8,
            'workflow_jondapp',
            'workflow_java_ondeck_app.xml',
            'Java Ondeck Workflow',
            'Java ondeck workflow',
            'system',
            SYSDATE)
/

INSERT INTO AIMS_WORKFLOW_DETAILS (WORKFLOW_DETAIL_ID,
                                   WORKFLOW_TYPE,
                                   MODULE_TYPE,
                                   MODULE_TYPE_ID,
                                   WORKFLOW_NAME,
                                   WORKFLOW_XML_NAME,
                                   WORKFLOW_DISPLAY_NAME,
                                   DESCRIPTION,
                                   CREATED_BY,
                                   CREATED_DATE)
  VALUES   (10,
            40,
            41,
            10,
            'workflow_jofdapp',
            'workflow_java_offdeck_app.xml',
            'Java Offdeck Workflow',
            'Java offdeck workflow',
            'system',
            SYSDATE)
/

INSERT INTO aims_sys_privileges (privilege_id,
                                 privilege_name,
                                 privilege_description,
                                 created_by,
                                 created_date,
                                 last_updated_by,
                                 last_updated_date,
                                 menu_id,
                                 sub_menu_id,
                                 privilege_key,
                                 available_to)
  VALUES   (801,
            'Java App Programming Content Manager',
            'Java App Programming Content Manager',
            'system',
            SYSDATE,
            'system',
            SYSDATE,
            NULL,
            NULL,
            'JAVA_APP_PROGRAMMING_CONTENT_MANAGER',
            'V')
/

INSERT INTO aims_sys_privileges (privilege_id,
                                 privilege_name,
                                 privilege_description,
                                 created_by,
                                 created_date,
                                 last_updated_by,
                                 last_updated_date,
                                 menu_id,
                                 sub_menu_id,
                                 privilege_key,
                                 available_to)
  VALUES   (803,
            'Java App Legal Manager',
            'Java App Legal Manager',
            'system',
            SYSDATE,
            'system',
            SYSDATE,
            NULL,
            NULL,
            'JAVA_APP_LEGAL_MANAGER',
            'V')
/

INSERT INTO aims_sys_privileges (privilege_id,
                                 privilege_name,
                                 privilege_description,
                                 created_by,
                                 created_date,
                                 last_updated_by,
                                 last_updated_date,
                                 menu_id,
                                 sub_menu_id,
                                 privilege_key,
                                 available_to)
  VALUES   (804,
            'Java App Content Standard Manager',
            'Java App Content Standard Manager',
            'system',
            SYSDATE,
            'system',
            SYSDATE,
            NULL,
            NULL,
            'JAVA_APP_CONTENT_STANDARD_MANAGER',
            'V')
/

INSERT INTO aims_sys_privileges (privilege_id,
                                 privilege_name,
                                 privilege_description,
                                 created_by,
                                 created_date,
                                 last_updated_by,
                                 last_updated_date,
                                 menu_id,
                                 sub_menu_id,
                                 privilege_key,
                                 available_to)
  VALUES   (805,
            'Java App Tax Manager',
            'Java App Tax Manager',
            'system',
            SYSDATE,
            'system',
            SYSDATE,
            NULL,
            NULL,
            'JAVA_APP_TAX_MANAGER',
            'V')
/

INSERT INTO aims_sys_privileges (privilege_id,
                                 privilege_name,
                                 privilege_description,
                                 created_by,
                                 created_date,
                                 last_updated_by,
                                 last_updated_date,
                                 menu_id,
                                 sub_menu_id,
                                 privilege_key,
                                 available_to)
  VALUES   (2001,
            'Manage App Javaondeck',
            'Manage App Javaondeck',
            'system',
            SYSDATE,
            'system',
            SYSDATE,
            NULL,
            NULL,
            'MANAGE_APP_JAVAONDECK',
            'B')
/

INSERT INTO aims_sys_privileges (privilege_id,
                                 privilege_name,
                                 privilege_description,
                                 created_by,
                                 created_date,
                                 last_updated_by,
                                 last_updated_date,
                                 menu_id,
                                 sub_menu_id,
                                 privilege_key,
                                 available_to)
  VALUES   (2002,
            'Manage App Javaoffdeck',
            'Manage App Javaoffdeck',
            'system',
            SYSDATE,
            'system',
            SYSDATE,
            NULL,
            NULL,
            'MANAGE_APP_JAVAFFDECK',
            'B')
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2001,
            'SUBMITTED',
            'Submission Phase for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2002,
            'RFI-CONTENT PROG',
            'RFI-CONTENT PROG for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2003,
            'CONTENT PROG APPROVED',
            'CONTENT PROG APPROVED for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2004,
            'RFI-LEGAL/CONTENT',
            'RFI-LEGAL/CONTENT for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2005,
            'LEGAL/CONTENT APPROVED',
            'LEGAL/CONTENT APPROVED for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2006,
            'RFI-TAX REVIEW',
            'RFI-TAX REVIEW for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2007,
            'PENDING TAX APPROVAL',
            'PENDING TAX APPROVAL for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2008,
            'REJECTED',
            'Rejected for java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID,
                                   PHASE_NAME,
                                   PHASE_DESC,
                                   CREATED_BY,
                                   CREATED_DATE,
                                   LAST_UPDATED_BY,
                                   LAST_UPDATED_DATE)
  VALUES   (2009,
            'APPROVED',
            'APPROVED java application',
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/
INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2003,
            'JAVA_APP_APPROVAL',
            'JAVA_APP_APPROVAL',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/

INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2003, 2003, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2003)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2003)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2003)
/

INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2004,
            'JAVA_APP_PROGRAMMING_CONTENT_APPROVAL',
            'JAVA_APP_PROGRAMMING_CONTENT_APPROVAL',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/

INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2004, 2004, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2004)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2004)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2004)
/

INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2005,
            'JAVA_APP_PROGRAMMING_CONTENT_RFI',
            'JAVA_APP_PROGRAMMING_CONTENT_RFI',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/

INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2005, 2005, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2005)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2005)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2005)
/

INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2006,
            'JAVA_APP_PROGRAMMING_CONTENT_DENY',
            'JAVA_APP_PROGRAMMING_CONTENT_DENY',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/

INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2006, 2006, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2006)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2006)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2006)
/

INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2007,
            'JAVA_APP_LEGAL_CONTENT_APPROVAL',
            'JAVA_APP_LEGAL_CONTENT_APPROVAL',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/

INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2007, 2007, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2007)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2007)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2007)
/

INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2008,
            'JAVA_APP_LEGAL_CONTENT_DENY',
            'JAVA_APP_LEGAL_CONTENT_DENY',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/

INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2008, 2008, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2008)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2008)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2008)
/

INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2009,
            'JAVA_APP_LEGAL_CONTENT_RFI',
            'JAVA_APP_LEGAL_CONTENT_RFI',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/

INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2009, 2009, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2009)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2009)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2009)
/

INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2010,
            'JAVA_APP_TAX_APPROVAL_PENDING',
            'JAVA_APP_TAX_APPROVAL_PENDING',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/

INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2010, 2010, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2010)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2010)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2010)
/

INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2011,
            'JAVA_APP_TAX_APPROVAL_RFI',
            'JAVA_APP_TAX_APPROVAL_RFI',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/

INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2011, 2011, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2011)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2011)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2011)
/

INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2012,
            'JAVA_APP_PROGRAMMING_CONTENT_RESUBMIT',
            'JAVA_APP_PROGRAMMING_CONTENT_RESUBMIT',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/

INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2012, 2012, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2012)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2012)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2012)
/

INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2013,
            'JAVA_APP_LEGAL_CONTENT_RESUBMIT',
            'JAVA_APP_LEGAL_CONTENT_RESUBMIT',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/

INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2013, 2013, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2013)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2013)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2013)
/

INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2014,
            'JAVA_APP_TAX_RESUBMIT',
            'JAVA_APP_TAX_RESUBMIT',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/


INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2014, 2014, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (1, 2014)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (2, 2014)
/

INSERT INTO aims_event_place_holders (place_holder_id, event_id)
  VALUES   (6, 2014)
/
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2101, 'JAVA_APP_DELETED', 'JAVA_APP_DELETED', sysdate, 'system', sysdate, 'system')
/   

Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2101)
/   
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2101)
/
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2101,2101, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/

INSERT INTO AIMS_ROLE_PRIVILEGES (ROLE_ID,
                                  PRIVILEGE_ID,
                                  IF_SELECT_ALLOWED,
                                  IF_CREATE_ALLOWED,
                                  IF_UPDATE_ALLOWED,
                                  IF_DELETE_ALLOWED)
  VALUES   (3,
            2001,
            'Y',
            'Y',
            'Y',
            'Y')
/

INSERT INTO AIMS_ROLE_PRIVILEGES (ROLE_ID,
                                  PRIVILEGE_ID,
                                  IF_SELECT_ALLOWED,
                                  IF_CREATE_ALLOWED,
                                  IF_UPDATE_ALLOWED)
  VALUES   (3,
            2002,
            'Y',
            'Y',
            'Y')
/

INSERT INTO AIMS_ROLE_PRIVILEGES (ROLE_ID,
                                  PRIVILEGE_ID,
                                  IF_SELECT_ALLOWED,
                                  IF_CREATE_ALLOWED,
                                  IF_UPDATE_ALLOWED,
                                  IF_DELETE_ALLOWED)
  VALUES   (131,
            2001,
            'Y',
            'Y',
            'Y',
            'Y')
/

INSERT INTO AIMS_ROLE_PRIVILEGES (ROLE_ID,
                                  PRIVILEGE_ID,
                                  IF_SELECT_ALLOWED,
                                  IF_CREATE_ALLOWED,
                                  IF_UPDATE_ALLOWED)
  VALUES   (131,
            2002,
            'Y',
            'Y',
            'Y')
/

INSERT INTO AIMS_ROLE_PRIVILEGES (ROLE_ID,
                                  PRIVILEGE_ID,
                                  IF_SELECT_ALLOWED,
                                  IF_CREATE_ALLOWED,
                                  IF_UPDATE_ALLOWED,
                                  IF_DELETE_ALLOWED)
  VALUES   (98,
            801,
            'Y',
            'Y',
            'Y',
            'Y')
/

INSERT INTO AIMS_ROLE_PRIVILEGES (ROLE_ID,
                                  PRIVILEGE_ID,
                                  IF_SELECT_ALLOWED,
                                  IF_CREATE_ALLOWED,
                                  IF_UPDATE_ALLOWED)
  VALUES   (98,
            803,
            'Y',
            'Y',
            'Y')
/

INSERT INTO AIMS_ROLE_PRIVILEGES (ROLE_ID,
                                  PRIVILEGE_ID,
                                  IF_SELECT_ALLOWED,
                                  IF_CREATE_ALLOWED,
                                  IF_UPDATE_ALLOWED,
                                  IF_DELETE_ALLOWED)
  VALUES   (98,
            804,
            'Y',
            'Y',
            'Y',
            'Y')
/

INSERT INTO AIMS_ROLE_PRIVILEGES (ROLE_ID,
                                  PRIVILEGE_ID,
                                  IF_SELECT_ALLOWED,
                                  IF_CREATE_ALLOWED,
                                  IF_UPDATE_ALLOWED)
  VALUES   (98,
            805,
            'Y',
            'Y',
            'Y')
/

ALTER TABLE aims_contacts
  ADD alliance_id NUMBER
/
ALTER TABLE aims_contacts$AUD
  ADD alliance_id NUMBER
/

CREATE OR REPLACE TRIGGER aims_contacts$audtrg
   AFTER INSERT OR DELETE OR UPDATE
   ON aims_contacts
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

   IF INSERTING OR UPDATING
   THEN
      INSERT INTO AIMS_CONTACTS$AUD (CONTACT_ID,
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
                                     alliance_id,
                                     aud_action,
                                     aud_timestamp,
                                     aud_user)
        VALUES   (:new.CONTACT_ID,
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
                  :new.alliance_id,
                  v_operation,
                  SYSDATE,
                  USER);
   ELSE
      INSERT INTO AIMS_CONTACTS$AUD (CONTACT_ID,
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
                                     alliance_id,
                                     aud_action,
                                     aud_timestamp,
                                     aud_user)
        VALUES   (:old.CONTACT_ID,
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
                  :old.alliance_id,
                  v_operation,
                  SYSDATE,
                  USER);
   END IF;
END;
/

ALTER TABLE aims_sys_roles
  ADD vzdn_mapping_role_id NUMBER
/
ALTER TABLE aims_sys_roles$aud
  ADD vzdn_mapping_role_id NUMBER
/


CREATE OR REPLACE TRIGGER aims_sys_roles$audtrg
   AFTER INSERT OR DELETE OR UPDATE
   ON aims_sys_roles
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

   IF INSERTING OR UPDATING
   THEN
      INSERT INTO AIMS_SYS_ROLES$AUD (ROLE_ID,
                                      ROLE_NAME,
                                      ROLE_DESCRIPTION,
                                      ROLE_TYPE,
                                      CREATED_BY,
                                      CREATED_DATE,
                                      LAST_UPDATED_BY,
                                      LAST_UPDATED_DATE,
                                      vzdn_mapping_role_id,
                                      aud_action,
                                      aud_timestamp,
                                      aud_user)
        VALUES   (:new.ROLE_ID,
                  :new.ROLE_NAME,
                  :new.ROLE_DESCRIPTION,
                  :new.ROLE_TYPE,
                  :new.CREATED_BY,
                  :new.CREATED_DATE,
                  :new.LAST_UPDATED_BY,
                  :new.LAST_UPDATED_DATE,
                  :new.vzdn_mapping_role_id,
                  v_operation,
                  SYSDATE,
                  USER);
   ELSE
      INSERT INTO AIMS_SYS_ROLES$AUD (ROLE_ID,
                                      ROLE_NAME,
                                      ROLE_DESCRIPTION,
                                      ROLE_TYPE,
                                      CREATED_BY,
                                      CREATED_DATE,
                                      LAST_UPDATED_BY,
                                      LAST_UPDATED_DATE,
                                      vzdn_mapping_role_id,
                                      aud_action,
                                      aud_timestamp,
                                      aud_user)
        VALUES   (:old.ROLE_ID,
                  :old.ROLE_NAME,
                  :old.ROLE_DESCRIPTION,
                  :old.ROLE_TYPE,
                  :old.CREATED_BY,
                  :old.CREATED_DATE,
                  :old.LAST_UPDATED_BY,
                  :old.LAST_UPDATED_DATE,
                  :old.vzdn_mapping_role_id,
                  v_operation,
                  SYSDATE,
                  USER);
   END IF;
END;
/



CREATE TABLE aims_user_offer (offer_id           NUMBER,
                              offer_date         DATE,
                              offer_to           VARCHAR2 (100),
                              alliance_id        NUMBER NOT NULL,
                              acc_rej_date       DATE,
                              status             VARCHAR2 (10),
                              offer_from_name    VARCHAR2 (100),
                              offer_from_email   VARCHAR2 (100))
/

ALTER TABLE aims_user_offer ADD (
  CONSTRAINT pk_aims_user_offer
 PRIMARY KEY
 (offer_id)
)
/
ALTER TABLE aims_user_offer ADD (
 CONSTRAINT fk_user_offer_alliance_1
 FOREIGN KEY (alliance_id)
 REFERENCES aims_alliances (alliance_id))
/

CREATE SEQUENCE seq_aims_user_offer
/

CREATE TABLE aims_user_offer$aud (offer_id           NUMBER,
                                  offer_date         DATE,
                                  offer_to           VARCHAR2 (100),
                                  alliance_id        NUMBER,
                                  acc_rej_date       DATE,
                                  status             VARCHAR2 (10),
                                  offer_from_name    VARCHAR2 (100),
                                  offer_from_email   VARCHAR2 (100),
                                  AUD_ACTION         CHAR (3),
                                  AUD_TIMESTAMP      DATE,
                                  AUD_USER           VARCHAR2 (30))
/


CREATE OR REPLACE TRIGGER aims_user_offer$audtrg
   AFTER INSERT OR DELETE OR UPDATE
   ON aims_user_offer
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

   IF INSERTING OR UPDATING
   THEN
      INSERT INTO aims_user_offer$AUD (offer_id,
                                       offer_date,
                                       offer_to,
                                       alliance_id,
                                       acc_rej_date,
                                       status,
                                       offer_from_name,
                                       offer_from_email,
                                       aud_action,
                                       aud_timestamp,
                                       aud_user)
        VALUES   (:NEW.offer_id,
                  :NEW.offer_date,
                  :NEW.offer_to,
                  :NEW.alliance_id,
                  :NEW.acc_rej_date,
                  :NEW.status,
                  :NEW.offer_from_name,
                  :NEW.offer_from_email,
                  v_operation,
                  SYSDATE,
                  USER);
   ELSE
      INSERT INTO aims_user_offer$AUD (offer_id,
                                       offer_date,
                                       offer_to,
                                       alliance_id,
                                       acc_rej_date,
                                       status,
                                       offer_from_name,
                                       offer_from_email,
                                       aud_action,
                                       aud_timestamp,
                                       aud_user)
        VALUES   (:OLD.offer_id,
                  :OLD.offer_date,
                  :OLD.offer_to,
                  :OLD.alliance_id,
                  :OLD.acc_rej_date,
                  :OLD.status,
                  :OLD.offer_from_name,
                  :OLD.offer_from_email,
                  v_operation,
                  SYSDATE,
                  USER);
   END IF;
END;
/

ALTER TABLE aims_contracts ADD
  (html_file             BLOB,
   html_file_file_name   VARCHAR2(150),
   html_file_content_type VARCHAR2(100),
   is_click_through_contract VARCHAR2(1))
/
ALTER TABLE aims_contracts ADD (
  CONSTRAINT fk_contracts_3 FOREIGN KEY (ring_number)
    REFERENCES aims_types (type_id))
/
ALTER TABLE aims_contracts$AUD ADD
  (html_file_file_name   VARCHAR2(150),
   html_file_content_type VARCHAR2(100),
   is_click_through_contract VARCHAR2(1))
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

   IF INSERTING OR UPDATING
   THEN
      INSERT INTO aims_contracts$aud (contract_id,
                                      contract_title,
                                      version,
                                      platform_id,
                                      if_negotiable,
                                      status,
                                      if_attach_to_soln,
                                      comments,
                                      created_by,
                                      created_date,
                                      last_updated_by,
                                      last_updated_date,
                                      document_file_name,
                                      document_content_type,
                                      expiry_date,
                                      IS_BOBO_CONTRACT,
                                      RING_NUMBER,
                                      CONTRACT_REF_ID,
                                      html_file_file_name,
                                      html_file_content_type,
                                      is_click_through_contract,
                                      aud_action,
                                      aud_timestamp,
                                      aud_user)
        VALUES   (:NEW.contract_id,
                  :NEW.contract_title,
                  :NEW.version,
                  :NEW.platform_id,
                  :NEW.if_negotiable,
                  :NEW.status,
                  :NEW.if_attach_to_soln,
                  :NEW.comments,
                  :NEW.created_by,
                  :NEW.created_date,
                  :NEW.last_updated_by,
                  :NEW.last_updated_date,
                  :NEW.document_file_name,
                  :NEW.document_content_type,
                  :NEW.expiry_date,
                  :NEW.IS_BOBO_CONTRACT,
                  :NEW.RING_NUMBER,
                  :NEW.CONTRACT_REF_ID,
                  :NEW.html_file_file_name,
                  :NEW.html_file_content_type,
                  :NEW.is_click_through_contract,
                  v_operation,
                  SYSDATE,
                  USER);
   ELSE
      INSERT INTO aims_contracts$aud (contract_id,
                                      contract_title,
                                      version,
                                      platform_id,
                                      if_negotiable,
                                      status,
                                      if_attach_to_soln,
                                      comments,
                                      created_by,
                                      created_date,
                                      last_updated_by,
                                      last_updated_date,
                                      document_file_name,
                                      document_content_type,
                                      expiry_date,
                                      IS_BOBO_CONTRACT,
                                      RING_NUMBER,
                                      CONTRACT_REF_ID,
                                      html_file_file_name,
                                      html_file_content_type,
                                      is_click_through_contract,
                                      aud_action,
                                      aud_timestamp,
                                      aud_user)
        VALUES   (:OLD.contract_id,
                  :OLD.contract_title,
                  :OLD.version,
                  :OLD.platform_id,
                  :OLD.if_negotiable,
                  :OLD.status,
                  :OLD.if_attach_to_soln,
                  :OLD.comments,
                  :OLD.created_by,
                  :OLD.created_date,
                  :OLD.last_updated_by,
                  :OLD.last_updated_date,
                  :OLD.document_file_name,
                  :OLD.document_content_type,
                  :OLD.expiry_date,
                  :OLD.IS_BOBO_CONTRACT,
                  :OLD.RING_NUMBER,
                  :OLD.CONTRACT_REF_ID,
                  :OLD.html_file_file_name,
                  :OLD.html_file_content_type,
                  :OLD.is_click_through_contract,
                  v_operation,
                  SYSDATE,
                  USER);
   END IF;
END;
/


INSERT INTO aims_menus (menu_id,
                        menu_name,
                        menu_url,
                        image_name,
                        sort_order,
                        created_by,
                        created_date,
                        last_updated_by,
                        last_updated_date)
  VALUES   (101,
            'Manage Contacts',
            '/aims/contacts.do!?task=viewList',
            'manage_contacts',
            1350,
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO aims_sys_privileges (privilege_id,
                                 privilege_name,
                                 privilege_description,
                                 created_by,
                                 created_date,
                                 last_updated_by,
                                 last_updated_date,
                                 menu_id,
                                 sub_menu_id,
                                 privilege_key,
                                 available_to)
  VALUES   (701,
            'Manage Contacts',
            'Manage Contacts',
            'system',
            SYSDATE,
            'system',
            SYSDATE,
            101,
            NULL,
            'MANAGE_CONTACTS',
            'A')
/

INSERT INTO aims_role_privileges (role_id,
                                  privilege_id,
                                  if_select_allowed,
                                  if_create_allowed,
                                  if_update_allowed,
                                  if_delete_allowed)
  VALUES   (3,
            701,
            'Y',
            'Y',
            'Y',
            'Y')
/

UPDATE   aims_login_content lc
   SET   lc.ignored_path =
            '/loginAllianceContactUpdateAction.do,/contactsSetup.do,/contactEdit.do'
 WHERE   lc.login_content_id = 1
/

DELETE FROM   aims_role_privileges
      WHERE   privilege_id IN (167, 168)
/

DELETE FROM   aims_sys_privileges
      WHERE   privilege_id IN (168)
/

DELETE FROM   aims_sub_menus
      WHERE   sub_menu_id IN (38)
/

INSERT INTO aims_menus (menu_id,
                        menu_name,
                        menu_url,
                        image_name,
                        sort_order,
                        created_by,
                        created_date,
                        last_updated_by,
                        last_updated_date)
  VALUES   (102,
            'Click Through Contracts',
            '/aims/allianceClickThroughContracts.do',
            'click_through_contracts',
            1200,
            'system',
            SYSDATE,
            'system',
            SYSDATE)
/

INSERT INTO aims_sys_privileges (privilege_id,
                                 privilege_name,
                                 privilege_description,
                                 created_by,
                                 created_date,
                                 last_updated_by,
                                 last_updated_date,
                                 menu_id,
                                 sub_menu_id,
                                 privilege_key,
                                 available_to)
  VALUES   (702,
            'Click Through Contracts',
            'Click Through Contracts',
            'system',
            SYSDATE,
            'system',
            SYSDATE,
            102,
            NULL,
            'CLICK_THROUGH_CONTRACTS',
            'A')
/

INSERT INTO aims_role_privileges (role_id,
                                  privilege_id,
                                  if_select_allowed,
                                  if_create_allowed,
                                  if_update_allowed,
                                  if_delete_allowed)
  VALUES   (3,
            702,
            'Y',
            'Y',
            'Y',
            'Y')
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2001
 WHERE   role_name = 'Read Only for BREW'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2002
 WHERE   role_name = 'WAP Biz Dev'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2003
 WHERE   role_name = 'WAP Product Development'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2004
 WHERE   role_name = 'WAP Technology Development'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2005
 WHERE   role_name = 'Intertek'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2006
 WHERE   role_name = 'VZAppZone Admin'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2007
 WHERE   role_name = 'Dashboard Admin'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2008
 WHERE   role_name = 'Wireless Developer'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2009
 WHERE   role_name = 'BREW Admin'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2010
 WHERE   role_name = 'Legal'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2011
 WHERE   role_name = 'SUPERUSER'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2012
 WHERE   role_name = 'VZWADMIN'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2013
 WHERE   role_name = 'Primary User'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2014
 WHERE   role_name = 'WAP Admin'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2015
 WHERE   role_name = 'Enterprise Admin'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2016
 WHERE   role_name = 'Tech Team'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2017
 WHERE   role_name = 'JMA Contract Admin'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2018
 WHERE   role_name = 'Super'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2019
 WHERE   role_name = 'WAP - Read only'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2020
 WHERE   role_name = 'Evaluation'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2021
 WHERE   role_name = 'Biz Dev BREW Admin'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2022
 WHERE   role_name = 'Read Only'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2023
 WHERE   role_name = 'Rollback_DCR'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2024
 WHERE   role_name = 'BREW - Read only'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2025
 WHERE   role_name = 'Secondary User'
/

Insert into AIMS_SYS_ROLES
   (ROLE_ID, ROLE_NAME, ROLE_DESCRIPTION, ROLE_TYPE, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE,vzdn_mapping_role_id)
 Values
   (254, 'JAVA Admin', 'JAVA Admin', 'V', 'admin@vzw.com', sysdate, 'admin@vzw.com', sysdate, 2027);
/

Insert into AIMS_SYS_ROLES
   (ROLE_ID, ROLE_NAME, ROLE_DESCRIPTION, ROLE_TYPE, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE,vzdn_mapping_role_id)
 Values
   (255, 'JAVA Programming Content Manager', 'JAVA Programming Content Manager', 'V', 'admin@vzw.com', sysdate, 'admin@vzw.com', sysdate, 2028);
/

Insert into AIMS_SYS_ROLES
   (ROLE_ID, ROLE_NAME, ROLE_DESCRIPTION, ROLE_TYPE, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE,vzdn_mapping_role_id)
 Values
   (256, 'JAVA Content Standard Manager', 'JAVA Content Standard Manager', 'V', 'admin@vzw.com', sysdate, 'admin@vzw.com', sysdate, 2029);
/

Insert into AIMS_SYS_ROLES
   (ROLE_ID, ROLE_NAME, ROLE_DESCRIPTION, ROLE_TYPE, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE,vzdn_mapping_role_id)
 Values
   (257, 'JAVA Legal Manager', 'JAVA Legal Manager', 'V', 'admin@vzw.com', sysdate, 'admin@vzw.com', sysdate, 2030);
/

Insert into AIMS_SYS_ROLES
   (ROLE_ID, ROLE_NAME, ROLE_DESCRIPTION, ROLE_TYPE, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE,vzdn_mapping_role_id)
 Values
   (258, 'JAVA Tax Manager', 'JAVA Tax Manager', 'V', 'admin@vzw.com', sysdate, 'admin@vzw.com', sysdate, 2031);
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2032
 WHERE   role_name = 'Brew Evaluation'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2033
 WHERE   role_name = 'Brew Content Standard Approval'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2034
 WHERE   role_name = 'Brew Legal Approval'
/

UPDATE   aims_sys_roles
   SET   vzdn_mapping_role_id = 2035
 WHERE   role_name = 'JMA Admin'
/

INSERT INTO AIMS_PLACE_HOLDERS (PLACE_HOLDER_ID,
                                PLACE_HOLDER_DISPLAY_NAME,
                                CREATED_DATE,
                                CREATED_BY,
                                LAST_UPDATED_DATE,
                                LAST_UPDATED_BY)
  VALUES   (2001,
            'RESUBMIT_ALLIANCE_SERVICE_URL',
            SYSDATE,
            'system',
            SYSDATE,
            SYSDATE)
/
INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2100, 'ALLIANCE_SERVICE_ACCOUNT_DISABLED', 'ALLIANCE_SERVICE_ACCOUNT_DISABLED', SYSDATE,
             'system', SYSDATE, 'system'
            )
/
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2100, 2100, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2100
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2100
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2001, 2100
            )
/
INSERT INTO AIMS_PLACE_HOLDERS
   (PLACE_HOLDER_ID, PLACE_HOLDER_DISPLAY_NAME, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 VALUES
   (2002, 'ALLIANCE_SERVICE_FAILURE_CAUSE_MESSAGE', SYSDATE, 'system', SYSDATE, '30-Jun-2009')
/   
INSERT INTO AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 VALUES
   (2002, 2100)
/
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2015, 'JAVA_APP_CONTENT_STANDARD_RFI', 'JAVA_APP_CONTENT_STANDARD_RFI', sysdate, 'system', sysdate, 'system')
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values (1, 2015)
/   
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values (2, 2015)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values (6, 2015)
/
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2016, 'JAVA_APP_CONTENT_STANDARD_APPROVAL', 'JAVA_APP_CONTENT_STANDARD_APPROVAL',  sysdate, 'system', sysdate, 'system')
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values (1, 2016)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values (2, 2016)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values  (6, 2016)
/ 
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values (2001, 'JAVA_APP_WORKFLOW_STARTED', 'JAVA_APP_WORKFLOW_STARTED',  sysdate, 'system', sysdate, 'system')
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values (1, 2001)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values (2, 2001)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values  (6, 2001)
/
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values (2017, 'JAVA_APP_LEGAL_CONTENT_DENIED', 'JAVA_APP_LEGAL_CONTENT_DENIED',  sysdate, 'system', sysdate, 'system')
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values (1, 2017)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values (2, 2017)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values (6, 2017)
/
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values (2018, 'JAVA_APP_CONTENT_STANDARD_DENIED', 'JAVA_APP_CONTENT_STANDARD_DENIED',  sysdate, 'system', sysdate, 'system')
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values (1, 2018)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2018)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2018)
/   
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2019, 'JAVA_APP_REJECTED', 'JAVA_APP_REJECTED', sysdate, 'system', sysdate, 'system')
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2019)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2019)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2019)
/   
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2020, 'JAVA_APP_CONTENT_STANDARD_RESUBMIT', 'JAVA_APP_CONTENT_STANDARD_RESUBMIT',  sysdate, 'system', sysdate, 'system')
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2020)
/   
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2020)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2020)
/   
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2021, 'JAVA_APP_TAX_REVIEW_RESUBMIT', 'JAVA_APP_TAX_REVIEW_RESUBMIT',  sysdate, 'system', sysdate, 'system')
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2021)
 /
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2021)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2021)
/
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2015,2015, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2016, 2016, 'com.netpace.aims.bo.events.ApplicationEventHandler')
 /
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2001, 2001, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   ( 2017, 2017, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/    
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   ( 2018, 2018, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/ 
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   ( 2019, 2019, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   ( 2020, 2020, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   ( 2021, 2021, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/ 
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2022, 'JAVA_OFFDECK_WORKFLOW_STARTED', 'JAVA_OFFDECK_WORKFLOW_STARTED',  sysdate, 'system', sysdate, 'system')
/   
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2022)
/   
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2022)
/   
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2022)
/   
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2022,2022, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/ 
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2023, 'JAVA_OFFDECK_TAX_APPROVAL', 'JAVA_OFFDECK_TAX_APPROVAL',  sysdate, 'system', sysdate, 'system')
/   
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2023)
/   
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2023)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2023)
/
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2023,2023, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2024, 'JAVA_OFFDECK_TAX_RFI', 'JAVA_OFFDECK_TAX_RFI',  sysdate, 'system', sysdate, 'system')
/   
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2024)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2024)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2024)
/
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2024,2024, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2025, 'JAVA_OFFDECK_TAX_REJECTED', 'JAVA_OFFDECK_TAX_REJECTED',  sysdate, 'system', sysdate, 'system')
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2025)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2025)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2025)
/
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2025,2025, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/
Insert into AIMS_EVENTS
   (EVENT_ID, EVENT_NAME, EVENT_DESC, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY)
 Values
   (2026, 'JAVA_OFFDECK_TAXRFI_RESUBMITTED', 'JAVA_OFFDECK_TAXRFI_RESUBMITTED',  sysdate, 'system', sysdate, 'system')
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (1, 2026)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (2, 2026)
/
Insert into AIMS_EVENT_PLACE_HOLDERS
   (PLACE_HOLDER_ID, EVENT_ID)
 Values
   (6, 2026)
/
Insert into AIMS_EVENT_HANDLERS   (EVENT_HANDLER_ID, EVENT_ID, CLASS_NAME)
 Values   (2026,2026, 'com.netpace.aims.bo.events.ApplicationEventHandler')
 /   

Insert into AIMS_SUB_MENUS
   (SUB_MENU_ID, SUB_MENU_NAME, SUB_MENU_URL, 
   IMAGE_NAME, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE, MENU_ID)
 Values
   (2000, 'All Approved Java Applications', '/aims/approvedJavaApps.do', 
   'all_applications', 4015, 'system', sysdate, 'system', sysdate, 3)
/
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by,
             created_date, last_updated_by, last_updated_date, menu_id,
             sub_menu_id, privilege_key, available_to
            )
     VALUES (2000, 'Approved Java Applications', 'APPROVED_JAVA_APPLICATIONS', 'system',
             SYSDATE, 'system', SYSDATE, 3,
             2000, 'APPROVED_JAVA_APPLICATIONS', 'A'	
            )
/
ALTER TABLE AIMS_JAVA_APPS
 ADD CONSTRAINT FK_JAVA_CATEGORY_TYPE1 
 FOREIGN KEY (APP_CATEGORY_1_TYPE_ID) 
 REFERENCES AIMS_APP_CATEGORIES (CATEGORY_ID)
/
ALTER TABLE AIMS_JAVA_APPS
 ADD CONSTRAINT FK_JAVA_CATEGORY_TYPE2 
 FOREIGN KEY (APP_CATEGORY_2_TYPE_ID) 
 REFERENCES AIMS_APP_CATEGORIES (CATEGORY_ID)
/
ALTER TABLE AIMS_JAVA_APPS
 ADD CONSTRAINT FK_JAVA_SUB_CATEGORY_TYPE1 
 FOREIGN KEY (APP_SUB_CATEGORY_1_TYPE_ID) 
 REFERENCES AIMS_APP_SUB_CATEGORIES (SUB_CATEGORY_ID)
/
ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_SUB_CATEGORY_TYPE2 
 FOREIGN KEY (APP_SUB_CATEGORY_2_TYPE_ID) 
 REFERENCES AIMS_APP_SUB_CATEGORIES (SUB_CATEGORY_ID))
/
ALTER TABLE AIMS_APPS
	  ADD (RING_TYPE_ID  NUMBER  DEFAULT 172)
/

ALTER TABLE AIMS_APPS$AUD
	  ADD (RING_TYPE_ID  NUMBER)
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

   IF INSERTING OR UPDATING
   THEN
      INSERT INTO AIMS_APPS$AUD (APPS_ID,
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
                                 APP_REF_ID,
                                 RING_TYPE_ID,
                                 AUD_ACTION,
                                 AUD_TIMESTAMP,
                                 AUD_USER)
        VALUES   (:NEW.APPS_ID,
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
                  :NEW.APP_REF_ID,
                  :NEW.RING_TYPE_ID,
                  V_OPERATION,
                  SYSDATE,
                  USER);
   ELSE
      INSERT INTO AIMS_APPS$AUD (APPS_ID,
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
                                 APP_REF_ID,
                                 RING_TYPE_ID,
                                 AUD_ACTION,
                                 AUD_TIMESTAMP,
                                 AUD_USER)
        VALUES   (:OLD.APPS_ID,
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
                  :OLD.APP_REF_ID,
                  :OLD.RING_TYPE_ID,
                  V_OPERATION,
                  SYSDATE,
                  USER);
   END IF;
END;
/
ALTER TABLE AIMS_APPS ADD (
	CONSTRAINT FK_AIMS_APP_RING_TYPE 
 	FOREIGN KEY (RING_TYPE_ID) 
 	REFERENCES AIMS_TYPES (TYPE_ID))
/
UPDATE AIMS_APPS
	SET RING_TYPE_ID=172 
	WHERE RING_TYPE_ID IS NULL
/

DECLARE

   CURSOR BUS_FUN_TST IS
        SELECT AIMS_JAVA_APPS.RING_NUMBER,AIMS_JAVA_APPS.JAVA_APPS_ID
          FROM AIMS_JAVA_APPS,AIMS_APPS
         WHERE AIMS_APPS.APPS_ID=AIMS_JAVA_APPS.JAVA_APPS_ID;

BEGIN

  FOR A IN BUS_FUN_TST LOOP

   UPDATE AIMS_APPS
      SET RING_TYPE_ID=A.RING_NUMBER
    WHERE APPS_ID =   A.JAVA_APPS_ID;

  END LOOP;

END;
/

ALTER TABLE AIMS_JAVA_APPS 
	DROP COLUMN RING_NUMBER
/

ALTER TABLE AIMS_JAVA_APPS$AUD 
	DROP COLUMN RING_NUMBER
/
CREATE OR REPLACE TRIGGER AIMS_JAVA_APPS$audtrg
   AFTER INSERT OR DELETE OR UPDATE
   ON AIMS_JAVA_APPS
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

   IF INSERTING OR UPDATING
   THEN
      INSERT INTO AIMS_JAVA_APPS$AUD (JAVA_APPS_ID,
                                      CONTENT_RATING_TYPE_ID,
                                      INFO_URL,
                                      ENTERPRISE_APP,
                                      APP_KEYWORD,
                                      APP_CATEGORY_TYPE_ID,
                                      APP_SUB_CATEGORY_TYPE_ID,
                                      CONTRACT_ID,
                                      MKTG_RELEASE,
                                      HR_PUBLISHER_LOGO_FILE_NAME,
                                      HR_PUBLISHER_LOGO_CONTENT_TYPE,
                                      CHNL_TITLE_ICON_FILE_NAME,
                                      CHNL_TITLE_ICON_CONTENT_TYPE,
                                      SPLH_SCREEN_SHOT_FILE_NAME,
                                      SPLH_SCREEN_SHOT_CONTENT_TYPE,
                                      ACTV_SCREEN_SHOT_FILE_NAME,
                                      ACTV_SCREEN_SHOT_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_FILE_NAME,
                                      APP_SCREEN_SHOT_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_1_FILE_NAME,
                                      APP_SCREEN_SHOT_1_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_2_FILE_NAME,
                                      APP_SCREEN_SHOT_2_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_3_FILE_NAME,
                                      APP_SCREEN_SHOT_3_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_4_FILE_NAME,
                                      APP_SCREEN_SHOT_4_CONTENT_TYPE,
                                      FAQ_FILE_FILE_NAME,
                                      FAQ_FILE_CONTENT_TYPE,
                                      TECH_CONTACT_ID,
                                      TECH_CONTACT_FIRST_NAME,
                                      TECH_CONTACT_LAST_NAME,
                                      TECH_CONTACT_TITLE,
                                      TECH_CONTACT_EMAIL_ADDRESS,
                                      TECH_CONTACT_PHONE,
                                      TECH_CONTACT_MOBILE,
                                      COMPANY_LOGO_FILE_NAME,
                                      COMPANY_LOGO_CONTENT_TYPE,
                                      APPLICATION_NAME,
                                      APP_TITLE_NAME_FILE_NAME,
                                      APP_TITLE_NAME_CONTENT_TYPE,
                                      PRODUCT_DESCRIPTION,
                                      APP_SCREEN_SHOT_5_FILE_NAME,
                                      APP_SCREEN_SHOT_5_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_6_FILE_NAME,
                                      APP_SCREEN_SHOT_6_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_7_FILE_NAME,
                                      APP_SCREEN_SHOT_7_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_8_FILE_NAME,
                                      APP_SCREEN_SHOT_8_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_9_FILE_NAME,
                                      APP_SCREEN_SHOT_9_CONTENT_TYPE,
                                      APP_CATEGORY_1_TYPE_ID,
                                      APP_SUB_CATEGORY_1_TYPE_ID,
                                      APP_CATEGORY_2_TYPE_ID,
                                      APP_SUB_CATEGORY_2_TYPE_ID,
                                      CONTENT_TYPE_ID,
                                      VZW_PROJECTED_LIVE_DATE,
                                      ENTERPRISE_ID,
                                      NOTES,
                                      TAX_CATEGORY_CODE_ID,
                                      CONTRACT_REF_ID,
                                      APP_REF_ID,
                                      TITLE,
                                      SHORT_DESC,
                                      LONG_DESC,
                                      aud_action,
                                      aud_timestamp,
                                      aud_user)
        VALUES   (:NEW.JAVA_APPS_ID,
                  :NEW.CONTENT_RATING_TYPE_ID,
                  :NEW.INFO_URL,
                  :NEW.ENTERPRISE_APP,
                  :NEW.APP_KEYWORD,
                  :NEW.APP_CATEGORY_TYPE_ID,
                  :NEW.APP_SUB_CATEGORY_TYPE_ID,
                  :NEW.CONTRACT_ID,
                  :NEW.MKTG_RELEASE,
                  :NEW.HR_PUBLISHER_LOGO_FILE_NAME,
                  :NEW.HR_PUBLISHER_LOGO_CONTENT_TYPE,
                  :NEW.CHNL_TITLE_ICON_FILE_NAME,
                  :NEW.CHNL_TITLE_ICON_CONTENT_TYPE,
                  :NEW.SPLH_SCREEN_SHOT_FILE_NAME,
                  :NEW.SPLH_SCREEN_SHOT_CONTENT_TYPE,
                  :NEW.ACTV_SCREEN_SHOT_FILE_NAME,
                  :NEW.ACTV_SCREEN_SHOT_CONTENT_TYPE,
                  :NEW.APP_SCREEN_SHOT_FILE_NAME,
                  :NEW.APP_SCREEN_SHOT_CONTENT_TYPE,
                  :NEW.APP_SCREEN_SHOT_1_FILE_NAME,
                  :NEW.APP_SCREEN_SHOT_1_CONTENT_TYPE,
                  :NEW.APP_SCREEN_SHOT_2_FILE_NAME,
                  :NEW.APP_SCREEN_SHOT_2_CONTENT_TYPE,
                  :NEW.APP_SCREEN_SHOT_3_FILE_NAME,
                  :NEW.APP_SCREEN_SHOT_3_CONTENT_TYPE,
                  :NEW.APP_SCREEN_SHOT_4_FILE_NAME,
                  :NEW.APP_SCREEN_SHOT_4_CONTENT_TYPE,
                  :NEW.FAQ_FILE_FILE_NAME,
                  :NEW.FAQ_FILE_CONTENT_TYPE,
                  :NEW.TECH_CONTACT_ID,
                  :NEW.TECH_CONTACT_FIRST_NAME,
                  :NEW.TECH_CONTACT_LAST_NAME,
                  :NEW.TECH_CONTACT_TITLE,
                  :NEW.TECH_CONTACT_EMAIL_ADDRESS,
                  :NEW.TECH_CONTACT_PHONE,
                  :NEW.TECH_CONTACT_MOBILE,
                  :NEW.COMPANY_LOGO_FILE_NAME,
                  :NEW.COMPANY_LOGO_CONTENT_TYPE,
                  :NEW.APPLICATION_NAME,
                  :NEW.APP_TITLE_NAME_FILE_NAME,
                  :NEW.APP_TITLE_NAME_CONTENT_TYPE,
                  :NEW.PRODUCT_DESCRIPTION,
                  :NEW.APP_SCREEN_SHOT_5_FILE_NAME,
                  :NEW.APP_SCREEN_SHOT_5_CONTENT_TYPE,
                  :NEW.APP_SCREEN_SHOT_6_FILE_NAME,
                  :NEW.APP_SCREEN_SHOT_6_CONTENT_TYPE,
                  :NEW.APP_SCREEN_SHOT_7_FILE_NAME,
                  :NEW.APP_SCREEN_SHOT_7_CONTENT_TYPE,
                  :NEW.APP_SCREEN_SHOT_8_FILE_NAME,
                  :NEW.APP_SCREEN_SHOT_8_CONTENT_TYPE,
                  :NEW.APP_SCREEN_SHOT_9_FILE_NAME,
                  :NEW.APP_SCREEN_SHOT_9_CONTENT_TYPE,
                  :NEW.APP_CATEGORY_1_TYPE_ID,
                  :NEW.APP_SUB_CATEGORY_1_TYPE_ID,
                  :NEW.APP_CATEGORY_2_TYPE_ID,
                  :NEW.APP_SUB_CATEGORY_2_TYPE_ID,
                  :NEW.CONTENT_TYPE_ID,
                  :NEW.VZW_PROJECTED_LIVE_DATE,
                  :NEW.ENTERPRISE_ID,
                  :NEW.NOTES,
                  :NEW.TAX_CATEGORY_CODE_ID,
                  :NEW.CONTRACT_REF_ID,
                  :NEW.APP_REF_ID,
                  :NEW.TITLE,
                  :NEW.SHORT_DESC,
                  :NEW.LONG_DESC,
                  v_operation,
                  SYSDATE,
                  USER);
   ELSE
      INSERT INTO AIMS_JAVA_APPS$AUD (JAVA_APPS_ID,
                                      CONTENT_RATING_TYPE_ID,
                                      INFO_URL,
                                      ENTERPRISE_APP,
                                      APP_KEYWORD,
                                      APP_CATEGORY_TYPE_ID,
                                      APP_SUB_CATEGORY_TYPE_ID,
                                      CONTRACT_ID,
                                      MKTG_RELEASE,
                                      HR_PUBLISHER_LOGO_FILE_NAME,
                                      HR_PUBLISHER_LOGO_CONTENT_TYPE,
                                      CHNL_TITLE_ICON_FILE_NAME,
                                      CHNL_TITLE_ICON_CONTENT_TYPE,
                                      SPLH_SCREEN_SHOT_FILE_NAME,
                                      SPLH_SCREEN_SHOT_CONTENT_TYPE,
                                      ACTV_SCREEN_SHOT_FILE_NAME,
                                      ACTV_SCREEN_SHOT_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_FILE_NAME,
                                      APP_SCREEN_SHOT_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_1_FILE_NAME,
                                      APP_SCREEN_SHOT_1_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_2_FILE_NAME,
                                      APP_SCREEN_SHOT_2_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_3_FILE_NAME,
                                      APP_SCREEN_SHOT_3_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_4_FILE_NAME,
                                      APP_SCREEN_SHOT_4_CONTENT_TYPE,
                                      FAQ_FILE_FILE_NAME,
                                      FAQ_FILE_CONTENT_TYPE,
                                      TECH_CONTACT_ID,
                                      TECH_CONTACT_FIRST_NAME,
                                      TECH_CONTACT_LAST_NAME,
                                      TECH_CONTACT_TITLE,
                                      TECH_CONTACT_EMAIL_ADDRESS,
                                      TECH_CONTACT_PHONE,
                                      TECH_CONTACT_MOBILE,
                                      COMPANY_LOGO_FILE_NAME,
                                      COMPANY_LOGO_CONTENT_TYPE,
                                      APPLICATION_NAME,
                                      APP_TITLE_NAME_FILE_NAME,
                                      APP_TITLE_NAME_CONTENT_TYPE,
                                      PRODUCT_DESCRIPTION,
                                      APP_SCREEN_SHOT_5_FILE_NAME,
                                      APP_SCREEN_SHOT_5_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_6_FILE_NAME,
                                      APP_SCREEN_SHOT_6_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_7_FILE_NAME,
                                      APP_SCREEN_SHOT_7_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_8_FILE_NAME,
                                      APP_SCREEN_SHOT_8_CONTENT_TYPE,
                                      APP_SCREEN_SHOT_9_FILE_NAME,
                                      APP_SCREEN_SHOT_9_CONTENT_TYPE,
                                      APP_CATEGORY_1_TYPE_ID,
                                      APP_SUB_CATEGORY_1_TYPE_ID,
                                      APP_CATEGORY_2_TYPE_ID,
                                      APP_SUB_CATEGORY_2_TYPE_ID,
                                      CONTENT_TYPE_ID,
                                      VZW_PROJECTED_LIVE_DATE,
                                      ENTERPRISE_ID,
                                      NOTES,
                                      TAX_CATEGORY_CODE_ID,
                                      CONTRACT_REF_ID,
                                      APP_REF_ID,
                                      TITLE,
                                      SHORT_DESC,
                                      LONG_DESC,
                                      aud_action,
                                      aud_timestamp,
                                      aud_user)
        VALUES   (:OLD.JAVA_APPS_ID,
                  :OLD.CONTENT_RATING_TYPE_ID,
                  :OLD.INFO_URL,
                  :OLD.ENTERPRISE_APP,
                  :OLD.APP_KEYWORD,
                  :OLD.APP_CATEGORY_TYPE_ID,
                  :OLD.APP_SUB_CATEGORY_TYPE_ID,
                  :OLD.CONTRACT_ID,
                  :OLD.MKTG_RELEASE,
                  :OLD.HR_PUBLISHER_LOGO_FILE_NAME,
                  :OLD.HR_PUBLISHER_LOGO_CONTENT_TYPE,
                  :OLD.CHNL_TITLE_ICON_FILE_NAME,
                  :OLD.CHNL_TITLE_ICON_CONTENT_TYPE,
                  :OLD.SPLH_SCREEN_SHOT_FILE_NAME,
                  :OLD.SPLH_SCREEN_SHOT_CONTENT_TYPE,
                  :OLD.ACTV_SCREEN_SHOT_FILE_NAME,
                  :OLD.ACTV_SCREEN_SHOT_CONTENT_TYPE,
                  :OLD.APP_SCREEN_SHOT_FILE_NAME,
                  :OLD.APP_SCREEN_SHOT_CONTENT_TYPE,
                  :OLD.APP_SCREEN_SHOT_1_FILE_NAME,
                  :OLD.APP_SCREEN_SHOT_1_CONTENT_TYPE,
                  :OLD.APP_SCREEN_SHOT_2_FILE_NAME,
                  :OLD.APP_SCREEN_SHOT_2_CONTENT_TYPE,
                  :OLD.APP_SCREEN_SHOT_3_FILE_NAME,
                  :OLD.APP_SCREEN_SHOT_3_CONTENT_TYPE,
                  :OLD.APP_SCREEN_SHOT_4_FILE_NAME,
                  :OLD.APP_SCREEN_SHOT_4_CONTENT_TYPE,
                  :OLD.FAQ_FILE_FILE_NAME,
                  :OLD.FAQ_FILE_CONTENT_TYPE,
                  :OLD.TECH_CONTACT_ID,
                  :OLD.TECH_CONTACT_FIRST_NAME,
                  :OLD.TECH_CONTACT_LAST_NAME,
                  :OLD.TECH_CONTACT_TITLE,
                  :OLD.TECH_CONTACT_EMAIL_ADDRESS,
                  :OLD.TECH_CONTACT_PHONE,
                  :OLD.TECH_CONTACT_MOBILE,
                  :OLD.COMPANY_LOGO_FILE_NAME,
                  :OLD.COMPANY_LOGO_CONTENT_TYPE,
                  :OLD.APPLICATION_NAME,
                  :OLD.APP_TITLE_NAME_FILE_NAME,
                  :OLD.APP_TITLE_NAME_CONTENT_TYPE,
                  :OLD.PRODUCT_DESCRIPTION,
                  :OLD.APP_SCREEN_SHOT_5_FILE_NAME,
                  :OLD.APP_SCREEN_SHOT_5_CONTENT_TYPE,
                  :OLD.APP_SCREEN_SHOT_6_FILE_NAME,
                  :OLD.APP_SCREEN_SHOT_6_CONTENT_TYPE,
                  :OLD.APP_SCREEN_SHOT_7_FILE_NAME,
                  :OLD.APP_SCREEN_SHOT_7_CONTENT_TYPE,
                  :OLD.APP_SCREEN_SHOT_8_FILE_NAME,
                  :OLD.APP_SCREEN_SHOT_8_CONTENT_TYPE,
                  :OLD.APP_SCREEN_SHOT_9_FILE_NAME,
                  :OLD.APP_SCREEN_SHOT_9_CONTENT_TYPE,
                  :OLD.APP_CATEGORY_1_TYPE_ID,
                  :OLD.APP_SUB_CATEGORY_1_TYPE_ID,
                  :OLD.APP_CATEGORY_2_TYPE_ID,
                  :OLD.APP_SUB_CATEGORY_2_TYPE_ID,
                  :OLD.CONTENT_TYPE_ID,
                  :OLD.VZW_PROJECTED_LIVE_DATE,
                  :OLD.ENTERPRISE_ID,
                  :OLD.NOTES,
                  :OLD.TAX_CATEGORY_CODE_ID,
                  :OLD.CONTRACT_REF_ID,
                  :OLD.APP_REF_ID,
                  :OLD.TITLE,
                  :OLD.SHORT_DESC,
                  :OLD.LONG_DESC,
                  v_operation,
                  SYSDATE,
                  USER);
   END IF;
END;
/


UPDATE aims_sys_roles sysrole
   SET sysrole.role_description =
              'Privileges include - Alliance Information, Manage Applications'
 WHERE sysrole.role_id = 131
/
DELETE FROM aims_role_privileges
      WHERE privilege_id = 82
/
DELETE FROM aims_sys_privileges
      WHERE privilege_id = 82
/
DELETE FROM aims_sub_menus
      WHERE sub_menu_id = 2
/


delete from AIMS_EVENT_HANDLERS aeh where aeh.EVENT_ID in (2022, 2023, 2024, 2025, 2026)
/
delete from AIMS_EVENT_PLACE_HOLDERS aeph where aeph.EVENT_ID in (2022, 2023, 2024, 2025, 2026)
/
delete from AIMS_NOTIF_AD_HOC_RECIPIENTS anahr where anahr.NOTIFICATION_ID in (select aen.NOTIFICATION_ID 
	from AIMS_EVENT_NOTIFICATIONS aen where aen.EVENT_ID in (2022, 2023, 2024, 2025, 2026))
/
delete from AIMS_EVENT_NOTIFICATIONS aen where aen.EVENT_ID in (2022, 2023, 2024, 2025, 2026)
/
delete from AIMS_EVENTS ae where ae.EVENT_ID in (2022, 2023, 2024, 2025, 2026)
/
delete from AIMS_EVENT_HANDLERS aeh where aeh.EVENT_ID in (2014)
/
delete from AIMS_EVENT_PLACE_HOLDERS aeph where aeph.EVENT_ID in (2014)
/
delete from AIMS_NOTIF_AD_HOC_RECIPIENTS anahr where anahr.NOTIFICATION_ID in (select aen.NOTIFICATION_ID 
	from AIMS_EVENT_NOTIFICATIONS aen where aen.EVENT_ID in (2014))
/
delete from AIMS_EVENT_NOTIFICATIONS aen where aen.EVENT_ID in (2014)
/
delete from AIMS_EVENTS ae where ae.EVENT_ID in (2014)
/

delete from AIMS_ROLE_PRIVILEGES arp where arp.PRIVILEGE_ID in (2001,2002)
/
delete from AIMS_SYS_PRIVILEGES asp where asp.PRIVILEGE_ID in (2001,2002)
/
Insert into AIMS_SYS_PRIVILEGES
   (PRIVILEGE_ID, PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE, PRIVILEGE_KEY, AVAILABLE_TO)
 Values
   (2001, 'Manage Java Apps', 'Manage Java Apps', 'system', sysdate, 'system', sysdate, 'MANAGE_APP_JAVA', 'B')
/
 
set escape off