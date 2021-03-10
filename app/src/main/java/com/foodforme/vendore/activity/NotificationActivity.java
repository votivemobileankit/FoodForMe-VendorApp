package com.foodforme.vendore.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.foodforme.vendore.R;
import com.foodforme.vendore.SetterGatter.SetGet;
import com.foodforme.vendore.adapter.NotificationAdapter;
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
import java.util.ArrayList;

public class NotificationActivity extends Fragment {

    private static final String TAG = NotificationActivity.class.getSimpleName();
    Toolbar toolbar;
    Context mContext;
    RelativeLayout back_relative, notification_relative;
    CustomTextView toolbar_title;
    RecyclerView notificationlist;
    NotificationAdapter notificationAdapter;
    ArrayList<SetGet.NotificationList> NotificationList = new ArrayList<SetGet.NotificationList>();
    NestedScrollView notification_nestedScrollView;
    int lastPage = 0;
    public String mMaxoffset = "", TotalPage, PerPage, CurrentPage, requestBody;
    JSONObject jObject, jsonObject;
    ImageView back_img;
    SharedPreferences preferences;
    TransparentProgressDialog progressDialog;


    protected void attachBaseContext(Context base) {
        attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notification, container, false);
        progressDialog = TransparentProgressDialog.getInstance();
        mContext = this.getActivity();
        preferences = mContext.getSharedPreferences("Vendor", Context.MODE_PRIVATE);

        //    toolbar_title  = view.findViewById(R.id.toolbar_title);
        notificationlist = view.findViewById(R.id.notificationlist);
        //   back_relative = view.findViewById(R.id.back_relative);
        //   back_img = view.findViewById(R.id.back_img);
        notification_relative = view.findViewById(R.id.notification_relative);
        notification_nestedScrollView = view.findViewById(R.id.notification_nestedScrollView);
        //  toolbar_title.setText(getResources().getString(R.string.notification));


       /* if(Utility.getSharedPreferences(mContext, Constant.Language)!=null)
        {
            if (Utility.getSharedPreferences(mContext, Constant.Language).equals("ar"))
            {
                back_img.setScaleX(-1);
            }
        }
*/

        Notificationlist();
    /*    back_relative.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });*/


        if (notification_nestedScrollView != null) {
            notification_nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (v.getChildAt(v.getChildCount() - 1) != null) {
                        if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                            lastPage = lastPage + 1;
                            if (Integer.parseInt(mMaxoffset) >= lastPage) {
                                Notificationlist();
                            } else {
                                Utility.ShowSnakebarMessage(notification_relative, getResources().getString(R.string.no_more_data));
                                // Toast.makeText(mContext, "Sorry, no more data available!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }
        return view;
    }


    private void Notificationlist() {
        try {
            jObject = new JSONObject();
            jObject.put("userid", Utility.getSharedPreferences(mContext, Constant.vendor_id));
            jObject.put("page_no", lastPage);
            jObject.put("lang_id", preferences.getString("lang", ""));
            final String requestBody = jObject.toString();
            Log.e("requestBody", requestBody);
            progressDialog.show(mContext);
            StringRequest sr = new StringRequest(Request.Method.POST, Constant.OrderNotificationeAPI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.show(mContext);
                            Log.e(TAG, "162 : onResponse: " + response);
                            try {
                                jObject = new JSONObject(response);

                                String ok = jObject.optString("ok");

                                if (ok.equalsIgnoreCase("1")) {
                                    JSONObject jsonObject = jObject.getJSONObject("notification_detail");

                                    TotalPage = jsonObject.getString("total");

                                    PerPage = jsonObject.getString("per_page");
                                    CurrentPage = jsonObject.getString("current_page");

                                    mMaxoffset = jsonObject.getString("last_page");
                                    Log.e("Maxoffset", "" + mMaxoffset);

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                        SetGet.NotificationList notificationListing = new SetGet.NotificationList();
                                        notificationListing.setNoti_id(jsonObject1.optString("noti_id"));
                                        notificationListing.setNoti_title(jsonObject1.optString("noti_title"));
                                        notificationListing.setNoti_desc(jsonObject1.optString("noti_desc"));
                                        notificationListing.setCreated_at(jsonObject1.optString("ago_time"));
                                        NotificationList.add(notificationListing);

                                    }
                                    notificationlist.setHasFixedSize(true);
                                    // use a linear layout manager
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                                    notificationlist.setLayoutManager(layoutManager);
                                    notificationAdapter = new NotificationAdapter(mContext, NotificationList);
                                    notificationlist.setAdapter(notificationAdapter);
                                    notificationAdapter.notifyDataSetChanged();
                                } else {
                                    //Utility.ShowSnakebarMessage(Main_Relative, message);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "200 : onErrorResponse: " + error.getMessage());

                            progressDialog.dismiss();

                        }
                    }) {
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
            sr.setRetryPolicy(new DefaultRetryPolicy(Constant.TIME_OUT_MIN,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }


   /* public void showProgressDialog(Context context, String message) {
        if (progressDialog == null)
            progressDialog = new TransparentProgressDialog(context, context.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgressDialog() {

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
*/
   /* @Override
    public void onBackPressed() {
            finish();
            super.onBackPressed();


    }
*/
}
