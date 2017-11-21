package com.luminous.mpartner.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.adapters.ProductsAdapter;
import com.luminous.mpartner.adapters.ProductsCategoryAdapter;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.Product;
import com.luminous.mpartner.utils.FontProvider;
import com.luminous.mpartner.utils.Navigator;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ProductLevel2Activity extends AppCompatActivity {

    private ExpandableListView lvProducts1;
    private LinkedHashMap<String, ArrayList<Product>> mProductCategories;

    //private ListView lvProducts;
    private String mProductId = "";
    private ArrayList<Product> mProducts;
    private String mProductLevelIdOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_promotion);
        setContentView(R.layout.activity_product_category);

        prepareViews();
    }

    private void prepareViews() {

        mProductCategories = new LinkedHashMap<>();  //Anusha

        mProductId = getIntent().getStringExtra("Id");
        mProductLevelIdOne = getIntent().getStringExtra("OneId");
        lvProducts1 = (ExpandableListView) findViewById(R.id.activity_promotion_lv_products);
        //lvProducts = (ListView) findViewById(R.id.activity_promotion_lv_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText("Product Level 2");
        title.setTypeface(FontProvider.getInstance().tfBold);

        getProductLevelTwo();
    }

    private void getProductLevelTwo() {
        UserController userController = new UserController(this, "updateProductsLevelTwo");
        userController.getProductLevelTwo(mProductId, mProductLevelIdOne);
    }

    public void updateProductsLevelTwo(Object object) {
        mProducts = ((UserController) object).mProducts;
        if (mProducts != null) {

            for (Product product : mProducts) {
                 getProductLevelThree(mProductId,mProductLevelIdOne,product.Id);
            }
        }
    }

    private void getProductLevelThree(String productId,String productLevelIdOne,String productLevelIdTwo) {
        UserController userController = new UserController(this, "updateProductsLevelThree");
        userController.getProductLevelThree(productId, productLevelIdOne, productLevelIdTwo);
    }

    public void updateProductsLevelThree(Object object) {
         ArrayList<Product> products = ((UserController) object).mProducts;
         final String productId = ((UserController) object).selectedProductId;
        if (productId != null) {
             mProductCategories.put(productId, products);
             if (mProductCategories.size() == mProducts.size()) {
               ProductsCategoryAdapter productsCategoryAdapter = new ProductsCategoryAdapter(this, mProducts, mProductCategories);
               lvProducts1.setAdapter(productsCategoryAdapter);
               lvProducts1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                 @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Bundle bundle = new Bundle();
                  bundle.putSerializable("Id", mProductId);
                  bundle.putSerializable("OneId", mProductLevelIdOne);
                  bundle.putSerializable("TwoId", mProducts.get(groupPosition).Id);
                  bundle.putSerializable("ThreeId", mProductCategories.get(mProducts.get(groupPosition).Id).get(childPosition).Id);
                  Navigator.getInstance().navigateToActivityWithData(ProductLevel2Activity.this, ProductDetailActivity.class, bundle);
               return true;
             }
              });
            for(int i=0;i<mProducts.size();i++)
             {
              lvProducts1.expandGroup(i);
              }
             lvProducts1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId) {
                   lvProducts1.expandGroup(itemPosition);
                   return true;
               }
             });
            }
        }
    }

 /*   public void updateProductsLevelTwo(Object object) {
        mProducts = ((UserController) object).mProducts;
        if (mProducts != null) {
            ProductsAdapter productsAdapter = new ProductsAdapter(this, mProducts);
            lvProducts.setAdapter(productsAdapter);
            lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Id", mProductId);
                    bundle.putSerializable("OneId", mProductLevelIdOne);
                    bundle.putSerializable("TwoId", mProducts.get(position).Id);
                    Navigator.getInstance().navigateToActivityWithData(ProductLevel2Activity.this, ProductLevel3Activity.class, bundle);
                }
            });
        }
    }  */

    public void goBack(View v) {
        finish();
    }

}