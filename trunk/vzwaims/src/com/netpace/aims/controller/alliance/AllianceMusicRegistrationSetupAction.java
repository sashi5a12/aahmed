package com.netpace.aims.controller.alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceMusicInfoManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.VzdnCredentials;
import com.netpace.aims.model.alliance.AimsMusicProductType;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/allianceMusicRegistrationSetup"  
 *                name="AllianceMusicRegistrationForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="update" path="/alliance/allianceMusicRegistrationUpdate.jsp"
 * @struts.action-forward name="vzdnloginaction" path="/vzdnLoginAction.do"
 * @author Adnan Makda
 */
public class AllianceMusicRegistrationSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceMusicRegistrationSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String forward = "update";

        //Get Form
        AllianceMusicRegistrationForm allianceMusicRegForm = (AllianceMusicRegistrationForm) form;
        if( request.getSession().getAttribute(AimsConstants.AIMS_USER) != null){
        	AimsUser user = (AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER);
        	allianceMusicRegForm.setVzdnManagerRoles(null);
    		allianceMusicRegForm.setEmail(user.getUsername());
    		allianceMusicRegForm.setFirstName(user.getAimsContact().getFirstName());
    		allianceMusicRegForm.setLastName(user.getAimsContact().getLastName());
    		allianceMusicRegForm.setTitle(user.getAimsContact().getTitle());
    		allianceMusicRegForm.setOfficePhone(user.getAimsContact().getPhone());
    		allianceMusicRegForm.setMobilePhone(user.getAimsContact().getMobile());
    		allianceMusicRegForm.setFax(user.getAimsContact().getFax());
    		
    		Collection products = new ArrayList();
            AimsMusicProductType aimsMusicProductType = null;

            for (Iterator iter = AllianceMusicInfoManager.getMusicProductTypes().iterator(); iter.hasNext();){
                aimsMusicProductType = (AimsMusicProductType) iter.next();
                products.add(new AllianceMusicRegistrationProductBean(aimsMusicProductType.getProductTypeId().toString(), aimsMusicProductType.getProductTypeName()));
            }
            allianceMusicRegForm.setAllProductTypes(products);
            return mapping.findForward(forward);

    		
        }
        
        if(request.getAttribute("vzdnCredentials")!=null ){
    		VzdnCredentials vzdnCredentials = (VzdnCredentials) request.getAttribute("vzdnCredentials");
    		allianceMusicRegForm.setVzdnManagerRoles(vzdnCredentials.getVzdnManagerRoles());
    		allianceMusicRegForm.setEmail(vzdnCredentials.getUserName());
    		allianceMusicRegForm.setFirstName(vzdnCredentials.getFirstName());
    		allianceMusicRegForm.setLastName(vzdnCredentials.getLastName());
    		allianceMusicRegForm.setTitle(vzdnCredentials.getTitle());
    		allianceMusicRegForm.setOfficePhone(vzdnCredentials.getPhoneNumber());
    		allianceMusicRegForm.setFax(vzdnCredentials.getFaxNumber());
    		allianceMusicRegForm.setMobilePhone(vzdnCredentials.getMobileNumber());
    		
    		Collection products = new ArrayList();
            AimsMusicProductType aimsMusicProductType = null;

            for (Iterator iter = AllianceMusicInfoManager.getMusicProductTypes().iterator(); iter.hasNext();){
                aimsMusicProductType = (AimsMusicProductType) iter.next();
                products.add(new AllianceMusicRegistrationProductBean(aimsMusicProductType.getProductTypeId().toString(), aimsMusicProductType.getProductTypeName()));
            }
            allianceMusicRegForm.setAllProductTypes(products);
            return mapping.findForward(forward);
    	}else{
    			request.setAttribute("registrationLink","/allianceMusicRegistrationSetup.do");
    			return mapping.findForward("vzdnloginaction");
    	}
        
        
    }
}
