CREATE OR REPLACE Package BDS_SPOTLIGHT
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


    PROCEDURE get_spotlight_page
         (
           p_solution_id                 IN number,               -- solution_id
           p_spotlight_type_id           IN number,               -- spotlight_type_id
		   p_if_public                   IN varchar2,              -- if only public solutions required
           p_out_partner_details        OUT TYPES.cursor_type,    -- Result cursor
           p_out_solution_details       OUT TYPES.cursor_type     -- Result cursor                     
         );

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_SPOTLIGHT; -- Package Specification BDS_SPOTLIGHT
/

