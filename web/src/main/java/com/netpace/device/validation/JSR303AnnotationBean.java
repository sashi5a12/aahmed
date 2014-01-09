/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netpace.device.validation;

import com.netpace.device.utils.ReflectionUtils;
import java.lang.annotation.Annotation;
import javax.validation.constraints.DecimalMax;

/**
 *
 * @author Content5
 */
public class JSR303AnnotationBean extends AnnotationBean{

   
    @DecimalMax(message="", value="")
    public JSR303AnnotationBean() {
    }

    public JSR303AnnotationBean(Annotation annotation, JSR303ValidtionContraints constraint, String fieldValue) {
        super(annotation);
        setAnnotationMethodValues(ReflectionUtils.getAnnotationMethodReturnValuesMap(getAnnotation(), JSR303ValidtionContraints.allowedAnnotationMethods));
        setMessage((String)getAnnotationMethodValues().get("message"));

        setConstraint(constraint);
        setValueToValidate(fieldValue);
    }

    @Override
    public boolean validate() {
        return JSR303ValidtionContraints.validate(getConstraint(),getAnnotationMethodValues(), getValueToValidate());
    }


    

    @Override
    public String toString(){
        return new StringBuilder("Message : ").append(getMessage()).append("\n")
                .append("ValueToValidate : ").append(getValueToValidate()).append("\n")
                .append("Constraint : ").append(getConstraint()).append("\n")
                .toString();
    }

   



}
