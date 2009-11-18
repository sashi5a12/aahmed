package com.netpace.aims.ws.amdocs.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.netpace.aims.ws.amdocs.WSSEHandler;

public class RestfulClient {
	
	private String username = null;
	private String password = null;
	private String url = null;
	private String filePath = null;
	private static final Header HEADER_AUTH = new Header("Authorization", "WSSE profile=\"UsernameToken\"");;
	
	
	public RestfulClient(String username, String password, String url, String filePath) {
		this.username = username;
		this.password = password;
		this.url = url;
		this.filePath = filePath;
	}

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * @throws Exception 
	 * @throws Exception
	 */
	public static void main(String[] args) {

		System.out.println(" Username = " + args[0]);
		System.out.println(" Password = " + args[1]);
		System.out.println(" URL = " + args[2]);
		System.out.println(" Input File = " + args[3]);
		
		RestfulClient restfulClient = new RestfulClient(args[0], args[1], args[2], args[3]);
		System.out.println("Calling RestfulClient.callGetWithWsse()......");
		String response2  = restfulClient.callPostWithWsse(args[2], args[3]);
		System.out.println("response = " + response2);
	}

	
	/**
	 * Make REST Web Service call for GET
	 * @param url
	 * @return Response XML document
	 * @throws Exception
	 */
	public String callGetWithWsse(String url) {
		HttpClient httpClient = new HttpClient();
		String EOL = System.getProperty("line.separator");

		GetMethod getMethod = null;
		StringBuilder buffer = new StringBuilder();
		try {
			getMethod = new GetMethod(url);
			//set wsse header
			getMethod.setRequestHeader(HEADER_AUTH);
			getMethod.setRequestHeader("X-WSSE", new WSSEHandler().getAuthString(username, password));
			
			httpClient.executeMethod(getMethod);

			BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								getMethod.getResponseBodyAsStream()));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line).append(EOL);
			}
			System.out.println("REST inquiry with URL "
					+ url
					+ " returned"
					+ (buffer.length() == 0 ? " EMPTY STRING" : ":" + EOL
							+ buffer.toString()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return buffer.toString();

	}
	
	
	/**
	 * Make REST Web Service call for GET
	 * @param url
	 * @return Response XML document
	 * @throws Exception
	 */
	public String callPostWithWsse(String url, String file) {
		HttpClient httpClient = new HttpClient();
		String EOL = System.getProperty("line.separator");

		PostMethod postMethod = null;
		StringBuilder sBuffer = new StringBuilder();
		try {
			postMethod = new PostMethod(url);
			//set wsse header
			postMethod.setRequestHeader(HEADER_AUTH);
			postMethod.setRequestHeader("X-WSSE", new WSSEHandler().getAuthString(username, password));
			
			//write request
			FileInputStream body = new FileInputStream(new File(file));
			postMethod.setRequestBody(body);
	        // write body if we're doing POST or PUT
			/*
	        byte buffer[] = new byte[8192];
	        int read = 0;
	        if (body != null)
	        {
	        	ByteArrayOutputStream  output = new ByteArrayOutputStream();
				RequestEntity re = postMethod.getRequestEntity();
				re.writeRequest(output);
				while ((read = body.read(buffer)) != -1)
	            {
	                output.write(buffer, 0, read);
	            }
	        }
			*/
			
			//make call
			httpClient.executeMethod(postMethod);
			
			//read response
			BufferedReader reader = new BufferedReader(
						new InputStreamReader(
									postMethod.getResponseBodyAsStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sBuffer.append(line).append(EOL);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
		return sBuffer.toString();
	}

	
}



