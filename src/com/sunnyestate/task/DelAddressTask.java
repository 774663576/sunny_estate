package com.sunnyestate.task;

import com.sunnyestate.data.Adress;
import com.sunnyestate.enums.RetError;

public class DelAddressTask extends BaseAsyncTask<Adress, Void, RetError> {
	private Adress address;

	@Override
	protected RetError doInBackground(Adress... params) {
		address = params[0];
		RetError ret = address.delAddress();
		return ret;
	}

}
