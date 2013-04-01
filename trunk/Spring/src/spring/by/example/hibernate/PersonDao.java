package spring.by.example.hibernate;

import java.util.Collection;
import org.springframework.dao.DataAccessException;
import spring.by.example.hibernate.model.Person;

public interface PersonDao {
	public Collection<Person> findPersons() throws DataAccessException;
	public Collection<Person> findPersonsByLastName(String lastName) throws DataAccessException;
    public void save(Person person);
}
