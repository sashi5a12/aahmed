package cc.db.hibernate;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.db.beans.Department;
import cc.db.beans.Employee;
import cc.db.beans.Job;
import cc.db.beans.Location;
import cc.db.dao.DepartmentDAO;
import cc.db.dao.EmployeeDAO;
import cc.db.dao.JobDAO;
import cc.db.dao.LocationDAO;

public class PrintEntities {
	private EmployeeDAO eDAO;

	private DepartmentDAO dDAO;

	private LocationDAO lDAO;

	private JobDAO jDAO;

	public PrintEntities() {
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("DataSourceBeans.xml");
		
		eDAO = (EmployeeDAO) context.getBean("empDAO");
		dDAO = (DepartmentDAO) context.getBean("deptDAO");
		lDAO = (LocationDAO) context.getBean("locDAO");
		jDAO = (JobDAO) context.getBean("jobDAO");
	}

	public static void main(String[] args) {
		PrintEntities pe = new PrintEntities();
		pe.printEmployees();
		pe.printDepartments();
		pe.printLocations();
		pe.printJobs();
	}

	private void printLocations() {
		System.out.println("\nLocations");
		List<Location> locs = lDAO.findAll();
		for (Location loc : locs) {
			System.out.println(loc);
		}
	}

	private void printJobs() {
		System.out.println("\nJobs");
		List<Job> jobs = jDAO.findAll();
		for (Job j : jobs) {
			System.out.println(j);
		}
	}

	private void printEmployees() {
		System.out.println("\nEmployees:");
		List<Employee> emps = eDAO.findAll();
		for (Employee e : emps) {
			System.out.println(e);
		}
	}

	private void printDepartments() {
		System.out.println("\nDepartments");
		List<Department> depts = dDAO.findAll();
		for (Department d : depts) {
			System.out.println(d);
		}
	}
}
