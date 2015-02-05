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
import com.sunnyestate.utils.SharedUtils;
import com.sunnyestate.utils.ToastUtil;

public class MyRightPopWindow implements OnClickListener {
	private PopupWindow popupWindow;
	private Context mContext;
	private View v;
	private View view;
	private LinearLayout layout_exit;
	private LinearLayout layout_edit_pwd;

	private OnlistOnclick callback;

	public MyRightPopWindow(Context context, View v) {
		this.mContext = context;
		this.v = v;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.my_right_menu_layout, null);
		initView();
		initPopwindow();
	}

	private void initView() {
		layout_exit = (LinearLayout) view.findViewById(R.id.layout_exit);
		layout_exit.setOnClickListener(this);
		layout_edit_pwd = (LinearLayout) view
				.findViewById(R.id.layout_find_password);
		layout_edit_pwd.setOnClickListener(this);
	}

	/**
	 * ��ʼ��popwindow
	 */
	@SuppressWarnings("deprecation")
	private void initPopwindow() {
		popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı�����������ģ�
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * popwindow����ʾ
	 */
	public void show() {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
				location[0] + v.getWidth(), location[1] + v.getHeight()); // ʹ��ۼ�
		popupWindow.setFocusable(true);
		// ����������������ʧ
		popupWindow.setOutsideTouchable(true);
		// ˢ��״̬
		popupWindow.update();
	}

	// ����
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
		case R.id.layout_exit:
			callback.onclick(0);
			break;
		case R.id.layout_find_password:
			if ("".equals(SharedUtils.getPasswordKey())) {
				ToastUtil.showToast("���ȵ�¼");
				return;
			}
			callback.onclick(1);
			break;
		default:
			break;
		}
	}
}
