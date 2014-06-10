package com.fahimahmed.bv.util;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
 
public class ConnectionDetector {
 
    private static Context _context;
 
    public ConnectionDetector(Context context){
        this._context = context;
    }
 
    //Checking for all possible Internet providers
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
    
    public  void connectionAlert(){
    	AlertDialog.Builder alert = new AlertDialog.Builder(
				_context);
		alert.setTitle("No Internet!");
		alert.setMessage("Do you want to open Internet Settings?");
		alert.setCancelable(true);
		alert.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
			

						Intent internetSettingIntent = new Intent(
								android.provider.Settings.ACTION_WIFI_SETTINGS);
						_context.startActivity(internetSettingIntent);

					}
				});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						

					}
				});

		alert.show();
    }
}
