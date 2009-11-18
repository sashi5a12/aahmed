package com.netpace.aims.controller.alliance;

import org.apache.log4j.Logger;

public class AllianceMusicRegistrationProductBean
{

    static Logger log = Logger.getLogger(AllianceMusicRegistrationProductBean.class.getName());

    private String productId;
    private String productName;
    private String sizeTotalCatalog;
    private String sizeMobileCatalog;
    private String incomeNonMobile;
    private String incomeMobile;
    private String topSellingArtists;

    public AllianceMusicRegistrationProductBean()
    {}

    public AllianceMusicRegistrationProductBean(String productId, String productName)
    {
        this.productId = productId;
        this.productName = productName;
        this.sizeTotalCatalog = "";
        this.sizeMobileCatalog = "";
        this.incomeNonMobile = "";
        this.incomeMobile = "";
        this.topSellingArtists = "";
    }

    public AllianceMusicRegistrationProductBean(
        String productId,
        String productName,
        String sizeTotalCatalog,
        String sizeMobileCatalog,
        String incomeNonMobile,
        String incomeMobile,
        String topSellingArtists)
    {
        this.productId = productId;
        this.productName = productName;
        this.sizeTotalCatalog = sizeTotalCatalog;
        this.sizeMobileCatalog = sizeMobileCatalog;
        this.incomeNonMobile = incomeNonMobile;
        this.incomeMobile = incomeMobile;
        this.topSellingArtists = topSellingArtists;
    }

    /**
     * @return
     */
    public String getTopSellingArtists()
    {
        return topSellingArtists;
    }

    /**
     * @return
     */
    public String getProductId()
    {
        return productId;
    }

    /**
     * @return
     */
    public String getIncomeNonMobile()
    {
        return incomeNonMobile;
    }

    /**
     * @return
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * @return
     */
    public String getSizeTotalCatalog()
    {
        return sizeTotalCatalog;
    }

    /**
     * @param string
     */
    public void setTopSellingArtists(String string)
    {
        topSellingArtists = string;
    }

    /**
     * @param string
     */
    public void setProductId(String string)
    {
        productId = string;
    }

    /**
     * @param string
     */
    public void setIncomeNonMobile(String string)
    {
        incomeNonMobile = string;
    }

    /**
     * @param string
     */
    public void setProductName(String string)
    {
        productName = string;
    }

    /**
     * @param string
     */
    public void setSizeTotalCatalog(String string)
    {
        sizeTotalCatalog = string;
    }

    /**
     * @return
     */
    public String getIncomeMobile()
    {
        return incomeMobile;
    }

    /**
     * @return
     */
    public String getSizeMobileCatalog()
    {
        return sizeMobileCatalog;
    }

    /**
     * @param string
     */
    public void setIncomeMobile(String string)
    {
        incomeMobile = string;
    }

    /**
     * @param string
     */
    public void setSizeMobileCatalog(String string)
    {
        sizeMobileCatalog = string;
    }

}
