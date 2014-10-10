package nu.ac.th.rescueunit.robolectric.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;

import nu.ac.th.rescueunit.AccidentData;
import nu.ac.th.rescueunit.AccidentPollingData;
import nu.ac.th.rescueunit.AccidentPollingDataConverter;
import nu.ac.th.rescueunit.AdditionalInfo;
import nu.ac.th.rescueunit.JSONKeys;
import nu.ac.th.rescueunit.Position;
import static nu.ac.th.rescueunit.Compatibility_PHP_JAVA.*;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AccidentPollingDataConverterTest {

	private AccidentPollingData testAccidentPollingData;
	private AccidentData testAccidentData;
	private Position position;
	private AdditionalInfo additionalInfo;
	private JSONObject jsonObj;

	
	private int accidentId = 7;
	private int latitude = 12;
	private int longitude = 34;
	private String accidentType = "TESTTYPE";
	private int amountOfInjured = 10;
	private int amountOfDead = 100;
	private int trafficBlocked = 1;
	private String message = "TESTMSG";
	
    @Before
    public void setUp() throws Exception {
    	position = new Position(latitude, longitude);
    	additionalInfo 
    		= new AdditionalInfo(accidentType, amountOfInjured, amountOfDead, true, message);
    	
    	 //2014-09-28 16:21:51
    	Long phpTimeStamp = 1411921311L;
    	Date dateTime = new Date(new Long(timeStampInPhpToJava(phpTimeStamp)));
    	Date serverDateTime = new Date(new Long(timeStampInPhpToJava(phpTimeStamp)));
    	Date assignDateTime = new Date(new Long(timeStampInPhpToJava(phpTimeStamp)));
    	
    	boolean resolve = false;
    	
    	testAccidentData = 
    			new AccidentData(accidentId, position, additionalInfo, dateTime, serverDateTime, resolve);
    	testAccidentPollingData =
    			new AccidentPollingData(testAccidentData, assignDateTime);
    	
    	//long timeStamp = timeStampInJavaToPhp(dateTime.getTime());
    	/* 
    	 * "{ "AccidentData" : {
    	 * 			"AccidentID" : x,
    	 * 			"Position" : { }, 
    	 * 			"AdditionalInfo" : { },
    	 * 			"Date" : x,
    	 * 			"ServerDate" : x,
    	 * 			"Resolve" : x
    	 * 	},"AssignDateTime": x }"
		*/
    	String jsonString = "{ \"" + JSONKeys.JSON_ACCIDENT_DATA + 
    							"\" : {\"" + JSONKeys.ACCIDENT_ID + "\" : " + accidentId + 
    							", \"" + JSONKeys.JSON_POSITION +
    							"\" : {" +
    							"\"" + JSONKeys.LATITUDE + "\" : " + latitude +
    							", \"" + JSONKeys.LONGITUDE + "\" : " + longitude +
    							"}," +
    							"\"" + JSONKeys.JSON_ADDITIONAL_INFO +
    							"\" : {" +
    							"\"" + JSONKeys.ACCIDENT_TYPE + "\" : \"" + accidentType +
    							"\", \"" + JSONKeys.AMOUNT_OF_INJURED + "\" : " + amountOfInjured +
    							", \"" + JSONKeys.AMOUNT_OF_DEAD + "\" : " + amountOfDead +
    							", \"" + JSONKeys.TRAFFIC_BLOCKED + "\" : " + trafficBlocked +
    							", \"" + JSONKeys.MESSAGE + "\" : \"" + message +
    							"\"}, \"" + JSONKeys.DATE_TIME + "\" : " + phpTimeStamp +
    							", \"" + JSONKeys.SERVER_DATE_TIME + "\" : " + phpTimeStamp +
    							", \"" + JSONKeys.RESOLVE + "\" : " + phpTimeStamp +
    							"}, \"" + JSONKeys.ASSIGN_DATE_TIME + "\" : " + phpTimeStamp +"}";
    	jsonObj =  new JSONObject(jsonString);
    }

	@Test
	public void testFromJSON() throws Exception {
		AccidentPollingData actualPollingData = AccidentPollingDataConverter.fromJSON(jsonObj);
		AccidentData actual = actualPollingData.getAccidentData();
		AccidentData expected = testAccidentPollingData.getAccidentData();
		
		Long actualTimeStamp = actual.getDateTime().getTime();
		Long expectTimeStamp = testAccidentData.getDateTime().getTime();
		Long actualServerTimeStamp = actual.getServerDateTime().getTime();
		Long expectServerTimeStamp = testAccidentData.getServerDateTime().getTime();
		boolean actualResolve = actual.isResolve();
		boolean expectResolve = expected.isResolve();
		Long actualAssignTimeStamp = actualPollingData.getAssignDate().getTime();
		Long expectAssignTimeStamp = testAccidentPollingData.getAssignDate().getTime();
		
		assertThat(actual.getAccidentID(), 
				equalTo(expected.getAccidentID()));
		
		assertThat(actual.getPosition().toString(), 
					equalTo(expected.getPosition().toString()));
		
		assertThat(actual.getAdditionalInfo().toString(), 
				equalTo(expected.getAdditionalInfo().toString()));
		
		assertThat(actualTimeStamp, equalTo(expectTimeStamp));
		assertThat(actualServerTimeStamp, equalTo(expectServerTimeStamp));
		assertThat(actualResolve, equalTo(expectResolve));
		assertThat(actualAssignTimeStamp, equalTo(expectAssignTimeStamp));
	}
}
