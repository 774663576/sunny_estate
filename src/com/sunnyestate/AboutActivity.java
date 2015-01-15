package com.sunnyestate;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutActivity extends BaseActivity {
	private ImageView img_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		setListener();
	}

	private void setListener() {
		img_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finishThisActivity();
			break;

		default:
			break;
		}
	}
}
