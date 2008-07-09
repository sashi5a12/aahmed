package transferAndconversion;

import com.opensymphony.xwork2.ActionSupport;

public class ArrayDataTransfer extends ActionSupport{
	private Integer[] ages;
	private String[] names=new String[10];
	public Integer[] getAges() {
		return ages;
	}
	public void setAges(Integer[] ages) {
		this.ages = ages;
	}
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	
	public String execute(){
		return SUCCESS;
	}
}
