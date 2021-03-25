package com.foodforme.vendore.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.foodforme.vendore.adapter.OrderHistoryAdapter;
import com.foodforme.vendore.customwidget.CustomTextView;
import com.foodforme.vendore.serverintegration.Constant;
import com.foodforme.vendore.utils.ApplicationController;
import com.foodforme.vendore.utils.TransparentProgressDialog;
import com.foodforme.vendore.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OrderHistoryFragment extends Fragment {
    private static final String TAG = OrderHistoryFragment.class.getSimpleName();
    Context mContext;
    RelativeLayout back_relative, order_history_relative;
    CustomTextView toolbar_title;
    ImageView back_img;
    RecyclerView order_historylist;
    NestedScrollView nestedScrollView;
    int lastPage = 0;
    public String mMaxoffset = "", TotalPage, PerPage, CurrentPage;
    OrderHistoryAdapter OrderHistoryAdapter;
    JSONObject jObject;
    TransparentProgressDialog progressDialog;
    Date mParsedDate;
    String mOutputDateString;
    Spinner spinner;
    EditText date_pick2, date_pick1;
    Button apply_filter;
    Calendar myCalendar;
    String Order_status;
    String From_date = "";
    String To_date = "";
    SharedPreferences preferences;
    ArrayList<SetGet.OrderHistory> OrderHistoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_order_history, container, false);
        progressDialog = TransparentProgressDialog.getInstance();
        mContext = this.getActivity();
        OrderHistoryList = new ArrayList<>();

        // toolbar_title =view.findViewById(R.id.toolbar_title);
        //  back_relative =view.findViewById(R.id.back_relative);
        order_history_relative = view.findViewById(R.id.order_history_relative);
        order_historylist = view.findViewById(R.id.order_historylist);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);

        myCalendar = Calendar.getInstance();
        date_pick1 = view.findViewById(R.id.date_pick1);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        date_pick1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };
        date_pick2 = view.findViewById(R.id.date_pick2);
        date_pick2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        preferences = mContext.getSharedPreferences("Vendor", Context.MODE_PRIVATE);
        //    toolbar_title.setText(getResources().getString(R.string.order_history));

        spinner = view.findViewById(R.id.spinner);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, getActivity().getResources().getStringArray(R.array.status_class));
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getSelectedItemId() == 0) {
                    Order_status = " ";
                } else if (parent.getSelectedItemId() == 1) {
                    Order_status = "11";
                } else if (parent.getSelectedItemId() == 2) {
                    Order_status = "6";
                }
                Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        apply_filter = view.findViewById(R.id.apply_filter);
        apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                From_date=date_pick1.getText().toString().trim();
//                To_date=date_pick2.getText().toString().trim();

                OrderHistoryList.clear();
                setAdapter();
                NotficationHistoryAPI(Order_status, From_date, To_date);

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
                                    NotficationHistoryAPI(Order_status, From_date, To_date);
                                } else {
                                    Utility.ShowSnakebarMessage(order_history_relative, "Sorry, no more data available!");
                                    // Toast.makeText(mContext, "Sorry, no more data available!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        }

        NotficationHistoryAPI(Order_status, From_date, To_date);

        return view;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        Log.e("Date Picker", "" + sdf.format(myCalendar.getTime()));
        From_date = "" + sdf.format(myCalendar.getTime());
        date_pick1.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabel1() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        To_date = "" + sdf.format(myCalendar.getTime());
        date_pick2.setText(sdf.format(myCalendar.getTime()));
    }

    public void setAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        order_historylist.setLayoutManager(layoutManager);
        OrderHistoryAdapter = new OrderHistoryAdapter(mContext, OrderHistoryList);
        order_historylist.setAdapter(OrderHistoryAdapter);
        OrderHistoryAdapter.notifyDataSetChanged();

    }

    private void NotficationHistoryAPI(String order_status, String from_date, String to_date) {
        OrderHistoryList.clear();
        try {
           /* {"userid":"103","page_no":0,"fromdate":"","order_status":"","todate":"","lang_id":"en"}*/
            jObject = new JSONObject();
            Log.e("dATA VALUE", "" + order_status + "" + from_date + "" + to_date);
            jObject.put("userid", Utility.getSharedPreferences(mContext, Constant.vendor_id));
            jObject.put("page_no", lastPage);
            jObject.put("order_status", order_status);
            jObject.put("fromdate", from_date);
            jObject.put("todate", to_date);
            jObject.put("lang_id", preferences.getString("lang", ""));

            final String requestBody = jObject.toString();
            Log.e("pARAM VALUE", " === " + requestBody);
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
                                    setAdapter();
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
                    return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
                }
            };
            // add the request object to the queue to be executed
            ApplicationController.getInstance().addToRequestQueue(sr);
            sr.setRetryPolicy(new DefaultRetryPolicy(Constant.TIME_OUT_MIN,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
