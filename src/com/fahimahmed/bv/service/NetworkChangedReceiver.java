package com.fahimahmed.bv.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fahimahmed.bv.util.ConnectionDetector;

public class NetworkChangedReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		ConnectionDetector connDetector = new ConnectionDetector(context);

		if (connDetector.isConnectingToInternet()) {
			Log.i("Network Info", "Netowrk Available");
			Toast.makeText(context, "Netowrk Available", Toast.LENGTH_SHORT)
					.show();
			context.startService(new Intent(context, SendEmailService.class));

		} else {
			Log.i("Network Info", "Netowrk not available!");
			Toast.makeText(context, "Netowrk Not Available", Toast.LENGTH_SHORT)
					.show();
			context.stopService(new Intent(context, SendEmailService.class));
		}
	}
}
