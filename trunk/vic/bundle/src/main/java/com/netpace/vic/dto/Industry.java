
package com.netpace.vic.dto;

public class Industry extends Base{
    private Integer industryId;
    private String industryName;

    public Industry(Integer industryId, String industryName) {
        this.industryId = industryId;
        this.industryName = industryName;
    }

    public Industry() {
    }

    
    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    @Override
    public String toString() {
        return "Industry{" + "industryId=" + industryId + ", industryName=" + industryName + '}';
    }
        
}
