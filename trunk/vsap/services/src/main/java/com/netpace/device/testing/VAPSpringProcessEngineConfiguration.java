package com.netpace.device.testing;

import java.util.List;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

public class VAPSpringProcessEngineConfiguration extends SpringProcessEngineConfiguration {

    private Log log = LogFactory.getLog(this.getClass());

    @Override
    protected void autoDeployResources(ProcessEngine processEngine) {
        if (deploymentResources != null && deploymentResources.length > 0) {
            for (Resource resource : deploymentResources) {
                log.info("file name : " + resource.getFilename());

                RepositoryService repoService = processEngine.getRepositoryService();
                DeploymentQuery dq = repoService.createDeploymentQuery();
                List<Deployment> deploymentList = dq.deploymentName("SpringAutoDeployment").orderByDeploymenTime().desc().list();
                if (!deploymentList.isEmpty()) {
                    Deployment existingDeployment = deploymentList.get(0);
                    log.info("Deployment Info : " + existingDeployment.getName() + "\t:\t" + existingDeployment.getId() + "\t:\t" + existingDeployment.getDeploymentTime());
                    repoService.getResourceAsStream(existingDeployment.getId(),"D:\\projects\\Device Accessories\\Source\\trunk\\services\\target\\classes\\spring-test-activiti.bpmn20.xml");
                }
            }
        }
    }
}
