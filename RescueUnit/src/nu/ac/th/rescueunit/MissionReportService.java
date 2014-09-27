package nu.ac.th.rescueunit;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MissionReportService extends IntentService {
	public static final String MISSION_REPORT_DATA = "MISSION REPORT DATA";
	
	public MissionReportService() {
		super("MissionReportService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.v("FAXXX", "HAAAAAAAAAAAAAAAAAAAA");
	}
}
