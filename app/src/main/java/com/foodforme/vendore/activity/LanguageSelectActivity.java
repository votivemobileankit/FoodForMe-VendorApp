package com.foodforme.vendore.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.foodforme.vendore.R;
import com.foodforme.vendore.customwidget.CustomTextView;
import com.foodforme.vendore.languagechange.LocaleHelper;
import com.foodforme.vendore.serverintegration.Constant;
import com.foodforme.vendore.utils.Utility;

import java.util.Locale;

public class LanguageSelectActivity extends AppCompatActivity {

    private static final String TAG = LanguageSelectActivity.class.getSimpleName();
    Context mContext;
    CustomTextView englist_language, arabic_language;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);
        mContext = this;

        englist_language = findViewById(R.id.englist_language);
        arabic_language = findViewById(R.id.arabic_language);
        Utility.setSharedPreference(mContext, Constant.installCheck, "ok");

        englist_language.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updateViews("en");
                Utility.setSharedPreference(mContext, Constant.Language, "en");
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


        arabic_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateViews("ar");
                Utility.setSharedPreference(mContext, Constant.Language, "ar");
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
