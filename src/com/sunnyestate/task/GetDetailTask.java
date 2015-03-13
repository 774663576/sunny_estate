package com.sunnyestate.task;

import com.sunnyestate.data.CategoryDataDetail;
import com.sunnyestate.enums.RetError;

public class GetDetailTask extends
		BaseAsyncTask<CategoryDataDetail, Void, RetError> {
	private CategoryDataDetail detail;

	@Override
	protected RetError doInBackground(CategoryDataDetail... params) {
		detail = params[0];
		RetError ret = detail.refushDetailByID();
		return ret;
	}

}
