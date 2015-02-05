package com.sunnyestate.task;

import com.sunnyestate.data.User;
import com.sunnyestate.enums.RetError;

public class GetUserInfoTask extends BaseAsyncTask<User, Void, RetError> {
	private User user;

	@Override
	protected RetError doInBackground(User... params) {
		user = params[0];
		RetError ret = user.getUserInfo();
		return ret;
	}
}
