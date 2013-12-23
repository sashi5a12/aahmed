package com.ttdev.is;

import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.axis2.Constants;

public class ImageClient {
	public static void main(String args[]) throws RemoteException {
		ImageServiceStub service = new ImageServiceStub("http://localhost:1234/axis2/services/ImageService");
		service._getServiceClient().getOptions().setProperty(Constants.Configuration.ENABLE_MTOM, "true");
		FileDataSource ds = new FileDataSource("src/main/resources/p01.png");
		service.uploadImage("p01", new DataHandler(ds));
	}
}
