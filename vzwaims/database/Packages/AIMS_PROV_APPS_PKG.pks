CREATE OR REPLACE Package AIMS_PROV_APPS_PKG
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for provisioned apps functionality.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 09-03-2004       rqazi           Created
||
||
||
||
*/


/* -------------------------------------------------------------------------------------------------------------  */


    PROCEDURE get_results_cursor
         (
            p_user_id                       IN number,              -- user id of the person searching the records
            p_page_no_needed                IN number,              -- page number requested
            p_num_per_page                  IN number,              -- number of records per page
            p_filter_text                   IN varchar2,            -- filtering text
            p_filter_field                  IN varchar2,            -- filtering field
            p_sort_field                    IN varchar2,            -- filtering text
            p_out_total_records            OUT number,              -- total number of records in selection set
            p_out_result                   OUT TYPES.cursor_type    -- Result cursor
         );


/* -------------------------------------------------------------------------------------------------------------  */


END AIMS_PROV_APPS_PKG; -- Package Specification AIMS_PROV_APPS_PKG
/

