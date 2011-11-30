package sia3.chap02;

import java.util.Arrays;
import java.util.List;

public class SpELExample {
	private int count;
	private String message;
	private float frequency;
	private long capacity;
	private Instrument instrument;
	private String song;
	private String song2;
	private float pi;
	private float random;
	private City chosenCityFixed;
	private City chosenCityRandom;
	private String name;
	private List<City> bigCities;
	private City firstCity;
	private City lastCity;
	private String[] cityNames;
	private String[] bitCityNames;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public float getFrequency() {
		return frequency;
	}
	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}
	public long getCapacity() {
		return capacity;
	}
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	public String getSong() {
		return song;
	}
	public void setSong(String song) {
		this.song = song;
	}
	public String getSong2() {
		return song2;
	}
	public void setSong2(String song2) {
		this.song2 = song2;
	}
	public float getPi() {
		return pi;
	}
	public void setPi(float pi) {
		this.pi = pi;
	}
	public float getRandom() {
		return random;
	}
	public void setRandom(float random) {
		this.random = random;
	}
	
	public City getChosenCityFixed() {
		return chosenCityFixed;
	}
	public void setChosenCityFixed(City chosenCityFixed) {
		this.chosenCityFixed = chosenCityFixed;
	}
	public City getChosenCityRandom() {
		return chosenCityRandom;
	}
	public void setChosenCityRandom(City chosenCityRandom) {
		this.chosenCityRandom = chosenCityRandom;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<City> getBigCities() {
		return bigCities;
	}
	public void setBigCities(List<City> bigCities) {
		this.bigCities = bigCities;
	}
	public City getFirstCity() {
		return firstCity;
	}
	public void setFirstCity(City firstCity) {
		this.firstCity = firstCity;
	}
	public City getLastCity() {
		return lastCity;
	}
	public void setLastCity(City lastCity) {
		this.lastCity = lastCity;
	}
	
	public String[] getCityNames() {
		return cityNames;
	}
	public void setCityNames(String[] cityNames) {
		this.cityNames = cityNames;
	}
	public String[] getBitCityNames() {
		return bitCityNames;
	}
	public void setBitCityNames(String[] bitCityNames) {
		this.bitCityNames = bitCityNames;
	}
	public String toString()
	{
		this.instrument.play();	    
	    final String TAB = "    ";
	    
	    String retValue = "";
	    retValue = "SpELExample ( "
	        + "count = " + this.count + TAB
	        + "message = " + this.message + TAB
	        + "frequency = " + this.frequency + TAB
	        + "capacity = " + this.capacity + TAB
	        + "song = " + this.song + TAB
	        + "song2 = " + this.song2 + TAB
	        + "pi = " + this.pi + TAB
	        + "random = " + this.random + TAB
	        + "\nchosenCityFixed = " + this.chosenCityFixed + TAB
	        + "\nchosenCityRandom = " + this.chosenCityRandom + TAB
	        + "\nname = " + this.name + TAB
	        + "\nbigCities = " + this.bigCities + TAB
	        + "\nfirstCity = " + this.firstCity + TAB
	        + "\nlastCity = " + this.lastCity + TAB
	        + "\ncityNames = " + Arrays.toString(this.cityNames) + TAB
	        + "\nbigCityNames = " + Arrays.toString(this.bitCityNames) + TAB
	        + " )";
	
	    return retValue;
	}
	
}
