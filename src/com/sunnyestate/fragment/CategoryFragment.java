package com.sunnyestate.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sunnyestate.ProductDetailActivity;
import com.sunnyestate.R;
import com.sunnyestate.adapter.CategoryAdapter;
import com.sunnyestate.data.Category;
import com.sunnyestate.data.CategoryData;
import com.sunnyestate.data.CategoryDataDetail;
import com.sunnyestate.data.CategoryFilterItem;
import com.sunnyestate.enums.RetError;
import com.sunnyestate.popwindow.TitleMenuPopwindow;
import com.sunnyestate.popwindow.TitleMenuPopwindow.OnlistOnclick;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.CategoryDataTask;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.Utils;
import com.sunnyestate.views.CategoryGridView;
import com.sunnyestate.views.ScrollBottomScrollView;
import com.sunnyestate.views.ScrollBottomScrollView.ScrollBottomListener;

public class CategoryFragment extends Fragment implements OnClickListener,
		OnTouchListener, OnItemClickListener {
	private CategoryAdapter adapter;

	private CategoryGridView mGridView;
	private Button btn_title;
	private LinearLayout layout_putaojiu;
	private Button btn_pinpai;
	private Button btn_tiandu;
	private Button btn_pinzhong;
	private LinearLayout layout_more;
	private ScrollBottomScrollView scrollView;

	private TitleMenuPopwindow pop;

	private CategoryData data = new CategoryData();

	private Dialog dialog;

	private List<String> popList = new ArrayList<String>();

	private int cid = 0;
	private int filter_id = 0;
	private int filter_item_id = 0;
	private boolean isLoading = false;
	private List<CategoryDataDetail> detailList = new ArrayList<CategoryDataDetail>();
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				layout_more.setVisibility(View.GONE);
				detailList.addAll(data.getDetailList());
				adapter.notifyDataSetChanged();
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
		return inflater.inflate(R.layout.tab_category_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new CategoryAdapter(getActivity(), detailList);
		initView();
		popList.add("全部");
		refushData(true, cid, filter_id, filter_item_id);
	}

	private void initView() {
		scrollView = (ScrollBottomScrollView) getView().findViewById(
				R.id.scrollView1);
		layout_more = (LinearLayout) getView().findViewById(R.id.layout_more);
		layout_putaojiu = (LinearLayout) getView().findViewById(
				R.id.layout_putaojiu);
		btn_pinpai = (Button) getView().findViewById(R.id.btn_pinpai);
		btn_tiandu = (Button) getView().findViewById(R.id.btn_tiandu);
		btn_pinzhong = (Button) getView().findViewById(R.id.btn_pinzhong);
		btn_title = (Button) getView().findViewById(R.id.txt_title);
		mGridView = (CategoryGridView) getView().findViewById(R.id.gridview);
		mGridView.setAdapter(adapter);
		setListener();
	}

	private void setListener() {
		mGridView.setOnItemClickListener(this);
		btn_pinpai.setOnTouchListener(this);
		btn_pinzhong.setOnTouchListener(this);
		btn_tiandu.setOnTouchListener(this);
		btn_title.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					titleDown();
					showPop(v);
					break;
				}
				return true;
			}
		});
		scrollView.setScrollBottomListener(new ScrollBottomListener() {

			@Override
			public void scrollBottom() {
				System.out.println("aaaaaaaaaaaa" + data.getNextPage()
						+ "        " + isLoading);
				if (!isLoading && !"".equals(data.getNextPage())) {
					System.out.println("aaaaaaaaaaaa==" + data.getNextPage()
							+ "        " + isLoading);
					layout_more.setVisibility(View.VISIBLE); // 加载数据
					loadMore();
				}
			}
		});
	}

	private void loadMore() {
		isLoading = true;
		new Thread() {
			public void run() {
				RetError ret = data.loadMore(data.getNextPage());
				isLoading = false;
				if (ret == RetError.NONE) {
					mHandler.sendEmptyMessage(0);
				}
			}
		}.start();

	}

	private void refushData(final boolean refush_menu, int cid, int filter_id,
			int filter_item_id) {
		dialog = DialogUtil.createLoadingDialog(getActivity());
		dialog.setCancelable(false);
		dialog.show();
		CategoryDataTask task = new CategoryDataTask(refush_menu, cid,
				filter_id, filter_item_id);
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				if (refush_menu) {
					for (Category category : data.getCategoryList()) {
						popList.add(category.getTitle());
					}
				}
				detailList.clear();
				detailList.addAll(data.getDetailList());
				adapter.notifyDataSetChanged();
			}
		});
		task.executeParallel(data);
	}

	private void titleDown() {
		btn_title.setBackgroundResource(R.drawable.category_title_background);
		btn_title.setTextColor(R.color.title_color);
		btn_title.getBackground().setAlpha(125);
		Drawable img;
		Resources res = getResources();
		img = res.getDrawable(R.drawable.arrow_down_select);
		img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
		btn_title.setCompoundDrawables(null, null, img, null);
	}

	private void titleUp() {
		Drawable img;
		Resources res = getResources();
		img = res.getDrawable(R.drawable.arrow_down);
		img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
		btn_title.setCompoundDrawables(null, null, img, null);
		btn_title.setBackgroundResource(0);
		btn_title.setTextColor(Color.WHITE);

	}

	private void showPop(View v) {
		pop = new TitleMenuPopwindow(getActivity(), v, popList);
		pop.show();
		pop.setOnlistOnclick(new OnlistOnclick() {
			@Override
			public void onclick(int position) {
				pop = null;
				String str = popList.get(position);
				btn_title.setText(str);
				if ("葡萄酒".equals(str)) {
					layout_putaojiu.setVisibility(View.VISIBLE);
				} else {
					layout_putaojiu.setVisibility(View.GONE);
				}
				cid = position;
				filter_id = 0;
				filter_item_id = 0;
				refushData(false, cid, filter_id, filter_item_id);
			}

			@Override
			public void dissmissListener() {
				titleUp();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}

	private void category_btn_down(Button btn) {
		btn.setTextColor(getResources().getColor(R.color.title_color));
		btn.getBackground().setAlpha(20);
	}

	private void category_btn_up(Button btn) {
		btn.setBackgroundColor(Color.BLACK);
		btn.setTextColor(Color.WHITE);

	}

	private void showBtnPopWindow(final Button v) {
		final List<String> menuList = new ArrayList<String>();
		List<CategoryFilterItem> filter_item_list = null;
		switch (v.getId()) {
		case R.id.btn_pinpai:
			filter_item_list = data.getCategoryList().get(0).getFilter_list()
					.get(0).getFilter_item_list();
			filter_id = data.getCategoryList().get(0).getFilter_list().get(0)
					.getId();
			break;
		case R.id.btn_tiandu:
			filter_item_list = data.getCategoryList().get(0).getFilter_list()
					.get(1).getFilter_item_list();
			filter_id = data.getCategoryList().get(0).getFilter_list().get(1)
					.getId();
			break;
		case R.id.btn_pinzhong:
			filter_item_list = data.getCategoryList().get(0).getFilter_list()
					.get(2).getFilter_item_list();
			filter_id = data.getCategoryList().get(0).getFilter_list().get(2)
					.getId();
			break;
		default:
			break;
		}

		for (CategoryFilterItem item : filter_item_list) {
			menuList.add(item.getName());
		}
		pop = new TitleMenuPopwindow(getActivity(), v, menuList);
		pop.show();
		pop.setOnlistOnclick(new OnlistOnclick() {
			@Override
			public void onclick(int position) {
				pop = null;
				String str = menuList.get(position);
				v.setText(str);
				filter_item_id = data.getCategoryList().get(0).getFilter_list()
						.get(filter_id - 1).getFilter_item_list().get(position)
						.getId();
				refushData(false, cid, filter_id, filter_item_id);
			}

			@Override
			public void dissmissListener() {
				category_btn_up(v);
			}
		});
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			category_btn_down((Button) v);
			showBtnPopWindow((Button) v);
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

		startActivity(new Intent(getActivity(), ProductDetailActivity.class)
				.putExtra("categorydatadetail", detailList.get(position)));
		Utils.leftOutRightIn(getActivity());
	}
}
