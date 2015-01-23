package com.sunnyestate.task;

import com.sunnyestate.data.User;
import com.sunnyestate.enums.RetError;

public class FreedBackTask extends BaseAsyncTask<User, Void, RetError> {
	private String content = "";
	private User user;

	public FreedBackTask(String content) {
		this.content = content;
	}

	@Override
	protected RetError doInBackground(User... params) {
		user = params[0];
		RetError ret = user.freedBack(content);
		return ret;
	}
}
