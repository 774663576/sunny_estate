package com.sunnyestate;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.sunnyestate.adapter.AddressAdapter;
import com.sunnyestate.contentprovider.AdressProvider;
import com.sunnyestate.data.AbstractData.Status;
import com.sunnyestate.data.Adress;
import com.sunnyestate.db.DBUtils;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.Utils;

public class AdressActivity extends BaseActivity {
	private Button btn_add_adress;
	private ImageView img_back;
	private ListView mListView;

	private List<Adress> lists = new ArrayList<Adress>();

	private AddressAdapter adapter;

	private AsyncQueryHandler asyncQuery;

	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adress);
		initView();
		setValue();
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
		initQuery();
	}

	private void initQuery() {
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		asyncQuery = new MyAsyncQueryHandler(this.getContentResolver());
		String[] projection = { AdressProvider.AdressColumns.ID,
				AdressProvider.AdressColumns.ADRESS,
				AdressProvider.AdressColumns.ADRESS_DETAIL,
				AdressProvider.AdressColumns.CELLPHONE,
				AdressProvider.AdressColumns.CODE,
				AdressProvider.AdressColumns.DEFAULT_ADRESS,
				AdressProvider.AdressColumns.NAME, }; // 查询的列
		asyncQuery.startQuery(0, null,
				AdressProvider.AdressColumns.CONTENT_URI, projection, null,
				null, null);
	}

	/**
	 * 数据库异步查询类AsyncQueryHandler
	 * 
	 * 
	 */
	private class MyAsyncQueryHandler extends AsyncQueryHandler {
		public MyAsyncQueryHandler(ContentResolver cr) {
			super(cr);
		}

		/**
		 * 查询结束的回调函数
		 */
		@Override
		protected void onQueryComplete(int token, Object cookie,
				final Cursor cursor) {
			if (dialog != null) {
				dialog.dismiss();
			}
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				for (int i = 0; i < cursor.getCount(); i++) {
					int id = cursor.getInt(0);
					String adress = cursor.getString(1);
					String adress_detail = cursor.getString(2);
					String cellphone = cursor.getString(3);
					String code = cursor.getString(4);
					int default_adress = cursor.getInt(5);
					String name = cursor.getString(6);
					Adress addre = new Adress();
					addre.setAdress(adress);
					addre.setAdress_detail(adress_detail);
					addre.setCellphone(cellphone);
					addre.setCode(code);
					addre.setDefault_adress(default_adress);
					addre.setName(name);
					addre.setId(id);
					lists.add(addre);
					cursor.moveToNext();
				}
				adapter.notifyDataSetChanged();
			} else {
			}
		}
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
				lists.get(0).setDefault_adress(1);
				lists.get(0).write(DBUtils.getDBsa(2));
			}
		} else if (requestCode == 400) {
			int position = data.getIntExtra("position", 0);
			Adress adress = (Adress) data.getSerializableExtra("adress");
			lists.get(position).setAdress(adress.getAdress());
			lists.get(position).setAdress_detail(adress.getAdress_detail());
			lists.get(position).setCellphone(adress.getCellphone());
			lists.get(position).setCode(adress.getCode());
			lists.get(position).setName(adress.getName());
			adapter.notifyDataSetChanged();
			lists.get(position).setStatus(Status.UPDATE);
			lists.get(position).write(DBUtils.getDBsa(2));
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
