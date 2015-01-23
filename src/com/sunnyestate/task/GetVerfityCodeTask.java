package com.sunnyestate.task;

import com.sunnyestate.data.User;
import com.sunnyestate.enums.RetError;

public class GetVerfityCodeTask extends BaseAsyncTask<User, Void, RetError> {
	private User user;

	@Override
	protected RetError doInBackground(User... params) {
		user = params[0];
		RetError ret = user.getVerfityCodeError();
		return ret;
	}
}
