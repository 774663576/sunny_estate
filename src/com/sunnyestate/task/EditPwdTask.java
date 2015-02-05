package com.sunnyestate.task;

import com.sunnyestate.data.User;
import com.sunnyestate.enums.RetError;

public class EditPwdTask extends BaseAsyncTask<User, Void, RetError> {
	private String old_pwd = "";
	private String new_pwd = "";

	private User user;

	public EditPwdTask(String old_pwd, String new_pwd) {
		this.old_pwd = old_pwd;
		this.new_pwd = new_pwd;
	}

	@Override
	protected RetError doInBackground(User... params) {
		user = params[0];
		RetError ret = user.edidPwd(old_pwd, new_pwd);
		return ret;
	}
}
