package nu.ac.th.rescueunit;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabHostActivity extends Activity{
	public static final String ACCIDENT_WITH_STATE = IntentExtraKeys.ACCIDENT_WITH_STATE;
	
	private TabHost myTabHost;
	private LocalActivityManager myLocalActivityManager;
	private ApplicationDbHelper db;
	private AccidentWithState accidentWithState;
	private AccidentData accidentData;
	private AccidentRescueState accidentRescueState;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		
		initializeVariables();
		
		myTabHost = (TabHost)findViewById(R.id.tabhost_detail);
		myLocalActivityManager = new LocalActivityManager(this, false);
		myTabHost.setup(myLocalActivityManager);
		myLocalActivityManager.dispatchCreate(savedInstanceState);
		
		TabHost.TabSpec spec;
		Intent goDetail = new Intent().setClass(this, DetailActivity.class);
		goDetail.putExtra(DetailActivity.ACCIDENT_WITH_STATE, accidentWithState);
		spec = myTabHost.newTabSpec("tab1").setIndicator("Detail").setContent(goDetail);
		myTabHost.addTab(spec);
		
		Intent goMap = new Intent().setClass(this, MapActivity.class);
		goMap.putExtra(MapActivity.ACCIDENT_DATA, accidentData);
		spec = myTabHost.newTabSpec("tab2").setIndicator("Map").setContent(goMap);
		myTabHost.addTab(spec);
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
	}
}
