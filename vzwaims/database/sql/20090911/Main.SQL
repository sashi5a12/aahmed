SPOOL D:\RESULT.LOG
@D:\script.sql
exec dbms_utility.compile_schema('AIMS');
SPOOL OFF
