package htmltagdemo;

import com.opensymphony.xwork2.ActionSupport;

public class HtmlTagDemo extends ActionSupport{
	private String selectResult;
	private String[] checkboxResult;
	private String[] multiSelectResult;
	private String radioResult;
	private int country;
	private int city;
	 
	public int getCountry() {
		return country;
	}
	public void setCountry(int country) {
		this.country = country;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public String getSelectResult() {
		return selectResult;
	}
	public void setSelectResult(String selectResult) {
		this.selectResult = selectResult;
	}
	public String[] getCheckboxResult() {
		return checkboxResult;
	}
	public void setCheckboxResult(String[] checkboxResult) {
		this.checkboxResult = checkboxResult;
	}
	public String[] getMultiSelectResult() {
		return multiSelectResult;
	}
	public void setMultiSelectResult(String[] multiSelectResult) {
		this.multiSelectResult = multiSelectResult;
	}
	public String getRadioResult() {
		return radioResult;
	}
	public void setRadioResult(String radioResult) {
		this.radioResult = radioResult;
	}
	public String execute(){
		return SUCCESS;
	}
}
