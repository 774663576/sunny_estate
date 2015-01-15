package com.sunnyestate;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.sunnyestate.adapter.DingDanAdapter;
import com.sunnyestate.fragment.MyFragment;
import com.sunnyestate.utils.Utils;
import com.sunnyestate.utils.WigdtContorl;

public class PersonalCentenDingDan extends PersonalCenter implements
		OnItemClickListener {
	private ListView mListView;
	private ImageView img_arrow;
	private DingDanAdapter adpater;

	public PersonalCentenDingDan(Context mContext, MyFragment fragment,
			View contentRootView) {
		super(mContext, fragment, contentRootView);
		setValue();
	}

	@Override
	public void initView() {
		img_arrow = (ImageView) findViewById(R.id.img_arrow);
		mListView = (ListView) findViewById(R.id.listView1);
		WigdtContorl.setLayoutX_LinearLayout(img_arrow,
				Utils.getSecreenWidth(mContext) / 3 - fragment.getImgWidth()
						/ 2);
	}

	@Override
	public void setListener() {
		mListView.setOnItemClickListener(this);
	}

	private void setValue() {
		adpater = new DingDanAdapter(mContext);
		mListView.setAdapter(adpater);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		mContext.startActivity(new Intent(mContext, ProductDetailActivity.class));
		Utils.leftOutRightIn(mContext);
	}
}
