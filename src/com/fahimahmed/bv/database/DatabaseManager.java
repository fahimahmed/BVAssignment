package com.fahimahmed.bv.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

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
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			Toast.makeText(context, "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
		}

	}

	public ArrayList<Product> getAllProducts() {

		ArrayList<Product> products = new ArrayList<Product>();

		Cursor cursor = database.rawQuery("select * from "
				+ Product.PRODUCT_TABLE, null);
		System.out.println(cursor.getCount());

		while (cursor.moveToNext()) {

			Product product = new Product();
			product.id = cursor.getInt(cursor
					.getColumnIndex(Product.PRODUCT_ID));
			product.name = cursor.getString(cursor
					.getColumnIndex(Product.PRODUCT_NAME));
			product.price = cursor.getString(cursor
					.getColumnIndex(Product.PRODUCT_PRICE));
			product.quantity = cursor.getInt(cursor
					.getColumnIndex(Product.PRODUCT_QUANTITY));
			product.isEmailSent = cursor.getInt(cursor
					.getColumnIndex(Product.PRODUCT_EMAIL_SENT));
			products.add(product);
		}
		cursor.close();
		return products;

	}

}
