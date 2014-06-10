package com.fahimahmed.bv.service;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fahimahmed.bv.database.DatabaseManager;
import com.fahimahmed.bv.database.Product;
import com.fahimahmed.bv.util.ConnectionDetector;
import com.fahimahmed.bv.util.GMailSender;

public class SendEmailService extends Service {

	private TimerTask timerTask;
	private Timer timer;
	private ArrayList<Product> unsyncedProducts;
	private DatabaseManager database;
	private ConnectionDetector connDetector;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		System.out.println("Service Started!!");

		database = DatabaseManager.getInstance(getApplicationContext());
		connDetector = new ConnectionDetector(getApplicationContext());

		timerTask = new TimerTask() {

			@Override
			public void run() {

				unsyncedProducts = database.getUnsyncedProducts();

				System.out.println("UnSynced Products::"
						+ unsyncedProducts.size());

				for (int i = 0; i < unsyncedProducts.size(); i++) {
					System.out.println("Doing Job For Product Id: "
							+ unsyncedProducts.get(i).id);

					if (connDetector.isConnectingToInternet()) {
						try {
							GMailSender sender = new GMailSender(
									"project.geeft@gmail.com", "geeftgeeft");
							sender.sendMail("BV Assignemnt Auto Email Sending",
									"Product Synced. Produce ID: "
											+ unsyncedProducts.get(i).id,
									"project.geeft@gmail.com",
									"fahim.ahmed1988@gmail.com");
						} catch (Exception e) {
							Log.e("SendMail", e.getMessage(), e);
						}

						database.UpdateProductAfterSyncing(unsyncedProducts
								.get(i).id);
					}
				}

			}
		};

		timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 1000, 2000);

		return Service.START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("Service Destroyed!!");
	}

}
