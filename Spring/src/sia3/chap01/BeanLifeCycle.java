package sia3.chap01;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanLifeCycle implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, BeanPostProcessor, InitializingBean, DisposableBean{
	private String property;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		System.out.println(property);
		this.property = property;
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("BeanNameAware method called: "+name);
	}

	@Override
	public void setBeanFactory(BeanFactory name) throws BeansException {
		System.out.println("BeanFactoryAware method called: ");
	}

	@Override
	public void setApplicationContext(ApplicationContext name)throws BeansException {
		System.out.println("ApplicationContextAware method called: ");
	}

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
		System.out.println("BeanPostProcessor->postProcessBeforeInitialization method called: "+arg0 +":"+arg1);
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("InitialzingBean method called");
	}
	
	@Override
	public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
		System.out.println("BeanPostProcessor->postProcessAfterInitialization method called: "+arg0 +":"+arg1);
		return null;
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("DisposableBean method called");
		
	}
}
