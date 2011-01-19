package cc.db.dao; 

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cc.db.beans.Location;

@Transactional
public class LocationDAO {
    private SessionFactory factory;

    public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
    public List<Location> findAll() {
        return factory.getCurrentSession().createCriteria(Location.class).list();
    }
    
    public Location makePersistent(Location u) {
        factory.getCurrentSession().saveOrUpdate(u);
        return u;
    }
    
    public void makeTransient(Location u) {
        factory.getCurrentSession().delete(u);
    }
    
    public Location findById(Integer id) {
        return (Location) factory.getCurrentSession().get(Location.class, id);
    }
}
