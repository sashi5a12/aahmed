CREATE OR REPLACE Package Body AIMS_BREW_APPS_TRIGGER_HELPER
IS

/* -------------------------------------------------------------------------------------------------------------  */

PROCEDURE on_evaluation_change
         (
            p_brew_apps_id           IN  number,             -- brew apps id
            p_old_evaluation         IN  varchar2,           -- current evaluation flag
			p_new_evaluation         IN  varchar2,           -- new evaluation flag
			p_new_deck_launch_date   IN  date                -- new deck launch date
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
10  EVALUATED
11  SUNSET
12  CONCEPT ACCEPTED
13  CONCEPT REJECTED

    The logic for this trigger is as follows:

If application status = "Submitted"
                        OR "Under Testing"
                        OR "Evaluated"          and application_evaluation = "Rejected",            then change the application status to "Rejected"
                        
If application status = "Under Testing"         and application_evaluation = "Sunset",              then change the application status to "Sunset"
                      

If application status = "Accepted"              and application_evaluation = "Sunset",              then change the application status to "Sunset"

If application status = "Submitted"             and application_evaluation = "Accepted Featured"    then change the application status to "Evaluated"
                                                                             OR "Accepted General"
                                                            WITH new_deck_launch_date = "NULL"

 If application status = "Submitted"            and application_evaluation = "Accepted Featured"    then change the application status to "Under Testing"
                                                                             OR "Accepted General"
                                                            WITH new_deck_launch_date = "NOT NULL"
                                                                            
If application status = "Rejected"              and application_evaluation = "Accepted Featured"    then change the application status to "Evaluated"
                                                                             OR "Accepted General"

If application status = "Submitted"             and application_evaluation = "Concept Accepted"     then change the application status to "Concept Accepted"

If application status = "Submitted"             and application_evaluation = "Concept Rejected"     then change the application status to "Concept Rejected"

If application status = "Concept Rejected"      and application_evaluation = "Concept Accepted"     then change the application status to "Concept Accepted"

If application status = "Concept Accepted"      and application_evaluation = "Concept Rejected"     then change the application status to "Concept Rejected"




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


        if ((v_app_status = 1 or v_app_status = 6 or v_app_status = 10) and (p_new_evaluation = 'N')) then
            update aims_apps set phase_id = 9 where apps_id = p_brew_apps_id;
        elsif ((v_app_status = 6) and (p_new_evaluation = 'S')) then
            update aims_apps set phase_id = 11 where apps_id = p_brew_apps_id;            
        elsif ((v_app_status = 7) and (p_new_evaluation = 'S')) then
            update aims_apps set phase_id = 11 where apps_id = p_brew_apps_id;            
        elsif ((v_app_status = 1) and (p_new_evaluation = 'F' or p_new_evaluation = 'G')) then
		    if (p_new_deck_launch_date is null) then
                update aims_apps set phase_id = 10 where apps_id = p_brew_apps_id;
            else
			    update aims_apps set phase_id = 6 where apps_id = p_brew_apps_id;
            end if;
        elsif ((v_app_status = 9) and (p_new_evaluation = 'F' or p_new_evaluation = 'G')) then
            update aims_apps set phase_id = 10 where apps_id = p_brew_apps_id;
        elsif ((v_app_status = 1) and (p_new_evaluation = 'A')) then
            update aims_apps set phase_id = 12 where apps_id = p_brew_apps_id;         
        elsif ((v_app_status = 1) and (p_new_evaluation = 'R')) then
            update aims_apps set phase_id = 13 where apps_id = p_brew_apps_id;
        elsif ((v_app_status = 13) and (p_new_evaluation = 'A')) then
            update aims_apps set phase_id = 12 where apps_id = p_brew_apps_id;         
        elsif ((v_app_status = 12) and (p_new_evaluation = 'R')) then
            update aims_apps set phase_id = 13 where apps_id = p_brew_apps_id;                    
        end if;
		

   END on_evaluation_change;
   
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE on_deck_launch_date_change
         (
            p_brew_apps_id           IN  number,             -- brew apps id
            p_old_deck_launch_date   IN  date,               -- current deck launch date
            p_new_deck_launch_date   IN  date            -- new deck launch date
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
10  EVALUATED
11  SUNSET
12  CONCEPT ACCEPTED
13  CONCEPT REJECTED

    The logic for this trigger is as follows:

On Change of Deck Launch Date, if the Application Status is Evaluated, it will move to Under Testing.


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


        if (v_app_status = 10) then
            update aims_apps set phase_id = 6 where apps_id = p_brew_apps_id;        
        end if;


   END on_deck_launch_date_change;
   
/* -------------------------------------------------------------------------------------------------------------  */
   
END AIMS_BREW_APPS_TRIGGER_HELPER; -- Package Body AIMS_BREW_APPS_TRIGGER_HELPER
/

