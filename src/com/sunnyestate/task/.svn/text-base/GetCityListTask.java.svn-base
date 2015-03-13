package com.sunnyestate.task;

import com.sunnyestate.data.CityListData;
import com.sunnyestate.enums.RetError;

public class GetCityListTask extends
		BaseAsyncTask<CityListData, Void, RetError> {
	private CityListData cityList;

	@Override
	protected RetError doInBackground(CityListData... params) {
		cityList = params[0];
		RetError ret = cityList.getCityList();
		return ret;
	}

}
