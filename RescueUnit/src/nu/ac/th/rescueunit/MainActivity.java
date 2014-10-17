package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.ServiceUtility.isMyServiceRunning;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
	private static final String BROADCAST_POLLING_ACCIDENT 
		= "nu.ac.th.rescueunit.android.action.broadcast.pollingaccident";
	private static final String BROADCAST_SELF_UPDATE 
		= "nu.ac.th.rescueunit.android.action.broadcast.selfupdate";
	private static final String BROADCAST_LOCATOR
		= "nu.ac.th.rescueunit.android.action.broadcast.locator";
	
	private BroadcastReceiver processIncomingAccidentServiceBroadcastReceiver;
	private AccidentPollingService_BroadcastReceiver accidentPollingService_BroadcastReceiver;
	private SelfUpdateService_BroadcastReceiver selfUpdateService_BroadcastReceiver;
	private LocatorService_BroadcastReceiver locatorService_BroadcastReceiver;
	
	private AccidentListAdapter accidentListAdapter;
	private ListView listViewAccident;
	private OnItemClickListener listViewAccidentListener;
	private ApplicationDbHelper db;
	
	private List<AccidentWithState> listOfAccidentWithState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db = new ApplicationDbHelper(this);
		
		createInterface();
		initializeVariables();
		initializeGUIComponents();
		startMainApplicationServices();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		loadAccidentList();
	}
	
	private void createInterface() {
		listViewAccidentListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
				AccidentWithState item = (AccidentWithState)listViewAccident.getItemAtPosition(position);
				Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
				intent.putExtra(DetailActivity.ACCIDENT_WITH_STATE, item);
				startActivity(intent);
			}
		};
	}
	
	private void initializeVariables() {
		listOfAccidentWithState = new ArrayList<AccidentWithState>();
		accidentListAdapter = new AccidentListAdapter(this, listOfAccidentWithState);
		
		processIncomingAccidentServiceBroadcastReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				loadAccidentList();
			}
		};
		
		LocalBroadcastManager.getInstance(this)
			.registerReceiver((processIncomingAccidentServiceBroadcastReceiver),
					new IntentFilter(ProcessIncomingAccidentService.BROADCAST));
	}
	
	private void initializeGUIComponents() {
		listViewAccident = (ListView)findViewById(R.id.listView_accident);
		listViewAccident.setOnItemClickListener(listViewAccidentListener);
		listViewAccident.setAdapter(accidentListAdapter);
	}

	private void startMainApplicationServices() {
		if(!isMyServiceRunning(AccidentPollingService.class, getApplicationContext())) {
			IntentFilter intentFilter = new IntentFilter(BROADCAST_POLLING_ACCIDENT);
			registerReceiver(accidentPollingService_BroadcastReceiver , intentFilter);
			
			Intent intent = new Intent(BROADCAST_POLLING_ACCIDENT);  
	        sendBroadcast(intent);
		}
		
		if(!isMyServiceRunning(SelfUpdateService.class, getApplicationContext())) {
			IntentFilter intentFilter = new IntentFilter(BROADCAST_SELF_UPDATE);
			registerReceiver(selfUpdateService_BroadcastReceiver , intentFilter);
			
			Intent intent = new Intent(BROADCAST_SELF_UPDATE);  
	        sendBroadcast(intent);
		}
		
		if(!isMyServiceRunning(LocatorService.class, getApplicationContext())) {
			IntentFilter intentFilter = new IntentFilter(BROADCAST_LOCATOR);
			registerReceiver(selfUpdateService_BroadcastReceiver , intentFilter);
			
			Intent intent = new Intent(BROADCAST_LOCATOR);  
	        sendBroadcast(intent);
		}
	}
	
	private void loadAccidentList() {
		listOfAccidentWithState = db.getAccidentWithState();
		accidentListAdapter.clear();
		accidentListAdapter.addAll(listOfAccidentWithState);
		accidentListAdapter.notifyDataSetChanged();
	}
}
