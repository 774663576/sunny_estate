package com.sunnyestate.task;

import com.sunnyestate.data.AdressList;
import com.sunnyestate.enums.RetError;

public class GetAddressTask extends BaseAsyncTask<AdressList, Void, RetError> {
	private AdressList list;

	@Override
	protected RetError doInBackground(AdressList... params) {
		list = params[0];
		RetError ret = list.refushAddress();
		return ret;
	}

}
