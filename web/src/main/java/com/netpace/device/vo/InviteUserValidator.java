package com.netpace.device.vo;

import static com.netpace.commons.utils.ValidationUtil.*;
import com.netpace.device.utils.VAPConstants;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component(value = "inviteUserValidator")
public class InviteUserValidator extends LocalValidatorFactoryBean implements Validator {

    @Autowired
    protected MessageSource messageSource;

    @Override
    public boolean supports(Class<?> type) {
        return InviteUserVO.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        InviteUserVO inviteUserVO = (InviteUserVO) target;

        if (isNotEmpty(errors, messageSource, "emailAddress", "NotBlank")) {
            if (isValidEmail(errors, messageSource, "emailAddress", "Pattern")) {
                isValidLength(errors, messageSource, "emailAddress", "Length", null, 100);
            }
        }

        if (isNotEmpty(errors, messageSource, "fullName", "NotBlank")) {
            if (isValidPattern(errors, messageSource, "fullName", "Pattern", VAPConstants.REGEX_STD_FULLNAME)) {
                isValidLength(errors, messageSource, "fullName", "Length", null, 70);
            }
        }
        
        // If email domain and company domain are same
        if (StringUtils.isNotBlank(inviteUserVO.getEmailAddress())
                && StringUtils.isNotBlank(inviteUserVO.getCompanyDomain())) {
            
            List list = errors.getFieldErrors("emailAddress");

            if (list == null || list.size() == 0) {
                isValidDomainEmail(errors, messageSource, "emailAddress", "Match", inviteUserVO.getCompanyDomain(), "Company domain");
            }
        }
    }
}
