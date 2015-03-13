package com.sunnyestate.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 用于发送HTTP请求的工具类
 * 
 * @author *
 */
public class HttpClientUtil {

	public static final int CONNECTION_TIMEOUT = 10 * 1000;
	public static final int SO_TIMEOUT = 10 * 1000;
	private String requestURL;

	/**
	 * @throws Exception
	 * @throws TimeOutExcetipion
	 * @throws ConnetcionTimeOutException
	 */
	public String sendToBoss() {
		// long begin = System.currentTimeMillis();
		HttpClient client = new HttpClient();
		String result = "";

		PostMethod method = null;
		// method.setRequestHeader("Connection", "close");

		try {
			// ByteArrayRequestEntity entity = new
			// ByteArrayRequestEntity(xml.getBytes("utf-8"));
			method = new PostMethod(requestURL);
			// method.setRequestEntity(entity);
			method.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			// 设置请求包头文件
			method.setRequestHeader("Content-Type", "text/xml;charset=utf-8");
			// method.setRequestHeader("Content-Length",
			// String.valueOf(entity.getContentLength()));
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(CONNECTION_TIMEOUT);
			client.getHttpConnectionManager().getParams()
					.setSoTimeout(SO_TIMEOUT);
			client.executeMethod(method);
			// System.out.println(statusCode);
			result = readStream(method.getResponseBodyAsStream(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}

		return result;
	}

	/**
	 * @throws Exception
	 * @throws TimeOutExcetipion
	 * @throws ConnetcionTimeOutException
	 */
	public static String sendToBoss(String requestURL, String xml) {
		System.out.println("Postresult:::::::::::::====" + requestURL);
		// log.debug("发送XML请求文件："+xml);
		// long begin = System.currentTimeMillis();
		HttpClient client = new HttpClient();
		String result = "";

		PostMethod method = null;
		// method.setRequestHeader("Connection", "close");

		try {
			ByteArrayRequestEntity entity = new ByteArrayRequestEntity(
					xml.getBytes("utf-8"));
			method = new PostMethod(requestURL);
			method.setRequestEntity(entity);
			method.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			// 设置请求包头文件
			method.setRequestHeader("Content-Type", "text/xml;charset=utf-8");
			method.setRequestHeader("Content-Length",
					String.valueOf(entity.getContentLength()));
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(CONNECTION_TIMEOUT);
			client.getHttpConnectionManager().getParams()
					.setSoTimeout(SO_TIMEOUT);
			client.executeMethod(method);
			// System.out.println(statusCode);
			result = readStream(method.getResponseBodyAsStream(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Postresult:::::::::::::====" + e.toString());
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}

		return result;
	}

	public InputStream sendToBossRStream(String xml) throws Exception {
		HttpClient client = new HttpClient();

		PostMethod method = new PostMethod(requestURL);
		// method.setRequestHeader("Connection", "close");
		ByteArrayRequestEntity entity = new ByteArrayRequestEntity(
				xml.getBytes("utf-8"));
		try {

			// RequestEntity entity = new StringRequestEntity(xml, "text/xml",
			// "utf-8");
			method.setRequestEntity(entity);

			method.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			// 设置请求包头文件
			method.setRequestHeader("Content-Type", "text/xml;charset=utf-8");
			// System.out.println("发送XML文件请求长度：===========："+String.valueOf(entity.getContentLength()));
			method.setRequestHeader("Content-Length",
					String.valueOf(entity.getContentLength()));
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(CONNECTION_TIMEOUT);
			client.getHttpConnectionManager().getParams()
					.setSoTimeout(SO_TIMEOUT);
			int statusCode = client.executeMethod(method);
			// System.out.println(statusCode);
			return method.getResponseBodyAsStream();

		} catch (HttpException e) {
			e.printStackTrace();

			// throw new
			// Exception("java.net.SocketTimeoutException: Read timed out");
		} catch (IOException e) {
			e.printStackTrace();

			// throw new
			// Exception("java.net.SocketException: Connection reset");
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
			// ((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();
		}
		return null;
	}

	public static String readStream(InputStream in, String charset)
			throws IOException {
		StringBuilder buf = new StringBuilder();
		int ch = -1;
		Reader reader = new InputStreamReader(new BufferedInputStream(in),
				charset);
		while (-1 != (ch = reader.read())) {
			buf.append((char) ch);
			System.out.println("Postresult:::::::::::::====" + buf.toString());

		}
		return buf.toString();
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

}
