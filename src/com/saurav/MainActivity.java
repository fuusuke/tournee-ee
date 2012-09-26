package com.saurav;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnItemClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();

	private static final String AUTH_TOKEN_TYPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
	private String accountName;
	private String authToken;
	private SharedPreferences mPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		chooseAccount();
		super.onCreate(savedInstanceState);
	}

	private void chooseAccount() {
		try {
			mPreferences = getSharedPreferences("bond", MODE_PRIVATE);
			if (mPreferences == null || isEmpty(mPreferences)) {
				saveCredentials();
			}
		} catch (Exception e) {
			Log.e(TAG, "", e);
			saveCredentials();
		}

		// getAuthToken();
	}

	private boolean isEmpty(SharedPreferences mPreferences2) {
		final String uname = mPreferences.getString("uname", "nothing");
		if (uname.equals("nothing"))
			return true;
		else
			return false;
	}

	private void saveCredentials() {
		final Account[] accounts = AccountManager.get(this).getAccounts();

		Spinner spin = (Spinner) findViewById(R.id.spinner);

		AccountSpinnerAdapter accountSpinnerAdapter = new AccountSpinnerAdapter(this, accounts);
		spin.setAdapter(accountSpinnerAdapter);

		spin.setOnItemClickListener(this);

	}

	private void getAuthToken() {
		AccountManager.get(this).getAuthTokenByFeatures("com.google", AUTH_TOKEN_TYPE, null, this, null, null,
				new AccountManagerCallback<Bundle>() {

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

	private void setAuthToken(String authToken) {
		Log.i(TAG, "setAuthToken " + authToken);
		this.authToken = authToken;
	}

	private void setAccountName(String accountName) {
		Log.i(TAG, "setAccountName " + accountName);
		this.accountName = accountName;
	}

	private void writeToPreference(Account account) {
		mPreferences = getSharedPreferences("bond", MODE_PRIVATE);
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putString("uname", account.name);
		editor.commit();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Account account = (Account) arg0.getAdapter().getItem(arg2);
		writeToPreference(account);
	}

}
