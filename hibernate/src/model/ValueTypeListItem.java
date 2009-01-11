package model;

import java.util.ArrayList;
import java.util.List;


/**
 * ValueTypeListItem entity. @author MyEclipse Persistence Tools
 */

public class ValueTypeListItem  implements java.io.Serializable {


    // Fields    

     private Long itemId;
     private String itemName;
     private List images = new ArrayList(0);


    // Constructors

    /** default constructor */
    public ValueTypeListItem() {
    }

	/** minimal constructor */
    public ValueTypeListItem(String itemName) {
        this.itemName = itemName;
    }
    
    /** full constructor */
    public ValueTypeListItem(String itemName, List images) {
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

    public List getImages() {
        return this.images;
    }
    
    public void setImages(List images) {
        this.images = images;
    }
   








}