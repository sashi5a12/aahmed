package cc.db.hibernate;

import java.util.List;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import cc.db.beans.Employee;
import cc.db.dao.EmployeeDAO;

public class PrintEmployees {
	public static void main(String[] args) {
		
		XmlBeanFactory context = new XmlBeanFactory(new FileSystemResource("WebContent/WEB-INF/SpringBeans.xml"));
		EmployeeDAO eDAO = (EmployeeDAO) context.getBean("empDAO");		
		List<Employee> emps = eDAO.findAll();
		for (Employee e : emps) {
			System.out.println(e.getLastName() + " has job "
					+ e.getJob().getName());
		}
	}
}
