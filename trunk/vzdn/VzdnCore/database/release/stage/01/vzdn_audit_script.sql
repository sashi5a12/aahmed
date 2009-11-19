/*
SQLyog Community Edition- MySQL GUI v8.05 
MySQL - 5.0.45-log : Database - vzdn
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- CREATE DATABASE /*!32312 IF NOT EXISTS*/`vzdn` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `vzdn`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Audit Table structure for table `vzdn_dev_care_categories` */

-- DROP TABLE IF EXISTS `vzdn_dev_care_categories$aud`;

CREATE TABLE `vzdn_dev_care_categories$aud` (
  `category_id` INT(11) ,
  `category_name` VARCHAR(50) ,
  `created_by` INT(11) ,
  `created_date` DATETIME ,
  `updated_by` INT(11) ,
  `updated_date` DATETIME ,
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_dev_care_issues` */

-- DROP TABLE IF EXISTS `vzdn_dev_care_issues$aud`;

CREATE TABLE `vzdn_dev_care_issues$aud` (
  `issue_id` INT(11),
  `title` VARCHAR(50),
  `description` VARCHAR(500),
  `category_id` INT(11),
  `ticket_id` VARCHAR(200),
  `user_id` INT(11),
  `attachment_name` VARCHAR(500),
  `sub_category_id` INT(11) ,
  `created_by` INT(11) ,
  `created_date` DATETIME ,
  `updated_by` INT(11) ,
  `updated_date` DATETIME ,
  `attachment_type` VARCHAR(20) ,
  `type_id` INT(11),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_dev_care_life_cycle` */

-- DROP TABLE IF EXISTS `vzdn_dev_care_life_cycle$aud`;

CREATE TABLE `vzdn_dev_care_life_cycle$aud` (
  `comment_id` INT(11) ,
  `comments` VARCHAR(1000),
  `user_id` INT(11) ,
  `created_by` INT(11) ,
  `created_date` DATETIME ,
  `updated_by` INT(11) ,
  `updated_date` DATETIME ,
  `issue_id` INT(11) ,
  `type_id` INT(11),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_dev_care_sub_categories` */

-- DROP TABLE IF EXISTS `vzdn_dev_care_sub_categories$aud`;

CREATE TABLE `vzdn_dev_care_sub_categories$aud` (
  `sub_category_id` INT(11) ,
  `sub_category_name` VARCHAR(50) ,
  `created_by` INT(11) ,
  `category_id` INT(11) ,
  `updated_by` INT(11) ,
  `updated_date` DATETIME ,
  `created_date` DATETIME,
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_email_messages` */

-- DROP TABLE IF EXISTS `vzdn_email_messages$aud`;

CREATE TABLE `vzdn_email_messages$aud` (
  `email_message_id` INT(11),
  `email_title` VARCHAR(200),
  `email_text` VARCHAR(3500),
  `email_desc` VARCHAR(1000),
  `email_category` VARCHAR(100),
  `created_by` VARCHAR(50),
  `created_date` DATETIME,
  `last_updated_by` VARCHAR(50),
  `last_updated_date` DATETIME ,
  `from_address` VARCHAR(200),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_email_messages_att` */

-- DROP TABLE IF EXISTS `vzdn_email_messages_att$aud`;

CREATE TABLE `vzdn_email_messages_att$aud` (
  `email_message_id` INT(11) ,
  `att_file_name` VARCHAR(150) ,
  `att_content_type` VARCHAR(100) ,
  `created_by` VARCHAR(50) ,
  `created_date` DATETIME,
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_event_handlers` */

-- DROP TABLE IF EXISTS `vzdn_event_handlers$aud`;

CREATE TABLE `vzdn_event_handlers$aud` (
  `event_handler_id` INT(11) ,
  `event_id` INT(11) ,
  `class_name` VARCHAR(200),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_event_notifications` */

-- DROP TABLE IF EXISTS `vzdn_event_notifications$aud`;

CREATE TABLE `vzdn_event_notifications$aud` (
  `notification_id` INT(11) ,
  `message_id` INT(11) ,
  `event_id` INT(11) ,
  `notification_title` VARCHAR(100) ,
  `media` VARCHAR(20) ,
  `status` VARCHAR(20) ,
  `from_address` VARCHAR(100),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_event_place_holders` */

-- DROP TABLE IF EXISTS `vzdn_event_place_holders$aud`;

CREATE TABLE `vzdn_event_place_holders$aud` (
  `place_holder_id` INT(11) ,
  `event_id` INT(11),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_events` */

-- DROP TABLE IF EXISTS `vzdn_events$aud`;

CREATE TABLE `vzdn_events$aud` (
  `event_id` INT(11) ,
  `event_name` VARCHAR(50) ,
  `event_desc` VARCHAR(200) ,
  `created_date` DATETIME ,
  `created_by` VARCHAR(50) ,
  `last_updated_date` DATETIME ,
  `last_updated_by` VARCHAR(50),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_menus` */

-- DROP TABLE IF EXISTS `vzdn_menus$aud`;

CREATE TABLE `vzdn_menus$aud` (
  `menu_id` INT(11) ,
  `menu_name` VARCHAR(100) ,
  `menu_url` VARCHAR(200) ,
  `image_name` VARCHAR(100) ,
  `sort_order` INT(11) ,
  `created_by` VARCHAR(50) ,
  `created_date` DATETIME ,
  `last_updated_by` VARCHAR(50) ,
  `last_updated_date` DATETIME,
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_newsletters_log` */

-- DROP TABLE IF EXISTS `vzdn_newsletters_log$aud`;

CREATE TABLE `vzdn_newsletters_log$aud` (
  `log_id` INT(11) ,
  `email_subject` VARCHAR(255) ,
  `email_body` VARCHAR(1000) ,
  `email_attachment` CHAR(18) ,
  `email_addresses` VARCHAR(1000) ,
  `description` VARCHAR(255) ,
  `created_date` DATETIME ,
  `created_by` VARCHAR(100),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_notif_ad_hoc_recipients` */

-- DROP TABLE IF EXISTS `vzdn_notif_ad_hoc_recipients$aud`;

CREATE TABLE `vzdn_notif_ad_hoc_recipients$aud` (
  `ad_hoc_id` INT(11) ,
  `notification_id` INT(11) ,
  `email_address` VARCHAR(100),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_notif_opt_out_recipients` */

-- DROP TABLE IF EXISTS `vzdn_notif_opt_out_recipients$aud`;

CREATE TABLE `vzdn_notif_opt_out_recipients$aud` (
  `notification_id` INT(11) ,
  `user_id` INT(11),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_notification_roles` */

-- DROP TABLE IF EXISTS `vzdn_notification_roles$aud`;

CREATE TABLE `vzdn_notification_roles$aud` (
  `notification_id` INT(11) ,
  `role_id` INT(11),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_place_holders` */

-- DROP TABLE IF EXISTS `vzdn_place_holders$aud`;

CREATE TABLE `vzdn_place_holders$aud` (
  `place_holder_id` INT(11) ,
  `place_holder_display_name` VARCHAR(200) ,
  `created_date` DATETIME ,
  `created_by` VARCHAR(50) ,
  `last_updated_date` DATETIME ,
  `last_updated_by` VARCHAR(50),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_report_roles` */

-- DROP TABLE IF EXISTS `vzdn_report_roles$aud`;

CREATE TABLE `vzdn_report_roles$aud` (
  `report_id` INT(11) ,
  `role_id` INT(11),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_reports` */

-- DROP TABLE IF EXISTS `vzdn_reports$aud`;

CREATE TABLE `vzdn_reports$aud` (
  `report_id` INT(11) ,
  `name` VARCHAR(100) ,
  `description` VARCHAR(500) ,
  `report_file` VARCHAR(100) ,
  `pdf_export` CHAR(1) ,
  `cvs_export` CHAR(1) ,
  `rtf_export` CHAR(1) ,
  `html_export` CHAR(1) ,
  `created_by` VARCHAR(100) ,
  `created_date` DATETIME ,
  `last_updated_by` VARCHAR(100) ,
  `last_updated_date` DATETIME,
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_role_privileges` */

-- DROP TABLE IF EXISTS `vzdn_role_privileges$aud`;

CREATE TABLE `vzdn_role_privileges$aud` (
  `role_id` INT(11) ,
  `privilege_id` INT(11),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_sub_menus` */

-- DROP TABLE IF EXISTS `vzdn_sub_menus$aud`;

CREATE TABLE `vzdn_sub_menus$aud` (
  `sub_menu_id` INT(11) ,
  `sub_menu_name` VARCHAR(100) ,
  `sub_menu_url` VARCHAR(200) ,
  `image_name` VARCHAR(100) ,
  `sort_order` INT(11) ,
  `created_by` VARCHAR(50) ,
  `created_date` DATETIME ,
  `last_updated_by` VARCHAR(50) ,
  `last_updated_date` DATETIME ,
  `menu_id` INT(11),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_sys_privileges` */

-- DROP TABLE IF EXISTS `vzdn_sys_privileges$aud`;

CREATE TABLE `vzdn_sys_privileges$aud` (
  `privilege_id` INT(11) ,
  `privilege_name` VARCHAR(100) ,
  `privilege_description` VARCHAR(200) ,
  `created_by` VARCHAR(50) ,
  `created_date` DATETIME ,
  `last_updated_by` VARCHAR(50) ,
  `last_updated_date` DATETIME ,
  `menu_id` INT(11) ,
  `sub_menu_id` INT(11) ,
  `privilege_key` VARCHAR(100) ,
  `available_to` VARCHAR(1),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_sys_roles` */

-- DROP TABLE IF EXISTS `vzdn_sys_roles$aud`;

CREATE TABLE `vzdn_sys_roles$aud` (
  `role_id` INT(11) ,
  `role_name` VARCHAR(50) ,
  `role_description` VARCHAR(500) ,
  `type_id` INT(11) ,
  `created_by` VARCHAR(50) ,
  `created_date` DATETIME ,
  `last_updated_by` VARCHAR(50) ,
  `last_updated_date` DATETIME ,
  `vzdn_role_mapping_id` INT(11),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_temp_files` */

-- DROP TABLE IF EXISTS `vzdn_temp_files$aud`;

CREATE TABLE `vzdn_temp_files$aud` (
  `temp_file_id` INT(11) ,
  `session_id` VARCHAR(1000) ,
  `temp_file_name` VARCHAR(150) ,
  `temp_file_content_type` VARCHAR(100) ,
  `created_by` VARCHAR(50) ,
  `created_date` DATETIME ,
  `temp_file_comments` VARCHAR(1000),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_type_defs` */

-- DROP TABLE IF EXISTS `vzdn_type_defs$aud`;

CREATE TABLE `vzdn_type_defs$aud` (
  `type_def_id` INT(11) ,
  `type_name` VARCHAR(100) ,
  `type_description` VARCHAR(500) ,
  `created_by` VARCHAR(50) ,
  `created_date` DATETIME ,
  `last_updated_by` VARCHAR(50) ,
  `last_updated_date` DATETIME,
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_types` */

-- DROP TABLE IF EXISTS `vzdn_types$aud`;

CREATE TABLE `vzdn_types$aud` (
  `type_id` INT(11) ,
  `type_def_id` INT(11) ,
  `type_value` VARCHAR(100) ,
  `description` VARCHAR(500) ,
  `sort_order` INT(11) ,
  `created_by` VARCHAR(50) ,
  `created_date` DATETIME ,
  `last_updated_by` VARCHAR(50) ,
  `last_updated_date` DATETIME,
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_user_roles` */

-- DROP TABLE IF EXISTS `vzdn_user_roles$aud`;

CREATE TABLE `vzdn_user_roles$aud` (
  `role_id` INT(11) ,
  `user_id` INT(11),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_users` */

-- DROP TABLE IF EXISTS `vzdn_users$aud`;

CREATE TABLE `vzdn_users$aud` (
  `user_id` INT(11) ,
  `user_name` VARCHAR(100) ,
  `password` VARCHAR(50) ,
  `title` VARCHAR(20) ,
  `last_name` VARCHAR(100) ,
  `first_name` VARCHAR(100) ,
  `phone_number` VARCHAR(50) ,
  `security_question` VARCHAR(255) ,
  `security_answer` VARCHAR(255) ,
  `created_by` VARCHAR(100) ,
  `created_date` DATETIME ,
  `last_updated_by` VARCHAR(100) ,
  `last_updated_date` DATETIME ,
  `fax_number` VARCHAR(50) ,
  `mobile_number` VARCHAR(50) ,
  `newsletter_opt_out` CHAR(1) ,
  `status_type_id` INT(11) ,
  `user_type_id` INT(11) ,
  `manager_organization` VARCHAR(100) ,
  `page_size` INT(10),
  `gtm_comp_id` VARCHAR(500),
  `country` VARCHAR(500),  
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Audit Table structure for table `vzdn_zon_temp_users` */

-- DROP TABLE IF EXISTS `vzdn_zon_temp_users$aud`;

CREATE TABLE `vzdn_zon_temp_users$aud` (
  `user_id` INT(11) ,
  `username` VARCHAR(50) ,
  `user_type` VARCHAR(20) ,
  `title` VARCHAR(20) ,
  `first_name` VARCHAR(20) ,
  `last_name` VARCHAR(20) ,
  `phone_number` VARCHAR(20) ,
  `fax_number` VARCHAR(20) ,
  `mobile_number` VARCHAR(20) ,
  `password` VARCHAR(20),
  `AUD_ACTION` VARCHAR(3) ,
  `AUD_TIMESTAMP` TIMESTAMP,
  `AUD_USER` VARCHAR(30)) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;