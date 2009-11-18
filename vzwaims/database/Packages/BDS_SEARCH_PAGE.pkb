CREATE OR REPLACE Package Body      BDS_SEARCH_PAGE
IS

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION getFirstMarketSegmentId
        return number is

    ret_number number := 0;
    BEGIN
            for c in (select market_segment_id from bds_market_segments order by market_segment_name) loop
                return c.market_segment_id;
            end loop;
            return ret_number;
    END getFirstMarketSegmentId;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE return_null_cursor
         ( p_out_null_cursor                OUT TYPES.cursor_type   -- null cursor
         )
    IS

    /*
    || Overview:        Gets just a NULL cursor.
    ||
    || Called from:
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 03-04-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */


    BEGIN

      OPEN p_out_null_cursor FOR SELECT 1 FROM DUAL WHERE 1=2;

    END return_null_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_cat_subcat_cursor
         (
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_cat_subcat               OUT TYPES.cursor_type    -- Result cursor
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

   BEGIN

        sql_select :=   sql_select || 'SELECT  x.*, ';
        sql_select :=   sql_select || '     cursor(select sub_category_id, sub_category_name from aims_app_sub_categories where category_id = x.category_id) ';
        sql_select :=   sql_select || 'FROM ';
        sql_select :=   sql_select || '( ';
        sql_select :=   sql_select || '     SELECT distinct ';
        sql_select :=   sql_select || '         c.category_id, ';
        sql_select :=   sql_select || '         c.category_name  ';
        sql_select :=   sql_select || '     FROM  ';
        sql_select :=   sql_select || '         aims_ent_apps_sub_categories e, ';
        sql_select :=   sql_select || '         aims_app_sub_categories s, ';
        sql_select :=   sql_select || '         aims_app_categories c, ';
        sql_select :=   sql_select || '         bds_solution_market_segments m  ';
        sql_select :=   sql_select || '     WHERE  ';
        sql_select :=   sql_select || '         m.market_segment_id = :1 ';
        sql_select :=   sql_select || '         and c.platform_id = 5 ';
        sql_select :=   sql_select || '         and e.enterprise_apps_id = m.solution_id  ';
        sql_select :=   sql_select || '         and e.sub_category_id = s.sub_category_id  ';
        sql_select :=   sql_select || '         and s.category_id = c.category_id  ';
        sql_select :=   sql_select || '     ORDER BY 2 ';
        sql_select :=   sql_select || ') x ';

        --p('Value of sql_countone='|| sql_select);

        OPEN p_out_cat_subcat FOR sql_select USING p_mkt_seg_id;

   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_cat_subcat%ISOPEN) THEN
            CLOSE p_out_cat_subcat;
          END IF;
          RAISE;

   END get_cat_subcat_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_soln_provider_cursor
         (
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_soln_provider            OUT TYPES.cursor_type    -- Result cursor
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

        OPEN p_out_soln_provider FOR

            SELECT distinct
                 al.alliance_id,
                 al.company_name
            FROM
                 aims_enterprise_apps e,
                 aims_apps ap,
                 aims_alliances al,
                 bds_partner_market_segments m
            WHERE
                 m.market_segment_id = p_mkt_seg_id
                 and al.alliance_id = m.partner_id
                 and e.enterprise_apps_id = ap.apps_id
                 and ap.alliance_id = al.alliance_id
                 and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, m.market_segment_id) = 1
            ORDER BY 2;


   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_soln_provider%ISOPEN) THEN
            CLOSE p_out_soln_provider;
          END IF;
          RAISE;

   END get_soln_provider_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_all_subcats_for_category
         (
            p_category_id                  number,
            p_out_category_name        OUT varchar2,
            p_out_sub_categories       OUT TYPES.cursor_type    -- Result cursor
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

            SELECT
                category_name
            INTO
                p_out_category_name
            FROM
                aims_app_categories
            WHERE
                category_id = p_category_id;

        OPEN p_out_sub_categories FOR

            SELECT
                sub_category_id,
                sub_category_name
            FROM
                aims_app_sub_categories
            WHERE
                category_id = p_category_id
            ORDER BY 2;

   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_sub_categories%ISOPEN) THEN
            CLOSE p_out_sub_categories;
          END IF;
          RAISE;

   END get_all_subcats_for_category;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_all_soln_providers_cursor
         (
            p_out_soln_provider            OUT TYPES.cursor_type    -- Result cursor
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

        OPEN p_out_soln_provider FOR
            SELECT distinct
                 al.alliance_id,
                 al.company_name
            FROM
                 aims_enterprise_apps e,
                 aims_apps ap,
                 aims_alliances al,
                 bds_partner_market_segments m
            WHERE
                 al.alliance_id = m.partner_id
                 and e.enterprise_apps_id = ap.apps_id
                 and ap.alliance_id = al.alliance_id
                 and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded(e.enterprise_apps_id) = 1
            ORDER BY 2;

   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_soln_provider%ISOPEN) THEN
            CLOSE p_out_soln_provider;
          END IF;
          RAISE;

   END get_all_soln_providers_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_soln_provider_type_cursor
         (
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_soln_provider_type       OUT TYPES.cursor_type    -- Result cursor
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

        OPEN p_out_soln_provider_type FOR

            SELECT
                 role_id,
                 role_name
            FROM
                 aims_roles_of_alliance
            ORDER BY 2;

   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_soln_provider_type%ISOPEN) THEN
            CLOSE p_out_soln_provider_type;
          END IF;
          RAISE;

    END get_soln_provider_type_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_soln_type_cursor
         (
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_soln_type                OUT TYPES.cursor_type    -- Result cursor
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

   BEGIN

        OPEN p_out_soln_type FOR

            SELECT
                 solution_type_id,
                 solution_type_name
            FROM
                 bds_solution_types
            ORDER BY 2;

   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_soln_type%ISOPEN) THEN
            CLOSE p_out_soln_type;
          END IF;
          RAISE;

   END get_soln_type_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

   PROCEDURE get_vert_soln_cursor
         (
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_vert_soln                OUT TYPES.cursor_type    -- Result cursor
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

   BEGIN

        OPEN p_out_vert_soln FOR

            SELECT
                 industry_id,
                 industry_name
            FROM
                 aims_industry_focus
            ORDER BY 2;

   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_vert_soln%ISOPEN) THEN
            CLOSE p_out_vert_soln;
          END IF;
          RAISE;

   END get_vert_soln_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

   PROCEDURE get_region_cursor
         (
           p_mkt_seg_id                    IN number,              -- market segment id
           p_out_region                   OUT TYPES.cursor_type    -- Result cursor
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

   BEGIN

        OPEN p_out_region FOR

            SELECT
                 region_id,
                 region_name
            FROM
                 aims_regions
            ORDER BY 2;

   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_region%ISOPEN) THEN
            CLOSE p_out_region;
          END IF;
          RAISE;

   END get_region_cursor;

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
        v_mkt_seg_id number:= p_mkt_seg_id;

   BEGIN
        if (v_mkt_seg_id is null or v_mkt_seg_id = 0) then
            v_mkt_seg_id := getFirstMarketSegmentId();
        end if;

        get_cat_subcat_cursor           (v_mkt_seg_id, p_out_cat_subcat);
        get_soln_provider_cursor        (v_mkt_seg_id, p_out_soln_provider);
        get_soln_provider_type_cursor   (v_mkt_seg_id, p_out_soln_provider_type);
        get_soln_type_cursor            (v_mkt_seg_id, p_out_soln_type);
        get_vert_soln_cursor            (v_mkt_seg_id, p_out_vert_soln);
        get_region_cursor               (v_mkt_seg_id, p_out_region);

   END get_search_page;

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_SEARCH_PAGE; -- Package Body BDS_SEARCH_PAGE
/

