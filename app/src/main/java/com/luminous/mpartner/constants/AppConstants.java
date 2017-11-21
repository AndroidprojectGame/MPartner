package com.luminous.mpartner.constants;

import android.content.Context;
import android.content.Intent;

public class AppConstants {

	public static final String SHARED_PREF_NAME = "LuminousPrefrences";
	public static final String SCAN_RESULT = "SCAN_RESULT";
	public static final String BUNDLE_SERIAL_LIST = "SERIAL_LIST";
	public static final String SCAN_RESULT_TYPE = "SCAN_RESULT_TYPE";
	public static final int ZBAR_SCANNER_REQUEST = 0;
	public static final String ERROR_INFO = "ERROR_INFO";
	public static final String EXTRA_NOTIFICATIONS = "notifications";
	public static final String EXTRA_DATE = "date";
	public static final String EXTRA_PRODUCT_DETAIL = "product_detail";
	public static final String USER_DISTRIBUTER = "DISTY";

	/****************Added by Anusha to implement GCM Push Notification ********************/
	//` Google project id
	public static final String SENDER_ID = "233628028283";
	/**
	 * Tag used on log messages.
	 */
	public static final String TAG = "AndroidHive GCM";
	public static final String DISPLAY_MESSAGE_ACTION = "com.luminous.pushnotifications.DISPLAY_MESSAGE";
	public static final String EXTRA_MESSAGE = "message";

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 *
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	public static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
	/********************End of Change*************************************/
}
