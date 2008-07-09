package transferAndconversion;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

public class ListDataTransfer extends ActionSupport{
	List<String> lastNames;
	List<String> middleNames;
	List<Double> weights;
	public List<String> getLastNames() {
		return lastNames;
	}
	public void setLastNames(List<String> lastNames) {
		this.lastNames = lastNames;
	}
	public List<String> getMiddleNames() {
		return middleNames;
	}
	public void setMiddleNames(List<String> middleNames) {
		this.middleNames = middleNames;
	}
	public List<Double> getWeights() {
		return weights;
	}
	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}
	
	public String execute(){
		return SUCCESS;
	}
}
