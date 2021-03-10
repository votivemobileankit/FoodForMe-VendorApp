package com.foodforme.vendore.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import com.foodforme.vendore.R;
import com.foodforme.vendore.languagechange.LocaleHelper;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    Context mContext;
    SharedPreferences pref1;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        pref1 = getSharedPreferences("Vendor", MODE_PRIVATE);


      /*  if (pref1.getString("lang_value", "").equalsIgnoreCase("")) {

            updateViews("de");
            SharedPreferences.Editor editor = pref1.edit();
            editor.putString("lang", "de");
            editor.putString("lang_value", "0");
            editor.apply();
            editor.commit();
        }else if (pref1.getString("lang_value", "").equalsIgnoreCase("0")) {
            updateViews("de");
            SharedPreferences.Editor editor = pref1.edit();
            editor.putString("lang", "de");
            editor.putString("lang_value", "0");
            editor.apply();
            editor.commit();
        }else if (pref1.getString("lang_value", "").equalsIgnoreCase("1")) {
            updateViews("en");
            SharedPreferences.Editor editor = pref1.edit();
            editor.putString("lang", "en");
            editor.putString("lang_value", "1");
            editor.apply();
            editor.commit();
        }*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

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

}
