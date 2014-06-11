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

				ContentValues row = new ContentValues();

				row.put(Product.PRODUCT_NAME, product.name);
				row.put(Product.PRODUCT_PRICE, product.price);
				row.put(Product.PRODUCT_QUANTITY, product.quantity);
				row.put(Product.PRODUCT_EMAIL_SENT, product.isEmailSent);

				database.insert(Product.PRODUCT_TABLE, null, row);
			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();

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

	public ArrayList<Product> getUnsyncedProducts() {

		ArrayList<Product> products = new ArrayList<Product>();

		Cursor cursor = database.rawQuery("select * from "
				+ Product.PRODUCT_TABLE + " where "
				+ Product.PRODUCT_EMAIL_SENT + " =0", null);

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

	public void UpdateProductAfterSyncing(int product_id) {
		String query = "update " + Product.PRODUCT_TABLE + " set "
				+ Product.PRODUCT_EMAIL_SENT + " = 1 where "
				+ Product.PRODUCT_ID + " = " + product_id;
		System.out.println(query);
		database.execSQL(query);
	}

	public void deleteProduct(int product_id) {
		String query = "delete from " + Product.PRODUCT_TABLE + " where "
				+ Product.PRODUCT_ID + " = " + product_id;
		System.out.println(query);
		database.execSQL(query);
	}

	public boolean updateProduct(Product product) {

		ContentValues cv = new ContentValues();
		cv.put(Product.PRODUCT_NAME, product.name);
		cv.put(Product.PRODUCT_PRICE, product.price);
		cv.put(Product.PRODUCT_QUANTITY, product.quantity);
		cv.put(Product.PRODUCT_EMAIL_SENT, product.isEmailSent);

		boolean flag = false;
		try {
			database.update(Product.PRODUCT_TABLE, cv, Product.PRODUCT_ID+"='" + product.id + "'",
					null);
			flag = true;
		} catch (Exception exception) {
			flag = false;
		}
		
		return flag;
	}

}
