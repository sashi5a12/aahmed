CREATE OR REPLACE PACKAGE parse AS

/*
|| Package of utility procedures for parsing delimited or fixed position strings into tables
|| of individual values, and vice versa.
*/

TYPE varchar2_table IS TABLE OF VARCHAR2(32767) INDEX BY BINARY_INTEGER;
TYPE integer_table IS TABLE OF PLS_INTEGER INDEX BY BINARY_INTEGER;

PROCEDURE delimstring_to_table
( p_delimstring IN VARCHAR2
, p_table OUT DBMS_UTILITY.UNCL_ARRAY
, p_nfields OUT INTEGER
, p_delim IN VARCHAR2 DEFAULT ','
);

    
/* 
 || If strings are greater than 227 characters,so delimlongstring_to_table will be used 
 || instead of delimstring_to_table. 
*/

PROCEDURE delimlongstring_to_table
( p_delimstring IN VARCHAR2
, p_table OUT DBMS_UTILITY.LNAME_ARRAY
, p_nfields OUT INTEGER
, p_delim IN VARCHAR2 DEFAULT ','
);

PROCEDURE table_to_delimstring
( p_table IN DBMS_UTILITY.UNCL_ARRAY
, p_delimstring OUT VARCHAR2
, p_delim IN VARCHAR2 DEFAULT ','
);

PROCEDURE fixedstring_to_table
( p_fixedstring IN VARCHAR2
, p_table OUT DBMS_UTILITY.UNCL_ARRAY
, p_postab IN integer_table
, p_lentab IN integer_table
);

PROCEDURE table_to_fixedstring
( p_table IN DBMS_UTILITY.UNCL_ARRAY
, p_fixedstring OUT VARCHAR2
, p_postab IN integer_table
, p_lentab IN integer_table
);

END parse;
/

