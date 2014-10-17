package nu.ac.th.rescueunit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity{
	 public static final String ACCIDENT_DATA = IntentExtraKeys.ACCIDENT_WITH_STATE;
	  
	 // GUI
	 private GoogleMap googleMap;
	 private Marker accidentMarker;

	 private AccidentData accidentData;
	 
	 private static final String RESCUE_UNIT_MARKER_TITLE = "Me";
	 private static final String ACCIDENT_MARKER_TITLE = "Accident";
	 
	 private static final int ZOOM = 14;
	 private static final int MAP_TYPE = GoogleMap.MAP_TYPE_HYBRID;
	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_map);
	    
	    createInterface();
		initializeVariables();
		initializeGUIComponents(); 
		setMapMarker();
	 }
	  
	 
	private void createInterface() {
				
	} 
	
	private void initializeVariables() {
		Intent receivedIntent = getIntent();
		
		try {
			accidentData = (AccidentData)receivedIntent.getSerializableExtra(ACCIDENT_DATA);
		} catch(NullPointerException e) {
			// NO DATA?
		}
	}
	
	@SuppressLint("NewApi")
	private void initializeGUIComponents() {
		googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		googleMap.setMapType(MAP_TYPE);
	}
	
	private void setMapMarker() {
		
		try {
			Position position = accidentData.getPosition();
			AdditionalInfo additionalInfo = accidentData.getAdditionalInfo();
			
			String positionStr = position.toString();
			String additionalInfoStr = additionalInfo.toString();
			
			LatLng accidentLatLng = new LatLng(position.getLatitude(), position.getLongitude());
			
			accidentMarker = googleMap.addMarker(new MarkerOptions()
		      	  .position(accidentLatLng)
		          .title(ACCIDENT_MARKER_TITLE)
		          .snippet(positionStr + "\n" + additionalInfoStr)
		          .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_dot)));
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(accidentLatLng, ZOOM));
	    } catch(NullPointerException e) {
	    	
	    }
	}
}
