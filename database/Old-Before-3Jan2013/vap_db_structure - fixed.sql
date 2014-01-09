DROP DATABASE IF EXISTS deviceportal;

CREATE DATABASE deviceportal;
use deviceportal;

/*
SQLyog Community Edition- MySQL GUI v8.2 
MySQL - 5.0.45-community-nt : Database - vap
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*Table structure for table `vap_registration_status` */

CREATE TABLE `vap_registration_status` (
  `registration_status_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(50) NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `updated_date` DATETIME NOT NULL,
  `updated_by` VARCHAR(50) NOT NULL,
  `is_active` VARCHAR(1) NOT NULL DEFAULT '1',
  PRIMARY KEY  (`registration_status_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `vap_company` */

CREATE TABLE `vap_company` (
  `company_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `company_name` VARCHAR(50) DEFAULT NULL,
  `company_legal_name` VARCHAR(50) DEFAULT NULL,
  `mobile` VARCHAR(50) DEFAULT NULL,
  `address1` VARCHAR(100) DEFAULT NULL,
  `city` VARCHAR(100) DEFAULT NULL,
  `state` VARCHAR(100) DEFAULT NULL,
  `country` INT(10) UNSIGNED DEFAULT NULL,
  `postal_code` VARCHAR(100) DEFAULT NULL,
  `website` VARCHAR(100) DEFAULT NULL,
  `company_domain` VARCHAR(100) DEFAULT NULL,
  `terms_accepted` VARCHAR(1) DEFAULT NULL,
  `terms_acceptance_date` DATETIME DEFAULT NULL,
  `terms_accepted_by` varchar(50),
  `current_registration_status` INT(10) UNSIGNED NOT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL,
  `is_active` VARCHAR(1) DEFAULT '1',
  PRIMARY KEY  (`company_id`),
  KEY `vap_company_current_registration_status_fk1` (`current_registration_status`),
  CONSTRAINT `FK_vap_company_current_registration_status_fk1` FOREIGN KEY (`current_registration_status`) REFERENCES `vap_registration_status` (`registration_status_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `vap_nda_question` */

CREATE TABLE `vap_nda_question` (
  `question_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sort_order` INT(10) UNSIGNED DEFAULT NULL,
  `question` VARCHAR(500) DEFAULT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `is_active` VARCHAR(1) NOT NULL DEFAULT '1',
  PRIMARY KEY  (`question_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `vap_nda_vendor_data` */

CREATE TABLE `vap_nda_vendor_data` (
  `vendor_data_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `company_id` INT(10) UNSIGNED DEFAULT NULL,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `corporate_name` VARCHAR(50) DEFAULT NULL,
  `address1` VARCHAR(200) DEFAULT NULL,
  `city` VARCHAR(50) DEFAULT NULL,
  `state` VARCHAR(50) DEFAULT NULL,
  `country` INT(10) UNSIGNED NOT NULL,
  `zipcode` VARCHAR(20) DEFAULT NULL,
  `state_of_incorporation` VARCHAR(50) DEFAULT NULL,
  `contact_full_name` VARCHAR(50) DEFAULT NULL,
  `contact_title` VARCHAR(20) DEFAULT NULL,
  `contact_phone` VARCHAR(50) DEFAULT NULL,
  `contact_email` VARCHAR(50) DEFAULT NULL,
  `current_status` INT(10) UNSIGNED NOT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL,
  `is_active` VARCHAR(1) NOT NULL DEFAULT '1',
  PRIMARY KEY  (`vendor_data_id`),
  KEY `FK_vap_nda_vendor_data_vap_company_fk1` (`company_id`),
  KEY `FK_vap_registration_status_vap_company_fk2` (`current_status`),
  CONSTRAINT `FK_vap_nda_vendor_data_vap_company_fk1` FOREIGN KEY (`company_id`) REFERENCES `vap_company` (`company_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_vap_registration_status_vap_company_fk2` FOREIGN KEY (`current_status`) REFERENCES `vap_registration_status` (`registration_status_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `vap_temp_user` */

CREATE TABLE `vap_temp_user` (
  `user_name` VARCHAR(50) DEFAULT NULL,
  `password` VARCHAR(250) DEFAULT NULL,
  `email_address` VARCHAR(50) DEFAULT NULL,
  `full_name` VARCHAR(50) DEFAULT NULL,
  `phone` VARCHAR(50) DEFAULT NULL,
  `mobile` VARCHAR(50) DEFAULT NULL,
  `company_domain` VARCHAR(50) DEFAULT NULL,
  `activation_code` VARCHAR(100) DEFAULT NULL,
  `temp_user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `activation_type` VARCHAR(50) DEFAULT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL,
  `is_active` VARCHAR(1) DEFAULT '1',
  PRIMARY KEY  (`temp_user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `vap_user` */

CREATE TABLE `vap_user` (
  `user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_type` int(2) NOT NULL,
  `company_id` INT(10) UNSIGNED DEFAULT NULL,
  `user_name` VARCHAR(50) NOT NULL,
  `password` VARCHAR(250) NOT NULL,
  `email_address` VARCHAR(50) NOT NULL,
  `full_name` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(50) NOT NULL,
  `mobile` VARCHAR(50) DEFAULT NULL,
  `company_domain` VARCHAR(100) DEFAULT NULL,
  `last_login_date` DATETIME DEFAULT NULL,
  `token` VARCHAR(100) DEFAULT NULL,
  `reset_password_token` varchar(50) DEFAULT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL,
  `is_active` VARCHAR(1) NOT NULL DEFAULT '1',
  PRIMARY KEY  (`user_id`),
  KEY `FK_vap_user_vap_company_fk1` (`company_id`),
  CONSTRAINT `FK_vap_user_vap_company_fk1` FOREIGN KEY (`company_id`) REFERENCES `vap_company` (`company_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `vap_company_join_offer` */

CREATE TABLE `vap_company_join_offer` (
  `offer_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `company_id` INT(10) UNSIGNED NOT NULL,
  `acceptor_id` INT(10) UNSIGNED DEFAULT NULL,
  `offered_to` INT(10) UNSIGNED NOT NULL,
  `offer_date` DATETIME NOT NULL,
  `accept_reject_date` DATETIME DEFAULT NULL,
  `status` VARCHAR(50) NOT NULL,
  `offer_type` VARCHAR(50) NOT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `is_active` VARCHAR(1) DEFAULT '1',
  PRIMARY KEY  (`offer_id`),
  KEY `FK_vap_company_join_offer_vap_user_fk1` (`acceptor_id`),
  KEY `FK_vap_company_join_offer_vap_user_fk2` (`offered_to`),
  KEY `FK_vap_company_join_offer_vap_company_fk3` (`company_id`),
  CONSTRAINT `FK_vap_company_join_offer_vap_company_fk3` FOREIGN KEY (`company_id`) REFERENCES `vap_company` (`company_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_vap_company_join_offer_vap_user_fk1` FOREIGN KEY (`acceptor_id`) REFERENCES `vap_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_vap_company_join_offer_vap_user_fk2` FOREIGN KEY (`offered_to`) REFERENCES `vap_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `vap_company_registration_status` */

CREATE TABLE `vap_company_registration_status` (
  `registration_status_id` INT(10) UNSIGNED NOT NULL,
  `company_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY  (`registration_status_id`,`company_id`),
  KEY `FK_vap_company_registration_status_vap_company_fk1` (`company_id`),
  KEY `FK_vap_company_registration_status_vap_registration_status_fk1` (`registration_status_id`),
  CONSTRAINT `FK_vap_company_registration_status_vap_company_fk1` FOREIGN KEY (`company_id`) REFERENCES `vap_company` (`company_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_vap_company_registration_status_vap_registration_status_fk1` FOREIGN KEY (`registration_status_id`) REFERENCES `vap_registration_status` (`registration_status_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `vap_vdc_type_defs` */

CREATE TABLE `vap_vdc_type_defs` (
  `type_def_id` INT(11) NOT NULL AUTO_INCREMENT,
  `type_name` VARCHAR(100) NOT NULL,
  `type_description` VARCHAR(500) DEFAULT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `last_updated_date` DATETIME DEFAULT NULL,
  `last_updated_by` VARCHAR(50) DEFAULT NULL,
  `is_active` VARCHAR(1) DEFAULT '1',
  PRIMARY KEY  (`type_def_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `vap_types` */

CREATE TABLE `vap_types` (
  `type_id` INT(11) NOT NULL AUTO_INCREMENT,
  `type_def_id` INT(11) NOT NULL,
  `type_value` VARCHAR(100) NOT NULL,
  `description` VARCHAR(500) DEFAULT NULL,
  `sort_order` INT(11) NOT NULL,
  `is_active` VARCHAR(1) DEFAULT '1',
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `last_updated_date` DATETIME DEFAULT NULL,
  `last_updated_by` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY  (`type_id`),
  KEY `fk_types_1` (`type_def_id`),
  CONSTRAINT `fk_types_1` FOREIGN KEY (`type_def_id`) REFERENCES `vap_vdc_type_defs` (`type_def_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
 

/*Table structure for table `vap_nda_question_answer` */

CREATE TABLE `vap_nda_question_answer` (
  `answer_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `vendor_data_id` INT(10) UNSIGNED NOT NULL,
  `question_id` INT(10) UNSIGNED NOT NULL,
  `answered_by` INT(10) UNSIGNED NOT NULL,
  `company_id` INT(10) UNSIGNED DEFAULT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL,
  `is_active` VARCHAR(1) NOT NULL DEFAULT '1',
  PRIMARY KEY  (`answer_id`),
  KEY `FK_vap_nda_question_answer_vap_nda_vendor_data_fk1` (`vendor_data_id`),
  KEY `FK_vap_nda_question_answer_vap_nda_question_fk2` (`question_id`),
  KEY `FK_vap_nda_question_answer_vap_company_fk2` (`company_id`),
  KEY `FK_vap_nda_question_answer_vap_user_fk2` (`answered_by`),
  CONSTRAINT `FK_vap_nda_question_answer_vap_nda_question_fk2` FOREIGN KEY (`question_id`) REFERENCES `vap_nda_question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_vap_nda_question_answer_vap_company_fk2` FOREIGN KEY (`company_id`) REFERENCES `vap_company` (`company_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_vap_nda_question_answer_vap_user_fk2` FOREIGN KEY (`answered_by`) REFERENCES `vap_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_vap_nda_question_answer_vap_nda_vendor_data_fk1` FOREIGN KEY (`vendor_data_id`) REFERENCES `vap_nda_vendor_data` (`vendor_data_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;


CREATE TABLE `vap_events` (
  `event_id` INT(11) NOT NULL AUTO_INCREMENT,
  `event_name` VARCHAR(50) DEFAULT NULL,
  `event_desc` VARCHAR(200) DEFAULT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL,
  `is_active` VARCHAR(1) DEFAULT '1',
  PRIMARY KEY (`event_id`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8;


CREATE TABLE `vap_place_holders` (
  `place_holder_id` INT(11) NOT NULL AUTO_INCREMENT,
  `place_holder_display_name` VARCHAR(200) DEFAULT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL,
  `is_active` VARCHAR(1) DEFAULT '1',
  PRIMARY KEY (`place_holder_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


CREATE TABLE `vap_event_place_holders` (
  `place_holder_id` INT(11) NOT NULL,
  `event_id` INT(11) NOT NULL,
  KEY `fk_event_place_holders_1` (`place_holder_id`),
  KEY `fk_event_place_holders_2` (`event_id`),
  CONSTRAINT `fk_event_place_holders_1` FOREIGN KEY (`place_holder_id`) REFERENCES `vap_place_holders` (`place_holder_id`),
  CONSTRAINT `fk_event_place_holders_2` FOREIGN KEY (`event_id`) REFERENCES `vap_events` (`event_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


CREATE TABLE `vap_notifications` (
  `notification_id` INT(11) NOT NULL AUTO_INCREMENT,
  `event_id` INT(11) DEFAULT NULL,
  `notification_title` VARCHAR(200) DEFAULT NULL,
  `notification_description` VARCHAR(1000) DEFAULT NULL,
  `media` VARCHAR(20) DEFAULT NULL,
  `status` VARCHAR(20) DEFAULT NULL,
  `from_address` VARCHAR(100) DEFAULT NULL,
  `include_vzw_account_manager` VARCHAR(1) DEFAULT NULL,
  `include_vzw_apps_contact` VARCHAR(1) DEFAULT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL,
  `is_active` VARCHAR(1) DEFAULT '1',
  PRIMARY KEY (`notification_id`),
  KEY `fk_event_notifications_2` (`event_id`),
  CONSTRAINT `fk_event_notifications_2` FOREIGN KEY (`event_id`) REFERENCES `vap_events` (`event_id`)
  
) ENGINE=INNODB  DEFAULT CHARSET=utf8;

CREATE TABLE `vap_email_messages` (
  `email_message_id` INT(11) NOT NULL AUTO_INCREMENT,
  `notification_id` INT(11) NOT NULL,
  `email_attachment_name` VARCHAR(200) DEFAULT NULL,
  `email_title` VARCHAR(200) NOT NULL,
  `email_text` VARCHAR(4000) DEFAULT NULL,
  `email_desc` VARCHAR(1000) DEFAULT NULL,
  `email_category` VARCHAR(100) DEFAULT NULL,
  `email_attachment` LONGBLOB,
  `from_address` VARCHAR(200) DEFAULT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `created_by` VARCHAR(50) DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL,
  `is_active` VARCHAR(1) DEFAULT '1',
  PRIMARY KEY (`email_message_id`),
  KEY `fk_notifications_1` (`notification_id`),
CONSTRAINT `fk_email_notifications_1` FOREIGN KEY (`notification_id`) REFERENCES `vap_notifications` (`notification_id`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8;

CREATE TABLE `vap_application_properties` (
  `property_id` int(10) unsigned NOT NULL auto_increment,
  `type` varchar(250) NOT NULL,
  `name` varchar(250) NOT NULL,
  `value` varchar(2000) default NULL,
  `sort_order` int(11) NOT NULL default '0',
  `created_date` datetime default NULL,
  `created_by` varchar(50) default NULL,
  `updated_date` datetime default NULL,
  `updated_by` varchar(50) default NULL,
  `is_active` varchar(1) default '1',
  PRIMARY KEY  (`property_id`),
  UNIQUE KEY `uk_name` (`name`)
)

INSERT INTO `vap_registration_status` (`status`, `description`, `created_date`, `created_by`, `updated_date`, `updated_by`, `is_active`) VALUES('IN_PROGRESS','Registration in progress',NOW(),'admin',NOW(),'admin','1');
INSERT INTO `vap_registration_status` (`status`, `description`, `created_date`, `created_by`, `updated_date`, `updated_by`, `is_active`) VALUES('PENDING_APPROVAL','Pending approval',NOW(),'admin',NOW(),'admin','1');
INSERT INTO `vap_registration_status` (`status`, `description`, `created_date`, `created_by`, `updated_date`, `updated_by`, `is_active`) VALUES('REGISTERED','Registered',NOW(),'admin',NOW(),'admin','1');


insert into `vap_nda_question` (`question_id`, `sort_order`, `question`, `created_date`, `created_by`, `updated_by`, `updated_date`, `is_active`) values('1','1','Will the vendor need to share VZW Confidential Info with a foreign parent or affiliate(s)?  If so, please list the official company name and location of such parent or affiliate(s).',now(),'admin','admin',now(),'1');
insert into `vap_nda_question` (`question_id`, `sort_order`, `question`, `created_date`, `created_by`, `updated_by`, `updated_date`, `is_active`) values('2','2','If the vendor is a foreign corporation do they have a U.S. subsidiary or office?  If applicable, please specify which and provide address.  If subsidiary provide state of incorporation.',now(),'admin','admin',now(),'1');
insert into `vap_nda_question` (`question_id`, `sort_order`, `question`, `created_date`, `created_by`, `updated_by`, `updated_date`, `is_active`) values('3','3','Is this vendor owned by a foreign government?  If so, name country.',now(),'admin','admin',now(),'1');
insert into `vap_nda_question` (`question_id`, `sort_order`, `question`, `created_date`, `created_by`, `updated_by`, `updated_date`, `is_active`) values('4','4','Complete description of business matter for which NDA is needed.',now(),'admin','admin',now(),'1');
insert into `vap_nda_question` (`question_id`, `sort_order`, `question`, `created_date`, `created_by`, `updated_by`, `updated_date`, `is_active`) values('5','5','Specifically describe the confidential information that will be disclosed by the vendor.',now(),'admin','admin',now(),'1');
insert into `vap_nda_question` (`question_id`, `sort_order`, `question`, `created_date`, `created_by`, `updated_by`, `updated_date`, `is_active`) values('6','6','Specifically describe the confidential information that will be disclosed by VZW.',now(),'admin','admin',now(),'1');
insert into `vap_nda_question` (`question_id`, `sort_order`, `question`, `created_date`, `created_by`, `updated_by`, `updated_date`, `is_active`) values('7','7','Whether a Mutual or Unilateral NDA is needed (VZW\'s determination, not vendor\'s)',now(),'admin','admin',now(),'1');


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
