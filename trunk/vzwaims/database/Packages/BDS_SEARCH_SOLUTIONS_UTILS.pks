CREATE OR REPLACE Package BDS_SEARCH_SOLUTIONS_UTILS
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by BDS_SEARCH_SOLUTIONS package for user searching solutions.
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

    FUNCTION check_excluded_mkt_segment(p_solution_id number, p_mkt_segment_id number)
        return number;
        
/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION check_excluded(ent_app_id number)
        return number;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION check_if_public(ent_app_id number)
        return number;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION check_excluded_partner(p_alliance_id number)
        return number;
        
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_final_search_table
         ( p_search_string          IN  varchar2,                   -- search string          
           p_out_search_table      IN OUT NOCOPY DBMS_UTILITY.UNCL_ARRAY     -- search table
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_title
         (
            p_mkt_seg_id            number, 
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY             -- search string     
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_long_desc
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         );
         
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_short_desc
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         );    
         
/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_cust_benefits
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         );

 /* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_alliance_name
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         );

 /* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_dev_options
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         );

 /* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_components
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         );

 /* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_vert_markets
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         );                     

 /* -------------------------------------------------------------------------------------------------------------  */
 
    PROCEDURE search_ent_app_solution_type
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         );
 
 /* -------------------------------------------------------------------------------------------------------------  */
 
    PROCEDURE search_ent_app_category_id
         (
            p_mkt_seg_id            number,
            p_category_id          IN number
         );
 
 /* -------------------------------------------------------------------------------------------------------------  */
 
    PROCEDURE search_ent_app_sub_cat_id
         (
            p_mkt_seg_id            number,
            p_sub_category_id       IN number
         );
 
  /* -------------------------------------------------------------------------------------------------------------  */
  
    PROCEDURE search_ent_app_soln_type_id
         (
            p_mkt_seg_id            number,
            p_soln_type_id          IN number
         );
         
  /* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_vert_soln_id
         (
            p_mkt_seg_id            number,
            p_vert_soln_id          IN number
         );      
         
  /* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_soln_prov_id
     (
        p_mkt_seg_id            number,
        p_soln_provider_id      IN number
     );

  /* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_slprv_type_id
     (
        p_mkt_seg_id                     number,
        p_soln_provider_type_id          IN number
     );
         
  /* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_region_id
     (
        p_mkt_seg_id         number,
        p_region_id          IN number
     );

  /* -------------------------------------------------------------------------------------------------------------  */                   
  
END BDS_SEARCH_SOLUTIONS_UTILS; -- Package Specification BDS_SEARCH_SOLUTIONS_UTILS
/

