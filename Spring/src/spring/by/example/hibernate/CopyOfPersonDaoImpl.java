package spring.by.example.hibernate;

import java.util.Collection;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.by.example.hibernate.model.Person;

@Repository
@Transactional(readOnly = true)
public class CopyOfPersonDaoImpl implements PersonDao {
    protected HibernateTemplate template = null;
    /**
     * Sets Hibernate session factory.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        template = new HibernateTemplate(sessionFactory);
}
    /**
     * Find persons.
     */
    @SuppressWarnings("unchecked")
    public Collection<Person> findPersons() throws DataAccessException {
        return template.find("from Person");
    }
    /**
     * Find persons by last name.
     */
    @SuppressWarnings("unchecked")
    public Collection<Person> findPersonsByLastName(String lastName) throws DataAccessException {
        return template.find("from Person p where p.lastName = ?", lastName);
    }
    /**
     * Saves person.
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void save(Person person) {
        template.saveOrUpdate(person);
    }
}