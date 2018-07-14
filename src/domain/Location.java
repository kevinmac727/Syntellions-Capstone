package domain;

public class Location {

    //This class refers to locations of stores and users..
    //It is set by admins.
    
    String locationId;  //Primary Key. Used as a Foreign Key in the Store class.
                        //locationId is called in the storeDetailsScreen() function in Tiger.

    String userID;      //This was missing from original file, but was in database.
                        //It is a foreign key, referencing userIDs.
                        //If a location refers to a store, userID should be NULL.

    Float taxrate;      //This was missing from original file, but was in database.

    String street;      //The rest of the fields are never really used/displayed outside LocationService.java.
                        //However, there is a section in tiger where a customer enters street/city/etc.,
                        //but it is commented out.
    String city;
    String country;
    String state;
    String zip;
    private int isSaved;

    public Location() {
        super();
    }

    // The rest of this file is constructer, getters, setters, and toString.
    public Location(String locationId, String userID, Float taxrate, String street, String city, String state, String country, String zip) {
        this.locationId = locationId;
        this.userID = userID;
        this.taxrate = taxrate;
        this.street = street;
        this.city = city;
        this.country = country;
        this.state = state;
        this.zip = zip;
    }
    
        // The rest of this file is constructer, getters, setters, and toString.
    public Location(String locationId, String userID, Float taxrate, String street, String city, String state, String country, String zip, int isSaved) {
        this.locationId = locationId;
        this.userID = userID;
        this.taxrate = taxrate;
        this.street = street;
        this.city = city;
        this.country = country;
        this.state = state;
        this.zip = zip;
        this.isSaved = this.isSaved;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Float getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(Float taxrate) {
        this.taxrate = taxrate;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Location{" + "locationId=" + locationId + ", userID=" + userID + ", taxrate=" + taxrate + ", street=" + street + ", city=" + city + ", country=" + country + ", state=" + state + ", zip=" + zip + '}';
    }

    public void setIsSaved(int isaved) {
        
        this.isSaved = isaved;
    }

    public int getSaveState() {
        return isSaved;
    }
}