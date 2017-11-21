package com.luminous.mpartner.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.luminous.mpartner.constants.UrlConstants;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.utils.AppSharedPrefrences;
import com.luminous.mpartner.utils.FontProvider;
import com.luminous.mpartner.utils.Navigator;
import com.luminous.mpartner.R;

public class SplashActivity extends AppCompatActivity {

    ImageView ivLogo;
    private EditText etId;
    Button btLogin;
    private String mUserId;

    private static final int REQUEST_READ_PHONE_STATE_PERMISSION = 225;  //By Anusha
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;  //By Anusha
    private String TAG;  //By Anusha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if ((AppSharedPrefrences.getInstance(this).getUserId().trim().length() > 0) && (AppSharedPrefrences.getInstance(this).getOtp().trim().length() > 0)) {
            Navigator.getInstance().navigateToActivity(this, DashboardActivity.class);
            finish();
        } else {
            prepareViews();
        }

        /**************Change by Anusha for GCM Push Notification****************************/
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    /***************************End of change********************************************/

    private void prepareViews() {
        ivLogo = (ImageView) findViewById(R.id.activity_splash_iv_logo);
        etId = (EditText) findViewById(R.id.activity_splash_et_id);
        btLogin = (Button) findViewById(R.id.activity_splash_bt_login);

        ivLogo.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        etId.setAnimation(AnimationUtils.loadAnimation(this, R.anim.view_fade_in));
        btLogin.setAnimation(AnimationUtils.loadAnimation(this, R.anim.view_fade_in));

        etId.setTypeface(FontProvider.getInstance().tfRegular);
        btLogin.setTypeface(FontProvider.getInstance().tfSemibold);
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
        mUserId = etId.getText().toString();
        mUserId = mUserId.replaceFirst("^0+(?!$)", "");  //Edited by Anusha to remove leading zeros
        if (mUserId.trim().length() > 0) {
            etId.setError(null);
            /****************Change by Anusha to get Device Reg Id for GCM Push Notification*************/
            String token = AppSharedPrefrences.getInstance(this).getRegistrationId();
            UserController userController = new UserController(this,
                    "deviceId");
            userController.updateDeviceId(mUserId, token);
            /***********End of change**************************************/
            getDataAndLoginUser();
        } else {
            etId.setError("Enter Distributer/Dealer Id");     //Edited by Anusha
        }
    }

    /**
     * Get device informations and send to user login.
     */
    private void getDataAndLoginUser() {
        /*********************Change by Anusha to support dangerous permissions in Android 6.0 version*********************/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String Imei = telephonyManager.getDeviceId();
            String deviceVersion = android.os.Build.VERSION.RELEASE;
            PackageInfo pInfo;
            String appVersion = "";
            try {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                appVersion = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            UserController userController = new UserController(this,
                    "updateOtpStatus");

            userController
                    .createOtp(mUserId, Imei, deviceVersion, "Android", appVersion);

        } else {
            Log.d(TAG, "Current app does not have READ_PHONE_STATE permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "Permission Granted");
                    //Proceed to next steps

                } else {
                    Log.e(TAG, "Permission Denied");
                }
                return;
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void deviceId(Object object) {
        //UserController userController = (UserController) object;
    }
    /******************************End of Change******************************************/

    /**
     * Validates successful data and moves user to next screen.
     */
    public void updateOtpStatus(Object object) {
        UserController userController = (UserController) object;
        if (userController.mCode.equalsIgnoreCase(UrlConstants.STATUS_SUCCESS)) {

            Toast.makeText(this, userController.mMessage, Toast.LENGTH_LONG)
                    .show();

            AppSharedPrefrences.getInstance(this).setUserId(mUserId);


            Navigator.getInstance().navigateToActivity(this, VerifyOTPActivity.class);
            finish();



        } else {
            Toast.makeText(this, userController.mMessage, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
