package cc.db.beans; 

public class Location {
    private int id;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public int getId() {
        return id;
    }
    private void setId(int id) {
        this.id = id;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    @Override
    public String toString() {
        return "(" + id + "," + getStreetAddress() + ","
            + getCity() + "," + getState() + "," 
            + getZipCode() + ")";
    }
}
