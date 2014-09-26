package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.ServiceUtility.isMyServiceRunning;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends Activity {
	private static final String BROADCAST_POLLING_ACCIDENT 
		= "nu.ac.th.rescueunit.android.action.broadcast.pollingaccident";
	//private BroadcastReceiver dataBroadcastReceiver;
	private AccidentPollingService_BroadcastReceiver accidentPollingService_BroadcastReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button btnTest = (Button)findViewById(R.id.btn_test);
		btnTest.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v){
				
				LayoutInflater inflater = (LayoutInflater)MainActivity
						.this.getSystemService(LAYOUT_INFLATER_SERVICE);
				View popupView = inflater.inflate(R.layout.popup_nonti, (ViewGroup)findViewById(R.id.layout_popup_nonti));
				
				final PopupWindow popupWindow = new PopupWindow(
						popupView,
						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT,
						true);
				popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
				
				final Button btnOpen = (Button)popupView.findViewById(R.id.btn_opent_accident);
				btnOpen.setOnClickListener(new Button.OnClickListener(){
					
					@Override
					public void onClick(View v){
						popupWindow.dismiss();
						Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
						startActivity(intent);
						
					}
					
				});
				
			}
		});
		
		final Button btnTestDetail = (Button)findViewById(R.id.btn_test_detail);
		btnTestDetail.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
				startActivity(intent);
			}
		});
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
	}

	@Override
	protected void onStop() {
		//TODO Auto-generated method stub
		super.onStop();
	}
}
