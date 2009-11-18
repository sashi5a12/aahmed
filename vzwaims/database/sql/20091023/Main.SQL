SPOOL C:\RESULT.LOG
@C:\script.sql
exec dbms_utility.compile_schema('AIMS');
SPOOL OFF
