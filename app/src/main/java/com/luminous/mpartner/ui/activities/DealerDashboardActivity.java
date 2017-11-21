package com.luminous.mpartner.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.luminous.mpartner.R;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.database.AccessCodeDatabase;
import com.luminous.mpartner.models.AccessCode;
import com.luminous.mpartner.utils.FontProvider;

import java.util.ArrayList;

public class DealerDashboardActivity extends AppCompatActivity {

    private AccessCodeDatabase db = new AccessCodeDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareViews();
    }

    private void prepareViews() {
        setContentView(R.layout.activity_dashboard_dealer);
        getAccessCodes();
        prepareToolbar();
    }

    private void getAccessCodes() {
        UserController userController = new UserController(this, "updateAccessCodes");
        userController.getAccessCodes();
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_bb, 0, 0, 0);
        tvTitle.setCompoundDrawablePadding(10);
        tvTitle.setTypeface(FontProvider.getInstance().tfBold);
        tvTitle.setText("B2B");
    }

    public void updateAccessCodes(Object object) {
        ArrayList<AccessCode> accessCodeList = ((UserController) object).mAccessCodeList;
        if (accessCodeList != null) {
            if (accessCodeList.size() > 0) {
                db.deleteAllAccessCodes();
                for (AccessCode accessCode : accessCodeList) {
                    db.insertAccessCode(accessCode);
                }
            }
        }
    }

    public void navigateToDealersList(View v) {
        Intent intent = new Intent(this, DealerListActivity.class);
        startActivity(intent);
    }

    public void navigateToSecondarySales(View v) {
        Toast.makeText(v.getContext(), "This part of application is under development.", Toast.LENGTH_SHORT).show();
    }

    public void navigateToSavedDrafts(View v) {
        Intent intent = new Intent(this, DraftsListActivity.class);
        startActivity(intent);
    }

}
