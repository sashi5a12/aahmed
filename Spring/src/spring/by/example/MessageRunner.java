package spring.by.example;

import org.apache.log4j.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MessageRunner {
	final static Logger logger = Logger.getLogger(MessageRunner.class);

	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		logger.info("Initializing Spring context.");
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/by/example/application-context.xml");
		logger.info("Spring context initialized.");
		SetterMessage message = (SetterMessage) applicationContext.getBean("messageByRef");
		logger.debug("message='" + message.getMessage() + "'");
	}
}