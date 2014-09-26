package nu.ac.th.rescueunit;

public class Position {
	private double latitude;
	private double longitude;
	
	public Position() {
		this(0, 0);
	}

	public Position(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return String.valueOf(this.latitude) + " : " + String.valueOf(this.longitude) ;
	}
}
