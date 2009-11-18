package com.netpace.aims.controller.alliance;

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
import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.util.*;

import com.netpace.aims.controller.BaseAction;


/**
 * This class takes care of action for display of submission form of White Papers.
 *
 * @struts.action path="/whitePaperSetup"
 *                name="WhitePaperForm"
 *                scope="request"
 *                input="/alliance/whitePaperCreate.jsp"
 *				  			validate="false"
 * @struts.action-forward name="create" path="/alliance/whitePaperCreate.jsp"
 * @author Ahson Imtiaz
 */
public class WhitePaperSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(WhitePaperSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {

		//log.debug("whitePaperSetup.do Forwarding: " + forward);
		return mapping.findForward("create");
    }
}
