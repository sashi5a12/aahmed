package com.netpace.device.vo;

import static com.netpace.commons.utils.ValidationUtil.isBothMatches;
import static com.netpace.commons.utils.ValidationUtil.isNotEmpty;
import static com.netpace.commons.utils.ValidationUtil.isValidLength;
import static com.netpace.commons.utils.ValidationUtil.isValidPattern;
import com.netpace.device.utils.VAPConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "userInfoResendValidator")
public class UserInfoResendValidator extends BaseValidator {

    @Autowired
    protected MessageSource messageSource;

    @Override
    public boolean supports(Class<?> type) {
        return UserInfoResend.class.equals(type);
    }

    @Override
    public void validateForm(Object o, Errors errors) {
        UserInfoResend userInfoResend = (UserInfoResend) o;
        if ("passwordReset".equalsIgnoreCase(userInfoResend.getField())) {

            if (isNotEmpty(errors, messageSource, "newPassword", "NotBlank")) {
                if (isValidPattern(errors, messageSource, "newPassword", "Pattern", VAPConstants.REGEX_STD_PASSWORD)) {
                    isValidLength(errors, messageSource, "newPassword", "Length", 6, 25);
                }
            }

            if (isNotEmpty(errors, messageSource, "confirmPassword", "NotBlank")) {
                isBothMatches(errors, messageSource, "newPassword", "confirmPassword", "Equal");
            }

        } else {
            if ("username".equalsIgnoreCase(userInfoResend.getField())) {
                if (isNotEmpty(errors, messageSource, "emailAddress", "NotBlank")) {
                    isValidPattern(errors, messageSource, "emailAddress", "Pattern", VAPConstants.REGEX_STD_EMAIL);
                }
            } else if ("password".equalsIgnoreCase(userInfoResend.getField())) {
                if (isNotEmpty(errors, messageSource, "userName", "NotBlank")) {
                    isValidPattern(errors, messageSource, "userName", "Pattern", VAPConstants.REGEX_STD_USERNAME);
                }
            }
        }
    }
}
