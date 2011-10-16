package utils;

public class Location {
	private String location;
	private double longitude;
	private double latitude;
	
	public Location(String name) {
		this.location = name;
	}
	public Location(String name, double lat, double lng) {
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
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLatitude() {
		return latitude;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return location;
	}



}
