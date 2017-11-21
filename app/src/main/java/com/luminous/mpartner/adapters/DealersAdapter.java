package com.luminous.mpartner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.luminous.mpartner.R;
import com.luminous.mpartner.models.Dealer;

import java.util.List;

public class DealersAdapter extends BaseAdapter {

	private Context mContext;
	private List<Dealer> mDealers;
	private LayoutInflater mInflater;

	public DealersAdapter(Context context, List<Dealer> dealers) {
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
			convertView = mInflater.inflate(R.layout.item_dealer, null);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.item_dealer_tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvName.setText(mDealers.get(position).getDealerName());
		return convertView;
	}

	class ViewHolder {
		TextView tvName;
	}

}
