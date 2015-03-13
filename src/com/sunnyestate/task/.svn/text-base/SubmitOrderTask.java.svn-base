package com.sunnyestate.task;

import java.util.ArrayList;
import java.util.List;

import com.sunnyestate.data.OrderData;
import com.sunnyestate.data.ShoppingCar;
import com.sunnyestate.enums.RetError;

public class SubmitOrderTask extends BaseAsyncTask<OrderData, Void, RetError> {
	private OrderData order;
	private List<ShoppingCar> lists = new ArrayList<ShoppingCar>();

	public SubmitOrderTask(List<ShoppingCar> lists) {
		this.lists = lists;
	}

	@Override
	protected RetError doInBackground(OrderData... params) {
		order = params[0];
		RetError ret = order.sumitOrder(lists);
		return ret;
	}

}
