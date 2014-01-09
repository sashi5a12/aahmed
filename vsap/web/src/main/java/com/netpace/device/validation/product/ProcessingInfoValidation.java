package com.netpace.device.validation.product;

import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.product.ProcessingInfoVO;


@Component
public class ProcessingInfoValidation extends com.netpace.device.vo.BaseValidator {

	private static final Log log = LogFactory.getLog(ProcessingInfoValidation.class);
	
	
	public boolean supports(Class clazz){
		return ProcessingInfoVO.class.isAssignableFrom(clazz);
	}
	
	
	public boolean isValidEccnOrErn(String str) {
		try {
			if (StringUtils.isNotEmpty(str)){
				Pattern p = Pattern.compile("((\\d[A-Za-z]\\d{3})|([A-Za-z]{3}\\d{2})|([A-Za-z]\\d{6}))");
				return (p.matcher(str).matches());
			}
			else {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean isValidCcats(String str) {
		try {
			if (StringUtils.isNotEmpty(str)){
				Pattern p = Pattern.compile("([A-Za-z]\\d{6})");
				return (p.matcher(str).matches());
			}
			else {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	@Override
	public void validateForm(Object target, Errors errors) {
		
		ProcessingInfoVO form=(ProcessingInfoVO)target;
		
		if (VAPConstants.PRODUCT_PROCESS_SECTION_DM.equals(form.getSectionName())){
			isEmpty(errors, "ringAssociation", "productVO.required", "processingInfo.ring");
			if (isGreater(form.getRingAssociation(), 10)){
				addSizeError(errors, "ringAssociation", "productVO.length", 10, "processingInfo.ring");
			}
		}
		
		if (VAPConstants.PRODUCT_PROCESS_SECTION_PIU.equals(form.getSectionName())){
			
			isEmpty(errors, "testing_spreadsheet.mediaId", "productVO.required", "processingInfo.testing.spreadsheet");
			isEmpty(errors, "product_label.mediaId", "productVO.required", "processingInfo.product.label");
			isEmpty(errors, "sustainability_disclosure.mediaId", "productVO.required", "processingInfo.disclosure");
			isEmpty(errors, "packaging_label.mediaId", "productVO.required", "processingInfo.image.packaging");
//			isEmpty(errors, "aggreeChkbox", "processingInfo.accept.aggreement", "processingInfo.accept.aggreement");
//			isEmpty(errors, "agreementAcceptTitle", "productVO.required", "processingInfo.title");
			
//			if (isGreater(form.getAgreementAcceptTitle(), 50)){
//				addSizeError(errors, "agreementAcceptTitle", "productVO.length", 50, "processingInfo.title");
//			}
		}
		
		if (VAPConstants.PRODUCT_PROCESS_SECTION_EC.equals(form.getSectionName())){
			isEmpty(errors, "eccn", "productVO.required", "processingInfo.eccn");
			if (isGreater(form.getEccn(), 10)){
				addSizeError(errors, "eccn", "productVO.length", 10, "processingInfo.eccn");
			}

			if (isGreater(form.getCcats(), 10)){
				addSizeError(errors, "ccats", "productVO.length", 10, "processingInfo.ccats");
			}

			if (StringUtils.isNotEmpty(form.getEccn())){
				if (!isValidEccnOrErn(form.getEccn())){
					errors.rejectValue("eccn", "productVO.invalid.eccn", new String[]{messageSource.getMessage("processingInfo.eccn", null, Locale.getDefault())}, "");
				}
			}
			
			if (StringUtils.isNotEmpty(form.getCcats())){
				if (!isValidCcats(form.getCcats())){
					errors.rejectValue("ccats", "productVO.invalid.ccats", new String[]{messageSource.getMessage("processingInfo.ccats", null, Locale.getDefault())}, "");
				}
			}

		}
		
		if (VAPConstants.PRODUCT_PROCESS_SECTION_PDF.equals(form.getSectionName())){
			isEmpty(errors, "pdf_sample_product.mediaId", "productVO.required", "processingInfo.final.pdf");
		}
		
	}

}
