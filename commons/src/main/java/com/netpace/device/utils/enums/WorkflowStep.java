package com.netpace.device.utils.enums;

public enum WorkflowStep {
    
    // Common workflow steps
    DeviceMarketingReview, DeviceMarketingReviewDenied, Approved,
    
    // Product workflow steps
    ProductInfoUpload, ExportCompliance, UploadPDFofSampleProduct, 
    
    // Company workflow steps
    CertificationNDA, OfflineCertificationNDA, OfflineCertificationNDADenied ; 
    
}
