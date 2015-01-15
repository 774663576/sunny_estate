package com.sunnyestate;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.sunnyestate.fragment.CategoryFragment;
import com.sunnyestate.fragment.HomeFragment;
import com.sunnyestate.fragment.MyFragment;
import com.sunnyestate.fragment.SettingFragment;
import com.sunnyestate.fragment.ShoppingCartFragment;
import com.sunnyestate.utils.ClientUpgrade;
import com.sunnyestate.utils.Constants;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	private int currentTabIndex = -1;
	private HomeFragment home_fragment;
	private CategoryFragment category_fragment;
	private ShoppingCartFragment shop_fragment;
	private SettingFragment set_fragment;
	private MyFragment my_fragment;
	private RadioGroup rg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initFragment();
		registerBoradcastReceiver();
		update();
	}

	private void update() {
		ClientUpgrade cu = new ClientUpgrade(this);
		cu.startCheckVersion(null);
	}

	private void initFragment() {
		rg = (RadioGroup) this.findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(this);
		home_fragment = new HomeFragment();
		category_fragment = new CategoryFragment();
		shop_fragment = new ShoppingCartFragment();
		set_fragment = new SettingFragment();
		my_fragment = new MyFragment();
		fragmentList.add(home_fragment);
		fragmentList.add(category_fragment);
		fragmentList.add(my_fragment);
		fragmentList.add(shop_fragment);
		fragmentList.add(set_fragment);
		showTab(0);
	}

	public void showTab(int tabIndex) {
		if (tabIndex < 0 || tabIndex >= rg.getChildCount())
			return;
		if (currentTabIndex == tabIndex)
			return;
		if (currentTabIndex >= 0) {
			fragmentList.get(currentTabIndex).onPause();
		}
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		for (int i = 0; i < rg.getChildCount(); i++) {
			Fragment fg = fragmentList.get(i);
			if (i == tabIndex) {
				if (fg.isAdded()) {
					fg.onResume();
				} else {
					ft.add(R.id.realtabcontent, fg);
				}
				ft.show(fg);
			} else {
				ft.hide(fg);
			}
		}
		ft.commitAllowingStateLoss();
		currentTabIndex = tabIndex;
		RadioButton rb = (RadioButton) rg.getChildAt(tabIndex);
		if (!rb.isChecked())
			rb.setChecked(true);
	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		for (int i = 0; i < group.getChildCount(); i++) {
			if (group.getChildAt(i).getId() == checkedId) {
				showTab(i);
				break;
			}
		}
	}

	/**
	 * 注册该广播
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Constants.GO_BUY_SHOPPING);
		myIntentFilter.addAction(Constants.RETURN_HOME);
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	/**
	 * 定义广播
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.GO_BUY_SHOPPING)) {
				showTab(1);
			} else if (action.equals(Constants.RETURN_HOME)) {
				showTab(0);
			}
		}
	};

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	};

}
