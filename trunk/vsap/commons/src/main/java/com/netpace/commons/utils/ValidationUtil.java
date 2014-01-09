package com.netpace.commons.utils;

import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

public class ValidationUtil {

    private static void addError(Errors errors, String fieldName, String errorCode, Object... args){
        errors.rejectValue(fieldName, errorCode, args, "");
    }

    /**
     * add error if field is empty
     * 
     * @param errors
     * @param messageSource
     * @param fieldName
     * @param errorCode
     * @return 
     */
    public static boolean isNotEmpty(Errors errors, MessageSource messageSource, String fieldName, String errorCode) {
        Object value = errors.getFieldValue(fieldName);
        if(value == null || StringUtils.isBlank(value.toString())){
            addError(errors, fieldName, errorCode, messageSource.getMessage(errors.getObjectName()+"."+fieldName, null, Locale.getDefault()));
            return false;
        }
        
        return true;
    }
    
    /**
     * add error if fields pattern does not match
     * 
     * @param errors
     * @param messageSource
     * @param fieldName
     * @param errorCode
     * @param regEx
     * @return 
     */
    public static boolean isValidPattern(Errors errors, MessageSource messageSource, String fieldName, String errorCode, String regEx){
        Object value = errors.getFieldValue(fieldName);
        if(value == null || !value.toString().matches(regEx)){
            addError(errors, fieldName, errorCode, messageSource.getMessage(errors.getObjectName()+"."+fieldName, null, Locale.getDefault()));
            return false;
        }
        
        return true;
    }
    
    /**
     * add error if field length not within bounds
     * 
     * @param errors
     * @param messageSource
     * @param fieldName
     * @param errorCode
     * @param min
     * @param max
     * @return 
     */
    public static boolean isValidLength(Errors errors, MessageSource messageSource, String fieldName, String errorCode, Integer min, Integer max){
        Object value = errors.getFieldValue(fieldName);
        if(value == null || (min != null && value.toString().length() < min) || (max != null && value.toString().length() > max) ){
            addError(errors, fieldName, errorCode, messageSource.getMessage(errors.getObjectName()+"."+fieldName, null, Locale.getDefault()), min, max);
            return false;
        }
        
        return true;
    }
    
    
    /**
     * add error if not valid email
     * 
     * @param errors
     * @param messageSource
     * @param fieldName
     * @param errorCode
     * @return 
     */
    public static boolean isValidEmail(Errors errors, MessageSource messageSource, String fieldName, String errorCode){
        Object value = errors.getFieldValue(fieldName);
        if(value == null || !EmailValidator.getInstance().isValid(value.toString()) ){
            addError(errors, fieldName, errorCode, messageSource.getMessage(errors.getObjectName()+"."+fieldName, null, Locale.getDefault()));
            return false;
        }
        
        return true;
    }

    /**
     * add error if email do not match domain
     * can also be used in forms where company domain is not taken input
     * 
     * @param errors
     * @param messageSource
     * @param emailFieldName e.g emailAddress
     * @param errorCode errorCode class e.g Match
     * @param domainFieldValue domain name e.g netpace.com
     * @param domainFieldLabel label to show in error message e.g Company domain
     * @return 
     */
    public static boolean isValidDomainEmail(Errors errors, MessageSource messageSource, String emailFieldName, String errorCode, String domainFieldValue, String domainFieldLabel){
        Object value = errors.getFieldValue(emailFieldName);
        if(value == null || !EmailValidator.getInstance().isValid(value.toString()) || value.toString().split("@").length < 2){
            return false;
        }
        String[] emailParts = StringUtils.split(value.toString(), "@");
        String emailDomain = emailParts[1];
        if (!StringUtils.equals(emailDomain, domainFieldValue)) {
            addError(errors, emailFieldName, errorCode, messageSource.getMessage(errors.getObjectName()+"."+emailFieldName, null, Locale.getDefault()), domainFieldLabel);
            return false;
        }
        
        return true;
    }

    /**
     * add error if email do not match domain ignoring case
     * can also be used in forms where company domain is not taken input
     * 
     * @param errors
     * @param messageSource
     * @param emailFieldName e.g emailAddress
     * @param errorCode errorCode class e.g Match
     * @param domainFieldValue domain name e.g netpace.com
     * @param domainFieldLabel label to show in error message e.g Company domain
     * @return 
     */
    public static boolean isValidDomainEmailIgnoreCase(Errors errors, MessageSource messageSource, String emailFieldName, String errorCode, String domainFieldValue, String domainFieldLabel){
        Object value = errors.getFieldValue(emailFieldName);
        if(value == null || !EmailValidator.getInstance().isValid(value.toString()) || value.toString().split("@").length < 2){
            return false;
        }
        String[] emailParts = StringUtils.split(value.toString(), "@");
        String emailDomain = emailParts[1];
        if (!StringUtils.equalsIgnoreCase(emailDomain, domainFieldValue)) {
            addError(errors, emailFieldName, errorCode, messageSource.getMessage(errors.getObjectName()+"."+emailFieldName, null, Locale.getDefault()), domainFieldLabel);
            return false;
        }
        
        return true;
    }

    /**
     * add error if domain name not valid 
     * 
     * @param errors
     * @param messageSource
     * @param fieldName
     * @param errorCode
     * @return 
     */
    public static boolean isValidDomain(Errors errors, MessageSource messageSource, String fieldName, String errorCode){
        Object value = errors.getFieldValue(fieldName);
        if(value == null || !DomainValidator.getInstance().isValid(value.toString()) ){
            addError(errors, fieldName, errorCode, messageSource.getMessage(errors.getObjectName()+"."+fieldName, null, Locale.getDefault()));
            return false;
        }
        
        return true;
    }

    /**
     * add error if url not valid
     * 
     * @param errors
     * @param messageSource
     * @param fieldName
     * @param errorCode
     * @return 
     */
    public static boolean isValidUrl(Errors errors, MessageSource messageSource, String fieldName, String errorCode){
        Object value = errors.getFieldValue(fieldName);
        if(value == null || !UrlValidator.getInstance().isValid(value.toString().toLowerCase()) ){
            addError(errors, fieldName, errorCode, messageSource.getMessage(errors.getObjectName()+"."+fieldName, null, Locale.getDefault()));
            return false;
        }
        
        return true;
    }

    /**
     * add error if field value not in range
     * 
     * @param errors
     * @param messageSource
     * @param fieldName
     * @param errorCode
     * @param min
     * @param max
     * @return 
     */
    public static boolean isValidRange(Errors errors, MessageSource messageSource, String fieldName, String errorCode, Integer min, Integer max) {
        Object value = errors.getFieldValue(fieldName);
        if(value == null || StringUtils.isEmpty(value.toString()) || (min != null && new Integer(value.toString()) < min) || (max != null && new Integer(value.toString()) > max) ){
            addError(errors, fieldName, errorCode, messageSource.getMessage(errors.getObjectName()+"."+fieldName, null, Locale.getDefault()));
            return false;
        }
        
        return true;
    }

    /**
     * add error if field1 and field2 values are not same
     * 
     * @param errors
     * @param messageSource
     * @param fieldName1
     * @param fieldName2
     * @param errorCode
     * @return 
     */
    public static boolean isBothMatches(Errors errors, MessageSource messageSource, String fieldName1, String fieldName2, String errorCode) {
        Object value1 = errors.getFieldValue(fieldName1);
        Object value2 = errors.getFieldValue(fieldName2);
        if(value1 == null || value2 == null || !StringUtils.equals(value1.toString(), value2.toString())){
            addError(errors, fieldName1, errorCode, messageSource.getMessage(errors.getObjectName()+"."+fieldName1, null, Locale.getDefault()), messageSource.getMessage(errors.getObjectName()+"."+fieldName2, null, Locale.getDefault()));
            return false;
        }
        
        return true;
    }

    /**
     * add error if field1 and field2 values are not same ignoring case
     * 
     * @param errors
     * @param messageSource
     * @param fieldName1
     * @param fieldName2
     * @param errorCode
     * @return 
     */
    public static boolean isBothMatchesIgnoreCase(Errors errors, MessageSource messageSource, String fieldName1, String fieldName2, String errorCode) {
        Object value1 = errors.getFieldValue(fieldName1);
        Object value2 = errors.getFieldValue(fieldName2);
        if(value1 == null || value2 == null || !StringUtils.equalsIgnoreCase(value1.toString(), value2.toString())){
            addError(errors, fieldName1, errorCode, messageSource.getMessage(errors.getObjectName()+"."+fieldName1, null, Locale.getDefault()), messageSource.getMessage(errors.getObjectName()+"."+fieldName2, null, Locale.getDefault()));
            return false;
        }
        
        return true;
    }
}
