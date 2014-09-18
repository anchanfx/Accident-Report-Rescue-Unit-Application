package nu.ac.th.rescueunit;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	/*
	@Override
	private void createReportPopup() {
		LayoutInflater inflater = (LayoutInflater)MainActivity
				.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		viewReport = inflater.inflate(R.layout.popup_report, (ViewGroup)findViewById(R.id.layout_report));
		
		pwindoReport = new PopupWindow(viewReport, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		
		txtViewReportMessage = (TextView)viewReport.findViewById(R.id.txtview_msg_report);
		btnClosePopup = (Button)viewReport.findViewById(R.id.btn_close_report);
		btnClosePopup.setOnClickListener(btnClosePopupListener);
	}
	*/
	private View viewReport;
	private PopupWindow pwindoReport;
	private void createPopupNonti()
	{
		LayoutInflater inflater = (LayoutInflater)MainActivity
				.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		viewReport = inflater.inflate(R.layout.popup_nonti, (ViewGroup)findViewById(R.id.layout_popup_nonti));
		
		pwindoReport = new PopupWindow(viewReport, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		
	}
	
	private OnClickListener btnTest;
	private void btnTest()
	{	
		btnTest = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				createPopupNonti();
			}
		};
	}
	
}
