CREATE OR REPLACE Package AIMS_PROG_GUIDE_PKG
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
            p_program_guide_name            IN varchar2,            -- full/partial name of program guide
            p_nstl_comp_start_date          IN varchar2,            -- nstl completion start date
            p_nstl_comp_end_date            IN varchar2,            -- nstl completion end date
            p_page_no_needed                IN number,              -- page number requested
            p_num_per_page                  IN number,              -- number of records per page
            p_filter_text                   IN varchar2,            -- filtering text
            p_filter_field                  IN varchar2,            -- filtering field
            p_sort_field                    IN varchar2,            -- filtering text
            p_out_total_records            OUT number,              -- total number of records in selection set
            p_out_result                   OUT TYPES.cursor_type    -- Result cursor
         );


/* -------------------------------------------------------------------------------------------------------------  */


END AIMS_PROG_GUIDE_PKG; -- Package Specification AIMS_PROG_GUIDE_PKG
/

