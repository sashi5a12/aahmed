package com.netpace.aims.bo.core;

import com.netpace.aims.bo.core.AimsException;


/**
 * This Exception class is thrown for violations occured due to any reason. Hence all-general-purpose use.
 * @author Adnan Makda
 * @version 1.0
 * @see com.netpace.aims.bo.core.AimsException 
 */
public class GenericException extends AimsException
{
   public GenericException()
   {
     super();
   }
   
   public GenericException(String message)
   {
     super(message);
   }
   
   public GenericException(Throwable cause)
   {
     super(cause);
   }
   
}