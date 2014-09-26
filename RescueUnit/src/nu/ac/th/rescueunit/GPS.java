package nu.ac.th.rescueunit;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPS extends Locator implements LocationListener {
	private static final long MIN_TIME = 1500;
	private static final float MIN_DISTANCE = 1;
	
	private LocationManager mLocationManager;
	
	public GPS(LocatorListener locatorListener, Position position, Context context) {
		super(locatorListener, position);
		mLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	public void startLocatePosition() {
		mLocationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
	}

	
	@Override
	public void stopLocatePosition() {
		mLocationManager.removeUpdates(this);
	}

	
	@Override
	public void onLocationChanged(Location location) {
		mPosition.setLatitude(location.getLatitude());
		mPosition.setLongitude(location.getLongitude());
		updatePosition(mPosition);
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
}
