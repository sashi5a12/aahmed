package com.netpace.aims.controller.alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceMusicInfoManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.alliance.AimsAllianceMusic;
import com.netpace.aims.model.alliance.AimsAllianceMusicProdType;
import com.netpace.aims.model.alliance.AimsMusicProductType;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;

/**
 * This class allows the user to proceed to the Add/Update Accounts functionality.
 *
 * @struts.action path="/allianceMusicInfoSetup"  
 *                name="AllianceMusicInfoForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdate" path="/alliance/allianceMusicInfoUpdate.jsp"
 * @struts.action-forward name="vzwView" path="/alliance/allianceMusicInfoUpdate.jsp" 
 * @author Rizwan Qazi
 */
public class AllianceMusicInfoSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceMusicInfoSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskname = request.getParameter("task");
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);

        //Get Form
        AllianceMusicInfoForm allianceMusicInfoForm = (AllianceMusicInfoForm) form;
        
        String user_type = user.getUserType();
        Long alliance_id = null;
        String forward = "";

        if (user_type.equalsIgnoreCase("A"))
        {

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VCAST_MUSIC_INFORMATION, AimsSecurityManager.SELECT)))
                throw new AimsSecurityException();

            alliance_id = user.getAimsAllianc();
            allianceMusicInfoForm.setAllianceId(alliance_id);
            if (taskname.equalsIgnoreCase("createForm"))
            {
                if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VCAST_MUSIC_INFORMATION, AimsSecurityManager.INSERT))
                    forward = "createUpdate";
                else
                    forward = "vzwView";
            }

            if (taskname.equalsIgnoreCase("editForm"))
            {
                if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VCAST_MUSIC_INFORMATION, AimsSecurityManager.UPDATE))
                    forward = "createUpdate";
                else
                    forward = "vzwView";
            }
        }

        if (user_type.equalsIgnoreCase("V"))
        {
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.SELECT)))
                throw new AimsSecurityException();

            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
            allianceMusicInfoForm.setAllianceId(alliance_id);
            forward = "vzwView";
        }

        HashMap musicAlliance = null;
        AimsAllianc aimsAlliance = null;
        AimsAllianceMusic aimsAllianceMusic = null;

        try
        {
            musicAlliance = AllianceMusicInfoManager.getAllianceMusicInfo(alliance_id);
        }
        catch (AimsException ae)
        {
            saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            return mapping.getInputForward();
        }

        aimsAlliance = (AimsAllianc) musicAlliance.get("AimsAllianc");
        aimsAllianceMusic = (AimsAllianceMusic) musicAlliance.get("AimsAllianceMusic");

        allianceMusicInfoForm.setCompanyName(aimsAlliance.getCompanyName());

        allianceMusicInfoForm.setHaveRightsCleared(aimsAllianceMusic.getHaveRightsCleared());
        allianceMusicInfoForm.setHaveExclusiveRights(aimsAllianceMusic.getHaveExclusiveRights());
        allianceMusicInfoForm.setWhatIsExclusive(aimsAllianceMusic.getWhatIsExclusive());
        allianceMusicInfoForm.setContentThruAggregator(aimsAllianceMusic.getContentThruAggregator());
        allianceMusicInfoForm.setCurrentDistributionPartners(aimsAllianceMusic.getCurrentDistributionPartners());
        allianceMusicInfoForm.setAnnualRevenue(aimsAllianceMusic.getAnnualRevenue());
        allianceMusicInfoForm.setAdditionalInformation(aimsAllianceMusic.getAdditionalInformation());

        Collection products = new ArrayList();
        Collection allianceProducts = AllianceMusicInfoManager.getAllianceMusicProductTypes(alliance_id);
        AimsMusicProductType aimsMusicProductType = null;
        AimsAllianceMusicProdType aimsAllianceMusicProdType = null;
        HashSet productIdsSelected = new HashSet();

        for (Iterator iter = AllianceMusicInfoManager.getMusicProductTypes().iterator(); iter.hasNext();)
        {
            aimsMusicProductType = (AimsMusicProductType) iter.next();
            AllianceMusicRegistrationProductBean allianceMusicRegistrationProductBean =
                new AllianceMusicRegistrationProductBean(aimsMusicProductType.getProductTypeId().toString(), aimsMusicProductType.getProductTypeName());
            for (Iterator iter2 = allianceProducts.iterator(); iter2.hasNext();)
            {
                aimsAllianceMusicProdType = (AimsAllianceMusicProdType) iter2.next();
                if (aimsMusicProductType.getProductTypeId().longValue() == aimsAllianceMusicProdType.getProductTypeId().getProductTypeId().longValue())
                {
                    if ((aimsAllianceMusicProdType.getIsSelected() != null) && (aimsAllianceMusicProdType.getIsSelected().equals("Y")))
                        productIdsSelected.add(aimsAllianceMusicProdType.getProductTypeId().getProductTypeId().toString());
                    allianceMusicRegistrationProductBean.setSizeTotalCatalog(aimsAllianceMusicProdType.getSizeTotalCatalog());
                    allianceMusicRegistrationProductBean.setSizeMobileCatalog(aimsAllianceMusicProdType.getSizeMobileCatalog());
                    allianceMusicRegistrationProductBean.setIncomeNonMobile(aimsAllianceMusicProdType.getIncomeNonMobile());
                    allianceMusicRegistrationProductBean.setIncomeMobile(aimsAllianceMusicProdType.getIncomeMobile());
                    allianceMusicRegistrationProductBean.setTopSellingArtists(aimsAllianceMusicProdType.getTopSellingArtists());
                }
            }
            products.add(allianceMusicRegistrationProductBean);
        }

        String[] tempProductIds = new String[productIdsSelected.size()];
        productIdsSelected.toArray(tempProductIds);
        allianceMusicInfoForm.setProductId(tempProductIds);

        allianceMusicInfoForm.setAllProductTypes(products);

        return mapping.findForward(forward);
    }
}
