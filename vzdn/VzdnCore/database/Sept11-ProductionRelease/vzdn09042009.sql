
INSERT INTO vzdn_role_privileges(role_id,privilege_id) VALUES(5,7);

UPDATE vzdn_sys_privileges SET privilege_description='Edit Profile' WHERE privilege_id=1;
UPDATE vzdn_sys_privileges SET privilege_description='Blogs Administrator' WHERE privilege_id=3;
UPDATE vzdn_sys_privileges SET privilege_description='Forum Administrator' WHERE privilege_id=4;
UPDATE vzdn_sys_privileges SET privilege_description='Applications link for Verizon User' WHERE privilege_id=5;

UPDATE vzdn_sys_privileges SET privilege_description='Alliances link for Verizon User' WHERE privilege_id=6;
UPDATE vzdn_sys_privileges SET privilege_description='Contracts link for Verizon User' WHERE privilege_id=14;

UPDATE vzdn_sys_privileges SET privilege_description='Blogs link for Developer' WHERE privilege_id=15;
UPDATE vzdn_sys_privileges SET privilege_description='Forum link for Developer' WHERE privilege_id=16;
UPDATE vzdn_sys_privileges SET privilege_description='Report link for Verizon User' WHERE privilege_id=21;

