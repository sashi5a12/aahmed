package cc.db.hibernate;

import java.util.Collection;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.db.beans.Department;
import cc.db.beans.Employee;
import cc.db.dao.DepartmentDAO;

public class PrintDepartments {
  public static void main(String[] args) {
    ApplicationContext context = 
      new ClassPathXmlApplicationContext("DataSourceBeans.xml");

    DepartmentDAO dDAO = (DepartmentDAO) context.getBean("deptDAO");

    List<Department> depts = dDAO.findAll();
    for (Department d : depts) {
      System.out.println(d + " located at " + d.getLocation()
          + " managed by " + d.getManager().getLastName());
    }

    
    // Option 1: Use a new method in EmployeeDAO to get the employees
//    Department research = dDAO.findByName("Research");
//    EmployeeDAO eDAO = (EmployeeDAO) context.getBean("empDAO");   
//    Collection<Employee> emps = eDAO.findByDepartment(research.getId());
    
    // Option 2: Use a new method in DepartmentDAO to init the emps with the dept
    Department research = dDAO.findByNameWithEmployees("Research");
    
    // Option 3: Use a session -- doesn't work for some reason
//    SessionFactory factory = (SessionFactory) context.getBean("mySessionFactory");
//    Session s = factory.getCurrentSession();
//    Department research = dDAO.findByName("Research");
    Collection<Employee> emps = research.getEmployees();
    System.out.println(research);
    for (Employee e : emps) {
      System.out.println(e);
    }
//    s.close();
  }
}
