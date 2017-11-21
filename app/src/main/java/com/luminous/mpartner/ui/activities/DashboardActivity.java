package com.luminous.mpartner.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luminous.mpartner.Global;
import com.luminous.mpartner.constants.AppConstants;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.Notification;
import com.luminous.mpartner.models.Product;
import com.luminous.mpartner.models.ProductDetail;
import com.luminous.mpartner.models.Profile;
import com.luminous.mpartner.ui.custom.CircleImageView;
import com.luminous.mpartner.utils.AppSharedPrefrences;
import com.luminous.mpartner.utils.AppUtility;
import com.luminous.mpartner.utils.FontProvider;
import com.luminous.mpartner.utils.Navigator;
import com.luminous.mpartner.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import me.leolin.shortcutbadger.ShortcutBadger;

public class DashboardActivity extends AppCompatActivity {


    private static DashboardActivity mInstance;
   // private TextView tvHome, tvWrs, tvProducts, tvPromotion, tvNotification;
    TextView tvB2B, tvPRODUCT, tvPRODUCT1, tvPRODUCT2, tvPRODUCT3, tvPROMOTION, tvWRS;
    private Typeface tfSemibold;
  //  private LinearLayout ll1, ll2, ll3, ll4;
    private Profile mProfileDetail;
    private TextView tvName;
    private CircleImageView ivProfilePic;
    String mImageUrl;
    private ImageView ivBanner, ivPRODUCT, ivPRODUCT1, ivPRODUCT2, ivPRODUCT3, ivNotification;
    private ArrayList<Notification> mNotifications;
    private TextView tvNotificationTag;
    private String currentdate;
    private EditText etSearch;
    private ArrayList<Product> mProducts;
    String batteryId;
    String upsId;
    String fanId;
    String categoryid;
    int count = 0;
    String battery;
    String ups;
    String fan;
    String categoryname;

    public static DashboardActivity getInstance() {
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mInstance = this;

        prepareViews();

    }

    private void prepareViews() {

    /************Change by Anusha to remove Home tab******************/
       // tvHome = (TextView) findViewById(R.id.common_top_header_tv_home);
        //tvWrs = (TextView) findViewById(R.id.common_top_header_tv_wrs);
       // tvProducts = (TextView) findViewById(R.id.common_top_header_tv_product);
       // tvPromotion = (TextView) findViewById(R.id.common_top_header_tv_promotion);
        ivNotification = (ImageView) findViewById(R.id.activity_dashboard_iv_notification);
        tvNotificationTag = (TextView) findViewById(R.id.activity_dashboard_tv_notification_count_tag);

        etSearch = (EditText) findViewById(R.id.activity_dashboard_et_search);
        tvB2B = (TextView) findViewById(R.id.activity_dashboard_tv_bb);
        ivBanner = (ImageView) findViewById(R.id.activity_dashboard_iv_banner);
        ivPRODUCT = (ImageView) findViewById(R.id.activity_dashboard_iv_product);
        ivPRODUCT1 = (ImageView) findViewById(R.id.activity_dashboard_iv_product1);
        ivPRODUCT2 = (ImageView) findViewById(R.id.activity_dashboard_iv_product2);
        ivPRODUCT3 = (ImageView) findViewById(R.id.activity_dashboard_iv_product3);
      //  tvPRODUCT = (TextView) findViewById(R.id.activity_dashboard_tv_product);
      //  tvPRODUCT1 = (TextView) findViewById(R.id.activity_dashboard_tv_product1);
      //  tvPRODUCT2 = (TextView) findViewById(R.id.activity_dashboard_tv_product2);
      //  tvPRODUCT3 = (TextView) findViewById(R.id.activity_dashboard_tv_product3);
        tvPROMOTION = (TextView) findViewById(R.id.activity_dashboard_tv_promotion);
      //  ll1 = (LinearLayout) findViewById(R.id.lL1);
      //  ll2 = (LinearLayout) findViewById(R.id.lL2);
      //  ll3 = (LinearLayout) findViewById(R.id.lL3);
      //  ll4 = (LinearLayout) findViewById(R.id.lL4);
        /***********End of Change************************/
        tvWRS = (TextView) findViewById(R.id.activity_dashboard_tv_wrs);

        tfSemibold = FontProvider.getInstance().tfSemibold;

        /*************Commented by Anusha to remove home tab******************/
       // tvHome.setTypeface(tfSemibold);
       // tvWrs.setTypeface(tfSemibold);
        // tvProducts.setTypeface(tfSemibold);
        // tvPromotion.setTypeface(tfSemibold);

        //ivNotification.setTypeface(tfSemibold);
        tvB2B.setTypeface(tfSemibold);
        tvWRS.setTypeface(tfSemibold);
       // tvPRODUCT.setTypeface(tfSemibold);
        tvPROMOTION.setTypeface(tfSemibold);
        getProducts();  //By Anusha
        prepareToolbar();
        getProfile();
        getBannerImage();
        getNotifications();
        setupSearchFunctionality();

    }

    /*********************Changed by Anusha to merge level 1 and level 2********************************/
    private void getProducts() {
        UserController userController = new UserController(this, "updateProducts");
        userController.getProducts();
    }

    public void updateProducts(Object object) {
        mProducts = ((UserController) object).mProducts;
        if (mProducts != null) {

            for (Product product : mProducts) {
                count = count + 1;
                openProductCategory(product.Name, product.Id);
              /*  if((product.Name).equalsIgnoreCase("Battery")){
                    ivPRODUCT.setVisibility(View.VISIBLE);
                    batteryId = product.Id;
                  /*  Picasso.with(getApplicationContext())
                            .load(R.drawable.battery)
                            .transform(new CircleTransform())
                            .into(ivPRODUCT);  */
               /*  }
               else if((product.Name).equalsIgnoreCase("Home UPS\t")){
                    ivPRODUCT1.setVisibility(View.VISIBLE);
                    upsId = product.Id;
                  /*  Picasso.with(getApplicationContext())
                            .load(R.drawable.homeups2)
                            .transform(new CircleTransform())
                            .into(ivPRODUCT1);  */
                /* }
                else if((product.Name).equalsIgnoreCase("Fan")){
                    ivPRODUCT2.setVisibility(View.VISIBLE);
                    fanId = product.Id;
                }*/
                // getProductLevelOne(product.Id);
            }
            count = 0;
        }
    }
    /***********End of Change************************/

    @Override
    protected void onStart() {
        super.onStart();

        /*********** Change by Anusha for refreshing Notification count*******************/
        try{
        ShortcutBadger.with(getApplicationContext()).count(0); //for 1.1.3
    } catch (SecurityException e) {
        e.printStackTrace();
    }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Count", 0);
        editor.commit();
        /**************End of change **********************/

        tvNotificationTag.setVisibility(tvNotificationTag.getVisibility() == View.VISIBLE ? View.GONE : tvNotificationTag.getVisibility());
    }

    private void setupSearchFunctionality() {
        etSearch.setVisibility(View.VISIBLE);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (etSearch.getText().toString().trim().length() > 0) {
                        searchProducts(etSearch.getText().toString());
                    }
                    handled = true;
                }
                return handled;
            }
        });

    }

    private void searchProducts(String s) {
        UserController userController = new UserController(this, "updateSearchedProduct");
        userController.getSearchedProduct(s);
    }

    public void updateSearchedProduct(Object object) {
        ProductDetail productDetail = ((UserController) object).mProductDetail;
        if(productDetail!=null)
        {
            etSearch.setText("");
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstants.EXTRA_PRODUCT_DETAIL,productDetail);
            Navigator.getInstance().navigateToActivityWithData(this,ProductDetailActivity.class,bundle);
        }
        else
        {
            Global.showToast("No result found.");
        }
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName = (TextView) toolbar.findViewById(R.id.tv_profile);
        ivProfilePic = (CircleImageView) toolbar.findViewById(R.id.iv_profile_pic);
        tvName.setTypeface(tfSemibold);
    }

    public void openContactUsScreen(View v) {
        Navigator.getInstance().navigateToActivity(this, ContactUsActivity.class);
    }

    public void openMyInformation(View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Profile", mProfileDetail);
        Navigator.getInstance().navigateToActivityWithData(this, MyProfileActivity.class, bundle);
    }

   public void openNotifications(View v) {
        if (mNotifications != null) {

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AppConstants.EXTRA_NOTIFICATIONS, mNotifications);
            bundle.putString(AppConstants.EXTRA_DATE, currentdate);

            Navigator.getInstance().navigateToActivityWithData(this, NotificationsActivity.class, bundle);

        } else {
            Global.showToast("No notifications found");
        }

    }

    public void openProductPromotion(View v) {
        Navigator.getInstance().navigateToActivity(this, PromotionActivity.class);
    }

    /********************Change by Anusha for Product Category**************************/

    private void openProductCategory(String productName, String productId) {
        if(count == 1) {
            ivPRODUCT.setVisibility(View.VISIBLE);
            battery = productName;
            batteryId = productId;
        }
        if(count == 2) {
            ivPRODUCT1.setVisibility(View.VISIBLE);
            ups = productName;
            upsId = productId;
        }
    /*    if(count == 3) {
            ivPRODUCT2.setVisibility(View.VISIBLE);
            fan = productName;
            fanId = productId;
        }
        if(count == 4) {
            ivPRODUCT3.setVisibility(View.VISIBLE);
            categoryname = productName;
            categoryid = productId;
        }  */
    }

    public void openProduct1(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("Product", battery);
        bundle.putString("CategoryId", batteryId);
        Navigator.getInstance().navigateToActivityWithData(this, ProductActivity.class, bundle);
    }

    public void openProduct2(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("Product", ups);
        bundle.putString("CategoryId", upsId);
        Navigator.getInstance().navigateToActivityWithData(this, ProductActivity.class, bundle);
    }

    public void openProduct3(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("Product", fan);
        bundle.putString("CategoryId", fanId);
        Navigator.getInstance().navigateToActivityWithData(this, ProductActivity.class, bundle);
    }

    public void openProduct4(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("Product", categoryname);
        bundle.putString("CategoryId", categoryid);
        Navigator.getInstance().navigateToActivityWithData(this, ProductActivity.class, bundle);
    }
   // public void openProduct(View v) {
   //     Navigator.getInstance().navigateToActivity(this, ProductActivity.class);
   // }
    /***********End of Change************************/

    public void openWRS(View v) {
        Navigator.getInstance().navigateToActivity(this, WRSActivity.class);
    }

    public void openBB(View v) {
        Navigator.getInstance().navigateToActivity(this, DealerDashboardActivity.class);
    }

    public void getProfile() {
        UserController userController = new UserController(this, "updateProfileData");
        userController.getProfile();
    }

    public void getNotifications() {
        Calendar mCurrentDate = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        currentdate = dateFormatter.format(mCurrentDate
                .getTime());
        UserController userController = new UserController(this, "updateNotifications");
        userController.getNotifications(currentdate, currentdate);
    }

    public void updateNotifications(Object object) {
        mNotifications = ((UserController) object).mNotifications;
        if (mNotifications != null) {
            countUnreadNotificationsAndSetTag();
        }
    }

    public void getBannerImage() {
        UserController userController = new UserController(this, "updateBannerImage");
        userController.getBannerImage(AppUtility.getCurrentDateInFormat());
    }

    private void countUnreadNotificationsAndSetTag() {
        int unreadNotificationCount = 0;
        for (Notification notification : mNotifications) {
            if (!notification.isread) {
                unreadNotificationCount++;
            }
        }
        if (unreadNotificationCount > 0) {
            tvNotificationTag.setVisibility(View.VISIBLE);
            tvNotificationTag.setText("" + unreadNotificationCount);
        } else {
            tvNotificationTag.setVisibility(View.INVISIBLE);
        }
    }

    public void updateBannerImage(Object object) {
        mImageUrl = ((UserController) object).mImageUrl;
        if (mImageUrl != null) {
            ivBanner.setVisibility(View.VISIBLE);
            Picasso.with(getApplicationContext()).load(mImageUrl)
                    //.error(R.drawable.luminous_logo_no_image)
                    //.placeholder(R.drawable.luminous_logo_loading)
                            //.resize(IMAGE_HEIGHT, IMAGE_WIDTH).centerCrop()
                    .into(ivBanner);
            //ImageLoader imageLoader = ImageLoader.getInstance();
            //DisplayImageOptions options = new DisplayImageOptions.Builder()
                   // .showImageOnLoading(R.drawable.luminous_logo_loading) // resource or drawable
                   // .showImageForEmptyUri(R.drawable.luminous_logo_no_image) // resource or drawable
                   // .showImageOnFail(R.drawable.luminous_logo_no_image) // resource or drawable
                   // .showImageOnFail(R.drawable.banner2)    //By Anusha
                 //   .build();
           // imageLoader.displayImage(mImageUrl, ivProfilePic, options);
            //imageLoader.displayImage(mImageUrl, ivBanner, options);  //By Anusha
        }
    }

    public void updateProfileData(Object object) {
        mProfileDetail = ((UserController) object).mProfileDetail;
        if (mProfileDetail != null) {
            tvName.setText("" + mProfileDetail.Name);
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMyInformation(v);
                }
            });
            AppSharedPrefrences.getInstance(this).setUserType(mProfileDetail.CustomerType);
            if (!mProfileDetail.CustomerType.equalsIgnoreCase(AppConstants.USER_DISTRIBUTER)) {
                tvB2B.setVisibility(View.GONE);
            }
            Picasso.with(getApplicationContext()).load(mProfileDetail.UserImage)
                    .error(R.drawable.icon_profile)
                    .placeholder(R.drawable.icon_profile)
                            //.resize(IMAGE_HEIGHT, IMAGE_WIDTH).centerCrop()
                    .into(ivProfilePic);
           /* ImageLoader imageLoader = ImageLoader.getInstance();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.icon_profile) // resource or drawable
                    .showImageForEmptyUri(R.drawable.icon_profile) // resource or drawable
                    .showImageOnFail(R.drawable.icon_profile) // resource or drawable
                    .build();
            imageLoader.displayImage(mProfileDetail.UserImage, ivProfilePic, options);  */
        }

    }

}
