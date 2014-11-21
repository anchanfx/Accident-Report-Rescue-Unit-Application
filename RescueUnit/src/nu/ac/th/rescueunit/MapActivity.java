package nu.ac.th.rescueunit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity{
	 public static final String ACCIDENT_DATA = IntentExtraKeys.ACCIDENT_WITH_STATE;
	 
	 private BroadcastReceiver LocatorServiceBroadcastReceiver;
	 
	 // GUI
	 private GoogleMap googleMap;
	 private Marker accidentMarker;
	 private Marker rescueUnitMarker;
	 
	 private AccidentData accidentData;
	 private Position rescueUnitPosition;
	 
	 private static final String RESCUE_UNIT_MARKER_TITLE = "Me";
	 private static final String ACCIDENT_MARKER_TITLE = "Accident";
	 
	 private static final int ZOOM = 14;
	 private static final int MAP_TYPE = GoogleMap.MAP_TYPE_HYBRID;
	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_map);
	    MapsInitializer.initialize(getApplicationContext());
	    createInterface();
		initializeVariables();
		initializeGUIComponents(); 
		setupMapMarker();
	 }
	  
	 
	private void createInterface() {
				
	} 
	
	private void initializeVariables() {
		
		readUpdatePosition();
		Intent receivedIntent = getIntent();
		
		try {
			accidentData = (AccidentData)receivedIntent.getSerializableExtra(ACCIDENT_DATA);
		} catch(NullPointerException e) {
			// NO DATA?
		}
		
		LocatorServiceBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				onRescueUnitPositionUpdate();
			}
		};
		
		LocalBroadcastManager.getInstance(this)
			.registerReceiver((LocatorServiceBroadcastReceiver),
					new IntentFilter(LocatorService.BROADCAST));

	}
	
	@SuppressLint("NewApi")
	private void initializeGUIComponents() {
		googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		//googleMap.setMapType(MAP_TYPE);
	}
	
	private void setupMapMarker() {
		rescueUnitMarker = googleMap.addMarker(new MarkerOptions()
    	  .position(createLatLng(rescueUnitPosition))
          .title(RESCUE_UNIT_MARKER_TITLE)
          .snippet("Your Position")
          .icon(BitmapDescriptorFactory.fromResource(R.drawable.white_dot)));
		
		try {
			Position position = accidentData.getPosition();
			AdditionalInfo additionalInfo = accidentData.getAdditionalInfo();
			
			String additionalInfoStr = additionalInfo.toString();
			
			LatLng accidentLatLng = createLatLng(position);
			
			accidentMarker = googleMap.addMarker(new MarkerOptions()
		      	  .position(accidentLatLng)
		          .title(ACCIDENT_MARKER_TITLE)
		          .snippet(additionalInfoStr)
		          .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_dot)));
	
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(accidentLatLng, ZOOM));
	    } catch(NullPointerException e) {
	    	
	    }
	}
	
	private void onRescueUnitPositionUpdate() {
		readUpdatePosition();
		rescueUnitMarker.setPosition(createLatLng(rescueUnitPosition));
	}
	
	private void readUpdatePosition() {
		rescueUnitPosition = ApplicationSharedPreference.getPosition(this);
	}
	
	private LatLng createLatLng(Position position) {
		return new LatLng(position.getLatitude(), position.getLongitude());
	}
}
