/*
SQLyog Community Edition- MySQL GUI v8.05 
MySQL - 5.0.45-log : Database - vzdn
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`vzdn` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `vzdn`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `vzdn_dev_care_categories` */

DROP TABLE IF EXISTS `vzdn_dev_care_categories`;

CREATE TABLE `vzdn_dev_care_categories` (
  `category_id` int(11) NOT NULL auto_increment,
  `category_name` varchar(50) NOT NULL,
  `created_by` int(11) default NULL,
  `created_date` datetime default NULL,
  `updated_by` int(11) default NULL,
  `updated_date` datetime default NULL,
  PRIMARY KEY  (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_dev_care_categories` */

/*Table structure for table `vzdn_dev_care_issues` */

DROP TABLE IF EXISTS `vzdn_dev_care_issues`;

CREATE TABLE `vzdn_dev_care_issues` (
  `issue_id` int(11) NOT NULL auto_increment,
  `title` varchar(50) default NULL,
  `description` varchar(500) default NULL,
  `category_id` int(11) default NULL,
  `ticket_id` varchar(200) NOT NULL,
  `user_id` int(11) default NULL,
  `attachment` longblob,
  `attachment_name` varchar(500) default NULL,
  `sub_category_id` int(11) default NULL,
  `created_by` int(11) default NULL,
  `created_date` datetime default NULL,
  `updated_by` int(11) default NULL,
  `updated_date` datetime default NULL,
  `attachment_type` varchar(20) default NULL,
  `type_id` int(11) default NULL,
  PRIMARY KEY  (`issue_id`),
  KEY `sys_c00306604` (`type_id`),
  KEY `sys_c00306605` (`sub_category_id`),
  KEY `sys_c00306606` (`category_id`),
  CONSTRAINT `sys_c00306604` FOREIGN KEY (`type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306605` FOREIGN KEY (`sub_category_id`) REFERENCES `vzdn_dev_care_sub_categories` (`sub_category_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306606` FOREIGN KEY (`category_id`) REFERENCES `vzdn_dev_care_categories` (`category_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_dev_care_issues` */

/*Table structure for table `vzdn_dev_care_life_cycle` */

DROP TABLE IF EXISTS `vzdn_dev_care_life_cycle`;

CREATE TABLE `vzdn_dev_care_life_cycle` (
  `comment_id` int(11) NOT NULL auto_increment,
  `comments` varchar(1000) default NULL,
  `user_id` int(11) default NULL,
  `created_by` int(11) default NULL,
  `created_date` datetime default NULL,
  `updated_by` int(11) default NULL,
  `updated_date` datetime default NULL,
  `issue_id` int(11) default NULL,
  `type_id` int(11) default NULL,
  PRIMARY KEY  (`comment_id`),
  KEY `sys_c00306607` (`type_id`),
  KEY `sys_c00306608` (`issue_id`),
  KEY `sys_c00306609` (`user_id`),
  CONSTRAINT `sys_c00306607` FOREIGN KEY (`type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306608` FOREIGN KEY (`issue_id`) REFERENCES `vzdn_dev_care_issues` (`issue_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306609` FOREIGN KEY (`user_id`) REFERENCES `vzdn_users` (`user_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_dev_care_life_cycle` */

/*Table structure for table `vzdn_dev_care_sub_categories` */

DROP TABLE IF EXISTS `vzdn_dev_care_sub_categories`;

CREATE TABLE `vzdn_dev_care_sub_categories` (
  `sub_category_id` int(11) NOT NULL auto_increment,
  `sub_category_name` varchar(50) NOT NULL,
  `created_by` int(11) default NULL,
  `category_id` int(11) default NULL,
  `updated_by` int(11) default NULL,
  `updated_date` datetime default NULL,
  `created_date` datetime default NULL,
  PRIMARY KEY  (`sub_category_id`),
  KEY `sys_c00306610` (`category_id`),
  CONSTRAINT `sys_c00306610` FOREIGN KEY (`category_id`) REFERENCES `vzdn_dev_care_categories` (`category_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_dev_care_sub_categories` */

/*Table structure for table `vzdn_email_messages` */

DROP TABLE IF EXISTS `vzdn_email_messages`;

CREATE TABLE `vzdn_email_messages` (
  `email_message_id` int(11) NOT NULL auto_increment,
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

/*Data for the table `vzdn_email_messages` */

/*Table structure for table `vzdn_email_messages_att` */

DROP TABLE IF EXISTS `vzdn_email_messages_att`;

CREATE TABLE `vzdn_email_messages_att` (
  `email_message_id` int(11) default NULL,
  `att` longblob,
  `att_file_name` varchar(150) default NULL,
  `att_content_type` varchar(100) default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  KEY `sys_c00306611` (`email_message_id`),
  CONSTRAINT `sys_c00306611` FOREIGN KEY (`email_message_id`) REFERENCES `vzdn_email_messages` (`email_message_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_email_messages_att` */

/*Table structure for table `vzdn_event_handlers` */

DROP TABLE IF EXISTS `vzdn_event_handlers`;

CREATE TABLE `vzdn_event_handlers` (
  `event_handler_id` int(11) NOT NULL auto_increment,
  `event_id` int(11) default NULL,
  `class_name` varchar(200) default NULL,
  PRIMARY KEY  (`event_handler_id`),
  KEY `sys_c00306612` (`event_id`),
  CONSTRAINT `sys_c00306612` FOREIGN KEY (`event_id`) REFERENCES `vzdn_events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_event_handlers` */

/*Table structure for table `vzdn_event_notifications` */

DROP TABLE IF EXISTS `vzdn_event_notifications`;

CREATE TABLE `vzdn_event_notifications` (
  `notification_id` int(11) NOT NULL auto_increment,
  `message_id` int(11) default NULL,
  `event_id` int(11) default NULL,
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

/*Data for the table `vzdn_event_notifications` */

/*Table structure for table `vzdn_event_place_holders` */

DROP TABLE IF EXISTS `vzdn_event_place_holders`;

CREATE TABLE `vzdn_event_place_holders` (
  `place_holder_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  PRIMARY KEY  (`place_holder_id`,`event_id`),
  KEY `sys_c00306616` (`event_id`),
  CONSTRAINT `sys_c00306615` FOREIGN KEY (`place_holder_id`) REFERENCES `vzdn_place_holders` (`place_holder_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306616` FOREIGN KEY (`event_id`) REFERENCES `vzdn_events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_event_place_holders` */

/*Table structure for table `vzdn_events` */

DROP TABLE IF EXISTS `vzdn_events`;

CREATE TABLE `vzdn_events` (
  `event_id` int(11) NOT NULL auto_increment,
  `event_name` varchar(50) default NULL,
  `event_desc` varchar(200) default NULL,
  `created_date` datetime default NULL,
  `created_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  PRIMARY KEY  (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_events` */

/*Table structure for table `vzdn_menus` */

DROP TABLE IF EXISTS `vzdn_menus`;

CREATE TABLE `vzdn_menus` (
  `menu_id` int(11) NOT NULL auto_increment,
  `menu_name` varchar(100) default NULL,
  `menu_url` varchar(200) default NULL,
  `image_name` varchar(100) default NULL,
  `sort_order` int(11) default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  PRIMARY KEY  (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_menus` */

insert  into `vzdn_menus`(`menu_id`,`menu_name`,`menu_url`,`image_name`,`sort_order`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`) values (1,'My Info',NULL,'icon_myinfo.gif',1,'ikram','0000-00-00 00:00:00',NULL,NULL),(2,'Community',NULL,'icon_community.gif',2,'ikram','0000-00-00 00:00:00',NULL,NULL),(3,'Go To Market',NULL,'icon_gotoMarket.gif',3,'ikram','0000-00-00 00:00:00',NULL,NULL),(4,'Verizon Developer Network News',NULL,'icon_rss2.gif',4,'ikram','0000-00-00 00:00:00',NULL,NULL),(5,'Management',NULL,'icon_management.gif',5,'ikram','0000-00-00 00:00:00',NULL,NULL);

/*Table structure for table `vzdn_newsletters_log` */

DROP TABLE IF EXISTS `vzdn_newsletters_log`;

CREATE TABLE `vzdn_newsletters_log` (
  `log_id` int(11) NOT NULL auto_increment,
  `email_subject` varchar(255) default NULL,
  `email_body` varchar(1000) default NULL,
  `email_attachment` char(18) default NULL,
  `email_addresses` varchar(1000) default NULL,
  `description` varchar(255) default NULL,
  `created_date` datetime default NULL,
  `created_by` varchar(100) default NULL,
  PRIMARY KEY  (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_newsletters_log` */

/*Table structure for table `vzdn_notif_ad_hoc_recipients` */

DROP TABLE IF EXISTS `vzdn_notif_ad_hoc_recipients`;

CREATE TABLE `vzdn_notif_ad_hoc_recipients` (
  `ad_hoc_id` int(11) NOT NULL auto_increment,
  `notification_id` int(11) default NULL,
  `email_address` varchar(100) default NULL,
  PRIMARY KEY  (`ad_hoc_id`),
  KEY `sys_c00306617` (`notification_id`),
  CONSTRAINT `sys_c00306617` FOREIGN KEY (`notification_id`) REFERENCES `vzdn_event_notifications` (`notification_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_notif_ad_hoc_recipients` */

/*Table structure for table `vzdn_notif_opt_out_recipients` */

DROP TABLE IF EXISTS `vzdn_notif_opt_out_recipients`;

CREATE TABLE `vzdn_notif_opt_out_recipients` (
  `notification_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY  (`notification_id`,`user_id`),
  KEY `sys_c00306618` (`user_id`),
  CONSTRAINT `sys_c00306618` FOREIGN KEY (`user_id`) REFERENCES `vzdn_users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306619` FOREIGN KEY (`notification_id`) REFERENCES `vzdn_event_notifications` (`notification_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_notif_opt_out_recipients` */

/*Table structure for table `vzdn_notification_roles` */

DROP TABLE IF EXISTS `vzdn_notification_roles`;

CREATE TABLE `vzdn_notification_roles` (
  `notification_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY  (`notification_id`,`role_id`),
  KEY `sys_c00306621` (`role_id`),
  CONSTRAINT `sys_c00306620` FOREIGN KEY (`notification_id`) REFERENCES `vzdn_event_notifications` (`notification_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306621` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_notification_roles` */

/*Table structure for table `vzdn_place_holders` */

DROP TABLE IF EXISTS `vzdn_place_holders`;

CREATE TABLE `vzdn_place_holders` (
  `place_holder_id` int(11) NOT NULL auto_increment,
  `place_holder_display_name` varchar(200) default NULL,
  `created_date` datetime default NULL,
  `created_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  PRIMARY KEY  (`place_holder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_place_holders` */

/*Table structure for table `vzdn_report_roles` */

DROP TABLE IF EXISTS `vzdn_report_roles`;

CREATE TABLE `vzdn_report_roles` (
  `report_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY  (`report_id`,`role_id`),
  KEY `sys_c00306622` (`role_id`),
  CONSTRAINT `sys_c00306622` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306623` FOREIGN KEY (`report_id`) REFERENCES `vzdn_reports` (`report_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_report_roles` */

/*Table structure for table `vzdn_reports` */

DROP TABLE IF EXISTS `vzdn_reports`;

CREATE TABLE `vzdn_reports` (
  `report_id` int(11) NOT NULL auto_increment,
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

/*Data for the table `vzdn_reports` */

/*Table structure for table `vzdn_role_privileges` */

DROP TABLE IF EXISTS `vzdn_role_privileges`;

CREATE TABLE `vzdn_role_privileges` (
  `role_id` int(11) NOT NULL,
  `privilege_id` int(11) NOT NULL,
  PRIMARY KEY  (`role_id`,`privilege_id`),
  KEY `sys_c00306625` (`privilege_id`),
  CONSTRAINT `sys_c00306624` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306625` FOREIGN KEY (`privilege_id`) REFERENCES `vzdn_sys_privileges` (`privilege_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_role_privileges` */

insert  into `vzdn_role_privileges`(`role_id`,`privilege_id`) values (5,1),(6,1),(5,2),(5,3),(5,4),(5,5),(5,6),(5,7),(5,8),(5,11),(6,12),(6,13),(5,14),(6,15),(6,16);

/*Table structure for table `vzdn_sub_menus` */

DROP TABLE IF EXISTS `vzdn_sub_menus`;

CREATE TABLE `vzdn_sub_menus` (
  `sub_menu_id` int(11) NOT NULL auto_increment,
  `sub_menu_name` varchar(100) default NULL,
  `sub_menu_url` varchar(200) default NULL,
  `image_name` varchar(100) default NULL,
  `sort_order` int(11) default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  `menu_id` int(11) default NULL,
  PRIMARY KEY  (`sub_menu_id`),
  KEY `sys_c00306626` (`menu_id`),
  CONSTRAINT `sys_c00306626` FOREIGN KEY (`menu_id`) REFERENCES `vzdn_menus` (`menu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_sub_menus` */

insert  into `vzdn_sub_menus`(`sub_menu_id`,`sub_menu_name`,`sub_menu_url`,`image_name`,`sort_order`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`menu_id`) values (1,'Edit Profile','#',NULL,1,'ikram',NULL,NULL,NULL,1),(2,'Notifications','#',NULL,2,'ikram',NULL,NULL,NULL,1),(3,'Blog Administration','http://vzdnapp.netpace.com/Weblogger/roller-ui/menu.rol',NULL,1,'ikram',NULL,NULL,NULL,2),(4,'Forum Administration','http://vzdnapp.netpace.com/jforum/admBase/login.page',NULL,2,'ikram',NULL,NULL,NULL,2),(5,'Application','http://dts1.netpace.com/aims/applicationsViewDelete.do?task=view&app_type=all_apps&cancel_search',NULL,1,'ikram',NULL,NULL,NULL,3),(6,'Alliances','http://dts1.netpace.com/aims/vzwAlliance.do?task=view&all_type=ALL',NULL,2,'ikram',NULL,NULL,NULL,3),(7,'New JDK Download Available','#',NULL,1,'ikram',NULL,NULL,NULL,4),(8,'Brew gets better','#',NULL,2,'ikram',NULL,NULL,NULL,4),(9,'Notifications','#',NULL,1,'ikram',NULL,NULL,NULL,5),(10,'Content Publishing','#',NULL,2,'ikram',NULL,NULL,NULL,5),(13,'Users Management','searchUsersAction.action',NULL,5,'ikram',NULL,NULL,NULL,5),(14,'My Applications','http://dts1.netpace.com/aims/applicationsViewDelete.do?task=view&app_type=all_apps&cancel_search',NULL,6,'ikram',NULL,NULL,NULL,3),(15,'My Contracts','http://dts1.netpace.com/aims/allianceContracts.do?task=view',NULL,7,NULL,NULL,NULL,NULL,3),(16,'Contracts','http://dts1.netpace.com/aims/contracts.do?task=view',NULL,8,NULL,NULL,NULL,NULL,3),(17,'Forum','http://vzdnapp.netpace.com/jforum/forums/list.page',NULL,9,NULL,NULL,NULL,NULL,2),(18,'Blog','http://vzdnapp.netpace.com/Weblogger/',NULL,10,NULL,NULL,NULL,NULL,2);

/*Table structure for table `vzdn_sys_privileges` */

DROP TABLE IF EXISTS `vzdn_sys_privileges`;

CREATE TABLE `vzdn_sys_privileges` (
  `privilege_id` int(11) NOT NULL auto_increment,
  `privilege_name` varchar(100) default NULL,
  `privilege_description` varchar(200) default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  `menu_id` int(11) default NULL,
  `sub_menu_id` int(11) default NULL,
  `privilege_key` varchar(100) default NULL,
  `available_to` varchar(1) default NULL,
  PRIMARY KEY  (`privilege_id`),
  KEY `sys_c00306627` (`menu_id`),
  KEY `sys_c00306628` (`sub_menu_id`),
  CONSTRAINT `sys_c00306627` FOREIGN KEY (`menu_id`) REFERENCES `vzdn_menus` (`menu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306628` FOREIGN KEY (`sub_menu_id`) REFERENCES `vzdn_sub_menus` (`sub_menu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_sys_privileges` */

insert  into `vzdn_sys_privileges`(`privilege_id`,`privilege_name`,`privilege_description`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`menu_id`,`sub_menu_id`,`privilege_key`,`available_to`) values (1,'edit_profile','Edit Profile','ikram',NULL,NULL,NULL,NULL,1,NULL,NULL),(2,'notifications','Notifications','ikram',NULL,NULL,NULL,NULL,2,NULL,NULL),(3,'blog_link_for_admin','Blogs','ikram',NULL,NULL,NULL,NULL,3,NULL,NULL),(4,'forum_link_for_admin','Forums','ikram',NULL,NULL,NULL,NULL,4,NULL,NULL),(5,'applications','Applications','ikram',NULL,NULL,NULL,NULL,5,NULL,NULL),(6,'alliances','Alliances','ikram',NULL,NULL,NULL,NULL,6,NULL,NULL),(7,'manage_notifications','Notifications Management','ikram',NULL,NULL,NULL,NULL,9,NULL,NULL),(8,'publish_content','Conent Publishing','ikram',NULL,NULL,NULL,NULL,10,NULL,NULL),(11,'manage_users','Manager Users','ikram',NULL,NULL,NULL,NULL,13,NULL,NULL),(12,'my_application_for_dev','My Application Link for Developers','ikram',NULL,NULL,NULL,NULL,14,NULL,NULL),(13,'my_contracts_for_dev','My Contracts Link for Developers',NULL,NULL,NULL,NULL,NULL,15,NULL,NULL),(14,'contracts_page_for_vz_user','contracts link for Verizon User',NULL,NULL,NULL,NULL,NULL,16,NULL,NULL),(15,'blog_link_for_developer','blogs link  for developer',NULL,NULL,NULL,NULL,NULL,18,NULL,NULL),(16,'forum_link_for_developer','forum link for developer',NULL,NULL,NULL,NULL,NULL,17,NULL,NULL);

/*Table structure for table `vzdn_sys_roles` */

DROP TABLE IF EXISTS `vzdn_sys_roles`;

CREATE TABLE `vzdn_sys_roles` (
  `role_id` int(11) NOT NULL auto_increment,
  `role_name` varchar(50) default NULL,
  `role_description` varchar(500) default NULL,
  `type_id` int(11) NOT NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  `vzdn_role_mapping_id` int(11) NOT NULL,
  PRIMARY KEY  (`role_id`),
  KEY `sys_c00306634` (`type_id`),
  CONSTRAINT `sys_c00306634` FOREIGN KEY (`type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_sys_roles` */

insert  into `vzdn_sys_roles`(`role_id`,`role_name`,`role_description`,`type_id`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`,`vzdn_role_mapping_id`) values (1,'Forum User','Forum User',2,NULL,NULL,NULL,NULL,5002),(2,'Forum Admin','Forum Admin',2,NULL,NULL,NULL,NULL,5001),(3,'Blog User','Blog User',3,NULL,NULL,NULL,NULL,4002),(4,'Blog Admin','Blog Admin',2,NULL,NULL,NULL,NULL,4001),(5,'System Admin','VzdnCore Admin',2,NULL,NULL,NULL,NULL,1001),(6,'Developer Role','This role will be assigned to all developers when they log in',4,NULL,NULL,NULL,NULL,0);

/*Table structure for table `vzdn_temp_files` */

DROP TABLE IF EXISTS `vzdn_temp_files`;

CREATE TABLE `vzdn_temp_files` (
  `temp_file_id` int(11) NOT NULL auto_increment,
  `session_id` varchar(1000) default NULL,
  `temp_file` longblob,
  `temp_file_name` varchar(150) default NULL,
  `temp_file_content_type` varchar(100) default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `temp_file_comments` varchar(1000) default NULL,
  PRIMARY KEY  (`temp_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_temp_files` */

/*Table structure for table `vzdn_type_defs` */

DROP TABLE IF EXISTS `vzdn_type_defs`;

CREATE TABLE `vzdn_type_defs` (
  `type_def_id` int(11) NOT NULL auto_increment,
  `type_name` varchar(100) NOT NULL,
  `type_description` varchar(500) default NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  PRIMARY KEY  (`type_def_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_type_defs` */

insert  into `vzdn_type_defs`(`type_def_id`,`type_name`,`type_description`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`) values (1,'Role Type','Role Type',NULL,NULL,NULL,NULL),(2,'User Status Type','User Status Type',NULL,NULL,NULL,NULL);

/*Table structure for table `vzdn_types` */

DROP TABLE IF EXISTS `vzdn_types`;

CREATE TABLE `vzdn_types` (
  `type_id` int(11) NOT NULL auto_increment,
  `type_def_id` int(11) NOT NULL,
  `type_value` varchar(100) NOT NULL,
  `description` varchar(500) default NULL,
  `sort_order` int(11) NOT NULL,
  `created_by` varchar(50) default NULL,
  `created_date` datetime default NULL,
  `last_updated_by` varchar(50) default NULL,
  `last_updated_date` datetime default NULL,
  PRIMARY KEY  (`type_id`),
  KEY `sys_c00306629` (`type_def_id`),
  CONSTRAINT `sys_c00306629` FOREIGN KEY (`type_def_id`) REFERENCES `vzdn_type_defs` (`type_def_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_types` */

insert  into `vzdn_types`(`type_id`,`type_def_id`,`type_value`,`description`,`sort_order`,`created_by`,`created_date`,`last_updated_by`,`last_updated_date`) values (1,1,'Developer','some desc',1,NULL,NULL,NULL,NULL),(2,1,'Verizon','some desc',2,NULL,NULL,NULL,NULL),(3,1,'Both','some desc',3,NULL,NULL,NULL,NULL),(4,1,'Hidden','Hidden Role',0,NULL,NULL,NULL,NULL),(5,2,'Active','Active User',0,NULL,NULL,NULL,NULL),(6,2,'Inactive','Inactive User',0,NULL,NULL,NULL,NULL);

/*Table structure for table `vzdn_user_roles` */

DROP TABLE IF EXISTS `vzdn_user_roles`;

CREATE TABLE `vzdn_user_roles` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY  (`role_id`,`user_id`),
  KEY `sys_c00306631` (`user_id`),
  CONSTRAINT `sys_c00306630` FOREIGN KEY (`role_id`) REFERENCES `vzdn_sys_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306631` FOREIGN KEY (`user_id`) REFERENCES `vzdn_users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_user_roles` */

/*Table structure for table `vzdn_users` */

DROP TABLE IF EXISTS `vzdn_users`;

CREATE TABLE `vzdn_users` (
  `user_id` int(11) NOT NULL auto_increment,
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
  `status_type_id` int(11) default NULL,
  `user_type_id` int(11) default NULL,
  `manager_organization` varchar(100) default NULL,
  `page_size` int(10) default NULL,
  PRIMARY KEY  (`user_id`),
  KEY `sys_c00306632` (`user_type_id`),
  KEY `sys_c00306633` (`status_type_id`),
  CONSTRAINT `sys_c00306632` FOREIGN KEY (`user_type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `sys_c00306633` FOREIGN KEY (`status_type_id`) REFERENCES `vzdn_types` (`type_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vzdn_users` */

/*Table structure for table `vzdn_zon_temp_users` */

DROP TABLE IF EXISTS `vzdn_zon_temp_users`;

CREATE TABLE `vzdn_zon_temp_users` (
  `user_id` int(11) NOT NULL auto_increment,
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

/*Data for the table `vzdn_zon_temp_users` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
