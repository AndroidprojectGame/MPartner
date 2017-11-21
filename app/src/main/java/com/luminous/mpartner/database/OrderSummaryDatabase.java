package com.luminous.mpartner.database;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.luminous.mpartner.models.Secondary;

public class OrderSummaryDatabase extends SQLiteOpenHelper {

	// database version
	private static final int DB_VERSION = 1;
	// database name
	private static final String DB_NAME = "OrderDB";
	private static final String TABLE_ORDER = "OrderTable";
	private static final String ORDER_ID = "id";
	private static final String ORDER_DEALER_ID = "DealerId";
	private static final String ORDER_DEALER_NAME = "DealerName";
	private static final String ORDER_DEALER_ADDRESS = "DealerAddress";
	private static final String ORDER_DEALER_QTY = "Quantity";
	private static final String ORDER_DEALER_SERIALS = "Serials";
	private static final String ORDER_DATE = "Date";
	private static final String ORDER_TIME = "Time";

	private static final String[] COLUMNS = { ORDER_ID, ORDER_DEALER_ID,
			ORDER_DEALER_NAME, ORDER_DEALER_QTY, ORDER_DEALER_SERIALS,
			ORDER_DEALER_ADDRESS };

	public OrderSummaryDatabase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create book table
		String CREATE_ORDER_TABLE = "CREATE TABLE " + TABLE_ORDER + " ( "
				+ ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ ORDER_DEALER_ID + " TEXT, " + ORDER_DEALER_NAME + " TEXT, "
				+ ORDER_DEALER_QTY + " TEXT, " + ORDER_DEALER_SERIALS
				+ " TEXT, " + ORDER_DATE + " TEXT, " + ORDER_TIME + " TEXT, "
				+ ORDER_DEALER_ADDRESS + " TEXT )";
		db.execSQL(CREATE_ORDER_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// drop books table if already exists
		db.execSQL("DROP TABLE IF EXISTS books");
		this.onCreate(db);

	}

	public void insertOrder(Secondary order) {
		// get reference of the BookDB database
		SQLiteDatabase db = this.getWritableDatabase();

		// make values to be inserted
		ContentValues values = new ContentValues();
		values.put(ORDER_DEALER_ID, order.getDealerCode());
		values.put(ORDER_DEALER_NAME, order.getDealerName());
		values.put(ORDER_DEALER_QTY, order.getQuantity());
		values.put(ORDER_DEALER_SERIALS, order.getSerialNumbers());
		values.put(ORDER_DATE, order.getDate());
		values.put(ORDER_TIME, order.getTime());
		values.put(ORDER_DEALER_ADDRESS, order.getDealerAddress());

		// insert book
		db.insert(TABLE_ORDER, null, values);

		// close database transaction
		db.close();
	}

	public void updateOrder(Secondary order) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ORDER_DEALER_ID, order.getDealerCode());
		values.put(ORDER_DEALER_NAME, order.getDealerName());
		values.put(ORDER_DEALER_QTY, order.getQuantity());
		values.put(ORDER_DEALER_SERIALS, order.getSerialNumbers());
		values.put(ORDER_DATE, order.getDate());
		values.put(ORDER_TIME, order.getTime());
		values.put(ORDER_DEALER_ADDRESS, order.getDealerAddress());

		int i = db.update(TABLE_ORDER, values, ORDER_ID + "=?",
				new String[] { order.getId() + "" });
		db.close();
	}

	public Secondary readOrder(int id) {
		// get reference of the BookDB database
		SQLiteDatabase db = this.getReadableDatabase();

		// get book query
		Cursor cursor = db.query(
				TABLE_ORDER, // a. table
				COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null,
				null, null, null);

		// if results !=null, parse the first one
		if (cursor != null)
			cursor.moveToFirst();

		Secondary secondary = new Secondary();
		secondary.setId(cursor.getString(0));
		secondary.setDealerCode(cursor.getString(1));
		secondary.setDealerName(cursor.getString(2));
		secondary.setQuantity(cursor.getString(3));
		secondary.setSerialNumbers(cursor.getString(4));
		secondary.setDate(cursor.getString(5));
		secondary.setTime(cursor.getString(6));
		secondary.setDealerAddress(cursor.getString(7));
		db.close();
		return secondary;
	}

	public List<Secondary> getAllOrders() {
		List<Secondary> secondaryList = new LinkedList<Secondary>();

		// select book query
		String query = "SELECT  * FROM " + TABLE_ORDER;

		// get reference of the BookDB database
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// parse all results
		Secondary secondary = null;
		if (cursor.moveToFirst()) {
			do {
				secondary = new Secondary();
				secondary.setId(cursor.getString(0));
				secondary.setDealerCode(cursor.getString(1));
				secondary.setDealerName(cursor.getString(2));
				secondary.setQuantity(cursor.getString(3));
				secondary.setSerialNumbers(cursor.getString(4));
				secondary.setDate(cursor.getString(5));
				secondary.setTime(cursor.getString(6));
				secondary.setDealerAddress(cursor.getString(7));

				// Add book to books
				secondaryList.add(secondary);
			} while (cursor.moveToNext());
		}
		
		db.close();
		return secondaryList;
	}

	// Deleting single contact
	public void deleteOrder(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ORDER, ORDER_ID + " = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

}
