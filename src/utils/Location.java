package utils;

/*
 * Must maintain exact mapping with columns in table "roads" in database
 */

public class Location {
	private String location;
	private double longitude;
	private double latitude;
	private int routeID;
	
	public Location(String name) {
		this.location = name;
	}
	public Location(String name, double lat, double lng) {
		this.longitude = lng;
		this.latitude = lat;
		this.location = name;
	}
	
	public Location(String name, double lat, double lng, int routeID) {
		this.longitude = lng;
		this.latitude = lat;
		this.location = name;
		this.routeID = routeID;
	}
	
	public String getLocationName() {
		return location;
	}

	public void setLocationName(String location) {
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


	public int getRouteID() {
		return routeID;
	}
	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return location;
	}



}
