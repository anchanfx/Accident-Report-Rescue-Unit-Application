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
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
/*
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
*/
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity{
	public static final String ACCIDENT_WITH_STATE = IntentExtraKeys.ACCIDENT_WITH_STATE;
	
	private BroadcastReceiver missionReportBroadcastReceiver;
	private ApplicationDbHelper db;
	
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
	private Button btnAcccept;
	private Button btnReject;
	private Button btnSubmit;
	private OnClickListener btnMapListener;
	private OnClickListener btnAccceptListener;
	private OnClickListener btnRejectListener;
	private OnClickListener btnSubmitListener;
	
	private AccidentWithState accidentWithState;
	private AccidentData accidentData;
	private AccidentRescueState accidentRescueState;
	private AccidentRescueState newAccidentRescueState;
	
	private Spinner spinner_state;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		/*
		TabHost tabHost=(TabHost)findViewById(R.id.tabhost);
		tabHost.setup();
		
		TabHost.TabSpec tabSpec=tabHost.newTabSpec("tab1");
		tabSpec.setContent(R.id.tab1);
		tabSpec.setIndicator("Detail");
		tabHost.addTab(tabSpec);
		
		tabSpec=tabHost.newTabSpec("tab2");
		tabSpec.setContent(R.id.tab2);
		tabSpec.setIndicator("Map");
		tabHost.addTab(tabSpec);
		*/
		
		createInterface();
		initializeVariables();
		initializeGUIComponents();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		loadStateSpinner();
		// Restrict State for current State
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
		
		btnAccceptListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submitStatus(RescueState.ACCEPT);
			}
		};
		
		btnRejectListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submitStatus(RescueState.REJECT);
			}
		};
		
		btnSubmitListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RescueState rescueState = (RescueState)spinner_state.getSelectedItem();
				submitStatus(rescueState.getCode());
			}
		};
	}
	
	private void initializeVariables() {
		db = new ApplicationDbHelper(this);
		Intent receivedIntent = getIntent();
		
		try {
			accidentWithState = (AccidentWithState)receivedIntent.getSerializableExtra(ACCIDENT_WITH_STATE);
			accidentData = accidentWithState.getAccidentData();
			accidentRescueState = accidentWithState.getAccidentRescueState();
		} catch (NullPointerException e) {
			// NO DATA 
		}
		
		missionReportBroadcastReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				AcknowledgeDataCollection acknowledgeDataCollection = 
						(AcknowledgeDataCollection)intent
						.getSerializableExtra(MissionReportService.ACKNOWLEDGE_DATA_COLLECTION);
				onAcknowledgeReceive(acknowledgeDataCollection);
			}
		};
		
		LocalBroadcastManager.getInstance(this)
			.registerReceiver((missionReportBroadcastReceiver),
					new IntentFilter(MissionReportService.BROADCAST));
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
		
		btnMap = (Button)findViewById(R.id.btn_map);
		btnMap.setOnClickListener(btnMapListener);
		btnAcccept = (Button)findViewById(R.id.btn_accept);
		btnAcccept.setOnClickListener(btnAccceptListener);
		btnReject = (Button)findViewById(R.id.btn_reject);
		btnReject.setOnClickListener(btnRejectListener);
		btnSubmit = (Button)findViewById(R.id.btn_submit_state);
		btnSubmit.setOnClickListener(btnSubmitListener);
		
		spinner_state = (Spinner)findViewById(R.id.spinner_state_accident);
	}
	
	private void loadStateSpinner() {
		// FUTURE : Load Process State from DB?
		List<RescueState> listOfRescueState = 
				RescueState.LIST_OF_RESCUE_STATE_IN_PROCESS;
		RescueStateSpinnerAdapter adapter = 
				new RescueStateSpinnerAdapter(getApplicationContext(), listOfRescueState);
		spinner_state.setAdapter(adapter);
	}
	
	private void submitStatus(int rescueState) {
		Date assignDate = accidentRescueState.getAssignDateTime();
		Date date = ApplicationTime.newDateInstance();
		
		MissionReport missionReport = new MissionReport(
				IMEI.getDeviceIMEI(this),
				accidentData.getAccidentID(), 
				rescueState, 
				assignDate,
				date, 
				"");
		newAccidentRescueState = new AccidentRescueState(assignDate, date, rescueState);
		
		reportMission(missionReport);
		// LOCK? waiting for submition
	}
	
	private void reportMission(MissionReport missionReport) {
		Intent reportIntent = new Intent(this, MissionReportService.class);
		reportIntent.putExtra(MissionReportService.MISSION_REPORT_DATA, missionReport);
		startService(reportIntent);
	}
	
	private void onAcknowledgeReceive(AcknowledgeDataCollection acknowledgeDataCollection) {
		saveRescueState(accidentData.getAccidentID(), newAccidentRescueState);
		accidentRescueState = newAccidentRescueState;
		// Restrict State for current State
		// Release LOCK?
		Toast t = Toast.makeText(this, "Mission Report Submit Successful", Toast.LENGTH_SHORT);
		t.setGravity(Gravity.CENTER, 0, 0);
		t.show();
	}
	
	private void saveRescueState(int accidentId, AccidentRescueState accidentRescueState) {
		db.addAccidentRescueState(accidentId, accidentRescueState);
	}
}
