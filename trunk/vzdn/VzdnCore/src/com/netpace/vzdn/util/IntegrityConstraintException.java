package com.netpace.vzdn.util;

import com.netpace.vzdn.util.VzdnException;


/**
 * This Exception class is thrown for Integrity Constraint violations
 * @author Rizwan Qazi
 * @version 1.0
 * @see com.netpace.aims.bo.core.VzdnException 
 */
public class IntegrityConstraintException extends VzdnException
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