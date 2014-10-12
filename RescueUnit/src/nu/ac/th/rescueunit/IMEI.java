package nu.ac.th.rescueunit;

import android.content.Context;
import android.telephony.TelephonyManager;

public class IMEI {
	public static String getDeviceIMEI(Context context) {
		TelephonyManager telephonyManager 
			= (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

	    String imei = telephonyManager.getDeviceId();
	    
	    return imei;
	}
}
