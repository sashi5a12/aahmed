CREATE OR REPLACE Package Body      BDS_DATA_WIZARD
IS


/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_datawizard_table
        (
           p_quest_resp_string varchar2
        )
     is

    v_pipe_array  DBMS_UTILITY.UNCL_ARRAY;
    v_comma_array DBMS_UTILITY.UNCL_ARRAY;
    v_cnt_pipe_array    number;
    v_cnt_comma_array   number;

    BEGIN

        if (INSTR(p_quest_resp_string,'|') > 0) then
            PARSE.delimstring_to_table(p_quest_resp_string, v_pipe_array, v_cnt_pipe_array, '|');
        elsif (LENGTH(p_quest_resp_string) > 0) then
            v_pipe_array(1) := p_quest_resp_string;
            v_cnt_pipe_array := 1;
        end if;

        if (v_cnt_pipe_array > 0) then
            for i IN 1..v_pipe_array.count loop
                PARSE.delimstring_to_table(v_pipe_array(i), v_comma_array, v_cnt_comma_array, ',');
                insert into bds_datawizard_table(question_id, response_id)
                    values(v_comma_array(1), v_comma_array(2));
            end loop;
        end if;

    END insert_datawizard_table;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION getSolutionPercentage
        (
            p_solution_id       number,
            p_max_points        number,
            p_market_seg_id     number
        )
        return number is

    v_percent       number:= 0;
    v_temp_count    number:= 0;
    v_score_count  number:= 0;


    BEGIN
        --ins_sql(' p_solution_id --> ' || p_solution_id || '  p_max_points --> ' || p_max_points );
        for c in (select question_id, response_id from bds_datawizard_table) loop

            begin

                select
                    points
                into
                    v_temp_count
                from
                    bds_question_scoring
                where
                    question_id = c.question_id
                    and response_id = c.response_id
                    and solution_id = p_solution_id
                    and market_segment_id = p_market_seg_id;

            exception
                when no_data_found then
                    v_temp_count := 0;
            end;

            v_score_count :=  v_score_count + v_temp_count;
            v_temp_count := 0;
        end loop;
            --p('Value of v_percent_string=' ||  to_char(v_score_count/p_max_points*100, '99.99'));
            v_percent := to_char((v_score_count/p_max_points)*100, 'FM00.00');
            --v_percent := v_score_count;
        return v_percent;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            return 00.00;

    END getSolutionPercentage;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION getMaxPointsForMktSegment(p_market_segment_id number)
        return number is

    v_ret_number number := 0;

    BEGIN

        select
            nvl(sum(sum_maxpoints), 0)
        into
            v_ret_number
        from
        (
            select
                question_id,
                sum(maxpoints) sum_maxpoints
            from
            (
                select
                    b.response_type,
                    a.question_id,
                    a.response_id,
                    max(a.points) maxpoints
                from
                    bds_question_scoring a,
                    bds_questions b
                where
                    a.solution_id in
                    (
                        select
                            solution_id
                        from
                            bds_solution_market_segments
                        where
                            market_segment_id = p_market_segment_id
                    )
                    and a.question_id = b.question_id
               group by
                    b.response_type, a.question_id, a.response_id
            )
            where
                response_type = 'C'
            group by
                question_id

        union

            select
                question_id,
                max(maxpoints) sum_maxpoints
            from
            (
                select
                    b.response_type,
                    a.question_id,
                    a.response_id,
                    max(a.points) maxpoints
                from
                    bds_question_scoring a,
                    bds_questions b
                where
                    a.solution_id in
                    (
                        select
                            solution_id
                        from
                            bds_solution_market_segments
                        where
                            market_segment_id = p_market_segment_id
                    )
                    and a.question_id = b.question_id
                group by
                    b.response_type, a.question_id, a.response_id
            )
            where
                response_type = 'R'
            group by
                question_id
        );

        return v_ret_number;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            return 0;
    END getMaxPointsForMktSegment;

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

    FUNCTION getMaxPoints(p_question_id number, p_market_segment_id number)
        return number is

    v_ret_number number := 0;
    v_response_type varchar2(10);

    BEGIN

        select
            response_type
        into
            v_response_type
        from
            bds_questions
        where
            question_id = p_question_id;


        if (v_response_type = 'R') then
            select
                max(points)
            into
                v_ret_number
            from
                bds_question_scoring
            where
                question_id = p_question_id
                and solution_id in
                (
                  select solution_id from bds_solution_market_segments where market_segment_id = p_market_segment_id
                )
                and rownum = 1;
        end if;

        if (v_response_type = 'C') then
            select
                sum(max(points))
            into
                v_ret_number
            from
                bds_question_scoring
            where
                question_id = p_question_id
                and solution_id in
                (
                  select solution_id from bds_solution_market_segments where market_segment_id = p_market_segment_id
                );
        end if;

        return v_ret_number;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            return 0;
    END getMaxPoints;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_questions_for_mkt_segments
         (
            p_market_segment_id            number,
            p_out_questions            OUT TYPES.cursor_type    -- Result cursor
         )
    IS

    /*
    || Overview:        Get questions for a given market segment.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 03-08-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */
        v_mkt_seg_id number:= p_market_segment_id;
        sql_select   varchar2(2000);

   BEGIN
        if (v_mkt_seg_id is null or v_mkt_seg_id = 0) then
            v_mkt_seg_id := getFirstMarketSegmentId();
        end if;

        sql_select :=   sql_select || 'select ';
        sql_select :=   sql_select || '     x.*, ';
        sql_select :=   sql_select || '     cursor ( ';
        sql_select :=   sql_select || '         select  ';
        sql_select :=   sql_select || '             r.response_id, r.response_name ';
        sql_select :=   sql_select || '         from ';
        sql_select :=   sql_select || '             bds_responses r, ';
        sql_select :=   sql_select || '             bds_question_responses bqr ';
        sql_select :=   sql_select || '         where ';
        sql_select :=   sql_select || '             r.response_id = bqr.response_id ';
        sql_select :=   sql_select || '             and bqr.question_id = x.question_id  ';
        sql_select :=   sql_select || '            ) ';
        sql_select :=   sql_select || 'from ';
        sql_select :=   sql_select || '     (  ';
        sql_select :=   sql_select || '         select distinct ';
        sql_select :=   sql_select || '             q.question_id, ';
        sql_select :=   sql_select || '             q.question_phrase, ';
        sql_select :=   sql_select || '             q.response_type   ';
        sql_select :=   sql_select || '         from ';
        sql_select :=   sql_select || '             bds_questions q, ';
        sql_select :=   sql_select || '             bds_question_scoring bqs, ';
        sql_select :=   sql_select || '             bds_solution_market_segments bsms ';
        sql_select :=   sql_select || '         where ';
        sql_select :=   sql_select || '             bsms.market_segment_id = :1 ';
        sql_select :=   sql_select || '             and q.question_id = bqs.question_id ';
        sql_select :=   sql_select || '             and bqs.solution_id = bsms.solution_id  ';
        sql_select :=   sql_select || '         order by ';
        sql_select :=   sql_select || '             2  ';
        sql_select :=   sql_select || '     )x ';


        OPEN p_out_questions FOR sql_select USING p_market_segment_id;

   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_questions%ISOPEN) THEN
            CLOSE p_out_questions;
          END IF;
          RAISE;
   END get_questions_for_mkt_segments;

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
        v_max_points       number;
        v_num_per_page     number := 10;
        v_total_records    number := p_num_per_page;

   BEGIN

        if (v_total_records is null or v_total_records <= 0) then
            v_total_records := 10;
        end if;

        from_rownum := ((p_page_no_needed - 1) * v_num_per_page) + 1;
        --to_rownum   := (from_rownum + v_num_per_page) - 1;
        to_rownum   := v_total_records;

        v_max_points := getMaxPointsForMktSegment(p_mkt_seg_id);

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
        sql_select :=   sql_select || '   a.alliance_id, ';
        sql_select :=   sql_select || '   (select company_name from aims_alliances where alliance_id = a.alliance_id) company_name, ';
        sql_select :=   sql_select || '   nvl((     select ';
        sql_select :=   sql_select || '                 is_preferred ';
        sql_select :=   sql_select || '             from ';
        sql_select :=   sql_select || '                 bds_solution_market_segments ';
        sql_select :=   sql_select || '             where ';
        sql_select :=   sql_select || '                 solution_id = e.enterprise_apps_id ';
        sql_select :=   sql_select || '                 and market_segment_id = m.market_segment_id ';
        sql_select :=   sql_select || '                 and is_preferred = ''Y'' and rownum = 1), ''N'') is_preferred_soln, ';
        sql_select :=   sql_select || '   nvl((     select ';
        sql_select :=   sql_select || '                 is_preferred ';
        sql_select :=   sql_select || '             from ';
        sql_select :=   sql_select || '                 bds_partner_market_segments ';
        sql_select :=   sql_select || '             where ';
        sql_select :=   sql_select || '                 partner_id = a.alliance_id ';
        sql_select :=   sql_select || '                 and market_segment_id = m.market_segment_id ';
        sql_select :=   sql_select || '                 and is_preferred = ''Y'' and rownum = 1), ''N'') is_preferred_partner, ';
        sql_select :=   sql_select || '    BDS_DATA_WIZARD.getSolutionPercentage(e.enterprise_apps_id, :1, m.market_segment_id)              ';
        sql_select :=   sql_select || 'FROM ';
        sql_select :=   sql_select || '   aims_enterprise_apps e, ';
        sql_select :=   sql_select || '   aims_apps a, ';
        sql_select :=   sql_select || '   bds_solution_market_segments m ';
        sql_select :=   sql_select || 'WHERE ';
        sql_select :=   sql_select || '   m.market_segment_id = :2 ';
        sql_select :=   sql_select || '   and BDS_SEARCH_SOLUTIONS_UTILS.check_excluded_mkt_segment(e.enterprise_apps_id, m.market_segment_id) = 1 ';
        sql_select :=   sql_select || '   and e.enterprise_apps_id = a.apps_id ';
        sql_select :=   sql_select || '   and e.enterprise_apps_id = m.solution_id ';

        sql_seltwo :=   sql_seltwo || ' ORDER BY 8 desc, 7 desc, 5 ';
        sql_seltwo :=   sql_seltwo || '         ) a ';
        sql_seltwo :=   sql_seltwo || '         WHERE ';
        sql_seltwo :=   sql_seltwo || '         ROWNUM <=  :3) b '; -- to_rownum
        sql_seltwo :=   sql_seltwo || '         WHERE ';
        sql_seltwo :=   sql_seltwo || '                     rnum >= :4 '; -- from_rownum

        --p('Value of sql_countone='|| sql_selone || sql_select  || sql_seltwo);

        EXECUTE IMMEDIATE sql_countone || sql_select || sql_counttwo INTO p_out_total_records USING
                                                                            v_max_points, p_mkt_seg_id;

        if (v_total_records < p_out_total_records) then
            p_out_total_records := v_total_records;
        end if;

        OPEN p_out_result FOR sql_selone || sql_select || sql_seltwo USING  v_max_points, p_mkt_seg_id,
                                                                            to_rownum, from_rownum;
/*
   EXCEPTION
      WHEN OTHERS THEN
          IF(p_out_result%ISOPEN) THEN
            CLOSE p_out_result;
          END IF;
          RAISE;
*/
   END get_results_cursor;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_data_wizard_solutions
         (
           p_quest_resp_string      IN varchar2,            -- search string
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

   BEGIN
        --ins_sql(' p_quest_resp_string --> ' || p_quest_resp_string);
        insert_datawizard_table(p_quest_resp_string);

        get_results_cursor
         (
           p_page_no_needed,
           p_num_per_page,
           p_mkt_seg_id,
           p_out_total_records,
           p_out_result
         );

   END get_data_wizard_solutions;

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_DATA_WIZARD; -- Package Body BDS_DATA_WIZARD
/

