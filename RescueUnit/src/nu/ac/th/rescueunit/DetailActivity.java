package nu.ac.th.rescueunit;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class DetailActivity extends Activity{
	public static final String ACCIDENT_WITH_STATE = IntentExtraKeys.ACCIDENT_WITH_STATE;
	
	// GUI
	private TextView txtViewDate;
	private TextView txtViewLongitude;
	private TextView txtViewLatitude;
	private TextView txtViewAccidentType;
	private TextView txtViewAmountOfDead;
	private TextView txtViewAmountOfInjured;
	private TextView txtViewMessage;
	private CheckBox chkboxTrafficBlocked;
	private Button btnMap;
	private OnClickListener btnMapListener;
	
	AccidentWithState accidentWithState;
	AccidentData accidentData;
	AccidentRescueState accidentRescueState;
	
	Spinner spin;
	
	private GestureLibrary gestureLib;
	ViewFlipper vf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		spin = (Spinner) findViewById(R.id.spinner_state_accident);
		String[] obj = {"Abadon", "Success"};
		
		ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.menu_spinner, obj);
		spin.setAdapter(adapter);
		
		GestureOverlayView gest1 = (GestureOverlayView)findViewById(R.id.gestures1);
		gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if(!gestureLib.load()){
			finish();
		}
		gest1.addOnGesturePerformedListener(handleGestureListener);
		
		createInterface();
		initializeVariables();
		initializeGUIComponents();
		
		
	}
	
	/*private OnGesturePerformedListene handleGestureListener = new OnGesturePerformedListener() {
		public void onGesturePerformed(GestureOverlayView gestureView,
				Gesture gesture) {
 
			ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
 
			if (predictions.size() > 0) {
				Prediction prediction = predictions.get(0);
				if (prediction.score > 1.0) {
					
				     	String action = predictions.get(0).name;
				        if ("Slide to left".equals(action)) {
				        	vf.showPrevious();
							Toast.makeText(MainActivity.this, prediction.name + " : Show Next Picture" ,
									Toast.LENGTH_SHORT).show();	
				        } else if ("Slide to right".equals(action)) {
				        	vf.showPrevious();
							vf.showPrevious();
							Toast.makeText(MainActivity.this, prediction.name + " : Show Previous Picture" ,
									Toast.LENGTH_SHORT).show();		
				        }
				   

				}
			}
 
		}
	};
	
	*/
	
	private OnGesturePerformedListener handleGestureListener = new OnGesturePerformedListener() {
		
		@Override
		public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
			// TODO Auto-generated method stub
			ArrayList<Prediction> presictions = gestureLib.recognize(gesture);
			if(presictions.size() > 0){
				Prediction prediction = presictions.get(0);
				if(prediction.score > 1.0){
					
					Intent mapActivityIntent = new Intent(getApplicationContext(), MapActivity.class);
					mapActivityIntent.putExtra(MapActivity.ACCIDENT_DATA, accidentData);
					startActivity(mapActivityIntent);
					
				}
			}
		}
	};
	
	private void createInterface() {
		btnMapListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mapActivityIntent = new Intent(getApplicationContext(), MapActivity.class);
				mapActivityIntent.putExtra(MapActivity.ACCIDENT_DATA, accidentData);
				startActivity(mapActivityIntent);
			}
		};
	}
	
	private void initializeVariables() {
		Intent receivedIntent = getIntent();
		try {
			accidentWithState = (AccidentWithState)receivedIntent.getSerializableExtra(ACCIDENT_WITH_STATE);
			accidentData = accidentWithState.getAccidentData();
			accidentRescueState = accidentWithState.getAccidentRescueState();
		} catch (NullPointerException e) {
			// NO DATA 
		}
	}
	
	private void initializeGUIComponents() {
		try {
			Position position = accidentData.getPosition();
			AdditionalInfo additionalInfo = accidentData.getAdditionalInfo();
			Date dateTime = accidentData.getDateTime();
			
			txtViewDate = (TextView)findViewById(R.id.txt_show_datetime);
			txtViewDate.setText(ApplicationTime.dateToString(dateTime));
			
			txtViewLongitude = (TextView)findViewById(R.id.txt_show_longitude);
			txtViewLongitude.setText(String.valueOf(position.getLongitude()));
			
			txtViewLatitude = (TextView)findViewById(R.id.txt_show_latitude);
			txtViewLatitude.setText(String.valueOf(position.getLatitude()));
			
			txtViewAccidentType = (TextView)findViewById(R.id.txt_show_type);
			txtViewAccidentType.setText(additionalInfo.getAccidentType());
			
			txtViewAmountOfDead = (TextView)findViewById(R.id.txt_show_dead);
			txtViewAmountOfDead.setText(String.valueOf(additionalInfo.getAmountOfDead()));
			
			txtViewAmountOfInjured = (TextView)findViewById(R.id.txt_show_injured);
			txtViewAmountOfInjured.setText(String.valueOf(additionalInfo.getAmountOfInjured()));
			
			txtViewMessage = (TextView)findViewById(R.id.txt_show_message);
			txtViewMessage.setText(additionalInfo.getMessage());
			
			chkboxTrafficBlocked = (CheckBox)findViewById(R.id.Yes);
			chkboxTrafficBlocked.setChecked(additionalInfo.isTrafficBlocked());
		} catch (NullPointerException e) {
			// NO DATA 
		}
		
		
		btnMap = (Button)findViewById(R.id.btn_map);
		btnMap.setOnClickListener(btnMapListener);
	}
}
