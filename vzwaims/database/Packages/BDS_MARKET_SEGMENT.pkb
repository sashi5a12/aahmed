CREATE OR REPLACE Package Body BDS_MARKET_SEGMENT
AS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE delete_market_segment_details
         (
           p_market_segment_id                  IN number               -- market_segment_id
         )
    IS

    /*
    || Overview:        Delete the market segment and all its related mapping tables
    ||
    ||
    || Dependencies:     None
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 05-19-2004       Ahson Imtiaz    Created
    ||
    ||
    ||
    ||
    */

   BEGIN

   -- Add other tables delete statment if constraint is added in database having market segment id
   delete from BDS_PARTNER_MARKET_SEGMENTS where MARKET_SEGMENT_ID = p_market_segment_id;
   delete from BDS_QUESTION_MARKET_SEGMENTS where MARKET_SEGMENT_ID = p_market_segment_id;
   delete from BDS_SOLUTION_MARKET_SEGMENTS where MARKET_SEGMENT_ID = p_market_segment_id;
   delete from BDS_QUESTION_SCORING where MARKET_SEGMENT_ID = p_market_segment_id;
   delete from BDS_MARKET_SEGMENTS where MARKET_SEGMENT_ID = p_market_segment_id;

   COMMIT;

   EXCEPTION
      WHEN OTHERS THEN
	  	  ROLLBACK;

   raise_application_error(-20004, 'Error deleting the market segment from the relative tables.');


   END delete_market_segment_details;

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_MARKET_SEGMENT; -- Package Body BDS_MARKET_SEGMENT
/

