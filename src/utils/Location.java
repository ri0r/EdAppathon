package utils;

public class Location {
	private String location;
	
	public Location(String name) {
		this.location = name;
	}
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return location;
	}



}
