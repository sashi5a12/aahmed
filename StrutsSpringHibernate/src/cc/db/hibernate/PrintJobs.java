package cc.db.hibernate;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.db.beans.Job;
import cc.db.dao.JobDAO;

public class PrintJobs {
	public static void main(String[] args) {
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("DataSourceBeans.xml");

		JobDAO jDAO = (JobDAO) context.getBean("jobDAO");

		List<Job> jobs = jDAO.findAll();
		for (Job j : jobs) {
			System.out.println(j);
		}
	}
}
