package com.luminous.mpartner.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkStatus {
	private static Context mainContext;
	private static CheckNetworkStatus instance;

	public static CheckNetworkStatus getInstance(Context context) {
		mainContext = context;
		if (instance == null) {
			instance = new CheckNetworkStatus(mainContext);

		}

		return instance;
	}

	public CheckNetworkStatus(Context context) {
		mainContext = context;
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) mainContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if ((netInfo != null) && (netInfo.isConnected())) {
			return true;
		}
		return false;
	}

}