package com.luminous.mpartner.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.luminous.mpartner.R;
import com.luminous.mpartner.ui.activities.DraftDetailActivity;
import com.luminous.mpartner.ui.activities.SummaryActivity;

import java.util.ArrayList;
import java.util.List;

public class SerialAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> mSerials;
	private List<Boolean> mSerialStockList;
	private LayoutInflater mInflater;

	public SerialAdapter(Context context, List<String> serials,
			ArrayList<Boolean> serialStockList) {
		mContext = context;
		mSerials = serials;
		mSerialStockList = serialStockList;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mSerials.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_serial, null);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.item_serial_tv_serial);
			holder.ivDelete = (ImageView) convertView
					.findViewById(R.id.item_serial_iv_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvName.setText("" + mSerials.get(position));
		holder.ivDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mContext instanceof SummaryActivity)
				{
					SummaryActivity.getInstance().removeSerial(position);
				}
				else if(mContext instanceof DraftDetailActivity)
				{
					DraftDetailActivity.getInstance().removeSerial(position);
				}
				
			}
		});
		
		convertView.setBackgroundColor(mSerialStockList.get(position)? Color.WHITE: Color.parseColor("#FFB9C0"));
		
		return convertView;
	}


	class ViewHolder {
		TextView tvName;
		ImageView ivDelete;
	}

}
