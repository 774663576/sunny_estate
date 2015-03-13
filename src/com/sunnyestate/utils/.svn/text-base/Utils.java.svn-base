package com.sunnyestate.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.view.View;

import com.sunnyestate.MyApplation;
import com.sunnyestate.R;
import com.sunnyestate.db.DataBaseHelper;

public class Utils {
	public static void execCmd(String cmd) {
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 
	 * @Description 检查网络状态
	 * @param context
	 * @return boolean
	 */
	public static boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) MyApplation
				.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static void print(String str) {
		System.out.println(str);
	}

	/**
	 * 获取焦点
	 * 
	 * @param v
	 */
	public static void getFocus(View v) {
		v.setFocusable(true);
		v.setFocusableInTouchMode(true);
		v.requestFocus();
		v.requestFocusFromTouch();
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getSecreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getSecreenHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		int screenHeight = dm.heightPixels;
		return screenHeight;
	}

	// 设置切换动画，从右边进入，左边退出

	public static void leftOutRightIn(Context context) {
		((Activity) context).overridePendingTransition(R.anim.in_from_right,
				R.anim.out_to_left);
	}

	/**
	 * 右侧退出
	 * 
	 * @param context
	 */
	public static void rightOut(Context context) {
		((Activity) context).overridePendingTransition(R.anim.right_in,
				R.anim.right_out);

	}

	/**
	 * 获取应用的当前版本号
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getVersionName(Context context) {
		String version = "";
		try {

			// 获取packagemanager的实例
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			version = packInfo.versionName;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	public static String getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = String.format("%d", info.versionCode);
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/** * 按名字清除本应用数据库 * * @param context * @param dbName */
	public static void cleanDatabaseByName(Context context) {
		context.deleteDatabase(DataBaseHelper.DATABASE_NAME
				+ SharedUtils.getUid());
	}
}
