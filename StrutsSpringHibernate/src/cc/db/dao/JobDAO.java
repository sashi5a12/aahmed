package cc.db.dao; 

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cc.db.beans.Job;

@Transactional
public class JobDAO {
    private SessionFactory factory;

    public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
    public List<Job> findAll() {
        return factory.getCurrentSession().createCriteria(Job.class).list();
    }
    
    public Job makePersistent(Job u) {
        factory.getCurrentSession().saveOrUpdate(u);
        return u;
    }
    
    public void makeTransient(Job u) {
        factory.getCurrentSession().delete(u);
    }
    
    public Job findById(Integer id) {
        return (Job) factory.getCurrentSession().get(Job.class, id);
    }
    
    public Job findByName(String name) {
        return (Job) factory.getCurrentSession().createQuery("from Job e where e.name = :name")
            .setParameter("name", name)
            .uniqueResult();
    }
}
