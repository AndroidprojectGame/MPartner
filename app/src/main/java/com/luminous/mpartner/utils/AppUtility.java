package com.luminous.mpartner.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AppUtility {

    public static final String PROFILE_PIC_NAME = "PROFILE_PIC.jpg";

    public static String convertToStringSerials(ArrayList<String> serials) {
        StringBuilder stringSerials = new StringBuilder();
        for (String serial : serials) {
            stringSerials.append("," + serial);
        }
        return stringSerials.toString().replaceFirst(",", "");
    }

    public static ArrayList<String> convertToArrayListSerials(String serials) {
        ArrayList<String> serialList = new ArrayList<String>();
        String[] serialsArray = serials.split(",");
        for (String serial : serialsArray) {
            serialList.add(serial);
        }
        return serialList;
    }

    public static Bitmap getBitmap(Context context, Uri imgUri) {

        String selectedImagePath = getRealPathFromURI(context, imgUri);
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(selectedImagePath, options);

    }

    public static String profileImgsaveToInternalSorage(Context context, Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, PROFILE_PIC_NAME);

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return directory.getAbsolutePath();
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getCurrentDateInFormat() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formatedDate = formatter.format(today);

        return formatedDate;
    }

}
