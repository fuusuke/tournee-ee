package com.saurav;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccountSpinnerAdapter extends BaseAdapter {

	private static final String TAG = AccountSpinnerAdapter.class.getSimpleName();
	private List<Account> accountList;
	private Context context;

	public AccountSpinnerAdapter(Context context, Account[] accounts) {
		this.context = context;
		this.accountList = getFilteredList(accounts);
	}

	private List<Account> getFilteredList(Account[] accounts) {
		List<Account> filteredList = new ArrayList<Account>();
		for (Account account : accounts) {
			if (account.name.contains("@gmail.com")) {
				Log.d(TAG, "Account Type: " + account.type);
				filteredList.add(account);
			}
		}
		return filteredList;
	}

	@Override
	public int getCount() {
		return accountList.size();
	}

	@Override
	public Object getItem(int position) {
		return accountList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			TextView textView = new TextView(context);
			textView.setText(accountList.get(position).name);
			return textView;
		} else {
			TextView textView = (TextView) (convertView);
			textView.setText(accountList.get(position).name);
			return textView;
		}
	}

}
