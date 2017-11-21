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

public class PromotionActivity extends AppCompatActivity {

    private ListView lvProducts;
    private ArrayList<Product> mProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        prepareViews();
    }

    private void prepareViews() {
        lvProducts = (ListView) findViewById(R.id.activity_promotion_lv_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText("Promotion Type");
        title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_promotion, 0, 0, 0);
        title.setCompoundDrawablePadding(10);
        title.setTypeface(FontProvider.getInstance().tfBold);

        getPromotion();
    }


    private void getPromotion() {
        UserController userController = new UserController(this, "updatePromotion");
        userController.getPromotions();
    }

    public void updatePromotion(Object object) {
        mProducts = ((UserController) object).mProducts;
        if (mProducts != null) {
            ProductsAdapter productsAdapter = new ProductsAdapter(this, mProducts);
            lvProducts.setAdapter(productsAdapter);
            lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("PromotionId", mProducts.get(position).Id);
                    Navigator.getInstance().navigateToActivityWithData(PromotionActivity.this, ProductPromotionActivity.class, bundle);
                }
            });
        }
    }

    public void goBack(View v) {
        finish();
    }

}
