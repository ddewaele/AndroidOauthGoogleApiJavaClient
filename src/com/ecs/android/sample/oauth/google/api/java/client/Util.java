package com.ecs.android.sample.oauth.google.api.java.client;

import android.content.SharedPreferences;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.googleapis.json.JsonCParser;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.sample.buzz.model.BuzzActivity;
import com.google.api.client.sample.buzz.model.BuzzActivityFeed;

public class Util {
	//public static final HttpTransport TRANSPORT = newTransport(false);
	//public static final HttpTransport AUTH_TRANSPORT = newTransport(true);
	
	public static final HttpTransport TRANSPORT = new NetHttpTransport();
	
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();
	public static final String APP_DESCRIPTION = "Buzz API Java Client Sample";

//	static HttpTransport newTransport(boolean forAuth) {
//		HttpTransport result = new NetHttpTransport();
//		GoogleUtils.useMethodOverride(result);
//		GoogleHeaders headers = new GoogleHeaders();
//		headers.setApplicationName("Google-LatitudeSample/1.0");
//		result.defaultHeaders = headers;
//		if (!forAuth) {
//			JsonCParser parser = new JsonCParser();
//			parser.jsonFactory =  new JacksonFactory();
//			result.addParser(parser);
//		}
//		return result;
//	}
	
	 public static HttpRequestFactory createRequestFactory(HttpTransport transport,SharedPreferences prefs) {
		    OAuthParameters parameters = createOAuthParameters(prefs);
		    HttpRequestFactory requestFactory = transport.createRequestFactory(parameters);
		    return requestFactory;
	}
	 
//	 public static HttpRequestFactory createRequestFactory(
//			   final HttpTransport transport) {
//			   
//			  return transport.createRequestFactory(new HttpRequestInitializer() {
//			   public void initialize(HttpRequest request) {
//			    GoogleHeaders headers = new GoogleHeaders();
//			    headers.setApplicationName("Google-FusionTables/1.0");
//			    request.headers=headers;
//			    request.addParser(new CsvParser());
//			    try {
//			     authorizeTransport(request);
//			    } catch (Exception e) {
//			     e.printStackTrace();
//			    }
//			   }
//			  });
//	 }	 
//	 

	public static OAuthParameters createOAuthParameters(SharedPreferences prefs) {
		String token = prefs.getString(Constants.PREF_KEY_OAUTH_TOKEN, "");
		String secret = prefs.getString(Constants.PREF_KEY_OAUTH_TOKEN_SECRET,"");
		OAuthHmacSigner signer = new OAuthHmacSigner();
		signer.clientSharedSecret = Constants.CONSUMER_SECRET;
		signer.tokenSharedSecret = secret;
		OAuthParameters oauthParameters = new OAuthParameters();
		oauthParameters.consumerKey = Constants.CONSUMER_KEY;
		oauthParameters.signer = signer;
		oauthParameters.token = token;
		return oauthParameters;
	}

	public static String executeApiCall(SharedPreferences prefs) throws Exception {
		BuzzActivityFeed feed = BuzzActivityFeed.list(prefs);
		StringBuffer sb = new StringBuffer();
		for (BuzzActivity activity : feed.items) {
			sb.append("\n");
			sb.append("-----------------------------------------------\n");
			sb.append("Content: " + activity.object.content);
			sb.append("\n");
			sb.append("Updated: " + activity.updated);
			;
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
