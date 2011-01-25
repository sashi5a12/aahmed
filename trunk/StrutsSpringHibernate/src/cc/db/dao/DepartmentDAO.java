package cc.db.dao; 

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import cc.db.beans.Department;
import cc.db.beans.Employee;

@Transactional
public class DepartmentDAO extends HibernateDaoSupport {

  @SuppressWarnings("unchecked")
    public List<Department> findAll() {
	  DetachedCriteria criteria = DetachedCriteria.forClass(Department.class);
        return super.getHibernateTemplate().findByCriteria(criteria);
    }
    
    public Department makePersistent(Department u) {
    	super.getHibernateTemplate().saveOrUpdate(u);
        return u;
    }
    
    public void makeTransient(Department u) {
    	super.getHibernateTemplate().delete(u);
    }
    
    public Department findById(Integer id) {
    	return super.getHibernateTemplate().get(Department.class, id);
    }
    
    public Department findByName(String name) {
        return (Department) super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("from Department e where e.name = :name")
            .setParameter("name", name)
            .uniqueResult();
    }

    public Department findByNameWithEmployees(String name) {
        Department d = (Department) super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("from Department e where e.name = :name")
            .setParameter("name", name)
            .uniqueResult();
        Hibernate.initialize(d.getEmployees());
        return d;
    }
}

