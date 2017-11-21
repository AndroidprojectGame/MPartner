package com.luminous.mpartner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.luminous.mpartner.R;
import com.luminous.mpartner.models.Secondary;

import java.util.List;

public class SecondaryAdapter extends BaseAdapter {

	private Context mContext;
	private List<Secondary> mDealers;
	private LayoutInflater mInflater;

	public SecondaryAdapter(Context context, List<Secondary> dealers) {
		mContext = context;
		mDealers = dealers;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mDealers.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_draft, null);
			holder.tvDate = (TextView) convertView
					.findViewById(R.id.item_draft_tv_datetime);
			holder.tvQuantity = (TextView) convertView
					.findViewById(R.id.item_draft_tv_quantity);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.item_draft_tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvDate.setText("Date: "+mDealers.get(position).getDate()+" - "+mDealers.get(position).getTime());
		holder.tvQuantity.setText("Quantity: "+mDealers.get(position).getQuantity());
		holder.tvName.setText(mDealers.get(position).getDealerName());
		return convertView;
	}

	class ViewHolder {
		TextView tvName,tvDate,tvQuantity;
	}

}
