package com.sunnyestate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sunnyestate.data.Adress;
import com.sunnyestate.db.DBUtils;
import com.sunnyestate.interfaces.MyEditTextWatcher;
import com.sunnyestate.interfaces.OnEditFocusChangeListener;
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
		edit_adress.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_adress, this));
		edit_code.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_code, this));
		edit_name.setOnFocusChangeListener(new OnEditFocusChangeListener(
				edit_name, this));
		edit_cellphone.addTextChangedListener(new MyEditTextWatcher(
				edit_cellphone, this));
		edit_adress_detail.addTextChangedListener(new MyEditTextWatcher(
				edit_adress_detail, this));
		edit_adress.addTextChangedListener(new MyEditTextWatcher(edit_adress,
				this));
		edit_code
				.addTextChangedListener(new MyEditTextWatcher(edit_code, this));
		edit_name
				.addTextChangedListener(new MyEditTextWatcher(edit_name, this));
		btn_save.setOnClickListener(this);
		img_back.setOnClickListener(this);

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
		default:
			break;
		}
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
		Adress adress = new Adress();

		adress.setProvincename(adress_str);
		adress.setAreaname("");
		adress.setDetail(adress_detail);
		adress.setPhone(cellphone);
		adress.setPostcode(code);
		adress.setReceiver(name);
		Intent intent = new Intent();
		intent.putExtra("adress", adress);
		int requestCode = 300;
		if (adr != null) {
			intent.putExtra("position", position);
			requestCode = 400;
		} else {
			adress.write(DBUtils.getDBsa(2));
		}

		setResult(requestCode, intent);
		finishThisActivity();
	}
}
