package nu.ac.th.rescueunit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SelfUpdateService extends Service {
	private Thread thread;
	
	public static String imei;
	
	@Override
	public void onCreate() {
		super.onCreate();
		imei = IMEI.getDeviceIMEI(this);	
	}



	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
 