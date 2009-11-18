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
import com.netpace.aims.bo.alliance.AllianceSalesLeadManager;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.bo.security.InvalidUserInfoException;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;


/**
 * This class takes care of action for display of submission form of Sales Lead.
 *
 * @struts.action path="/salesLeadSetup"
 *                name="SalesLeadForm"
 *                scope="request"
 *                input="/common/security.jsp"
 *          	  validate="false"
 * @struts.action-forward name="create" path="/alliance/allianceSalesLeadCreate.jsp"
 * @struts.action-exception key="error.saleslead.InvalidUserInfo" type="com.netpace.aims.bo.security.InvalidUserInfoException"
 * @author Ahson Imtiaz
 */
public class SalesLeadSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(SalesLeadSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {

      AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.
                    AIMS_USER);
      String user_type = user.getUserType();
      Long allianceId  = null;//user.getAimsAllianc();
      if (user_type.equalsIgnoreCase("A"))
      {
      	allianceId  = user.getAimsAllianc();
      }
      
      if (user_type.equalsIgnoreCase("V"))
      {
      	allianceId = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
      }      
      Collection list = AllianceSalesLeadManager
          .checkAllianceStatus(allianceId, AimsConstants.ALLIANCE_STATUS_ACCEPTED,
                               AimsConstants.ACCEPTANCE_ID,
                               VOLookupFactory.
                               getInstance().getAimsPlatform("Enterprise").
                               getPlatformId(), AimsConstants.CONTRACT_ACCEPTED);

       if ( (user_type.equalsIgnoreCase("A")) && (list.size() < 1) )
       {
          throw new InvalidUserInfoException();
       }

       request.getSession().setAttribute("appList", list);

       return mapping.findForward("create");
    }
}
