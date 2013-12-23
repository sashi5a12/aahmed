package com.ttdev.service.img;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.jws.WebService;

@WebService(endpointInterface="com.ttdev.service.img.ImageService")
public class ImageServiceImpl implements ImageService {

	@Override
	public void uploadImage(String productId, DataHandler image) {
		try {
			FileOutputStream out = new FileOutputStream("src/main/resources/"+productId+".jpg");
			image.writeTo(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
