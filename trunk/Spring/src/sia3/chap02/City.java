package sia3.chap02;

public class City {
	private String name;
	private String state;
	private int population;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public String toString()
	{
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "City ( "
	        + "name = " + this.name + TAB
	        + "state = " + this.state + TAB
	        + "population = " + this.population + TAB
	        + " )";
	
	    return retValue;
	}
}