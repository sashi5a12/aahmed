CREATE OR REPLACE Package AIMS_DISCUSSION_FORUM
  IS

/*
|| All contents are Copyright 2003 Verizon Wireless, Inc.
||
|| Author:              Rizwan Qazi (rqazi)
||
|| File:
||
|| Overview:            Used by AIMS application for discussion forum filter keywords.
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 03-18-2004       rqazi           Created
||
||
||
||
*/

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_filter_words
         (
            p_user_name              IN  varchar2,           -- username of the person updating the records
            p_filter_words           IN  varchar2            -- comma seperated list of filter words
         );

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_filter_word
         (
            p_words_title            IN  varchar2,           -- title
            p_words_body             IN  varchar2,           -- body
            p_out_result            OUT  varchar2            -- 'Y' one of the filter words is used 'N' words are good
          );


/* -------------------------------------------------------------------------------------------------------------  */


END AIMS_DISCUSSION_FORUM; -- Package Specification AIMS_DISCUSSION_FORUM
/

