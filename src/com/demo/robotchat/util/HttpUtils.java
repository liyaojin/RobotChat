package com.demo.robotchat.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.http.HttpStatus;

import com.demo.robotchat.bean.ChatMessage;
import com.demo.robotchat.bean.ChatMessage.Type;
import com.demo.robotchat.bean.Result;
import com.google.gson.Gson;

public class HttpUtils {

	private static String API_KEY = "8d7bd4859a5b0453f7f7a78c8ef25448";
	private static String URL = "http://www.tuling123.com/openapi/api";

	/**
	 * 发送一个消息 并得到返回消息
	 * 
	 * @param msg
	 * @return
	 */
	public static ChatMessage sendMessage(String msg) {
		ChatMessage message = new ChatMessage();
		String url = setParams(msg);
		String res = doGet(url);
		Gson gson = new Gson();
		Result result = gson.fromJson(res, Result.class);

		if (result.getCode() > 400000 || result.getText() == null
				|| result.getText().trim().equals("")) {
			message.setMsg("该功能等待开发...");
		} else {
			message.setMsg(result.getText());
		}
		message.setType(Type.INCOMING);
		message.setDate(new Date());

		return message;
	}

	/**
	 * 拼接url
	 * 
	 * @param msg
	 * @return
	 */
	private static String setParams(String msg) {
		try {
			msg = URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return URL + "?key=" + API_KEY + "&info=" + msg;
	}

	/**
	 * doGet 返回请求数据
	 * 
	 * @param urlStr
	 * @return
	 */
	public static String doGet(String msg) {
		String result = "";
		String url = setParams(msg);
		java.net.URL urlNet = null;
		HttpURLConnection connection = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			urlNet = new java.net.URL(url);
			connection = (HttpURLConnection) urlNet.openConnection();
			connection.setReadTimeout(5 * 1000);
			connection.setConnectTimeout(5 * 1000);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() == HttpStatus.SC_OK) {
				is = connection.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];
				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				result = baos.toString();
			} else {

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			connection.disconnect();
		}

		return result;
	}
}
