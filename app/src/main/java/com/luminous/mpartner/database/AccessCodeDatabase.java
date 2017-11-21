package com.luminous.mpartner.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.luminous.mpartner.models.AccessCode;


public class AccessCodeDatabase extends SQLiteOpenHelper {

    // database version
    private static final int DB_VERSION = 1;
    // database name
    private static final String DB_NAME = "AccessCodeDB";
    private static final String TABLE_ACCESSCODE = "AccessCodeTable";
    private static final String ACCESSCODE_ID = "id";
    private static final String ACCESSCODE_MODEL_ID = "SKUModelId";
    private static final String ACCESSCODE_CODE = "AccessCode";
    private static final String ACCESSCODE_MODEL_NAME = "SKUModelName";
    private static final String ACCESSCODE_QTY = "Quantity";

    private static final String[] COLUMNS = {ACCESSCODE_ID, ACCESSCODE_MODEL_ID,
            ACCESSCODE_CODE, ACCESSCODE_MODEL_NAME, ACCESSCODE_QTY};

    public AccessCodeDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_ORDER_TABLE = "CREATE TABLE " + TABLE_ACCESSCODE + " ( "
                + ACCESSCODE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ACCESSCODE_MODEL_ID + " TEXT, " + ACCESSCODE_CODE + " TEXT, "
                + ACCESSCODE_MODEL_NAME + " TEXT, "
                + ACCESSCODE_QTY + " TEXT )";
        db.execSQL(CREATE_ORDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop books table if already exists
        db.execSQL("DROP TABLE IF EXISTS books");
        this.onCreate(db);

    }

    public void insertAccessCode(AccessCode accessCode) {
        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(ACCESSCODE_MODEL_ID, accessCode.getSKUModelId());
        values.put(ACCESSCODE_CODE, accessCode.getAccessCode());
        values.put(ACCESSCODE_MODEL_NAME, accessCode.getSKUModelName());
        values.put(ACCESSCODE_QTY, accessCode.getTAvlQuantity());


        // insert book
        db.insert(TABLE_ACCESSCODE, null, values);

        // close database transaction
        db.close();
    }

    public AccessCode readAccessCode(int id) {
        // get reference of the BookDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get book query
        Cursor cursor = db.query(
                TABLE_ACCESSCODE, // a. table
                COLUMNS, " id = ?", new String[]{String.valueOf(id)}, null,
                null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        AccessCode accessCode = new AccessCode(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        db.close();
        return accessCode;
    }

    public ArrayList<AccessCode> getAllAccesCodes() {
        ArrayList<AccessCode> accessCodes = new ArrayList<AccessCode>();

        // select book query
        String query = "SELECT  * FROM " + TABLE_ACCESSCODE;

        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        AccessCode accessCode = null;
        if (cursor.moveToFirst()) {
            do {
                accessCode = new AccessCode(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                accessCodes.add(accessCode);
            } while (cursor.moveToNext());
        }

        db.close();
        return accessCodes;
    }

    // Deleting single contact
    public void deleteAccessCode(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCESSCODE, ACCESSCODE_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAllAccessCodes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCESSCODE, null, null);
        db.close();
    }

}
