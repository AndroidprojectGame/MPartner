package com.luminous.mpartner.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.luminous.mpartner.R;
import com.luminous.mpartner.adapters.ReportsAdapter;
import com.luminous.mpartner.adapters.SchemesDropdownAdapter;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.Report;
import com.luminous.mpartner.models.Scheme;
import com.luminous.mpartner.utils.AppSharedPrefrences;

import java.util.ArrayList;
import java.util.List;


public class ReportsFragment extends Fragment {

    private static ReportsFragment instance;
    private View vRootView;
    private ListView lvReports;
    private EditText etSearch;
    private ReportsAdapter mAdapter;
    private Spinner spSchemes;
    private ArrayList<Scheme> mSchemes;
    public String mSelectedScheme = "";

    public static ReportsFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vRootView = inflater.inflate(R.layout.fragment_reports, container, false);
        lvReports = (ListView) vRootView.findViewById(R.id.fragment_reports_lv_report);
        etSearch = (EditText) vRootView.findViewById(R.id.fragment_reports_et_search);
        spSchemes = (Spinner) vRootView.findViewById(R.id.fragment_reports_sp_schemes);

        instance = this;

        return vRootView;
    }

    private void setupSearchFunctionality() {
        etSearch.setVisibility(View.VISIBLE);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.filter(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void updateSchemes(Object object) {
        mSchemes = ((UserController) object).mSchemeList;
        if (mSchemes != null) {
            spSchemes.setAdapter(new SchemesDropdownAdapter(getActivity(), mSchemes));
            spSchemes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mSelectedScheme = position > 0 ? mSchemes.get(position - 1).schemeValue : "";
                    getReports();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public void getReports() {
        UserController userController = new UserController(getActivity(), "updateReports");
        userController.getReports(mSelectedScheme, AppSharedPrefrences.getInstance(getActivity()).getUserId(), "");
    }

    public void updateReports(Object object) {
        List<Report> reports = ((UserController) object).mReports;
        if (reports != null) {
            mAdapter = new ReportsAdapter(getActivity(), reports);
            lvReports.setAdapter(mAdapter);
            setupSearchFunctionality();
        }
    }
}
