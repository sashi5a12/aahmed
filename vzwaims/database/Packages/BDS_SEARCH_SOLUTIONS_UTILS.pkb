CREATE OR REPLACE Package Body BDS_SEARCH_SOLUTIONS_UTILS
IS

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION check_excluded_partner(p_alliance_id number)
        return number is

    ret_number number := 0;
    begin

    /* The partner should be included in atleast one market segment.
            If it is excluded in all then just return 0 */

            begin
                select
                    1
                into
                    ret_number
                from
                    bds_partner_market_segments
                where
                    partner_id = p_alliance_id
                    and is_excluded = 'N'
                    and rownum = 1;
            exception
                when no_data_found then
                    ret_number := 0;
            end;

            return ret_number;
    END check_excluded_partner;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION check_excluded(ent_app_id number)
        return number is

    ret_number number := 0;
    begin

    /* The partner should be included in atleast one market segment.
            If it is excluded in all then just return 0 */

            begin
                select
                    1
                into
                    ret_number
                from
                    bds_partner_market_segments ms,
                    aims_apps a
                where
                    a.apps_id = ent_app_id
                    and ms.partner_id = a.alliance_id
                    and is_excluded = 'N'
                    and rownum = 1;
            exception
                when no_data_found then
                    ret_number := 0;
            end;


    /*  The solution should be included at least in one market segment */

        if (ret_number > 0) then
                begin
                    select
                        1
                    into
                        ret_number
                    from
                        bds_solution_market_segments
                    where
                        solution_id = ent_app_id
                        and is_excluded = 'N'
                        and rownum = 1;
                exception
                    when no_data_found then
                        ret_number := 0;
                end;
            end if;

            return ret_number;
    END CHECK_EXCLUDED;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION check_if_public(ent_app_id number)
        return number is
    
    ret_number number := 0;
    begin
    
    /* If it is public then return 1 */
            
            begin
                select         
                    1
                into
                    ret_number             
                from
                    bds_solution_market_segments
                where
                    solution_id = ent_app_id            
                    and is_public = 'Y'
                    and rownum = 1;
            exception
                when no_data_found then
                    ret_number := 0;
            end;                   
    
            return ret_number;
			
    END check_if_public;
	
/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION check_excluded_mkt_segment(p_solution_id number, p_mkt_segment_id number)
        return number is

        ret_number number := 0;

    begin

    /* The partner should be included in the given market segment.
            If it is excluded then just return 0 */

            begin
                select
                    1
                into
                    ret_number
                from
                    bds_partner_market_segments ms,
                    aims_apps a
                where
                    a.apps_id = p_solution_id
                    and ms.partner_id = a.alliance_id
                    and ms.market_segment_id = p_mkt_segment_id
                    and is_excluded = 'N'
                    and rownum = 1;
            exception
                when no_data_found then
                    ret_number := 0;
            end;


    /*  The solution should be included at least in one market segment */

        if (ret_number > 0) then
                begin
                    select
                        1
                    into
                        ret_number
                    from
                        bds_solution_market_segments
                    where
                        solution_id = p_solution_id
                        and market_segment_id = p_mkt_segment_id
                        and is_excluded = 'N';
                exception
                    when no_data_found then
                        ret_number := 0;
                end;
            end if;

            return ret_number;
    END check_excluded_mkt_segment;

/* -------------------------------------------------------------------------------------------------------------  */


    PROCEDURE get_final_search_table
         ( p_search_string          IN  varchar2,                   -- search string
           p_out_search_table      IN OUT NOCOPY DBMS_UTILITY.UNCL_ARRAY     -- search table
         )

    IS

    /*
    || Overview:        Updates a given user account to the database
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


    v_search_string varchar2(500) := STRING_UTILS.sql_escape(trim(p_search_string));
    v_tbl_search_strings DBMS_UTILITY.UNCL_ARRAY;
    v_tbl_final_strings DBMS_UTILITY.UNCL_ARRAY;
    v_cnt_final_table pls_integer := 0;

    BEGIN

        if (INSTR(v_search_string, ' ') > 0) then
            v_tbl_search_strings := STRING_UTILS.split_standard(v_search_string, ' ');
        elsif (LENGTH(v_search_string) > 0) then
            v_tbl_search_strings(1) := UPPER(v_search_string);
        end if;

        for i in 1..v_tbl_search_strings.COUNT loop
            if (length(trim(v_tbl_search_strings(i))) > 0 ) then
                v_cnt_final_table := v_cnt_final_table + 1;
                v_tbl_final_strings(v_cnt_final_table) := UPPER(trim(v_tbl_search_strings(i)));
            end if;
        end loop;

        p_out_search_table := v_tbl_final_strings;

   END get_final_search_table;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_title
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY             -- search string
         )

    IS

    /*
    || Overview:        Updates a given user account to the database
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
    sql_select varchar2(32767);
    sql_insert varchar2(32767);

    BEGIN

                sql_select := sql_select || 'SELECT ';
                sql_select := sql_select || '   enterprise_apps_id ';
                sql_select := sql_select || 'FROM ';
                sql_select := sql_select || '   aims_enterprise_apps ';
                sql_select := sql_select || 'WHERE ';
                sql_select := sql_select || '   BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(enterprise_apps_id, :1 ) = 1 ';
                sql_select := sql_select || '   and ( ';

        for i in 1..p_search_table.COUNT loop
            if (i > 1) then
                sql_select := sql_select || '   OR ';
            end if;
                sql_select := sql_select || '   UPPER(bds_name) LIKE ''%' || p_search_table(i) || '%''';
        end loop;
                sql_select := sql_select || '        ) ';

        sql_insert := sql_insert || 'INSERT INTO bds_search_table (ent_apps_id) ';
        sql_insert := sql_insert || sql_select;

        execute immediate sql_insert using p_mkt_seg_id;

   END search_ent_app_title;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_long_desc
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         )

    IS

    /*
    || Overview:        Searches given keywords in the long description of the enterprise app table.
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
    sql_select varchar2(32767);
    sql_insert varchar2(32767);

    BEGIN

                sql_select := sql_select || 'SELECT ';
                sql_select := sql_select || '   e.enterprise_apps_id ';
                sql_select := sql_select || 'FROM ';
                sql_select := sql_select || '   aims_enterprise_apps e, ';
                sql_select := sql_select || '   aims_apps a ';
                sql_select := sql_select || 'WHERE ';
                sql_select := sql_select || '   a.apps_id = e.enterprise_apps_id ';
                sql_select := sql_select || '   and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, :1 ) = 1 ';
                sql_select := sql_select || '   and ( ';

        for i in 1..p_search_table.COUNT loop
            if (i > 1) then
                sql_select := sql_select || '   OR ';
            end if;
                sql_select := sql_select || '   UPPER(a.long_desc) LIKE ''%' || p_search_table(i) || '%''';
        end loop;
                sql_select := sql_select || '        ) ';

                sql_insert := sql_insert || 'INSERT INTO bds_search_table (ent_apps_id) ';
                sql_insert := sql_insert || sql_select;

                execute immediate sql_insert using p_mkt_seg_id;

   END search_ent_app_long_desc;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_short_desc
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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
    sql_select varchar2(32767);
    sql_insert varchar2(32767);

    BEGIN

                sql_select := sql_select || 'SELECT ';
                sql_select := sql_select || '   e.enterprise_apps_id ';
                sql_select := sql_select || 'FROM ';
                sql_select := sql_select || '   aims_enterprise_apps e, ';
                sql_select := sql_select || '   aims_apps a ';
                sql_select := sql_select || 'WHERE ';
                sql_select := sql_select || '   a.apps_id = e.enterprise_apps_id ';
                sql_select := sql_select || '   and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, :1 ) = 1 ';
                sql_select := sql_select || '   and ( ';

        for i in 1..p_search_table.COUNT loop
            if (i > 1) then
                sql_select := sql_select || '   OR ';
            end if;
                sql_select := sql_select || '   UPPER(a.short_desc) LIKE ''%' || p_search_table(i) || '%''';
        end loop;
                sql_select := sql_select || '        ) ';

                sql_insert := sql_insert || 'INSERT INTO bds_search_table (ent_apps_id) ';
                sql_insert := sql_insert || sql_select;

                execute immediate sql_insert using p_mkt_seg_id;

   END search_ent_app_short_desc;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_cust_benefits
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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
    sql_select varchar2(32767);
    sql_insert varchar2(32767);

    BEGIN

                sql_select := sql_select || 'SELECT ';
                sql_select := sql_select || '   enterprise_apps_id ';
                sql_select := sql_select || 'FROM ';
                sql_select := sql_select || '   aims_enterprise_apps ';
                sql_select := sql_select || 'WHERE ';
                sql_select := sql_select || '   BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(enterprise_apps_id, :1 ) = 1 ';
                sql_select := sql_select || '   and ( ';


        for i in 1..p_search_table.COUNT loop
            if (i > 1) then
                sql_select := sql_select || '   OR ';
            end if;
                sql_select := sql_select || '   UPPER(customer_benefits) LIKE ''%' || p_search_table(i) || '%''';
        end loop;
                sql_select := sql_select || '        ) ';

        sql_insert := sql_insert || 'INSERT INTO bds_search_table (ent_apps_id) ';
        sql_insert := sql_insert || sql_select;

        execute immediate sql_insert using p_mkt_seg_id;

   END search_ent_app_cust_benefits;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_alliance_name
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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
    sql_select varchar2(32767);
    sql_insert varchar2(32767);

    BEGIN

                sql_select := sql_select || 'SELECT ';
                sql_select := sql_select || '   e.enterprise_apps_id ';
                sql_select := sql_select || 'FROM ';
                sql_select := sql_select || '   aims_enterprise_apps e, ';
                sql_select := sql_select || '   aims_apps a, ';
                sql_select := sql_select || '   aims_alliances al ';
                sql_select := sql_select || 'WHERE ';
                sql_select := sql_select || '   a.apps_id = e.enterprise_apps_id ';
                sql_select := sql_select || '   and a.alliance_id = al.alliance_id ';
                sql_select := sql_select || '   and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, :1 ) = 1 ';
                sql_select := sql_select || '   and ( ';

        for i in 1..p_search_table.COUNT loop
            if (i > 1) then
                sql_select := sql_select || '   OR ';
            end if;
                sql_select := sql_select || '   UPPER(al.company_name) LIKE ''%' || p_search_table(i) || '%''';
        end loop;
                sql_select := sql_select || '        ) ';

                sql_insert := sql_insert || 'INSERT INTO bds_search_table (ent_apps_id) ';
                sql_insert := sql_insert || sql_select;

                execute immediate sql_insert using p_mkt_seg_id;

   END search_ent_app_alliance_name;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_dev_options
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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
    sql_select varchar2(32767);
    sql_insert varchar2(32767);

    BEGIN

                sql_select := sql_select || 'SELECT ';
                sql_select := sql_select || '   e.enterprise_apps_id ';
                sql_select := sql_select || 'FROM ';
                sql_select := sql_select || '   aims_enterprise_apps e, ';
                sql_select := sql_select || '   bds_device_options d, ';
                sql_select := sql_select || '   bds_solution_device_options sdo ';
                sql_select := sql_select || 'WHERE ';
                sql_select := sql_select || '   e.enterprise_apps_id = sdo.solution_id ';
                sql_select := sql_select || '   and d.device_option_id = sdo.device_option_id ';
                sql_select := sql_select || '   and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, :1 ) = 1 ';
                sql_select := sql_select || '   and ( ';

        for i in 1..p_search_table.COUNT loop
            if (i > 1) then
                sql_select := sql_select || '   OR ';
            end if;
                sql_select := sql_select || '   UPPER(d.device_option_name) LIKE ''%' || p_search_table(i) || '%''';
        end loop;
                sql_select := sql_select || '        ) ';

                sql_insert := sql_insert || 'INSERT INTO bds_search_table (ent_apps_id) ';
                sql_insert := sql_insert || sql_select;

                execute immediate sql_insert using p_mkt_seg_id;

   END search_ent_app_dev_options;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_components
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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
    sql_select varchar2(32767);
    sql_insert varchar2(32767);

    BEGIN

                sql_select := sql_select || 'SELECT ';
                sql_select := sql_select || '   e.enterprise_apps_id ';
                sql_select := sql_select || 'FROM ';
                sql_select := sql_select || '   aims_enterprise_apps e, ';
                sql_select := sql_select || '   bds_components c, ';
                sql_select := sql_select || '   bds_solution_components sco ';
                sql_select := sql_select || 'WHERE ';
                sql_select := sql_select || '   e.enterprise_apps_id = sco.solution_id ';
                sql_select := sql_select || '   and c.component_id = sco.component_id ';
                sql_select := sql_select || '   and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, :1 ) = 1 ';
                sql_select := sql_select || '   and ( ';

        for i in 1..p_search_table.COUNT loop
            if (i > 1) then
                sql_select := sql_select || '   OR ';
            end if;
                sql_select := sql_select || '   upper(c.component_name) like ''%' || p_search_table(i) || '%''';
        end loop;
                sql_select := sql_select || '        ) ';

                sql_insert := sql_insert || 'INSERT INTO bds_search_table (ent_apps_id) ';
                sql_insert := sql_insert || sql_select;

                execute immediate sql_insert using p_mkt_seg_id;

   END search_ent_app_components;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_vert_markets
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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
    sql_select varchar2(32767);
    sql_insert varchar2(32767);

    BEGIN

                sql_select := sql_select || 'SELECT ';
                sql_select := sql_select || '   e.enterprise_apps_id ';
                sql_select := sql_select || 'FROM ';
                sql_select := sql_select || '   aims_enterprise_apps e, ';
                sql_select := sql_select || '   aims_industry_focus i, ';
                sql_select := sql_select || '   aims_ent_apps_ind_focus eif ';
                sql_select := sql_select || 'where ';
                sql_select := sql_select || '   e.enterprise_apps_id = eif.enterprise_apps_id ';
                sql_select := sql_select || '   and i.industry_id = eif.industry_id ';
                sql_select := sql_select || '   and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, :1 ) = 1 ';
                sql_select := sql_select || '   and ( ';

        for i in 1..p_search_table.COUNT loop
            if (i > 1) then
                sql_select := sql_select || '   OR ';
            end if;
                sql_select := sql_select || '   upper(i.industry_name) like ''%' || p_search_table(i) || '%''';
        end loop;
                sql_select := sql_select || '        ) ';

                sql_insert := sql_insert || 'INSERT INTO bds_search_table (ent_apps_id) ';
                sql_insert := sql_insert || sql_select;

                execute immediate sql_insert using p_mkt_seg_id;

   END search_ent_app_vert_markets;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_solution_type
         (
            p_mkt_seg_id            number,
            p_search_table          IN OUT NOCOPY  DBMS_UTILITY.UNCL_ARRAY
         )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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
    sql_select varchar2(32767);
    sql_insert varchar2(32767);

    BEGIN

                sql_select := sql_select || 'SELECT ';
                sql_select := sql_select || '   e.enterprise_apps_id ';
                sql_select := sql_select || 'FROM ';
                sql_select := sql_select || '   aims_enterprise_apps e, ';
                sql_select := sql_select || '   bds_solution_types s ';
                sql_select := sql_select || 'where ';
                sql_select := sql_select || '   e.solution_type_id = s.solution_type_id ';
                sql_select := sql_select || '   and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, :1 ) = 1 ';
                sql_select := sql_select || '   and ( ';

        for i in 1..p_search_table.COUNT loop
            if (i > 1) then
                sql_select := sql_select || '   OR ';
            end if;
                sql_select := sql_select || '   upper(s.solution_type_name) like ''%' || p_search_table(i) || '%''';
        end loop;
                sql_select := sql_select || '        ) ';
                sql_insert := sql_insert || 'INSERT INTO bds_search_table (ent_apps_id) ';
                sql_insert := sql_insert || sql_select;

                execute immediate sql_insert using p_mkt_seg_id;

   END search_ent_app_solution_type;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_category_id
         (
            p_mkt_seg_id            number,
            p_category_id          IN number
         )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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

        INSERT INTO bds_search_table (ent_apps_id)
          SELECT
             e.enterprise_apps_id
          FROM
             aims_enterprise_apps e,
             aims_ent_apps_sub_categories es,
             aims_app_sub_categories s,
             aims_app_categories c
          WHERE
             c.category_id = p_category_id
             and e.enterprise_apps_id = es.enterprise_apps_id
             and s.sub_category_id = es.sub_category_id
             and c.category_id = s.category_id
             and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, p_mkt_seg_id ) = 1;

   END search_ent_app_category_id;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_sub_cat_id
         (
            p_mkt_seg_id            number,
            p_sub_category_id       IN number
         )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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

        INSERT INTO bds_search_table (ent_apps_id)
          SELECT
             e.enterprise_apps_id
          FROM
             aims_enterprise_apps e,
             aims_ent_apps_sub_categories es,
             aims_app_sub_categories s
          where
             s.sub_category_id = p_sub_category_id
             and e.enterprise_apps_id = es.enterprise_apps_id
             and s.sub_category_id = es.sub_category_id
             and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, p_mkt_seg_id ) = 1;

   END search_ent_app_sub_cat_id;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_soln_type_id
         (
            p_mkt_seg_id            number,
            p_soln_type_id          IN number
         )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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

        INSERT INTO bds_search_table (ent_apps_id)
          SELECT distinct
             e.enterprise_apps_id
          FROM
             aims_ent_apps_sol_types e
          where
             e.solution_type_id = p_soln_type_id
             and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, p_mkt_seg_id ) = 1;

   END search_ent_app_soln_type_id;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_vert_soln_id
         (
            p_mkt_seg_id            number,
            p_vert_soln_id          IN number
         )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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

        INSERT INTO bds_search_table (ent_apps_id)
          SELECT
             e.enterprise_apps_id
          FROM
             aims_enterprise_apps e,
             aims_ent_apps_ind_focus eif
          where
             eif.industry_id = p_vert_soln_id
             and e.enterprise_apps_id = eif.enterprise_apps_id
             and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, p_mkt_seg_id ) = 1;

   END search_ent_app_vert_soln_id;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_soln_prov_id
     (
        p_mkt_seg_id            number,
        p_soln_provider_id      IN number
     )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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

        INSERT INTO bds_search_table (ent_apps_id)
          SELECT
             e.enterprise_apps_id
          FROM
             aims_enterprise_apps e,
             aims_apps a
          where
             a.alliance_id = p_soln_provider_id
             and e.enterprise_apps_id = a.apps_id
             and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, p_mkt_seg_id ) = 1;

   END search_ent_app_soln_prov_id;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_slprv_type_id
     (
        p_mkt_seg_id                     number,
        p_soln_provider_type_id          IN number
     )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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

        INSERT INTO bds_search_table (ent_apps_id)
          SELECT
             e.enterprise_apps_id
          FROM
             aims_enterprise_apps e,
             aims_apps a,
             aims_alliance_roles ar
          where
             ar.role_id = p_soln_provider_type_id
             and ar.alliance_id = a.alliance_id
             and e.enterprise_apps_id = a.apps_id
             and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, p_mkt_seg_id ) = 1;

   END search_ent_app_slprv_type_id;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE search_ent_app_region_id
     (
        p_mkt_seg_id         number,
        p_region_id          IN number
     )

    IS

    /*
    || Overview:        Searches given keywords in the short description of the enterprise app table.
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

        INSERT INTO bds_search_table (ent_apps_id)
          SELECT
             e.enterprise_apps_id
          FROM
             aims_enterprise_apps e,
             aims_ent_apps_region ar
          where
             e.enterprise_apps_id = ar.enterprise_apps_id
             and ar.region_id = p_region_id
             and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, p_mkt_seg_id ) = 1;

   END search_ent_app_region_id;

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_SEARCH_SOLUTIONS_UTILS; -- Package Body BDS_SEARCH_SOLUTIONS_UTILS
/

