package com.luminous.mpartner.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.models.Product;
import com.luminous.mpartner.utils.FontProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ProductsAdapter extends BaseAdapter {


    private final Typeface tfSemibold;
    private final List<Product> mProducts;
    private ArrayList<Product> arraylist;
    private LayoutInflater mInflater;

    public ProductsAdapter(Context mContext, List<Product> products) {
        this.mInflater = LayoutInflater.from(mContext);
        tfSemibold = FontProvider.getInstance().tfSemibold;
        mProducts = products;
        this.arraylist = new ArrayList<Product>();
        this.arraylist.addAll(mProducts);
    }

    @Override
    public int getCount() {
        return mProducts.size();
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

        holder.tvName.setText(mProducts.get(position).Name);

        return convertView;
    }

    private static class ViewHolder {
        TextView tvName;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mProducts.clear();
        if (charText.trim().length() == 0) {
            mProducts.addAll(arraylist);
        } else {
            for (int i = 0; i < arraylist.size(); i++) {

                if (arraylist.get(i).Name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    mProducts.add(arraylist.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }


}
