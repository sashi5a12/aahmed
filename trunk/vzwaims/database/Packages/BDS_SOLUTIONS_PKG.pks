CREATE OR REPLACE Package BDS_SOLUTIONS_PKG
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for user account maintenance.
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

    PROCEDURE delete_solution
         (
           p_solution_id            IN number
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE add_bds_records_on_accept
         (
           p_solution_id            IN number,
		   p_partner_id            IN number
         );

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_SOLUTIONS_PKG; -- Package Specification BDS_SOLUTIONS_PKG
/

