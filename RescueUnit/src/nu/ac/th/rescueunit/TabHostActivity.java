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
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		
		myTabHost = (TabHost)findViewById(R.id.tabhost_detail);
		myLocalActivityManager = new LocalActivityManager(this, false);
		myTabHost.setup(myLocalActivityManager);
		myLocalActivityManager.dispatchCreate(savedInstanceState);
		
		TabHost.TabSpec spec;
		Intent goDetail = new Intent().setClass(this, DetailActivity.class);
		spec = myTabHost.newTabSpec("tab1").setIndicator("Detail").setContent(goDetail);
		myTabHost.addTab(spec);
		
		Intent goMap = new Intent().setClass(this, MapActivity.class);
		spec = myTabHost.newTabSpec("tab2").setIndicator("Map").setContent(goMap);
		myTabHost.addTab(spec);
	}
}
