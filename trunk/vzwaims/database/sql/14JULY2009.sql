delete from AIMS_ROLE_PRIVILEGES arp where arp.PRIVILEGE_ID in (2001,2002)
/
delete from AIMS_SYS_PRIVILEGES asp where asp.PRIVILEGE_ID in (2001,2002)
/
Insert into AIMS_SYS_PRIVILEGES
   (PRIVILEGE_ID, PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE, PRIVILEGE_KEY, AVAILABLE_TO)
 Values
   (2001, 'Manage Java Apps', 'Manage Java Apps', 'system', sysdate, 'system', sysdate, 'MANAGE_APP_JAVA', 'B')
/