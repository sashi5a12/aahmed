/*
SQLyog Community Edition- MySQL GUI v8.12 
MySQL - 5.0.45-log : Database - vzdnBlog
*********************************************************************
*/ 
/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`vzdnBlog` /*!40100 */DEFAULT CHARACTER SET utf8;

USE `vzdnBlog`;

/*Table structure for table `autoping` */

-- DROP TABLE IF EXISTS `autoping`;

CREATE TABLE `autoping` (
  `id` VARCHAR(48) NOT NULL,
  `websiteid` VARCHAR(48) NOT NULL,
  `pingtargetid` VARCHAR(48) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `ap_websiteid_idx` (`websiteid`),
  KEY `ap_pingtid_idx` (`pingtargetid`),
  CONSTRAINT `ap_pingtargetid_fk` FOREIGN KEY (`pingtargetid`) REFERENCES `pingtarget` (`id`),
  CONSTRAINT `ap_websiteid_fk` FOREIGN KEY (`websiteid`) REFERENCES `website` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `autoping` */

/*Table structure for table `bookmark` */

-- DROP TABLE IF EXISTS `bookmark`;

CREATE TABLE `bookmark` (
  `id` VARCHAR(48) NOT NULL,
  `folderid` VARCHAR(48) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `url` VARCHAR(255) NOT NULL,
  `weight` INT(11) NOT NULL DEFAULT '0',
  `priority` INT(11) NOT NULL DEFAULT '100',
  `image` VARCHAR(255) DEFAULT NULL,
  `feedurl` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`id`),
  KEY `bm_folderid_idx` (`folderid`),
  CONSTRAINT `bm_folderid_fk` FOREIGN KEY (`folderid`) REFERENCES `folder` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `entryattribute` */

-- DROP TABLE IF EXISTS `entryattribute`;

CREATE TABLE `entryattribute` (
  `id` VARCHAR(48) NOT NULL,
  `entryid` VARCHAR(48) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `value` TEXT NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ea_name_uq` (`entryid`,`name`),
  KEY `ea_entryid_idx` (`entryid`),
  CONSTRAINT `att_entryid_fk` FOREIGN KEY (`entryid`) REFERENCES `weblogentry` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `folder` */

-- DROP TABLE IF EXISTS `folder`;

CREATE TABLE `folder` (
  `id` VARCHAR(48) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `websiteid` VARCHAR(48) NOT NULL,
  `parentid` VARCHAR(48) DEFAULT NULL,
  `path` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`id`),
  KEY `fo_websiteid_idx` (`websiteid`),
  KEY `fo_parentid_idx` (`parentid`),
  KEY `fo_path_idx` (`path`),
  CONSTRAINT `fo_websiteid_fk` FOREIGN KEY (`websiteid`) REFERENCES `website` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `folderassoc` */

-- DROP TABLE IF EXISTS `folderassoc`;

CREATE TABLE `folderassoc` (
  `id` VARCHAR(48) NOT NULL,
  `folderid` VARCHAR(48) NOT NULL,
  `ancestorid` VARCHAR(40) DEFAULT NULL,
  `relation` VARCHAR(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fa_folderid_idx` (`folderid`),
  KEY `fa_ancestorid_idx` (`ancestorid`),
  KEY `fa_relation_idx` (`relation`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `folderassoc` */

/*Table structure for table `newsfeed` */

-- DROP TABLE IF EXISTS `newsfeed`;

CREATE TABLE `newsfeed` (
  `id` VARCHAR(48) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `link` VARCHAR(255) NOT NULL,
  `websiteid` VARCHAR(48) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `nf_websiteid_idx` (`websiteid`),
  CONSTRAINT `nf_websiteid_fk` FOREIGN KEY (`websiteid`) REFERENCES `website` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `newsfeed` */

/*Table structure for table `pingcategory` */

-- DROP TABLE IF EXISTS `pingcategory`;

CREATE TABLE `pingcategory` (
  `id` VARCHAR(48) NOT NULL,
  `autopingid` VARCHAR(48) NOT NULL,
  `categoryid` VARCHAR(48) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `pc_autopingid_idx` (`autopingid`),
  KEY `pc_categoryid_idx` (`categoryid`),
  CONSTRAINT `pc_categoryid_fk` FOREIGN KEY (`categoryid`) REFERENCES `weblogcategory` (`id`),
  CONSTRAINT `pc_autopingid_fk` FOREIGN KEY (`autopingid`) REFERENCES `autoping` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `pingcategory` */

/*Table structure for table `pingqueueentry` */

-- DROP TABLE IF EXISTS `pingqueueentry`;

CREATE TABLE `pingqueueentry` (
  `id` VARCHAR(48) NOT NULL,
  `entrytime` DATETIME NOT NULL,
  `pingtargetid` VARCHAR(48) NOT NULL,
  `websiteid` VARCHAR(48) NOT NULL,
  `attempts` INT(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `pqe_entrytime_idx` (`entrytime`),
  KEY `pqe_pingtid_idx` (`pingtargetid`),
  KEY `pqe_websiteid_idx` (`websiteid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `pingqueueentry` */

/*Table structure for table `pingtarget` */

-- DROP TABLE IF EXISTS `pingtarget`;

CREATE TABLE `pingtarget` (
  `id` VARCHAR(48) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `pingurl` VARCHAR(255) NOT NULL,
  `websiteid` VARCHAR(48) DEFAULT NULL,
  `conditioncode` INT(11) NOT NULL DEFAULT '0',
  `lastsuccess` DATETIME DEFAULT NULL,
  `autoenabled` BIT(1) NOT NULL DEFAULT '\0',
  PRIMARY KEY  (`id`),
  KEY `pt_websiteid_idx` (`websiteid`),
  CONSTRAINT `pt_websiteid_fk` FOREIGN KEY (`websiteid`) REFERENCES `website` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `rag_entry` */

-- DROP TABLE IF EXISTS `rag_entry`;

CREATE TABLE `rag_entry` (
  `id` VARCHAR(48) NOT NULL,
  `subscription_id` VARCHAR(48) NOT NULL,
  `handle` VARCHAR(255) DEFAULT NULL,
  `title` VARCHAR(255) DEFAULT NULL,
  `guid` VARCHAR(255) DEFAULT NULL,
  `permalink` TEXT NOT NULL,
  `author` VARCHAR(255) DEFAULT NULL,
  `content` TEXT,
  `categories` TEXT,
  `published` DATETIME NOT NULL,
  `updated` DATETIME DEFAULT NULL,
  PRIMARY KEY  (`id`),
  KEY `rage_sid_idx` (`subscription_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `rag_entry` */

/*Table structure for table `rag_group` */

-- DROP TABLE IF EXISTS `rag_group`;

CREATE TABLE `rag_group` (
  `id` VARCHAR(48) NOT NULL,
  `planet_id` VARCHAR(48) NOT NULL,
  `handle` VARCHAR(32) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `max_page_entries` INT(11) DEFAULT '30',
  `max_feed_entries` INT(11) DEFAULT '30',
  `cat_restriction` TEXT,
  `group_page` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ragg_handle_uq` (`planet_id`,`handle`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `rag_group_subscription` */

-- DROP TABLE IF EXISTS `rag_group_subscription`;

CREATE TABLE `rag_group_subscription` (
  `group_id` VARCHAR(48) NOT NULL,
  `subscription_id` VARCHAR(48) NOT NULL,
  KEY `raggs_gid_idx` (`group_id`),
  KEY `raggs_sid_idx` (`subscription_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `rag_group_subscription` */

/*Table structure for table `rag_planet` */

-- DROP TABLE IF EXISTS `rag_planet`;

CREATE TABLE `rag_planet` (
  `id` VARCHAR(48) NOT NULL,
  `handle` VARCHAR(32) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ragp_handle_uq` (`handle`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `rag_properties` */

-- DROP TABLE IF EXISTS `rag_properties`;

CREATE TABLE `rag_properties` (
  `name` VARCHAR(255) NOT NULL,
  `value` TEXT,
  PRIMARY KEY  (`name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `rag_properties` */

/*Table structure for table `rag_subscription` */

-- DROP TABLE IF EXISTS `rag_subscription`;

CREATE TABLE `rag_subscription` (
  `id` VARCHAR(48) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `feed_url` VARCHAR(255) NOT NULL,
  `site_url` VARCHAR(255) DEFAULT NULL,
  `author` VARCHAR(255) DEFAULT NULL,
  `last_updated` DATETIME DEFAULT NULL,
  `inbound_links` INT(11) DEFAULT '-1',
  `inbound_blogs` INT(11) DEFAULT '-1',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `rags_feed_url_uq` (`feed_url`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `rag_subscription` */

/*Table structure for table `referer` */

-- DROP TABLE IF EXISTS `referer`;

CREATE TABLE `referer` (
  `id` VARCHAR(48) NOT NULL,
  `websiteid` VARCHAR(48) NOT NULL,
  `entryid` VARCHAR(48) DEFAULT NULL,
  `datestr` VARCHAR(10) DEFAULT NULL,
  `refurl` VARCHAR(255) NOT NULL,
  `refpermalink` VARCHAR(255) DEFAULT NULL,
  `reftime` DATETIME DEFAULT NULL,
  `requrl` VARCHAR(255) DEFAULT NULL,
  `title` VARCHAR(255) DEFAULT NULL,
  `excerpt` TEXT,
  `dayhits` INT(11) NOT NULL DEFAULT '0',
  `totalhits` INT(11) NOT NULL DEFAULT '0',
  `visible` BIT(1) NOT NULL DEFAULT '\0',
  `duplicate` BIT(1) NOT NULL DEFAULT '\0',
  PRIMARY KEY  (`id`),
  KEY `ref_websiteid_idx` (`websiteid`),
  KEY `ref_entryid_idx` (`entryid`),
  KEY `ref_refurl_idx` (`refurl`),
  KEY `ref_requrl_idx` (`requrl`),
  KEY `ref_datestr_idx` (`datestr`),
  KEY `ref_refpermlnk_idx` (`refpermalink`),
  KEY `ref_duplicate_idx` (`duplicate`),
  CONSTRAINT `ref_websiteid_fk` FOREIGN KEY (`websiteid`) REFERENCES `website` (`id`),
  CONSTRAINT `ref_entryid_fk` FOREIGN KEY (`entryid`) REFERENCES `weblogentry` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `referer` */

/*Table structure for table `roller_audit_log` */

-- DROP TABLE IF EXISTS `roller_audit_log`;

CREATE TABLE `roller_audit_log` (
  `id` VARCHAR(48) NOT NULL,
  `user_id` VARCHAR(48) NOT NULL,
  `object_id` VARCHAR(48) DEFAULT NULL,
  `object_class` VARCHAR(255) DEFAULT NULL,
  `comment_text` VARCHAR(255) NOT NULL,
  `change_time` DATETIME DEFAULT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `roller_audit_log` */

/*Table structure for table `roller_comment` */

-- DROP TABLE IF EXISTS `roller_comment`;

CREATE TABLE `roller_comment` (
  `id` VARCHAR(48) NOT NULL,
  `entryid` VARCHAR(48) NOT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  `email` VARCHAR(255) DEFAULT NULL,
  `url` VARCHAR(255) DEFAULT NULL,
  `content` TEXT,
  `posttime` DATETIME NOT NULL,
  `notify` BIT(1) NOT NULL DEFAULT '\0',
  `remotehost` VARCHAR(128) DEFAULT NULL,
  `referrer` VARCHAR(255) DEFAULT NULL,
  `useragent` VARCHAR(255) DEFAULT NULL,
  `status` VARCHAR(20) NOT NULL,
  `plugins` VARCHAR(255) DEFAULT NULL,
  `contenttype` VARCHAR(128) NOT NULL DEFAULT 'text/plain',
  PRIMARY KEY  (`id`),
  KEY `co_entryid_idx` (`entryid`),
  KEY `co_status_idx` (`status`),
  CONSTRAINT `co_entryid_fk` FOREIGN KEY (`entryid`) REFERENCES `weblogentry` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `roller_hitcounts` */

-- DROP TABLE IF EXISTS `roller_hitcounts`;

CREATE TABLE `roller_hitcounts` (
  `id` VARCHAR(48) NOT NULL,
  `websiteid` VARCHAR(48) NOT NULL,
  `dailyhits` INT(11) DEFAULT NULL,
  PRIMARY KEY  (`id`),
  KEY `rhc_websiteid_idx` (`websiteid`),
  KEY `rhc_dailyhits_idx` (`dailyhits`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `roller_hitcounts` */

/*Table structure for table `roller_properties` */

-- DROP TABLE IF EXISTS `roller_properties`;

CREATE TABLE `roller_properties` (
  `name` VARCHAR(255) NOT NULL,
  `value` TEXT,
  PRIMARY KEY  (`name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `roller_tasklock` */

-- DROP TABLE IF EXISTS `roller_tasklock`;

CREATE TABLE `roller_tasklock` (
  `id` VARCHAR(48) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `islocked` BIT(1) DEFAULT '\0',
  `timeacquired` DATETIME DEFAULT NULL,
  `timeleased` INT(11) DEFAULT NULL,
  `lastrun` DATETIME DEFAULT NULL,
  `client` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `rtl_name_uq` (`name`),
  KEY `rtl_taskname_idx` (`name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;



/*Table structure for table `roller_user_permissions` */

-- DROP TABLE IF EXISTS `roller_user_permissions`;

CREATE TABLE `roller_user_permissions` (
  `id` VARCHAR(48) NOT NULL,
  `website_id` VARCHAR(48) NOT NULL,
  `user_id` VARCHAR(48) NOT NULL,
  `permission_mask` INT(11) NOT NULL,
  `pending` BIT(1) NOT NULL DEFAULT '',
  PRIMARY KEY  (`id`),
  KEY `up_userid_fk` (`user_id`),
  KEY `up_websiteid_fk` (`website_id`),
  CONSTRAINT `up_websiteid_fk` FOREIGN KEY (`website_id`) REFERENCES `website` (`id`),
  CONSTRAINT `up_userid_fk` FOREIGN KEY (`user_id`) REFERENCES `rolleruser` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `roller_weblogentrytag` */

-- DROP TABLE IF EXISTS `roller_weblogentrytag`;

CREATE TABLE `roller_weblogentrytag` (
  `id` VARCHAR(48) NOT NULL,
  `entryid` VARCHAR(48) NOT NULL,
  `websiteid` VARCHAR(48) NOT NULL,
  `userid` VARCHAR(48) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `time` DATETIME NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `wet_entryid_idx` (`entryid`),
  KEY `wet_websiteid_idx` (`websiteid`),
  KEY `wet_userid_idx` (`userid`),
  KEY `wet_name_idx` (`name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `roller_weblogentrytagagg` */

-- DROP TABLE IF EXISTS `roller_weblogentrytagagg`;

CREATE TABLE `roller_weblogentrytagagg` (
  `id` VARCHAR(48) NOT NULL,
  `websiteid` VARCHAR(48) DEFAULT NULL,
  `name` VARCHAR(255) NOT NULL,
  `total` INT(11) NOT NULL,
  `lastused` DATETIME NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `weta_websiteid_idx` (`websiteid`),
  KEY `weta_name_idx` (`name`),
  KEY `weta_lastused_idx` (`lastused`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `rollerconfig` */

-- DROP TABLE IF EXISTS `rollerconfig`;

CREATE TABLE `rollerconfig` (
  `id` VARCHAR(48) NOT NULL,
  `sitedescription` VARCHAR(255) DEFAULT NULL,
  `sitename` VARCHAR(255) DEFAULT NULL,
  `emailaddress` VARCHAR(255) DEFAULT NULL,
  `absoluteurl` VARCHAR(255) DEFAULT NULL,
  `adminusers` VARCHAR(255) DEFAULT NULL,
  `encryptpasswords` BIT(1) NOT NULL DEFAULT '',
  `algorithm` VARCHAR(10) DEFAULT NULL,
  `newuserallowed` BIT(1) NOT NULL DEFAULT '\0',
  `editorpages` VARCHAR(255) DEFAULT NULL,
  `userthemes` VARCHAR(255) NOT NULL,
  `indexdir` VARCHAR(255) DEFAULT NULL,
  `memdebug` BIT(1) NOT NULL DEFAULT '\0',
  `autoformatcomments` BIT(1) NOT NULL DEFAULT '\0',
  `escapecommenthtml` BIT(1) NOT NULL DEFAULT '',
  `emailcomments` BIT(1) NOT NULL DEFAULT '\0',
  `enableaggregator` BIT(1) NOT NULL DEFAULT '\0',
  `enablelinkback` BIT(1) NOT NULL DEFAULT '\0',
  `rsscachetime` INT(11) NOT NULL DEFAULT '3000',
  `rssusecache` BIT(1) NOT NULL DEFAULT '',
  `uploadallow` VARCHAR(255) DEFAULT NULL,
  `uploadforbid` VARCHAR(255) DEFAULT NULL,
  `uploadenabled` BIT(1) NOT NULL DEFAULT '',
  `uploaddir` VARCHAR(255) NOT NULL,
  `uploadpath` VARCHAR(255) NOT NULL,
  `uploadmaxdirmb` DECIMAL(5,2) NOT NULL DEFAULT '4.00',
  `uploadmaxfilemb` DECIMAL(5,2) NOT NULL DEFAULT '1.50',
  `dbversion` VARCHAR(10) DEFAULT NULL,
  `refspamwords` TEXT,
  PRIMARY KEY  (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `rollerconfig` */

/*Table structure for table `rolleruser` */

-- DROP TABLE IF EXISTS `rolleruser`;

CREATE TABLE `rolleruser` (
  `id` VARCHAR(48) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  `passphrase` VARCHAR(255) NOT NULL,
  `screenname` VARCHAR(255) NOT NULL,
  `fullname` VARCHAR(255) NOT NULL,
  `emailaddress` VARCHAR(255) NOT NULL,
  `activationcode` VARCHAR(48) DEFAULT NULL,
  `datecreated` DATETIME NOT NULL,
  `locale` VARCHAR(20) DEFAULT NULL,
  `timezone` VARCHAR(50) DEFAULT NULL,
  `isenabled` BIT(1) NOT NULL DEFAULT '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ru_username_uq` (`username`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `usercookie` */

-- DROP TABLE IF EXISTS `usercookie`;

CREATE TABLE `usercookie` (
  `id` VARCHAR(48) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  `cookieid` VARCHAR(100) NOT NULL,
  `datecreated` DATETIME NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `uc_username_idx` (`username`),
  KEY `uc_cookieid_idx` (`cookieid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `usercookie` */

/*Table structure for table `userrole` */

-- DROP TABLE IF EXISTS `userrole`;

CREATE TABLE `userrole` (
  `id` VARCHAR(48) NOT NULL,
  `rolename` VARCHAR(255) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  `userid` VARCHAR(48) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `ur_userid_idx` (`userid`),
  KEY `ur_username_idx` (`username`),
  CONSTRAINT `ur_userid_fk` FOREIGN KEY (`userid`) REFERENCES `rolleruser` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `weblogcategory` */

-- DROP TABLE IF EXISTS `weblogcategory`;

CREATE TABLE `weblogcategory` (
  `id` VARCHAR(48) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `websiteid` VARCHAR(48) NOT NULL,
  `image` VARCHAR(255) DEFAULT NULL,
  `parentid` VARCHAR(48) DEFAULT NULL,
  `path` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`id`),
  KEY `wc_websiteid_idx` (`websiteid`),
  KEY `ws_parentid_idx` (`parentid`),
  KEY `ws_path_idx` (`path`),
  CONSTRAINT `wc_websiteid_fk` FOREIGN KEY (`websiteid`) REFERENCES `website` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `weblogcategoryassoc` */

-- DROP TABLE IF EXISTS `weblogcategoryassoc`;

CREATE TABLE `weblogcategoryassoc` (
  `id` VARCHAR(48) NOT NULL,
  `categoryid` VARCHAR(48) NOT NULL,
  `ancestorid` VARCHAR(40) DEFAULT NULL,
  `relation` VARCHAR(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `wca_categoryid_idx` (`categoryid`),
  KEY `wca_ancestorid_idx` (`ancestorid`),
  KEY `wca_relation_idx` (`relation`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `weblogcategoryassoc` */

/*Table structure for table `weblogentry` */

-- DROP TABLE IF EXISTS `weblogentry`;

CREATE TABLE `weblogentry` (
  `id` VARCHAR(48) NOT NULL,
  `userid` VARCHAR(48) NOT NULL,
  `anchor` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `text` TEXT NOT NULL,
  `pubtime` DATETIME DEFAULT NULL,
  `updatetime` DATETIME NOT NULL,
  `websiteid` VARCHAR(48) NOT NULL,
  `categoryid` VARCHAR(48) NOT NULL,
  `publishentry` BIT(1) NOT NULL DEFAULT '',
  `link` VARCHAR(255) DEFAULT NULL,
  `plugins` VARCHAR(255) DEFAULT NULL,
  `allowcomments` BIT(1) NOT NULL DEFAULT '\0',
  `commentdays` INT(11) NOT NULL DEFAULT '7',
  `rightToLeft` BIT(1) NOT NULL DEFAULT '\0',
  `pinnedtomain` BIT(1) NOT NULL DEFAULT '\0',
  `locale` VARCHAR(20) DEFAULT NULL,
  `status` VARCHAR(20) NOT NULL,
  `summary` TEXT,
  `content_type` VARCHAR(48) DEFAULT NULL,
  `content_src` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`id`),
  KEY `we_websiteid_idx` (`websiteid`),
  KEY `we_categoryid_idx` (`categoryid`),
  KEY `we_pinnedtom_idx` (`pinnedtomain`),
  KEY `we_userid_idx` (`userid`),
  KEY `we_status_idx` (`status`),
  KEY `we_locale_idx` (`locale`),
  KEY `we_combo1_idx` (`status`,`pubtime`,`websiteid`),
  KEY `we_combo2_idx` (`websiteid`,`pubtime`,`status`),
  CONSTRAINT `wc_categoryid_fk` FOREIGN KEY (`categoryid`) REFERENCES `weblogcategory` (`id`),
  CONSTRAINT `we_websiteid_fk` FOREIGN KEY (`websiteid`) REFERENCES `website` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `webpage` */

-- DROP TABLE IF EXISTS `webpage`;

CREATE TABLE `webpage` (
  `id` VARCHAR(48) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `link` VARCHAR(255) DEFAULT NULL,
  `websiteid` VARCHAR(48) NOT NULL,
  `template` TEXT NOT NULL,
  `updatetime` DATETIME NOT NULL,
  `hidden` BIT(1) NOT NULL DEFAULT '\0',
  `navbar` BIT(1) NOT NULL DEFAULT '\0',
  `templatelang` VARCHAR(20) NOT NULL,
  `decorator` VARCHAR(255) DEFAULT NULL,
  `outputtype` VARCHAR(48) DEFAULT NULL,
  `action` VARCHAR(16) NOT NULL DEFAULT 'custom',
  PRIMARY KEY  (`id`),
  KEY `wp_name_idx` (`name`),
  KEY `wp_link_idx` (`link`),
  KEY `wp_id_idx` (`websiteid`),
  CONSTRAINT `wp_websiteid_fk` FOREIGN KEY (`websiteid`) REFERENCES `website` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*Table structure for table `website` */

-- DROP TABLE IF EXISTS `website`;

CREATE TABLE `website` (
  `id` VARCHAR(48) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `handle` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `userid` VARCHAR(48) NOT NULL,
  `defaultpageid` VARCHAR(48) DEFAULT '',
  `weblogdayid` VARCHAR(48) NOT NULL,
  `ignorewords` TEXT,
  `enablebloggerapi` BIT(1) NOT NULL DEFAULT '\0',
  `editorpage` VARCHAR(255) DEFAULT NULL,
  `bloggercatid` VARCHAR(48) DEFAULT NULL,
  `defaultcatid` VARCHAR(48) DEFAULT NULL,
  `allowcomments` BIT(1) NOT NULL DEFAULT '',
  `emailcomments` BIT(1) NOT NULL DEFAULT '\0',
  `emailfromaddress` VARCHAR(255) DEFAULT NULL,
  `emailaddress` VARCHAR(255) NOT NULL,
  `editortheme` VARCHAR(255) DEFAULT NULL,
  `locale` VARCHAR(20) DEFAULT NULL,
  `timezone` VARCHAR(50) DEFAULT NULL,
  `defaultplugins` VARCHAR(255) DEFAULT NULL,
  `isenabled` BIT(1) NOT NULL DEFAULT '',
  `isactive` BIT(1) NOT NULL DEFAULT '',
  `datecreated` DATETIME NOT NULL,
  `blacklist` TEXT,
  `defaultallowcomments` BIT(1) NOT NULL DEFAULT '',
  `defaultcommentdays` INT(11) NOT NULL DEFAULT '7',
  `commentmod` BIT(1) NOT NULL DEFAULT '\0',
  `displaycnt` INT(11) NOT NULL DEFAULT '15',
  `lastmodified` DATETIME DEFAULT NULL,
  `pagemodels` VARCHAR(255) DEFAULT NULL,
  `enablemultilang` BIT(1) NOT NULL DEFAULT '\0',
  `showalllangs` BIT(1) NOT NULL DEFAULT '',
  `customstylesheet` VARCHAR(128) DEFAULT NULL,
  `about` VARCHAR(255) DEFAULT NULL,
  `icon` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ws_handle_uq` (`handle`),
  KEY `ws_userid_idx` (`userid`),
  KEY `ws_isenabled_idx` (`isenabled`),
  CONSTRAINT `ws_userid_fk` FOREIGN KEY (`userid`) REFERENCES `rolleruser` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `website` */


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;