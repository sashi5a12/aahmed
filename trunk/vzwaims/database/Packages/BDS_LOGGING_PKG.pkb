CREATE OR REPLACE Package Body BDS_LOGGING_PKG
IS

/*
-------------------------------------------------------------------------------------------------------------
  */

     PROCEDURE log_action
          ( p_module_path            IN  varchar2,          -- path of the module accessed egs /aims/createThis.do
            p_task_name              IN  varchar2,          -- user name of the person deleting the alliance
            p_requested_URL			 IN  varchar2,
			p_solution_id            IN  number,
            p_mkt_segment_id         IN  number,
            p_partner_id             IN  number,
            p_user_id                IN  number,
            p_session_id             IN  varchar2,
            p_spotlight_id           IN  number,
            p_component_id           IN  number,
            p_access_date            IN  date,
			p_remote_ip_address      IN  varchar2,
			p_referrer				 IN  varchar2
           )
     IS

     /*
     || Overview:        Logs the user action for the BDS module
     ||
     ||
     || Dependencies:
     ||
     || Modification History:
     || When             Who             What
     ||---------------------------------------
     || 06-16-2004       rqazi           Created
     ||
     ||
     ||
     ||
     */
         pragma autonomous_transaction;

         v_module_path     varchar2(200);
         v_module_id        number;

    BEGIN
		 begin
         	   select
               		  module_id
         	   into
               		  v_module_id
         	   from
               		  bds_log_module_mst
         	   where
              		  module_key = p_module_path;
				exception
				            when others then
                			null;
		 end;

         insert into bds_log_entry_mst
             (entry_id, module_id, task_name, requested_url, solution_id,
              market_segment_id, partner_id, user_id,
              session_id, spotlight_id, component_id, access_date,
			  remote_ip_address, referrer
			  )
         values
             (SEQ_PK_LOG_ENTRY_MST.nextval, v_module_id, p_task_name,
			  p_requested_URL, p_solution_id,
              p_mkt_segment_id, p_partner_id, p_user_id,
              p_session_id, p_spotlight_id, p_component_id,p_access_date,
			  p_remote_ip_address,p_referrer);

         commit;
    END log_action;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_login_try_entry
         ( p_user_name              IN  varchar2,  -- username of the login tried
           p_pass_word              IN  varchar2,  -- password of the login tried
           p_status                 IN  varchar2,  -- status - F (failed) OR P (passed)
           p_ip_address             IN  varchar2,  -- ip address from which login was tried
           p_host_address           IN  varchar2,  -- host address from which login was tried
           p_out_result            OUT  varchar2   -- result - F (failed) OR P (passed)
          )

     IS

     /*
     || Overview:        Logs the login attemps and specifies whether tries have been exceeded.
     ||
     ||
     || Dependencies:
     ||
     || Modification History:
     || When             Who             What
     ||---------------------------------------
     || 11-19-2004       rqazi           Created
     ||
     ||
     ||
     ||
     */
            v_tries     number;
            const_max_tries constant number := 5;

     BEGIN

            insert into bds_login_info
                (
                login_info_id,
                user_name,
                pass_word,
                status,
                ip,
                host_addr,
                unlock_flag,
                created_date
                )
            values
                (
                seq_pk_bds_login_info.nextval,
                p_user_name,
                p_pass_word,
                p_status,
                p_ip_address,
                p_host_address,
                'F',
                sysdate
                );

                commit;

                select
                    count(*)
                into
                    v_tries
                from
                    bds_login_info
                where
                    user_name = p_user_name
                    and status = 'F'
                    and unlock_flag = 'F'
                    and trunc(created_date) = trunc(sysdate);

                if (v_tries > const_max_tries) then
                    if (v_tries = const_max_tries + 1) then
                        p_out_result := 'F';
                    else
                        p_out_result := 'X';
                    end if;
                else
                    p_out_result := 'P';
                end if;

     END insert_login_try_entry;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE unlock_login_try_entry
         ( p_user_name              IN  varchar2,  -- username of the login tried
           p_pass_word              IN  varchar2,  -- password of the login tried
           p_status                 IN  varchar2,  -- status - F (failed) OR P (passed)
           p_ip_address             IN  varchar2,  -- ip address from which login was tried
           p_host_address           IN  varchar2   -- host address from which login was tried
          )

     IS

     /*
     || Overview:        Unlocks the locked username
     ||
     ||
     || Dependencies:
     ||
     || Modification History:
     || When             Who             What
     ||---------------------------------------
     || 11-19-2004       rqazi           Created
     ||
     ||
     ||
     ||
     */

     BEGIN

            insert into bds_login_info
                (
                login_info_id,
                user_name,
                pass_word,
                status,
                ip,
                host_addr,
                created_date
                )
            values
                (
                seq_pk_bds_login_info.nextval,
                p_user_name,
                p_pass_word,
                'U',            -- unlocked and allowed
                p_ip_address,
                p_host_address,
                sysdate
                );

            update
                bds_login_info
            set
                unlock_flag='T'
            where
                user_name = p_user_name
                and status = 'F';

            commit;

     END unlock_login_try_entry;

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_LOGGING_PKG; -- Package Body BDS_LOGGING_PKG
/

