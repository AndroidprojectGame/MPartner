package com.luminous.mpartner.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.luminous.mpartner.R;
import com.luminous.mpartner.adapters.PagerAdapter;
import com.luminous.mpartner.constants.AppConstants;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.ui.fragments.EntryFragment;
import com.luminous.mpartner.ui.fragments.ReportsFragment;
import com.luminous.mpartner.utils.FontProvider;

public class WRSActivity extends AppCompatActivity {

    private final int REQUEST_CAMERA_USE = 200;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrs);

        prepareToolbar();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Entry"));
        tabLayout.addTab(tabLayout.newTab().setText("Reports"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getSchemes();
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText("WRS");
        title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_wrs, 0, 0, 0);
        title.setCompoundDrawablePadding(10);
        title.setTypeface(FontProvider.getInstance().tfBold);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goBack(View v) {
        finish();
    }

    public void getSchemes() {
        UserController userController = new UserController(this, "updateSchemes");
        userController.getSchemes();
    }

    public void updateSchemes(Object object) {
        ReportsFragment.getInstance().updateSchemes(object);
    }

    public void openScanner(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_USE);

            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

        if (isCameraAvailable()) {
            Intent intent = new Intent(this, SimpleScannerActivity.class);
            startActivityForResult(intent, AppConstants.ZBAR_SCANNER_REQUEST);
        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable",
                    Toast.LENGTH_SHORT).show();
        }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_USE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "Permission Granted");
                } else {
                    Log.e(TAG, "Permission Denied");
                }
                return;
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppConstants.ZBAR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {
                    EntryFragment.getInstance().updateSerialNumber(data
                            .getStringExtra(AppConstants.SCAN_RESULT));
                } else if (resultCode == RESULT_CANCELED && data != null) {
                    String error = data.getStringExtra(AppConstants.ERROR_INFO);
                    if (!TextUtils.isEmpty(error)) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public void updateDealerByDist(Object object)
    {
        EntryFragment.getInstance().updateDealerByDist(object);
    }

    public void updateDistByDealer(Object object)
    {
        EntryFragment.getInstance().updateDistByDealer(object);
    }

    public void updateStates(Object object) {
        EntryFragment.getInstance().updateStates(object);
    }

    public void updateCities(Object object) {
        EntryFragment.getInstance().updateCities(object);
    }

    public void updateSerialNumberValidation(Object object) {
        EntryFragment.getInstance().updateSerialNumberValidation(object);

    }

    public void updateEntryStatus(Object object) {
        EntryFragment.getInstance().updateEntryStatus(object);
    }

    public void updateReports(Object object) {
        ReportsFragment.getInstance().updateReports(object);
    }

    public void updateDealerAddress(Object object) {
        EntryFragment.getInstance().updateDealerAddress(object);
    }
}