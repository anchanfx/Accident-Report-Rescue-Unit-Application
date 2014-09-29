package nu.ac.th.rescueunit.robolectric.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;

import nu.ac.th.rescueunit.AccidentData;
import nu.ac.th.rescueunit.AccidentDataConverter;
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
public class AccidentDataConverterTest {

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
    	
    	testAccidentData = new AccidentData(accidentId, position, additionalInfo, dateTime);
    	
    	//long timeStamp = timeStampInJavaToPhp(dateTime.getTime());
    	/* 
    	 * "{ "AccidentData" : {
    	 * 			"AccidentID" : x,
    	 * 			"Position" : { }, 
    	 * 			"AdditionalInfo" : { },
    	 * 			"Date" : x
    	 * 	} }"
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
    							"} }";
    	jsonObj =  new JSONObject(jsonString);
    }

	@Test
	public void testFromJSON() throws Exception {
		AccidentData actual = AccidentDataConverter.fromJSON(jsonObj);
		
		AccidentData expected = testAccidentData;
		
		Long actualTimeStamp = actual.getDateTime().getTime();
		Long expectTimeStamp = testAccidentData.getDateTime().getTime();
		
		assertThat(actual.getAccidentID(), 
				equalTo(expected.getAccidentID()));
		
		assertThat(actual.getPosition().toString(), 
					equalTo(expected.getPosition().toString()));
		
		assertThat(actual.getAdditionalInfo().toString(), 
				equalTo(expected.getAdditionalInfo().toString()));
		
		assertThat(actualTimeStamp, equalTo(expectTimeStamp));
	}
}
