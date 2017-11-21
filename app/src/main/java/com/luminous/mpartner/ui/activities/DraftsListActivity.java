package com.luminous.mpartner.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.adapters.SecondaryAdapter;
import com.luminous.mpartner.database.OrderSummaryDatabase;
import com.luminous.mpartner.models.Secondary;
import com.luminous.mpartner.utils.FontProvider;

import java.util.List;

public class DraftsListActivity extends AppCompatActivity {

    private ListView lvDealers;
    private List<Secondary> secondaryList;
    SecondaryAdapter mAdapter;
    OrderSummaryDatabase db = new OrderSummaryDatabase(this);
    private TextView tvNoDraft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareViews();
    }

    private void prepareViews() {
        setContentView(R.layout.activity_drafts_list);
        lvDealers = (ListView) findViewById(R.id.activity_draft_list_lv_drafts);
        tvNoDraft = (TextView) findViewById(R.id.activity_draft_list_tv_no_draft);
        getSecondaryList();
        prepareToolbar();
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        tvTitle.setTypeface(FontProvider.getInstance().tfBold);
        tvTitle.setText("Saved Drafts");
    }

    public void getSecondaryList() {
        secondaryList = db.getAllOrders();
        if (secondaryList != null) {
            if (secondaryList.size() > 0) {
                tvNoDraft.setVisibility(View.INVISIBLE);
                mAdapter = new SecondaryAdapter(this, secondaryList);

                lvDealers.setAdapter(mAdapter);
				lvDealers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Intent intent = new Intent(DraftsListActivity.this,
								DraftDetailActivity.class);
						intent.putExtra("Secondary",
								secondaryList.get(position));
						startActivity(intent);
						finish();
					}
				});
            } else {
                tvNoDraft.setVisibility(View.VISIBLE);
            }
        }
    }
}
