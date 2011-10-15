package utils;

public class Location {
	private String location;
	private float longitude;
	private float latitude;
	
	public Location(String name) {
		this.location = name;
	}
	public Location(String name, float lat, float lng) {
		this.longitude = lng;
		this.latitude = lat;
		this.location = name;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}


	public float getLongitude() {
		return longitude;
	}


	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}


	public float getLatitude() {
		return latitude;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return location;
	}



}
