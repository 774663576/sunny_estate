package com.sunnyestate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.sunnyestate.MyApplation;

/**
 * * SharedPreferences 的公具类
 * 
 * @author teeker_bin
 * 
 */
public class SharedUtils {
	private static final String SP_NAME = "sunnyestate";
	private static SharedPreferences sharedPreferences = MyApplation
			.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
	private static Editor editor = sharedPreferences.edit();
	public static final String SP_UID = "userid";
	private static final String SP_USER_NAME = "username";
	private static final String SP_USER_PASSWORD = "user_password";
	private static final String SP_PASSWORD_KEY = "password_key";

	public static String getString(String key, String defaultValue) {
		return sharedPreferences.getString(key, defaultValue);
	}

	public static int getInt(String key, int defaultValue) {
		return sharedPreferences.getInt(key, defaultValue);
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		return sharedPreferences.getBoolean(key, defaultValue);
	}

	public static void setString(String key, String value) {
		editor.putString(key, value);
		editor.commit();

	}

	public static long getLong(String key, long defaultValue) {
		return sharedPreferences.getLong(key, defaultValue);

	}

	public static void setLong(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}

	public static void setInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public static void setBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void setUid(String uid) {
		setString(SP_UID, uid);
	}

	public static String getUid() {
		return getString(SP_UID, "0");
	}

	public static int getIntUid() {
		String uid = getUid();
		if (uid.length() > 0) {
			return Integer.parseInt(uid);
		}
		return 0;
	}

	public static void setUserName(String value) {
		editor.putString(SP_USER_NAME, value);
		editor.commit();
	}

	public static String getUserName() {
		return sharedPreferences.getString(SP_USER_NAME, "");

	}

	public static void setUserPassword(String value) {
		editor.putString(SP_USER_PASSWORD, value);
		editor.commit();
	}

	public static String getUserPassword() {
		return sharedPreferences.getString(SP_USER_PASSWORD, "");

	}

	public static void setPasswordKey(String value) {
		editor.putString(SP_PASSWORD_KEY, value);
		editor.commit();
	}

	public static String getPasswordKey() {
		return sharedPreferences.getString(SP_PASSWORD_KEY, "");

	}

	public static void setHeadUrl(String value) {
		editor.putString("headurl", value);
		editor.commit();
	}

	public static String getHeadUrl() {
		return sharedPreferences.getString("headurl", "");

	}

	public static void setScore(int value) {
		editor.putInt("score", value);
		editor.commit();
	}

	public static int getScore() {
		return sharedPreferences.getInt("score", 0);

	}

	public static void setLevel(String value) {
		editor.putString("levels", value);
		editor.commit();
	}

	public static String getLevel() {
		return sharedPreferences.getString("levels", "");

	}

	public static void clearData() {
		editor.clear();
		editor.commit();
	}
}
