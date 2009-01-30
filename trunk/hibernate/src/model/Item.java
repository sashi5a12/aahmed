package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Item entity. @author MyEclipse Persistence Tools
 */

public class Item  implements java.io.Serializable {


    // Fields    

     private Long itemId;
     private Users seller;
     private Users approvedBy;
     private Integer objVersion;
     private String itemName;
     private String description;
     private Double initialPrice;
     private String initialPriceCurrency;
     private Double reservePrice;
     private String reservePriceCurrency;
     private Timestamp startDate;
     private Timestamp endDate;
     private String itemState;
     private Timestamp approvalDatetime;
     private Timestamp created;
     private Set categorizedItems = new HashSet(0);
     private List bids = new ArrayList(0);
     private Collection images = new ArrayList(0);
     private Collection categories = new ArrayList(0);
     private Users buyer;


    // Constructors

    /** default constructor */
    public Item() {
    }

	/** minimal constructor */
    public Item(String itemName) {
        this.itemName = itemName;
    }
    
    /** full constructor */
    public Item(Users seller, Users approvedBy, Integer objVersion, String itemName, String description, Double initialPrice, String initialPriceCurrency, Double reservePrice, String reservePriceCurrency, Timestamp startDate, Timestamp endDate, String itemState, Timestamp approvalDatetime, Timestamp created, Set categorizedItems, List bids, Collection images, Collection categories, Users buyer) {
        this.seller = seller;
        this.approvedBy = approvedBy;
        this.objVersion = objVersion;
        this.itemName = itemName;
        this.description = description;
        this.initialPrice = initialPrice;
        this.initialPriceCurrency = initialPriceCurrency;
        this.reservePrice = reservePrice;
        this.reservePriceCurrency = reservePriceCurrency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.itemState = itemState;
        this.approvalDatetime = approvalDatetime;
        this.created = created;
        this.categorizedItems = categorizedItems;
        this.bids = bids;
        this.images = images;
        this.categories = categories;
        this.buyer = buyer;
    }

   
    // Property accessors

    public Long getItemId() {
        return this.itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Users getSeller() {
        return this.seller;
    }
    
    public void setSeller(Users seller) {
        this.seller = seller;
    }

    public Users getApprovedBy() {
        return this.approvedBy;
    }
    
    public void setApprovedBy(Users approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Integer getObjVersion() {
        return this.objVersion;
    }
    
    public void setObjVersion(Integer objVersion) {
        this.objVersion = objVersion;
    }

    public String getItemName() {
        return this.itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getInitialPrice() {
        return this.initialPrice;
    }
    
    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getInitialPriceCurrency() {
        return this.initialPriceCurrency;
    }
    
    public void setInitialPriceCurrency(String initialPriceCurrency) {
        this.initialPriceCurrency = initialPriceCurrency;
    }

    public Double getReservePrice() {
        return this.reservePrice;
    }
    
    public void setReservePrice(Double reservePrice) {
        this.reservePrice = reservePrice;
    }

    public String getReservePriceCurrency() {
        return this.reservePriceCurrency;
    }
    
    public void setReservePriceCurrency(String reservePriceCurrency) {
        this.reservePriceCurrency = reservePriceCurrency;
    }

    public Timestamp getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getItemState() {
        return this.itemState;
    }
    
    public void setItemState(String itemState) {
        this.itemState = itemState;
    }

    public Timestamp getApprovalDatetime() {
        return this.approvalDatetime;
    }
    
    public void setApprovalDatetime(Timestamp approvalDatetime) {
        this.approvalDatetime = approvalDatetime;
    }

    public Timestamp getCreated() {
        return this.created;
    }
    
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Set getCategorizedItems() {
        return this.categorizedItems;
    }
    
    public void setCategorizedItems(Set categorizedItems) {
        this.categorizedItems = categorizedItems;
    }

    public List getBids() {
        return this.bids;
    }
    
    public void setBids(List bids) {
        this.bids = bids;
    }

    public Collection getImages() {
        return this.images;
    }
    
    public void setImages(Collection images) {
        this.images = images;
    }

    public Collection getCategories() {
        return this.categories;
    }
    
    public void setCategories(Collection categories) {
        this.categories = categories;
    }

    public Users getBuyer() {
        return this.buyer;
    }
    
    public void setBuyer(Users buyer) {
        this.buyer = buyer;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("itemId").append("='").append(getItemId()).append("' ");			
      buffer.append("itemName").append("='").append(getItemName()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }





  // The following is extra code specified in the hbm.xml files

    	 	public void addBid(Bid bid){
				bid.setItem(this);
				this.bids.add(bid);
			}    	 	
    	 
  // end of extra code specified in the hbm.xml files


}