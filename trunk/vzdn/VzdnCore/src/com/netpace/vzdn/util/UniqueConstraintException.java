package com.netpace.vzdn.util;

import com.netpace.vzdn.util.VzdnException;


/**
 * This Exception class is thrown for Unique Constraint violations
 * @author Rizwan Qazi
 * @version 1.0
 * @see com.netpace.aims.bo.core.VzdnException 
 */
public class UniqueConstraintException extends VzdnException
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