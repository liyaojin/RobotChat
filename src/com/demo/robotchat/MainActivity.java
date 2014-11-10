package com.demo.robotchat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.demo.robotchat.adapter.ChatMessageAdapter;
import com.demo.robotchat.bean.ChatMessage;
import com.demo.robotchat.bean.ChatMessage.Type;
import com.demo.robotchat.util.HttpUtils;
import com.demo.rootchat.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView mListView;
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mDatas;

	private EditText mInputMsg;
	private Button mSendMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();

		initDatas();

		initLister();
	}

	private Handler mHandle = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// 等待接收，子线程完成数据的返回
			ChatMessage from = (ChatMessage) msg.obj;
			mDatas.add(from);
			mAdapter.notifyDataSetChanged();
			mListView.setSelection(mDatas.size() - 1);
		}

	};

	private void initLister() {
		mSendMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String toMsg = mInputMsg.getText().toString();
				if (TextUtils.isEmpty(toMsg)) {
					Toast.makeText(MainActivity.this, "发送内容不能为空",
							Toast.LENGTH_SHORT).show();
				}
				ChatMessage toMessge = new ChatMessage();
				toMessge.setDate(new Date());
				toMessge.setMsg(toMsg);
				toMessge.setType(Type.OUTCOMING);
				mDatas.add(toMessge);
				mAdapter.notifyDataSetChanged();

				mInputMsg.setText("");
				new Thread(new Runnable() {
					public void run() {
						ChatMessage from = HttpUtils.sendMessage(toMsg);
						Message message = Message.obtain();
						message.obj = from;
						mHandle.sendMessage(message);
					}
				}).start();
			}
		});
	}

	private void initDatas() {
		mDatas = new ArrayList<ChatMessage>();
		mDatas.add(new ChatMessage("你好，很高兴为你服务", Type.INCOMING, new Date()));
		mDatas.add(new ChatMessage("hello", Type.OUTCOMING, new Date()));
		mAdapter = new ChatMessageAdapter(this, mDatas);
		mListView.setAdapter(mAdapter);
	}

	public void init() {
		mListView = (ListView) findViewById(R.id.id_listview_msg);
		mInputMsg = (EditText) findViewById(R.id.id_input_msg);
		mSendMsg = (Button) findViewById(R.id.id_send_msg);
	}
}
