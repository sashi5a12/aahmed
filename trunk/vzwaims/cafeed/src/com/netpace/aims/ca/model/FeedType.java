package com.netpace.aims.ca.model;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public class FeedType {
	private Character typeSymbol;
	private String typeName;
	private String typeDescription;
	
	public FeedType(Character typeSymbol, String typeName, String typeDescription){
		this.typeSymbol = typeSymbol;
		this.typeName = typeName;
		this.typeDescription = typeDescription;
	}
	
	public Character getTypeSymbol() {
		return typeSymbol;
	}
	public void setTypeSymbol(Character typeSymbol) {
		this.typeSymbol = typeSymbol;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeDescription() {
		return typeDescription;
	}
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
}
