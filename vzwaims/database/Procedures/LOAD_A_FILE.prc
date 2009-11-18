CREATE OR REPLACE procedure load_a_file( p_dir_name in varchar2,
                       p_file_name in varchar2 )
as
    l_blob    blob;
    l_bfile   bfile;
begin
    -- First we must create a LOB in the database. We
    -- need an empty CLOB, BLOB, or a LOB created via the
    -- CREATE TEMPORARY API call to load into.

    insert into upload_files (file_id, file_name, file_contents) values ( blob_seq.nextval, p_file_name, empty_blob() )
    returning file_contents into l_Blob;

    -- Next, we open the BFILE we will load
    -- from.

    l_bfile := bfilename( p_dir_name, p_file_name );
    dbms_lob.fileopen( l_bfile );


    -- Then, we call LOADFROMFILE, loading the CLOB we
    -- just created with the entire contents of the BFILE
    -- we just opened.
    
    begin
    
    dbms_lob.loadfromfile( l_blob, l_bfile,
                           dbms_lob.getlength( l_bfile ) );
    exception
        when others then
            null;            
    end;

    -- Close out the BFILE we opened to avoid running
    -- out of file handles eventually.

    dbms_lob.fileclose( l_bfile );
end;
/

