package com.netpace.aims.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.netpace.aims.bo.core.TempFilesManager;

public class AimsSessionListener implements HttpSessionBindingListener,java.io.Serializable
{
   
   public void valueBound(HttpSessionBindingEvent event)
   {
     System.out.println("Initialize for session - " + event.getSession().getId());
   }
   
   public void valueUnbound(HttpSessionBindingEvent event)
   {
     System.out.println("Uninitialize for session - "  + event.getSession().getId());
     TempFilesManager.cleanTempFiles(event.getSession().getId());
     com.netpace.aims.bo.application.ReconcileCatalogManager.deleteTempData(event.getSession().getId());
   }
   
   
}

