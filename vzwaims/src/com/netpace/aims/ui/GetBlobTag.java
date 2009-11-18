package com.netpace.aims.ui;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;


import com.netpace.aims.util.*;
import com.netpace.aims.bo.core.*;

/**
 * This class is responsible for displaying the Blob file.
 * @author Rizwan Qazi
 */

public class GetBlobTag extends BodyTagSupport {

    protected String tableName;
	protected String colName;
	protected String pkCol;
	protected String pkId;

    public void setTableName(String tableName){
      this.tableName = tableName;
    }

    public String getTableName(){
      return this.tableName;
    }

    public void setColName(String colName){
      this.colName = colName;
    }

    public String getColName(){
      return this.colName;
    }

    public void setPkCol(String pkCol){
      this.pkCol = pkCol;
    }

    public String getPkCol(){
      return this.pkCol;
    }

    public void setPkId(String pkId){
      this.pkId = pkId;
    }

    public String getPkId(){
      return this.pkId;
    }

    public int doStartTag() throws JspException
    {


		HttpServletRequest  request  = (HttpServletRequest)  pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

		InputStream inputStream = null;
		OutputStream outputStream = null;

		//JspWriter out = pageContext.getOut();
		StringBuffer outBuffer = new StringBuffer();
		
		try {
				outputStream = response.getOutputStream();

				Object [] resourceValues =  AllianceLobManager.getResource(tableName, colName, pkCol, pkId);

				inputStream = ((Blob)resourceValues[0]).getBinaryStream();
				//response.setHeader("Content-Disposition", "filename=" + (String)resourceValues[1]);
				response.setContentType((String)resourceValues[2]);
				//response.setContentLength((int)((Blob)resourceValues[0]).length());  
						
				if (inputStream != null)
				{
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
						outputStream.write (buffer, 0, bytesRead);
						
					}
					inputStream.close();
				}
				
					
				//out.println(outBuffer.toString());
				outputStream.flush();
				outputStream.close();	
				

			}

		 catch (IOException ioExc)
			{
				ioExc.printStackTrace(System.out);
				throw new JspException(ioExc.toString());
			}

		  catch (Exception Exc)
			{
				Exc.printStackTrace(System.out);
				throw new JspException(Exc.toString());
			}

			return SKIP_BODY;
    }


	public int doEndTag()
	{
		return EVAL_PAGE;
	}


	public void release()
	{		
		tableName = null;
		colName = null;
		pkId = null;
		super.release();
	}

}