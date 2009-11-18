package com.netpace.aims.util;

import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.security.AimsSysRole;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 * This class is a general utility class for the AIMS application.
 * @author Rizwan Qazi
 */

public class AimsUtils
{

	public static String getAllianceStatus (String key) {
		HashMap allianceStatusHashMap = new HashMap();
		String retString = "";
		allianceStatusHashMap.put("A", "Accepted");
		allianceStatusHashMap.put("R", "Rejected");
		allianceStatusHashMap.put("S", "Submitted");
        allianceStatusHashMap.put("U", "Reviewed");
        allianceStatusHashMap.put("D", "Deleted");

		if (allianceStatusHashMap.containsKey(key))
		{
			retString = (String) allianceStatusHashMap.get(key);
		}
		return retString;
	}

	public static String getAllianceStatusString (String searchString) {

		HashMap allianceStatusHashMap = new HashMap();
		String retString = "";
        String tempString = "";
        String tString = "";

		allianceStatusHashMap.put("ACCEPTED", "A");
		allianceStatusHashMap.put("REJECTED", "R");
		allianceStatusHashMap.put("SUBMITTED", "S");
        allianceStatusHashMap.put("REVIEWED", "U");
        allianceStatusHashMap.put("DELETED", "D");

        Set allianceKeySet = allianceStatusHashMap.keySet();

        for (Iterator iter = allianceKeySet.iterator(); iter.hasNext();)
        {
            tempString = (String)  iter.next();
            if (tempString.indexOf(searchString.trim().toUpperCase()) > -1)
            {
                tString = "'" + (String) allianceStatusHashMap.get(tempString) + "'";
                System.out.println ("This is a tString " + tString);
                if (retString.length() > 0)
                {
                    retString = retString + "," + tString ;
                }
                else
                {
                    retString = retString + tString ;
                }
            }
            tString = "";
        }

        if (retString.length() <= 0)
        {
            retString = "'1'";
        }

		return retString;
	}


	public static String getContractStatus (String key) {
		HashMap contractStatusHashMap = new HashMap();
		String retString = "";
		contractStatusHashMap.put("A", "Active");
		contractStatusHashMap.put("E", "Expired");
		contractStatusHashMap.put("H", "On Hold");
		if (contractStatusHashMap.containsKey(key))
		{
			retString = (String) contractStatusHashMap.get(key);
		}
		return retString;
	}

	public static String getYesNoStatus (String key) {
		HashMap yesNoStatusHashMap = new HashMap();
		String retString = "";
		yesNoStatusHashMap.put("Y", "Yes");
		yesNoStatusHashMap.put("N", "No");
		if (yesNoStatusHashMap.containsKey(key))
		{
			retString = (String) yesNoStatusHashMap.get(key);
		}
		return retString;
	}

	public static String getPlatform(String key){
		HashMap platforms = new HashMap();
		platforms.put(AimsConstants.BREW_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_BREW);
		platforms.put(AimsConstants.SMS_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_SMS);
		platforms.put(AimsConstants.MMS_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_MMS);
		platforms.put(AimsConstants.WAP_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_WAP);
		platforms.put(AimsConstants.ENTERPRISE_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_ENTERPRISE);
		platforms.put(AimsConstants.VCAST_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_VCAST);
		if (platforms.containsKey(key)){
			return platforms.get(key).toString();
		}
		return key;
	}
	public static boolean isDate (String str) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
      java.util.Date date = df.parse(str);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			String month = str.substring(0,str.indexOf("/"));
			int iMonth = Integer.parseInt(month);
			if ((iMonth-1) != c1.get(Calendar.MONTH))
				return false;

			return true;
		} catch (Exception ex) {
			return false;
		}
	}

        public static String appendProtocol(String text)
        {
          StringBuffer url= new StringBuffer();

          if ( text != null && text.trim().length() != 0 )
          {

           if (!text.startsWith("http://"))
           {
               url.append("http://").append(text);
           } else
               url.append(text);
          }
          return url.toString();
        }

    /**
     * this method checks whether given user is alliance admin or not, by matching its roletype
     * @param user
     * @return
     */
    public static boolean isAllianceAdmin(AimsUser user)
    {
        boolean isAlliancAdmin = false;
        Set roles = user.getRoles();
        AimsSysRole role = null;
        Iterator rolesIter = null;
        if(roles!=null)
        {
            rolesIter = roles.iterator();
            while (rolesIter.hasNext())
            {
                role = (AimsSysRole) rolesIter.next();
                if (AimsConstants.ALLIANCEADMIN_ROLETYPE.equalsIgnoreCase(role.getRoleType()))
                {
                    isAlliancAdmin = true;
                    break;
                }
            }//end while
        }
        return isAlliancAdmin;
    }
}