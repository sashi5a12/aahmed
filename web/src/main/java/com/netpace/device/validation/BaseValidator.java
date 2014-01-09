/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netpace.device.validation;


import com.netpace.device.utils.ReflectionUtils;
import com.netpace.device.vo.Record;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;


/**
 *
 * @author Content5
 */
public abstract class BaseValidator <T extends Record> {

    public BaseValidator(){
    }


    protected Map<String, List<AnnotationBean> > validateJSR303Fields(T elem) {

        Map<String, List<AnnotationBean> > invalidFields = new HashMap<String, List<AnnotationBean> >();
        List<AnnotationBean> fieldAnnotations = null;
        JSR303ValidtionContraints constraint = null;
        AnnotationBean annotationBean = null;

        Field[] fields = elem.getClass().getDeclaredFields();
        for (Field field : fields) {
            
            boolean jsrAnnotationfound = false;
            
            for (Annotation annotation : field.getAnnotations()) {

                constraint = JSR303ValidtionContraints.getConstraint(annotation.annotationType().getCanonicalName());
                jsrAnnotationfound = constraint != null;

                if(jsrAnnotationfound){

                    // Initializing list for annotations;
                    if(fieldAnnotations == null) {
                        fieldAnnotations = new ArrayList<AnnotationBean>();
                    }

                    annotationBean = new JSR303AnnotationBean(annotation,constraint,this.getFieldValue(elem,field.getName()));
                    
                    //Validating bean;
                    if(!annotationBean.validate()){
                        fieldAnnotations.add(annotationBean);
                    }
                }
            }

            //If jsrAnnotation found and fieldAnnotations is not empty (means validation is failed)
            if(jsrAnnotationfound && fieldAnnotations != null && !fieldAnnotations.isEmpty()){
                invalidFields.put(field.getName(), fieldAnnotations);
            }

            // preparing to instantiate new list for next field;
            fieldAnnotations = null;
            jsrAnnotationfound = false;
        }
        return invalidFields;
    }

    private String getFieldValue(T inputObject, String fieldName){
        try {

            String methodName = "get"+StringUtils.capitalize(fieldName);
            
            return (String) ReflectionUtils.getMethodReturnValue(inputObject, methodName);
            
        } catch (Exception ex) {
            Logger.getLogger(BaseValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}
