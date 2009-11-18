package com.netpace.aims.ws.vds;

import java.util.ArrayList;
import java.util.List;

public class BulkDevelopersLog {

	private int total;
	private int success;
	private int fail;
	List<String> successIds;
	List<String> failureIds;
	
	public BulkDevelopersLog() {
		total = success = fail = 0 ;
		successIds = new ArrayList<String>();
		failureIds = new ArrayList<String>();
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public int getFail() {
		return fail;
	}
	public void setFail(int fail) {
		this.fail = fail;
	}
	public List<String> getSuccessIds() {
		return successIds;
	}

	public List<String> getFailureIds() {
		return failureIds;
	}
	public void addSuccessId(String successId) {
		this.successIds.add( successId );
	}

	public void addFailureId(String failureId) {
		this.failureIds.add( failureId );
	}
}
