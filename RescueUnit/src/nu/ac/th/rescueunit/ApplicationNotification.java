package nu.ac.th.rescueunit;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class ApplicationNotification {
	
	public static void sendNotification(ApplicationNotificationParameter param) {
		Context context = param.getContext();
		
		NotificationManager notificationManager 
			= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Notification noti = new NotificationCompat.Builder(context)
			.setSmallIcon(param.getIcon())
			.setTicker(param.getTicker())
			.setContentTitle(param.getContentTitle())
			.setContentText(param.getContentText())
			.setContentIntent(param.getContentIntent())
			/** Begin Area : Need API 4.1 or higer to work! **/
			.setStyle(new NotificationCompat.BigTextStyle()
				.bigText(param.getBigText()))
	        /** End Area : Need API 4.1 or higer to work! **/
		    .setSound(param.getSound())
	    	.setVibrate(param.getVibrate())
	    	.setAutoCancel(true)
	        .build();
   
		notificationManager.notify(param.getNotificationId(), noti);
	}
}
