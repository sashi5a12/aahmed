CREATE OR REPLACE Package AIMS_APPS_LIST_PKG
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for generating different reports.
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

    PROCEDURE get_results_cursor
         (
            p_app_title                     IN varchar2,            -- full/partial title of application
            p_platform_id                   IN number,              -- platform id of the application
            p_phase_id                      IN number,              -- phase id of the application
            p_category_id                   IN number,              -- category id of the application
            p_sub_category_id               IN number,              -- sub category id of the application
            p_selected_devices              IN varchar2,            -- comma seperated list of device ids
            p_brew_app_type                 IN varchar2,            -- New VZW App, Concept, etc.
            p_permitted_platforms           IN varchar2,            -- comma seperated list permitted platform ids
            p_alliance_id                   IN number,              -- alliance id of the application user
            p_user_id                       IN number,              -- user id of the application user
            p_contact_id                    IN number,              -- vzw contact id of the application
            p_query_type                    IN varchar2,            -- for egs. NEW BREW APP, SAVED APPS, SUBMITTED APPS, etc
            p_page_no_needed                IN number,              -- page number requested
            p_num_per_page                  IN number,              -- number of records per page
            p_filter_text                   IN varchar2,            -- filtering text
            p_filter_field                  IN varchar2,            -- filtering field
            p_sort_field                    IN varchar2,            -- sort field
            p_user_type                     IN varchar2,            -- type of user
			p_filter_platforms				IN varchar2,			-- comma seperated list of selected platforms
			p_filter_statuses				IN varchar2,			-- comma seperated list of selected statuses
			p_sort_order					IN varchar2,			-- sorting order either asc or desc
            p_out_total_records            OUT number,              -- total number of records in selection set
            p_out_result                   OUT TYPES.cursor_type    -- Result cursor
         );


/* -------------------------------------------------------------------------------------------------------------  */


END AIMS_APPS_LIST_PKG; -- Package Specification AIMS_PROG_GUIDE_PKG
/

