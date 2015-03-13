package com.sunnyestate.task;

import com.sunnyestate.data.User;
import com.sunnyestate.enums.RetError;

public class RegisterTask extends BaseAsyncTask<User, Void, RetError> {
	private User user;
	private String code = "";

	public RegisterTask(String code) {
		this.code = code;
	}

	@Override
	protected RetError doInBackground(User... params) {
		user = params[0];
		RetError ret = user.register(code);
		return ret;
	}

}
