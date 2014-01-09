package com.netpace.device.services.impl;

import com.netpace.device.entities.Company;
import com.netpace.device.entities.VapComment;
import com.netpace.device.entities.VapProduct;
import com.netpace.device.entities.VapProductProcessInfo;
import com.netpace.device.services.ApprovalService;
import com.netpace.device.utils.enums.ProductStatus;
import com.netpace.device.utils.enums.WorkflowAction;
import com.netpace.device.utils.enums.WorkflowStep;
import org.apache.commons.lang.StringUtils;

public abstract class AbstractApprovalServiceImpl implements ApprovalService {

    protected VapComment getComment(String title, String decision, String commentText,
            String workflowType, Company company, VapProduct product, String loggedInUserName) {
        VapComment comment = new VapComment();
        comment.setCommentsText(title + " - " + decision + " Email Text: " + StringUtils.defaultString(commentText));
        comment.setCommentsType(workflowType);
        comment.setCompany(company);
        comment.setProduct(product);
        comment.populatedAuditFields(loggedInUserName);
        return comment;
    }

    protected void setProductProcessingInfoStepStatus(VapProductProcessInfo productProcessInfo, String title, String status) {
        if (productProcessInfo != null) {
            if (title.equals(WorkflowStep.DeviceMarketingReview.toString())) {
                productProcessInfo.setDeviceMarketingInputStatus(status);
            } else if (title.equals(WorkflowStep.ProductInfoUpload.toString())) {
                productProcessInfo.setProductInfoInputStatus(status);
            } else if (title.equals(WorkflowStep.ExportCompliance.toString())) {
                productProcessInfo.setExportComplianceInputStatus(status);
            } else if (title.equals(WorkflowStep.UploadPDFofSampleProduct.toString())) {
                productProcessInfo.setUploadPdfInputStatus(status);
            }
        }
    }

    protected void updateProductStatus(VapProduct product, String title, String actionTaken) {
        if (actionTaken.equals(WorkflowAction.Deny.toString())) {
            product.setStatus(ProductStatus.Denied.toString());
        } else if (actionTaken.equals(WorkflowAction.UnDeny.toString())) {
            product.setStatus(ProductStatus.Submitted.toString());
        } else if (actionTaken.equals(WorkflowAction.RFI.toString())) {
            product.setStatus(ProductStatus.RFI.toString());
        } else if (actionTaken.equals(WorkflowAction.UnRFI.toString())) {
            product.setStatus(ProductStatus.Submitted.toString());
        } else if (title.equals(WorkflowStep.Approved.toString())) {
            product.setStatus(ProductStatus.Approved.toString());
        }
    }
}
