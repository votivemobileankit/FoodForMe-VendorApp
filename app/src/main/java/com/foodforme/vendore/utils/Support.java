package com.foodforme.vendore.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.webkit.URLUtil.isAboutUrl;
import static android.webkit.URLUtil.isAssetUrl;
import static android.webkit.URLUtil.isContentUrl;
import static android.webkit.URLUtil.isFileUrl;
import static android.webkit.URLUtil.isHttpUrl;
import static android.webkit.URLUtil.isHttpsUrl;
import static android.webkit.URLUtil.isJavaScriptUrl;
//import android.support.design.widget.Snackbar;

/**
 * Created by user on 14/6/16.
 */
public class Support {
    static Context mContext;
    private static String PREFERENCE = "Patnapark";

    public Support(Context c) {
        this.mContext = c;
    }

//    public static Typeface ButtonFont(Context context) {
//        Typeface tf = Typeface.createFromAsset(context.getAssets(),
//                Constants.FONT_STYLE);
//        return tf;
//    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }


public static void showToast(Context context,String s)
{
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
}
    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$ ";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void app_msg(final Context mContext, final String msg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        alertDialogBuilder.setMessage(Html.fromHtml(/*"<font color='#D35400'>" +*/ msg + " "   /*+ "</font>"*/));
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
    }

    //     myFormat -> "Aug 21,2012" to "2019-04-03";
    public static String convertDate(String indate)
    {
        String formattedDate="";
        try {
            DateFormat originalFormat = new SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date1);
            Log.e("","formattedDate1= "+formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  formattedDate;
    }
    //     myFormat -> "2019-02-27 10:43:30" to "2019-04-03";
    public static String convertDate1(String indate)
    {
        String formattedDate="";
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date1);
            Log.e("","formattedDate2= "+formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  formattedDate;
    }

    //     myFormat -> "2019-04-03" to "04/03/2019";
    public static String convertDate2(String indate)
    {
        String formattedDate="";
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date1);
            Log.e("","formattedDate1= "+formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  formattedDate;
    }
//2019-04-26 05:54:42
    //     myFormat -> "2019-02-27 10:43:30" to "Aug 21,2012";
    public static String convertDate3(String indate)
    {
        String formattedDate="";
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("MMM dd,yyyy");
            Date date1 = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date1);
            Log.e("","formattedDate2= "+formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  formattedDate;
    }

    //     myFormat -> "2019-02-27 10:43:30" to "04/03/2019";
    public static String convertDate4(String indate)
    {
        String formattedDate="";
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date1);
            Log.e("","formattedDate2= "+formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  formattedDate;
    }

    /**
     * @return {@code true} if the url is valid.
     */
    public static boolean isValidUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }

        return (isAssetUrl(url) ||
                isResourceUrl(url) ||
                isFileUrl(url) ||
                isAboutUrl(url) ||
                isHttpUrl(url) ||
                isHttpsUrl(url) ||
                isJavaScriptUrl(url) ||
                isContentUrl(url));
    }
    /**
     * @return True iff the url is a resource file.
     * @hide
     */
    private static boolean isResourceUrl(String url) {
        return (null != url) && url.startsWith(RESOURCE_BASE);
    }
    private static final String RESOURCE_BASE = "file:///android_res/";

    public static int getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.heightPixels;
    }
 public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.widthPixels;
    }


    /**
     * Function to get Progress percentage
     */
    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration;
        totalDuration = totalDuration / 1000;
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    public static String convertMilisecToFormat(int videoTotal){
        String convertString;
        if (TimeUnit.MILLISECONDS.toHours(videoTotal) != 0){
            convertString = String.format(Locale.getDefault(),"%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(videoTotal),
                    TimeUnit.MILLISECONDS.toMinutes(videoTotal)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(videoTotal)),
                    TimeUnit.MILLISECONDS.toSeconds(videoTotal)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(videoTotal)));
        }else {
            convertString = String.format(Locale.getDefault(),"%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(videoTotal)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(videoTotal)),
                    TimeUnit.MILLISECONDS.toSeconds(videoTotal)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(videoTotal)));
        }
        return convertString;
    }

    public static String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public static byte[] readBytesFromFile (String aFilePath){
        FileInputStream fio = null;
        byte[] bytesArray = null;
        try {
            File file = new File(aFilePath);
            bytesArray = new byte[(int) file.length()];
            fio = new FileInputStream(file);
            fio.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fio != null) {
                try {
                    fio.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }

/*
    public static TextView Alertmessage(final Context context, String titleString, String descriptionString, String negetiveText, String positiveText) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        if (dialog.getWindow() !=null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.logout_popup_layout);

        TextView title_popup = dialog.findViewById(R.id.title_popup);
        TextView message_popup = dialog.findViewById(R.id.message_popup);
        TextView no_text_popup = dialog.findViewById(R.id.no_text_popup);
        TextView yes_text_popup = dialog.findViewById(R.id.yes_text_popup);
        title_popup.setText(titleString);
        message_popup.setText(descriptionString);
        no_text_popup.setText(negetiveText);
        yes_text_popup.setText(positiveText);

        no_text_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        return yes_text_popup;
    }
*/


    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }

}
