package com.netpace.aims.bo.core;

import com.netpace.aims.bo.core.AimsException;


/**
 * This Exception class is thrown for violations occured due to illegally accessing records through changing the id in the url.
 * @author Rizwan Qazi
 * @version 1.0
 * @see com.netpace.aims.bo.core.AimsException 
 */
public class RecordNotFoundException extends AimsException
{
   public RecordNotFoundException()
   {
     super();
   }
   
   public RecordNotFoundException(String message)
   {
     super(message);
   }
   
   public RecordNotFoundException(Throwable cause)
   {
     super(cause);
   }
   
}