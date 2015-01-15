package com.sunnyestate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.sunnyestate.MyApplation;

public class DataBaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "sunnyestate";
	private static DataBaseHelper instance;
	private static final int DATABASE_VERSION = 1;

	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public static DataBaseHelper getInstance() {
		return getInstance(MyApplation.getInstance());
	}

	public static DataBaseHelper getInstance(Context context) {
		// String uid = SharedUtils.getUid();
		return getInstance(context, "");
	}

	public static DataBaseHelper getInstance(Context context, String postfix) {
		if (instance == null) {
			instance = new DataBaseHelper(context, DATABASE_NAME + postfix,
					null, DATABASE_VERSION);

		}
		return instance;
	}

	public static void setIinstanceNull() {
		instance = null;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createDB(db);
	}

	private void createDB(SQLiteDatabase db) {
		db.execSQL("create table IF NOT EXISTS "
				+ Const.SHOPPING_CAR_TABLE_NAME + "( "
				+ Const.SHOPPING_CAR_TABLE_STRUCTURE + " )");
		db.execSQL("create table IF NOT EXISTS " + Const.ADRESS_TABLE_NAME
				+ "( " + Const.ADRESS_TABLE_STRUCTURE + " )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
