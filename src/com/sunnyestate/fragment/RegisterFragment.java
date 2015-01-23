package com.sunnyestate.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.sunnyestate.R;
import com.sunnyestate.data.User;
import com.sunnyestate.enums.RetError;
import com.sunnyestate.interfaces.MyEditTextWatcher;
import com.sunnyestate.interfaces.OnEditFocusChangeListener;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.GetVerfityCodeTask;
import com.sunnyestate.task.RegisterTask;
import com.sunnyestate.utils.Constants;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.SharedUtils;
import com.sunnyestate.utils.ToastUtil;
import com.sunnyestate.views.MyEditTextDeleteImg;

public class RegisterFragment extends Fragment implements OnClickListener {

	private MyEditTextDeleteImg edit_telphone;
	private MyEditTextDeleteImg edit_password;
	private MyEditTextDeleteImg edit_code;
	private Button btn_register;
	private Button btn_get_code;

	private int second = 60;

	private Dialog dialog;

	private User user;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				second--;
				if (second < 1) {
					btn_get_code.setText("��ȡ��֤��");
					btn_get_code.setEnabled(true);
					return;
				}
				btn_get_code.setText(second + "������»�ȡ");
				mHandler.sendEmptyMessageDelayed(0, 1000);
				break;

			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.register_fragment_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		user = new User();
		initView();
	}

	private void initView() {
		btn_register = (Button) getView().findViewById(R.id.btn_register);
		btn_get_code = (Button) getView().findViewById(R.id.btn_get_code);
		edit_password = (MyEditTextDeleteImg) getView().findViewById(
				R.id.edit_password);
		edit_telphone = (MyEditTextDeleteImg) getView().findViewById(
				R.id.edit_telphone);
		edit_telphone.setTag("phone_num");
		edit_code = (MyEditTextDeleteImg) getView()
				.findViewById(R.id.edit_code);
		setListener();
	}

	private void setListener() {
		btn_get_code.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		edit_telphone.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_telphone, getActivity()));
		edit_telphone.addTextChangedListener(new MyEditTextWatcher(
				edit_telphone, getActivity()));
		edit_password.addTextChangedListener(new MyEditTextWatcher(
				edit_password, getActivity()));
		edit_password.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_password, getActivity()));
		edit_code.addTextChangedListener(new MyEditTextWatcher(edit_code,
				getActivity()));
		edit_code.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_code, getActivity()));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get_code:
			String mobile = edit_telphone.getText().toString().replace(" ", "");
			if ("".equals(mobile)) {
				ToastUtil.showToast("����д�ֻ���");
				return;
			}
			getCode(mobile);
			break;
		case R.id.btn_register:
			String pwd = edit_password.getText().toString();
			String code = edit_code.getText().toString();
			if ("".equals(pwd)) {
				ToastUtil.showToast("����д����");
				return;
			}
			if ("".equals(code)) {
				ToastUtil.showToast("����д��֤��");
				return;
			}
			register(pwd, code);
			break;
		default:
			break;
		}

	}

	private void register(String passwrod, String code) {
		user.setPwd(passwrod);
		dialog = DialogUtil.createLoadingDialog(getActivity());
		dialog.show();
		RegisterTask task = new RegisterTask(code);
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
				ToastUtil.showToast("ע��ɹ�");
				getActivity().sendBroadcast(
						new Intent(Constants.REGISTER_SUCCESS));
				getActivity().finish();
			}

		});
		task.executeParallel(user);
	}

	private void getCode(String mobile) {
		dialog = DialogUtil.createLoadingDialog(getActivity());
		dialog.show();
		GetVerfityCodeTask task = new GetVerfityCodeTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				second = 60;
				btn_get_code.setEnabled(false);
				mHandler.sendEmptyMessage(0);
			}

		});
		user.setMobile(mobile);
		task.executeParallel(user);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacksAndMessages(null);
	}
}
