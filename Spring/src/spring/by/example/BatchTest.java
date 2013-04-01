package spring.by.example;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/by/example/application-context.xml" })
public class BatchTest {

	private JdbcTemplate jdbcTemplate;
	private List<Customer> customers=new ArrayList<Customer>();
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(final DataSource dataSource){
		this.jdbcTemplate=new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
	}

	@Before
	public void setUp(){
		for (int i=1; i<=10000; i++){
			Customer c=new Customer();
			c.setFirstName(String.valueOf(i));
			c.setLastName(String.valueOf(i));
			customers.add(c);
		}
	}
	
	
	
	
	public void testJdbcTemplateBatch(){
		
		int[] recordInserted=batchUpdateUsingBatchPreparedStatementSetter(customers);
		for(int i:recordInserted){			
			System.out.println("Inserted Record: "+i);
		}
		
	}
	
	@Test
	public void testNamedParamTemplateBatch(){
		String sql="insert into customers (first_name, last_name) values(:firstName,:lastName)";
		SqlParameterSource[] source=SqlParameterSourceUtils.createBatch(customers.toArray());
		int[] insertCount=namedParameterJdbcTemplate.batchUpdate(sql, source);
		for(int i:insertCount){			
			System.out.println("Inserted Record: "+i);
		}
	}
	private int[] batchUpdateUsingBatchPreparedStatementSetter(final List<Customer> customers){
		String sql="insert into customers (first_name, last_name) values(?,?)";
		int[] updateCount= jdbcTemplate.batchUpdate(sql, 
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, customers.get(i).getFirstName());
						ps.setString(2, customers.get(i).getLastName());
					}
					
					@Override
					public int getBatchSize() {
						 return customers.size();
					}
				});
		return updateCount;
	}

}
