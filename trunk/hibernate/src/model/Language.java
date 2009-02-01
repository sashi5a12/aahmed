package model;



/**
 * Language entity. @author MyEclipse Persistence Tools
 */

public class Language  implements java.io.Serializable {


    // Fields    

     private long id;
     private String code;
     private String name;


    // Constructors

    /** default constructor */
    public Language() {
    }

    
    /** full constructor */
    public Language(String code, String name) {
        this.code = code;
        this.name = name;
    }

   
    // Property accessors

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
   








}