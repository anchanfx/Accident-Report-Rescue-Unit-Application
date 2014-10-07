package nu.ac.th.rescueunit;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpProcessor {
	
	public static HttpResponse jsonRequest(String URL, JSONObject jsonObject) throws ApplicationException {	
		HttpClient httpClient = new DefaultHttpClient();
   	    HttpPost httpPost = new HttpPost(URL);
		HttpResponse httpResponse = null;
		
   	    try {
   	    	StringEntity se = new StringEntity(jsonObject.toString(), HTTP.UTF_8);
   	   	    
   	   	    httpPost.setEntity(se);
   			httpPost.setHeader("Accept", "application/json");
   			httpPost.setHeader("Content-type", "application/json");
   			
   			httpResponse = httpClient.execute(httpPost);
   	    } catch (ClientProtocolException e) {
   	    	throw new ApplicationException();
   	    } catch (IOException e) {
   	    	throw new ServerErrorException(ServerErrorException.NO_CONNECTION);
   	    }
   	    
   	    return httpResponse;
	}
	
	public static JSONObject httpResponse_to_JSONObject(HttpResponse httpResponse) throws ApplicationException {
		String jsonString = "";
		JSONObject jsonObject = null;	
		
		try {
			jsonString = EntityUtils.toString(httpResponse.getEntity());
			jsonObject = new JSONObject(jsonString);
		} catch (ParseException e) {			
			throw new ApplicationException();
		} catch (IOException e) {			
			throw new ApplicationException();
		} catch (JSONException e) {			
			throw new ServerErrorException(ServerErrorException.NO_RESPOND);
		}
			
		return jsonObject;
	}
}
