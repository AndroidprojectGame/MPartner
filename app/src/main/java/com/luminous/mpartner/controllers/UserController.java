package com.luminous.mpartner.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.luminous.mpartner.Global;
import com.luminous.mpartner.constants.UrlConstants;
import com.luminous.mpartner.database.DealerDatabase;
import com.luminous.mpartner.models.AccessCode;
import com.luminous.mpartner.models.City;
import com.luminous.mpartner.models.ContactUs;
import com.luminous.mpartner.models.Dealer;
import com.luminous.mpartner.models.DealerAddress;
import com.luminous.mpartner.models.Function;
import com.luminous.mpartner.models.Notification;
import com.luminous.mpartner.models.Product;
import com.luminous.mpartner.models.ProductDetail;
import com.luminous.mpartner.models.Profile;
import com.luminous.mpartner.models.Promotion;
import com.luminous.mpartner.models.Report;
import com.luminous.mpartner.models.Scheme;
import com.luminous.mpartner.models.State;
import com.luminous.mpartner.service.CommonAsyncTask;
import com.luminous.mpartner.service.CommonAsyncTask1;
import com.luminous.mpartner.utils.AppSharedPrefrences;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;


public class UserController {

    private Context mContext;
    private String mMethodname;
    public String mCode;
    public String mMessage;
    public ArrayList<Dealer> mDealersList;
    public ArrayList<AccessCode> mAccessCodeList;
    public DealerAddress mDealerAddress;
    public boolean isFromDB;
    public boolean isSerialValid;
    public ArrayList<State> mStateList;
    public ArrayList<City> mCityList;
    public ArrayList<Report> mReports;
    public ArrayList<Scheme> mSchemeList;
    public ArrayList<Notification> mNotifications;
    public ArrayList<Product> mProducts;
    public ArrayList<Promotion> mPromotions;
    public ProductDetail mProductDetail;
    public Profile mProfileDetail;
    public ArrayList<ContactUs> mContactUs;
    public Promotion mPromotionDetail;
    public String selectedProductId;
    public String mImageUrl;
    public ArrayList<Function> mFunctionNames;

    public UserController(Context context, String methodName) {
        mContext = context;
        mMethodname = methodName;
    }

    @SuppressWarnings("rawtypes")
    public Object invoke(Object... parameters)
            throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException {
        Class[] argumentTypes = {Object.class};
        Log.e("", "====> " + argumentTypes.length);
        Method method = mContext.getClass().getMethod(mMethodname,
                argumentTypes);
        return method.invoke(mContext, this);
    }

    public void createOtp(String empId, String imei, String osversion,
                          String devicename, String appversion) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("DisID", empId);
        properties.put("imeinumber", imei);
        properties.put("osversion", osversion);
        properties.put("devicename", devicename);
        properties.put("appversion", appversion);

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        mCode = resultObj
                                .getPropertyAsString(UrlConstants.KEY_CODE);
                        mMessage = resultObj
                                .getPropertyAsString(UrlConstants.KEY_DESCRIPTION);
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_CREATE_OTP);
    }

    public void validateOtp(String empId, String imei, String osversion,
                            String devicename, String otp) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("DisID", empId);
        properties.put("imeinumber", imei);
        properties.put("osversion", osversion);
        properties.put("devicename", devicename);
        properties.put("otp", otp);

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    SoapObject resultObj = (SoapObject) response
                            .getProperty(0);
                    try {
                        mCode = resultObj
                                .getPropertyAsString(UrlConstants.KEY_CODE);
                        mMessage = resultObj
                                .getPropertyAsString(UrlConstants.KEY_DESCRIPTION);
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_VALIDATE_OTP);
    }

    public void getBannerImage(String date) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("date", date);

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    SoapObject resultObj = (SoapObject) response
                            .getProperty(0);
                    try {
                        mImageUrl = resultObj
                                .getPropertyAsString("Image");
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_BANNER);
    }

    public void getDealerList(String flag) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("SessionLoginDisID",
                AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("Flag", flag);

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        mDealersList = new ArrayList<Dealer>();
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Dealer dealer = new Dealer(record
                                    .getProperty(UrlConstants.KEY_DEALERCODE)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty(UrlConstants.KEY_DEALERNAME)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mDealersList.add(dealer);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        DealerDatabase dbDealerDatabase = new DealerDatabase(mContext);
                        mDealersList = new ArrayList<Dealer>();
                        mDealersList = dbDealerDatabase.getDealerList();
                        isFromDB = true;
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_DEALER_LIST);
    }


    public void getDealerAddress(String dealerId) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("DlrID", dealerId);

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        SoapObject ss = (SoapObject) resultObj.getProperty("StrssscDealerNameAddress");
                        if (ss != null) {
                            mDealerAddress = new DealerAddress(ss.getProperty(UrlConstants.KEY_DEALERADDRESS)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), ss.getProperty(UrlConstants.KEY_DEALERNAME)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_DEALER_ADDRESS);
    }


    public void getAccessCodes() {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("SessionLoginDisID",
                AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("Flag", "M");
        properties.put("ItemID", "All");


        new CommonAsyncTask(mContext, properties, false) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        mAccessCodeList = new ArrayList<AccessCode>();
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            AccessCode accessCode = new AccessCode(record
                                    .getProperty(UrlConstants.KEY_SKUMODELID)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty(UrlConstants.KEY_ACCESSCODE)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty(UrlConstants.KEY_SKUMODELNAME)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty(UrlConstants.KEY_QUANTITY)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mAccessCodeList.add(accessCode);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_ACCESS_CODE);
    }


    public void SubmitOrder(String dealerCode, ArrayList<String> serialNumbers) {
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("SessionLoginDisID",
                AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("DlrID", dealerCode);
        properties.put("Flag", "M");
        HashMap<String, ArrayList<String>> arrayProperties = new HashMap<String, ArrayList<String>>();
        arrayProperties.put("SerialNo", serialNumbers);

        new CommonAsyncTask(mContext, properties, arrayProperties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mMessage = response
                                .getPropertyAsString(0);
                        if (mMessage.contains("Successfully Submitted.")) {
                            mCode = UrlConstants.STATUS_SUCCESS;
                        } else {
                            mCode = UrlConstants.STATUS_ERROR;
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_SUBMIT_ORDER);
    }

    public void getSerialNumberValidation(String serialNumber) {
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("sr_no",
                serialNumber);

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        String invalid = response
                                .getPropertyAsString(0);
                        if (invalid.contains("Invalid Serial Number")) {
                            isSerialValid = false;
                            mMessage = invalid;
                        } else if (invalid.contains("Already Entered")) {
                            isSerialValid = false;
                            mMessage = invalid;
                        } else {
                            isSerialValid = true;
                        }

                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_SERIAL_EXISTANCCE);
    }

    public void getDealerListByDistributer(String distributerId) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("dist_code",
                distributerId);


        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mDealersList = new ArrayList<Dealer>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Dealer dealer = new Dealer(record
                                    .getProperty(UrlConstants.KEY_DEALER_CODE)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty(UrlConstants.KEY_DEALER_NAME)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mDealersList.add(dealer);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_DEALER_BY_DISTRIBUTER_LIST);
    }

    public void getDistributerListByDealer(String dealerId) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("dlr_code",
                dealerId);


        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mDealersList = new ArrayList<Dealer>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Dealer dealer = new Dealer(record
                                    .getProperty("dist_code")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("dist_name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mDealersList.add(dealer);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_DISTRIBUTER_BY_DEALER_LIST);
    }

    public void getStateList() {

        HashMap<String, String> properties = new HashMap<String, String>();

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mStateList = new ArrayList<State>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            State state = new State(record
                                    .getProperty(UrlConstants.KEY_STATE_ID)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty(UrlConstants.KEY_STATE_NAME)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mStateList.add(state);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_STATE_LIST);
    }

    public void getSchemes() {

        HashMap<String, String> properties = new HashMap<String, String>();

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mSchemeList = new ArrayList<Scheme>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Scheme scheme = new Scheme(record
                                    .getProperty(UrlConstants.KEY_SCHEME_NAME)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty(UrlConstants.KEY_SCHEME_VALUE)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mSchemeList.add(scheme);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_SCHEME);
    }

    public void getCityList(String stateId) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("Stateid", stateId);

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mCityList = new ArrayList<City>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            City city = new City(record
                                    .getProperty(UrlConstants.KEY_DIST_ID)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty(UrlConstants.KEY_DIST_NAME)
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mCityList.add(city);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_CITY_LIST);
    }

    public void getProducts() {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("userid", AppSharedPrefrences.getInstance(mContext).getUserId());

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mProducts = new ArrayList<Product>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Product product = new Product(record
                                    .getProperty("Id")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));

                            mProducts.add(product);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_PRODUCTS);
    }

    public void getProductLevelOne(String productId) {

        HashMap<String, String> properties = new HashMap<String, String>();

        properties.put("userid", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("ProductId", productId);
        selectedProductId = productId;

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mProducts = new ArrayList<Product>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Product product = new Product(record
                                    .getProperty("Id")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));

                            mProducts.add(product);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_PRODUCT_LEVEL_ONE);
    }

    public void getProductLevelTwo(String productId, String productLevelOneId) {

        HashMap<String, String> properties = new HashMap<String, String>();

        properties.put("userid", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("productid", productId);
        properties.put("ProductLevelOne", productLevelOneId);
        selectedProductId = productLevelOneId;

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mProducts = new ArrayList<Product>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Product product = new Product(record
                                    .getProperty("Id")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));

                            mProducts.add(product);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_PRODUCT_LEVEL_TWO);
    }

    public void getProductLevelThree(String productId, String productLevelOneId, String productLevelTwoId) {

        HashMap<String, String> properties = new HashMap<String, String>();

        properties.put("userid", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("productid", productId);
        properties.put("ProductLevelOne", productLevelOneId);
        properties.put("productlevelTwo", productLevelTwoId);

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mProducts = new ArrayList<Product>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Product product = new Product(record
                                    .getProperty("Id")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));

                            mProducts.add(product);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_PRODUCT_LEVEL_THREE);
    }

  /*  public void getProductLevelFour(String productId, String productLevelOneId, String productLevelTwoId, String productLevelThreeId) {

        HashMap<String, String> properties = new HashMap<String, String>();

        properties.put("userid", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("productid", productId);
        properties.put("ProductLevelOne", productLevelOneId);
        properties.put("productlevelTwo", productLevelTwoId);
        properties.put("productlevelThree", productLevelThreeId);

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mProducts = new ArrayList<Product>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Product product = new Product(record
                                    .getProperty("Id")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));

                            mProducts.add(product);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_PRODUCT_LEVEL_FOUR);
    }  */

    /**************Changed by Anusha to remove product level 4 and display details even when image not available**********/
    public void getProductDetail(String productId) {

        HashMap<String, String> properties = new HashMap<String, String>();

        properties.put("userid", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("ProductLevelThreeId", productId);

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        SoapObject record = (SoapObject) response
                                .getProperty(0);
                        if(record.hasProperty("Error"))
                        {
                            Global.showToast(""+record.getProperty("Error").toString());
                            return;
                        }
                        SoapObject image = (SoapObject) record
                                .getProperty("Image");
                        SoapObject ProductImges;
                        String imageName = null;
                        if(image.hasProperty("ProductImges"))
                        {
                            ProductImges = (SoapObject) image
                                    .getProperty("ProductImges");
                            if(ProductImges.hasProperty("Images"))
                            {
                                imageName = ProductImges
                                        .getProperty("Images")
                                        .toString()
                                        .replace(UrlConstants.KEY_ANY,
                                                UrlConstants.KEY_BLANK);
                            }
                        }
                       // SoapObject image = (SoapObject) record
                        //        .getProperty("Image");
                      //  SoapObject ProductImges = (SoapObject) image
                       //         .getProperty("ProductImges");

                        mProductDetail = new ProductDetail(record
                                .getProperty("id")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("LevelOneName")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("LevelTwoName")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                             //   .getProperty("LevelThreeName")
                             //   .toString()
                             //   .replace(UrlConstants.KEY_ANY,
                              //          UrlConstants.KEY_BLANK), record
                                .getProperty("ProductLevelThreeName")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("ProductCode")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("TechnicleSpecification")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("MRP")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK),record
                                .getProperty("Warranty")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK),record
                                .getProperty("KeyFeature")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK),record
                                .getProperty("brochure")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK)
                                , imageName==null?"":imageName);
                               // , ProductImges
                              //  .getProperty("Images")
                              //  .toString()
                              //  .replace(UrlConstants.KEY_ANY,
                              //          UrlConstants.KEY_BLANK));

                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_PRODUCT_DETAIL);
    }
    /***************End of change***********************/

    public void getPromotions() {

        HashMap<String, String> properties = new HashMap<String, String>();

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mProducts = new ArrayList<Product>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Product product = new Product(record
                                    .getProperty("Id")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));

                            mProducts.add(product);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_PROMOTION_TYPE);
    }

    public void getPromotionDetail(String promotionType, String productId, String levelOneId) {

        HashMap<String, String> properties = new HashMap<String, String>();

        properties.put("userid", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("PermotionTypeID", promotionType);
        properties.put("ProductCatergoryId", productId);
        properties.put("ProductLevelOneId", levelOneId);
        //SharedPreferences prefs = mContext.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
       // SharedPreferences.Editor edit = prefs.edit();
        //edit.put("properties",properties);
     /*
        SharedPreferences keyValues = getContext().getSharedPreferences("Your_Shared_Prefs"), Context.MODE_PRIVATE);
        SharedPreferences.Editor keyValuesEditor = keyValues.edit();

        for (String s : properties.keySet()) {
            keyValuesEditor.putString(s, properties.get(s));
        }

        keyValuesEditor.commit();  */
        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mPromotions = new ArrayList<Promotion>();
                                        SoapObject resultObj = (SoapObject) response
                                                .getProperty(0);
                                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                                            SoapObject record = (SoapObject) resultObj
                                                    .getProperty(i);
                                            mPromotionDetail = new Promotion(record
                                                    .getProperty("id")
                                                    .toString()
                                                    .replace(UrlConstants.KEY_ANY,
                                                            UrlConstants.KEY_BLANK), record
                                                    .getProperty("PromotionType")
                                                    .toString()
                                                    .replace(UrlConstants.KEY_ANY,
                                                            UrlConstants.KEY_BLANK), record
                                                    .getProperty("ProductCategory")
                                                    .toString()
                                                    .replace(UrlConstants.KEY_ANY,
                                                            UrlConstants.KEY_BLANK), record
                                                    .getProperty("ProductlevelOne")
                                                    .toString()
                                                    .replace(UrlConstants.KEY_ANY,
                                                            UrlConstants.KEY_BLANK), record
                                                    .getProperty("Descriptons")
                                                    .toString()
                                                    .replace(UrlConstants.KEY_ANY,
                                                            UrlConstants.KEY_BLANK), record
                                                    .getProperty("ImagePath")
                                                    .toString()
                                                    .replace(UrlConstants.KEY_ANY,
                                                            UrlConstants.KEY_BLANK));

                                            mPromotions.add(mPromotionDetail);
                                        }

                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_PROMOTION_DETAIL);
    }

    public void getProfile() {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("Email", AppSharedPrefrences.getInstance(mContext).getUserId());
        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        SoapObject record = (SoapObject) response
                                .getProperty("GetProfileResult");
                        mProfileDetail = new Profile(record
                                .getProperty("id")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("id")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("Email")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("ContactNo")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("UserImage")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("Address1")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("CustomerType")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("Dis_Sap_Code")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK)
                                , record
                                .getProperty("Name")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK));
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_PROFILE);
    }

    public void updateProfile(String mobile, String email, String address1, String image, String imagename) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("userID", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("mobileNo", mobile);
        properties.put("email", email);
        properties.put("Address1", address1);
        properties.put("Image", image);
        properties.put("ImageName", imagename);
        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
//                        SoapObject record = (SoapObject) response
//                                .getProperty("GetProfileResult");
//                        mProfileDetail = new Profile(record
//                                .getProperty("id")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("id")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("Email")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("ContactNo")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("UserImage")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("CustomerType")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("Dis_Sap_Code")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK)
//                                , record
//                                .getProperty("Name")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK));
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_UPDATE_PROFILE);
    }

    public void getContactUs() {

        HashMap<String, String> properties = new HashMap<String, String>();
        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        mContactUs = new ArrayList<ContactUs>();
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            ContactUs contactUs = new ContactUs(record
                                    .getProperty("ContactUsType")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("Address")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("email")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("Fax")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("PhoneNumber")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mContactUs.add(contactUs);

                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_CONTACT_US);
    }

    public void getReports(String schemeName, String distributerId, String dealerId) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("dist_code", distributerId);
        properties.put("dlr_code", dealerId);
        properties.put("Scheme", schemeName);

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {

                        mReports = new ArrayList<Report>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Report report = new Report(record
                                    .getProperty("Scheme_Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("dealer_code")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("Dealer_Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("BattRec")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("BattAcp")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("HkvaRec")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("HkvaAcp")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("InvRec")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("InvAcp")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("TotalRec")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("TotalAcp")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("TotalRej")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK), record
                                    .getProperty("DlrPay")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),
                                    record
                                            .getProperty("DistPay")
                                            .toString()
                                            .replace(UrlConstants.KEY_ANY,
                                                    UrlConstants.KEY_BLANK), record
                                    .getProperty("Total_Payout")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));

                            mReports.add(report);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_REPORTS);
    }

    public void getNotifications(String startDate,String endDate) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("userid", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("StartDate", startDate);
        properties.put("EndDate", endDate);

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {

                        mNotifications = new ArrayList<Notification>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Notification notification = new Notification(Integer.parseInt(record
                                    .getProperty("id")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK)),record
                                    .getProperty("Subject")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Text")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),Boolean.parseBoolean(record
                                    .getProperty("isread")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK)));

                            mNotifications.add(notification);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_NOTIFICATIONS);
    }

    public void getFunctionNames() {

        HashMap<String, String> properties = new HashMap<String, String>();

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {

                        mFunctionNames = new ArrayList<Function>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Function function = new Function(Integer.parseInt(record
                                    .getProperty("Id")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK)),record
                                    .getProperty("Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));

                            mFunctionNames.add(function);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_FUNCTIONS);
    }

    public void getSearchedProduct(final String productName) {

        HashMap<String, String> properties = new HashMap<String, String>();

        properties.put("userId", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("ProductLevelName", productName);

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        SoapObject record = (SoapObject) response
                                .getProperty(0);
                        if(record.hasProperty("Error"))
                        {
                            Global.showToast(""+record.getProperty("Error").toString());
                            return;
                        }
                        SoapObject image = (SoapObject) record
                                .getProperty("Image");
                        SoapObject ProductImges;
                        String imageName = null;
                        if(image.hasProperty("ProductImges"))
                        {
                            ProductImges = (SoapObject) image
                                    .getProperty("ProductImges");
                            if(ProductImges.hasProperty("Images"))
                            {
                                imageName = ProductImges
                                        .getProperty("Images")
                                        .toString()
                                        .replace(UrlConstants.KEY_ANY,
                                                UrlConstants.KEY_BLANK);
                            }
                        }

                        mProductDetail = new ProductDetail(record
                                .getProperty("id")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("LevelOneName")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("LevelTwoName")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                        /*********Change by Anusha for removing product level 4*********/
                          //      .getProperty("LevelThreeName")
                           //     .toString()
                          //      .replace(UrlConstants.KEY_ANY,
                           //             UrlConstants.KEY_BLANK), record
                                .getProperty("ProductLevelThreeName")        //End of change
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("ProductCode")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("TechnicleSpecification")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK), record
                                .getProperty("MRP")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK),record
                                .getProperty("Warranty")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK),record
                                .getProperty("KeyFeature")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK),record
                                .getProperty("brochure")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK)
                                , imageName==null?"":imageName);
                         //       , fullFileName==null?"":fullFileName);

                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_SEARCH);
    }

    public void sendSuggestion(int id, String suggestion, String image, String imagename) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("userId", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("functionId", id+"");
        properties.put("suggestion", suggestion);
        properties.put("Image", image);
        properties.put("filename", imagename);
        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
//                        SoapObject record = (SoapObject) response
//                                .getProperty("GetProfileResult");
//                        mProfileDetail = new Profile(record
//                                .getProperty("id")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("id")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("Email")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("ContactNo")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("UserImage")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("CustomerType")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK), record
//                                .getProperty("Dis_Sap_Code")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK)
//                                , record
//                                .getProperty("Name")
//                                .toString()
//                                .replace(UrlConstants.KEY_ANY,
//                                        UrlConstants.KEY_BLANK));
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_SUGGESTION);
    }

    public void saveEntry(String SerialNo, String DisCode, String DlrCode, String SaleDate, String CustomerName,
                          String CustomerPhone, String CustomerLandLineNumber, String CustomerState, String CustomerCity, String CustomerAddress, String LogDistyId) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("SerialNo", SerialNo);
        properties.put("DisCode", DisCode);
        properties.put("DlrCode", DlrCode);
        properties.put("SaleDate", SaleDate);
        properties.put("CustomerName", CustomerName);
        properties.put("CustomerPhone", CustomerPhone);
        properties.put("CustomerLandLineNumber", CustomerLandLineNumber);
        properties.put("CustomerState", CustomerState);
        properties.put("CustomerCity", CustomerCity);
        properties.put("CustomerAddress", CustomerAddress);
        properties.put("LogDistyId", LogDistyId);

        new CommonAsyncTask(mContext, properties, true) {


            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        mMessage = response.getPropertyAsString(0);
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_SAVE_ENTRY);
    }

    /***************Added by Anusha to get Device Reg Id for GCM Push Notification*******************/
    public void updateDeviceId(String user, String deviceId) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("userid", user);
        properties.put("DeviceId", deviceId);
        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_UPDATE_DEVICE_ID);
    }
    /*******************End of change***********************/
}
