package com.netpace.aims.controller.application;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;

/**
 * This class takes care of action for display of update form of Brew Application.
 *
 * @struts.action path="/applicationResource"
 *                scope="request"
 * @struts.action-forward name="update" path="/application/brewApplicationUpdate.jsp"
 * @author Adnan Makda
 */
public class ApplicationResourceAction extends BaseAction
{

    static Logger log = Logger.getLogger(BrewApplicationUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {

        HttpSession session = request.getSession();
        Object o_param;
        String app_id;
        String app_resource;
        String composite_id;

        response.setHeader("Cache-Control", "no-cache");

        log.debug("IN EXECUTE OF RESOURCE");

        o_param = request.getParameter("app_id");
        if (o_param != null)
            app_id = request.getParameter("app_id");
        else
            app_id = "0";

        o_param = request.getParameter("app_res");
        if (o_param != null)
            app_resource = request.getParameter("app_res");
        else
            app_resource = "0";

        o_param = request.getParameter("composite_id");
        if (o_param != null)
            composite_id = request.getParameter("composite_id");
        else
            composite_id = "0";

        log.debug("APP_ID" + app_id);
        log.debug("APP_RESOURCE" + app_resource);
        log.debug("COMPOSITE_ID" + composite_id);

        if (!(app_id.equals("0")) && !(app_resource.equals("0")))
        {
            AimsTempFile aimsTempFile = null;

            Long allianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
            String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
            String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();

            String[] objInfo = null;
            boolean showTempFile = false;

            if (app_resource.equals("ScreenJpeg"))
                objInfo = ManageApplicationsConstants.SCREEN_JPEG_BLOB_OBJ_INFO;
            if (app_resource.equals("ScreenJpeg2"))
                objInfo = ManageApplicationsConstants.SCREEN_JPEG_2_BLOB_OBJ_INFO;
            if (app_resource.equals("ScreenJpeg3"))
                objInfo = ManageApplicationsConstants.SCREEN_JPEG_3_BLOB_OBJ_INFO;
            if (app_resource.equals("ScreenJpeg4"))
                objInfo = ManageApplicationsConstants.SCREEN_JPEG_4_BLOB_OBJ_INFO;
            if (app_resource.equals("ScreenJpeg5"))
                objInfo = ManageApplicationsConstants.SCREEN_JPEG_5_BLOB_OBJ_INFO;
            if (app_resource.equals("FlashDemo"))
                objInfo = ManageApplicationsConstants.FLASH_DEMO_BLOB_OBJ_INFO;
            if (app_resource.equals("UserGuide"))
                objInfo = ManageApplicationsConstants.USER_GUIDE_BLOB_OBJ_INFO;
            if (app_resource.equals("SplashScreenEps"))
                objInfo = ManageApplicationsConstants.SPLASH_SCREEN_EPS_BLOB_OBJ_INFO;
            if (app_resource.equals("ActiveScreenEps"))
                objInfo = ManageApplicationsConstants.ACTIVE_SCREEN_EPS_BLOB_OBJ_INFO;
            if (app_resource.equals("FaqDoc"))
                objInfo = ManageApplicationsConstants.FAQ_DOC_BLOB_OBJ_INFO;
            if (app_resource.equals("TestPlanResults"))
                objInfo = ManageApplicationsConstants.TEST_PLAN_RESULTS_BLOB_OBJ_INFO;
            if (app_resource.equals("StyleGuide"))
                objInfo = ManageApplicationsConstants.STYLE_GUIDE_BLOB_OBJ_INFO;
            if (app_resource.equals("MktgSlickSheet"))
                objInfo = ManageApplicationsConstants.MKTG_SLICK_SHEET_BLOB_OBJ_INFO;
            if (app_resource.equals("AppLogoBwSmall"))
                objInfo = ManageApplicationsConstants.APP_LOGO_BW_SMALL_BLOB_OBJ_INFO;
            if (app_resource.equals("AppLogoBwLarge"))
                objInfo = ManageApplicationsConstants.APP_LOGO_BW_LARGE_BLOB_OBJ_INFO;
            if (app_resource.equals("AppLogoColorSmall"))
                objInfo = ManageApplicationsConstants.APP_LOGO_CLRSMALL_BLOB_OBJ_INFO;
            if (app_resource.equals("AppLogoColorLarge"))
                objInfo = ManageApplicationsConstants.APP_LOGO_CLRLARGE_BLOB_OBJ_INFO;
            if (app_resource.equals("ClrPubLogo"))
                objInfo = ManageApplicationsConstants.CLR_PUB_LOGO_BLOB_OBJ_INFO;
            if (app_resource.equals("ProgGuide"))
                objInfo = ManageApplicationsConstants.PROG_GUIDE_BLOB_OBJ_INFO;
            if (app_resource.equals("AppTitleName"))
                objInfo = ManageApplicationsConstants.APP_TITLE_NAME_BLOB_OBJ_INFO;
            if (app_resource.equals("BrewPresentation"))
                objInfo = ManageApplicationsConstants.BREW_PRESENTATION_BLOB_OBJ_INFO;
            if (app_resource.equals("CompanyLogo"))
            	objInfo = ManageApplicationsConstants.BREW_COMPANY_LOGO_BLOB_OBJ_INFO;
            if (app_resource.equals("TitleShot"))
            	objInfo = ManageApplicationsConstants.BREW_TITLE_SHOT_BLOB_OBJ_INFO;            
            if (app_resource.equals("HighResSplash"))
            	objInfo = ManageApplicationsConstants.BREW_HIGH_RES_SPLASH_BLOB_OBJ_INFO;
            if (app_resource.equals("HighResActive"))
            	objInfo = ManageApplicationsConstants.BREW_HIGH_RES_ACTIVE_BLOB_OBJ_INFO;
            if (app_resource.equals("SplashScreen"))
            	objInfo = ManageApplicationsConstants.BREW_SPLASH_SCREEN_BLOB_OBJ_INFO;
            if (app_resource.equals("SmallSplash"))
            	objInfo = ManageApplicationsConstants.BREW_SMALL_SPLASH_BLOB_OBJ_INFO;
            if (app_resource.equals("ActiveScreen1"))
            	objInfo = ManageApplicationsConstants.BREW_ACTIVE_SCREEN_1_BLOB_OBJ_INFO;
            if (app_resource.equals("ActiveScreen2"))
            	objInfo = ManageApplicationsConstants.BREW_ACTIVE_SCREEN_2_BLOB_OBJ_INFO;
            if (app_resource.equals("SmlActiveScreen"))
            	objInfo = ManageApplicationsConstants.BREW_SML_ACTIVE_SCREEN_BLOB_OBJ_INFO;
            if (app_resource.equals("AppActiionFlash"))
            	objInfo = ManageApplicationsConstants.BREW_APP_ACTIION_FLASH_BLOB_OBJ_INFO;
            if (app_resource.equals("AppGifAction"))
            	objInfo = ManageApplicationsConstants.BREW_APP_GIF_ACTION_BLOB_OBJ_INFO;
            if (app_resource.equals("MediaStore"))
            	objInfo = ManageApplicationsConstants.BREW_MEDIA_STORE_BLOB_OBJ_INFO;            
            if (app_resource.equals("FlashDemoMovie"))
            	objInfo = ManageApplicationsConstants.FLASH_DEMO_MOVIE_BLOB_OBJ_INFO;            
            if (app_resource.equals("DashboardScrImg"))
            	objInfo = ManageApplicationsConstants.DASHBOARD_SCR_IMG_BLOB_OBJ_INFO;            
            if (app_resource.equals("UserGuidePdf"))
            	objInfo = ManageApplicationsConstants.BREW_USER_GUIDE_PDF_BLOB_OBJ_INFO;            
            if (app_resource.equals("MessageFlow"))
                objInfo = ManageApplicationsConstants.MESSAGE_FLOW_BLOB_OBJ_INFO;
            if (app_resource.equals("SampleContentFile"))
                objInfo = ManageApplicationsConstants.SAMPLE_CONTENT_BLOB_OBJ_INFO;
            if (app_resource.equals("WapProductLogo"))
                objInfo = ManageApplicationsConstants.WAP_PRODUCT_LOGO_BLOB_OBJ_INFO;
            if (app_resource.equals("WapProductIcon"))
                objInfo = ManageApplicationsConstants.WAP_PRODUCT_ICON_BLOB_OBJ_INFO;
            if (app_resource.equals("WapPresentation"))
                objInfo = ManageApplicationsConstants.WAP_PRESENTATION_BLOB_OBJ_INFO;
            if (app_resource.equals("WapAppMediumLargeImage"))
                objInfo = ManageApplicationsConstants.WAP_MEDIUM_IMAGE_BLOB_OBJ_INFO;//medium image
            if (app_resource.equals("WapAppQVGAPotraitImage"))
                objInfo = ManageApplicationsConstants.WAP_POTRAIT_IMAGE_BLOB_OBJ_INFO;//potrait
            if (app_resource.equals("WapQVGALandscapeImage"))
                objInfo = ManageApplicationsConstants.WAP_LANDSCAPE_IMAGE_BLOB_OBJ_INFO;//landscape
            if (app_resource.equals("PresentationFile"))
                objInfo = ManageApplicationsConstants.PRESENTATION_BLOB_OBJ_INFO;
            if (app_resource.equals("resultFile"))
                objInfo = ManageApplicationsConstants.RESULTS_FILE_BLOB_OBJ_INFO;
            if (app_resource.equals("VcastSampleClip1"))
                objInfo = ManageApplicationsConstants.VCAST_SAMPLE_CLIP_1_BLOB_OBJ_INFO;
            if (app_resource.equals("VcastSampleClip2"))
                objInfo = ManageApplicationsConstants.VCAST_SAMPLE_CLIP_2_BLOB_OBJ_INFO;
            if (app_resource.equals("VcastSampleClip3"))
                objInfo = ManageApplicationsConstants.VCAST_SAMPLE_CLIP_3_BLOB_OBJ_INFO;
            if (app_resource.equals("VZAppZonePresentation"))
                objInfo = ManageApplicationsConstants.VZAPPZONE_PRESENTATION_BLOB_OBJ_INFO;
            if (app_resource.equals("VZAppZoneContentLandingPage"))
                objInfo = ManageApplicationsConstants.VZAPPZONE_CONTENT_LANDING_PAGE_BLOB_OBJ_INFO;
            if (app_resource.equals("DashClrPubLogo"))
                objInfo = ManageApplicationsConstants.DASH_CLR_PUB_LOGO_BLOB_OBJ_INFO;
            if (app_resource.equals("DashAppTitleName"))
                objInfo = ManageApplicationsConstants.DASH_APP_TITLE_NAME_BLOB_OBJ_INFO;
            if (app_resource.equals("DashContentZipFile"))
            	objInfo = ManageApplicationsConstants.DASH_CONTENT_ZIP_FILE_BLOB_OBJ_INFO;
            if (app_resource.equals("JavaAppChnlTitle"))
                objInfo = ManageApplicationsConstants.JAVA_APP_CHNL_TITLE_BLOB_OBJ_INFO;
            if (app_resource.equals("JavaClrPubLogo"))
            	objInfo = ManageApplicationsConstants.JAVA_CLR_PUB_LOGO_BLOB_OBJ_INFO;
            if (app_resource.equals("JavaCompanyLogo"))
            	objInfo = ManageApplicationsConstants.JAVA_COMPANY_LOGO_BLOB_OBJ_INFO;
            if (app_resource.equals("CompanyLogo"))
            	objInfo = ManageApplicationsConstants.DASH_COMPANY_LOGO_BLOB_OBJ_INFO;
            if (app_resource.equals("TitleImage"))
            	objInfo = ManageApplicationsConstants.DASH_TITLE_IMAGE_BLOB_OBJ_INFO;
            if(app_resource.equals("boboLetterOfAuthFile"))
            	objInfo = ManageApplicationsConstants.BOBO_LETTER_AUTH_BLOB_OBJ_INFO;
            if(app_resource.equals("lbsContractFile"))
            	objInfo = ManageApplicationsConstants.LBS_CONTRACT_BLOB_OBJ_INFO;
            
            
            if (app_resource.equals("TempFile"))
                showTempFile = true;

            InputStream in = null;
            OutputStream ou = null;

            try
            {
                ou = response.getOutputStream();
                if (objInfo != null)
                    if (app_resource.equals("resultFile"))
                        aimsTempFile = ApplicationsManagerHelper.getFile(new Long(app_id), new Long(composite_id), allianceId, objInfo);
                    else
                        aimsTempFile = ApplicationsManagerHelper.getFile(new Long(app_id), allianceId, objInfo);
                else if (showTempFile)
                    aimsTempFile = TempFilesManager.getTempFile(new Long(app_id), currUser);

                if ((aimsTempFile == null) || (aimsTempFile.getTempFile() == null) || (aimsTempFile.getTempFileName() == null))
                    aimsTempFile = ApplicationsManagerHelper.getMiscImage(AimsConstants.MISC_IMAGE_NOT_AVAILABLE, currUserType);

                if (aimsTempFile != null)
                {
                    in = aimsTempFile.getTempFile().getBinaryStream();
                    response.reset();
                    response.setHeader("Content-Disposition", "attachment; filename=" + aimsTempFile.getTempFileName());
                    response.setContentType(aimsTempFile.getTempFileContentType());
                    response.setContentLength((int) aimsTempFile.getTempFile().length());
                    if (in != null)
                    {
                        int bytesRead = 0;
                        byte[] buffer = new byte[8192];
                        while ((bytesRead = in.read(buffer, 0, 8192)) != -1)
                            ou.write(buffer, 0, bytesRead);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println("Exception occured in ApplicationResourceAction");
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (in != null)
                        in.close();
                }
                catch (Exception ex2)
                {}

                try
                {
                    if (ou != null)
                    {
                        ou.flush();
                        ou.close();
                    }
                }
                catch (Exception ex2)
                {}
            }
        }
        return null;
    }
}
