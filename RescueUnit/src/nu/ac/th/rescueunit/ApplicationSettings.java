package nu.ac.th.rescueunit;

import android.net.Uri;
import android.provider.Settings;

public class ApplicationSettings {
	
	public static abstract class Notification {
		public static int ICON = R.drawable.ic_launcher;
		public static Uri SOUND = Settings.System.DEFAULT_NOTIFICATION_URI;
		public static final long[] VIBRATE_PATTERN = new long[]{50, 100, 50, 100, 50, 100, 50, 500};
	}
}
