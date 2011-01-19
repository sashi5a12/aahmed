package cc.db.dao; 

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cc.db.beans.Department;

@Transactional
public class DepartmentDAO {
    private SessionFactory factory;

   public SessionFactory getFactory() {
    return factory;
  }

  public void setFactory(SessionFactory factory) {
    this.factory = factory;
  }

  @SuppressWarnings("unchecked")
    public List<Department> findAll() {
        return factory.getCurrentSession().createCriteria(Department.class).list();
    }
    
    public Department makePersistent(Department u) {
        factory.getCurrentSession().saveOrUpdate(u);
        return u;
    }
    
    public void makeTransient(Department u) {
        factory.getCurrentSession().delete(u);
    }
    
    public Department findById(Integer id) {
        return (Department) factory.getCurrentSession().get(Department.class, id);
    }
    
    public Department findByName(String name) {
        return (Department) factory.getCurrentSession().createQuery("from Department e where e.name = :name")
            .setParameter("name", name)
            .uniqueResult();
    }

    public Department findByNameWithEmployees(String name) {
        Department d = (Department) factory.getCurrentSession().createQuery("from Department e where e.name = :name")
            .setParameter("name", name)
            .uniqueResult();
        Hibernate.initialize(d.getEmployees());
        return d;
    }
}

