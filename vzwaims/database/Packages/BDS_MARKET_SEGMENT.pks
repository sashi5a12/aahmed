CREATE OR REPLACE Package BDS_MARKET_SEGMENT
  IS

/*
|| All contents are Copyright 2004 Verizon Wireless, Inc.
||
|| Author:              Ahson Imtiaz
||
|| File:
||
|| Overview:            Used by BDS application for market segment management.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 05-19-2004       Ahson Imtiaz   Created
||
||
||
||
*/
/* -------------------------------------------------------------------------------------------------------------  */


    PROCEDURE delete_market_segment_details
         (
           p_market_segment_id                  IN number               -- market segment id
         );

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_MARKET_SEGMENT; -- Package Specification BDS_MARKET_SEGMENT
/

