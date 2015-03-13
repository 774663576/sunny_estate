package com.sunnyestate;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sunnyestate.data.User;
import com.sunnyestate.enums.RetError;
import com.sunnyestate.interfaces.MyEditTextWatcher;
import com.sunnyestate.interfaces.OnEditFocusChangeListener;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.FindPasswordTask;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.ToastUtil;
import com.sunnyestate.views.MyEditTextDeleteImg;

public class FindPasswordActivity extends BaseActivity {
	private MyEditTextDeleteImg edit_user_name;
	private Button btn_finish;

	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_password);
		initView();
	}

	private void initView() {
		btn_finish = (Button) findViewById(R.id.btn_finish);
		edit_user_name = (MyEditTextDeleteImg) findViewById(R.id.edit_user_name);
		setListener();
	}

	private void setListener() {
		edit_user_name.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_user_name, this));
		edit_user_name.addTextChangedListener(new MyEditTextWatcher(
				edit_user_name, this));
		btn_finish.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_finish:
			getPwd();
			break;

		default:
			break;
		}
	}

	private void getPwd() {
		String user_name = edit_user_name.getText().toString();
		if ("".equals(user_name)) {
			ToastUtil.showToast("请输入手机号");
			return;
		}
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		FindPasswordTask task = new FindPasswordTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				ToastUtil.showToast("密码已经发送到你的手机");
				finishThisActivity();
			}
		});
		User user = new User();
		user.setUsername(user_name);
		task.executeParallel(user);
	}
}
