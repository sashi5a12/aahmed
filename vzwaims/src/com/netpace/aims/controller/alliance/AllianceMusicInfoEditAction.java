package com.netpace.aims.controller.alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.alliance.AllianceMusicInfoManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.alliance.AimsAllianceMusic;
import com.netpace.aims.model.alliance.AimsAllianceMusicProdType;
import com.netpace.aims.model.alliance.AimsMusicProductType;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/allianceMusicInfoEdit"                
 *                scope="request" 
 *				  name="AllianceMusicInfoForm"
 *				  validate="true"
 *                input="/alliance/allianceMusicInfoUpdate.jsp"
 * @struts.action-forward name="view" path="/alliance/allianceMusicInfoUpdate.jsp"
 * @author Rizwan Qazi
 */

public class AllianceMusicInfoEditAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceMusicInfoEditAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));
        log.debug("Task : " + taskname);
        String forward = "view";
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_type = user.getUserType();
        Long allianceId = null;

        AllianceMusicInfoForm allianceMusicInfoForm = (AllianceMusicInfoForm) form;

        if (user_type.equalsIgnoreCase("A"))
            allianceId = user.getAimsAllianc();

        if (user_type.equalsIgnoreCase("V"))
            allianceId = allianceMusicInfoForm.getAllianceId();

        if (taskname.equalsIgnoreCase("edit"))
        {

            HashMap musicAlliance = null;
            AimsAllianceMusic aimsAllianceMusic = null;

            try
            {
                musicAlliance = AllianceMusicInfoManager.getAllianceMusicInfo(allianceId);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }

            aimsAllianceMusic = (AimsAllianceMusic) musicAlliance.get("AimsAllianceMusic");

            aimsAllianceMusic.setHaveRightsCleared(allianceMusicInfoForm.getHaveRightsCleared());
            aimsAllianceMusic.setHaveExclusiveRights(allianceMusicInfoForm.getHaveExclusiveRights());
            aimsAllianceMusic.setWhatIsExclusive(allianceMusicInfoForm.getWhatIsExclusive());
            aimsAllianceMusic.setContentThruAggregator(allianceMusicInfoForm.getContentThruAggregator());
            aimsAllianceMusic.setCurrentDistributionPartners(allianceMusicInfoForm.getCurrentDistributionPartners());
            aimsAllianceMusic.setAnnualRevenue(allianceMusicInfoForm.getAnnualRevenue());
            aimsAllianceMusic.setAdditionalInformation(allianceMusicInfoForm.getAdditionalInformation());
            aimsAllianceMusic.setLastUpdatedBy(user.getUsername());
            aimsAllianceMusic.setLastUpdatedDate(new Date());

            String[] productsSelected = allianceMusicInfoForm.getProductId();
            String[] productSizeTotalCatalog = allianceMusicInfoForm.getSizeTotalCatalog();
            String[] productSizeMobileCatalog = allianceMusicInfoForm.getSizeMobileCatalog();
            String[] productIncomeNonMobile = allianceMusicInfoForm.getIncomeNonMobile();
            String[] productIncomeMobile = allianceMusicInfoForm.getIncomeMobile();
            String[] productTopSellingArtists = allianceMusicInfoForm.getTopSellingArtists();

            Set productTypeSet = new HashSet();
            Set productTypeInserted = new HashSet();
            AimsAllianceMusicProdType aimsAllianceMusicProdType = null;
            AimsMusicProductType aimsMusicProductType = null;
            Collection allianceProducts = AllianceMusicInfoManager.getAllianceMusicProductTypes(allianceId);
            for (Iterator iter = allianceProducts.iterator(); iter.hasNext();)
            {
                aimsAllianceMusicProdType = (AimsAllianceMusicProdType) iter.next();

                productTypeInserted.add(aimsAllianceMusicProdType.getProductTypeId().getProductTypeId());
                //Reset all values if this time around the checkbox is unchecked.
                if ((aimsAllianceMusicProdType.getIsSelected() != null)
                    && (aimsAllianceMusicProdType.getIsSelected().equals("Y"))
                    && (aimsAllianceMusicProdType.getProductTypeId().getIsActive() != null)
                    && (aimsAllianceMusicProdType.getProductTypeId().getIsActive().equals("Y")))
                {
                    aimsAllianceMusicProdType.setSizeTotalCatalog(null);
                    aimsAllianceMusicProdType.setSizeMobileCatalog(null);
                    aimsAllianceMusicProdType.setIncomeNonMobile(null);
                    aimsAllianceMusicProdType.setIncomeMobile(null);
                    aimsAllianceMusicProdType.setTopSellingArtists(null);
                    aimsAllianceMusicProdType.setLastUpdatedBy(user.getUsername());
                    aimsAllianceMusicProdType.setLastUpdatedDate(new Date());
                    aimsAllianceMusicProdType.setIsSelected("N");
                }

                for (int iIndex = 0; iIndex < productsSelected.length; iIndex++)
                {
                    if (aimsAllianceMusicProdType.getProductTypeId().getProductTypeId().toString().equals(productsSelected[iIndex]))
                    {
                        int loopProductId = ((Long) allianceMusicInfoForm.getProductMapper().get(productsSelected[iIndex])).intValue();
                        aimsAllianceMusicProdType.setSizeTotalCatalog(productSizeTotalCatalog[loopProductId]);
                        aimsAllianceMusicProdType.setSizeMobileCatalog(productSizeMobileCatalog[loopProductId]);
                        aimsAllianceMusicProdType.setIncomeNonMobile(productIncomeNonMobile[loopProductId]);
                        aimsAllianceMusicProdType.setIncomeMobile(productIncomeMobile[loopProductId]);
                        aimsAllianceMusicProdType.setTopSellingArtists(productTopSellingArtists[loopProductId]);
                        aimsAllianceMusicProdType.setLastUpdatedBy(user.getUsername());
                        aimsAllianceMusicProdType.setLastUpdatedDate(new Date());
                        aimsAllianceMusicProdType.setIsSelected("Y");
                    }
                }
                productTypeSet.add(aimsAllianceMusicProdType);
            }

            //Adding nwe product types (that have just been activated in the master table)
            Set productTypeNewSet = new HashSet();

            for (Iterator iter = AllianceMusicInfoManager.getMusicProductTypes().iterator(); iter.hasNext();)
            {
                aimsMusicProductType = (AimsMusicProductType) iter.next();
                if (!productTypeInserted.contains(aimsMusicProductType.getProductTypeId()))
                {
                    System.out.println("\n\nNo it does not contains" + aimsMusicProductType.getProductTypeId());
                    aimsAllianceMusicProdType = new AimsAllianceMusicProdType();
                    aimsAllianceMusicProdType.setAllianceId(allianceId);
                    aimsAllianceMusicProdType.setProductTypeId(aimsMusicProductType);
                    aimsAllianceMusicProdType.setCreatedBy(user.getUsername());
                    aimsAllianceMusicProdType.setCreatedDate(new Date());
                    aimsAllianceMusicProdType.setLastUpdatedBy(user.getUsername());
                    aimsAllianceMusicProdType.setLastUpdatedDate(new Date());
                    aimsAllianceMusicProdType.setIsSelected("N");

                    for (int iIndex = 0; iIndex < productsSelected.length; iIndex++)
                    {
                        if (aimsMusicProductType.getProductTypeId().toString().equals(productsSelected[iIndex]))
                        {
                            int loopProductId = ((Long) allianceMusicInfoForm.getProductMapper().get(productsSelected[iIndex])).intValue();
                            aimsAllianceMusicProdType.setSizeTotalCatalog(productSizeTotalCatalog[loopProductId]);
                            aimsAllianceMusicProdType.setSizeMobileCatalog(productSizeMobileCatalog[loopProductId]);
                            aimsAllianceMusicProdType.setIncomeNonMobile(productIncomeNonMobile[loopProductId]);
                            aimsAllianceMusicProdType.setIncomeMobile(productIncomeMobile[loopProductId]);
                            aimsAllianceMusicProdType.setTopSellingArtists(productTopSellingArtists[loopProductId]);
                            aimsAllianceMusicProdType.setIsSelected("Y");
                        }
                    }
                    productTypeNewSet.add(aimsAllianceMusicProdType);
                }
            }

            try
            {
                AllianceMusicInfoManager.saveOrUpdateMusicAlliance(aimsAllianceMusic, productTypeSet, productTypeNewSet);

            }
            catch (UniqueConstraintException uce)
            {
                ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError(uce.getMessageKey());
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
                return (mapping.findForward("error"));
            }
            catch (Exception ex)
            {
                ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError("error.generic.database");
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
                return (mapping.findForward("error"));
            }

            //TODO: Setting the updated values the crude way
            Collection products = new ArrayList();
            Collection allianceProducts2 = AllianceMusicInfoManager.getAllianceMusicProductTypes(allianceId);
            AimsMusicProductType aimsMusicProductType2 = null;
            AimsAllianceMusicProdType aimsAllianceMusicProdType2 = null;

            for (Iterator iter = AllianceMusicInfoManager.getMusicProductTypes().iterator(); iter.hasNext();)
            {
                aimsMusicProductType2 = (AimsMusicProductType) iter.next();
                AllianceMusicRegistrationProductBean allianceMusicRegistrationProductBean =
                    new AllianceMusicRegistrationProductBean(aimsMusicProductType2.getProductTypeId().toString(), aimsMusicProductType2.getProductTypeName());
                for (Iterator iter2 = allianceProducts2.iterator(); iter2.hasNext();)
                {
                    aimsAllianceMusicProdType2 = (AimsAllianceMusicProdType) iter2.next();
                    if (aimsMusicProductType2.getProductTypeId().longValue() == aimsAllianceMusicProdType2.getProductTypeId().getProductTypeId().longValue())
                    {
                        allianceMusicRegistrationProductBean.setSizeTotalCatalog(aimsAllianceMusicProdType2.getSizeTotalCatalog());
                        allianceMusicRegistrationProductBean.setSizeMobileCatalog(aimsAllianceMusicProdType2.getSizeMobileCatalog());
                        allianceMusicRegistrationProductBean.setIncomeNonMobile(aimsAllianceMusicProdType2.getIncomeNonMobile());
                        allianceMusicRegistrationProductBean.setIncomeMobile(aimsAllianceMusicProdType2.getIncomeMobile());
                        allianceMusicRegistrationProductBean.setTopSellingArtists(aimsAllianceMusicProdType2.getTopSellingArtists());
                    }
                }
                products.add(allianceMusicRegistrationProductBean);
            }

            allianceMusicInfoForm.setAllProductTypes(products);

        }
        ActionMessages messages = new ActionMessages();
        ActionMessage message = null;
        message = new ActionMessage("message.alliance.vcast.music.info.submitted");
        messages.add(ActionMessages.GLOBAL_MESSAGE, message);
        saveMessages(request, messages);

        return mapping.findForward(forward);
    }
}
