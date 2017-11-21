package com.luminous.mpartner.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.models.Notification;
import com.luminous.mpartner.utils.FontProvider;

import java.util.ArrayList;


public class NotificationsAdapter extends BaseAdapter {


    private final Typeface tfRegular,tfSemibold;
    private final ArrayList<Notification> mNotifications;
    private LayoutInflater mInflater;

    public NotificationsAdapter(Context mContext, ArrayList<Notification> notifications) {
        this.mInflater = LayoutInflater.from(mContext);
        tfRegular = FontProvider.getInstance().tfRegular;
        tfSemibold = FontProvider.getInstance().tfSemibold;
        mNotifications = notifications;
    }

    @Override
    public int getCount() {
        return mNotifications.size();
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
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_notifications,
                    null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView
                    .findViewById(R.id.item_notification_tv_message);
            holder.tvVisiblity = (TextView) convertView
                    .findViewById(R.id.item_notification_tv_time);

            holder.tvName.setTypeface(tfSemibold);
            holder.tvVisiblity.setTypeface(tfRegular);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Setting Group Name
        holder.tvName.setText(""+mNotifications.get(position).Subject);
        holder.tvVisiblity.setText(""+mNotifications.get(position).Text);

        return convertView;
    }

    private static class ViewHolder {
        TextView tvName, tvVisiblity;
    }


}
