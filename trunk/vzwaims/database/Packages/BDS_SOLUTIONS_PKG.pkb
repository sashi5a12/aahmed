CREATE OR REPLACE Package Body BDS_SOLUTIONS_PKG
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_solution
         (
           p_solution_id            IN number
         )
    IS

    /*
    || Overview:        Deletes a given enterprise_apps and related records in the database.
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

	   delete from bds_question_scoring where solution_id = p_solution_id;
	   delete from bds_solution_components where solution_id = p_solution_id;
	   delete from bds_solution_device_options where solution_id = p_solution_id;
	   delete from bds_solution_market_segments where solution_id = p_solution_id;
	   delete from bds_solution_partners where solution_id = p_solution_id;

   END delete_solution;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE add_bds_records_on_accept
         (
           p_solution_id            IN number,
		   p_partner_id            IN number
         )
    IS

    /*
    || Overview:        Adds Records in BDS Mapping table, when the status of application/solution changes to 'Accepted'.
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

        v_count_solution_mkt_segment number :=0;
        v_count_partner_mkt_segment number :=0;


   BEGIN
        select
            count(*)
        into
            v_count_solution_mkt_segment
        from
            bds_solution_market_segments
        where
            solution_id = p_solution_id;

        select
            count(*)
        into
            v_count_partner_mkt_segment
        from
            bds_partner_market_segments
        where
            partner_id = p_partner_id;

        for c in (select market_segment_id from bds_market_segments) loop
            if (v_count_solution_mkt_segment = 0) then
                insert into bds_solution_market_segments (solution_id, market_segment_id)
                    values (p_solution_id, c.market_segment_id);
            end if;

            if (v_count_partner_mkt_segment = 0) then
                insert into bds_partner_market_segments (partner_id, market_segment_id)
                    values (p_partner_id, c.market_segment_id);
            end if;
        end loop;

   END add_bds_records_on_accept;

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_SOLUTIONS_PKG; -- Package Body BDS_SOLUTIONS_PKG
/

