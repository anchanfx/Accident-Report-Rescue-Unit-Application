package nu.ac.th.rescueunit.robolectric.test;

import static nu.ac.th.rescueunit.Compatibility_PHP_JAVA.timeStampInPhpToJava;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Date;

import nu.ac.th.rescueunit.JSONKeys;
import nu.ac.th.rescueunit.MissionReport;
import nu.ac.th.rescueunit.MissionReportConverter;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MissionReportConverterTest {
	private String testIMEI = "123456789";
	private int testID = 7;
	private int testRescueState = 1;
	private String testMessage = "TESTMSG";
	private Long phpTimeStamp = 1411921311L;
	private MissionReport missonReport;
	
	@Before
	public void setUp() throws Exception {
		Date dateTime = new Date(new Long(timeStampInPhpToJava(phpTimeStamp)));
		missonReport = new MissionReport(testIMEI, testID, testRescueState, 
				dateTime, dateTime, testMessage);
	}
	
	@Test
	public void testToJSON() throws Exception {
		JSONObject actual = MissionReportConverter.toJSON(missonReport);
		
		// { "MissionReport": { "IMEI":"", "AccidentID":x, "RescueState":x, "Message":"" } }
		String jsonString = "{ \""+ JSONKeys.JSON_MISSION_REPORT + 
							"\" : {\""+ JSONKeys.IMEI + "\" : \"" + testIMEI + 
							"\", \"" + JSONKeys.ACCIDENT_ID + "\" : " + testID +
							", \"" + JSONKeys.RESCUE_STATE + "\" : " + testRescueState +
							", \"" + JSONKeys.ASSIGN_DATE_TIME + "\" : " + phpTimeStamp +
							", \"" + JSONKeys.DATE_TIME + "\" : " + phpTimeStamp +
							", \"" + JSONKeys.MESSAGE + "\" : \"" + testMessage +
							"\" }}";
		JSONObject expected = new JSONObject(jsonString);
		assertThat(actual.toString(), equalTo(expected.toString()));
	}
}
