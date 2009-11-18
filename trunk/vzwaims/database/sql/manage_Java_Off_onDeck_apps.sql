-- RUN THIS SCRIPT IF YOU HAVE NOT RUN THE MASTER SCRIPT
-- (ALREADY INCLUDED IN MASTER)


INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id,sub_menu_id, privilege_key, available_to  )
     VALUES (2001, 'Manage App Javaondeck', 'Manage App Javaondeck', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'MANAGE_APP_JAVAONDECK', 'B'  )
;            
INSERT INTO aims_sys_privileges
            (privilege_id, privilege_name, privilege_description, created_by, created_date, last_updated_by, 
			last_updated_date, menu_id,sub_menu_id, privilege_key, available_to  )
     VALUES (2002, 'Manage App Javaoffdeck', 'Manage App Javaoffdeck', 
     		'system', SYSDATE, 'system', SYSDATE, NULL, NULL, 'MANAGE_APP_JAVAFFDECK', 'B'  )
;