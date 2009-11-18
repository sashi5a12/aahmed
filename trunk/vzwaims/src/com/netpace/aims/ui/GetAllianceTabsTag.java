package com.netpace.aims.ui;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.alliance.AllianceSalesLeadManager;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class is responsible for displaying the tabs for the Spotlights page.
 *
 * @author Rizwan Qazi
 */

public class GetAllianceTabsTag extends BodyTagSupport {

    static Logger log = Logger.getLogger(GetAllianceTabsTag.class.getName());

    protected String attributeName;

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public int doStartTag() throws JspException {

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        Collection alliancePrivileges = null;

        try {
            alliancePrivileges = AllianceManager.getAlliancePriviliges();

        }

        catch (HibernateException he) {
            log.debug("A hibernate exception occured!");
        }

        JspWriter out = pageContext.getOut();
        StringBuffer outBuffer = new StringBuffer("");
        Object[] userValues = null;

        int cnt = 0;

        try {

            for (Iterator iter = alliancePrivileges.iterator(); iter.hasNext();) {
                cnt++;
                userValues = (Object[]) iter.next();

                // userValues[0]  privilegeId
                // userValues[1]  privilegeName
                // userValues[2]  privilegeDescription
                // userValues[3]  privilegeKey
                // userValues[4]  availableTo
                // userValues[5]  subMenuUrl

                if (cnt == 1) {
                    outBuffer.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><DIV class=\"tab\">");
                }

                if ((AimsSecurityManager.checkAccess(request, (String) userValues[3], AimsSecurityManager.SELECT))) {
                    if ("VCAST Music Information".equalsIgnoreCase((String) userValues[1]) && !(this.getVcastMusicStatus(request)))
                        continue;

                    if ("Submit Sales Lead".equalsIgnoreCase((String) userValues[2]) && !(this.getSubmitSalesLeadStatus(request)))
                        continue;
                    
                    if("JMA Info".equalsIgnoreCase((String) userValues[2]))
                    { 
                    	log.debug("JMA Info Tab----------------------------------");
                    	AimsAllianc alliance=null;
                    	try
                    	{
	                    	HttpSession session = request.getSession();
	                    	Long allianceId=((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
	                    	alliance =(AimsAllianc)DBHelper.getInstance().load(AimsAllianc.class, allianceId.toString());
                    	
	                    	if(!(StringFuncs.NullValueReplacement(alliance.getIsJMAAlliance()).equals("Y")))
	                    	{
	                    		continue;
	                    	}
                    	}catch(Exception ex)
                    	{
                    		log.error("GetAllianceTabsTag.doStartTag : Exception occure while processing JMA Info tab", ex);
                    		continue;
                    	}
                    }

                    if (!getAttributeName().equalsIgnoreCase((String) userValues[2])) {
                        outBuffer
                                .append("<div class=\"tabinActive\"><a href=\"")
                                .append((String) userValues[5])
                                .append("\" >")
                                .append((String) userValues[2])
                                .append("</a></div>")
                                .append("<div class=\"divider\"> </div>");
                    } else {
                        outBuffer
                                .append("<div class=\"tabActiveBegin\"></div><div class=\"tabActive\"><a href=\"")
                                .append((String) userValues[5])
                                .append("\" >")
                                .append((String) userValues[2])
                                .append("</a></div>");
                    }
                }
            }

            if (cnt > 0) {
                outBuffer.append("</DIV></table>");
            }
            out.println(outBuffer.toString());
        }

        catch (IOException ioExc) {
            ioExc.printStackTrace(System.out);
            throw new JspException(ioExc.toString());
        }

        catch (Exception Exc) {
            Exc.printStackTrace(System.out);
            throw new JspException(Exc.toString());
        }

        return SKIP_BODY;
    }

    public int doEndTag() {
        return EVAL_PAGE;
    }

    public void release() {
        attributeName = null;
        super.release();
    }

    private boolean getSubmitSalesLeadStatus(HttpServletRequest request) throws Exception {

        boolean submitSalesLeadStatus = true;
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        Long allianceId = user.getAimsAllianc();
        Collection list =
                AllianceSalesLeadManager.checkAllianceStatus(
                        allianceId,
                        AimsConstants.ALLIANCE_STATUS_ACCEPTED,
                        AimsConstants.ACCEPTANCE_ID,
                        VOLookupFactory.getInstance().getAimsPlatform("Enterprise").getPlatformId(),
                        AimsConstants.CONTRACT_ACCEPTED);

        if (list.size() < 1) {
            submitSalesLeadStatus = false;
        }
        return submitSalesLeadStatus;

    }

    private boolean getVcastMusicStatus(HttpServletRequest request) throws Exception {
        boolean vcastMusicStatus = false;
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        AimsAllianc aimsAlliance = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, user.getAimsAllianc().toString());

        if ((aimsAlliance.getIsVcastMusicAlliance() != null) && (aimsAlliance.getIsVcastMusicAlliance().equals("Y")))
            vcastMusicStatus = true;

        return vcastMusicStatus;
    }
}
