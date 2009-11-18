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
 * This class is responsible for displaying the status message while doing
 * the request to the server.
 * @author Fawad Sikandar
 */

public class GetRequestStatusTag extends BodyTagSupport {

    static Logger log = Logger.getLogger(GetRequestStatusTag.class.getName());

    private String message;
    private String imgSrc;
    private String width;
    private String height;

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public String getImgSrc() {
      return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
      this.imgSrc = imgSrc;
    }

    public String getWidth() {
      return width;
    }

    public void setWidth(String width) {
      this.width = width;
    }

    public String getHeight() {
      return height;
    }

    public void setHeight(String height) {
      this.height = height;
    }


    public int doStartTag() throws JspException
    {
      HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
      HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

      if ( !Utility.isString(message) )
          message = (String) request.getSession().getAttribute(AimsConstants.STATUS_MSG);
      if ( !Utility.isString(width) )
          width = "100" ;
      if ( !Utility.isString(height) )
          height = "100" ;

        try{

		JspWriter out = pageContext.getOut();
		StringBuffer outBuffer = new StringBuffer();

               outBuffer.append("<script language='javascript'>function statusPage() { showElement('status'); hideElement('body');}</script>")
                        .append("<div id='status' style='position:absolute'>")
                        .append("<table width='" + width + "' height='" + height + "'>")
                        .append("<tr><td align='center' class='aimssecheading'>")
                        .append("<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='30' height='30'>")
                        .append("<param name='movie' value='images/arrow_ani.swf'>")
                        .append("<param name='quality' value='high'>")
                        .append("<embed src='images/arrow_ani.swf' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='30' height='30'></embed></object>")
                        .append(message)
                        .append("</td></tr></table></div>")
                        .append("<div id='body' style='position:relative'>");

                  out.println(outBuffer.toString());

              }catch (IOException ioExc)
              {
                log.debug("Exception : " + ioExc.getMessage());
                throw new JspException(ioExc.toString());
              }
              return EVAL_BODY_INCLUDE;
    }

	public int doEndTag()throws JspException
	{
           try {

            JspWriter out = pageContext.getOut();
            StringBuffer outBuffer = new StringBuffer();

            outBuffer.append("</div><div style='position:absolute' id='daysOfMonth2'/>")
            .append("<script language='javascript'> hideElement('status'); showElement('body'); </script>");

            out.println(outBuffer.toString());

          }catch (IOException ioExc)
          {
            log.debug("Exception : " + ioExc.getMessage());
            throw new JspException(ioExc.toString());
          }

  		return SKIP_PAGE;
	}

        public void release()
	{
		super.release();
	}




}