package com.ecs.android.sample.oauth.google.api.java.client;


public class Constants {

	public static final String CONSUMER_KEY 	= "anonymous";
	public static final String CONSUMER_SECRET 	= "anonymous";

	public static final String SCOPE 			= "https://www.googleapis.com/auth/buzz";
	public static final String REQUEST_URL 		= "https://www.google.com/accounts/OAuthGetRequestToken";
	public static final String ACCESS_URL 		= "https://www.google.com/accounts/OAuthGetAccessToken";  
	public static final String AUTHORIZE_URL 	= "https://www.google.com/buzz/api/auth/OAuthAuthorizeToken";

	public static final String ENCODING 		= "UTF-8";
	
	public static final String	OAUTH_CALLBACK_SCHEME	= "x-oauthflow";
	public static final String	OAUTH_CALLBACK_HOST		= "callback";
	public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

	public static final String PREF_KEY_OAUTH_TOKEN_SECRET = "oauth_token_secret";
	public static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
}
