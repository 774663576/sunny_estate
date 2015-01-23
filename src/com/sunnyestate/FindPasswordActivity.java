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
	private MyEditTextDeleteImg edit_telphone;
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
		edit_telphone = (MyEditTextDeleteImg) findViewById(R.id.edit_telphone);
		edit_telphone.setTag("phone_num");
		setListener();
	}

	private void setListener() {
		edit_telphone.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_telphone, this));
		edit_telphone.addTextChangedListener(new MyEditTextWatcher(
				edit_telphone, this));
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
		String mobile = edit_telphone.getText().toString();
		if ("".equals(mobile)) {
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
			}
		});
		User user = new User();
		user.setMobile(mobile);
		task.executeParallel(user);
	}
}
