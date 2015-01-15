package com.sunnyestate;

import android.content.Context;
import android.view.View;

import com.sunnyestate.fragment.MyFragment;

public abstract class PersonalCenter {
	private View contentRootView;
	protected Context mContext;
	protected MyFragment fragment;

	public PersonalCenter(Context mContext, MyFragment fragment,
			View contentRootView) {
		this.contentRootView = contentRootView;
		this.mContext = mContext;
		this.fragment = fragment;
		initView();
		setListener();
	}

	public abstract void initView();

	public abstract void setListener();

	public View findViewById(int id) {
		return contentRootView.findViewById(id);

	}

}
