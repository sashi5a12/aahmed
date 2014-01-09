package com.netpace.device.vo;

import static com.netpace.commons.utils.ValidationUtil.*;
import com.netpace.device.utils.VAPConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component(value = "changeUsernameValidator")
public class ChangeUsernameValidator extends LocalValidatorFactoryBean implements Validator {

    @Autowired
    protected MessageSource messageSource;

    @Override
    public boolean supports(Class<?> type) {
        return ChangeUserNameVO.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangeUserNameVO changeUserNameVO = (ChangeUserNameVO) target;

        if (isNotEmpty(errors, messageSource, "userName", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "userName", "Pattern", VAPConstants.REGEX_STD_USERNAME)) {
                isValidLength(errors, messageSource, "userName", "Length", 6, 25);
            }
        }

        isNotEmpty(errors, messageSource, "currentPassword", "NotBlank");
    }
}
