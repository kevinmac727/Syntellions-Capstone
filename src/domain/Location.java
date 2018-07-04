package domain;

public class Location {
	
	String locationId;
	String street;
	String city;
	String country;
	String state;
	String zip;
	
	public Location() {
		super();
	}
	
	public Location(String locationId, String street, String city, String country, String state, String zip) {
		super();
		this.locationId = locationId;
		this.street = street;
		this.city = city;
		this.country = country;
		this.state = state;
		this.zip = zip;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
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
		return "Locations [locationId=" + locationId + ", street=" + street + ", city=" + city + ", country="
				+ country + ", state=" + state + ", zip=" + zip + "]";
	}
	
	
	
	
}

