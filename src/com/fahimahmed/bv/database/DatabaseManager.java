package com.fahimahmed.bv.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

	private Context context;
	private static DatabaseManager mDatabaseManager;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase database;

	private DatabaseManager(Context context) {
		this.context = context;
	}

	public static DatabaseManager getInstance(Context context) {

		if (mDatabaseManager == null) {
			mDatabaseManager = new DatabaseManager(
					context.getApplicationContext());
		}
		return mDatabaseManager;

	}

	public DatabaseManager open() {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;

	}

	public synchronized void close() {
		dbHelper.close();
	}
	
	public void insertProducts(ArrayList<Product> products) {

		database.beginTransaction();
		try {
			for (int i = 0; i < products.size(); i++) {

				Product product = products.get(i);

				String query = "select * from " + Product.PRODUCT_TABLE
						+ " where id = '" + product.id + "'";

				Cursor cursor = database.rawQuery(query, null);

				if (cursor.getCount() > 0) {

				} else {
					ContentValues row = new ContentValues();

					row.put(Product.PRODUCT_ID, product.id);
					row.put(Product.PRODUCT_NAME, product.name);
					row.put(Product.PRODUCT_PRICE, product.price);
					row.put(Product.PRODUCT_QUANTITY, product.quantity);
					row.put(Product.PRODUCT_EMAIL_SENT, product.isEmailSent);

					database.insert(Product.PRODUCT_TABLE, null, row);
				}

				cursor.close();

			}
			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}

	}

}
