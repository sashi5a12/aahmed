--To Add 'All Approved Java Applications' follow the following steps--

/////////////////////////////////////////////////////////////////

-- 1). First insert in aims_sub_menus table with starting id = 2000

drop from AIMS_SUB_MENUS where SUB_MENU_ID = 2000;

Insert into AIMS_SUB_MENUS
   (SUB_MENU_ID, SUB_MENU_NAME, SUB_MENU_URL, IMAGE_NAME, SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE, MENU_ID)
 Values
   (2000, 'All Approved Java Applications', '/aims/approvedJavaApps.do', 'all_applications', 4015, 'system', sysdate, 'system', sysdate, 3);


--#DELETE FROM AIMS_SUB_MENUS WHERE SUB_MENU_ID=2000;

////////////////////////////////////////////////////////////////

--2).Then insert in aims_sys_privileges with starting id = 2000
 
drop from aims_sys_privileges where privilege_id = 2000;

INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by,
             created_date, last_updated_by, last_updated_date, menu_id,
             sub_menu_id, privilege_key, available_to
            )
     VALUES (2000, 'Approved Java Applications', 'APPROVED_JAVA_APPLICATIONS', 'system',
             SYSDATE, 'system', SYSDATE, 3,
             2000, 'APPROVED_JAVA_APPLICATIONS', 'A'	
            )

--#DELETE FROM AIMS_SYS_PRIVILEGES WHERE PRIVILEGE_ID=2000;

/////////////////////////////////////////////////////////////code changes

--AimsPrivilegeConstants.java

--  public static String APPROVED_JAVA_APPLICATIONS = "APPROVED_JAVA_APPLICATIONS";

////////////////////////////////////////////////////////////////

--3). Login with admin user.
--4). Click 'Manage security' in the main menu.
--5). Click sub-menu item 'Roles'.
--6). Edit 'Primary User'
--7). Search privilege name 'All Approved Java Applications' and assign privileges accordingly.
 
