package com.luminous.mpartner.ui.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.luminous.mpartner.Global;
import com.luminous.mpartner.R;
import com.luminous.mpartner.adapters.CityDropdownAdapter;
import com.luminous.mpartner.adapters.DealersDropdownAdapter;
import com.luminous.mpartner.adapters.StateDropdownAdapter;
import com.luminous.mpartner.constants.AppConstants;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.City;
import com.luminous.mpartner.models.Dealer;
import com.luminous.mpartner.models.DealerAddress;
import com.luminous.mpartner.models.State;
import com.luminous.mpartner.utils.AppSharedPrefrences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EntryFragment extends Fragment {

    private View vRootView;
    private EditText etSerialNumber, etName, etPhone, etLandline, etAddress;
    private static EntryFragment instance;
    private Spinner spDealers, spState, spCity;
    private LinearLayout llForm;
    private DatePickerDialog startDatePickerDialog;
    private Calendar mStartDate, mCurrentDate;
    private TextView tvSalesDate;
    private SimpleDateFormat dateFormatter;
    private Button btSubmit;
    private ArrayList<Dealer> dealerList;
    private ArrayList<City> cityList;
    private ArrayList<State> stateList;
    private TextView tvDealerAddress;
    String addressFormatter = "<font color=\"black\"><b>%s<br><br>Address: </b></font><font color=\"grey\">%s</font>";
    private TextView tvDealer;

    public static EntryFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vRootView = inflater.inflate(R.layout.fragment_entry, container, false);

        instance = this;
        prepareViews();

        return vRootView;
    }

    private void prepareViews() {
        etSerialNumber = (EditText) vRootView.findViewById(R.id.fragment_entry_et_srno);
        llForm = (LinearLayout) vRootView.findViewById(R.id.fragment_entry_ll_form);
        spDealers = (Spinner) vRootView.findViewById(R.id.fragment_entry_sp_dealer);
        tvSalesDate = (TextView) vRootView.findViewById(R.id.fragment_entry_tv_sale_date);
        tvDealer = (TextView) vRootView.findViewById(R.id.fragment_entry_tv_dealer);
        tvDealerAddress = (TextView) vRootView.findViewById(R.id.fragment_entry_tv_dealer_address);
        etName = (EditText) vRootView.findViewById(R.id.fragment_entry_et_name);
        etPhone = (EditText) vRootView.findViewById(R.id.fragment_entry_et_phone);
        etLandline = (EditText) vRootView.findViewById(R.id.fragment_entry_et_landline);
        etAddress = (EditText) vRootView.findViewById(R.id.fragment_entry_et_address);
        spState = (Spinner) vRootView.findViewById(R.id.fragment_entry_sp_state);
        spCity = (Spinner) vRootView.findViewById(R.id.fragment_entry_sp_city);
        btSubmit = (Button) vRootView.findViewById(R.id.fragment_entry_bt_submit);

        mCurrentDate = Calendar.getInstance();
        mStartDate = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        // Initialize Date Picker
        startDatePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mStartDate.set(year, monthOfYear, dayOfMonth);
                        tvSalesDate.setText(dateFormatter.format(mStartDate
                                .getTime()));
                    }

                }, mCurrentDate.get(Calendar.YEAR),
                mCurrentDate.get(Calendar.MONTH),
                mCurrentDate.get(Calendar.DAY_OF_MONTH));
        startDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

        etSerialNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    getSerialNumberValidation();
                    return true;
                }
                return false;
            }
        });

        tvSalesDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatePickerDialog.show();
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEntry();
            }
        });

        if (AppSharedPrefrences.getInstance(getActivity()).getUserType().equalsIgnoreCase(AppConstants.USER_DISTRIBUTER)) {
            tvDealer.setText("Dealer");
            getDealers();
        }
        else
        {
            tvDealer.setText("Distributer");
            getDistributers();
        }

        getStates();
    }

    private void getSerialNumberValidation() {
        UserController userController = new UserController(getActivity(), "updateSerialNumberValidation");
        userController.getSerialNumberValidation(etSerialNumber.getText().toString());
    }

    private void getDealers() {
        UserController userController = new UserController(getActivity(), "updateDealerByDist");
        userController.getDealerListByDistributer(AppSharedPrefrences.getInstance(getActivity()).getUserId());
    }

    private void getDistributers() {
        UserController userController = new UserController(getActivity(), "updateDistByDealer");
        userController.getDistributerListByDealer(AppSharedPrefrences.getInstance(getActivity()).getUserId());
    }

    private void getStates() {
        UserController userController = new UserController(getActivity(), "updateStates");
        userController.getStateList();
    }

    private void getCities(String stateId) {
        UserController userController = new UserController(getActivity(), "updateCities");
        userController.getCityList(stateId);
    }

    public void updateSerialNumberValidation(Object object) {
        boolean isSerialNumberValid = ((UserController) object).isSerialValid;
        llForm.setVisibility(isSerialNumberValid ? View.VISIBLE : View.GONE);
        if (!isSerialNumberValid) {
            Global.showToast("" + ((UserController) object).mMessage);
        }
    }

    public void updateDealerByDist(Object object) {
        dealerList = ((UserController) object).mDealersList;
        if (dealerList != null) {
            spDealers.setAdapter(new DealersDropdownAdapter(getActivity(), dealerList));
            spDealers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        UserController userController = new UserController(getActivity(), "updateDealerAddress");
                        userController.getDealerAddress(dealerList.get(position - 1).getDealerCode());
                    } else {
                        tvDealerAddress.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public void updateDistByDealer(Object object) {
        dealerList = ((UserController) object).mDealersList;
        if (dealerList != null) {
            spDealers.setAdapter(new DealersDropdownAdapter(getActivity(), dealerList));
            spDealers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        UserController userController = new UserController(getActivity(), "updateDealerAddress");
                        userController.getDealerAddress(dealerList.get(position - 1).getDealerCode());
                    } else {
                        tvDealerAddress.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public void updateDealerAddress(Object object) {
        DealerAddress mDealerAddress = ((UserController) object).mDealerAddress;
        if (mDealerAddress != null) {
            tvDealerAddress.setText(Html.fromHtml(String.format(addressFormatter,
                    mDealerAddress.getDealerName(),
                    mDealerAddress.getDealerAddress())));
            tvDealerAddress.setVisibility(View.VISIBLE);
        }
    }

    public void updateStates(Object object) {
        stateList = ((UserController) object).mStateList;
        if (stateList != null) {
            spState.setAdapter(new StateDropdownAdapter(getActivity(), stateList));
        }
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    getCities(stateList.get(position - 1).getStateId());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void updateCities(Object object) {
        cityList = ((UserController) object).mCityList;
        if (cityList != null) {
            spCity.setAdapter(new CityDropdownAdapter(getActivity(), cityList));
        }
    }

    public void saveEntry() {

        if (spDealers.getSelectedItemPosition() == 0) {
            if (AppSharedPrefrences.getInstance(getActivity()).getUserType().equalsIgnoreCase(AppConstants.USER_DISTRIBUTER)) {
                Global.showToast("Please select a Dealer");
            }
            else
            {
                Global.showToast("Please select a Distributer");
            }
        } else if (tvSalesDate.getText().toString().equalsIgnoreCase("Sale Date")) {
            Global.showToast("Please select a sale date");
        } else if (etName.getText().toString().trim().length() == 0) {
            Global.showToast("Please enter customer name");
        } else if (etPhone.getText().toString().trim().length() == 0) {
            Global.showToast("Please select customer phone");
        } else if (etAddress.getText().toString().trim().length() == 0) {
            Global.showToast("Please enter customer address");
        } else if (spState.getSelectedItemPosition() == 0) {
            Global.showToast("Please select a State");
        } else if (spCity.getSelectedItemPosition() == 0) {
            Global.showToast("Please select a City");
        } else {
            UserController userController = new UserController(getActivity(), "updateEntryStatus");

            String distCode = "";
            String dlrCode = "";

            if (AppSharedPrefrences.getInstance(getActivity()).getUserType().equalsIgnoreCase(AppConstants.USER_DISTRIBUTER)) {
                distCode = AppSharedPrefrences.getInstance(getActivity()).getUserId();
                dlrCode = dealerList.get((spDealers.getSelectedItemPosition() - 1)).getDealerCode();
            }
            else
            {
                dlrCode = AppSharedPrefrences.getInstance(getActivity()).getUserId();
                distCode = dealerList.get((spDealers.getSelectedItemPosition() - 1)).getDealerCode();
            }

            userController.saveEntry(etSerialNumber.getText().toString(),
                    distCode,
                    dlrCode,
                    tvSalesDate.getText().toString(), etName.getText().toString(),
                    etPhone.getText().toString(), etLandline.getText().toString(),
                    stateList.get(spState.getSelectedItemPosition() - 1).getStateName(),
                    cityList.get(spCity.getSelectedItemPosition() - 1).getCityName(),
                    etAddress.getText().toString(), AppSharedPrefrences.getInstance(getActivity()).getUserId());
        }
    }

    public void updateEntryStatus(Object object) {
        Global.showToast("" + ((UserController) object).mMessage);
        refreshScreen();
    }

    private void refreshScreen() {
        etSerialNumber.setText("");
        llForm.setVisibility(View.GONE);
        spDealers.setSelection(0);
        tvSalesDate.setText("Sale Date");
        etName.setText("");
        etPhone.setText("");
        etLandline.setText("");
        etAddress.setText("");
        spState.setSelection(0);
        spCity.setAdapter(null);

    }

    public void updateSerialNumber(String serialNumber) {
        etSerialNumber.setText("" + serialNumber);
        getSerialNumberValidation();
    }
}
