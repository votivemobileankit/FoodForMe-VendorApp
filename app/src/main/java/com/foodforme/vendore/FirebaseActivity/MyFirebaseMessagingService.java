package com.foodforme.vendore.FirebaseActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.foodforme.vendore.serverintegration.Constant;
import com.foodforme.vendore.utils.ApplicationController;
import com.foodforme.vendore.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.foodforme.vendore.serverintegration.Constant.TIME_OUT_MIN;
import static com.foodforme.vendore.utils.ApplicationController.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }

    private void sendRegistrationToServer(String devicetoken) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userid", Utility.getSharedPreferences(getApplicationContext(), Constant.vendor_id));
            jsonObject.put("guestid", "0");
            jsonObject.put("devicetoken", devicetoken);
            jsonObject.put("deviceid", Utility.getSharedPreferences(getApplicationContext(), Constant.deviceid));
            jsonObject.put("devicetype", Constant.DeviceType);
            final String requestBody = jsonObject.toString();

            Log.e(TAG, "62 : sendDeviceTokenToServer: " + jsonObject.toString());

            StringRequest sr = new StringRequest(Request.Method.POST, Constant.FirebaseTokenSendAPI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "68 : onResponse: " + response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "74 : onErrorResponse: " + error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("device_token", "qwertyui");
//                    params.put("deviceid", AppUtils.getDeviceId(getApplicationContext()));
//                    params.put("devicetype", "android");
                    return params;
                }
     /* @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(AUTH_KEY, AUTH_VALUE);
                    headers.put(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
                    return headers;
                }*/

                @Override
                public String getBodyContentType() {
                    return String.format("application/json; charset=utf-8");
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
                }
            };
            ApplicationController.getInstance().addToRequestQueue(sr);
            sr.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_MIN,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived" + remoteMessage.getData().toString());
       // String title1 = remoteMessage.getData().get("title");
      //  String body1 = remoteMessage.getData().get("message");
       //  MyNotificationManager.getInstance(getApplicationContext()).displayNotification(title1, body1);
        if (remoteMessage.getData().size() > 0) {
            //handle the data message here
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("message");
           MyNotificationManager.getInstance(getApplicationContext()).displayNotification(title, body);
        }
        invitMethod(getApplicationContext());
    }



    private void invitMethod(Context mContext) {
        Intent notificationIntent = new Intent("newNotification");
        LocalBroadcastManager.getInstance(this).sendBroadcast(notificationIntent);
    }
}
