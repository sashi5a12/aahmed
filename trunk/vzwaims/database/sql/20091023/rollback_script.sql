ALTER TABLE AIMS_ALLIANCES
 DROP (REMIT_STATE,  
       REMIT_TO,
       REMIT_ADDRESS1,
       REMIT_ADDRESS2,
       REMIT_CITY,
       REMIT_POSTAL_CODE,
       REMIT_COUNTRY);
 
ALTER TABLE AIMS_ALLIANCES$AUD 
 DROP (REMIT_STATE,
       REMIT_TO,
       REMIT_ADDRESS1,
       REMIT_ADDRESS2,
       REMIT_CITY,
       REMIT_POSTAL_CODE,
       REMIT_COUNTRY);
       
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
                   v_operation, SYSDATE, USER);
   END IF;
END;
/
DELETE FROM aims_event_handlers 
  WHERE event_handler_id = 2102 
    AND event_id = 2102
    AND class_name = 'com.netpace.aims.bo.events.ApplicationEventHandler' 
/
DELETE FROM aims_event_place_holders
    WHERE place_holder_id = 6
      AND event_id = 2102
/
DELETE FROM aims_event_place_holders
   WHERE place_holder_id = 2001 
     AND event_id = 2102 
/
DELETE FROM aims_event_place_holders
  WHERE place_holder_id = 2002 
   AND  event_id = 2102
/
DELETE FROM AIMS_NOTIF_AD_HOC_RECIPIENTS AR
  WHERE AR.NOTIFICATION_ID IN ( SELECT NOTIFICATION_ID 
                                  FROM AIMS_EVENT_NOTIFICATIONS
                                 WHERE EVENT_ID = 2102 )
/
DELETE FROM AIMS_NOTIFICATION_ROLES ANR
  WHERE ANR.NOTIFICATION_ID IN ( SELECT NOTIFICATION_ID 
                                   FROM AIMS_EVENT_NOTIFICATIONS
                                  WHERE EVENT_ID = 2102 )
/
DELETE FROM AIMS_NOTIF_OPT_IN_RECIPIENTS 
 WHERE NOTIFICATION_ID IN ( SELECT NOTIFICATION_ID 
                                  FROM AIMS_EVENT_NOTIFICATIONS
                                 WHERE EVENT_ID = 2102 )
/ 
DELETE FROM AIMS_EVENT_NOTIFICATIONS
 WHERE EVENT_ID = 2102
/
DELETE FROM aims_events 
 WHERE event_id = 2102
/  
UPDATE aims_types
SET type_value = 'APPLICATION'
, description = 'APPLICATION'
WHERE type_id = 301
/      
UPDATE AIMS_JAVA_APPS$AUD
   SET APP_KEYWORD = NULL
/
ALTER TABLE AIMS_JAVA_APPS$AUD
MODIFY(APP_KEYWORD VARCHAR2(50))
/
DECLARE

   CURSOR BUS_FUN_TST IS
        SELECT * FROM AIMS_JAVA_APPS$AUD_TEMP
         WHERE JAVA_APPS_ID IN (SELECT JAVA_APPS_ID FROM AIMS_JAVA_APPS$AUD);

BEGIN

 FOR A IN BUS_FUN_TST LOOP

   UPDATE AIMS_JAVA_APPS$AUD
      SET APP_KEYWORD = A.APP_KEYWORD
    WHERE JAVA_APPS_ID =   A.JAVA_APPS_ID;
  COMMIT;

  END LOOP;

END;
/
UPDATE AIMS_JAVA_APPS
   SET APP_KEYWORD = NULL
/
ALTER TABLE AIMS_JAVA_APPS
MODIFY(APP_KEYWORD VARCHAR2(50))
/
DECLARE

   CURSOR BUS_FUN_TST IS
        SELECT * FROM AIMS_JAVA_APPS_TEMP
         WHERE JAVA_APPS_ID IN (SELECT JAVA_APPS_ID FROM AIMS_JAVA_APPS);

BEGIN

 FOR A IN BUS_FUN_TST LOOP

   UPDATE AIMS_JAVA_APPS
      SET APP_KEYWORD = A.APP_KEYWORD
    WHERE JAVA_APPS_ID =   A.JAVA_APPS_ID;
  COMMIT;

  END LOOP;

END;
/