package spring.by.example.hibernate;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.by.example.hibernate.model.Address;
import spring.by.example.hibernate.model.Person;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/by/example/hibernate/application-context.xml");
		PersonDao dao=(PersonDao)applicationContext.getBean("personDao");
		Collection<Person> persons=dao.findPersons();
		 Person person = persons.iterator().next();
		 person.setLastName("xxxxxxxxxxxxxxxx");
		 Address add=new Address();
		 add.setAddress("xxxxxxxx");
		 add.setCity("xxxxxx");
		 add.setPerson(person);
		 add.setState("xxxxxxxx");
		 add.setZipPostal("xxxxxx");
		 add.setCreated(new Date(System.currentTimeMillis()));
		 Set<Address> addresses=new HashSet<Address>();
		 addresses.add(add);
		 person.setAddresses(addresses);
		 
         dao.save(person);
	}

}
