package model;

import java.util.HashSet;
import java.util.Set;


/**
 * Employee entity. @author MyEclipse Persistence Tools
 */

public class Employee  implements java.io.Serializable {


    // Fields    

     private long id;
     private String surname;
     private String firstname;
     private Country country;
     private Set languages = new HashSet(0);


    // Constructors

    /** default constructor */
    public Employee() {
    }

	/** minimal constructor */
    public Employee(Country country) {
        this.country = country;
    }
    
    /** full constructor */
    public Employee(String surname, String firstname, Country country, Set languages) {
        this.surname = surname;
        this.firstname = firstname;
        this.country = country;
        this.languages = languages;
    }

   
    // Property accessors

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return this.surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return this.firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Country getCountry() {
        return this.country;
    }
    
    public void setCountry(Country country) {
        this.country = country;
    }

    public Set getLanguages() {
        return this.languages;
    }
    
    public void setLanguages(Set languages) {
        this.languages = languages;
    }
   








}