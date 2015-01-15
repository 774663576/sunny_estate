package com.sunnyestate.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sunnyestate.R;

public class ProduceDetailRightPopwindow implements OnClickListener {
	private PopupWindow popupWindow;
	private Context mContext;
	private View v;
	private View view;
	private LinearLayout layout_home;
	private LinearLayout layout_share;

	private OnlistOnclick callback;

	public ProduceDetailRightPopwindow(Context context, View v) {
		this.mContext = context;
		this.v = v;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.produce_detail_right_popwindow_layout,
				null);
		initView();
		initPopwindow();
	}

	private void initView() {
		layout_home = (LinearLayout) view.findViewById(R.id.layout_home);
		layout_share = (LinearLayout) view.findViewById(R.id.layout_share);
		layout_home.setOnClickListener(this);
		layout_share.setOnClickListener(this);
	}

	/**
	 * 初始化popwindow
	 */
	@SuppressWarnings("deprecation")
	private void initPopwindow() {
		popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * popwindow的显示
	 */
	public void show() {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
				location[0] + v.getWidth(), location[1] + v.getHeight()); // 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 刷新状态
		popupWindow.update();
	}

	// 隐藏
	public void dismiss() {
		popupWindow.dismiss();
	}

	class ViewHolder {
		TextView text;
		LinearLayout laybg;
	}

	public void setOnlistOnclick(OnlistOnclick callback) {
		this.callback = callback;
	}

	public interface OnlistOnclick {
		void onclick(int position);
	}

	@Override
	public void onClick(View v) {
		dismiss();
		switch (v.getId()) {
		case R.id.layout_home:
			callback.onclick(0);
			break;
		case R.id.layout_share:
			callback.onclick(1);
			break;
		default:
			break;
		}
	}
}
