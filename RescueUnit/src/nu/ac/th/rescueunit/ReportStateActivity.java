package nu.ac.th.rescueunit;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;

public class ReportStateActivity extends Activity {
	
	private Spinner spinner_state;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_state);
		loadStateSpinner();
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
}
