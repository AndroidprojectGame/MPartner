package com.luminous.mpartner.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.adapters.ProductsAdapter;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.Product;
import com.luminous.mpartner.utils.FontProvider;
import com.luminous.mpartner.utils.Navigator;

import java.util.ArrayList;

public class ProductPromotionLevel1Activity extends AppCompatActivity {

    private ListView lvProducts;
    private String mProductId = "";
    private ArrayList<Product> mProducts;
    private String mPromotionId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        prepareViews();
    }

    private void prepareViews() {

        mPromotionId = getIntent().getStringExtra("PromotionId");
        mProductId = getIntent().getStringExtra("Id");

        lvProducts = (ListView) findViewById(R.id.activity_promotion_lv_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText("Product Level 1");
        title.setTypeface(FontProvider.getInstance().tfBold);

        getProductLevelOne();
    }

    private void getProductLevelOne() {
        UserController userController = new UserController(this, "updateProductsLevelOne");
        userController.getProductLevelOne(mProductId);
    }


    public void updateProductsLevelOne(Object object) {
        mProducts = ((UserController) object).mProducts;
        if (mProducts != null) {
            ProductsAdapter productsAdapter = new ProductsAdapter(this, mProducts);
            lvProducts.setAdapter(productsAdapter);
            lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("PromotionId", mPromotionId);
                    bundle.putString("Id", mProductId);
                    bundle.putString("OneId", mProducts.get(position).Id);
                    Navigator.getInstance().navigateToActivityWithData(ProductPromotionLevel1Activity.this, PromotionDetailActivity.class, bundle);
                }
            });
        }
    }

    public void goBack(View v) {
        finish();
    }

}
