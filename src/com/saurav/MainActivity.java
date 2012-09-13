package com.saurav;

import java.io.IOException;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private static final String AUTH_TOKEN_TYPE = "";
	// private GoogleCredential credential = new GoogleCredential();
	private String accountName;
	private String authToken;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		chooseAccount();
	}

	private void chooseAccount() {
		AccountManager.get(this).getAuthTokenByFeatures(GoogleAccountManager.ACCOUNT_TYPE, AUTH_TOKEN_TYPE, null,
				MainActivity.this, null, null, new AccountManagerCallback<Bundle>() {

					public void run(AccountManagerFuture<Bundle> future) {
						Bundle bundle;
						try {
							bundle = future.getResult();
							setAccountName(bundle.getString(AccountManager.KEY_ACCOUNT_NAME));
							setAuthToken(bundle.getString(AccountManager.KEY_AUTHTOKEN));

							// do something with auth token

						} catch (OperationCanceledException e) {
							Log.e(TAG, e.getMessage(), e);
						} catch (AuthenticatorException e) {
							Log.e(TAG, e.getMessage(), e);
						} catch (IOException e) {
							Log.e(TAG, e.getMessage(), e);
						}
					}

				}, null);
	}

	private void setAuthToken(String string) {
		this.authToken = string;
	}

	private void setAccountName(String string) {
		this.accountName = string;
	}

}
