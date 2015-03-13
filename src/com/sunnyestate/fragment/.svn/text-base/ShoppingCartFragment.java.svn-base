package com.sunnyestate.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunnyestate.FillOrderActivity;
import com.sunnyestate.LoginAndRegisterActivity;
import com.sunnyestate.R;
import com.sunnyestate.adapter.ShoppingCarAdapter;
import com.sunnyestate.adapter.ShoppingCarAdapter.SelectListener;
import com.sunnyestate.contentprovider.ShoppingCarProvider;
import com.sunnyestate.data.AbstractData.Status;
import com.sunnyestate.data.ShoppingCar;
import com.sunnyestate.db.DBUtils;
import com.sunnyestate.task.ConfirmDialog;
import com.sunnyestate.utils.Constants;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.SharedUtils;
import com.sunnyestate.utils.ToastUtil;
import com.sunnyestate.utils.Utils;

import fynn.app.PromptDialog;

public class ShoppingCartFragment extends Fragment implements OnClickListener,
		SelectListener {
	private LinearLayout layout_no_shopping;
	private Button btn_go_buy;
	private ListView mListView;
	private RelativeLayout layout_jiesuan;
	private Button btn_jiesuan;
	private TextView txt_totlal_price;
	private CheckBox check_all;
	private Button btn_del;
	private Button btn_edit;
	private RelativeLayout layout_del;

	private AsyncQueryHandler asyncQuery;

	private List<ShoppingCar> lists = new ArrayList<ShoppingCar>();

	private ShoppingCarAdapter adapter;

	private Dialog dialog;

	private boolean isEdit = false;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (dialog != null) {
					dialog.dismiss();
				}
				adapter.notifyDataSetChanged();
				if (lists.size() == 0) {
					layout_no_shopping.setVisibility(View.VISIBLE);
					btn_edit.setVisibility(View.GONE);
					check_all.setChecked(false);
					layout_del.setVisibility(View.GONE);
					mListView.setVisibility(View.GONE);
				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.shopping_car_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setValue();
		registerBoradcastReceiver();
	}

	private void initView() {
		mListView = (ListView) getView().findViewById(R.id.listView1);
		layout_no_shopping = (LinearLayout) getView().findViewById(
				R.id.layout_no_shopping);
		btn_go_buy = (Button) getView().findViewById(R.id.btn_go_buy);
		layout_jiesuan = (RelativeLayout) getView().findViewById(
				R.id.layout_jiesuan);
		btn_jiesuan = (Button) getView().findViewById(R.id.btn_jiesuan);
		txt_totlal_price = (TextView) getView().findViewById(
				R.id.txt_total_price);
		check_all = (CheckBox) getView().findViewById(R.id.check_all);
		btn_del = (Button) getView().findViewById(R.id.btn_delte);
		btn_edit = (Button) getView().findViewById(R.id.btn_edit);
		layout_del = (RelativeLayout) getView().findViewById(R.id.layout_del);
		setListener();
	}

	private void setListener() {
		btn_go_buy.setOnClickListener(this);
		btn_jiesuan.setOnClickListener(this);
		btn_del.setOnClickListener(this);
		btn_edit.setOnClickListener(this);
		check_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean flag) {
				for (ShoppingCar car : lists) {
					car.setSelect(flag);
				}
				adapter.notifyDataSetChanged();
			}
		});
	}

	private void setValue() {
		txt_totlal_price.setText("￥" + "0.00");
		adapter = new ShoppingCarAdapter(getActivity(), lists);
		adapter.setmCallBack(this);
		mListView.setAdapter(adapter);
		initQuery();

	}

	private void initQuery() {
		dialog = DialogUtil.createLoadingDialog(getActivity());
		dialog.show();
		asyncQuery = new MyAsyncQueryHandler(getActivity().getContentResolver());
		String[] projection = { ShoppingCarProvider.ShoppingCarColumns.COUNT,
				ShoppingCarProvider.ShoppingCarColumns.IMG_URL,
				ShoppingCarProvider.ShoppingCarColumns.PRICE,
				ShoppingCarProvider.ShoppingCarColumns.SHOPPING_ID,
				ShoppingCarProvider.ShoppingCarColumns.TITLE,
				ShoppingCarProvider.ShoppingCarColumns.MEMBER_PRICE }; // 查询的列
		asyncQuery.startQuery(0, null,
				ShoppingCarProvider.ShoppingCarColumns.CONTENT_URI, projection,
				null, null, null);
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
				layout_jiesuan.setVisibility(View.VISIBLE);
				btn_edit.setVisibility(View.VISIBLE);
				cursor.moveToFirst();
				for (int i = 0; i < cursor.getCount(); i++) {
					int count = cursor.getInt(0);
					String img_url = cursor.getString(1);
					int price = cursor.getInt(2);
					int shopping_id = cursor.getInt(3);
					String title = cursor.getString(4);
					int member_price = cursor.getInt(5);
					ShoppingCar car = new ShoppingCar();
					car.setCount(count);
					car.setImg_url(img_url);
					car.setPrice(price);
					car.setShopping_id(shopping_id);
					car.setTitle(title);
					car.setMember_price(member_price);
					lists.add(car);
					cursor.moveToNext();
				}
				adapter.notifyDataSetChanged();
			} else {
				layout_no_shopping.setVisibility(View.VISIBLE);
				mListView.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_go_buy:
			getActivity().sendBroadcast(new Intent(Constants.GO_BUY_SHOPPING));
			break;
		case R.id.btn_edit:
			isEdit = !isEdit;
			if (isEdit) {
				btn_edit.setText("完成");
				layout_del.setVisibility(View.VISIBLE);
				layout_jiesuan.setVisibility(View.GONE);
			} else {
				btn_edit.setText("编辑");
				layout_del.setVisibility(View.GONE);
			}
			for (ShoppingCar car : lists) {
				car.setSelect(false);
				car.setEdit(false);
			}
			adapter.notifyDataSetChanged();
			break;
		case R.id.btn_delte:
			delShopping();
			break;
		case R.id.btn_jiesuan:
			List<ShoppingCar> selectShopping = new ArrayList<ShoppingCar>();
			for (ShoppingCar car : lists) {
				if (car.isSelect()) {
					selectShopping.add(car);
				}
			}
			if (selectShopping.size() == 0) {
				ToastUtil.showToast("请选择要结算的商品");
				return;
			}
			if ("".equals(SharedUtils.getPasswordKey())) {
				promptLoginDialog();
				return;
			}

			startActivity(new Intent(getActivity(), FillOrderActivity.class)
					.putExtra("datadetail", (Serializable) selectShopping));
			Utils.leftOutRightIn(getActivity());
			break;
		default:
			break;
		}
	}

	private void promptLoginDialog() {
		PromptDialog.Builder dialog = DialogUtil.confirmDialog(getActivity(),
				"您还没有登录,请先登录在继续操作", "登录", "取消", new ConfirmDialog() {

					@Override
					public void onOKClick() {
						startActivity(new Intent(getActivity(),
								LoginAndRegisterActivity.class));
						Utils.leftOutRightIn(getActivity());
					}

					@Override
					public void onCancleClick() {

					}
				});
		dialog.show();

	}

	private void delShopping() {
		// dialog = DialogUtil.createLoadingDialog(getActivity(), "删除中...");
		// dialog.show();
		new Thread() {
			public void run() {
				for (Iterator<ShoppingCar> iter = lists.iterator(); iter
						.hasNext();) {
					ShoppingCar car = iter.next();
					if (car.isSelect()) {
						car.setStatus(Status.DEL);
						car.write(DBUtils.getDBsa(2));
						iter.remove();
					}
				}
				mHandler.sendEmptyMessage(0);

			}
		}.start();
	}

	/**
	 * 注册该广播
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Constants.ADD_SHOPPING_CAR);
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	/**
	 * 定义广播
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			layout_no_shopping.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
			btn_edit.setVisibility(View.VISIBLE);
			layout_jiesuan.setVisibility(View.VISIBLE);
			if (action.equals(Constants.ADD_SHOPPING_CAR)) {
				ShoppingCar car = (ShoppingCar) intent
						.getSerializableExtra("shoppingcar");
				lists.add(0, car);
				adapter.notifyDataSetChanged();
			}
		}
	};

	public void onDestroyView() {
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	public void onSelect() {
		if (isEdit) {
			return;
		}
		int count = 0;
		float total_price = 0;

		for (ShoppingCar car : lists) {
			if (car.isSelect()) {
				count++;
				total_price += car.getMember_price() * car.getCount();
			}
		}
		if (count == 0) {
			btn_jiesuan.setText("结算");
			txt_totlal_price.setText("￥" + total_price + "0");
			total_price = 0;
		} else {
			layout_jiesuan.setVisibility(View.VISIBLE);
			btn_jiesuan.setText("结算(" + count + ")");
			txt_totlal_price.setText("￥" + total_price + "0");
		}
	};

}
