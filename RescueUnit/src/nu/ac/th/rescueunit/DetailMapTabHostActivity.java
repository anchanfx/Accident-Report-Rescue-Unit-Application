package nu.ac.th.rescueunit;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class DetailMapTabHostActivity extends Activity{
	public static final String ACCIDENT_WITH_STATE = IntentExtraKeys.ACCIDENT_WITH_STATE;
	
	private TabHost mainTabHost;
	private LocalActivityManager myLocalActivityManager;
	private AccidentWithState accidentWithState;
	private AccidentData accidentData;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailmaptab);
		
		initializeVariables();
		initializeTabHost(savedInstanceState);
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
		
		mainTabHost = (TabHost)findViewById(R.id.tabhost_detailmap);
		myLocalActivityManager = new LocalActivityManager(this, false);
		mainTabHost.setup(myLocalActivityManager);
		myLocalActivityManager.dispatchCreate(savedInstanceState);
		
		
		Intent goDetail = new Intent().setClass(this, DetailActivity.class);
		goDetail.putExtra(DetailActivity.ACCIDENT_WITH_STATE, accidentWithState);
		detailSpec = mainTabHost.newTabSpec("tab1").setIndicator("Detail").setContent(goDetail);
		mainTabHost.addTab(detailSpec);
		
		Intent goMap = new Intent().setClass(this, MapActivity.class);
		goMap.putExtra(MapActivity.ACCIDENT_DATA, accidentData);
		mapSpec = mainTabHost.newTabSpec("tab2").setIndicator("Map").setContent(goMap);
		mainTabHost.addTab(mapSpec);
	}
	
}
