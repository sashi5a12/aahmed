CREATE OR REPLACE Package Body      BDS_SEARCH_SOLUTIONS
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_results_cursor
         (
           p_page_no_needed                IN number,              -- page number requested
           p_num_per_page                  IN number,              -- number of records per page
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_total_records            OUT number,              -- total number of records in selection set
           p_out_result                   OUT TYPES.cursor_type    -- Result cursor
         )
    IS

    /*
    || Overview:        Searches a give token of strings and return solution details cursor
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

        sql_select         varchar2(4000);
        sql_selone         varchar2(4000);
        sql_seltwo         varchar2(4000);
        sql_countone       varchar2(50) := 'SELECT COUNT(*) FROM ( ';
        sql_counttwo       varchar2(50) := ') ';

        from_rownum        number;
        to_rownum          number;

   BEGIN

        from_rownum := ((p_page_no_needed - 1) * p_num_per_page) + 1;
        to_rownum   := (from_rownum + p_num_per_page) - 1;

        sql_selone :=   sql_selone || 'SELECT ';
        sql_selone :=   sql_selone || '   b.*, ';
        sql_selone :=   sql_selone || '   cursor( select ';
        sql_selone :=   sql_selone || '               d.device_option_id, d.device_option_name, d.device_option_url ';
        sql_selone :=   sql_selone || '           from ';
        sql_selone :=   sql_selone || '               bds_device_options d, bds_solution_device_options sd ';
        sql_selone :=   sql_selone || '           where ';
        sql_selone :=   sql_selone || '               sd.solution_id = b.enterprise_apps_id ';
        sql_selone :=   sql_selone || '               and sd.device_option_id = d.device_option_id) options_cursor, ';
        sql_selone :=   sql_selone || '   cursor( select ';
        sql_selone :=   sql_selone || '               c.component_id, c.component_name, c.component_url, c.component_type ';
        sql_selone :=   sql_selone || '           from ';
        sql_selone :=   sql_selone || '               bds_components c, bds_solution_components sc ';
        sql_selone :=   sql_selone || '           where ';
        sql_selone :=   sql_selone || '               sc.solution_id = b.enterprise_apps_id ';
        sql_selone :=   sql_selone || '               and sc.component_id = c.component_id) components_cursor, ';
        sql_selone :=   sql_selone || '   cursor( select ';
        sql_selone :=   sql_selone || '               p.alliance_id, p.company_name ';
        sql_selone :=   sql_selone || '           from ';
        sql_selone :=   sql_selone || '               aims_alliances p, bds_solution_partners q ';
        sql_selone :=   sql_selone || '           where ';
        sql_selone :=   sql_selone || '               q.solution_id = b.enterprise_apps_id ';
        sql_selone :=   sql_selone || '               and p.alliance_id = q.partner_id ) add_components_cursor, ';
        sql_selone :=   sql_selone || '   cursor( select ';
        sql_selone :=   sql_selone || '               bst.solution_type_id, bst.solution_type_name ';
        sql_selone :=   sql_selone || '           from ';
        sql_selone :=   sql_selone || '               bds_solution_types bst, aims_ent_apps_sol_types ast ';
        sql_selone :=   sql_selone || '           where ';
        sql_selone :=   sql_selone || '               ast.enterprise_apps_id = b.enterprise_apps_id ';
        sql_selone :=   sql_selone || '               and ast.solution_type_id = bst.solution_type_id ) soln_type_cursor ';
        sql_selone :=   sql_selone || 'FROM (  ';
        sql_selone :=   sql_selone || '       SELECT ';
        sql_selone :=   sql_selone || '           a.*, ';
        sql_selone :=   sql_selone || '           ROWNUM rnum ';
        sql_selone :=   sql_selone || '       FROM  (   ';

        sql_select :=   sql_select || 'SELECT  DISTINCT ';
        sql_select :=   sql_select || '   e.enterprise_apps_id, ';
        sql_select :=   sql_select || '   a.title, ';
        sql_select :=   sql_select || '   e.customer_benefits, ';
        sql_select :=   sql_select || '    a.alliance_id, ';
        sql_select :=   sql_select || '   (select company_name from aims_alliances where alliance_id = a.alliance_id) company_name, ';
        sql_select :=   sql_select || '               nvl((   select ';
        sql_select :=   sql_select || '                           is_preferred ';
        sql_select :=   sql_select || '                       from ';
        sql_select :=   sql_select || '                           bds_solution_market_segments ';
        sql_select :=   sql_select || '                       where ';
        sql_select :=   sql_select || '                           solution_id = e.enterprise_apps_id ';
        sql_select :=   sql_select || '                           and market_segment_id = m.market_segment_id ';
        sql_select :=   sql_select || '                           and is_preferred = ''Y'' and rownum = 1), ''N'') is_preferred_soln, ';
        sql_select :=   sql_select || '               nvl((   select ';
        sql_select :=   sql_select || '                           is_preferred ';
        sql_select :=   sql_select || '                       from ';
        sql_select :=   sql_select || '                           bds_partner_market_segments ';
        sql_select :=   sql_select || '                       where ';
        sql_select :=   sql_select || '                           partner_id = a.alliance_id ';
        sql_select :=   sql_select || '                           and market_segment_id = m.market_segment_id ';
        sql_select :=   sql_select || '                           and is_preferred = ''Y'' and rownum = 1), ''N'') is_preferred_partner ';
        sql_select :=   sql_select || 'FROM ';
        sql_select :=   sql_select || '   aims_enterprise_apps e, ';
        sql_select :=   sql_select || '   aims_apps a, ';
        sql_select :=   sql_select || '   bds_solution_market_segments m ';
        sql_select :=   sql_select || 'WHERE ';
        sql_select :=   sql_select || '   m.market_segment_id = :1 ';
        sql_select :=   sql_select || '   and e.enterprise_apps_id in (SELECT distinct ent_apps_id FROM bds_search_table) ';
        sql_select :=   sql_select || '   and e.enterprise_apps_id = a.apps_id ';
        sql_select :=   sql_select || '   and e.enterprise_apps_id = m.solution_id ';
        sql_select :=   sql_select || '   and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, m.market_segment_id) = 1 ';

        sql_seltwo :=   sql_seltwo || ' ORDER BY 7 desc, upper(company_name) ';
        sql_seltwo :=   sql_seltwo || '         ) a ';
        sql_seltwo :=   sql_seltwo || '         WHERE ';
        sql_seltwo :=   sql_seltwo || '         ROWNUM <=  :2) b '; -- to_rownum
        sql_seltwo :=   sql_seltwo || '         WHERE ';
        sql_seltwo :=   sql_seltwo || '                     rnum >= :3 '; -- from_rownum

        p('Value of sql_countone='|| sql_selone || sql_select  || sql_seltwo);

        EXECUTE IMMEDIATE sql_countone || sql_select || sql_counttwo INTO p_out_total_records USING p_mkt_seg_id;

        OPEN p_out_result FOR sql_selone || sql_select || sql_seltwo USING  p_mkt_seg_id, to_rownum, from_rownum;

   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_result%ISOPEN) THEN
            CLOSE p_out_result;
          END IF;
          RAISE;

   END get_results_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions
         (
           p_search_string          IN varchar2,           -- search string
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- number of records per page
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         )
    IS

    /*
    || Overview:        Searches a give token of strings and return solution details cursor
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
        v_tbl_search_table  DBMS_UTILITY.UNCL_ARRAY;

   BEGIN

        BDS_SEARCH_SOLUTIONS_UTILS.get_final_search_table(p_search_string, v_tbl_search_table);

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_title(p_mkt_seg_id, v_tbl_search_table);

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_long_desc(p_mkt_seg_id, v_tbl_search_table);

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_short_desc(p_mkt_seg_id, v_tbl_search_table);

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_cust_benefits(p_mkt_seg_id, v_tbl_search_table);

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_alliance_name(p_mkt_seg_id, v_tbl_search_table);

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_dev_options(p_mkt_seg_id, v_tbl_search_table);

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_components(p_mkt_seg_id, v_tbl_search_table);

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_vert_markets(p_mkt_seg_id, v_tbl_search_table);

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_solution_type(p_mkt_seg_id, v_tbl_search_table);

        get_results_cursor
         (
           p_page_no_needed,
           p_num_per_page,
           p_mkt_seg_id,
           p_out_total_records,
           p_out_result
         );

   END search_solutions;

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
         )
    IS

    /*
    || Overview:        Searches a give token of strings and return solution details cursor
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

        if (p_category_id > 0) then
            BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_category_id(p_mkt_seg_id, p_category_id);
            select category_name into p_out_display_name
                from aims_app_categories where category_id = p_category_id;
        end if;

        if (p_sub_category_id > 0) then
            BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_sub_cat_id(p_mkt_seg_id, p_sub_category_id);
            select sub_category_name into p_out_display_name
                from aims_app_sub_categories where sub_category_id = p_sub_category_id;
        end if;

        --select count(*) into p_out_result from bds_search_table;

        get_results_cursor
         (
           p_page_no_needed,
           p_num_per_page,
           p_mkt_seg_id,
           p_out_total_records,
           p_out_result
         );

   END search_solutions_cat_subcat;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions_sol_prov
         (
           p_soln_provider_id       IN number,
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- market segment id
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         )
    IS

    /*
    || Overview:        Searches a give token of strings and return solution details cursor
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

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_soln_prov_id(p_mkt_seg_id, p_soln_provider_id);

        get_results_cursor
         (
           p_page_no_needed,
           p_num_per_page,
           p_mkt_seg_id,
           p_out_total_records,
           p_out_result
         );

   END search_solutions_sol_prov;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions_slprv_type
         (
           p_soln_provider_type_id  IN number,
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- market segment id
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         )
    IS

    /*
    || Overview:        Searches a give token of strings and return solution details cursor
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

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_slprv_type_id(p_mkt_seg_id, p_soln_provider_type_id);

        get_results_cursor
         (
           p_page_no_needed,
           p_num_per_page,
           p_mkt_seg_id,
           p_out_total_records,
           p_out_result
         );

   END search_solutions_slprv_type;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions_dist_area
         (
           p_region_id              IN number,
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- market segment id
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         )
    IS

    /*
    || Overview:        Searches a give token of strings and return solution details cursor
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

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_region_id(p_mkt_seg_id, p_region_id);

        get_results_cursor
         (
           p_page_no_needed,
           p_num_per_page,
           p_mkt_seg_id,
           p_out_total_records,
           p_out_result
         );

   END search_solutions_dist_area;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions_soln_type
         (
           p_soln_type_id           IN number,
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- market segment id
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         )
    IS

    /*
    || Overview:        Searches a give token of strings and return solution details cursor
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

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_soln_type_id(p_mkt_seg_id, p_soln_type_id);

        get_results_cursor
         (
           p_page_no_needed,
           p_num_per_page,
           p_mkt_seg_id,
           p_out_total_records,
           p_out_result
         );

   END search_solutions_soln_type;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_solutions_vert_soln
         (
           p_vert_soln_id           IN number,
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- market segment id
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         )
    IS

    /*
    || Overview:        Searches a give token of strings and return solution details cursor
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

        BDS_SEARCH_SOLUTIONS_UTILS.search_ent_app_vert_soln_id(p_mkt_seg_id, p_vert_soln_id);

        get_results_cursor
         (
           p_page_no_needed,
           p_num_per_page,
           p_mkt_seg_id,
           p_out_total_records,
           p_out_result
         );

   END search_solutions_vert_soln;

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_SEARCH_SOLUTIONS; -- Package Body BDS_SEARCH_SOLUTIONS
/

