package cc.db.beans; 

/**
 * An employee in the company.
 * 
 * @author Will Provost
 */
public class Employee implements java.io.Serializable {
    private int id;
    private Department department;
    private Department managedDepartment;
    private Job job;
    private String gender;
    private String email;
    private String extension;
    private String hireDate;
    private double commissionPct;
    private Address address;
    private String firstName;
    private String middleName;
    private String lastName;
    private double salary;
    
    public Employee() {
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getManagedDepartment() {
        return managedDepartment;
    }

    public void setManagedDepartment(Department managedDepartment) {
        this.managedDepartment = managedDepartment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//  public enum Gender {
//      FEMALE, MALE
//  };

    public String getFirstName ()
    {
        return firstName;
    }

    /**
     * @return Returns the lastName.
     */
    public String getLastName ()
    {
        return lastName;
    }

    /**
     * @return Returns the salary.
     */
    public double getSalary ()
    {
        return salary;
    }
    /**
     * @param firstName
     *            The firstName to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param lastName
     *            The lastName to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @param salary
     *            The salary to set.
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * @return Returns the address.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address
     *            The address to set.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return Returns the commissionPct.
     */
    public double getCommissionPct() {
        return commissionPct;
    }

    /**
     * @param commissionPct
     *            The commissionPct to set.
     */
    public void setCommissionPct(double commissionPct) {
        this.commissionPct = commissionPct;
    }

    /**
     * @return Returns the eMail.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param mail
     *            The eMail to set.
     */
    public void setEmail(String mail) {
        email = mail;
    }

    /**
     * @return Returns the extension.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension
     *            The extension to set.
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return Returns the gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     *            The gender to set.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return Returns the hireDate.
     */
    public String getHireDate() {
        return hireDate;
    }

    /**
     * @param hireDate
     *            The hireDate to set.
     */
    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * @return Returns the middleName.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName
     *            The middleName to set.
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String toString() {
        return "(Name: " + firstName + " " + lastName + ", Address: " +  address + ")";
    }
}
