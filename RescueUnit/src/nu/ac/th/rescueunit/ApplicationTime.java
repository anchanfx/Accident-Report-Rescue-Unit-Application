package nu.ac.th.rescueunit;

import java.util.Date;
import java.util.TimeZone;

public class ApplicationTime {
	public static final String TIME_ZONE = "UTC";
	
	public static Date constructDate(Long timeStamp) {
		TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
		Date date = new Date(Long.valueOf(timeStamp));
		
		return date;
	}
}
