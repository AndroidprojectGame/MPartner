package com.luminous.mpartner.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.luminous.mpartner.R;
import com.luminous.mpartner.adapters.SerialAdapter;
import com.luminous.mpartner.constants.AppConstants;
import com.luminous.mpartner.constants.UrlConstants;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.database.AccessCodeDatabase;
import com.luminous.mpartner.database.OrderSummaryDatabase;
import com.luminous.mpartner.models.AccessCode;
import com.luminous.mpartner.models.Dealer;
import com.luminous.mpartner.models.DealerAddress;
import com.luminous.mpartner.models.Secondary;
import com.luminous.mpartner.utils.AppUtility;
import com.luminous.mpartner.utils.FontProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SummaryActivity extends AppCompatActivity {

    String addressFormatter = "<font color=\"black\"><b>%s<br><br>Address: </b></font><font color=\"grey\">%s</font>";
    private TextView tvAddress;
    private Dealer mDealer;
    private ArrayList<String> mSerialList;
    private ArrayList<Boolean> mSerialStockList;
    ListView lvSerialNoList;
    private SerialAdapter mSerialListAdapter;

    private OrderSummaryDatabase dbSummary = new OrderSummaryDatabase(this);
    private AccessCodeDatabase dbAccessCode = new AccessCodeDatabase(this);
    private ArrayList<AccessCode> mAccessCodeList;
    private DealerAddress mDealerAddress;
    private LinearLayout llManualContainer;
    private EditText etManual;
    private static SummaryActivity instance = null;

    public static SummaryActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        prepareViews();
    }

    private void prepareViews() {
        setContentView(R.layout.activity_summary);
        tvAddress = (TextView) findViewById(R.id.activity_summary_tv_dealer);
        lvSerialNoList = (ListView) findViewById(R.id.activity_summary_lv_serial);
        llManualContainer = (LinearLayout) findViewById(R.id.activity_summary_ll_manual_container);
        etManual = (EditText) findViewById(R.id.activity_summary_et_manual);

        // Initializing data
        mSerialList = new ArrayList<String>();
        mSerialStockList = new ArrayList<Boolean>();
        mSerialListAdapter = new SerialAdapter(this, mSerialList,
                mSerialStockList);
        lvSerialNoList.setAdapter(mSerialListAdapter);

        // Setting Data
        mDealer = (Dealer) getIntent().getSerializableExtra("Dealer");
        if (mDealer != null) {
            tvAddress.setText(Html.fromHtml(String.format(addressFormatter,
                    mDealer.getDealerName(), "")));
            getDealerAddress();
        }

        getAccessCodes();
        prepareToolbar();
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        tvTitle.setTypeface(FontProvider.getInstance().tfBold);
        tvTitle.setText("Punch Secondary");
    }


    private void getAccessCodes() {
        UserController userController = new UserController(this,
                "updateAccessCodes");
        userController.getAccessCodes();
    }

    public void updateAccessCodes(Object object) {
        mAccessCodeList = ((UserController) object).mAccessCodeList;
        if (mAccessCodeList != null) {
            if (mAccessCodeList.size() > 0) {
                dbAccessCode.deleteAllAccessCodes();
                for (AccessCode accessCode : mAccessCodeList) {
                    dbAccessCode.insertAccessCode(accessCode);
                }
            }
        } else {
            mAccessCodeList = dbAccessCode.getAllAccesCodes();
        }
    }

    private void getDealerAddress() {
        UserController userController = new UserController(this,
                "updateDealerAddress");
        userController.getDealerAddress(mDealer.getDealerCode());
    }

    public void updateDealerAddress(Object object) {
        mDealerAddress = ((UserController) object).mDealerAddress;
        if (mDealerAddress != null) {
            tvAddress.setText(Html.fromHtml(String.format(addressFormatter,mDealerAddress.getDealerName(),
                    mDealerAddress.getDealerAddress())));
        }
    }

    public void openScanner(View v) {
		if (isCameraAvailable()) {
			Intent intent = new Intent(this, ScannerActivity.class);
			intent.putExtra(AppConstants.BUNDLE_SERIAL_LIST, mSerialList);
			startActivityForResult(intent, AppConstants.ZBAR_SCANNER_REQUEST);
		} else {
			Toast.makeText(this, "Rear Facing Camera Unavailable",
					Toast.LENGTH_SHORT).show();
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
                    updateSerialNumberList(data
                            .getStringArrayListExtra(AppConstants.SCAN_RESULT));
                } else if (resultCode == RESULT_CANCELED && data != null) {
                    String error = data.getStringExtra(AppConstants.ERROR_INFO);
                    if (!TextUtils.isEmpty(error)) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void updateSerialNumberList(ArrayList<String> serials) {
        mSerialList.clear();
        mSerialList.addAll(serials);
        mSerialStockList.clear();
        for (String serial : mSerialList) {
            mSerialStockList.add(true);
        }
        mSerialListAdapter.notifyDataSetChanged();
    }

    public void submitOrder(View view) {
        if (mSerialList.size() > 0) {

            mSerialStockList.clear();
            for (String serial : mSerialList) {
                mSerialStockList.add(checkSerialNumberAndQty(serial));
            }
            mSerialListAdapter.notifyDataSetChanged();

            boolean isInStock = true;
            for (Boolean stock : mSerialStockList) {
                if (stock == false) {
                    isInStock = false;
                    break;
                }
            }

            if (isInStock) {
                UserController userController = new UserController(this,
                        "updateSubmitStatus");
                userController
                        .SubmitOrder(mDealer.getDealerCode(), mSerialList);
            } else {
                Toast.makeText(
                        view.getContext(),
                        "There are one or more invalid or out of stock serial numbers.",
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(view.getContext(),
                    "Please scan at least one serial number.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void updateSubmitStatus(Object object) {
        UserController userController = (UserController) object;
        if (userController.mCode.equalsIgnoreCase(UrlConstants.STATUS_SUCCESS)) {

            Toast.makeText(this, userController.mMessage, Toast.LENGTH_LONG)
                    .show();

            Intent intent = new Intent(SummaryActivity.this,
                    DealerListActivity.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, userController.mMessage, Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void saveDraft(View view) {
        if (mSerialList.size() > 0) {

            mSerialStockList.clear();
            for (String serial : mSerialList) {
                mSerialStockList.add(checkSerialNumberAndQty(serial));
            }
            mSerialListAdapter.notifyDataSetChanged();

            boolean isInStock = true;
            for (Boolean stock : mSerialStockList) {
                if (stock == false) {
                    isInStock = false;
                    break;
                }
            }

            if (isInStock) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        view.getContext());

                // set title
                alertDialogBuilder.setTitle("Alert");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure to save as draft?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        String date = new SimpleDateFormat(
                                                "dd-MM-yyyy")
                                                .format(new Date());
                                        String time = new SimpleDateFormat(
                                                "hh:mm aa").format(new Date());
                                        dbSummary
                                                .insertOrder(new Secondary(
                                                        "",
                                                        mDealer.getDealerCode(),
                                                        mDealer.getDealerName(),
                                                        mDealerAddress != null ? mDealerAddress
                                                                .getDealerAddress()
                                                                : "",
                                                        AppUtility
                                                                .convertToStringSerials(mSerialList),
                                                        "" + mSerialList.size(),
                                                        "" + date, "" + time));
                                        Toast.makeText(
                                                SummaryActivity.this,
                                                "Successfully saved in drafts.",
                                                Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(
                                                SummaryActivity.this,
                                                DealerListActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            } else {
                Toast.makeText(
                        view.getContext(),
                        "There are one or more invalid or out of stock serial numbers.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(view.getContext(),
                    "Please scan at least one serial number.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkSerialNumberAndQty(String serialNumber) {

        String serialAccessCode = serialNumber.substring(3, 6);
        boolean isValidSerial = false;
        for (AccessCode accessCode : mAccessCodeList) {
            Log.d(">>>>>>>>>", accessCode.getAccessCode());
            if (accessCode.getAccessCode().contains(serialAccessCode)) {
                if (Integer.parseInt(accessCode.getTAvlQuantity()) > 0) {
                    isValidSerial = true;
                } else {
                    isValidSerial = false;
                }
                break;
            }
        }
        return isValidSerial;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SummaryActivity.this,
                DealerListActivity.class);
        startActivity(intent);
        finish();
    }

    public void removeSerial(int position) {
        mSerialList.remove(position);
        mSerialListAdapter.notifyDataSetChanged();
    }

    public void toggleManual(View v) {
        if (llManualContainer.getVisibility() == View.GONE) {
            etManual.setText("");
            llManualContainer.setVisibility(View.VISIBLE);
        } else {
            llManualContainer.setVisibility(View.GONE);
        }
    }

    public void addSerialManually(View v) {

        if (etManual.getText().toString().trim().length() > 0) {
            if (etManual.getText().toString().trim().length() == 14) {

                if (hasWhiteSpace(etManual.getText().toString())) {
                    Toast.makeText(this, "Serial No. should not contain whitespace.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (etManual.getText().toString().matches("[a-zA-z0-9]*")) {
                        checkSerialNumberAndAdd(etManual.getText().toString());
                    } else {
                        Toast.makeText(this, "Serial No. should be alphanumeric.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Serial No. should be 14 characters long.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean hasWhiteSpace(String s) {
        return s.indexOf(" ") >= 0;
    }

    private void checkSerialNumberAndAdd(String serialNumber) {

        String serialAccessCode = serialNumber.substring(3, 6);
        boolean isValidSerial = false;
        for (AccessCode accessCode : mAccessCodeList) {
            Log.d(">>>>>>>>>", accessCode.getAccessCode());
            if (accessCode.getAccessCode().contains(serialAccessCode)) {

                isValidSerial = true;
                break;
            }
        }

        if (isValidSerial) {
            ArrayList<String> newArr = new ArrayList<String>();
            newArr.addAll(mSerialList);
            if (!newArr.contains(serialNumber)) {
                newArr.add(serialNumber);
                updateSerialNumberList(newArr);
                etManual.setText("");
            } else {
                Toast.makeText(this, "Serial Number Already Present.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please Enter a Valid Serial Number.",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
