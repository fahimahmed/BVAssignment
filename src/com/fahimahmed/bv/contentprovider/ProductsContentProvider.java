package com.fahimahmed.bv.contentprovider;

import static com.fahimahmed.bv.contentprovider.ProductsContract.AUTHORITY;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.fahimahmed.bv.database.DatabaseHelper;
import com.fahimahmed.bv.database.Product;


public class ProductsContentProvider extends ContentProvider {

    public static final UriMatcher URI_MATCHER = buildUriMatcher();
    public static final String PATH = "products";
    public static final int PATH_TOKEN = 100;
    public static final String PATH_FOR_ID = "products/*";
    public static final int PATH_FOR_ID_TOKEN = 200;

    // Uri Matcher for the content provider
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;
        matcher.addURI(authority, PATH, PATH_TOKEN);
        matcher.addURI(authority, PATH_FOR_ID, PATH_FOR_ID_TOKEN);
        return matcher;
    }

    // Content Provider stuff

    private DatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        Context ctx = getContext();
        dbHelper = new DatabaseHelper(ctx);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case PATH_TOKEN:
                return ProductsContract.CONTENT_TYPE_DIR;
            case PATH_FOR_ID_TOKEN:
                return ProductsContract.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported.");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            // retrieve tv shows list
            case PATH_TOKEN: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(Product.PRODUCT_TABLE);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case PATH_FOR_ID_TOKEN: {
                int tvShowId = (int)ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(Product.PRODUCT_TABLE);
                builder.appendWhere(Product.PRODUCT_ID + "=" + tvShowId);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        switch (token) {
            case PATH_TOKEN: {
                long id = db.insert(Product.PRODUCT_TABLE, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return ProductsContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            default: {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        int rowsDeleted = -1;
        switch (token) {
            case (PATH_TOKEN):
                rowsDeleted = db.delete(Product.PRODUCT_TABLE, selection, selectionArgs);
                break;
            case (PATH_FOR_ID_TOKEN):
                String tvShowIdWhereClause = Product.PRODUCT_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    tvShowIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(Product.PRODUCT_TABLE, tvShowIdWhereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // Notifying the changes, if there are any
        if (rowsDeleted != -1)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
    	 SQLiteDatabase db = dbHelper.getWritableDatabase();
         int token = URI_MATCHER.match(uri);
         int rowsUpdated = -1;
         switch (token) {
             case (PATH_TOKEN):
                 rowsUpdated = db.update(Product.PRODUCT_TABLE, values, selection, selectionArgs);
                 break;
             case (PATH_FOR_ID_TOKEN):
                 String tvShowIdWhereClause = Product.PRODUCT_ID + "=" + uri.getLastPathSegment();
                 if (!TextUtils.isEmpty(selection))
                     tvShowIdWhereClause += " AND " + selection;
                 rowsUpdated = db.update(Product.PRODUCT_TABLE, values, tvShowIdWhereClause, selectionArgs);
                 break;
             default:
                 throw new IllegalArgumentException("Unsupported URI: " + uri);
         }
         // Notifying the changes, if there are any
         if (rowsUpdated != -1)
             getContext().getContentResolver().notifyChange(uri, null);
         return rowsUpdated;
    }
}