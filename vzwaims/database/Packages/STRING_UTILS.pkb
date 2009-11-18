CREATE OR REPLACE PACKAGE BODY STRING_UTILS
IS

-------------------------------------------------------------------------------------------

Function RET_SQL_STMT
( tbl_status IN DBMS_UTILITY.UNCL_ARRAY)

RETURN  varchar2 IS

/*
|| Overview:        Takes an array and returns an select statement of the form
||                   select elm1, elm2 .... from dual
||
|| Dependencies:
||
|| Modification History:
|| When             Who             What
||---------------------------------------
|| 10-30-2002       rqazi           Created
||
||
||
||
*/

sql_status varchar2(4000);

BEGIN
                sql_status := sql_status || 'SELECT ';
        FOR i IN 1..tbl_status.COUNT LOOP
            IF(i <> tbl_status.LAST) THEN
                sql_status := sql_status || '''' || tbl_status(i) || '''' || ',';
            ELSE
                sql_status := sql_status || '''' || tbl_status(i) || '''';
            END IF;
        END LOOP;
                sql_status := sql_status || ' FROM dual';

RETURN sql_status;

END RET_SQL_STMT;

-------------------------------------------------------------------------------------------

Function check_number
  ( word IN varchar2)

RETURN  varchar2 IS

/*
Name -                CHECK_NUMBER
Description -        This function checks to see if 'word' is a number.
Input Parameters -    The string to be checked.
Return Value -       '1' if 'word' is a number; '0' otherwise.
Created By -         Rizwan Qazi (Netpace Inc.)
Created On -         11/29/2000
*/

    temp_string    varchar2(1000);

BEGIN

    temp_string := to_number(word); -- if this succeeds then 'word' is a number
        RETURN '1' ;

EXCEPTION

   WHEN value_error THEN
        RETURN '0' ;

END CHECK_NUMBER;

-------------------------------------------------------------------------------------------

Function         TIME_STRING
  RETURN  varchar2 IS

out_time varchar2(50);
BEGIN
    SELECT
        to_char(sysdate,'DD:Mon:YYYY -- HH24:MI:SS')
    INTO out_time
    FROM
        dual;
    RETURN out_time ;

END TIME_STRING; -- Function TIME_STRING

-------------------------------------------------------------------------------------------

Function         TRIM_CRLF
  ( in_string varchar2)
  RETURN  varchar2 IS
/*
Line feed chr(10)
Carraige return chr(13)
*/

ret_string varchar2(2000);
BEGIN
    ret_string := TRIM(in_string);

    ret_string := replace(in_string, chr(10),'');
    ret_string := replace(ret_string, chr(13),'');
    RETURN ret_string ;

EXCEPTION
    WHEN OTHERS THEN
       RETURN ret_string ;
END TRIM_CRLF; -- Function TRIM_CRLF

-------------------------------------------------------------------------------------------

Function         ESCAPE_JS
  ( in_string IN varchar2)
  RETURN  varchar2 IS
   retstring                 varchar2(2000) := in_string;
   -- Declare program variables as shown above
BEGIN
    retstring := REPLACE(retstring, '"',';');
    RETURN retstring ;
EXCEPTION
   WHEN OTHERS THEN
       RETURN  retstring;
END ESCAPE_JS; -- Function ESCAPE_JS

-------------------------------------------------------------------------------------------

Function         UNESCAPE_JS
  ( in_string IN varchar2)
  RETURN  varchar2 IS
   retstring                 varchar2(2000) := in_string;

BEGIN
    retstring := REPLACE(retstring, ';','"');
    RETURN retstring ;
EXCEPTION
   WHEN OTHERS THEN
       RETURN  retstring;
END UNESCAPE_JS; -- Function UNESCAPE_JS

-------------------------------------------------------------------------------------------

Function         SQL_ESCAPE
  ( in_string IN varchar2)

  RETURN  varchar2 IS
var_in_string varchar2(4000) := in_string;
BEGIN
    var_in_string := replace(var_in_string,'''','''''');
    RETURN var_in_string ;

END SQL_ESCAPE; -- Function SQL_ESCAPE

-------------------------------------------------------------------------------------------

Function         SQL_UNESCAPE
  ( in_string IN varchar2)

  RETURN  varchar2 IS
var_in_string varchar2(4000) := in_string;
BEGIN
    var_in_string := replace(var_in_string,'''','''''');
    RETURN var_in_string ;

END SQL_UNESCAPE; -- Function SQL_UNESCAPE

-------------------------------------------------------------------------------------------

Function         XML_ESCAPE
  ( in_string IN varchar2)

  RETURN  varchar2 IS
var_in_string varchar2(4000) := in_string;
BEGIN
    var_in_string := replace(var_in_string,'',';');
    var_in_string := replace(var_in_string,',','chr(44)');
    RETURN var_in_string ;

END XML_ESCAPE; -- Function XML_ESCAPE

-------------------------------------------------------------------------------------------

Function         XML_UNESCAPE
  ( in_string IN varchar2)

  RETURN  varchar2 IS
var_in_string varchar2(4000) := in_string;
BEGIN
    var_in_string := replace(var_in_string,';','');
    var_in_string := replace(var_in_string,'chr(44)',',');
    RETURN var_in_string ;

END XML_UNESCAPE; -- Function XML_ESCAPE

-------------------------------------------------------------------------------------------

Function         SPLIT
    (   in_string IN varchar2,
        delimiter IN varchar2)


RETURN  STRING_UTILS.tbl_varchar2 IS

/*
Name:               SPLIT
Description:        This function splits an input string 'in_string'
                    and splits it on a 'delimeter' string.
Input Parameters:   The input string 'in_string' and the delimeter string 'delimiter'.
Return Value:       The table of split values.
Created By:         Rizwan Qazi (Netpace Inc.)
Created On:         11/29/2000
*/

    v_rc        varchar2(2000);
        aID             VARCHAR2(2000):= in_string ;
        posID           NUMBER;
        biID            BINARY_INTEGER := 1;
    tempvar      varchar2(2000);
        flag            boolean := TRUE;
    ret_table   STRING_UTILS.tbl_varchar2;

BEGIN
   IF aID is not null then
          IF (aID = delimiter) THEN  -- if both are equal then just assign zero length strings as the first and
            ret_table(1) := '';      -- second values of the return table  */
            ret_table(2) := '';
            RETURN ret_table;
          END IF;
          IF (instr(aID,delimiter,1,1) = 0) THEN
            ret_table(1) := aID;
            ret_table(2) := '';
            RETURN ret_table;
          END IF;
          posID :=instr(aID,delimiter,1,1);
          IF  (posID = LENGTH(aID)) THEN  -- if aID = '5|' and delimeter = '|'
                ret_table(1) := substr(aID,1, posID-1);
                ret_table(2) := '';
                RETURN ret_table;
          END IF;
          IF (posID = 1 and instr(aID,delimiter,2,1) = 0) THEN
                ret_table(1) := '';
                ret_table(2) := substr(aID,2);
                RETURN ret_table;
          END IF;

   END IF;
   WHILE (flag = TRUE)
        LOOP
        IF aID is not null then
              IF (aID = delimiter) THEN
                  ret_table(1) := '';
                  ret_table(2) := '';
                  EXIT;
              END IF;
                  posID :=instr(aID,delimiter,1,1);
                  IF posID > 0 THEN
                        ret_table(biID) := substr(aID,1,posID-1);
                    tempvar := substr(aID,posID+1);
                if tempvar is not null then
                             aID := tempvar;
                             biID := biID+1;
                end if;
                  END IF;

              IF posID = 0 then
                        flag := FALSE ;
                        ret_table(biID) := aID;
                  END IF;

        END IF;
        END LOOP;


--RETURN ret_table(1);
RETURN ret_table;

END SPLIT;

-------------------------------------------------------------------------------------------

Function         SPLIT_STANDARD
    (   in_string IN varchar2,
        delimiter IN varchar2)


RETURN  DBMS_UTILITY.UNCL_ARRAY IS

/*
Name:               SPLIT
Description:        This function splits an input string 'in_string'
                    and splits it on a 'delimeter' string.
Input Parameters:   The input string 'in_string' and the delimeter string 'delimiter'.
Return Value:       The table of split values.
Created By:         Rizwan Qazi (Netpace Inc.)
Created On:         11/29/2000
*/

    v_rc        varchar2(2000);
        aID             VARCHAR2(2000):= in_string ;
        posID           NUMBER;
        biID            BINARY_INTEGER := 1;
    tempvar      varchar2(2000);
        flag            boolean := TRUE;
    ret_table  DBMS_UTILITY.UNCL_ARRAY;

BEGIN
   IF aID is not null then
          IF (aID = delimiter) THEN  -- if both are equal then just assign zero length strings as the first and
            ret_table(1) := '';      -- second values of the return table  */
            ret_table(2) := '';
            RETURN ret_table;
          END IF;
          IF (instr(aID,delimiter,1,1) = 0) THEN
            ret_table(1) := aID;
            ret_table(2) := '';
            RETURN ret_table;
          END IF;
          posID :=instr(aID,delimiter,1,1);
          IF  (posID = LENGTH(aID)) THEN  -- if aID = '5|' and delimeter = '|'
                ret_table(1) := substr(aID,1, posID-1);
                ret_table(2) := '';
                RETURN ret_table;
          END IF;
          IF (posID = 1 and instr(aID,delimiter,2,1) = 0) THEN
                ret_table(1) := '';
                ret_table(2) := substr(aID,2);
                RETURN ret_table;
          END IF;

   END IF;
   WHILE (flag = TRUE)
        LOOP
        IF aID is not null then
              IF (aID = delimiter) THEN
                  ret_table(1) := '';
                  ret_table(2) := '';
                  EXIT;
              END IF;
                  posID :=instr(aID,delimiter,1,1);
                  IF posID > 0 THEN
                        ret_table(biID) := substr(aID,1,posID-1);
                    tempvar := substr(aID,posID+1);
                if tempvar is not null then
                             aID := tempvar;
                             biID := biID+1;
                end if;
                  END IF;

              IF posID = 0 then
                        flag := FALSE ;
                        ret_table(biID) := aID;
                  END IF;

        END IF;
        END LOOP;


--RETURN ret_table(1);
RETURN ret_table;

END SPLIT_STANDARD;

-------------------------------------------------------------------------------------------

END STRING_UTILS;
/

