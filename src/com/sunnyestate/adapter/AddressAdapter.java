package com.sunnyestate.adapter;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunnyestate.AddAdressActivity;
import com.sunnyestate.R;
import com.sunnyestate.data.AbstractData.Status;
import com.sunnyestate.data.Adress;
import com.sunnyestate.db.DBUtils;
import com.sunnyestate.enums.RetError;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.ConfirmDialog;
import com.sunnyestate.task.SetDefaultAddressTask;
import com.sunnyestate.utils.Constants;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.Utils;

import fynn.app.PromptDialog;

public class AddressAdapter extends BaseAdapter {
	private List<Adress> lists;
	private Context mContext;

	public AddressAdapter(Context mContext, List<Adress> lists) {
		this.mContext = mContext;
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.adress_item, null);
			holder = new ViewHolder();
			holder.checkbox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			holder.txt_adress = (TextView) convertView
					.findViewById(R.id.txt_adress);
			holder.txt_adress_detail = (TextView) convertView
					.findViewById(R.id.txt_adress_detail);
			holder.txt_edit = (TextView) convertView
					.findViewById(R.id.txt_edit);
			holder.txt_del = (TextView) convertView.findViewById(R.id.txt_del);
			holder.txt_name = (TextView) convertView
					.findViewById(R.id.txt_name);
			holder.txt_tishi = (TextView) convertView
					.findViewById(R.id.txt_tishi);
			holder.layotu_item = (LinearLayout) convertView
					.findViewById(R.id.layout_item);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_tishi.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		holder.txt_tishi.getPaint().setFakeBoldText(true);
		holder.txt_adress.setText(lists.get(position).getProvincename() + " "
				+ lists.get(position).getAreaname());
		holder.txt_adress_detail.setText(lists.get(position).getDetail());
		holder.txt_name.setText(lists.get(position).getReceiver() + "  "
				+ lists.get(position).getPhone());
		if (lists.get(position).getIsdefault() == 1) {
			holder.txt_tishi.setText("默认地址");
			holder.checkbox.setChecked(true);
		} else {
			holder.txt_tishi.setText("设为默认");
			holder.checkbox.setChecked(false);
		}
		holder.checkbox.setOnClickListener(new Onclick(position));
		holder.txt_edit.setOnClickListener(new Onclick(position));
		holder.txt_del.setOnClickListener(new Onclick(position));
		holder.layotu_item.setOnClickListener(new Onclick(position));
		return convertView;
	}

	class ViewHolder {
		CheckBox checkbox;
		TextView txt_del;
		TextView txt_edit;
		TextView txt_adress;
		TextView txt_adress_detail;
		TextView txt_name;
		TextView txt_tishi;
		LinearLayout layotu_item;
	}

	class Onclick implements OnClickListener {
		int position;

		public Onclick(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.checkbox:
				if (lists.get(position).getIsdefault() == 1) {
					lists.get(position).setIsdefault(0);
					notifyDataSetChanged();

				} else {
					setDefaultAddress(position);
				}
				break;
			case R.id.txt_edit:
				((Activity) mContext).startActivityForResult(
						new Intent(mContext, AddAdressActivity.class).putExtra(
								"position", position).putExtra("adress",
								lists.get(position)), 400);
				Utils.leftOutRightIn(mContext);
				break;
			case R.id.txt_del:
				del(position);
				break;
			case R.id.layout_item:
				((Activity) mContext).setResult(300,
						new Intent().putExtra("adress", lists.get(position)));
				((Activity) mContext).finish();
				Utils.rightOut(mContext);
				break;
			default:
				break;
			}
		}
	}

	private void setDefaultAddress(final int position) {
		final Dialog dialog;
		dialog = DialogUtil.createLoadingDialog(mContext);
		dialog.show();
		SetDefaultAddressTask task = new SetDefaultAddressTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				for (Adress adress : lists) {
					if (adress.getIsdefault() == 1) {
						adress.setIsdefault(0);
					}
				}
				lists.get(position).setIsdefault(1);
				notifyDataSetChanged();
			}
		});
		task.executeParallel(lists.get(position));
	}

	private void del(final int position) {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(mContext,
				"确定要删除该地址吗?", "确定", "取消", new ConfirmDialog() {

					@Override
					public void onOKClick() {
						lists.get(position).setStatus(Status.DEL);
						lists.get(position).write(DBUtils.getDBsa(2));
						lists.remove(position);
						notifyDataSetChanged();
						if (lists.size() == 0) {
							mContext.sendBroadcast(new Intent(
									Constants.NO_ADRESS));
						}
					}

					@Override
					public void onCancleClick() {

					}
				});
		dialog.show();
	}
}
