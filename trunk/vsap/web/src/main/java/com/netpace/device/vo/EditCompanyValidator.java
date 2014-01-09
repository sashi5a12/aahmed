package com.netpace.device.vo;

import static com.netpace.commons.utils.ValidationUtil.isNotEmpty;
import static com.netpace.commons.utils.ValidationUtil.isValidEmail;
import static com.netpace.commons.utils.ValidationUtil.isValidLength;
import static com.netpace.commons.utils.ValidationUtil.isValidPattern;
import static com.netpace.commons.utils.ValidationUtil.isValidUrl;
import com.netpace.device.utils.VAPConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 *
 * @author Noorain
 */
@Component
public class EditCompanyValidator extends LocalValidatorFactoryBean implements Validator {

    @Autowired
    protected MessageSource messageSource;

    @Override
    public boolean supports(Class<?> type) {
        return CompanyProfileWithActiveUser.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CompanyProfileWithActiveUser companyProfileWithActiveUser = (CompanyProfileWithActiveUser) target;

        if (isNotEmpty(errors, messageSource, "company.companyName", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "company.companyName", "Pattern", VAPConstants.REGEX_COMPANY_NAME)) {
                isValidLength(errors, messageSource, "company.companyName", "Length", null, 200);
            }
        }

        if (isNotEmpty(errors, messageSource, "company.companyLegalName", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "company.companyLegalName", "Pattern", VAPConstants.REGEX_COMPANY_NAME)) {
                isValidLength(errors, messageSource, "company.companyLegalName", "Length", null, 200);
            }
        }

        if (isNotEmpty(errors, messageSource, "company.website", "NotBlank")) {
            if (isValidUrl(errors, messageSource, "company.website", "Pattern")) {
                isValidLength(errors, messageSource, "company.website", "Length", null, 250);
            }
        }

        if (StringUtils.isNotBlank(companyProfileWithActiveUser.getCompany().getMainPhoneNumber())) {
            if (isValidPattern(errors, messageSource, "company.mainPhoneNumber", "Pattern", VAPConstants.REGEX_STD_PHONE)) {
                isValidLength(errors, messageSource, "company.mainPhoneNumber", "Length", null, 25);
            }
        }

        if (isNotEmpty(errors, messageSource, "company.mainCompanyStreetAddress", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "company.mainCompanyStreetAddress", "Pattern", VAPConstants.REGEX_STD_ADDRESS)) {
                isValidLength(errors, messageSource, "company.mainCompanyStreetAddress", "Length", null, 250);
            }
        }

        if (isNotEmpty(errors, messageSource, "company.city", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "company.city", "Pattern", VAPConstants.REGEX_STD_CITY)) {
                isValidLength(errors, messageSource, "company.city", "Length", null, 100);
            }
        }

        if (isNotEmpty(errors, messageSource, "company.state", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "company.state", "Pattern", VAPConstants.REGEX_STD_STATE)) {
                isValidLength(errors, messageSource, "company.state", "Length", null, 100);
            }
        }

        isNotEmpty(errors, messageSource, "company.country", "NotBlank");

        if (isNotEmpty(errors, messageSource, "company.zip", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "company.zip", "Pattern", VAPConstants.REGEX_STD_ZIPCODE)) {
                isValidLength(errors, messageSource, "company.zip", "Length", null, 20);
            }
        }

        if (isNotEmpty(errors, messageSource, "company.salesContact.name", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "company.salesContact.name", "Pattern", VAPConstants.REGEX_STD_FULLNAME)) {
                isValidLength(errors, messageSource, "company.salesContact.name", "Length", null, 70);
            }
        }

        if (isNotEmpty(errors, messageSource, "company.salesContact.phone", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "company.salesContact.phone", "Pattern", VAPConstants.REGEX_STD_PHONE)) {
                isValidLength(errors, messageSource, "company.salesContact.phone", "Length", null, 25);
            }
        }

        if (StringUtils.isNotBlank(companyProfileWithActiveUser.getCompany().getSalesContact().getMobile())) {
            if (isValidPattern(errors, messageSource, "company.salesContact.mobile", "Pattern", VAPConstants.REGEX_STD_PHONE)) {
                isValidLength(errors, messageSource, "company.salesContact.mobile", "Length", null, 25);
            }
        }

        if (isNotEmpty(errors, messageSource, "company.salesContact.emailAddress", "NotBlank")) {
            if (isValidEmail(errors, messageSource, "company.salesContact.emailAddress", "Pattern")) {
                isValidLength(errors, messageSource, "company.salesContact.emailAddress", "Length", null, 100);
            }
        }
    }
}
