package com.ch03;

import com.ch03.SkiImageC.SkiImageService_Service;
import java.io.IOException;
import javax.activation.DataHandler;

public class SkiImageClient2 {
    public static void main (String args[]){
        SkiImageService_Service service = new SkiImageService_Service();
        com.ch03.SkiImageC.SkiImageService port = service.getSkiImageServicePort();
        DataHandler image = port.getImage("nordic");
        dump(image);
    }
    
    private static void dump(DataHandler dh) {
       System.out.println();
       try {
          System.out.println("MIME type: " + dh.getContentType());
          System.out.println("Content:   " + dh.getContent());
       }
       catch(IOException e) { System.err.println(e); }
    }

}
