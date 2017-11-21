package com.luminous.mpartner.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * This class includes fonts helper methods.
 */
public class FontProvider {

    private static FontProvider instance = null;
    public Typeface tfRegular, tfLight, tfBold, tfSemibold;
    private String PROXIMA_REGULAR = "ProximaNova-Regular.otf";
    private String PROXIMA_LIGHT = "ProximaNova-Light.otf";
    private String PROXIMA_SEMIBOLD = "ProximaNova-Semibold.otf";
    private String PROXIMA_BOLD = "ProximaNova-Bold.otf";


    //Private Constructor so that no instance can be made.
    private FontProvider() {
    }

    public static FontProvider getInstance() {
        if (instance == null) {
            instance = new FontProvider();
        }
        return instance;
    }

    /**
     * Initializing fonts.
     */
    public void init(Context context) {

        tfRegular = Typeface.createFromAsset(context.getAssets(),
                "fonts/" + PROXIMA_REGULAR);
        tfLight = Typeface.createFromAsset(context.getAssets(),
                "fonts/" + PROXIMA_LIGHT);
        tfBold = Typeface.createFromAsset(context.getAssets(),
                "fonts/" + PROXIMA_BOLD);
        tfSemibold = Typeface.createFromAsset(context.getAssets(),
                "fonts/" + PROXIMA_SEMIBOLD);

    }


}
