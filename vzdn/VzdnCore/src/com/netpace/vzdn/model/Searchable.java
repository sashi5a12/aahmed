package com.netpace.vzdn.model;

public class Searchable {
	private int searchableId;
	private String displayedValue;
	
	private String contentToSearch;
	
	
	public Searchable(int id, String value){
		searchableId = id;
		this.displayedValue = value;	
	}
	
	public Searchable(){}
	
	public int getSearchableId() {
		return searchableId;
	}
	public void setSearchableId(int searchableId) {
		this.searchableId = searchableId;
	}
	public String getContentToSearch() {
		return contentToSearch;
	}
	public void setContentToSearch(String contentToSearch) {
		this.contentToSearch = contentToSearch;
	}

	public String getDisplayedValue() {
		return displayedValue;
	}

	public void setDisplayedValue(String displayedValue) {
		this.displayedValue = displayedValue;
	}
}
