CREATE TABLE  `test`.`customer` (
  `CUSTOMER_ID` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) NOT NULL,
  `ADDRESS` varchar(255) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`CUSTOMER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

CREATE TABLE  `test`.`user` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `first_name` varchar(128) NOT NULL,
  `last_name` varchar(128) NOT NULL,
  `date_created` bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;