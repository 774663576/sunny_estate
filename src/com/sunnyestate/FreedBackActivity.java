package com.sunnyestate;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sunnyestate.data.User;
import com.sunnyestate.enums.RetError;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.FreedBackTask;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.SharedUtils;
import com.sunnyestate.utils.ToastUtil;

public class FreedBackActivity extends BaseActivity {
	private ImageView img_back;
	private EditText edit_content;
	private Button btn_submit;
	private Dialog dialog;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_freed_back);
		user = new User();
		initView();
	}

	private void initView() {
		btn_submit = (Button) findViewById(R.id.btn_submit);
		edit_content = (EditText) findViewById(R.id.edit_content);
		img_back = (ImageView) findViewById(R.id.img_back);
		setListener();
	}

	private void setListener() {
		btn_submit.setOnClickListener(this);
		img_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finishThisActivity();
			break;
		case R.id.btn_submit:
			if ("".equals(SharedUtils.getPasswordKey())) {
				ToastUtil.showToast("ÇëÏÈµÇÂ¼");
				return;
			}
			String content = edit_content.getText().toString();
			if ("".equals(content)) {
				return;
			}
			submit(content);
			break;
		default:
			break;
		}
	}

	private void submit(String content) {
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		FreedBackTask task = new FreedBackTask(content);
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				ToastUtil.showToast("Ð»Ð»ÄúµÄ·´À¡");
			}
		});
		task.executeParallel(user);
	}
}
