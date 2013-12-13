package jaxb.helloworld;

import java.math.BigDecimal;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import jaxb.schema.course.CompanyType;
import jaxb.schema.course.ContactType;
import jaxb.schema.course.CourseBooking;
import jaxb.schema.course.ObjectFactory;
import jaxb.schema.course.StudentType;

public class CourseTest {
    public static void main(String args[]) throws JAXBException, DatatypeConfigurationException{
        JAXBContext ctx = JAXBContext.newInstance("jaxb.schema.course");
        
        CourseBooking booking = new CourseBooking();
        booking.setCourseReference("UML-101");
        booking.setTotalPrice(new BigDecimal(10000));
        booking.setInvoiceReference("123456");

        DatatypeFactory datatypes = DatatypeFactory.newInstance();
        booking.setCourseDate(datatypes.newXMLGregorianCalendarDate(2006,06,15,0));
        booking.setTotalPrice(new BigDecimal(10000));
        booking.setInvoiceReference("123456");
        
        booking.getStudent().add(new StudentType());
        booking.getStudent().get(0).setFirstName("John");
        booking.getStudent().get(0).setSurname("Smith");
        
        booking.setCompany(new CompanyType());
        booking.getCompany().setName("Clients inc.");        
        booking.getCompany().setContact(new ContactType());
        booking.getCompany().getContact().setName("Paul");
        booking.getCompany().getContact().setEmail("paul@clients.inc");
        booking.getCompany().getContact().setTelephone("12345678");
        booking.getCompany().setAddress("10 client street");
        
        Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        JAXBElement<CourseBooking> bookingElement=(new ObjectFactory()).createBooking(booking);
        m.marshal(bookingElement, System.out);
    }
}
