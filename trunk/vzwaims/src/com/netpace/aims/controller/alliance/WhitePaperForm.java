package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.*;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import java.util.Set;
import java.lang.Long;
import org.apache.log4j.Logger;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.tools.*;
import com.netpace.aims.util.*;
import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.bo.tools.*;

/**
 * @struts.form name="WhitePaperForm"
 * @author Ahson Imtiaz.
 */
public class WhitePaperForm  extends
		com.netpace.aims.controller.BaseValidatorForm
{

    	static Logger log = Logger.getLogger(WhitePaperForm.class.getName());

		private String whitePaperName;
		private String whitePaperDesc;
		private FormFile whitePaperFile;
		private String whitePaperStatus;
		private String whitePaperFilename;
		private Long whitePaperId;


		/* Public Getter and Setter Functions Starts*/

		/*  */
		public void setWhitePaperName(String strInput)
		{
			this.whitePaperName = strInput;
		}

		public String getWhitePaperName()
		{
			return this.whitePaperName;
		}

		/*  */
		public void setWhitePaperDesc(String strInput)
		{
			this.whitePaperDesc = strInput;
		}

		public String getWhitePaperDesc()
		{
			return this.whitePaperDesc;
		}

		/*  */
		public void setWhitePaperStatus(String strInput)
		{
			this.whitePaperStatus = strInput;
		}

		public String getWhitePaperStatus()
		{
			return this.whitePaperStatus;
		}

		/*  */
		public void setWhitePaperFilename(String strInput)
		{
			this.whitePaperFilename = strInput;
		}

		public String getWhitePaperFilename()
		{
			return this.whitePaperFilename;
		}

		/*  */
		public void setWhitePaperFile(FormFile ffFile)
		{
			this.whitePaperFile = ffFile;
		}

		public FormFile getWhitePaperFile()
		{
			return this.whitePaperFile;
		}

		/*  */
		public void setWhitePaperId(Long lngTempId)
		{
			this.whitePaperId = lngTempId;
		}

		public Long getWhitePaperId()
		{
			return this.whitePaperId;
		}

      /* RESET FUNCTION */
		public void reset (ActionMapping mapping, HttpServletRequest request)   {


				log.debug("In RESET White Paper Out");

		}

		public ActionErrors	validate(ActionMapping mapping,	HttpServletRequest request)	{

			ActionErrors errors	=	new	ActionErrors();

			if (this.isBlankString(this.whitePaperName))
					errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.WhitePaperForm.whitePaperName"));

			if (this.isBlankString(this.whitePaperDesc))
					errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.WhitePaperForm.whitePaperDesc"));

			if ( ((this.whitePaperFile == null) || !(this.whitePaperFile.getFileSize()>0)) && (this.isBlankString(this.whitePaperFilename)) )
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.WhitePaperForm.whitePaperFileName"));
			else if ( !(whitePaperFile.getContentType().equalsIgnoreCase("application/pdf") || whitePaperFile.getContentType().equalsIgnoreCase("application/msword") ) )
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.WhitePaperForm.WhitePaperNotValidFile"));

			return errors;

		}


}

