package nu.ac.th.rescueunit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class DetailActivity extends Activity{
	public static final String ACCIDENT_DATA = IntentExtraKeys.ACCIDENT_DATA;
	
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
	
	AccidentData accidentData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		createInterface();
		initializeVariables();
		initializeGUIComponents();
	}
	
	
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
			accidentData = (AccidentData)receivedIntent.getSerializableExtra(ACCIDENT_DATA);
		} catch (NullPointerException e) {
			// NO DATA 
		}
	}
	
	private void initializeGUIComponents() {
		try {
			Position position = accidentData.getPosition();
			AdditionalInfo additionalInfo = accidentData.getAdditionalInfo();
			
			txtViewDate = (TextView)findViewById(R.id.txt_show_datetime);
			
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
