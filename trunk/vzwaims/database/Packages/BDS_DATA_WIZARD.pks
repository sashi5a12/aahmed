CREATE OR REPLACE Package BDS_DATA_WIZARD
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

   PROCEDURE insert_datawizard_table
        (
           p_quest_resp_string varchar2
        );

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION getSolutionPercentage
        (
            p_solution_id       number,
            p_max_points        number,
            p_market_seg_id     number
        )
        return number;

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION getMaxPointsForMktSegment(p_market_segment_id number)
        return number;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_questions_for_mkt_segments
         (
            p_market_segment_id            number,
            p_out_questions            OUT TYPES.cursor_type    -- Result cursor
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE get_data_wizard_solutions
         (
           p_quest_resp_string      IN varchar2,            -- search string
           p_page_no_needed         IN number,              -- page number requested
           p_num_per_page           IN number,              -- number of records per page
           p_mkt_seg_id             IN number,              -- number of records per page
           p_out_total_records     OUT number,              -- total number of records
           p_out_result            OUT TYPES.cursor_type    -- Result cursor
         );

 /* -------------------------------------------------------------------------------------------------------------  */

END BDS_DATA_WIZARD; -- Package Specification BDS_DATA_WIZARD
/

