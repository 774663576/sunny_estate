package com.sunnyestate.popwindow;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sunnyestate.R;
import com.sunnyestate.data.Area;
import com.sunnyestate.data.City;
import com.sunnyestate.data.Province;

import fynn.util.Util;

public class CityListPopWindow implements OnClickListener {
	private PopupWindow popupWindow;
	private Context mContext;
	private View v;
	private View view;
	private ListView listView_province;
	private ListView listView_city;
	private ListView listView_area;

	private ProvinceAdapter province_adapter;
	private CityAdapter city_adapter;
	private AreaAdapter area_adapter;
	private List<Province> lists = new ArrayList<Province>();
	private LinearLayout layout_parent;
	private LinearLayout layout_content;

	private int province_position = 0;
	private int city_position = 0;

	private SelectCity mCallBack;

	public void setmCallBack(SelectCity mCallBack) {
		this.mCallBack = mCallBack;
	}

	public CityListPopWindow(Context context, View v, List<Province> lists) {
		this.mContext = context;
		this.v = v;
		this.lists = lists;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.city_list_layout, null);
		initView();
		setListener();
		initPopwindow();
		setValue();
	}

	private void initView() {
		layout_content = (LinearLayout) view.findViewById(R.id.layout_conent);
		layout_content.setLayoutParams(new LinearLayout.LayoutParams(Util
				.getScreenWidth(mContext) - 200,
				Util.getScreenWidth(mContext) / 2 + 100));
		layout_parent = (LinearLayout) view.findViewById(R.id.layout_parent);
		layout_parent.getBackground().setAlpha(150);
		listView_area = (ListView) view.findViewById(R.id.listView_area);
		listView_province = (ListView) view
				.findViewById(R.id.listView_province);
		listView_city = (ListView) view.findViewById(R.id.listView_city);
	}

	private void setListener() {
		listView_province.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				city_adapter.setData(lists.get(position).getCityLists());
				if (lists.get(position).getCityLists().size() == 0) {
					return;
				}
				area_adapter.setData(lists.get(position).getCityLists().get(0)
						.getAreaLists());
				province_position = position;
				city_position = 0;

			}
		});
		listView_city.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				area_adapter.setData(lists.get(province_position)
						.getCityLists().get(position).getAreaLists());
				city_position = position;

			}
		});
		listView_area.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				mCallBack
						.selectCity(province_position, city_position, position);
				dismiss();
			}
		});
	}

	private void setValue() {
		province_adapter = new ProvinceAdapter();
		listView_province.setAdapter(province_adapter);
		city_adapter = new CityAdapter(lists.get(0).getCityLists());
		listView_city.setAdapter(city_adapter);
		area_adapter = new AreaAdapter(lists.get(0).getCityLists().get(0)
				.getAreaLists());
		listView_area.setAdapter(area_adapter);
	}

	/**
	 * 初始化popwindow
	 */
	@SuppressWarnings("deprecation")
	private void initPopwindow() {
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// popupWindow.setAnimationStyle(R.style.AnimBottom);
	}

	/**
	 * popwindow的显示
	 */
	public void show() {
		popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
		// 使其聚集
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

	@Override
	public void onClick(View v) {
		dismiss();
		switch (v.getId()) {
		case R.id.layout_parent:
			dismiss();
			break;
		default:
			break;
		}

	}

	class ProvinceAdapter extends BaseAdapter {

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
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.city_item, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txt.setText(lists.get(position).getProvince_name());
			return convertView;
		}

	}

	class CityAdapter extends BaseAdapter {
		private List<City> lists;

		public CityAdapter(List<City> lists) {
			this.lists = lists;
		}

		public void setData(List<City> lists) {
			this.lists = lists;
			notifyDataSetChanged();
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
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.city_item, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txt.setText(lists.get(position).getCity_name());
			return convertView;
		}

	}

	class AreaAdapter extends BaseAdapter {
		private List<Area> lists;

		public AreaAdapter(List<Area> lists) {
			this.lists = lists;
		}

		public void setData(List<Area> lists) {
			this.lists = lists;
			notifyDataSetChanged();
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
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.city_item, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txt.setText(lists.get(position).getArea_name());
			return convertView;
		}

	}

	class ViewHolder {
		TextView txt;
	}

	public interface SelectCity {
		void selectCity(int province_postion, int city_position,
				int area_position);
	}
}
