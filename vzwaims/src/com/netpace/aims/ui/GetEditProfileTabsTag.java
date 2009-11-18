package com.netpace.aims.ui;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;

import net.sf.hibernate.*;

import com.netpace.aims.util.*;
import com.netpace.aims.util.StringFuncs;

import org.apache.log4j.Logger;


/**
 * This class is responsible for displaying the tabs for the Edit Profile page.
 *
 * @author Ahson Imtiaz
 */

public class GetEditProfileTabsTag extends TagSupport {

    static Logger log = Logger.getLogger(GetEditProfileTabsTag.class.getName());

    protected String selectionName;

    public void setSelectionName(String strValue) {
        this.selectionName = strValue;
    }

    public String getSelectionName() {
        return this.selectionName;
    }

    public int doStartTag() throws JspException {


        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();


        JspWriter out = pageContext.getOut();
        StringBuffer outBuffer = new StringBuffer("");
        Object[] userValues = null;

        int cnt = 0;

        log.debug("************************ : " + selectionName);

        try {
            outBuffer.append("<DIV class=\"tab\">");

            if ("UserInfo".equalsIgnoreCase(selectionName)) {

                outBuffer.append("<div class=\"tabActiveBegin\"></div>")
                        .append("<div class=\"tabActive\">")
                        .append("<a href=\"/aims/editprofile.do\">  My Info </a>")
                        .append("</div>");

            } else {
                outBuffer.append("<div class=\"tabinActive\">")
                        .append("<a href=\"/aims/editprofile.do\">  My Info </a>")
                        .append("</div>")
                        .append("<div class=\"divider\">&nbsp;</div>");
            }

            if (!("Y".equalsIgnoreCase((String) request.getSession().getAttribute(AimsConstants.AIMS_ONLY_PROV_APPS)))) {
                if ("Notifications".equalsIgnoreCase(selectionName)) {
                    outBuffer.append("<div class=\"tabActiveBegin\"></div>")
                            .append("<div class=\"tabActive\">")
                            .append("<a href=\"/aims/editprofilenotif.do\">Notifications</a>")
                            .append("</div>");
                } else {
                    outBuffer.append("<div class=\"tabinActive\">")
                            .append("<a href=\"/aims/editprofilenotif.do\">Notifications</a>")
                            .append("</div>")
                            .append("<div class=\"divider\">&nbsp;</div>");
                }
            }

            outBuffer.append("</div>");

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
        selectionName = null;
        super.release();
    }


}