package com.netpace.device.vo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.netpace.device.utils.VAPConstants;

/**
 *
 * This is a BaseValidator Defined to use Springs annotation based validation
 * 
 * All forms can use Spring 's annotation based validation and for any other validation
 * which is user defined and needs to be implemented through code can be done by extending 
 * this BaseValidator and overriding 
 * 
 * public void validateForm(Object o, Errors errors)
 * 
 * @author Humza Ghayas
 */
public abstract class BaseValidator extends LocalValidatorFactoryBean implements Validator{

    @Resource(name="validatorFactory")
    ValidatorFactory validatorFactory;
    
	@Autowired
	protected MessageSource messageSource;
	
    @Override
    public final void validate(Object o, Errors errors) {
        super.validate(o, errors);
        validateForm(o, errors);
    }
    
    public abstract void validateForm(Object o, Errors errors);
    
    
    public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
    
	public void isEmpty(Errors errors,  String fieldName,String errorCode, String fieldLabel){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, errorCode,  new String[]{messageSource.getMessage(fieldLabel, null, Locale.getDefault())});
	}

	public void addSizeError(Errors errors, String fieldName, String errorCode, int size, String fieldLabel){
		errors.rejectValue(fieldName, errorCode, new String[]{messageSource.getMessage(fieldLabel, null, Locale.getDefault()), String.valueOf(size)}, "");
	}
	
	public boolean isGreater(String fieldName, int size){
		if (fieldName != null && fieldName.trim().length() > size){
			return true;
		}
		return false;
	}
	public boolean isValidPhone(String str) {
		try {
			if (StringUtils.isNotEmpty(str)){
				Pattern p = Pattern.compile("\\(?\\d\\d\\d\\)? *\\-? *\\d\\d\\d *\\-? *\\d\\d\\d\\d");
				return (p.matcher(str).matches());
			} else {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean isValidEmail(String str) {
		try {
			if (StringUtils.isNotEmpty(str)){
				Pattern p = Pattern.compile(VAPConstants.REGEX_STD_EMAIL);
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
	
	public boolean isValidZipCode(String str) {
		try {
			if (StringUtils.isNotEmpty(str)){			
				Pattern p = Pattern.compile(VAPConstants.REGEX_STD_ZIPCODE);
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
	
	public boolean isDate(String str) {
		SimpleDateFormat df = new SimpleDateFormat(VAPConstants.DATE_FORMAT_MM_DD_YYYY);
		Calendar calendar = Calendar.getInstance();
		try {
			if (StringUtils.isNotEmpty(str)){
				java.util.Date date = df.parse(str);
				calendar.setTime(date);
				String month = str.substring(0, str.indexOf("/"));
				String year = str.substring(str.lastIndexOf("/") + 1, str.length());
				int iMonth = Integer.parseInt(month);
				if ((iMonth - 1) != calendar.get(Calendar.MONTH))
					return false;
				if (year.length() != 4) 
					return false;
				return true;
			}
			else {
				return true;
			}
		} catch (Exception ex) {
			return false;
		}
	}
}
