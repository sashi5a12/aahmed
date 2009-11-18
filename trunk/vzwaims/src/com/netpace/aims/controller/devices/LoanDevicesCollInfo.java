package com.netpace.aims.controller.devices;

import java.io.Serializable;
import java.util.Collection;
import java.lang.Long;

/**
 * For returing list of loaned device and their respective count and current page no 
 * @author Ahson Imtiaz */
public class LoanDevicesCollInfo implements Serializable {

    private java.util.Collection collection;
    private java.lang.Integer totalNumberOfRecords;

    /** default constructor */
    public LoanDevicesCollInfo() {
   }
   
    /** default constructor */
    public LoanDevicesCollInfo(Collection coll, Integer totalRecords) {
    	collection = coll;
    	totalNumberOfRecords = totalRecords;
    }


    public java.lang.Integer getTotalNumberOfRecords() {
        return this.totalNumberOfRecords ;
    }

    public void setTotalNumberOfRecords(java.lang.Integer totalRecords) {
        this.totalNumberOfRecords = totalRecords;
    }

    public Collection getRecords() {
        return this.collection;
    }

    public void setRecords(Collection coll) {
        this.collection = coll;
    }

}
