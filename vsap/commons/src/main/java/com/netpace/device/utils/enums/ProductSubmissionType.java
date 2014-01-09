package com.netpace.device.utils.enums;

public enum ProductSubmissionType {
	Product(1, "Product (existing physical product)"), 
	Concept(2, "Concept (no physical product available at this time)");

	private int code;
	private String label;

	private ProductSubmissionType(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

}
