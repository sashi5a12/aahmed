CREATE OR REPLACE Package BDS_SEARCH_PAGE
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

    PROCEDURE return_null_cursor
         (
            p_out_null_cursor       OUT TYPES.cursor_type    -- null cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_all_subcats_for_category
         (
            p_category_id                  number,
            p_out_category_name        OUT varchar2,
            p_out_sub_categories       OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_soln_provider_cursor
         (
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_soln_provider            OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_all_soln_providers_cursor
         (
            p_out_soln_provider            OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_soln_provider_type_cursor
         (
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_soln_provider_type       OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

     PROCEDURE get_soln_type_cursor
         (
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_soln_type                OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_vert_soln_cursor
         (
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_vert_soln                OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_region_cursor
         (
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_region                   OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_search_page
         (
           p_mkt_seg_id                  IN number,               -- number of records per page
           p_out_cat_subcat             OUT TYPES.cursor_type,    -- Result cursor
           p_out_soln_provider          OUT TYPES.cursor_type,    -- Result cursor
           p_out_soln_provider_type     OUT TYPES.cursor_type,    -- Result cursor
           p_out_soln_type              OUT TYPES.cursor_type,    -- Result cursor
           p_out_vert_soln              OUT TYPES.cursor_type,    -- Result cursor
           p_out_region                 OUT TYPES.cursor_type     -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_SEARCH_PAGE; -- Package Specification BDS_SEARCH_PAGE
/

