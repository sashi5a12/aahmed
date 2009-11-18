package com.netpace.aims.ws.appinfo;

import java.lang.Long;
import java.lang.String;;

public class AimsApp 
{
	private Long appsId;
	private String longDesc;
	private String shortDesc;
	private String title;
	private Long appRefId;
	private Long aimsLifecyclePhaseId;
	
	public Long getAppRefId() {
		return appRefId;
	}
	public void setAppRefId(Long appRefId) {
		this.appRefId = appRefId;
	}
	
	public Long getAimsLifecyclePhaseId() {
		return aimsLifecyclePhaseId;
	}
	public void setAimsLifecyclePhaseId(Long aimsLifecyclePhaseId) {
		this.aimsLifecyclePhaseId = aimsLifecyclePhaseId;
	}
	public Long getAppsId() {
		return appsId;
	}
	public void setAppsId(Long appsId) {
		this.appsId = appsId;
	}
	public String getLongDesc() {
		return longDesc;
	}
	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
