CREATE OR REPLACE procedure temp_move_users_to_bdsreport is
begin
insert into bdsreports.REPORT_USER
(
 REPORTUSER_ID,
 NAME,
 PASSWORD,
 ADMIN,
 EXTERNAL_ID,
 EMAIL_ADDRESS,
 PDF_EXPORT_TYPE
 )
 select u.USER_ID,
           USERNAME,
           PASSWORD,
           0,
           0,
           PASSWORD,
           0
           from bds_users u, bds_user_roles r
           where u.user_id = r.user_id
           and r.role_id = 1;


insert into bdsreports.user_group_map
(
    USER_ID,
    GROUP_ID,
    MAP_ID
)
 select u.USER_ID,
           1,
           0
           from bds_users u, bds_user_roles r
           where u.user_id = r.user_id
           and r.role_id = 1;

end;
/

