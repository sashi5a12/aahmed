/**
 * ImageServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package com.ttdev.is;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ImageServiceSkeleton java skeleton for the axisService
 */
public class ImageServiceSkeleton {

	/**
	 * Auto generated method signature
	 * 
	 * @param productId
	 * @param image
	 * @return
	 */

	public void uploadImage(java.lang.String productId, javax.activation.DataHandler image) {
		try {
			FileOutputStream out = new FileOutputStream("C:/resources/"+productId+".jpg");
			image.writeTo(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
