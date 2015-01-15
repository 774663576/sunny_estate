package com.sunnyestate;

import android.content.AsyncQueryHandler;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunnyestate.contentprovider.AdressProvider;
import com.sunnyestate.data.Adress;
import com.sunnyestate.data.ShoppingCar;
import com.sunnyestate.utils.Constants;
import com.sunnyestate.utils.UniversalImageLoadTool;
import com.sunnyestate.utils.Utils;

/**
 * 填写订单
 */
public class FillOrderActivity extends BaseActivity {
	private ShoppingCar detail;

	private ImageView img_logo;
	private TextView txt_price;
	private TextView txt_count;
	private TextView txt_title;
	private Button btn_tijiaodingdan;
	private TextView txt_fukuan;
	private TextView txt_heji;
	private TextView txt_yunfei;
	private ImageView img_back;
	private RelativeLayout layotu_select_adress;
	private TextView txt_adress;
	private TextView txt_adress_detail;
	private TextView txt_name;

	private AsyncQueryHandler asyncQuery;

	private Adress adress = new Adress();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fill_order);
		detail = (ShoppingCar) getIntent().getSerializableExtra("datadetail");
		initView();
		setValue();
		initQuery();
		registerBoradcastReceiver();
	}

	private void initView() {
		txt_adress = (TextView) findViewById(R.id.txt_address);
		txt_adress_detail = (TextView) findViewById(R.id.txt_address_detail);
		txt_name = (TextView) findViewById(R.id.txt_name);
		img_logo = (ImageView) findViewById(R.id.img_logo);
		txt_count = (TextView) findViewById(R.id.txt_count);
		txt_price = (TextView) findViewById(R.id.txt_price);
		txt_title = (TextView) findViewById(R.id.txt_title);
		btn_tijiaodingdan = (Button) findViewById(R.id.btn_tijiaodingdan);
		txt_fukuan = (TextView) findViewById(R.id.txt_fukuan);
		txt_heji = (TextView) findViewById(R.id.txt_heji);
		txt_yunfei = (TextView) findViewById(R.id.txt_yunfei);
		img_back = (ImageView) findViewById(R.id.img_back);
		layotu_select_adress = (RelativeLayout) findViewById(R.id.layout_add_address);
		setListener();
	}

	private void setListener() {
		btn_tijiaodingdan.setOnClickListener(this);
		img_back.setOnClickListener(this);
		layotu_select_adress.setOnClickListener(this);
	}

	private void setValue() {
		UniversalImageLoadTool.disPlay(detail.getImg_url(), img_logo,
				R.drawable.img1);
		txt_count.setText("数量： " + detail.getCount());
		txt_price.setText("￥" + detail.getPrice() / 100 + ".00");
		txt_title.setText(detail.getTitle());
		txt_heji.setText(Html.fromHtml("共" + detail.getCount()
				+ "件商品,合计: <font color=#c00000>￥" + detail.getPrice() / 100
				* detail.getCount() + ".00</font> "));
		txt_yunfei.setText(Html.fromHtml("运费： <font color=#c00000>￥" + 0
				+ ".00</font> "));
		txt_fukuan.setText(Html.fromHtml("实付款: <font color=#c00000>￥"
				+ detail.getPrice() / 100 * detail.getCount() + ".00</font> "));
	}

	private void initQuery() {
		asyncQuery = new MyAsyncQueryHandler(this.getContentResolver());
		String[] projection = { AdressProvider.AdressColumns.ID,
				AdressProvider.AdressColumns.ADRESS,
				AdressProvider.AdressColumns.ADRESS_DETAIL,
				AdressProvider.AdressColumns.CELLPHONE,
				AdressProvider.AdressColumns.CODE,
				AdressProvider.AdressColumns.DEFAULT_ADRESS,
				AdressProvider.AdressColumns.NAME, }; // 查询的列
		asyncQuery.startQuery(0, null,
				AdressProvider.AdressColumns.CONTENT_URI, projection,
				AdressProvider.AdressColumns.DEFAULT_ADRESS + "= ?",
				new String[] { "1" }, null);
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

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				int id = cursor.getInt(0);
				String adress_str = cursor.getString(1);
				String adress_detail = cursor.getString(2);
				String cellphone = cursor.getString(3);
				String code = cursor.getString(4);
				int default_adress = cursor.getInt(5);
				String name = cursor.getString(6);
				adress.setAdress(adress_str);
				adress.setAdress_detail(adress_detail);
				adress.setCellphone(cellphone);
				adress.setCode(code);
				adress.setDefault_adress(default_adress);
				adress.setName(name);
				adress.setId(id);
				setAdressValue();
			} else {
			}
		}
	}

	public void setAdressValue() {
		txt_adress_detail.setText(adress.getAdress_detail());
		txt_adress.setText(adress.getAdress());
		txt_name.setText(adress.getName() + "  " + adress.getCellphone());
	}

	/**
	 * 注册该广播
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Constants.NO_ADRESS);
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	/**
	 * 定义广播
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.NO_ADRESS)) {
				adress = null;
				txt_adress_detail.setText("还没有添加地址,点击添加");
				txt_adress.setText("");
				txt_name.setText("");
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		if (requestCode == 300) {
			Adress adress = (Adress) data.getSerializableExtra("adress");
			this.adress = adress;
			setAdressValue();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finishThisActivity();
			break;
		case R.id.layout_add_address:
			startActivityForResult(new Intent(this, AdressActivity.class), 300);
			Utils.leftOutRightIn(this);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}
}
