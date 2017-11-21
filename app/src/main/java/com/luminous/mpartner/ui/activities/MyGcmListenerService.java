/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*****************Created by Anusha for GCM Push Notification*************************************/

package com.luminous.mpartner.ui.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.luminous.mpartner.R;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    int badgeCount = 1;

  /*  final String HOME_PACKAGE_SONY = "com.sonyericsson.home";
    final String HOME_PACKAGE_SAMSUNG = "com.sec.android.app.launcher";
    final String HOME_PACKAGE_LG = "com.lge.launcher2";
    final String HOME_PACKAGE_HTC = "com.htc.launcher";
    final String HOME_PACKAGE_ANDROID = "com.android.launcher";
    final String HOME_PACKAGE_APEX = "com.anddoes.launcher";
    final String HOME_PACKAGE_ADW = "org.adw.launcher";
    final String HOME_PACKAGE_ADW_EX = "org.adwfreak.launcher";
    final String HOME_PACKAGE_NOVA = "com.teslacoilsw.launcher";  */

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        int num = (int) System.currentTimeMillis();

        /*********** Change by Anusha for displaying Notification count*******************/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
         //ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4
        if (prefs.getInt("Count", 0) != 0) {
            badgeCount = prefs.getInt("Count", 0);
        }
      /*  String classPath = "com.luminous.mpartner.ui.activities.MyGcmListenerService";

        ContentValues cv = new ContentValues();
        cv.put("package", getApplicationContext().getPackageName());
        cv.put("class", classPath);
        cv.put("badgecount", badgeCount);
        getApplicationContext().getContentResolver().insert(Uri.parse(HOME_PACKAGE_SAMSUNG), cv);*/

        try{
        ShortcutBadger.with(getApplicationContext()).count(badgeCount); //for 1.1.3
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        badgeCount = badgeCount+1;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Count", badgeCount);
        editor.commit();
        // ShortcutBadger.applyCount(this, badgeCount); //for 1.1.4
        /***************End of change********************/

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, num, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))  //Inserted by Anusha
                .setSmallIcon(R.drawable.ic_launcher_small)
                .setContentTitle("Luminous")
                .setContentText(message)
               // .setNumber(count)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(message));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(num, notificationBuilder.build());

    }
}
