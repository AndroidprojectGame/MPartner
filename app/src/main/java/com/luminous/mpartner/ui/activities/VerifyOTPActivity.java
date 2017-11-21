package com.luminous.mpartner.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.luminous.mpartner.constants.UrlConstants;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.utils.AppSharedPrefrences;
import com.luminous.mpartner.utils.FontProvider;
import com.luminous.mpartner.utils.Navigator;
import com.luminous.mpartner.R;

public class VerifyOTPActivity extends AppCompatActivity {

    private EditText etCode;
    TextView tvLabel;
    Button btValidate;
    private String otpPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        prepareViews();
    }

    private void prepareViews() {
        etCode = (EditText) findViewById(R.id.activity_verify_et_id);
        tvLabel = (TextView) findViewById(R.id.activity_verify_tv_label);
        btValidate = (Button) findViewById(R.id.activity_verify_bt_login);

        tvLabel.setTypeface(FontProvider.getInstance().tfRegular);
        etCode.setTypeface(FontProvider.getInstance().tfRegular);
        btValidate.setTypeface(FontProvider.getInstance().tfSemibold);
    }

    public void openContactUsScreen(View v) {
        Navigator.getInstance().navigateToActivity(this, ContactUsActivity.class);
    }

    public void openFb(View v) {
        openLink(getString(R.string.url_fb));
    }

    public void openTwitter(View v) {
        openLink(getString(R.string.url_twitter));
    }

//    public void openBlog(View v) {
//        openLink(getString(R.string.url_blog));
//    }

    public void openYoutube(View v) {
        openLink(getString(R.string.url_youtube));
    }

//    public void openGPlus(View v) {
//        openLink(getString(R.string.url_gplus));
//    }

    public void openWebpage(View v) {
        openLink(getString(R.string.url_web));
    }

    private void openLink(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void attemptLogin(View v) {
        otpPin = etCode.getText().toString();
        if (otpPin.trim().length() > 0) {
            etCode.setError(null);
            getDataAndValidateOtp();
        } else {
            etCode.setError("Enter Code");
        }
    }

    private void getDataAndValidateOtp() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String Imei = telephonyManager.getDeviceId();
        String deviceVersion = android.os.Build.VERSION.RELEASE;
        UserController userController = new UserController(this,
                "updateOtpStatus");
        userController
                .validateOtp(AppSharedPrefrences.getInstance(this).getUserId(), Imei, deviceVersion, "Android", otpPin);
    }


    public void updateOtpStatus(Object object) {
        UserController userController = (UserController) object;
        if (userController.mCode.equalsIgnoreCase(UrlConstants.STATUS_SUCCESS)) {

            Toast.makeText(this, userController.mMessage, Toast.LENGTH_LONG)
                    .show();

            AppSharedPrefrences.getInstance(this).setOtp(otpPin);
            Navigator.getInstance().navigateToActivity(this, DashboardActivity.class);
            finish();

        } else {
            Toast.makeText(this, userController.mMessage, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
