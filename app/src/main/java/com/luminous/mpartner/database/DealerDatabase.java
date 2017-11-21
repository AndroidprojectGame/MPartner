package com.luminous.mpartner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.luminous.mpartner.models.Dealer;

import java.util.ArrayList;


public class DealerDatabase extends SQLiteOpenHelper {

    // database version
    private static final int DB_VERSION = 1;
    // database name
    private static final String DB_NAME = "DealerDB";
    private static final String TABLE_ORDER = "DealerTable";
    private static final String ORDER_ID = "id";
    private static final String ORDER_DEALER_ID = "DealerId";
    private static final String ORDER_DEALER_NAME = "DealerName";

    private static final String[] COLUMNS = {ORDER_ID, ORDER_DEALER_ID,
            ORDER_DEALER_NAME};

    public DealerDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_ORDER_TABLE = "CREATE TABLE " + TABLE_ORDER + " ( "
                + ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_DEALER_ID + " TEXT, " + ORDER_DEALER_NAME + " TEXT )";
        db.execSQL(CREATE_ORDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop books table if already exists
        db.execSQL("DROP TABLE IF EXISTS DealerTable");
        this.onCreate(db);

    }

    public void insertDealers(ArrayList<Dealer> dealers) {
        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();

        for (Dealer dealer : dealers) {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(ORDER_DEALER_ID, dealer.getDealerCode());
            values.put(ORDER_DEALER_NAME, dealer.getDealerName());

            // insert book
            db.insert(TABLE_ORDER, null, values);
        }

        // close database transaction
        db.close();
    }

    public Dealer readDealer(int id) {
        // get reference of the BookDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get book query
        Cursor cursor = db.query(
                TABLE_ORDER, // a. table
                COLUMNS, " id = ?", new String[]{String.valueOf(id)}, null,
                null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        Dealer dealer = new Dealer(cursor.getString(1), cursor.getString(2));
        // close database transaction
        db.close();
        return dealer;
    }

    public ArrayList<Dealer> getDealerList() {
        ArrayList<Dealer> dealerList = new ArrayList<Dealer>();

        // select book query
        String query = "SELECT  * FROM " + TABLE_ORDER;

        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        Dealer dealer = null;
        if (cursor.moveToFirst()) {
            do {
                dealer = new Dealer(cursor.getString(1), cursor.getString(2));
                // Add book to books
                dealerList.add(dealer);
            } while (cursor.moveToNext());
        }

        // close database transaction
        db.close();

        return dealerList;
    }

    public void deleteAllDealers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER, null, null);
        db.close();
    }

}
