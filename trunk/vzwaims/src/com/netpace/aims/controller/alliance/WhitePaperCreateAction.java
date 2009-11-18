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
import com.netpace.aims.model.alliance.*;
import com.netpace.aims.util.*;

import com.netpace.aims.controller.BaseAction;


/**
 * This class takes care of action for display of submission form of White Papers.
 *
 * @struts.action path="/whitePaperCreate"
 *                name="WhitePaperForm"
 *                scope="request"
 *                input="/alliance/whitePaperCreate.jsp"
 *				  			validate="true"
 * @struts.action-forward name="success" path="/alliance/whitePaperCreated.jsp"
 * @struts.action-exception key="error.WhitePaperForm.WhitePaperTitleExists" type="com.netpace.aims.bo.alliance.AimsWhitePaperTitleException"
 * @author Ahson Imtiaz
 */
public class WhitePaperCreateAction extends BaseAction
{

    static Logger log = Logger.getLogger(WhitePaperCreateAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {
    	HttpSession session = request.getSession();
		WhitePaperForm wPaperForm = (WhitePaperForm) form;
		AimsUser oAimsUser = (AimsUser) (session.getAttribute(AimsConstants.AIMS_USER));
		String currUser = oAimsUser.getUsername();
		
		AimsAllianceWhitePaper awhitepaper = new AimsAllianceWhitePaper();
		awhitepaper.setWhitePaperName(wPaperForm.getWhitePaperName());
		awhitepaper.setWhitePaperDesc(wPaperForm.getWhitePaperDesc());
		awhitepaper.setStatus("S");
		awhitepaper.setCreatedBy(currUser);
        awhitepaper.setCreatedDate(new Date());
		awhitepaper.setLastUpdatedBy(currUser);
        awhitepaper.setLastUpdatedDate(new Date());
		awhitepaper.setSubmittedBy(oAimsUser.getUserId());
        awhitepaper.setSubmittedDate(new Date());
        awhitepaper.setAllianceId(oAimsUser.getAimsAllianc());
		AllianceWhitePaperManager.saveOrUpdate(awhitepaper,wPaperForm.getWhitePaperFile());
		
		log.debug("whitePaperSetup.do Forwarding: success");
		return mapping.findForward("success");
    }
}
