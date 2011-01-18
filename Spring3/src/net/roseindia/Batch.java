package net.roseindia;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class Batch {
    public static void main(String args[]) {
        try {
            ApplicationContext ac = new ClassPathXmlApplicationContext("Batch.xml", Batch.class);
            DataSource source = (DataSource) ac.getBean("dataSource");
            JdbcTemplate jt = new JdbcTemplate(source);
            jt.batchUpdate(new String[]{"update employee set Department = 'Finance#'", "delete from employee where Employee_Id =5"});
            System.out.println("Data updated successfully");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
