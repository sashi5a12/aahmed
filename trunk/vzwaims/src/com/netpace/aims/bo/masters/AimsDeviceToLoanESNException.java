package com.netpace.aims.bo.masters;

import com.netpace.aims.bo.core.AimsException;

/**
 * This Exception class is thrown on non-unique data
 * @author Ahson Imtiaz
 * @version 1.0
 * @see com.netpace.aims.bo.core.AimsException
 */

public class AimsDeviceToLoanESNException extends AimsException
{
   public AimsDeviceToLoanESNException()
   {
     super();
   }

   public AimsDeviceToLoanESNException(Throwable cause)
   {
     super(cause);
   }

}
