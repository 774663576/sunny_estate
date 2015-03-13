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
import com.sunnyestate.popwindow.CityListPopWindow1;
import com.sunnyestate.popwindow.CityListPopWindow1.SelectCity;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.AddAddressTask;
import com.sunnyestate.task.GetCityListTask;
import com.sunnyestate.task.UpdateAddressTask;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.ToastUtil;
import com.sunnyestate.views.MyEditTextDeleteImg;

public class AddAdressActivity extends BaseActivity implements SelectCity {
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
	private CityListPopWindow1 pop;
	private CityListData cityList;
	private List<Province> lists = new ArrayList<Province>();

	private String area_code = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_adress);
		adr = (Adress) getIntent().getSerializableExtra("adress");
		position = getIntent().getIntExtra("position", 0);
		initView();
		if (adr != null) {
			area_code = adr.getAreaid() + "";
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
		edit_adress.setText(adr.getRegion());
		edit_adress_detail.setText(adr.getFulladdress());
		edit_cellphone.setText(adr.getPhone());
		edit_code.setText(adr.getZip());
		edit_name.setText(adr.getConsgneedname());
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

	private void getCityList(final View v) {
		if (lists.size() != 0) {
			pop = new CityListPopWindow1(AddAdressActivity.this, v, lists);
			pop.setmCallBack(AddAdressActivity.this);
			pop.show();
			return;
		}
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
				pop = new CityListPopWindow1(AddAdressActivity.this, v, lists);
				pop.setmCallBack(AddAdressActivity.this);
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
		adress.setFulladdress(adress_detail);
		adress.setPhone(cellphone);
		adress.setZip(code);
		adress.setRegion(adress_str);
		adress.setAreaid(Integer.valueOf(area_code));
		adress.setConsgneedname(name);
		if (adr != null) {
			update(adress);
			return;
		}
		add(adress);
	}

	private void update(final Adress adress) {
		adress.setId(adr.getId());
		UpdateAddressTask task = new UpdateAddressTask();
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
				intent.putExtra("position", position);
				setResult(400, intent);
				finishThisActivity();
			};
		});
		task.executeParallel(adress);
	}

	private void add(final Adress adress) {
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

	@Override
	public void selectCity(String region, String area_code) {
		this.area_code = area_code;
		edit_adress.setText(region);
	}
}
