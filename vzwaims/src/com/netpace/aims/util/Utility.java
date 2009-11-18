package com.netpace.aims.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Utility
{

    static Logger log = Logger.getLogger(Utility.class.getName());
    public static final byte REPLACE_ALL = 0;
    public static final byte REPLACE_FIRST_ONLY = 1;
    public static final byte REPLACE_LAST_ONLY = 2;

    /**
      *  Method for debugging
      */
    public static void debugCollection(Collection collection)
    {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }
    }

    public static String getObjectString(Object obj)
    {
        if (obj != null)
            return obj.toString();
        else
            return "";
    }

    public static Long getObjectLong(Object obj)
    {
        if (obj != null)
            return (Long) obj;
        else
            return new Long("0");
    }

    public static String getObjectDate(Object obj)
    {
        if (obj != null)
            return convertToString((Date) obj, AimsConstants.DATE_FORMAT);
        else
            return "";
    }

    public static boolean isString(Object obj)
    {
        if (obj == null)
            return false;
        else if (((String) obj).trim().equals(""))
            return false;
        else
            return true;
    }

    public static java.util.Date convertToDate(String strDate, String dateFormat)
    {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        java.util.Date date = null;
        try
        {
            date = df.parse(strDate);
        }
        catch (Exception e)
        {
            log.debug("Date conversion error " + strDate + e);
            date = null;
        }
        return date;
    }

    public static java.lang.Long convertToLong(String strLong)
    {
        java.lang.Long lng = null;
        try
        {
            lng = new Long(Long.parseLong(strLong));
        }
        catch (Exception e)
        {
            log.debug("Long parse error " + strLong + e);
            lng = null;
        }
        return lng;
    }

    public static java.math.BigDecimal convertToBigDecimal(String strBigDecimal)
    {
        java.math.BigDecimal decimal = null;
        try
        {
            decimal = new java.math.BigDecimal(strBigDecimal);
        }
        catch (Exception e)
        {
            log.debug("BigDecimal parse error " + strBigDecimal + e);
            decimal = null;
        }
        return decimal;
    }

    public static String convertToString(java.util.Date date, String dateFormat)
    {
        if (date != null)
        {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            return df.format(date);
        }

        return null;
    }

    public static String convertToString(java.lang.Long lng)
    {
        if (lng != null)
        {
            return lng.toString();
        }

        return null;
    }

    public static String convertToString(java.math.BigDecimal decimal)
    {
        if (decimal != null)
        {
            return decimal.toString();
        }

        return null;
    }

    /** Search and replaces the string with provided string. (Added by Ahson Imtiaz)
    * @param strSource String to be searched.
    * @param strToFind String to find in strSource passed.
    * @param strReplacement String to be replaced with string strToFind.
    * @reutn String the replaced string
    */
    public static String replaceString(String strSource, String strToFind, String strReplacement) throws Exception
    {
        /*if (strToFind == null || strSource == null || strReplacement == null) return "";*/

        StringBuffer strReplaced = new StringBuffer(strSource);
        int position = strSource.indexOf(strToFind);
        while (position > -1)
        {
            strReplaced.replace(position, position + strToFind.length(), strReplacement);
            position = strReplaced.toString().indexOf(strToFind, position + strReplacement.length());
        }

        return strReplaced.toString();
    }

    /*
    ZeroValueReplacement takes a Long and returns 0 if the Long is null
    and unchanged otherwise.
    */
    public static Long ZeroValueReplacement(Long lng)
    {
        Long retLong = lng;

        if (lng == null)
        {
            retLong = new Long(0);
        }

        return retLong;
    }
    public static String replace(String source, String stringToReplace,
			String replaceWith, int option) {
		replaceWith = (replaceWith == null) ? "" : replaceWith;
		StringBuffer buffer = new StringBuffer(source);

		if (option == REPLACE_FIRST_ONLY) {
			int start = source.indexOf(stringToReplace);
			int end = start + stringToReplace.length();
			buffer.replace(start, end, replaceWith);
		} else if (option == REPLACE_LAST_ONLY) {
			int start = source.lastIndexOf(stringToReplace);
			int end = start + stringToReplace.length();
			buffer.replace(start, end, replaceWith);
		} else if (option == REPLACE_ALL) {
			int start = 0;

			while ((start = buffer.toString().indexOf(stringToReplace, start)) > -1) {
				int end = start + stringToReplace.length();
				buffer.replace(start, end, replaceWith);
				start = start + replaceWith.length();
			}
		}

		return buffer.toString();
	}

    public static String getEllipseString(int length, String data) {
        return Utility.getEllipseString(length, data, true);//escape html data
    }


    public static String getEllipseString(int length, String data, boolean filterData) {
    	StringBuffer str=new StringBuffer();
    	try {
            if(filterData) {
                //escape html if filter data is true
                data = StringEscapeUtils.escapeHtml(data);
            }
            if (data!=null && data.length()>length) {
                String ellispedTxt="";
				char[] chars=data.toCharArray();
				if (length == 50 && chars.length>50 && chars[50]=='/'){
					ellispedTxt=data.substring(0, length-3);
				}
				else if (length == 50 && '/'==chars[49]){
					ellispedTxt=data.substring(0, length-4);
				}			
				else if (length == 50 && '<'==chars[49]){
					ellispedTxt=data.substring(0, length-1);
				}
				else if (length == 50 && '<'==chars[48] && 'b'==chars[49]){
					ellispedTxt=data.substring(0, length-2);
				}
				else if (length == 50 && '<'==chars[47] && 'b'==chars[48] && 'r'==chars[49]){
					ellispedTxt=data.substring(0, length-3);
				}			
				else {
					ellispedTxt=data.substring(0, length);
				}
				ellispedTxt += "...";
				data=Utility.replace(data, "'", "\\'", Utility.REPLACE_ALL);
				//data=Utility.replace(data, "\"", "&quot;", Utility.REPLACE_ALL);
				data=Utility.replace(data, "\r\n", "\\r\\n", Utility.REPLACE_ALL);
				str.append("<span onmouseover=\"return Tip('"+data+"')\">"+ellispedTxt+"</span>");
			}
			else if (StringUtils.isEmpty(data)){
				return "&nbsp;";
			}
			else {
				str.append(data);
			}
		} catch (RuntimeException e) {
			log.error("Error in Utility.getEllipseString: "+data,e);
		}    	
    	return str.toString();
    }
    
    public static boolean isValidEmail(String str) {
		try {
			Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9_.-]*@[a-zA-Z0-9][a-zA-Z0-9_-]*[a-zA-Z0-9]\\.[a-zA-Z0-9][a-zA-Z0-9_-]*[a-zA-Z0-9][\\.[a-zA-Z0-9][a-zA-Z0-9_-]*[a-zA-Z0-9]]*");
			return (p.matcher(str).matches());
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

    public static long convertToGMTMilli(Date inDate, String format) throws Exception {
		return convertToGMT(inDate, format).getTime();
	}

	public static Date convertToGMT(Date inDate, String format) throws Exception {
		DateTime sourceDate = new DateTime(inDate.getTime());
		DateTime gmtTime = sourceDate.withZone(DateTimeZone.forID("GMT"));
		return convertToDate(gmtTime.toString(format), format);
	}
	public static String strTruncate(String content, int truncLen){
		if (StringUtils.isNotEmpty(content) && content.length()>truncLen){
			return content.substring(0,truncLen);
		}
		else {
			return content;
		}
	}
    public static void main(String args[])throws Exception{
    	Timestamp ts=new Timestamp(System.currentTimeMillis());
    	System.out.println(ts);
    	System.out.println(Utility.convertToString(ts, "MM/dd/yy 'at' hh:mm a z"));
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    	Timestamp timestamp=new Timestamp(Utility.convertToGMTMilli(new Date(), "yyyyMMddHHmm"));
    	System.out.println(dateFormat.format(timestamp));
    	
    	
    	
    	System.out.println(strTruncate("TitleTitleTitleTitle", 50));
    }
}