package nu.ac.th.rescueunit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ApplicationTime {
	public static final String TIME_ZONE = "Asia/Bangkok";
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static Date newDateInstance() {
		TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
		Date date = new Date();
		
		return date;
	}
	
	public static Date constructDate(Long timeStamp) {
		TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
		Date date = new Date(Long.valueOf(timeStamp));
		
		return date;
	}
	
	public static String dateToString(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
		SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
				DATE_FORMAT, java.util.Locale.getDefault());
		String date_to_string = dateformatyyyyMMdd.format(date);
		
		return date_to_string;
	}
}
