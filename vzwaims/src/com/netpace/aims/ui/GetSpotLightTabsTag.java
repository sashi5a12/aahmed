package com.netpace.aims.ui;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;

import com.netpace.aims.util.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.model.core.*;

import com.netpace.aims.util.*;

import org.apache.log4j.Logger;

/**
 * This class is responsible for displaying the tabs for the Spotlights page.
 * @author Rizwan Qazi
 */

public class GetSpotLightTabsTag extends BodyTagSupport {

	static Logger log = Logger.getLogger(GetSpotLightTabsTag.class.getName());
	 
    protected String attributeName;
    protected String allianceId;

    public void setAttributeName(String attributeName){
      this.attributeName = attributeName;
    }

    public String getAttributeName(){
      return this.attributeName;
    }

    public java.lang.String getAllianceId() {
        return this.allianceId;
    }

    public void setAllianceId(java.lang.String allianceId) {
        this.allianceId = allianceId;
    }


    public int doStartTag() throws JspException
    {


		HttpServletRequest  request  = (HttpServletRequest)  pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		
        String user_type = user.getUserType();

		AimsSpotlightType spotlight = null;
				
		Collection spotLightTypes = (Collection) request.getAttribute("AimsSpotlightTypes");
		
		JspWriter out = pageContext.getOut();
		StringBuffer outBuffer = new StringBuffer();

		int spotlightTypeId = StringFuncs.initializeIntGetParam(getAttributeName(), 1);
		int selectedSpotlightTypeId = 0;
		int cnt = 0;
        String spotlightImageName = null;

		
		try {
	
					
			for (Iterator iter = spotLightTypes.iterator(); iter.hasNext();) 
			{
					cnt++;
					spotlight = (AimsSpotlightType) iter.next();	
					selectedSpotlightTypeId = Integer.parseInt(spotlight.getSpotlightTypeId().toString());
                    spotlightImageName = spotlight.getImageName();

					if (cnt == 1)
					{
						outBuffer.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"1\" width=\"100%\">")
								 .append("<tr bgcolor=\"FFFFFF\" >")
                        		 .append("<td	valign=\"middle\"	colspan=2	class=\"totalbottomcopy\">")
                                 .append("<img src=\"images/space.gif\"	width=67 height=25 alt=\"\"	name=\"icontext\">")
                                 .append("</td>")
                                 .append("<td>")
                                 .append("<img src=\"images/left_arrow.gif\" width=12	height=36>")
                                 .append("</td>");
					}	

					if (selectedSpotlightTypeId == spotlightTypeId)		
					{
						outBuffer.append("<td  align=\"center\" valign=\"middle\" onMouseOver=\"changeImages('").append(spotlightImageName).append("', 'images/").append(spotlightImageName).append("-over.gif'); changeImages('icontext',	'images/").append(spotlightImageName).append("_doctype.gif'); return true;\" ")
                                 .append(" onMouseOut=\"changeImages('icontext', 'images/space.gif');	return true;\"  > ")
								 .append("<a class=\"standardSmallLink\" href=\"")
								 .append("/aims/allianceSpotlights.do?task=view&spotLightTypeId=")
								 .append(selectedSpotlightTypeId);
                        if (user_type.equalsIgnoreCase("V"))
                            {		
                        outBuffer.append("&alliance_id=")
                                 .append(getAllianceId());
                            } 
						outBuffer.append("\" >")
                                 .append("<img name=\"").append(spotlightImageName).append("\" src=\"images/")
                                 .append(spotlightImageName).append("-over.gif\"	width=27 height=27 border=0/>")							
								 .append("</a>")								
								 .append("</td>");

					}
					else
					{		
						
						outBuffer.append("<td  align=\"center\" valign=\"middle\" onMouseOver=\"changeImages('").append(spotlightImageName).append("', 'images/").append(spotlightImageName).append("-over.gif'); changeImages('icontext',	'images/").append(spotlightImageName).append("_doctype.gif'); return true;\" ")
                                 .append(" onMouseOut=\"changeImages('").append(spotlightImageName).append("',	'images/").append(spotlightImageName).append(".gif');	changeImages('icontext', 'images/space.gif');	return true;\"  > ")
								 .append("<a class=\"standardSmallLink\" href=\"")
								 .append("/aims/allianceSpotlights.do?task=view&spotLightTypeId=")
								 .append(selectedSpotlightTypeId);
                        if (user_type.equalsIgnoreCase("V"))
                            {		
                        outBuffer.append("&alliance_id=")
                                 .append(getAllianceId());
                            } 
						outBuffer.append("\" >")	
                                 .append("<img name=\"").append(spotlightImageName).append("\" src=\"images/")
                                 .append(spotlightImageName).append(".gif\"	width=27 height=27 border=0/>")
								 .append("</a>")	
								 .append("</td>");
					}	
			}

			if (cnt > 0)
			{
				outBuffer.append("</tr>")
						 .append("</table>");

			}
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
		attributeName = null;
		super.release();
	}

}