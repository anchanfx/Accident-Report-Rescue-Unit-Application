package nu.ac.th.rescueunit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SelfUpdateService extends Service {
	private Thread thread;
	private SelfUpdateThreadListener threadListener;
	
	private SelfUpdateData selfUpdateData;
	private static int UPDATE_INTERVAL = 15;
	
	@Override
	public void onCreate() {
		super.onCreate();
		initialize();
	}
	
	private void initialize() {
		selfUpdateData = new SelfUpdateData(new Position(),
				new RescueUnitStatus(), IMEI.getDeviceIMEI(this));
		
		threadListener = new SelfUpdateThreadListener() {
			
			@Override
			public void onUpdateData(AcknowledgeDataCollection acknowledgeDataCollection) {
				//TODO self update successful, do something with ack
			}
		};
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		thread = new Thread(new SelfUpdate(
				threadListener, UPDATE_INTERVAL, selfUpdateData, new TCP_IP()));
		thread.start();
		return START_STICKY;
	}


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
 