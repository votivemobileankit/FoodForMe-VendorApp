package com.foodforme.vendore.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.foodforme.vendore.R;
import com.foodforme.vendore.customwidget.CustomTextView;
import com.foodforme.vendore.languagechange.LocaleHelper;
import com.foodforme.vendore.serverintegration.Constant;
import com.foodforme.vendore.utils.ApplicationController;
import com.foodforme.vendore.utils.TransparentProgressDialog;
import com.foodforme.vendore.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.foodforme.vendore.serverintegration.Constant.TIME_OUT_MIN;

public class SettingActivity extends Fragment {

    private static final String TAG = SettingActivity.class.getSimpleName();
    Context mContext;
    RelativeLayout back_relative, order_history_relative;
    CustomTextView toolbar_title;
    CustomTextView English_btn, Arabic_btn, german_btn;
    ImageView back_img;
    JSONObject jObject;
    TransparentProgressDialog progressDialog;
    ImageButton select, unselect;
    SharedPreferences pref;
    SharedPreferences pref1;
    String flag = " ";


    protected void attachBaseContext(Context base) {
        attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);

        progressDialog = TransparentProgressDialog.getInstance();
        mContext = this.getActivity();
        jObject = new JSONObject();
        pref = mContext.getSharedPreferences("vendor", MODE_PRIVATE);
        pref1 = mContext.getSharedPreferences("vendor_app", MODE_PRIVATE);

        //  toolbar_title = view.findViewById(R.id.toolbar_title);
        back_relative = view.findViewById(R.id.back_relative);
        order_history_relative = view.findViewById(R.id.order_history_relative);
        //   notification_switch = findViewById(R.id.notification_switch);
        English_btn = view.findViewById(R.id.english_btn);
        Arabic_btn = view.findViewById(R.id.arabic_btn);
        back_img = view.findViewById(R.id.back_img);
        select = view.findViewById(R.id.select);
        unselect = view.findViewById(R.id.unselect);
        german_btn = view.findViewById(R.id.german_btn);

//        toolbar_title.setText(getResources().getString(R.string.setting));

        if (pref.getString("notification_status", "").equalsIgnoreCase("1")) {
            select.setVisibility(View.GONE);
            unselect.setVisibility(View.VISIBLE);

        } else if (pref.getString("notification_status", "").equalsIgnoreCase("0")) {
            unselect.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        }


        if (pref1.getString("lang_value", "").equalsIgnoreCase("1")) {
            Arabic_btn.setBackgroundResource(R.drawable.corners14);
            Arabic_btn.setTextColor(getResources().getColor(R.color.black));

            german_btn.setBackgroundResource(R.drawable.corners14);
            german_btn.setTextColor(getResources().getColor(R.color.black));
            English_btn.setBackgroundResource(R.drawable.corners11);
            English_btn.setTextColor(getResources().getColor(R.color.white));

        } else if (pref1.getString("lang_value", "").equalsIgnoreCase("0")) {
            English_btn.setBackgroundResource(R.drawable.corners14);
            English_btn.setTextColor(getResources().getColor(R.color.black));
            Arabic_btn.setBackgroundResource(R.drawable.corners11);
            Arabic_btn.setTextColor(getResources().getColor(R.color.white));

            german_btn.setBackgroundResource(R.drawable.corners11);
            german_btn.setTextColor(getResources().getColor(R.color.white));
        }
        german_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViews("de");
                SharedPreferences.Editor editor = pref1.edit();
                editor.putString("lang", "de");
                editor.putString("lang_value", "0");
                editor.apply();
                editor.commit();
                Arabic_btn.setBackgroundResource(R.drawable.corners12);
                Arabic_btn.setTextColor(getResources().getColor(R.color.black));
                English_btn.setBackgroundResource(R.drawable.corners13);
                English_btn.setTextColor(getResources().getColor(R.color.white));
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        English_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViews("en");
                SharedPreferences.Editor editor = pref1.edit();
                editor.putString("lang", "en");
                editor.putString("lang_value", "1");
                editor.apply();
                editor.commit();
                Arabic_btn.setBackgroundResource(R.drawable.corners12);
                Arabic_btn.setTextColor(getResources().getColor(R.color.black));
                English_btn.setBackgroundResource(R.drawable.corners13);
                English_btn.setTextColor(getResources().getColor(R.color.white));
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();

            }
        });

        Arabic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViews("ar");
                SharedPreferences.Editor editor = pref1.edit();
                editor.putString("lang", "ar");
                editor.putString("lang_value", "0");
                editor.apply();
                editor.commit();
                Arabic_btn.setBackgroundResource(R.drawable.corners14);
                Arabic_btn.setTextColor(getResources().getColor(R.color.black));
                English_btn.setBackgroundResource(R.drawable.corners11);
                English_btn.setTextColor(getResources().getColor(R.color.white));
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();

            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = "1";
                notificationStatus(flag);

            }
        });
        unselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = "0";
                notificationStatus(flag);
            }
        });


        return view;
    }


    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(getActivity(), languageCode);
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        android.content.res.Configuration conf = resources.getConfiguration();
        conf.setLocale(new Locale(languageCode.toLowerCase())); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
        resources.updateConfiguration(conf, dm);
    }


    private void notificationStatus(String flag) {
        progressDialog.show(mContext);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userid", Utility.getSharedPreferences(mContext, Constant.vendor_id));
            //  jsonObject.put("userid", "431");
            jsonObject.put("guest_id", "0");
            jsonObject.put("status", flag);
            jsonObject.put("usertype", "2");
            jsonObject.put("lang_id", pref1.getString("lang", ""));


            //     jsonObject.put("deviceid", AppUtils.getDeviceId(context));
            //   jsonObject.put("devicetype", DEVICE_TYPE);

            final String requestBody = jsonObject.toString();
            Log.e(TAG, "154 : getnotificationstatus: " + Constant.NOTIFICATION_STATUS);
            Log.e(TAG, "156 : getnotificationstatus: " + jsonObject.toString());

            StringRequest sr = new StringRequest(Request.Method.POST, Constant.NOTIFICATION_STATUS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            Log.e(TAG, "162 : onResponse: " + response);
                            try {
                                jObject = new JSONObject(response);
                                String message = jObject.optString("message");
                                String ok = jObject.optString("ok");

                                if (ok.equalsIgnoreCase("1")) {
                                    JSONObject jsonObject = jObject.getJSONObject("data");
                                    JSONArray jsonArray = jsonObject.getJSONArray("user_detail");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                        String NotifyId = jsonObject1.optString("id");
                                        String NotifyUserID = jsonObject1.optString("userid");
                                        String token = jsonObject1.optString("devicetoken");
                                        String NotifyDeviceId = jsonObject1.optString("deviceid");
                                        String NotifyDeviceType = jsonObject1.optString("devicetype");
                                        String NotifyStatus = jsonObject1.optString("notification_status");
                                        String NotifyTime = jsonObject1.optString("created_at");
                                        String updated = jsonObject1.optString("updated_at");

                                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();


                                        //   notification.setNotifyStatus(NotifyStatus);

                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("notification_status", NotifyStatus);
                                        editor.apply();
                                        editor.commit();

                                        if (NotifyStatus.equalsIgnoreCase("1")) {
                                            select.setVisibility(View.GONE);
                                            unselect.setVisibility(View.VISIBLE);


                                        } else if (NotifyStatus.equalsIgnoreCase("0")) {
                                            unselect.setVisibility(View.GONE);
                                            select.setVisibility(View.VISIBLE);
                                        }

                                    }

                                } else {
                                    Toast.makeText(mContext, getResources().getString(R.string.Notification_status_not_change), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "200 : onErrorResponse: " + error.getMessage());
                            progressDialog.dismiss();
                            Toast.makeText(mContext, getResources().getString(R.string.Server_not_responding) + "\n " + getResources().getString(R.string.Please_try_again), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                /*      @Override
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

    /*@Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }*/
}
