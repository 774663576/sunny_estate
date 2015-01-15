package com.sunnyestate.data;

import android.database.sqlite.SQLiteDatabase;

public interface IData {

	public void read(SQLiteDatabase db);

	public void write(SQLiteDatabase db);

}
