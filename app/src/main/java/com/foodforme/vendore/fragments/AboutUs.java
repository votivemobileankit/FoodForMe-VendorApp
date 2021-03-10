package com.foodforme.vendore.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foodforme.vendore.R;
import com.foodforme.vendore.languagechange.LocaleHelper;
import com.foodforme.vendore.prefmanager.PrefConnector;
import com.foodforme.vendore.utils.CheckConnection;
import com.foodforme.vendore.utils.GetInternalContentLink;
import com.foodforme.vendore.utils.TransparentProgressDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.foodforme.vendore.serverintegration.Constant.NETWORKMSG;
import static com.foodforme.vendore.serverintegration.Constant.NETWORKTITLE;
import static com.foodforme.vendore.utils.AppUtils.showAlertDialog;

public class AboutUs extends Fragment {

    private static final String TAG = ContactUs.class.getSimpleName();
    private Context mContext;
    private WebView mWebView;
    private TransparentProgressDialog mProgressDialog;
    SharedPreferences preferences;

    protected void attachBaseContext(Context base) {
        attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us, container, false);
        mContext = this.getActivity();
        mProgressDialog = TransparentProgressDialog.getInstance();
        preferences = getActivity().getSharedPreferences("Vendor", MODE_PRIVATE);
        mWebView = (WebView) view.findViewById(R.id.mWebView);
        mWebView.setLongClickable(false);
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                return true;
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setBackgroundColor(Color.TRANSPARENT);
        if (CheckConnection.isNetworkConnected(mContext)) {
            if (PrefConnector.readString(mContext, PrefConnector.APP_ABOUT_US, "").length() > 0) {
                startWebView(PrefConnector.readString(mContext, PrefConnector.APP_ABOUT_US, ""));

                Log.e("APP_ABOUT_US", PrefConnector.readString(mContext, PrefConnector.APP_ABOUT_US, ""));
            } else {
                String language = preferences.getString("lang", "");
                new GetInternalContentLink().getInternalContentLink(mContext, language);
            }
        } else {
            showAlertDialog(mContext, NETWORKTITLE, NETWORKMSG);
        }
        return view;
    }

    private void startWebView(String url) {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressDialog.show(mContext);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onLoadResource(WebView view, String url) {

            }

            public void onPageFinished(WebView view, String url) {
                mProgressDialog.dismiss();
            }

        });

        mWebView.loadUrl(url);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return onKeyDown(keyCode, event);
    }
}
