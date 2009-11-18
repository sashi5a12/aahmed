package com.netpace.vzdn.util;

public class MiscUtils
{

      public static String getExceptionStackTraceInfo(Exception ex)
    {
        StringBuffer errTrace = new StringBuffer();
        StackTraceElement[] stackElements = ex.getStackTrace();

        for (int lcv = 0; lcv < stackElements.length; lcv++)
        {
            errTrace.append(stackElements[lcv].toString());
            errTrace.append("\n");
        }

        return errTrace.toString();
    }
}
