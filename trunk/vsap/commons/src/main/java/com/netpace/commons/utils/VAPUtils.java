package com.netpace.commons.utils;

import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.CompanyVO;
import com.netpace.device.vo.FilterModel;
import com.netpace.device.vo.FilterVal;
import com.netpace.device.vo.VAPUserDetails;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author aahmed
 */
public class VAPUtils {

    public static List<String> getFilterValueList(List<FilterModel> filters) {
        List<String> filterValues = new ArrayList<String>();
        if (filters != null && filters.size() > 0) {
            for (FilterModel filterModel : filters) {
                for (FilterVal filterVal : filterModel.getFilterValues()) {
                    if (filterVal.isSelected()) {
                        filterValues.add(filterVal.getTitle());
                    }
                }
            }
        }
        return filterValues;
    }

    public static List<String> getFilterValues(FilterModel filter) {
        List<String> filterValues = new ArrayList<String>();
        if (filter.getFilterValues() != null && filter.getFilterValues().size() > 0) {
            for (FilterVal filterVal : filter.getFilterValues()) {
                if (filterVal.isSelected()) {
                    filterValues.add(filterVal.getTitle().trim());
                }
            }
        }
        return filterValues;
    }

    public static String getFileExtension(String fileName) {
        String fname = "";
        String ext = "";
        if (fileName != null && fileName.indexOf(".") != -1) {
            int mid = fileName.lastIndexOf(".");
            fname = fileName.substring(0, mid);
            ext = fileName.substring(mid + 1, fileName.length());
        }
        return ext;
    }

    public static List<String> getListFromCommaValues(String values) {
        List<String> list = new ArrayList<String>();
        String[] strArr = values.split(",");
        for (String s : strArr) {
            list.add(s);
        }
        return list;
    }

    public static String formatWorkflowSteps(String workflowSteps) {
        if (StringUtils.isNotEmpty(workflowSteps)) {
            StringBuilder buff = new StringBuilder();
            String[] steps = workflowSteps.split(",");
            for (String s : steps) {
                if (s.indexOf("Offline Certification Agreement") > -1 && s.indexOf("Denied") == -1){
                    continue;
                } else if (s.indexOf("Denied") == -1 && !s.equals("Concept Approved") && s.indexOf("Suspend") == -1) {
                    buff.append(s + " - In Progress");
                } else {
                    buff.append(s);
                }
            }
            return buff.toString();
        } else {
            return "Approved";
        }
    }

    public static String getFormattedURL(String scheme, String serverName, Integer serverPort, String contextPath, String resourcePath) {
        final Integer DEFAULT_PORT = 80;
        StringBuilder fromatedURL = new StringBuilder();

        fromatedURL.append(String.format("%s://", scheme));

        if (serverPort != DEFAULT_PORT) {
            fromatedURL.append(String.format("%s:%d", serverName, serverPort));
        } else {
            fromatedURL.append(serverName);
        }

        if (StringUtils.isNotBlank(contextPath)) {
            fromatedURL.append(contextPath);
        }

        if (StringUtils.isNotBlank(resourcePath)) {
            fromatedURL.append(resourcePath);
        }

        return fromatedURL.toString();
    }

    public static boolean canVerizonUserDeleteProduct(VAPUserDetails loggedInUser) {
        String roles = loggedInUser.commaSeparatedRolesList();
        if (StringUtils.contains(roles, VAPConstants.ROLE_SUPER_ADMIN)) {
            return true;
        } else if (StringUtils.contains(roles, VAPConstants.ROLE_VERIZON_ADMIN)) {
            return true;
        }
        return false;
    }

    public static boolean canVerizonUserDeleteProductAttachment(VAPUserDetails loggedInUser) {
        String roles = loggedInUser.commaSeparatedRolesList();
        if (StringUtils.contains(roles, VAPConstants.ROLE_SUPER_ADMIN)) {
            return true;
        } else if (StringUtils.contains(roles, VAPConstants.ROLE_VERIZON_ADMIN)) {
            return true;
        } else if (StringUtils.contains(roles, VAPConstants.ROLE_DEVICE_MARKETING)) {
            return true;
        }
        return false;
    }

    public static boolean canVerizonUserEditProduct(VAPUserDetails loggedInUser) {
        String roles = loggedInUser.commaSeparatedRolesList();
        if (StringUtils.contains(roles, VAPConstants.ROLE_SUPER_ADMIN)) {
            return true;
        } else if (StringUtils.contains(roles, VAPConstants.ROLE_VERIZON_ADMIN)) {
            return true;
        } else if (StringUtils.contains(roles, VAPConstants.ROLE_DEVICE_MARKETING)) {
            return true;
        }
        return false;
    }

    public static boolean canVerizonUserViewProduct(VAPUserDetails loggedInUser){
    	String roles=loggedInUser.commaSeparatedRolesList();
    	if(StringUtils.contains(roles, VAPConstants.ROLE_SUPER_ADMIN)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_VERIZON_ADMIN)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_DEVICE_MARKETING)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_OFAC)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_REQUIREMENTS_GROUP)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_DEVICE_COMPLIANCE)){
    		return true;
    	}
    	else if(StringUtils.contains(roles, VAPConstants.ROLE_EXPORT_COMPLAINCE)){
    		return true;
    	}
    	return false;
    }
    public static boolean isCompanyDenied(CompanyVO companyVO) {

        String workFlowSteps = companyVO.getWorkFlowSteps();

        if (StringUtils.contains(workFlowSteps,
                VAPConstants.VAP_WORKFLOW_COMPANY_STEP_TITLE_DeviceMarketingReviewDenied)
                || StringUtils.contains(workFlowSteps,
                VAPConstants.VAP_WORKFLOW_COMPANY_STEP_TITLE_OfflineCertificationNDADenied)) {
            return true;
        }
        return false;
    }

    public static Date getDateOlderThanDays(Integer noOfDays) {
        Calendar olderDate = Calendar.getInstance();
        olderDate.add(Calendar.DAY_OF_YEAR, -noOfDays);
        return olderDate.getTime();
    }
}
