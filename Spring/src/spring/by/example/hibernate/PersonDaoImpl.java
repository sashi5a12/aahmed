package spring.by.example.hibernate;

import java.util.Collection;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.by.example.hibernate.model.Person;

@Repository
@Transactional(readOnly = true)
public class PersonDaoImpl implements PersonDao {
    protected SessionFactory sessionFactory = null;
    /**
     * Sets Hibernate session factory.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
}
    /**
     * Find persons.
     */
    @SuppressWarnings("unchecked")
    public Collection<Person> findPersons() throws DataAccessException {
        return sessionFactory.getCurrentSession().createQuery("from Person").list();
    }
    /**
     * Find persons by last name.
     */
    @SuppressWarnings("unchecked")
    public Collection<Person> findPersonsByLastName(String lastName) throws DataAccessException {
    	Query hQry=sessionFactory.getCurrentSession().createQuery("from Person p where p.lastName = ?");
    	hQry.setString(1, lastName);
        return hQry.list();
    }
    /**
     * Saves person.
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void save(Person person) {
    	sessionFactory.getCurrentSession().saveOrUpdate(person);
    }
}