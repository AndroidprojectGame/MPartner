package com.luminous.mpartner.service;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.luminous.mpartner.R;

import org.apache.http.client.ClientProtocolException;
import org.ksoap2.serialization.SoapObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CommonAsyncTask extends AsyncTask<String, Void, SoapObject> {

	protected Context context;
	private String methodName;
	public Dialog customProgressDialog;
	public SoapObject soapResult;
	public static boolean networkerror, servererror;
	public boolean progressbarEnable; // if 'true' progressbar is enable
	private HashMap<String, String> properties;
	private HashMap<String, ArrayList<String>> arrayProperties;

	public CommonAsyncTask(Context context, HashMap<String, String> properties) {
		this.context = context;
		this.progressbarEnable = true;
		this.properties = properties;
	}

	public CommonAsyncTask(Context context, HashMap<String, String> properties,
						   boolean progressbarEnable) {
		this.context = context;
		this.properties = properties;
		this.progressbarEnable = progressbarEnable;
	}
	
	public CommonAsyncTask(Context context, HashMap<String, String> properties, HashMap<String, ArrayList<String>> arrayProperties,
						   boolean progressbarEnable) {
		this.context = context;
		this.properties = properties;
		this.arrayProperties  = arrayProperties;
		this.progressbarEnable = progressbarEnable;
	}

	@Override
	protected void onPreExecute() {
		if (progressbarEnable) {
			//SHOW PROGRESSBAR
			showProgress();
		}
	}

	@Override
	protected SoapObject doInBackground(String... params) {

		// Cheking if device is connected to Internet
		if (CheckNetworkStatus.getInstance(context).isOnline()) {
			methodName = params[0];

			// / prepare an url for hitting the server
			RequestBuilder requestBuilder = new RequestBuilder();
			requestBuilder.setContext(context);
			requestBuilder.setMethodName(methodName);
			requestBuilder.setProperties(properties);
			requestBuilder.setArrayProperties(arrayProperties);

			try {
				// / calling to the service class which directly hits the server
				try {
					soapResult = new HttpRequestService().getRequestData(
							requestBuilder, context);
				} catch (ClassCastException e) {
					Log.e("", "==> " + e.getMessage());
				}

			} catch (ClientProtocolException e) {

				networkerror = false;
				servererror = true;
				Log.e("", "==> " + e.getMessage());
			} catch (CustomException e) {

				servererror = false;
				networkerror = true;
				Log.e("", "==> " + e.getMessage());

			} catch (IOException e) {

				networkerror = false;
				servererror = true;
				Log.e("", "==> " + e.getMessage());
			} catch (Exception e) {

				e.printStackTrace();
			}
			return soapResult;
		} else {
			soapResult = null;
			return soapResult;
		}
	}

	@Override
	protected void onPostExecute(SoapObject result) {
		dismissDialog();
		if (result == null) {
			Toast.makeText(context, "No internet connection available.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void showProgress() {
		customProgressDialog = new Dialog(context, R.style.CGDialog);
		customProgressDialog.setContentView(R.layout.indeterminate_custom_progress_dialog);
		customProgressDialog.setCancelable(false);
		customProgressDialog.show();

	}

	public void dismissDialog() {
		try {
			if ((this.customProgressDialog != null) && this.customProgressDialog.isShowing()) {
				this.customProgressDialog.dismiss();
			}
		} catch (final IllegalArgumentException e) {
			// Handle or log or ignore
		} catch (final Exception e) {
			// Handle or log or ignore
		} finally {
			this.customProgressDialog = null;
		}
	}

}