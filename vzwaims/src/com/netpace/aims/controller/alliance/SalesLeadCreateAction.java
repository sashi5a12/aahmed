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
import java.text.*;

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
 * This class takes care of action for display of submission form of Sales Lead.
 *
 * @struts.action path="/salesLeadCreate"
 *                name="SalesLeadForm"
 *                scope="request"
 *                input="/alliance/allianceSalesLeadCreate.jsp"
 *				  			validate="true"
 * @struts.action-forward name="success" path="/alliance/allianceSalesLeadCreated.jsp"
  * @author Ahson Imtiaz
 */
public class SalesLeadCreateAction extends BaseAction
{

    static Logger log = Logger.getLogger(SalesLeadCreateAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {
    	HttpSession session = request.getSession();
		SalesLeadForm salesForm = (SalesLeadForm) form;

                 log.debug(" No of App.   ");
                 if ( salesForm.getAppsId() != null ) {
                   log.debug(" Length  Of  App. Ids : " + salesForm.getAppsId().length);
                 }


		AimsUser oAimsUser = (AimsUser) (session.getAttribute(AimsConstants.AIMS_USER));
		String currUser = oAimsUser.getUsername();
		AimsAllianceSalesLead asaleslead = new AimsAllianceSalesLead();
		asaleslead.setPlatform(MiscUtils.ConvertPlatformArrayToHashSet(salesForm.getPlatformIds()));
		asaleslead.setEstimatedArpu(Utility.convertToLong(salesForm.getEstimatedARPU()));
                asaleslead.setAllianceApps(MiscUtils.ConvertAppArrayToHashSet(salesForm.getAppsId()));
                asaleslead.setCompanySolution(salesForm.getCompanySolution());
		asaleslead.setEstimatedSize(Utility.convertToLong(salesForm.getEstimatedSize()));
		asaleslead.setLeadQualification(salesForm.getLeadQualification());

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date date = null;
		try {
			date = df.parse(salesForm.getPurchaseDecision());

		} catch (Exception ex) {
			// not reachable codition if validate is true
		}
		asaleslead.setPurchaseDecisionDate(date); //
		asaleslead.setSalesLeadDesc(salesForm.getSalesLeadDesc());
		asaleslead.setStatus("N");

		/* Generic Fields are updation Starts */
		asaleslead.setCreatedBy(currUser);
                asaleslead.setCreatedDate(new Date());
		asaleslead.setLastUpdatedBy(currUser);
                asaleslead.setLastUpdatedDate(new Date());
		asaleslead.setSubmittedBy(oAimsUser.getUserId());
                asaleslead.setSubmittedDate(new Date());
                asaleslead.setAllianceId(oAimsUser.getAimsAllianc());
                /* Generic Fields are updation Ends*/
		AllianceSalesLeadManager.saveOrUpdate(asaleslead);

		log.debug("salesLeadCreate.do Forwarding: success");
		return mapping.findForward("success");
    }
}
