
package com.netpace.vic.util;

import org.apache.commons.lang.StringUtils;

public class Utility {
    public static String getStringFromArray(String[] array){
        StringBuilder sb = new StringBuilder();
        String str="";
        if (array!=null && array.length>0){
            for (String a : array) {
                sb.append(a).append("##");
            }
            
            str=StringUtils.removeEnd(sb.toString(), "##");
        }
        return str;
    }
}
