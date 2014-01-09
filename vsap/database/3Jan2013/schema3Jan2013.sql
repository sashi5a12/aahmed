DROP DATABASE IF EXISTS deviceportal;

CREATE DATABASE deviceportal;
use deviceportal;

CREATE TABLE `vap_application_properties` (
    property_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(250) NOT NULL,
    name VARCHAR(250) NOT NULL,
    value VARCHAR(2000),
    sort_order INT NOT NULL DEFAULT 0,
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) DEFAULT '1'
) ;
CREATE UNIQUE INDEX uk_name ON vap_application_properties (name);
CREATE TABLE `vap_company` (
    company_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(250),
    company_legal_name VARCHAR(250),
    mobile VARCHAR(25),
    address1 VARCHAR(250),
    city VARCHAR(100),
    state VARCHAR(100),
    country INT UNSIGNED,
    postal_code VARCHAR(20),
    website VARCHAR(250),
    company_domain VARCHAR(250),
    terms_accepted INT,
    terms_acceptance_date DATETIME,
    terms_accepted_by VARCHAR(50),
    current_registration_status INT UNSIGNED NOT NULL,
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) DEFAULT '1',
    main_contact INT UNSIGNED
) ;
CREATE UNIQUE INDEX `main_contact` ON vap_company (main_contact);
CREATE INDEX `vap_company_current_registration_status_fk1` ON vap_company (current_registration_status);
CREATE INDEX `idx_vap_company_main_contact` ON vap_company (main_contact);
CREATE INDEX `FK_vap_company_country_key` ON vap_company (country);
CREATE TABLE vap_company_join_offer (
    offer_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    company_id INT UNSIGNED NOT NULL,
    acceptor_id INT UNSIGNED,
    offered_to INT UNSIGNED NOT NULL,
    offer_date DATETIME NOT NULL,
    accept_reject_date DATETIME,
    status VARCHAR(50) NOT NULL,
    offer_type VARCHAR(50) NOT NULL,
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    updated_date DATETIME,
    is_active VARCHAR(1) DEFAULT '1'
) ;
CREATE INDEX FK_vap_company_join_offer_vap_user_fk1 ON vap_company_join_offer (acceptor_id);
CREATE INDEX FK_vap_company_join_offer_vap_user_fk2 ON vap_company_join_offer (offered_to);
CREATE INDEX FK_vap_company_join_offer_vap_company_fk3 ON vap_company_join_offer (company_id);
CREATE TABLE vap_email_messages (
    email_message_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    notification_id INT NOT NULL,
    email_attachment_name VARCHAR(200),
    email_title VARCHAR(200) NOT NULL,
    email_text VARCHAR(4000),
    email_desc VARCHAR(1000),
    email_category VARCHAR(100),
    email_attachment LONGBLOB,
    from_address VARCHAR(200),
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) DEFAULT '1'
) ;
CREATE INDEX fk_notifications_1 ON vap_email_messages (notification_id);
CREATE TABLE vap_event_place_holders (
    place_holder_id INT NOT NULL,
    event_id INT NOT NULL
) ;
CREATE INDEX fk_event_place_holders_1 ON vap_event_place_holders (place_holder_id);
CREATE INDEX fk_event_place_holders_2 ON vap_event_place_holders (event_id);
CREATE TABLE vap_event_queue (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    event_id INT NOT NULL DEFAULT 1,
    priority INT NOT NULL DEFAULT 1,
    properties_map BLOB,
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) DEFAULT '1'
) ;
CREATE INDEX vap_events_id_fk_1 ON vap_event_queue (event_id);
CREATE TABLE vap_events (
    event_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(50),
    event_desc VARCHAR(200),
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) DEFAULT '1'
) ;
CREATE TABLE vap_main_menu (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(15) NOT NULL,
    sort_order INT
) ;
CREATE TABLE vap_nda_question (
    question_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sort_order INT UNSIGNED,
    question VARCHAR(500),
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    updated_date DATETIME,
    is_active VARCHAR(1) NOT NULL DEFAULT '1'
) ;
CREATE TABLE vap_nda_question_answer (
    answer_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    vendor_data_id INT UNSIGNED NOT NULL,
    question_id INT UNSIGNED NOT NULL,
    answered_by INT UNSIGNED NOT NULL,
    company_id INT UNSIGNED,
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) NOT NULL DEFAULT '1',
    answer VARCHAR(500)
) ;
CREATE INDEX FK_vap_nda_question_answer_vap_nda_vendor_data_fk1 ON vap_nda_question_answer (vendor_data_id);
CREATE INDEX FK_vap_nda_question_answer_vap_nda_question_fk2 ON vap_nda_question_answer (question_id);
CREATE INDEX FK_vap_nda_question_answer_vap_company_fk2 ON vap_nda_question_answer (company_id);
CREATE INDEX FK_vap_nda_question_answer_vap_user_fk2 ON vap_nda_question_answer (answered_by);
CREATE TABLE vap_nda_vendor_data (
    vendor_data_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    company_id INT UNSIGNED,
    user_id INT UNSIGNED NOT NULL,
    corporate_name VARCHAR(250),
    address1 VARCHAR(250),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(50),
    zipcode VARCHAR(20),
    state_of_incorporation VARCHAR(100),
    contact_full_name VARCHAR(70),
    contact_title VARCHAR(70),
    contact_phone VARCHAR(25),
    contact_email VARCHAR(200),
    current_status INT,
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) NOT NULL DEFAULT '1'
) ;
CREATE INDEX FK_vap_nda_vendor_data_vap_company_fk1 ON vap_nda_vendor_data (company_id);
CREATE INDEX FK_vap_registration_status_vap_company_fk2 ON vap_nda_vendor_data (current_status);
CREATE TABLE vap_notifications (
    notification_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    notification_title VARCHAR(200),
    notification_description VARCHAR(1000),
    media VARCHAR(20),
    status VARCHAR(20),
    from_address VARCHAR(100),
    include_vzw_account_manager VARCHAR(1),
    include_vzw_apps_contact VARCHAR(1),
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) DEFAULT '1'
) ;
CREATE INDEX fk_event_notifications_2 ON vap_notifications (event_id);
CREATE TABLE vap_place_holders (
    place_holder_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    place_holder_display_name VARCHAR(200),
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) DEFAULT '1'
) ;
CREATE TABLE vap_sub_menu (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(25) NOT NULL,
    menu_id INT NOT NULL,
    system_role_id INT UNSIGNED NOT NULL,
    url VARCHAR(250) NOT NULL,
    sort_order INT
) ;
CREATE INDEX FK_vap_sub_menu ON vap_sub_menu (menu_id);
CREATE INDEX FK_vap_sub_menu_sys_role ON vap_sub_menu (system_role_id);
CREATE TABLE vap_system_role (
    role_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(250) NOT NULL,
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) DEFAULT '1'
) ;
CREATE TABLE vap_temp_user (
    user_name VARCHAR(50),
    password VARCHAR(250) NOT NULL,
    email_address VARCHAR(100),
    full_name VARCHAR(70),
    phone VARCHAR(25),
    mobile VARCHAR(25),
    company_domain VARCHAR(250),
    activation_code VARCHAR(100),
    temp_user_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    activation_type VARCHAR(50),
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) DEFAULT '1'
) ;
CREATE TABLE vap_types (
    type_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type_def_id INT NOT NULL,
    type_value VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    sort_order INT NOT NULL,
    is_active VARCHAR(1) DEFAULT '1',
    created_date DATETIME,
    created_by VARCHAR(50),
    last_updated_date DATETIME,
    last_updated_by VARCHAR(50)
) ;
CREATE INDEX fk_types_1 ON vap_types (type_def_id);
CREATE TABLE vap_user (
    user_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_type INT NOT NULL,
    company_id INT UNSIGNED,
    user_name VARCHAR(25) NOT NULL,
    password VARCHAR(250) NOT NULL,
    email_address VARCHAR(100) NOT NULL,
    full_name VARCHAR(70) NOT NULL,
    phone VARCHAR(25) NOT NULL,
    mobile VARCHAR(25),
    company_domain VARCHAR(250),
    last_login_date DATETIME,
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) NOT NULL DEFAULT '1',
    token VARCHAR(100),
    reset_password_token VARCHAR(50)
) ;
CREATE INDEX FK_vap_user_vap_company_fk1 ON vap_user (company_id);
CREATE TABLE vap_user_role (
    user_role_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role_id INT UNSIGNED NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    is_active VARCHAR(1) DEFAULT '1'
) ;
CREATE INDEX FK_vap_user_role_vap_user_fk1 ON vap_user_role (user_id);
CREATE INDEX FK_vap_user_role_vap_system_role_fk2 ON vap_user_role (role_id);
CREATE TABLE vap_vdc_type_defs (
    type_def_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(100) NOT NULL,
    type_description VARCHAR(500),
    created_date DATETIME,
    created_by VARCHAR(50),
    last_updated_date DATETIME,
    last_updated_by VARCHAR(50),
    is_active VARCHAR(1) DEFAULT '1'
) ;

CREATE TABLE `vap_workflow` (
    workflow_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    company_id INT UNSIGNED,
    assignee_user_id INT UNSIGNED,
    workflow_phase_id VARCHAR(100) NOT NULL,
    workflow_phase_name VARCHAR(100) NOT NULL,
    is_rfi VARCHAR(1) NOT NULL default 'N',
    start_date DATETIME NOT NULL,
    is_active VARCHAR(1) DEFAULT '1',
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    CONSTRAINT fk_vap_workflow_company FOREIGN KEY (company_id) REFERENCES vap_company(company_id),
    CONSTRAINT fk_vap_workflow_user FOREIGN KEY (assignee_user_id) REFERENCES vap_user(user_id)
);

ALTER TABLE vap_company ADD CONSTRAINT FK_vap_company_country_key FOREIGN KEY (country) REFERENCES vap_application_properties(property_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_company ADD CONSTRAINT vap_company_ibfk_1 FOREIGN KEY (main_contact) REFERENCES vap_user(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_company_join_offer ADD CONSTRAINT FK_vap_company_join_offer_vap_company_fk3 FOREIGN KEY (company_id) REFERENCES vap_company(company_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE vap_company_join_offer ADD CONSTRAINT FK_vap_company_join_offer_vap_user_fk1 FOREIGN KEY (acceptor_id) REFERENCES vap_user(user_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE vap_company_join_offer ADD CONSTRAINT FK_vap_company_join_offer_vap_user_fk2 FOREIGN KEY (offered_to) REFERENCES vap_user(user_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE vap_email_messages ADD CONSTRAINT fk_email_notifications_1 FOREIGN KEY (notification_id) REFERENCES vap_notifications(notification_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_event_place_holders ADD CONSTRAINT fk_event_place_holders_1 FOREIGN KEY (place_holder_id) REFERENCES vap_place_holders(place_holder_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_event_place_holders ADD CONSTRAINT fk_event_place_holders_2 FOREIGN KEY (event_id) REFERENCES vap_events(event_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_event_queue ADD CONSTRAINT vap_events_id_fk_1 FOREIGN KEY (event_id) REFERENCES vap_events(event_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_nda_question_answer ADD CONSTRAINT FK_vap_nda_question_answer_vap_company_fk2 FOREIGN KEY (company_id) REFERENCES vap_company(company_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE vap_nda_question_answer ADD CONSTRAINT FK_vap_nda_question_answer_vap_nda_question_fk2 FOREIGN KEY (question_id) REFERENCES vap_nda_question(question_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE vap_nda_question_answer ADD CONSTRAINT FK_vap_nda_question_answer_vap_nda_vendor_data_fk1 FOREIGN KEY (vendor_data_id) REFERENCES vap_nda_vendor_data(vendor_data_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE vap_nda_question_answer ADD CONSTRAINT FK_vap_nda_question_answer_vap_user_fk2 FOREIGN KEY (answered_by) REFERENCES vap_user(user_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE vap_nda_vendor_data ADD CONSTRAINT FK_vap_nda_vendor_data_vap_company_fk1 FOREIGN KEY (company_id) REFERENCES vap_company(company_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE vap_notifications ADD CONSTRAINT fk_event_notifications_2 FOREIGN KEY (event_id) REFERENCES vap_events(event_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_sub_menu ADD CONSTRAINT FK_vap_sub_menu FOREIGN KEY (menu_id) REFERENCES vap_main_menu(id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_sub_menu ADD CONSTRAINT FK_vap_sub_menu_sys_role FOREIGN KEY (system_role_id) REFERENCES vap_system_role(role_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_types ADD CONSTRAINT fk_types_1 FOREIGN KEY (type_def_id) REFERENCES vap_vdc_type_defs(type_def_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_user ADD CONSTRAINT FK_vap_user_vap_company_fk1 FOREIGN KEY (company_id) REFERENCES vap_company(company_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_user_role ADD CONSTRAINT FK_vap_user_role_vap_system_role_fk2 FOREIGN KEY (role_id) REFERENCES vap_system_role(role_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE vap_user_role ADD CONSTRAINT FK_vap_user_role_vap_user_fk1 FOREIGN KEY (user_id) REFERENCES vap_user(user_id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE `vap_workflow_comment` (
    workflow_comment_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    comment_text VARCHAR(500) NOT NULL,
    workflow_id INT NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    visibility INT UNSIGNED NOT NULL,  
    is_active VARCHAR(1) DEFAULT '1',
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    CONSTRAINT fk_vap_workflow_comment FOREIGN KEY (workflow_id) REFERENCES vap_workflow(workflow_id),
    CONSTRAINT fk_vap_workflow_comment_user FOREIGN KEY (user_id) REFERENCES vap_user(user_id)
);

CREATE TABLE `vap_workitem` (
    workitem_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    task_type VARCHAR(50) NOT NULL,
    task_id VARCHAR(50) NOT NULL,
    company_id INT UNSIGNED,
    start_date DATETIME NOT NULL,
    is_active VARCHAR(1) DEFAULT '1',
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    version INT DEFAULT 0,
    CONSTRAINT fk_vap_workitem_company FOREIGN KEY (company_id) REFERENCES vap_company(company_id)
);

CREATE TABLE `vap_workitem_comment` (
    workitem_comment_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    workitem_id INT NOT NULL,
    comment_text VARCHAR(500) NOT NULL,
    visibility VARCHAR(50) NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    is_active VARCHAR(1) DEFAULT '1',
    created_date DATETIME,
    created_by VARCHAR(50),
    updated_date DATETIME,
    updated_by VARCHAR(50),
    CONSTRAINT fk_vap_workitem_comment FOREIGN KEY (workitem_id) REFERENCES vap_workitem(workitem_id),
    CONSTRAINT fk_vap_workitem_comment_user FOREIGN KEY (user_id) REFERENCES vap_user(user_id)
);

CREATE TABLE `vap_notif_ad_hoc_recipients` (
    `notification_id` int(11) default NULL,
    `email_address` varchar(100) default NULL,
    `created_date` datetime default NULL,
    `created_by` varchar(50) default NULL,
    `updated_date` datetime default NULL,
    `updated_by` varchar(50) default NULL,
    `is_active` varchar(1) default '1',
    CONSTRAINT `fk_notification_recipients` FOREIGN KEY (`notification_id`) REFERENCES `vap_notifications` (`notification_id`)
);

CREATE TABLE `vap_notification_roles` (
    `notification_id` int(11) NOT NULL default '0',
    `role_id` int(10) unsigned NOT NULL default '0',
    `created_date` datetime default NULL,
    `created_by` varchar(50) default NULL,
    `updated_date` datetime default NULL,
    `updated_by` varchar(50) default NULL,
    `is_active` varchar(1) default '1',
    PRIMARY KEY  (`notification_id`,`role_id`),                     
    CONSTRAINT `fk_notification_roles_1` FOREIGN KEY (`notification_id`) REFERENCES `vap_notifications` (`notification_id`),
    CONSTRAINT `fk_notification_roles_2` FOREIGN KEY (`role_id`) REFERENCES `vap_system_role` (`role_id`)
);

alter table `vap_user` 
add column `address` varchar (250)  NOT NULL, 
add column `city` varchar (100)  NOT NULL, 
add column `state` varchar (100)  NOT NULL, 
add column `country` int (10)UNSIGNED   NOT NULL, 
add column `postal_code` varchar (20)  NOT NULL;

alter table `vap_temp_user` 
add column `address` varchar (250)  NOT NULL, 
add column `city` varchar (100)  NOT NULL, 
add column `state` varchar (100)  NOT NULL, 
add column `country` int (10)UNSIGNED   NOT NULL, 
add column `postal_code` varchar (20)  NOT NULL;

