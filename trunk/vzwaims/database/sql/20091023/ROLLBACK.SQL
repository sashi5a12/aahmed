SPOOL C:\RESULT.LOG
@C:\rollback_script.sql
exec dbms_utility.compile_schema('AIMS');
SPOOL OFF
