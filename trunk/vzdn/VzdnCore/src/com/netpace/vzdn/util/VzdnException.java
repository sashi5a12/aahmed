package com.netpace.vzdn.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;


/**
* This is the common superclass for all Aims application exceptions. This
* class and its subclasses support the chained exception facility that allows
* a root cause Throwable to be wrapped by this class or one of its
* descendants. This class also supports multiple exceptions via the
* exceptionList field.
* 
* All Aims User defined Exception will be in inherited from this class.
* @author Shahnawaz Bagdadi
*/

public class VzdnException extends Exception
{
 protected Throwable rootCause = null;

 private Collection exceptions = new ArrayList();
 
 private String messageKey = null;
 
 private Object[] messageArgs = null;
 
 public VzdnException()
 {
   super();
 }
 
 public VzdnException(String message)
 {
 	 super(message);
 }
   
 public VzdnException( Throwable cause ) 
 {
  this.rootCause = rootCause;
 }
 
 public Collection getCollection() 
 {
  return exceptions;
 }
 
 public void addException( VzdnException ex )
 {
  exceptions.add( ex );
  }
  
 public void setMessageKey( String key )
 {
  this.messageKey = key;
 }
 
 public String getMessageKey()
 {
  return messageKey;
 }
 
 public void setMessageArgs( Object[] args )
 {
 this.messageArgs = args;
 }
 
 public Object[] getMessageArgs()
 {
  return messageArgs;
  }
  
 public void setRootCause(Throwable anException) 
 {
  rootCause = anException;
  }
  
 public Throwable getRootCause() 
 {
  return rootCause;
 }

 public void printStackTrace() 
 {
  printStackTrace(System.err);
 }
 
 public void printStackTrace(PrintStream outStream) 
 {
  printStackTrace(new PrintWriter(outStream));
 }
 
 public void printStackTrace(PrintWriter writer) 
 {
  super.printStackTrace(writer);
  if ( getRootCause() != null ) 
  {
   getRootCause().printStackTrace(writer);
  }
  writer.flush();
 }
 
}