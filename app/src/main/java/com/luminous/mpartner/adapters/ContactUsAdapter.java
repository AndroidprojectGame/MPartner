package com.luminous.mpartner.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.models.ContactUs;
import com.luminous.mpartner.utils.FontProvider;

import java.util.List;


public class ContactUsAdapter extends BaseAdapter {


    private final Typeface tfRegular;
    private final List<ContactUs> mContactUsList;
    private LayoutInflater mInflater;
  //  private String contactUsTextFormat = "<font color=\"#071031\"><b>%s</b></font><br><br><font color=\"#071031\"><b>Address: </b></font> %s<br><br><font color=\"#071031\"><b>Email: </b></font>  %s<br><br><font color=\"#071031\"><b>Fax: </b></font>  %s<br><br><font color=\"#071031\"><b>PhoneNumber: </b></font>  %s";
  private String contactUsTextFormat = "<font color=\"#071031\"><b>%s</b></font><br><br><font color=\"#071031\"><b>Address: </b></font> %s<br><br><font color=\"#071031\"><b>Email: </b>  %s</font><br><br><font color=\"#071031\"><b>Fax: </b></font>  %s<br><br><font color=\"#071031\"><b>PhoneNumber: </b></font>  %s";
    public ContactUsAdapter(Context mContext, List<ContactUs> contactUsList) {
        this.mInflater = LayoutInflater.from(mContext);
        mContactUsList = contactUsList;
        tfRegular = FontProvider.getInstance().tfRegular;
    }

    @Override
    public int getCount() {
        return mContactUsList.size();
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
            convertView = mInflater.inflate(R.layout.item_contact_us,
                    null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView
                    .findViewById(R.id.item_contact_us_text);

            holder.tvName.setTypeface(tfRegular);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Setting Group Name
        holder.tvName.setText(Html.fromHtml("" + String.format(contactUsTextFormat, mContactUsList.get(position).ContactUsType, mContactUsList.get(position).Address, mContactUsList.get(position).email, mContactUsList.get(position).Fax, mContactUsList.get(position).PhoneNumber)));
        holder.tvName.setLinkTextColor(Color.GRAY);
        return convertView;
    }

    private static class ViewHolder {
        TextView tvName;
    }


}
