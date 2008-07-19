package crud;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport {
	// protected DB db;
	private long requestId;
	private boolean readOnly = false;
	private String mappedRequest;

	@SkipValidation
	public String show() {
		setReadOnly(true);
		// setMappedRequest(Constants.LIST);
		return SUCCESS;
	}

	@SkipValidation
	public String add() {
		// setMappedRequest(Constants.SAVE);
		return SUCCESS;
	}

	public String save() { // insert
	// db.save(getModel());
		return list();
	}

	@SkipValidation
	public String edit() {
		// setMappedRequest(Constants.UPDATE);
		return SUCCESS;
	}

	public String update() {// update
	// db.save(getModel());
		return list();
	}

	@SkipValidation
	public String destroy() {
		setReadOnly(true);
		// setMappedRequest(Constants.REMOVE);
		 return Constants.SUCCESS;
	}

	public String remove() {// delete
	// db.remove(getModel());
		return list();
	}

	@SkipValidation
	public String list() {
		// code to fetch list objects is in Tiles Controller
		setMappedRequest(Constants.LIST);
		return Constants.LIST;
	}

	public String getActionClass() {
		return getClass().getSimpleName();
	}

	public String getDestination() {
		return getClass().getSimpleName();
	}

	public String getActionMethod() {
		return mappedRequest;
	}

	// when invalid, the request parameter will restore command action
	public void setActionMethod(String method) {
		this.mappedRequest = method;
	}

	// this prepares command for button on initial screen write
	public void setMappedRequest(String actionMethod) {
		this.mappedRequest = getActionClass() + "_" + actionMethod;
		// log.debug("setting mappedRequest to " + getActionClass() + "_" +
		// actionMethod);
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		// log.debug("setting readOnly to " + readOnly);
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	// public void setDb(DB db) {
	// this.db = db;
	// }
	public boolean isReadOnly() {
		return readOnly;
	}

	public abstract Object getModel();
}
