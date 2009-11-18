package com.netpace.aims.bo.vzdn;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public final class PasswordUtil {
	
	private static final String KEY = "dYPB2rN5bvQE1iwV8gsHc8RSZwGzc9rc";
    private static BASE64Encoder base64encoder = new BASE64Encoder();
    private static BASE64Decoder base64decoder = new BASE64Decoder();
	
	 public static void main(String [] args){
		 try {
			//System.out.println("hello : "+ PasswordUtil.decrypt("Q1Exg1VN2VS4YtGqjLErpg=="));
			System.out.println(PasswordUtil.encrypt("12657alcosta"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 //Q1Exg1VN2VS4YtGqjLErpg==
			
		
	}
	
	  public static String encrypt(String passwd) throws Exception {
	     SecretKey key = readKey();
	     Cipher cipher = Cipher.getInstance("DESede");
	     cipher.init(Cipher.ENCRYPT_MODE, key);
	     byte[] ebytes = cipher.doFinal(passwd.getBytes("UTF8"));
	     String enc_passwd = base64encoder.encode(ebytes);
	     return enc_passwd;
 }
	 
	 
	  public static SecretKey readKey() throws Exception {
	     BASE64Decoder base64decoder = new BASE64Decoder();
	     byte[] rawkey = base64decoder.decodeBuffer(KEY);
	     // Convert the raw bytes to a secret key like this
	     DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
	     SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
	     SecretKey key = keyfactory.generateSecret(keyspec);
	
	     return key;
 }
	 public static String decrypt(String passwd) throws Exception {
	     SecretKey key = readKey();
	
	     // Create and initialize the decryption engine
	     Cipher cipher = Cipher.getInstance("DESede");
	     cipher.init(Cipher.DECRYPT_MODE, key);
	     byte[] dec_bytes = base64decoder.decodeBuffer(passwd);
	     byte[] dbytes = cipher.doFinal(dec_bytes);
	     return new String(dbytes);
 }
	 
}
