package com.netpace.aims.ui;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.log4j.Logger;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import com.netpace.aims.util.Utility;

/**
 * This class is responsible for displaying the file size message on the
 * basis of user type.
 * @author Fawad Sikandar
 */

public class GetFileSizeMessageTag extends TagSupport {

    static Logger log = Logger.getLogger(GetFileSizeMessageTag.class.getName());

  private String userType;
  private String message;
  private String imgSrc;

    public void setUserType(String userType){
      this.userType = userType;
    }

    public String getUserType(){
      return this.userType;
    }

    public String getMessage()
    {
      return message;
    }
    public void setMessage(String message)
    {
      this.message = message;
    }

    public String getImgSrc()
    {
      return imgSrc;
    }
    public void setImgSrc(String imgSrc)
    {
      this.imgSrc = imgSrc;
    }


    public int doStartTag() throws JspException
    {
      HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
      HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

      AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);

             try{

		JspWriter out = pageContext.getOut();
		StringBuffer outBuffer = new StringBuffer();

                 if ( !(Utility.isString(getUserType())) || !(user.getUserType().equalsIgnoreCase(getUserType())))
                {
                      log.debug("Checked In");
                      outBuffer.append("<table cellspacing='2' cellpadding='2'>")
                     .append("<tr><td>");
                       if (Utility.isString(imgSrc))
                         outBuffer.append("<img src='" + imgSrc + "'/>");
                      outBuffer.append("</td><td><span class='modFormFieldLbl'>")
                      .append(message)
                      .append("</span></td></tr></table>");

                  out.println(outBuffer.toString());

                }

              }catch (IOException ioExc)
              {
                ioExc.printStackTrace(System.out);
                throw new JspException(ioExc.toString());
              }
              return SKIP_BODY;
    }


	public int doEndTag()
	{
		return EVAL_PAGE;
	}


	public void release()
	{
		userType = null;
		super.release();
	}



}