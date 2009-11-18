CREATE OR REPLACE Package Body BDS_OPEN_REPORTS
IS

  cannotinsertnull EXCEPTION;
    PRAGMA EXCEPTION_INIT(cannotinsertnull,-1407);
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE sync_report_account
         ( p_bds_user_id                IN  number,             -- alliance user id whose account is to be updated
           p_trans_type                  IN  varchar2            -- 'A' add  'U' update 'D' delete
          )

    IS

    /*
    || Overview:        Updates a given user account to the database
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 04-15-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        v_report_user_id number;
        v_map_count number;
   BEGIN

        if (p_trans_type = 'D') then
            delete from bdsreports.user_group_map where user_id = p_bds_user_id;
            /*
                (select r.reportuser_id from bds_users u, report_user r where r.name = u.username
                    and u.user_id = p_bds_user_id and rownum = 1);
                    */
            delete from bdsreports.report_user where reportuser_id = p_bds_user_id;
                --(select username from bds_users where user_id = p_bds_user_id);
        end if;

        if (p_trans_type = 'I') then

            --select seq_pk_report_user.nextval into v_report_user_id from dual;

            insert into bdsreports.report_user
                (reportuser_id, name, password, admin, external_id, email_address, pdf_export_type)
            select
                p_bds_user_id, username, password, 0, 0, username, 0
                    from bds_users u, bds_user_roles r
                    where u.user_id = p_bds_user_id
                    and u.user_id = r.user_id
                    and r.role_id = 1
                    and user_type = 'V';

            v_map_count := 0;

            for c in (select role_id from bds_user_roles where user_id = p_bds_user_id and role_id = 1) loop

                begin
                    insert into bdsreports.user_group_map
                        (user_id, group_id, map_id)
                    values
                        (p_bds_user_id, c.role_id, v_map_count);

                    v_map_count := v_map_count + 1;
                exception
                    when others then
                        null;
                end;


            end loop;

        end if;

        if (p_trans_type = 'U') then
          begin

            select r.reportuser_id into v_report_user_id from  bdsreports.report_user r where
                    r.reportuser_id = p_bds_user_id and rownum = 1;

            update bdsreports.report_user set
                (name, password, email_address) =
                (select
                username, password, username
                    from bds_users u, bds_user_roles r
                    where u.user_id = p_bds_user_id
                    and u.user_id = r.user_id
                    and r.role_id = 1
                    and user_type = 'V')
            where reportuser_id = p_bds_user_id;

            v_map_count := 0;

            delete from bdsreports.user_group_map where user_id = v_report_user_id;

            for c in (select role_id from bds_user_roles where user_id = p_bds_user_id and role_id = 1) loop
                begin
                    insert into bdsreports.user_group_map
                        (user_id, group_id, map_id)
                    values
                        (p_bds_user_id, c.role_id, v_map_count);

                    v_map_count := v_map_count + 1;
                exception
                    when others then
                        null;
                end;
            end loop;

			exception
            when no_data_found then
                sync_report_account(p_bds_user_id, 'I');
            when cannotinsertnull then
                null;

            end;
         end if;

   END sync_report_account;

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_OPEN_REPORTS; -- Package Body bds_OPEN_REPORTS
/

