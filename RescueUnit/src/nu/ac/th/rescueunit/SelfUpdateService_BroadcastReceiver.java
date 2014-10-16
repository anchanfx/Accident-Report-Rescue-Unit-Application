package nu.ac.th.rescueunit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SelfUpdateService_BroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent(context, SelfUpdateService.class);
		context.startService(serviceIntent);
	}

}
