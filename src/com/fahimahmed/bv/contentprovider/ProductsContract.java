package com.fahimahmed.bv.contentprovider;

import android.net.Uri;

/**
 * Holds the API to the content provider
 *
 * Created by Udini on 7/2/13.
 */
public class ProductsContract {

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.bv.products";
    public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.bv.products";

    public static final String AUTHORITY = "com.bv.products.provider";
    // content://<authority>/<path to type>
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/products");

    public static final String PRODUCT_ID = "id";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_QUANTITY = "quantity";
    public static final String PRODUCT_EMAIL_SENT = "isEmailSent";
}
