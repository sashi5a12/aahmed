/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`vzdn` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `vzdn`;

/*Table structure for table `vzdn_dev_care_categories` */

CREATE TABLE `vzdn_dev_care_categories` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL,
  `created_by` INT default NULL,
  `created_date` datetime default NULL,
  `updated_by` INT default NULL,
  `updated_date` datetime default NULL,
  PRIMARY KEY  (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_dev_care_issues` */

CREATE TABLE `vzdn_dev_care_issues` (
  `issue_id` INT NOT NULL AUTO_INCREMENT,
  `title` varchar(50) default NULL,
  `description` varchar(500) default NULL,
  `category_id` INT default NULL,
  `ticket_id` varchar(200) NOT NULL,
  `user_id` INT default NULL,
  `attachment` longblob,
  `attachment_name` varchar(500) default NULL,
  `sub_category_id` INT default NULL,
  `created_by` INT default NULL,
  `created_date` datetime default NULL,
  `updated_by` INT default NULL,
  `updated_date` datetime default NULL,
  `attachment_type` varchar(20) default NULL,
  `type_id` INT default NULL,
  PRIMARY KEY  (`issue_id`),
  KEY `sys_c00306604` (`type_id`),
  KEY `sys_c00306605` (`sub_category_id`),
  KEY `sys_c00306606` (`category_id`),
  CONSTRAINT `sys_c00306604` FOREIGN KEY (`type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306605` FOREIGN KEY (`sub_category_id`) REFERENCES `vzdn_dev_care_sub_categories` (`sub_category_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306606` FOREIGN KEY (`category_id`) REFERENCES `vzdn_dev_care_categories` (`category_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_dev_care_life_cycle` */

CREATE TABLE `vzdn_dev_care_life_cycle` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `comments` varchar(1000) default NULL,
  `user_id` INT default NULL,
  `created_by` INT default NULL,
  `created_date` datetime default NULL,
  `updated_by` INT default NULL,
  `updated_date` datetime default NULL,
  `issue_id` INT default NULL,
  `type_id` INT default NULL,
  PRIMARY KEY  (`comment_id`),
  KEY `sys_c00306607` (`type_id`),
  KEY `sys_c00306608` (`issue_id`),
  KEY `sys_c00306609` (`user_id`),
  CONSTRAINT `sys_c00306607` FOREIGN KEY (`type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306608` FOREIGN KEY (`issue_id`) REFERENCES `vzdn_dev_care_issues` (`issue_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306609` FOREIGN KEY (`user_id`) REFERENCES `vzdn_users` (`user_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_dev_care_sub_categories` */

CREATE TABLE `vzdn_dev_care_sub_categories` (
  `sub_category_id` INT NOT NULL AUTO_INCREMENT,
  `sub_category_name` varchar(50) NOT NULL,
  `created_by` INT default NULL,
  `category_id` INT default NULL,
  `updated_by` INT default NULL,
  `updated_date` datetime default NULL,
  `created_date` datetime default NULL,
  PRIMARY KEY  (`sub_category_id`),
  KEY `sys_c00306610` (`category_id`),
  CONSTRAINT `sys_c00306610` FOREIGN KEY (`category_id`) REFERENCES `vzdn_dev_care_categories` (`category_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_email_messages` */

CREATE TABLE `vzdn_email_messages` (
  `email_message_id` INT NOT NULL AUTO_INCREMENT,
  `email_title` varchar(200) default NULL,
  `email_text` varchar(3500) default NULL,
  `email_desc` varchar(1000) default NULL,
  `email_category` varchar(100) default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  `email_attachment` longblob,
  `from_address` varchar(200) default NULL,
  PRIMARY KEY  (`email_message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_email_messages_att` */

CREATE TABLE `vzdn_email_messages_att` (
  `email_message_id` INT default NULL,
  `att` longblob,
  `att_file_name` varchar(150) default NULL,
  `att_content_type` varchar(100) default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  KEY `sys_c00306611` (`email_message_id`),
  CONSTRAINT `sys_c00306611` FOREIGN KEY (`email_message_id`) REFERENCES `vzdn_email_messages` (`email_message_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_event_handlers` */

CREATE TABLE `vzdn_event_handlers` (
  `event_handler_id` INT NOT NULL AUTO_INCREMENT,
  `event_id` INT default NULL,
  `class_name` varchar(200) default NULL,
  PRIMARY KEY  (`event_handler_id`),
  KEY `sys_c00306612` (`event_id`),
  CONSTRAINT `sys_c00306612` FOREIGN KEY (`event_id`) REFERENCES `vzdn_events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_event_notifications` */

CREATE TABLE `vzdn_event_notifications` (
  `notification_id` INT NOT NULL AUTO_INCREMENT,
  `message_id` INT default NULL,
  `event_id` INT default NULL,
  `notification_title` varchar(100) default NULL,
  `media` varchar(20) default NULL,
  `status` varchar(20) default NULL,
  `from_address` varchar(100) default NULL,
  PRIMARY KEY  (`notification_id`),
  KEY `sys_c00306613` (`message_id`),
  KEY `sys_c00306614` (`event_id`),
  CONSTRAINT `sys_c00306613` FOREIGN KEY (`message_id`) REFERENCES `vzdn_email_messages` (`email_message_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306614` FOREIGN KEY (`event_id`) REFERENCES `vzdn_events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_event_place_holders` */

CREATE TABLE `vzdn_event_place_holders` (
  `place_holder_id` INT NOT NULL,
  `event_id` INT NOT NULL,
  PRIMARY KEY  (`place_holder_id`,`event_id`),
  KEY `sys_c00306616` (`event_id`),
  CONSTRAINT `sys_c00306615` FOREIGN KEY (`place_holder_id`) REFERENCES `vzdn_place_holders` (`place_holder_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306616` FOREIGN KEY (`event_id`) REFERENCES `vzdn_events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_events` */

CREATE TABLE `vzdn_events` (
  `event_id` INT NOT NULL AUTO_INCREMENT,
  `event_name` varchar(50) default NULL,
  `event_desc` varchar(200) default NULL,
  `created_date` datetime default NULL,
  `created_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  PRIMARY KEY  (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_menus` */

CREATE TABLE `vzdn_menus` (
  `menu_id` INT NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(100) default NULL,
  `menu_url` varchar(200) default NULL,
  `image_name` varchar(100) default NULL,
  `sort_order` INT default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  PRIMARY KEY  (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_newsletters_log` */

CREATE TABLE `vzdn_newsletters_log` (
  `log_id` INT NOT NULL AUTO_INCREMENT,
  `email_subject` varchar(255) default NULL,
  `email_body` varchar(1000) default NULL,
  `email_attachment` char(18) default NULL,
  `email_addresses` varchar(1000) default NULL,
  `description` varchar(255) default NULL,
  `created_date` datetime default NULL,
  `created_by` varchar(100) default NULL,
  PRIMARY KEY  (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_notif_ad_hoc_recipients` */

CREATE TABLE `vzdn_notif_ad_hoc_recipients` (
  `ad_hoc_id` INT NOT NULL AUTO_INCREMENT,
  `notification_id` INT default NULL,
  `email_address` varchar(100) default NULL,  
  PRIMARY KEY(`ad_hoc_id`),
  CONSTRAINT `sys_c00306617` FOREIGN KEY (`notification_id`) REFERENCES `vzdn_event_notifications` (`notification_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_notif_opt_out_recipients` */

CREATE TABLE `vzdn_notif_opt_out_recipients` (
  `notification_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY  (`notification_id`,`user_id`),
  KEY `sys_c00306618` (`user_id`),
  CONSTRAINT `sys_c00306618` FOREIGN KEY (`user_id`) REFERENCES `vzdn_users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306619` FOREIGN KEY (`notification_id`) REFERENCES `vzdn_event_notifications` (`notification_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_notification_roles` */

CREATE TABLE `vzdn_notification_roles` (
  `notification_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY  (`notification_id`,`role_id`),
  KEY `sys_c00306621` (`role_id`),
  CONSTRAINT `sys_c00306620` FOREIGN KEY (`notification_id`) REFERENCES `vzdn_event_notifications` (`notification_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306621` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_place_holders` */

CREATE TABLE `vzdn_place_holders` (
  `place_holder_id` INT NOT NULL AUTO_INCREMENT,
  `place_holder_display_name` varchar(200) default NULL,
  `created_date` datetime default NULL,
  `created_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  PRIMARY KEY  (`place_holder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_report_roles` */

CREATE TABLE `vzdn_report_roles` (
  `report_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY  (`report_id`,`role_id`),
  KEY `sys_c00306622` (`role_id`),
  CONSTRAINT `sys_c00306622` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306623` FOREIGN KEY (`report_id`) REFERENCES `vzdn_reports` (`report_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_reports` */

CREATE TABLE `vzdn_reports` (
  `report_id` INT NOT NULL AUTO_INCREMENT,
  `name` varchar(100) default NULL,
  `description` varchar(500) default NULL,
  `report_file` varchar(100) default NULL,
  `pdf_export` char(1) default NULL,
  `cvs_export` char(1) default NULL,
  `rtf_export` char(1) default NULL,
  `html_export` char(1) default NULL,
  `created_by` varchar(100) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(100) default NULL,
  `last_updated_date` datetime default NULL,
  PRIMARY KEY  (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_role_privileges` */

CREATE TABLE `vzdn_role_privileges` (
  `role_id` INT NOT NULL,
  `privilege_id` INT NOT NULL,
  PRIMARY KEY  (`role_id`,`privilege_id`),
  KEY `sys_c00306625` (`privilege_id`),
  CONSTRAINT `sys_c00306624` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306625` FOREIGN KEY (`privilege_id`) REFERENCES `vzdn_sys_privileges` (`privilege_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_sub_menus` */

CREATE TABLE `vzdn_sub_menus` (
  `sub_menu_id` INT NOT NULL AUTO_INCREMENT,
  `sub_menu_name` varchar(100) default NULL,
  `sub_menu_url` varchar(200) default NULL,
  `image_name` varchar(100) default NULL,
  `sort_order` INT default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  `menu_id` INT default NULL,
  PRIMARY KEY  (`sub_menu_id`),
  KEY `sys_c00306626` (`menu_id`),
  CONSTRAINT `sys_c00306626` FOREIGN KEY (`menu_id`) REFERENCES `vzdn_menus` (`menu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_sys_privileges` */

CREATE TABLE `vzdn_sys_privileges` (
  `privilege_id` INT NOT NULL AUTO_INCREMENT,
  `privilege_name` varchar(100) default NULL,
  `privilege_description` varchar(200) default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  `menu_id` INT default NULL,
  `sub_menu_id` INT default NULL,
  `privilege_key` varchar(100) default NULL,
  `available_to` varchar(1) default NULL,
  PRIMARY KEY  (`privilege_id`),
  KEY `sys_c00306627` (`menu_id`),
  KEY `sys_c00306628` (`sub_menu_id`),
  CONSTRAINT `sys_c00306627` FOREIGN KEY (`menu_id`) REFERENCES `vzdn_menus` (`menu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306628` FOREIGN KEY (`sub_menu_id`) REFERENCES `vzdn_sub_menus` (`sub_menu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_sys_roles` */

CREATE TABLE `vzdn_sys_roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) default NULL,
  `role_description` varchar(500) default NULL,
  `type_id` INT NOT NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  `vzdn_role_mapping_id` INT NOT NULL,
  PRIMARY KEY  (`role_id`),
  KEY `sys_c00306634` (`type_id`),
  CONSTRAINT `sys_c00306634` FOREIGN KEY (`type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_temp_files` */

CREATE TABLE `vzdn_temp_files` (
  `temp_file_id` INT NOT NULL AUTO_INCREMENT,
  `session_id` varchar(1000) default NULL,
  `temp_file` longblob,
  `temp_file_name` varchar(150) default NULL,
  `temp_file_content_type` varchar(100) default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `temp_file_comments` varchar(1000) default NULL,
  PRIMARY KEY  (`temp_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_type_defs` */

CREATE TABLE `vzdn_type_defs` (
  `type_def_id` INT NOT NULL AUTO_INCREMENT,
  `type_name` varchar(100) NOT NULL,
  `type_description` varchar(500) default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  PRIMARY KEY  (`type_def_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_types` */

CREATE TABLE `vzdn_types` (
  `type_id` INT NOT NULL AUTO_INCREMENT,
  `type_def_id` INT NOT NULL,
  `type_value` varchar(100) NOT NULL,
  `description` varchar(500) default NULL,
  `sort_order` INT NOT NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  PRIMARY KEY  (`type_id`),
  KEY `sys_c00306629` (`type_def_id`),
  CONSTRAINT `sys_c00306629` FOREIGN KEY (`type_def_id`) REFERENCES `vzdn_type_defs` (`type_def_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_user_roles` */

CREATE TABLE `vzdn_user_roles` (
  `role_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY  (`role_id`,`user_id`),
  KEY `sys_c00306631` (`user_id`),
  CONSTRAINT `sys_c00306630` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306631` FOREIGN KEY (`user_id`) REFERENCES `vzdn_users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_users` */

CREATE TABLE `vzdn_users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) default NULL,
  `password` varchar(50) default NULL,
  `title` varchar(20) default NULL,
  `last_name` varchar(100) default NULL,
  `first_name` varchar(100) default NULL,
  `phone_number` varchar(50) default NULL,
  `security_question` varchar(255) default NULL,
  `security_answer` varchar(255) default NULL,
  `created_by` varchar(100) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(100) default NULL,
  `last_updated_date` datetime default NULL,
  `fax_number` varchar(50) default NULL,
  `mobile_number` varchar(50) default NULL,
  `newsletter_opt_out` char(1) default NULL,
  `status_type_id` INT default NULL,
  `user_type_id` INT default NULL,
  `manager_organization` varchar(100),
  PRIMARY KEY  (`user_id`),
  KEY `sys_c00306632` (`user_type_id`),
  KEY `sys_c00306633` (`status_type_id`),
  CONSTRAINT `sys_c00306632` FOREIGN KEY (`user_type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306633` FOREIGN KEY (`status_type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vzdn_zon_temp_users` */

CREATE TABLE `vzdn_zon_temp_users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` varchar(50) default NULL,
  `user_type` varchar(20) default NULL,
  `title` varchar(20) default NULL,
  `first_name` varchar(20) default NULL,
  `last_name` varchar(20) default NULL,
  `phone_number` varchar(20) default NULL,
  `fax_number` varchar(20) default NULL,
  `mobile_number` varchar(20) default NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;


/* Notifications related tables */

CREATE TABLE AIMS_PLACE_HOLDERS
(
  PLACE_HOLDER_ID           INT NOT NULL AUTO_INCREMENT,
  PLACE_HOLDER_DISPLAY_NAME  VARCHAR(200),
  CREATED_DATE               DATE,
  CREATED_BY                 VARCHAR(50),
  LAST_UPDATED_DATE          DATE,
  LAST_UPDATED_BY            VARCHAR(50),
   PRIMARY KEY (`PLACE_HOLDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE AIMS_EVENTS
(
  EVENT_ID  INT NOT NULL AUTO_INCREMENT,
  EVENT_NAME         VARCHAR(50),
  EVENT_DESC         VARCHAR(200),
  CREATED_DATE       DATE,
  CREATED_BY         VARCHAR(50),
  LAST_UPDATED_DATE  DATE,
  LAST_UPDATED_BY    VARCHAR(50),
   PRIMARY KEY (`EVENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE AIMS_EVENT_HANDLERS
(
  EVENT_HANDLER_ID  INT NOT NULL AUTO_INCREMENT,
  EVENT_ID          INT,
  CLASS_NAME        VARCHAR(200),
  PRIMARY KEY (`EVENT_HANDLER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE AIMS_EVENT_PLACE_HOLDERS
(
  PLACE_HOLDER_ID INT,
  EVENT_ID         INT  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE AIMS_EMAIL_MESSAGES
(
  EMAIL_MESSAGE_ID    INT NOT NULL AUTO_INCREMENT,
  EMAIL_TITLE        VARCHAR(200),
  EMAIL_TEXT         VARCHAR(3500),
  EMAIL_DESC         VARCHAR(1000),
  EMAIL_CATEGORY     VARCHAR(100),
  CREATED_BY         VARCHAR(50),
  CREATED_DATE       DATE,
  LAST_UPDATED_BY    VARCHAR(50),
  LAST_UPDATED_DATE  DATE,
  EMAIL_ATTACHMENT   BLOB,
  FROM_ADDRESS       VARCHAR(200),
  PRIMARY KEY (`EMAIL_MESSAGE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE AIMS_EVENT_NOTIFICATIONS
(
  NOTIFICATION_ID    INT NOT NULL AUTO_INCREMENT,
  MESSAGE_ID                  INT,
  EVENT_ID                     INT,
  NOTIFICATION_TITLE           VARCHAR(100),
  MEDIA                        VARCHAR(20),
  STATUS                       VARCHAR(20),
  FROM_ADDRESS                 VARCHAR(100),
  INCLUDE_VZW_ACCOUNT_MANAGER  VARCHAR(1),
  INCLUDE_VZW_APPS_CONTACT     VARCHAR(1),
  PRIMARY KEY (`NOTIFICATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE AIMS_NOTIFICATION_ROLES
(
  NOTIFICATION_ID  INT,
  ROLE_ID          INT
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE AIMS_NOTIF_AD_HOC_RECIPIENTS
(
  NOTIFICATION_ID  INT,
  EMAIL_ADDRESS    VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE AIMS_NOTIF_OPT_IN_RECIPIENTS
(
  NOTIFICATION_ID  INT,
  USER_ID          INT
) ENGINE=InnoDB DEFAULT CHARSET=latin1;