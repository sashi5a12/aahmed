package com.netpace.aims.bo.core;

import com.netpace.aims.bo.core.AimsException;


/**
 * This Exception class is thrown for Unique Constraint violations
 * @author Rizwan Qazi
 * @version 1.0
 * @see com.netpace.aims.bo.core.AimsException 
 */
public class UniqueConstraintException extends AimsException
{
   public UniqueConstraintException()
   {
     super();
   }
   
   public UniqueConstraintException(String message)
   {
     super(message);
   }
   
   public UniqueConstraintException(Throwable cause)
   {
     super(cause);
   }
   
}