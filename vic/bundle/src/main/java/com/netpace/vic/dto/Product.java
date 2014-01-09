package com.netpace.vic.dto;

public class Product extends Base {

    private Integer productId;
    private String productTitle;
    private String parnterName;
    private String partnerUrl;
    private String featured;
    private Integer industryId;
    private String industryName;
    private String shortDesc;
    private String longDesc;
    private String businessSalesNum;
    private String govSalesNum;
    private String salesInfo;
    private String url;
    private String email;    
    private Media media;
    private Media partnerMedia;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getParnterName() {
        return parnterName;
    }

    public void setParnterName(String parnterName) {
        this.parnterName = parnterName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getBusinessSalesNum() {
        return businessSalesNum;
    }

    public void setBusinessSalesNum(String businessSalesNum) {
        this.businessSalesNum = businessSalesNum;
    }

    public String getGovSalesNum() {
        return govSalesNum;
    }

    public void setGovSalesNum(String govSalesNum) {
        this.govSalesNum = govSalesNum;
    }

    public String getSalesInfo() {
		return salesInfo;
	}

	public void setSalesInfo(String salesInfo) {
		this.salesInfo = salesInfo;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*
    public List<Media> getImages() {
        return images;
    }

    public void setImages(List<Media> images) {
        this.images = images;
    }
    
    public void addImage(Media m){
        if(this.images==null){
            this.images=new ArrayList<Media>();
        }
        this.images.add(m);
    }
*/    
    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Media getPartnerMedia() {
        return partnerMedia;
    }

    public void setPartnerMedia(Media partnerMedia) {
        this.partnerMedia = partnerMedia;
    }
   
    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public String getPartnerUrl() {
        return partnerUrl;
    }

    public void setPartnerUrl(String partnerUrl) {
        this.partnerUrl = partnerUrl;
    }
    
    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productTitle=" + productTitle + ", parnterName=" + parnterName + '}';
    }
    
    
}