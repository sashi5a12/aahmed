package com.netpace.device.vo;

/**
 *
 * @author aahmed
 */
public class GenericListForm<E> {
    private PageModel<E> pageModel;

    public GenericListForm() {
         pageModel = new PageModel<E>();
    }
    
    public PageModel<E> getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel<E> pageModel) {
        this.pageModel = pageModel;
    }
}
