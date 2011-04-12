package com.ecs.android.sample.oauth.google.api.java.client;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ecs.android.sample.oauth.google.api.java.client.oauth.PrepareRequestTokenActivity;

public class AndroidOauthGoogleApiJavaClient extends Activity {

	private SharedPreferences prefs;
	private TextView textView; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		//Util.setUpTransport();
		Util.setupAuthorizer(prefs);
		

		Button launchOauth = (Button) findViewById(R.id.btn_launch_oauth);
		Button clearCredentials = (Button) findViewById(R.id.btn_clear_credentials);

		textView = (TextView) findViewById(R.id.response_code);
		
		// Launch the OAuth flow to get an access token required to do authorized API calls.
		// When the OAuth flow finishes, we redirect to this Activity to perform the API call.
		launchOauth.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent().setClass(v.getContext(),
						PrepareRequestTokenActivity.class));
			}
		});

		// Clearing the credentials and performing an API call to see the unauthorized message.
		clearCredentials.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				clearCredentials();
				performApiCall();
			}

		});
		
		// Performs an authorized API call.
		performApiCall();

	}
	
	/**
	 * Clears our credentials (token and token secret) from the shared preferences.
	 * We also setup the authorizer (without the token).
	 * After this, no more authorized API calls will be possible.
	 */
    private void clearCredentials() {
		final Editor edit = prefs.edit();
		edit.remove(Constants.PREF_KEY_OAUTH_TOKEN);
		edit.remove(Constants.PREF_KEY_OAUTH_TOKEN_SECRET);
		edit.commit();
		Util.setupAuthorizer(prefs);
	}
	
    /**
     * Performs an authorized API call.
     */
	private void performApiCall() {
		String output=null;
		try {
			output = Util.executeApiCall();
			textView.setText(output);
		} catch (Exception ex) {
			ex.printStackTrace();
			textView.setText("Error occured : " + ex.getMessage());
		}
	}

}