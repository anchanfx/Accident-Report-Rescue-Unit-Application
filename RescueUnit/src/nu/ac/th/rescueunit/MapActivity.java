package nu.ac.th.rescueunit;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

public class MapActivity extends Activity{
	static final LatLng KIEL = new LatLng(16.742906328, 100.203977484);
	  private GoogleMap map;

	  @SuppressLint("NewApi")
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.map);
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragment1))
	        .getMap();
	    
	    if (map!=null){
	      Marker kiel = map.addMarker(new MarkerOptions()
	      	  .position(KIEL)
	          .title("Kiel")
	          .snippet("Kiel is cool")
	          .icon(BitmapDescriptorFactory
	          .fromResource(R.drawable.ic_launcher)));
	    }
	    
	  } 
}
