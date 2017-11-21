package com.luminous.mpartner.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.luminous.mpartner.R;

/**
 * Handles all the navigation in the application.
 */
public class Navigator {

    private static Navigator instance;

    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }



    public void navigateToActivity(Activity activity, Class<?> toClass) {
        Intent intent = new Intent(activity, toClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }


    public void navigateToActivityWithData(Activity activity, Class<?> toClass, Bundle bundle) {
        Intent intent = new Intent(activity, toClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}
