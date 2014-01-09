package com.netpace.vic.servlet;

import com.netpace.vic.dto.UserApplication;
import com.netpace.vic.service.UserApplicationService;
import com.netpace.vic.util.Utility;
import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({
    @Property(name = "service.description", value = "Servlet to save application data"),
    @Property(name = "service.vendor", value = "Netpace"),
    @Property(name = "sling.servlet.paths", value = {"/bin/submitApplication"
    })
})
public class ApplicationServlet extends SlingAllMethodsServlet {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("ApplicationServlet.doPost========================Start");
        BundleContext bundleContext = FrameworkUtil.getBundle(ApplicationServlet.class).getBundleContext();
        ServiceReference factoryRef = bundleContext.getServiceReference(UserApplicationService.class.getName());
        UserApplicationService applicationService = (UserApplicationService) bundleContext.getService(factoryRef);
        
        //section-1
        String companyName = request.getParameter("companyName");
        String website = request.getParameter("website");
        String stageOfCompany = request.getParameter("stageOfCompany");
        String numberOfEmployees = request.getParameter("numberOfEmployees");
        String revenueGeneration = request.getParameter("revenueGeneration");
        String reasonForEnagaging = request.getParameter("reasonForEnagaging");
        
        //section-2
        String productIdea = request.getParameter("productIdea");
        String projectDevelopmentStage = request.getParameter("projectDevelopmentStage");
        String connectivityUtilization = request.getParameter("connectivityUtilization");
        String[] productDataSpeed = request.getParameterValues("productDataSpeed");
        String[] programImmediateHelp = request.getParameterValues("programImmediateHelp");
        String programImmediateHelpOther = request.getParameter("programImmediateHelpOther");
        
        //section-3
        String verticalIndustry = request.getParameter("verticalIndustry");
        String existingPartnerships = request.getParameter("existingPartnerships");
        String[] typeOfFiniancing = request.getParameterValues("typeOfFiniancing");
        String typeOfFiniancingOther = request.getParameter("typeOfFiniancingOther");
        String projectBusinessModel = request.getParameter("projectBusinessModel");
        String[] targetCustomers = request.getParameterValues("targetCustomers");
        String targetCustomersOther = request.getParameter("targetCustomersOther");
        String productUsage = request.getParameter("productUsage");
        String existingSalesChannels = request.getParameter("existingSalesChannels");
        String companiesWorkingSameSpace = request.getParameter("companiesWorkingSameSpace");
        String oneYearMarketSales = request.getParameter("oneYearMarketSales");
                
        //section-4
        String contactName = request.getParameter("contactName");
        String contactPosition = request.getParameter("contactPosition");
        String contactEmail = request.getParameter("contactEmail");
        String contactPhone = request.getParameter("contactPhone");
        String contactSalesforceIdentifier = request.getParameter("contactSalesforceIdentifier");
        String contactVerizonSalesRep = request.getParameter("contactVerizonSalesRep");
        
        UserApplication application = new UserApplication();
        
        application.setCompanyName(companyName);
        application.setWebsite(website);
        application.setStageOfCompany(stageOfCompany);
        application.setNumberOfEmployees(numberOfEmployees);
        application.setRevenueGeneration(revenueGeneration);
        application.setReasonForEnagaging(reasonForEnagaging);
        application.setProductIdea(productIdea);
        application.setProjectDevelopmentStage(projectDevelopmentStage);
        application.setConnectivityUtilization(connectivityUtilization);
        application.setProductDataSpeed(Utility.getStringFromArray(productDataSpeed));
        application.setProgramImmediateHelp(Utility.getStringFromArray(programImmediateHelp));
        if(StringUtils.isNotEmpty(programImmediateHelpOther)){
            application.setProgramImmediateHelp(application.getProgramImmediateHelp()+":"+programImmediateHelpOther);
        }
        application.setVerticalIndustry(verticalIndustry);
        application.setExistingPartnerships(existingPartnerships);
        application.setTypeOfFiniancing(Utility.getStringFromArray(typeOfFiniancing));
        if(StringUtils.isNotEmpty(typeOfFiniancingOther)){
            application.setTypeOfFiniancing(application.getTypeOfFiniancing()+":"+typeOfFiniancingOther);
        }
        application.setProjectBusinessModel(projectBusinessModel);
        application.setTargetCustomers(Utility.getStringFromArray(targetCustomers));
        if(StringUtils.isNotEmpty(targetCustomersOther)){
            application.setTargetCustomers(application.getTargetCustomers()+":"+targetCustomersOther);
        }
 
        application.setProductUsage(productUsage);
        application.setExistingSalesChannels(existingSalesChannels);
        application.setCompaniesWorkingSameSpace(companiesWorkingSameSpace);
        application.setOneYearMarketSales(oneYearMarketSales);

        application.setContactName(contactName);
        application.setContactPosition(contactPosition);
        application.setContactEmail(contactEmail);
        application.setContactPhone(contactPhone);
        application.setContactSalesforceIdentifier(contactSalesforceIdentifier);
        application.setContactVerizonSalesRep(contactVerizonSalesRep);
        
        LOGGER.info("\n\nSubmitted Data: "+application+"\n\n");
        
        applicationService.saveUserApplication(application);
        
        request.getSession().setAttribute("START_APP",null);
        LOGGER.info("ApplicationServlet.doPost========================End");
        response.sendRedirect("/content/vic/startApp/thankyou.html");
        
    }
}
