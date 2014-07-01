package com.fahimahmed.bv.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ProductSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static ProductSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
    	Log.e("", "Sync Service Started");
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null)
                sSyncAdapter = new ProductSyncAdapter(getApplicationContext(), true);
        }
    }

    
    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
