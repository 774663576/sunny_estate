package com.sunnyestate.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunnyestate.R;
import com.sunnyestate.data.ShoppingCar;
import com.sunnyestate.utils.UniversalImageLoadTool;

public class FillOrderAdapter extends BaseAdapter {
	private Context mContext;
	private List<ShoppingCar> lists = new ArrayList<ShoppingCar>();

	public FillOrderAdapter(Context context, List<ShoppingCar> lists) {
		this.lists = lists;
		this.mContext = context;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.file_order_listview_item, null);
			holder = new ViewHolder();
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
		ShoppingCar detail = lists.get(position);
		UniversalImageLoadTool.disPlay(detail.getImg_url(), holder.img_logo,
				R.drawable.img1);
		holder.txt_count.setText("ÊýÁ¿£º " + detail.getCount());
		holder.txt_price.setText("£¤" + detail.getMember_price());
		holder.txt_title.setText(detail.getTitle());
		return convertView;
	}

	class ViewHolder {
		private ImageView img_logo;
		private TextView txt_price;
		private TextView txt_count;
		private TextView txt_title;
	}
}
