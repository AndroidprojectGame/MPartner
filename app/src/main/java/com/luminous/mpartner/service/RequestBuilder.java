package com.luminous.mpartner.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * this class is used to build the requested url which is to be hit on the
 * server
 */

public class RequestBuilder {

    private String methodName;
    private HashMap<String, String> properties;
    private HashMap<String, ArrayList<String>> arrayProperties;
    public HashMap<String, ArrayList<String>> getArrayProperties() {
        return arrayProperties;
    }

    public void setArrayProperties(
            HashMap<String, ArrayList<String>> arrayProperties) {
        this.arrayProperties = arrayProperties;
    }

    private Context context;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, String> parameters) {
        this.properties = parameters;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}