package jaxb.helloworld;

import com.jaxb.helloword.GreetingListType;
import com.jaxb.helloword.GreetingType;
import com.jaxb.helloword.ObjectFactory;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Hello {
    private ObjectFactory of;
    private GreetingListType grList;
    
    public Hello(){
        of = new ObjectFactory();
        grList = of.createGreetingListType();
    }
    
    public void make(String t, String l){
        GreetingType g = of.createGreetingType();
        g.setText(t);
        g.setLanguage(l);
        grList.getGreeting().add(g);
    }
    
    public void marshal(){
        JAXBElement<GreetingListType> gl = of.createGreetings(grList);
        try {
            JAXBContext ctx = JAXBContext.newInstance("com.jaxb.helloword");
            Marshaller mr = ctx.createMarshaller();
            mr.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            mr.marshal(gl, System.out);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }
}
