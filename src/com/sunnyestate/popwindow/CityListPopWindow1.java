package com.sunnyestate.popwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.sunnyestate.R;
import com.sunnyestate.data.Area;
import com.sunnyestate.data.City;
import com.sunnyestate.data.Province;

public class CityListPopWindow1 implements OnClickListener,
		OnWheelChangedListener {
	private PopupWindow popupWindow;
	private Context mContext;
	private View v;
	private View view;
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Button mBtnConfirm;
	private List<Province> lists = new ArrayList<Province>();
	private SelectCity mCallBack;
	private RelativeLayout layout_parent;

	/**
	 * 所有省
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	/**
	 * key - 区 values - 邮编
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

	/**
	 * 当前省的名称
	 */
	protected String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	protected String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	protected String mCurrentDistrictName = "";

	/**
	 * 当前区的邮政编码
	 */
	protected String mCurrentZipCode = "";

	public void setmCallBack(SelectCity mCallBack) {
		this.mCallBack = mCallBack;
	}

	public CityListPopWindow1(Context context, View v, List<Province> lists) {
		this.mContext = context;
		this.v = v;
		this.lists = lists;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.city_list_layout1, null);
		initView();
		setListener();
		initPopwindow();
		setValue();
	}

	private void initView() {
		layout_parent = (RelativeLayout) view.findViewById(R.id.layout_parent);
		layout_parent.getBackground().setAlpha(150);
		mViewProvince = (WheelView) view.findViewById(R.id.id_province);
		mViewCity = (WheelView) view.findViewById(R.id.id_city);
		mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
		mBtnConfirm = (Button) view.findViewById(R.id.btn_ok);
	}

	private void setListener() {
		// 添加change事件
		mViewProvince.addChangingListener(this);
		// 添加change事件
		mViewCity.addChangingListener(this);
		// 添加change事件
		mViewDistrict.addChangingListener(this);
		// 添加onclick事件
		mBtnConfirm.setOnClickListener(this);
		layout_parent.setOnClickListener(this);
	}

	private void setValue() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(mContext,
				mProvinceDatas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(mContext,
				areas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity
				.setViewAdapter(new ArrayWheelAdapter<String>(mContext, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	protected void initProvinceDatas() {
		List<Province> provinceList = null;

		provinceList = lists;
		// */ 初始化默认选中的省、市、区
		if (provinceList != null && !provinceList.isEmpty()) {
			mCurrentProviceName = provinceList.get(0).getProvince_name();
			List<City> cityList = provinceList.get(0).getCityLists();
			if (cityList != null && !cityList.isEmpty()) {
				mCurrentCityName = cityList.get(0).getCity_name();
				List<Area> districtList = cityList.get(0).getAreaLists();
				mCurrentDistrictName = districtList.get(0).getArea_name();
				mCurrentZipCode = districtList.get(0).getArea_id() + "";
			}
		}
		// */
		mProvinceDatas = new String[provinceList.size()];
		for (int i = 0; i < provinceList.size(); i++) {
			// 遍历所有省的数据
			mProvinceDatas[i] = provinceList.get(i).getProvince_name();
			List<City> cityList = provinceList.get(i).getCityLists();
			String[] cityNames = new String[cityList.size()];
			for (int j = 0; j < cityList.size(); j++) {
				// 遍历省下面的所有市的数据
				cityNames[j] = cityList.get(j).getCity_name();
				List<Area> districtList = cityList.get(j).getAreaLists();
				String[] distrinctNameArray = new String[districtList.size()];
				Area[] distrinctArray = new Area[districtList.size()];
				for (int k = 0; k < districtList.size(); k++) {
					// 遍历市下面所有区/县的数据
					Area districtModel = new Area();
					districtModel.setArea_name(districtList.get(k)
							.getArea_name());
					districtModel.setArea_id(districtList.get(k).getArea_id());
					// 区/县对于的邮编，保存到mZipcodeDatasMap
					mZipcodeDatasMap.put(districtList.get(k).getArea_name(),
							districtList.get(k).getArea_id() + "");
					distrinctArray[k] = districtModel;
					distrinctNameArray[k] = districtModel.getArea_name();
				}
				// 市-区/县的数据，保存到mDistrictDatasMap
				mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
			}
			// 省-市的数据，保存到mCitisDatasMap
			mCitisDatasMap.put(provinceList.get(i).getProvince_name(),
					cityNames);
		}
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
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
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
		switch (v.getId()) {
		case R.id.btn_ok:
			showSelectedResult();
			break;
		case R.id.layout_parent:
			dismiss();
			break;
		default:
			break;
		}
	}

	private void showSelectedResult() {
		dismiss();
		mCallBack.selectCity(mCurrentProviceName + " " + mCurrentCityName + " "
				+ mCurrentDistrictName, mCurrentZipCode);
	}

	public interface SelectCity {
		void selectCity(String region, String area_code);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

}
