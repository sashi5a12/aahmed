package cc.db.dao; 

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cc.db.beans.Employee;

//@Transactional
public class EmployeeDAO {
  private SessionFactory factory;
  
  public SessionFactory getFactory() {
    return factory;
  }

  public void setFactory(SessionFactory factory) {
    this.factory = factory;
  }

  @SuppressWarnings("unchecked")
    public List<Employee> findAll() {
        return factory.getCurrentSession().createCriteria(Employee.class).list();
    }
    
    public Employee makePersistent(Employee u) {
        factory.getCurrentSession().saveOrUpdate(u);
        return u;
    }
    
    public void makeTransient(Employee u) {
        factory.getCurrentSession().delete(u);
    }
    
    public Employee findById(Integer id) {
        return (Employee) factory.getCurrentSession().get(Employee.class, id);
    }
    
    public Employee findByLastName(String name) {
        return (Employee) factory.getCurrentSession().createQuery("from Employee e where e.lastName = :name")
            .setParameter("name", name)
            .uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
  public Collection<Employee> findByDepartment(int deptID) {
      return factory.getCurrentSession()
        .createQuery("from Employee e where e.department.id = :id")
        .setInteger("id", deptID)
        .list();
    }
}
