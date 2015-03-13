package com.sunnyestate.task;

import com.sunnyestate.data.User;
import com.sunnyestate.enums.RetError;

public class UpdateVersionTask extends BaseAsyncTask<User, Void, RetError> {
	private User user;
	private String phoneType = "";

	public UpdateVersionTask(String phoneType) {
		this.phoneType = phoneType;
	}

	@Override
	protected RetError doInBackground(User... params) {
		user = params[0];
		RetError ret = user.updateVersion(phoneType);
		return ret;
	}

}
