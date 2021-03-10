package com.foodforme.vendore.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.foodforme.vendore.R;


/**
 * Created by abc on 06-Nov-17.
 */

 public  class TransparentProgressDialog  {


    private static TransparentProgressDialog uniqInstance = null;
    private static RotateLoading rotateLoading;
    private static Dialog mDialog;
    private static GraduallyTextView mGraduallyTextView;
    private static String text;


    public static TransparentProgressDialog getInstance() {
        if (uniqInstance == null) {
            uniqInstance = new TransparentProgressDialog();
        }
        return uniqInstance;
    }

    public void show(final Context context) {
        if (mDialog == null) {
            mDialog = new Dialog(context, R.style.CustomProgressDialog);
            mDialog.setContentView(R.layout.dialog_loading);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.getWindow().setGravity(Gravity.CENTER);
            View view = mDialog.getWindow().getDecorView();
            rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading);
            mGraduallyTextView = (GraduallyTextView) view.findViewById(R.id.graduallyTextView);
            if (!TextUtils.isEmpty(text)) {
                mGraduallyTextView.setText(text);
            }
            try {
                mDialog.show();
            }
            catch (WindowManager.BadTokenException e)
            {
                e.printStackTrace();
            }
            onStart();
            /*if (mDialog.isShowing())
            {
                onStop();
                mDialog.dismiss();
            } else {
                mDialog.show();
                onStart();
            }*/
        }
    }

    public void onStart() {
        if (mGraduallyTextView != null) {
            mGraduallyTextView.startLoading();
            rotateLoading.start();
        }
    }

    public void onStop() {
        if (mGraduallyTextView != null) {
            mGraduallyTextView.stopLoading();
            rotateLoading.stop();
        }
    }

    public void setText(String str) {
        text = str;
    }

    public void dismiss() {
        try {
            if (mDialog != null) {
                onStop();
                mDialog.dismiss();
                mDialog = null;
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static final String DEBUG_TAG = "TransparentProgressDialog";
//    private String mMessage;
//    private  ImageView iv;
//    public TransparentProgressDialog(Context context, String message) {
//        super(context, R.style.CustomAlertDialogStyle);
//        this.mMessage = message;
//        // TODO Auto-generated constructor stub
//    }
//
//    public TransparentProgressDialog(Context context) {
//        super(context, R.style.CustomAlertDialogStyle);
//        this.mMessage = "Loading...";
//        // TODO Auto-generated constructor stub
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.p);
//
//        setCancelable(false);
//        iv = findViewById(R.id.iv);
//        TextView progressMessage = findViewById(R.id.progressMessage);
//        progressMessage.setText(mMessage);
//    }
//    @Override
//    public void show() {
//        super.show();
//        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
//        anim.setInterpolator(new LinearInterpolator());
//        anim.setRepeatCount(Animation.INFINITE);
//        anim.setDuration(800);
//        iv.setAnimation(anim);
//        iv.startAnimation(anim);
//    }
}
