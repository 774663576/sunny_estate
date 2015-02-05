package com.sunnyestate.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunnyestate.R;
import com.sunnyestate.data.CategoryDataDetail;
import com.sunnyestate.utils.UniversalImageLoadTool;
import com.sunnyestate.utils.Utils;

public class CategoryAdapter extends BaseAdapter {
	private Context mContext;
	private List<CategoryDataDetail> detailList = new ArrayList<CategoryDataDetail>();

	public CategoryAdapter(Context context, List<CategoryDataDetail> detailList) {
		this.mContext = context;
		this.detailList = detailList;
	}

	@Override
	public int getCount() {
		return detailList.size();
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
					R.layout.category_gridview_item, null);
			holder = new ViewHolder();
			holder.img_logo = (ImageView) convertView
					.findViewById(R.id.img_logo);
			holder.img_sweet = (ImageView) convertView
					.findViewById(R.id.img_sweet);
			holder.txt_title = (TextView) convertView
					.findViewById(R.id.txt_title);
			holder.txt_price = (TextView) convertView
					.findViewById(R.id.txt_price);
			holder.txt_member_price = (TextView) convertView
					.findViewById(R.id.txt_member_price);
			holder.ietm_layout = (RelativeLayout) convertView
					.findViewById(R.id.item_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.ietm_layout
				.setLayoutParams(new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, Utils
								.getSecreenHeight(mContext) / 3));
		LayoutParams layoutParams = holder.img_logo.getLayoutParams();
		layoutParams.height = Utils.getSecreenHeight(mContext) / 5;
		layoutParams.width = Utils.getSecreenWidth(mContext) / 3;
		holder.img_logo.setLayoutParams(layoutParams);
		CategoryDataDetail detail = detailList.get(position);
		UniversalImageLoadTool.disPlay(detail.getDefaultimg(), holder.img_logo,
				R.drawable.img1);
		holder.txt_title.setText(detail.getProducttile());
		holder.txt_price.setText("гд" + detail.getOriginalprice() + "0");
		holder.txt_member_price.setText("гд" + detail.getPrice() + "0");
		if (detail.getSweet() == 0) {
			holder.img_sweet.setVisibility(View.GONE);
		} else if (detail.getSweet() == 1) {
			holder.img_sweet.setVisibility(View.VISIBLE);
			holder.img_sweet.setImageResource(R.drawable.sweet1);
		} else if (detail.getSweet() == 2) {
			holder.img_sweet.setVisibility(View.VISIBLE);
			holder.img_sweet.setImageResource(R.drawable.sweet2);
		} else if (detail.getSweet() == 3) {
			holder.img_sweet.setVisibility(View.VISIBLE);
			holder.img_sweet.setImageResource(R.drawable.sweet3);
		}

		return convertView;
	}

	class ViewHolder {
		RelativeLayout ietm_layout;
		ImageView img_sweet;
		ImageView img_logo;
		TextView txt_title;
		TextView txt_price;
		TextView txt_member_price;
	}
}
