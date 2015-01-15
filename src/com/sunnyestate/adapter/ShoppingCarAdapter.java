package com.sunnyestate.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunnyestate.R;
import com.sunnyestate.data.ShoppingCar;
import com.sunnyestate.utils.UniversalImageLoadTool;

public class ShoppingCarAdapter extends BaseAdapter {
	private List<ShoppingCar> lists = new ArrayList<ShoppingCar>();
	private Context mContext;
	private SelectListener mCallBack;

	public void setmCallBack(SelectListener mCallBack) {
		this.mCallBack = mCallBack;
	}

	public ShoppingCarAdapter(Context context, List<ShoppingCar> lists) {
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
					R.layout.shopping_car_item_layout, null);
			holder = new ViewHolder();
			holder.checkbox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			holder.edit_count = (EditText) convertView
					.findViewById(R.id.edit_count);
			holder.img_dec = (ImageView) convertView.findViewById(R.id.img_dec);
			holder.img_edit = (ImageView) convertView
					.findViewById(R.id.img_edit);
			holder.img_edit_finish = (ImageView) convertView
					.findViewById(R.id.img_edit_finish);
			holder.img_inc = (ImageView) convertView.findViewById(R.id.img_inc);
			holder.img_logo = (ImageView) convertView
					.findViewById(R.id.img_logo);
			holder.layout_edit = (LinearLayout) convertView
					.findViewById(R.id.layout_edit);
			holder.layout_inpupt_count = (RelativeLayout) convertView
					.findViewById(R.id.layout_input_count);
			holder.txt_count = (TextView) convertView
					.findViewById(R.id.txt_count);
			holder.txt_price = (TextView) convertView
					.findViewById(R.id.txt_price);
			holder.txt_title = (TextView) convertView
					.findViewById(R.id.txt_title);
			holder.txt_member_price = (TextView) convertView
					.findViewById(R.id.txt_member_price);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ShoppingCar car = lists.get(position);
		holder.layout_edit.setOnClickListener(new Onclick(position));
		holder.img_edit_finish.setOnClickListener(new Onclick(position));
		holder.checkbox.setOnClickListener(new Onclick(position));
		holder.img_dec.setOnClickListener(new Onclick(position));
		holder.img_inc.setOnClickListener(new Onclick(position));
		UniversalImageLoadTool.disPlay(car.getImg_url(), holder.img_logo,
				R.drawable.img1);
		holder.txt_count.setText("×" + car.getCount());
		holder.edit_count.setText(car.getCount() + "");
		holder.txt_title.setText(car.getTitle());
		holder.txt_member_price.setText(Html
				.fromHtml("商品价格￥<font color=#c00000>" + car.getMember_price()
						/ 100 + ".00</font> "));
		holder.txt_price.setText("原价:" + car.getPrice() / 100 + ".00");
		holder.txt_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 删除线
		if (car.isSelect()) {
			holder.checkbox.setChecked(true);
		} else {
			holder.checkbox.setChecked(false);
		}
		if (car.isEdit()) {
			holder.layout_inpupt_count.setVisibility(View.VISIBLE);
			holder.layout_edit.setVisibility(View.GONE);
		} else {
			holder.layout_inpupt_count.setVisibility(View.INVISIBLE);
			holder.layout_edit.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	class ViewHolder {
		CheckBox checkbox;
		ImageView img_logo;
		TextView txt_title;
		TextView txt_price;
		ImageView img_dec;
		EditText edit_count;
		ImageView img_inc;
		ImageView img_edit_finish;
		TextView txt_count;
		ImageView img_edit;
		LinearLayout layout_edit;
		RelativeLayout layout_inpupt_count;
		TextView txt_member_price;
	}

	class Onclick implements OnClickListener {
		int position;

		public Onclick(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.layout_edit:
				lists.get(position).setEdit(true);
				break;
			case R.id.img_edit_finish:
				lists.get(position).setEdit(false);
				break;
			case R.id.checkbox:
				lists.get(position).setSelect(!lists.get(position).isSelect());
				if (mCallBack != null) {
					mCallBack.onSelect();
				}
				break;
			case R.id.img_dec:
				changeCount(v.getId(), position);
				break;
			case R.id.img_inc:
				changeCount(v.getId(), position);
				break;
			default:
				break;
			}
			notifyDataSetChanged();

		}

		private void changeCount(int id, int position) {
			int count = lists.get(position).getCount();
			if (id == R.id.img_inc) {
				lists.get(position).setCount(count + 1);
			} else if (id == R.id.img_dec) {
				if (count == 0) {
					return;
				}
				lists.get(position).setCount(count - 1);
			}
		}
	}

	public interface SelectListener {
		void onSelect();
	}
}
