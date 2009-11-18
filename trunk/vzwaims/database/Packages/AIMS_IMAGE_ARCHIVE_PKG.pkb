CREATE OR REPLACE PACKAGE BODY AIMS_IMAGE_ARCHIVE_PKG
IS


/* -------------------------------------------------------------------------------------------------------------  */

    FUNCTION if_image_to_be_inserted
         (
            p_image_name              varchar2,     -- name of the image
            p_table_name              varchar2,     -- table name where the image belongs
            p_col_name                varchar2,     -- column name of the above table
            p_pk_col_id               number,       -- primary key id egs. apps_id
            p_image_size              number        -- the size of the image
         )
         RETURN boolean
    IS


    /*
    || Overview:        Checks:
                        1) If the image is a placeholder image (image_archived.jpg)
    ||                  2) If an image with the same name,size, tablename and column name already exists
    ||                  If so then the function returns true else false.
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 12-07-2004       rqazi           Created
    ||
    */

    result boolean := true;
    v_num  number;

    BEGIN
        if (trim(upper(p_image_name)) = 'IMAGE_ARCHIVED.JPG' ) then
            result := false;
            return result;
        end if;

        begin
            select
                1
            into
                v_num
            from
                aims_blobs
            where
                blob_pk_col_id = p_pk_col_id
                and blob_col_name = p_col_name
                and blob_tab_name = p_table_name
                and nvl(dbms_lob.getlength(blob_file), 0) = nvl(p_image_size, 0)
                and nvl(blob_file_name, 'NOVALUE') = nvl(p_image_name, 'NOVALUE');

                result := false;
                --dbms_output.put_line('Value of false \n');
        exception
            when no_data_found then
                --dbms_output.put_line('Value of true \n');
                result := true;
        end;

        return result;

    END if_image_to_be_inserted;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE backup_images
         (
            p_status              OUT  varchar2     -- status of archive process 'Y' success  'N' failure
         )
    IS

    /*
    || Overview:        Backs up the "old" images
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 12-01-2004       rqazi           Created
    ||
    || phase_id :
    ||              11	SUNSET
    ||              9	REJECTED
    || created_date:
    ||              trunc(sysdate) - 540  (540 days from today)
    */

        sql_insert      varchar2(32767);
        sql_update      varchar2(32767);
        sql_criteria    varchar2(1000);
        pk_id_col_name  varchar2(30);
        v_cnt           pls_integer := 0;
        g_crlf          char(2) default chr(13)||chr(10);



    BEGIN
                execute immediate 'truncate table aims_blobs_bk';

                p_status :=  'N';

                sql_criteria  := sql_criteria || '              select ' || g_crlf;
                sql_criteria  := sql_criteria || '                  apps_id ' || g_crlf;
                sql_criteria  := sql_criteria || '              from ' || g_crlf;
                sql_criteria  := sql_criteria || '                  aims_apps ' || g_crlf;
                sql_criteria  := sql_criteria || '              where ' || g_crlf;
                sql_criteria  := sql_criteria || '                  phase_id IN (11)  ' || g_crlf;

                -- rqazi 02-25-2005 03:05 pm - changed the criteria after talking to cedric
                --sql_criteria  := sql_criteria || '                  created_date < trunc(sysdate) - 540  ' || g_crlf;
                --sql_criteria  := sql_criteria || '                  OR phase_id IN (11, 9)  ' || g_crlf;

            for c in (select
                          lower(table_name) table_name,
                          lower(column_name) column_name
                      from
                          user_tab_columns
                      where
                          table_name IN ('AIMS_APPS', 'AIMS_BREW_APPS', 'AIMS_WAP_APPS')
                          and data_type = 'BLOB'
                      order by
                          1,2
                     ) loop

                v_cnt := v_cnt + 1;

                if (upper(c.table_name) = 'AIMS_APPS' ) then
                    pk_id_col_name := 'APPS_ID';
                elsif (upper(c.table_name) = 'AIMS_BREW_APPS') then
                    pk_id_col_name := 'BREW_APPS_ID';
                elsif (upper(c.table_name) = 'AIMS_WAP_APPS') then
                    pk_id_col_name := 'WAP_APPS_ID';
                end if;

                sql_insert  := sql_insert || 'INSERT ' || g_crlf;
                sql_insert  := sql_insert || '  into aims_blobs_bk ' || g_crlf;
                sql_insert  := sql_insert || '( ' || g_crlf;
                sql_insert  := sql_insert || '          blob_id, ' || g_crlf;
                sql_insert  := sql_insert || '          blob_pk_col_id, ' || g_crlf;
                sql_insert  := sql_insert || '          blob_col_name, ' || g_crlf;
                sql_insert  := sql_insert || '          blob_tab_name, ' || g_crlf;
                sql_insert  := sql_insert || '          blob_file, ' || g_crlf;
                sql_insert  := sql_insert || '          blob_file_name, ' || g_crlf;
                sql_insert  := sql_insert || '          blob_content_type, ' || g_crlf;
                sql_insert  := sql_insert || '          created_date, ' || g_crlf;
                sql_insert  := sql_insert || '          created_by ' || g_crlf;
                sql_insert  := sql_insert || ' ) ' || g_crlf;
                sql_insert  := sql_insert || 'SELECT  ' || g_crlf;
                sql_insert  := sql_insert || '          seq_pk_blobs_bk.NEXTVAL, ' || g_crlf;
                sql_insert  := sql_insert ||            pk_id_col_name  ||  ', ' || g_crlf;
                sql_insert  := sql_insert || '''' ||    c.column_name  || '''' ||  ', ' || g_crlf;
                sql_insert  := sql_insert || '''' ||    c.table_name || '''' ||  ', ' || g_crlf;
                sql_insert  := sql_insert ||            c.column_name  ||  ', ' || g_crlf;
                sql_insert  := sql_insert ||            c.column_name  ||  '_file_name, ' || g_crlf;
                sql_insert  := sql_insert ||            c.column_name  ||  '_content_type, ' || g_crlf;
                sql_insert  := sql_insert || '          sysdate , ' || g_crlf;
                sql_insert  := sql_insert || '          ''system'' ' || g_crlf;
                sql_insert  := sql_insert || 'FROM  ' || g_crlf;
                sql_insert  := sql_insert ||            c.table_name || ' ' || g_crlf;
                sql_insert  := sql_insert || 'WHERE ' || g_crlf;
                sql_insert  := sql_insert || '          NVL(dbms_lob.getlength(' || c.column_name || '), 0) > 0 ' ;
                sql_insert  := sql_insert || '          AND ' ||  pk_id_col_name || ' IN ( '  || g_crlf;
                sql_insert  := sql_insert || sql_criteria;
                sql_insert  := sql_insert || '                                  ) '  || g_crlf;

                --p(v_cnt || ': ' || sql_insert);

                execute immediate sql_insert;
                sql_insert := '';

                sql_update  := sql_update || 'UPDATE  ' || g_crlf;
                sql_update  := sql_update ||            c.table_name || ' ' || g_crlf;
                sql_update  := sql_update || 'SET  ( ' || g_crlf;
                sql_update  := sql_update ||            c.column_name  ||  ', ' || g_crlf;
                sql_update  := sql_update ||            c.column_name  ||  '_file_name, ' || g_crlf;
                sql_update  := sql_update ||            c.column_name  ||  '_content_type ) = ' || g_crlf;
                sql_update  := sql_update ||         '  (SELECT image_file, image_name, image_content_type FROM aims_misc_images ' || g_crlf;
                sql_update  := sql_update ||         '  WHERE image_name= ''image_archived.jpg'' ) ' || g_crlf;
                sql_update  := sql_update || 'WHERE ' || g_crlf;
                sql_update  := sql_update || '          NVL(dbms_lob.getlength(' || c.column_name || '), 0) > 0 ' ;
                sql_update  := sql_update || '          AND ' ||  pk_id_col_name || ' IN ( '  || g_crlf;
                sql_update  := sql_update || sql_criteria;
                sql_update  := sql_update || '                                  ) '  || g_crlf;

                --p(v_cnt || ': ' || sql_update);

                execute immediate sql_update;

                sql_update := '';

            end loop;

            p_status :=  'Y';
            /*
    EXCEPTION
        WHEN OTHERS THEN
            p_status :=  'N';
            raise;  */
    END backup_images;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE archive_images
         (
            p_status              OUT  varchar2     -- status of archive process 'Y' success  'N' failure
         )
    IS

    /*
    || Overview:        Archives the "old" images. It picks up the image from the backup table and inserts
    ||                  into the main table.
    ||                  Before inserting it checks whether
    ||                  1) If the image is a placeholder image (image_archived.jpg)
    ||                  2) If an image with the same name,size, tablename and column name already exists
    ||                  If so then the image is not inserted. Otherwise the image is inserted.
    ||
    ||                  After inserting the images the backup table is truncated.
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 12-07-2004       rqazi           Created
    ||
    */



    BEGIN

        p_status := 'N';

        for c in (select * from aims_blobs_bk) loop
            if (
                if_image_to_be_inserted(c.blob_file_name,
                                        c.blob_tab_name,
                                        c.blob_col_name,
                                        c.blob_pk_col_id,
                                        nvl(dbms_lob.getlength(c.blob_file), 0)
                                       )
            ) then

                    insert
                        into aims_blobs
                        (
                            blob_id,
                            blob_pk_col_id,
                            blob_col_name,
                            blob_tab_name,
                            blob_file,
                            blob_file_name,
                            blob_content_type,
                            created_date,
                            created_by
                        )
                    values
                        (
                            seq_pk_blobs.NEXTVAL,
                            c.blob_pk_col_id,
                            c.blob_col_name,
                            c.blob_tab_name,
                            c.blob_file,
                            c.blob_file_name,
                            c.blob_content_type,
                            c.created_date,
                            c.created_by
                        );
            end if;
        end loop;


        p_status := 'Y';
    EXCEPTION
        WHEN OTHERS THEN
            p_status := 'N';
            rollback;
            raise;
    END archive_images;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_IMAGE_ARCHIVE_PKG; -- Package Body AIMS_IMAGE_ARCHIVE_PKG
/

