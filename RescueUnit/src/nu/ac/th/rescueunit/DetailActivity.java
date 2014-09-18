package nu.ac.th.rescueunit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		
		final Button btnMap = (Button)findViewById(R.id.btn_map);
		btnMap.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), MapActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
}
