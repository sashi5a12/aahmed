package com.netpace.aims.bo.security;

import com.netpace.aims.model.security.*;
import java.util.*;
import java.io.Serializable;

/**
  *  This class is used to sort the menu items.
  */
public class SubMenuComparator implements Comparator,Serializable
{


  public int compare(Object o1,Object o2)
  {
    AimsSubMenu submenu1 = (AimsSubMenu) o1;
    AimsSubMenu submenu2 = (AimsSubMenu) o2;
    
    int value1 = (submenu1.getAimsMenu().getSortOrder().intValue() * 100) + submenu1.getSortOrder().intValue();
    int value2 = (submenu2.getAimsMenu().getSortOrder().intValue() * 100) + submenu2.getSortOrder().intValue();
    //System.out.println("value1 : " + value1 + " value2 : " + value2);
    return value1 - value2;
  }


  public boolean equals(Object obj)
  {
   return obj.equals(this);
  }


  
}
