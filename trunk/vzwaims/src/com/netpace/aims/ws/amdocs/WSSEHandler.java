package com.netpace.aims.ws.amdocs;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import org.apache.log4j.Logger;

import org.apache.commons.codec.binary.Base64;

public class WSSEHandler {
	
	private static Logger log = Logger.getLogger(SettlementServices.class.getName());
	
	final static String HEADER_NAME = "WS-Security";
	final static String USERNAME_FIELD = "Username";
	final static String PW_DIGEST_FIELD = "Password";
	final static String NONCE_FIELD = "Nonce";
	final static String CREATED_FIELD = "Created";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		if(args.length < 2 )
			System.out.println("Usage: java WSSEHandler <url> <username> <password>");
		
		System.out.println(" Username = " + args[0]);
		System.out.println(" Password = " + args[1]);
		
		WSSEHandler authHdlr = new WSSEHandler();
		
		System.out.println("Calling WSSEHandler.getAuthHeaders()......");

		String authStr =  authHdlr.getAuthString(args[0], args[1]);
		System.out.println("WSSE Auth String = " + authStr);

		HashMap<String, String> wsseHeader =  authHdlr.getAuthHeaders(args[0], args[1]);
		
		System.out.println("WSSE Header = " + wsseHeader);
	}

	/**
	 * Return WS-Security headers for REST Web Service call. 
	 * 
	 * @param url - 		Complete URL.
	 * @param username -	username to use in the REST call
	 * @param password -	Password to use in clear text
	 * @return HashMap -	WS-Security header entries.
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws Exception
	 */
	public HashMap<String, String> getAuthHeaders(String username, String password) 
		throws UnsupportedEncodingException, NoSuchAlgorithmException  {

		String authStr = getAuthString( username, password);
		HashMap<String, String> authHeader = new HashMap<String, String>();
		authHeader.put("Authorization", "WSSE profile=\"UsernameToken\"");
		authHeader.put("X-WSSE", authStr);
		
		if (log.isDebugEnabled())
			log.debug("X-WSSE: " + authStr);
			
		return authHeader;
	}	
	
	/**
	 * Generate Authentication String used in WSSE header.
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public String getAuthString(String username, String password) 
		throws UnsupportedEncodingException, NoSuchAlgorithmException  {

		String nonceString = genNonce();
		String formatedTime = getFormatedTime();
		
		String pwDigest = getPasswordDigestFromClearTextPW(
				nonceString.getBytes("UTF-8"), formatedTime.getBytes("UTF-8"),
				password);

		String authString = "UsernameToken Username=\"" + username
				+ "\", PasswordDigest=\"" + pwDigest + "\", Nonce=\""
				+ nonceString + "\", Created=\"" + formatedTime + "\"";
		
		if (log.isDebugEnabled()){
			log.debug("=========================== WSSE Authentication ==============================");
			log.debug("None:"+nonceString);
			log.debug("Formated Time:"+formatedTime);
			log.debug("Password Digest:"+pwDigest);
			log.debug("Authentication String:"+authString);
			log.debug("==============================================================================");
		}
		
		return authString;
	}
	
	

	/**
	 * Implements the #Password or #PasswordDigest with nonce and created date
	 * MAC from the OASIS Web Services Security UsernameToken Profile.
	 * <p>
	 * Construct the hash mac based on the nonce, created and password. The
	 * value computed is Base64(SHA1(nonce + created + password)). The result is
	 * called the password digest in the UsernameToken Profile specification,
	 * even though it is really more like a hash mac.
	 * <p>
	 * The password is actually the password or the password hash equivalent
	 * (Base64 SHA1 of UTF8 encoded password), but in reality should always be
	 * the password hash equivalent since using the plaintext password requires
	 * the server to have access to the plaintext password.
	 * <p>
	 * All digesting is done in the UTF-8 encoding.
	 * 
	 * @param nonce
	 *            optional - a unique value that will never be used again
	 * @param created
	 *            optional - the wsu:Created time in the format
	 *            yyyy-MM-dd'T'HH:mm:ss'Z'
	 * @param password
	 *            the password or password hash equivalent
	 * @return the base 64 encoded hash mac SHA1(nonce + created + password)
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	private String getPasswordDigestFromClearTextPW(byte[] nonce,
			byte[] created, String password) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest sha1 = getMessageDigest();

		if (nonce != null) {
			sha1.update(nonce);
		}
		if (created != null) {
			sha1.update(created);
		}
		String passwordDigest = new String(Base64.encodeBase64(sha1
				.digest(getHash(password))));
		sha1.reset();

		return passwordDigest;
	}

	/*
	 * Get a nonce String.
	 */
	private String genNonce() throws UnsupportedEncodingException {
		SecureRandom random = null;
		int length = 128;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] nonceValue = new byte[length / 8];
		random.nextBytes(nonceValue);
		return new String(Base64.encodeBase64(nonceValue), "UTF-8");
	}

	/*
	 * Generate current time in format compliant with REST standards.
	 */
	private String getFormatedTime() {
		Date date = new Date();
		DateFormat zuluTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		zuluTime.setTimeZone(TimeZone.getTimeZone("UTC"));
		return zuluTime.format(date);
	}

	/**
	 * Gets the SHA-1 hash of the string. This is a helper method to first hash
	 * the password so that password hash can be fed to passwordDigest.
	 * <p>
	 * All digesting is done in the UTF-8 encoding.
	 * 
	 * @param string
	 * @return the SHA-1 hash of the UTF8 encoded string
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	private byte[] getHash(String string) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest sha1 = getMessageDigest();
		/*
		 * Java strings are UTF-16, so when digesting use the UTF-8 encoding as
		 * that is standard practice on the Internet.
		 */
		byte[] hash = getMessageDigest().digest(string.getBytes("UTF-8"));
		sha1.reset();
		return hash;
	}

	/**
	 * Get a MessageDigest instance for the given algorithm.
	 * 
	 * @return MessageDigest instance
	 * @throws NoSuchAlgorithmException
	 */
	protected MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("SHA-1");

	}

}



