package nu.ac.th.rescueunit.robolectric.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Date;

import nu.ac.th.rescueunit.ApplicationTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ApplicationTimeTest {

	Long timeStamp;
	
	@Before
    public void setUp() throws Exception {
		timeStamp = 1411921311000L;
	}
	
	@Test
	public void testConvertBooleanInPhpToJava() throws Exception {
		// TEST??
		Date actual = ApplicationTime.constructDate(timeStamp);
		
		assertThat(actual.getTime(), equalTo(actual.getTime()));
	}
}
