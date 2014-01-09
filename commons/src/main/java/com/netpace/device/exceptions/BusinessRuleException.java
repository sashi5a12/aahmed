package com.netpace.device.exceptions;

import com.netpace.device.enums.BusinessRule;

public class BusinessRuleException extends RuntimeException{

	private static final long serialVersionUID = 1789894049479870503L;
	
	private BusinessRule rule;

    public BusinessRuleException(BusinessRule rule) {
        super(rule.getMessage());
        this.rule = rule;
    }

    public BusinessRule getBusinessRule() {
        return rule;
    }
}
