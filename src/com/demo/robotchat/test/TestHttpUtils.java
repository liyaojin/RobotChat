package com.demo.robotchat.test;

import com.demo.robotchat.util.HttpUtils;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestHttpUtils extends AndroidTestCase {
	
	public void testSendInfo(){
		String res=HttpUtils.doGet("���ҽ�һ��Ц��");
		Log.e("TAG", res);
		res=HttpUtils.doGet("���");
		Log.e("TAG", res);
		res=HttpUtils.doGet("����Ư��");
		Log.e("TAG", res);
	}
}
