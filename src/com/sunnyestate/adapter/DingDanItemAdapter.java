package com.sunnyestate.adapter;

import java.util.ArrayList;
import java.util.List;

import com.sunnyestate.R;
import com.sunnyestate.data.OrderItem;
import com.sunnyestate.utils.UniversalImageLoadTool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DingDanItemAdapter extends BaseAdapter {
	private Context mContext;
	private List<OrderItem> itemList = new ArrayList<OrderItem>();

	public DingDanItemAdapter(Context context, List<OrderItem> itemList) {
		this.mContext = context;
		this.itemList = itemList;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_ding_dan_item, null);
			holder.img_logo = (ImageView) convertView
					.findViewById(R.id.img_logo);
			holder.txt_count = (TextView) convertView
					.findViewById(R.id.txt_count);
			holder.txt_price = (TextView) convertView
					.findViewById(R.id.txt_price);
			holder.txt_title = (TextView) convertView
					.findViewById(R.id.txt_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		OrderItem item = itemList.get(position);
		UniversalImageLoadTool.disPlay(item.getImageurl(), holder.img_logo,
				R.drawable.img1);
		holder.txt_count.setText("ÊýÁ¿£º " + item.getNum());
		holder.txt_price.setText("£¤" + item.getPrice() / 100 + ".00");
		holder.txt_title.setText(item.getTitle());
		return convertView;
	}

	class ViewHolder {
		ImageView img_logo;
		TextView txt_title;
		TextView txt_count;
		TextView txt_price;
	}
}
