package com.sunnyestate;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class WebPageActivity extends BaseActivity {
	private WebView wb;
	private ImageView img_back;
	private String url = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_page);
		url = getIntent().getStringExtra("url");
		initView();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		wb = (WebView) findViewById(R.id.webView1);
		wb.setWebViewClient(new Default());
		wb.loadUrl(url);
		setListener();
	}

	private class Default extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			wb.loadUrl(url);
			return true;
		}
	}

	private void setListener() {
		img_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		finishThisActivity();
	}
}
