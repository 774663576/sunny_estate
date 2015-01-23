package com.sunnyestate.task;

import com.sunnyestate.data.OrderData;
import com.sunnyestate.enums.RetError;

public class DelOrderTask extends BaseAsyncTask<OrderData, Void, RetError> {
	private OrderData order;

	@Override
	protected RetError doInBackground(OrderData... params) {
		order = params[0];
		RetError ret = order.delOrder();
		return ret;
	}

}
