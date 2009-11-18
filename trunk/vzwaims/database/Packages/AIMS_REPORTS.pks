CREATE OR REPLACE Package AIMS_REPORTS
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

    PROCEDURE staff_performance_report
         ( p_user_id                IN  number,             -- VZW employee user_id whose performance report is calculated.
           p_curr_user_name         IN  varchar2,           -- The sytem username of the user running the report
           p_sort_order_col         IN  number,             -- The sort order column
           p_out_employee_name      OUT varchar2,           -- VZW employee user_name whose performance report is calculated.
           p_out_department_name    OUT varchar2,           -- VZW employee dept_name whose performance report is calculated.
           p_out_report_date        OUT varchar2,           -- The date the report is run.
           p_out_emp_alnc_details   OUT TYPES.cursor_type,  -- The Alliances associated with the VZW employee
           p_out_employee_details   OUT TYPES.cursor_type  -- All the eligible VZW employees
          );


/* -------------------------------------------------------------------------------------------------------------  */


END AIMS_REPORTS; -- Package Specification AIMS_REPORTS
/

