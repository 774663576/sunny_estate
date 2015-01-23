package com.sunnyestate;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.sunnyestate.fragment.LoginFragment;
import com.sunnyestate.fragment.RegisterFragment;
import com.sunnyestate.utils.Utils;

public class LoginAndRegisterActivity extends FragmentActivity implements
		OnClickListener {
	private int currentTabIndex = -1;

	private RadioGroup radioGroup;
	private ImageView img_back; 

	private LoginFragment logonFrag;
	private RegisterFragment registerFrag;

	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_and_register);
		initViwe();
		initFragment();
		showTab(0, radioGroup);
	}

	private void initViwe() {
		img_back = (ImageView) findViewById(R.id.img_back);
		radioGroup = (RadioGroup) findViewById(R.id.tab_group);
		setListener();
	}

	private void initFragment() {
		logonFrag = new LoginFragment();
		registerFrag = new RegisterFragment();
		fragmentList.add(logonFrag);
		fragmentList.add(registerFrag);
	}

	private void setListener() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				for (int i = 0; i < group.getChildCount(); i++) {
					if (group.getChildAt(i).getId() == checkedId) {
						showTab(i, group);
						break;
					}
				}
			}
		});
		img_back.setOnClickListener(this);
	}

	public void showTab(int tabIndex, RadioGroup group) {
		if (tabIndex < 0 || tabIndex >= group.getChildCount())
			return;
		if (currentTabIndex == tabIndex)
			return;
		if (currentTabIndex >= 0) {
			fragmentList.get(currentTabIndex).onPause();
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		for (int i = 0; i < group.getChildCount(); i++) {
			Fragment fg = fragmentList.get(i);
			RadioButton tabItem = (RadioButton) group.getChildAt(i);
			if (i == tabIndex) {
				if (fg.isAdded()) {
					fg.onResume();
				} else {
					ft.add(R.id.realtabcontent, fg);
				}
				tabItem.setTextColor(R.color.title_color);
				tabItem.setBackgroundResource(R.drawable.category_title_background);
				tabItem.getBackground().setAlpha(100);
				ft.show(fg);
			} else {
				ft.hide(fg);
				tabItem.setTextColor(Color.WHITE);
				tabItem.getBackground().setAlpha(0);

			}
		}
		ft.commit();
		currentTabIndex = tabIndex;
		RadioButton rb = (RadioButton) group.getChildAt(tabIndex);
		if (!rb.isChecked())
			rb.setChecked(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			Utils.rightOut(this);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			Utils.rightOut(this);
		}
		return super.onKeyDown(keyCode, event);
	}

}
