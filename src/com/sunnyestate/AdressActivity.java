package com.sunnyestate;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.sunnyestate.adapter.AddressAdapter;
import com.sunnyestate.data.AbstractData.Status;
import com.sunnyestate.data.Adress;
import com.sunnyestate.data.AdressList;
import com.sunnyestate.db.DBUtils;
import com.sunnyestate.enums.RetError;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.GetAddressTask;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.Utils;

public class AdressActivity extends BaseActivity {
	private Button btn_add_adress;
	private ImageView img_back;
	private ListView mListView;

	private List<Adress> lists = new ArrayList<Adress>();
	private AdressList adressList;

	private AddressAdapter adapter;

	private AsyncQueryHandler asyncQuery;

	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adress);
		adressList = new AdressList(0);
		initView();
		setValue();
		refushData();
	}

	private void initView() {
		btn_add_adress = (Button) findViewById(R.id.btn_add_address);
		img_back = (ImageView) findViewById(R.id.img_back);
		mListView = (ListView) findViewById(R.id.listView1);
		setListener();
	}

	private void setListener() {
		btn_add_adress.setOnClickListener(this);
		img_back.setOnClickListener(this);
	}

	private void setValue() {
		adapter = new AddressAdapter(this, lists);
		mListView.setAdapter(adapter);
	}

	private void refushData() {
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		GetAddressTask task = new GetAddressTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				lists.addAll(adressList.getLists());
				adapter.notifyDataSetChanged();
			}
		});
		task.executeParallel(adressList);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		if (requestCode == 300) {
			Adress adress = (Adress) data.getSerializableExtra("adress");
			lists.add(0, adress);
			adapter.notifyDataSetChanged();
			if (lists.size() == 1) {
				lists.get(0).setStatus(Status.UPDATE);
				lists.get(0).setIsdefault(1);
				// lists.get(0).write(DBUtils.getDBsa(2));
			}
		} else if (requestCode == 400) {
			int position = data.getIntExtra("position", 0);
			Adress adress = (Adress) data.getSerializableExtra("adress");
			lists.get(position).setProvincename(adress.getProvincename());
			lists.get(position).setAreaname(adress.getAreaname());
			lists.get(position).setDetail(adress.getDetail());
			lists.get(position).setPhone(adress.getPhone());
			lists.get(position).setPostcode(adress.getPostcode());
			lists.get(position).setReceiver(adress.getReceiver());
			adapter.notifyDataSetChanged();
			// lists.get(position).setStatus(Status.UPDATE);
			// lists.get(position).write(DBUtils.getDBsa(2));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_address:
			startActivityForResult(new Intent(this, AddAdressActivity.class),
					300);
			Utils.leftOutRightIn(this);
			break;
		case R.id.img_back:
			finishThisActivity();
			break;
		default:
			break;
		}
	}
}
