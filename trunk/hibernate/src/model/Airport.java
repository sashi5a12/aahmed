package model;



/**
 * Airport entity. @author MyEclipse Persistence Tools
 */

public class Airport  implements java.io.Serializable {


    // Fields    

     private long id;
     private String name;


    // Constructors

    /** default constructor */
    public Airport() {
    }

    
    /** full constructor */
    public Airport(String name) {
        this.name = name;
    }

   
    // Property accessors

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
   








}