package com.sunnyestate.task;

import com.sunnyestate.data.Adress;
import com.sunnyestate.enums.RetError;

public class SetDefaultAddressTask extends
		BaseAsyncTask<Adress, Void, RetError> {
	private Adress adress;

	@Override
	protected RetError doInBackground(Adress... params) {
		adress = params[0];
		RetError ret = adress.setDefaultAddress();
		return ret;
	}
}
