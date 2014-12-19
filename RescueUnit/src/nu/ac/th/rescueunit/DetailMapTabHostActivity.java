package nu.ac.th.rescueunit;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TabHost;

public class DetailMapTabHostActivity extends Activity{
	public static final String ACCIDENT_WITH_STATE = IntentExtraKeys.ACCIDENT_WITH_STATE;
	
	private TabHost mainTabHost;
	private LocalActivityManager myLocalActivityManager;
	private AccidentWithState accidentWithState;
	private AccidentData accidentData;
	
	private static final String DETAIL_ACTIVITY = "Detail";
	private static final String MAP_ACTIVITY = "Map";
	private static final String MISSION_REPORT_ACTIVITY = "Mission Report";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailmaptab);
		
		initializeVariables();
		initializeTabHost(savedInstanceState);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	    try {
	    	myLocalActivityManager.dispatchResume();
	    } catch (Exception e) {}
	}

	@Override
	protected void onPause() {
		super.onPause();
	    try {
	    	myLocalActivityManager.dispatchPause(isFinishing());
	    } catch (Exception e) {}
	}
	
	private void initializeVariables() {
		Intent receivedIntent = getIntent();
		
		try {
			accidentWithState = (AccidentWithState)receivedIntent.getSerializableExtra(ACCIDENT_WITH_STATE);
			accidentData = accidentWithState.getAccidentData();
		} catch (NullPointerException e) {
			// NO DATA 
		}
	}
	
	private void initializeTabHost(Bundle savedInstanceState) {
		TabHost.TabSpec detailSpec;
		TabHost.TabSpec mapSpec;
		TabHost.TabSpec reportState;
		
		mainTabHost = (TabHost)findViewById(R.id.tabhost_detailmap);
		myLocalActivityManager = new LocalActivityManager(this, false);
		mainTabHost.setup(myLocalActivityManager);
		myLocalActivityManager.dispatchCreate(savedInstanceState);
		
		
		Intent goDetail = new Intent().setClass(this, DetailActivity.class);
		goDetail.putExtra(DetailActivity.ACCIDENT_WITH_STATE, accidentWithState);
		detailSpec = mainTabHost.newTabSpec(DETAIL_ACTIVITY).setIndicator(DETAIL_ACTIVITY).setContent(goDetail);
		mainTabHost.addTab(detailSpec);
		
		Intent goMap = new Intent().setClass(this, MapActivity.class);
		goMap.putExtra(MapActivity.ACCIDENT_DATA, accidentData);
		mapSpec = mainTabHost.newTabSpec(MAP_ACTIVITY).setIndicator(MAP_ACTIVITY).setContent(goMap);
		mainTabHost.addTab(mapSpec);
		
		Intent goReport = new Intent().setClass(this, ReportStateActivity.class);
		goReport.putExtra(ReportStateActivity.ACCIDENT_WITH_STATE, accidentWithState);
		reportState = mainTabHost.newTabSpec(MISSION_REPORT_ACTIVITY).setIndicator(MISSION_REPORT_ACTIVITY).setContent(goReport);
		mainTabHost.addTab(reportState);
	}
	
}
