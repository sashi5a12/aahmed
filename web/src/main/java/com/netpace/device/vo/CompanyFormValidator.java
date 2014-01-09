package com.netpace.device.vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import com.netpace.device.utils.VAPConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Validator;

import static com.netpace.commons.utils.ValidationUtil.*;

@Component
public class CompanyFormValidator extends LocalValidatorFactoryBean implements Validator {

    @Autowired
    protected MessageSource messageSource;

    @Override
    public boolean supports(Class<?> type) {
        return CompanyRegistration.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CompanyRegistration companyRegistration = (CompanyRegistration) target;

        if (isNotEmpty(errors, messageSource, "companyName", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "companyName", "Pattern", VAPConstants.REGEX_COMPANY_NAME)) {
                isValidLength(errors, messageSource, "companyName", "Length", null, 200);
            }
        }

        if (isNotEmpty(errors, messageSource, "companyLegalName", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "companyLegalName", "Pattern", VAPConstants.REGEX_COMPANY_NAME)) {
                isValidLength(errors, messageSource, "companyLegalName", "Length", null, 200);
            }
        }

        if (isNotEmpty(errors, messageSource, "website", "NotBlank")) {
            if (isValidUrl(errors, messageSource, "website", "Pattern")) {
                isValidLength(errors, messageSource, "website", "Length", null, 250);
            }
        }

        if (StringUtils.isNotBlank(companyRegistration.getMainPhoneNumber())) {
            if (isValidPattern(errors, messageSource, "mainPhoneNumber", "Pattern", VAPConstants.REGEX_STD_PHONE)) {
                isValidLength(errors, messageSource, "mainPhoneNumber", "Length", null, 25);
            }
        }
        if (isNotEmpty(errors, messageSource, "mainCompanyStreetAddress", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "mainCompanyStreetAddress", "Pattern", VAPConstants.REGEX_STD_ADDRESS)) {
                isValidLength(errors, messageSource, "mainCompanyStreetAddress", "Length", null, 250);
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

        if (isNotEmpty(errors, messageSource, "salesContact.name", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "salesContact.name", "Pattern", VAPConstants.REGEX_STD_FULLNAME)) {
                isValidLength(errors, messageSource, "salesContact.name", "Length", null, 70);
            }
        }

        if (isNotEmpty(errors, messageSource, "salesContact.phone", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "salesContact.phone", "Pattern", VAPConstants.REGEX_STD_PHONE)) {
                isValidLength(errors, messageSource, "salesContact.phone", "Length", null, 25);
            }
        }

        if (StringUtils.isNotBlank(companyRegistration.getSalesContact().getMobile())) {
            if (isValidPattern(errors, messageSource, "salesContact.mobile", "Pattern", VAPConstants.REGEX_STD_PHONE)) {
                isValidLength(errors, messageSource, "salesContact.mobile", "Length", null, 25);
            }
        }

        if (isNotEmpty(errors, messageSource, "salesContact.emailAddress", "NotBlank")) {
            if (isValidEmail(errors, messageSource, "salesContact.emailAddress", "Pattern")) {
                isValidLength(errors, messageSource, "salesContact.emailAddress", "Length", null, 100);
            }
        }
    }
}
