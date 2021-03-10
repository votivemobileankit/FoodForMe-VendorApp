package com.foodforme.vendore.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import com.foodforme.vendore.R;
import com.foodforme.vendore.customwidget.CustomTextView;
import com.foodforme.vendore.serverintegration.Constant;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import static android.icu.text.DateTimePatternGenerator.PatternInfo.OK;



/**
 * Created by ADMIN on 02-Sep-17.
 */

public class AppUtils {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = AppUtils.class.getSimpleName();

    public static void showAlertDialog(Context mContext, String title, String message)
    {
        LayoutInflater li = LayoutInflater.from(mContext);
        View view = li.inflate(R.layout.dialog_alert, null);
        CustomTextView tvALertTitle = (CustomTextView) view.findViewById(R.id.tvALertTitle);
        tvALertTitle.setTypeface(tvALertTitle.getTypeface(), Typeface.BOLD);
        CustomTextView tvALertMsg = (CustomTextView) view.findViewById(R.id.tvALertMsg);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
//        builder.setTitle(title);
//        builder.setMessage(message);
        tvALertTitle.setText(title);
        tvALertMsg.setText(message);
        builder.setCancelable(false);
        builder.setView(view);
        builder.setPositiveButton(OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private String generatingRandomeNumber() {
        Random random = new Random();
        int low = 10000000;
        int high = 99999999;
        int randomNumber = random.nextInt(high - low) + low;
        return "0" + randomNumber;
    }

    public static String printKeyHash(Activity context) {
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();
            //Retriving package info
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Log.e(TAG, "78 : printKeyHash: " + context.getApplicationContext().getPackageName());
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e(TAG, "84 : printKeyHash: " + key);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public static String getDeviceId(Context context) {
//      IMEI is dependent on the Simcard slot, 2 slot have 2 emei no, req permisson
//      TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//      String imei = mTelephonyMgr.getDeviceId();
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mDateformat = new SimpleDateFormat("yyyy-MM-dd");
        String mCurrentDate = mDateformat.format(calendar.getTime());
        Log.e(TAG, "106 : getCurrentDate: " + mCurrentDate);
        return mCurrentDate;
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mTimeformat = new SimpleDateFormat("HH:mm a");
        String mCurrentTime = mTimeformat.format(calendar.getTime());
        Log.e(TAG, "113 : getCurrentTime: " + mCurrentTime);
        return mCurrentTime;
    }

    @SuppressLint("DefaultLocale")
    public static String getDecentDecimalValue(double inputValue) {
        return String.format("%.2f", inputValue);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     *
     * @param mContext
     */
/*
    public static boolean checkPlayServices(Context mContext) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(mContext);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog((Activity) mContext, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(mContext, "This device is not supported.", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }
*/

   /* public void openGooglePlayStore() {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+this.getPackageName()));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request. Please install a web browser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        finish();
    }*/

    public void getDeviceInfo(Context mContext) {
        String details = "VERSION.RELEASE : " + Build.VERSION.RELEASE
                + "\nVERSION.INCREMENTAL : " + Build.VERSION.INCREMENTAL
                + "\nVERSION.SDK.NUMBER : " + Build.VERSION.SDK_INT
                + "\nBOARD : " + Build.BOARD
                + "\nBOOTLOADER : " + Build.BOOTLOADER
                + "\nBRAND : " + Build.BRAND
                + "\nCPU_ABI : " + Build.CPU_ABI
                + "\nCPU_ABI2 : " + Build.CPU_ABI2
                + "\nDISPLAY : " + Build.DISPLAY
                + "\nFINGERPRINT : " + Build.FINGERPRINT
                + "\nHARDWARE : " + Build.HARDWARE
                + "\nHOST : " + Build.HOST
                + "\nID : " + Build.ID
                + "\nMANUFACTURER : " + Build.MANUFACTURER
                + "\nMODEL : " + Build.MODEL
                + "\nPRODUCT : " + Build.PRODUCT
                + "\nSERIAL : " + Build.SERIAL
                + "\nTAGS : " + Build.TAGS
                + "\nTIME : " + Build.TIME
                + "\nTYPE : " + Build.TYPE
                + "\nUNKNOWN : " + Build.UNKNOWN
                + "\nUSER : " + Build.USER;
        Utility.setSharedPreference(mContext, Constant.DEVICE_MANUFACTURER_MODEL, Build.MANUFACTURER + " " + Build.MODEL);
        Field[] fields = Build.VERSION_CODES.class.getFields();
        String osName = fields[Build.VERSION.SDK_INT + 1].getName();
        Utility.setSharedPreference(mContext, Constant.DEVICE_OS_NAME, osName);
        Log.e(TAG, "182 : getDeviceInfo: " + details);
    }
}
