package com.luminous.mpartner.ui.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.adapters.NotificationsAdapter;
import com.luminous.mpartner.constants.AppConstants;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.Notification;
import com.luminous.mpartner.ui.custom.swipelistview.SwipeMenu;
import com.luminous.mpartner.ui.custom.swipelistview.SwipeMenuCreator;
import com.luminous.mpartner.ui.custom.swipelistview.SwipeMenuItem;
import com.luminous.mpartner.ui.custom.swipelistview.SwipeMenuListView;
import com.luminous.mpartner.utils.FontProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotificationsActivity extends AppCompatActivity {

    private SwipeMenuListView lvNotifications;
    private ArrayList<Notification> mNotifications;
    private TextView tvStartDate;
    private SimpleDateFormat dateFormatter;
    private String dateFormat = "%s  <b>to</b>  %s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        mNotifications = getIntent().getParcelableArrayListExtra(AppConstants.EXTRA_NOTIFICATIONS);

        prepareViews();
    }

    private void prepareViews() {

        lvNotifications = (SwipeMenuListView) findViewById(R.id.activity_notifications_lv_notifications);
        tvStartDate = (TextView) findViewById(R.id.activity_notifications_tv_select_dates);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText("Notifications");
       // title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mail, 0, 0, 0);
        title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.noti, 0, 0, 0);    //By Anusha
        title.setCompoundDrawablePadding(10);
        title.setTypeface(FontProvider.getInstance().tfBold);

        NotificationsAdapter notificationsAdapter = new NotificationsAdapter(this,mNotifications);
        lvNotifications.setAdapter(notificationsAdapter);
        createSwipeLayout();

        String currentDateString = getIntent().getStringExtra(AppConstants.EXTRA_DATE);
        tvStartDate.setTypeface(FontProvider.getInstance().tfRegular);
        tvStartDate.setText(Html.fromHtml(String.format(dateFormat, currentDateString, currentDateString)));

        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDateDialog();
            }
        });
    }

    private void createSwipeLayout() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                switch (menu.getViewType()) {
                    case 0:
                        // create "delete" item
                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                NotificationsActivity.this);
                        // set item background
                        deleteItem.setBackground(new ColorDrawable(Color.rgb(0x00,
                                0x60, 0xA0)));
                        // set item width
                        deleteItem.setWidth(150);
                        deleteItem.setIcon(R.drawable.icon_delete);

                        // add to menu
                        menu.addMenuItem(deleteItem);

                        break;
                }
            }
        };


        lvNotifications.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        // set creator
        lvNotifications.setMenuCreator(creator);

        lvNotifications.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        break;
                }
                return false;
            }
        });
    }

    private void showStartDateDialog()
    {
        Calendar mCurrentDate = Calendar.getInstance();
        final Calendar startDateCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        // Initialize Date Picker
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > android.os.Build.VERSION_CODES.LOLLIPOP) {
            DatePickerDialog startDatePickerDialog = new DatePickerDialog(this, R.style.datepicker,
                    new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if (view.isShown()) {
                                // updateDate(year, monthOfYear, dayOfMonth);

                                startDateCalendar.set(year, monthOfYear, dayOfMonth);
                                String startDate = dateFormatter.format(startDateCalendar
                                        .getTime());
                                showEndDateDialog(startDate);
                            }
                        }

                    }, mCurrentDate.get(Calendar.YEAR),
                    mCurrentDate.get(Calendar.MONTH),
                    mCurrentDate.get(Calendar.DAY_OF_MONTH));
            startDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            startDatePickerDialog.show();
        }
        else {
            DatePickerDialog startDatePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if (view.isShown()) {
                                // updateDate(year, monthOfYear, dayOfMonth);

                                startDateCalendar.set(year, monthOfYear, dayOfMonth);
                                String startDate = dateFormatter.format(startDateCalendar
                                        .getTime());
                                showEndDateDialog(startDate);
                            }
                        }

                    }, mCurrentDate.get(Calendar.YEAR),
                    mCurrentDate.get(Calendar.MONTH),
                    mCurrentDate.get(Calendar.DAY_OF_MONTH));
            startDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            startDatePickerDialog.show();
        }
    }

    private void showEndDateDialog(final String startDate)
    {
        Calendar mCurrentDate = Calendar.getInstance();
        final Calendar startDateCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        // Initialize Date Picker
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > android.os.Build.VERSION_CODES.LOLLIPOP) {
            DatePickerDialog startDatePickerDialog = new DatePickerDialog(this, R.style.datepicker,
                    new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if (view.isShown()) {
                                startDateCalendar.set(year, monthOfYear, dayOfMonth);
                                String endDate = dateFormatter.format(startDateCalendar
                                        .getTime());
                                tvStartDate.setText(Html.fromHtml(String.format(dateFormat, startDate, endDate)));
                                getNotifications(startDate, endDate);
                            }
                        }

                    }, mCurrentDate.get(Calendar.YEAR),
                    mCurrentDate.get(Calendar.MONTH),
                    mCurrentDate.get(Calendar.DAY_OF_MONTH));
            startDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            startDatePickerDialog.show();
        }
        else {
            DatePickerDialog startDatePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if (view.isShown()) {
                                startDateCalendar.set(year, monthOfYear, dayOfMonth);
                                String endDate = dateFormatter.format(startDateCalendar
                                        .getTime());
                                tvStartDate.setText(Html.fromHtml(String.format(dateFormat, startDate, endDate)));
                                getNotifications(startDate, endDate);
                            }
                        }

                    }, mCurrentDate.get(Calendar.YEAR),
                    mCurrentDate.get(Calendar.MONTH),
                    mCurrentDate.get(Calendar.DAY_OF_MONTH));
            startDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            startDatePickerDialog.show();
        }
    }


    public void getNotifications(String startDate, String endDate) {
        UserController userController = new UserController(this, "updateNotifications");
        userController.getNotifications(startDate, endDate);
    }

    public void updateNotifications(Object object) {
        mNotifications = ((UserController) object).mNotifications;
        if (mNotifications != null) {
            NotificationsAdapter notificationsAdapter = new NotificationsAdapter(this,mNotifications);
            lvNotifications.setAdapter(notificationsAdapter);;
        }
    }

    public  void  goBack(View v)
    {
        finish();
    }

}
