package com.luminous.mpartner.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.luminous.mpartner.R;
import com.luminous.mpartner.constants.AppConstants;
import com.luminous.mpartner.database.AccessCodeDatabase;
import com.luminous.mpartner.models.AccessCode;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerActivity extends Activity implements
        ZBarScannerView.ResultHandler {

    private ZBarScannerView mScannerView;
    private Vibrator mVibrator;
    private Button btScan;
    private ArrayList<String> mSerialNumbers;
    private String totalScanned = "Total Scanned : %s";
    private TextView tvQuantity;
    private AccessCodeDatabase dbAccessCode = new AccessCodeDatabase(this);
    private List<AccessCode> mAccessCodesList;
    ToggleButton btToggleFlash;
    private boolean isFlashOn;
    private TextView tvScanValue;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        if (!isCameraAvailable()) {
            // Cancel request if there is no rear-facing camera.
            cancelRequest();
            return;
        }

        setContentView(R.layout.activity_scanner);
        mScannerView = (ZBarScannerView) findViewById(R.id.activity_scanner_scannerview);
        btScan = (Button) findViewById(R.id.activity_scanner_bt_scan);
        btToggleFlash = (ToggleButton) findViewById(R.id.activity_scanner_bt_flash_toggle);
        tvQuantity = (TextView) findViewById(R.id.activity_scanner_tv_quantity);
        tvScanValue = (TextView) findViewById(R.id.activity_scanner_tv_scan_value);

        // Intializing Database
        mAccessCodesList = dbAccessCode.getAllAccesCodes();

        // Initialize views data
        mSerialNumbers = getIntent().getStringArrayListExtra(
                AppConstants.BUNDLE_SERIAL_LIST);
        tvQuantity.setText(String.format(totalScanned, mSerialNumbers.size()));
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btToggleFlash.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                isFlashOn = isChecked;
                mScannerView.stopCamera();
                mScannerView.setResultHandler(ScannerActivity.this);
                mScannerView.startCamera(isChecked);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler
        // for scan results.
        mScannerView.startCamera(isFlashOn); // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera(); // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) { // etc.)
        saveSerial(rawResult.getContents());
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

    public void returnBackResult(View v) {
        Intent dataIntent = new Intent();
        dataIntent.putStringArrayListExtra(AppConstants.SCAN_RESULT,
                mSerialNumbers);
        setResult(Activity.RESULT_OK, dataIntent);
        finish();
    }

    private void saveSerial(String SerialNumber) {
        tvScanValue.setText("" + SerialNumber);
        mVibrator.vibrate(300);
        btScan.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.scan_animation));
        btScan.setText("Please wait..");
        if (mSerialNumbers.contains(SerialNumber)) {
            Toast.makeText(this, "Serial Number already Scanned",
                    Toast.LENGTH_SHORT).show();
            btScan.setText("Scan Serial No.");
            mScannerView.startCamera();
        } else {
            checkSerialNumberAndAdd(SerialNumber);
        }
    }

    private void checkSerialNumberAndAdd(String serialNumber) {

        String serialAccessCode = serialNumber.substring(3, 6);
        boolean isValidSerial = false;
        for (AccessCode accessCode : mAccessCodesList) {
            Log.d(">>>>>>>>>", accessCode.getAccessCode());
            if (accessCode.getAccessCode().contains(serialAccessCode)) {
                isValidSerial = true;
                break;
            }
        }

        if (isValidSerial) {
            mSerialNumbers.add(serialNumber);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    tvQuantity.setText(String.format(totalScanned,
                            mSerialNumbers.size()));
                    btScan.setText("Scan Serial No.");
                    mScannerView.startCamera();
                }
            }, 1000);
        } else {
            Toast.makeText(this, "Please Scan a Valid Serial Number.",
                    Toast.LENGTH_SHORT).show();
            mScannerView.startCamera();
        }
    }

    @Override
    public void onBackPressed() {
        Intent dataIntent = new Intent();
        dataIntent.putStringArrayListExtra(AppConstants.SCAN_RESULT,
                mSerialNumbers);
        setResult(Activity.RESULT_OK, dataIntent);
        finish();
    }
}