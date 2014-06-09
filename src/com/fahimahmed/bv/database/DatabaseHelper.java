package com.fahimahmed.bv.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	private static final String DB_NAME = "product_bv";
	private static final int DB_VERSION = 1;
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE IF NOT EXISTS android_metadata ('locale' TEXT DEFAULT 'en_US')");
	
		db.execSQL("create table IF NOT EXISTS " + Product.PRODUCT_TABLE
				+ " (" + Product.PRODUCT_ID +  " integer not null primary key autoincrement,"
				+ Product.PRODUCT_NAME + " text, "
				+ Product.PRODUCT_PRICE + " text, " 
				+ Product.PRODUCT_QUANTITY + " integer, " 
				+ Product.PRODUCT_EMAIL_SENT + " integer);");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
