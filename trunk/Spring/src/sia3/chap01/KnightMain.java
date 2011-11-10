package sia3.chap01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KnightMain {
  public static void main(String[] args) {
    ApplicationContext context = 
        new ClassPathXmlApplicationContext("sia3/chap01/knights.xml"); 
    
//    Knight knight = (Knight) context.getBean("knight"); 
    
//    knight.embarkOnQuest();
    
//    BeanLifeCycle beanLifeCycle=(BeanLifeCycle)context.getBean("beanLifeCycle");
  }
}
