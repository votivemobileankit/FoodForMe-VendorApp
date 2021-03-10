package com.foodforme.vendore.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    Context mContext;
    RelativeLayout login_relative;
    CustomEditText Userid_mobile_ext, password_ext;
    CustomTextView login_btn, ForgotPass_btn;
    AlertDialog alertDialog;
    String Device_id, requestBody;
    JSONObject jsonObject;
    TransparentProgressDialog progressDialog;
    TextView language;
    SharedPreferences pref1;
    Intent startintent;
    String devicetoken = "";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = TransparentProgressDialog.getInstance();

        mContext = this;
        jsonObject = new JSONObject();
        pref1 = getSharedPreferences("Vendor", MODE_PRIVATE);
        startintent = getIntent();

        if (Utility.getSharedPreferences(mContext, Constant.vendor_id) != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();   //finish current activity
        }

        Device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Userid_mobile_ext = findViewById(R.id.user_id_mobile_ext);
        password_ext = findViewById(R.id.password_ext);
        ForgotPass_btn = findViewById(R.id.forgot_password_btn);
        login_btn = findViewById(R.id.login_btn);
        login_relative = findViewById(R.id.login_relative);

        language = findViewById(R.id.selection);
        if (pref1.getString("lang_value", "").equalsIgnoreCase("1")) {
            language.setText(getResources().getString(R.string.german));
        } else if (pref1.getString("lang_value", "").equalsIgnoreCase("0")) {
            language.setText(getResources().getString(R.string.english));
        } else {
            language.setText(getResources().getString(R.string.german));
        }
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAppLanguage();

            }
        });
        sendDeviceToken();

        Userid_mobile_ext.setFilters(new InputFilter[]{filter});
        password_ext.setFilters(new InputFilter[]{filter});
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isNetworkConnected(mContext)) {
                    loginTask();
                } else {
                    Utility.ShowSnakebarMessage(login_relative, getResources().getString(R.string.please_check_internet));
                }
            }
        });

        ForgotPass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgetPassPopup();
            }
        });


    }

    private void selectAppLanguage() {
        final CharSequence[] options = {getResources().getString(R.string.english), getResources().getString(R.string.german), getResources().getString(R.string.Cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle(getResources().getString(R.string.select_language_txt));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getResources().getString(R.string.english))) {
                    updateViews("en");
                    SharedPreferences.Editor editor = pref1.edit();
                    editor.putString("lang", "en");
                    editor.putString("lang_value", "1");
                    editor.apply();
                    editor.commit();
                    //Toast.makeText(getApplicationContext(), getResources().getString(R.string.English_Language_Selected), Toast.LENGTH_LONG).show();
                    language.setText(getResources().getString(R.string.german));
                    finish();
                    startActivity(startintent);
//                    Intent i=new Intent(getApplicationContext(),LoginActivity.class);
//                    startActivity(i);


                } else if (options[item].equals(getResources().getString(R.string.german))) {
                    updateViews("de");
                    SharedPreferences.Editor editor = pref1.edit();
                    editor.putString("lang", "de");
                    editor.putString("lang_value", "0");
                    editor.apply();
                    editor.commit();
                    //Toast.makeText(getApplicationContext(), getResources().getString(R.string.Arabic_Language_Selected), Toast.LENGTH_LONG).show();
                    language.setText(getResources().getString(R.string.english));
                    finish();
                    startActivity(startintent);
//                    Intent i=new Intent(getApplicationContext(),LoginActivity.class);
//                    startActivity(i);

                } else if (options[item].equals(getResources().getString(R.string.Cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        android.content.res.Configuration conf = resources.getConfiguration();
        conf.setLocale(new Locale(languageCode.toLowerCase())); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
        resources.updateConfiguration(conf, dm);
    }

    private void ForgetPassPopup() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.forget_pass, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setGravity(Gravity.CENTER);


        final EditText forgot_pass_ext = layout.findViewById(R.id.Forgot_pass_ext);
        CustomTextView submitbtn = layout.findViewById(R.id.submit_btn);

        forgot_pass_ext.setFilters(new InputFilter[]{filter});

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (forgot_pass_ext.getText().toString().trim().isEmpty()) {
                    Utility.ShowToastMessage(mContext, getResources().getString(R.string.email_empty));
                    //  Utility.ShowSnakebarMessage(login_relative,getResources().getString(R.string.email_empty));
                    Log.e(TAG, "email is empty");
                } else if (!Utility.isValidEmail(forgot_pass_ext.getText().toString().trim())) {
                    Utility.ShowToastMessage(mContext, getResources().getString(R.string.Valid_email));
                    // Utility.ShowSnakebarMessage(login_relative,getResources().getString(R.string.Valid_email));
                    Log.e(TAG, "password is empty");
                } else {
                    alertDialog.dismiss();
                    ForgetPasswordTask(forgot_pass_ext.getText().toString().trim());

                }
            }
        });


        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);


    }

    private void sendDeviceToken() {
        Log.e("token", "call");
       /* FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String token = task.getResult().getToken();
                LogsUtils.printLog("Token in main", "=====  " + token);
            }
        });*/


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

// Get new Instance ID tokenes
                        devicetoken = task.getResult().getToken();

                        //       devicetoken
                        Log.e("token", devicetoken);


                    }
                });
    }

    private void loginTask() {
        if (Userid_mobile_ext.getText().toString().trim().isEmpty()) {
            Utility.ShowSnakebarMessage(login_relative, getResources().getString(R.string.userid_mobile_empty));
            Log.e(TAG, "email is empty");
        } else if (password_ext.getText().toString().trim().isEmpty()) {
            Utility.ShowSnakebarMessage(login_relative, getResources().getString(R.string.password_empty));
            Log.e(TAG, "password is empty");
        }/*else if (!Utility.isvalidatePassword(password_ext.getText().toString().trim())) {
            Utility.ShowSnakebarMessage(login_relative,getResources().getString(R.string.password_not_valid));
            Log.e(TAG, "password is empty");
        }else if (password_ext.getText().toString().trim().length()<8) {
            Utility.ShowSnakebarMessage(login_relative,getResources().getString(R.string.password_notvalid));
            Log.e(TAG, "password is empty");
        }*/ else {
            try {
                jsonObject.put("mobileno", Userid_mobile_ext.getText().toString().trim());
                jsonObject.put("password", password_ext.getText().toString().trim());
                jsonObject.put("deviceid", Device_id);
               // jsonObject.put("devicetoken", devicetoken);
                jsonObject.put("devicetype", Constant.DeviceType);
                jsonObject.put("lang_id", pref1.getString("lang", ""));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("OBJECT", String.valueOf(jsonObject));
            progressDialog.show(mContext);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestBody = jsonObject.toString();
            StringRequest signinRequest = new StringRequest(Request.Method.POST, Constant.LoginAPI,
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
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    startActivity(intent);


                                } else {
                                    progressDialog.dismiss();
                                    Utility.ShowSnakebarMessage(login_relative, message);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


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


    private void ForgetPasswordTask(String email) {
        try {
            jsonObject.put("email", email);
            jsonObject.put("lang_id", pref1.getString("lang", ""));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show(mContext);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestBody = jsonObject.toString();
        StringRequest signinRequest = new StringRequest(Request.Method.POST, Constant.ForgotPasswordAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: " + response);
                        progressDialog.dismiss();

                        try {
                            JSONObject jObject = new JSONObject(response);
                            String ok = jObject.getString("ok");
                            final String message = jObject.getString("message");
                            if (ok.equalsIgnoreCase("1")) {

                                ForgetPassSuccess();
                            } else {
                                progressDialog.dismiss();
                                Utility.ShowSnakebarMessage(login_relative, getResources().getString(R.string.internetOff));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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


    private void ForgetPassSuccess() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.resend_otp, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setGravity(Gravity.CENTER);

        // ImageView mob_img = layout.findViewById(R.id.mob_img);
        CustomTextView ForgetPass_note = layout.findViewById(R.id.ResendOTP_txt);
        CustomTextView ForgetPass_message = layout.findViewById(R.id.ResendOTP);


        //   mob_img.setImageResource(R.drawable.lock_ref);
        ForgetPass_note.setText(getResources().getString(R.string.forgot_password));
        ForgetPass_message.setText(getResources().getString(R.string.forgetpass_email_send));


        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
            }
        }, 5000);
    }


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

        super.onBackPressed();
        finish();
    }
}




