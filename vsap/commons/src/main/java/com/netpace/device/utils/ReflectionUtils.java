/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netpace.device.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Content5
 */
public class ReflectionUtils {


     public static Object getAnnotationMethodReturnValue(Annotation annotation, String methodName){
        try {
            return annotation.annotationType().getMethod(methodName, new Class[0]).invoke(annotation, new Object[0]);
        } catch (Exception e){
            Logger.getLogger(ReflectionUtils.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static Object getMethodReturnValue(Object object, String methodName) {
        try {
            return object.getClass().getMethod(methodName, new Class[0]).invoke(object, new Object[0]);
        } catch (Exception e){
            Logger.getLogger(ReflectionUtils.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

     public static Map<String, String> getAnnotationMethodReturnValuesMap(Annotation annotation, Map<String,String> allowedMethods){
        Method[] methods = annotation.annotationType().getMethods();
        Map<String, String> map = new HashMap<String,String>();
        for (Method method : methods) {

            if(allowedMethods.get(method.getName())!= null)
                map.put(method.getName(),(String)getMethodReturnValue(annotation, method.getName()));
        }

        return map;
    }

}
