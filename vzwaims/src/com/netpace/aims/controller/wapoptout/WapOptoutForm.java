package com.netpace.aims.controller.wapoptout;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.alliance.AllianceCompInfoManager;
import com.netpace.aims.bo.wapoptout.WapOptoutManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.WapoptoutUtil;

/**
 * @struts.form name="WapOptoutForm"
 */
public class WapOptoutForm extends BaseValidatorForm {

	private static final Logger log = Logger.getLogger(WapOptoutForm.class
			.getName());

	private java.lang.String task;
	private java.lang.String companyName;
	private java.lang.String streetAddress;
	private java.lang.String city;
	private java.lang.String state;
	private java.lang.String zipCode;
	private java.lang.String country;
	private java.lang.String reqFirstName;
	private java.lang.String reqLastName;
	private java.lang.String reqPhoneNumber;
	private java.lang.String reqEmailAddress;
	private java.lang.String adminCompanyName;
	private java.lang.String adminFirstName;
	private java.lang.String adminLastName;
	private java.lang.String adminPhoneNumber;
	private java.lang.String adminEmailAddress;
	private java.lang.String submitDate;
	private java.lang.Long submittalNumber;
	private java.lang.String[] bypassUrls;

	public java.lang.String getTask() {
		return task;
	}

	public void setTask(java.lang.String task) {
		this.task = task;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}

	public java.lang.String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(java.lang.String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public java.lang.String getCity() {
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}

	public java.lang.String getState() {
		return state;
	}

	public void setState(java.lang.String state) {
		this.state = state;
	}

	public java.lang.String getZipCode() {
		return zipCode;
	}

	public void setZipCode(java.lang.String zipCode) {
		this.zipCode = zipCode;
	}

	public java.lang.String getCountry() {
		return country;
	}

	public void setCountry(java.lang.String country) {
		this.country = country;
	}

	public java.lang.String getReqFirstName() {
		return reqFirstName;
	}

	public void setReqFirstName(java.lang.String reqFirstName) {
		this.reqFirstName = reqFirstName;
	}

	public java.lang.String getReqLastName() {
		return reqLastName;
	}

	public void setReqLastName(java.lang.String reqLastName) {
		this.reqLastName = reqLastName;
	}

	public java.lang.String getReqPhoneNumber() {
		return reqPhoneNumber;
	}

	public void setReqPhoneNumber(java.lang.String reqPhoneNumber) {
		this.reqPhoneNumber = reqPhoneNumber;
	}

	public java.lang.String getReqEmailAddress() {
		return reqEmailAddress;
	}

	public void setReqEmailAddress(java.lang.String reqEmailAddress) {
		this.reqEmailAddress = reqEmailAddress;
	}

	public java.lang.String getAdminCompanyName() {
		return adminCompanyName;
	}

	public void setAdminCompanyName(java.lang.String adminCompanyName) {
		this.adminCompanyName = adminCompanyName;
	}

	public java.lang.String getAdminFirstName() {
		return adminFirstName;
	}

	public void setAdminFirstName(java.lang.String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}

	public java.lang.String getAdminLastName() {
		return adminLastName;
	}

	public void setAdminLastName(java.lang.String adminLastName) {
		this.adminLastName = adminLastName;
	}

	public java.lang.String getAdminPhoneNumber() {
		return adminPhoneNumber;
	}

	public void setAdminPhoneNumber(java.lang.String adminPhoneNumber) {
		this.adminPhoneNumber = adminPhoneNumber;
	}

	public java.lang.String getAdminEmailAddress() {
		return adminEmailAddress;
	}

	public void setAdminEmailAddress(java.lang.String adminEmailAddress) {
		this.adminEmailAddress = adminEmailAddress;
	}

	public java.lang.String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(java.lang.String submitDate) {
		this.submitDate = submitDate;
	}

	public java.lang.Long getSubmittalNumber() {
		return submittalNumber;
	}

	public void setSubmittalNumber(java.lang.Long submittalNumber) {
		this.submittalNumber = submittalNumber;
	}

	public java.lang.String[] getBypassUrls() {
		return bypassUrls;
	}

	public void setBypassUrls(java.lang.String[] bypassUrls) {
		this.bypassUrls = bypassUrls;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		request.setAttribute("countryList", this.getCountriesList());
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (this.isBlankString(task) == false
				&& (this.task.equalsIgnoreCase("submit") || this.task
						.equalsIgnoreCase("confirm"))) {
			validateToDBFields(errors);
			if (this.isBlankString(this.companyName)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.companyName"));
			}
			if (this.isBlankString(this.reqFirstName)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.reqFirstName"));
			}
			if (this.isBlankString(this.reqLastName)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.reqLastName"));
			}
			if (this.isBlankString(this.streetAddress)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.streetAddress"));
			}
			if (this.isBlankString(this.reqEmailAddress)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.reqEmailAddress"));
			} else if (this.isValidEmail(this.reqEmailAddress) == false) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.invalid.reqEmailAddress"));
			}
			if (this.isBlankString(this.city)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.city"));
			}
			if (this.isBlankString(this.state)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.state"));
			}
			if (this.isBlankString(this.zipCode)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.zipCode"));
			}
			if (this.isBlankString(this.country)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.country"));
			}
			if (this.isBlankString(this.reqPhoneNumber)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.reqPhoneNumber"));
			}
			boolean isEmpty = true;
			if (this.bypassUrls != null && this.bypassUrls.length > 0) {
				for (int i = 0; i < this.bypassUrls.length; i++) {
					if (!isBlankString(this.bypassUrls[i])) {
						isEmpty = false;

						int inValidWebURLCase;
						inValidWebURLCase = this.isValidBypassUrl(this.bypassUrls[i].trim());

						if (inValidWebURLCase == AimsConstants.URL_ALREADY_EXISTS_IN_WHITE_LIST) {
							errors.add(	ActionErrors.GLOBAL_MESSAGE,
											new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.urlexistsinwhitelist",
													this.bypassUrls[i]));
						} else if (inValidWebURLCase == AimsConstants.DOMAIN_NAME_DOES_NOT_EXISTS) {
							errors.add(	ActionErrors.GLOBAL_MESSAGE,
											new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.reverseLookUp",
													this.bypassUrls[i]));
						} else if (!(inValidWebURLCase == AimsConstants.URL_IS_VALID)) {
							errors.add(	ActionErrors.GLOBAL_MESSAGE,
											new ActionMessage(	"error.WapOptoutForm.bypassUrl.invalid.urlIsInvalid",
													this.bypassUrls[i]));
						}

						/*   		
						   		switch (inValidWebURLCase) {
									case AimsConstants.URL_IS_VALID:
										break;
									case AimsConstants.URL_CONTAINS_INVALID_CHARECTORS_AT_START:
										errors.add(ActionErrors.GLOBAL_MESSAGE, 
												new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.urlIsInvalid",this.bypassUrls[i]));
										break;
									case AimsConstants.URL_CONTAINS_INVALID_CHARECTORS_SOMEWHERE:
										errors.add(ActionErrors.GLOBAL_MESSAGE, 
												new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.urlIsInvalid",this.bypassUrls[i]));
										break;
									case AimsConstants.URL_ENDS_WITH_MOBI:
										errors.add(ActionErrors.GLOBAL_MESSAGE, 
												new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.urlIsInvalid",this.bypassUrls[i]));
										break;
									case AimsConstants.URL_ENDS_WITH_INVALID_CHAR:
										errors.add(ActionErrors.GLOBAL_MESSAGE, 
												new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.urlIsInvalid",this.bypassUrls[i]));
										break;
									case AimsConstants.URL_CONTAINS_WHITESPACES:
										errors.add(ActionErrors.GLOBAL_MESSAGE, 
												new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.urlIsInvalid",this.bypassUrls[i]));
										break;
									case AimsConstants.DOMAIN_NAME_DOES_NOT_EXISTS:
										errors.add(ActionErrors.GLOBAL_MESSAGE, 
												new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.urlIsInvalid",this.bypassUrls[i]));
										break;
									case AimsConstants.URL_IS_INVALID:
										errors.add(ActionErrors.GLOBAL_MESSAGE, 
												new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.urlIsInvalid",this.bypassUrls[i]));
										break;
									case AimsConstants.URL_ALREADY_EXISTS_IN_WHITE_LIST:
										errors.add(ActionErrors.GLOBAL_MESSAGE, 
												new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.urlexistsinwhitelist",this.bypassUrls[i]));
										break;
										case AimsConstants.URL_ENDS_WITH_WML_OR_XHTML:
										errors.add(ActionErrors.GLOBAL_MESSAGE, 
												new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.urlIsInvalid",this.bypassUrls[i]));
										break;
									default:
										errors.add(ActionErrors.GLOBAL_MESSAGE, 
												new ActionMessage("error.WapOptoutForm.bypassUrl.invalid.urlIsInvalid",this.bypassUrls[i]));
									}
						 */
					}
				}
			}
			if (isEmpty) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.bypassUrls"));
				this.bypassUrls = null;
			}

			if (this.isBlankString(this.adminCompanyName)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.adminCompanyName"));
			}
			if (this.isBlankString(this.adminFirstName)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.adminFirstName"));
			}
			if (this.isBlankString(this.adminLastName)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.adminLastName"));
			}
			if (this.isBlankString(this.adminPhoneNumber)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.adminPhoneNumber"));
			}
			if (this.isBlankString(this.adminEmailAddress)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.required.adminEmailAddress"));
			} else if (this.isValidEmail(this.adminEmailAddress) == false) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.WapOptoutForm.invalid.adminEmailAddress"));
			}		
		}
		return errors;
	}

	public void validateToDBFields(ActionErrors errors) {
		if (this.isBlankString(this.companyName) == false
				&& this.companyName.length() > 255) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.companyName"));
		}
		if (this.isBlankString(this.reqFirstName) == false
				&& this.reqFirstName.length() > 100) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.reqFirstName"));
		}
		if (this.isBlankString(this.reqLastName) == false
				&& this.reqLastName.length() > 100) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.reqLastName"));
		}
		if (this.isBlankString(this.streetAddress) == false
				&& this.streetAddress.length() > 255) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.streetAddress"));
		}
		if (this.isBlankString(this.reqEmailAddress) == false
				&& this.reqEmailAddress.length() > 200) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.reqEmailAddress"));
		}
		if (this.isBlankString(this.city) == false && this.city.length() > 100) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.city"));
		}
		if (this.isBlankString(this.state) == false
				&& this.state.length() > 100) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.state"));
		}
		if (this.isBlankString(this.zipCode) == false
				&& this.zipCode.length() > 20) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.zipCode"));
		}
		if (this.isBlankString(this.country) == false
				&& this.country.length() > 100) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.country"));
		}
		if (this.isBlankString(this.reqPhoneNumber) == false
				&& this.reqPhoneNumber.length() > 50) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.reqPhoneNumber"));
		}
		if (this.bypassUrls != null && this.bypassUrls.length > 0) {
			for (int i = 0; i < this.bypassUrls.length; i++) {
				if (!isBlankString(this.bypassUrls[i])
						&& this.bypassUrls[i].length() > 255) {
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
							"error.WapOptoutForm.length.URL",
							this.bypassUrls[i]));
				}
			}
		}
		if (this.isBlankString(this.adminCompanyName) == false
				&& this.adminCompanyName.length() > 255) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.adminCompanyName"));
		}
		if (this.isBlankString(this.adminFirstName) == false
				&& this.adminFirstName.length() > 100) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.adminFirstName"));
		}
		if (this.isBlankString(this.adminLastName) == false
				&& this.adminLastName.length() > 100) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.adminLastName"));
		}
		if (this.isBlankString(this.adminPhoneNumber) == false
				&& this.adminPhoneNumber.length() > 50) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.adminPhoneNumber"));
		}
		if (this.isBlankString(this.adminEmailAddress) == false
				&& this.adminEmailAddress.length() > 200) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"error.WapOptoutForm.length.adminEmailAddress"));
		}

	}

	/**
	 * Returns the value in integer if it is voilation any rule regarding the 
	 * wap opt-out url validation
	 * @author mnauman
	 * @param url
	 * @return -1 if default OR value of rule voilated
	 */

	public int isValidBypassUrl(String url) {
		if (log.isInfoEnabled()) {
			log.info("validating :-----> " + url);
		}

		try {

			if ("".equals(url)) {
				if (log.isInfoEnabled()) {
					log.info(" url  " + url + " is blank ");
				}
				return AimsConstants.URL_IS_INVALID;
			}

			if (((Pattern.compile("(.)*[\\s](.)*")).matcher(url)).matches()) {
				if (log.isInfoEnabled()) {
					log.info(" url  " + url + " contains spaces");
				}
				return AimsConstants.URL_IS_INVALID;
			}

			if (((Pattern.compile("(.)*://(.)*")).matcher(url)).matches()) {
				if (log.isInfoEnabled()) {
					log.info(" url  " + url + " contains ://");
				}
				return AimsConstants.URL_IS_INVALID;
			}

			if (((Pattern.compile("(.)*:[\\d](.)*")).matcher(url)).matches()) {
				if (log.isInfoEnabled()) {
					log.info(" url  " + url + " contains port ");
				}
				return AimsConstants.URL_IS_INVALID;
			}

			if (((Pattern.compile("^(.)*\\*([^\\.])(.)*$")).matcher(url))
					.matches()) {
				if (log.isInfoEnabled()) {
					log.info(" url  " + url + " contains charectors after *");
				}
				return AimsConstants.URL_IS_INVALID;
			}

			if (((Pattern.compile("^(.)*\\.\\*$")).matcher(url)).matches()) {
				if (log.isInfoEnabled()) {
					log.info(" url  " + url + " contains  .* @ end");
				}
				return AimsConstants.URL_IS_INVALID;
			}
			
			if (WapOptoutManager.doesUrlExistsInWhitelist(url)) {
				if (log.isDebugEnabled()) {
					log.debug(" url exists in white list  ");
				}
				return AimsConstants.URL_ALREADY_EXISTS_IN_WHITE_LIST;
			}

			String urlToPing = WapoptoutUtil.extractDomain(url);

			if ("".equals(urlToPing)) {
				if (log.isInfoEnabled()) {
					log.info(" url  " + url + " valid url canot be extracted");
				}
				return AimsConstants.URL_IS_INVALID;
			}

			try {
				InetAddress address = InetAddress.getByName(urlToPing);

				if (log.isInfoEnabled())
					log.info(" ping successfull for url:'" + urlToPing
							+ "' and returned address:'"
							+ address.getHostName().toString() + "'  and ip:'"
							+ address.getHostAddress() + "'");

			} catch (UnknownHostException e) {
				if (log.isInfoEnabled())
					log.info(" ping unsuccessfull for url " + urlToPing);
				return AimsConstants.DOMAIN_NAME_DOES_NOT_EXISTS;
			}

			if (log.isDebugEnabled()) {
				log.debug(" URL:" + url + ": is valid");
			}
			return AimsConstants.URL_IS_VALID;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	private Collection getCountriesList() {
		Collection countryList = null;
		try {
			countryList = AllianceCompInfoManager.getAllCountries();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return countryList;
	}

}
