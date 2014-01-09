/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netpace.device.validation;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 *
 * @author Content5
 */
public abstract class AnnotationBean {

    private Annotation annotation;
    private String message;
    private String valueToValidate;
    private String valueBound;
    private JSR303ValidtionContraints constraint;
    private Map<String,String> annotationMethodValues;

    public AnnotationBean(){}

    public AnnotationBean(Annotation annot){
        this.annotation = annot;
    }

    public Annotation getAnnotation(){
        return this.annotation;
    }

    public void setAnnotation(Annotation message){
        this.annotation = annotation;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public JSR303ValidtionContraints getConstraint() {
        return constraint;
    }

    public void setConstraint(JSR303ValidtionContraints constraint) {
        this.constraint = constraint;
    }

    public String getValueToValidate() {
        return valueToValidate;
    }

    public void setValueToValidate(String valueToValidate) {
        this.valueToValidate = valueToValidate;
    }

    public void setValueBound(String valueBound) {
        this.valueBound = valueBound;
    }

    public String getValueBound() {
        return valueBound;
    }

    public void setAnnotationMethodValues(Map<String, String> annotationMethodValues) {
        this.annotationMethodValues = annotationMethodValues;
    }

    public Map<String, String> getAnnotationMethodValues() {
        return annotationMethodValues;
    }

    

    
    public abstract boolean validate();
    
}
