package com.netpace.device.vo;

import java.util.List;

public class WorklistForm extends GenericListForm {

    private List<WorkItem> items;

    public WorklistForm() {
    }

    public List<WorkItem> getItems() {
        return items;
    }

    public void setItems(List<WorkItem> items) {
        this.items = items;
    }
}
