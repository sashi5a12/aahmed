package com.netpace.aims.ui;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;

import com.netpace.aims.model.reports.*;
import com.netpace.aims.util.StringFuncs;

/**
 * This class is responsible for displaying the US states list details.
 * @author Rizwan Qazi
 */

public class GetUSStatesList extends BodyTagSupport {


    public int doStartTag() throws JspException
    {


		HttpServletRequest  request  = (HttpServletRequest)  pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

		JspWriter out = pageContext.getOut();
		StringBuffer outBuffer = new StringBuffer();

		
		try {
			   outBuffer.append("<html:option value=\"AL\">ALABAMA</html:option>")
						.append("<html:option value=\"AK\">ALASKA</html:option>")
						.append("<html:option value=\"AS\">AMERICAN SAMOA</html:option>")
						.append("<html:option value=\"AZ\">ARIZONA</html:option>")
						.append("<html:option value=\"AR\">ARKANSAS</html:option>")
						.append("<html:option value=\"CA\">CALIFORNIA</html:option>")
						.append("<html:option value=\"CO\">COLORADO</html:option>")
						.append("<html:option value=\"CT\">CONNECTICUT</html:option>")
						.append("<html:option value=\"DE\">DELAWARE</html:option>")
						.append("<html:option value=\"DC\">DISTRICT OF COLUMBIA</html:option>")
						.append("<html:option value=\"FM\">FEDERATED STATES OF MICRONESIA</html:option>")
						.append("<html:option value=\"FL\">FLORIDA</html:option>")
						.append("<html:option value=\"GA\">GEORGIA</html:option>")
						.append("<html:option value=\"GU\">GUAM</html:option>")
						.append("<html:option value=\"HI\">HAWAII</html:option>")
						.append("<html:option value=\"ID\">IDAHO</html:option>")
						.append("<html:option value=\"IL\">ILLINOIS</html:option>")
						.append("<html:option value=\"IN\">INDIANA</html:option>")
						.append("<html:option value=\"IA\">IOWA</html:option>")
						.append("<html:option value=\"KS\">KANSAS</html:option>")
						.append("<html:option value=\"KY\">KENTUCKY</html:option>")
						.append("<html:option value=\"LA\">LOUISIANA</html:option>")
						.append("<html:option value=\"ME\">MAINE</html:option>")
						.append("<html:option value=\"MH\">MARSHALL ISLANDS</html:option>")
						.append("<html:option value=\"MD\">MARYLAND</html:option>")
						.append("<html:option value=\"MA\">MASSACHUSETTS</html:option>")
						.append("<html:option value=\"MI\">MICHIGAN</html:option>")
						.append("<html:option value=\"MN\">MINNESOTA</html:option>")
						.append("<html:option value=\"MS\">MISSISSIPPI</html:option>")
						.append("<html:option value=\"MO\">MISSOURI</html:option>")
						.append("<html:option value=\"MT\">MONTANA</html:option>")
						.append("<html:option value=\"NE\">NEBRASKA</html:option>")
						.append("<html:option value=\"NV\">NEVADA</html:option>")
						.append("<html:option value=\"NH\">NEW HAMPSHIRE</html:option>")
						.append("<html:option value=\"NJ\">NEW JERSEY</html:option>")
						.append("<html:option value=\"NM\">NEW MEXICO</html:option>")
						.append("<html:option value=\"NY\">NEW YORK</html:option>")
						.append("<html:option value=\"NC\">NORTH CAROLINA</html:option>")
						.append("<html:option value=\"ND\">NORTH DAKOTA</html:option>")
						.append("<html:option value=\"MP\">NORTHERN MARIANA ISLANDS</html:option>")
						.append("<html:option value=\"OH\">OHIO</html:option>")
						.append("<html:option value=\"OK\">OKLAHOMA</html:option>")
						.append("<html:option value=\"OR\">OREGON</html:option>")
						.append("<html:option value=\"PW\">PALAU</html:option>")
						.append("<html:option value=\"PA\">PENNSYLVANIA</html:option>")
						.append("<html:option value=\"PR\">PUERTO RICO</html:option>")
						.append("<html:option value=\"RI\">RHODE ISLAND</html:option>")
						.append("<html:option value=\"SC\">SOUTH CAROLINA</html:option>")
						.append("<html:option value=\"SD\">SOUTH DAKOTA</html:option>")
						.append("<html:option value=\"TN\">TENNESSEE</html:option>")
						.append("<html:option value=\"TX\">TEXAS</html:option>")
						.append("<html:option value=\"UT\">UTAH</html:option>")
						.append("<html:option value=\"VT\">VERMONT</html:option>")
						.append("<html:option value=\"VI\">VIRGIN ISLANDS</html:option>")
						.append("<html:option value=\"VA\">VIRGINIA</html:option>")
						.append("<html:option value=\"WA\">WASHINGTON</html:option>")
						.append("<html:option value=\"WV\">WEST VIRGINIA</html:option>")
						.append("<html:option value=\"WI\">WISCONSIN</html:option>")
						.append("<html:option value=\"WY\">WYOMING</html:option>");

				out.println(outBuffer.toString());

			}

		 catch (IOException ioExc)
			{
				ioExc.printStackTrace(System.out);
				throw new JspException(ioExc.toString());
			}
/*
		  catch (Exception Exc)
			{
				Exc.printStackTrace(System.out);
				throw new JspException(Exc.toString());
			}
*/
			return SKIP_BODY;
    }


	public int doEndTag()
	{
		return EVAL_PAGE;
	}


	public void release()
	{				
		super.release();
	}

}