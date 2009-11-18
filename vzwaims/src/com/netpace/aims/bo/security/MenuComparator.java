package com.netpace.aims.bo.security;

import com.netpace.aims.model.security.*;
import java.io.Serializable;
import java.util.*;


/**
  *  This class is used to sort the menu items.
  */
public class MenuComparator implements Comparator,Serializable
{


  public int compare(Object o1,Object o2)
  {
    AimsMenu menu1 = (AimsMenu) o1;
    AimsMenu menu2 = (AimsMenu) o2;
    
    int value1 = menu1.getSortOrder().intValue();
    int value2 = menu2.getSortOrder().intValue();
    return  value1 - value2;
  }


  public boolean equals(Object obj)
  {
   return obj.equals(this);
  }


  
}
