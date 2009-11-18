package com.netpace.vzdn.model;

public class VzdnEventHandlers implements java.io.Serializable {

	private Integer eventHandlerId;
	private VzdnEvents vzdnEvents;
	private String className;

	/** default constructor */
	public VzdnEventHandlers() {
	}

	public Integer getEventHandlerId() {
		return this.eventHandlerId;
	}

	public void setEventHandlerId(Integer eventHandlerId) {
		this.eventHandlerId = eventHandlerId;
	}

	public VzdnEvents getVzdnEvents() {
		return this.vzdnEvents;
	}

	public void setVzdnEvents(VzdnEvents vzdnEvents) {
		this.vzdnEvents = vzdnEvents;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}