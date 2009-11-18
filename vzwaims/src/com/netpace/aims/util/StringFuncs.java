package com.netpace.aims.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ArrayList;

/*
Name of file:   StringFuncs.java
Date:           10/09/2002
Purpose:        Contains general purpose String utilities.
Arguments:      N/A
History:
@1/5/2003::rqazi::added a new overloaded function :public static String initializeStringGetParam(String param, String defaultVal)
*/

public class StringFuncs
{
    private static Logger log = Logger.getLogger(StringFuncs.class.getName());

    public static final String EMPTY = "";

    /*
     string_substitute replaces all occurances of sReplace with sNew
     */
    public static String string_substitute(String sOriginal, String sReplace, String sNew)
    {
        int nIndex = 0;
        String sReturn = new String();

        if (sOriginal != null)
        {
            sReturn = sOriginal;
            nIndex = sOriginal.indexOf(sReplace);
            while (nIndex != -1)
            {
                sReturn = sReturn.substring(0, nIndex) + sNew + sReturn.substring(nIndex + sReplace.length(), sReturn.length());
                nIndex = sReturn.indexOf(sReplace, nIndex + sNew.length());
            }
        }
        else
        {
            sReturn = "";
        }
        return sReturn;
    } // end of method string_substitute

    /*
    sqlEscape replaces all occurances of ' with ''
    (in a SQL statement).
    */
    public static String sqlEscape(String sOriginal)
    {
        String sReturn = new String();
        if (sOriginal != null)
        {
            sReturn = string_substitute(sOriginal, "'", "''");
        }
        else
            sReturn = "";
        return sReturn;
    } // end of method sqlEscape

    /*
    htmlEscape replaces all occurances of " with &#34;
    */
    public static String htmlEscape(String sOriginal)
    {
        String sReturn = new String();
        if (sOriginal != null)
        {
            sReturn = string_substitute(sOriginal, "\"", "&#34;");
        }
        else
            sReturn = "";
        return sReturn;
    } // end of method htmlEscape

    /*
    htmlUnEscape replaces all occurances of &#34; with "
    */
    public static String htmlUnEscape(String sOriginal)
    {
        String sReturn = new String();
        if (sOriginal != null)
        {
            sReturn = string_substitute(sOriginal, "&#34;", "\"");
        }
        else
            sReturn = "";
        return sReturn;
    } // end of method htmlUnEscape
    /*
    tokenize splits the input on the delimiter and
    returns a String array.
    */
    public static String[] tokenize(String input, String delimiter)
    {
        Vector v = new Vector();
        StringTokenizer t = new StringTokenizer(input, delimiter);
        String cmd[];

        while (t.hasMoreTokens())
            v.addElement(t.nextToken());

        cmd = new String[v.size()];

        for (int i = 0; i < cmd.length; i++)
            cmd[i] = (String) v.elementAt(i);

        return cmd;
    } // end of method tokenize

    /*
    ConvertArrToString takes a array and returns a csv string
    */

    public static String ConvertArrToString(String[] arr, String delimiter)
    {
        String retString = "";

        if (arr == null)
        {
            retString = "";
        }
        else
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (i != arr.length - 1)
                {
                    retString = retString + arr[i] + delimiter;
                }
                else
                {
                    retString = retString + arr[i];
                }
            }
        }

        return retString;
    } // end of method ConvertArrToString

    /*
     takes a String array and returns a Sentence
    */
    public static String getSentence(String[] arr, String delimiter)
    {
        String retString = "";

        if (arr == null)
        {
            retString = "";
        }
        else
        {
            for (int i = 0; i < arr.length; i++)
            {
            	if(i == arr.length - 2)
            	{
            		retString = retString + arr[i]	;
            	}
                else if (i != arr.length - 1)
                {
                    retString = retString + arr[i] + delimiter + " ";
                }
                else
                {
                    retString = retString + (retString.length()>0 ? " and " : "") + arr[i];
                }
            }
        }

        return retString;
    } // end of method getSentence

    /*
ConvertArrToString takes a Long array and returns a csv string    */

    public static String ConvertLongArrToString(Long[] arr, String delimiter)
    {
        String retString = "";

        if (arr == null)
        {
            retString = "";
        }
        else
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (i != arr.length - 1)
                {
                    retString = retString + arr[i].toString() + delimiter;
                }
                else
                {
                    retString = retString + arr[i].toString();
                }
            }
        }

        return retString;
    } // end of method ConvertArrToString

    /*
    ConvertArrToVector takes a array and returns a vector
    */

    public static Vector ConvertArrToVector(String[] arr)
    {

        Vector vector = new Vector();

        if (arr != null && arr.length > 0)
        {
            for (int i = 0; i < arr.length; i++)
            {
                vector.add(arr[i]);
            }
        }

        return vector;
    } // end of method ConvertArrToVector


    /*
    ConvertArrToVector takes a array and returns a ArrayList
    */
    public static ArrayList ConvertArrToArrayList(String[] arr)
    {

        ArrayList list = new ArrayList();

        if (arr != null && arr.length > 0)
        {
            for (int i = 0; i < arr.length; i++)
            {
                list.add(arr[i]);
            }
        }

        return list;
    } // end of method ConvertArrToArrayList

    public static String[] ConvertListToStringArray(List arrayList)
    {

        String[] arr = new String[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++)
        {
            arr[i] = (String) arrayList.get(i);
        }

        return arr;
    } // end of method ConvertListToStringArray

    /*
    NullValueReplacement takes a string and returns "" if the string is null
    and unchanged string otherwise.
    */

    public static String NullValueReplacement(String str)
    {
        String retString = str;

        if (str == null || str.length() == 0)
        {
            retString = "";
        }

        return retString;
    } // end of method NullValueReplacement

    /*
    NullValueReplacement takes a Long and returns "" if the object is null
    and toString  otherwise.
    */

    public static String NullValueReplacement(Long lng)
    {
        String retString = "";

        if (lng == null)
        {
            retString = "";
        }
        else
        {
            retString = lng.toString();
        }

        return retString;
    } // end of method NullValueReplacement

    /*
    NullValueHTMLReplacement takes a string and returns "&nbsp;" if the string is null
    and unchanged string otherwise.
    */

    public static String NullValueHTMLReplacement(String str)
    {
        String retString = str;

        if (str == null || str.length() == 0)
        {
            retString = "&nbsp;";
        }

        return retString;
    } // end of method NullValueHTMLReplacement

    /*
    isChecked takes a string and returns "checked" if the value is equal
    to checkedDefault; othewise it returs ""
    */

    public static String isChecked(String str, String checkedDefault)
    {
        String retString = str;

        if (str.equalsIgnoreCase(checkedDefault))
        {
            retString = " checked ";
        }
        else
        {
            retString = "";
        }

        return retString;
    } // end of method isChecked

    /*
    ifSelected takes a string and returns "selected" if the value is equal
    to checkedDefault; othewise it returs ""
    */

    public static String ifSelected(int currSel, int rowValue)
    {
        String retString = "";
        //System.out.println("The current selection 1 is " + currSel);
        //System.out.println("The rowValue 1 is " + rowValue);
        if (currSel == rowValue)
        {
            retString = " selected ";
        }
        else
        {
            retString = "";
        }

        return retString;
    } // end of method ifSelected

    /*
    ifSelectedString takes a string and returns "selected" if the value is equal
    to checkedDefault; othewise it returs ""
    */

    public static String ifSelectedString(String currSel, String rowValue)
    {
        String retString = "";

        if (currSel.equalsIgnoreCase(rowValue))
        {
            retString = " selected ";
        }
        else
        {
            retString = "";
        }

        return retString;
    } // end of method ifSelectedString
    /*
      trimZero trims a 0 IF it occurs as a first
      character in a string
      */

    public static String trimZero(String str)
    {
        String retString = str;

        if (str.indexOf("0") == 0)
        {
            retString = str.substring(1);
        }

        return retString;
    } // end of method NullValueReplacement

    /*
    
    Returns date in the format November 8, 2000 (if LONG format is stated)
    
    (Default)   Nov 8, 2000
    (SHORT)     11/8/00
    (MEDIUM)    Nov 8, 2000
    (LONG)      November 8, 2000
    (FULL)      Wednesday, November 8, 2000
    
    */
    public static String NowString()
    {

        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        String s = df.format(now);
        return s;
    } // end of method NowString

    public static String NowStringWTime()
    {

        Date now = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        String s = df.format(now);
        return s;
    } // end of method NowStringWTime

    public static Date getDate(String dateString)
    {

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try
        {
            Date date = df.parse(dateString);
            return date;
        }
        catch (Exception ex)
        {
            return null;
        }
    } // end of method getDate
    /*
     * loadProperties: load properties from the file
     * @return: Properties
     */
    public static Properties loadProperties(String fileName)
    {
        Properties p = null;
        try
        {
            InputStream f = ClassLoader.getSystemResourceAsStream(fileName);
            p = new Properties();
            p.load(f);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }

        return p;
    }

    public static String crlfEscape(String sOriginal)
    /**
    Name of method:   crlfEscape
    Purpose:          replaces all occurances of carriage return and line feed with ""
    Arguments:        original string
    Return Value(s):  The new string after carriage return and line feed with are replaced with "".
    */
    {
        String sReturn = new String();
        if (sOriginal != null)
        {
            sReturn = string_substitute(sOriginal, String.valueOf('\n'), "");
            sReturn = string_substitute(sReturn, String.valueOf('\r'), "");
        }
        else
            sReturn = "";
        return sReturn;
    } // end of method crlfEscape

    public static String sqlCrlfEscape(String sOriginal)
    /**
    Name of method:   crlfEscape
    Purpose:          replaces all occurances of carriage return and line feed with ""
    Arguments:        original string
    Return Value(s):  The new string after carriage return and line feed with are replaced with "".
    */
    {
        String sReturn = new String();
        StringBuffer strBuf = new StringBuffer();
        if (sOriginal != null)
        {
            String[] s = tokenize(sOriginal, String.valueOf('\n'));
            if (s.length > 0)
            {
                for (int i = 0; i < s.length; i++)
                {
                    if (i > 0)
                        strBuf.append("||");
                    strBuf.append("'");
                    strBuf.append(s[i]);
                    strBuf.append("'");
                    strBuf.append("||CHR(10)||CHR(13)");
                }
            }
            else
            {
                return sOriginal;
            }
            return strBuf.toString();
        }
        return sReturn;
    } // end of method crlfEscape

    /**
     * initializeIntGetParam method is used to convert the param valve to an integer.
     * if the the param is not present in the request then the defaultVal is returen
     */
    public static int initializeIntGetParam(String param, int defaultVal)
    {
        int val;
        if (param == null || param.length() == 0)
        {
            val = defaultVal;
        }
        else
        {
            val = Integer.parseInt(param);
        }
        return val;
    }

    /**
     * initializeStringGetParam method is used to convert the param valve to a String.
     * If the the param is not present in the request then the defaultVal is returned.
     */
    public static String initializeStringGetParam(String param, String defaultVal)
    {
        String val;
        if (param == null || param.length() == 0)
        {
            val = defaultVal;
        }
        else
        {
            val = param;
        }
        return val;
    }

    /**
     * initializeLongGetParam method is used to convert the param valve to a String.
     * If the the param is not present in the request then the defaultVal is returned.
     */
    public static String initializeLongGetParam(Long param, String defaultVal)
    {
        String val;
        if (param == null)
        {
            val = defaultVal;
        }
        else
        {
            val = param.toString();
        }
        return val;
    }

    /**
    * returnLongOrNull method is used to convert the param value(string) to a Long
    * If the the param is zero length of null then null is returned.
    */
    public static Long returnLongOrNull(String param)
    {
        Long val;
        if (param == null || param.length() == 0)
        {
            val = null;
        }
        else
        {
            val = new Long(param);
        }
        return val;
    }

    /*
    NulltoZeroStringReplacement takes a string and returns "" if the string is null
    and unchanged string otherwise.
    */

    public static String NulltoZeroStringReplacement(String str)
    {
        String retString = str;

        if (str == null || str.length() == 0)
        {
            retString = "0";
        }

        return retString;
    } // end of method NulltoZeroStringReplacement

    public static boolean isEmpty(String str)
    {
        if (str.equals(EMPTY))
            return true;
        else
            return false;
    }

    public static boolean isNullOrEmpty(String str)
    {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    public static String replaceInvalidCharacters(String str, String replacedChar)
    {
        String [] invalidChars = new String[] {" ", "\\", "/", ":", "*", "?", "\"", "<", ">", "|"};
        for(int i=0; i<invalidChars.length; i++)
        {
            str = StringUtils.replace(str, invalidChars[i], replacedChar);
        }
        return str;
    }
    public static String replaceInvalidCharacters(String str, String replacedChar, String[] invalidChars)
    {
//    	String [] invalidChars = new String[] {" ", "\\", "/", ":", "*", "?", "\"", "<", ">", "|"};
    	if (invalidChars != null &&  invalidChars.length >0){
	    	for(int i=0; i<invalidChars.length; i++)
	    	{
	    		str = StringUtils.replace(str, invalidChars[i], replacedChar);
	    	}
    	}
    	return str;
    }


    public static String[] addArray(String[] a, String[] b)
    {
        String[] c = new String[a.length + b.length];
        for (int i = 0; i < a.length; i++)
        {
            c[i] = a[i];
        }
        int k = 0;
        for (int j = a.length - 1; j < c.length; j++)
        {
            c[j] = b[k];
            k = k + 1;
        }
        return c;
    }

    /**
     * shortenString method is used to reduce the 'strValue' value to the 'intValue' length. 
     * If the length 'strValue' is greater than 'intValue-3', then ... is added after strValue
     */
    public static String shortenString(String strValue, int intValue)
    {
        String sReturn = null;
        if (strValue != null)
        {
            if ((strValue.length() > intValue - 3) && !(strValue.length() <= intValue))
                sReturn = strValue.substring(0, intValue).concat("...");
            System.out.println("\n\nshortenString" + sReturn);
        }
        return sReturn;

    }

    /**
     * Hex encoder
     */
    public static String asHex(byte buf[])
    {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;

        for (i = 0; i < buf.length; i++)
        {
            if (((int) buf[i] & 0xff) < 0x10)
                strbuf.append("0");

            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }

        return strbuf.toString();
    }


    /**
     * Returns trimmed value
     * It Returns null if given str is null
     * @param str
     * @return
     */
    public static String trim(String str) {
        return StringUtils.trim(str);
    }


    public static String getTruncatedString(int length, String data) {
    	return StringFuncs.getTruncatedString(length, data, false);
    }

    /**
     * Returns truncated string if length of data is greater than given length
     * @param length
     * @param data
     * @param filterData
     * @return
     */
    public static String getTruncatedString(int length, String data, boolean filterData) {
    	StringBuffer str=new StringBuffer();
    	try {
            if(filterData) {
                //escape html if filter data is true
                data = StringEscapeUtils.escapeHtml(data);
            }
            if (data!=null && data.length()>length) {
				str.append(data.substring(0, length));
				str.append("...");
			}
			else if (StringUtils.isEmpty(data)){
				return "";
			}
			else {
				str.append(data);
			}
		} catch (RuntimeException e) {
			log.error("Error in StringFuncs.getTruncatedString: "+data,e);
		}
    	return str.toString();
    }

} // end of class StringFuncs
