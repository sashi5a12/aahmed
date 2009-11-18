package com.netpace.aims.bo.security;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.core.AimsUser;

/**
 * This Exception class is thrown for Invalid user login
 * @author Shahnawaz Bagdadi
 * @version 1.0
 * @see com.netpace.aims.bo.core.AimsException 
 */
public class InvalidLoginException extends AimsException
{
   public InvalidLoginException()
   {
     super();
   }
   
   public InvalidLoginException(Throwable cause)
   {
     super(cause);
   }
   
}
