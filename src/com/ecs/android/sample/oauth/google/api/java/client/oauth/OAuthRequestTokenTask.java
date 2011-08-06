package com.ecs.android.sample.oauth.google.api.java.client.oauth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.ecs.android.sample.oauth.google.api.java.client.Constants;
import com.ecs.android.sample.oauth.google.api.java.client.Util;
import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.googleapis.auth.oauth.GoogleOAuthGetTemporaryToken;

/**
 * An asynchronous task that communicates with Google to 
 * retrieve a request token.
 * (OAuthGetRequestToken)
 * 
 * After receiving the request token from Google, 
 * show a browser to the user to authorize the Request Token.
 * (OAuthAuthorizeToken)
 * 
 */
public class OAuthRequestTokenTask extends AsyncTask<Void, Void, Void> {

	final String TAG = getClass().getName();

	private OAuthHmacSigner signer;
	private Context	context;

	/**
	 * 
	 * We pass the OAuth consumer and provider.
	 * 
	 * @param 	context
	 * 			Required to be able to start the intent to launch the browser.
	 * @param 	signer
	 * 			The OAuthHmacSigner object
	 */
	public OAuthRequestTokenTask(Context context,OAuthHmacSigner signer) {
		this.context = context;
		this.signer=signer;
	}

	/**
	 * 
	 * Retrieve the OAuth Request Token and present a browser to the user to authorize the token.
	 * 
	 */
	@Override
	protected Void doInBackground(Void... params) {
		
		try {
			Log.i(TAG, "Retrieving request token from Google servers");

			GoogleOAuthGetTemporaryToken temporaryToken = new GoogleOAuthGetTemporaryToken();
			temporaryToken.transport = Util.TRANSPORT;
			temporaryToken.signer = signer;
			temporaryToken.consumerKey = Constants.CONSUMER_KEY;
			temporaryToken.scope = Constants.SCOPE;
			temporaryToken.displayName = Util.APP_DESCRIPTION;
			temporaryToken.callback = Constants.OAUTH_CALLBACK_URL;
			
			OAuthCredentialsResponse tempCredentials = temporaryToken.execute();
			signer.tokenSharedSecret = tempCredentials.tokenSecret;
			
			OAuthAuthorizeTemporaryTokenUrl authorizeUrl = new OAuthAuthorizeTemporaryTokenUrl(Constants.AUTHORIZE_URL);
			authorizeUrl.set("scope", temporaryToken.scope);
			authorizeUrl.set("domain", Constants.CONSUMER_KEY);
			authorizeUrl.set("xoauth_displayname", Util.APP_DESCRIPTION);
			authorizeUrl.temporaryToken = tempCredentials.token;
			String authorizationUrl = authorizeUrl.build();
			
			Log.i(TAG, "Popping a browser with the authorize URL : " + authorizationUrl);
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorizationUrl)).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);
			context.startActivity(intent);
			
		} catch (Exception e) {
			Log.e(TAG, "Error during OAUth retrieve request token", e);
		}

		return null;
	}

}