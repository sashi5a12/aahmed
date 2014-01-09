/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netpace.device.validation;


import com.netpace.device.utils.ReflectionUtils;
import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Content5
 */

public enum JSR303ValidtionContraints {
    NOTNULL(NotNull.class.getCanonicalName()),NULL(Null.class.getCanonicalName()),
    DECIMALMAX(DecimalMax.class.getCanonicalName()),DECIMALMIN(DecimalMin.class.getCanonicalName()),
    DIGITS(Digits.class.getCanonicalName()),FUTURE(Future.class.getCanonicalName()),
    MAX(Max.class.getCanonicalName()),MIN(Min.class.getCanonicalName()),
    PAST(Past.class.getCanonicalName()),PATTERN(Pattern.class.getCanonicalName()),
    SIZE(Size.class.getCanonicalName());

    
    private String canonicalName;
    
    public String getCanonicalName(){
        return this.canonicalName;
    }

    private JSR303ValidtionContraints(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public  static <F> boolean validate(JSR303ValidtionContraints constraintToValidate, Map<String,String> annotationAttributeValues, F fieldValue){
        boolean validate = false;

        switch(constraintToValidate) {
            case NOTNULL:

                    validate = fieldValue != null;
                    break;

            case NULL:
                    validate = fieldValue == null;
                break;

            case DECIMALMAX:
                    validate = constraintToValidate.isDecimalMax(String.valueOf(fieldValue), annotationAttributeValues.get("value"));
                break;

            case DECIMALMIN:
                    validate = constraintToValidate.isDecimalMin(String.valueOf(fieldValue), annotationAttributeValues.get("value"));
                break;

            case DIGITS:
                    validate = constraintToValidate.isDigits(String.valueOf(fieldValue), annotationAttributeValues);
                break;

            case FUTURE:
                    validate = constraintToValidate.isFuture((Date)fieldValue);
                break;

            case MAX:
                    validate = constraintToValidate.isMax(String.valueOf(fieldValue), annotationAttributeValues.get("value"));
                break;

            case MIN:
                     validate = constraintToValidate.isMin(String.valueOf(fieldValue), annotationAttributeValues.get("value"));
                break;

            case PAST:
                     validate = constraintToValidate.isPast((Date)fieldValue);
                break;

            case SIZE:
                     validate = constraintToValidate.isSize(fieldValue, annotationAttributeValues);
                break;
        }

        return validate;
    }


    private boolean isDecimalMax(Object fieldValue, String bound){
        BigDecimal number = isNumber(fieldValue);
        if(number != null){
            if(StringUtils.isNumeric(bound)){
                 BigDecimal boundValue = new BigDecimal(bound);
                int result = boundValue.compareTo(number);
                return (result < 0 || result == 0);
            }
        }
        return false;
    }

    private boolean isDecimalMin(Object fieldValue, String bound){

        BigDecimal number = isNumber(fieldValue);
        if(number != null){
            if(StringUtils.isNumeric(bound)){
                 BigDecimal boundValue = new BigDecimal(bound);
                int result = boundValue.compareTo(number);
                return ( result == 0 || result > 0);
            }
        }
        return false;
    }

    private boolean isDigits(Object fieldValue, Map<String, String> annotationAttributes){

        BigDecimal number = isNumber(fieldValue);
        if(number != null){
            String fraction = annotationAttributes.get("fraction");
            String integer = annotationAttributes.get("integer");

            if(StringUtils.isNumeric(fraction) && StringUtils.isNumeric(integer)) {
                int maxFractionLength = Integer.parseInt(fraction);
                int maxIntegerLength = Integer.parseInt(integer);

                return maxIntegerLength<= number.precision() && maxFractionLength <= number.scale();
            }
        }
        return false;
    }


    private boolean isFuture(Object fieldValue){
        Date date = isDate(fieldValue);
        if(date != null){
            return date.after(new Date());
        }
        return false;
    }

    private boolean isMax(Object fieldValue, String bound){

        Number number = isNumber(fieldValue);
        if(number != null) {
            if( StringUtils.isNumeric(bound)){
                return number.intValue() <= Integer.parseInt(bound) ;
            }
        }
        return false;
    }


    private boolean isMin(Object fieldValue, String bound){

        Number number = isNumber(fieldValue);
        if(number != null) {
            if( StringUtils.isNumeric(bound)){
                return number.intValue() >= Integer.parseInt(bound) ;
            }
        }
        return false;
    }

    private boolean isPast(Date fieldValue){
        Date date = isDate(fieldValue);
        if(date != null){
            return date.before(new Date());
        }
        return false;
    }

    private boolean isSize(Object fieldValue, Map<String, String> annotationAttributes) {
        String min = annotationAttributes.get("min");
        String max = annotationAttributes.get("max");
        
        Collection collection = isCollection(fieldValue);
        Map map = isMap(fieldValue);
        String string = isString(fieldValue);
        boolean isArray = fieldValue.getClass().isArray();
        
        if(collection != null) {
            return this.isMin(collection.size(), min) && this.isMax(collection.size(), max);

        } else if(map != null) {
            return this.isMin(map.size(), min) && this.isMax(map.size(), max);

        } else if(string != null) {
            return this.isMin(string.length(), min) && this.isMax(string.length(), max);

        } else if(isArray) {
            int size = (Integer)ReflectionUtils.getMethodReturnValue(fieldValue, "length");

            return this.isMin(size, min) && this.isMax(size, max);
        }
        
        return false;
    }


    private Collection isCollection(Object fieldValue){
        if(fieldValue instanceof Collection){
            return (Collection)fieldValue;
        }
        return null;
    }

    private Map isMap(Object fieldValue ){
        if(fieldValue instanceof Map){
            return (Map)fieldValue;
        }
        return null;
    }

    private String isString(Object fieldValue ){
        if(fieldValue instanceof String){
            return (String)fieldValue;
        }
        return null;
    }

    private Date isDate(Object fieldValue ){
        if(fieldValue instanceof Date){
            return (Date)fieldValue;

        } else if(fieldValue instanceof Calendar){
            return ((Calendar)fieldValue).getTime();
        }
        return null;
    }


    private BigDecimal isNumber(Object fieldValue ){
        if(fieldValue instanceof Number){
            return new BigDecimal(((Number)fieldValue).toString());
        }
        return null;
    }


    public static JSR303ValidtionContraints getConstraint(String canonicalName){
        return JSR303ValidtionContraints.constraints.get(canonicalName);
    }

    public static final Map<String, JSR303ValidtionContraints> constraints;
    public static final Map<String, String> allowedAnnotationMethods;
    static{
        constraints = new HashMap<String, JSR303ValidtionContraints>();
        for(JSR303ValidtionContraints constraint : JSR303ValidtionContraints.values()){
            constraints.put(constraint.getCanonicalName(), constraint);
        }
        allowedAnnotationMethods = new HashMap<String,String>();
        allowedAnnotationMethods.put("value", "value");
        allowedAnnotationMethods.put("min", "min");
        allowedAnnotationMethods.put("max", "max");
        allowedAnnotationMethods.put("message", "message");
    }

    
}
