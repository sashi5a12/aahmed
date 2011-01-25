package cc.db.dao; 

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import cc.db.beans.Employee;

@Transactional
public class EmployeeDAO extends HibernateDaoSupport{
// private HibernateTemplate hibernateTemplate;


//  public void setFactory(SessionFactory factory) {
//	  this.hibernateTemplate = new HibernateTemplate(factory);

//  }

  @SuppressWarnings("unchecked")
    public List<Employee> findAll() {
	  DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);		
	  return  super.getHibernateTemplate().findByCriteria(criteria);
    }
    
    public Employee makePersistent(Employee u) {
    	super.getHibernateTemplate().saveOrUpdate(u);
        return u;
    }
    
    public void makeTransient(Employee u) {
    	super.getHibernateTemplate().delete(u);
    }
    
    public Employee findById(Integer id) {
    	return super.getHibernateTemplate().get(Employee.class, id);
    }    
    public Employee findByLastName(String name) {
    	
        return (Employee) super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("from Employee e where e.lastName = :name").setParameter("name", name).uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
  public Collection<Employee> findByDepartment(int deptID) {
      return super.getHibernateTemplate()
        .findByNamedQuery("from Employee e where e.department.id = :id", deptID);
    }
}
