package htmltagdemo;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

public class HtmlTagDemoSetup extends ActionSupport{
	private List<KeyBean> list;
	private List<Country> countries;
	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public List<KeyBean> getList() {
		return list;
	}

	public void setList(List<KeyBean> list) {
		this.list = list;
	}

	public String execute(){
		this.setList(getKeyBeans());
		this.setCountries(getCountryList());
		return SUCCESS;
	}
	
	public List<KeyBean> getKeyBeans(){
		String[] count={"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten"};
		List<KeyBean> list=new ArrayList<KeyBean>();
		for(int i=0; i<count.length; i++){
			KeyBean bean=new KeyBean();
			bean.setKey(i+1);
			bean.setValue(count[i]);
			list.add(bean);
		}
		return list;
	}
	private List<Country> getCountryList(){
		Country pakistan=new Country();
		pakistan.setCountryId(1);
		pakistan.setName("Pakistan");
		City karachi=new City();
		karachi.setCityId(1);
		karachi.setName("Karachi");
		City multan=new City();
		multan.setCityId(2);
		multan.setName("Multan");
		City lahore=new City();
		lahore.setCityId(3);
		lahore.setName("Lahore");
		List<City> pakCities=new  ArrayList<City>();
		pakCities.add(karachi);
		pakCities.add(multan);
		pakCities.add(lahore);
		pakistan.setCities(pakCities);
		
		Country india=new Country();
		india.setCountryId(2);
		india.setName("India");
		City bombay=new City();
		bombay.setCityId(1);
		bombay.setName("Bombay");
		City dehli=new City();
		dehli.setCityId(2);
		dehli.setName("New Dehli");
		City hyderabad=new City();
		hyderabad.setCityId(3);
		hyderabad.setName("Hyderabad");
		List<City> inCities=new  ArrayList<City>();
		inCities.add(bombay);
		inCities.add(dehli);
		inCities.add(hyderabad);
		india.setCities(inCities);
		
		List<Country> countryList=new ArrayList<Country>();
		countryList.add(pakistan);
		countryList.add(india);
		
		return countryList;
	}
}

class KeyBean {
	private int key;
	private String value;
	KeyBean(){
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}

class Country{
	private int countryId;
	private String name;
	List<City> cities;
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
class City{
	private int cityId;
	private String name;
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}