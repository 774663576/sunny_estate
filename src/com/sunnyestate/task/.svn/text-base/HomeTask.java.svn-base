package com.sunnyestate.task;

import com.sunnyestate.data.HomeData;
import com.sunnyestate.enums.RetError;

public class HomeTask extends BaseAsyncTask<HomeData, Void, RetError> {
	private HomeData home_data;

	@Override
	protected RetError doInBackground(HomeData... params) {
		home_data = params[0];
		RetError ret = home_data.refush();
		return ret;
	}

}
