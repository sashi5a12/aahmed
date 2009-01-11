package model;

import java.util.SortedMap;
import java.util.TreeMap;


/**
 * ValueTypeMapItem entity. @author MyEclipse Persistence Tools
 */

public class ValueTypeMapItem  implements java.io.Serializable {


    // Fields    

     private Long itemId;
     private String itemName;
     private SortedMap images = new TreeMap();


    // Constructors

    /** default constructor */
    public ValueTypeMapItem() {
    }

	/** minimal constructor */
    public ValueTypeMapItem(String itemName) {
        this.itemName = itemName;
    }
    
    /** full constructor */
    public ValueTypeMapItem(String itemName, SortedMap images) {
        this.itemName = itemName;
        this.images = images;
    }

   
    // Property accessors

    public Long getItemId() {
        return this.itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return this.itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public SortedMap getImages() {
        return this.images;
    }
    
    public void setImages(SortedMap images) {
        this.images = images;
    }
   








}