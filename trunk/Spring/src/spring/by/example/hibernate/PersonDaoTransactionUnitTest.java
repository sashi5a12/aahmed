package spring.by.example.hibernate;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import spring.by.example.hibernate.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/by/example/hibernate/application-context.xml" })
@TransactionConfiguration
@Transactional
public class PersonDaoTransactionUnitTest extends AbstractTransactionalJUnit4SpringContextTests {
    protected static int SIZE = 2;
    protected static Integer ID = new Integer(1);
    protected static String FIRST_NAME = "Joe";
    protected static String LAST_NAME = "Smith";
    protected static String CHANGED_LAST_NAME = "Jackson";
    
    @Autowired
    protected PersonDao personDao = null;
    /**
     * Tests that the size and first record match what is expected
     * before the transaction.
     */
    @BeforeTransaction
    public void beforeTransaction() {
        testPerson(true, LAST_NAME);
    }
    
    /**
     * Tests person table and changes the first records last name.
     */
    @Test
    public void testHibernateTemplate() throws SQLException {
        Assert.assertNotNull("Person DAO is null.", personDao);
        Collection<Person> lPersons = personDao.findPersons();
        Assert.assertNotNull("Person list is null.", lPersons);
        Assert.assertEquals("Number of persons should be " + SIZE + ".", SIZE, lPersons.size());
        for (Person person : lPersons) {
            Assert.assertNotNull("Person is null.", person);
            if (ID.equals(person.getPersonId())) {
                Assert.assertEquals("Person first name should be " + FIRST_NAME + ".", FIRST_NAME, person.getFirstName());
                //Assert.assertEquals("Person last name should be " + LAST_NAME + ".", LAST_NAME, person.getLastName());
                person.setLastName(CHANGED_LAST_NAME);
                personDao.save(person);
            }

        } 
    }
    /**
     * Tests that the size and first record match what is expected
     * after the transaction.
     */
    @AfterTransaction
    public void afterTransaction() {
        testPerson(false, LAST_NAME);
    }
    /**
     * Tests person table.
     */
    protected void testPerson(boolean beforeTransaction, String matchLastName) {
        //List<Map<String, Object>> lPersonMaps = simpleJdbcTemplate.queryForList("SELECT * FROM PERSON");
    	Collection<Person> persons=personDao.findPersons();
//        Assert.assertNotNull("Person list is null.", lPersonMaps);
        Assert.assertNotNull("Person list is null.", persons);
//        Assert.assertEquals("Number of persons should be " + SIZE + ".", SIZE, lPersonMaps.size());
        Assert.assertEquals("Number of persons should be " + SIZE + ".", SIZE, persons.size());
//        Map<String, Object> hPerson = lPersonMaps.get(0);
        Person hPerson = persons.iterator().next();
        System.out.println((beforeTransaction ? "Before" : "After") + " transaction.  " + hPerson.toString());
//        Integer id = (Integer)hPerson.get("ID");
        Integer id = hPerson.getPersonId();
        String firstName = (String)hPerson.getFirstName();
        String lastName = (String)hPerson.getLastName();
        if (ID.equals(id)) {
        	Assert.assertEquals("Person first name should be " + FIRST_NAME + ".", FIRST_NAME, firstName);
        	//Assert.assertEquals("Person last name should be " + matchLastName + ".", matchLastName,lastName); 
        }
    }
}