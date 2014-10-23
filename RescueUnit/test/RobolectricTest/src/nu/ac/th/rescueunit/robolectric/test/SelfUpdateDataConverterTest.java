package nu.ac.th.rescueunit.robolectric.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Date;

import nu.ac.th.rescueunit.JSONKeys;
import nu.ac.th.rescueunit.MissionReport;
import nu.ac.th.rescueunit.MissionReportConverter;
import nu.ac.th.rescueunit.Position;
import nu.ac.th.rescueunit.RescueUnitStatus;
import nu.ac.th.rescueunit.SelfUpdate;
import nu.ac.th.rescueunit.SelfUpdateData;
import nu.ac.th.rescueunit.SelfUpdateDataConverter;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SelfUpdateDataConverterTest {
	private String testIMEI = "123456789";
	private double testLatitude = 7.123456;
	private double testLongitude = 6.543217;
	private boolean testOnline = true;
	private boolean testAvailable = false;
	private SelfUpdateData selfUpdateData;
	
	@Before
	public void setUp() throws Exception {
		selfUpdateData = new SelfUpdateData(new Position(testLatitude, testLongitude), 
				new RescueUnitStatus(testOnline, testAvailable), testIMEI);
	}
	
	@Test
	public void testToJSON() throws Exception {
		JSONObject actual = SelfUpdateDataConverter.toJSON(selfUpdateData);
		
		// { "RescueUnitData": { "IMEI":"", "AccidentID":x, "RescueState":x, "Message":"" } }
		String jsonString = "{ \""+ JSONKeys.JSON_RESCUE_UNIT_DATA + 
							"\" : {\""+ JSONKeys.IMEI + "\" : \"" + testIMEI + 
							"\", \"" + JSONKeys.JSON_POSITION + "\" : " +
							"{ \"" + JSONKeys.LATITUDE + "\" : " + testLatitude +
							", \"" + JSONKeys.LONGITUDE + "\" : " + testLongitude +
							"}, \"" + JSONKeys.JSON_RESCUE_STATUS + "\" : " +
							"{ \"" + JSONKeys.STATUS_ONLINE + "\" : " + testOnline +
							", \"" + JSONKeys.STATUS_AVAILABLE + "\" : " + testAvailable +
							" }}}";
		JSONObject expected = new JSONObject(jsonString);
		assertThat(actual.toString(), equalTo(expected.toString()));
	}
}
