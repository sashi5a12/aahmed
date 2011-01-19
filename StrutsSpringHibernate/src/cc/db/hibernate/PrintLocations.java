package cc.db.hibernate;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.db.beans.Location;
import cc.db.dao.LocationDAO;

public class PrintLocations {

	public static void main(String[] args) {
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("DataSourceBeans.xml");

		LocationDAO lDAO = (LocationDAO) context.getBean("locDAO");
		
		List<Location> locs = lDAO.findAll();
		for (Location loc : locs) {
			System.out.println(loc);
		}
	}
}
