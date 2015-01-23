package com.sunnyestate.task;

import com.sunnyestate.data.OrderList;
import com.sunnyestate.enums.RetError;

public class GetMyOrderListTask extends
		BaseAsyncTask<OrderList, Void, RetError> {
	private OrderList list;

	@Override
	protected RetError doInBackground(OrderList... params) {
		list = params[0];
		RetError ret = list.refushData();
		return ret;
	}

}
