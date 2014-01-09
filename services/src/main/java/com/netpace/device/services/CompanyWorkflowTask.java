package com.netpace.device.services;

import com.netpace.device.services.impl.VAPApplicationContext;
import java.util.Map;

public class CompanyWorkflowTask implements org.activiti.engine.delegate.JavaDelegate {
    
    @Override
    public void execute(org.activiti.engine.delegate.DelegateExecution delegateExecution) throws Exception {
        Map<String,Object> varArgsMap = delegateExecution.getVariables();

        
        CompanyService companyService = (CompanyService) VAPApplicationContext.getApplicationContext().getBean("companyService");
         //companyService.updateCompanyWorkflow(varArgsMap);
    }

}
