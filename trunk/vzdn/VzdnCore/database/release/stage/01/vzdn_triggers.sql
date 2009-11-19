USE vzdn;

DROP TRIGGER IF EXISTS vzdn_dev_care_categoriestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_categoriestrgins AFTER INSERT ON vzdn_dev_care_categories
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_categories$aud (category_id,category_name,created_by,created_date,updated_by,updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.category_id,NEW.category_name,NEW.created_by,NEW.created_date,NEW.updated_by,NEW.updated_date,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_dev_care_issuestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_issuestrgins AFTER INSERT ON vzdn_dev_care_issues
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_issues$aud (issue_id,title,description,category_id,ticket_id,user_id,attachment_name,sub_category_id,created_by,created_date,updated_by,updated_date,attachment_type,type_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.issue_id,NEW.title,NEW.description,NEW.category_id,NEW.ticket_id,NEW.user_id,NEW.attachment_name,NEW.sub_category_id,NEW.created_by,NEW.created_date,NEW.updated_by,NEW.updated_date,NEW.attachment_type,NEW.type_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_dev_care_life_cycletrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_life_cycletrgins AFTER INSERT ON vzdn_dev_care_life_cycle
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_life_cycle$aud (comment_id,comments,user_id,created_by,created_date,updated_by,updated_date,issue_id,type_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.comment_id,NEW.comments,NEW.user_id,NEW.created_by,NEW.created_date,NEW.updated_by,NEW.updated_date,NEW.issue_id,NEW.type_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_dev_care_sub_categoriestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_sub_categoriestrgins AFTER INSERT ON vzdn_dev_care_sub_categories
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_sub_categories$aud (sub_category_id,sub_category_name,created_by,category_id,updated_by,updated_date,created_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.sub_category_id,NEW.sub_category_name,NEW.created_by,NEW.category_id,NEW.updated_by,NEW.updated_date,NEW.created_date,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_device_anywheretrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_device_anywheretrgins AFTER INSERT ON vzdn_device_anywhere
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_device_anywhere$aud (id,useremail,datetime,gtm_company_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.useremail,NEW.datetime,NEW.gtm_company_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_email_messagestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_email_messagestrgins AFTER INSERT ON vzdn_email_messages
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_email_messages$aud (email_message_id,email_title,email_text,email_desc,email_category,created_by,created_date,last_updated_by,last_updated_date,from_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.email_message_id,NEW.email_title,NEW.email_text,NEW.email_desc,NEW.email_category,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,NEW.from_address,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_email_messages_atttrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_email_messages_atttrgins AFTER INSERT ON vzdn_email_messages_att
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_email_messages_att$aud (email_message_id,att_file_name,att_content_type,created_by,created_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.email_message_id,NEW.att_file_name,NEW.att_content_type,NEW.created_by,NEW.created_date,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_event_handlerstrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_event_handlerstrgins AFTER INSERT ON vzdn_event_handlers
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_event_handlers$aud (event_handler_id,event_id,class_name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.event_handler_id,NEW.event_id,NEW.class_name,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_event_notificationstrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_event_notificationstrgins AFTER INSERT ON vzdn_event_notifications
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_event_notifications$aud (notification_id,message_id,event_id,notification_title,media,status,from_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.notification_id,NEW.message_id,NEW.event_id,NEW.notification_title,NEW.media,NEW.status,NEW.from_address,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_event_place_holderstrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_event_place_holderstrgins AFTER INSERT ON vzdn_event_place_holders
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_event_place_holders$aud (place_holder_id,event_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.place_holder_id,NEW.event_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_eventstrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_eventstrgins AFTER INSERT ON vzdn_events
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_events$aud (event_id,event_name,event_desc,created_date,created_by,last_updated_date,last_updated_by,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.event_id,NEW.event_name,NEW.event_desc,NEW.created_date,NEW.created_by,NEW.last_updated_date,NEW.last_updated_by,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_menustrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_menustrgins AFTER INSERT ON vzdn_menus
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_menus$aud (menu_id,menu_name,menu_url,image_name,sort_order,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.menu_id,NEW.menu_name,NEW.menu_url,NEW.image_name,NEW.sort_order,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_newsletters_logtrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_newsletters_logtrgins AFTER INSERT ON vzdn_newsletters_log
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletters_log$aud (log_id,email_subject,email_body,email_attachment,email_addresses,description,created_date,created_by,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.log_id,NEW.email_subject,NEW.email_body,NEW.email_attachment,NEW.email_addresses,NEW.description,NEW.created_date,NEW.created_by,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_notif_ad_hoc_recipientstrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_notif_ad_hoc_recipientstrgins AFTER INSERT ON vzdn_notif_ad_hoc_recipients
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_notif_ad_hoc_recipients$aud (ad_hoc_id,notification_id,email_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.ad_hoc_id,NEW.notification_id,NEW.email_address,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_notif_opt_out_recipientstrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_notif_opt_out_recipientstrgins AFTER INSERT ON vzdn_notif_opt_out_recipients
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_notif_opt_out_recipients$aud (notification_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.notification_id,NEW.user_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_notification_rolestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_notification_rolestrgins AFTER INSERT ON vzdn_notification_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_notification_roles$aud (notification_id,role_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.notification_id,NEW.role_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_place_holderstrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_place_holderstrgins AFTER INSERT ON vzdn_place_holders
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_place_holders$aud (place_holder_id,place_holder_display_name,created_date,created_by,last_updated_date,last_updated_by,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.place_holder_id,NEW.place_holder_display_name,NEW.created_date,NEW.created_by,NEW.last_updated_date,NEW.last_updated_by,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_report_rolestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_report_rolestrgins AFTER INSERT ON vzdn_report_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_report_roles$aud (report_id,role_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.report_id,NEW.role_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_reportstrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_reportstrgins AFTER INSERT ON vzdn_reports
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_reports$aud (report_id,name,description,report_file,pdf_export,cvs_export,rtf_export,html_export,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.report_id,NEW.name,NEW.description,NEW.report_file,NEW.pdf_export,NEW.cvs_export,NEW.rtf_export,NEW.html_export,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_role_privilegestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_role_privilegestrgins AFTER INSERT ON vzdn_role_privileges
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_role_privileges$aud (role_id,privilege_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.role_id,NEW.privilege_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_sub_menustrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_sub_menustrgins AFTER INSERT ON vzdn_sub_menus
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_sub_menus$aud (sub_menu_id,sub_menu_name,sub_menu_url,image_name,sort_order,created_by,created_date,last_updated_by,last_updated_date,menu_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.sub_menu_id,NEW.sub_menu_name,NEW.sub_menu_url,NEW.image_name,NEW.sort_order,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,NEW.menu_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_sys_privilegestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_sys_privilegestrgins AFTER INSERT ON vzdn_sys_privileges
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_sys_privileges$aud (privilege_id,privilege_name,privilege_description,created_by,created_date,last_updated_by,last_updated_date,menu_id,sub_menu_id,privilege_key,available_to,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.privilege_id,NEW.privilege_name,NEW.privilege_description,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,NEW.menu_id,NEW.sub_menu_id,NEW.privilege_key,NEW.available_to,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_sys_rolestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_sys_rolestrgins AFTER INSERT ON vzdn_sys_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_sys_roles$aud (role_id,role_name,role_description,type_id,created_by,created_date,last_updated_by,last_updated_date,vzdn_role_mapping_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.role_id,NEW.role_name,NEW.role_description,NEW.type_id,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,NEW.vzdn_role_mapping_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_temp_filestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_temp_filestrgins AFTER INSERT ON vzdn_temp_files
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_temp_files$aud (temp_file_id,session_id,temp_file_name,temp_file_content_type,created_by,created_date,temp_file_comments,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.temp_file_id,NEW.session_id,NEW.temp_file_name,NEW.temp_file_content_type,NEW.created_by,NEW.created_date,NEW.temp_file_comments,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_type_defstrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_type_defstrgins AFTER INSERT ON vzdn_type_defs
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_type_defs$aud (type_def_id,type_name,type_description,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.type_def_id,NEW.type_name,NEW.type_description,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_typestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_typestrgins AFTER INSERT ON vzdn_types
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_types$aud (type_id,type_def_id,type_value,description,sort_order,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.type_id,NEW.type_def_id,NEW.type_value,NEW.description,NEW.sort_order,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_user_rolestrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_user_rolestrgins AFTER INSERT ON vzdn_user_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_user_roles$aud (role_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.role_id,NEW.user_id,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_userstrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_userstrgins AFTER INSERT ON vzdn_users
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_users$aud (user_id,user_name,password,title,last_name,first_name,phone_number,security_question,security_answer,created_by,created_date,last_updated_by,last_updated_date,fax_number,mobile_number,newsletter_opt_out,status_type_id,user_type_id,manager_organization,page_size,gtm_comp_id,country,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.user_id,NEW.user_name,NEW.password,NEW.title,NEW.last_name,NEW.first_name,NEW.phone_number,NEW.security_question,NEW.security_answer,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,NEW.fax_number,NEW.mobile_number,NEW.newsletter_opt_out,NEW.status_type_id,NEW.user_type_id,NEW.manager_organization,NEW.page_size,NEW.gtm_comp_id,NEW.country,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_zon_temp_userstrgins;

DELIMITER $$ 
CREATE TRIGGER vzdn_zon_temp_userstrgins AFTER INSERT ON vzdn_zon_temp_users
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_zon_temp_users$aud (user_id,username,user_type,title,first_name,last_name,phone_number,fax_number,mobile_number,password,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.user_id,NEW.username,NEW.user_type,NEW.title,NEW.first_name,NEW.last_name,NEW.phone_number,NEW.fax_number,NEW.mobile_number,NEW.password,'INS',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_dev_care_categoriestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_categoriestrgupd AFTER UPDATE ON vzdn_dev_care_categories
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_categories$aud (category_id,category_name,created_by,created_date,updated_by,updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.category_id,NEW.category_name,NEW.created_by,NEW.created_date,NEW.updated_by,NEW.updated_date,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_dev_care_issuestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_issuestrgupd AFTER UPDATE ON vzdn_dev_care_issues
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_issues$aud (issue_id,title,description,category_id,ticket_id,user_id,attachment_name,sub_category_id,created_by,created_date,updated_by,updated_date,attachment_type,type_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.issue_id,NEW.title,NEW.description,NEW.category_id,NEW.ticket_id,NEW.user_id,NEW.attachment_name,NEW.sub_category_id,NEW.created_by,NEW.created_date,NEW.updated_by,NEW.updated_date,NEW.attachment_type,NEW.type_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_dev_care_life_cycletrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_life_cycletrgupd AFTER UPDATE ON vzdn_dev_care_life_cycle
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_life_cycle$aud (comment_id,comments,user_id,created_by,created_date,updated_by,updated_date,issue_id,type_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.comment_id,NEW.comments,NEW.user_id,NEW.created_by,NEW.created_date,NEW.updated_by,NEW.updated_date,NEW.issue_id,NEW.type_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_dev_care_sub_categoriestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_sub_categoriestrgupd AFTER UPDATE ON vzdn_dev_care_sub_categories
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_sub_categories$aud (sub_category_id,sub_category_name,created_by,category_id,updated_by,updated_date,created_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.sub_category_id,NEW.sub_category_name,NEW.created_by,NEW.category_id,NEW.updated_by,NEW.updated_date,NEW.created_date,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_device_anywheretrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_device_anywheretrgupd AFTER UPDATE ON vzdn_device_anywhere
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_device_anywhere$aud (id,useremail,datetime,gtm_company_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.id,NEW.useremail,NEW.datetime,NEW.gtm_company_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_email_messagestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_email_messagestrgupd AFTER UPDATE ON vzdn_email_messages
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_email_messages$aud (email_message_id,email_title,email_text,email_desc,email_category,created_by,created_date,last_updated_by,last_updated_date,from_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.email_message_id,NEW.email_title,NEW.email_text,NEW.email_desc,NEW.email_category,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,NEW.from_address,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_email_messages_atttrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_email_messages_atttrgupd AFTER UPDATE ON vzdn_email_messages_att
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_email_messages_att$aud (email_message_id,att_file_name,att_content_type,created_by,created_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.email_message_id,NEW.att_file_name,NEW.att_content_type,NEW.created_by,NEW.created_date,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_event_handlerstrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_event_handlerstrgupd AFTER UPDATE ON vzdn_event_handlers
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_event_handlers$aud (event_handler_id,event_id,class_name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.event_handler_id,NEW.event_id,NEW.class_name,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_event_notificationstrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_event_notificationstrgupd AFTER UPDATE ON vzdn_event_notifications
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_event_notifications$aud (notification_id,message_id,event_id,notification_title,media,status,from_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.notification_id,NEW.message_id,NEW.event_id,NEW.notification_title,NEW.media,NEW.status,NEW.from_address,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_event_place_holderstrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_event_place_holderstrgupd AFTER UPDATE ON vzdn_event_place_holders
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_event_place_holders$aud (place_holder_id,event_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.place_holder_id,NEW.event_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_eventstrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_eventstrgupd AFTER UPDATE ON vzdn_events
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_events$aud (event_id,event_name,event_desc,created_date,created_by,last_updated_date,last_updated_by,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.event_id,NEW.event_name,NEW.event_desc,NEW.created_date,NEW.created_by,NEW.last_updated_date,NEW.last_updated_by,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_menustrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_menustrgupd AFTER UPDATE ON vzdn_menus
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_menus$aud (menu_id,menu_name,menu_url,image_name,sort_order,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.menu_id,NEW.menu_name,NEW.menu_url,NEW.image_name,NEW.sort_order,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_newsletters_logtrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_newsletters_logtrgupd AFTER UPDATE ON vzdn_newsletters_log
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletters_log$aud (log_id,email_subject,email_body,email_attachment,email_addresses,description,created_date,created_by,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.log_id,NEW.email_subject,NEW.email_body,NEW.email_attachment,NEW.email_addresses,NEW.description,NEW.created_date,NEW.created_by,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_notif_ad_hoc_recipientstrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_notif_ad_hoc_recipientstrgupd AFTER UPDATE ON vzdn_notif_ad_hoc_recipients
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_notif_ad_hoc_recipients$aud (ad_hoc_id,notification_id,email_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.ad_hoc_id,NEW.notification_id,NEW.email_address,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_notif_opt_out_recipientstrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_notif_opt_out_recipientstrgupd AFTER UPDATE ON vzdn_notif_opt_out_recipients
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_notif_opt_out_recipients$aud (notification_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.notification_id,NEW.user_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_notification_rolestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_notification_rolestrgupd AFTER UPDATE ON vzdn_notification_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_notification_roles$aud (notification_id,role_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.notification_id,NEW.role_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_place_holderstrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_place_holderstrgupd AFTER UPDATE ON vzdn_place_holders
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_place_holders$aud (place_holder_id,place_holder_display_name,created_date,created_by,last_updated_date,last_updated_by,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.place_holder_id,NEW.place_holder_display_name,NEW.created_date,NEW.created_by,NEW.last_updated_date,NEW.last_updated_by,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_report_rolestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_report_rolestrgupd AFTER UPDATE ON vzdn_report_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_report_roles$aud (report_id,role_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.report_id,NEW.role_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_reportstrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_reportstrgupd AFTER UPDATE ON vzdn_reports
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_reports$aud (report_id,name,description,report_file,pdf_export,cvs_export,rtf_export,html_export,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.report_id,NEW.name,NEW.description,NEW.report_file,NEW.pdf_export,NEW.cvs_export,NEW.rtf_export,NEW.html_export,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_role_privilegestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_role_privilegestrgupd AFTER UPDATE ON vzdn_role_privileges
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_role_privileges$aud (role_id,privilege_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.role_id,NEW.privilege_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_sub_menustrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_sub_menustrgupd AFTER UPDATE ON vzdn_sub_menus
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_sub_menus$aud (sub_menu_id,sub_menu_name,sub_menu_url,image_name,sort_order,created_by,created_date,last_updated_by,last_updated_date,menu_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.sub_menu_id,NEW.sub_menu_name,NEW.sub_menu_url,NEW.image_name,NEW.sort_order,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,NEW.menu_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_sys_privilegestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_sys_privilegestrgupd AFTER UPDATE ON vzdn_sys_privileges
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_sys_privileges$aud (privilege_id,privilege_name,privilege_description,created_by,created_date,last_updated_by,last_updated_date,menu_id,sub_menu_id,privilege_key,available_to,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.privilege_id,NEW.privilege_name,NEW.privilege_description,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,NEW.menu_id,NEW.sub_menu_id,NEW.privilege_key,NEW.available_to,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_sys_rolestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_sys_rolestrgupd AFTER UPDATE ON vzdn_sys_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_sys_roles$aud (role_id,role_name,role_description,type_id,created_by,created_date,last_updated_by,last_updated_date,vzdn_role_mapping_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.role_id,NEW.role_name,NEW.role_description,NEW.type_id,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,NEW.vzdn_role_mapping_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_temp_filestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_temp_filestrgupd AFTER UPDATE ON vzdn_temp_files
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_temp_files$aud (temp_file_id,session_id,temp_file_name,temp_file_content_type,created_by,created_date,temp_file_comments,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.temp_file_id,NEW.session_id,NEW.temp_file_name,NEW.temp_file_content_type,NEW.created_by,NEW.created_date,NEW.temp_file_comments,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_type_defstrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_type_defstrgupd AFTER UPDATE ON vzdn_type_defs
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_type_defs$aud (type_def_id,type_name,type_description,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.type_def_id,NEW.type_name,NEW.type_description,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_typestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_typestrgupd AFTER UPDATE ON vzdn_types
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_types$aud (type_id,type_def_id,type_value,description,sort_order,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.type_id,NEW.type_def_id,NEW.type_value,NEW.description,NEW.sort_order,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_user_rolestrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_user_rolestrgupd AFTER UPDATE ON vzdn_user_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_user_roles$aud (role_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.role_id,NEW.user_id,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_userstrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_userstrgupd AFTER UPDATE ON vzdn_users
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_users$aud (user_id,user_name,password,title,last_name,first_name,phone_number,security_question,security_answer,created_by,created_date,last_updated_by,last_updated_date,fax_number,mobile_number,newsletter_opt_out,status_type_id,user_type_id,manager_organization,page_size,gtm_comp_id,country,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.user_id,NEW.user_name,NEW.password,NEW.title,NEW.last_name,NEW.first_name,NEW.phone_number,NEW.security_question,NEW.security_answer,NEW.created_by,NEW.created_date,NEW.last_updated_by,NEW.last_updated_date,NEW.fax_number,NEW.mobile_number,NEW.newsletter_opt_out,NEW.status_type_id,NEW.user_type_id,NEW.manager_organization,NEW.page_size,NEW.gtm_comp_id,NEW.country,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_zon_temp_userstrgupd;

DELIMITER $$ 
CREATE TRIGGER vzdn_zon_temp_userstrgupd AFTER UPDATE ON vzdn_zon_temp_users
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_zon_temp_users$aud (user_id,username,user_type,title,first_name,last_name,phone_number,fax_number,mobile_number,password,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (NEW.user_id,NEW.username,NEW.user_type,NEW.title,NEW.first_name,NEW.last_name,NEW.phone_number,NEW.fax_number,NEW.mobile_number,NEW.password,'UPD',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_dev_care_categoriestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_categoriestrgdel AFTER DELETE ON vzdn_dev_care_categories
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_categories$aud (category_id,category_name,created_by,created_date,updated_by,updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.category_id,OLD.category_name,OLD.created_by,OLD.created_date,OLD.updated_by,OLD.updated_date,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_dev_care_issuestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_issuestrgdel AFTER DELETE ON vzdn_dev_care_issues
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_issues$aud (issue_id,title,description,category_id,ticket_id,user_id,attachment_name,sub_category_id,created_by,created_date,updated_by,updated_date,attachment_type,type_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.issue_id,OLD.title,OLD.description,OLD.category_id,OLD.ticket_id,OLD.user_id,OLD.attachment_name,OLD.sub_category_id,OLD.created_by,OLD.created_date,OLD.updated_by,OLD.updated_date,OLD.attachment_type,OLD.type_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_dev_care_life_cycletrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_life_cycletrgdel AFTER DELETE ON vzdn_dev_care_life_cycle
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_life_cycle$aud (comment_id,comments,user_id,created_by,created_date,updated_by,updated_date,issue_id,type_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.comment_id,OLD.comments,OLD.user_id,OLD.created_by,OLD.created_date,OLD.updated_by,OLD.updated_date,OLD.issue_id,OLD.type_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_dev_care_sub_categoriestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_dev_care_sub_categoriestrgdel AFTER DELETE ON vzdn_dev_care_sub_categories
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_dev_care_sub_categories$aud (sub_category_id,sub_category_name,created_by,category_id,updated_by,updated_date,created_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.sub_category_id,OLD.sub_category_name,OLD.created_by,OLD.category_id,OLD.updated_by,OLD.updated_date,OLD.created_date,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_device_anywheretrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_device_anywheretrgdel AFTER DELETE ON vzdn_device_anywhere
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_device_anywhere$aud (id,useremail,datetime,gtm_company_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.id,OLD.useremail,OLD.datetime,OLD.gtm_company_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_email_messagestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_email_messagestrgdel AFTER DELETE ON vzdn_email_messages
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_email_messages$aud (email_message_id,email_title,email_text,email_desc,email_category,created_by,created_date,last_updated_by,last_updated_date,from_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.email_message_id,OLD.email_title,OLD.email_text,OLD.email_desc,OLD.email_category,OLD.created_by,OLD.created_date,OLD.last_updated_by,OLD.last_updated_date,OLD.from_address,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_email_messages_atttrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_email_messages_atttrgdel AFTER DELETE ON vzdn_email_messages_att
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_email_messages_att$aud (email_message_id,att_file_name,att_content_type,created_by,created_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.email_message_id,OLD.att_file_name,OLD.att_content_type,OLD.created_by,OLD.created_date,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_event_handlerstrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_event_handlerstrgdel AFTER DELETE ON vzdn_event_handlers
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_event_handlers$aud (event_handler_id,event_id,class_name,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.event_handler_id,OLD.event_id,OLD.class_name,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_event_notificationstrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_event_notificationstrgdel AFTER DELETE ON vzdn_event_notifications
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_event_notifications$aud (notification_id,message_id,event_id,notification_title,media,status,from_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.notification_id,OLD.message_id,OLD.event_id,OLD.notification_title,OLD.media,OLD.status,OLD.from_address,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_event_place_holderstrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_event_place_holderstrgdel AFTER DELETE ON vzdn_event_place_holders
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_event_place_holders$aud (place_holder_id,event_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.place_holder_id,OLD.event_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_eventstrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_eventstrgdel AFTER DELETE ON vzdn_events
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_events$aud (event_id,event_name,event_desc,created_date,created_by,last_updated_date,last_updated_by,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.event_id,OLD.event_name,OLD.event_desc,OLD.created_date,OLD.created_by,OLD.last_updated_date,OLD.last_updated_by,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_menustrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_menustrgdel AFTER DELETE ON vzdn_menus
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_menus$aud (menu_id,menu_name,menu_url,image_name,sort_order,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.menu_id,OLD.menu_name,OLD.menu_url,OLD.image_name,OLD.sort_order,OLD.created_by,OLD.created_date,OLD.last_updated_by,OLD.last_updated_date,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_newsletters_logtrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_newsletters_logtrgdel AFTER DELETE ON vzdn_newsletters_log
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_newsletters_log$aud (log_id,email_subject,email_body,email_attachment,email_addresses,description,created_date,created_by,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.log_id,OLD.email_subject,OLD.email_body,OLD.email_attachment,OLD.email_addresses,OLD.description,OLD.created_date,OLD.created_by,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_notif_ad_hoc_recipientstrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_notif_ad_hoc_recipientstrgdel AFTER DELETE ON vzdn_notif_ad_hoc_recipients
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_notif_ad_hoc_recipients$aud (ad_hoc_id,notification_id,email_address,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.ad_hoc_id,OLD.notification_id,OLD.email_address,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_notif_opt_out_recipientstrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_notif_opt_out_recipientstrgdel AFTER DELETE ON vzdn_notif_opt_out_recipients
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_notif_opt_out_recipients$aud (notification_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.notification_id,OLD.user_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_notification_rolestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_notification_rolestrgdel AFTER DELETE ON vzdn_notification_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_notification_roles$aud (notification_id,role_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.notification_id,OLD.role_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_place_holderstrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_place_holderstrgdel AFTER DELETE ON vzdn_place_holders
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_place_holders$aud (place_holder_id,place_holder_display_name,created_date,created_by,last_updated_date,last_updated_by,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.place_holder_id,OLD.place_holder_display_name,OLD.created_date,OLD.created_by,OLD.last_updated_date,OLD.last_updated_by,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_report_rolestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_report_rolestrgdel AFTER DELETE ON vzdn_report_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_report_roles$aud (report_id,role_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.report_id,OLD.role_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_reportstrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_reportstrgdel AFTER DELETE ON vzdn_reports
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_reports$aud (report_id,name,description,report_file,pdf_export,cvs_export,rtf_export,html_export,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.report_id,OLD.name,OLD.description,OLD.report_file,OLD.pdf_export,OLD.cvs_export,OLD.rtf_export,OLD.html_export,OLD.created_by,OLD.created_date,OLD.last_updated_by,OLD.last_updated_date,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_role_privilegestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_role_privilegestrgdel AFTER DELETE ON vzdn_role_privileges
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_role_privileges$aud (role_id,privilege_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.role_id,OLD.privilege_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_sub_menustrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_sub_menustrgdel AFTER DELETE ON vzdn_sub_menus
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_sub_menus$aud (sub_menu_id,sub_menu_name,sub_menu_url,image_name,sort_order,created_by,created_date,last_updated_by,last_updated_date,menu_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.sub_menu_id,OLD.sub_menu_name,OLD.sub_menu_url,OLD.image_name,OLD.sort_order,OLD.created_by,OLD.created_date,OLD.last_updated_by,OLD.last_updated_date,OLD.menu_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_sys_privilegestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_sys_privilegestrgdel AFTER DELETE ON vzdn_sys_privileges
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_sys_privileges$aud (privilege_id,privilege_name,privilege_description,created_by,created_date,last_updated_by,last_updated_date,menu_id,sub_menu_id,privilege_key,available_to,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.privilege_id,OLD.privilege_name,OLD.privilege_description,OLD.created_by,OLD.created_date,OLD.last_updated_by,OLD.last_updated_date,OLD.menu_id,OLD.sub_menu_id,OLD.privilege_key,OLD.available_to,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_sys_rolestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_sys_rolestrgdel AFTER DELETE ON vzdn_sys_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_sys_roles$aud (role_id,role_name,role_description,type_id,created_by,created_date,last_updated_by,last_updated_date,vzdn_role_mapping_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.role_id,OLD.role_name,OLD.role_description,OLD.type_id,OLD.created_by,OLD.created_date,OLD.last_updated_by,OLD.last_updated_date,OLD.vzdn_role_mapping_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_temp_filestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_temp_filestrgdel AFTER DELETE ON vzdn_temp_files
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_temp_files$aud (temp_file_id,session_id,temp_file_name,temp_file_content_type,created_by,created_date,temp_file_comments,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.temp_file_id,OLD.session_id,OLD.temp_file_name,OLD.temp_file_content_type,OLD.created_by,OLD.created_date,OLD.temp_file_comments,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_type_defstrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_type_defstrgdel AFTER DELETE ON vzdn_type_defs
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_type_defs$aud (type_def_id,type_name,type_description,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.type_def_id,OLD.type_name,OLD.type_description,OLD.created_by,OLD.created_date,OLD.last_updated_by,OLD.last_updated_date,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_typestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_typestrgdel AFTER DELETE ON vzdn_types
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_types$aud (type_id,type_def_id,type_value,description,sort_order,created_by,created_date,last_updated_by,last_updated_date,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.type_id,OLD.type_def_id,OLD.type_value,OLD.description,OLD.sort_order,OLD.created_by,OLD.created_date,OLD.last_updated_by,OLD.last_updated_date,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_user_rolestrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_user_rolestrgdel AFTER DELETE ON vzdn_user_roles
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_user_roles$aud (role_id,user_id,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.role_id,OLD.user_id,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_userstrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_userstrgdel AFTER DELETE ON vzdn_users
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_users$aud (user_id,user_name,password,title,last_name,first_name,phone_number,security_question,security_answer,created_by,created_date,last_updated_by,last_updated_date,fax_number,mobile_number,newsletter_opt_out,status_type_id,user_type_id,manager_organization,page_size,gtm_comp_id,country,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.user_id,OLD.user_name,OLD.password,OLD.title,OLD.last_name,OLD.first_name,OLD.phone_number,OLD.security_question,OLD.security_answer,OLD.created_by,OLD.created_date,OLD.last_updated_by,OLD.last_updated_date,OLD.fax_number,OLD.mobile_number,OLD.newsletter_opt_out,OLD.status_type_id,OLD.user_type_id,OLD.manager_organization,OLD.page_size,OLD.gtm_comp_id,OLD.country,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
DROP TRIGGER IF EXISTS vzdn_zon_temp_userstrgdel;

DELIMITER $$ 
CREATE TRIGGER vzdn_zon_temp_userstrgdel AFTER DELETE ON vzdn_zon_temp_users
FOR EACH ROW BEGIN 
INSERT INTO vzdn_audit.vzdn_zon_temp_users$aud (user_id,username,user_type,title,first_name,last_name,phone_number,fax_number,mobile_number,password,AUD_ACTION,AUD_TIMESTAMP,AUD_USER) 
VALUES (OLD.user_id,OLD.username,OLD.user_type,OLD.title,OLD.first_name,OLD.last_name,OLD.phone_number,OLD.fax_number,OLD.mobile_number,OLD.password,'DEL',NOW(),CURRENT_USER());
END; $$
DELIMITER ;	
