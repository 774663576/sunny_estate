package com.sunnyestate;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sunnyestate.data.Adress;
import com.sunnyestate.data.CityListData;
import com.sunnyestate.data.Province;
import com.sunnyestate.enums.RetError;
import com.sunnyestate.interfaces.MyEditTextWatcher;
import com.sunnyestate.interfaces.OnEditFocusChangeListener;
import com.sunnyestate.popwindow.CityListPopWindow;
import com.sunnyestate.popwindow.CityListPopWindow.SelectCity;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.AddAddressTask;
import com.sunnyestate.task.GetCityListTask;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.ToastUtil;
import com.sunnyestate.views.MyEditTextDeleteImg;

public class AddAdressActivity extends BaseActivity {
	private MyEditTextDeleteImg edit_name;
	private MyEditTextDeleteImg edit_cellphone;
	private MyEditTextDeleteImg edit_code;
	private MyEditTextDeleteImg edit_adress;
	private MyEditTextDeleteImg edit_adress_detail;
	private Button btn_save;
	private ImageView img_back;
	private Adress adr;
	private int position;

	private Dialog dialog;
	private CityListPopWindow pop;
	private CityListData cityList;
	private List<Province> lists = new ArrayList<Province>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_adress);
		adr = (Adress) getIntent().getSerializableExtra("adress");
		position = getIntent().getIntExtra("position", 0);
		initView();
		if (adr != null) {
			setValue();
		}
	}

	private void initView() {
		edit_adress = (MyEditTextDeleteImg) findViewById(R.id.edit_adress);
		edit_adress_detail = (MyEditTextDeleteImg) findViewById(R.id.edit_adress_detail);
		edit_cellphone = (MyEditTextDeleteImg) findViewById(R.id.edit_cellphone);
		edit_code = (MyEditTextDeleteImg) findViewById(R.id.edit_code);
		edit_name = (MyEditTextDeleteImg) findViewById(R.id.edit_name);
		btn_save = (Button) findViewById(R.id.btn_save);
		img_back = (ImageView) findViewById(R.id.img_back);
		setListener();
	}

	private void setListener() {
		edit_cellphone.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_cellphone, this));
		edit_adress_detail
				.setOnFocusChangeListener(new OnEditFocusChangeListener(
						edit_adress_detail, this));
		edit_code.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_code, this));
		edit_name.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_name, this));
		edit_cellphone.addTextChangedListener(new MyEditTextWatcher(
				edit_cellphone, this));
		edit_adress_detail.addTextChangedListener(new MyEditTextWatcher(
				edit_adress_detail, this));
		edit_code
				.addTextChangedListener(new MyEditTextWatcher(edit_code, this));
		edit_name
				.addTextChangedListener(new MyEditTextWatcher(edit_name, this));
		btn_save.setOnClickListener(this);
		img_back.setOnClickListener(this);
		edit_adress.setOnClickListener(this);

	}

	private void setValue() {
		edit_adress.setText(adr.getProvincename() + " " + adr.getAreaname());
		edit_adress_detail.setText(adr.getDetail());
		edit_cellphone.setText(adr.getPhone());
		edit_code.setText(adr.getPostcode());
		edit_name.setText(adr.getReceiver());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finishThisActivity();
			break;
		case R.id.btn_save:
			save();
			break;
		case R.id.edit_adress:
			getCityList(v);
			break;
		default:
			break;
		}
	}

	private int province_position = 0;
	private int city_position = 0;
	private int area_postion = 0;

	private void getCityList(final View v) {
		cityList = new CityListData();
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		GetCityListTask task = new GetCityListTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				lists = cityList.getLists();
				pop = new CityListPopWindow(AddAdressActivity.this, v, lists);
				pop.setmCallBack(new SelectCity() {
					@Override
					public void selectCity(int province_po, int city_po,
							int area_po) {
						province_position = province_po;
						city_position = city_po;
						area_postion = area_po;
						edit_adress.setText(lists.get(province_po)
								.getProvince_name()
								+ " "
								+ lists.get(province_po).getCityLists()
										.get(city_po).getCity_name()
								+ " "
								+ lists.get(province_po).getCityLists()
										.get(city_po).getAreaLists()
										.get(area_po).getArea_name());
					}
				});
				pop.show();
			}
		});
		task.executeParallel(cityList);
	}

	private void save() {
		String name = edit_name.getText().toString().trim();
		String cellphone = edit_cellphone.getText().toString().trim();
		String code = edit_code.getText().toString().trim();
		String adress_str = edit_adress.getText().toString().trim();
		String adress_detail = edit_adress_detail.getText().toString().trim();
		if (name.length() == 0 || cellphone.length() == 0 || code.length() == 0
				|| adress_str.length() == 0 || adress_detail.length() == 0) {
			ToastUtil.showToast("«ÎÃÓ–¥ÕÍ’˚");
			return;
		}
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		final Adress adress = new Adress();

		adress.setProvincename(adress_str);
		adress.setAreaname("");
		adress.setDetail(adress_detail);
		adress.setPhone(cellphone);
		adress.setPostcode(code);
		adress.setReceiver(name);
		AddAddressTask task = new AddAddressTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("adress", adress);
				int requestCode = 300;
				if (adr != null) {
					intent.putExtra("position", position);
					requestCode = 400;
				}
				setResult(requestCode, intent);
				finishThisActivity();
			};
		});
		task.executeParallel(adress);
	}
}
