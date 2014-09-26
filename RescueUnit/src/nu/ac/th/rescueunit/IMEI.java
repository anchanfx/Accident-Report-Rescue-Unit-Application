package nu.ac.th.rescueunit;

import android.content.Context;
import android.telephony.TelephonyManager;

public class IMEI {
	private static final String DEFAULT = "UNKNOWN";
		
	private String imei;
	
	public IMEI(){
		this(DEFAULT);
	}
	
	public IMEI(String imei) {
		this.imei = imei;
	}
	
	public String getImei(){
		return imei;
	}
	
	public void setImei(String imei){
		this.imei = imei;
	}
	
	public static String getDeviceIMEI(Context context) {
		TelephonyManager telephonyManager 
			= (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

	    String imei = telephonyManager.getDeviceId();
	    
	    return imei;
	}
}
