package com.netpace.aims.bo.security;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.core.AimsUser;

/**
 * This Exception class is thrown for Invalid user information
 * @author Fawad Sikandar
 * @version 1.0
 * @see com.netpace.aims.bo.core.AimsException
 */
public class InvalidUserInfoException extends AimsException
{
   public InvalidUserInfoException()
   {
     super();
   }

   public InvalidUserInfoException(Throwable cause)
   {
     super(cause);
   }

}
