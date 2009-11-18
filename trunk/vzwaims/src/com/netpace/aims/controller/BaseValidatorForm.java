package com.netpace.aims.controller;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts.validator.ValidatorForm;

import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.ImageInfo;
import com.netpace.aims.util.Utility;

/**
 * This is the base class from which all ValidatorForms will be derived.
 * This layer of abstraction is done to enable common tasks across all forms.
 *
 * Add XDoclet tags for Validator form in your sub-class , these tags will be picked by the Ant XDoclet task during build time and added to the struts config file.
 * @author Shahnawaz Bagdadi
 */
public abstract class BaseValidatorForm extends ValidatorForm
{

    static Logger log = Logger.getLogger(BaseValidatorForm.class.getName());

    protected boolean isBlankString(String str)
    {
        if (str == null)
            return true;
        return (str.trim().length() == 0);
    }

    protected boolean isLong(String str)
    {
        try
        {
            new java.lang.Long(str);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    protected boolean isNumber(String str)
    {
        log.debug("String isNumber --> " + str);
        try
        {
            Integer.parseInt(str);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    protected boolean isPositiveNumber(String str)
    {
        log.debug("String isNumber --> " + str);
        try
        {
            int x = Integer.parseInt(str);
            if (x > 0)
                return true;
            else
                return false;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    protected boolean isDecimal(String str)
    {
        try
        {
            new java.math.BigDecimal(str);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    protected boolean isDecimalWithinRange(String str, double low, double high)
    {
        try
        {
            java.math.BigDecimal dNumber = new java.math.BigDecimal(str);
            if ((dNumber.doubleValue() >= low) && (dNumber.doubleValue() <= high))
                return true;
            else
                return false;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    protected boolean isDate(String str)
    {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try
        {
            java.util.Date date = df.parse(str);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            String month = str.substring(0, str.indexOf("/"));
            String year = str.substring(str.lastIndexOf("/") + 1, str.length());
            int iMonth = Integer.parseInt(month);
            if ((iMonth - 1) != c1.get(Calendar.MONTH))
                return false;
            if (year.length() != 4) //e.g. Dates like 04/16/05 are not allowed as they can be confused with 04/16/0005
                return false;
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    protected boolean compareDateWithCurrentDate(String date)
    {

        Date date1 = Utility.convertToDate(date, AimsConstants.DATE_FORMAT);
        Date date2 = new Date(System.currentTimeMillis());

        return (date1.compareTo(date2) > 0);

    }

    protected boolean isValidFileSize(String maxFileSize, int fileSize)
    {

        boolean isValid = false;
        log.debug("Max file Size : " + maxFileSize + " ||| file size : " + fileSize);
        if ((maxFileSize != null) && Integer.parseInt(maxFileSize) >= fileSize)
        {
            isValid = true;
        }
        return isValid;
    }

    protected boolean isValidFileType(String strFile, String[] strExt)
    {

        boolean returnValue = false;
        try
        {

            if ((strFile == null) || (strExt == null))
                return false;
            else if (strFile.trim().length() == 0)
                return true;

            String extension = null;
            int whereDot = strFile.lastIndexOf('.');

            if (whereDot > 0 && whereDot <= strFile.length() - 2)
            {
                extension = strFile.substring(whereDot + 1).toLowerCase();
            }

            for (int i = 0; i < strExt.length; i++)
            {
                if (extension.equals(strExt[i]))
                {
                    returnValue = true;
                }

            }

        }
        catch (Exception ex)
        {
            returnValue = false;
        }

        return returnValue;
    }

    //This method can check for filenames like '*_template.doc'
    protected boolean isValidFileTypeWithName(String strFile, String[] strExt)
    {

        boolean returnValue = false;
        try
        {
            if ((strFile == null) || (strExt == null))
                return false;
            else if (strFile.trim().length() == 0)
                return true;

            String extension = null;
            int whereUnderScore = strFile.lastIndexOf('_');
            if ((whereUnderScore > 0) && (whereUnderScore <= strFile.length() - 2))
            {
                extension = strFile.substring(whereUnderScore + 1).toLowerCase();
            }

            for (int i = 0; i < strExt.length; i++)
            {
                if (extension.equals(strExt[i]))
                {
                    returnValue = true;
                }
            }
        }
        catch (Exception ex)
        {
            returnValue = false;
        }

        return returnValue;
    }

    protected boolean isDropDownSelected(Long lng)
    {
        if (lng == null)
            return false;
        return (lng.longValue() != 0);
    }

    protected boolean isSelectBoxPopulated(String[] arr)
    {
        if (arr == null)
            return false;

        return (arr.length != 0);
    }

    protected boolean isValidPhone(String str)
    {
        try
        {
            Pattern p = Pattern.compile("\\(?\\d\\d\\d\\)? *\\-? *\\d\\d\\d *\\-? *\\d\\d\\d\\d");
            return (p.matcher(str).matches());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    protected boolean isValidEmail(String str)
    {
        try
        {
            Pattern p =
                Pattern.compile(
                    "[a-zA-Z0-9][a-zA-Z0-9_.-]*@[a-zA-Z0-9][a-zA-Z0-9_-]*[a-zA-Z0-9]\\.[a-zA-Z0-9][a-zA-Z0-9_-]*[a-zA-Z0-9][\\.[a-zA-Z0-9][a-zA-Z0-9_-]*[a-zA-Z0-9]]*");
            //^[a-zA-Z0-9][a-zA-Z0-9_.-]*@([a-zA-Z0-9][a-zA-Z0-9_-]*[a-zA-Z0-9]|[a-zA-Z0-9][[a-zA-Z0-9_-]*\.[a-zA-Z0-9]*]*[a-zA-Z0-9]|[a-zA-Z0-9])\.\w\w\w
            return (p.matcher(str).matches());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    protected boolean isValidURL(String urlString)
    {
        try
        {
            URL testURL = new URL(urlString);
            testURL = null;
            return true;
        }
        catch (MalformedURLException e)
        {
            return false;
        }
    }

    protected boolean isValidWebURL(String str) {
		try {
			String urlRegex = "^((http|https|ftp)://)?(((www\\.)?[^ ]+\\.[com|org|net|edu|gov|us])|([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+))([^ ]+)?$";
			Pattern p = Pattern.compile(urlRegex);
			return (p.matcher(str.toLowerCase()).matches());
		} catch (Exception ex) {
			ex.printStackTrace();

			return false;
		}
	}
	

    protected boolean isValidHttpURL(String str) 
    {
    	try 
		{
    		//String urlRegex = "^http[s]?://([^.\\s\"<>#%{}\\[\\]|^~'\\\\])[-a-zA-Z0-9_.:]+[-a-zA-Z0-9_:@&?=+,.!/~*'%$]*$";
    		String urlRegex = "^http(s?)://(([a-z0-9]([a-z0-9]|-)*[a-z0-9]|[a-z0-9])\\.)+([a-z]([a-z0-9]|-)*[a-z0-9]|[a-z])(/([a-z0-9\\.!$&'\\(\\)*+,;=_~:@-]|%[a-f0-9]{2})*)*(\\?[a-z0-9\\.!$&'\\(\\)*+,;=_~:@/?-]*)?(\\#[a-z0-9\\.!$&'\\(\\)*+,;=_~:@/?-]*)?";
			Pattern p = Pattern.compile(urlRegex);
			return (p.matcher(str.toLowerCase()).matches());
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();

			return false;
		}
	}

    /**
     * Get file format, image resolution,  and physical resolution from 
     * JPEG, GIF, BMP, PCX, PNG, IFF, RAS, PBM, PGM, PPM and PSD files
     * @param in
     * @param width
     * @param height
     * @return
     */
    protected boolean isValidImageWidthHeight(InputStream in, int width, int height){
    	ImageInfo ii = new ImageInfo();
    	ii.setInput(in); // in can be InputStream or RandomAccessFile
		if (ii.check() == true && (ii.getWidth() <= width && ii.getHeight() <= height)) {
				return true;
		}
		else {
			return false;
		}
    }
    protected boolean isImageWidthHeightEqual(InputStream in, int width, int height){
    	ImageInfo ii = new ImageInfo();
    	ii.setInput(in); // in can be InputStream or RandomAccessFile
    	if (ii.check() == true && (ii.getWidth() == width && ii.getHeight() == height)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    protected boolean isAlphaNumeric(String str)
    {
    	try
        {
            Pattern p = Pattern.compile("[A-Za-z0-9\\s]*");
            
            return (p.matcher(str).matches());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    

    /**
     * This method checks whether provided string is a 4-digit year or not
     * Year must be started with 1 or 2 like 1090, 2001 
     * Passed: 2004, 1090, 1981
     * Failed: abac, 10, 001, 04, 6666
     * @param str
     * @return
     */
    protected boolean isValidYear(String str)
    {
        try
        {
        	Calendar toDay = Calendar.getInstance();
        	int year = toDay.get(Calendar.YEAR);
        	
            Pattern p = Pattern.compile(AimsConstants.PATTERN_4_DIGIT_YEAR);
            return (p.matcher(str).matches() && (Integer.parseInt(str)<=year));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
}
