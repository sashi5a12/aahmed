CREATE OR REPLACE Package Body      AIMS_REPORTS
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE staff_performance_report
         ( p_user_id                IN  number,             -- VZW employee user_id whose performance report is calculated.
           p_curr_user_name         IN  varchar2,           -- The sytem username of the user running the report
           p_sort_order_col         IN  number,             -- The sort order column
           p_out_employee_name      OUT varchar2,           -- VZW employee user_name whose performance report is calculated.
           p_out_department_name    OUT varchar2,           -- VZW employee dept_name whose performance report is calculated.
           p_out_report_date        OUT varchar2,           -- The date the report is run.
           p_out_emp_alnc_details   OUT TYPES.cursor_type,  -- The Alliances associated with the VZW employee
           p_out_employee_details   OUT TYPES.cursor_type   -- All the eligible VZW employees
          )
    IS

    /*
    || Overview:        Gives the cursor for the alliances associated with a VZW employee.
    ||
    ||
    || Dependencies:
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

   BEGIN

       /*  Get the employee name of the selected user (for whom the performance report is needed) */
       p_out_employee_name := AIMS_REPORTS_UTILS.get_employee_name(p_user_id);


       /*  Get the department name of the selected user (for whom the performance report is needed) */
       p_out_department_name := AIMS_REPORTS_UTILS.get_department_name(p_user_id);


       /*  Get the date in MM/DD/YYYY on which the report is run */
       p_out_report_date := AIMS_REPORTS_UTILS.get_report_date();


       /*  Get the cursor for alliance details associated with a VZW user */
       AIMS_REPORTS_UTILS.vzw_emp_alliance_details(p_user_id, p_out_emp_alnc_details);


       /*  Get the cursor for all eligible VZW users (for the drop down) */
       AIMS_REPORTS_UTILS.vzw_employee_details(p_out_employee_details);


   END staff_performance_report;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_REPORTS; -- Package Body AIMS_REPORTS
/

