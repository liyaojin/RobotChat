package com.demo.robotchat.test;

import com.demo.robotchat.util.HttpUtils;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestHttpUtils extends AndroidTestCase {
	
	public void testSendInfo(){
		String res=HttpUtils.doGet("给我讲一个笑话");
		Log.e("TAG", res);
		res=HttpUtils.doGet("你好");
		Log.e("TAG", res);
		res=HttpUtils.doGet("你真漂亮");
		Log.e("TAG", res);
	}
}
