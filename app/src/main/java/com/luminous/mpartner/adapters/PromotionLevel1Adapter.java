package com.luminous.mpartner.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.models.Promotion;
import com.luminous.mpartner.utils.FontProvider;

import java.util.List;


public class PromotionLevel1Adapter extends BaseAdapter {


    private final Typeface tfSemibold;
    private final List<Promotion> mPromotions;
    private LayoutInflater mInflater;

    public PromotionLevel1Adapter(Context mContext, List<Promotion> promotions) {
        this.mInflater = LayoutInflater.from(mContext);
        tfSemibold = FontProvider.getInstance().tfSemibold;
        mPromotions = promotions;
    }

    @Override
    public int getCount() {
        return mPromotions.size();
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
            convertView = mInflater.inflate(R.layout.item_product,
                    null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView
                    .findViewById(R.id.item_product_tv_product);
            holder.tvName.setTypeface(tfSemibold);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Setting Group Name
        holder.tvName.setText(mPromotions.get(position).ProductlevelOne);

        return convertView;
    }

    private static class ViewHolder {
        TextView tvName;
    }


}
