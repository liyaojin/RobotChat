package com.demo.robotchat.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.demo.robotchat.bean.ChatMessage;
import com.demo.robotchat.bean.ChatMessage.Type;

import com.demo.rootchat.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatMessageAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ChatMessage> mDatas;

	public ChatMessageAdapter(Context context, List<ChatMessage> mDatas) {
		this.mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
	}

	@Override
	public int getItemViewType(int position) {
		ChatMessage chatMessage = mDatas.get(position);
		// 接受消息INCOMING 发送信息OUTCOMING
		if (chatMessage.getType() == Type.INCOMING) {
			return 0;
		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ChatMessage chatMessage = mDatas.get(position);
		viewHoder viewHoder = new viewHoder();
		if (convertView == null) {
			if (getItemViewType(position) == 0) {
				convertView = mInflater.inflate(R.layout.item_from_msg, parent,
						false);
				viewHoder.mDate = (TextView) convertView
						.findViewById(R.id.id_from_date);
				viewHoder.mMsg = (TextView) convertView
						.findViewById(R.id.id_from_msg_info);
			} else {
				convertView = mInflater.inflate(R.layout.item_to_msg, parent,
						false);
				viewHoder.mDate = (TextView) convertView
						.findViewById(R.id.id_to_msg_date);
				viewHoder.mMsg = (TextView) convertView
						.findViewById(R.id.id_to_msg_info);
			}
			convertView.setTag(viewHoder);
		} else {
			viewHoder = (viewHoder) convertView.getTag();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		viewHoder.mDate.setText(df.format(chatMessage.getDate()));
		viewHoder.mMsg.setText(chatMessage.getMsg());
		return convertView;
	}

	private final class viewHoder {
		TextView mDate;
		TextView mMsg;
	}
}
