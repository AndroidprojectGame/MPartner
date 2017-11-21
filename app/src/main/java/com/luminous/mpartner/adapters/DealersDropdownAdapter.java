package com.luminous.mpartner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.constants.AppConstants;
import com.luminous.mpartner.models.Dealer;
import com.luminous.mpartner.utils.AppSharedPrefrences;

import java.util.List;


public class DealersDropdownAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Dealer> dealerList;
    private LayoutInflater mInflator;

    public DealersDropdownAdapter(Context context, List<Dealer> dealerList) {
        this.dealerList = dealerList;
        mContext = context;
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.textview, null);
            holder = new ViewHolder();
            holder.entryName = (TextView) convertView
                    .findViewById(R.id.TextView01);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (AppSharedPrefrences.getInstance(mContext).getUserType().equalsIgnoreCase(AppConstants.USER_DISTRIBUTER)) {
            holder.entryName.setText(position == 0 ? "Select Dealer" : dealerList
                    .get(position - 1).getDealerName());
        }
        else
        {
            holder.entryName.setText(position == 0 ? "Select Distributer" : dealerList
                    .get(position - 1).getDealerName());
        }


        return convertView;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getCount() {
        return dealerList.size() + 1;
    }

    class ViewHolder {
        TextView entryName;
    }

}
