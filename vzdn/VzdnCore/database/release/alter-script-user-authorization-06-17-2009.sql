update vzdn_sub_menus set sub_menu_name = 'My Notifications' where sub_menu_id=2;
update vzdn_sub_menus set sub_menu_url = 'myNotification.action' where sub_menu_id=2;

update vzdn_sys_privileges set privilege_key = 'EDIT_PROFILE' where privilege_id=1;
update vzdn_sys_privileges set privilege_key = 'MY_NOTIFICATIONS' where privilege_id=2;
update vzdn_sys_privileges set privilege_key = 'BLOG_LINK_FOR_ADMIN' where privilege_id=3;
update vzdn_sys_privileges set privilege_key = 'FORUM_LINK_FOR_ADMIN' where privilege_id=4;
update vzdn_sys_privileges set privilege_key = 'APPLICATIONS' where privilege_id=5;
update vzdn_sys_privileges set privilege_key = 'ALLIANCES' where privilege_id=6;
update vzdn_sys_privileges set privilege_key = 'MANAGE_NOTIFICATIONS' where privilege_id=7;
update vzdn_sys_privileges set privilege_key = 'PUBLISH_CONTENT' where privilege_id=8;
update vzdn_sys_privileges set privilege_key = 'MANAGE_USERS' where privilege_id=11;
update vzdn_sys_privileges set privilege_key = 'MY_APPLICATION_FOR_DEV' where privilege_id=12;
update vzdn_sys_privileges set privilege_key = 'MY_CONTRACTS_FOR_DEV' where privilege_id=13;
update vzdn_sys_privileges set privilege_key = 'CONTRACTS_PAGE_FOR_VZ_USER' where privilege_id=14;
update vzdn_sys_privileges set privilege_key = 'BLOG_LINK_FOR_DEVELOPER' where privilege_id=15;
update vzdn_sys_privileges set privilege_key = 'FORUM_LINK_FOR_DEVELOPER'where privilege_id=16;
