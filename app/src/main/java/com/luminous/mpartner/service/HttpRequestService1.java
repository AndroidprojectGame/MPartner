package com.luminous.mpartner.service;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.luminous.mpartner.constants.UrlConstants;

import org.apache.http.client.ClientProtocolException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class HttpRequestService1 {

    /* private variables */
    private SoapObject request, response;

    public SoapObject getRequestData(RequestBuilder requestBuilder,
                                     Context context) throws CustomException, ClientProtocolException,
            IOException, Exception {

        if (CheckNetworkStatus.getInstance(requestBuilder.getContext())
                .isOnline()) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);


            String SOAP_ACTION = UrlConstants.NAMESPACE + requestBuilder.getMethodName();
            // Create the soap request object
            request = new SoapObject(UrlConstants.NAMESPACE, requestBuilder.getMethodName());

            HashMap<String, String> properties = requestBuilder.getProperties();
            Iterator<String> iterator = properties.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                request.addProperty(key, properties.get(key));
            }


            Log.i("REQUEST>>>>>>>>>>", request.toString() + "");

            // Create the envelop.Envelop will be used to send the request
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            // Says that the soap webservice is a .Net service
            envelope.dotNet = true;

            HttpTransportSE androidHttpTransport = new HttpTransportSE(UrlConstants.URL1);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);

                response = (SoapObject) envelope.bodyIn;
                Log.i("RESPONSE>>>>>>>>>>", response.toString() + "");
                // Output to the log

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            CustomException cu = new CustomException();
            throw cu;
        }
        return response;

    }
}