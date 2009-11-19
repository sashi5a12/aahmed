/*
SQLyog Community Edition- MySQL GUI v8.12 
MySQL - 5.0.45-log : Database - jforum
*********************************************************************
*/ 
/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`jforum` /*!40100*/ DEFAULT CHARACTER SET utf8 ;

USE `jforum`;

/*Table structure for table `jforum_api` */

-- DROP TABLE IF EXISTS `jforum_api`;

CREATE TABLE `jforum_api` (
  `api_id` INT(11) NOT NULL AUTO_INCREMENT,
  `api_key` VARCHAR(32) NOT NULL,
  `api_validity` DATETIME NOT NULL,
  PRIMARY KEY  (`api_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_attach` */

-- DROP TABLE IF EXISTS `jforum_attach`;

CREATE TABLE `jforum_attach` (
  `attach_id` INT(11) NOT NULL AUTO_INCREMENT,
  `post_id` INT(11) DEFAULT NULL,
  `privmsgs_id` INT(11) DEFAULT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY  (`attach_id`),
  KEY `idx_att_post` (`post_id`),
  KEY `idx_att_priv` (`privmsgs_id`),
  KEY `idx_att_user` (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_attach_desc` */

-- DROP TABLE IF EXISTS `jforum_attach_desc`;

CREATE TABLE `jforum_attach_desc` (
  `attach_desc_id` INT(11) NOT NULL AUTO_INCREMENT,
  `attach_id` INT(11) NOT NULL,
  `physical_filename` VARCHAR(255) NOT NULL,
  `real_filename` VARCHAR(255) NOT NULL,
  `download_count` INT(11) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `mimetype` VARCHAR(50) DEFAULT NULL,
  `filesize` INT(11) DEFAULT NULL,
  `upload_time` DATETIME DEFAULT NULL,
  `thumb` TINYINT(1) DEFAULT '0',
  `extension_id` INT(11) DEFAULT NULL,
  PRIMARY KEY  (`attach_desc_id`),
  KEY `idx_att_d_att` (`attach_id`),
  KEY `idx_att_d_ext` (`extension_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_attach_quota` */

-- DROP TABLE IF EXISTS `jforum_attach_quota`;

CREATE TABLE `jforum_attach_quota` (
  `attach_quota_id` INT(11) NOT NULL AUTO_INCREMENT,
  `group_id` INT(11) NOT NULL,
  `quota_limit_id` INT(11) NOT NULL,
  PRIMARY KEY  (`attach_quota_id`),
  KEY `group_id` (`group_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_banlist` */

-- DROP TABLE IF EXISTS `jforum_banlist`;

CREATE TABLE `jforum_banlist` (
  `banlist_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) DEFAULT NULL,
  `banlist_ip` VARCHAR(15) DEFAULT NULL,
  `banlist_email` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`banlist_id`),
  KEY `idx_user` (`user_id`),
  KEY `banlist_ip` (`banlist_ip`),
  KEY `banlist_email` (`banlist_email`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_banner` */

-- DROP TABLE IF EXISTS `jforum_banner`;

CREATE TABLE `jforum_banner` (
  `banner_id` INT(11) NOT NULL AUTO_INCREMENT,
  `banner_name` VARCHAR(90) DEFAULT NULL,
  `banner_placement` INT(11) NOT NULL DEFAULT '0',
  `banner_description` VARCHAR(250) DEFAULT NULL,
  `banner_clicks` INT(11) NOT NULL DEFAULT '0',
  `banner_views` INT(11) NOT NULL DEFAULT '0',
  `banner_url` VARCHAR(250) DEFAULT NULL,
  `banner_weight` TINYINT(1) NOT NULL DEFAULT '50',
  `banner_active` TINYINT(1) NOT NULL DEFAULT '0',
  `banner_comment` VARCHAR(250) DEFAULT NULL,
  `banner_type` INT(11) NOT NULL DEFAULT '0',
  `banner_width` INT(11) NOT NULL DEFAULT '0',
  `banner_height` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY  (`banner_id`),
  KEY `banner_id` (`banner_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_bookmarks` */

-- DROP TABLE IF EXISTS `jforum_bookmarks`;

CREATE TABLE `jforum_bookmarks` (
  `bookmark_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `relation_id` INT(11) NOT NULL,
  `relation_type` INT(11) NOT NULL,
  `public_visible` INT(11) DEFAULT '1',
  `title` VARCHAR(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`bookmark_id`),
  KEY `book_idx_relation` (`relation_id`),
  KEY `user_id` (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_categories` */

-- DROP TABLE IF EXISTS `jforum_categories`;

CREATE TABLE `jforum_categories` (
  `categories_id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL DEFAULT '',
  `display_order` INT(11) NOT NULL DEFAULT '0',
  `moderated` TINYINT(1) DEFAULT '0',
  PRIMARY KEY  (`categories_id`)
) ENGINE=INNODB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_config` */

-- DROP TABLE IF EXISTS `jforum_config`;

CREATE TABLE `jforum_config` (
  `config_name` VARCHAR(255) NOT NULL DEFAULT '',
  `config_value` VARCHAR(255) NOT NULL DEFAULT '',
  `config_id` INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY  (`config_id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_extension_groups` */

-- DROP TABLE IF EXISTS `jforum_extension_groups`;

CREATE TABLE `jforum_extension_groups` (
  `extension_group_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `allow` TINYINT(1) DEFAULT '1',
  `upload_icon` VARCHAR(100) DEFAULT NULL,
  `download_mode` TINYINT(1) DEFAULT '1',
  PRIMARY KEY  (`extension_group_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_extensions` */

-- DROP TABLE IF EXISTS `jforum_extensions`;

CREATE TABLE `jforum_extensions` (
  `extension_id` INT(11) NOT NULL AUTO_INCREMENT,
  `extension_group_id` INT(11) NOT NULL,
  `description` VARCHAR(100) DEFAULT NULL,
  `upload_icon` VARCHAR(100) DEFAULT NULL,
  `extension` VARCHAR(10) DEFAULT NULL,
  `allow` TINYINT(1) DEFAULT '1',
  PRIMARY KEY  (`extension_id`),
  KEY `extension_group_id` (`extension_group_id`),
  KEY `extension` (`extension`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_forums` */

-- DROP TABLE IF EXISTS `jforum_forums`;

CREATE TABLE `jforum_forums` (
  `forum_id` INT(11) NOT NULL AUTO_INCREMENT,
  `categories_id` INT(11) NOT NULL DEFAULT '1',
  `forum_name` VARCHAR(150) NOT NULL DEFAULT '',
  `forum_desc` VARCHAR(255) DEFAULT NULL,
  `forum_order` INT(11) DEFAULT '1',
  `forum_topics` INT(11) NOT NULL DEFAULT '0',
  `forum_last_post_id` INT(11) NOT NULL DEFAULT '0',
  `moderated` TINYINT(1) DEFAULT '0',
  PRIMARY KEY  (`forum_id`),
  KEY `categories_id` (`categories_id`),
  KEY `idx_forums_cats` (`categories_id`)
) ENGINE=INNODB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_forums_watch` */

-- DROP TABLE IF EXISTS `jforum_forums_watch`;

CREATE TABLE `jforum_forums_watch` (
  `forum_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  KEY `idx_fw_forum` (`forum_id`),
  KEY `idx_fw_user` (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `jforum_forums_watch` */

/*Table structure for table `jforum_groups` */

-- DROP TABLE IF EXISTS `jforum_groups`;

CREATE TABLE `jforum_groups` (
  `group_id` INT(11) NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(40) NOT NULL DEFAULT '',
  `group_description` VARCHAR(255) DEFAULT NULL,
  `parent_id` INT(11) DEFAULT '0',
  `vzdn_manager_role` INT(11) DEFAULT '0',
  PRIMARY KEY  (`group_id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_karma` */

-- DROP TABLE IF EXISTS `jforum_karma`;

CREATE TABLE `jforum_karma` (
  `karma_id` INT(11) NOT NULL AUTO_INCREMENT,
  `post_id` INT(11) NOT NULL,
  `topic_id` INT(11) NOT NULL,
  `post_user_id` INT(11) NOT NULL,
  `from_user_id` INT(11) NOT NULL,
  `points` INT(11) NOT NULL,
  `rate_date` DATETIME DEFAULT NULL,
  PRIMARY KEY  (`karma_id`),
  KEY `post_id` (`post_id`),
  KEY `topic_id` (`topic_id`),
  KEY `post_user_id` (`post_user_id`),
  KEY `from_user_id` (`from_user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `jforum_karma` */

/*Table structure for table `jforum_mail_integration` */

-- DROP TABLE IF EXISTS `jforum_mail_integration`;

CREATE TABLE `jforum_mail_integration` (
  `forum_id` INT(11) NOT NULL,
  `forum_email` VARCHAR(100) NOT NULL,
  `pop_username` VARCHAR(100) NOT NULL,
  `pop_password` VARCHAR(100) NOT NULL,
  `pop_host` VARCHAR(100) NOT NULL,
  `pop_port` INT(11) DEFAULT '110',
  `pop_ssl` TINYINT(4) DEFAULT '0',
  KEY `forum_id` (`forum_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `jforum_mail_integration` */

/*Table structure for table `jforum_moderation_log` */

-- DROP TABLE IF EXISTS `jforum_moderation_log`;

CREATE TABLE `jforum_moderation_log` (
  `log_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `log_description` TEXT NOT NULL,
  `log_original_message` TEXT,
  `log_date` DATETIME NOT NULL,
  `log_type` TINYINT(4) DEFAULT '0',
  `post_id` INT(11) DEFAULT '0',
  `topic_id` INT(11) DEFAULT '0',
  `post_user_id` INT(11) DEFAULT '0',
  PRIMARY KEY  (`log_id`),
  KEY `user_id` (`user_id`),
  KEY `post_user_id` (`post_user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `jforum_moderation_log` */

/*Table structure for table `jforum_posts` */

-- DROP TABLE IF EXISTS `jforum_posts`;

CREATE TABLE `jforum_posts` (
  `post_id` INT(11) NOT NULL AUTO_INCREMENT,
  `topic_id` INT(11) NOT NULL DEFAULT '0',
  `forum_id` INT(11) NOT NULL DEFAULT '0',
  `user_id` INT(11) NOT NULL DEFAULT '0',
  `post_time` DATETIME DEFAULT NULL,
  `poster_ip` VARCHAR(15) DEFAULT NULL,
  `enable_bbcode` TINYINT(1) NOT NULL DEFAULT '1',
  `enable_html` TINYINT(1) NOT NULL DEFAULT '1',
  `enable_smilies` TINYINT(1) NOT NULL DEFAULT '1',
  `enable_sig` TINYINT(1) NOT NULL DEFAULT '1',
  `post_edit_time` DATETIME DEFAULT NULL,
  `post_edit_count` INT(11) NOT NULL DEFAULT '0',
  `status` TINYINT(1) DEFAULT '1',
  `attach` TINYINT(1) DEFAULT '0',
  `need_moderate` TINYINT(1) DEFAULT '0',
  PRIMARY KEY  (`post_id`),
  KEY `user_id` (`user_id`),
  KEY `topic_id` (`topic_id`),
  KEY `forum_id` (`forum_id`),
  KEY `post_time` (`post_time`),
  KEY `need_moderate` (`need_moderate`)
) ENGINE=INNODB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_posts_text` */

-- DROP TABLE IF EXISTS `jforum_posts_text`;

CREATE TABLE `jforum_posts_text` (
  `post_id` INT(11) NOT NULL,
  `post_text` TEXT,
  `post_subject` VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY  (`post_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_privmsgs` */

-- DROP TABLE IF EXISTS `jforum_privmsgs`;

CREATE TABLE `jforum_privmsgs` (
  `privmsgs_id` INT(11) NOT NULL AUTO_INCREMENT,
  `privmsgs_type` TINYINT(4) NOT NULL DEFAULT '0',
  `privmsgs_subject` VARCHAR(255) NOT NULL DEFAULT '',
  `privmsgs_from_userid` INT(11) NOT NULL DEFAULT '0',
  `privmsgs_to_userid` INT(11) NOT NULL DEFAULT '0',
  `privmsgs_date` DATETIME DEFAULT NULL,
  `privmsgs_ip` VARCHAR(15) NOT NULL DEFAULT '',
  `privmsgs_enable_bbcode` TINYINT(1) NOT NULL DEFAULT '1',
  `privmsgs_enable_html` TINYINT(1) NOT NULL DEFAULT '0',
  `privmsgs_enable_smilies` TINYINT(1) NOT NULL DEFAULT '1',
  `privmsgs_attach_sig` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY  (`privmsgs_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `jforum_privmsgs` */

/*Table structure for table `jforum_privmsgs_text` */

-- DROP TABLE IF EXISTS `jforum_privmsgs_text`;

CREATE TABLE `jforum_privmsgs_text` (
  `privmsgs_id` INT(11) NOT NULL,
  `privmsgs_text` TEXT,
  PRIMARY KEY  (`privmsgs_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `jforum_privmsgs_text` */

/*Table structure for table `jforum_quota_limit` */

-- DROP TABLE IF EXISTS `jforum_quota_limit`;

CREATE TABLE `jforum_quota_limit` (
  `quota_limit_id` INT(11) NOT NULL AUTO_INCREMENT,
  `quota_desc` VARCHAR(50) NOT NULL,
  `quota_limit` INT(11) NOT NULL,
  `quota_type` TINYINT(1) DEFAULT '1',
  PRIMARY KEY  (`quota_limit_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `jforum_quota_limit` */

/*Table structure for table `jforum_ranks` */

-- DROP TABLE IF EXISTS `jforum_ranks`;

CREATE TABLE `jforum_ranks` (
  `rank_id` INT(11) NOT NULL AUTO_INCREMENT,
  `rank_title` VARCHAR(50) NOT NULL DEFAULT '',
  `rank_min` INT(11) NOT NULL DEFAULT '0',
  `rank_special` TINYINT(1) DEFAULT NULL,
  `rank_image` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`rank_id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_role_values` */

-- DROP TABLE IF EXISTS `jforum_role_values`;

CREATE TABLE `jforum_role_values` (
  `role_id` INT(11) NOT NULL,
  `role_value` VARCHAR(255) DEFAULT NULL,
  KEY `idx_role` (`role_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_roles` */

-- DROP TABLE IF EXISTS `jforum_roles`;

CREATE TABLE `jforum_roles` (
  `role_id` INT(11) NOT NULL AUTO_INCREMENT,
  `group_id` INT(11) DEFAULT '0',
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY  (`role_id`),
  KEY `idx_group` (`group_id`),
  KEY `idx_name` (`name`)
) ENGINE=INNODB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_sessions` */

-- DROP TABLE IF EXISTS `jforum_sessions`;

CREATE TABLE `jforum_sessions` (
  `session_id` VARCHAR(150) NOT NULL DEFAULT '',
  `session_user_id` INT(11) NOT NULL DEFAULT '0',
  `session_start` DATETIME DEFAULT NULL,
  `session_time` BIGINT(20) DEFAULT '0',
  `session_ip` VARCHAR(15) NOT NULL DEFAULT '',
  `session_page` INT(11) NOT NULL DEFAULT '0',
  `session_logged_int` TINYINT(1) DEFAULT NULL,
  KEY `idx_sessions_users` (`session_user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_smilies` */

-- DROP TABLE IF EXISTS `jforum_smilies`;

CREATE TABLE `jforum_smilies` (
  `smilie_id` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(50) NOT NULL DEFAULT '',
  `url` VARCHAR(100) DEFAULT NULL,
  `disk_name` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`smilie_id`)
) ENGINE=INNODB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_themes` */

-- DROP TABLE IF EXISTS `jforum_themes`;

CREATE TABLE `jforum_themes` (
  `themes_id` INT(11) NOT NULL AUTO_INCREMENT,
  `template_name` VARCHAR(30) NOT NULL DEFAULT '',
  `style_name` VARCHAR(30) NOT NULL DEFAULT '',
  PRIMARY KEY  (`themes_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_topics` */

-- DROP TABLE IF EXISTS `jforum_topics`;

CREATE TABLE `jforum_topics` (
  `topic_id` INT(11) NOT NULL AUTO_INCREMENT,
  `forum_id` INT(11) NOT NULL DEFAULT '0',
  `topic_title` VARCHAR(100) NOT NULL DEFAULT '',
  `user_id` INT(11) NOT NULL DEFAULT '0',
  `topic_time` DATETIME DEFAULT NULL,
  `topic_views` INT(11) DEFAULT '1',
  `topic_replies` INT(11) DEFAULT '0',
  `topic_status` TINYINT(3) DEFAULT '0',
  `topic_vote_id` INT(11) NOT NULL DEFAULT '0',
  `topic_type` TINYINT(3) DEFAULT '0',
  `topic_first_post_id` INT(11) DEFAULT '0',
  `topic_last_post_id` INT(11) NOT NULL DEFAULT '0',
  `topic_moved_id` INT(11) DEFAULT '0',
  `moderated` TINYINT(1) DEFAULT '0',
  PRIMARY KEY  (`topic_id`),
  KEY `forum_id` (`forum_id`),
  KEY `user_id` (`user_id`),
  KEY `topic_first_post_id` (`topic_first_post_id`),
  KEY `topic_last_post_id` (`topic_last_post_id`),
  KEY `topic_moved_id` (`topic_moved_id`)
) ENGINE=INNODB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_topics_watch` */

-- DROP TABLE IF EXISTS `jforum_topics_watch`;

CREATE TABLE `jforum_topics_watch` (
  `topic_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `is_read` TINYINT(1) DEFAULT '1',
  KEY `idx_topic` (`topic_id`),
  KEY `idx_user` (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_user_groups` */

-- DROP TABLE IF EXISTS `jforum_user_groups`;

CREATE TABLE `jforum_user_groups` (
  `group_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  KEY `idx_group` (`group_id`),
  KEY `idx_user` (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_users` */

-- DROP TABLE IF EXISTS `jforum_users`;

CREATE TABLE `jforum_users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_active` TINYINT(1) DEFAULT NULL,
  `username` VARCHAR(50) NOT NULL DEFAULT '',
  `user_password` VARCHAR(32) NOT NULL DEFAULT '',
  `user_session_time` BIGINT(20) DEFAULT '0',
  `user_session_page` INT(11) NOT NULL DEFAULT '0',
  `user_lastvisit` DATETIME DEFAULT NULL,
  `user_regdate` DATETIME DEFAULT NULL,
  `user_level` TINYINT(4) DEFAULT NULL,
  `user_posts` INT(11) NOT NULL DEFAULT '0',
  `user_timezone` VARCHAR(5) NOT NULL DEFAULT '',
  `user_style` TINYINT(4) DEFAULT NULL,
  `user_lang` VARCHAR(255) NOT NULL DEFAULT '',
  `user_dateformat` VARCHAR(20) NOT NULL DEFAULT '%d/%M/%Y %H:%i',
  `user_new_privmsg` INT(11) NOT NULL DEFAULT '0',
  `user_unread_privmsg` INT(11) NOT NULL DEFAULT '0',
  `user_last_privmsg` DATETIME DEFAULT NULL,
  `user_emailtime` DATETIME DEFAULT NULL,
  `user_viewemail` TINYINT(1) DEFAULT '0',
  `user_attachsig` TINYINT(1) DEFAULT '1',
  `user_allowhtml` TINYINT(1) DEFAULT '0',
  `user_allowbbcode` TINYINT(1) DEFAULT '1',
  `user_allowsmilies` TINYINT(1) DEFAULT '1',
  `user_allowavatar` TINYINT(1) DEFAULT '1',
  `user_allow_pm` TINYINT(1) DEFAULT '1',
  `user_allow_viewonline` TINYINT(1) DEFAULT '1',
  `user_notify` TINYINT(1) DEFAULT '1',
  `user_notify_always` TINYINT(1) DEFAULT '0',
  `user_notify_text` TINYINT(1) DEFAULT '0',
  `user_notify_pm` TINYINT(1) DEFAULT '1',
  `user_popup_pm` TINYINT(1) DEFAULT '1',
  `rank_id` INT(11) DEFAULT '0',
  `user_avatar` VARCHAR(100) DEFAULT NULL,
  `user_avatar_type` TINYINT(4) NOT NULL DEFAULT '0',
  `user_email` VARCHAR(255) NOT NULL DEFAULT '',
  `user_icq` VARCHAR(15) DEFAULT NULL,
  `user_website` VARCHAR(255) DEFAULT NULL,
  `user_from` VARCHAR(100) DEFAULT NULL,
  `user_sig` TEXT,
  `user_sig_bbcode_uid` VARCHAR(10) DEFAULT NULL,
  `user_aim` VARCHAR(255) DEFAULT NULL,
  `user_yim` VARCHAR(255) DEFAULT NULL,
  `user_msnm` VARCHAR(255) DEFAULT NULL,
  `user_occ` VARCHAR(100) DEFAULT NULL,
  `user_interests` VARCHAR(255) DEFAULT NULL,
  `user_biography` TEXT,
  `user_actkey` VARCHAR(32) DEFAULT NULL,
  `gender` CHAR(1) DEFAULT NULL,
  `themes_id` INT(11) DEFAULT NULL,
  `deleted` TINYINT(1) DEFAULT NULL,
  `user_viewonline` TINYINT(1) DEFAULT '1',
  `security_hash` VARCHAR(32) DEFAULT NULL,
  `user_karma` DOUBLE DEFAULT NULL,
  `user_authhash` VARCHAR(32) DEFAULT NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_vote_desc` */

-- DROP TABLE IF EXISTS `jforum_vote_desc`;

CREATE TABLE `jforum_vote_desc` (
  `vote_id` INT(11) NOT NULL AUTO_INCREMENT,
  `topic_id` INT(11) NOT NULL DEFAULT '0',
  `vote_text` VARCHAR(255) NOT NULL DEFAULT '',
  `vote_start` DATETIME NOT NULL,
  `vote_length` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY  (`vote_id`),
  KEY `topic_id` (`topic_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_vote_results` */

-- DROP TABLE IF EXISTS `jforum_vote_results`;

CREATE TABLE `jforum_vote_results` (
  `vote_id` INT(11) NOT NULL DEFAULT '0',
  `vote_option_id` TINYINT(4) NOT NULL DEFAULT '0',
  `vote_option_text` VARCHAR(255) NOT NULL DEFAULT '',
  `vote_result` INT(11) NOT NULL DEFAULT '0',
  KEY `vote_id` (`vote_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_vote_voters` */

-- DROP TABLE IF EXISTS `jforum_vote_voters`;

CREATE TABLE `jforum_vote_voters` (
  `vote_id` INT(11) NOT NULL DEFAULT '0',
  `vote_user_id` INT(11) NOT NULL DEFAULT '0',
  `vote_user_ip` VARCHAR(15) NOT NULL DEFAULT '',
  KEY `vote_id` (`vote_id`),
  KEY `vote_user_id` (`vote_user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `jforum_words` */

-- DROP TABLE IF EXISTS `jforum_words`;

CREATE TABLE `jforum_words` (
  `word_id` INT(11) NOT NULL AUTO_INCREMENT,
  `word` VARCHAR(100) NOT NULL DEFAULT '',
  `replacement` VARCHAR(100) NOT NULL DEFAULT '',
  PRIMARY KEY  (`word_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


# Set default user/group ids
SET @GENERAL_GROUP_ID = 1;
SET @ADMIN_GROUP_ID = 2;
SET @ANONYMOUS_ID = 1;
SET @ADMIN_ID = 2;
SET @VERIZON_ADMIN_GROUP_ID = 3;
SET @GENERAL_GROUP_VZDN_ROLE_ID = 5001;
SET @ADMIN_GROUP_VZDN_ROLE_ID = 1001;
SET @VERIZON_ADMIN_GROUP_VZDN_ROLE_ID = 5002;

#
# Groups
#
INSERT INTO jforum_groups ( group_id, group_name, group_description, vzdn_manager_role ) VALUES (@GENERAL_GROUP_ID, 'Forum User', 'For Forum Users only.', @GENERAL_GROUP_VZDN_ROLE_ID);
INSERT INTO jforum_groups ( group_id, group_name, group_description, vzdn_manager_role ) VALUES (@ADMIN_GROUP_ID, 'Forum Admin', 'for admin users only', @ADMIN_GROUP_VZDN_ROLE_ID);
INSERT INTO jforum_groups ( group_id, group_name, group_description, vzdn_manager_role ) VALUES (@VERIZON_ADMIN_GROUP_ID, 'Verizon Admin', 'For Verizon Admins', @VERIZON_ADMIN_GROUP_VZDN_ROLE_ID);

# 
# Users
#
INSERT INTO jforum_users ( user_id, username, user_password, user_regdate ) VALUES (@ANONYMOUS_ID, 'Anonymous', 'nopass', NOW());
INSERT INTO jforum_users ( user_id, username, user_password, user_regdate, user_posts ) VALUES (@ADMIN_ID, 'Admin', '21232f297a57a5a743894a0e4a801fc3', NOW(), 1);

#
# User Groups
#
INSERT INTO jforum_user_groups (group_id, user_id) VALUES (@GENERAL_GROUP_ID, @ANONYMOUS_ID);
INSERT INTO jforum_user_groups (group_id, user_id) VALUES (@ADMIN_GROUP_ID, @ADMIN_ID);
	
#
# Smilies
#
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':)', '<img src=\"#CONTEXT#/images/smilies/3b63d1616c5dfcf29f8a7a031aaa7cad.gif\" />', '3b63d1616c5dfcf29f8a7a031aaa7cad.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':-)', '<img src=\"#CONTEXT#/images/smilies/3b63d1616c5dfcf29f8a7a031aaa7cad.gif\"/>', '3b63d1616c5dfcf29f8a7a031aaa7cad.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':D', '<img src=\"#CONTEXT#/images/smilies/283a16da79f3aa23fe1025c96295f04f.gif\" />', '283a16da79f3aa23fe1025c96295f04f.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':-D', '<img src=\"#CONTEXT#/images/smilies/283a16da79f3aa23fe1025c96295f04f.gif\" />', '283a16da79f3aa23fe1025c96295f04f.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':(', '<img src=\"#CONTEXT#/images/smilies/9d71f0541cff0a302a0309c5079e8dee.gif\" />', '9d71f0541cff0a302a0309c5079e8dee.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':mrgreen:', '<img src=\"#CONTEXT#/images/smilies/ed515dbff23a0ee3241dcc0a601c9ed6.gif\" />', 'ed515dbff23a0ee3241dcc0a601c9ed6.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':-o', '<img src=\"#CONTEXT#/images/smilies/47941865eb7bbc2a777305b46cc059a2.gif\"  />', '47941865eb7bbc2a777305b46cc059a2.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':shock:', '<img src=\"#CONTEXT#/images/smilies/385970365b8ed7503b4294502a458efa.gif\" />', '385970365b8ed7503b4294502a458efa.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':?:', '<img src=\"#CONTEXT#/images/smilies/0a4d7238daa496a758252d0a2b1a1384.gif\" />', '0a4d7238daa496a758252d0a2b1a1384.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES ('8)', '<img src=\"#CONTEXT#/images/smilies/b2eb59423fbf5fa39342041237025880.gif\"  />', 'b2eb59423fbf5fa39342041237025880.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':lol:', '<img src=\"#CONTEXT#/images/smilies/97ada74b88049a6d50a6ed40898a03d7.gif\" />', '97ada74b88049a6d50a6ed40898a03d7.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':x', '<img src=\"#CONTEXT#/images/smilies/1069449046bcd664c21db15b1dfedaee.gif\"  />', '1069449046bcd664c21db15b1dfedaee.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':P', '<img src=\"#CONTEXT#/images/smilies/69934afc394145350659cd7add244ca9.gif\" />', '69934afc394145350659cd7add244ca9.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':-P', '<img src=\"#CONTEXT#/images/smilies/69934afc394145350659cd7add244ca9.gif\" />', '69934afc394145350659cd7add244ca9.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':oops:', '<img src=\"#CONTEXT#/images/smilies/499fd50bc713bfcdf2ab5a23c00c2d62.gif\" />', '499fd50bc713bfcdf2ab5a23c00c2d62.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':cry:', '<img src=\"#CONTEXT#/images/smilies/c30b4198e0907b23b8246bdd52aa1c3c.gif\" />', 'c30b4198e0907b23b8246bdd52aa1c3c.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':evil:', '<img src=\"#CONTEXT#/images/smilies/2e207fad049d4d292f60607f80f05768.gif\" />', '2e207fad049d4d292f60607f80f05768.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':twisted:', '<img src=\"#CONTEXT#/images/smilies/908627bbe5e9f6a080977db8c365caff.gif\" />', '908627bbe5e9f6a080977db8c365caff.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':roll:', '<img src=\"#CONTEXT#/images/smilies/2786c5c8e1a8be796fb2f726cca5a0fe.gif\" />', '2786c5c8e1a8be796fb2f726cca5a0fe.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':wink:', '<img src=\"#CONTEXT#/images/smilies/8a80c6485cd926be453217d59a84a888.gif\" />', '8a80c6485cd926be453217d59a84a888.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (';)', '<img src=\"#CONTEXT#/images/smilies/8a80c6485cd926be453217d59a84a888.gif\" />', '8a80c6485cd926be453217d59a84a888.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (';-)', '<img src=\"#CONTEXT#/images/smilies/8a80c6485cd926be453217d59a84a888.gif\" />', '8a80c6485cd926be453217d59a84a888.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':!:', '<img src=\"#CONTEXT#/images/smilies/9293feeb0183c67ea1ea8c52f0dbaf8c.gif\" />', '9293feeb0183c67ea1ea8c52f0dbaf8c.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':?', '<img src=\"#CONTEXT#/images/smilies/136dd33cba83140c7ce38db096d05aed.gif\" />', '136dd33cba83140c7ce38db096d05aed.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':idea:', '<img src=\"#CONTEXT#/images/smilies/8f7fb9dd46fb8ef86f81154a4feaada9.gif\" />', '8f7fb9dd46fb8ef86f81154a4feaada9.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':arrow:', '<img src=\"#CONTEXT#/images/smilies/d6741711aa045b812616853b5507fd2a.gif\" />', 'd6741711aa045b812616853b5507fd2a.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':hunf:', '<img src=\"#CONTEXT#/images/smilies/0320a00cb4bb5629ab9fc2bc1fcc4e9e.gif\" />', '0320a00cb4bb5629ab9fc2bc1fcc4e9e.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':-(', '<img src=\"#CONTEXT#/images/smilies/9d71f0541cff0a302a0309c5079e8dee.gif\"  />', '9d71f0541cff0a302a0309c5079e8dee.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':XD:', '<img src=\"#CONTEXT#/images/smilies/49869fe8223507d7223db3451e5321aa.gif\" />', '49869fe8223507d7223db3451e5321aa.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':thumbup:', '<img src=\"#CONTEXT#/images/smilies/e8a506dc4ad763aca51bec4ca7dc8560.gif\" />', 'e8a506dc4ad763aca51bec4ca7dc8560.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':thumbdown:', '<img src=\"#CONTEXT#/images/smilies/e78feac27fa924c4d0ad6cf5819f3554.gif\" />', 'e78feac27fa924c4d0ad6cf5819f3554.gif');
INSERT INTO jforum_smilies (code, url, disk_name) VALUES (':|', '<img src=\"#CONTEXT#/images/smilies/1cfd6e2a9a2c0cf8e74b49b35e2e46c7.gif\" />', '1cfd6e2a9a2c0cf8e74b49b35e2e46c7.gif');

#
# Demonstration Forum
#
INSERT INTO jforum_categories VALUES (1,'Category Test',1,0);
INSERT INTO jforum_forums VALUES (1,1,'Test Forum','This is a test forum',1,1,1,0);
INSERT INTO jforum_topics VALUES (1,1,'Welcome to JForum',2,'2005-01-04 16:59:54',1,0,0,0,0,1,1,0, 0);
INSERT INTO jforum_posts VALUES (1,1,1,2,'2005-01-04 16:59:54','127.0.0.1',1,0,1,1,null,0,1,0,0);
INSERT INTO jforum_posts_text VALUES (1,'[b][color=blue][size=18]Congratulations :!: [/size][/color][/b]\nYou have completed the installation, and JForum is up and running. \n\nTo start administering the board, login as [i]Admin / <the password you supplied in the installer>[/i] and access the [b][url=/admBase/login.page]Admin Control Panel[/url][/b] using the link that shows up in the bottom of the page. There you will be able to create Categories, Forums and much more  :D  \n\nFor more information and support, please refer to the following pages:\n\n:arrow: Community forum: http://www.jforum.net/community.jsp\n:arrow: Documentation: http://www.jforum.net/doc\n\nThank you for choosing JForum.\n\n[url=http://www.jforum.net/doc/Team]The JForum Team[/url]\n\n','Welcome to JForum');

#
# Roles
#
INSERT INTO jforum_roles (group_id, name) VALUES (@GENERAL_GROUP_ID, 'perm_vote');
INSERT INTO jforum_roles (group_id, name) VALUES (@GENERAL_GROUP_ID, 'perm_karma_enabled');
INSERT INTO jforum_roles (group_id, name) VALUES (@GENERAL_GROUP_ID, 'perm_create_poll');
INSERT INTO jforum_roles (group_id, name) VALUES (@GENERAL_GROUP_ID, 'perm_bookmarks_enabled');
INSERT INTO jforum_roles (group_id, name) VALUES (@GENERAL_GROUP_ID, 'perm_attachments_download');
INSERT INTO jforum_roles (group_id, name) VALUES (@GENERAL_GROUP_ID, 'perm_create_sticky_announcement_topics');
INSERT INTO jforum_roles (group_id, name) VALUES (@GENERAL_GROUP_ID, 'perm_moderation_log');

# Admin
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_administration');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_moderation');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_moderation_post_remove');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_moderation_post_edit');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_moderation_topic_move');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_moderation_topic_lockUnlock');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_moderation_approve_messages');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_create_sticky_announcement_topics');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_vote');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_create_poll');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_karma_enabled');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_bookmarks_enabled');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_attachments_download');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_moderation_log');
INSERT INTO jforum_roles (group_id, name) VALUES (@ADMIN_GROUP_ID, 'perm_full_moderation_log');

# Verizon Admin
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_administration');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_moderation');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_moderation_post_remove');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_moderation_post_edit');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_moderation_topic_move');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_moderation_topic_lockUnlock');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_moderation_approve_messages');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_create_sticky_announcement_topics');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_vote');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_create_poll');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_karma_enabled');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_bookmarks_enabled');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_attachments_download');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_moderation_log');
INSERT INTO jforum_roles (group_id, name) VALUES (@VERIZON_ADMIN_GROUP_ID, 'perm_full_moderation_log');

#
# View Forum
#
INSERT INTO jforum_roles (name, group_id) VALUES ('perm_forum', @GENERAL_GROUP_ID);
INSERT INTO jforum_role_values (role_id, role_value) VALUES (LAST_INSERT_ID(), '1');
	
INSERT INTO jforum_roles (name, group_id) VALUES ('perm_forum', @ADMIN_GROUP_ID);
INSERT INTO jforum_role_values (role_id, role_value) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id) VALUES ('perm_forum', @VERIZON_ADMIN_GROUP_ID);
INSERT INTO jforum_role_values (role_id, role_value) VALUES (LAST_INSERT_ID(), '1');

#
# View Category
#
INSERT INTO jforum_roles (name, group_id) VALUES ('perm_category', @GENERAL_GROUP_ID);
INSERT INTO jforum_role_values (role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id) VALUES ('perm_category', @ADMIN_GROUP_ID);
INSERT INTO jforum_role_values (role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id) VALUES ('perm_category', @VERIZON_ADMIN_GROUP_ID);
INSERT INTO jforum_role_values (role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

#
# Create / Reply to topics
#
INSERT INTO jforum_roles (name, group_id) VALUES ('perm_read_only_forums', @GENERAL_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id) VALUES ('perm_read_only_forums', @ADMIN_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id) VALUES ('perm_read_only_forums', @VERIZON_ADMIN_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

# 
# Enable HTML
#
INSERT INTO jforum_roles (name, group_id ) VALUES ('perm_html_disabled', @GENERAL_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id ) VALUES ('perm_html_disabled', @ADMIN_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id ) VALUES ('perm_html_disabled', @VERIZON_ADMIN_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

#
# Reply only
#
INSERT INTO jforum_roles (name, group_id) VALUES ('perm_reply_only',  @GENERAL_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id) VALUES ('perm_reply_only', @ADMIN_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id) VALUES ('perm_reply_only', @VERIZON_ADMIN_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

#
# Reply without moderation
#
INSERT INTO jforum_roles (name, group_id) VALUES ('perm_reply_without_moderation',  @GENERAL_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id) VALUES ('perm_reply_without_moderation', @ADMIN_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id) VALUES ('perm_reply_without_moderation', @VERIZON_ADMIN_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

#
# Moderation of forums
#
INSERT INTO jforum_roles (name, group_id) VALUES ('perm_moderation_forums', @ADMIN_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

INSERT INTO jforum_roles (name, group_id) VALUES ('perm_moderation_forums', @VERIZON_ADMIN_GROUP_ID);
INSERT INTO jforum_role_values ( role_id, role_value ) VALUES (LAST_INSERT_ID(), '1');

DELETE FROM jforum_roles WHERE name like "perm_create_poll";

UPDATE jforum_groups SET vzdn_manager_role = 5003 WHERE vzdn_manager_role = 1001;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;