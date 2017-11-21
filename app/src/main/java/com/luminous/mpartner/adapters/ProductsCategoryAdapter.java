package com.luminous.mpartner.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.models.Product;
import com.luminous.mpartner.utils.FontProvider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class ProductsCategoryAdapter extends BaseExpandableListAdapter {


    private final Typeface tfSemibold, tfRegular;
    private final List<Product> mProducts;
    private final LinkedHashMap<String, ArrayList<Product>> mProductCategories;
    private LayoutInflater mInflater;

    public ProductsCategoryAdapter(Context mContext, List<Product> products, LinkedHashMap<String, ArrayList<Product>> productCategories) {
        this.mInflater = LayoutInflater.from(mContext);
        tfSemibold = FontProvider.getInstance().tfSemibold;
        tfRegular = FontProvider.getInstance().tfRegular;
        mProducts = products;
        mProductCategories = productCategories;
    }

    @Override
    public int getGroupCount() {
        return mProducts.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mProductCategories.get(mProducts.get(groupPosition).Id).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
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

        holder.tvName.setText("" + mProducts.get(groupPosition).Name);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_product,
                    null);
            holder = new ChildViewHolder();
            holder.tvName = (TextView) convertView
                    .findViewById(R.id.item_product_tv_product);
            holder.tvName.setTypeface(tfRegular);

            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        holder.tvName.setText("" + mProductCategories.get(mProducts.get(groupPosition).Id).get(childPosition).Name);
        holder.tvName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bullet,0,0,0);
        holder.tvName.setCompoundDrawablePadding(10);
        holder.tvName.setPadding(20,0,0,0);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ViewHolder {
        TextView tvName;
    }

    private static class ChildViewHolder {
        TextView tvName;
    }


}
