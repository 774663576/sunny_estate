package com.sunnyestate.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sunnyestate.AboutActivity;
import com.sunnyestate.FreedBackActivity;
import com.sunnyestate.R;
import com.sunnyestate.utils.ClientUpgrade;
import com.sunnyestate.utils.ClientUpgrade.ClientUpgradeCallback;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.ToastUtil;
import com.sunnyestate.utils.Utils;

public class SettingFragment extends Fragment implements OnClickListener {
	private TextView txt_clear_cache;
	private TextView txt_freedback;
	private TextView txt_update_new_verison;
	private TextView txt_about_us;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_setting_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		txt_clear_cache = (TextView) getView().findViewById(
				R.id.txt_clear_cache);
		txt_about_us = (TextView) getView().findViewById(R.id.txt_about_us);
		txt_freedback = (TextView) getView().findViewById(R.id.txt_freedback);
		txt_update_new_verison = (TextView) getView().findViewById(
				R.id.txt_update_new_version);
		setListerer();
	}

	private void setListerer() {
		txt_about_us.setOnClickListener(this);
		txt_update_new_verison.setOnClickListener(this);
		txt_freedback.setOnClickListener(this);
		txt_clear_cache.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_freedback:
			startActivity(new Intent(getActivity(), FreedBackActivity.class));
			Utils.leftOutRightIn(getActivity());
			break;
		case R.id.txt_about_us:
			startActivity(new Intent(getActivity(), AboutActivity.class));
			Utils.leftOutRightIn(getActivity());
			break;
		case R.id.txt_update_new_version:
			update();
			break;
		case R.id.txt_clear_cache:
			ToastUtil.showToast("已清除", Toast.LENGTH_SHORT);
			break;
		default:
			break;
		}
	}

	private void update() {
		final Dialog dialog = DialogUtil.createLoadingDialog(getActivity(),
				"请稍后");
		dialog.show();
		// 启动后，自动检测版本更新
		ClientUpgrade cu = new ClientUpgrade(getActivity());
		cu.startCheckVersion(new ClientUpgradeCallback() {

			@Override
			public void onCheckFinished(int state) {

				if (dialog != null) {
					dialog.dismiss();
				}
				if (state == -1) {
					ToastUtil.showToast("当前已是最新版本", Toast.LENGTH_SHORT);
				}
			}
		});
	}
}
