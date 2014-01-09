package com.netpace.device.utils;
import com.netpace.device.vo.Record;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Page<E extends Record> {
    List<E> records = new ArrayList<E>();

    /** The number of the current page. */
    int currentPage;

    /** The total number of pages. */
    long totalPages;

    /** size of each page */
    int pageSize;

    /** total number of records */
    long totalRecs;

    /** the sort order */
    String sortOrder;

    /** asc/desc sort. defaults to ascending sort*/
    boolean ascending = true;

    /**
     * @return the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the totalPages
     */
    public long getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages the totalPages to set
     */
    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
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
     * @return the totalRecs
     */
    public long getTotalRecs() {
        return totalRecs;
    }

    /**
     * @param totalRecs the totalRecs to set
     */
    public void setTotalRecs(long totalRecs) {
        this.totalRecs = totalRecs;
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

    public PagingOptions getPagingOptions() {
        return new PagingOptions(
            currentPage,
            pageSize,
            sortOrder,
            ascending);
    }

    public boolean add(E e) {
        return records.add(e);
    }

    public boolean addAll(Collection<?extends E> c) {
        return records.addAll(c);
    }

    public void clear() {
        records.clear();
    }

    public E get(int index) {
        return records.get(index);
    }

    public boolean isEmpty() {
        return records.isEmpty();
    }

    public Iterator<E> iterator() {
        return records.iterator();
    }

    public int size() {
        return records.size();
    }

    public List<E> getRecords() {
        return records;
    }

    public void setRecords(List<E> records) {
        this.records = records;
    }
}
