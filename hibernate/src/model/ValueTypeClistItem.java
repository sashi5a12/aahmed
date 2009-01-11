package model;

import java.util.ArrayList;
import java.util.List;


/**
 * ValueTypeClistItem entity. @author MyEclipse Persistence Tools
 */

public class ValueTypeClistItem  implements java.io.Serializable {


    // Fields    

     private Long itemId;
     private String itemName;
     private List images = new ArrayList(0);


    // Constructors

    /** default constructor */
    public ValueTypeClistItem() {
    }

	/** minimal constructor */
    public ValueTypeClistItem(String itemName) {
        this.itemName = itemName;
    }
    
    /** full constructor */
    public ValueTypeClistItem(String itemName, List images) {
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