package com.netpace.aims.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.masters.AimsSpotlightType;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class is responsible for displaying the tabs for the Tools page.
 * @author Rizwan Qazi
 */

public class GetToolsTabsTag extends BodyTagSupport
{

    static Logger log = Logger.getLogger(GetToolsTabsTag.class.getName());

    protected String attributeName;
    protected String typeName;

    public void setAttributeName(String attributeName)
    {
        this.attributeName = attributeName;
    }

    public String getAttributeName()
    {
        return this.attributeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getTypeName()
    {
        return this.typeName;
    }

    public int doStartTag() throws JspException
    {

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);

        String user_type = user.getUserType();
        Long alliance_id = null;

        if (user_type.equalsIgnoreCase("V"))
        {
            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
        }
        else
        {
            alliance_id = user.getAimsAllianc();
        }

        AimsSpotlightType spotlight = null;
        Collection allianceTools = null;

        try
        {
            allianceTools = AllianceManager.getAllianceTools(alliance_id, user_type);

        }

        catch (HibernateException he)
        {
            log.debug("A hibernate exception occured!");
        } catch (SQLException e) {
			log.error(e,e);
		}

        JspWriter out = pageContext.getOut();
        StringBuffer outBuffer = new StringBuffer();
        Object[] userValues = null;
        String toolName = null;
        String toolDesc = null;

        int cnt = 0;

        try
        {

            if (getTypeName().equalsIgnoreCase("tabs"))
            {

                for (Iterator iter = allianceTools.iterator(); iter.hasNext();)
                {
                    cnt++;
                    userValues = (Object[]) iter.next();
                    toolName = (String) userValues[1];
                    toolDesc = (String) userValues[2];
                    if (StringUtils.isEmpty(toolDesc)){
                    	toolDesc=StringFuncs.NullValueHTMLReplacement(toolDesc);
                    }
                    else {
                        toolDesc = StringUtils.replace(toolDesc, "'", "\\'");
                        toolDesc = StringUtils.replace(toolDesc, "\"", "&quot;");
                        toolDesc = StringUtils.replace(toolDesc, "\r\n", "\\r\\n");                    	
                    }

                    if (cnt == 1)
                    {
                        outBuffer.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">").append(
                            "<tr  >");
                    }

                    if ((!getAttributeName().equalsIgnoreCase(toolName)))
                    {
                        outBuffer.append("<td align=\"center\" valign=\"middle\" >").
                        append("<span onmouseover=\"return Tip('"+toolDesc+"')\">").
                        append("<a target='_blank' class=\"a\" href=\"").append((String) userValues[6]);
                        if ((user_type.equalsIgnoreCase("V")) && (toolName.equalsIgnoreCase("Spotlight Page")))
                        {
                            outBuffer.append("&alliance_id=").append(getAttributeName());
                        }
                        outBuffer.append("\" >").append(toolName).append("</a></span>").append("</td>");
                    }
                    else
                    {

                        outBuffer.append("<td align=\"center\" valign=\"middle\" >").
                        append("<span onmouseover=\"return Tip('"+toolDesc+"')\">").
                        append("<a target='_blank' class=\"a\" href=\"").append((String) userValues[6]);
                        if ((user_type.equalsIgnoreCase("V")) && (toolName.equalsIgnoreCase("Spotlight Page")))
                        {
                            outBuffer.append("&alliance_id=").append(getAttributeName());
                        }
                        outBuffer.append("\" >").append(toolName).append("</a></span>").append("</td>");
                    }

                }
                if (cnt > 0)
                {
                    outBuffer.append("</tr>").append("</table>");

                }
            }

            if (getTypeName().equalsIgnoreCase("bullets"))
            {

                for (Iterator iter = allianceTools.iterator(); iter.hasNext();)
                {
                    cnt++;
                    userValues = (Object[]) iter.next();
                    toolName = (String) userValues[1];
                    toolDesc = (String) userValues[2];
                    if (StringUtils.isEmpty(toolDesc)){
                    	toolDesc=StringFuncs.NullValueHTMLReplacement(toolDesc);
                    }
                    else {
                        toolDesc = StringUtils.replace(toolDesc, "'", "\\'");
                        toolDesc = StringUtils.replace(toolDesc, "\"", "&quot;");
                        toolDesc = StringUtils.replace(toolDesc, "\r\n", "\\r\\n");                    	
                    }

                    outBuffer.append("<span onmouseover=\"return Tip('"+toolDesc+"')\">");
                    outBuffer.append("<a target='_blank' class=\"a\" href=\"");

                    if ((userValues[6] != null) && (((String) userValues[6]).length() > 0))
                        outBuffer.append((String) userValues[6]);
                    else if ((userValues[5] != null) && (((String) userValues[5]).length() > 0))
                        outBuffer.append("toolsResource.do?toolResource=toolFile&toolId=").append(userValues[0]);
                    else
                        outBuffer.append((String) userValues[6]);

                    if ((user_type.equalsIgnoreCase("V")) && (toolName.equalsIgnoreCase("Spotlight Page")))
                    {
                        outBuffer.append("&alliance_id=").append(getAttributeName());
                    }

                    outBuffer.append("\" >").append(toolName) //.append((String)userValues[2])
                    .append("</a></span>").append("<br/>").append("<br/>");
                }

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