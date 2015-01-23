package com.sunnyestate.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sunnyestate.R;
import com.sunnyestate.data.OrderData;
import com.sunnyestate.enums.RetError;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.ConfirmDialog;
import com.sunnyestate.task.DelOrderTask;
import com.sunnyestate.utils.DialogUtil;

import fynn.app.PromptDialog;

public class DingDanAdapter extends BaseAdapter {
	private Context mContext;
	private List<OrderData> lists = new ArrayList<OrderData>();

	public DingDanAdapter(Context mContext, List<OrderData> lists) {
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
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.dingdan_item, null);
			holder = new ViewHolder();
			holder.btn_again_buy = (Button) convertView
					.findViewById(R.id.img_again_buy);
			holder.btn_go_pay = (Button) convertView
					.findViewById(R.id.img_go_pay);
			holder.btn_wuliu = (Button) convertView
					.findViewById(R.id.img_wuliu);
			holder.img_del = (ImageView) convertView.findViewById(R.id.img_del);
			holder.txt_dingdan_number = (TextView) convertView
					.findViewById(R.id.txt_dingdan_number);
			holder.txt_fukuan = (TextView) convertView
					.findViewById(R.id.txt_fukuan);
			holder.txt_status = (TextView) convertView
					.findViewById(R.id.txt_tishi);
			holder.line = (View) convertView.findViewById(R.id.line);
			holder.mListView = (ListView) convertView
					.findViewById(R.id.listView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == 0) {
			holder.line.setVisibility(View.GONE);
		} else {
			holder.line.setVisibility(View.VISIBLE);
		}
		OrderData order = lists.get(position);
		holder.txt_status.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		holder.txt_status.getPaint().setFakeBoldText(true);
		holder.txt_status.setText(order.getStatusname());
		holder.txt_fukuan.setText(Html.fromHtml("实付款:<font color=#ff4900>￥"
				+ order.getRealcharges() / 100 + ".00</font> "));
		holder.txt_dingdan_number.setText("订单号:" + order.getOrderid());
		holder.mListView.setAdapter(new DingDanItemAdapter(mContext, order
				.getItemList()));
		holder.img_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				del(position);
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView txt_status;
		TextView txt_dingdan_number;
		ImageView img_del;
		ListView mListView;
		TextView txt_fukuan;
		Button btn_go_pay;
		Button btn_wuliu;
		Button btn_again_buy;
		View line;
	}

	private void del(final int position) {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(mContext,
				"确定要删除该订单吗?", "确定", "取消", new ConfirmDialog() {

					@Override
					public void onOKClick() {
						delOrder(position);
					}

					@Override
					public void onCancleClick() {

					}
				});
		dialog.show();
	}

	private void delOrder(final int position) {
		final Dialog dialog = DialogUtil.createLoadingDialog(mContext, "删除中");
		dialog.show();
		DelOrderTask task = new DelOrderTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				lists.remove(position);
				notifyDataSetChanged();
			}
		});
		task.executeParallel(lists.get(position));

	}
}
