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
	 * ����ʡ
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - ʡ value - ��
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - �� values - ��
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	/**
	 * key - �� values - �ʱ�
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

	/**
	 * ��ǰʡ������
	 */
	protected String mCurrentProviceName;
	/**
	 * ��ǰ�е�����
	 */
	protected String mCurrentCityName;
	/**
	 * ��ǰ��������
	 */
	protected String mCurrentDistrictName = "";

	/**
	 * ��ǰ������������
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
		// ���change�¼�
		mViewProvince.addChangingListener(this);
		// ���change�¼�
		mViewCity.addChangingListener(this);
		// ���change�¼�
		mViewDistrict.addChangingListener(this);
		// ���onclick�¼�
		mBtnConfirm.setOnClickListener(this);
		layout_parent.setOnClickListener(this);
	}

	private void setValue() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(mContext,
				mProvinceDatas));
		// ���ÿɼ���Ŀ����
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	/**
	 * ���ݵ�ǰ���У�������WheelView����Ϣ
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
	 * ���ݵ�ǰ��ʡ��������WheelView����Ϣ
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
		// */ ��ʼ��Ĭ��ѡ�е�ʡ���С���
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
			// ��������ʡ������
			mProvinceDatas[i] = provinceList.get(i).getProvince_name();
			List<City> cityList = provinceList.get(i).getCityLists();
			String[] cityNames = new String[cityList.size()];
			for (int j = 0; j < cityList.size(); j++) {
				// ����ʡ����������е�����
				cityNames[j] = cityList.get(j).getCity_name();
				List<Area> districtList = cityList.get(j).getAreaLists();
				String[] distrinctNameArray = new String[districtList.size()];
				Area[] distrinctArray = new Area[districtList.size()];
				for (int k = 0; k < districtList.size(); k++) {
					// ����������������/�ص�����
					Area districtModel = new Area();
					districtModel.setArea_name(districtList.get(k)
							.getArea_name());
					districtModel.setArea_id(districtList.get(k).getArea_id());
					// ��/�ض��ڵ��ʱ࣬���浽mZipcodeDatasMap
					mZipcodeDatasMap.put(districtList.get(k).getArea_name(),
							districtList.get(k).getArea_id() + "");
					distrinctArray[k] = districtModel;
					distrinctNameArray[k] = districtModel.getArea_name();
				}
				// ��-��/�ص����ݣ����浽mDistrictDatasMap
				mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
			}
			// ʡ-�е����ݣ����浽mCitisDatasMap
			mCitisDatasMap.put(provinceList.get(i).getProvince_name(),
					cityNames);
		}
	}

	/**
	 * ��ʼ��popwindow
	 */
	@SuppressWarnings("deprecation")
	private void initPopwindow() {
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı�����������ģ�
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// popupWindow.setAnimationStyle(R.style.AnimBottom);
	}

	/**
	 * popwindow����ʾ
	 */
	public void show() {
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		// ʹ��ۼ�
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
