CREATE OR REPLACE Package BDS_LOGGING_PKG
   IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by BDS application for logging the user
actions.
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

/* ------------------------------------------------------------------------------------------------------------ */

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
           );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_login_try_entry
         ( p_user_name              IN  varchar2,  -- username of the login tried
           p_pass_word              IN  varchar2,  -- password of the login tried
           p_status                 IN  varchar2,  -- status - F (failed) OR P (passed)
           p_ip_address             IN  varchar2,  -- ip address from which login was tried
           p_host_address           IN  varchar2,  -- host address from which login was tried
           p_out_result            OUT  varchar2   -- result - F (failed) OR P (passed)
          );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE unlock_login_try_entry
         ( p_user_name              IN  varchar2,  -- username of the login tried
           p_pass_word              IN  varchar2,  -- password of the login tried
           p_status                 IN  varchar2,  -- status - F (failed) OR P (passed)
           p_ip_address             IN  varchar2,  -- ip address from which login was tried
           p_host_address           IN  varchar2   -- host address from which login was tried
          );

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_LOGGING_PKG; -- Package Specification BDS_LOGGING_PKG
/

