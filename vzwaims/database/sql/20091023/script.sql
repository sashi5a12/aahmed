ALTER TABLE AIMS_ALLIANCES
 ADD (REMIT_STATE  VARCHAR2(50));

ALTER TABLE AIMS_ALLIANCES
 ADD (REMIT_TO  VARCHAR2(50));

ALTER TABLE AIMS_ALLIANCES
 ADD (REMIT_ADDRESS1  VARCHAR2(200));

ALTER TABLE AIMS_ALLIANCES
 ADD (REMIT_ADDRESS2  VARCHAR2(200));

ALTER TABLE AIMS_ALLIANCES
 ADD (REMIT_CITY  VARCHAR2(50));

ALTER TABLE AIMS_ALLIANCES
 ADD (REMIT_POSTAL_CODE  VARCHAR2(9));

ALTER TABLE AIMS_ALLIANCES
 ADD (REMIT_COUNTRY  VARCHAR2(50));
 
ALTER TABLE AIMS_ALLIANCES$AUD 
  ADD (REMIT_STATE  VARCHAR2(50),
       REMIT_TO  VARCHAR2(50),
       REMIT_ADDRESS1  VARCHAR2(200),
       REMIT_ADDRESS2  VARCHAR2(200),
       REMIT_CITY  VARCHAR2(50),
       REMIT_POSTAL_CODE  VARCHAR2(9),
       REMIT_COUNTRY  VARCHAR2(50));
       
CREATE OR REPLACE TRIGGER aims_alliances$audtrg
AFTER INSERT  OR  DELETE  OR UPDATE 
ON aims_alliances
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
      INSERT INTO aims_alliances$aud
                  (alliance_id, company_name,
                   company_legal_name, auth_rep_name,
                   alliance_reg_date, street_address_1,
                   steet_address_2, state, zip_code,
                   country, state_of_inc, web_site_url,
                   other_carrier_alliances, prev_year_revenues,
                   db_number, is_finance_info_public,
                   vzw_current_contracts, vzw_relationship_reasons,
                   date_established, num_full_time_emp,
                   num_part_time_emp, num_inside_sales_emp,
                   num_out_sales_emp, num_retail_emp,
                   num_tele_mktg_emp, num_technician_emp,
                   num_warehouse_emp, num_supp_clerical_emp,
                   num_other_emp, company_country_of_origin,
                   prod_and_services_desc, competetive_advantages,
                   status, admin_user_id, vzw_account_manager,
                   bus_contact_id, mktg_pr_contact_id,
                   tech_contact_id, executive_contact_id,
                   sales_contact_id, created_by, created_date,
                   last_updated_by, last_updated_date, city,
                   company_present_file_name,
                   company_present_content_type,
                   company_logo_file_name,
                   company_logo_content_type,
                   comm_other_carrier_alliances,
                   other_industry_focus, is_on_hold,
                   prog_guide_file_name, prog_guide_content_type,
                   escalation_instructions, vendor_id,
                   is_vcast_music_alliance, partner_brand,
                   outsource_dev_publihser_name, employees_range,mportal_alliance_name,
                   IS_JMA_ALLIANCE,IS_JMA_INFO_COMPLETE,YEAR_COMPANY_FOUNDED,
                   REMIT_STATE,REMIT_TO,REMIT_ADDRESS1,
                   REMIT_ADDRESS2,REMIT_CITY,REMIT_POSTAL_CODE,REMIT_COUNTRY,
                   aud_action, aud_timestamp, aud_user)
           VALUES (:NEW.alliance_id, :NEW.company_name,
                   :NEW.company_legal_name, :NEW.auth_rep_name,
                   :NEW.alliance_reg_date, :NEW.street_address_1,
                   :NEW.steet_address_2, :NEW.state, :NEW.zip_code,
                   :NEW.country, :NEW.state_of_inc, :NEW.web_site_url,
                   :NEW.other_carrier_alliances, :NEW.prev_year_revenues,
                   :NEW.db_number, :NEW.is_finance_info_public,
                   :NEW.vzw_current_contracts, :NEW.vzw_relationship_reasons,
                   :NEW.date_established, :NEW.num_full_time_emp,
                   :NEW.num_part_time_emp, :NEW.num_inside_sales_emp,
                   :NEW.num_out_sales_emp, :NEW.num_retail_emp,
                   :NEW.num_tele_mktg_emp, :NEW.num_technician_emp,
                   :NEW.num_warehouse_emp, :NEW.num_supp_clerical_emp,
                   :NEW.num_other_emp, :NEW.company_country_of_origin,
                   :NEW.prod_and_services_desc, :NEW.competetive_advantages,
                   :NEW.status, :NEW.admin_user_id, :NEW.vzw_account_manager,
                   :NEW.bus_contact_id, :NEW.mktg_pr_contact_id,
                   :NEW.tech_contact_id, :NEW.executive_contact_id,
                   :NEW.sales_contact_id, :NEW.created_by, :NEW.created_date,
                   :NEW.last_updated_by, :NEW.last_updated_date, :NEW.city,
                   :NEW.company_present_file_name,
                   :NEW.company_present_content_type,
                   :NEW.company_logo_file_name,
                   :NEW.company_logo_content_type,
                   :NEW.comm_other_carrier_alliances,
                   :NEW.other_industry_focus, :NEW.is_on_hold,
                   :NEW.prog_guide_file_name, :NEW.prog_guide_content_type,
                   :NEW.escalation_instructions, :NEW.vendor_id,
                   :NEW.is_vcast_music_alliance, :NEW.partner_brand,
                   :NEW.outsource_dev_publihser_name, :NEW.employees_range,:NEW.mportal_alliance_name,
                   :NEW.IS_JMA_ALLIANCE,:NEW.IS_JMA_INFO_COMPLETE,:NEW.YEAR_COMPANY_FOUNDED,
                   :NEW.REMIT_STATE,:NEW.REMIT_TO,:NEW.REMIT_ADDRESS1,
                   :NEW.REMIT_ADDRESS2,:NEW.REMIT_CITY,:NEW.REMIT_POSTAL_CODE,:NEW.REMIT_COUNTRY,
                   v_operation, SYSDATE, USER);
   ELSE
      INSERT INTO aims_alliances$aud
                  (alliance_id, company_name,
                   company_legal_name, auth_rep_name,
                   alliance_reg_date, street_address_1,
                   steet_address_2, state, zip_code,
                   country, state_of_inc, web_site_url,
                   other_carrier_alliances, prev_year_revenues,
                   db_number, is_finance_info_public,
                   vzw_current_contracts, vzw_relationship_reasons,
                   date_established, num_full_time_emp,
                   num_part_time_emp, num_inside_sales_emp,
                   num_out_sales_emp, num_retail_emp,
                   num_tele_mktg_emp, num_technician_emp,
                   num_warehouse_emp, num_supp_clerical_emp,
                   num_other_emp, company_country_of_origin,
                   prod_and_services_desc, competetive_advantages,
                   status, admin_user_id, vzw_account_manager,
                   bus_contact_id, mktg_pr_contact_id,
                   tech_contact_id, executive_contact_id,
                   sales_contact_id, created_by, created_date,
                   last_updated_by, last_updated_date, city,
                   company_present_file_name,
                   company_present_content_type,
                   company_logo_file_name,
                   company_logo_content_type,
                   comm_other_carrier_alliances,
                   other_industry_focus, is_on_hold,
                   prog_guide_file_name, prog_guide_content_type,
                   escalation_instructions, vendor_id,
                   is_vcast_music_alliance, partner_brand,
                   outsource_dev_publihser_name, employees_range,mportal_alliance_name,
                   IS_JMA_ALLIANCE,IS_JMA_INFO_COMPLETE,YEAR_COMPANY_FOUNDED,
                   REMIT_STATE,REMIT_TO,REMIT_ADDRESS1,
                   REMIT_ADDRESS2,REMIT_CITY,REMIT_POSTAL_CODE,REMIT_COUNTRY,
                   aud_action, aud_timestamp, aud_user)
           VALUES (:OLD.alliance_id, :OLD.company_name,
                   :OLD.company_legal_name, :OLD.auth_rep_name,
                   :OLD.alliance_reg_date, :OLD.street_address_1,
                   :OLD.steet_address_2, :OLD.state, :OLD.zip_code,
                   :OLD.country, :OLD.state_of_inc, :OLD.web_site_url,
                   :OLD.other_carrier_alliances, :OLD.prev_year_revenues,
                   :OLD.db_number, :OLD.is_finance_info_public,
                   :OLD.vzw_current_contracts, :OLD.vzw_relationship_reasons,
                   :OLD.date_established, :OLD.num_full_time_emp,
                   :OLD.num_part_time_emp, :OLD.num_inside_sales_emp,
                   :OLD.num_out_sales_emp, :OLD.num_retail_emp,
                   :OLD.num_tele_mktg_emp, :OLD.num_technician_emp,
                   :OLD.num_warehouse_emp, :OLD.num_supp_clerical_emp,
                   :OLD.num_other_emp, :OLD.company_country_of_origin,
                   :OLD.prod_and_services_desc, :OLD.competetive_advantages,
                   :OLD.status, :OLD.admin_user_id, :OLD.vzw_account_manager,
                   :OLD.bus_contact_id, :OLD.mktg_pr_contact_id,
                   :OLD.tech_contact_id, :OLD.executive_contact_id,
                   :OLD.sales_contact_id, :OLD.created_by, :OLD.created_date,
                   :OLD.last_updated_by, :OLD.last_updated_date, :OLD.city,
                   :OLD.company_present_file_name,
                   :OLD.company_present_content_type,
                   :OLD.company_logo_file_name,
                   :OLD.company_logo_content_type,
                   :OLD.comm_other_carrier_alliances,
                   :OLD.other_industry_focus, :OLD.is_on_hold,
                   :OLD.prog_guide_file_name, :OLD.prog_guide_content_type,
                   :OLD.escalation_instructions, :OLD.vendor_id,
                   :OLD.is_vcast_music_alliance, :OLD.partner_brand,
                   :OLD.outsource_dev_publihser_name, :OLD.employees_range,:OLD.mportal_alliance_name,
                   :OLD.IS_JMA_ALLIANCE,:OLD.IS_JMA_INFO_COMPLETE,:OLD.YEAR_COMPANY_FOUNDED,
                   :OLD.REMIT_STATE,:OLD.REMIT_TO,:OLD.REMIT_ADDRESS1,
                   :OLD.REMIT_ADDRESS2,:OLD.REMIT_CITY,:OLD.REMIT_POSTAL_CODE,:OLD.REMIT_COUNTRY,
                   v_operation, SYSDATE, USER);
   END IF;
END;
/
INSERT INTO aims_events (event_id,
                         event_name,
                         event_desc,
                         created_date,
                         created_by,
                         last_updated_date,
                         last_updated_by)
  VALUES   (2102,
            'SETTLEMENT_SERVICE_FOLLOWUP',
            'SETTLEMENT_SERVICE_FOLLOWUP',
            SYSDATE,
            'system',
            SYSDATE,
            'system')
/
INSERT INTO aims_event_handlers (event_handler_id, event_id, class_name)
  VALUES   (2102, 2102, 'com.netpace.aims.bo.events.ApplicationEventHandler')
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (6, 2102
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2001, 2102
            )
/
INSERT INTO aims_event_place_holders
            (place_holder_id, event_id
            )
     VALUES (2002, 2102
            )
/
UPDATE aims_types
SET type_value = 'APPLICATION'
, description = 'APPLICATN'
WHERE type_id = 301
/
CREATE TABLE AIMS_JAVA_APPS_TEMP AS 
SELECT JAVA_APPS_ID,APP_KEYWORD
  FROM AIMS_JAVA_APPS
/
ALTER TABLE AIMS_JAVA_APPS
  ADD (TEMP_KW VARCHAR2(40))
/  
UPDATE AIMS_JAVA_APPS
   SET TEMP_KW = SUBSTR(APP_KEYWORD,1,40)
/
UPDATE AIMS_JAVA_APPS
   SET APP_KEYWORD = NULL
/
ALTER TABLE AIMS_JAVA_APPS
MODIFY(APP_KEYWORD VARCHAR2(40))
/
UPDATE AIMS_JAVA_APPS
   SET APP_KEYWORD = TEMP_KW
/
ALTER TABLE AIMS_JAVA_APPS
 DROP (TEMP_KW)
/
CREATE TABLE AIMS_JAVA_APPS$AUD_TEMP AS 
SELECT JAVA_APPS_ID,APP_KEYWORD
  FROM AIMS_JAVA_APPS$AUD
/
ALTER TABLE AIMS_JAVA_APPS$AUD
  ADD (TEMP_KW VARCHAR2(40))
/  
UPDATE AIMS_JAVA_APPS$AUD
   SET TEMP_KW = SUBSTR(APP_KEYWORD,1,40)
/
UPDATE AIMS_JAVA_APPS$AUD
   SET APP_KEYWORD = NULL
/
ALTER TABLE AIMS_JAVA_APPS$AUD
MODIFY(APP_KEYWORD VARCHAR2(40))
/
UPDATE AIMS_JAVA_APPS$AUD
   SET APP_KEYWORD = TEMP_KW
/
ALTER TABLE AIMS_JAVA_APPS$AUD
 DROP (TEMP_KW)
/
Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (148, 8, 'OTHER', 'OTHER', 107, 'SYSTEM', sysdate, 'SYSTEM', sysdate) 
/   
Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (149, 8, 'DON''T HAVE AN AGGREGATOR', 'DON''T HAVE AN AGGREGATOR', 108, 'SYSTEM', sysdate, 'SYSTEM', sysdate)
/
Insert into AIMS_TYPES
   (TYPE_ID, TYPE_DEF_ID, TYPE_VALUE, DESCRIPTION, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (150, 8, 'ALLONE MOBILE', 'ALLONE MOBILE', 109, 'SYSTEM', sysdate, 'SYSTEM', sysdate)
/     
Update AIMS_TAX_CATEGORY_CODES
set TAX_CATEGORY_CODE_DESC = 'News/Information Services'
where TAX_CATEGORY_CODE = 'A08'
/
Update AIMS_TAX_CATEGORY_CODES
set TAX_CATEGORY_CODE_DESC = 'Video Works'
where TAX_CATEGORY_CODE = 'A16'
/
 
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (18, 'A17', 'Location Base Svc', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (19, 'A18', 'Data Processing', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (20, 'A19', 'Tangible Personal Property', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (21, 'A20', 'Mobile TV', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (22, 'A21', 'Antenna', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (23, 'A22', 'Valtus', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (24, 'A23', 'Econz', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (25, 'A24', 'OTA Music', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (26, 'A25', 'PC Download Music', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (27, 'A26', 'Non Taxable', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (28, 'A27', 'Visual Voicemail', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (29, 'A28', 'Digital Books', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (30, 'A29', 'PIX-MMS', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (31, 'A30', 'Enhanced Svc', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/
Insert into AIMS_TAX_CATEGORY_CODES
   (TAX_CATEGORY_CODE_ID, TAX_CATEGORY_CODE, TAX_CATEGORY_CODE_DESC, STATUS, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE)
 Values
   (32, 'A31', 'Audio-Visual Works', 'ACTIVE', 'system', sysdate, 'system', sysdate)
/   
UPDATE   aims_alliance_contracts
   SET   contract_id = 551 
 WHERE   contract_id = 552
   AND   alliance_id NOT IN (SELECT   DISTINCT alliance_id
                               FROM   aims_alliance_contracts
                              WHERE   contract_id = 551);

DELETE FROM AIMS_ALLIANCE_CONTRACT_AMENDS s
      WHERE S.ALLIANCE_CONTRACT_ID IN
                    (SELECT   ALLIANCE_CONTRACT_ID
                       FROM   aims_alliance_contracts
                      WHERE   contract_id = 552
                        AND   alliance_id IN
                                       (SELECT   DISTINCT alliance_id
                                          FROM   aims_alliance_contracts
                                         WHERE   contract_id = 551));
                                         
DELETE FROM aims_alliance_contracts
      WHERE contract_id = 552
        AND alliance_id IN (SELECT   DISTINCT alliance_id
                                FROM   aims_alliance_contracts
                               WHERE   contract_id = 551);  
                          
UPDATE aims_tool_contracts
   SET contract_id = 551
 WHERE contract_id = 552
   AND tool_id NOT IN (SELECT DISTINCT tool_id
                           FROM aims_tool_contracts
                          WHERE contract_id = 551);
                          
                          
DELETE FROM aims_tool_contracts
      WHERE contract_id = 552
        AND tool_id IN  (SELECT   DISTINCT tool_id
                             FROM   aims_tool_contracts
                            WHERE   contract_id = 551);                            
 

UPDATE aims_java_apps
   SET contracT_id = 551
 where contracT_id = 552;
                               
UPDATE AIMS_CONTRACT_AMENDMENTS
   SET CONTRACT_ID = 551 
 WHERE CONTRACT_ID = 552
   AND ADDENDUM_ID NOT IN (SELECT   DISTINCT ADDENDUM_ID
                             FROM   AIMS_CONTRACT_AMENDMENTS
                            WHERE   CONTRACT_ID = 551);
                                                                                 
DELETE FROM AIMS_CONTRACT_AMENDMENTS
 WHERE CONTRACT_ID = 552
   AND ADDENDUM_ID IN (SELECT   DISTINCT ADDENDUM_ID
                         FROM   AIMS_CONTRACT_AMENDMENTS
                        WHERE   CONTRACT_ID = 551);
                           
UPDATE AIMS_EMAIL_GROUP_CONTRACT_IDS
   SET CONTRACT_ID = 551
 WHERE CONTRACT_ID = 552
   AND GROUP_ID NOT IN (SELECT   DISTINCT GROUP_ID
                          FROM   AIMS_EMAIL_GROUP_CONTRACT_IDS
                         WHERE   CONTRACT_ID = 551);    
                                                 
DELETE FROM AIMS_EMAIL_GROUP_CONTRACT_IDS
 WHERE CONTRACT_ID = 552
   AND GROUP_ID IN     (SELECT   DISTINCT GROUP_ID
                          FROM   AIMS_EMAIL_GROUP_CONTRACT_IDS
                         WHERE   CONTRACT_ID = 551);
                         
UPDATE aims_java_apps
   SET contracT_id = 551
 WHERE contracT_id = 552;                         
                                                                         
DELETE FROM aims_contracts
      WHERE CONTRACT_ID = 552 
        AND CONTRACT_REF_ID = '6000144';