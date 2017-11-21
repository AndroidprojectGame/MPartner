package com.luminous.mpartner.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.luminous.mpartner.R;
import com.luminous.mpartner.constants.AppConstants;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class SimpleScannerActivity extends Activity implements
        ZBarScannerView.ResultHandler {

    private ZBarScannerView mScannerView;
    private String mSerialNumber = "";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        if (!isCameraAvailable()) {
            // Cancel request if there is no rear-facing camera.
            cancelRequest();
            return;
        }

        setContentView(R.layout.activity_simple_scanner);
        mScannerView = (ZBarScannerView) findViewById(R.id.activity_scanner_scannerview);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler
        // for scan results.
        mScannerView.startCamera(false); // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera(); // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) { // etc.)
        mSerialNumber = rawResult.getContents();
        returnBackResult();
    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void cancelRequest() {
        Intent dataIntent = new Intent();
        dataIntent.putExtra(AppConstants.ERROR_INFO, "Camera unavailable");
        setResult(Activity.RESULT_CANCELED, dataIntent);
        finish();
    }

    private void returnBackResult() {
        Intent dataIntent = new Intent();
        dataIntent.putExtra(AppConstants.SCAN_RESULT,
                mSerialNumber);
        setResult(Activity.RESULT_OK, dataIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent dataIntent = new Intent();
        dataIntent.putExtra(AppConstants.SCAN_RESULT,
                mSerialNumber);
        setResult(Activity.RESULT_OK, dataIntent);
        finish();
    }
}