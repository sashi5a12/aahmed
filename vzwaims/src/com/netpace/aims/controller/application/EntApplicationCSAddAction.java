package com.netpace.aims.controller.application;

import org.apache.struts.util.MessageResources;
import org.apache.struts.action.*;
import org.apache.log4j.Logger;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;

import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.masters.*;
import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.util.*;

import com.netpace.aims.controller.BaseAction;


/**
 * This class takes care of action for display of update form of Enterprise Application.
 *
 * @struts.action path="/entApplicationCSAdd"
 *                name="EntApplicationUpdateCSForm"
 *                scope="request"
 *                input="/application/entApplicationUpdate3.jsp"
 *				  		validate="true"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="caseStudyPage" path="/application/entApplicationUpdate3.jsp"
 * @author Ahson Imtiaz
 */
public class EntApplicationCSAddAction extends BaseAction
{

    static Logger log = Logger.getLogger(EntApplicationSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {

        HttpSession session = request.getSession();
        String forward = "caseStudyPage";
				Object o_param;

				o_param = request.getParameter("cstask");
				
				if ( o_param != null && ((String)o_param).equals("delete"))
				{
					ActionMessages messages = new ActionMessages();
					messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.enterprise.app.csRecordRemoved"));
					saveMessages( request, messages );
				}
				else
				{
					ActionMessages messages = new ActionMessages();
					messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.enterprise.app.csRecordAdded"));
					saveMessages( request, messages );
				}
				
		return mapping.findForward(forward);
    }
}
