package com.foodforme.vendore.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodforme.vendore.R;
import com.foodforme.vendore.SetterGatter.SetGet;
import com.foodforme.vendore.SetterGatter.WalletInfo;
import com.foodforme.vendore.adapter.TransactionHistoryAdapter;
import com.foodforme.vendore.adapter.WalletInfoAdapter;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.foodforme.vendore.serverintegration.Constant.MSG_EXCEPTION;

public class WalletActivty extends AppCompatActivity {

    private static final String TAG = WalletActivty.class.getSimpleName();
    Toolbar toolbar;
    Context mContext;
    RelativeLayout back_relative, send_wallet_bal;
    CustomTextView toolbar_title;
    RecyclerView notificationlist;
    ImageView back_img;
    RecyclerView Transactionlist;
    NestedScrollView nestedScrollView;
    int lastPage = 0;
    public String mMaxoffset = "", TotalPage, PerPage, CurrentPage;
    JSONObject jObject;
    RelativeLayout Transaction_History_layout;
    TransactionHistoryAdapter transactionHistoryAdapter;
    ArrayList<SetGet.orderdetail> transectionlist;
    TransparentProgressDialog progressDialog;
    EditText userInput;

    JSONObject jsonObject;
    private String requestBody;
    ArrayList<WalletInfo> walletInfoArrayList;
    RecyclerView transect_list;

    private WalletInfoAdapter walletInfoAdapter;
    CustomTextView walletAmount;
    String wallet_amount;
    RelativeLayout add_money;
    SharedPreferences preferences;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_activty);
        preferences = getSharedPreferences("Vendor", MODE_PRIVATE);

        mContext = this;
        jObject = new JSONObject();
        progressDialog = TransparentProgressDialog.getInstance();
        transectionlist = new ArrayList<SetGet.orderdetail>();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);*/

        toolbar.setNavigationIcon(R.drawable.left_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //       toolbar_title  = findViewById(R.id.toolbar_title);


        notificationlist = findViewById(R.id.notificationlist);
        back_relative = findViewById(R.id.back_relative);
        back_img = findViewById(R.id.back_img);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        Transaction_History_layout = findViewById(R.id.Transaction_History_layout);
        send_wallet_bal = findViewById(R.id.send_wallet_bal);

        transect_list = findViewById(R.id.transect_list);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        walletAmount = findViewById(R.id.wallet_amount);

        getWalletInfo();
        //   toolbar_title.setText(getResources().getString(R.string.Wallet));

        //   TransactionHistorylist();

        // transactionHistoryAdapter.notifyDataSetChanged();

/*

        if(Utility.getSharedPreferences(mContext, Constant.Language)!=null)
        {
            if (Utility.getSharedPreferences(mContext, Constant.Language).equals("ar"))
            {
                back_img.setScaleX(-1);
            }
        }*/

/*
        back_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/


        send_wallet_bal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // get prompts.xml view

                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View promptsView = layoutInflater.inflate(R.layout.custom_dialog, null);

                AlertDialog.Builder alBuilder = new AlertDialog.Builder(mContext);
                // set prompts.xml to alertdialog builder
                alBuilder.setView(promptsView);

                userInput = promptsView.findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alBuilder.setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.OK),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        if (userInput.getText().toString().trim().isEmpty()) {
                                            Toast.makeText(mContext, getResources().getString(R.string.Enter_Amount), Toast.LENGTH_SHORT).show();
                                        } else {

                                            requestAmount();
                                        }
                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alBuilder.create();
                // show it
                alertDialog.show();

            }
        });


/*
        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (v.getChildAt(v.getChildCount() - 1) != null) {
                        if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                            lastPage = lastPage + 1;
                            if (Integer.parseInt(mMaxoffset) >= lastPage) {
                                TransactionHistorylist();
                            } else {
                                Utility.ShowSnakebarMessage(Transaction_History_layout,"Sorry, no more data available!");
                                 Toast.makeText(mContext, "Sorry, no more data available!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }
*/


    }

    public void getWalletInfo() {
        try {
            progressDialog.show(this);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            jsonObject = new JSONObject();
            jsonObject.put("vendor_id", Utility.getSharedPreferences(mContext, Constant.vendor_id));
            jsonObject.put("lang_id", preferences.getString("lang", ""));
            requestBody = jsonObject.toString();
            Log.e(TAG, "332 : userwallet: " + Constant.MY_WALLET);
            Log.e(TAG, "333 : userwallet: " + jsonObject.toString());
            StringRequest signinRequest = new StringRequest(Request.Method.POST, Constant.MY_WALLET,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e(TAG, "338 : onResponse: " + response);
                            progressDialog.dismiss();
                            try {
                                walletInfoArrayList = new ArrayList<WalletInfo>();
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.optString("message");
                                String ok = jsonObject.optString("ok");
                                if (ok.equalsIgnoreCase("1")) {
                                    JSONObject jObject = jsonObject.getJSONObject("data");
                                    wallet_amount = jObject.getString("wallet_amount");
                                    walletAmount.setText("NIS " + "" + wallet_amount);

                                    String bankStatus = jObject.getString("bankStatus");

                                    JSONArray jsonArray = jObject.getJSONArray("payment_summary");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String vendor_wallet_id = jsonObject1.getString("id");
                                        String vendor_id = jsonObject1.getString("vendor_id");
                                        String total_pay_amount = jsonObject1.getString("total_pay_amount");
                                        String txn_method = jsonObject1.getString("txn_method");
                                        String vendor_amount = jsonObject1.getString("vendor_amount");
                                        String commission_amount = jsonObject1.getString("commission_amount");
                                        String admin_commission_per = jsonObject1.getString("admin_commission_per");
                                        String updated_at = jsonObject1.getString("updated_at");
                                        String created_at = jsonObject1.getString("created_at");
                                        String transfer_status = jsonObject1.getString("transfer_status");

                                        WalletInfo walletInfo = new WalletInfo();
                                        walletInfo.setId(vendor_wallet_id);
                                        walletInfo.setVend_id(vendor_id);
                                        walletInfo.setAdmin_commission_per(admin_commission_per);
                                        walletInfo.setCommission_amount(commission_amount);
                                        walletInfo.setCreated_at(created_at);
                                        walletInfo.setTxn_method(txn_method);
                                        walletInfo.setTotal_pay_amount(total_pay_amount);
                                        walletInfo.setTransfer_status(transfer_status);
                                        walletInfo.setVendor_amount(vendor_amount);
                                        walletInfo.setUpdated_at(updated_at);

                                        walletInfoArrayList.add(walletInfo);
                                    }
                                    transect_list.setHasFixedSize(true);
                                    // use a linear layout manager
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                                    transect_list.setLayoutManager(layoutManager);
                                    walletInfoAdapter = new WalletInfoAdapter(getApplicationContext(), walletInfoArrayList);
                                    transect_list.setAdapter(walletInfoAdapter);
                                    walletInfoAdapter.notifyDataSetChanged();
                                } else {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "369 : onErrorResponse: " + error.getMessage());
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), MSG_EXCEPTION, Toast.LENGTH_SHORT).show();
                        }
                    }) {
              /*  @Override
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
            requestQueue.add(signinRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_money_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMoney:
                /* Action goes here */
                Intent intent = new Intent(WalletActivty.this, BankDetailSetup.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void requestAmount() {
        try {
            jObject = new JSONObject();
            jObject.put("vendor_id", Utility.getSharedPreferences(mContext, Constant.vendor_id));
            jObject.put("request_amount", userInput.getText().toString().trim());
            jObject.put("lang_id", preferences.getString("lang", ""));
            final String requestBody = jObject.toString();
            Log.e("requestBody", requestBody);
            progressDialog.show(mContext);
            StringRequest sr = new StringRequest(Request.Method.POST, Constant.REQUEST_AMOUNT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.show(mContext);
                            Log.e(TAG, "162 : onResponse: " + response);
                            try {
                                jObject = new JSONObject(response);

                                String ok = jObject.optString("ok");
                                String message = jObject.optString("message");

                                if (ok.equalsIgnoreCase("1")) {
                                    JSONObject jsonObject = jObject.getJSONObject("data");
                                    String Req_amount = jsonObject.getString("request_amount");
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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

    /* private void TransactionHistorylist()
     {
         Transactionlist.setHasFixedSize(true);
         // use a linear layout manager
         LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
         Transactionlist.setLayoutManager(layoutManager);
         transactionHistoryAdapter = new TransactionHistoryAdapter(mContext, transectionlist);
         Transactionlist.setAdapter(transactionHistoryAdapter);
     }
 */
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
