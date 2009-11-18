package com.netpace.aims.util;

// file LobUtils.java
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

public class LobUtils
{
    public static long writeToOraBlob(Blob blob, InputStream in) throws SQLException, IOException
    {
        BLOB oblob = (BLOB) blob;
        OutputStream out = oblob.getBinaryOutputStream();
        int length = -1;
        long wrote = 0;
        int chunkSize = oblob.getChunkSize();
        byte[] buf = new byte[chunkSize];
        while ((length = in.read(buf)) != -1)
        {
            out.write(buf, 0, length);
            wrote += length;
        }
        out.close();
        in.close();
        return wrote;
    }

    public static long writeToOraClob(Clob clob, InputStream in) throws SQLException, IOException
    {
        CLOB oclob = (CLOB) clob;
        OutputStream out = oclob.getAsciiOutputStream();
        int length = -1;
        long wrote = 0;
        int chunkSize = oclob.getChunkSize();
        byte[] buf = new byte[chunkSize];
        while ((length = in.read(buf)) != -1)
        {
            out.write(buf, 0, length);
            wrote += length;
        }
        out.close();
        in.close();
        return wrote;
    }

    /**
     * This method uses character stream to preserve special characters
     * @param clob
     * @param in
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static long writeToOraClobWithCharEncoding(Clob clob, InputStream in) throws SQLException, IOException
    {
        CLOB oclob = null;
        Writer outWriter = null;
        InputStreamReader isReader = null;
        int length = -1;
        long wrote = 0;
        int chunkSize = -1;
        char[] cBuf = null;
        try
        {
            oclob = (CLOB) clob;
            outWriter = oclob.getCharacterOutputStream();
            isReader = new InputStreamReader(in);

            chunkSize = oclob.getChunkSize();
            cBuf = new char[chunkSize];

            while ((length = isReader.read(cBuf)) != -1)
            {
                outWriter.write(cBuf, 0, length);
                wrote += length;
            }
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            throw sqle;
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            throw ioe;
        }
        finally
        {
            if(outWriter != null)
            {
                outWriter.close();
            }
            if(isReader != null)
            {
                isReader.close();
            }
            if(in != null)
            {
                in.close();
            }
        }
        return wrote;
    }//writeToOraClobWithCharEncoding
    
    public static long writeToOraClob(Clob clob, String data) throws SQLException, IOException
    {
        CLOB oclob = (CLOB) clob;
        OutputStream out = null;
        InputStream in =null;
        int length = -1;
        long wrote = 0;
        try {
            out = oclob.getAsciiOutputStream();
            in =new ByteArrayInputStream(data.getBytes());        	
	        int chunkSize = oclob.getChunkSize();
	        byte[] buf = new byte[chunkSize];
	        while ((length = in.read(buf)) != -1)
	        {
	            out.write(buf, 0, length);
	            wrote += length;
	        }
        }
        catch(SQLException e){
        	throw e;
        }
        catch(IOException e){
        	throw e;
        }
        finally {
        	if (out != null){
        		out.close();
        	}
        	if (in != null){
        		in.close();
        	}
        }
        return wrote;
    }
    public static String clobToString(Clob clob) throws SQLException, IOException
    {
        if (clob == null)
            return "";

        StringBuffer strOut = new StringBuffer();
        BufferedReader in = new BufferedReader(clob.getCharacterStream());

        try
        {
            char[] buf = new char[256];
            int n = 0;
            while (-1 != (n = in.read(buf)))
            {
                strOut.append(buf, 0, n);
            }
        }
        catch (IOException ex)
        {
            throw ex;
        }
        finally
        {
            if (in != null)
                in.close();
        }

        return strOut.toString();
    }
    /*
    
    final static int bBufLen = 4 * 8192;
    String query;
    String connectString;
    String outFile;
    Connection conn;
    
    
    public LobUtils(String connectString, String query, String outFile)
    {
        this.connectString = connectString;
        this.query = query;
        this.outFile = outFile;
        this.conn = null;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException
    {
        if (args.length < 5)
            usage();
        int ii = 0;
        String connString = args[ii++];
        String queryFile = null;
        String outputFile = null;
    
        boolean read = true;
        boolean isBinary = false;
    
        for (; ii < args.length; ii++)
        {
            if (args[ii].equals("-write"))
                read = false;
    
            if (args[ii].equals("-blob"))
                isBinary = true;
    
            if (args[ii].equals("-qf") && ii < args.length - 1)
                queryFile = args[++ii];
    
            if (args[ii].equals("-lf") && ii < args.length - 1)
                outputFile = args[++ii];
    
        }
    
        if (queryFile == null || outputFile == null)
            usage();
    
        // all set
        if (read)
        {
            BufferedReader freader = new BufferedReader(new FileReader(queryFile));
            StringWriter swriter = new StringWriter();
            int bufLen = 1024;
            char[] cbuf = new char[bufLen];
            int length = -1;
            while ((length = freader.read(cbuf, 0, bufLen)) != -1)
            {
                swriter.write(cbuf, 0, length);
            }
            freader.close();
            swriter.close();
            String query = swriter.toString();
    
            LobUtils lutils = new LobUtils(connString, query, outputFile);
            if (isBinary)
            {
                Blob blob = lutils.getBlob();
                long wrote = lutils.writeBlobToFile(blob);
                System.out.println("Wrote " + wrote + " bytes to file " + outputFile);
            }
            else
            {
                Clob clob = lutils.getClob();
                long wrote = lutils.writeClobToFile(clob);
                System.out.println("Wrote " + wrote + " bytes to file " + outputFile);
            }
        }
        else
        {
            BufferedReader freader = new BufferedReader(new FileReader(queryFile));
            StringWriter swriter = new StringWriter();
            int bufLen = 1024;
            char[] cbuf = new char[bufLen];
            int length = -1;
            while ((length = freader.read(cbuf, 0, bufLen)) != -1)
            {
                swriter.write(cbuf, 0, length);
            }
            freader.close();
            swriter.close();
            String query = swriter.toString();
    
            LobUtils lutils = new LobUtils(connString, query, outputFile);
            Clob clob = lutils.getClob();
            InputStream creader = new FileInputStream(outputFile);
            long wrote = lutils.writeToOraClob(clob, creader);
            System.out.println("Wrote " + wrote + " bytes from file " + outputFile);
        }
    }
    
    
    public Clob getClob() throws SQLException
    {
        //conn = ConnUtil.getConnection(connectString); //Makda - could bot find ConnUtil
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Clob clob = null;
        if (rs.next())
        {
            clob = rs.getClob(1);
        }
        return clob;
    }
    
    public Blob getBlob() throws SQLException
    {
        //conn = ConnUtil.getConnection(connectString); //Makda - could bot find ConnUtil
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Blob blob = null;
        if (rs.next())
        {
            blob = rs.getBlob(1);
        }
        return blob;
    }
     
     
    public long writeClobToFile(Clob clob) throws IOException, SQLException
    {
        long wrote = 0;
        BufferedWriter fwriter = new BufferedWriter(new FileWriter(outFile));
        wrote = readFromClob(clob, fwriter);
        fwriter.close();
        conn.commit();
        conn.close();
        return wrote;
    }
    
    public long writeBlobToFile(Blob blob) throws IOException, SQLException
    {
        long wrote = 0;
        OutputStream fwriter = new FileOutputStream(outFile);
        wrote = readFromBlob(blob, fwriter);
        fwriter.close();
        conn.commit();
        conn.close();
        return wrote;
    }
    
    private static void usage()
    {
        System.err.println("Usage: java LobUtils user/passwd@sid [-write] [-blob] -qf query_file -lf lob_file");
        System.exit(1);
    }
    
    public static long readFromBlob(Blob blob, OutputStream out) throws SQLException, IOException
    {
        InputStream in = blob.getBinaryStream();
        int length = -1;
        long read = 0;
        byte[] buf = new byte[bBufLen];
        while ((length = in.read(buf)) != -1)
        {
            out.write(buf, 0, length);
            read += length;
        }
        in.close();
        return read;
    }
    
    public static long readFromClob(Clob clob, Writer out) throws SQLException, IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(clob.getAsciiStream()));
        int length = -1;
        long read = 0;
        char[] buf = new char[bBufLen];
        while ((length = in.read(buf, 0, bBufLen)) != -1)
        {
            out.write(buf, 0, length);
            read += length;
        }
        in.close();
        return read;
    }
    */
}