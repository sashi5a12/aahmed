package cc.db.beans; 

import java.util.Set;

public class Department {
    private Integer id;
    private String name;
    private Location location;
    private Employee manager;
    private Set<Employee> employees;
    
    public Integer getId() {
        return id;
    }
    private void setId(Integer id) {
        this.id = id;
    }
    public Employee getManager() {
        return manager;
    }
    public void setManager(Employee manager) {
        this.manager = manager;
    }
    public Set<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    
    public void addEmployee(Employee e) {
        employees.add(e);
        e.setDepartment(this);
    }
    
    public void removeEmployee(Employee e) {
        employees.remove(e);
        e.setDepartment(null);
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    @Override
    public String toString() {
        return "(" + getId() + "," + getName() + ")";
    }
}
