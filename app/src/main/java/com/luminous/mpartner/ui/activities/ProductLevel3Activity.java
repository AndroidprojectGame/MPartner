package com.luminous.mpartner.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.adapters.ProductsAdapter;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.Product;
import com.luminous.mpartner.utils.FontProvider;
import com.luminous.mpartner.utils.Navigator;

import java.util.ArrayList;

public class ProductLevel3Activity extends AppCompatActivity {

    private ListView lvProducts;
    private String mProductId = "";
    private ArrayList<Product> mProducts;
    private String mProductLevelIdOne;
    private String mProductLevelIdTwo;

    private EditText etSearch;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        prepareViews();
    }

    private void prepareViews() {

        mProductId = getIntent().getStringExtra("Id");
        mProductLevelIdOne = getIntent().getStringExtra("OneId");
        mProductLevelIdTwo = getIntent().getStringExtra("TwoId");

        etSearch = (EditText) findViewById(R.id.activity_promotion_et_search);
        etSearch.setTypeface(FontProvider.getInstance().tfRegular);
        etSearch.setVisibility(View.VISIBLE);
        lvProducts = (ListView) findViewById(R.id.activity_promotion_lv_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText("Product Level 3");
        title.setTypeface(FontProvider.getInstance().tfBold);

        getProductLevelThree();
    }

    private void getProductLevelThree() {
        UserController userController = new UserController(this, "updateProductsLevelThree");
        userController.getProductLevelThree(mProductId, mProductLevelIdOne, mProductLevelIdTwo);
    }


    public void updateProductsLevelThree(Object object) {
        mProducts = ((UserController) object).mProducts;
        if (mProducts != null) {
            productsAdapter = new ProductsAdapter(this, mProducts);
            lvProducts.setAdapter(productsAdapter);
            lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Id", mProductId);
                    bundle.putSerializable("OneId", mProductLevelIdOne);
                    bundle.putSerializable("TwoId", mProductLevelIdTwo);
                    bundle.putSerializable("ThreeId", mProducts.get(position).Id);

                    /**********Changed by Anusha to remove level 4 products************/
                    Navigator.getInstance().navigateToActivityWithData(ProductLevel3Activity.this, ProductDetailActivity.class, bundle);
                }
            });
            setupSearchFunctionality();
        }
    }

    public void goBack(View v) {
        finish();
    }

    private void setupSearchFunctionality() {
        etSearch.setVisibility(View.VISIBLE);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productsAdapter.filter(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    /********************End of Change*****************************/

}
