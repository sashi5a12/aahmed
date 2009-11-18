package com.netpace.aims.controller.login;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.SendFailedException;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

import com.netpace.aims.bo.security.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.CommonProperties;
import com.netpace.aims.util.MailUtils;
import com.netpace.aims.util.ConfigEnvProperties;

/*****************************
    //commented for accounts cleanup, user can not change password
    //disabled xdoclet struts actions, forwards and forms
      This class takes care of action for the password of the aims user.
      If the user information is invalid it throws a InvalidUserInfoException

      author Fawad Sikandar
      struts.action path="/forgotPassword"
      name="ForgotPasswordForm"
      scope="request"
      input="/public/forgotPassword.jsp"
      validate="true"
     struts.action-forward name="success" path="/public/password.jsp"
     struts.action-exception key="error.forgotpassword.InvalidUserInfo" type="com.netpace.aims.bo.security.InvalidUserInfoException"
     com.netpace.aims.bo.security.InvalidUserInfoException
*****************************/

public class ForgotPasswordAction extends Action {

    static Logger log = Logger.getLogger(ForgotPasswordAction.class.getName());

    /**
     * This method verify the user information.
     * It calls the Security manager with the user name and mothers maiden name
     * and gets a user object if the user verified.
     * <p/>
     * If the username and mothers maiden name does not match it throws
     * a InvalidUserInfoException.
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        DynaValidatorForm forgotPasswordForm = (DynaValidatorForm) form;
        String username = (String) forgotPasswordForm.get("userName");
        String motherMaidenName = (String) forgotPasswordForm.get("motherMaidenName");

        log.debug("User : " + forgotPasswordForm.get("userName") + " motherMaidenName : " + forgotPasswordForm.get("motherMaidenName"));

        AimsUser user = AimsSecurityManager.getPassword(username, motherMaidenName);

        /*now we are not show the password on the main page.*/
        /*request.setAttribute("aimsUser",user);*/

        CommonProperties properties=CommonProperties.getInstance();
        String fromUser = properties.getProperty("forgotPassword.fromUser");
        String subject =properties.getProperty("forgotPassword.subject");
        String toCC = properties.getProperty("forgotPassword.toCC");
        String bodyText = properties.getProperty("forgotPassword.bodyText");


        /*todo:find a way to find out if the mail was sent of not, cause for the moment
        * i dont thing this is happening due to the thread that is spwan from the send mail
        * */
        try {
            MailUtils.sendMail(user.getUsername(), fromUser, subject, toCC, StringUtils.replace(bodyText, "@password@", user.getPassword()));
            request.setAttribute("responseMsg", "Your password have been sent to your mailing address.");
        } catch (SendFailedException sfEx) {
            request.setAttribute("responseMsg", "An error occurred while sending the email.");
            sfEx.printStackTrace();
        } catch (MessagingException mEx) {
            request.setAttribute("responseMsg", "An error occurred while sending the email.");
            mEx.printStackTrace();
        } catch (Exception mailEx) {
            request.setAttribute("responseMsg", "An error occurred while sending the email.");
            mailEx.printStackTrace();
        }


        return mapping.findForward("success");
    }
}