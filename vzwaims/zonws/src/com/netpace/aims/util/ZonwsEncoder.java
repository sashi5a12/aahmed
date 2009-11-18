package com.netpace.aims.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ZonwsEncoder {
	
	private static final String TOP_SECRET_PHASE = "SS#$#&*%H%*)(*%><?{+_+OH<><MT%$E%$E^*&(*)afdsfGOI_()_OIF%$#WJ";

	public static String encode(String plainText ) 
		throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException
		, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		
		//SETUP
		DESKeySpec keySpec = new DESKeySpec(TOP_SECRET_PHASE.getBytes("UTF8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(keySpec);
				
		//ENCODE plainText String
		byte[] plainTextBytes = plainText.getBytes("UTF8");      
		Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
		cipher.init(Cipher.ENCRYPT_MODE, key);
		//base64 encode
		BASE64Encoder base64encoder = new BASE64Encoder();
		return base64encoder.encode(cipher.doFinal(plainTextBytes));
	}
	
	public static String decode(String encryptedText )
		throws NoSuchAlgorithmException, NoSuchPaddingException
		, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeySpecException 	{
		
		//SETUP
		DESKeySpec keySpec = new DESKeySpec(TOP_SECRET_PHASE.getBytes("UTF8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(keySpec);
		
		// DECODE encryptedText String
		BASE64Decoder base64decoder = new BASE64Decoder();
		byte[] encrypedTextBytes = base64decoder.decodeBuffer(encryptedText);
	
		Cipher cipher = Cipher.getInstance("DES");// cipher is not thread safe
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainTextBytes = (cipher.doFinal(encrypedTextBytes));
		return new String(plainTextBytes);
	}
	
	public static void main(String[] args){
		System.out.println("Input String="+args[0]);
		try {
			String encStr = ZonwsEncoder.encode(args[0]);
			System.out.println("Encoded String="+encStr);
			String decStr = ZonwsEncoder.decode(encStr);
			System.out.println("Decoded String="+decStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
