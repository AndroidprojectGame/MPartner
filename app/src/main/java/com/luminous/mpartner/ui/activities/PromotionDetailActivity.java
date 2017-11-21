package com.luminous.mpartner.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.luminous.mpartner.R;
import com.luminous.mpartner.adapters.ProductsAdapter;
import com.luminous.mpartner.adapters.PromotionDetailAdapter;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.Product;
import com.luminous.mpartner.models.Promotion;
import com.luminous.mpartner.service.CommonAsyncTask1;
import com.luminous.mpartner.utils.FontProvider;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class PromotionDetailActivity extends AppCompatActivity {

    private String mPromotionId = "";
    private String mProductId = "";
    private String levelOneId = "";
    private ArrayList<Promotion> mPromotions;
    private PromotionDetailAdapter promotionDetailAdapter;
    TextView tvMsg;
   // SharedPreferences sp;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_detail);

        prepareViews();
      //  sp = getSharedPreferences("My_prefs", Activity.MODE_PRIVATE);
      //  SharedPreferences.Editor editor = sp.edit();
      //  editor.putInt("count", 1);
       // editor.commit();
        getPromotionDetail();
      //  CommonAsyncTask1 async = new CommonAsyncTask1();
       // Boolean dismiss = sp.getBoolean("dismiss", false);
       // if(dismiss == true && lv.getCount() == 0){
       //     tvMsg.setVisibility(View.VISIBLE);
       // }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    private void prepareViews() {
        mPromotionId = getIntent().getStringExtra("PromotionId");
        mProductId = getIntent().getStringExtra("Id");
        levelOneId = getIntent().getStringExtra("OneId");

       // ivPromotion = (ImageView) findViewById(R.id.activity_promotion_detail_iv_product);  //By Anusha
       // tvPromotion = (TextView) findViewById(R.id.activity_promotion_detail_tv_description);  //By Anusha
        tvMsg = (TextView)findViewById(R.id.textView);  //By Anusha
        lv=(ListView) findViewById(R.id.listView);  //By Anusha
        tvMsg.setVisibility(View.VISIBLE);  //By Anusha
       // lv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_title);
        ImageView ivShare = (ImageView) toolbar.findViewById(R.id.toolbar_report_iv_share);
        ivShare.setVisibility(View.VISIBLE);
        title.setText("Promotion Detail");
        title.setTypeface(FontProvider.getInstance().tfBold);

       // tvPromotion.setTypeface(FontProvider.getInstance().tfRegular);

//        tvPromotion.setText("" + mPromotion.Descriptons);
//
//        if (mPromotion.ImagePath != null) {
//            if (mPromotion.ImagePath.contains(".pdf")) {
//                ivPromotion.setImageResource(R.drawable.download_pdf);
//                ivPromotion.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPromotion.ImagePath));
//                        startActivity(browserIntent);
//                    }
//                });
//            } else {
//                ImageLoader imageLoader = ImageLoader.getInstance();
//                DisplayImageOptions options = new DisplayImageOptions.Builder()
//                        .showImageOnLoading(R.drawable.luminous_logo_loading) // resource or drawable
//                        .showImageForEmptyUri(R.drawable.luminous_logo_no_image) // resource or drawable
//                        .showImageOnFail(R.drawable.luminous_logo_no_image) // resource or drawable
//                        .build();
//                imageLoader.displayImage(mPromotion.ImagePath, ivPromotion, options);
//            }
//
//        }

    }

    private void getPromotionDetail() {
        UserController userController = new UserController(this, "updatePromotionDetail");
        userController.getPromotionDetail(mPromotionId, mProductId, levelOneId);
    }

    public void updatePromotionDetail(Object object) {
        mPromotions = ((UserController) object).mPromotions;
        if (mPromotions != null) {
            tvMsg.setVisibility(View.GONE); //By Anusha
            promotionDetailAdapter = new PromotionDetailAdapter(this, mPromotions);  //By Anusha to display all promotions
            lv.setAdapter(promotionDetailAdapter);

            //Commented by Anusha
           // tvPromotion.setText(Html.fromHtml("<b>Promotion Description :</b> <br><br>" + mPromotionDetail.Descriptons));

          /*  if (mPromotionDetail.ImagePath != null) {
                if (mPromotionDetail.ImagePath.contains(".pdf") || mPromotionDetail.ImagePath.contains(".PDF")) {
                    ivPromotion.setImageResource(R.drawable.download_pdf);
                    ivPromotion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPromotionDetail.ImagePath));
                            startActivity(browserIntent);
                        }
                    });
                } else {
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.drawable.luminous_logo_loading) // resource or drawable
                            .showImageForEmptyUri(R.drawable.luminous_logo_no_image) // resource or drawable
                            .showImageOnFail(R.drawable.luminous_logo_no_image) // resource or drawable
                            .build();
                    imageLoader.displayImage(mPromotionDetail.ImagePath, ivPromotion, options);
                }
            } */
        }

    }

    public void goBack(View v) {
        finish();
    }

    //Commented by Anusha
    /* public void share(View v) {
        String downloadText = "";
        if (mPromotionDetail.ImagePath.contains(".pdf")) {
            downloadText = "Download PDF : " + mPromotionDetail.ImagePath;
        } else {
            downloadText = "Download Image : " + mPromotionDetail.ImagePath;
        }

        String shareText = "Checkout : " + mPromotionDetail.ProductlevelOne + " " + mPromotionDetail.ProductCategory + "\n\n" +
                mPromotionDetail.Descriptons + "\n\n" + downloadText;
        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(i, "Share via"));

    } */
}
