package com.netpace.aims.bo.security;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.core.AimsUser;

/**
 * This Exception class is thrown for Invalid acess to a Action
 * @author Shahnawaz Bagdadi
 * @version 1.0
 * @see com.netpace.aims.bo.core.AimsException 
 */
public class AimsSecurityException extends AimsException
{
   public AimsSecurityException()
   {
     super();
   }
   
   public AimsSecurityException(Throwable cause)
   {
     super(cause);
   }
   
}
