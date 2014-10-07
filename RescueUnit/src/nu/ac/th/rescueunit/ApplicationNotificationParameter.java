package nu.ac.th.rescueunit;

import static nu.ac.th.rescueunit.ApplicationSettings.Notification.ICON;
import static nu.ac.th.rescueunit.ApplicationSettings.Notification.SOUND;
import static nu.ac.th.rescueunit.ApplicationSettings.Notification.VIBRATE_PATTERN;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;

public class ApplicationNotificationParameter {
	private Context context;
	private String ticker;
	private String contentTitle;
	private String contentText;
	private String bigText;
	private PendingIntent contentIntent;
	private int notificationId;
	
	private int icon;
	private Uri sound;
	private long[] vibrate;
	
	public ApplicationNotificationParameter(Context context, String ticker,
			String contentTitle, String contentText, String bigText, 
			PendingIntent contentIntent, int notificationId) {
		this(context, ticker, contentTitle, contentText, bigText, contentIntent, notificationId, 
				ICON, SOUND, VIBRATE_PATTERN);
	}

	public ApplicationNotificationParameter(Context context, String ticker,
			String contentTitle, String contentText, String bigText, 
			PendingIntent contentIntent, int notificationId,
			int icon, Uri sound, long[] vibrate) {
		this.context = context;
		this.ticker = ticker;
		this.contentTitle = contentTitle;
		this.contentText = contentText;
		this.bigText = bigText;
		this.contentIntent = contentIntent;
		this.notificationId = notificationId;
		this.icon = icon;
		this.sound = sound;
		this.vibrate = vibrate;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getBigText() {
		return bigText;
	}

	public void setBigText(String bigText) {
		this.bigText = bigText;
	}

	public PendingIntent getContentIntent() {
		return contentIntent;
	}

	public void setContentIntent(PendingIntent contentIntent) {
		this.contentIntent = contentIntent;
	}
	
	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public Uri getSound() {
		return sound;
	}

	public void setSound(Uri sound) {
		this.sound = sound;
	}

	public long[] getVibrate() {
		return vibrate;
	}

	public void setVibrate(long[] vibrate) {
		this.vibrate = vibrate;
	}
}
