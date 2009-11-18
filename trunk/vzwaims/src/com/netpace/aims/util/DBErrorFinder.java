package com.netpace.aims.util;

import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.bo.core.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import java.util.*;

/*
Name of file:   DBErrorFinder.java
Date:           12/01/2003
Purpose:        Contains functions to map errors from Exception Messages String 
Arguments:      N/A
History:
*/


//ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS
//ManageApplicationsConstants.INTEGRITY_CONSTRAINT_KEYS

public class DBErrorFinder
{

		//This method populates the AimsException collection with Unique Constraints (if any)
		public static boolean searchUniqueConstraintErrors(String exMessage, String[] keyList, AimsException aimsException)
    {
			boolean errorFound = false;
			
			for (int i=0;i<keyList.length/2;i++)
			{
				Object[] objs = {keyList[i*2]};
				
				if (exMessage.indexOf(AimsConstants.UNIQUE_CONSTRAINT_MESSAGE.format(objs)) > -1) 
				{		
					aimsException.addException(new UniqueConstraintException(keyList[(i*2)+1]));
					errorFound=true;
				} 
			}
      
      return errorFound;
    }

    //This method populates the AimsException collection with Unique Constraints (if any), it reads schema name from environment properties file
	public static boolean searchUniqueConstraintErrorsWithSchemaName(String exMessage, String[] keyList, AimsException aimsException)
    {
			boolean errorFound = false;

            String schemaName = StringFuncs.NullValueReplacement(ConfigEnvProperties.getInstance().getProperty("connection.schema"));
            for (int i=0;i<keyList.length/2;i++)
			{
				Object[] objs = {schemaName, keyList[i*2]};
                if (exMessage.indexOf(AimsConstants.UNIQUE_CONSTRAINT_MESSAGE_DB.format(objs)) > -1)
				{
					aimsException.addException(new UniqueConstraintException(keyList[(i*2)+1]));
					errorFound=true;
                }
			}

      return errorFound;
    }
    
    //This method populates the messages collection with Integrity Constraints (if any)
    public static boolean searchIntegrityConstraintErrors(String exMessage, String[] keyList, AimsException aimsException)
    {
			boolean errorFound = false;
			
			for (int i=0;i<keyList.length/2;i++)
			{
				Object[] objs = {keyList[i*2]};
				
				if (exMessage.indexOf(AimsConstants.INTEGRITY_CONSTRAINT_MESSAGE .format(objs)) > -1) 
				{		
					aimsException.addException(new IntegrityConstraintException(keyList[(i*2)+1]));
					errorFound=true;
				} 
			}
      
      return errorFound;
    }
    
    //This method checks to see if there is Integrity Constraint
    public static boolean isIntegrityConstraintError(String exMessage, AimsException aimsException)
    {
			boolean errorFound = false;
			
			if (exMessage.indexOf(AimsConstants.INTEGRITY_CONSTRAINT_MESSAGE_PREFIX) > -1) 
			{		
				aimsException.addException(new IntegrityConstraintException(AimsConstants.INTEGRITY_CONSTRAINT_RES_MESSAGE));
				errorFound=true;
			} 			
      return errorFound;
    }
     
    //This method populates and returns the errors collection with the errors contained in the AimsException's Collection
    public static ActionErrors populateActionErrors(AimsException aimsExceptionList)
    {
			AimsException aimsException = null;
			ActionErrors errors = new ActionErrors();
			for	(Iterator it = aimsExceptionList.getCollection().iterator();	it.hasNext();) 
			{
				aimsException = (AimsException) it.next();			
				if (aimsException.getMessageArgs() != null)
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(aimsException.getMessage(), aimsException.getMessageArgs()));			
				else
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(aimsException.getMessage()));			
			}
			return errors;
    }   
 
}