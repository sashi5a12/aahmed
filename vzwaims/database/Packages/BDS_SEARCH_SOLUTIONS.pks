CREATE OR REPLACE Package BDS_SEARCH_SOLUTIONS
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

    PROCEDURE search_solutions
         (
           p_search_string          IN varchar2,           -- search string
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- number of records per page
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions_cat_subcat
         (
           p_category_id            IN number,
           p_sub_category_id        IN number,
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- market segment id
           p_out_display_name      OUT varchar2,            -- display name
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions_sol_prov
         (
           p_soln_provider_id       IN number,
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- market segment id
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions_slprv_type
         (
           p_soln_provider_type_id  IN number,
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- market segment id
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions_dist_area
         (
           p_region_id              IN number,
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- market segment id
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions_soln_type
         (
           p_soln_type_id           IN number,
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- market segment id
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions_vert_soln
         (
           p_vert_soln_id           IN number,
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- market segment id
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_SEARCH_SOLUTIONS; -- Package Specification BDS_SEARCH_SOLUTIONS
/

