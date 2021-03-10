package com.foodforme.vendore.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
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
import com.foodforme.vendore.adapter.OrderHistoryAdapter;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderHistoryActivity extends AppCompatActivity {

    private static final String TAG = OrderHistoryActivity.class.getSimpleName();
    Context mContext;
    RelativeLayout back_relative, order_history_relative;
    CustomTextView toolbar_title;
    ImageView back_img;

    RecyclerView order_historylist;
    NestedScrollView nestedScrollView;
    int lastPage = 0;
    public String mMaxoffset = "", TotalPage, PerPage, CurrentPage;
    ArrayList<SetGet.OrderHistory> OrderHistoryList = new ArrayList<SetGet.OrderHistory>();
    OrderHistoryAdapter OrderHistoryAdapter;
    JSONObject jObject;
    TransparentProgressDialog progressDialog;
    Date mParsedDate;
    String mOutputDateString;
    SharedPreferences preferences;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        progressDialog = TransparentProgressDialog.getInstance();
        preferences = getSharedPreferences("Vendor", MODE_PRIVATE);
        mContext = this;
        OrderHistoryList = new ArrayList<>();

        jObject = new JSONObject();
        toolbar_title = findViewById(R.id.toolbar_title);
        back_relative = findViewById(R.id.back_relative);
        order_history_relative = findViewById(R.id.order_history_relative);
        order_historylist = findViewById(R.id.order_historylist);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        back_img = findViewById(R.id.back_img);


        if (Utility.getSharedPreferences(mContext, Constant.Language) != null) {
            if (Utility.getSharedPreferences(mContext, Constant.Language).equals("ar")) {
                back_img.setScaleX(-1);
            }
        }


        if (nestedScrollView.getVisibility() == View.VISIBLE) {
            if (nestedScrollView != null) {
                nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (v.getChildAt(v.getChildCount() - 1) != null) {
                            if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                                lastPage = lastPage + 1;
                                if (Integer.parseInt(mMaxoffset) >= lastPage) {
                                    NotficationHistoryAPI();
                                } else {
                                    Utility.ShowSnakebarMessage(order_history_relative, getResources().getString(R.string.no_more_data));
                                    // Toast.makeText(mContext, "Sorry, no more data available!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        }

        NotficationHistoryAPI();
        back_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void NotficationHistoryAPI() {

        OrderHistoryList.clear();

        try {
            jObject.put("userid", Utility.getSharedPreferences(mContext, Constant.vendor_id));
            jObject.put("page_no", lastPage);
            jObject.put("lang_id", preferences.getString("lang", ""));

            final String requestBody = jObject.toString();
            Log.e(TAG, "Constant.OrderHistoryAPI " + Constant.OrderHistoryAPI);
            progressDialog.show(mContext);
            StringRequest sr = new StringRequest(Request.Method.POST, Constant.OrderHistoryAPI,
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

                                        SetGet.OrderHistory orderListing = new SetGet.OrderHistory();
                                        orderListing.setRest_name(jsonObject1.optString("rest_name"));
                                        orderListing.setOrder_id(jsonObject1.optString("order_id"));
                                        orderListing.setUnique_order_id(jsonObject1.optString("order_uniqueid"));
                                        orderListing.setPayment_type(jsonObject1.optString("payment_type"));
                                        orderListing.setOrder_status(jsonObject1.optString("order_status"));
                                        orderListing.setOrder_date_time(jsonObject1.optString("order_create"));

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
                                        orderListing.setOrder_date_time("Delivery at " + mOutputDateString);
                                        OrderHistoryList.add(orderListing);
                                    }
                                    order_historylist.setHasFixedSize(true);
                                    // use a linear layout manager
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                                    order_historylist.setLayoutManager(layoutManager);
                                    OrderHistoryAdapter = new OrderHistoryAdapter(mContext, OrderHistoryList);
                                    order_historylist.setAdapter(OrderHistoryAdapter);
                                    OrderHistoryAdapter.notifyDataSetChanged();

                                    progressDialog.dismiss();

                                } else {
                                    progressDialog.dismiss();
                                    Utility.ShowSnakebarMessage(order_history_relative, message);
                                    // Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
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


    /*  public void showProgressDialog(Context context, String message) {
          if (progressDialog == null)
              progressDialog = new TransparentProgressDialog(context, context.getResources().getString(R.string.loading));
          progressDialog.setCancelable(false);
          progressDialog.show();
      }

      public void hideProgressDialog() {
          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  if (progressDialog != null) {
                      progressDialog.dismiss();
                  }
              }
          }, 3000);
      }
  */
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
