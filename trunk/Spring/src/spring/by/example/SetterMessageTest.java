package spring.by.example;

import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/by/example/application-context.xml" })
public class SetterMessageTest {
	final Logger logger = Logger.getLogger(SetterMessageTest.class);
	
	@Autowired
	private SetterMessage message = null;

	/**
	 * Tests message.
	 */
	@Test
	public void testMessage() {
		
		Assert.assertNotNull("Constructor message instance is null.", message);
		String msg = message.getMessage();
		Assert.assertNotNull("Message is null.", msg);
		String expectedMessage = "Spring is fun.";
		Assert.assertEquals("Message should be '" + expectedMessage + "'.", expectedMessage, msg);
		
		logger.info("message='{}'"+ msg);
	}
}
