package com.foodforme.vendore.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class BankDetailSetup extends AppCompatActivity {

    JSONObject jObject;
    CustomTextView toolbar_title;
    Context context;
    EditText holder_name_ext, bank_name_ext, branch_name_ext, account_number_ext, ifsc_code_ext;
    Button add_money;
    TransparentProgressDialog progressDialog;
    private static final String TAG = BankDetailSetup.class.getSimpleName();
    ImageView back_img;
    SharedPreferences preferences;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bankdetail_setup);
        preferences = getSharedPreferences("Vendor", MODE_PRIVATE);

        context = BankDetailSetup.this;
        progressDialog = TransparentProgressDialog.getInstance();
        toolbar_title = findViewById(R.id.toolbar_title);

        holder_name_ext = findViewById(R.id.holder_name_ext);
        bank_name_ext = findViewById(R.id.bank_name_ext);
        branch_name_ext = findViewById(R.id.branch_name_ext);
        account_number_ext = findViewById(R.id.account_number_ext);
        ifsc_code_ext = findViewById(R.id.ifsc_code_ext);

        add_money = findViewById(R.id.add_money);
        //cancel=findViewById(R.id.cancel);

        toolbar_title.setText(getResources().getString(R.string.addBankdetail));
        back_img = findViewById(R.id.back_img);

        getBankDetail();

        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder_name_ext = findViewById(R.id.holder_name_ext);
                bank_name_ext = findViewById(R.id.bank_name_ext);
                branch_name_ext = findViewById(R.id.branch_name_ext);
                account_number_ext = findViewById(R.id.account_number_ext);
                ifsc_code_ext = findViewById(R.id.ifsc_code_ext);
                if (holder_name_ext.getText().toString().trim().isEmpty()) {
                    Toast.makeText(BankDetailSetup.this, R.string.Enter_Account_Holder_Name, Toast.LENGTH_SHORT).show();
                } else if (bank_name_ext.getText().toString().trim().isEmpty()) {
                    Toast.makeText(BankDetailSetup.this, R.string.Enter_Bank_Name, Toast.LENGTH_SHORT).show();
                } else if (branch_name_ext.getText().toString().trim().isEmpty()) {
                    Toast.makeText(BankDetailSetup.this, R.string.Enter_Branch_name, Toast.LENGTH_SHORT).show();
                } else if (account_number_ext.getText().toString().trim().isEmpty()) {
                    Toast.makeText(BankDetailSetup.this, R.string.Enter_Account_Number, Toast.LENGTH_SHORT).show();
                } else if (ifsc_code_ext.getText().toString().trim().isEmpty()) {
                    Toast.makeText(BankDetailSetup.this, R.string.Enter_Ifsc_Code, Toast.LENGTH_SHORT).show();
                } else {
                    bankSetup();
                }


            }
        });
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getBankDetail() {
        try {
            jObject = new JSONObject();
            jObject.put("vendor_id", Utility.getSharedPreferences(context, Constant.vendor_id));
            jObject.put("lang_id", preferences.getString("lang", ""));

            final String requestBody = jObject.toString();
            Log.e("requestBody", requestBody);
            progressDialog.show(context);
            StringRequest sr = new StringRequest(Request.Method.POST, Constant.GET_BANKDETAIL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.show(context);
                            Log.e(TAG, "162 : onResponse: " + response);
                            try {
                                jObject = new JSONObject(response);

                                String ok = jObject.optString("ok");
                                String message = jObject.optString("message");

                                if (ok.equalsIgnoreCase("1")) {
                                    JSONObject jsonObject = jObject.getJSONObject("data");
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("bank_details");
                                    for (int i = 0; i < jsonObject1.length(); i++) {
                                        String id = jsonObject1.getString("id");
                                        String vendor_id = jsonObject1.getString("vendor_id");
                                        String bank_name = jsonObject1.getString("bank_name");
                                        String branch = jsonObject1.getString("branch");
                                        String account_number = jsonObject1.getString("account_number");
                                        String holder_name = jsonObject1.getString("holder_name");
                                        String ifsc = jsonObject1.getString("ifsc");
                                        String status = jsonObject1.getString("status");
                                        String created_at = jsonObject1.getString("created_at");
                                        String updated_at = jsonObject1.getString("updated_at");

                                        holder_name_ext.setText(holder_name);
                                        bank_name_ext.setText(bank_name);
                                        branch_name_ext.setText(branch);
                                        account_number_ext.setText(account_number);
                                        ifsc_code_ext.setText(ifsc);


                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


                                    }

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

    private void bankSetup() {
        try {
            jObject = new JSONObject();
            jObject.put("vendor_id", Utility.getSharedPreferences(context, Constant.vendor_id));
            jObject.put("holder_name", holder_name_ext.getText().toString().trim());
            jObject.put("bank_name", bank_name_ext.getText().toString().trim());
            jObject.put("branch", branch_name_ext.getText().toString().trim());
            jObject.put("account_number", account_number_ext.getText().toString().trim());
            jObject.put("ifsc_code", ifsc_code_ext.getText().toString().trim());
            jObject.put("lang_id", preferences.getString("lang", ""));

            final String requestBody = jObject.toString();
            Log.e("requestBody", requestBody);
            progressDialog.show(context);
            StringRequest sr = new StringRequest(Request.Method.POST, Constant.BANK_SETUP,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.show(context);
                            Log.e(TAG, "162 : onResponse: " + response);
                            try {
                                jObject = new JSONObject(response);

                                String ok = jObject.optString("ok");
                                String message = jObject.optString("message");

                                if (ok.equalsIgnoreCase("1")) {
                                    JSONObject jsonObject = jObject.getJSONObject("data");
                                    JSONArray jsonArray = jsonObject.getJSONArray("bank_detail");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                        String id = jsonObject1.getString("id");
                                        String vendor_id = jsonObject1.getString("vendor_id");
                                        String bank_name = jsonObject1.getString("bank_name");
                                        String branch = jsonObject1.getString("branch");
                                        String account_number = jsonObject1.getString("account_number");
                                        String holder_name = jsonObject1.getString("holder_name");
                                        String ifsc = jsonObject1.getString("ifsc");
                                        String status = jsonObject1.getString("status");
                                        String created_at = jsonObject1.getString("created_at");
                                        String updated_at = jsonObject1.getString("updated_at");
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


                                    }

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
}
