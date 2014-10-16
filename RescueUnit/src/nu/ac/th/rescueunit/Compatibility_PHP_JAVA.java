package nu.ac.th.rescueunit;

public class Compatibility_PHP_JAVA {
	
	public static boolean intToBoolean(int booleanInt) {
		return booleanInt == 1;
	}
	
	public static Long timeStampInPhpToJava(Long timeStamp) {
		return timeStamp*1000;
	}
	
	public static Long timeStampInJavaToPhp(Long timeStamp) {
		return timeStamp/1000;
	}
}
