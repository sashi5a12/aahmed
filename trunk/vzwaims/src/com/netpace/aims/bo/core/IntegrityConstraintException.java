package com.netpace.aims.bo.core;

import com.netpace.aims.bo.core.AimsException;


/**
 * This Exception class is thrown for Integrity Constraint violations
 * @author Rizwan Qazi
 * @version 1.0
 * @see com.netpace.aims.bo.core.AimsException 
 */
public class IntegrityConstraintException extends AimsException
{
   public IntegrityConstraintException()
   {
     super();
   }
   
   public IntegrityConstraintException(String message)
   {
     super(message);
   }
   
   public IntegrityConstraintException(Throwable cause)
   {
     super(cause);
   }
   
}