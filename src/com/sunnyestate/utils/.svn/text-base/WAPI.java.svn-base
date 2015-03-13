package com.sunnyestate.utils;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

public class WAPI {

	private static WAPI myProfileInstance = null;

	public final static String WAPI_BASE_URL = "http://www.timesyw.com:8080/tvmarket/WAPI";

	// 检查升级
	public final static String WAPI_CHECK_VERSION_URL = WAPI_BASE_URL
			+ "/checkversion.jsp";

	// 下载通知
	public final static String WAPI_DOWNLOAD_NOTIFY_URL = WAPI_BASE_URL
			+ "/download.jsp";

	public static WAPI getInstance() {
		if (myProfileInstance == null)
			myProfileInstance = new WAPI();

		return myProfileInstance;
	}

	public static String addGeneralParams(Context context, String urlString) {
		String newURLString;
		String splitString = "?";
		if (urlString.indexOf("?") >= 0) {
			splitString = "&";
		}

		newURLString = String.format("%s%svercode=%s&vername=%s&client=%s",
				urlString, splitString, Utils.getVersionCode(context),
				Utils.getVersionName(context), "sunnyestate");

		return newURLString;
	}

	// sunnyestate
	public static String http_get_content(String url) {
		HttpGet request = new HttpGet(url);
		// request.setHeader("User-Agent", MyProfile.http_user_agent);

		HttpClient httpClient = new DefaultHttpClient();
		try {
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 15000);
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = EntityUtils.toString(response.getEntity());
				return str;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String get_content_from_remote_url(String url) {

		try {
			String scontent = http_get_content(url);
			if (scontent == null || scontent == "")
				return null;

			return scontent;
		} catch (Exception e) {

		}

		return null;

	}

	public static int save_to_private_file(Context context, String scontent,
			String filename) {
		int ret = 1;
		try {
			FileOutputStream fos = context.openFileOutput(filename,
					Context.MODE_PRIVATE);
			fos.write(scontent.getBytes());
			fos.close();
			ret = 0;
		} catch (Exception e) {
		}

		return ret;

	}

	public static int getJsonInt(JSONObject jsonObject, String name,
			int defaultValue) {
		try {
			int n = jsonObject.getInt(name);

			return n;
		} catch (Exception e) {

		}

		return defaultValue;
	}

	public static String getJsonString(JSONObject jsonObject, String name) {
		try {
			return jsonObject.getString(name);
		} catch (Exception e) {

		}

		return "";
	}

	public static JSONObject getJsonObject(JSONObject jsonObject, String name) {
		try {
			return jsonObject.getJSONObject(name);
		} catch (Exception e) {

		}

		return null;
	}

	public static JSONArray getJsonArray(JSONObject jsonObject, String name) {
		try {
			return jsonObject.getJSONArray(name);
		} catch (Exception e) {

		}

		return null;
	}

	public static int parseVersionInfoResponse(Context context,
			String responseString, ArrayList<String> fieldList) {
		int ret = 1;

		try {
			JSONObject jsonObject = new JSONObject(responseString);
			JSONObject resultObject = jsonObject.getJSONObject("result");
			int code = resultObject.getInt("code");
			if (code == 0) {
				jsonObject = jsonObject.getJSONObject("versioninfo");

				String version = jsonObject.getString("versioncode");
				String desc = jsonObject.getString("desc");
				String force = getJsonString(jsonObject, "force");
				String downloadurl = jsonObject.getString("downloadurl");

				if (force == null || force.length() < 1)
					force = "no";

				fieldList.add(version);
				fieldList.add(desc);
				fieldList.add(downloadurl);
				fieldList.add(force);

				ret = 0;
			}

		} catch (Exception e) {

		}

		return ret;
	}

	public static String getDownloadNotifyURLString(Context context, int appId) {
		String urlString = String.format("%s?appid=%d",
				WAPI_DOWNLOAD_NOTIFY_URL, appId);
		urlString = addGeneralParams(context, urlString);

		return urlString;
	}

}
