package com.netpace.aims.bo.profile;

import com.netpace.aims.bo.core.AimsException;

/**
 * This Exception class is thrown on non-unique data
 * @author Ahson Imtiaz
 * @version 1.0
 * @see com.netpace.aims.bo.core.AimsException
 */

public class AimsEmailAddressException extends AimsException
{
   public AimsEmailAddressException()
   {
     super();
   }

   public AimsEmailAddressException(Throwable cause)
   {
     super(cause);
   }

}
