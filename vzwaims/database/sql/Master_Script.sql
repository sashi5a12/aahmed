------------------------------------------------------------------------
--create sequence for app ref id  and contact ref id
------------------------------------------------------------------------
DROP SEQUENCE SEQ_APP_REF_ID;

CREATE SEQUENCE SEQ_APP_REF_ID
MINVALUE 5000000
MAXVALUE 999999999999999999999999999
START WITH 5000000
INCREMENT BY 1
CACHE 20;

DROP SEQUENCE SEQ_CONTRACT_REF_ID;

CREATE SEQUENCE SEQ_CONTRACT_REF_ID
MINVALUE 6000000
MAXVALUE 999999999999999999999999999
START WITH 6000000
INCREMENT BY 1
CACHE 20;

------------------------------------------------------------------------
-- Add columns to AIMS_CONTRACTS, AIMS_APPS, AIMS_WORKITEM
------------------------------------------------------------------------
ALTER TABLE AIMS_CONTRACTS
ADD ( RING_NUMBER NUMBER );

ALTER TABLE AIMS_CONTRACTS
ADD( CONTRACT_REF_ID NUMBER);

ALTER TABLE  AIMS_APPS
ADD (APP_REF_ID NUMBER);

ALTER TABLE AIMS_WORKITEM
ADD (ACTION_PAGE_URL VARCHAR2(500));

------------------------------------------------------------------------
--create Java apps table
------------------------------------------------------------------------
DROP TABLE AIMS_JAVA_APPS
;

CREATE TABLE AIMS_JAVA_APPS
( JAVA_APPS_ID               		NUMBER
, RING_NUMBER						NUMBER
, CONTENT_RATING_TYPE_ID   			NUMBER
, INFO_URL							VARCHAR2(150)
, ENTERPRISE_APP					VARCHAR2(1)
, APP_KEYWORD						VARCHAR2(50)
, APP_CATEGORY_TYPE_ID  			NUMBER
, APP_SUB_CATEGORY_TYPE_ID			NUMBER
, CONTRACT_ID						NUMBER
, MKTG_RELEASE						VARCHAR2(1)
, HR_PUBLISHER_LOGO         		BLOB
, HR_PUBLISHER_LOGO_FILE_NAME       VARCHAR2(150)
, HR_PUBLISHER_LOGO_CONTENT_TYPE	VARCHAR2(100)
, CHNL_TITLE_ICON                 	BLOB
, CHNL_TITLE_ICON_FILE_NAME       	VARCHAR2(150)
, CHNL_TITLE_ICON_CONTENT_TYPE    	VARCHAR2(100)
, SPLH_SCREEN_SHOT            		BLOB
, SPLH_SCREEN_SHOT_FILE_NAME      	VARCHAR2(150)
, SPLH_SCREEN_SHOT_CONTENT_TYPE     VARCHAR2(100)  
, ACTV_SCREEN_SHOT            		BLOB
, ACTV_SCREEN_SHOT_FILE_NAME        VARCHAR2(150)
, ACTV_SCREEN_SHOT_CONTENT_TYPE     VARCHAR2(100)  
, APP_SCREEN_SHOT            		BLOB
, APP_SCREEN_SHOT_FILE_NAME         VARCHAR2(150)
, APP_SCREEN_SHOT_CONTENT_TYPE      VARCHAR2(100)  
, APP_SCREEN_SHOT_1            		BLOB
, APP_SCREEN_SHOT_1_FILE_NAME       VARCHAR2(150)
, APP_SCREEN_SHOT_1_CONTENT_TYPE    VARCHAR2(100)  
, APP_SCREEN_SHOT_2            		BLOB
, APP_SCREEN_SHOT_2_FILE_NAME       VARCHAR2(150)
, APP_SCREEN_SHOT_2_CONTENT_TYPE    VARCHAR2(100)  
, APP_SCREEN_SHOT_3            	  	BLOB
, APP_SCREEN_SHOT_3_FILE_NAME       VARCHAR2(150)
, APP_SCREEN_SHOT_3_CONTENT_TYPE    VARCHAR2(100)  
, APP_SCREEN_SHOT_4            		BLOB
, APP_SCREEN_SHOT_4_FILE_NAME       VARCHAR2(150)
, APP_SCREEN_SHOT_4_CONTENT_TYPE    VARCHAR2(100)  
, FAQ_FILE		            		BLOB
, FAQ_FILE_FILE_NAME          		VARCHAR2(150)
, FAQ_FILE_CONTENT_TYPE       		VARCHAR2(100)  
, TECH_CONTACT_ID					NUMBER       
, TECH_CONTACT_FIRST_NAME       	VARCHAR2(100)
, TECH_CONTACT_LAST_NAME          	VARCHAR2(200)
, TECH_CONTACT_TITLE              	VARCHAR2(200)
, TECH_CONTACT_EMAIL_ADDRESS      	VARCHAR2(200)
, TECH_CONTACT_PHONE              	VARCHAR2(50)
, TECH_CONTACT_MOBILE             	VARCHAR2(50)
, COMPANY_LOGO            			BLOB
, COMPANY_LOGO_FILE_NAME    		VARCHAR2(150)
, COMPANY_LOGO_CONTENT_TYPE    		VARCHAR2(100)
, APPLICATION_NAME					VARCHAR2(200)
, APP_TITLE_NAME                	BLOB
, APP_TITLE_NAME_FILE_NAME      	VARCHAR2(150)
, APP_TITLE_NAME_CONTENT_TYPE   	VARCHAR2(100)
, PRODUCT_DESCRIPTION				VARCHAR2(1000)
, APP_SCREEN_SHOT_5            		BLOB
, APP_SCREEN_SHOT_5_FILE_NAME       VARCHAR2(150)
, APP_SCREEN_SHOT_5_CONTENT_TYPE    VARCHAR2(100)  
, APP_SCREEN_SHOT_6            		BLOB
, APP_SCREEN_SHOT_6_FILE_NAME       VARCHAR2(150)
, APP_SCREEN_SHOT_6_CONTENT_TYPE    VARCHAR2(100)  
, APP_SCREEN_SHOT_7            		BLOB
, APP_SCREEN_SHOT_7_FILE_NAME       VARCHAR2(150)
, APP_SCREEN_SHOT_7_CONTENT_TYPE    VARCHAR2(100)  
, APP_SCREEN_SHOT_8            		BLOB
, APP_SCREEN_SHOT_8_FILE_NAME       VARCHAR2(150)
, APP_SCREEN_SHOT_8_CONTENT_TYPE    VARCHAR2(100)  
, APP_SCREEN_SHOT_9            		BLOB
, APP_SCREEN_SHOT_9_FILE_NAME       VARCHAR2(150)
, APP_SCREEN_SHOT_9_CONTENT_TYPE    VARCHAR2(100)  
, USING_APPLICATION               	CLOB
, TIPS_AND_TRICKS                 	CLOB
, FAQ							  	CLOB
, TROUBLESHOOTING                 	CLOB
, DEVELOPMENT_COMPANY_DISCLAIMER  	CLOB
, ADDITIONAL_INFORMATION          	CLOB
, APP_CATEGORY_1_TYPE_ID  			NUMBER
, APP_SUB_CATEGORY_1_TYPE_ID		NUMBER
, APP_CATEGORY_2_TYPE_ID  			NUMBER
, APP_SUB_CATEGORY_2_TYPE_ID		NUMBER
, CONTENT_TYPE_ID					NUMBER					
, VZW_PROJECTED_LIVE_DATE			DATE
, ENTERPRISE_ID						VARCHAR2(100)
, NOTES								VARCHAR2(1000)
, TAX_CATEGORY_CODE_ID				NUMBER
, CONTRACT_REF_ID 					NUMBER
, APP_REF_ID 						NUMBER
);

ALTER TABLE AIMS_JAVA_APPS ADD (
  PRIMARY KEY
 (JAVA_APPS_ID)  
);

ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_APPS_1 
 FOREIGN KEY (JAVA_APPS_ID) 
 REFERENCES AIMS_APPS (APPS_ID)
);

ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_CONTENT_RATING 
 FOREIGN KEY (CONTENT_RATING_TYPE_ID) 
 REFERENCES AIMS_TYPES (TYPE_ID)
);
 
--ALTER TABLE AIMS_JAVA_APPS ADD (
--  CONSTRAINT UNIQUE_JAVA_APP_KEYWORD
-- UNIQUE (APP_KEYWORD) 
--);
 
--ALTER TABLE AIMS_JAVA_APPS ADD (
--  CONSTRAINT UNIQUE_JAVA_ENTERPRISE_ID
-- UNIQUE (ENTERPRISE_ID) 
--);

ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_CATEGORY_TYPE
 FOREIGN KEY (APP_CATEGORY_TYPE_ID) 
 REFERENCES AIMS_TYPES (TYPE_ID)
);

ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_SUB_CATEGORY_TYPE 
 FOREIGN KEY (APP_SUB_CATEGORY_TYPE_ID) 
 REFERENCES AIMS_TYPES (TYPE_ID)
);
 
ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_CATEGORY_TYPE1
 FOREIGN KEY (APP_CATEGORY_1_TYPE_ID) 
 REFERENCES AIMS_TYPES (TYPE_ID)
);

ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_SUB_CATEGORY_TYPE1 
 FOREIGN KEY (APP_SUB_CATEGORY_1_TYPE_ID) 
 REFERENCES AIMS_TYPES (TYPE_ID)
);
 
ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_CATEGORY_TYPE2
 FOREIGN KEY (APP_CATEGORY_2_TYPE_ID) 
 REFERENCES AIMS_TYPES (TYPE_ID)
);

ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_SUB_CATEGORY_TYPE2 
 FOREIGN KEY (APP_SUB_CATEGORY_2_TYPE_ID) 
 REFERENCES AIMS_TYPES (TYPE_ID)
);
 
ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_TAX_CATEGORY 
 FOREIGN KEY (TAX_CATEGORY_CODE_ID) 
 REFERENCES AIMS_TAX_CATEGORY_CODES (TAX_CATEGORY_CODE_ID)
);
 
ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_CONTENT_TYPE 
 FOREIGN KEY (CONTENT_TYPE_ID) 
 REFERENCES AIMS_TYPES (TYPE_ID)
);

ALTER TABLE AIMS_JAVA_APPS ADD (
  CONSTRAINT FK_JAVA_CONTRACT
 FOREIGN KEY (CONTRACT_ID) 
 REFERENCES AIMS_CONTRACTS (CONTRACT_ID)
);

------------------------------------------------------------------------
-- Create Index for Java Apps
------------------------------------------------------------------------
DROP INDEX FK_IND_AIMS_JAVA_APPS_CNT_RAT;
DROP INDEX FK_IND_AIMS_JAVA_APPS_CAT;
DROP INDEX FK_IND_AIMS_JAVA_APPS_SUB_CAT;
DROP INDEX FK_IND_AIMS_JAVA_APPS_CAT1;
DROP INDEX FK_IND_AIMS_JAVA_APPS_SUB_CAT1;
DROP INDEX FK_IND_AIMS_JAVA_APPS_CAT2;
DROP INDEX FK_IND_AIMS_JAVA_APPS_SUB_CAT2;
DROP INDEX FK_IND_AIMS_JAVA_APPS_TAX_CAT;
DROP INDEX FK_IND_AIMS_JAVA_APPS_CNT_TYPE;
DROP INDEX FK_IND_AIMS_JAVA_APPS_CNRT_ID;

CREATE INDEX FK_IND_AIMS_JAVA_APPS_CNT_RAT ON AIMS_JAVA_APPS (CONTENT_RATING_TYPE_ID);
CREATE INDEX FK_IND_AIMS_JAVA_APPS_CAT ON AIMS_JAVA_APPS (APP_CATEGORY_TYPE_ID);
CREATE INDEX FK_IND_AIMS_JAVA_APPS_SUB_CAT ON AIMS_JAVA_APPS (APP_SUB_CATEGORY_TYPE_ID);
CREATE INDEX FK_IND_AIMS_JAVA_APPS_CAT1 ON AIMS_JAVA_APPS (APP_CATEGORY_1_TYPE_ID);
CREATE INDEX FK_IND_AIMS_JAVA_APPS_SUB_CAT1 ON AIMS_JAVA_APPS (APP_SUB_CATEGORY_1_TYPE_ID);
CREATE INDEX FK_IND_AIMS_JAVA_APPS_CAT2 ON AIMS_JAVA_APPS (APP_CATEGORY_2_TYPE_ID);
CREATE INDEX FK_IND_AIMS_JAVA_APPS_SUB_CAT2 ON AIMS_JAVA_APPS (APP_SUB_CATEGORY_2_TYPE_ID);
CREATE INDEX FK_IND_AIMS_JAVA_APPS_TAX_CAT ON AIMS_JAVA_APPS (TAX_CATEGORY_CODE_ID);
CREATE INDEX FK_IND_AIMS_JAVA_APPS_CNT_TYPE ON AIMS_JAVA_APPS (CONTENT_TYPE_ID);
CREATE INDEX FK_IND_AIMS_JAVA_APPS_CNRT_ID ON AIMS_JAVA_APPS (CONTRACT_ID);




------------------------------------------------------------------------
-- add Java platform
------------------------------------------------------------------------
INSERT INTO AIMS_PLATFORMS
   (PLATFORM_ID, PLATFORM_NAME, PLATFORM_DESC, CREATED_BY, CREATED_DATE, 
    LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (44, 'Java', 'Java', 'system', SYSDATE, 
    'system', SYSDATE);

------------------------------------------------------------------------
--Add TYPE DEF for JAVA APPS
------------------------------------------------------------------------
INSERT INTO AIMS_TYPE_DEFS (TYPE_DEF_ID, TYPE_NAME, TYPE_DESCRIPTION, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
VALUES (9, 'JAVA_CONTENT_RATING', 'Content Rating for Java Apps', 'system', SYSDATE, 'system', SYSDATE );

INSERT INTO AIMS_TYPE_DEFS (TYPE_DEF_ID, TYPE_NAME, TYPE_DESCRIPTION, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
VALUES (10, 'JAVA_CONTENT_TYPE', 'Content Type for Java Apps', 'system', SYSDATE, 'system', SYSDATE );

Insert into AIMS_TYPE_DEFS
   (TYPE_DEF_ID, TYPE_NAME, TYPE_DESCRIPTION, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (20, 'CONTRACT_RING_NUMBER', 'Contract Ring Number', 'system', sysdate, 'system', sysdate);

------------------------------------------------------------------------
--Add Java Ring 2 and 3
------------------------------------------------------------------------
Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (172, 20, 'Ring-2', 'Ring-2', 20, 'system', sysdate, 'system', sysdate);
Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (173, 20, 'Ring-3', 'Ring-3', 30, 'system', sysdate, 'system', sysdate);

------------------------------------------------------------------------
--Add Content Rating for JAVA APPS
------------------------------------------------------------------------
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (201, 9, 'Verizon - C7+ (Children)', 'Verizon - C7+ (Children)', 1, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (202, 9, 'Verizon - T13+ (Teen)', 'Verizon - T13+ (Teen)', 2, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (203, 9, 'Verizon - YA17+ (Young Adult)', 'Verizon - YA17+ (Young Adult)', 3, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (204, 9, 'ESRB - EC (Early Childhood)', 'ESRB - EC (Early Childhood)', 4, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (205, 9, 'ESRB - E (Everyone)', 'ESRB - E (Everyone)', 5, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (206, 9, 'ESRB - E10+ (Everyone 10+)', 'ESRB - E10+ (Everyone 10+)', 6, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (207, 9, 'ESRB - T (Teen 13+)', 'ESRB - T (Teen 13+)', 7, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (208, 9, 'ESRB - M (Mature 17+)', 'ESRB - M (Mature 17+)', 8, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (209, 9, 'MPAA - G', 'MPAA - G', 9, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (210, 9, 'MPAA - PG', 'MPAA - PG', 10, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (211, 9, 'MPAA - PG13', 'MPAA - PG13', 11, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (212, 9, 'MPAA - R', 'MPAA - R', 12, 'system', SYSDATE, 'system', SYSDATE);
   
------------------------------------------------------------------------
--Add Content Type for JAVA APPS  
------------------------------------------------------------------------
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (301, 10, 'APPLICATION', 'APPLICATION', 1, 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (302, 10, 'GAME', 'GAME', 2, 'system', SYSDATE, 'system', SYSDATE);
    
------------------------------------------------------------------------
-- Add Java Categories
------------------------------------------------------------------------
INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (161, 'Games', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (162, 'Entertainment', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (163, 'News', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (164, 'Sports', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (165, 'Money / Trading', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (166, 'Directories', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (167, 'Shopping', '', 44 , 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (168, 'Travel', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (169, 'Other', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (170, 'Weather', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_CATEGORIES (CATEGORY_ID, CATEGORY_NAME, CATEGORY_DESC, PLATFORM_ID, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (171, 'local Info', '', 44, 'system', SYSDATE, SYSDATE, SYSDATE);

------------------------------------------------------------------------
-- Add Java Sub-Categories
------------------------------------------------------------------------
INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (271,  'Music',  'Music', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (272,  'TV',  'TV', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (273,  'Travel',  'Travel', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 VALUES
   (274,  'Fun',  'Fun', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (275,  'Games',  'Games', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (276,  'Astrology',  'Astrology', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (277,  'Movies',  'Movies', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (278,  'Health',  'Health', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (279,  'Fashion',  'Fashion', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (280,  'Gossip',  'Gossip', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (281,  'For Him',  'For Him', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (282,  'Home and Family',  'Home and Family', 161, 'system', SYSDATE, SYSDATE, SYSDATE);

INSERT INTO AIMS_APP_SUB_CATEGORIES
   (SUB_CATEGORY_ID, SUB_CATEGORY_NAME, SUB_CATEGORY_DESC, CATEGORY_ID, CREATED_BY, 
    CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) VALUES
   (283,  'Teens and Tweens',  'Teens and Tweens', 161, 'system', SYSDATE, SYSDATE, SYSDATE);
   
--ADD MORE SUB-CAT ?????   

------------------------------------------------------------------------
-- ADD JAVA WORKFLOWS
------------------------------------------------------------------------
INSERT INTO AIMS_WORKFLOW_DETAILS (WORKFLOW_DETAIL_ID, WORKFLOW_TYPE, MODULE_TYPE, MODULE_TYPE_ID, WORKFLOW_NAME, WORKFLOW_XML_NAME, WORKFLOW_DISPLAY_NAME, DESCRIPTION, CREATED_BY, CREATED_DATE) VALUES (8, 40, 41, 8, 'workflow_jondapp', 'workflow_java_ondeck_app.xml', 'Java Ondeck Workflow', 'Java ondeck workflow', 'system', SYSDATE);
INSERT INTO AIMS_WORKFLOW_DETAILS (WORKFLOW_DETAIL_ID, WORKFLOW_TYPE, MODULE_TYPE, MODULE_TYPE_ID, WORKFLOW_NAME, WORKFLOW_XML_NAME, WORKFLOW_DISPLAY_NAME, DESCRIPTION, CREATED_BY, CREATED_DATE) VALUES (10, 40, 41, 10, 'workflow_jofdapp', 'workflow_java_offdeck_app.xml', 'Java Offdeck Workflow', 'Java offdeck workflow', 'system', SYSDATE);


------------------------------------------------------------------------
-- ADD JAVA SYS PRIVILEGES
------------------------------------------------------------------------
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id, sub_menu_id, privilege_key, available_to  )
     VALUES (801, 'Java App Programming Content Manager', 'Java App Programming Content Manager', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'JAVA_APP_PROGRAMMING_CONTENT_MANAGER', 'V' )
;         
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id, sub_menu_id, privilege_key, available_to     )
     VALUES (803, 'Java App Legal Manager', 'Java App Legal Manager', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'JAVA_APP_LEGAL_MANAGER', 'V'  )
;            
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id, sub_menu_id, privilege_key, available_to  )
     VALUES (804, 'Java App Content Standard Manager', 'Java App Content Standard Manager', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'JAVA_APP_CONTENT_STANDARD_MANAGER', 'V'  )
;            
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id,sub_menu_id, privilege_key, available_to  )
     VALUES (805, 'Java App Tax Manager', 'Java App Tax Manager', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'JAVA_APP_TAX_MANAGER', 'V'  )
;            
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id,sub_menu_id, privilege_key, available_to  )
     VALUES (2001, 'Manage App Javaondeck', 'Manage App Javaondeck', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'MANAGE_APP_JAVAONDECK', 'B'  )
;            
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id,sub_menu_id, privilege_key, available_to  )
     VALUES (2002, 'Manage App Javaoffdeck', 'Manage App Javaoffdeck', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'MANAGE_APP_JAVAFFDECK', 'B'  )
;            
            

------------------------------------------------------------------------
--Add Java lifecycle phases
------------------------------------------------------------------------
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2001, 'SUBMITTED', 'Submission Phase for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2002, 'RFI-CONTENT PROG', 'RFI-CONTENT PROG  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2003, 'CONTENT APPROVED', 'CONTENT APPROVED  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2004, 'RFI-LEGAL/CONTENT', 'RFI-LEGAL/CONTENT  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2005, 'LEGAL APPROVED', 'LEGAL APPROVED  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2006, 'RFI-TAX REVIEW', 'RFI-TAX REVIEW  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2007, 'PENDING TAX APPROVAL', 'PENDING TAX APPROVAL  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2008, 'REJECTED', 'Rejected  for java application', 'system', SYSDATE, 'system', SYSDATE);
INSERT INTO AIMS_LIFECYCLE_PHASES (PHASE_ID, PHASE_NAME, PHASE_DESC, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) 
 VALUES (2009, 'APPROVED', 'APPROVED  java application', 'system', SYSDATE, 'system', SYSDATE);
   
 
------------------------------------------------------------------------
-- EVENT GENERATION
------------------------------------------------------------------------
INSERT INTO aims_events
	( event_id, event_name, event_desc, created_date, created_by, last_updated_date, last_updated_by )
	VALUES (2002, 'JAVA_APP_STATUS_CHANGE', 'JAVA_APP_STATUS_CHANGE', SYSDATE, 'system', SYSDATE, 'system'  )
;
INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name )
     VALUES (2002, 2002, 'com.netpace.aims.bo.events.ApplicationEventHandler'  )
;
INSERT INTO aims_event_place_holders (place_holder_id, event_id )
     VALUES (1, 2002 )
;
INSERT INTO aims_event_place_holders (place_holder_id, event_id )
     VALUES (2, 2002 )
;
INSERT INTO aims_event_place_holders (place_holder_id, event_id )
     VALUES (6, 2002 )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2003, 'JAVA_APP_APPROVAL', 'JAVA_APP_APPROVAL', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2003, 2003, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2003
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2003
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2003
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2004, 'JAVA_APP_PROGRAMMING_CONTENT_APPROVAL', 'JAVA_APP_PROGRAMMING_CONTENT_APPROVAL', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2004, 2004, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2004
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2004
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2004
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2005, 'JAVA_APP_PROGRAMMING_CONTENT_RFI', 'JAVA_APP_PROGRAMMING_CONTENT_RFI', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2005, 2005, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2005
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2005
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2005
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2006, 'JAVA_APP_PROGRAMMING_CONTENT_DENY', 'JAVA_APP_PROGRAMMING_CONTENT_DENY', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2006, 2006, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2006
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2006
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2006
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2007, 'JAVA_APP_LEGAL_CONTENT_APPROVAL', 'JAVA_APP_LEGAL_CONTENT_APPROVAL', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2007, 2007, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2007
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2007
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2007
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2008, 'JAVA_APP_LEGAL_CONTENT_DENY', 'JAVA_APP_LEGAL_CONTENT_DENY', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2008, 2008, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2008
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2008
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2008
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2009, 'JAVA_APP_LEGAL_CONTENT_RFI', 'JAVA_APP_LEGAL_CONTENT_RFI', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2009, 2009, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2009
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2009
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2009
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2010, 'JAVA_APP_TAX_APPROVAL_PENDING', 'JAVA_APP_TAX_APPROVAL_PENDING', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2010, 2010, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2010
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2010
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2010
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2011, 'JAVA_APP_TAX_APPROVAL_RFI', 'JAVA_APP_TAX_APPROVAL_RFI', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2011, 2011, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2011
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2011
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2011
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2012, 'JAVA_APP_PROGRAMMING_CONTENT_RESUBMIT', 'JAVA_APP_PROGRAMMING_CONTENT_RESUBMIT', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2012, 2012, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2012
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2012
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2012
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2013, 'JAVA_APP_LEGAL_CONTENT_RESUBMIT', 'JAVA_APP_LEGAL_CONTENT_RESUBMIT', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2013, 2013, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2013
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2013
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2013
            )
;

INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (2014, 'JAVA_APP_TAX_RESUBMIT', 'JAVA_APP_TAX_RESUBMIT', SYSDATE,
             'system', SYSDATE, 'system'
            )
;
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (2014, 2014, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (1, 2014
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 2014
            )
;
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2014
            )
;


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- MEMU and PRIVILLEGE FOR ALL APPROVED JAVA APPS
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Insert into AIMS_SUB_MENUS
   (SUB_MENU_ID, SUB_MENU_NAME, SUB_MENU_URL, IMAGE_NAME, 
   SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE, MENU_ID ) 
 Values
   (2000, 'All Approved Java Applications', '/aims/approvedJavaApps.do', 'all_applications', 
   4015, 'system', sysdate, 'system', sysdate, 3)
;
  
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by,
             created_date, last_updated_by, last_updated_date, menu_id,
             sub_menu_id, privilege_key, available_to       )
     VALUES (2000, 'Approved Java Applications', 'APPROVED_JAVA_APPLICATIONS', 'system',
             SYSDATE, 'system', SYSDATE, 3,
             2000, 'APPROVED_JAVA_APPLICATIONS', 'A'   )
;
			
Insert into AIMS_ROLE_PRIVILEGES
   (ROLE_ID, PRIVILEGE_ID, IF_SELECT_ALLOWED, IF_CREATE_ALLOWED, IF_UPDATE_ALLOWED, IF_DELETE_ALLOWED)
 Values
   (3, 2000, 'Y', 'Y', 'Y', 'Y');
;   


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ASSIGNING PRIVILEGES TO ROLES
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




Insert into AIMS_ROLE_PRIVILEGES
   (ROLE_ID, PRIVILEGE_ID, IF_SELECT_ALLOWED, IF_CREATE_ALLOWED, IF_UPDATE_ALLOWED, IF_DELETE_ALLOWED)
 Values
   (3, 2001, 'Y', 'Y', 'Y', 'Y');
Insert into AIMS_ROLE_PRIVILEGES
   (ROLE_ID, PRIVILEGE_ID, IF_SELECT_ALLOWED, IF_CREATE_ALLOWED, IF_UPDATE_ALLOWED)
 Values
   (3, 2002, 'Y', 'Y', 'Y');
   
   
   
Insert into AIMS_ROLE_PRIVILEGES
   (ROLE_ID, PRIVILEGE_ID, IF_SELECT_ALLOWED, IF_CREATE_ALLOWED, IF_UPDATE_ALLOWED, IF_DELETE_ALLOWED)
 Values
   (131, 2001, 'Y', 'Y', 'Y', 'Y');
Insert into AIMS_ROLE_PRIVILEGES
   (ROLE_ID, PRIVILEGE_ID, IF_SELECT_ALLOWED, IF_CREATE_ALLOWED, IF_UPDATE_ALLOWED)
 Values
   (131, 2002, 'Y', 'Y', 'Y');   
   




Insert into AIMS_ROLE_PRIVILEGES
   (ROLE_ID, PRIVILEGE_ID, IF_SELECT_ALLOWED, IF_CREATE_ALLOWED, IF_UPDATE_ALLOWED, IF_DELETE_ALLOWED)
 Values
   (98, 801, 'Y', 'Y', 'Y', 'Y');
Insert into AIMS_ROLE_PRIVILEGES
   (ROLE_ID, PRIVILEGE_ID, IF_SELECT_ALLOWED, IF_CREATE_ALLOWED, IF_UPDATE_ALLOWED)
 Values
   (98, 803, 'Y', 'Y', 'Y');   

Insert into AIMS_ROLE_PRIVILEGES
   (ROLE_ID, PRIVILEGE_ID, IF_SELECT_ALLOWED, IF_CREATE_ALLOWED, IF_UPDATE_ALLOWED, IF_DELETE_ALLOWED)
 Values
   (98, 804, 'Y', 'Y', 'Y', 'Y');
Insert into AIMS_ROLE_PRIVILEGES
   (ROLE_ID, PRIVILEGE_ID, IF_SELECT_ALLOWED, IF_CREATE_ALLOWED, IF_UPDATE_ALLOWED)
 Values
   (98, 805, 'Y', 'Y', 'Y');

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- VZDN ZON INT SCRIPT
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

ALTER TABLE aims_contacts
  ADD alliance_id NUMBER
/
ALTER TABLE aims_sys_roles
  ADD vzdn_mapping_role_id NUMBER
/
CREATE TABLE aims_user_offer
(
  offer_id            NUMBER,
  offer_date          DATE,
  offer_to            VARCHAR2(100),
  alliance_id         NUMBER                   NOT NULL,
  acc_rej_date        DATE,
  status              VARCHAR2(10),
  offer_from_name  VARCHAR2(100),
  offer_from_email  VARCHAR2(100)
)
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
CREATE  SEQUENCE seq_aims_user_offer
/

ALTER TABLE aims_contracts ADD
  (html_document             CLOB,
   html_document_file_name   VARCHAR2(150),
   html_document_content_type VARCHAR2(100),
   is_click_through_contract VARCHAR2(1))
/

ALTER TABLE aims_contracts ADD (
  CONSTRAINT fk_contracts_3 FOREIGN KEY (ring_number)
    REFERENCES aims_types (type_id))
/

--------------- Manage Contact Role
INSERT INTO aims_menus
            (menu_id, menu_name, menu_url,
             image_name, sort_order, created_by, created_date,
             last_updated_by, last_updated_date
            )
     VALUES (101, 'Manage Contacts', '/aims/contacts.do?task=viewList',
             'manage_contacts', 1350, 'system', SYSDATE,
             'system', SYSDATE
            )
/

INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by,
             created_date, last_updated_by, last_updated_date, menu_id,
             sub_menu_id, privilege_key, available_to
            )
     VALUES (701, 'Manage Contacts', 'Manage Contacts', 'system',
             SYSDATE, 'system', SYSDATE, 101,
             NULL, 'MANAGE_CONTACTS', 'A'
            )
/





INSERT INTO aims_role_privileges
            (role_id, privilege_id, if_select_allowed, if_create_allowed,
             if_update_allowed, if_delete_allowed
            )
     VALUES (3, 701, 'Y', 'Y',
             'Y', 'Y'
            )
/


--------------- login conent script for contact
UPDATE aims_login_content lc
   SET lc.ignored_path =
          '/loginAllianceContactUpdateAction.do,/contactsSetup.do,/contactEdit.do'
 WHERE lc.login_content_id = 1
/
--------------- vzw and role cleanup
DELETE FROM aims_role_privileges
      WHERE privilege_id IN (167, 168)
/
DELETE FROM aims_sys_privileges
      WHERE privilege_id IN (168)
/
DELETE FROM aims_sub_menus
      WHERE sub_menu_id IN (38)
/



--------------- Click Through Contracts Menu
INSERT INTO aims_menus
            (menu_id, menu_name, menu_url,
             image_name, sort_order, created_by, created_date,
             last_updated_by, last_updated_date
            )
     VALUES (102, 'Click Through Contracts', '/aims/allianceClickThroughContracts.do',
             'click_through_contracts', 1200, 'system', SYSDATE,
             'system', SYSDATE
            )
/
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by,
             created_date, last_updated_by, last_updated_date, menu_id,
             sub_menu_id, privilege_key, available_to
            )
     VALUES (702, 'Click Through Contracts', 'Click Through Contracts', 'system',
             SYSDATE, 'system', SYSDATE, 102,
             NULL, 'CLICK_THROUGH_CONTRACTS', 'A'
            )
/
INSERT INTO aims_role_privileges
            (role_id, privilege_id, if_select_allowed, if_create_allowed,
             if_update_allowed, if_delete_allowed
            )
     VALUES (3, 702, 'Y', 'Y',
             'Y', 'Y'
            )
/






update aims_sys_roles set vzdn_mapping_role_id=2001 where role_name='Read Only for BREW'; 
update aims_sys_roles set vzdn_mapping_role_id=2002 where role_name='WAP Biz Dev'; 
update aims_sys_roles set vzdn_mapping_role_id=2003 where role_name='WAP Product Development'; 
update aims_sys_roles set vzdn_mapping_role_id=2004 where role_name='WAP Technology Development'; 
update aims_sys_roles set vzdn_mapping_role_id=2005 where role_name='Intertek'; 
update aims_sys_roles set vzdn_mapping_role_id=2006 where role_name='VZAppZone Admin';
update aims_sys_roles set vzdn_mapping_role_id=2007 where role_name='Dashboard Admin'; 
update aims_sys_roles set vzdn_mapping_role_id=2008 where role_name='Wireless Developer'; 
update aims_sys_roles set vzdn_mapping_role_id=2009 where role_name='BREW Admin'; 
update aims_sys_roles set vzdn_mapping_role_id=2010 where role_name='Legal'; 
update aims_sys_roles set vzdn_mapping_role_id=2011 where role_name='SUPERUSER'; 
update aims_sys_roles set vzdn_mapping_role_id=2012 where role_name='VZWADMIN'; 
update aims_sys_roles set vzdn_mapping_role_id=2013 where role_name='Primary User'; 
update aims_sys_roles set vzdn_mapping_role_id=2014 where role_name='WAP Admin'; 
update aims_sys_roles set vzdn_mapping_role_id=2015 where role_name='Enterprise Admin'; 
update aims_sys_roles set vzdn_mapping_role_id=2016 where role_name='Tech Team'; 
update aims_sys_roles set vzdn_mapping_role_id=2017 where role_name='JMA Contract Admin'; 
update aims_sys_roles set vzdn_mapping_role_id=2018 where role_name='Super'; 
update aims_sys_roles set vzdn_mapping_role_id=2019 where role_name='WAP - Read only'; 
update aims_sys_roles set vzdn_mapping_role_id=2020 where role_name='Evaluation'; 
update aims_sys_roles set vzdn_mapping_role_id=2021 where role_name='Biz Dev BREW Admin'; 
update aims_sys_roles set vzdn_mapping_role_id=2022 where role_name='Read Only'; 
update aims_sys_roles set vzdn_mapping_role_id=2023 where role_name='Rollback_DCR'; 
update aims_sys_roles set vzdn_mapping_role_id=2024 where role_name='BREW - Read only'; 
update aims_sys_roles set vzdn_mapping_role_id=2025 where role_name='Secondary User';











--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LONE STORED PROCEDURE
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------











CREATE OR REPLACE PACKAGE BODY Aims_Lob_Utils
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

        INSERT INTO AIMS_UPLOADED_DOCUMENTS
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
            AIMS_TEMP_FILES
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
            AIMS_TEMP_FILES
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
         ( p_from_table_col_name     IN  VARCHAR2,  -- name of the column of the table which will be selected for update. 
           p_from_table_name         IN  VARCHAR2,  -- name of table which record will be selected for update.
           p_pk_expr_from_table      IN  VARCHAR2,  -- expression in the form pkid = value
           p_to_table_col_name       IN  VARCHAR2,  -- name of the column of the table which record will be updated
           p_to_table_name           IN  VARCHAR2,  -- name of the table which column will be updated
           p_pk_expr_to_table        IN  VARCHAR2   -- expression in the to pkid = value
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
	|| 11-16-2008       MMahmood       Modified
    || 
    ||
    ||
    ||
    ||
    */
        sql_update          VARCHAR2(2000);

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

       EXECUTE IMMEDIATE sql_update;

   END copy_lob_from_to_table;


/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE clone_images
         ( p_clone_from_app_id      IN  NUMBER,    -- application id to clone from
           p_clone_to_app_id        IN  VARCHAR2,  -- application id to clone to
           p_platform_id            IN  VARCHAR2   -- platform id
         )
   IS
        v_faq_doc AIMS_APPS.faq_doc%TYPE;
        v_faq_doc_file_name AIMS_APPS.faq_doc_file_name%TYPE;
        v_faq_doc_content_type AIMS_APPS.faq_doc_content_type%TYPE;

        v_user_guide AIMS_APPS.user_guide%TYPE;
        v_user_guide_file_name AIMS_APPS.user_guide_file_name%TYPE;
        v_user_guide_content_type AIMS_APPS.user_guide_content_type%TYPE;

        v_test_plan_results AIMS_APPS.test_plan_results%TYPE;
        v_test_plan_results_file_name AIMS_APPS.test_plan_results_file_name%TYPE;
        v_test_plan_results_cont_type AIMS_APPS.test_plan_results_content_type%TYPE;

        v_splash_screen_eps AIMS_APPS.splash_screen_eps%TYPE;
        v_splash_screen_eps_file_name AIMS_APPS.splash_screen_eps_file_name%TYPE;
        v_splash_screen_eps_cont_type AIMS_APPS.splash_screen_eps_content_type%TYPE;

        v_screen_jpeg AIMS_APPS.screen_jpeg%TYPE;
        v_screen_jpeg_file_name AIMS_APPS.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_content_type AIMS_APPS.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_2 AIMS_APPS.screen_jpeg%TYPE;
        v_screen_jpeg_2_file_name AIMS_APPS.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_2_content_type AIMS_APPS.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_3 AIMS_APPS.screen_jpeg%TYPE;
        v_screen_jpeg_3_file_name AIMS_APPS.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_3_content_type AIMS_APPS.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_4 AIMS_APPS.screen_jpeg%TYPE;
        v_screen_jpeg_4_file_name AIMS_APPS.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_4_content_type AIMS_APPS.screen_jpeg_content_type%TYPE;

        v_screen_jpeg_5 AIMS_APPS.screen_jpeg%TYPE;
        v_screen_jpeg_5_file_name AIMS_APPS.screen_jpeg_file_name%TYPE;
        v_screen_jpeg_5_content_type AIMS_APPS.screen_jpeg_content_type%TYPE;

        v_flash_demo AIMS_APPS.flash_demo%TYPE;
        v_flash_demo_file_name AIMS_APPS.flash_demo_file_name%TYPE;
        v_flash_demo_content_type AIMS_APPS.flash_demo_content_type%TYPE;

        v_active_screen_eps AIMS_APPS.active_screen_eps%TYPE;
        v_active_screen_eps_file_name AIMS_APPS.active_screen_eps_file_name%TYPE;
        v_active_screen_eps_cont_type AIMS_APPS.active_screen_eps_content_type%TYPE;


  --For BREW
        v_style_guide AIMS_BREW_APPS.style_guide%TYPE;
        v_style_guide_file_name AIMS_BREW_APPS.style_guide_file_name%TYPE;
        v_style_guide_content_type AIMS_BREW_APPS.style_guide_content_type%TYPE;

        v_mktg_slick_sheet AIMS_BREW_APPS.mktg_slick_sheet%TYPE;
        v_mktg_slick_sheet_file_name AIMS_BREW_APPS.mktg_slick_sheet_file_name%TYPE;
        v_mktg_slick_sheet_cont_type AIMS_BREW_APPS.mktg_slick_sheet_content_type%TYPE;

     v_app_logo_bw_small AIMS_BREW_APPS.app_logo_bw_small%TYPE;
        v_app_logo_bw_small_file_name AIMS_BREW_APPS.app_logo_bw_small_file_name%TYPE;
        v_app_logo_bw_small_cont_type AIMS_BREW_APPS.app_logo_bw_small_content_type%TYPE;

     v_app_logo_bw_large AIMS_BREW_APPS.app_logo_bw_large%TYPE;
        v_app_logo_bw_large_file_name AIMS_BREW_APPS.app_logo_bw_large_file_name%TYPE;
        v_app_logo_bw_large_cont_type AIMS_BREW_APPS.app_logo_bw_large_content_type%TYPE;

     v_app_logo_clrsmall AIMS_BREW_APPS.app_logo_clrsmall%TYPE;
        v_app_logo_clrsmall_file_name AIMS_BREW_APPS.app_logo_clrsmall_file_name%TYPE;
        v_app_logo_clrsmall_cont_type AIMS_BREW_APPS.app_logo_clrsmall_content_type%TYPE;

  v_app_logo_clrlarge AIMS_BREW_APPS.app_logo_clrlarge%TYPE;
        v_app_logo_clrlarge_file_name AIMS_BREW_APPS.app_logo_clrlarge_file_name%TYPE;
        v_app_logo_clrlarge_cont_type AIMS_BREW_APPS.app_logo_clrlarge_content_type%TYPE;

  v_clr_pub_logo AIMS_BREW_APPS.clr_pub_logo%TYPE;
        v_clr_pub_logo_file_name AIMS_BREW_APPS.clr_pub_logo_file_name%TYPE;
        v_clr_pub_logo_cont_type AIMS_BREW_APPS.clr_pub_logo_content_type%TYPE;

        v_prog_guide AIMS_BREW_APPS.prog_guide%TYPE;
        v_prog_guide_file_name AIMS_BREW_APPS.prog_guide_file_name%TYPE;
        v_prog_guide_cont_type AIMS_BREW_APPS.prog_guide_content_type%TYPE;

        v_app_title_name AIMS_BREW_APPS.app_title_name%TYPE;
        v_app_title_name_file_name AIMS_BREW_APPS.app_title_name_file_name%TYPE;
        v_app_title_name_cont_type AIMS_BREW_APPS.app_title_name_content_type%TYPE;

        v_brew_presentation AIMS_BREW_APPS.brew_presentation%TYPE;
        v_brew_presentation_file_name AIMS_BREW_APPS.brew_presentation_file_name%TYPE;
        v_brew_presentation_cont_type AIMS_BREW_APPS.brew_presentation_content_type%TYPE;

        v_brew_company_logo AIMS_BREW_APPS.company_logo%TYPE;
        v_brew_company_logo_file_name AIMS_BREW_APPS.company_logo_file_name%TYPE;
        v_brew_company_logo_cont_type AIMS_BREW_APPS.company_logo_content_type%TYPE;

        v_brew_title_shot AIMS_BREW_APPS.title_shot%TYPE;
        v_brew_title_shot_file_name AIMS_BREW_APPS.title_shot_file_name%TYPE;
        v_brew_title_shot_cont_type AIMS_BREW_APPS.title_shot_content_type%TYPE;
        
        v_brew_high_res_spl AIMS_BREW_APPS.high_res_splash%TYPE;
        v_brew_high_res_spl_cont_type AIMS_BREW_APPS.high_res_splash_content_type%TYPE;
        v_brew_high_res_spl_file_name AIMS_BREW_APPS.high_res_splash_file_name%TYPE;
                            
        v_brew_high_res_act AIMS_BREW_APPS.high_res_active%TYPE;
        v_brew_high_res_act_cont_type AIMS_BREW_APPS.high_res_active_content_type%TYPE;
        v_brew_high_res_act_file_name AIMS_BREW_APPS.high_res_active_file_name%TYPE;                
        
        v_brew_splash_screen AIMS_BREW_APPS.splash_screen%TYPE;
        v_brew_splash_screen_cont_type AIMS_BREW_APPS.splash_screen_content_type%TYPE;
        v_brew_splash_screen_file_name AIMS_BREW_APPS.splash_screen_file_name%TYPE;        
                
        v_brew_small_splash AIMS_BREW_APPS.small_splash%TYPE;
        v_brew_small_splash_cont_type AIMS_BREW_APPS.small_splash_content_type%TYPE;
        v_brew_small_splash_file_name AIMS_BREW_APPS.small_splash_file_name%TYPE;        
        
        v_brew_act_screen_1 AIMS_BREW_APPS.active_screen_1%TYPE;
        v_brew_act_screen_1_cont_type AIMS_BREW_APPS.active_screen_1_content_type%TYPE;
        v_brew_act_screen_1_file_name AIMS_BREW_APPS.active_screen_1_file_name%TYPE;        
                
        v_brew_act_screen_2 AIMS_BREW_APPS.active_screen_2%TYPE;
        v_brew_act_screen_2_cont_type AIMS_BREW_APPS.active_screen_2_content_type%TYPE;
        v_brew_act_screen_2_file_name AIMS_BREW_APPS.active_screen_2_file_name%TYPE;        
                
        v_brew_sml_active_screen AIMS_BREW_APPS.sml_active_screen%TYPE;
        v_brew_smlactscreen_cont_type AIMS_BREW_APPS.sml_active_screen_content_type%TYPE;
        v_brew_smlactscreen_file_name AIMS_BREW_APPS.sml_active_screen_file_name%TYPE;        
                
        v_brew_app_act_flash AIMS_BREW_APPS.app_actiion_flash%TYPE;
        v_brew_app_act_flash_cont_type AIMS_BREW_APPS.app_actiion_flash_content_type%TYPE;
        v_brew_app_act_flash_file_name AIMS_BREW_APPS.app_actiion_flash_file_name%TYPE;
                        
        v_brew_app_gif_act AIMS_BREW_APPS.app_gif_action%TYPE;
        v_brew_app_gif_act_cont_type AIMS_BREW_APPS.app_gif_action_content_type%TYPE;
        v_brew_app_gif_act_file_name AIMS_BREW_APPS.app_gif_action_file_name%TYPE;        
                
        v_brew_media_store AIMS_BREW_APPS.media_store%TYPE;
        v_brew_media_store_cont_type AIMS_BREW_APPS.media_store_content_type%TYPE;        
        v_brew_media_store_file_name AIMS_BREW_APPS.media_store_file_name%TYPE;
        
        v_brew_flash_dem_mov AIMS_BREW_APPS.flash_demo_movie%TYPE;
        v_brew_flash_dem_mov_cont_type AIMS_BREW_APPS.flash_demo_movie_content_type%TYPE;        
        v_brew_flash_dem_mov_file_name AIMS_BREW_APPS.flash_demo_movie_file_name%TYPE;
        
        v_brew_dash_scr_img AIMS_BREW_APPS.dashboard_scr_img%TYPE;
        v_brew_dash_scr_img_cont_type AIMS_BREW_APPS.dashboard_scr_img_content_type%TYPE;        
        v_brew_dash_scr_img_file_name AIMS_BREW_APPS.dashboard_scr_img_file_name%TYPE;
                    
  --For Enterprise
        v_presentation AIMS_ENTERPRISE_APPS.presentation%TYPE;
        v_presentation_file_name AIMS_ENTERPRISE_APPS.presentation_file_name%TYPE;
        v_presentation_content_type AIMS_ENTERPRISE_APPS.presentation_content_type%TYPE;


  --For MMS
        v_sample_content AIMS_MMS_APPS.sample_content%TYPE;
        v_sample_content_file_name AIMS_MMS_APPS.sample_content_file_name%TYPE;
        v_sample_content_content_type AIMS_MMS_APPS.sample_content_content_type%TYPE;


        --For SMS
        v_message_flow AIMS_SMS_APPS.message_flow%TYPE;
        v_message_flow_file_name AIMS_SMS_APPS.message_flow_file_name%TYPE;
        v_message_flow_content_type AIMS_SMS_APPS.message_flow_content_type%TYPE;


     --For WAP
        v_screen_shot AIMS_WAP_APPS.screen_shot%TYPE;
        v_screen_shot_file_name AIMS_WAP_APPS.screen_shot_file_name%TYPE;
        v_screen_shot_content_type AIMS_WAP_APPS.screen_shot_content_type%TYPE;

        v_wap_presentation AIMS_WAP_APPS.presentation%TYPE;
        v_wap_presentation_file_name AIMS_WAP_APPS.presentation_file_name%TYPE;
        v_wap_presentation_cont_type AIMS_WAP_APPS.presentation_content_type%TYPE;

        v_wap_product_logo AIMS_WAP_APPS.product_logo%TYPE;
        v_wap_product_logo_file_name AIMS_WAP_APPS.product_logo_file_name%TYPE;
        v_wap_product_logo_cont_type AIMS_WAP_APPS.product_logo_content_type%TYPE;

        v_wap_product_icon AIMS_WAP_APPS.product_icon%TYPE;
        v_wap_product_icon_file_name AIMS_WAP_APPS.product_icon_file_name%TYPE;
        v_wap_product_icon_cont_type AIMS_WAP_APPS.product_icon_content_type%TYPE;

        v_wap_img_medium AIMS_WAP_APPS.app_img_medium%TYPE;
        v_wap_img_medium_file_name AIMS_WAP_APPS.app_img_medium_file_name%TYPE;
        v_wap_img_medium_content_type AIMS_WAP_APPS.app_img_medium_content_type%TYPE;
        
        v_wap_img_potrait AIMS_WAP_APPS.app_img_potrait%TYPE;
        v_wap_img_potrait_file_name AIMS_WAP_APPS.app_img_potrait_file_name%TYPE;
        v_wap_img_potrait_content_type AIMS_WAP_APPS.app_img_potrait_content_type%TYPE;
        
        v_wap_img_landscape AIMS_WAP_APPS.app_img_landscape%TYPE;
        v_wap_img_landscape_file_name AIMS_WAP_APPS.app_img_landscape_file_name%TYPE;
        v_wap_img_landscape_cont_type AIMS_WAP_APPS.app_img_landscape_content_type%TYPE;

        
     --For VCAST
        v_sample_clip_1 AIMS_VCAST_APPS.sample_clip_1%TYPE;
        v_sample_clip_1_file_name AIMS_VCAST_APPS.sample_clip_1_file_name%TYPE;
        v_sample_clip_1_content_type AIMS_VCAST_APPS.sample_clip_1_content_type%TYPE;

        v_sample_clip_2 AIMS_VCAST_APPS.sample_clip_2%TYPE;
        v_sample_clip_2_file_name AIMS_VCAST_APPS.sample_clip_2_file_name%TYPE;
        v_sample_clip_2_content_type AIMS_VCAST_APPS.sample_clip_2_content_type%TYPE;
        
        v_sample_clip_3 AIMS_VCAST_APPS.sample_clip_3%TYPE;
        v_sample_clip_3_file_name AIMS_VCAST_APPS.sample_clip_3_file_name%TYPE;
        v_sample_clip_3_content_type AIMS_VCAST_APPS.sample_clip_3_content_type%TYPE;

     
     -- For VZ_APPS_ZON
        v_app_landing_page AIMS_VZAPPZONE_APPS.app_landing_page%TYPE;
        v_app_landing_page_content_typ AIMS_VZAPPZONE_APPS.app_landing_page_content_type%TYPE;
        v_app_landing_page_file_name AIMS_VZAPPZONE_APPS.app_landing_page_file_name%TYPE;

        v_app_presentation AIMS_VZAPPZONE_APPS.app_presentation%TYPE;
        v_app_presentation_content_typ AIMS_VZAPPZONE_APPS.app_presentation_content_type%TYPE;
        v_app_presentation_file_name AIMS_VZAPPZONE_APPS.app_presentation_file_name%TYPE;

                 
     --For Dashboard
        v_dash_pub_logo AIMS_DASHBOARD_APPS.clr_pub_logo%TYPE;
        v_dash_pub_logo_file_name AIMS_DASHBOARD_APPS.clr_pub_logo_file_name%TYPE;
        v_dash_pub_logo_cont_type AIMS_DASHBOARD_APPS.clr_pub_logo_content_type%TYPE;

        v_dash_title_name AIMS_DASHBOARD_APPS.app_title_name%TYPE;
        v_dash_title_name_file_name AIMS_DASHBOARD_APPS.app_title_name_file_name%TYPE;
        v_dash_title_name_cont_type AIMS_DASHBOARD_APPS.app_title_name_content_type%TYPE;

        v_dash_content_file AIMS_DASHBOARD_APPS.content_zip_file%TYPE;
        v_dash_content_file_file_name AIMS_DASHBOARD_APPS.content_zip_file_file_name%TYPE;
        v_dash_content_file_cont_type AIMS_DASHBOARD_APPS.content_zip_file_content_type%TYPE;

        v_dash_company_logo AIMS_DASHBOARD_APPS.company_logo%TYPE;
        v_dash_company_logo_file_name AIMS_DASHBOARD_APPS.company_logo_file_name%TYPE;
        v_dash_company_logo_cont_type AIMS_DASHBOARD_APPS.company_logo_content_type%TYPE;
        
        v_dash_title_image AIMS_DASHBOARD_APPS.title_image%TYPE;
        v_dash_title_image_file_name AIMS_DASHBOARD_APPS.title_image_file_name%TYPE;
        v_dash_title_image_cont_type AIMS_DASHBOARD_APPS.title_image_content_type%TYPE;
        
	--For JAVA
	  v_j_hr_pub_logo		        AIMS_JAVA_APPS.hr_publisher_logo%TYPE;
	  v_j_hr_pub_logo_file_name		AIMS_JAVA_APPS.hr_publisher_logo_file_name%TYPE;
	  v_j_hr_pub_logo_content_type	AIMS_JAVA_APPS.hr_publisher_logo_content_type%TYPE;

	  v_j_ch_title_icon		        AIMS_JAVA_APPS.chnl_title_icon%TYPE;
	  v_j_ch_title_icon_file_name		AIMS_JAVA_APPS.chnl_title_icon_file_name%TYPE;
	  v_j_ch_title_icon_content_type	AIMS_JAVA_APPS.chnl_title_icon_content_type%TYPE;

	  v_j_sp_scr_shot		        AIMS_JAVA_APPS.splh_screen_shot%TYPE;
	  v_j_sp_scr_shot_file_name		AIMS_JAVA_APPS.splh_screen_shot_file_name%TYPE;
	  v_j_sp_scr_shot_content_type	AIMS_JAVA_APPS.splh_screen_shot_content_type%TYPE;

	  v_j_actv_scr_shot		        AIMS_JAVA_APPS.actv_screen_shot%TYPE;
	  v_j_actv_scr_shot_file_name		AIMS_JAVA_APPS.actv_screen_shot_file_name%TYPE;
	  v_j_actv_scr_shot_content_type	AIMS_JAVA_APPS.actv_screen_shot_content_type%TYPE;
	  
	  v_j_app_scr		        AIMS_JAVA_APPS.app_screen_shot%TYPE;
	  v_j_app_scr_file_name		AIMS_JAVA_APPS.app_screen_shot_file_name%TYPE;
	  v_j_app_scr_content_type	AIMS_JAVA_APPS.app_screen_shot_content_type%TYPE;
	  
	  v_j_app_scr_1		        AIMS_JAVA_APPS.app_screen_shot_1%TYPE;
	  v_j_app_scr_1_file_name	AIMS_JAVA_APPS.app_screen_shot_1_file_name%TYPE;
	  v_j_app_scr_1_content_type	AIMS_JAVA_APPS.app_screen_shot_1_content_type%TYPE;
	  
  	  v_j_app_scr_2		        AIMS_JAVA_APPS.app_screen_shot_2%TYPE;
	  v_j_app_scr_2_file_name	AIMS_JAVA_APPS.app_screen_shot_2_file_name%TYPE;
	  v_j_app_scr_2_content_type	AIMS_JAVA_APPS.app_screen_shot_2_content_type%TYPE;
	  
  	  v_j_app_scr_3		        AIMS_JAVA_APPS.app_screen_shot_3%TYPE;
	  v_j_app_scr_3_file_name	AIMS_JAVA_APPS.app_screen_shot_3_file_name%TYPE;
	  v_j_app_scr_3_content_type	AIMS_JAVA_APPS.app_screen_shot_3_content_type%TYPE;
	  
  	  v_j_app_scr_4		        AIMS_JAVA_APPS.app_screen_shot_4%TYPE;
	  v_j_app_scr_4_file_name	AIMS_JAVA_APPS.app_screen_shot_4_file_name%TYPE;
	  v_j_app_scr_4_content_type	AIMS_JAVA_APPS.app_screen_shot_4_content_type%TYPE;
	  
	  v_j_app_scr_5		        AIMS_JAVA_APPS.app_screen_shot_5%TYPE;
	  v_j_app_scr_5_file_name	AIMS_JAVA_APPS.app_screen_shot_5_file_name%TYPE;
	  v_j_app_scr_5_content_type	AIMS_JAVA_APPS.app_screen_shot_5_content_type%TYPE;
	  
  	  v_j_app_scr_6		        AIMS_JAVA_APPS.app_screen_shot_6%TYPE;
	  v_j_app_scr_6_file_name	AIMS_JAVA_APPS.app_screen_shot_6_file_name%TYPE;
	  v_j_app_scr_6_content_type	AIMS_JAVA_APPS.app_screen_shot_6_content_type%TYPE;
	  
  	  v_j_app_scr_7		        AIMS_JAVA_APPS.app_screen_shot_7%TYPE;
	  v_j_app_scr_7_file_name	AIMS_JAVA_APPS.app_screen_shot_7_file_name%TYPE;
	  v_j_app_scr_7_content_type	AIMS_JAVA_APPS.app_screen_shot_7_content_type%TYPE;
	  
  	  v_j_app_scr_8		        AIMS_JAVA_APPS.app_screen_shot_8%TYPE;
	  v_j_app_scr_8_file_name	AIMS_JAVA_APPS.app_screen_shot_8_file_name%TYPE;
	  v_j_app_scr_8_content_type	AIMS_JAVA_APPS.app_screen_shot_8_content_type%TYPE;	  

  	  v_j_app_scr_9		        AIMS_JAVA_APPS.app_screen_shot_9%TYPE;
	  v_j_app_scr_9_file_name	AIMS_JAVA_APPS.app_screen_shot_9_file_name%TYPE;
	  v_j_app_scr_9_content_type	AIMS_JAVA_APPS.app_screen_shot_9_content_type%TYPE;	  
	  
	  v_j_faq_file		        		AIMS_JAVA_APPS.faq_file%TYPE;
	  v_j_faq_file_file_name				AIMS_JAVA_APPS.faq_file_file_name%TYPE;
	  v_j_faq_file_content_type			AIMS_JAVA_APPS.faq_file_content_type%TYPE;
	  
  	  v_j_company_logo		        	AIMS_JAVA_APPS.company_logo%TYPE;
	  v_j_company_logo_file_name			AIMS_JAVA_APPS.company_logo_file_name%TYPE;
	  v_j_company_logo_content_type		AIMS_JAVA_APPS.company_logo_content_type%TYPE;

   	  v_j_app_title		     		AIMS_JAVA_APPS.app_title_name%TYPE;
	  v_j_app_title_file_name		AIMS_JAVA_APPS.app_title_name_file_name%TYPE;
	  v_j_app_title_content_type	AIMS_JAVA_APPS.app_title_name_content_type%TYPE;
              
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
            AIMS_APPS
        WHERE
            apps_id = p_clone_from_app_id;

        UPDATE
            AIMS_APPS
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
                AIMS_BREW_APPS
            WHERE
                brew_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_BREW_APPS
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
                AIMS_ENTERPRISE_APPS
            WHERE
                enterprise_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_ENTERPRISE_APPS
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
                AIMS_MMS_APPS
            WHERE
                mms_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_MMS_APPS
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
                AIMS_SMS_APPS
            WHERE
                sms_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_SMS_APPS
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
                AIMS_WAP_APPS
            WHERE
                wap_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_WAP_APPS
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
                AIMS_VCAST_APPS
            WHERE
                vcast_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_VCAST_APPS
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
                AIMS_DASHBOARD_APPS
            WHERE
                dashboard_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_DASHBOARD_APPS
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
     IF (p_platform_id = 42) THEN
            SELECT
                app_landing_page,app_landing_page_content_type,app_landing_page_file_name,
                app_presentation,app_presentation_content_type,app_presentation_file_name
            INTO
                v_app_landing_page,v_app_landing_page_content_typ,v_app_landing_page_file_name,
                v_app_presentation,v_app_presentation_content_typ,v_app_presentation_file_name
            FROM
                AIMS_VZAPPZONE_APPS
            WHERE
                vzappzone_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_VZAPPZONE_APPS
            SET
                app_landing_page = v_app_landing_page,
                app_landing_page_content_type = v_app_landing_page_content_typ,
                app_landing_page_file_name = v_app_landing_page_file_name,
                app_presentation = v_app_presentation,
                app_presentation_content_type = v_app_presentation_content_typ,
                app_presentation_file_name = v_app_presentation_file_name
            WHERE
                vzappzone_apps_id = p_clone_to_app_id;

        END IF;
        
		-- For JAVA
        IF (p_platform_id = 44) THEN
            SELECT
			  hr_publisher_logo, hr_publisher_logo_file_name, hr_publisher_logo_content_type
			  , chnl_title_icon, chnl_title_icon_file_name, chnl_title_icon_content_type
			  , splh_screen_shot, splh_screen_shot_file_name, splh_screen_shot_content_type
			  , actv_screen_shot, actv_screen_shot_file_name, actv_screen_shot_content_type
			  , app_screen_shot, app_screen_shot_file_name, app_screen_shot_content_type
			  , app_screen_shot_1, app_screen_shot_1_file_name, app_screen_shot_1_content_type
			  , app_screen_shot_2, app_screen_shot_2_file_name, app_screen_shot_2_content_type
			  , app_screen_shot_3, app_screen_shot_3_file_name, app_screen_shot_3_content_type
			  , app_screen_shot_4, app_screen_shot_4_file_name, app_screen_shot_4_content_type
			  , app_screen_shot_5, app_screen_shot_5_file_name, app_screen_shot_5_content_type
			  , app_screen_shot_6, app_screen_shot_6_file_name, app_screen_shot_6_content_type
			  , app_screen_shot_7, app_screen_shot_7_file_name, app_screen_shot_7_content_type
			  , app_screen_shot_8, app_screen_shot_8_file_name, app_screen_shot_8_content_type
			  , app_screen_shot_9, app_screen_shot_9_file_name, app_screen_shot_9_content_type
			  , faq_file, faq_file_file_name, faq_file_content_type
			  , company_logo, company_logo_file_name, company_logo_content_type
			  , app_title_name, app_title_name_file_name, app_title_name_content_type

            INTO
			  v_j_hr_pub_logo, v_j_hr_pub_logo_file_name, v_j_hr_pub_logo_content_type
			  , v_j_ch_title_icon, v_j_ch_title_icon_file_name, v_j_ch_title_icon_content_type
			  , v_j_sp_scr_shot, v_j_sp_scr_shot_file_name, v_j_sp_scr_shot_content_type
			  , v_j_actv_scr_shot, v_j_actv_scr_shot_file_name, v_j_actv_scr_shot_content_type
			  , v_j_app_scr, v_j_app_scr_file_name, v_j_app_scr_content_type
			  , v_j_app_scr_1, v_j_app_scr_1_file_name, v_j_app_scr_1_content_type
			  , v_j_app_scr_2, v_j_app_scr_2_file_name, v_j_app_scr_2_content_type
			  , v_j_app_scr_3, v_j_app_scr_3_file_name, v_j_app_scr_3_content_type
			  , v_j_app_scr_4, v_j_app_scr_4_file_name, v_j_app_scr_4_content_type
			  , v_j_app_scr_5, v_j_app_scr_5_file_name, v_j_app_scr_5_content_type
			  , v_j_app_scr_6, v_j_app_scr_6_file_name, v_j_app_scr_6_content_type
			  , v_j_app_scr_7, v_j_app_scr_7_file_name, v_j_app_scr_7_content_type
			  , v_j_app_scr_8, v_j_app_scr_8_file_name, v_j_app_scr_8_content_type
			  , v_j_app_scr_9, v_j_app_scr_9_file_name, v_j_app_scr_9_content_type
			  , v_j_faq_file, v_j_faq_file_file_name, v_j_faq_file_content_type
			  , v_j_company_logo, v_j_company_logo_file_name, v_j_company_logo_content_type
			  , v_j_app_title, v_j_app_title_file_name, v_j_app_title_content_type

            FROM
                AIMS_JAVA_APPS
            WHERE
                java_apps_id = p_clone_from_app_id;

            UPDATE
                AIMS_JAVA_APPS
            SET
			  hr_publisher_logo		     	 =v_j_hr_pub_logo
			  ,hr_publisher_logo_file_name	 =v_j_hr_pub_logo_file_name
			  ,hr_publisher_logo_content_type=v_j_hr_pub_logo_content_type
		
			  ,chnl_title_icon		        =v_j_ch_title_icon
			  ,chnl_title_icon_file_name	=v_j_ch_title_icon_file_name
			  ,chnl_title_icon_content_type	=v_j_ch_title_icon_content_type
		
			  ,splh_screen_shot		        =v_j_sp_scr_shot
			  ,splh_screen_shot_file_name	=v_j_sp_scr_shot_file_name
			  ,splh_screen_shot_content_type=v_j_sp_scr_shot_content_type
		
			  ,actv_screen_shot		        =v_j_actv_scr_shot
			  ,actv_screen_shot_file_name	=v_j_actv_scr_shot_file_name
			  ,actv_screen_shot_content_type=v_j_actv_scr_shot_content_type
			  
			  ,app_screen_shot		        =v_j_app_scr
			  ,app_screen_shot_file_name	=v_j_app_scr_file_name
			  ,app_screen_shot_content_type	=v_j_app_scr_content_type
			  
			  ,app_screen_shot_1		     =v_j_app_scr_1
			  ,app_screen_shot_1_file_name	 =v_j_app_scr_1_file_name
			  ,app_screen_shot_1_content_type=v_j_app_scr_1_content_type
			  
		  	  ,app_screen_shot_2		     =v_j_app_scr_2
			  ,app_screen_shot_2_file_name	 =v_j_app_scr_2_file_name
			  ,app_screen_shot_2_content_type=v_j_app_scr_2_content_type
			  
		  	  ,app_screen_shot_3		     =v_j_app_scr_3
			  ,app_screen_shot_3_file_name	 =v_j_app_scr_3_file_name
			  ,app_screen_shot_3_content_type=v_j_app_scr_3_content_type
			  
		  	  ,app_screen_shot_4		     =v_j_app_scr_4
			  ,app_screen_shot_4_file_name	 =v_j_app_scr_4_file_name
			  ,app_screen_shot_4_content_type=v_j_app_scr_4_content_type
			  
			  ,app_screen_shot_5		     =v_j_app_scr_5
			  ,app_screen_shot_5_file_name	 =v_j_app_scr_5_file_name
			  ,app_screen_shot_5_content_type=v_j_app_scr_5_content_type
			  
		  	  ,app_screen_shot_6		     =v_j_app_scr_6
			  ,app_screen_shot_6_file_name	 =v_j_app_scr_6_file_name
			  ,app_screen_shot_6_content_type=v_j_app_scr_6_content_type
			  
		  	  ,app_screen_shot_7		     =v_j_app_scr_7
			  ,app_screen_shot_7_file_name	 =v_j_app_scr_7_file_name
			  ,app_screen_shot_7_content_type=v_j_app_scr_7_content_type
			  
		  	  ,app_screen_shot_8		     =v_j_app_scr_8
			  ,app_screen_shot_8_file_name	 =v_j_app_scr_8_file_name
			  ,app_screen_shot_8_content_type=v_j_app_scr_8_content_type	  
		
		  	  ,app_screen_shot_9		     =v_j_app_scr_9
			  ,app_screen_shot_9_file_name	 =v_j_app_scr_9_file_name
			  ,app_screen_shot_9_content_type=v_j_app_scr_9_content_type	  
			  
			  ,faq_file		        		 =v_j_faq_file
			  ,faq_file_file_name			 =v_j_faq_file_file_name
			  ,faq_file_content_type		 =v_j_faq_file_content_type
			  
		  	  ,company_logo		        	 =v_j_company_logo
			  ,company_logo_file_name		 =v_j_company_logo_file_name
			  ,company_logo_content_type	 =v_j_company_logo_content_type
		
		   	  ,app_title_name		     	 =v_j_app_title
			  ,app_title_name_file_name		 =v_j_app_title_file_name
			  ,app_title_name_content_type	 =v_j_app_title_content_type

            WHERE
                java_apps_id = p_clone_to_app_id;

        END IF;


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
        Aims_Lob_Utils.check_single_files('ACTIVE_SCREEN_EPS', 'ACTIVE_SCREEN_EPS_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Active Screen Shot';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('FAQ_DOC', 'FAQ_DOC_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'FAQ';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('FLASH_DEMO', 'FLASH_DEMO_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Flash Demo of Running App';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SCREEN_JPEG', 'SCREEN_JPEG_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SCREEN_JPEG_2', 'SCREEN_JPEG_2_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 2';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SCREEN_JPEG_3', 'SCREEN_JPEG_3_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 3';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SCREEN_JPEG_4', 'SCREEN_JPEG_4_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 4';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SCREEN_JPEG_5', 'SCREEN_JPEG_5_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Screen Shot of Application 5';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SPLASH_SCREEN_EPS', 'SPLASH_SCREEN_EPS_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Splash Screen Shot';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('TEST_PLAN_RESULTS', 'TEST_PLAN_RESULTS_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Testing Plan And Results';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('USER_GUIDE', 'USER_GUIDE_FILE_NAME', 'AIMS_APPS', 'APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'User Guide';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('APP_LOGO_BW_LARGE', 'APP_LOGO_BW_LARGE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo BW Large';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('APP_LOGO_BW_SMALL', 'APP_LOGO_BW_SMALL_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo BW Small';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('APP_LOGO_CLRLARGE', 'APP_LOGO_CLRLARGE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo Color Large';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('APP_LOGO_CLRSMALL', 'APP_LOGO_CLRSMALL_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Logo Color Small';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('CLR_PUB_LOGO', 'CLR_PUB_LOGO_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'High Resolution Publisher Logo';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('PROG_GUIDE', 'PROG_GUIDE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Programming Guide';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('MKTG_SLICK_SHEET', 'MKTG_SLICK_SHEET_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Marketing Slick Sheet';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('STYLE_GUIDE', 'STYLE_GUIDE_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Style Guide for Use of Application Logo';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('APP_TITLE_NAME', 'APP_TITLE_NAME_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Application Title Image';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('BREW_PRESENTATION', 'BREW_PRESENTATION_FILE_NAME', 'AIMS_BREW_APPS', 'BREW_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Presentation';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('PRESENTATION', 'PRESENTATION_FILE_NAME', 'AIMS_ENTERPRISE_APPS', 'ENTERPRISE_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Presentation File';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SAMPLE_CONTENT', 'SAMPLE_CONTENT_FILE_NAME', 'AIMS_MMS_APPS', 'MMS_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Content File';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('MESSAGE_FLOW', 'MESSAGE_FLOW_FILE_NAME', 'AIMS_SMS_APPS', 'SMS_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Message Flow';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SAMPLE_CLIP_1', 'SAMPLE_CLIP_1_FILE_NAME', 'AIMS_VCAST_APPS', 'VCAST_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Video Clip';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SAMPLE_CLIP_2', 'SAMPLE_CLIP_2_FILE_NAME', 'AIMS_VCAST_APPS', 'VCAST_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Video Clip 2';
        END IF;

        v_temp_result := 'N';
        Aims_Lob_Utils.check_single_files('SAMPLE_CLIP_3', 'SAMPLE_CLIP_3_FILE_NAME', 'AIMS_VCAST_APPS', 'VCAST_APPS_ID', p_apps_id, v_temp_result);
        IF (v_temp_result = 'Y') THEN
            v_cnt := v_cnt + 1;
            v_file_names_array(v_cnt) := 'Sample Video Clip 3';
        END IF;

        IF (v_file_names_array.COUNT > 0) THEN
            Parse.table_to_delimstring(v_file_names_array, p_out_result, ', ');
        ELSE
            p_out_result := '';
        END IF;

   END check_uploaded_files;

/* -------------------------------------------------------------------------------------------------------------  */

END Aims_Lob_Utils; -- Package Body AIMS_LOB_UTILS
/
