package com.foodforme.vendore.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodforme.vendore.R;
import com.foodforme.vendore.customwidget.CustomEditText;
import com.foodforme.vendore.customwidget.CustomTextView;
import com.foodforme.vendore.languagechange.LocaleHelper;
import com.foodforme.vendore.serverintegration.Constant;
import com.foodforme.vendore.utils.TransparentProgressDialog;
import com.foodforme.vendore.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = UserProfileActivity.class.getSimpleName();

    Context mContext;
    CustomEditText First_name_ext, last_name_ext, user_address_ext,
            user_city_ext, user_state_ext, user_postal_code_ext;
    CustomTextView user_mobile_ext, user_email_ext, user_name_txt;
    RelativeLayout profile_edit_btn, profile_update_btn, back_btn, User_profile_relative;
    ImageView back_icon;
    String Device_id, requestBody;
    JSONObject jsonObject;
    TransparentProgressDialog progressDialog;
    SharedPreferences preferences;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        progressDialog = TransparentProgressDialog.getInstance();
        preferences = getSharedPreferences("Vendor", MODE_PRIVATE);
        mContext = this;
        jsonObject = new JSONObject();

     /*   Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);*/

        First_name_ext = findViewById(R.id.First_name_ext);
        last_name_ext = findViewById(R.id.last_name_ext);
        user_mobile_ext = findViewById(R.id.user_mobile_ext);
        user_email_ext = findViewById(R.id.user_email_ext);
        user_address_ext = findViewById(R.id.user_address_ext);
        user_city_ext = findViewById(R.id.user_city_ext);
        user_state_ext = findViewById(R.id.user_state_ext);
        user_postal_code_ext = findViewById(R.id.user_postal_code_ext);
        profile_edit_btn = findViewById(R.id.profile_edit_btn);
        profile_update_btn = findViewById(R.id.profile_update_btn);
        user_name_txt = findViewById(R.id.user_name_txt);
        back_btn = findViewById(R.id.back_btn);
        back_icon = findViewById(R.id.back_icon);
        User_profile_relative = findViewById(R.id.User_profile_relative);
        if (Utility.getSharedPreferences(mContext, Constant.Language) != null) {
            if (Utility.getSharedPreferences(mContext, Constant.Language).equals("ar")) {
                back_icon.setScaleX(-1);
            }
        }

        if (Utility.getSharedPreferences(mContext, Constant.vendor_name) != null) {
            First_name_ext.setText(Utility.getSharedPreferences(mContext, Constant.vendor_name));
        }
        if (Utility.getSharedPreferences(mContext, Constant.vendor_last_name) != null) {
            last_name_ext.setText(Utility.getSharedPreferences(mContext, Constant.vendor_last_name));
            user_name_txt.setText(First_name_ext.getText().toString() + " " + last_name_ext.getText().toString());
        }
        if (Utility.getSharedPreferences(mContext, Constant.vendor_mob_no) != null) {
            user_mobile_ext.setText(Utility.getSharedPreferences(mContext, Constant.vendor_mob_no));
        }
        if (Utility.getSharedPreferences(mContext, Constant.vendor_email) != null) {
            user_email_ext.setText(Utility.getSharedPreferences(mContext, Constant.vendor_email));
        }
        if (Utility.getSharedPreferences(mContext, Constant.vendor_address) != null) {
            user_address_ext.setText(Utility.getSharedPreferences(mContext, Constant.vendor_address));
        }
        if (Utility.getSharedPreferences(mContext, Constant.vendor_city) != null) {
            user_city_ext.setText(Utility.getSharedPreferences(mContext, Constant.vendor_city));
        }
        if (Utility.getSharedPreferences(mContext, Constant.vendor_state) != null) {
            user_state_ext.setText(Utility.getSharedPreferences(mContext, Constant.vendor_state));
        }
        if (Utility.getSharedPreferences(mContext, Constant.vendor_postalcode) != null) {
            user_postal_code_ext.setText(Utility.getSharedPreferences(mContext, Constant.vendor_postalcode));
        }

        First_name_ext.setFilters(new InputFilter[]{filter});
        last_name_ext.setFilters(new InputFilter[]{filter});
        user_address_ext.setFilters(new InputFilter[]{filter});
        user_city_ext.setFilters(new InputFilter[]{filter});
        user_state_ext.setFilters(new InputFilter[]{filter});
        user_postal_code_ext.setFilters(new InputFilter[]{filter});

        if (profile_update_btn.getVisibility() == View.GONE) {
            profile_edit_btn.setVisibility(View.VISIBLE);
            First_name_ext.setEnabled(false);
            last_name_ext.setEnabled(false);
            user_address_ext.setEnabled(false);
            user_city_ext.setEnabled(false);
            user_state_ext.setEnabled(false);
            user_postal_code_ext.setEnabled(false);
        }


        profile_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_edit_btn.setVisibility(View.GONE);
                profile_update_btn.setVisibility(View.VISIBLE);
                First_name_ext.setEnabled(true);
                last_name_ext.setEnabled(true);
                user_address_ext.setEnabled(true);
                user_city_ext.setEnabled(true);
                user_state_ext.setEnabled(true);
                user_postal_code_ext.setEnabled(true);

            }
        });

        profile_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                UpdateVendorProfile();

            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void UpdateVendorProfile() {

        if (First_name_ext.getText().toString().trim().isEmpty()) {
            Utility.ShowSnakebarMessage(User_profile_relative, getResources().getString(R.string.First_name));
            Log.e(TAG, "first name is empty");
        } else if (last_name_ext.getText().toString().trim().isEmpty()) {
            Utility.ShowSnakebarMessage(User_profile_relative, getResources().getString(R.string.lastname));
            Log.e(TAG, "last name is empty");
        } else if (user_address_ext.getText().toString().trim().isEmpty()) {
            Utility.ShowSnakebarMessage(User_profile_relative, getResources().getString(R.string.user_address));
            Log.e(TAG, "user address is empty");
        } else if (user_city_ext.getText().toString().trim().isEmpty()) {
            Utility.ShowSnakebarMessage(User_profile_relative, getResources().getString(R.string.user_city));
            Log.e(TAG, "user city is empty");
        } else if (user_state_ext.getText().toString().trim().isEmpty()) {
            Utility.ShowSnakebarMessage(User_profile_relative, getResources().getString(R.string.user_state));
            Log.e(TAG, "user state is empty");
        } else if (user_postal_code_ext.getText().toString().trim().isEmpty()) {
            Utility.ShowSnakebarMessage(User_profile_relative, getResources().getString(R.string.user_postal_code));
            Log.e(TAG, "user postal code is empty");
        } else {

            try {
                jsonObject.put("vendorid", Utility.getSharedPreferences(mContext, Constant.vendor_id));
                jsonObject.put("fname", First_name_ext.getText().toString().trim());
                jsonObject.put("lname", last_name_ext.getText().toString().trim());
                jsonObject.put("email", user_email_ext.getText().toString().trim());
                jsonObject.put("address", user_address_ext.getText().toString().trim());
                jsonObject.put("city", user_city_ext.getText().toString().trim());
                jsonObject.put("state", user_state_ext.getText().toString().trim());
                jsonObject.put("postcode", user_postal_code_ext.getText().toString().trim());
                jsonObject.put("mobile", user_mobile_ext.getText().toString().trim());
                jsonObject.put("deviceid", Device_id);
                jsonObject.put("devicetype", Constant.DeviceType);
                jsonObject.put("lang_id", preferences.getString("lang", ""));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("OBJECT", String.valueOf(jsonObject));
            progressDialog.show(mContext);
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestBody = jsonObject.toString();
            StringRequest signinRequest = new StringRequest(Request.Method.POST, Constant.UpdateProfileAPI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e(TAG, "onResponse: " + response);
                            progressDialog.show(mContext);
                            try {
                                JSONObject jObject = new JSONObject(response);
                                String ok = jObject.getString("ok");
                                final String message = jObject.getString("message");
                                if (ok.equalsIgnoreCase("1")) {
                                    JSONObject jsonObject = jObject.getJSONObject("data");
                                    JSONArray jsonArray = jsonObject.getJSONArray("user_detail");
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                                    String email = jsonObject1.getString("email");
                                    Log.e("email", email);
                                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                                    Utility.setSharedPreference(mContext, Constant.vendor_id, jsonObject1.optString("vendor_id"));
                                    Utility.setSharedPreference(mContext, Constant.vendor_name, jsonObject1.optString("name"));
                                    Utility.setSharedPreference(mContext, Constant.vendor_last_name, jsonObject1.optString("last_name"));
                                    Utility.setSharedPreference(mContext, Constant.vendor_email, jsonObject1.optString("email"));
                                    Utility.setSharedPreference(mContext, Constant.deviceid, jsonObject1.optString("deviceid"));
                                    Utility.setSharedPreference(mContext, Constant.vendor_address, jsonObject1.optString("address"));
                                    Utility.setSharedPreference(mContext, Constant.vendor_city, jsonObject1.optString("suburb"));
                                    Utility.setSharedPreference(mContext, Constant.vendor_state, jsonObject1.optString("state"));
                                    Utility.setSharedPreference(mContext, Constant.vendor_postalcode, jsonObject1.optString("zipcode"));
                                    Utility.setSharedPreference(mContext, Constant.vendor_remember_token, jsonObject1.optString("remember_token"));

                                    if (jsonObject1.optString("mob_no") != null) {
                                        Utility.setSharedPreference(mContext, Constant.vendor_mob_no, jsonObject1.optString("mob_no"));
                                    }
                                    user_name_txt.setText(jsonObject1.optString("name") + " " + jsonObject1.optString("last_name"));
                                    profile_edit_btn.setVisibility(View.VISIBLE);
                                    profile_update_btn.setVisibility(View.GONE);
                                    First_name_ext.setEnabled(false);
                                    last_name_ext.setEnabled(false);
                                    user_address_ext.setEnabled(false);
                                    user_city_ext.setEnabled(false);
                                    user_state_ext.setEnabled(false);
                                    user_postal_code_ext.setEnabled(false);

                                } else {
                                    Utility.ShowSnakebarMessage(User_profile_relative, message);
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
                            Log.e(TAG, "onErrorResponse: " + error.getMessage());
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
            requestQueue.add(signinRequest);
        }
    }


    private void hideSoftKeyboard() {
        View view = UserProfileActivity.this.getCurrentFocus();

        if (view != null) {
            //InputMethodManager imm = (InputMethodManager) UserProfileActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            InputMethodManager imm = (InputMethodManager) UserProfileActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
    }*/

    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                //System.out.println("Type : " + type);
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    };

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
