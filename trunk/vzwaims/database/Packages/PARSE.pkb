CREATE OR REPLACE PACKAGE BODY parse AS

    PROCEDURE delimstring_to_table

        ( p_delimstring IN VARCHAR2
        , p_table OUT DBMS_UTILITY.UNCL_ARRAY
        , p_nfields OUT INTEGER
        , p_delim IN VARCHAR2 DEFAULT ','
        )
    IS
        v_string VARCHAR2(32767) := p_delimstring;
        v_nfields PLS_INTEGER := 1;
        v_table DBMS_UTILITY.UNCL_ARRAY;
        v_delimpos PLS_INTEGER := INSTR(p_delimstring, p_delim);
        v_delimlen PLS_INTEGER := LENGTH(p_delim);

    BEGIN
        NULL;

        WHILE v_delimpos > 0
        LOOP
          v_table(v_nfields) := SUBSTR(v_string,1,v_delimpos-1);
          v_string := SUBSTR(v_string,v_delimpos+v_delimlen);
          v_nfields := v_nfields+1;
          v_delimpos := INSTR(v_string, p_delim);
        END LOOP;

        v_table(v_nfields) := v_string;
        p_table := v_table;
        p_nfields := v_nfields;

    END delimstring_to_table;

    PROCEDURE delimlongstring_to_table

        ( p_delimstring IN VARCHAR2
        , p_table OUT DBMS_UTILITY.LNAME_ARRAY
        , p_nfields OUT INTEGER
        , p_delim IN VARCHAR2 DEFAULT ','
        )
    IS
        v_string VARCHAR2(32767) := p_delimstring;
        v_nfields PLS_INTEGER := 1;
        v_table DBMS_UTILITY.LNAME_ARRAY;
        v_delimpos PLS_INTEGER := INSTR(p_delimstring, p_delim);
        v_delimlen PLS_INTEGER := LENGTH(p_delim);

    BEGIN
        NULL;

        WHILE v_delimpos > 0
        LOOP
          v_table(v_nfields) := SUBSTR(v_string,1,v_delimpos-1);
          v_string := SUBSTR(v_string,v_delimpos+v_delimlen);
          v_nfields := v_nfields+1;
          v_delimpos := INSTR(v_string, p_delim);
        END LOOP;

        v_table(v_nfields) := v_string;
        p_table := v_table;
        p_nfields := v_nfields;

    END delimlongstring_to_table;


    PROCEDURE table_to_delimstring

      ( p_table IN DBMS_UTILITY.UNCL_ARRAY
      , p_delimstring OUT VARCHAR2
      , p_delim IN VARCHAR2 DEFAULT ','
      )

    IS
        v_nfields PLS_INTEGER := p_table.COUNT;
        v_string VARCHAR2(32767);

    BEGIN

        FOR i IN 1..v_nfields
        LOOP
            v_string := v_string || p_table(i);
            IF i < v_nfields THEN
                v_string := v_string || p_delim;
            END IF;
        END LOOP;
        p_delimstring := v_string;

    END table_to_delimstring;


    PROCEDURE fixedstring_to_table
        ( p_fixedstring IN VARCHAR2
        , p_table OUT DBMS_UTILITY.UNCL_ARRAY
        , p_postab IN integer_table
        , p_lentab IN integer_table
        )
    IS
        v_nfields PLS_INTEGER := p_postab.COUNT;
    BEGIN

        FOR i IN 1..v_nfields
        LOOP
            p_table(i) := SUBSTR( p_fixedstring, p_postab(i), p_lentab(i) );
        END LOOP;

    END fixedstring_to_table;


    PROCEDURE table_to_fixedstring
        ( p_table IN DBMS_UTILITY.UNCL_ARRAY
        , p_fixedstring OUT VARCHAR2
        , p_postab IN integer_table
        , p_lentab IN integer_table
        )

    IS

    v_nfields PLS_INTEGER := p_postab.COUNT;
    v_string VARCHAR2(32767);
    v_length PLS_INTEGER := 0;

    BEGIN

      /* Determine total length of record */
      FOR i IN 1..v_nfields
      LOOP
        v_length := GREATEST( v_length, p_postab(i)+p_lentab(i)-1 );
      END LOOP;

      /* Create blank record of appropriate length */
      v_string := RPAD( ' ', v_length, ' ' );

      /* Fill blank record with the fields */
      FOR i IN 1..v_nfields
      LOOP
        v_string := SUBSTR( v_string, 1, p_postab(i)-1 )
        || RPAD( p_table(i), p_lentab(i) )
        || SUBSTR( v_string, p_postab(i)+p_lentab(i) );
      END LOOP;

      p_fixedstring := v_string;

    END table_to_fixedstring;

END parse;
/

