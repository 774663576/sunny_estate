package com.sunnyestate.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.sunnyestate.FindPasswordActivity;
import com.sunnyestate.R;
import com.sunnyestate.data.User;
import com.sunnyestate.enums.RetError;
import com.sunnyestate.interfaces.MyEditTextWatcher;
import com.sunnyestate.interfaces.OnEditFocusChangeListener;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.LoginTask;
import com.sunnyestate.utils.Constants;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.SharedUtils;
import com.sunnyestate.utils.ToastUtil;
import com.sunnyestate.utils.Utils;
import com.sunnyestate.views.MyEditTextDeleteImg;

public class LoginFragment extends Fragment implements OnClickListener {
	private MyEditTextDeleteImg edit_telphone;
	private MyEditTextDeleteImg edit_password;
	private Button btn_login;
	private Button btn_forget_password;

	private User user;
	private Dialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.login_fragment_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		user = new User();
		initView();
	}

	private void initView() {
		btn_forget_password = (Button) getView().findViewById(
				R.id.btn_forget_password);
		btn_login = (Button) getView().findViewById(R.id.btn_login);
		edit_password = (MyEditTextDeleteImg) getView().findViewById(
				R.id.edit_password);
		edit_telphone = (MyEditTextDeleteImg) getView().findViewById(
				R.id.edit_telphone);
		edit_telphone.setTag("phone_num");
		setListener();
	}

	private void setListener() {
		edit_telphone.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_telphone, getActivity()));
		edit_telphone.addTextChangedListener(new MyEditTextWatcher(
				edit_telphone, getActivity()));
		edit_password.addTextChangedListener(new MyEditTextWatcher(
				edit_password, getActivity()));
		edit_password.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_password, getActivity()));
		btn_login.setOnClickListener(this);
		btn_forget_password.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			login();
			break;
		case R.id.btn_forget_password:
			getActivity().startActivity(
					new Intent(getActivity(), FindPasswordActivity.class));
			Utils.leftOutRightIn(getActivity());
			break;
		default:
			break;
		}
	}

	private void login() {
		String mobile = edit_telphone.getText().toString();
		String pwd = edit_password.getText().toString();
		if ("".equals(mobile)) {
			ToastUtil.showToast("请输入手机号");
			return;
		}
		if ("".equals(pwd)) {
			ToastUtil.showToast("请输入密码");
			return;
		}
		dialog = DialogUtil.createLoadingDialog(getActivity());
		dialog.show();
		LoginTask task = new LoginTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				SharedUtils.setUid(user.getUid() + "");
				SharedUtils.setNickName(user.getNickname());
				SharedUtils.setUserName(user.getUsername());
				SharedUtils.setScore(user.getScore());
				SharedUtils.setLevel(user.getLevel());
				ToastUtil.showToast("登录成功");
				getActivity().sendBroadcast(
						new Intent(Constants.REGISTER_SUCCESS));
				getActivity().finish();
			}
		});
		user.setMobile(mobile);
		user.setPwd(pwd);
		task.executeParallel(user);
	}
}
