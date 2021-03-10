package com.foodforme.vendore.utils;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.foodforme.vendore.prefmanager.PrefConnector;
import com.foodforme.vendore.serverintegration.Constant;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import static com.android.volley.VolleyLog.TAG;
import static com.foodforme.vendore.serverintegration.Constant.TIME_OUT_MIN;


//import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by ADMIN on 18-Sep-17.
 */

public class GetInternalContentLink {

    public void getInternalContentLink(final Context mContext, final String language)
    {
        try {
            JSONObject jsonObject = new JSONObject();
       //     jsonObject.put("deviceid", AppUtils.getDeviceId(mContext));
         //   jsonObject.put("devicetype", DEVICE_TYPE);
            jsonObject.put("lang_id",language);

            final String requestBody = jsonObject.toString();
            Log.e(TAG, "46 : getInternalContentLink: " + Constant.APP_CONTENT_LINK);
            Log.e(TAG, "45 : getInternalContentLink: " + jsonObject.toString());
            StringRequest sr = new StringRequest(Request.Method.POST, Constant.APP_CONTENT_LINK,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e(TAG, "50 : onResponse: " + response);
                            try {
                                JSONObject jObject = new JSONObject(response);
                                String message = jObject.optString("message");
                                String ok = jObject.optString("ok");
                                if (ok.equalsIgnoreCase("1")) {
                                    JSONObject jObjectt = jObject.getJSONObject("page_data");
                                    String aboutus = jObjectt.optString("aboutus");
                                    String contactus = jObjectt.optString("contactus");
                                    String termsconditions = jObjectt.optString("termsconditions");
                                    String privacypolicy = jObjectt.optString("privacypolicy");

                                    PrefConnector.writeString(mContext, PrefConnector.APP_ABOUT_US, aboutus);
                                    PrefConnector.writeString(mContext, PrefConnector.APP_CONTACT_US, contactus);
                                    PrefConnector.writeString(mContext, PrefConnector.APP_TERM_CONDITION, termsconditions);
                                    PrefConnector.writeString(mContext, PrefConnector.APP_PRIVACYPOLICY, privacypolicy);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "75 : onErrorResponse: " + error.getMessage());
                            getInternalContentLink(mContext, language);
                        }
                    }) {
             /*   @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(AUTH_KEY, AUTH_VALUE);
                    headers.put(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
                    return headers;
                }
*/
                @Override
                public String getBodyContentType() {
                    return String.format("application/json; charset=utf-8");
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            // add the request object to the queue to be executed
            ApplicationController.getInstance().addToRequestQueue(sr);
            sr.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_MIN,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
