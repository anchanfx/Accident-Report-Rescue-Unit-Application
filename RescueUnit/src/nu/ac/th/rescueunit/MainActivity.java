package nu.ac.th.rescueunit;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends Activity {

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
				
				PopupWindow popupWindow = new PopupWindow(
						popupView,
						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT,
						true);
				popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
			}
		});
	}

}
