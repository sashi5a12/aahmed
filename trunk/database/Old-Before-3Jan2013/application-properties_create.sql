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