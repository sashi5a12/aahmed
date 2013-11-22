package com.ch03;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
//, wsdlLocation = "http://localhost:8080/jwsur/SkiImageService.wsdl"
@WebService(serviceName = "SkiImageService")
@BindingType(value = SOAPBinding.SOAP11HTTP_MTOM_BINDING)
public class SkiImageService {
    private Map<String, String> photos;
        
    public SkiImageService() {
       photos = new HashMap<String, String>();
       photos.put("nordic", "nordic.jpg");
       photos.put("alpine", "alpine.jpg");
       photos.put("telemk", "telemk.jpg");
    }

    @WebMethod
    public Image getImage(String name){
        return createImage(name);
    }
    
    @WebMethod
    public List<Image> getImages(){
        return createImageList();
    }
    private Image createImage(String name){
        byte[] bytes = getRawBytes(name);
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        Iterator iterators = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader imageReader = (ImageReader)iterators.next();
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(in);
            imageReader.setInput(iis, true);
            return imageReader.read(0);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // Create a list of all available images.
    private List<Image> createImageList() {
       List<Image> list = new ArrayList<Image>();
       Set<String> key_set = photos.keySet();
       for (String key : key_set) {
          Image image = createImage(key);
          if (image != null) list.add(image);
       }
       return list;
    }

    // Read the bytes from the file for one image.
    private byte[] getRawBytes(String name){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String cwd = System.getProperty("user.dir");
        String sep = System.getProperty("file.separator");
        String base_name = cwd + sep + "jpegs" + sep;
        System.out.println("************ base_name= "+base_name);                
        String file_name = base_name + name + ".jpg";
        try {
            FileInputStream in = new FileInputStream(file_name);
            
            // Send default image if there's none with this name.
            if (in == null) in = new FileInputStream(base_name + "nordic.jpg");
            byte[] buff= new byte[2048];
            int n=0;
            while ((n=in.read(buff)) != -1){
                out.write(buff,0,n);
            }
            in.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return out.toByteArray();
    }
}
