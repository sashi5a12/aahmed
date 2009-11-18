package com.netpace.aims.bo.application;

import com.netpace.aims.bo.core.AimsException;

/**
 * This Exception class is thrown on data parsijng of excel file
 * @author Ahson Imtiaz
 * @version 1.0
 * @see com.netpace.aims.bo.core.AimsException
 */

public class AimsInvalidDataException extends AimsException
{
   public AimsInvalidDataException()
   {
     super();
   }

   public AimsInvalidDataException(Throwable cause)
   {
     super(cause);
   }

}
