package com.foodforme.vendore.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.foodforme.vendore.R;
import com.foodforme.vendore.SetterGatter.SetGet;
import com.foodforme.vendore.adapter.InProgressAdapter;
import com.foodforme.vendore.adapter.NewOrderAdapter;
import com.foodforme.vendore.customwidget.CustomTextView;
import com.foodforme.vendore.serverintegration.Constant;
import com.foodforme.vendore.utils.ApplicationController;
import com.foodforme.vendore.utils.TransparentProgressDialog;
import com.foodforme.vendore.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HomeFragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = HomeFragment2.class.getSimpleName();
    RelativeLayout fragement_home_layout;
    CustomTextView new_order_btn, Inprogress_btn;
    RecyclerView order_list, inprogress_list;
    NestedScrollView nestedScrollView, InProgress_nestedScrollView;
    int lastPage = 0;
    public String mMaxoffset = "", TotalPage, PerPage, CurrentPage, requestBody, mOutputDateString;
    JSONObject jObject, jsonObject;
    NewOrderAdapter newOrderAdapter;
    InProgressAdapter inProgressAdapter;
    ArrayList<SetGet> newOrderlist;
    ArrayList<SetGet> inprogress_Orderlist;
    TransparentProgressDialog progressDialog;
    Date mParsedDate;
    Context mContext;
    SharedPreferences preferences;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newOrderlist = new ArrayList<SetGet>();
        inprogress_Orderlist = new ArrayList<SetGet>();
        mContext = this.getActivity();
        progressDialog = TransparentProgressDialog.getInstance();
        preferences = mContext.getSharedPreferences("Vendor", Context.MODE_PRIVATE);
        jsonObject = new JSONObject();

        new_order_btn = view.findViewById(R.id.new_order_btn);
        Inprogress_btn = view.findViewById(R.id.inprogress_btn);
        order_list = view.findViewById(R.id.new_order_list);
        inprogress_list = view.findViewById(R.id.inprogress_list);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        InProgress_nestedScrollView = view.findViewById(R.id.InProgress_nestedScrollView);
        fragement_home_layout = view.findViewById(R.id.fragement_home);
       /* no_Order_found_txt = view.findViewById(R.id.no_Order_found_txt);
        no_Order_found_txt2 = view.findViewById(R.id.no_Order_found_txt2);*/

        swipeRefresh = view.findViewById(R.id.swipe_container);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefresh.post(new Runnable() {

            @Override
            public void run() {
                newOrderlist.clear();
                inprogress_Orderlist.clear();
                setNewOrderAdapter();
                setInProgressAdapter();

                NewOrderlist();
                InProgressOrderlist();
            }
        });

        new_order_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                newOrderlist.clear();
                Inprogress_btn.setBackgroundResource(R.drawable.corners4);
                Inprogress_btn.setTextColor(getResources().getColor(R.color.black));
                new_order_btn.setBackgroundResource(R.drawable.corners);
                new_order_btn.setTextColor(getResources().getColor(R.color.white));
                nestedScrollView.setVisibility(View.VISIBLE);
                InProgress_nestedScrollView.setVisibility(View.GONE);
                //    newOrderlist.clear();
                lastPage = 0;
                NewOrderlist();

            }
        });


        Inprogress_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inprogress_Orderlist.clear();
                new_order_btn.setBackgroundResource(R.drawable.corners2);
                new_order_btn.setTextColor(getResources().getColor(R.color.black));
                Inprogress_btn.setBackgroundResource(R.drawable.corners3);
                Inprogress_btn.setTextColor(getResources().getColor(R.color.white));
                InProgress_nestedScrollView.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.GONE);
                //   inprogress_Orderlist.clear();
                lastPage = 0;
                InProgressOrderlist();

            }
        });

        if (nestedScrollView.getVisibility() == View.VISIBLE) {
            if (nestedScrollView != null) {
                nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (v.getChildAt(v.getChildCount() - 1) != null) {
                            if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                                lastPage = lastPage + 1;
                                if (Integer.parseInt(mMaxoffset) >= lastPage) {
                                    NewOrderlist();
                                } else {
                                    Utility.ShowSnakebarMessage(fragement_home_layout, "Sorry, no more data available!");
                                    // Toast.makeText(mContext, "Sorry, no more data available!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        }

        if (InProgress_nestedScrollView != null) {
            InProgress_nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (v.getChildAt(v.getChildCount() - 1) != null) {
                        if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                            lastPage = lastPage + 1;
                            if (Integer.parseInt(mMaxoffset) >= lastPage) {
                                InProgressOrderlist();
                            } else {
                                Utility.ShowSnakebarMessage(fragement_home_layout, "Sorry, no more data available!");
                                //Toast.makeText(mContext, "Sorry, no more data available!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }

        return view;
    }

    public void setNewOrderAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        order_list.setLayoutManager(layoutManager);
        newOrderAdapter = new NewOrderAdapter(mContext, newOrderlist);
        order_list.setAdapter(newOrderAdapter);
        newOrderAdapter.notifyDataSetChanged();

    }

    public void setInProgressAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        inprogress_list.setLayoutManager(layoutManager);
        inProgressAdapter = new InProgressAdapter(mContext, inprogress_Orderlist);
        inprogress_list.setAdapter(inProgressAdapter);
        inProgressAdapter.notifyDataSetChanged();
        progressDialog.dismiss();

    }

    @Override
    public void onResume() {
        super.onResume();
        newOrderlist.clear();
        inprogress_Orderlist.clear();
        setNewOrderAdapter();
        setInProgressAdapter();
        NewOrderlist();
        InProgressOrderlist();
      /*  if (Utility.getSharedPreferences(mContext, Constant.Order) != null) {
            if (Utility.getSharedPreferences(mContext, Constant.Order).equals(getResources().getString(R.string.confirm_order))) {
                // inprogress_Orderlist.clear();
                InProgressOrderlist();
            }
            if (Utility.getSharedPreferences(mContext, Constant.Order).equals(getResources().getString(R.string.neworder))) {
                //  newOrderlist.clear();

            }

        }*/
    }


    private void NewOrderlist() {
        newOrderlist.clear();
        try {
            jObject = new JSONObject();
            jObject.put("userid", Utility.getSharedPreferences(mContext, Constant.vendor_id));
            jObject.put("page_no", lastPage);
            jObject.put("lang_id", preferences.getString("lang", ""));
            Log.e(TAG, "167 : Url: " + Constant.NewOrderAPI);
            final String requestBody = jObject.toString();
            Log.e("requestBody", requestBody);
            progressDialog.show(mContext);
            StringRequest sr = new StringRequest(Request.Method.POST, Constant.NewOrderAPI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.show(mContext);
                            Log.e(TAG, "162 : onResponse: " + response);
                            try {
                                jObject = new JSONObject(response);
                                String message = jObject.optString("message");
                                String ok = jObject.optString("ok");

                                if (ok.equalsIgnoreCase("1")) {

                                    JSONObject jsonObject = jObject.getJSONObject("order_list");

                                    TotalPage = jsonObject.getString("total");

                                    PerPage = jsonObject.getString("per_page");
                                    CurrentPage = jsonObject.getString("current_page");

                                    mMaxoffset = jsonObject.getString("last_page");
                                    Log.e("Maxoffset", "" + mMaxoffset);

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                        SetGet orderListing = new SetGet();
                                        orderListing.setRest_name(jsonObject1.optString("rest_name"));
                                        orderListing.setOrder_id(jsonObject1.optString("order_id"));
                                        orderListing.setUnique_order_id(jsonObject1.optString("order_uniqueid"));
                                        orderListing.setOrder_total(jsonObject1.optString("order_total"));
                                        orderListing.setPayment_type(jsonObject1.optString("payment_type"));
                                        orderListing.setUser_id(jsonObject1.optString("user_id"));
                                        orderListing.setRest_id(jsonObject1.optString("rest_id"));
                                        orderListing.setOrder_fname(jsonObject1.optString("order_fname"));
                                        orderListing.setOrder_lname(jsonObject1.optString("order_lname"));
                                        orderListing.setOrder_email(jsonObject1.optString("order_email"));
                                        orderListing.setOrder_tel(jsonObject1.optString("order_tel"));
                                        orderListing.setOrder_address(jsonObject1.optString("order_address"));

                                        orderListing.setOrder_city(jsonObject1.optString("order_city"));
                                        orderListing.setOrder_pcode(jsonObject1.optString("order_pcode"));
                                        orderListing.setOrder_deliveryadd1(jsonObject1.optString("order_deliveryadd1"));
                                        orderListing.setOrder_deliverynumber(jsonObject1.optString("order_deliverynumber"));

                                        String Order_create_date = jsonObject1.optString("order_create");
                                        SimpleDateFormat mInputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                                        SimpleDateFormat mOutputDateFormat = new SimpleDateFormat("MMM dd, yyyy, hh:mm a", java.util.Locale.getDefault());
                                        try {
                                            mParsedDate = mInputDateFormat.parse(Order_create_date);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        mOutputDateString = mOutputDateFormat.format(mParsedDate);
                                        Log.e("new date is", mOutputDateString);

                                        orderListing.setOrder_date_time("Delivery Time " + mOutputDateString);

                                        newOrderlist.add(orderListing);

                                    }

                                    order_list.setHasFixedSize(true);
                                    // use a linear layout manager

                                    setNewOrderAdapter();
                                    swipeRefresh.setRefreshing(false);
                                } else {

                                    Utility.ShowSnakebarMessage(fragement_home_layout, message);
                                    swipeRefresh.setRefreshing(false);
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
                    return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
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


    private void InProgressOrderlist() {
        inprogress_Orderlist.clear();
        try {
            jObject = new JSONObject();
            jObject.put("userid", Utility.getSharedPreferences(mContext, Constant.vendor_id));
            jObject.put("page_no", lastPage);
            jObject.put("lang_id", preferences.getString("lang", ""));
            Log.e(TAG, "165 : Url: " + Constant.InProgressOrderAPI);
            final String requestBody = jObject.toString();
            Log.e("requestBody", requestBody);
            progressDialog.show(mContext);
            StringRequest sr = new StringRequest(Request.Method.POST, Constant.InProgressOrderAPI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.show(mContext);
                            Log.e(TAG, "162 : onResponse: " + response);
                            try {
                                jObject = new JSONObject(response);
                                String message = jObject.optString("message");
                                String ok = jObject.optString("ok");

                                if (ok.equalsIgnoreCase("1")) {
                                    inprogress_Orderlist = new ArrayList<SetGet>();
                                    JSONObject jsonObject = jObject.getJSONObject("order_list");

                                    TotalPage = jsonObject.getString("total");

                                    PerPage = jsonObject.getString("per_page");
                                    CurrentPage = jsonObject.getString("current_page");

                                    mMaxoffset = jsonObject.getString("last_page");
                                    Log.e("Maxoffset", "" + mMaxoffset);
                                    /*clear array list before new data add*/
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                        SetGet orderListing = new SetGet();
                                        orderListing.setRest_name(jsonObject1.optString("rest_name"));
                                        orderListing.setOrder_id(jsonObject1.optString("order_id"));
                                        orderListing.setUnique_order_id(jsonObject1.optString("order_uniqueid"));
                                        orderListing.setOrder_total(jsonObject1.optString("order_total"));
                                        orderListing.setPayment_type(jsonObject1.optString("payment_type"));
                                        orderListing.setUser_id(jsonObject1.optString("user_id"));
                                        orderListing.setRest_id(jsonObject1.optString("rest_id"));
                                        orderListing.setOrder_fname(jsonObject1.optString("order_fname"));
                                        orderListing.setOrder_lname(jsonObject1.optString("order_lname"));
                                        orderListing.setOrder_email(jsonObject1.optString("order_email"));
                                        orderListing.setOrder_tel(jsonObject1.optString("order_tel"));
                                        orderListing.setOrder_address(jsonObject1.optString("order_address"));
                                        orderListing.setOrder_status(jsonObject1.optString("order_status"));
                                        orderListing.setOrder_date_time(jsonObject1.optString("order_create"));
                                        orderListing.setOrder_city(jsonObject1.optString("order_city"));
                                        orderListing.setOrder_pcode(jsonObject1.optString("order_pcode"));
                                        orderListing.setOrder_deliveryadd1(jsonObject1.optString("order_deliveryadd1"));
                                        orderListing.setOrder_deliverynumber(jsonObject1.optString("order_deliverynumber"));

                                        String Order_create_date = jsonObject1.optString("order_create");
                                        SimpleDateFormat mInputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                                        SimpleDateFormat mOutputDateFormat = new SimpleDateFormat("MMM dd, yyyy, hh:mm a", java.util.Locale.getDefault());
                                        try {
                                            mParsedDate = mInputDateFormat.parse(Order_create_date);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        mOutputDateString = mOutputDateFormat.format(mParsedDate);
                                        Log.e("new date is", mOutputDateString);

                                        orderListing.setOrder_date_time("Delivery Time " + mOutputDateString);
                                        inprogress_Orderlist.add(orderListing);

                                    }

                                    inprogress_list.setHasFixedSize(true);
                                    // use a linear layout manager
                                    setInProgressAdapter();
                                    swipeRefresh.setRefreshing(false);
                                } else {

                                    progressDialog.dismiss();
                                    Utility.ShowSnakebarMessage(fragement_home_layout, message);
                                    swipeRefresh.setRefreshing(false);
                                    //  Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
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

                        }
                    }) {

                @Override
                public String getBodyContentType() {
                    return String.format("application/json; charset=utf-8");
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
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

    @Override
    public void onRefresh() {
        newOrderlist.clear();
        inprogress_Orderlist.clear();
        setNewOrderAdapter();
        setInProgressAdapter();
        NewOrderlist();
        InProgressOrderlist();
        swipeRefresh.setRefreshing(true);
    }


}

