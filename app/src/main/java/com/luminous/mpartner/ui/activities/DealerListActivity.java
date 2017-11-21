package com.luminous.mpartner.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.luminous.mpartner.adapters.DealersAdapter;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.database.DealerDatabase;
import com.luminous.mpartner.models.Dealer;
import com.luminous.mpartner.utils.FontProvider;

import java.util.ArrayList;

public class DealerListActivity extends AppCompatActivity {

    private ListView lvDealers;
    private ArrayList<Dealer> mDealersList, mSearchDealersList;
    private EditText etSearch;
    private String mSearchKey = "";
    private DealersAdapter mAdapter;
    private DealerDatabase dbDealerDatabase = new DealerDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareViews();
    }

    private void prepareViews() {
        setContentView(R.layout.activity_dealer_list);
        lvDealers = (ListView) findViewById(R.id.activity_dealer_list_lv_dealers);
        etSearch = (EditText) findViewById(R.id.activity_dealer_list_et_search);

        UserController controller = new UserController(this, "updateDealerList");
        controller.getDealerList("Active");

        mSearchDealersList = new ArrayList<Dealer>();

        prepareToolbar();
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        tvTitle.setTypeface(FontProvider.getInstance().tfBold);
        tvTitle.setText("Punch Secondary");
    }

    public void updateDealerList(Object object) {
        mDealersList = ((UserController) object).mDealersList;
        if (mDealersList != null) {
            if (!((UserController) object).isFromDB) {
                dbDealerDatabase.insertDealers(mDealersList);
            }
            mSearchDealersList.clear();
            mSearchDealersList.addAll(mDealersList);
            mAdapter = new DealersAdapter(this, mSearchDealersList);

            lvDealers.setAdapter(mAdapter);
            lvDealers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    Intent intent = new Intent(DealerListActivity.this, SummaryActivity.class);
                    intent.putExtra("Dealer", mDealersList.get(position));
                    startActivity(intent);
                    finish();
                }
            });
            etSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    mSearchKey = s.toString();
                    populateSearchResults();
                }
            });
        }
    }

    public void searchDealerList(View view) {
        mSearchKey = etSearch.getText().toString();
        if (mSearchKey.trim().length() > 0) {
            populateSearchResults();
        } else {
            mSearchDealersList.clear();
            mSearchDealersList.addAll(mDealersList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("DefaultLocale")
    private void populateSearchResults() {
        mSearchDealersList.clear();
        for (Dealer dealer : mDealersList) {
            if (dealer.getDealerName().toLowerCase()
                    .contains(mSearchKey.toLowerCase())) {
                mSearchDealersList.add(dealer);
            }
        }

        mAdapter.notifyDataSetChanged();

    }

}
