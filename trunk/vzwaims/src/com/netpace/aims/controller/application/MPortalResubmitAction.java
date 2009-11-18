package com.netpace.aims.controller.application;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.VZAppZoneApplicationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsVZAppBinaries;
import com.netpace.aims.model.application.AimsVZAppZoneApp;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.MiscUtils;

/**
 * This class is used to get resources for InfoSpace.
 * 
 * @struts.action path="/mPortalResubmit"
 * @struts.action-forward name="thankyou" path="/public/thankyou.jsp"
 * @struts.action-forward name="duplicate" path="/public/Duplicate.jsp"
 * @author Adnan Ahmed
 */
public class MPortalResubmitAction extends BaseAction {

	static Logger log = Logger.getLogger(MPortalResubmitAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Object o_param;
		String app_id;
		String binaryFirmware_ids;
		String other_info;
		String binary_ids;
        String xml_type;
        String forward = "thankyou";

		o_param = request.getParameter("app_id");
		if (o_param != null)
			app_id = request.getParameter("app_id");
		else
			app_id = "0";

        o_param = request.getParameter("xml_type");
		if (o_param != null) {
			xml_type = request.getParameter("xml_type");
            if(!xml_type.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_OTA)
                    &&!xml_type.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_STAGE)
                    &&!xml_type.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_PROD)) {
                xml_type = "0";
            }
        }
        else {
			xml_type = "0";
        }

        o_param = request.getParameter("bf_id");
		if (o_param != null)
			binaryFirmware_ids = request.getParameter("bf_id");
		else
			binaryFirmware_ids = "0";

		o_param = request.getParameter("b_id");
		if (o_param != null)
			binary_ids = request.getParameter("b_id");
		else
			binary_ids = "0";

		o_param = request.getParameter("other_info");
		if (o_param != null)
			other_info = request.getParameter("other_info");
		else
			other_info = "0";

		if (!(app_id.equals("0")) && !(binaryFirmware_ids.equals("0")) &&
                !(other_info.equals("0")) && !(binary_ids.equals("0"))
                &&!(xml_type.equals("0"))) {
			// Authenticating URL
			String key = MiscUtils.getBase64Digest(
						(app_id+binary_ids+xml_type).getBytes(),
						binaryFirmware_ids.getBytes(),
						AimsConstants.MPORTAL_KEY_FOR_RESUBMIT_URL.getBytes());

			if (!other_info.equals(key)) {
				StringBuffer emailSubject = new StringBuffer("Hacking Tried in MPortalResubmitAction, on .... ");
				if (request.getServerName() != null)
					emailSubject.append(request.getServerName());

				MailUtils.sendMail(
								AimsConstants.EMAIL_EXCEPTION_ADMIN,
								"exceptions@netpace.com", 
								emailSubject.toString(),
								null, 
								MiscUtils.getRequestInfo(request));
			} 
			else {
				boolean processXMLRequest=VZAppZoneApplicationManager.checkMPortalResubmitStatus(binaryFirmware_ids, binary_ids, xml_type);
				if(processXMLRequest){
					AimsApp app = null;
					AimsVZAppZoneApp vzApp = null;
                    AimsAllianc alliance = null;

					HashMap mapInfo = null;

                    String[] ids = binary_ids.split(",");

					Long[] binaryIdsArr=new Long[ids.length];
					for (int i=0; i<ids.length; i++){
						binaryIdsArr[i]=new Long(ids[i]);
					}

                    try {
						mapInfo = VZAppZoneApplicationManager.getVzAppAllianceMap(new Long(app_id));
					} catch (Exception ignoreEx) {
                        log.error(ignoreEx, ignoreEx);
                        return null;
					}
					app = (AimsApp) mapInfo.get("app");
					vzApp = (AimsVZAppZoneApp) mapInfo.get("vzApp");
                    alliance = (AimsAllianc) mapInfo.get("alliance");

                    //send xml for all binary firmwares against these binary ids
                    VZAppZoneApplicationHelper.sendMPortalXML(app, vzApp, alliance, binaryIdsArr, xml_type);
				}
				else {
					forward="duplicate";
				}
			}
		}

		return mapping.findForward(forward);
	}
}
