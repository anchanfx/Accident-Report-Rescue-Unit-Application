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
	
	@Before
    public void setUp() throws Exception {
		intFalse = 0;
		intTrue = 1;
	}
	
	@Test
	public void testConvertBooleanInPhpToJava() throws Exception {
		boolean actual1 = Compatibility_PHP_JAVA.convertBooleanInPhpToJava(intFalse);
		boolean actual2 = Compatibility_PHP_JAVA.convertBooleanInPhpToJava(intTrue);
		boolean expect1 = false;
		boolean expect2 = true;
		
		assertThat(actual1, equalTo(expect1));
		assertThat(actual2, equalTo(expect2));
	}
}
