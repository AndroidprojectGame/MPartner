package com.luminous.mpartner.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.constants.AppConstants;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.ProductDetail;
import com.luminous.mpartner.utils.FontProvider;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView tvDescription,tvWarranty,tvKeyFeatures,tvBrochure;  //tvProductCode,tvMrp,
    private ImageView ivPromotion;
    private String mLevelThreeId;
    private ProductDetail mProductDetail;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        prepareViews();

        //getProductDetail();
    }

    private void prepareViews() {
        mLevelThreeId = getIntent().getStringExtra("ThreeId");
        mProductDetail = (ProductDetail) getIntent().getSerializableExtra(AppConstants.EXTRA_PRODUCT_DETAIL);

        ivPromotion = (ImageView) findViewById(R.id.activity_product_detail_iv_product);
        tvDescription = (TextView) findViewById(R.id.activity_product_detail_tv_description);
        tvWarranty = (TextView) findViewById(R.id.activity_product_detail_tv_warranty);
        tvKeyFeatures = (TextView) findViewById(R.id.activity_product_detail_tv_key_feature);
        tvBrochure = (TextView) findViewById(R.id.activity_product_detail_tv_brochure);
   //     tvProductCode = (TextView) findViewById(R.id.activity_product_detail_tv_product_code);
   //     tvMrp = (TextView) findViewById(R.id.activity_product_detail_tv_mrp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) toolbar.findViewById(R.id.tv_title);
        ImageView ivShare = (ImageView) toolbar.findViewById(R.id.toolbar_report_iv_share);
        ivShare.setVisibility(View.VISIBLE);
        title.setText("Product Detail");
        title.setTypeface(FontProvider.getInstance().tfBold);

        tvDescription.setTypeface(FontProvider.getInstance().tfRegular);
        tvWarranty.setTypeface(FontProvider.getInstance().tfRegular);
        tvKeyFeatures.setTypeface(FontProvider.getInstance().tfRegular);
      //  tvProductCode.setTypeface(FontProvider.getInstance().tfRegular);
        tvBrochure.setTypeface(FontProvider.getInstance().tfRegular);
     //   tvMrp.setTypeface(FontProvider.getInstance().tfRegular);

        if(mProductDetail==null)
        {
            getProductDetail();
        }
        else {
            setData();
        }
    }

    private void getProductDetail() {
        UserController userController = new UserController(this, "updateProductsDetail");
            userController.getProductDetail(mLevelThreeId);
    }


    public void updateProductsDetail(Object object) {
        mProductDetail = ((UserController) object).mProductDetail;
        setData();
    }

    private void setData() {
        if (mProductDetail != null) {

            /********Changed by Anusha to change header color, display product name in action bar and remove product level 4*****/
            title.setText("");
            title.setText(mProductDetail.ProductLevelThreeName);
            tvDescription.setText(Html.fromHtml("<b><font color = \"#132E6E\">Technical Specifications :</font></b> <br><br>" + mProductDetail.ProductDescriptions));
            tvWarranty.setText(Html.fromHtml("<b><font color = \"#132E6E\">Warranty :</font></b> <br><br>" + mProductDetail.Warranty));
            tvKeyFeatures.setText(Html.fromHtml("<b><font color = \"#132E6E\">Key Features :</font></b> <br><br>" + mProductDetail.Keyfeature));
            tvBrochure.setText(Html.fromHtml("<b><font color = \"#132E6E\">Brochure :</font></b> <br><br>" + mProductDetail.Brochure));
           // tvProductCode.setText(Html.fromHtml("<b>Product Code :</b> " + mProductDetail.ProductCode));
           // tvMrp.setText(Html.fromHtml("<b>MRP :</b> " + mProductDetail.MRP));
            /***********End of change*******************************************/

            if (mProductDetail.Image != null) {
                if (mProductDetail.Image.contains(".pdf") || mProductDetail.Image.contains(".PDF")) {
                    ivPromotion.setImageResource(R.drawable.download_pdf);
                    ivPromotion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mProductDetail.Image));
                            startActivity(browserIntent);
                        }
                    });
                } else {
                 /*    Picasso.with(this).load(mProductDetail.Image)
                            .error(R.drawable.luminous_logo_no_image)
                            .placeholder(R.drawable.progress_animation)
                            .into(ivPromotion);  */
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.drawable.progress_animation) // resource or drawable
                            .showImageForEmptyUri(R.drawable.luminous_logo_no_image) // resource or drawable
                            .showImageOnFail(R.drawable.luminous_logo_no_image) // resource or drawable
                            .build();
                    imageLoader.displayImage(mProductDetail.Image, ivPromotion, options);
                }
            }
        }
    }

    public void goBack(View v) {
        finish();
    }

    public void share(View v) {
        String downloadText = "";
        if (mProductDetail.Image.contains(".pdf")) {
            downloadText = "Download PDF : " + mProductDetail.Image;
        } else {
            downloadText = "Download Image : " + mProductDetail.Image;
        }
        String shareText = "Checkout : \n" + tvDescription.getText() + "\n" + downloadText;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(i, "Share via"));
    }
}
