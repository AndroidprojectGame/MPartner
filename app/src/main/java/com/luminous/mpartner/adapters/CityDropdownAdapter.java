package com.luminous.mpartner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.models.City;

import java.util.List;


public class CityDropdownAdapter extends BaseAdapter {

    private List<City> dealerList;
    private LayoutInflater mInflator;

    public CityDropdownAdapter(Context context, List<City> dealerList) {
        this.dealerList = dealerList;
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

        holder.entryName.setText(position == 0 ? "Select City" : dealerList
                .get(position - 1).getCityName());

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
