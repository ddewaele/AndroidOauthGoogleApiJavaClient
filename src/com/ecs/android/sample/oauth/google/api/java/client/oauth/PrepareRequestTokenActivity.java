package com.ecs.android.sample.oauth.google.api.java.client.oauth;

import com.ecs.android.sample.oauth.google.api.java.client.Constants;
import com.google.api.client.auth.oauth.OAuthHmacSigner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Execute the OAuthRequestTokenTask to retrieve the request, and authorize the request.
 * After the request is authorized by the user, the callback URL will be intercepted here.
 * 
 */
public class PrepareRequestTokenActivity extends Activity {

	final String TAG = getClass().getName();
	private OAuthHmacSigner signer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	try {
			signer = new OAuthHmacSigner();
			signer.clientSharedSecret = Constants.CONSUMER_SECRET;        	
    	} catch (Exception e) {
    		Log.e(TAG, "Error creating consumer / provider",e);
		}
        Log.i(TAG, "Starting task to retrieve request token.");
		new OAuthRequestTokenTask(this,signer).execute();
	}

	/**
	 * Called when the OAuthRequestTokenTask finishes (user has authorized the request token).
	 * The callback URL will be intercepted here so we can fetch the token and token secret.
	 */
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent); 
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if (uri != null && uri.getScheme().equals(Constants.OAUTH_CALLBACK_SCHEME)) {
			Log.i(TAG, "Callback received : " + uri);
			Log.i(TAG, "Retrieving Access Token");
			new RetrieveAccessTokenTask(this,signer,prefs).execute(uri);
			finish();	
		}
	}
}
