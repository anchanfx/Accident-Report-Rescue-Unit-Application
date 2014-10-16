package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.ServiceUtility.isMyServiceRunning;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

public class MainActivity extends Activity {
	private static final String BROADCAST_POLLING_ACCIDENT 
		= "nu.ac.th.rescueunit.android.action.broadcast.pollingaccident";
	private static final String BROADCAST_SELF_UPDATE 
	= "nu.ac.th.rescueunit.android.action.broadcast.selfupdate";
	
	//private BroadcastReceiver dataBroadcastReceiver;
	private AccidentPollingService_BroadcastReceiver accidentPollingService_BroadcastReceiver;
	private SelfUpdateService_BroadcastReceiver selfUpdateService_BroadcastReceiver;
	
	private ListView listViewAccident;
	private OnItemClickListener listViewAccidentListener;
	private ApplicationDbHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createInterface();
		db = new ApplicationDbHelper(this);
		listViewAccident = (ListView)findViewById(R.id.listView_accident);
		listViewAccident.setOnItemClickListener(listViewAccidentListener);
		
		List<AccidentWithState> listOfAccidentWithState = db.getAccidentWithState();
		AccidentListAdapter adapter = new AccidentListAdapter(this, listOfAccidentWithState);
		listViewAccident.setAdapter(adapter);
		
		final Button btnTestSend = (Button)findViewById(R.id.btn_test_send);
		btnTestSend.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				
			}
		});
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
	
	@Override
	protected void onStart() {
		super.onStart();
		
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
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
}
