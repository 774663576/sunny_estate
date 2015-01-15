package com.sunnyestate.data;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

public class ShoppingCarList extends AbstractData {
	private List<ShoppingCar> lists = new ArrayList<ShoppingCar>();

	public List<ShoppingCar> getLists() {
		return lists;
	}

	public void setLists(List<ShoppingCar> lists) {
		this.lists = lists;
	}

	@Override
	public void read(SQLiteDatabase db) {

	}
}
