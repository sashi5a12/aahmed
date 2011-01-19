package cc.db.beans; 

public class Address {
    private String address;
    private String city;
    private String state;
    private String zIPCode;
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZIPCode() {
        return zIPCode;
    }
    public void setZIPCode(String code) {
        zIPCode = code;
    }
    
    public String toString() {
        return "(" + address + "," + city + "," + state + "," + zIPCode + ")";
    }
}
