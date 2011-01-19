package cc.db.beans; 

import java.text.NumberFormat;

public class Job {
    private Integer id;
    private String name;
    private double minimumSalary;
    private double maximumSalary;
    
    public Integer getId() {
        return id;
    }
    private void setId(Integer id) {
        this.id = id;
    }
    public double getMaximumSalary() {
        return maximumSalary;
    }
    public void setMaximumSalary(double maximumSalary) {
        if (minimumSalary > maximumSalary)
            throw new IllegalArgumentException("Maximum must be greater than minimum");
        this.maximumSalary = maximumSalary;
    }
    public double getMinimumSalary() {
        return minimumSalary;
    }
    public void setMinimumSalary(double minimumSalary) {
        if (minimumSalary > maximumSalary)
            throw new IllegalArgumentException("Maximum must be greater than minimum");
        this.minimumSalary = minimumSalary;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return "(" + id + "," + name + "," + nf.format(minimumSalary)
            + "," + nf.format(maximumSalary) + ")";
    }
}
