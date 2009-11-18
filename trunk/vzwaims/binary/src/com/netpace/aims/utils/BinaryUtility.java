package com.netpace.aims.utils;

import java.security.MessageDigest;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class BinaryUtility {
	
	public static String getBase64Digest(String param1, String company_id, String password) {
		try {
			MessageDigest messageDigester = MessageDigest.getInstance("SHA-1");

			messageDigester.reset();
			messageDigester.update(param1.getBytes());
			messageDigester.update(company_id.getBytes());
			messageDigester.update(password.getBytes());

			return base64Encode(messageDigester.digest());
		} catch (java.security.NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static String base64Encode(byte[] bytes) {
		// return Base64.encode(bytes);
		return new sun.misc.BASE64Encoder().encode(bytes);
	}

	public static String getRequestInfo(HttpServletRequest request) {
		StringBuffer requestInfo = new StringBuffer();
		requestInfo.append("\n\n\nREQUEST INFORMATION:\n");
		requestInfo.append("Servlet Path: " + request.getServletPath() + "\n");
		requestInfo.append("Method: " + request.getMethod() + "\n");
		requestInfo.append("Request URL: " + request.getRequestURL() + "\n");
		requestInfo.append("Query String: " + request.getQueryString() + "\n");
		requestInfo.append("Server Name: " + request.getServerName() + "\n");
		requestInfo.append("Remote Address: " + request.getRemoteAddr() + "\n");
		requestInfo.append("Remote Host: " + request.getRemoteHost() + "\n\n");

		Enumeration requestNames = request.getHeaderNames();
		while (requestNames.hasMoreElements()) {
			String reqName = requestNames.nextElement().toString();
			Object reqValue = request.getHeader(reqName);
			requestInfo.append("Request Header: " + reqName + " = " + reqValue + "\n");
		}

		requestInfo.append("\n");
		requestNames = request.getAttributeNames();
		while (requestNames.hasMoreElements()) {
			String reqName = requestNames.nextElement().toString();
			Object reqValue = request.getAttribute(reqName);
			requestInfo.append("Request Attribute: " + reqName + " = " + reqValue + "\n");
		}

		requestInfo.append("\n");
		requestNames = request.getParameterNames();
		while (requestNames.hasMoreElements()) {
			String reqName = requestNames.nextElement().toString();
			Object reqValue = request.getParameter(reqName);
			requestInfo.append("Request Parameter: " + reqName + " = " + reqValue + "\n");
		}

		return requestInfo.toString();
	}
	
    public static boolean isNullOrEmpty(String str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}

}
