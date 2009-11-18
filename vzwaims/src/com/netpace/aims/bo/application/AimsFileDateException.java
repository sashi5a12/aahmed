package com.netpace.aims.bo.application;

import com.netpace.aims.bo.core.AimsException;

/**
 * This Exception class is thrown on data present for same date.
 * @author Ahson Imtiaz
 * @version 1.0
 * @see com.netpace.aims.bo.core.AimsException
 */

public class AimsFileDateException extends AimsException
{
   public AimsFileDateException()
   {
     super();
   }

   public AimsFileDateException(Throwable cause)
   {
     super(cause);
   }

}
