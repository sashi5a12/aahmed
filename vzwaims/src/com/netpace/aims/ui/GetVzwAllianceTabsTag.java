package com.netpace.aims.ui;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.util.StringFuncs;

/**
 * This class is responsible for displaying the tabs for the VZW Spotlights page.
 * @author Rizwan Qazi
 */

public class GetVzwAllianceTabsTag extends BodyTagSupport
{

    static Logger log = Logger.getLogger(GetVzwAllianceTabsTag.class.getName());

    protected String attributeName;
    protected String allianceId;

    public void setAttributeName(String attributeName)
    {
        this.attributeName = attributeName;
    }

    public String getAttributeName()
    {
        return this.attributeName;
    }

    public java.lang.String getAllianceId()
    {
        return this.allianceId;
    }

    public void setAllianceId(java.lang.String allianceId)
    {
        this.allianceId = allianceId;
    }

    public int doStartTag() throws JspException
    {
        Collection alliancePrivileges = null;

        try
        {
            alliancePrivileges = AllianceManager.getAlliancePriviliges();

        }

        catch (HibernateException he)
        {
            log.debug("A hibernate exception occured!");
        }

        JspWriter out = pageContext.getOut();
        StringBuffer outBuffer = new StringBuffer();
        Object[] userValues = null;

        int cnt = 0;

        try
        {

            for (Iterator iter = alliancePrivileges.iterator(); iter.hasNext();)
            {
                cnt++;
                userValues = (Object[]) iter.next();

                // userValues[0]  privilegeId
                // userValues[1]  privilegeName
                // userValues[2]  privilegeDescription
                // userValues[3]  privilegeKey
                // userValues[4]  availableTo
                // userValues[5]  subMenuUrl

                if (cnt == 1)
                {
                    outBuffer.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><DIV class=\"tab\">");
                }

                if ("VCAST Music Information".equalsIgnoreCase((String) userValues[1]) && !(this.getVcastMusicStatus()))
                    continue;

                if ("Submit Sales Lead".equalsIgnoreCase((String) userValues[2]))
                    continue;

                if("JMA Info".equalsIgnoreCase((String) userValues[2]))
                { 
                	log.debug("JMA Info Tab----------------------------------");
                	AimsAllianc alliance =(AimsAllianc)DBHelper.getInstance().load(AimsAllianc.class, getAllianceId());
                	if(!(StringFuncs.NullValueReplacement(alliance.getIsJMAAlliance()).equals("Y")))
                	{
                		continue;
                	}
                }
                
                if ((!getAttributeName().equalsIgnoreCase((String) userValues[2])))
                {
                    outBuffer.append("<div class=\"tabinActive\"><a href=\"")
                        .append((String) userValues[5])
                        .append("&alliance_id=")
                        .append(getAllianceId())
                        .append("\" >")
                        .append((String) userValues[2])
                        .append("</a></div>")
                        .append("<div class=\"divider\"> </div>");
                }
                else
                {
                    outBuffer
                        .append("<div class=\"tabActiveBegin\"></div><div class=\"tabActive\"><a href=\"")
                        .append((String) userValues[5])
                        .append("&alliance_id=")
                        .append(getAllianceId())
                        .append("\" >")
                        .append((String) userValues[2])
                        .append("</a></div>");
                }

            }

            if ((!getAttributeName().equalsIgnoreCase("Journal")))
            {
                    outBuffer.append("<div class=\"tabinActive\"><a href=\"")
                    .append("/aims/allianceJournalEntrySetup.do?task=create")
                    .append("&alliance_id=")
                    .append(getAllianceId())
                    .append("\" >")
                    .append("Journal")
                    .append("</a></div>")
                    .append("<div class=\"divider\"> </div>");
            }
            else
            {
                outBuffer
                    .append("<div class=\"tabActiveBegin\"></div><div class=\"tabActive\"><a href=\"")
                    .append("/aims/allianceJournalEntrySetup.do?task=create")
                    .append("&alliance_id=")
                    .append(getAllianceId())
                    .append("\" >")
                    .append("Journal")
                   .append("</a></div>");
            }

            if (cnt > 0)
            {
                outBuffer.append("</DIV> </table>");
            }

            out.println(outBuffer.toString());
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
        attributeName = null;
        super.release();
    }

    private boolean getVcastMusicStatus() throws Exception
    {
        boolean vcastMusicStatus = false;
        AimsAllianc aimsAlliance = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, getAllianceId());

        if ((aimsAlliance.getIsVcastMusicAlliance() != null) && (aimsAlliance.getIsVcastMusicAlliance().equals("Y")))
            vcastMusicStatus = true;

        return vcastMusicStatus;
    }

}