package com.netpace.aims.dataaccess.valueobjects;

import com.netpace.aims.model.BaseValueObject;
import java.io.Serializable;



/**
 * Interface declaration for all value objects.
 *
 * @author Fawad Sikandar
 * @version 1.0
 */
public interface ValueObject extends Serializable{

  public Object getSortFieldValue() ;

  public String getValue();

}

