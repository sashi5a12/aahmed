package com.netpace.device.validation.product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.management.StringValueExp;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.enums.ProductSubmissionType;
import com.netpace.device.vo.product.ProductInfoVO;


@Component
public class ProductValidation extends com.netpace.device.vo.BaseValidator {

	private static final Log log = LogFactory.getLog(ProductValidation.class);
	
	public boolean supports(Class clazz){
		return ProductInfoVO.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validateForm(Object target, Errors errors) {
		
		ProductInfoVO form=(ProductInfoVO)target;
		
		isEmpty(errors, "productType", "productVO.required", "productVO.productType");
		if (isGreater(form.getProductType(), 100)){
			addSizeError(errors, "productType", "productVO.length", 100, "productVO.productType");
		}
		
		isEmpty(errors, "productName", "productVO.required", "productVO.productName");
		if (isGreater(form.getProductName(), 250)){
			addSizeError(errors, "productName", "productVO.length", 250, "productVO.productName");
		}
		
		if(form.getValidRequiredFields()){
			isEmpty(errors, "modelNumber", "productVO.required",  "productVO.modelNumber");
		}
		if (isGreater(form.getModelNumber(), 250)){
			addSizeError(errors, "modelNumber", "productVO.length", 250, "productVO.modelNumber");
		}
		
		if(form.getValidRequiredFields()){
			isEmpty(errors, "description", "productVO.required",  "productVO.description");
		}
		if (isGreater(form.getDescription(), 2000)){
			addSizeError(errors, "description", "productVO.length", 2000, "productVO.description");
		}

		if(form.getValidRequiredFields()){
			isEmpty(errors, "productCategory", "productVO.required",  "productVO.productCategory");
		}
		if (isGreater(form.getProductCategory(), 100)){
			addSizeError(errors, "productCategory", "productVO.length", 100, "productVO.productCategory");
		}

		if(form.getValidRequiredFields() && form.getProductType().equals(ProductSubmissionType.Product.getLabel())){
			isEmpty(errors, "partNumber", "productVO.required",  "productVO.partNumber");
		}
		if (isGreater(form.getPartNumber(), 250)){
			addSizeError(errors, "partNumber", "productVO.length", 250, "productVO.partNumber");
		}

		if(form.getProductType().equals(ProductSubmissionType.Product.getLabel())){
			if(form.getValidRequiredFields()){
				isEmpty(errors, "sampleTracking", "productVO.required",  "productVO.sampleTracking");
			}
			if (isGreater(form.getSampleTracking(), 250)){
				addSizeError(errors, "sampleTracking", "productVO.length", 250, "productVO.sampleTracking");
			}
		}
		
		if(form.getValidRequiredFields()){
			isEmpty(errors, "availabilityDate", "productVO.required",  "productVO.availabilityDate");
		}
		if (isGreater(form.getAvailabilityDate(), 15)){
			addSizeError(errors, "availabilityDate", "productVO.length", 15, "productVO.availabilityDate");
		}
		if (!isDate(form.getAvailabilityDate())){
			errors.rejectValue("availabilityDate", "productVO.invalid", new String[]{messageSource.getMessage("productVO.availabilityDate", null, Locale.getDefault())}, "");
		}

		if(form.getValidRequiredFields()){
			isEmpty(errors, "vzwExclusive", "productVO.required",  "productVO.vzwExclusive");
		}
		if (isGreater(form.getVzwExclusive(), 5)){
			addSizeError(errors, "vzwExclusive", "productVO.length", 5, "productVO.vzwExclusive");
		}

		if(form.getValidRequiredFields()){
			isEmpty(errors, "targetSegment", "productVO.required",  "productVO.targetSegment");
		}
		if (isGreater(form.getTargetSegment(), 1000)){
			addSizeError(errors, "targetSegment", "productVO.length", 1000, "productVO.targetSegment");
		}

		if(form.getValidRequiredFields()){
			isEmpty(errors, "positioningStatement", "productVO.required",  "productVO.positioningStatement");
		}
		if (isGreater(form.getPositioningStatement(), 1000)){
			addSizeError(errors, "positioningStatement", "productVO.length", 1000, "productVO.positioningStatement");
		}

		if(form.getValidRequiredFields()){
			isEmpty(errors, "deviceNeed", "productVO.required",  "productVO.deviceNeed");
		}
		if (isGreater(form.getDeviceNeed(), 1000)){
			addSizeError(errors, "deviceNeed", "productVO.length", 1000, "productVO.deviceNeed");
		}

		if(form.getValidRequiredFields()){
			isEmpty(errors, "mainCompetition", "productVO.required",  "productVO.mainCompetition");
		}
		if (isGreater(form.getMainCompetition(), 1000)){
			addSizeError(errors, "mainCompetition", "productVO.length", 1000, "productVO.mainCompetition");
		}

		if(form.getValidRequiredFields()){
			isEmpty(errors, "uniqueFunctionality", "productVO.required",  "productVO.uniqueFunctionality");
		}
		if (isGreater(form.getUniqueFunctionality(), 1000)){
			addSizeError(errors, "uniqueFunctionality", "productVO.length", 1000, "productVO.uniqueFunctionality");
		}

		if(form.getProductType().equals(ProductSubmissionType.Product.getLabel())){
			if(form.getValidRequiredFields()){
				isEmpty(errors, "connectivityType", "productVO.required",  "productVO.connectivityType");
			}
			if (isGreater(form.getConnectivityType(), 250)){
				addSizeError(errors, "connectivityType", "productVO.length", 250, "productVO.connectivityType");
			}
	
			if(form.getValidRequiredFields()){
				isEmpty(errors, "paltformSupported", "productVO.required",  "productVO.paltformSupported");
			}
			if (isGreater(form.getPaltformSupported(), 250)){
				addSizeError(errors, "paltformSupported", "productVO.length", 250, "productVO.paltformSupported");
			}
	
			if(form.getValidRequiredFields()){
				isEmpty(errors, "cloudSupported", "productVO.required",  "productVO.cloudSupported");
			}
			if (isGreater(form.getCloudSupported(), 5)){
				addSizeError(errors, "cloudSupported", "productVO.length", 5, "productVO.cloudSupported");
			}
	
			if(form.getValidRequiredFields()){
				isEmpty(errors, "formRequirements", "productVO.required",  "productVO.formRequirements");
			}
			if (isGreater(form.getFormRequirements(), 250)){
				addSizeError(errors, "formRequirements", "productVO.length", 250, "productVO.formRequirements");
			}
	
			if(form.getValidRequiredFields()){
				isEmpty(errors, "productDimensions", "productVO.required",  "productVO.productDimensions");
			}
			if (isGreater(form.getProductDimensions(), 250)){
				addSizeError(errors, "productDimensions", "productVO.length", 250, "productVO.productDimensions");
			}
	
			if(form.getValidRequiredFields()){
				isEmpty(errors, "itemIncluded", "productVO.required",  "productVO.itemIncluded");
			}
			if (isGreater(form.getItemIncluded(), 1000)){
				addSizeError(errors, "itemIncluded", "productVO.length", 1000, "productVO.itemIncluded");
			}
	
			if(form.getValidRequiredFields()){
				isEmpty(errors, "name", "productVO.required",  "productVO.name");
			}
			if (isGreater(form.getName(), 100)){
				addSizeError(errors, "name", "productVO.length", 100, "productVO.name");
			}
	
			if(form.getValidRequiredFields()){
				isEmpty(errors, "streetAddress", "productVO.required",  "productVO.streetAddress");
			}
			if (isGreater(form.getStreetAddress(), 500)){
				addSizeError(errors, "streetAddress", "productVO.length", 500, "productVO.streetAddress");
			}
	
			if(form.getValidRequiredFields()){
				isEmpty(errors, "emailAddress", "productVO.required",  "productVO.emailAddress");
			}
			if (isGreater(form.getEmailAddress(), 250)){
				addSizeError(errors, "emailAddress", "productVO.length", 250, "productVO.emailAddress");
			}
			if (!isValidEmail(form.getEmailAddress())){
				errors.rejectValue("emailAddress", "productVO.invalid", new String[]{messageSource.getMessage("productVO.emailAddress", null, Locale.getDefault())}, "");
			}
			
			if(form.getValidRequiredFields()){
				isEmpty(errors, "city", "productVO.required",  "productVO.city");
			}
			if (isGreater(form.getCity(), 100)){
				addSizeError(errors, "city", "productVO.length", 100, "productVO.city");
			}
	
			if(form.getValidRequiredFields()){
				isEmpty(errors, "phone", "productVO.required",  "productVO.phone");
			}
			if (isGreater(form.getPhone(), 25)){
				addSizeError(errors, "phone", "productVO.length", 25, "productVO.phone");
			}
			if (!isValidPhone(form.getPhone())){
				errors.rejectValue("phone", "productVO.phone.invalid", new String[]{messageSource.getMessage("productVO.phone", null, Locale.getDefault())}, "");
			}
			
			if (!isValidPhone(form.getMobile())){
				errors.rejectValue("mobile", "productVO.phone.invalid", new String[]{messageSource.getMessage("productVO.mobile", null, Locale.getDefault())}, "");
			}
			
			if(form.getValidRequiredFields()){
				isEmpty(errors, "postalCode", "productVO.required",  "productVO.postalCode");
			}
			if (isGreater(form.getPostalCode(), 10)){
				addSizeError(errors, "postalCode", "productVO.length", 10, "productVO.postalCode");
			}
			if (!isValidZipCode(form.getPostalCode())){
				errors.rejectValue("postalCode", "productVO.invalid.zipcode", new String[]{messageSource.getMessage("productVO.postalCode", null, Locale.getDefault())}, "");
			}
			
			if(form.getValidRequiredFields()){
				isEmpty(errors, "state", "productVO.required",  "productVO.state");
			}
			if (isGreater(form.getState(), 250)){
				addSizeError(errors, "state", "productVO.length", 250, "productVO.state");
			}
			
			if(form.getValidRequiredFields()){
				isEmpty(errors, "country", "productVO.required",  "productVO.country");
			}
			if (isGreater(form.getCountry(), 100)){
				addSizeError(errors, "country", "productVO.length", 100, "productVO.country");
			}
		}
		if(form.getValidRequiredFields()){
			
			isEmpty(errors, "out_front_view.mediaId", "productVO.required",  "productVO.productImage.out.forntView");
			isEmpty(errors, "out_angeled_view.mediaId", "productVO.required",  "productVO.productImage.out.angeledView");
			
			isEmpty(errors, "in_front_view.mediaId", "productVO.required",  "productVO.productImage.in.frontView");
			isEmpty(errors, "in_angeled_view.mediaId", "productVO.required",  "productVO.productImage.in.angeledView");
			
			if (form.getSupportedDevices()!=null && form.getSupportedDevices().length>0){
				
				for(String d: form.getSupportedDevices()){
					if (StringUtils.isNotEmpty(d) && d.equals("Phone")){
						isEmpty(errors, "phone_splash_screen.mediaId", "productVO.required",  "productVO.appImages.phoneSplashScreen");
					}
					if (StringUtils.isNotEmpty(d) && d.equals("Tablet")){
						isEmpty(errors, "tablet_splash_screen.mediaId", "productVO.required",  "productVO.appImages.tabletSplashScreen");
					}
				}				
				
				isEmpty(errors, "application_icon.mediaId", "productVO.required",  "productVO.appImages.appIcon");
			}
			
			
			if (ProductSubmissionType.Product.getLabel().equals(form.getProductType())){
				isEmpty(errors, "product_video.mediaId", "productVO.required",  "productVO.video.title");
			}
			
		}
	}

}
