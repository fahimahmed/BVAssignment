package com.fahimahmed.bv.syncadapter;

import java.util.ArrayList;

import com.fahimahmed.bv.contentprovider.ProductsContract;
import com.fahimahmed.bv.database.DatabaseManager;
import com.fahimahmed.bv.database.Product;
import com.fahimahmed.bv.util.ConnectionDetector;
import com.fahimahmed.bv.util.Constants;
import com.fahimahmed.bv.util.GMailSender;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

public class ProductSyncAdapter extends AbstractThreadedSyncAdapter {

	private final Context context;

	public ProductSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		this.context = context;
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		
		Log.i("", "Sync Adapter Called");
		
		 DatabaseManager database = DatabaseManager.getInstance(context);
		 ConnectionDetector connDetector = new ConnectionDetector(context);

		 ArrayList<Product> unsyncedProducts = database.getUnsyncedProducts();
		 
		 Log.i("", "Unsynced Products : "+ unsyncedProducts.size());
		 
			for (int i = 0; i < unsyncedProducts.size(); i++) {
				System.out.println("Doing Job For Product Id: "
						+ unsyncedProducts.get(i).id);

				if (connDetector.isConnectingToInternet()) {
					try {
						GMailSender sender = new GMailSender(
								Constants.SENDER_EMAIL, Constants.SENDER_PASSWORD);
						sender.sendMail("BV Assignemnt Auto Email Sending",
								"Product Synced. Produce ID: "
										+ unsyncedProducts.get(i).id,
								Constants.SENDER_EMAIL,
								Constants.RECEIVER_EMAIL);
					} catch (Exception e) {
						Log.e("SendMail", e.getMessage(), e);
					}
					
					try{
//					database.UpdateProductAfterSyncing(unsyncedProducts
//							.get(i).id);
						ContentValues cv = new ContentValues();
						cv.put(Product.PRODUCT_EMAIL_SENT, 1);
						cv.put(Product.PRODUCT_NAME, unsyncedProducts.get(i).name);
						cv.put(Product.PRODUCT_PRICE, unsyncedProducts.get(i).price);
						cv.put(Product.PRODUCT_QUANTITY, unsyncedProducts.get(i).quantity);
						context.getContentResolver().update(ProductsContract.CONTENT_URI, cv, Product.PRODUCT_ID + "=?",
							new String[] { String.valueOf(unsyncedProducts.get(i).id) });
					}catch(Exception e){
						Log.e("Update DB", e.getMessage(), e);
					}
				}
			}
	}
}
