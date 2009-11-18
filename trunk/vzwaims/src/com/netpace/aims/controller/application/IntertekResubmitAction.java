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
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.MiscUtils;

/**
 * This class is used to get resources for InfoSpace.
 * 
 * @struts.action path="/intertekResubmit"
 * @struts.action-forward name="thankyou" path="/public/thankyou.jsp"
 * @struts.action-forward name="duplicate" path="/public/Duplicate.jsp"
 * @author Adnan Ahmed
 */
public class IntertekResubmitAction extends BaseAction {

	static Logger log = Logger.getLogger(InfoSpaceResubmitAction.class
			.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Object o_param;
		String app_id;
		String binaryFirmware_ids;
		String other_info;
		String binary_id;
		String forward = "thankyou";

		o_param = request.getParameter("app_id");
		if (o_param != null)
			app_id = request.getParameter("app_id");
		else
			app_id = "0";

		o_param = request.getParameter("bf_id");
		if (o_param != null)
			binaryFirmware_ids = request.getParameter("bf_id");
		else
			binaryFirmware_ids = "0";

		o_param = request.getParameter("b_id");
		if (o_param != null)
			binary_id = request.getParameter("b_id");
		else
			binary_id = "0";

		o_param = request.getParameter("other_info");
		if (o_param != null)
			other_info = request.getParameter("other_info");
		else
			other_info = "0";

		if (!(app_id.equals("0")) && !(binaryFirmware_ids.equals("0")) && !(other_info.equals("0")) && !(binary_id.equals("0"))) {
			// Authenticating URL
			String key = MiscUtils.getBase64Digest(
						(app_id+binary_id).getBytes(), 
						binaryFirmware_ids.getBytes(),
						AimsConstants.INTERTEK_KEY_FOR_RESUBMIT_URL.getBytes());

			if (!other_info.equals(key)) {
				StringBuffer emailSubject = new StringBuffer("Hacking Tried in IntertekResubmitAction, on .... ");
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
				boolean processXMLRequest=VZAppZoneApplicationManager.checkIntertekResubmitStatus(binaryFirmware_ids);					
				if(processXMLRequest){
					AimsApp app = null;
					AimsAllianc alliance=null;
					AimsContact contact=null;
					List binaryFirmwareInfoList=null;
					AimsVZAppBinaries binary=null;
					HashMap mapInfo = null;
					
					String[] ids=binaryFirmware_ids.split(",");			
					Long[] binaryFirwares=new Long[ids.length];
					for (int i=0; i<ids.length; i++){
						binaryFirwares[i]=new Long(ids[i]);
					}

					try {
						mapInfo = VZAppZoneApplicationManager.getVzAppAllianceAndContact(new Long(app_id));
						binaryFirmwareInfoList=VZAppZoneApplicationManager.getBinaryFirwarebyIds(binaryFirwares);
						binary=(AimsVZAppBinaries)VZAppZoneApplicationManager.getAimsVZAppBinariesListByBinaryIds(new Long(app_id), new Long[]{new Long(binary_id)}).get(0);
					} catch (Exception ignoreEx) {
						return null;
					}
					app = (AimsApp) mapInfo.get("app");
					alliance = (AimsAllianc) mapInfo.get("alliance");
					contact = (AimsContact) mapInfo.get("contact");

					VZAppZoneApplicationHelper.sendXmlToIntertek(alliance, contact, app, binaryFirmwareInfoList, binary); 					
				}
				else {
					forward="duplicate";
				}
			}
		}

		return mapping.findForward(forward);
	}
}
