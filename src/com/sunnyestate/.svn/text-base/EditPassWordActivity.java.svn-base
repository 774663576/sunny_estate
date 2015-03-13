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
import com.sunnyestate.task.EditPwdTask;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.ToastUtil;
import com.sunnyestate.views.MyEditTextDeleteImg;

public class EditPassWordActivity extends BaseActivity {
	private MyEditTextDeleteImg edit_old_pwd;
	private MyEditTextDeleteImg edit_new_pwd;

	private Button btn_finish;

	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_pwd);
		initView();
	}

	private void initView() {
		btn_finish = (Button) findViewById(R.id.btn_finish);
		edit_old_pwd = (MyEditTextDeleteImg) findViewById(R.id.edit_old_pwd);
		edit_new_pwd = (MyEditTextDeleteImg) findViewById(R.id.edit_new_pwd);
		setListener();
	}

	private void setListener() {
		edit_old_pwd.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_old_pwd, this));
		edit_old_pwd.addTextChangedListener(new MyEditTextWatcher(edit_old_pwd,
				this));
		edit_new_pwd.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_new_pwd, this));
		edit_new_pwd.addTextChangedListener(new MyEditTextWatcher(edit_new_pwd,
				this));
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
		String new_pwd = edit_new_pwd.getText().toString();
		if ("".equals(new_pwd)) {
			ToastUtil.showToast("«Î ‰»Î–¬√‹¬Î");
			return;
		}
		String old_pwd = edit_old_pwd.getText().toString();
		if ("".equals(new_pwd)) {
			ToastUtil.showToast("«Î ‰»Î√‹¬Î");
			return;
		}
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		EditPwdTask task = new EditPwdTask(old_pwd, new_pwd);
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				ToastUtil.showToast("√‹¬Î–ﬁ∏ƒ≥…π¶");
				finishThisActivity();
			}
		});
		User user = new User();
		task.executeParallel(user);
	}
}
