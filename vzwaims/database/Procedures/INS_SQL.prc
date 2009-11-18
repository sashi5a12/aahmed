CREATE OR REPLACE procedure ins_sql( p_sql in varchar2 )

    as
    pragma autonomous_transaction;

   begin

    insert into test_sql (test_id , sql_statement , dt ) values (TEST_SEQ.nextval, p_sql, sysdate);
    commit;

   end;
/

