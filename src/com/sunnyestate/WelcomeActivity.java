package com.sunnyestate;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class WelcomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);
		new Thread() {
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				startActivity(new Intent(WelcomeActivity.this,
						MainActivity.class));

			}
		}.start();

	}

}
