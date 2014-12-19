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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ReportStateActivity extends Activity {
	public static final String ACCIDENT_WITH_STATE = IntentExtraKeys.ACCIDENT_WITH_STATE;
	
	private BroadcastReceiver missionReportBroadcastReceiver;
	
	private Spinner spinner_state;
	private EditText editTxtMessage;
	
	private Button btnAcccept;
	private Button btnReject;
	private Button btnSubmit;
	private OnClickListener btnAccceptListener;
	private OnClickListener btnRejectListener;
	private OnClickListener btnSubmitListener;
	
	private AccidentWithState accidentWithState;
	private AccidentData accidentData;
	private AccidentRescueState accidentRescueState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_state);
		
		initializeVariables();
		createInterface();
		initializeGUIComponents();
		loadStateSpinner();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this)
			.registerReceiver((missionReportBroadcastReceiver),
				new IntentFilter(MissionReportService.BROADCAST));
	}

	@Override
	protected void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(missionReportBroadcastReceiver);
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
		
		missionReportBroadcastReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				AcknowledgeDataCollection acknowledgeDataCollection = 
						(AcknowledgeDataCollection)intent
						.getSerializableExtra(MissionReportService.ACKNOWLEDGE_DATA_COLLECTION);
				MissionReport missionReport = 
						(MissionReport)intent.getSerializableExtra(MissionReportService.MISSION_REPORT);
				onReceivedBroadcastFromMissionReport(acknowledgeDataCollection, missionReport);
			}
		};
	}
	
	private void createInterface() {
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
	
	private void initializeGUIComponents() {
		btnAcccept = (Button)findViewById(R.id.btn_accept);
		btnAcccept.setOnClickListener(btnAccceptListener);
		btnReject = (Button)findViewById(R.id.btn_reject);
		btnReject.setOnClickListener(btnRejectListener);
		btnSubmit = (Button)findViewById(R.id.btn_report_mission);
		btnSubmit.setOnClickListener(btnSubmitListener);
		
		editTxtMessage = (EditText)findViewById(R.id.editTextMessage);
	}
	
	private void loadStateSpinner() {
		// FUTURE : Load Process State from DB?
		spinner_state = (Spinner)findViewById(R.id.spiner_state_of_accident);
		
		List<RescueState> listOfRescueState = 
				RescueState.LIST_OF_RESCUE_STATE_IN_PROCESS;
		RescueStateSpinnerAdapter adapter = 
				new RescueStateSpinnerAdapter(getApplicationContext(), listOfRescueState);
		spinner_state.setAdapter(adapter);
	}
	
	private void reportMission(MissionReport missionReport) {
		Intent reportIntent = new Intent(this, MissionReportService.class);
		reportIntent.putExtra(MissionReportService.MISSION_REPORT_DATA, missionReport);
		startService(reportIntent);
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
				editTxtMessage.getText().toString());
		
		reportMission(missionReport);
	}
	
	private void onReceivedBroadcastFromMissionReport(AcknowledgeDataCollection acknowledgeDataCollection, 
			MissionReport missionReport) {
		if(missionReport.getAccidentID() == accidentData.getAccidentID()) {
			// UPDATE STATE IN GUI
			
			Toast t = Toast.makeText(this, "Rescue State Update!", Toast.LENGTH_SHORT);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
		}
	}
}
