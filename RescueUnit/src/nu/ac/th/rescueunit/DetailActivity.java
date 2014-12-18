package nu.ac.th.rescueunit;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
	
	private AccidentWithState accidentWithState;
	private AccidentData accidentData;
	private AccidentRescueState accidentRescueState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initializeVariables();
		createInterface();
		initializeGUIComponents();
	}
	
	private void createInterface() {
		
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
			chkboxTrafficBlocked.setEnabled(false);
		} catch (NullPointerException e) {
			// NO DATA 
		}
	}
}
