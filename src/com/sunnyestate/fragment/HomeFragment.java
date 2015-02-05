package com.sunnyestate.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sunnyestate.ProductDetailActivity;
import com.sunnyestate.R;
import com.sunnyestate.data.CategoryDataDetail;
import com.sunnyestate.data.HomeCategoryItem;
import com.sunnyestate.data.HomeData;
import com.sunnyestate.enums.RetError;
import com.sunnyestate.task.AbstractTaskPostCallBack;
import com.sunnyestate.task.HomeTask;
import com.sunnyestate.utils.DialogUtil;
import com.sunnyestate.utils.UniversalImageLoadTool;
import com.sunnyestate.utils.Utils;
import com.sunnyestate.views.ExpandGridView;

public class HomeFragment extends Fragment implements OnPageChangeListener {
	private ExpandableListView mEListView;
	private List<ImageView> views = new ArrayList<ImageView>();
	private List<ImageView> dos = new ArrayList<ImageView>();
	private HomeData datas = new HomeData();
	private MyAdapter adapter;
	private ViewPager mViewpager;
	private View line;
	private ImageView img_logo;
	private LinearLayout dot_layout;

	private Dialog dialog;

	private RelativeLayout layout;

	private ScrollView mScrollView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.tab_home_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		refushData();
	}

	private void initView() {
		line = (View) getView().findViewById(R.id.line);
		mScrollView = (ScrollView) getView().findViewById(R.id.scrollView1);
		dot_layout = (LinearLayout) getView().findViewById(R.id.dot_layout);
		img_logo = (ImageView) getView().findViewById(R.id.img_logo);
		Utils.getFocus(img_logo);
		mEListView = (ExpandableListView) getView().findViewById(
				R.id.expandableListView1);
		mEListView.setGroupIndicator(null);
		layout = (RelativeLayout) getView().findViewById(R.id.parent);
		setListenser();
	}

	private void setListenser() {
		mEListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1,
					int arg2, long arg3) {
				return true;
			}
		});
	}

	private void refushData() {
		dialog = DialogUtil.createLoadingDialog(getActivity());
		dialog.show();
		HomeTask task = new HomeTask();
		task.setmCallBack(new AbstractTaskPostCallBack<RetError>() {
			@Override
			public void taskFinish(RetError result) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != RetError.NONE) {
					return;
				}
				initHeader();
				adapter = new MyAdapter();
				mEListView.setAdapter(adapter);
				line.setVisibility(View.VISIBLE);
				int groupCount = mEListView.getCount();
				for (int i = 0; i < groupCount; i++) {
					mEListView.expandGroup(i);
				}
			}
		});
		task.executeParallel(datas);
	}

	private void initHeader() {
		mViewpager = (ViewPager) getView().findViewById(R.id.mViewPager);
		mViewpager.setOnPageChangeListener(this);
		initDos(datas.getLsitRecomments().size());
		for (int i = 0; i < datas.getLsitRecomments().size(); i++) {
			ImageView img = new ImageView(getActivity());
			img.setScaleType(ScaleType.FIT_XY);
			UniversalImageLoadTool.disPlay(datas.getLsitRecomments().get(i)
					.getImg_url(), img, R.drawable.home_head_img);
			views.add(img);
		}
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) layout
				.getLayoutParams();
		linearParams.height = Utils.getSecreenHeight(getActivity()) / 3 + 50;
		mViewpager.setAdapter(new MyPagerAdapter());
	}

	private void initDos(int size) {
		for (int i = 0; i < size; i++) {
			ImageView dotImg = new ImageView(getActivity());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(8, 8, 8, 8);
			dotImg.setLayoutParams(lp);
			if (i == 0) {
				dotImg.setImageResource(R.drawable.dot_select);
			} else {
				dotImg.setImageResource(R.drawable.dot_nomal);
			}
			dot_layout.addView(dotImg);
			dos.add(dotImg);
		}

	}

	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1));
			return views.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));

		}
	}

	private class MyAdapter extends BaseExpandableListAdapter {
		int mPosX;
		int mCurrentPosX;
		int mPosY;
		int mCurrentPosY;

		public Object getChild(int groupPosition, int childPosition) {
			return datas.getList_categorys().get(groupPosition).getItems()
					.get(childPosition);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return 1;
		}

		public View getChildView(final int groupPosition,
				final int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			ViewChildHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.home_listview_child_layout, null);
				holder = new ViewChildHolder();
				holder.gridView = (ExpandGridView) convertView
						.findViewById(R.id.gridView1);
				// holder.mListView = (HorizontalListView) convertView
				// .findViewById(R.id.horizontal_listView);
				convertView.setTag(holder);
			} else {
				holder = (ViewChildHolder) convertView.getTag();
			}
			// holder.mListView.setLayoutParams(new LinearLayout.LayoutParams(
			// LayoutParams.MATCH_PARENT, Utils
			// .getSecreenHeight(getActivity()) / 3 + 50));
			// holder.mListView.setAdapter(new GridViewAdapter(datas
			// .getList_categorys().get(groupPosition).getItems()));

			// holder.mListView.setOnTouchListener(new View.OnTouchListener() {
			//
			// @Override
			// public boolean onTouch(View v, MotionEvent event) {
			// if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// mPosX = (int) event.getX();
			// mPosY = (int) event.getY();
			// } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// mCurrentPosX = (int) event.getX() - mPosX;
			// mCurrentPosY = (int) event.getY() - mPosY;
			// mPosX = (int) event.getX();
			// mPosY = (int) event.getY();
			// if (mCurrentPosX - mPosX > 0
			// && Math.abs(mCurrentPosY - mPosY) < 10) {
			//
			// System.out.println("aa:::::::::向右的按下位置" + mPosX
			// + "移动位置" + mCurrentPosX);
			//
			// } else if (mCurrentPosX - mPosX < 0
			// && Math.abs(mCurrentPosY - mPosY) < 10) {
			//
			// System.out.println("aa:::::::::向左的按下位置" + mPosX
			// + "移动位置" + mCurrentPosX);
			//
			// }
			// // mScrollView.requestDisallowInterceptTouchEvent(true);
			// } else {
			// // mScrollView.requestDisallowInterceptTouchEvent(false);
			// }
			// return false;
			// }
			// });
			holder.gridView.setAdapter(new GridViewAdapter(datas
					.getList_categorys().get(groupPosition).getItems()));
			holder.gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					HomeCategoryItem category = datas.getList_categorys()
							.get(groupPosition).getItems().get(position);
					CategoryDataDetail detail = new CategoryDataDetail();
					detail.setId(category.getId());
					detail.setDefaultimg(category.getDefaultimg());
					detail.setPrice(category.getPrice());
					detail.setOriginalprice(category.getOriginalprice());
					detail.setProducttile(category.getProducttile());
					startActivity(new Intent(getActivity(),
							ProductDetailActivity.class).putExtra(
							"categorydatadetail", detail));
					Utils.leftOutRightIn(getActivity());
				}
			});
			return convertView;
		}

		public Object getGroup(int groupPosition) {
			return datas.getList_categorys().get(groupPosition).getTitle();
		}

		public int getGroupCount() {
			return datas.getList_categorys().size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			ViewGroupHolder holder;
			if (convertView == null) {
				holder = new ViewGroupHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.home_listview_group_layout, null);
				holder.group_title = (TextView) convertView
						.findViewById(R.id.group_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewGroupHolder) convertView.getTag();
			}
			holder.group_title.setText((String) getGroup(groupPosition));
			return convertView;
		}

		public boolean hasStableIds() {
			return true;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	class ViewGroupHolder {
		TextView group_title;
	}

	class ViewChildHolder {
		ExpandGridView gridView;
		// HorizontalListView mListView;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int index) {
		for (ImageView dot : dos) {
			dot.setImageResource(R.drawable.dot_nomal);
		}
		dos.get(index).setImageResource(R.drawable.dot_select);
	}

	class GridViewAdapter extends BaseAdapter {
		private List<HomeCategoryItem> items;

		public GridViewAdapter(List<HomeCategoryItem> items) {
			this.items = items;
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.home_gridview_item, null);
				holder = new ViewHolder();
				holder.img_logo = (ImageView) convertView
						.findViewById(R.id.img_logo);
				holder.txt_desc = (TextView) convertView
						.findViewById(R.id.txt_desc);
				holder.txt_title = (TextView) convertView
						.findViewById(R.id.txt_title);
				holder.item_layout = (LinearLayout) convertView
						.findViewById(R.id.item_layout);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.item_layout.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, Utils
							.getSecreenHeight(getActivity()) / 3 + 100));
			holder.img_logo.setLayoutParams(new LinearLayout.LayoutParams(Utils
					.getSecreenHeight(getActivity()) / 5, Utils
					.getSecreenHeight(getActivity()) / 4));
			// holder.item_layout.setLayoutParams(new LinearLayout.LayoutParams(
			// Utils.getSecreenWidth(getActivity()) / 3,
			// LayoutParams.MATCH_PARENT));
			holder.txt_desc.setText(items.get(position).getProducttile());
			holder.txt_title.setText(items.get(position).getBrandstitle());
			UniversalImageLoadTool.disPlay(items.get(position).getDefaultimg(),
					holder.img_logo, R.drawable.img1);
			return convertView;
		}
	}

	class ViewHolder {
		LinearLayout item_layout;
		ImageView img_logo;
		TextView txt_title;
		TextView txt_desc;
	}
}
