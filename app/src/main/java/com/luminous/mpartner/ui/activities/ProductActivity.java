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

public class ProductActivity extends AppCompatActivity {

    private ExpandableListView lvProducts;
    private ArrayList<Product> mProducts;
    private LinkedHashMap<String, ArrayList<Product>> mProductCategories;
    String ProductName;
    String Categoryid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
       // setContentView(R.layout.activity_promotion);
        ProductName = getIntent().getStringExtra("Product");
        Categoryid = getIntent().getStringExtra("CategoryId");
        prepareViews();
    }

    private void prepareViews() {
        mProductCategories = new LinkedHashMap<>();
        lvProducts = (ExpandableListView) findViewById(R.id.activity_promotion_lv_products);
        //lvProducts = (ListView) findViewById(R.id.activity_promotion_lv_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_title);
      //  title.setText("Product Category");
        title.setText(ProductName);  //By Anusha
        title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_product, 0, 0, 0);
        title.setCompoundDrawablePadding(10);
        title.setTypeface(FontProvider.getInstance().tfBold);

        getProductLevelOne(Categoryid);
        //getProducts();
    }

   /* private void getProducts() {
        UserController userController = new UserController(this, "updateProducts");
        userController.getProducts();
    }

    public void updateProducts(Object object) {
        mProducts = ((UserController) object).mProducts;
        if (mProducts != null) {

            for (Product product : mProducts) {
                if((product.Name).equalsIgnoreCase(ProductName)){
                    Categoryid = product.Id;
                    getProductLevelOne(Categoryid);
                    break;
                }
               // getProductLevelOne(product.Id);
            }
        }
    }  */

    private void getProductLevelOne(String productId) {
        UserController userController = new UserController(this, "updateProductsLevelOne");
        userController.getProductLevelOne(productId);
    }

    //Anusha
    public void updateProductsLevelOne(Object object) {
        mProducts = ((UserController) object).mProducts;
        if (mProducts != null) {
            for (Product product : mProducts) {
                 getProductLevelTwo(Categoryid,product.Id);
            }
        }
    }

    private void getProductLevelTwo(String productId,String productLevelOneId) {
        UserController userController = new UserController(this, "updateProductsLevelTwo");
        userController.getProductLevelTwo(productId, productLevelOneId);
    }

    public void updateProductsLevelTwo(Object object) {
        ArrayList<Product> products = ((UserController) object).mProducts;
        final String productId = ((UserController) object).selectedProductId;
        if (productId != null) {
            mProductCategories.put(productId, products);
            if (mProductCategories.size() == mProducts.size()) {
                ProductsCategoryAdapter productsCategoryAdapter = new ProductsCategoryAdapter(this, mProducts, mProductCategories);
                lvProducts.setAdapter(productsCategoryAdapter);
                lvProducts.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Id", Categoryid);
                        bundle.putSerializable("OneId", mProducts.get(groupPosition).Id);
                        bundle.putSerializable("TwoId", mProductCategories.get(mProducts.get(groupPosition).Id).get(childPosition).Id);
                        Navigator.getInstance().navigateToActivityWithData(ProductActivity.this, ProductLevel3Activity.class, bundle);
                        return true;
                    }
                });
                for(int i=0;i<mProducts.size();i++)
                {
                    lvProducts.expandGroup(i);
                }
                lvProducts.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId) {
                        lvProducts.expandGroup(itemPosition);
                        return true;
                    }
                });
            }
        }
    }

 /*   public void updateProductsLevelOne(Object object) {
       // ArrayList<Product> products = ((UserController) object).mProducts;
       // final String productId = ((UserController) object).selectedProductId;
        mProductsList = ((UserController) object).mProducts;
        if (mProductsList != null) {
            ProductsAdapter productsAdapter = new ProductsAdapter(this, mProductsList);
            lvProducts.setAdapter(productsAdapter);
            lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Id", Categoryid);
                    bundle.putSerializable("OneId", mProductsList.get(position).Id);
                    Navigator.getInstance().navigateToActivityWithData(ProductActivity.this, ProductLevel2Activity.class, bundle);
                }
            });  */
           // mProductCategories.put(productId, products);
           // if (mProductCategories.size() == mProducts.size()) {
             //   ProductsCategoryAdapter productsCategoryAdapter = new ProductsCategoryAdapter(this, mProducts, mProductCategories);
             //   lvProducts.setAdapter(productsCategoryAdapter);
             //   lvProducts.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
               //     @Override
                //    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //        Bundle bundle = new Bundle();
                  //      bundle.putSerializable("Id", mProducts.get(groupPosition).Id);
                  //      bundle.putSerializable("OneId", mProductCategories.get(mProducts.get(groupPosition).Id).get(childPosition).Id);
                  //      Navigator.getInstance().navigateToActivityWithData(ProductActivity.this, ProductLevel2Activity.class, bundle);
                     //   return true;
                   // }
              //  });
                //for(int i=0;i<mProducts.size();i++)
               // {
                  //  lvProducts.expandGroup(i);
              //  }
               // lvProducts.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                //    public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId) {
                 //       lvProducts.expandGroup(itemPosition);
                 //       return true;
                 //   }
               // });
            //}
      //  }
    //}

//    public void updateProducts(Object object) {
//        mProducts = ((UserController) object).mProducts;
//        if (mProducts != null) {
//            ProductsAdapter productsAdapter = new ProductsAdapter(this, mProducts);
//            lvProducts.setAdapter(productsAdapter);
//            lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("Id", mProducts.get(position).Id);
//                    Navigator.getInstance().navigateToActivityWithData(ProductActivity.this, ProductLevel1Activity.class, bundle);
//                }
//            });
//        }
//    }

    public void goBack(View v) {
        finish();
    }

}
