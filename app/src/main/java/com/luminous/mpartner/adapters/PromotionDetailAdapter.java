package com.luminous.mpartner.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luminous.mpartner.R;
import com.luminous.mpartner.models.Product;
import com.luminous.mpartner.models.Promotion;
import com.luminous.mpartner.ui.activities.PromotionDetailActivity;
import com.luminous.mpartner.utils.FontProvider;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Anusha on 3/10/2016.
 */
public class PromotionDetailAdapter extends BaseAdapter {
    private Context context;
    private final Typeface tfRegular;
    private final List<Promotion> mPromotions;
    private ArrayList<Promotion> arraylist;
    private LayoutInflater mInflater;
    public Dialog customProgressDialog;

    public PromotionDetailAdapter(Context mContext, List<Promotion> promotions) {
        this.context = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        tfRegular = FontProvider.getInstance().tfRegular;
        mPromotions = promotions;
        this.arraylist = new ArrayList<Promotion>();
        this.arraylist.addAll(mPromotions);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mPromotions.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private static class ViewHolder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //ViewHolder holder;
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.program_list,
                    null);
            ViewHolder holder = new ViewHolder();
        holder.tv = (TextView) convertView.findViewById(R.id.activity_promotion_detail_tv_description);
        holder.tv.setTypeface(tfRegular);
        holder.img = (ImageView) convertView.findViewById(R.id.activity_promotion_detail_iv_product);
            convertView.setTag(holder);
        } //else {

        //}
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.tv.setText(Html.fromHtml("<b>Promotion Description :</b> <br><br>" + mPromotions.get(position).Descriptons));
        if (mPromotions.get(position).ImagePath != null) {
            if (mPromotions.get(position).ImagePath.contains(".pdf") || mPromotions.get(position).ImagePath.contains(".PDF")) {
                holder.img.setImageResource(R.drawable.download_pdf);
                holder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPromotions.get(position).ImagePath));
                        context.startActivity(browserIntent);
                    }
                });
            } else {
                Picasso.with(context).load(mPromotions.get(position).ImagePath)
                        .error(R.drawable.luminous_logo_no_image)
                        .placeholder(R.drawable.progress_animation)
                        .into(holder.img);
               /* ImageLoader imageLoader = ImageLoader.getInstance();
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.luminous_logo_loading) // resource or drawable
                        .showImageForEmptyUri(R.drawable.luminous_logo_no_image) // resource or drawable
                        .showImageOnFail(R.drawable.luminous_logo_no_image) // resource or drawable
                        .build();
                imageLoader.displayImage(mPromotions.get(position).ImagePath, holder.img, options); */

            }
        }
            //  holder.img.setImageResource(mPromotionsDetail.get(position).ImagePath);
            return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mPromotions.clear();
        if (charText.trim().length() == 0) {
            mPromotions.addAll(arraylist);
        } else {
            for (int i = 0; i < arraylist.size(); i++) {

                if (arraylist.get(i).Descriptons.toLowerCase(Locale.getDefault()).contains(charText)) {
                    mPromotions.add(arraylist.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
}
