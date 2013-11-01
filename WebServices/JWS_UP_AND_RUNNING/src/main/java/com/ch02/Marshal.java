package com.ch02;

import com.sun.jmx.remote.internal.Unmarshal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Marshal {
    public static final String FILE_NAME="bd.xml";
    
    public static void main (String[] args) throws Exception{
        new Marshal().runExample();
    }
    
    private void runExample() throws JAXBException, FileNotFoundException, IOException{
        JAXBContext ctx = JAXBContext.newInstance(com.ch02.Skier.class);        
        Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        // Marshal a Skier object: 1st to stdout, 2nd to file
        Skier s = createSkier();
        m.marshal(s, System.out);
        
        FileOutputStream out = new FileOutputStream(FILE_NAME);
        m.marshal(s, out);
        out.close();
        
        Unmarshaller um = ctx.createUnmarshaller();
        Skier clone = (Skier)um.unmarshal(new File (FILE_NAME));
        System.out.println();
        m.marshal(clone, System.out);
    }
    
    private Skier createSkier() {
        Person bd = new Person("Bjoern Daehlie", 41, "Male");
        List<String> list = new ArrayList<String>();
        list.add("12 Olympic Medals");
        list.add("9 World Championships");
        list.add("Winningest Winter Olympian");
        list.add("Greatest Nordic Skier");
        return new Skier(bd, "Norway", list);
    }

}
