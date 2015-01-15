package com.sunnyestate.fragment;

import com.sunnyestate.R;
import com.sunnyestate.interfaces.MyEditTextWatcher;
import com.sunnyestate.interfaces.OnEditFocusChangeListener;
import com.sunnyestate.views.MyEditTextDeleteImg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RegisterFragment extends Fragment {
	private MyEditTextDeleteImg edit_telphone;
	private MyEditTextDeleteImg edit_password;
	private MyEditTextDeleteImg edit_code;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.register_fragment_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
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
}
