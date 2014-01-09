package com.netpace.device.vo;

import static com.netpace.commons.utils.ValidationUtil.*;
import com.netpace.device.utils.VAPConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component(value = "activateAdminValidator")
public class ActivateAdminValidator extends LocalValidatorFactoryBean implements Validator {

    @Autowired
    protected MessageSource messageSource;

    @Override
    public boolean supports(Class<?> type) {
        return ActivateAdminVO.class.equals(type);
    }

    @Override
    public void validate(Object registration, Errors errors) {

        ActivateAdminVO activateAdminVO = (ActivateAdminVO) registration;

        if (isNotEmpty(errors, messageSource, "fullName", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "fullName", "Pattern", VAPConstants.REGEX_STD_FULLNAME)) {
                isValidLength(errors, messageSource, "fullName", "Length", null, 70);
            }
        }

        if (isNotEmpty(errors, messageSource, "phoneNumber", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "phoneNumber", "Pattern", VAPConstants.REGEX_STD_PHONE)) {
                isValidLength(errors, messageSource, "phoneNumber", "Length", null, 20);
            }
        }

        if (StringUtils.isNotBlank(activateAdminVO.getMobilePhoneNumber())) {
            if (isValidPattern(errors, messageSource, "mobilePhoneNumber", "Pattern", VAPConstants.REGEX_STD_PHONE)) {
                isValidLength(errors, messageSource, "mobilePhoneNumber", "Length", null, 20);
            }
        }

        if (isNotEmpty(errors, messageSource, "emailAddress", "NotBlank")) {
            if (isValidEmail(errors, messageSource, "emailAddress", "Pattern")) {
                isValidLength(errors, messageSource, "emailAddress", "Length", null, 100);
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
