package com.sunnyestate;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunnyestate.adapter.FillOrderAdapter;
import com.sunnyestate.data.Adress;
import com.sunnyestate.data.AdressList;
import com.sunnyestate.data.OrderData;
import com.sunnyestate.data.ShoppingCar;
import com.sunnyestate.enums.RetError;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.GetAddressTask;
import com.sunnyestate.task.SubmitOrderTask;
import com.sunnyestate.utils.Constants;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.SharedUtils;
import com.sunnyestate.utils.ToastUtil;
import com.sunnyestate.utils.Utils;

/**
 * 填写订单
 */
public class FillOrderActivity extends BaseActivity {

	private ListView mListView;
	private Button btn_tijiaodingdan;
	private TextView txt_fukuan;
	private TextView txt_heji;
	private TextView txt_yunfei;
	private ImageView img_back;
	private RelativeLayout layotu_select_adress;
	private TextView txt_adress;
	private TextView txt_adress_detail;
	private TextView txt_name;

	private Adress adress = new Adress();
	private Dialog dialog;
	private AdressList adressList;
	private List<ShoppingCar> lists = new ArrayList<ShoppingCar>();
	private FillOrderAdapter adapter;

	private float pay_price;
	private String pay_subject = "";
	private String pay_body = "";

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fill_order);
		lists = (List<ShoppingCar>) getIntent().getSerializableExtra(
				"datadetail");
		initView();
		setValue();
		registerBoradcastReceiver();
		refushAddress();
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.listView);
		txt_adress = (TextView) findViewById(R.id.txt_address);
		txt_adress_detail = (TextView) findViewById(R.id.txt_address_detail);
		txt_name = (TextView) findViewById(R.id.txt_name);
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
		adapter = new FillOrderAdapter(this, lists);
		mListView.setAdapter(adapter);
		int count = 0;
		int price = 0;
		for (ShoppingCar car : lists) {
			count += car.getCount();
			price += car.getMember_price() * car.getCount();
			pay_body += car.getTitle() + ",";
		}
		if (lists.size() > 0) {
			pay_subject = lists.get(0).getTitle() + "(共" + lists.size() + "件)";
		}
		txt_heji.setText(Html.fromHtml("共" + count
				+ "件商品,合计: <font color=#c00000>￥" + price + ".00</font> "));
		txt_yunfei.setText(Html.fromHtml("运费： <font color=#c00000>￥" + 0
				+ ".00</font> "));
		txt_fukuan.setText(Html.fromHtml("实付款: <font color=#c00000>￥" + price
				+ ".00</font> "));
		pay_price = price;
	}

	private void refushAddress() {
		adressList = new AdressList(SharedUtils.getIntUid());
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
				if (adressList.getLists().size() == 0) {
					return;
				}
				adress = adressList.getLists().get(0);
				setAdressValue();
			}
		});
		task.executeParallel(adressList);
	}

	public void setAdressValue() {
		txt_adress_detail.setText(adress.getFulladdress());
		txt_adress.setText(adress.getRegion());
		txt_name.setText(adress.getConsgneedname() + "  " + adress.getPhone());
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
		case R.id.btn_tijiaodingdan:
			submitDingdan();
			break;
		default:
			break;
		}
	}

	private void submitDingdan() {
		if (adress == null) {
			ToastUtil.showToast("请先选择地址");
			return;
		}
		dialog = DialogUtil.createLoadingDialog(this);
		dialog.show();
		SubmitOrderTask task = new SubmitOrderTask(lists);
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				System.out.println("pay_code:::::::::::::"
						+ result.getMessage());
				ToastUtil.showToast("下单成功");
				sendBroadcast(new Intent(Constants.REFUSH_MY_ORDER_LIST));
				startActivity(new Intent(FillOrderActivity.this,
						SelectPayActivity.class)
						.putExtra("pay_price", pay_price)
						.putExtra("pay_code", result.getMessage())
						.putExtra("pay_subject", pay_subject)
						.putExtra("pay_body", pay_body));
				finishThisActivity();
				Utils.leftOutRightIn(FillOrderActivity.this);

			}
		});
		OrderData order = new OrderData();
		order.setId(adress.getId());
		task.executeParallel(order);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}
}
