package com.netpace.aims.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.crypto.KeyGenerator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.axis.encoding.Base64;

import com.netpace.aims.controller.reports.displayobjects.UserDefinedReportDO;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.model.core.AimsPlatform;
import com.netpace.aims.model.events.AimsNotifAdHocRecipient;
import com.netpace.aims.model.events.AimsNotifAdHocRecipientLite;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.model.alliance.*;

public class MiscUtils
{

    public static String convertToPassedAndFailed(String text)
    {

        String value = "";

        if (Utility.isString(text))
        {
            value = ("P".equalsIgnoreCase(text) ? "Passed" : "Failed");
        }
        return value;
    }

    public static boolean isValidDate(String str, String format)
    {

        SimpleDateFormat df = new SimpleDateFormat(format);
        try
        {
            java.util.Date date = df.parse(str);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            String month = str.substring(0, str.indexOf("/"));
            int iMonth = Integer.parseInt(month);
            if ((iMonth - 1) != c1.get(Calendar.MONTH))
                return false;
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean contains(String value, Collection collection, boolean matchTable)
    {
        for (Iterator it = collection.iterator(); it.hasNext();)
        {
            UserDefinedReportDO displayObject = (UserDefinedReportDO) it.next();
            if (matchTable && displayObject.getReportObjectID().equals(value))
                return true;
            else if (!(matchTable) && displayObject.getReportObjectColumnId().equals(value))
                return true;
        }

        return false;
    }

    public static boolean linearSearch(String value, String[] array)
    {

        if (array != null)
            for (int i = 0; i < array.length; i++)
            {
                if (array[i].equals(value))
                {
                    return true;
                }
            }
        return false;
    }

    public static String getAllianceStatus(String key)
    {

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

    public static String getEmailCategory(String key)
    {

        HashMap emailCategoryHashMap = new HashMap();
        String retString = "";
        emailCategoryHashMap.put("C", "Canned");
        emailCategoryHashMap.put("S", "System");

        if (emailCategoryHashMap.containsKey(key))
        {
            retString = (String) emailCategoryHashMap.get(key);
        }
        return retString;
    }

    public static String getAppType(String key)
    {

        HashMap appTypeHashMap = new HashMap();
        String retString = "";
        appTypeHashMap.put(AimsConstants.BREW_APP_TYPE_NEW[0], AimsConstants.BREW_APP_TYPE_NEW[1]);
        appTypeHashMap.put(AimsConstants.BREW_APP_TYPE_EXISTING[0], AimsConstants.BREW_APP_TYPE_EXISTING[1]);
        appTypeHashMap.put(AimsConstants.BREW_APP_TYPE_CONCEPT[0], AimsConstants.BREW_APP_TYPE_CONCEPT[1]);

        if (appTypeHashMap.containsKey(key))
        {
            retString = (String) appTypeHashMap.get(key);
        }
        return retString;
    }

    public static String getAppTypeKeys(String value)
    {

        HashMap appTypeHashMap = new HashMap();
        String retString = "";
        appTypeHashMap.put(AimsConstants.BREW_APP_TYPE_NEW[0], AimsConstants.BREW_APP_TYPE_NEW[1].toUpperCase());
        appTypeHashMap.put(AimsConstants.BREW_APP_TYPE_EXISTING[0], AimsConstants.BREW_APP_TYPE_EXISTING[1].toUpperCase());
        appTypeHashMap.put(AimsConstants.BREW_APP_TYPE_CONCEPT[0], AimsConstants.BREW_APP_TYPE_CONCEPT[1].toUpperCase());

        Iterator it = appTypeHashMap.keySet().iterator();
        while (it.hasNext())
        {
            Object localKey = it.next();
            if (appTypeHashMap.get(localKey).toString().indexOf(value.toUpperCase()) != -1)
                retString = localKey.toString();
        }

        System.out.println("retString for appType is: " + retString);
        return retString;
    }

    public static String getWapAppContentType(String key)
    {

        HashMap appTypeHashMap = new HashMap();
        String retString = "";
        appTypeHashMap.put(AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[0], AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[1]);
        appTypeHashMap.put(AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[0], AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[1]);

        if (appTypeHashMap.containsKey(key))
        {
            retString = (String) appTypeHashMap.get(key);
        }
        return retString;
    }

    public static void sortFullName(AimsContact[] objects)
    {
        Arrays.sort(objects, new Comparator()
        {

            public int compare(Object o1, Object o2)
            {
                AimsContact value1 = ((AimsContact) o1);
                AimsContact value2 = ((AimsContact) o2);

                return ((value1.getFirstName().toUpperCase() + " " + value1.getLastName().toUpperCase())).compareTo(
                    (value2.getFirstName().toUpperCase() + " " + value2.getLastName().toUpperCase()));
            }
        });
    }

    public static Collection filterPlatformCollection(Collection allPlatforms, Collection excludedPlatforms)
    {

        Collection filteredList = new ArrayList();
        filteredList.addAll(allPlatforms);

        if (excludedPlatforms != null)
        {
            AimsPlatform[] platforms = (AimsPlatform[]) excludedPlatforms.toArray(new AimsPlatform[0]);
            for (int i = 0; i < platforms.length; i++)
            {
                Iterator iter = allPlatforms.iterator();
                while (iter.hasNext())
                {
                    AimsPlatform plat = (AimsPlatform) iter.next();
                    if ((plat.getPlatformId().equals(platforms[i].getPlatformId())))
                    {
                        filteredList.remove(plat);
                    }
                }
            }
        }
        return filteredList;
    }

    public static HashSet ConvertPlatformArrayToHashSet(Long[] array)
    {
        AimsPlatform platform = null;
        HashSet platformSet = new HashSet();

        if (array != null)
        {
            for (int i = 0; i < array.length; i++)
            {
                platform = new AimsPlatform();
                Long pfId = array[i];
                platform.setPlatformId(pfId);
                platformSet.add(platform);
            }
        }

        return platformSet;
    }

    public static HashSet ConvertContractArrayToHashSet(Long[] array)
    {
        AimsContract contract = null;
        HashSet contractSet = new HashSet();

        if (array != null)
        {
            for (int i = 0; i < array.length; i++)
            {
                contract = new AimsContract();
                Long pfId = array[i];
                contract.setContractId(pfId);
                contractSet.add(contract);
            }
        }
        return contractSet;
    }

    public static HashSet ConvertAppArrayToHashSet(Long[] array)
    {
        AimsApp app = null;
        HashSet appSet = new HashSet();

        if (array != null)
        {

            for (int i = 0; i < array.length; i++)
            {
                app = new AimsApp();
                Long appId = array[i];
                app.setAppsId(appId);
                appSet.add(app);

            }
        }
        return appSet;
    }

    /*public static HashSet convertCarriersArrayToHashSet(Long[] array)
    {
        AimsCarriers aimsCarriers = null;
        HashSet carriersSet = new HashSet();

        if (array != null)
        {

            for (int i = 0; i < array.length; i++)
            {
                aimsCarriers = new AimsCarriers();
                Long carrierId = array[i];
                aimsCarriers.setCarrierId(carrierId);
                carriersSet.add(aimsCarriers);
            }
        }
        return carriersSet;
    }
    public static HashSet convertVzwReasonsArrayToHashSet(Long[] array)
    {
        AimsVzwReasons aimsVzwReasons = null;
        HashSet vzwReasonsSet = new HashSet();

        if (array != null)
        {

            for (int i = 0; i < array.length; i++)
            {
                aimsVzwReasons = new AimsVzwReasons();
                Long vzwReasonsId = array[i];
                aimsVzwReasons.setReasonsId(vzwReasonsId);
                vzwReasonsSet.add(aimsVzwReasons);
            }
        }
        return vzwReasonsSet;
    }
    public static HashSet convertFinancingArrayToHashSet(Long[] array)
    {
        AimsFinancingOptions aimsFinancingOptions = null;
        HashSet financingOptionsSet = new HashSet();

        if (array != null)
        {

            for (int i = 0; i < array.length; i++)
            {
                aimsFinancingOptions = new AimsFinancingOptions();
                Long financingOptionId = array[i];
                aimsFinancingOptions.setFinancingOptionsId(financingOptionId);
                financingOptionsSet.add(aimsFinancingOptions);
            }
        }
        return financingOptionsSet;
    }*/

    public static HashSet convertCarriersArrayToHashSet(Long[] array)
    {
        AimsCarriers aimsCarrier = null;
        AimsAllianceCarriers allianceCarrier = null;
        HashSet allianceCarriersSet = new HashSet();

        if (array != null)
        {

            for (int i = 0; i < array.length; i++)
            {
                aimsCarrier = new AimsCarriers();
                aimsCarrier.setCarrierId(array[i]);
                allianceCarrier = new AimsAllianceCarriers(null, aimsCarrier);
                allianceCarriersSet.add(allianceCarrier);
            }
        }
        return allianceCarriersSet;
    }
    public static HashSet convertVzwReasonsArrayToHashSet(Long[] array)
    {
        AimsVzwReasons aimsVzwReason = null;
        AimsAllianceVzwReasons allianceVzwReason = null;
        HashSet allianceVzwReasonsSet = new HashSet();

        if (array != null)
        {

            for (int i = 0; i < array.length; i++)
            {
                aimsVzwReason = new AimsVzwReasons();
                aimsVzwReason.setReasonsId(array[i]);

                allianceVzwReason = new AimsAllianceVzwReasons(null, aimsVzwReason);
                allianceVzwReasonsSet.add(allianceVzwReason);
            }
        }
        return allianceVzwReasonsSet;
    }
    public static HashSet convertFinancingArrayToHashSet(Long[] array)
    {
        AimsFinancingOptions aimsFinancingOption = null;
        AimsAllianceFinancing allianceFinancingOption = null;
        HashSet allianceFinancingOptionsSet = new HashSet();

        if (array != null)
        {

            for (int i = 0; i < array.length; i++)
            {
                aimsFinancingOption = new AimsFinancingOptions();
                aimsFinancingOption.setFinancingOptionsId(array[i]);
                allianceFinancingOption = new AimsAllianceFinancing(null, aimsFinancingOption);
                allianceFinancingOptionsSet.add(allianceFinancingOption);
            }
        }
        return allianceFinancingOptionsSet ;
    }
    public static HashSet convertDevelopmentTechnologiesArrayToHashSet(Long[] array)
    {
        AimsDevTechnology aimsDevTechnology = null;
        AimsAllianceDevTech aimsAllianceDevTech = null;
        HashSet allianceDevelopmentTechnologiesSet = new HashSet();

        if (array != null)
        {

            for (int i = 0; i < array.length; i++)
            {
                aimsDevTechnology = new AimsDevTechnology();
                aimsDevTechnology.setDevTechnologyId(array[i]);
                aimsAllianceDevTech = new AimsAllianceDevTech(null, aimsDevTechnology);
                allianceDevelopmentTechnologiesSet.add(aimsAllianceDevTech);
            }
        }
        return allianceDevelopmentTechnologiesSet ;
    }
    public static HashSet convertDevelopmentsArrayToHashSet(Long[] array)
    {
        AimsDevelopments aimsDevelopment = null;
        AimsAllianceDevelopments allianceDevelopment = null;
        HashSet allianceDevelopmentsSet = new HashSet();

        if (array != null)
        {

            for (int i = 0; i < array.length; i++)
            {
                aimsDevelopment = new AimsDevelopments();
                aimsDevelopment.setDevelopmentId(array[i]);
                allianceDevelopment = new AimsAllianceDevelopments(null, aimsDevelopment);
                allianceDevelopmentsSet.add(allianceDevelopment);
            }
        }
        return allianceDevelopmentsSet ;
    }



    public static String convertToActiveOrInactive(String text)
    {
        String value = "";

        if (Utility.isString(text))
        {
            value = ("A".equalsIgnoreCase(text) ? "Active" : "Inactive");
        }
        return value;
    }

    public static HashSet ConvertRoleGroupArrayToHashSet(Long[] array)
    {
        AimsSysRol roleGroup = null;
        HashSet roleGroupSet = new HashSet();

        if (array != null)
        {

            for (int i = 0; i < array.length; i++)
            {
                roleGroup = new AimsSysRol();
                Long roleId = array[i];
                roleGroup.setRoleId(roleId);
                roleGroupSet.add(roleGroup);

            }
        }

        return roleGroupSet;
    }

    public static Set ConvertEmailAddresstoHashSet(String input, String delimiter)
    {

        Set set = null;
        AimsNotifAdHocRecipient notifAdHocRecipient = null;

        if (!Utility.isString(input))
            return null;

        set = new HashSet();
        StringTokenizer tokens = new StringTokenizer(input, delimiter);
        while (tokens.hasMoreTokens())
        {

            notifAdHocRecipient = new AimsNotifAdHocRecipient();
            notifAdHocRecipient.setEmailAddress(tokens.nextToken());
            set.add(notifAdHocRecipient);
        }
        return set;
    }

    public static String appendEmailAddressByDelimiter(Collection collection, String delimiter)
    {
        StringBuffer strbuffer = new StringBuffer();

        if (collection != null && !(collection.isEmpty()))
        {
            Iterator iter = collection.iterator();
            System.out.println(" No. of Emails: " + collection.size());

            while (iter.hasNext())
            {
                strbuffer.append(((AimsNotifAdHocRecipientLite) iter.next()).getEmailAddress());
                if (iter.hasNext())
                    strbuffer.append(delimiter);
            }
            System.out.println(" Buffer: " + strbuffer.toString());
        }
        return strbuffer.toString();
    }

    public static String checkYesNo(String value)
    {
        return "on".equalsIgnoreCase(value) ? "Y" : "";
    }

    public static String convertToYesNo(String value)
    {
        return "Y".equalsIgnoreCase(value) ? "yes" : "";
    }

    public static java.io.InputStream convertBlobObjectToInputStream(java.lang.Object blobObject) throws SQLException
    {
        java.io.InputStream inputStream = null;

        inputStream = ((Blob) blobObject).getBinaryStream();

        return inputStream;
    }

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

    public static String getRequestInfo(HttpServletRequest request)
    {
        StringBuffer requestInfo = new StringBuffer();
        requestInfo.append("\n\n\nREQUEST INFORMATION:\n");
        requestInfo.append("Servlet Path: " + request.getServletPath() + "\n");
        requestInfo.append("Method: " + request.getMethod() + "\n");
        requestInfo.append("Request URL: " + request.getRequestURL() + "\n");
        requestInfo.append("Query String: " + request.getQueryString() + "\n");
        requestInfo.append("Server Name: " + request.getServerName() + "\n");
        requestInfo.append("Remote Address: " + request.getRemoteAddr() + "\n");
        requestInfo.append("Remote Host: " + request.getRemoteHost() + "\n\n");

        Enumeration requestNames = request.getHeaderNames();
        while (requestNames.hasMoreElements())
        {
            String reqName = requestNames.nextElement().toString();
            Object reqValue = request.getHeader(reqName);
            requestInfo.append("Request Header: " + reqName + " = " + reqValue + "\n");
        }

        requestInfo.append("\n");
        requestNames = request.getAttributeNames();
        while (requestNames.hasMoreElements())
        {
            String reqName = requestNames.nextElement().toString();
            Object reqValue = request.getAttribute(reqName);
            requestInfo.append("Request Attribute: " + reqName + " = " + reqValue + "\n");
        }

        requestInfo.append("\n");
        requestNames = request.getParameterNames();
        while (requestNames.hasMoreElements())
        {
            String reqName = requestNames.nextElement().toString();
            Object reqValue = request.getParameter(reqName);
            requestInfo.append("Request Parameter: " + reqName + " = " + reqValue + "\n");
        }

        return requestInfo.toString();
    }

    public static String getSessionInfo(HttpSession session)
    {
        StringBuffer sessionInfo = new StringBuffer();

        if (session != null)
        {
            sessionInfo.append("\n\n\nSESSION INFORMATION:\n");
            sessionInfo.append("Session ID: " + session.getId() + "\n");
            sessionInfo.append("New Session: " + session.isNew() + "\n");
            sessionInfo.append("Creation Time: " + new Date(session.getCreationTime()) + "\n");
            sessionInfo.append("Last Accessed Time: " + new Date(session.getLastAccessedTime()) + "\n");
            sessionInfo.append("Session Inactive Interval: " + session.getMaxInactiveInterval() + "\n\n");

            Enumeration sessionNames = session.getAttributeNames();
            while (sessionNames.hasMoreElements())
            {
                String sesName = sessionNames.nextElement().toString();
                Object sesValue = session.getAttribute(sesName);
                sessionInfo.append("Session Attribute: " + sesName + " = " + sesValue + "\n");
            }
        }

        return sessionInfo.toString();
    }

    public static byte[] generate128bitkey() throws NoSuchAlgorithmException
    {
        //Get the KeyGenerator
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128); // 192 and 256 bits may not be available

        //Generate the secret key specs.
        return kgen.generateKey().getEncoded();
    }

    public static String base64Encode(byte[] bytes)
    {
        // Use Sun's encoder in this sample.
        return Base64.encode(bytes);
        //return new sun.misc.BASE64Encoder().encode(bytes);
    }
    public static synchronized String getBase64Digest(byte[] nonce, byte[] created, byte[] password)
    {
        try
        {
            MessageDigest messageDigester = MessageDigest.getInstance("SHA-1");
            // SHA-1 ( nonce + created + password )
            messageDigester.reset();
            messageDigester.update(nonce);
            messageDigester.update(created);
            messageDigester.update(password);

            return base64Encode(messageDigester.digest());
        }
        catch (java.security.NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }

    //param1 can be either vendor_id or device_uuid
    public static String getBase64Digest(String param1, String company_id, String password)
    {
        try
        {
            MessageDigest messageDigester = MessageDigest.getInstance("SHA-1");
            
            messageDigester.reset();
            messageDigester.update(param1.getBytes());
            messageDigester.update(company_id.getBytes());
            messageDigester.update(password.getBytes());

            return base64Encode(messageDigester.digest());
        }
        catch (java.security.NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
    public String[] mergeStringArrays(String []firstArray, String[] secondArray) {
        ArrayList mergedArrayList = new ArrayList();
        mergedArrayList.addAll(Arrays.asList(firstArray));
        mergedArrayList.addAll(Arrays.asList(secondArray));
        return (String[])mergedArrayList.toArray(new String[0]);
    }

    public static void main(String arg[]){
    	String generatedKey=MiscUtils.getBase64Digest("0", "13876", "Netpace-Intertek-Secret-Password");
    	System.out.println(generatedKey);
    	
    }
}
