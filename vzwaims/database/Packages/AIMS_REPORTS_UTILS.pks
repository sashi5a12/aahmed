CREATE OR REPLACE PACKAGE Aims_Reports_Utils
  IS

/*
|| All contents are Copyright Verizon Wireless.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS_REPORTS package
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

    FUNCTION get_employee_name
        (
            p_user_id                   IN      NUMBER
        )
    RETURN  VARCHAR2;

    PRAGMA RESTRICT_REFERENCES( get_employee_name, wnds, wnps, rnps );

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_department_name
        (
            p_user_id                   IN      NUMBER
        )
    RETURN  VARCHAR2;

    PRAGMA RESTRICT_REFERENCES( get_department_name, wnds, wnps, rnps );

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_report_date
    RETURN  VARCHAR2;

    PRAGMA RESTRICT_REFERENCES( get_report_date, wnds, wnps, rnps );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE vzw_emp_alliance_details
         (
           p_user_id                     IN     NUMBER,
           p_out_result                 OUT     Types.cursor_type
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE vzw_employee_details
         (
           p_out_result                 OUT     Types.cursor_type
         );

/* -------------------------------------------------------------------------------------------------------------  */
    FUNCTION Get_Market_Share
         (
            p_alliance_id                   IN  NUMBER,
            p_phase_id                      IN  NUMBER,
			p_start_date					IN  DATE,
			p_end_date						IN  DATE
          ) RETURN NUMBER;

END Aims_Reports_Utils; -- Package Specification AIMS_REPORTS_UTILS
/

