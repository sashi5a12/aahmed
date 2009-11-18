package com.netpace.vzdn.security;


/**
 * This Exception class is thrown for Invalid acess to a Action
 * @author Shahnawaz Bagdadi
 * @version 1.0
 * @see com.netpace.aims.bo.core.VzdnException 
 */
public class VzdnSecurityException extends Exception
{
   public VzdnSecurityException()
   {
     super();
   }
   
   public VzdnSecurityException(Throwable cause)
   {
     super(cause);
   }
   
}
