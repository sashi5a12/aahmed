SPOOL D:\RESULT.LOG
@D:\rollback_script.sql
exec dbms_utility.compile_schema('AIMS');
SPOOL OFF
