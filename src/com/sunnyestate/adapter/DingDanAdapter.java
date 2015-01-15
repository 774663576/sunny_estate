package com.sunnyestate.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunnyestate.ProductDetailActivity;
import com.sunnyestate.R;
import com.sunnyestate.utils.Utils;

public class DingDanAdapter extends BaseAdapter {
	private Context mContext;

	public DingDanAdapter(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return 5;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
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
			holder.img_logo = (ImageView) convertView
					.findViewById(R.id.img_logo);
			holder.txt_count = (TextView) convertView
					.findViewById(R.id.txt_count);
			holder.txt_dingdan_number = (TextView) convertView
					.findViewById(R.id.txt_dingdan_number);
			holder.txt_fukuan = (TextView) convertView
					.findViewById(R.id.txt_fukuan);
			holder.txt_price = (TextView) convertView
					.findViewById(R.id.txt_price);
			holder.txt_status = (TextView) convertView
					.findViewById(R.id.txt_tishi);
			holder.txt_title = (TextView) convertView
					.findViewById(R.id.txt_title);
			holder.line = (View) convertView.findViewById(R.id.line);
			holder.layout_item = (LinearLayout) convertView
					.findViewById(R.id.layout_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == 0) {
			holder.line.setVisibility(View.GONE);
		} else {
			holder.line.setVisibility(View.VISIBLE);
		}
		holder.txt_status.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		holder.txt_status.getPaint().setFakeBoldText(true);
		holder.txt_fukuan.setText(Html.fromHtml("Êµ¸¶¿î:<font color=#ff4900>£¤"
				+ 60000 / 100 + ".00</font> "));
		holder.layout_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mContext.startActivity(new Intent(mContext,
						ProductDetailActivity.class));
				Utils.leftOutRightIn(mContext);
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView txt_status;
		TextView txt_dingdan_number;
		ImageView img_del;
		ImageView img_logo;
		TextView txt_title;
		TextView txt_count;
		TextView txt_price;
		TextView txt_fukuan;
		Button btn_go_pay;
		Button btn_wuliu;
		Button btn_again_buy;
		View line;
		LinearLayout layout_item;
	}
}
