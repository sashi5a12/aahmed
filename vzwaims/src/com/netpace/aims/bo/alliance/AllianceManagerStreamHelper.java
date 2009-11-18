package com.netpace.aims.bo.alliance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import net.sf.hibernate.Session;
 
/**
 * This class is responsible for getting the BO for alliances.
 * It has static methods for getting, updating, deleting the alliances.
 * @author Rizwan Qazi
 */

public class AllianceManagerStreamHelper
{


 /**
   *  This static method gets the industry focus from an array on Ind Focus ids.
   */
  public static Object [] getMiscImage (String imageName, String user_type) 
  									
  { 	
    Session session = null;	
	Collection collection = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	Object [] resourceValues = new Object[3];

    try
    { 	
		
			
        DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());

        Connection conn = DriverManager.getConnection
             ("jdbc:oracle:thin:@localhost:1521:rizwan", "aims", "aims");
                             // @machineName:port:SID,   userid,  password

        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("select image_file, image_name, image_content_type from AIMS_MISC_IMAGES where image_name = 'not-available.jpg'");
        while (rset.next())
        {
        	resourceValues[0] = rset.getBlob(1);
        	resourceValues[1] = rset.getString(2);
        	resourceValues[2] = rset.getString(3);
        }
        stmt.close();
		      
	}
	catch(SQLException e)
	{
		e.printStackTrace();
		
	}

    return resourceValues;
  } 
  
}  