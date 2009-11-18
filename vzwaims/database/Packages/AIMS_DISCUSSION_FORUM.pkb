CREATE OR REPLACE PACKAGE BODY AIMS_DISCUSSION_FORUM
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_filter_words
         (
            p_user_name              IN  varchar2,           -- username of the person updating the records
            p_filter_words           IN  varchar2            -- comma seperated list of filter words
         )
    IS

    /*
    || Overview:        Updates the filter words table
    ||
    ||
    || Dependencies:
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

    v_space_array DBMS_UTILITY.UNCL_ARRAY;
    v_cnt_space_array    number;
    v_filter_words  varchar2(32767) := trim(p_filter_words);

    BEGIN

        if (INSTR(v_filter_words,' ') > 0) then
            PARSE.delimstring_to_table(v_filter_words, v_space_array, v_cnt_space_array, ' ');
        elsif (LENGTH(v_filter_words) > 0) then
            v_space_array(1) := v_filter_words;
            v_cnt_space_array := 1;
        end if;

        execute immediate 'truncate table aims_filter_words reuse storage';

        if (v_cnt_space_array > 0) then
            for i IN 1..v_space_array.count loop
                if(length(trim(v_space_array(i))) > 0) then
                    begin
                        insert into aims_filter_words(word)
                            values(v_space_array(i));
                    exception
                        when dup_val_on_index then
                            null;
                    end;
                end if;
            end loop;
        end if;

   END update_filter_words;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_filter_word
         (
            p_words_title            IN  varchar2,           -- title
            p_words_body             IN  varchar2,           -- body
            p_out_result            OUT  varchar2            -- 'Y' one of the filter words is used 'N' words are good
          )

    IS

    /*
    || Overview:        Checks if words are from filter words table.
    ||
    ||
    || Dependencies:
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
        v_space_array DBMS_UTILITY.UNCL_ARRAY;
        v_cnt_space_array    number;
        v_words  varchar2(32767) := trim(p_words_body) || trim(p_words_title);
        v_temp_word varchar2(100);
        v_out_result varchar2(1):= 'N';
        v_out_filter_words varchar2(32767) := '';

   BEGIN

        p_out_result := 'N';

        if (INSTR(v_words,' ') > 0) then
            PARSE.delimstring_to_table(v_words, v_space_array, v_cnt_space_array, ' ');
        elsif (LENGTH(v_words) > 0) then
            v_space_array(1) := v_words;
            v_cnt_space_array := 1;
        end if;

        if (v_cnt_space_array > 0) then
            for i IN 1..v_space_array.count loop
                v_temp_word := trim(v_space_array(i));
                if(length(v_temp_word) > 0) then
                    begin
                        select 'Y' into v_out_result from aims_filter_words where upper(word) = upper(v_temp_word);
                        return;
                    exception
                        when others then
                            null;
                    end;
                end if;
            end loop;
        end if;

        p_out_result := v_out_result || '|' || v_out_filter_words;

   END check_filter_word;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_DISCUSSION_FORUM; -- Package Body AIMS_DISCUSSION_FORUM
/

