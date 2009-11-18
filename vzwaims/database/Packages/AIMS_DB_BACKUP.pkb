CREATE OR REPLACE Package Body AIMS_DB_BACKUP
IS

    g_db_size varchar2(50);
    g_crlf    char(2) default chr(13)||chr(10);

/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION get_db_size
         RETURN varchar2
    IS

    v_db_size varchar2(50);

    BEGIN

        SELECT
            to_char(total_size_gb, 'FM00.00') || ' GB' total_size_gb
        INTO
            v_db_size
        FROM (
            SELECT
                tablespace_name,
                Sum(bytes)/1024/1024/1024 AS total_size_gb
            FROM
                user_segments
            GROUP BY
                rollup(tablespace_name)
            ORDER BY 2 DESC )
        WHERE ROWNUM = 1;

    RETURN v_db_size;

    END get_db_size;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE insert_db_size
    IS
    BEGIN

        INSERT INTO aims_db_size(db_date, db_size)
            VALUES(sysdate, g_db_size);

        commit;

    END insert_db_size;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE send_email
         (
            p_status                 IN  varchar2         -- 'Y' success 'N' failure
          )
    IS

        a_to    mail_pkg.array   := mail_pkg.array();
        a_cc    mail_pkg.array   := mail_pkg.array();
        a_bcc   mail_pkg.array   := mail_pkg.array();
        v_status varchar2(10);

    BEGIN

      if (p_status = 'Y') then
        v_status := 'Success';
      else
        v_status := 'Failure';
      end if;

      insert_db_size;

      a_to.extend;
      a_to(1) := 'rqazi@netpace.com';
      a_to.extend;
      a_to(2) := 'rq@vtext.com';
      mail_pkg.send(
                    --'rqazi@netpace.com',
                    'rizwanqazi@sbcglobal.net',
                    'rqazi@netpace.com',
                    a_to,
                    a_cc,
                    a_bcc,
                    'AIMS Export - ' || v_status || ' - DB Size: ' || g_db_size,
                    ' '
                    --'AIMS Export Backup Completed.' || g_crlf || 'Status: ' || v_status
                  );


    END send_email;

/* -------------------------------------------------------------------------------------------------------------  */
BEGIN

    g_db_size := get_db_size();

END AIMS_DB_BACKUP; -- Package Body AIMS_DB_BACKUP
/

