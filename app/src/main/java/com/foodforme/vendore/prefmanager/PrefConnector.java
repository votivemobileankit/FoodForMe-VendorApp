package com.foodforme.vendore.prefmanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PrefConnector {

    private static final String PREF_NAME = "RESTAURRENT";
    private static final int MODE = Context.MODE_PRIVATE;

    public static final String DEVICE_MANUFACTURER_MODEL = "DEVICE_MANUFACTURER_MODEL";
    public static final String DEVICE_OS_NAME = "DEVICE_OS_NAME";

    public static final String LOGIN_SESSION = "LOGIN_SESSION";

    public static final String SIGNIN_OTP_MOBILE_NO = "SIGNIN_OTP_MOBILE_NO";
    public static final String SIGNIN_EMAILID = "SIGNIN_EMAILID";
    public static final String SIGNUP_OTP_MOBILE_NO = "SIGNUP_OTP_MOBILE_NO";
    public static final String FORGET_PASSWORD_OTP_MOBILE_NO = "FORGET_PASSWORD_OTP_MOBILE_NO";
    public static final String FORGET_PASSWORD_EMAILID = "FORGET_PASSWORD_EMAILID";

    /*registered user details*/
    public static final String USER_ID = "USER_ID";
    public static final String USER_EMAILADDRESS = "USER_EMAILADDRESS";
    public static final String USER_ADDRESS = "USER_ADDRESS";
    public static final String USER_CITY = "USER_CITY";
    public static final String USER_STATE = "USER_STATE";
    public static final String USER_COUNTRY = "USER_COUNTRY";
    public static final String USER_ZIPCODE = "USER_ZIPCODE";
    public static final String USER_CONTACTNUMBER = "USER_CONTACTNUMBER";
    public static final String USER_TWOSTEPAUTHFLAG = "USER_TWOSTEPAUTHFLAG";

    public static final String NOTIFICATIONFLAG = "NOTIFICATIONFLAG";
    public static final String NOTIFICATION_COUNT = "NOTIFICATION_COUNT";


    /*user selected address and their lat, long*/
    public static final String USER_SELECTED_ADDRESS_ROW_ID = "USER_SELECTED_ADDRESS_ROW_ID";
    public static final String USER_SELECTED_ADDRESS = "USER_SELECTED_ADDRESS";
    public static final String USER_SELECTED_ADDRESS_LAT = "USER_SELECTED_ADDRESS_LAT";
    public static final String USER_SELECTED_ADDRESS_LANG = "USER_SELECTED_ADDRESS_LANG";

    /*selected restaurants details*/
    public static final String SELECTED_RESTAURANT_NAME = "SELECTED_RESTAURANT_NAME";
    public static final String SELECTED_RESTAURANT_LAT = "SELECTED_RESTAURANT_LAT";
    public static final String SELECTED_RESTAURANT_LANG = "SELECTED_RESTAURANT_LANG";
    public static final String SELECTED_RESTAURANT_CONTACT_NO = "SELECTED_RESTAURANT_CONTACT_NO";
    public static final String SELECTED_RESTAURANT_ID = "SELECTED_RESTAURANT_ID";
    public static final String SELECTED_RESTAURANT_CART_ID = "SELECTED_RESTAURANT_CART_ID";
    public static final String SELECTED_RESTAURANT_CART_TOTAL = "SELECTED_RESTAURANT_CART_TOTAL";

    public static final String SELECTED_RESTAURANT_OPEN_CLOSE_STATUS = "SELECTED_RESTAURANT_OPEN_CLOSE_STATUS";


    // ASAP Pickup/Delivery Flag
    public static final String SELECTED_RESTAURANT_ASAP_STATUS = "SELECTED_RESTAURANT_ASAP_STATUS";

    // Payment Flag
    public static final String SELECYED_RESTAURANT_PARTIAL_PAYMENT = "SELECYED_RESTAURANT_PARTIAL_PAYMENT";
    public static final String SELECYED_RESTAURANT_PARTIAL__REMAINING_PAYMENT = "SELECYED_RESTAURANT_PARTIAL__REMAINING_PAYMENT";
    public static final String SELECYED_RESTAURANT_PARTIAL__PAID_PAYMENT = "SELECYED_RESTAURANT_PARTIAL__PAID_PAYMENT";
    public static final String SELECYED_RESTAURANT_PARTIAL_PAYMENT_PERCENTAGE = "SELECYED_RESTAURANT_PARTIAL_PAYMENT_PERCENTAGE";
    public static final String SELECYED_RESTAURANT_PAYMENT_MODE = "SELECYED_RESTAURANT_PAYMENT_MODE";
    public static final String SELECYED_RESTAURANT_CASH_ON_DELIVERY_PAYMENT = "SELECYED_RESTAURANT_CASH_ON_DELIVERY_PAYMENT";

    public static final String SELECTED_RESTAURANT_MINIMUM_DELIVERY = "SELECTED_RESTAURANT_MINIMUM_DELIVERY";
    public static final String SELECTED_RESTAURANT_MINIMUM_ORDER = "SELECTED_RESTAURANT_MINIMUM_ORDER";
    /*restaurant location which is used at time of pickup address 1 and address 2*/
    public static final String SELECTED_RESTAURANT_ADDRESS1 = "SELECTED_RESTAURANT_ADDRESS1";
    public static final String SELECTED_RESTAURANT_ADDRESS2 = "SELECTED_RESTAURANT_ADDRESS2";
    public static final String SELECTED_RESTAURANT_ZIPCODE = "SELECTED_RESTAURANT_ZIPCODE";

    /*restaurant filters*/


    public static final String REST_FILTER_APPLY_STATUS = "REST_FILTER_APPLY_STATUS";
    public static final String REST_FILTER_DISTANCE = "REST_FILTER_DISTANCE";
    public static final String REST_FILTER_CUISINES_JSON = "REST_FILTER_CUISINES_JSON";
    public static final String REST_FILTER_CUISINES_SELECTED_ID = "REST_FILTER_CUISINES_SELECTED_ID";
    public static final String REST_FILTER_OPEN_STATUS = "REST_FILTER_OPEN_STATUS";
    public static final String REST_FILTER_SORTBY = "REST_FILTER_SORTBY";
    public static final String REST_FILTER_RATING = "REST_FILTER_RATING";
    public static final String REST_FILTER_PRICE = "REST_FILTER_PRICE";


    /*Order Setting*/
    public static final String CHECKOUT_ORDER_SETTING = "CHECKOUT_ORDER_SETTING";
    public static final String CHECKOUT_ORDER_TYPE = "CHECKOUT_ORDER_TYPE";

    public static final String CHECKOUT_ORDER_TYPE_LOCAL = "CHECKOUT_ORDER_TYPE_LOCAL";

    public static final String CHECKOUT_ORDER_ASAP = "CHECKOUT_ORDER_ASAP";
    public static final String CHECKOUT_ORDER_DATE = "CHECKOUT_ORDER_DATE";
    public static final String CHECKOUT_ORDER_DATE_SHOW = "CHECKOUT_ORDER_DATE_SHOW";
    public static final String CHECKOUT_ORDER_TIME = "CHECKOUT_ORDER_TIME";


    public static final String CHECKOUT_DELIVERY_ADDRESS_ID = "CHECKOUT_DELIVERY_ADDRESS_ID";
    public static final String CHECKOUT_DELIVERY_ADDRESS = "CHECKOUT_DELIVERY_ADDRESS";
    public static final String CHECKOUT_DELIVERY_CITY = "CHECKOUT_DELIVERY_CITY";
    public static final String CHECKOUT_DELIVERY_STATE = "CHECKOUT_DELIVERY_STATE";
    public static final String CHECKOUT_DELIVERY_COUNTRY = "CHECKOUT_DELIVERY_COUNTRY";
    public static final String CHECKOUT_DELIVERY_ZIPCODE = "CHECKOUT_DELIVERY_ZIPCODE";
    public static final String CHECKOUT_DELIVERY_CONTACT = "CHECKOUT_DELIVERY_CONTACT";

    public static final String CHECKOUT_TOTAL_PRICE = "CHECKOUT_TOTAL_PRICE";

    public static final String CHECKOUT_TIP_AMOUNT = "CHECKOUT_TIP_AMOUNT";
    public static final String CHECKOUT_GRAND_TOTAL = "CHECKOUT_GRAND_TOTAL";

    /*about us, term conditon, contact us, policy*/
    public static final String APP_ABOUT_US = "APP_ABOUT_US";
    public static final String APP_CONTACT_US = "APP_CONTACT_US";
    public static final String APP_TERM_CONDITION = "APP_TERM_CONDITION";
    public static final String APP_PRIVACYPOLICY = "APP_PRIVACYPOLICY";

    /*restaurant rating and review*/
    public static final String REST_RATING_COUNT = "REST_RATING_COUNT";
    public static final String REST_FOOD_REVIEW = "REST_FOOD_REVIEW";
    public static final String REST_DELIVERY_REVIEW = "REST_DELIVERY_REVIEW";
    public static final String REST_ORDER_REVIEW = "REST_ORDER_REVIEW";


    /*
    guest user otp verification
     */
    public static final String GUEST_USER_OTP_NAME = "GUEST_USER_OTP_NAME";
    public static final String GUEST_USER_OTP_EMAIL = "GUEST_USER_OTP_EMAIL";
    public static final String GUEST_USER_OTP_MOBILE_CARRIER = "GUEST_USER_OTP_MOBILE_CARRIER";
    public static final String GUEST_USER_OTP_MOBILE_NUMBER = "GUEST_USER_OTP_MOBILE_NUMBER";

    public static final String GUEST_USER_VERIFIED_FLAG = "GUEST_USER_VERIFIED_FLAG";

    // navigation from checkout page to
    public static final String GUEST_NAVIGATION_TO_CHECKOUT = "GUEST_NAVIGATION_TO_CHECKOUT";
    public static final String GUEST_NAVIGATION_TO_CHECKOUT_SIGNUP = "GUEST_NAVIGATION_TO_CHECKOUT_SIGNUP";
    public static final String GUEST_NAVIGATION_TO_CHECKOUT_SIGNIN = "GUEST_NAVIGATION_TO_CHECKOUT_SIGNIN";


    // rating popup at main screen
    public static final String ORDER_RESTAURANT_NAME = "ORDER_RESTAURANT_NAME";
    public static final String ORDER_ID = "ORDER_ID";


    public static final String MAINSCREEN_METADATA = "MAINSCREEN_METADATA";

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }


    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();

    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void writeArraylist(Context context, String key,
                                      List<String> arryid) {
        Set<String> set = new HashSet<String>(arryid);
        getEditor(context).putStringSet(key, set).commit();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static List<String> readArraylist(Context context, String key) {
        Set<String> stock_Set = getPreferences(context).getStringSet(key,
                new HashSet<String>());
        List<String> demo = new ArrayList<String>(stock_Set);

        return demo;
    }


    public static void remove(Context context, String key) {
        getEditor(context).remove(key).commit();
    }

    public static void clear(Context context) {
        getEditor(context).clear().commit();
    }
    public void SaveUserProfile(Context context, String usertype) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("profile_img", usertype);
        prefsEditor.apply();
    }

}
