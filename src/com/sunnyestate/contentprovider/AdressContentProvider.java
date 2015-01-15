package com.sunnyestate.contentprovider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.sunnyestate.db.DBUtils;
import com.sunnyestate.utils.Utils;

public class AdressContentProvider extends ContentProvider {

	private static HashMap<String, String> sMyCirclesProjectionMap;

	private static final int CIRCLES = 1;
	private static final int CIRCLES_ID = 2;

	private static final UriMatcher sUriMatcher;

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(AdressProvider.AdressColumns.TABLE_NAME);

		switch (sUriMatcher.match(uri)) {
		case CIRCLES:
			qb.setProjectionMap(sMyCirclesProjectionMap);
			break;

		case CIRCLES_ID:
			qb.setProjectionMap(sMyCirclesProjectionMap);
			qb.appendWhere(AdressProvider.AdressColumns._ID + "="
					+ uri.getPathSegments().get(1));
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// Get the database and run the query
		SQLiteDatabase db = DBUtils.getDBsa(1);
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, null);

		// Tell the cursor what uri to watch, so it knows when its source data
		// changes
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case CIRCLES:
			return AdressProvider.CONTENT_TYPE;
		case CIRCLES_ID:
			return AdressProvider.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		// Validate the requested uri
		if (sUriMatcher.match(uri) != CIRCLES) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		// Make sure that the fields are all set
		if (values.containsKey(AdressProvider.AdressColumns.ID) == false) {
			values.put(AdressProvider.AdressColumns.ID, "");
		}

		if (values.containsKey(AdressProvider.AdressColumns.ADRESS) == false) {
			values.put(AdressProvider.AdressColumns.ADRESS, 0);
		}

		SQLiteDatabase db = DBUtils.getDBsa(2);
		long rowId = db.insert(AdressProvider.AdressColumns.TABLE_NAME,
				AdressProvider.AdressColumns.ID, values);
		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(
					AdressProvider.AdressColumns.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		}
		Utils.print("error::::::::::::::::::::" + uri);
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = DBUtils.getDBsa(2);
		int count;
		switch (sUriMatcher.match(uri)) {
		case CIRCLES:
			count = db.delete(AdressProvider.AdressColumns.TABLE_NAME, where,
					whereArgs);
			break;

		case CIRCLES_ID:
			String noteId = uri.getPathSegments().get(1);
			count = db.delete(AdressProvider.AdressColumns.TABLE_NAME,
					AdressProvider.AdressColumns._ID
							+ "="
							+ noteId
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase db = DBUtils.getDBsa(2);
		int count;
		switch (sUriMatcher.match(uri)) {
		case CIRCLES:
			count = db.update(AdressProvider.AdressColumns.TABLE_NAME, values,
					where, whereArgs);
			break;

		case CIRCLES_ID:
			String noteId = uri.getPathSegments().get(1);
			count = db.update(AdressProvider.AdressColumns.TABLE_NAME, values,
					AdressProvider.AdressColumns._ID
							+ "="
							+ noteId
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		// 这个地方的members要和CircleMemberColumns.CONTENT_URI中最后面的一个Segment一致
		sUriMatcher.addURI(AdressProvider.AUTHORITY, "madress", CIRCLES);
		sUriMatcher.addURI(AdressProvider.AUTHORITY, "madress/#", CIRCLES_ID);

		sMyCirclesProjectionMap = new HashMap<String, String>();
		sMyCirclesProjectionMap.put(AdressProvider.AdressColumns._ID,
				AdressProvider.AdressColumns._ID);
		sMyCirclesProjectionMap.put(AdressProvider.AdressColumns.ID,
				AdressProvider.AdressColumns.ID);
		sMyCirclesProjectionMap.put(AdressProvider.AdressColumns.ADRESS,
				AdressProvider.AdressColumns.ADRESS);
		sMyCirclesProjectionMap.put(AdressProvider.AdressColumns.ADRESS_DETAIL,
				AdressProvider.AdressColumns.ADRESS_DETAIL);
		sMyCirclesProjectionMap.put(AdressProvider.AdressColumns.CELLPHONE,
				AdressProvider.AdressColumns.CELLPHONE);
		sMyCirclesProjectionMap.put(AdressProvider.AdressColumns.CODE,
				AdressProvider.AdressColumns.CODE);
		sMyCirclesProjectionMap.put(
				AdressProvider.AdressColumns.DEFAULT_ADRESS,
				AdressProvider.AdressColumns.DEFAULT_ADRESS);
		sMyCirclesProjectionMap.put(AdressProvider.AdressColumns.NAME,
				AdressProvider.AdressColumns.NAME);
	}
}
