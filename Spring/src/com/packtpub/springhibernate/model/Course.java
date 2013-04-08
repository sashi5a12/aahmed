package com.packtpub.springhibernate.model;

public class Course {
    private int id;
    private String name;
    private int unit;

    //default constructor
    public Course() {
    }

    public Course(String name, int unit) {
        this.name = name;
        this.unit = unit;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}
    
}