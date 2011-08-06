package com.ecs.android.sample.oauth.google.api.java.client.oauth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.ecs.android.sample.oauth.google.api.java.client.AndroidOauthGoogleApiJavaClient;
import com.ecs.android.sample.oauth.google.api.java.client.Constants;
import com.ecs.android.sample.oauth.google.api.java.client.Util;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.googleapis.auth.oauth.GoogleOAuthGetAccessToken;

public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

	final String TAG = getClass().getName();

	private Context context;
	private OAuthHmacSigner signer;
	private SharedPreferences prefs;
	private OAuthCredentialsResponse credentials;

	public RetrieveAccessTokenTask(Context context, OAuthHmacSigner signer,SharedPreferences prefs) {
		this.context = context;
		this.signer = signer;
		this.prefs = prefs;
	}

	/**
	 * Retrieve the oauth_verifier, and store the oauth and oauth_token_secret
	 * for future API calls.
	 */
	@Override
	protected Void doInBackground(Uri... params) {

		try {
			final Uri uri = params[0];

			String requestToken = uri.getQueryParameter("oauth_token");
			String verifier = uri.getQueryParameter("oauth_verifier");

			signer.clientSharedSecret = Constants.CONSUMER_SECRET;

			GoogleOAuthGetAccessToken accessToken = new GoogleOAuthGetAccessToken();
			accessToken.transport = Util.TRANSPORT;
			accessToken.temporaryToken = requestToken;
			accessToken.signer = signer;
			accessToken.consumerKey = Constants.CONSUMER_KEY;
			accessToken.verifier = verifier;

			credentials = accessToken.execute();
			signer.tokenSharedSecret = credentials.tokenSecret;

			final Editor edit = prefs.edit();
			edit.putString(Constants.PREF_KEY_OAUTH_TOKEN, credentials.token);
			edit.putString(Constants.PREF_KEY_OAUTH_TOKEN_SECRET,credentials.tokenSecret);
			edit.commit();

			context.startActivity(new Intent(context,AndroidOauthGoogleApiJavaClient.class));
			Log.i(TAG, "OAuth - Access Token Retrieved");

		} catch (Exception e) {
			Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
		}
		return null;
	}

}