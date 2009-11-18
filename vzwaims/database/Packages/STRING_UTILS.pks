CREATE OR REPLACE PACKAGE string_utils
  IS

TYPE tbl_varchar2 IS TABLE OF varchar2(4000)
   INDEX BY BINARY_INTEGER;

Function RET_SQL_STMT
( tbl_status IN DBMS_UTILITY.UNCL_ARRAY)
RETURN  varchar2;
PRAGMA restrict_references( RET_SQL_STMT, wnds, rnds, wnps, rnps );

Function CHECK_NUMBER
  ( word IN varchar2)
  RETURN  varchar2;
PRAGMA restrict_references( CHECK_NUMBER, wnds, rnds, wnps, rnps );

Function         TIME_STRING
  RETURN  varchar2;
PRAGMA restrict_references( TIME_STRING, wnds, wnps, rnps);

Function         TRIM_CRLF
  ( in_string varchar2)
  RETURN  varchar2;
PRAGMA restrict_references( TRIM_CRLF, wnds, rnds, wnps, rnps );

Function         ESCAPE_JS
  ( in_string IN varchar2)
  RETURN  varchar2;
PRAGMA restrict_references( ESCAPE_JS, wnds, rnds, wnps, rnps );

Function         UNESCAPE_JS
  ( in_string IN varchar2)
  RETURN  varchar2;
PRAGMA restrict_references( UNESCAPE_JS, wnds, rnds, wnps, rnps );

Function         SQL_ESCAPE
  ( in_string IN varchar2)
  RETURN  varchar2;
PRAGMA restrict_references( SQL_ESCAPE, wnds, rnds, wnps, rnps );

  Function         SQL_UNESCAPE
  ( in_string IN varchar2)
  RETURN  varchar2;
PRAGMA restrict_references( SQL_UNESCAPE, wnds, rnds, wnps, rnps );



Function         XML_ESCAPE
  ( in_string IN varchar2)
  RETURN  varchar2;
PRAGMA restrict_references( XML_ESCAPE, wnds, rnds, wnps, rnps );

Function         XML_UNESCAPE
  ( in_string IN varchar2)
  RETURN  varchar2;
PRAGMA restrict_references( XML_UNESCAPE, wnds, rnds, wnps, rnps );

Function         SPLIT
    (   in_string IN varchar2,
        delimiter IN varchar2)
  RETURN  STRING_UTILS.tbl_varchar2;
PRAGMA restrict_references( SPLIT, wnds, rnds, wnps, rnps );

Function         SPLIT_STANDARD
    (   in_string IN varchar2,
        delimiter IN varchar2)
RETURN  DBMS_UTILITY.UNCL_ARRAY;
PRAGMA restrict_references( SPLIT_STANDARD, wnds, rnds, wnps, rnps );

END STRING_UTILS; -- Package spec
/

