package nu.ac.th.rescueunit.robolectric.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import nu.ac.th.rescueunit.JSONKeys;
import nu.ac.th.rescueunit.PollAccidentData;
import nu.ac.th.rescueunit.PolllAccidentDataConverter;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class PollAccidentDataConverterTest {

	private String testIMEI = "123456789";
	private PollAccidentData pollAccidentData;
	
	@Before
	public void setUp() throws Exception {
		pollAccidentData = new PollAccidentData(testIMEI);
	}
	
	@Test
	public void testToJSON() throws Exception {
		JSONObject actual = PolllAccidentDataConverter.toJSON(pollAccidentData);
		
		// { "IMEI" : "123456789" }
		String jsonString = "{ \""+ JSONKeys.IMEI +"\" : \""+ testIMEI +"\"}";
		JSONObject expected = new JSONObject(jsonString);
		assertThat(actual.toString(), equalTo(expected.toString()));
	}
}
