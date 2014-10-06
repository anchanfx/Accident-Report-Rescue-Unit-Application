package nu.ac.th.rescueunit;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AccidentPollingService_BroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent(context, AccidentPollingService.class);
		context.startService(serviceIntent);
	}
}
