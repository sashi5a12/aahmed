package com.netpace.device.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.beans.support.SortDefinition;
import org.springframework.util.Assert;

public class PagedListHolder implements Serializable {

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_MAX_LINKED_PAGES = 5;
    private List source;
    private Date refreshDate;
    private SortDefinition sort;
    private SortDefinition sortUsed;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int page = 0;
    private boolean newPageSet;
    private int maxLinkedPages = DEFAULT_MAX_LINKED_PAGES;
    private int nrOfElements;

    public PagedListHolder() {
        this(new ArrayList(0));
    }

    public PagedListHolder(List source) {
        this(source, new MutableSortDefinition(true));
    }

    public PagedListHolder(List source, SortDefinition sort) {
        setSource(source);
        setSort(sort);
    }

    public void setSource(List source) {
        Assert.notNull(source, "Source List must not be null");
        this.source = source;
        this.refreshDate = new Date();
        this.sortUsed = null;
    }

    public List getSource() {
        return this.source;
    }

    public Date getRefreshDate() {
        return this.refreshDate;
    }

    public void setSort(SortDefinition sort) {
        this.sort = sort;
    }

    public SortDefinition getSort() {
        return this.sort;
    }

    public void setPageSize(int pageSize) {
        if (pageSize != this.pageSize) {
            this.pageSize = pageSize;
            if (!this.newPageSet) {
                this.page = 0;
            }
        }
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPage(int page) {
        this.page = page;
        this.newPageSet = true;
    }

    public int getPage() {
        this.newPageSet = false;
        if (this.page >= getPageCount()) {
            this.page = getPageCount() - 1;
        }
        return this.page;
    }

    public void setMaxLinkedPages(int maxLinkedPages) {
        this.maxLinkedPages = maxLinkedPages;
    }

    public int getMaxLinkedPages() {
        return this.maxLinkedPages;
    }

    public int getPageCount() {
        float nrOfPages = (float) getNrOfElements() / getPageSize();
        return (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages);
    }

    public boolean isFirstPage() {
        return getPage() == 0;
    }

    public boolean isLastPage() {
        return getPage() == getPageCount() - 1;
    }

    public void previousPage() {
        if (!isFirstPage()) {
            this.page--;
        }
    }

    public void nextPage() {
        if (!isLastPage()) {
            this.page++;
        }
    }

    public int getNrOfElements() {
        return this.nrOfElements;
    }

    public void setNrOfElements(int nrOfElements) {
        this.nrOfElements = nrOfElements;
    }

    public int getFirstElementOnPage() {
        return (getPageSize() * getPage());
    }

    public int getLastElementOnPage() {
        int endIndex = getPageSize() * (getPage() + 1);
        int size = getNrOfElements();
        return (endIndex > size ? size : endIndex) - 1;
    }

    public List getPageList() {
        return getSource();
    }

    public int getFirstLinkedPage() {
        return Math.max(0, getPage() - (getMaxLinkedPages() / 2));
    }

    public int getLastLinkedPage() {
        return Math.min(getFirstLinkedPage() + getMaxLinkedPages() - 1, getPageCount() - 1);
    }

    public void resort() {
        SortDefinition sort = getSort();
        if (sort != null && !sort.equals(this.sortUsed)) {
            this.sortUsed = copySortDefinition(sort);
            doSort(getSource(), sort);
            setPage(0);
        }
    }

    protected SortDefinition copySortDefinition(SortDefinition sort) {
        return new MutableSortDefinition(sort);
    }

    protected void doSort(List source, SortDefinition sort) {
        PropertyComparator.sort(source, sort);
    }
}
