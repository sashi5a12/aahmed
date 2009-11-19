/*

SQLyog Community Edition- MySQL GUI v8.05 

MySQL - 5.0.45-log : Database - vzdn

*********************************************************************

*/





/*!40101 SET NAMES utf8 */;



/*!40101 SET SQL_MODE=''*/;



/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;



-- CREATE DATABASE /*!32312 IF NOT EXISTS*/`vzdn` /*!40100 */DEFAULT CHARACTER SET utf8 ;



USE `vzdn`;



/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;



/*Table structure for table `vzdn_dev_care_categories` */



-- DROP TABLE IF EXISTS `vzdn_dev_care_categories`;



CREATE TABLE `vzdn_dev_care_categories` (

  `category_id` INT(11) NOT NULL AUTO_INCREMENT,

  `category_name` VARCHAR(50) NOT NULL,

  `created_by` INT(11) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `updated_by` INT(11) DEFAULT NULL,

  `updated_date` DATETIME DEFAULT NULL,

  PRIMARY KEY  (`category_id`)

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_dev_care_categories` */



/*Table structure for table `vzdn_dev_care_issues` */



-- DROP TABLE IF EXISTS `vzdn_dev_care_issues`;



CREATE TABLE `vzdn_dev_care_issues` (

  `issue_id` INT(11) NOT NULL AUTO_INCREMENT,

  `title` VARCHAR(50) DEFAULT NULL,

  `description` VARCHAR(500) DEFAULT NULL,

  `category_id` INT(11) DEFAULT NULL,

  `ticket_id` VARCHAR(200) NOT NULL,

  `user_id` INT(11) DEFAULT NULL,

  `attachment` LONGBLOB,

  `attachment_name` VARCHAR(500) DEFAULT NULL,

  `sub_category_id` INT(11) DEFAULT NULL,

  `created_by` INT(11) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `updated_by` INT(11) DEFAULT NULL,

  `updated_date` DATETIME DEFAULT NULL,

  `attachment_type` VARCHAR(20) DEFAULT NULL,

  `type_id` INT(11) DEFAULT NULL,

  PRIMARY KEY  (`issue_id`),

  KEY `sys_c00306604` (`type_id`),

  KEY `sys_c00306605` (`sub_category_id`),

  KEY `sys_c00306606` (`category_id`),

  CONSTRAINT `sys_c00306604` FOREIGN KEY (`type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306605` FOREIGN KEY (`sub_category_id`) REFERENCES `vzdn_dev_care_sub_categories` (`sub_category_id`) ON DELETE SET NULL ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306606` FOREIGN KEY (`category_id`) REFERENCES `vzdn_dev_care_categories` (`category_id`) ON DELETE SET NULL ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_dev_care_issues` */



/*Table structure for table `vzdn_dev_care_life_cycle` */



-- DROP TABLE IF EXISTS `vzdn_dev_care_life_cycle`;



CREATE TABLE `vzdn_dev_care_life_cycle` (

  `comment_id` INT(11) NOT NULL AUTO_INCREMENT,

  `comments` VARCHAR(1000) DEFAULT NULL,

  `user_id` INT(11) DEFAULT NULL,

  `created_by` INT(11) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `updated_by` INT(11) DEFAULT NULL,

  `updated_date` DATETIME DEFAULT NULL,

  `issue_id` INT(11) DEFAULT NULL,

  `type_id` INT(11) DEFAULT NULL,

  PRIMARY KEY  (`comment_id`),

  KEY `sys_c00306607` (`type_id`),

  KEY `sys_c00306608` (`issue_id`),

  KEY `sys_c00306609` (`user_id`),

  CONSTRAINT `sys_c00306607` FOREIGN KEY (`type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306608` FOREIGN KEY (`issue_id`) REFERENCES `vzdn_dev_care_issues` (`issue_id`) ON DELETE SET NULL ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306609` FOREIGN KEY (`user_id`) REFERENCES `vzdn_users` (`user_id`) ON DELETE SET NULL ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_dev_care_life_cycle` */



/*Table structure for table `vzdn_dev_care_sub_categories` */



-- DROP TABLE IF EXISTS `vzdn_dev_care_sub_categories`;



CREATE TABLE `vzdn_dev_care_sub_categories` (

  `sub_category_id` INT(11) NOT NULL AUTO_INCREMENT,

  `sub_category_name` VARCHAR(50) NOT NULL,

  `created_by` INT(11) DEFAULT NULL,

  `category_id` INT(11) DEFAULT NULL,

  `updated_by` INT(11) DEFAULT NULL,

  `updated_date` DATETIME DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  PRIMARY KEY  (`sub_category_id`),

  KEY `sys_c00306610` (`category_id`),

  CONSTRAINT `sys_c00306610` FOREIGN KEY (`category_id`) REFERENCES `vzdn_dev_care_categories` (`category_id`) ON DELETE SET NULL ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_dev_care_sub_categories` */



/*Table structure for table `vzdn_email_messages` */



-- DROP TABLE IF EXISTS `vzdn_email_messages`;



CREATE TABLE `vzdn_email_messages` (

  `email_message_id` INT(11) NOT NULL AUTO_INCREMENT,

  `email_title` VARCHAR(200) DEFAULT NULL,

  `email_text` VARCHAR(3500) DEFAULT NULL,

  `email_desc` VARCHAR(1000) DEFAULT NULL,

  `email_category` VARCHAR(100) DEFAULT NULL,

  `created_by` VARCHAR(50) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `last_updated_by` VARCHAR(50) DEFAULT NULL,

  `last_updated_date` DATETIME DEFAULT NULL,

  `email_attachment` LONGBLOB,

  `from_address` VARCHAR(200) DEFAULT NULL,

  PRIMARY KEY  (`email_message_id`)

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_email_messages` */



/*Table structure for table `vzdn_email_messages_att` */



-- DROP TABLE IF EXISTS `vzdn_email_messages_att`;



CREATE TABLE `vzdn_email_messages_att` (

  `email_message_id` INT(11) DEFAULT NULL,

  `att` LONGBLOB,

  `att_file_name` VARCHAR(150) DEFAULT NULL,

  `att_content_type` VARCHAR(100) DEFAULT NULL,

  `created_by` VARCHAR(50) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  KEY `sys_c00306611` (`email_message_id`),

  CONSTRAINT `sys_c00306611` FOREIGN KEY (`email_message_id`) REFERENCES `vzdn_email_messages` (`email_message_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_email_messages_att` */



/*Table structure for table `vzdn_event_handlers` */



-- DROP TABLE IF EXISTS `vzdn_event_handlers`;



CREATE TABLE `vzdn_event_handlers` (

  `event_handler_id` INT(11) NOT NULL AUTO_INCREMENT,

  `event_id` INT(11) DEFAULT NULL,

  `class_name` VARCHAR(200) DEFAULT NULL,

  PRIMARY KEY  (`event_handler_id`),

  KEY `sys_c00306612` (`event_id`),

  CONSTRAINT `sys_c00306612` FOREIGN KEY (`event_id`) REFERENCES `vzdn_events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_event_handlers` */



/*Table structure for table `vzdn_event_notifications` */



-- DROP TABLE IF EXISTS `vzdn_event_notifications`;



CREATE TABLE `vzdn_event_notifications` (

  `notification_id` INT(11) NOT NULL AUTO_INCREMENT,

  `message_id` INT(11) DEFAULT NULL,

  `event_id` INT(11) DEFAULT NULL,

  `notification_title` VARCHAR(100) DEFAULT NULL,

  `media` VARCHAR(20) DEFAULT NULL,

  `status` VARCHAR(20) DEFAULT NULL,

  `from_address` VARCHAR(100) DEFAULT NULL,

  PRIMARY KEY  (`notification_id`),

  KEY `sys_c00306613` (`message_id`),

  KEY `sys_c00306614` (`event_id`),

  CONSTRAINT `sys_c00306613` FOREIGN KEY (`message_id`) REFERENCES `vzdn_email_messages` (`email_message_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306614` FOREIGN KEY (`event_id`) REFERENCES `vzdn_events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_event_notifications` */



/*Table structure for table `vzdn_event_place_holders` */



-- DROP TABLE IF EXISTS `vzdn_event_place_holders`;



CREATE TABLE `vzdn_event_place_holders` (

  `place_holder_id` INT(11) NOT NULL,

  `event_id` INT(11) NOT NULL,

  PRIMARY KEY  (`place_holder_id`,`event_id`),

  KEY `sys_c00306616` (`event_id`),

  CONSTRAINT `sys_c00306615` FOREIGN KEY (`place_holder_id`) REFERENCES `vzdn_place_holders` (`place_holder_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306616` FOREIGN KEY (`event_id`) REFERENCES `vzdn_events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_event_place_holders` */



/*Table structure for table `vzdn_events` */



-- DROP TABLE IF EXISTS `vzdn_events`;



CREATE TABLE `vzdn_events` (

  `event_id` INT(11) NOT NULL AUTO_INCREMENT,

  `event_name` VARCHAR(50) DEFAULT NULL,

  `event_desc` VARCHAR(200) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `created_by` VARCHAR(50) DEFAULT NULL,

  `last_updated_date` DATETIME DEFAULT NULL,

  `last_updated_by` VARCHAR(50) DEFAULT NULL,

  PRIMARY KEY  (`event_id`)

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_events` */



/*Table structure for table `vzdn_menus` */



-- DROP TABLE IF EXISTS `vzdn_menus`;



CREATE TABLE `vzdn_menus` (

  `menu_id` INT(11) NOT NULL AUTO_INCREMENT,

  `menu_name` VARCHAR(100) DEFAULT NULL,

  `menu_url` VARCHAR(200) DEFAULT NULL,

  `image_name` VARCHAR(100) DEFAULT NULL,

  `sort_order` INT(11) DEFAULT NULL,

  `created_by` VARCHAR(50) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `last_updated_by` VARCHAR(50) DEFAULT NULL,

  `last_updated_date` DATETIME DEFAULT NULL,

  PRIMARY KEY  (`menu_id`)

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_menus` */



INSERT  INTO `vzdn_menus`(`menu_id`,`menu_name`,`menu_url`,`image_name`,`sort_order`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`) VALUES (1,'My Info',NULL,'icon_myinfo.gif',1,'ikram','0000-00-00 00:00:00',NULL,NULL),(2,'Community',NULL,'icon_community.gif',2,'ikram','0000-00-00 00:00:00',NULL,NULL),(3,'Go To Market',NULL,'icon_gotoMarket.gif',3,'ikram','0000-00-00 00:00:00',NULL,NULL),(4,'Verizon Developer Network News',NULL,'icon_rss2.gif',4,'ikram','0000-00-00 00:00:00',NULL,NULL),(5,'Management',NULL,'icon_management.gif',5,'ikram','0000-00-00 00:00:00',NULL,NULL);



/*Table structure for table `vzdn_newsletters_log` */



-- DROP TABLE IF EXISTS `vzdn_newsletters_log`;



CREATE TABLE `vzdn_newsletters_log` (

  `log_id` INT(11) NOT NULL AUTO_INCREMENT,

  `email_subject` VARCHAR(255) DEFAULT NULL,

  `email_body` VARCHAR(1000) DEFAULT NULL,

  `email_attachment` CHAR(18) DEFAULT NULL,

  `email_addresses` VARCHAR(1000) DEFAULT NULL,

  `description` VARCHAR(255) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `created_by` VARCHAR(100) DEFAULT NULL,

  PRIMARY KEY  (`log_id`)

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_newsletters_log` */



/*Table structure for table `vzdn_notif_ad_hoc_recipients` */



-- DROP TABLE IF EXISTS `vzdn_notif_ad_hoc_recipients`;



CREATE TABLE `vzdn_notif_ad_hoc_recipients` (

  `ad_hoc_id` INT(11) NOT NULL AUTO_INCREMENT,

  `notification_id` INT(11) DEFAULT NULL,

  `email_address` VARCHAR(100) DEFAULT NULL,

  PRIMARY KEY  (`ad_hoc_id`),

  KEY `sys_c00306617` (`notification_id`),

  CONSTRAINT `sys_c00306617` FOREIGN KEY (`notification_id`) REFERENCES `vzdn_event_notifications` (`notification_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_notif_ad_hoc_recipients` */



/*Table structure for table `vzdn_notif_opt_out_recipients` */



-- DROP TABLE IF EXISTS `vzdn_notif_opt_out_recipients`;



CREATE TABLE `vzdn_notif_opt_out_recipients` (

  `notification_id` INT(11) NOT NULL,

  `user_id` INT(11) NOT NULL,

  PRIMARY KEY  (`notification_id`,`user_id`),

  KEY `sys_c00306618` (`user_id`),

  CONSTRAINT `sys_c00306618` FOREIGN KEY (`user_id`) REFERENCES `vzdn_users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306619` FOREIGN KEY (`notification_id`) REFERENCES `vzdn_event_notifications` (`notification_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_notif_opt_out_recipients` */



/*Table structure for table `vzdn_notification_roles` */



-- DROP TABLE IF EXISTS `vzdn_notification_roles`;



CREATE TABLE `vzdn_notification_roles` (

  `notification_id` INT(11) NOT NULL,

  `role_id` INT(11) NOT NULL,

  PRIMARY KEY  (`notification_id`,`role_id`),

  KEY `sys_c00306621` (`role_id`),

  CONSTRAINT `sys_c00306620` FOREIGN KEY (`notification_id`) REFERENCES `vzdn_event_notifications` (`notification_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306621` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_notification_roles` */



/*Table structure for table `vzdn_place_holders` */



-- DROP TABLE IF EXISTS `vzdn_place_holders`;



CREATE TABLE `vzdn_place_holders` (

  `place_holder_id` INT(11) NOT NULL AUTO_INCREMENT,

  `place_holder_display_name` VARCHAR(200) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `created_by` VARCHAR(50) DEFAULT NULL,

  `last_updated_date` DATETIME DEFAULT NULL,

  `last_updated_by` VARCHAR(50) DEFAULT NULL,

  PRIMARY KEY  (`place_holder_id`)

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_place_holders` */



/*Table structure for table `vzdn_report_roles` */



-- DROP TABLE IF EXISTS `vzdn_report_roles`;



CREATE TABLE `vzdn_report_roles` (

  `report_id` INT(11) NOT NULL,

  `role_id` INT(11) NOT NULL,

  PRIMARY KEY  (`report_id`,`role_id`),

  KEY `sys_c00306622` (`role_id`),

  CONSTRAINT `sys_c00306622` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306623` FOREIGN KEY (`report_id`) REFERENCES `vzdn_reports` (`report_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_report_roles` */



/*Table structure for table `vzdn_reports` */



-- DROP TABLE IF EXISTS `vzdn_reports`;



CREATE TABLE `vzdn_reports` (

  `report_id` INT(11) NOT NULL AUTO_INCREMENT,

  `name` VARCHAR(100) DEFAULT NULL,

  `description` VARCHAR(500) DEFAULT NULL,

  `report_file` VARCHAR(100) DEFAULT NULL,

  `pdf_export` CHAR(1) DEFAULT NULL,

  `cvs_export` CHAR(1) DEFAULT NULL,

  `rtf_export` CHAR(1) DEFAULT NULL,

  `html_export` CHAR(1) DEFAULT NULL,

  `created_by` VARCHAR(100) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `last_updated_by` VARCHAR(100) DEFAULT NULL,

  `last_updated_date` DATETIME DEFAULT NULL,

  PRIMARY KEY  (`report_id`)

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_reports` */



/*Table structure for table `vzdn_role_privileges` */



-- DROP TABLE IF EXISTS `vzdn_role_privileges`;



CREATE TABLE `vzdn_role_privileges` (

  `role_id` INT(11) NOT NULL,

  `privilege_id` INT(11) NOT NULL,

  PRIMARY KEY  (`role_id`,`privilege_id`),

  KEY `sys_c00306625` (`privilege_id`),

  CONSTRAINT `sys_c00306624` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306625` FOREIGN KEY (`privilege_id`) REFERENCES `vzdn_sys_privileges` (`privilege_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_role_privileges` */



INSERT  INTO `vzdn_role_privileges`(`role_id`,`privilege_id`) VALUES (2,4),(3,17),(4,3),(5,1),(5,2),(5,3),(5,4),(5,5),(5,6),(5,8),(5,11),(5,14),(6,1),(6,2),(6,12),(6,13),(24,5),(24,6),(24,14);



/*Table structure for table `vzdn_sub_menus` */



-- DROP TABLE IF EXISTS `vzdn_sub_menus`;



CREATE TABLE `vzdn_sub_menus` (

  `sub_menu_id` INT(11) NOT NULL AUTO_INCREMENT,

  `sub_menu_name` VARCHAR(100) DEFAULT NULL,

  `sub_menu_url` VARCHAR(200) DEFAULT NULL,

  `image_name` VARCHAR(100) DEFAULT NULL,

  `sort_order` INT(11) DEFAULT NULL,

  `created_by` VARCHAR(50) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `last_updated_by` VARCHAR(50) DEFAULT NULL,

  `last_updated_date` DATETIME DEFAULT NULL,

  `menu_id` INT(11) DEFAULT NULL,

  PRIMARY KEY  (`sub_menu_id`),

  KEY `sys_c00306626` (`menu_id`),

  CONSTRAINT `sys_c00306626` FOREIGN KEY (`menu_id`) REFERENCES `vzdn_menus` (`menu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_sub_menus` */



INSERT  INTO `vzdn_sub_menus`(`sub_menu_id`,`sub_menu_name`,`sub_menu_url`,`image_name`,`sort_order`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`menu_id`) VALUES (1,'Edit Profile','https://developersso.verizon.com/auth/idm/EndUser',NULL,1,'admin',NULL,NULL,NULL,1),(2,'Notifications','#',NULL,2,'admin',NULL,NULL,NULL,1),(3,'Blog Administration','http://developer.verizon.com/blogs/roller-ui/menu.rol',NULL,1,'admin',NULL,NULL,NULL,2),(4,'Forum Administration','http://developer.verizon.com/forum/admBase/login.page',NULL,2,'ikram',NULL,NULL,NULL,2),(5,'Application','http://www.vzwdevelopers.com/aims/applicationsViewDelete.do?task=view&app_type=all_apps&cancel_search',NULL,1,'admin',NULL,NULL,NULL,3),(6,'Alliances','http://www.vzwdevelopers.com/aims/vzwAlliance.do?task=view&all_type=ALL',NULL,2,'ikram',NULL,NULL,NULL,3),(7,'New JDK Download Available','#',NULL,1,'ikram',NULL,NULL,NULL,4),(8,'Brew gets better','#',NULL,2,'ikram',NULL,NULL,NULL,4),(9,'Notifications','#',NULL,1,'ikram',NULL,NULL,NULL,5),(10,'Content Publishing','#',NULL,2,'ikram',NULL,NULL,NULL,5),(13,'Users Management','searchUsersAction.action',NULL,5,'ikram',NULL,NULL,NULL,5),(14,'My Applications','http://www.vzwdevelopers.com/aims/applicationsViewDelete.do?task=view&app_type=all_apps&cancel_search',NULL,6,'ikram',NULL,NULL,NULL,3),(15,'My Contracts','http://www.vzwdevelopers.com/aims/allianceContracts.do?task=view',NULL,7,NULL,NULL,NULL,NULL,3),(16,'Contracts','http://www.vzwdevelopers.com/aims/contracts.do?task=view',NULL,8,NULL,NULL,NULL,NULL,3),(17,'Forum','http://developer.verizon.com/forum/forums/list.page',NULL,10,NULL,NULL,NULL,NULL,2),(18,'Blog','http://developer.verizon.com/blogs/',NULL,9,NULL,NULL,NULL,NULL,2),(19,'Write a Blog','http://developer.verizon.com/blogs/roller-ui/menu.rol',NULL,3,NULL,NULL,NULL,NULL,2);



/*Table structure for table `vzdn_sys_privileges` */



-- DROP TABLE IF EXISTS `vzdn_sys_privileges`;



CREATE TABLE `vzdn_sys_privileges` (

  `privilege_id` INT(11) NOT NULL AUTO_INCREMENT,

  `privilege_name` VARCHAR(100) DEFAULT NULL,

  `privilege_description` VARCHAR(200) DEFAULT NULL,

  `created_by` VARCHAR(50) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `last_updated_by` VARCHAR(50) DEFAULT NULL,

  `last_updated_date` DATETIME DEFAULT NULL,

  `menu_id` INT(11) DEFAULT NULL,

  `sub_menu_id` INT(11) DEFAULT NULL,

  `privilege_key` VARCHAR(100) DEFAULT NULL,

  `available_to` VARCHAR(1) DEFAULT NULL,

  PRIMARY KEY  (`privilege_id`),

  KEY `sys_c00306627` (`menu_id`),

  KEY `sys_c00306628` (`sub_menu_id`),

  CONSTRAINT `sys_c00306627` FOREIGN KEY (`menu_id`) REFERENCES `vzdn_menus` (`menu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306628` FOREIGN KEY (`sub_menu_id`) REFERENCES `vzdn_sub_menus` (`sub_menu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_sys_privileges` */



INSERT  INTO `vzdn_sys_privileges`(`privilege_id`,`privilege_name`,`privilege_description`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`menu_id`,`sub_menu_id`,`privilege_key`,`available_to`) VALUES (1,'edit_profile','Edit Profile','ikram',NULL,NULL,NULL,NULL,1,NULL,NULL),(2,'notifications','Notifications','ikram',NULL,NULL,NULL,NULL,2,NULL,NULL),(3,'blog_link_for_admin','Blogs','ikram',NULL,NULL,NULL,NULL,3,NULL,NULL),(4,'forum_link_for_admin','Forums','ikram',NULL,NULL,NULL,NULL,4,NULL,NULL),(5,'applications','Applications','ikram',NULL,NULL,NULL,NULL,5,NULL,NULL),(6,'alliances','Alliances','ikram',NULL,NULL,NULL,NULL,6,NULL,NULL),(7,'manage_notifications','Notifications Management','ikram',NULL,NULL,NULL,NULL,9,NULL,NULL),(8,'publish_content','Conent Publishing','ikram',NULL,NULL,NULL,NULL,10,NULL,NULL),(11,'manage_users','Manager Users','ikram',NULL,NULL,NULL,NULL,13,NULL,NULL),(12,'my_application_for_dev','My Application Link for Developers','ikram',NULL,NULL,NULL,NULL,14,NULL,NULL),(13,'my_contracts_for_dev','My Contracts Link for Developers',NULL,NULL,NULL,NULL,NULL,15,NULL,NULL),(14,'contracts_page_for_vz_user','contracts link for Verizon User',NULL,NULL,NULL,NULL,NULL,16,NULL,NULL),(15,'blog_link_for_developer','blogs link  for developer',NULL,NULL,NULL,NULL,NULL,18,NULL,NULL),(16,'forum_link_for_developer','forum link for developer',NULL,NULL,NULL,NULL,NULL,17,NULL,NULL),(17,'write_a_blog_link_for_blogger','Link for Blog User',NULL,NULL,NULL,NULL,NULL,19,NULL,NULL);



/*Table structure for table `vzdn_sys_roles` */



-- DROP TABLE IF EXISTS `vzdn_sys_roles`;



CREATE TABLE `vzdn_sys_roles` (

  `role_id` INT(11) NOT NULL AUTO_INCREMENT,

  `role_name` VARCHAR(50) DEFAULT NULL,

  `role_description` VARCHAR(500) DEFAULT NULL,

  `type_id` INT(11) NOT NULL,

  `created_by` VARCHAR(50) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `last_updated_by` VARCHAR(50) DEFAULT NULL,

  `last_updated_date` DATETIME DEFAULT NULL,

  `vzdn_role_mapping_id` INT(11) NOT NULL,

  PRIMARY KEY  (`role_id`),

  KEY `sys_c00306634` (`type_id`),

  CONSTRAINT `sys_c00306634` FOREIGN KEY (`type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_sys_roles` */



INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(1,'Forum - Forum User','Forum User',2,NULL,NULL,NULL,NULL,5001);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(2,'Forum - Forum Admin','Forum Admin',2,NULL,NULL,NULL,NULL,5002);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(3,'Blog - Blog User','Blog User',3,NULL,NULL,NULL,NULL,4002);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(4,'Blog - Blog Admin','Blog Admin',2,NULL,NULL,NULL,NULL,4001);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(5,'Core - System Admin','VzdnCore Admin',2,NULL,NULL,NULL,NULL,1001);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(6,'Core - Developer Role','This role will be assigned to all developers when they log in',4,NULL,NULL,NULL,NULL,0);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(7,'GTM - Read Only for BREW','Read Only for BREW',2,NULL,NULL,NULL,NULL,2001);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(8,'GTM - WAP Biz Dev','WAP Biz Dev',2,NULL,NULL,NULL,NULL,2002);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(9,'GTM - WAP Product Development','WAP Product Development',2,NULL,NULL,NULL,NULL,2003);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(10,'GTM - WAP Technology Development','WAP Technology Development',2,NULL,NULL,NULL,NULL,2004);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(11,'GTM - Intertek','Intertek',2,NULL,NULL,NULL,NULL,2005);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(12,'GTM - VZAppZone Admin','VZAppZone Admin',2,NULL,NULL,NULL,NULL,2006);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(13,'GTM - Dashboard Admin','Dashboard Admin',2,NULL,NULL,NULL,NULL,2007);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(14,'GTM - Wireless Developer','Wireless Developer',2,NULL,NULL,NULL,NULL,2008);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(15,'GTM - BREW Admin','BREW Admin',2,NULL,NULL,NULL,NULL,2009);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(16,'GTM - Legal','Legal',2,NULL,NULL,NULL,NULL,2010);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(17,'GTM - SUPERUSER','SUPERUSER',2,NULL,NULL,NULL,NULL,2011);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(18,'GTM - VZWADMIN','VZWADMIN',2,NULL,NULL,NULL,NULL,2012);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(19,'GTM - WAP Admin','WAP Admin',2,NULL,NULL,NULL,NULL,2014);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(20,'GTM - Enterprise Admin','Enterprise Admin',2,NULL,NULL,NULL,NULL,2015);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(21,'GTM - Tech Team','Tech Team',2,NULL,NULL,NULL,NULL,2016);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(22,'GTM - JMA Contract Admin','JMA Contract Admin',2,NULL,NULL,NULL,NULL,2017);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(23,'GTM - Super','Super',2,NULL,NULL,NULL,NULL,2018);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(24,'GTM - WAP - Read only','WAP - Read only',2,NULL,NULL,NULL,NULL,2019);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(25,'GTM - Evaluation','Evaluation',2,NULL,NULL,NULL,NULL,2020);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(26,'GTM - Biz Dev BREW Admin','Biz Dev BREW Admin',2,NULL,NULL,NULL,NULL,2021);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(27,'GTM - Read Only','Read Only',2,NULL,NULL,NULL,NULL,2022);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(28,'GTM - Rollback_DCR','Rollback_DCR',2,NULL,NULL,NULL,NULL,2023);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(29,'GTM - BREW - Read only','BREW - Read only',2,NULL,NULL,NULL,NULL,2024);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(30,'GTM - Netpace Support Admin','GTM - Netpace Support Admin',4,NULL,NULL,NULL,NULL,2026);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(31,'GTM - JAVA Admin','GTM - JAVA Admin',2,NULL,NULL,NULL,NULL,2027);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(32,'GTM - JAVA Programming Content Manager','GTM - JAVA Programming Content Manager',2,NULL,NULL,NULL,NULL,2028);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(33,'GTM - JAVA Content Standard Manager','GTM - JAVA Content Standard Manager',2,NULL,NULL,NULL,NULL,2029);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(34,'GTM - JAVA Legal Manager','GTM - JAVA Legal Manager',2,NULL,NULL,NULL,NULL,2030);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(35,'GTM - JAVA Tax Manager','GTM - JAVA Tax Manager',2,NULL,NULL,NULL,NULL,2031);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(36,'Forum - Forum Super Admin','Forum Super Admin',4,NULL,NULL,NULL,NULL,5003);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(37,'Blog - Blog Super Admin','Blog Super Admin',4,NULL,NULL,NULL,NULL,4003);




/*Table structure for table `vzdn_temp_files` */



-- DROP TABLE IF EXISTS `vzdn_temp_files`;



CREATE TABLE `vzdn_temp_files` (

  `temp_file_id` INT(11) NOT NULL AUTO_INCREMENT,

  `session_id` VARCHAR(1000) DEFAULT NULL,

  `temp_file` LONGBLOB,

  `temp_file_name` VARCHAR(150) DEFAULT NULL,

  `temp_file_content_type` VARCHAR(100) DEFAULT NULL,

  `created_by` VARCHAR(50) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `temp_file_comments` VARCHAR(1000) DEFAULT NULL,

  PRIMARY KEY  (`temp_file_id`)

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_temp_files` */



/*Table structure for table `vzdn_type_defs` */



-- DROP TABLE IF EXISTS `vzdn_type_defs`;



CREATE TABLE `vzdn_type_defs` (

  `type_def_id` INT(11) NOT NULL AUTO_INCREMENT,

  `type_name` VARCHAR(100) NOT NULL,

  `type_description` VARCHAR(500) DEFAULT NULL,

  `created_by` VARCHAR(50) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `last_updated_by` VARCHAR(50) DEFAULT NULL,

  `last_updated_date` DATETIME DEFAULT NULL,

  PRIMARY KEY  (`type_def_id`)

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_type_defs` */



INSERT  INTO `vzdn_type_defs`(`type_def_id`,`type_name`,`type_description`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`) VALUES (1,'Role Type','Role Type',NULL,NULL,NULL,NULL),(2,'User Status Type','User Status Type',NULL,NULL,NULL,NULL);



/*Table structure for table `vzdn_types` */



-- DROP TABLE IF EXISTS `vzdn_types`;



CREATE TABLE `vzdn_types` (

  `type_id` INT(11) NOT NULL AUTO_INCREMENT,

  `type_def_id` INT(11) NOT NULL,

  `type_value` VARCHAR(100) NOT NULL,

  `description` VARCHAR(500) DEFAULT NULL,

  `sort_order` INT(11) NOT NULL,

  `created_by` VARCHAR(50) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `last_updated_by` VARCHAR(50) DEFAULT NULL,

  `last_updated_date` DATETIME DEFAULT NULL,

  PRIMARY KEY  (`type_id`),

  KEY `sys_c00306629` (`type_def_id`),

  CONSTRAINT `sys_c00306629` FOREIGN KEY (`type_def_id`) REFERENCES `vzdn_type_defs` (`type_def_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_types` */



INSERT  INTO `vzdn_types`(`type_id`,`type_def_id`,`type_value`,`description`,`sort_order`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`) VALUES (1,1,'Developer','some desc',1,NULL,NULL,NULL,NULL),(2,1,'Verizon','some desc',2,NULL,NULL,NULL,NULL),(3,1,'Both','some desc',3,NULL,NULL,NULL,NULL),(4,1,'Hidden','Hidden Role',0,NULL,NULL,NULL,NULL),(5,2,'Active','Active User',0,NULL,NULL,NULL,NULL),(6,2,'Inactive','Inactive User',0,NULL,NULL,NULL,NULL);



/*Table structure for table `vzdn_user_roles` */



-- DROP TABLE IF EXISTS `vzdn_user_roles`;



CREATE TABLE `vzdn_user_roles` (

  `role_id` INT(11) NOT NULL,

  `user_id` INT(11) NOT NULL,

  PRIMARY KEY  (`role_id`,`user_id`),

  KEY `sys_c00306631` (`user_id`),

  CONSTRAINT `sys_c00306630` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306631` FOREIGN KEY (`user_id`) REFERENCES `vzdn_users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_user_roles` */



/*Table structure for table `vzdn_users` */



-- DROP TABLE IF EXISTS `vzdn_users`;



CREATE TABLE `vzdn_users` (

  `user_id` INT(11) NOT NULL AUTO_INCREMENT,

  `user_name` VARCHAR(100) DEFAULT NULL,

  `password` VARCHAR(50) DEFAULT NULL,

  `title` VARCHAR(100) DEFAULT NULL,

  `last_name` VARCHAR(100) DEFAULT NULL,

  `first_name` VARCHAR(100) DEFAULT NULL,

  `phone_number` VARCHAR(50) DEFAULT NULL,

  `security_question` VARCHAR(255) DEFAULT NULL,

  `security_answer` VARCHAR(255) DEFAULT NULL,

  `created_by` VARCHAR(100) DEFAULT NULL,

  `created_date` DATETIME DEFAULT NULL,

  `last_updated_by` VARCHAR(100) DEFAULT NULL,

  `last_updated_date` DATETIME DEFAULT NULL,

  `fax_number` VARCHAR(50) DEFAULT NULL,

  `mobile_number` VARCHAR(50) DEFAULT NULL,

  `newsletter_opt_out` CHAR(1) DEFAULT NULL,

  `status_type_id` INT(11) DEFAULT NULL,

  `user_type_id` INT(11) DEFAULT NULL,

  `manager_organization` VARCHAR(100) DEFAULT NULL,

  `page_size` INT(10) DEFAULT NULL,

  PRIMARY KEY  (`user_id`),

  KEY `sys_c00306632` (`user_type_id`),

  KEY `sys_c00306633` (`status_type_id`),

  CONSTRAINT `sys_c00306632` FOREIGN KEY (`user_type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION,

  CONSTRAINT `sys_c00306633` FOREIGN KEY (`status_type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION

) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Data for the table `vzdn_users` */



/*Table structure for table `vzdn_zon_temp_users` */



-- DROP TABLE IF EXISTS `vzdn_zon_temp_users`;



CREATE TABLE `vzdn_zon_temp_users` (

  `user_id` INT(11) NOT NULL AUTO_INCREMENT,

  `username` VARCHAR(50) DEFAULT NULL,

  `user_type` VARCHAR(20) DEFAULT NULL,

  `title` VARCHAR(20) DEFAULT NULL,

  `first_name` VARCHAR(20) DEFAULT NULL,

  `last_name` VARCHAR(20) DEFAULT NULL,

  `phone_number` VARCHAR(20) DEFAULT NULL,

  `fax_number` VARCHAR(20) DEFAULT NULL,

  `mobile_number` VARCHAR(20) DEFAULT NULL,

  `password` VARCHAR(20) DEFAULT NULL,

  PRIMARY KEY  (`user_id`)

) ENGINE=INNODB DEFAULT CHARSET=utf8;





CREATE TABLE `vzdn_device_anywhere` (                   

                        `id` INT(11) NOT NULL AUTO_INCREMENT,                 

                        `useremail` VARCHAR(50) DEFAULT NULL,                 

                        `datetime` DATETIME DEFAULT NULL,                     

                        `gtm_company_id` VARCHAR(50) DEFAULT NULL,            

                        PRIMARY KEY  (`id`)                                   

                      ) ENGINE=INNODB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;









/*Data for the table `vzdn_zon_temp_users` */



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;





INSERT  INTO `vzdn_events`(`event_id`,`event_name`,`event_desc`,`created_date`,`created_by`,`last_updated_date`,`last_updated_by`) VALUES (1,'Test Event1','Test Event1 Description',NULL,NULL,NULL,NULL),(2,'Test Event2','Test Event2 Description',NULL,NULL,NULL,NULL),(3,'Test Event3','Test Event3 Description',NULL,NULL,NULL,NULL);



INSERT  INTO `vzdn_event_handlers`(`event_handler_id`,`event_id`,`class_name`) VALUES (1,1,'com.netpace.aims.bo.events.ApplicationEventHandler'),(2,2,'com.netpace.aims.bo.events.ApplicationEventHandler');



INSERT  INTO `vzdn_place_holders`(`place_holder_id`,`place_holder_display_name`,`created_date`,`created_by`,`last_updated_date`,`last_updated_by`) VALUES (1,'Place Holder1',NULL,NULL,NULL,NULL),(2,'Place Holder2',NULL,NULL,NULL,NULL),(3,'Place Holder3',NULL,NULL,NULL,NULL),(4,'Place Holder4',NULL,NULL,NULL,NULL),(5,'Place Holder5',NULL,NULL,NULL,NULL),(6,'Place Holder6',NULL,NULL,NULL,NULL);



INSERT  INTO `vzdn_event_place_holders`(`place_holder_id`,`event_id`) VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(1,2),(2,2),(3,2),(4,2),(5,2);



UPDATE `vzdn_sub_menus` SET `sub_menu_url` = 'notifications.action' WHERE sub_menu_id = 9;



UPDATE vzdn_sub_menus SET sub_menu_name = 'My Notifications' WHERE sub_menu_id=2;

UPDATE vzdn_sub_menus SET sub_menu_url = 'myNotification.action' WHERE sub_menu_id=2;



UPDATE vzdn_sys_privileges SET privilege_key = 'EDIT_PROFILE' WHERE privilege_id=1;

UPDATE vzdn_sys_privileges SET privilege_key = 'MY_NOTIFICATIONS' WHERE privilege_id=2;

UPDATE vzdn_sys_privileges SET privilege_key = 'BLOG_LINK_FOR_ADMIN' WHERE privilege_id=3;

UPDATE vzdn_sys_privileges SET privilege_key = 'FORUM_LINK_FOR_ADMIN' WHERE privilege_id=4;

UPDATE vzdn_sys_privileges SET privilege_key = 'APPLICATIONS' WHERE privilege_id=5;

UPDATE vzdn_sys_privileges SET privilege_key = 'ALLIANCES' WHERE privilege_id=6;

UPDATE vzdn_sys_privileges SET privilege_key = 'MANAGE_NOTIFICATIONS' WHERE privilege_id=7;

UPDATE vzdn_sys_privileges SET privilege_key = 'PUBLISH_CONTENT' WHERE privilege_id=8;

UPDATE vzdn_sys_privileges SET privilege_key = 'MANAGE_USERS' WHERE privilege_id=11;

UPDATE vzdn_sys_privileges SET privilege_key = 'MY_APPLICATION_FOR_DEV' WHERE privilege_id=12;

UPDATE vzdn_sys_privileges SET privilege_key = 'MY_CONTRACTS_FOR_DEV' WHERE privilege_id=13;

UPDATE vzdn_sys_privileges SET privilege_key = 'CONTRACTS_PAGE_FOR_VZ_USER' WHERE privilege_id=14;

UPDATE vzdn_sys_privileges SET privilege_key = 'BLOG_LINK_FOR_DEVELOPER' WHERE privilege_id=15;

UPDATE vzdn_sys_privileges SET privilege_key = 'FORUM_LINK_FOR_DEVELOPER' WHERE privilege_id=16;

UPDATE vzdn_sys_privileges SET privilege_key = 'BLOG_USER_LINK' WHERE privilege_id=17;



ALTER TABLE vzdn_users ADD COLUMN gtm_comp_id VARCHAR(500);



ALTER TABLE vzdn_users ADD COLUMN country VARCHAR(500);



UPDATE vzdn_sub_menus SET sub_menu_url='http://developer.verizon.com/cpm' WHERE sub_menu_id=10;

INSERT INTO vzdn_sys_privileges 
	(privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, last_updated_date, menu_id, sub_menu_id, privilege_key, available_to)
	VALUES
	(18, 'ca_super_admin', 'Content Authoring Super Admin', 'system', NULL, NULL, NULL, NULL, 10, 'CA_SUPER_ADMIN', NULL);

INSERT INTO vzdn_sys_privileges 
	(privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, last_updated_date, menu_id, sub_menu_id, privilege_key, available_to)
	VALUES	
	(19, 'content_manager', 'Content Manager', 'system', NULL, NULL, NULL, NULL, 10, 'CONTENT_MANAGER', NULL);
	
UPDATE vzdn_sys_privileges 
	SET privilege_name = 'content_publisher' , privilege_description = 'Content Publisher' , privilege_key = 'CONTENT_PUBLISHER' 
	WHERE privilege_id = '8' ;
	

INSERT INTO vzdn_sys_roles 
	(role_id,  role_name, role_description, type_id, created_by, created_date, last_updated_by, last_updated_date, vzdn_role_mapping_id)
	VALUES
	(38, 'CA - Super Admin', 'CA - Super Admin', '4', 'system', NULL, NULL, NULL, 3005);
	
INSERT INTO vzdn_sys_roles 
	(role_id,  role_name, role_description, type_id, created_by, created_date, last_updated_by, last_updated_date, vzdn_role_mapping_id)
	VALUES
	(39, 'CA - Content Publisher', 'CA - Content Publisher', '2', 'system', NULL, NULL, NULL, 3002);
	
INSERT INTO vzdn_sys_roles 
	(role_id,  role_name, role_description, type_id, created_by, created_date, last_updated_by, last_updated_date, vzdn_role_mapping_id)
	VALUES
	(40, 'CA - Content Manager', 'CA - Content Manager', '2', 'system', NULL, NULL, NULL, 3001);
	
    
INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(42,'GTM - Brew Evaluation','GTM - Brew Evaluation',2,NULL,NULL,NULL,NULL,2032);


INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(43,'GTM - Brew Content Standard Approval','GTM - Brew Content Standard Approval',2,NULL,NULL,NULL,NULL,2033);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(44,'GTM - Brew Legal Approval','GTM - Brew Legal Approval',2,NULL,NULL,NULL,NULL,2034);

INSERT  INTO `vzdn_sys_roles`

(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) 

VALUES 

(45,'GTM - JMA Admin','GTM - JMA Admin',2,NULL,NULL,NULL,NULL,2035);


    
    
    
INSERT INTO vzdn_role_privileges 
	(role_id, privilege_id)
	VALUES
	(38, 18);
	
INSERT INTO vzdn_role_privileges 
	(role_id, privilege_id)
	VALUES
	(39, 8);
	
INSERT INTO vzdn_role_privileges 
	(role_id, privilege_id)
	VALUES
	(40, 19);