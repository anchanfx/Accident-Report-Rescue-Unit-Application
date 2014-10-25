package nu.ac.th.rescueunit;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabHostDetailActivity extends TabActivity{
	public static final String ACCIDENT_WITH_STATE = IntentExtraKeys.ACCIDENT_WITH_STATE;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhostdetail);
		
		TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		TabSpec tab1 = tabHost.newTabSpec("Detail");
		TabSpec tab2 = tabHost.newTabSpec("Map");
		
		tab1.setIndicator("Tab1");
		tab1.setContent(new Intent(this, DetailActivity.class));
		
		tab2.setIndicator("Tab2");
		tab2.setContent(new Intent(this, MapActivity.class));
		
		tabHost.addTab(tab1);
		tabHost.addTab(tab2);
	}
}
