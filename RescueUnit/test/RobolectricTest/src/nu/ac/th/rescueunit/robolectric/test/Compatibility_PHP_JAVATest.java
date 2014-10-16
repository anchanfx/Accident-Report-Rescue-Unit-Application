package nu.ac.th.rescueunit.robolectric.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import nu.ac.th.rescueunit.Compatibility_PHP_JAVA;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Compatibility_PHP_JAVATest {
	
	int intFalse;
	int intTrue;
	Long timeStamp;
	
	@Before
    public void setUp() throws Exception {
		intFalse = 0;
		intTrue = 1;
		timeStamp = 1411921311L;
	}
	
	@Test
	public void testConvertBooleanInPhpToJava() throws Exception {
		boolean actual1 = Compatibility_PHP_JAVA.intToBoolean(intFalse);
		boolean actual2 = Compatibility_PHP_JAVA.intToBoolean(intTrue);
		boolean expect1 = false;
		boolean expect2 = true;
		
		assertThat(actual1, equalTo(expect1));
		assertThat(actual2, equalTo(expect2));
	}
	
	public void testTimeStampInPhpToJava() {
		long actual = Compatibility_PHP_JAVA.timeStampInPhpToJava(timeStamp);
		long expect = timeStamp * 1000;
		assertThat(actual, equalTo(expect));
	}
	
	public void testTimeStampInJavaToPhp(long timeStamp) {
		long actual = Compatibility_PHP_JAVA.timeStampInJavaToPhp(timeStamp);
		long expect = timeStamp / 1000;
		assertThat(actual, equalTo(expect));
	}
}
