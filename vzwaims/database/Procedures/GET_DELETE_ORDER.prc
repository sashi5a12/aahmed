CREATE OR REPLACE PROCEDURE GET_DELETE_ORDER

    IS

    /*
    || Overview:        Gets the delete order of the tables.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 10-16-200e       rqazi           Created
    ||
    ||
    ||
    ||
    */

    cnt_temp number:= 0;
    cnt_all_tables number:= 0;


    BEGIN


    /*
        Psuedo code for generating table dependencies for deletion.

        1) Create a temp table called ALL_AIMS_TABLES - col table_name
                create table ALL_AIMS_TABLES (table_name varchar2(30))
                insert into all_aims_tables
                    select table_name from user_tables where table_name like 'AIMS_%'
        2) Create a temp table called DELETE_ORDER - cols table_name, del_seq
                create table DELETE_ORDER (del_seq number, table_name varchar2(30))
	3) Create a sequence

		CREATE SEQUENCE seq_all_aims_tables
		 INCREMENT BY 1
		 START WITH 1
		 MINVALUE 1
		 MAXVALUE 999999999999999999999999999
		 NOCYCLE
		 NOORDER
		 CACHE 20
		/
        4) Create a procedure
            a) Loop through the user_constraints, check if a primary key of the table is referenced as a foreign key in ALL_AIMS_TABLES.
            b) If NO then delete the table_name from ALL_AIMS_TABLES; and add the table_name
               in DELETE_ORDER table with seq 1..n
            c) After all tables have been scanned check the number of tables in ALL_AIMS_TABLES.
            d) If the count is greater than 1 recursively call the same procedure.
            e) If the count is 1 check if the table referencing the foreign key is the same table. If so
               insert this table in the DETELE_ORDER table and come out.
            f) If the count is 0 come out.
    */

        for c IN (select table_name, constraint_name from user_constraints where table_name like 'AIMS_%'
                                and constraint_type = 'P'
                                and table_name in (select table_name from all_aims_tables) order by 1) loop

            select
                count(*)
            into
                cnt_temp
            from
                all_aims_tables a,
                user_constraints u
            where
                a.table_name = u.table_name
                and u.r_constraint_name = c.constraint_name;

            if (cnt_temp > 0) then
                null;
            else
                delete from all_aims_tables where table_name = c.table_name;
                begin
                insert into delete_order(table_name, del_seq) values (c.table_name, seq_all_aims_tables.NEXTVAL);
                exception
                when dup_val_on_index then
                    null;
                end;
            end if;
                commit;
        end loop;

            select
                count(*)
            into
                cnt_all_tables
            from
                all_aims_tables;

            if (cnt_all_tables <= 5) then
                null;
            else
                GET_DELETE_ORDER;
            end if;
            commit;
    END GET_DELETE_ORDER;
/

