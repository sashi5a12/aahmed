package sia3.chap02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) throws PerformanceException {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("sia3/chap02/chap02.xml");
		
		Juggler juggler=(Juggler)ctx.getBean("duke");
		juggler.perform();
		
		PoeticJuggler pJuggler=(PoeticJuggler)ctx.getBean("poeticJuggler");
		//pJuggler.perform();
		
		Stage stage=(Stage)ctx.getBean("stage");
		
		Auditorium auditorium=(Auditorium)ctx.getBean("auditorium");
	}

}