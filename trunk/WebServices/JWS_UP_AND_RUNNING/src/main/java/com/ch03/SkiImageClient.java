package com.ch03;

import com.ch03.SkiImageC.SkiImageService_Service;
import java.awt.Image;

public class SkiImageClient {
    public static void main(String args[]){
        SkiImageService_Service service = new SkiImageService_Service();
        com.ch03.SkiImageC.SkiImageService port = service.getSkiImageServicePort();
        Image image = port.getImage("nordic");
        System.out.println(image.toString());
    }
}
