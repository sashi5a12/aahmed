package cc.db.hibernate;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.annotation.Transactional;

import cc.db.beans.Address;
import cc.db.beans.Department;
import cc.db.beans.Employee;
import cc.db.beans.Location;

@Transactional
public class CriteriaQueries {
    private Session s;
    private SessionFactory sf;

    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    public void setSessionFactory (SessionFactory newValue)
    {
        sf = newValue;
    }

    @SuppressWarnings("unchecked")
    public void execute() {
        s = sf.getCurrentSession ();
        System.out.println("Conjunction: ");
        List<Employee> emps = testConjunction();
        printEmployees(emps);

        System.out.println("\nMultiple criteria (without conjunction)");
        emps = testMultipleCriteria();
        printEmployees(emps);

        System.out.println("\nDisjunction: ");
        emps = testDisjunction();
        printEmployees(emps);

        System.out.println("\nEmployees, 10 at a time");
        printWindowedResults();

        System.out.println("\nPrint Employees that work in MA");
        printEmpsWorkInMA();

        System.out.println("\nPrint number of Employees using Projection");
        countEmployeeRows();

        System.out.println("\nPrint multiple projections");
        printMultipleProjections();

        System.out.println("\nProperty projection for Employee");
        printProjectionsWithProperties();

        System.out.println("\nEmployees in MA using QBE");
        printEmpsInMAUsingQBE();

        System.out.println("\nDepartments located in MA by QBE");
        printDeptsInMAUsingQBE();

        System.out.println("\nNumber of employees in each department");
        projectionWithGroupBy();
    }

    @SuppressWarnings("unchecked")
    private void printDeptsInMAUsingQBE() {
        Department dept = new Department();
        Location loc = new Location();
        loc.setState("MA");
        List<Department> depts = s.createCriteria(Department.class).add(
                Example.create(dept)).createCriteria("location").add(
                Example.create(loc)).list();
        for (Department d : depts) {
            System.out.println(d.getName() + " " + d.getLocation().getState());
        }
    }

    @SuppressWarnings("unchecked")
    private void printEmpsInMAUsingQBE() {
        Criteria crit = s.createCriteria(Employee.class);
        Employee ex = new Employee();
        Address addr = new Address();
        addr.setState("MA");
        ex.setAddress(addr);
        crit.add(Example.create(ex).excludeZeroes());
        List<Employee> emps = crit.list();
        for (Employee e : emps) {
            System.out.println(e.getLastName() + " "
                    + e.getAddress().getState());
        }
        System.out.println(emps.size() + " found");
    }

    private void projectionWithGroupBy() {
        List output = s.createCriteria(Employee.class).setProjection(
                Projections.projectionList().add(Projections.rowCount()).add(
                        Projections.groupProperty("address.state"))).list();
        for (Object o : output) {
            System.out.println(Arrays.asList((Object[]) o));
        }
    }

    private void printProjectionsWithProperties() {
        // Multiple projections with properties
        Criteria crit = s.createCriteria(Employee.class);
        List results = crit.setProjection(
                Projections.projectionList().add(
                        Projections.property("firstName")).add(
                        Projections.property("lastName"))).list();
        for (Object o : results) {
            Object[] names = (Object[]) o;
            System.out.println(names[0] + " " + names[1]);
        }
    }

    private void printMultipleProjections() {
        // Multiple projections with aggregations
        Criteria crit = s.createCriteria(Employee.class);
        List results = crit.setProjection(
                Projections.projectionList().add(Projections.rowCount()).add(
                        Projections.avg("salary")).add(
                        Projections.min("salary")).add(
                        Projections.max("salary"))).list();
        Object[] res = (Object[]) results.get(0);
        System.out.println("Row count: " + res[0]);
        System.out.println("Average salary: " + nf.format(res[1]));
        System.out.println("Minimum salary: " + nf.format(res[2]));
        System.out.println("Maximum salary: " + nf.format(res[3]));
    }

    private void countEmployeeRows() {
        // Row count of Employees
        Criteria c2 = s.createCriteria(Employee.class);
        c2.setProjection(Projections.rowCount());
        List results = c2.list();
        System.out.println("Projection result: " + results.get(0));
    }

    @SuppressWarnings("unchecked")
    private void printEmpsWorkInMA() {
        // Employees that work in Massachusetts
        Criteria crit = s.createCriteria(Employee.class).createCriteria(
                "department").createCriteria("location").add(
                Restrictions.eq("state", "MA"));
        List<Employee> emps = crit.list();
        for (Employee e : emps) {
            System.out.println(e.getLastName() + " works for "
                    + e.getDepartment().getName() + " in "
                    + e.getDepartment().getLocation().getState());
        }
        System.out.println(emps.size() + " found");
    }

    private void printEmployees(List<Employee> emps) {
        for (Employee e : emps) {
            System.out.println(e.getFirstName() + " " + e.getLastName() + " "
                    + nf.format(e.getSalary()) + " "
                    + e.getAddress().getState());
        }
        System.out.println("Total: " + emps.size());
    }

    @SuppressWarnings("unchecked")
    public void printWindowedResults() {
        Criteria crit = s.createCriteria(Employee.class);
        crit.setMaxResults(10);
        int k = 0;
        for (int i = 0; i < 20; i++) {
            crit.setFirstResult(i * 10);
            List<Employee> emps = crit.list();
            if (emps.size() == 0)
                break;
            System.out.println("\nResults from " + (i * 10) + " to "
                    + (i * 10 + emps.size() - 1));
            for (Employee e : emps) {
                System.out.println(k++ + ": " + e.getLastName());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<Employee> testConjunction() {
        Criteria crit = s.createCriteria(Employee.class);
        Criterion name = Restrictions.like("lastName", "Ca%");
        Criterion salary = Restrictions.gt("salary", 30000.0);
        Criterion state = Restrictions.in("address.state", new String[] { "MA",
                "NC" });
        Conjunction cj = Restrictions.conjunction();
        cj.add(name);
        cj.add(salary);
        cj.add(state);
        crit.add(cj);
        return crit.list();
    }

    @SuppressWarnings("unchecked")
    public List<Employee> testMultipleCriteria() {
        Criteria crit = s.createCriteria(Employee.class);
        Criterion name = Restrictions.like("lastName", "Ca%");
        Criterion salary = Restrictions.gt("salary", 30000.0);
        Criterion state = Restrictions.in("address.state", new String[] { "MA",
                "NC" });
        crit.add(name);
        crit.add(salary);
        crit.add(state);
        return crit.list();
    }

    @SuppressWarnings("unchecked")
    public List<Employee> testDisjunction() {
        Criteria crit = s.createCriteria(Employee.class);
        Criterion name = Restrictions.like("lastName", "Ca%");
        Criterion salary = Restrictions.gt("salary", 30000.0);
        Criterion state = Restrictions.in("address.state", new String[] { "MA",
                "NC" });
        Disjunction dj = Restrictions.disjunction();
        dj.add(name);
        dj.add(salary);
        dj.add(state);
        crit.add(dj);
        return crit.list();
    }

    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("DataSourceBeans.xml");
    	XmlBeanFactory context = new XmlBeanFactory(new FileSystemResource("WebContent/WEB-INF/SpringBeans.xml"));
        CriteriaQueries cq = 
            (CriteriaQueries) context.getBean ("criteriaQueries");
        cq.execute ();
    }

}

