package nu.ac.th.rescueunit.robolectric.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import nu.ac.th.rescueunit.JSONKeys;
import nu.ac.th.rescueunit.AccidentPollingRequestData;
import nu.ac.th.rescueunit.AccidentPolllingRequestDataConverter;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AccidentPollingRequestDataConverterTest {

	private String testIMEI = "123456789";
	private AccidentPollingRequestData accidentPollingRequestData;
	
	@Before
	public void setUp() throws Exception {
		accidentPollingRequestData = new AccidentPollingRequestData(testIMEI);
	}
	
	@Test
	public void testToJSON() throws Exception {
		JSONObject actual = AccidentPolllingRequestDataConverter.toJSON(accidentPollingRequestData);
		
		// { "IMEI" : "123456789" }
		String jsonString = "{ \""+ JSONKeys.IMEI +"\" : \""+ testIMEI +"\"}";
		JSONObject expected = new JSONObject(jsonString);
		assertThat(actual.toString(), equalTo(expected.toString()));
	}
}
