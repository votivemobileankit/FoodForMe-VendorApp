package com.foodforme.vendore.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.foodforme.vendore.R;
import com.foodforme.vendore.customwidget.CustomTextView;
import com.foodforme.vendore.fragments.AboutUs;
import com.foodforme.vendore.fragments.ContactUs;
import com.foodforme.vendore.fragments.HomeFragment2;
import com.foodforme.vendore.fragments.OrderHistoryFragment;
import com.foodforme.vendore.languagechange.LocaleHelper;
import com.foodforme.vendore.serverintegration.Constant;
import com.foodforme.vendore.utils.ApplicationController;
import com.foodforme.vendore.utils.TransparentProgressDialog;
import com.foodforme.vendore.utils.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.foodforme.vendore.serverintegration.Constant.TIME_OUT_MIN;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    Context mContext;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    RelativeLayout Main_Relative;
    CustomTextView username, toolbar_title;
    JSONObject jObject, jsonObject;
    Fragment fragment = null;
    TransparentProgressDialog progressDialog;
    public static int navitemindex = 0;
    private static final String CURRENT_TAG = " ";
    SharedPreferences preferences;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("Vendor", MODE_PRIVATE);
        progressDialog = TransparentProgressDialog.getInstance();
        mContext = this;
        jsonObject = new JSONObject();
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.menu_navigation);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(Color.BLACK);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        username = headerLayout.findViewById(R.id.username);


        if (Utility.getSharedPreferences(mContext, Constant.vendor_name).equalsIgnoreCase(null)) {
            // Toast.makeText(getApplicationContext(), "name is not set ", Toast.LENGTH_SHORT).show();
            username.setText(getResources().getString(R.string.User_name));
        } else {
            username.setText(Utility.getSharedPreferences(mContext, Constant.vendor_name) + "  " + Utility.getSharedPreferences(mContext, Constant.vendor_last_name));
        }

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        Main_Relative = findViewById(R.id.Main_Relative);
        toolbar_title = findViewById(R.id.toolbar_title);
        Fragment fragment = new HomeFragment2();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
        sendDeviceToken();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "intent....." + intent.getAction());
                if (intent.getAction().equals("newNotification")) {
                    navitemindex = 0;
                    Fragment fragment = new HomeFragment2();
                    openFragment(fragment);

                }


            }
        };
    }
    @Override
    protected void onPause() {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        username.setText(Utility.getSharedPreferences(mContext, Constant.vendor_name) + "  " + Utility.getSharedPreferences(mContext, Constant.vendor_last_name));
        IntentFilter filterRefreshUpdate = new IntentFilter();
        filterRefreshUpdate.addAction("newNotification");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                filterRefreshUpdate);
//         Fragment fragment = new HomeFragment2();
//         getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }


    @Override
    public void onBackPressed() {//    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (navitemindex == 0) {
                moveTaskToBack(true);
            } else if (navitemindex == 1) {
                navitemindex = 0;
                Fragment fragment = new HomeFragment2();
                openFragment(fragment);
            } else if (navitemindex == 2) {
                navitemindex = 0;
                Fragment fragment = new HomeFragment2();
                openFragment(fragment);
            } else if (navitemindex == 3) {
                navitemindex = 0;
                toolbar_title.setText(" ");
                Fragment fragment = new HomeFragment2();
                openFragment(fragment);
            } else if (navitemindex == 4) {
                navitemindex = 0;
                toolbar_title.setText(" ");
                Fragment fragment = new HomeFragment2();
                openFragment(fragment);
            } else if (navitemindex == 5) {
                navitemindex = 0;
                toolbar_title.setText(" ");
                Fragment fragment = new HomeFragment2();
                openFragment(fragment);
            } else if (navitemindex == 6) {
                navitemindex = 0;
                toolbar_title.setText(" ");
                Fragment fragment = new HomeFragment2();
                openFragment(fragment);
            } else if (navitemindex == 7) {
                navitemindex = 0;
                toolbar_title.setText(" ");
                Fragment fragment = new HomeFragment2();
                openFragment(fragment);
            } else if (navitemindex == 8) {
                navitemindex = 0;
                Fragment fragment = new HomeFragment2();
                openFragment(fragment);
            }
            //    moveTaskToBack(true);

        }
    }

    void openFragment(Fragment fragment) {
        // Fragment fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.content_frame, fragment, CURRENT_TAG);
        fragmentTransaction.commit();
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        //initializing the fragment object which is selected
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                // this.recreate();
                navitemindex = 0;
                toolbar_title.setText(" ");
                fragment = new HomeFragment2();
                break;
            case R.id.nav_profile:
                navitemindex = 1;
                /*toolbar_title.setText(Utility.getSharedPreferences(mContext, Constant.vendor_name)+""+Utility.getSharedPreferences(mContext, Constant.vendor_last_name));
                 fragment = new UserProfileActivity();*/
                Intent i = new Intent(mContext, UserProfileActivity.class);
                startActivity(i);
                break;
            case R.id.nav_wallet:
                navitemindex = 2;
                Intent intent1 = new Intent(mContext, WalletActivty.class);
                startActivity(intent1);
                break;
            case R.id.nav_history:
                navitemindex = 3;
                toolbar_title.setText(getResources().getString(R.string.order_history));
                fragment = new OrderHistoryFragment();

                break;
            case R.id.nav_setting:
                navitemindex = 4;
                toolbar_title.setText(getResources().getString(R.string.setting));
                fragment = new SettingActivity();

                break;
            case R.id.nav_notify:
                navitemindex = 5;
                toolbar_title.setText(getResources().getString(R.string.notification));
                fragment = new NotificationActivity();

                break;
            case R.id.nav_aboutUs:
                navitemindex = 6;
                toolbar_title.setText(getResources().getString(R.string.about));
                fragment = new AboutUs();
                break;
            case R.id.nav_contactUs:
                navitemindex = 7;
                toolbar_title.setText(getResources().getString(R.string.contact));
                fragment = new ContactUs();
                break;
            case R.id.nav_logOut:
                navitemindex = 8;
                showAlert();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        drawer.closeDrawer(GravityCompat.START);

        return false;
    }

    public void showAlert() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getResources().getString(R.string.logout))
                .setMessage(getResources().getString(R.string.logMsg))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utility.clearSharedPreference(mContext);
                        Intent i = new Intent(mContext, LoginActivity.class);
                        startActivity(i);
                        finish();

                    }

                })
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
    }


    private void sendDeviceToken() {

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();
                Log.d(TAG, token);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userid", Utility.getSharedPreferences(getApplicationContext(), Constant.vendor_id));
                    jsonObject.put("guestid", "0");
                    jsonObject.put("devicetoken", token);
                    jsonObject.put("deviceid", Utility.getSharedPreferences(getApplicationContext(), Constant.deviceid));
                    jsonObject.put("devicetype", Constant.DeviceType);
                    jsonObject.put("lang_id", preferences.getString("lang", ""));

                    final String requestBody = jsonObject.toString();

                    Log.e(TAG, "62 : sendDeviceTokenToServer: " + jsonObject.toString());

                    StringRequest sr = new StringRequest(Request.Method.POST, Constant.FirebaseTokenSendAPI,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d(TAG, "68 : onResponse: " + response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(TAG, "74 : onErrorResponse: " + error.getMessage());
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
//                    params.put("device_token", "qwertyui");
//                    params.put("deviceid", AppUtils.getDeviceId(getApplicationContext()));
//                    params.put("devicetype", "android");
                            return params;
                        }
     /* @Override
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
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                        requestBody, "utf-8");
                                return null;
                            }
                        }
                    };
                    ApplicationController.getInstance().addToRequestQueue(sr);
                    sr.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_MIN,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    /*public void showProgressDialog(Context context, String message)
    {
        if (progressDialog == null)
            progressDialog = new TransparentProgressDialog(context, context.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
*/
  /*  public void hideProgressDialog()
    {

        if (progressDialog != null)
        {
            progressDialog.dismiss();
        }
    }
*/




   /* @Override
    public void onBackPressed()
    {
        //    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();

        }
    }*/

}
