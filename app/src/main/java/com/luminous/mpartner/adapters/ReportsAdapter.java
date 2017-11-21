package com.luminous.mpartner.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.models.Report;
import com.luminous.mpartner.utils.FontProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportsAdapter extends BaseAdapter {


    private ArrayList<Report> arraylist;
    private final Typeface tfRegular;
    private final Typeface tfBold;
    private Context mContext;
    private List<Report> mReports;
    private LayoutInflater mInflater;

    public ReportsAdapter(Context context, List<Report> reports) {
        mContext = context;
        mReports = reports;
        mInflater = LayoutInflater.from(mContext);
        tfRegular = FontProvider.getInstance().tfRegular;
        tfBold = FontProvider.getInstance().tfBold;
        this.arraylist = new ArrayList<Report>();
        this.arraylist.addAll(mReports);
    }

    @Override
    public int getCount() {
        return mReports.size();
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
            convertView = mInflater.inflate(R.layout.item_reports, null);

            holder.tvDealer = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_dealer);
            holder.tvDealerName = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_dealername);
            holder.tvDealerPayout = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_dealer_payout_amt);
            holder.tvDistPayout = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_dist_payout_amt);
            holder.tvTotalPayout = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_total_payout_amt);

            holder.tvHups = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_header_hups);
            holder.tvBattery = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_header_battery);
            holder.tvHkw = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_header_hkw);
            holder.tvTotal = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_header_total);

            holder.tvRec = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_rec);
            holder.tvHupsRec = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_hups_rec);
            holder.tvBatteryRec = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_battery_rec);
            holder.tvHkwRec = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_hkw_rec);
            holder.tvTotalRec = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_total_rec);


            holder.tvAcc = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_acc);
            holder.tvHupsAcc = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_hups_acc);
            holder.tvBatteryAcc = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_battery_acc);
            holder.tvHkwAcc = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_hkw_acc);
            holder.tvTotalAcc = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_total_acc);


            holder.tvRej = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_rej);
            holder.tvHupsRej = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_hups_rej);
            holder.tvBatteryRej = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_battery_rej);
            holder.tvHkwRej = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_hkw_rej);
            holder.tvTotalRej = (TextView) convertView
                    .findViewById(R.id.item_reports_tv_total_rej);

            holder.tvHups.setTypeface(tfRegular);
            holder.tvBattery.setTypeface(tfRegular);
            holder.tvHkw.setTypeface(tfRegular);
            holder.tvTotal.setTypeface(tfRegular);
            holder.tvDealer.setTypeface(tfBold);
            holder.tvDealerName.setTypeface(tfRegular);
            holder.tvDealerPayout.setTypeface(tfRegular);
            holder.tvDistPayout.setTypeface(tfRegular);
            holder.tvTotalPayout.setTypeface(tfRegular);

            holder.tvRec.setTypeface(tfRegular);
            holder.tvHupsRec.setTypeface(tfRegular);
            holder.tvBatteryRec.setTypeface(tfRegular);
            holder.tvHkwRec.setTypeface(tfRegular);
            holder.tvTotalRec.setTypeface(tfRegular);

            holder.tvAcc.setTypeface(tfRegular);
            holder.tvHupsAcc.setTypeface(tfRegular);
            holder.tvBatteryAcc.setTypeface(tfRegular);
            holder.tvHkwAcc.setTypeface(tfRegular);
            holder.tvTotalAcc.setTypeface(tfRegular);

            holder.tvRej.setTypeface(tfRegular);
            holder.tvHupsRej.setTypeface(tfRegular);
            holder.tvBatteryRej.setTypeface(tfRegular);
            holder.tvHkwRej.setTypeface(tfRegular);
            holder.tvTotalRej.setTypeface(tfRegular);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tvDealerName.setText(mReports.get(position).Dealer_Name + "");

        holder.tvDealerPayout.setText("" + mReports.get(position).DlrPay);
        holder.tvDistPayout.setText("" + mReports.get(position).DistPay);
        holder.tvTotalPayout.setText("" + mReports.get(position).Total_Payout);


        holder.tvHupsRec.setText("" + mReports.get(position).InvRec);
        holder.tvBatteryRec.setText("" + mReports.get(position).BattRec);
        holder.tvHkwRec.setText("" + mReports.get(position).HkvaRec);
        holder.tvTotalRec.setText("" + mReports.get(position).TotalRec);

        holder.tvHupsAcc.setText("" + mReports.get(position).InvAcp);
        holder.tvBatteryAcc.setText("" + mReports.get(position).BattAcp);
        holder.tvHkwAcc.setText("" + mReports.get(position).HkvaAcp);
        holder.tvTotalAcc.setText("" + mReports.get(position).TotalAcp);

        holder.tvHupsRej.setText("-");
        holder.tvBatteryRej.setText("-");
        holder.tvHkwRej.setText("-");
        holder.tvTotalRej.setText("" + mReports.get(position).TotalRej);

        return convertView;
    }

    class ViewHolder {
        TextView tvDealer, tvDealerName, tvDealerPayout, tvDistPayout, tvTotalPayout;
        TextView tvHups, tvBattery, tvHkw, tvTotal;
        TextView tvRec, tvHupsRec, tvBatteryRec, tvHkwRec, tvTotalRec;
        TextView tvAcc, tvHupsAcc, tvBatteryAcc, tvHkwAcc, tvTotalAcc;
        TextView tvRej, tvHupsRej, tvBatteryRej, tvHkwRej, tvTotalRej;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mReports.clear();
        if (charText.trim().length() == 0) {
            mReports.addAll(arraylist);
        } else {
            for (Report report : arraylist) {
                if (report.Dealer_Name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    mReports.add(report);
                }
            }
        }
        notifyDataSetChanged();
    }

}
