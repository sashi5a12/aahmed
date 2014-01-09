package com.netpace.device.utils;
public class PagingOptions {
    /** the page number requested. not required. defaults to 1st page */
    int pageNumber = 0;

    /** the size of the page. Not Required. Defaults to 3 */
    int pageSize = 3;

    /** the sort order. Not Required. Default sorting is determined by implementation */
    String sortOrder = null;

    /** asc/desc sort. defaults to ascending sort*/
    boolean ascending = true;

    public PagingOptions() {
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortOrder
     * @param ascending
     * @param versionNumber
     */
    public PagingOptions(int pageNumber, int pageSize, String sortOrder,
                         boolean ascending) {
        super();
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortOrder = sortOrder;
        this.ascending = ascending;
    }

    /**
     * Returns the first record number according to the page size and number.
     */
    public int getFirstResult() {
        return this.pageSize * this.pageNumber;
    }

    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the ascending
     */
    public boolean isAscending() {
        return ascending;
    }

    /**
     * @param ascending the ascending to set
     */
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

}
