package com.netpace.aims.controller.webservices;

/**
 * 
 * <pre>
 * &lt;s:complexType name="SubmitDocumentResponse" xmlns:s="http://www.w3.org/2001/XMLSchema">
 * &lt;s:sequence>
 * &lt;s:element minOccurs="0" maxOccurs="1" name="CallingUrl" type="s:string"/>
 * &lt;s:element minOccurs="0" maxOccurs="1" name="ErrorMsg" type="s:string"/>
 * &lt;/s:sequence>
 * &lt;/s:complexType>
 * </pre>
 */

public class SubmitDocumentResponse 
{
  private String m_CallingUrl;

  private String m_ErrorMsg;

  public SubmitDocumentResponse()
  {
  }

  public SubmitDocumentResponse(String CallingUrl, String ErrorMsg)
  {
    m_CallingUrl = CallingUrl;
    m_ErrorMsg = ErrorMsg;
  }

  public void setCallingUrl(String CallingUrl)
  {
    m_CallingUrl = CallingUrl;
  }

  public String getCallingUrl()
  {
    return m_CallingUrl;
  }

  public void setErrorMsg(String ErrorMsg)
  {
    m_ErrorMsg = ErrorMsg;
  }

  public String getErrorMsg()
  {
    return m_ErrorMsg;
  }
}