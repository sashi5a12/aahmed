package com.netpace.device.vo;

import static com.netpace.commons.utils.ValidationUtil.*;
import com.netpace.device.utils.VAPConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component(value = "changePasswordValidator")
public class ChangePasswordValidator extends LocalValidatorFactoryBean implements Validator {

    @Autowired
    protected MessageSource messageSource;

    @Override
    public boolean supports(Class<?> type) {
        return ChangePasswordVO.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangePasswordVO changePasswordVO = (ChangePasswordVO) target;

        isNotEmpty(errors, messageSource, "currentPassword", "NotBlank");

        if (isNotEmpty(errors, messageSource, "createPassword", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "createPassword", "Pattern", VAPConstants.REGEX_STD_PASSWORD)) {
                isValidLength(errors, messageSource, "createPassword", "Length", 6, 25);
            }
        }

        if (isNotEmpty(errors, messageSource, "confirmPassword", "NotBlank")) {
            isBothMatches(errors, messageSource, "createPassword", "confirmPassword", "Equal");
        }
    }
}
