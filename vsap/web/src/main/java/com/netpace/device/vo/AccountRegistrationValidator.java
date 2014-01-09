package com.netpace.device.vo;

import static com.netpace.commons.utils.ValidationUtil.isBothMatches;
import static com.netpace.commons.utils.ValidationUtil.isBothMatchesIgnoreCase;
import static com.netpace.commons.utils.ValidationUtil.isNotEmpty;
import static com.netpace.commons.utils.ValidationUtil.isValidDomain;
import static com.netpace.commons.utils.ValidationUtil.isValidEmail;
import static com.netpace.commons.utils.ValidationUtil.isValidLength;
import static com.netpace.commons.utils.ValidationUtil.isValidPattern;
import static com.netpace.commons.utils.ValidationUtil.isValidRange;
import static com.netpace.commons.utils.ValidationUtil.isValidDomainEmail;
import static com.netpace.commons.utils.ValidationUtil.isValidDomainEmailIgnoreCase;
import com.netpace.device.utils.VAPConstants;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component(value = "accountRegistrationValidator")
public class AccountRegistrationValidator extends LocalValidatorFactoryBean implements Validator {
    
    private static final Log log = LogFactory.getLog(AccountRegistrationValidator.class);

    @Autowired
    protected MessageSource messageSource;

    @Override
    public boolean supports(Class<?> type) {
        return AccountRegistration.class.equals(type);
    }

    @Override
    public void validate(Object registration, Errors errors) {

        AccountRegistration accountRegistration = (AccountRegistration) registration;

        if (isNotEmpty(errors, messageSource, "fullName", "NotBlank")) {
            Object fieldValue = errors.getFieldValue("fullName");
            if(fieldValue != null){
                try {
                    log.info("fullName: ["+ errors.getFieldValue("fullName")+"]");
                    log.info("fullName bytearray: ["+ToStringBuilder.reflectionToString(errors.getFieldValue("fullName").toString().getBytes("UTF16"))+"]");
                    log.info("REGEX_STD_FULLNAME: ["+VAPConstants.REGEX_STD_FULLNAME+"]");
                    log.info("REGEX_STD_FULLNAME bytearray: ["+ToStringBuilder.reflectionToString(VAPConstants.REGEX_STD_FULLNAME.getBytes("UTF16"))+"]");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(AccountRegistrationValidator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (isValidPattern(errors, messageSource, "fullName", "Pattern", VAPConstants.REGEX_STD_FULLNAME)) {
                isValidLength(errors, messageSource, "fullName", "Length", null, 70);
            }
        }

        if (isNotEmpty(errors, messageSource, "phoneNumber", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "phoneNumber", "Pattern", VAPConstants.REGEX_STD_PHONE)) {
                isValidLength(errors, messageSource, "phoneNumber", "Length", null, 20);
            }
        }

        if (StringUtils.isNotBlank(accountRegistration.getMobilePhoneNumber())) {
            if (isValidPattern(errors, messageSource, "mobilePhoneNumber", "Pattern", VAPConstants.REGEX_STD_PHONE)) {
                isValidLength(errors, messageSource, "mobilePhoneNumber", "Length", null, 20);
            }
        }

        if (isNotEmpty(errors, messageSource, "emailAddress", "NotBlank")) {
            if (isValidEmail(errors, messageSource, "emailAddress", "Pattern")) {
                isValidLength(errors, messageSource, "emailAddress", "Length", null, 100);
            }
        }

        if (isNotEmpty(errors, messageSource, "confirmEmailAddress", "NotBlank")) {
            isBothMatchesIgnoreCase(errors, messageSource, "emailAddress", "confirmEmailAddress", "Equal");
        }

        if (isNotEmpty(errors, messageSource, "companyDomain", "NotBlank")) {
            if (isValidDomain(errors, messageSource, "companyDomain", "Pattern")) {
                isValidLength(errors, messageSource, "companyDomain", "Length", null, 250);
            }
        }

        // If email domain and company domain are same
        if (StringUtils.isNotBlank(accountRegistration.getEmailAddress())
                && StringUtils.isNotBlank(accountRegistration.getCompanyDomain())) {
            List list = errors.getFieldErrors("emailAddress");
            List list1 = errors.getFieldErrors("companyDomain");

            if ((list == null || list.size() == 0) && (list1 == null || list1.size() == 0)) {
                isValidDomainEmailIgnoreCase(errors, messageSource, "emailAddress", "Match", accountRegistration.getCompanyDomain().toLowerCase(), "Company domain");
            }
        }

        if (isNotEmpty(errors, messageSource, "userName", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "userName", "Pattern", VAPConstants.REGEX_STD_USERNAME)) {
                isValidLength(errors, messageSource, "userName", "Length", 6, 25);
            }
        }

        if (isNotEmpty(errors, messageSource, "password", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "password", "Pattern", VAPConstants.REGEX_STD_PASSWORD)) {
                isValidLength(errors, messageSource, "password", "Length", 6, 25);
            }
        }

        if (isNotEmpty(errors, messageSource, "confirmPassword", "NotBlank")) {
            isBothMatches(errors, messageSource, "password", "confirmPassword", "Equal");
        }

        if (isNotEmpty(errors, messageSource, "address", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "address", "Pattern", VAPConstants.REGEX_STD_ADDRESS)) {
                isValidLength(errors, messageSource, "address", "Length", null, 250);
            }
        }

        if (isNotEmpty(errors, messageSource, "city", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "city", "Pattern", VAPConstants.REGEX_STD_CITY)) {
                isValidLength(errors, messageSource, "city", "Length", null, 100);
            }
        }

        if (isNotEmpty(errors, messageSource, "state", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "state", "Pattern", VAPConstants.REGEX_STD_STATE)) {
                isValidLength(errors, messageSource, "state", "Length", null, 100);
            }
        }

        isNotEmpty(errors, messageSource, "country", "NotBlank");

        if (isNotEmpty(errors, messageSource, "zip", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "zip", "Pattern", VAPConstants.REGEX_STD_ZIPCODE)) {
                isValidLength(errors, messageSource, "zip", "Length", null, 20);
            }
        }
    }
}