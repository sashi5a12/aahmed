set escape !

ALTER TABLE AIMS_ENTERPRISE_APPS
ADD( 
     IS_PUBLISHED 	CHAR(1),
     IS_FEATURED 	CHAR(1)
  )
/
ALTER TABLE aims_enterprise_apps$aud
ADD( 
     IS_PUBLISHED 	CHAR(1),
     IS_FEATURED 	CHAR(1)
  )
/
CREATE OR REPLACE TRIGGER aims_enterprise_apps$audtrg
   AFTER INSERT OR DELETE OR UPDATE
   ON aims_enterprise_apps
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
      INSERT INTO aims_enterprise_apps$aud (enterprise_apps_id,
                                            num_wireless_users,
                                            num_all_users,
                                            platform_dep_mode,
                                            brief_desc,
                                            presentation_file_name,
                                            presentation_content_type,
                                            cust_support_phone,
                                            cust_support_email,
                                            cust_support_hours,
                                            presentation_file_type,
                                            fortune_customers,
                                            other_ind_focus_value,
                                            bds_name,
                                            solution_type_id,
                                            customer_benefits,
                                            alliance_sponsor,
                                            national_partner_id,
                                            IS_INTERESTED_IN_BOBO,
                                            IS_INTERESTED_IN_LBS,
                                            BOBO_LETTER_AUTH_FILE_NAME,
                                            BOBO_LETTER_AUTH_CONTENT_TYPE,
                                            IS_BOBO_ACCEPT,
                                            LBS_CONTRACT_FILE_NAME,
                                            LBS_CONTRACT_CONTENT_TYPE,
                                            IS_LBS_ACCEPT,
                                            ACCEPT_DECLINE_DATE,
                                            ACCEPT_DECLINE_USER_ID,
                                            CREATED_BY,
                                            CREATED_DATE,
                                            LAST_UPDATED_BY,
                                            LAST_UPDATED_DATE,
                                            IS_PRODUCT_EXCLUSIVE_TO_VZW,
                                            PRODUCT_EXCLUSIVE_TO_VZW,
                                            IS_GO_EXCLUSIVE_WITH_VZW,
                                            IS_PRODUCT_USE_VZW_VZ_NT,
                                            IS_PRODUCT_CERTIFIED_VZW,
                                            PRODUCT_CERTIFIED_PHASE,
                                            IS_PRODUCT_CERTIFIED_ODI_PRO,
                                            PRODUCT_INFORMATION,
                                            IS_PRODUCT_REQUIED_LBS,
                                            IS_OFFER_BOBO_SERVIES,
                                            BRIEF_DESCRIPTION,
                                            SOLUTION_RESIDE,
                                            ADDITIONAL_INFORMATION,
                                            DEVICES,
                                            BOBO_PRESENT_DATE,
                                            BOBO_ACCEPT_DECLINE_DATE,
                                            BOBO_ACCEPT_DECLINE_EMAIL_ID,
                                            IS_LBS_ACCEPT_BY_ALLIANCE,
                                            LBS_PRESENT_DATE,
                                            LBS_ACCEPT_DECLINE_DATE,
                                            LBS_ACCEPT_DECLINE_EMAIL_ID,
                                            IS_PUBLISHED,IS_FEATURED,
                                            aud_action,
                                            aud_timestamp,
                                            aud_user)
        VALUES   (:NEW.enterprise_apps_id,
                  :NEW.num_wireless_users,
                  :NEW.num_all_users,
                  :NEW.platform_dep_mode,
                  :NEW.brief_desc,
                  :NEW.presentation_file_name,
                  :NEW.presentation_content_type,
                  :NEW.cust_support_phone,
                  :NEW.cust_support_email,
                  :NEW.cust_support_hours,
                  :NEW.presentation_file_type,
                  :NEW.fortune_customers,
                  :NEW.other_ind_focus_value,
                  :NEW.bds_name,
                  :NEW.solution_type_id,
                  :NEW.customer_benefits,
                  :NEW.alliance_sponsor,
                  :NEW.national_partner_id,
                  :NEW.IS_INTERESTED_IN_BOBO,
                  :NEW.IS_INTERESTED_IN_LBS,
                  :NEW.BOBO_LETTER_AUTH_FILE_NAME,
                  :NEW.BOBO_LETTER_AUTH_CONTENT_TYPE,
                  :NEW.IS_BOBO_ACCEPT,
                  :NEW.LBS_CONTRACT_FILE_NAME,
                  :NEW.LBS_CONTRACT_CONTENT_TYPE,
                  :NEW.IS_LBS_ACCEPT,
                  :NEW.ACCEPT_DECLINE_DATE,
                  :NEW.ACCEPT_DECLINE_USER_ID,
                  :NEW.CREATED_BY,
                  :NEW.CREATED_DATE,
                  :NEW.LAST_UPDATED_BY,
                  :NEW.LAST_UPDATED_DATE,
                  :NEW.IS_PRODUCT_EXCLUSIVE_TO_VZW,
                  :NEW.PRODUCT_EXCLUSIVE_TO_VZW,
                  :NEW.IS_GO_EXCLUSIVE_WITH_VZW,
                  :NEW.IS_PRODUCT_USE_VZW_VZ_NT,
                  :NEW.IS_PRODUCT_CERTIFIED_VZW,
                  :NEW.PRODUCT_CERTIFIED_PHASE,
                  :NEW.IS_PRODUCT_CERTIFIED_ODI_PRO,
                  :NEW.PRODUCT_INFORMATION,
                  :NEW.IS_PRODUCT_REQUIED_LBS,
                  :NEW.IS_OFFER_BOBO_SERVIES,
                  :NEW.BRIEF_DESCRIPTION,
                  :NEW.SOLUTION_RESIDE,
                  :NEW.ADDITIONAL_INFORMATION,
                  :NEW.DEVICES,
                  :NEW.BOBO_PRESENT_DATE,
                  :NEW.BOBO_ACCEPT_DECLINE_DATE,
                  :NEW.BOBO_ACCEPT_DECLINE_EMAIL_ID,
                  :NEW.IS_LBS_ACCEPT_BY_ALLIANCE,
                  :NEW.LBS_PRESENT_DATE,
                  :NEW.LBS_ACCEPT_DECLINE_DATE,
                  :NEW.LBS_ACCEPT_DECLINE_EMAIL_ID,
                  :NEW.IS_PUBLISHED,:NEW.IS_FEATURED,
                  v_operation,
                  SYSDATE,
                  USER);
   ELSE
      INSERT INTO aims_enterprise_apps$aud (enterprise_apps_id,
                                            num_wireless_users,
                                            num_all_users,
                                            platform_dep_mode,
                                            brief_desc,
                                            presentation_file_name,
                                            presentation_content_type,
                                            cust_support_phone,
                                            cust_support_email,
                                            cust_support_hours,
                                            presentation_file_type,
                                            fortune_customers,
                                            other_ind_focus_value,
                                            bds_name,
                                            solution_type_id,
                                            customer_benefits,
                                            alliance_sponsor,
                                            national_partner_id,
                                            IS_INTERESTED_IN_BOBO,
                                            IS_INTERESTED_IN_LBS,
                                            BOBO_LETTER_AUTH_FILE_NAME,
                                            BOBO_LETTER_AUTH_CONTENT_TYPE,
                                            IS_BOBO_ACCEPT,
                                            LBS_CONTRACT_FILE_NAME,
                                            LBS_CONTRACT_CONTENT_TYPE,
                                            IS_LBS_ACCEPT,
                                            ACCEPT_DECLINE_DATE,
                                            ACCEPT_DECLINE_USER_ID,
                                            CREATED_BY,
                                            CREATED_DATE,
                                            LAST_UPDATED_BY,
                                            LAST_UPDATED_DATE,
                                            IS_PRODUCT_EXCLUSIVE_TO_VZW,
                                            PRODUCT_EXCLUSIVE_TO_VZW,
                                            IS_GO_EXCLUSIVE_WITH_VZW,
                                            IS_PRODUCT_USE_VZW_VZ_NT,
                                            IS_PRODUCT_CERTIFIED_VZW,
                                            PRODUCT_CERTIFIED_PHASE,
                                            IS_PRODUCT_CERTIFIED_ODI_PRO,
                                            PRODUCT_INFORMATION,
                                            IS_PRODUCT_REQUIED_LBS,
                                            IS_OFFER_BOBO_SERVIES,
                                            BRIEF_DESCRIPTION,
                                            SOLUTION_RESIDE,
                                            ADDITIONAL_INFORMATION,
                                            DEVICES,
                                            BOBO_PRESENT_DATE,
                                            BOBO_ACCEPT_DECLINE_DATE,
                                            BOBO_ACCEPT_DECLINE_EMAIL_ID,
                                            IS_LBS_ACCEPT_BY_ALLIANCE,
                                            LBS_PRESENT_DATE,
                                            LBS_ACCEPT_DECLINE_DATE,
                                            LBS_ACCEPT_DECLINE_EMAIL_ID,
                                            IS_PUBLISHED,IS_FEATURED,
                                            aud_action,
                                            aud_timestamp,
                                            aud_user)
        VALUES   (:OLD.enterprise_apps_id,
                  :OLD.num_wireless_users,
                  :OLD.num_all_users,
                  :OLD.platform_dep_mode,
                  :OLD.brief_desc,
                  :OLD.presentation_file_name,
                  :OLD.presentation_content_type,
                  :OLD.cust_support_phone,
                  :OLD.cust_support_email,
                  :OLD.cust_support_hours,
                  :OLD.presentation_file_type,
                  :OLD.fortune_customers,
                  :OLD.other_ind_focus_value,
                  :OLD.bds_name,
                  :OLD.solution_type_id,
                  :OLD.customer_benefits,
                  :OLD.alliance_sponsor,
                  :OLD.national_partner_id,
                  :OLD.IS_INTERESTED_IN_BOBO,
                  :OLD.IS_INTERESTED_IN_LBS,
                  :OLD.BOBO_LETTER_AUTH_FILE_NAME,
                  :OLD.BOBO_LETTER_AUTH_CONTENT_TYPE,
                  :OLD.IS_BOBO_ACCEPT,
                  :OLD.LBS_CONTRACT_FILE_NAME,
                  :OLD.LBS_CONTRACT_CONTENT_TYPE,
                  :OLD.IS_LBS_ACCEPT,
                  :OLD.ACCEPT_DECLINE_DATE,
                  :OLD.ACCEPT_DECLINE_USER_ID,
                  :OLD.CREATED_BY,
                  :OLD.CREATED_DATE,
                  :OLD.LAST_UPDATED_BY,
                  :OLD.LAST_UPDATED_DATE,
                  :OLD.IS_PRODUCT_EXCLUSIVE_TO_VZW,
                  :OLD.PRODUCT_EXCLUSIVE_TO_VZW,
                  :OLD.IS_GO_EXCLUSIVE_WITH_VZW,
                  :OLD.IS_PRODUCT_USE_VZW_VZ_NT,
                  :OLD.IS_PRODUCT_CERTIFIED_VZW,
                  :OLD.PRODUCT_CERTIFIED_PHASE,
                  :OLD.IS_PRODUCT_CERTIFIED_ODI_PRO,
                  :OLD.PRODUCT_INFORMATION,
                  :OLD.IS_PRODUCT_REQUIED_LBS,
                  :OLD.IS_OFFER_BOBO_SERVIES,
                  :OLD.BRIEF_DESCRIPTION,
                  :OLD.SOLUTION_RESIDE,
                  :OLD.ADDITIONAL_INFORMATION,
                  :OLD.DEVICES,
                  :OLD.BOBO_PRESENT_DATE,
                  :OLD.BOBO_ACCEPT_DECLINE_DATE,
                  :OLD.BOBO_ACCEPT_DECLINE_EMAIL_ID,
                  :OLD.IS_LBS_ACCEPT_BY_ALLIANCE,
                  :OLD.LBS_PRESENT_DATE,
                  :OLD.LBS_ACCEPT_DECLINE_DATE,
                  :OLD.LBS_ACCEPT_DECLINE_EMAIL_ID,
                  :OLD.IS_PUBLISHED,:OLD.IS_FEATURED,
                  v_operation,
                  SYSDATE,
                  USER);
   END IF;
END;
/
INSERT INTO aims_sub_menus
            (sub_menu_id, sub_menu_name, sub_menu_url,
             image_name, sort_order, created_by, created_date,
             last_updated_by, last_updated_date, menu_id
            )
     VALUES (3000, 'Publish Solutions', '/aims/entAppManagePublishSolution.do!?task=view',
             'publish_solution', 4160, 'system', SYSDATE,
             'system', SYSDATE, 3
            )
/
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by,
             created_date, last_updated_by, last_updated_date, menu_id,
             sub_menu_id, privilege_key, available_to
            )
     VALUES (3000, 'Publish Solutions', 'Publish Solutions', 'system',
             SYSDATE, 'system', SYSDATE, NULL,
             3000, 'PUBLISH_SOLUTIONS', 'V'
            )
/
CREATE OR REPLACE Package AIMS_JMA_APP_PKG
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Arsalan Ahmed Qureshi (akureshi)
||
|| File:
||
|| Overview:            Used by AIMS application for JMA application maintenance.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 30-05-2009       akureshi        Created
|| 26-05-2009       akureshi        Updated, added script for isFeatured
||
||
||
*/

    PROCEDURE publish_jma_app
         (
           p_solution_id            IN number,
           p_is_published           IN char,
           p_is_mobile_professional IN char,
           p_is_soho                IN char,
           p_is_sme                 IN char,
           p_is_enterprise          IN char,
           p_is_featured            IN char
         );

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_JMA_APP_PKG; -- Package Specification AIMS_JMA_APP_PKG
/



CREATE OR REPLACE Package Body AIMS_JMA_APP_PKG
IS

/* -------------------------------------------------------------------------------------------------------------  */


    PROCEDURE publish_jma_app
         (
           p_solution_id            IN number,
           p_is_published           IN char,
           p_is_mobile_professional IN char,
	   p_is_soho                IN char,
	   p_is_sme                 IN char,
           p_is_enterprise          IN char,
           p_is_featured            IN char
         )
    IS

    /*
    || Overview:        is_published = FALSE
    ||				Then in BDS (BDS_SOLUTION_MARKET_SEGMENTS table):
    ||					1.    Set is_public = FALSE for all Market Segments
    ||                                  2.    Set is_excluded = TRUE for all Market Segments
    ||
    ||			is_published = TRUE
    ||                         Then in BDS (BDS_SOLUTION_MARKET_SEGMENTS table):
    ||                                 1.     Set is_public = FALSE for all Market Segments
    ||                                 2.     Set is_public = TRUE for those Market Segments that have been checked in AIMS
    ||                                 3.     Set is_excluded = TRUE for all Market Segments
    ||                                 4.     Set is_excluded = FALSE for those Market Segments that have been checked in AIMS			
    ||
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 30-05-2009       akureshi        Created
    || 26-05-2009       akureshi        Updated, added script for isFeatured
    ||
    ||
    ||
    */


   BEGIN
    
    if(p_is_published = 'N') then
   	
   	  UPDATE bds_solution_market_segments
   	  SET
   		IS_PUBLIC = 'N',
   		IS_EXCLUDED ='Y'	
   	  WHERE SOLUTION_ID = p_solution_id; 			   
   	  				   
    end if;
    
    if(p_is_published = 'Y') then
        
    	UPDATE bds_solution_market_segments 
        SET
       	    IS_PUBLIC = 'N',
       	    IS_EXCLUDED ='Y'	
        WHERE SOLUTION_ID = p_solution_id;
    		  
     	if(p_is_mobile_professional = 'Y') then
     		
     		UPDATE bds_solution_market_segments 
		SET
		     IS_PUBLIC = 'Y',
		     IS_EXCLUDED ='N'	
        	WHERE SOLUTION_ID = p_solution_id and MARKET_SEGMENT_ID = 9;
     	end if;
     	
     	if(p_is_soho = 'Y') then
	     		
	     UPDATE bds_solution_market_segments 
	     SET
		IS_PUBLIC = 'Y',
		IS_EXCLUDED ='N'	
	    WHERE SOLUTION_ID = p_solution_id and MARKET_SEGMENT_ID = 10;
     	end if;
        
        if(p_is_sme = 'Y') then
		     		
	       UPDATE bds_solution_market_segments 
	       SET
	       	   IS_PUBLIC = 'Y',
		   IS_EXCLUDED ='N'	
	      WHERE SOLUTION_ID = p_solution_id and MARKET_SEGMENT_ID = 11;
     	end if;
     	
     	 if(p_is_enterprise = 'Y') then
			     		
		UPDATE bds_solution_market_segments 
		SET
			IS_PUBLIC = 'Y',
			IS_EXCLUDED ='N'	
		WHERE SOLUTION_ID = p_solution_id and MARKET_SEGMENT_ID = 12;
     	end if;
     	
    end if;
    
    /*script for isFeatured*/
     
     if(p_is_featured = 'Y') then
       	
       	  UPDATE bds_solution_market_segments
       	  SET
       		IS_FEATURED = 'Y'	
       	  WHERE SOLUTION_ID = p_solution_id; 			   
       	  				   
    end if;
    
     if(p_is_featured = 'N') then
           	
          UPDATE bds_solution_market_segments
          SET
           	IS_FEATURED = 'N'	
          WHERE SOLUTION_ID = p_solution_id; 			   
           	  				   
    end if;
   	  

   END publish_jma_app;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_JMA_APP_PKG; -- Package Body END AIMS_JMA_APP_PKG
/
CREATE TABLE AIMS_JMA_SALES_LEAD
(
  SALES_LEAD_ID                  NUMBER,
  JMA_PARTNER_ID                 NUMBER,
  CUSTOMER_NAME                  VARCHAR2(200 BYTE),
  REGION_ID                      NUMBER,
  SUB_REGION_ID                  NUMBER,
  CITY                           VARCHAR2(100 BYTE),
  STATE                          VARCHAR2(20 BYTE),
  ZIP_CODE                       VARCHAR2(20 BYTE),
  IS_NEW_VZW_CUSTOMER            CHAR(1 BYTE),
  NEW_VZW_CUSTOMER               VARCHAR2(4000 BYTE),
  SOLUTION_NAME                  VARCHAR2(200 BYTE),
  SOLUTION_DESCRIPTION           VARCHAR2(4000 BYTE),
  VERTICAL_ID                    NUMBER,
  DEVICE_NAME                    VARCHAR2(4000 BYTE),
  NUMBER_OF_DEVICES              VARCHAR2(100 BYTE),
  SOLUTION_COMMENTS              VARCHAR2(4000 BYTE),
  SOLUTION_TOTAL_SALES           VARCHAR2(100 BYTE),
  SALES_REP_FULL_NAME            VARCHAR2(100 BYTE),
  SALES_REP_EMAIL_ADDRESS        VARCHAR2(200 BYTE),
  SALES_REP_PHONE_NUMBER         VARCHAR2(100 BYTE),
  SALES_REP_CONTACT_INFORMATION  VARCHAR2(4000 BYTE),
  SALES_LEAD_STATUS              NUMBER,
  CLOSE_DATE                     DATE,
  SALES_LEAD_SUBMITTED_BY        NUMBER,
  CREATE_BY                      VARCHAR2(50 BYTE),
  CREATED_DATE                   DATE,
  LAST_UPDATED_BY                VARCHAR2(50 BYTE),
  LAST_UPDATED_DATE              DATE
)
/
ALTER TABLE AIMS_JMA_SALES_LEAD ADD (
  CONSTRAINT PK_AIMS_JMA_SALES_LEAD
 PRIMARY KEY (SALES_LEAD_ID))
/
ALTER TABLE AIMS_JMA_SALES_LEAD ADD (
  CONSTRAINT FK_PAERNER_ID_AIMS_ALLIANCES 
 FOREIGN KEY (JMA_PARTNER_ID) 
 REFERENCES AIMS_ALLIANCES (ALLIANCE_ID))
/
ALTER TABLE AIMS_JMA_SALES_LEAD ADD (
  CONSTRAINT FK_REGION_AIMS_TYPES 
 FOREIGN KEY (REGION_ID) 
 REFERENCES AIMS_TYPES (TYPE_ID))
/
ALTER TABLE AIMS_JMA_SALES_LEAD ADD (
  CONSTRAINT FK_SALES_STATUS_AIMS_TYPES 
 FOREIGN KEY (SALES_LEAD_STATUS) 
 REFERENCES AIMS_TYPES (TYPE_ID))
/
ALTER TABLE AIMS_JMA_SALES_LEAD ADD (
  CONSTRAINT FK_SUB_REGION_AIMS_TYPES 
 FOREIGN KEY (SUB_REGION_ID) 
 REFERENCES AIMS_TYPES (TYPE_ID))
/ 
ALTER TABLE AIMS_JMA_SALES_LEAD ADD (
  CONSTRAINT FK_SUBMITTED_BY_AIMS_TYPE 
 FOREIGN KEY (SALES_LEAD_SUBMITTED_BY) 
 REFERENCES AIMS_TYPES (TYPE_ID))
/
ALTER TABLE AIMS_JMA_SALES_LEAD ADD (
  CONSTRAINT FK_VERTICAL_AIMS_IND_FOCUS 
 FOREIGN KEY (VERTICAL_ID) 
 REFERENCES AIMS_INDUSTRY_FOCUS (INDUSTRY_ID))
/
CREATE TABLE AIMS_JMA_SALES_LEAD$AUD
(
  SALES_LEAD_ID                  NUMBER,
  JMA_PARTNER_ID                 NUMBER,
  CUSTOMER_NAME                  VARCHAR2(200 BYTE),
  REGION_ID                      NUMBER,
  SUB_REGION_ID                  NUMBER,
  CITY                           VARCHAR2(100 BYTE),
  STATE                          VARCHAR2(20 BYTE),
  ZIP_CODE                       VARCHAR2(20 BYTE),
  IS_NEW_VZW_CUSTOMER            CHAR(1 BYTE),
  NEW_VZW_CUSTOMER               VARCHAR2(4000 BYTE),
  SOLUTION_NAME                  VARCHAR2(200 BYTE),
  SOLUTION_DESCRIPTION           VARCHAR2(4000 BYTE),
  VERTICAL_ID                    NUMBER,
  DEVICE_NAME                    VARCHAR2(4000 BYTE),
  NUMBER_OF_DEVICES              VARCHAR2(100 BYTE),
  SOLUTION_COMMENTS              VARCHAR2(4000 BYTE),
  SOLUTION_TOTAL_SALES           VARCHAR2(100 BYTE),
  SALES_REP_FULL_NAME            VARCHAR2(100 BYTE),
  SALES_REP_EMAIL_ADDRESS        VARCHAR2(200 BYTE),
  SALES_REP_PHONE_NUMBER         VARCHAR2(100 BYTE),
  SALES_REP_CONTACT_INFORMATION  VARCHAR2(4000 BYTE),
  SALES_LEAD_STATUS              NUMBER,
  CLOSE_DATE                     DATE,
  SALES_LEAD_SUBMITTED_BY        NUMBER,
  CREATE_BY                      VARCHAR2(50 BYTE),
  CREATED_DATE                   DATE,
  LAST_UPDATED_BY                VARCHAR2(50 BYTE),
  LAST_UPDATED_DATE              DATE,
  AUD_ACTION                     CHAR(3 BYTE),
  AUD_TIMESTAMP                  DATE,
  AUD_USER                       VARCHAR2(30 BYTE))
/
CREATE OR REPLACE TRIGGER AIMS_JMA_SALES_LEAD$audtrg
   AFTER INSERT OR DELETE OR UPDATE
   ON AIMS_JMA_SALES_LEAD
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
      INSERT INTO AIMS_JMA_SALES_LEAD$aud (SALES_LEAD_ID,
                                           JMA_PARTNER_ID,
                                           CUSTOMER_NAME,
                                           REGION_ID,
                                           SUB_REGION_ID,
                                           CITY,
                                           STATE,
                                           ZIP_CODE,
                                           IS_NEW_VZW_CUSTOMER,
                                           NEW_VZW_CUSTOMER,
                                           SOLUTION_NAME,
                                           SOLUTION_DESCRIPTION,
                                           VERTICAL_ID,
                                           DEVICE_NAME,
                                           NUMBER_OF_DEVICES,
                                           SOLUTION_COMMENTS,
                                           SOLUTION_TOTAL_SALES,
                                           SALES_REP_FULL_NAME,
                                           SALES_REP_EMAIL_ADDRESS,
                                           SALES_REP_PHONE_NUMBER,
                                           SALES_REP_CONTACT_INFORMATION,
                                           SALES_LEAD_STATUS,
                                           CLOSE_DATE,
                                           SALES_LEAD_SUBMITTED_BY,
                                           CREATE_BY,
                                           CREATED_DATE,
                                           LAST_UPDATED_BY,
                                           LAST_UPDATED_DATE,
                                           aud_action,
                                           aud_timestamp,
                                           aud_user)
        VALUES   (:NEW.SALES_LEAD_ID,
                  :NEW.JMA_PARTNER_ID,
                  :NEW.CUSTOMER_NAME,
                  :NEW.REGION_ID,
                  :NEW.SUB_REGION_ID,
                  :NEW.CITY,
                  :NEW.STATE,
                  :NEW.ZIP_CODE,
                  :NEW.IS_NEW_VZW_CUSTOMER,
                  :NEW.NEW_VZW_CUSTOMER,
                  :NEW.SOLUTION_NAME,
                  :NEW.SOLUTION_DESCRIPTION,
                  :NEW.VERTICAL_ID,
                  :NEW.DEVICE_NAME,
                  :NEW.NUMBER_OF_DEVICES,
                  :NEW.SOLUTION_COMMENTS,
                  :NEW.SOLUTION_TOTAL_SALES,
                  :NEW.SALES_REP_FULL_NAME,
                  :NEW.SALES_REP_EMAIL_ADDRESS,
                  :NEW.SALES_REP_PHONE_NUMBER,
                  :NEW.SALES_REP_CONTACT_INFORMATION,
                  :NEW.SALES_LEAD_STATUS,
                  :NEW.CLOSE_DATE,
                  :NEW.SALES_LEAD_SUBMITTED_BY,
                  :NEW.CREATE_BY,
                  :NEW.CREATED_DATE,
                  :NEW.LAST_UPDATED_BY,
                  :NEW.LAST_UPDATED_DATE,
                  v_operation,
                  SYSDATE,
                  USER);
   ELSE
      INSERT INTO AIMS_JMA_SALES_LEAD$aud (SALES_LEAD_ID,
                                           JMA_PARTNER_ID,
                                           CUSTOMER_NAME,
                                           REGION_ID,
                                           SUB_REGION_ID,
                                           CITY,
                                           STATE,
                                           ZIP_CODE,
                                           IS_NEW_VZW_CUSTOMER,
                                           NEW_VZW_CUSTOMER,
                                           SOLUTION_NAME,
                                           SOLUTION_DESCRIPTION,
                                           VERTICAL_ID,
                                           DEVICE_NAME,
                                           NUMBER_OF_DEVICES,
                                           SOLUTION_COMMENTS,
                                           SOLUTION_TOTAL_SALES,
                                           SALES_REP_FULL_NAME,
                                           SALES_REP_EMAIL_ADDRESS,
                                           SALES_REP_PHONE_NUMBER,
                                           SALES_REP_CONTACT_INFORMATION,
                                           SALES_LEAD_STATUS,
                                           CLOSE_DATE,
                                           SALES_LEAD_SUBMITTED_BY,
                                           CREATE_BY,
                                           CREATED_DATE,
                                           LAST_UPDATED_BY,
                                           LAST_UPDATED_DATE,
                                           aud_action,
                                           aud_timestamp,
                                           aud_user)
        VALUES   (:OLD.SALES_LEAD_ID,
                  :OLD.JMA_PARTNER_ID,
                  :OLD.CUSTOMER_NAME,
                  :OLD.REGION_ID,
                  :OLD.SUB_REGION_ID,
                  :OLD.CITY,
                  :OLD.STATE,
                  :OLD.ZIP_CODE,
                  :OLD.IS_NEW_VZW_CUSTOMER,
                  :OLD.NEW_VZW_CUSTOMER,
                  :OLD.SOLUTION_NAME,
                  :OLD.SOLUTION_DESCRIPTION,
                  :OLD.VERTICAL_ID,
                  :OLD.DEVICE_NAME,
                  :OLD.NUMBER_OF_DEVICES,
                  :OLD.SOLUTION_COMMENTS,
                  :OLD.SOLUTION_TOTAL_SALES,
                  :OLD.SALES_REP_FULL_NAME,
                  :OLD.SALES_REP_EMAIL_ADDRESS,
                  :OLD.SALES_REP_PHONE_NUMBER,
                  :OLD.SALES_REP_CONTACT_INFORMATION,
                  :OLD.SALES_LEAD_STATUS,
                  :OLD.CLOSE_DATE,
                  :OLD.SALES_LEAD_SUBMITTED_BY,
                  :OLD.CREATE_BY,
                  :OLD.CREATED_DATE,
                  :OLD.LAST_UPDATED_BY,
                  :OLD.LAST_UPDATED_DATE,
                  v_operation,
                  SYSDATE,
                  USER);
   END IF;
END;
/
CREATE SEQUENCE SEQ_PK_JMA_SALES_LEAD
/
INSERT INTO AIMS_MENUS 
                  ( MENU_ID, MENU_NAME, MENU_URL, IMAGE_NAME, 
                    SORT_ORDER, CREATED_BY,CREATED_DATE, 
                    LAST_UPDATED_BY, LAST_UPDATED_DATE
                  ) 
           VALUES ( 
                   3001, 'Manage Sales Lead', '#', 'manage_sales_lead',
                   1525, 'system',SYSDATE, 
                   'system',SYSDATE)
/
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by,
             created_date, last_updated_by, last_updated_date, menu_id,
             sub_menu_id, privilege_key, available_to
            )
     VALUES (3001, 'Manage Sales Lead', 'Manage Sales Lead', 'system',
             SYSDATE, 'system', SYSDATE, 3001,
             NULL, 'MANAGE_SALES_LEAD', 'B'
            )
/
INSERT INTO aims_sub_menus
            (sub_menu_id, sub_menu_name, sub_menu_url,
             image_name, sort_order, created_by, created_date,
             last_updated_by, last_updated_date, menu_id
            )
     VALUES (3001, 'Submit Sales Lead', '/aims/entAppSalesLeadSetup.do!?task=create',
             'submit_sales_lead', 4161, 'system', SYSDATE,
             'system', SYSDATE, 3001
            )
/
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by,
             created_date, last_updated_by, last_updated_date, menu_id,
             sub_menu_id, privilege_key, available_to
            )
     VALUES (3002, 'Submit JMA Sales Lead', 'Submit JMA Sales Lead', 'system',
             SYSDATE, 'system', SYSDATE, NULL,
             3001, 'SUBMIT_JMA_SALES_LEAD', 'B'
            )
/
INSERT INTO aims_sub_menus
            (sub_menu_id, sub_menu_name, sub_menu_url,
             image_name, sort_order, created_by, created_date,
             last_updated_by, last_updated_date, menu_id
            )
     VALUES (3002, 'Sales Lead Sent', '/aims/entAppSalesLeadView.do!?view=sent',
             'sales_lead_sent', 4165, 'system', SYSDATE,
             'system', SYSDATE, 3001
            )
/
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by,
             created_date, last_updated_by, last_updated_date, menu_id,
             sub_menu_id, privilege_key, available_to
            )
     VALUES (3003, 'JMA Sales Lead Sent', 'JMA Sales Lead Sent', 'system',
             SYSDATE, 'system', SYSDATE, NULL,
             3002, 'JMA_SALES_LEAD_SENT', 'B'
            )
/
INSERT INTO aims_sub_menus
            (sub_menu_id, sub_menu_name, sub_menu_url,
             image_name, sort_order, created_by, created_date,
             last_updated_by, last_updated_date, menu_id
            )
     VALUES (3003, 'Sales Lead Received', '/aims/entAppSalesLeadView.do!?view=received',
             'sales_lead_received', 4170, 'system', SYSDATE,
             'system', SYSDATE, 3001
            )
/
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by,
             created_date, last_updated_by, last_updated_date, menu_id,
             sub_menu_id, privilege_key, available_to
            )
     VALUES (3004, 'JMA Sales Lead Received', 'JMA Sales Lead Received', 'system',
             SYSDATE, 'system', SYSDATE, NULL,
             3003, 'JMA_SALES_LEAD_RECEIVED', 'B'
            )
/
INSERT INTO aims_type_defs
            (type_def_id, type_name, type_description, created_by,
             created_date, last_updated_by, last_updated_date
            )
     VALUES (3000, 'SALES_LEAD_AREA', 'Sales Lead Area', 'system',
             SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3001, 3000, 'Northeast Area', 'Northeast Area', 2,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3005, 3000, 'Midwest Area', 'Midwest Area', 1,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3004, 3000, 'South Area', 'South Area', 3,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3006, 3000, 'West Area', 'West Area', 4,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_type_defs
            (type_def_id, type_name, type_description,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3001, 'SALES_LEAD_NORTH_EAST_REGION', 'Sales Lead North East Region',
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3021, 3001, 'DC/MD/VA', 'DC/MD/VA', 1,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3022, 3001, 'New England', 'New England', 2,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3023, 3001, 'NYC Metro', 'NYC Metro', 3,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3024, 3001, 'Philadelphia', 'Philadelphia', 4,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3025, 3001, 'Upstate New York', 'Upstate New York', 5,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_type_defs
            (type_def_id, type_name, type_description,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3004, 'SALES_LEAD_SOUTH_REGION', 'Sales Lead South Region',
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3026, 3004, 'Carolinas/Tennessee', 'Carolinas/Tennessee', 1,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3027, 3004, 'Central Texas', 'Central Texas', 2,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3028, 3004, 'Florida', 'Florida', 3,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3029, 3004, 'Georgia/Alabama', 'Georgia/Alabama', 4,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3030, 3004, 'Houston/Gulf Coast', 'Houston/Gulf Coast', 5,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_type_defs
            (type_def_id, type_name, type_description,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3005, 'SALES_LEAD_MID_WEST_REGION', 'Sales Lead Mid West Region',
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3031, 3005, 'Great Plains', 'Great Plains', 1,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3032, 3005, 'Illinois/Wisconsin', 'Illinois/Wisconsin', 2,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3033, 3005, 'Kansas/Missouri', 'Kansas/Missouri', 3,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3034, 3005, 'Michigan/Indiana/Kentucky', 'Michigan/Indiana/Kentucky', 4,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3035, 3005, 'Ohio/PA', 'Ohio/PA', 5,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_type_defs
            (type_def_id, type_name, type_description,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3006, 'SALES_LEAD_WEST_REGION', 'Sales Lead West Region',
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3036, 3006, 'Mountain/Dessert', 'Mountain/Dessert', 1,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3037, 3006, 'No. California/Nevada', 'No. California/Nevada', 2,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3038, 3006, 'Pacific Northwest', 'Pacific Northwest', 3,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3039, 3006, 'Southern California', 'Southern California', 4,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description, sort_order,
             created_by, created_date, last_updated_by, last_updated_date
            )
     VALUES (3040, 3006, 'Southwest', 'Southwest', 5,
             'system', SYSDATE, 'system', SYSDATE
            )
/
INSERT INTO aims_type_defs
            (type_def_id, type_name,
             type_description, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (3002, 'SALES_LEAD_STATUS',
             'Sales Lead Status', 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description,
             sort_order, created_by,
             created_date,
             last_updated_by,
             last_updated_date
            )
     VALUES (3041, 3002, 'Opportunity', 'Opportunity',
             1, 'system',
             sysdate,
             'system',
             sysdate
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description,
             sort_order, created_by,
             created_date,
             last_updated_by,
             last_updated_date
            )
     VALUES (3042, 3002, 'Win', 'Win',
             2, 'system',
             sysdate,
             'system',
             sysdate
            )
/
INSERT INTO aims_type_defs
            (type_def_id, type_name,
             type_description, created_by, created_date, last_updated_by,
             last_updated_date
            )
     VALUES (3003, 'SALES_LEAD_SUBMITTED_BY',
             'Sales Lead Submitted by', 'system', SYSDATE, 'system',
             SYSDATE
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description,
             sort_order, created_by,
             created_date,
             last_updated_by,
             last_updated_date
            )
     VALUES (3043, 3003, 'Verizon', 'Verizon',
             1, 'system',
             sysdate,
             'system',
             sysdate
            )
/
INSERT INTO aims_types
            (type_id, type_def_id, type_value, description,
             sort_order, created_by,
             created_date,
             last_updated_by,
             last_updated_date
            )
     VALUES (3044, 3003, 'Partner', 'Partner',
             2, 'system',
             sysdate,
             'system',
             sysdate
            )
/
INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (3001, 'JMA_SALES_LEAD_SUBMITTED_BY_ADMIN', 'JMA_SALES_LEAD_SUBMITTED_BY_ADMIN', SYSDATE,
             'system', SYSDATE, 'system'
            )
/
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (3001, 3001, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 3001
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 3001
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (70, 3001
            )
/
INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (3002, 'JMA_SALES_LEAD_UPDATED_BY_ADMIN', 'JMA_SALES_LEAD_UPDATED_BY_ADMIN', SYSDATE,
             'system', SYSDATE, 'system'
            )
/
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (3002, 3002, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 3002
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 3002
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (70, 3002
            )
/
INSERT INTO aims_events
            (event_id, event_name,
             event_desc, created_date, created_by, last_updated_date,
             last_updated_by
            )
     VALUES (3003, 'JMA_SALES_LEAD_CLOSED_BY_ADMIN',
             'JMA_SALES_LEAD_CLOSED_BY_ADMIN', SYSDATE, 'system', SYSDATE,
             'system'
            )
/
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (3003, 3003, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 3003
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 3003
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (70, 3003
            )
/
INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (3004, 'JMA_SALES_LEAD_SUBMITTED_BY_PARTNER', 'JMA_SALES_LEAD_SUBMITTED_BY_PARTNER', SYSDATE,
             'system', SYSDATE, 'system'
            )
/
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (3004, 3004, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 3004
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 3004
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (70, 3004
            )
/
INSERT INTO aims_events
            (event_id, event_name, event_desc, created_date,
             created_by, last_updated_date, last_updated_by
            )
     VALUES (3005, 'JMA_SALES_LEAD_UPDATED_BY_PARTNER', 'JMA_SALES_LEAD_UPDATED_BY_PARTNER', SYSDATE,
             'system', SYSDATE, 'system'
            )
/
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (3005, 3005, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 3005
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 3005
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (70, 3005
            )
/
INSERT INTO aims_events
            (event_id, event_name,
             event_desc, created_date, created_by, last_updated_date,
             last_updated_by
            )
     VALUES (3006, 'JMA_SALES_LEAD_CLOSED_BY_PARTNER',
             'JMA_SALES_LEAD_CLOSED_BY_PARTNER', SYSDATE, 'system', SYSDATE,
             'system'
            )
/
INSERT INTO aims_event_handlers
            (event_handler_id, event_id, class_name
            )
     VALUES (3006, 3006, 'com.netpace.aims.bo.events.ApplicationEventHandler'
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2, 3006
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 3006
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (70, 3006
            )
/
update BDS_PARTNER_MARKET_SEGMENTS set is_excluded = 'N'
/
alter table BDS_PARTNER_MARKET_SEGMENTS modify IS_EXCLUDED default 'N'
/
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by,
             created_date, last_updated_by, last_updated_date, menu_id,
             sub_menu_id, privilege_key, available_to
            )
     VALUES (3005, 'JMA Application Spotlight View', 'JMA Application Spotlight View', 'system',
             SYSDATE, 'system', SYSDATE, NULL,
             NULL, 'JMA_APPLICATION_SPOTLIGHT_VIEW', 'V'
            )
/
INSERT INTO report
            (report_id, NAME,
             description, report_file, pdf_export,
             csv_export, xls_export, html_export, report_query,
             datasource_id, chart_id
            )
     VALUES (3001, 'JMA Partners Contract Report',
             'Displays all JMA Partners, by Active JMA Contracts', 'JMAContractsReport.jasper', 0,
             1, 1, 1, NULL,
             7, NULL
            )
/
INSERT INTO report
            (report_id, NAME, description,
             report_file, pdf_export, csv_export, xls_export, html_export,
             report_query, datasource_id, chart_id
            )
     VALUES (3002, 'Verizon Users By Role', 'Displays all Verizon Users that are tied to JMA specific roles like (JMA Super Admin, JMA Admin and JMA Sales)',
             'VerizonUsersByRoleReport.jasper', 0, 1, 1, 1,
             NULL, 7, NULL
            )
/
INSERT INTO report
            (report_id, NAME,
             description,
             report_file, pdf_export, csv_export, xls_export, html_export,
             report_query, datasource_id, chart_id
            )
     VALUES (3003, 'No.of Registered Alliances/Partners',
             'Displays all Registered JMA Alliances and Partners along with their Registration Date',
             'NoOfRegisteredAlliancesPartners.jasper', 0, 1, 1, 1,
             NULL, 7, NULL
            )
/
INSERT INTO report_parameter
            (parameter_id, NAME, TYPE, classname,
             DATA, datasource_id, description, required, multi_select
            )
     VALUES (3001, 'jma_users', 'List', 'java.lang.String',
             'JMA Super Admin|JMA Admin|JMA Sales', 7, 'JMA User', 1, 1
            )
/
INSERT INTO report_parameter
            (parameter_id, NAME, TYPE, classname,
             DATA, datasource_id, description, required, multi_select
            )
     VALUES (3002, 'ALLIANCE_TYPE', 'List', 'java.lang.String',
             'Alliance|Partner|Both', 7, 'Alliance Type', 1, 0
            )
/
ALTER TABLE AIMS_ALLIANCE_JMA
ADD
(JMA_INFO_COMPLETION_DATE              DATE)
/
ALTER TABLE AIMS_ALLIANCE_JMA$AUD
ADD
(JMA_INFO_COMPLETION_DATE              DATE)
/
CREATE OR REPLACE TRIGGER aims_alliance_jma$audtrg
   AFTER INSERT OR DELETE OR UPDATE
   ON aims_alliance_jma
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
      INSERT INTO aims_alliance_jma$aud (alliance_jma_id,
                                         alliance_id,
                                         company_short_description,
                                         faq,
                                         reason_for_relationship_vzw,
                                         list_existing_contract,
                                         option_to_go_vzw,
                                         option_to_go_vzw_flag,
                                         sales_engagements_vzw,
                                         sales_engagements_vzw_flag,
                                         product_menu,
                                         win_opportunities_file_name,
                                         win_opportunities_content_type,
                                         is_any_product_use_vzw_vz_nt,
                                         is_product_certified_vzw,
                                         is_odi_process,
                                         brief_description,
                                         product_information,
                                         solution_reside,
                                         is_product_required_lbs,
                                         is_offer_bobo_services,
                                         prod_presentation_file_name,
                                         prod_presentation_content_type,
                                         created_by,
                                         created_date,
                                         last_updated_by,
                                         last_updated_date,
                                         JMA_INFO_COMPLETION_DATE,
                                         aud_action,
                                         aud_timestamp,
                                         aud_user)
        VALUES   (:NEW.alliance_jma_id,
                  :NEW.alliance_id,
                  :NEW.company_short_description,
                  :NEW.faq,
                  :NEW.reason_for_relationship_vzw,
                  :NEW.list_existing_contract,
                  :NEW.option_to_go_vzw,
                  :NEW.option_to_go_vzw_flag,
                  :NEW.sales_engagements_vzw,
                  :NEW.sales_engagements_vzw_flag,
                  :NEW.product_menu,
                  :NEW.win_opportunities_file_name,
                  :NEW.win_opportunities_content_type,
                  :NEW.is_any_product_use_vzw_vz_nt,
                  :NEW.is_product_certified_vzw,
                  :NEW.is_odi_process,
                  :NEW.brief_description,
                  :NEW.product_information,
                  :NEW.solution_reside,
                  :NEW.is_product_required_lbs,
                  :NEW.is_offer_bobo_services,
                  :NEW.prod_presentation_file_name,
                  :NEW.prod_presentation_content_type,
                  :NEW.created_by,
                  :NEW.created_date,
                  :NEW.last_updated_by,
                  :NEW.last_updated_date,
                  :NEW.JMA_INFO_COMPLETION_DATE,
                  v_operation,
                  SYSDATE,
                  USER);
   ELSE
      INSERT INTO aims_alliance_jma$aud (alliance_jma_id,
                                         alliance_id,
                                         company_short_description,
                                         faq,
                                         reason_for_relationship_vzw,
                                         list_existing_contract,
                                         option_to_go_vzw,
                                         option_to_go_vzw_flag,
                                         sales_engagements_vzw,
                                         sales_engagements_vzw_flag,
                                         product_menu,
                                         win_opportunities_file_name,
                                         win_opportunities_content_type,
                                         is_any_product_use_vzw_vz_nt,
                                         is_product_certified_vzw,
                                         is_odi_process,
                                         brief_description,
                                         product_information,
                                         solution_reside,
                                         is_product_required_lbs,
                                         is_offer_bobo_services,
                                         prod_presentation_file_name,
                                         prod_presentation_content_type,
                                         created_by,
                                         created_date,
                                         last_updated_by,
                                         last_updated_date,
                                         JMA_INFO_COMPLETION_DATE,
                                         aud_action,
                                         aud_timestamp,
                                         aud_user)
        VALUES   (:OLD.alliance_jma_id,
                  :OLD.alliance_id,
                  :OLD.company_short_description,
                  :OLD.faq,
                  :OLD.reason_for_relationship_vzw,
                  :OLD.list_existing_contract,
                  :OLD.option_to_go_vzw,
                  :OLD.option_to_go_vzw_flag,
                  :OLD.sales_engagements_vzw,
                  :OLD.sales_engagements_vzw_flag,
                  :OLD.product_menu,
                  :OLD.win_opportunities_file_name,
                  :OLD.win_opportunities_content_type,
                  :OLD.is_any_product_use_vzw_vz_nt,
                  :OLD.is_product_certified_vzw,
                  :OLD.is_odi_process,
                  :OLD.brief_description,
                  :OLD.product_information,
                  :OLD.solution_reside,
                  :OLD.is_product_required_lbs,
                  :OLD.is_offer_bobo_services,
                  :OLD.prod_presentation_file_name,
                  :OLD.prod_presentation_content_type,
                  :OLD.created_by,
                  :OLD.created_date,
                  :OLD.last_updated_by,
                  :OLD.last_updated_date,
                  :OLD.JMA_INFO_COMPLETION_DATE,
                  v_operation,
                  SYSDATE,
                  USER);
   END IF;
END;
/
INSERT INTO aims_ent_apps_market_seg_info
            (type_id, enterprise_apps_id)
   SELECT   DECODE (bm.market_segment_id,
                    9, 161,
                    10, 162,
                    11, 163,
                    12, 164,
                    bm.market_segment_id
                   ) marketid,
            bd.solution_id
       FROM bds_solution_market_segments bd,
            aims_apps ap,
            bds_market_segments bm
      WHERE bd.solution_id = ap.apps_id
        AND bm.market_segment_id = bd.market_segment_id
        AND bd.is_excluded = 'N'
   ORDER BY 1
/
UPDATE aims_enterprise_apps ak
   SET ak.is_featured = 'Y'
 WHERE ak.enterprise_apps_id IN (
                    SELECT   bd.solution_id
                        FROM bds_solution_market_segments bd, aims_apps ap
                       WHERE bd.solution_id = ap.apps_id
                         AND bd.is_featured = 'Y'
                    GROUP BY bd.solution_id
                      HAVING COUNT (*) >= 4)
/

DELETE FROM   AIMS_APP_SUB_CATEGORIES aasc
      WHERE   aasc.CATEGORY_ID IN (SELECT   aac.CATEGORY_ID
                                     FROM   Aims_App_Categories aac
                                    WHERE   aac.PLATFORM_ID = 44);
    
DELETE FROM   Aims_App_Categories aac
      WHERE   aac.PLATFORM_ID = 44;
      
DELETE FROM   AIMS_LIFECYCLE_PHASES alp
      WHERE   alp.PHASE_ID IN
                    (2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009);


DELETE FROM AIMS_PLATFORMS ap
      WHERE ap.PLATFORM_ID = 44;


set escape off