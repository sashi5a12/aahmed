CREATE OR REPLACE PROCEDURE brew_apps_trigger_helper
         (
            p_brew_apps_id           IN  number,             -- brew apps id
            p_old_evaluation         IN  varchar2,           -- current evaluation flag
            p_new_evaluation         IN  varchar2            -- new evaluation flag
          )

    IS

    /*
    || Overview:        Used from the trigger TRG_AFT_UPD_BREW_APPS
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 04-09-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */
    /*
1	SUBMITTED
2	PREVIEWED
3	ASSIGNED
4	CERTIFIED
5	PRIORITIZED
6	UNDER TESTING
7	ACCEPTED
8	SAVED
9	REJECTED
    The logic for this trigger is as follows:

If application status = "Submitted" and application_evaluation = "Rejected", then change the application status to "Rejected"
If appliation status = "Rejected" and (application_evaluation = "Accepted Featured" or "Accepted General"), then change the application status to "Submitted"
If application status = "Under Testing" and (application_evaluation = "Accepted Featured" or "Accepted General"), then do nothing

    */

        v_app_status    number;
   BEGIN

        select
            phase_id
        into
            v_app_status
        from
            aims_apps
        where
            apps_id = p_brew_apps_id;


        if (v_app_status = 1 and p_new_evaluation = 'N') then
            update aims_apps set phase_id = 9 where apps_id = p_brew_apps_id;
        elsif ((v_app_status = 9) and (p_new_evaluation = 'F' or p_new_evaluation = 'G')) then
            update aims_apps set phase_id = 1 where apps_id = p_brew_apps_id;
        elsif ((v_app_status = 6) and (p_new_evaluation = 'F' or p_new_evaluation = 'G')) then
            null;
        end if;


   END brew_apps_trigger_helper;
/

