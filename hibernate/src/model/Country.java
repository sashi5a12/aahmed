package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Country entity. @author MyEclipse Persistence Tools
 */

public class Country implements java.io.Serializable {

	// Fields

	private long id;
	private String code;
	private String name;
	private Set airports = new HashSet(0);

	// Constructors

	/** default constructor */
	public Country() {
	}

	/** full constructor */
	public Country(String code, String name, Set airports) {
		this.code = code;
		this.name = name;
		this.airports = airports;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getAirports() {
		return this.airports;
	}

	public void setAirports(Set airports) {
		this.airports = airports;
	}

}