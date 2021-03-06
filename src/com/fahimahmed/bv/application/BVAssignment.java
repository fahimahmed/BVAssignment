package com.fahimahmed.bv.application;

import android.app.Application;

import com.fahimahmed.bv.database.DatabaseManager;

public class BVAssignment extends Application {

	private DatabaseManager dbManager;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		dbManager = DatabaseManager.getInstance(this);
		dbManager.open();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (dbManager != null)
			dbManager.close();
		super.onTerminate();
	}

}
