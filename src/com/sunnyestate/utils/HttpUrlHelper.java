package com.sunnyestate.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.sunnyestate.enums.RetError;

/**
 * 有关http辅助操作类
 * 
 * 
 */
public class HttpUrlHelper {
	public static final int CONNECTION_TIMEOUT = 10 * 1000;
	public static final int SO_TIMEOUT = 10 * 1000;
	// public static final String DEFAULT_HOST =
	// "http://114.112.48.148:8001/app/index.php/Wapi/v1/"; // 服务器地址
	public static final String DEFAULT_HOST = "http://www.sunnyestate.cn/Wapi/"; // 服务器地址

	/**
	 * get 提交方式 // *
	 * 
	 * @param url
	 *            URL 链接
	 * @return
	 */
	public static String getUrlData(String url) {
		try {
			HttpGet httpRequest = new HttpGet(DEFAULT_HOST + url
					+ getUrlParams());
			System.out.println("result:::::::::::::::"
					+ httpRequest.getURI().toString());

			HttpClient httpclient = new DefaultHttpClient();
			// 请求超时
			httpclient.getParams()
					.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
							CONNECTION_TIMEOUT);
			// 读取超时
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// 判断是否成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				System.out.println("result:::::::::::::::" + result);
				return result;
			} else {
				Utils.print("HttpUrlHelper.getUrlData::status code=:"
						+ httpResponse.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			Utils.print("HttpUrlHelper.getUrlData:" + e);
			return null;
		}
		return null;
	}

	public static String getUrlDataLoadMore(String url) {
		try {
			HttpGet httpRequest = new HttpGet(url + getUrlParams());
			System.out.println("result:::::::::::::::"
					+ httpRequest.getURI().toString());

			HttpClient httpclient = new DefaultHttpClient();
			// 请求超时
			httpclient.getParams()
					.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
							CONNECTION_TIMEOUT);
			// 读取超时
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// 判断是否成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				System.out.println("result:::::::::::::::" + result);
				return result;
			} else {
				Utils.print("HttpUrlHelper.getUrlData::status code=:"
						+ httpResponse.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			Utils.print("HttpUrlHelper.getUrlData:" + e);
			return null;
		}
		return null;
	}

	private static String httpError(RetError error) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("rt", 0);
		params.put("err", error.name());
		JSONObject jsonObjectFromMap = new JSONObject(params);
		return jsonObjectFromMap.toString();

	}

	/**
	 * POST 靖求方式
	 * 
	 * @param url
	 *            URL 链接
	 * @param pairs
	 *            传递的参数
	 * @return
	 */
	public static String postUrlData(String url, List<NameValuePair> pairs) {
		try {
			HttpPost httpPost = new HttpPost(url);
			HttpClient httpclient = new DefaultHttpClient();
			// 请求超时
			httpclient.getParams()
					.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
							CONNECTION_TIMEOUT);
			// 读取超时
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			if (pairs != null) {
				// 请求参数设置
				HttpEntity httpentity = new UrlEncodedFormEntity(pairs, "utf8");
				httpPost.setEntity(httpentity);
			}
			HttpResponse httpResponse = httpclient.execute(httpPost);
			// 判断是否成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(httpResponse.getEntity());
			} else {
				Utils.print("HttpUrlHelper.postUrlData:status code=:"
						+ httpResponse.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			Utils.print("HttpUrlHelper.postUrlData:" + e);

			return null;

		}
		return null;
	}

	/**
	 * 通过默认的host发送post请求
	 * 
	 * @param map
	 *            需要传的参数
	 * @param api
	 *            要访问的api
	 * @return
	 */
	public static String postData(Map<String, Object> map, String api) {
		return postData(map, api, HttpUrlHelper.DEFAULT_HOST);
	}

	/**
	 * 发送post请求，可以单独设置host
	 * 
	 * @param map
	 * @param api
	 * @param host
	 * @return
	 */
	public static String postData(Map<String, Object> map, String api,
			String host) {
		for (String key : map.keySet()) {
			Utils.print("RequestMap==" + "[param] " + key + ", " + map.get(key)
					+ "        " + api);

		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Iterator<?> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry testDemo = (Map.Entry) iterator.next();
			Object key = testDemo.getKey();
			Object value = testDemo.getValue();
			if (key != null && value != null) {
				params.add(new BasicNameValuePair(key.toString(), value
						.toString()));
			}
		}

		return HttpUrlHelper.postUrlData(createUrl(host, api), params);
	}

	private static String createUrl(String host, String api) {
		String url = host;
		if (host.charAt(host.length() - 1) != '/') {
			url = host + "/";
		}
		if (api.charAt(0) == '/') {
			url += api.substring(1);
		} else {
			url += api;
		}
		return url;
	}

	/**
	 * 上传图片请求
	 * 
	 * @param host
	 * @param api
	 * @param map
	 * @param file
	 * @param pkey
	 * @return
	 */
	public static String postDataFile(String api, Map<String, Object> map,
			File file) {
		return postDataWithFile(createUrl(DEFAULT_HOST, api), map, file);
	}

	/**
	 * 上传图片请求
	 * 
	 * @param url
	 * @param map
	 * @param file
	 * @param pkey
	 * @return
	 */
	public static String postDataWithFile(String url, Map<String, Object> map,
			File file) {
		HttpPost post = new HttpPost(url);
		HttpClient client = new DefaultHttpClient();
		MultipartEntity mpEntity = new MultipartEntity();
		Iterator<?> iterator = map.entrySet().iterator();
		if (file != null && file.exists()) {
			FileBody fileBody = new FileBody(file);
			mpEntity.addPart("image", fileBody);
		}
		try {

			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry testDemo = (Map.Entry) iterator.next();
				Object key = testDemo.getKey();
				Object value = testDemo.getValue();
				mpEntity.addPart(
						key.toString(),
						new StringBody(value.toString(), Charset
								.forName("UTF-8")));
			}
			post.setEntity(mpEntity);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), "utf-8");
			} else {
				Utils.print("HttpUrlHelper.upLoadPic:status code=:"
						+ response.getStatusLine().getStatusCode());

			}
		} catch (Exception e) {
			Utils.print("HttpUrlHelper.upLoadPic:" + e);
			return httpError(RetError.NETWORK_ERROR);

		} finally {
			if (mpEntity != null) {
				try {
					mpEntity.consumeContent();
				} catch (Exception e) {
					Utils.print("HttpUrlHelper.upLoadPic:" + e);
				}
			}
			client.getConnectionManager().shutdown();
		}

		return httpError(RetError.INVALID);
	}

	/**
	 * 上传图片数组
	 * 
	 * @param url
	 * @param map
	 * @param file
	 * @param pkey
	 * @return
	 */
	public static String upLoadPicArray(String host, String url,
			Map<String, Object> map, List<File> files) {
		HttpPost post = new HttpPost(createUrl(host, url));
		HttpClient client = new DefaultHttpClient();
		MultipartEntity mpEntity = new MultipartEntity();
		Iterator<?> iterator = map.entrySet().iterator();
		for (int i = 0; i < files.size(); i++) {
			FileBody fileBody = new FileBody(files.get(i), "image/pjpeg");
			mpEntity.addPart("image" + i, fileBody);
		}
		try {
			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry testDemo = (Map.Entry) iterator.next();
				Object key = testDemo.getKey();
				Object value = testDemo.getValue();
				mpEntity.addPart(
						key.toString(),
						new StringBody(value.toString(), Charset
								.forName("UTF-8")));
			}
			post.setEntity(mpEntity);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), "utf-8");
			} else {
				Utils.print("HttpUrlHelper.upLoadPic:status code=:"
						+ response.getStatusLine().getStatusCode());

			}
		} catch (Exception e) {
			Utils.print("HttpUrlHelper.upLoadPic:" + e);

			return httpError(RetError.NETWORK_ERROR);

		} finally {
			if (mpEntity != null) {
				try {
					mpEntity.consumeContent();
				} catch (Exception e) {
					Utils.print("HttpUrlHelper.upLoadPic:" + e);

				}
			}
			client.getConnectionManager().shutdown();
		}

		return httpError(RetError.INVALID);
	}

	public static String uploadArray(String urls, Map<String, Object> map,
			List<File> files, String pkey) {
		String result = "";
		try {
			// 定义数据分隔线
			String BOUNDARY = "------------------------7dc2fd5c0894";
			// 定义最后数据分隔线
			byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			URL url = new URL(urls);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());

			// name参数
			StringBuffer params = new StringBuffer();
			for (Entry<String, Object> entry : map.entrySet()) {
				Utils.print("uploadArrayuploadArray:" + entry.getKey() + "="
						+ entry.getValue());
				params.append("--" + BOUNDARY + "\r\n");
				params.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"" + "\r\n\r\n");
				params.append(entry.getValue());
				params.append("\r\n");
			}
			Utils.print("up::::::::::::::::::--" + params.toString());
			out.write(params.toString().getBytes());
			for (int i = 0; i < files.size(); i++) {
				File file = files.get(i);
				StringBuilder sb = new StringBuilder();
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data;name=\"image\";filename=\""
						+ file.getName() + "\"\r\n");
				// 这里不能漏掉，根据文件类型来来做处理，由于上传的是图片，所以这里可以写成image/pjpeg
				sb.append("Content-Type:image/pjpeg\r\n\r\n");
				out.write(sb.toString().getBytes());

				DataInputStream in = new DataInputStream(new FileInputStream(
						file));
				int bytes = 0;
				byte[] bufferOut = new byte[1024];
				while ((bytes = in.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, bytes);
				}
				out.write("\r\n".getBytes());
				in.close();
			}
			out.write(end_data);
			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				Utils.print("up::::::::::::::::::" + line);
				result = line;
			}
			Utils.print("up::::::::::::::::::code" + conn.getResponseCode());

		} catch (Exception e) {
			Utils.print("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		return result;
	}

	public String sendPost(String url, String params) {
		String result = "";
		URL realurl = null;
		InputStream in = null;
		HttpURLConnection conn = null;
		try {
			realurl = new URL(url);
			conn = (HttpURLConnection) realurl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			PrintWriter pw = new PrintWriter(conn.getOutputStream());
			pw.print(params);
			pw.flush();
			pw.close();
			in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String line = "";
			while ((line = reader.readLine()) != null) {
				Utils.print("up::::::::::::::::::" + line);
				result += line;
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static String getUrlParams() {
		String str = "SEAPILYON";
		str = MD5.MD5_32(str);
		String time = System.currentTimeMillis() + "";
		str = str.substring(0, 20) + time;
		str = MD5.MD5_32(str);
		StringBuilder sb = new StringBuilder(str);
		sb.reverse();
		return "/apikey/" + sb.toString() + "/apitime/" + time;
	}
}
