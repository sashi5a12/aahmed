CREATE OR REPLACE PACKAGE aims_brew_apps_trigger_helper
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application trigger on AIMS_BREW_APPS
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 11-05-2003       rqazi           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE on_evaluation_change
         (
            p_brew_apps_id           IN  number,             -- brew apps id
            p_old_evaluation         IN  varchar2,           -- current evaluation flag
			p_new_evaluation         IN  varchar2,           -- new evaluation flag
			p_new_deck_launch_date   IN  date                -- new deck launch date
          );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE on_deck_launch_date_change
         (
            p_brew_apps_id           IN  number,             -- brew apps id
            p_old_deck_launch_date   IN  date,               -- current deck launch date
            p_new_deck_launch_date   IN  date            -- new deck launch date
          );

/* -------------------------------------------------------------------------------------------------------------  */


END AIMS_BREW_APPS_TRIGGER_HELPER; -- Package Specification AIMS_BREW_APPS_TRIGGER_HELPER
/

