package com.foodforme.vendore.serverintegration;

public class Constant {

    /*Notification Channel ===========================================*/

    public static final String CHANNEL_ID = "my_channel_02";
    public static final String CHANNEL_NAME = "foodforme_vendor";
    public static final String CHANNEL_DESCRIPTION = "www.foodforme.com";
    public static final String SECURITY_KEY = "VOTIVE";
    public static final String SECURITY_VALUE = "123456";
    public static final String BASEURL="http://www.foodforme.ch/ws/";
   // public static final String BASEURL = "https://votivetech.in/restaurantsmarketplace/ws/";
    public static final String LoginAPI = BASEURL + "vendor-login";
    public static final String FirebaseTokenSendAPI = BASEURL + "vendor_devicetoken";
    public static final String ForgotPasswordAPI = BASEURL + "vendor-forget-passwrod";
    public static final String NewOrderAPI = BASEURL + "vendorOrder-list";
    public static final String InProgressOrderAPI = BASEURL + "vendor_inprogressOrder-list";
    public static final String OrderDetailAPI = BASEURL + "vendorOrder-detail";
    public static final String OrderAcceptReject = BASEURL + "vendor-accept_or_reject_order";
    public static final String OrderHistoryAPI = BASEURL + "vendorHistoryOrder-list";
    public static final String UpdateProfileAPI = BASEURL + "vendor-edit-profile";
    public static final String OrderNotificationeAPI = BASEURL + "vendor_notification_list";
    public static final String NOTIFICATION_STATUS = BASEURL + "notification_status";
    public static final String APP_CONTENT_LINK = BASEURL + "content_pages";
    public static final String MY_WALLET = BASEURL + "vendor/my_wallet ";
    public static final String REQUEST_AMOUNT = BASEURL + "vendor/request-amount";
    public static final String BANK_SETUP = BASEURL + "vendor/bank-setup";
    public static final String GET_BANKDETAIL = BASEURL + "vendor/get_bank_details";
    public static final String BILL_PRINT = BASEURL + "invoice_order";
    //Perameters
    public static final String Language = "lang";
    public static final String DeviceType = "android";
    public static final String vendor_id = "vendor_id";
    public static final String vendor_name = "name";
    public static final String vendor_last_name = "last_name";
    public static final String vendor_email = "email";
    public static final String vendor_mob_no = "mob_no";
    public static final String vendor_address = "address";
    public static final String vendor_city = "suburb";
    public static final String vendor_state = "state";
    public static final String vendor_postalcode = "zipcode";
    public static final String deviceid = "deviceid";
    public static final String vendor_remember_token = "remember_token";
    public static final String Order = "Order";
    public static final String installCheck = "install";
    public static final int TIME_OUT_MIN = 1000 * 30;
    public static final String MSG_EXCEPTION = "The Server is not responding.\nPlease try again later.";
    public static final String NETWORKTITLE = "Well That Didn't Work....";
    public static final String NETWORKMSG = "Your request failed due to a timeout, connectivity issue, or planetary misalignment. Let's try again!";

    public static final String DEVICE_OS_NAME = "DEVICE_OS_NAME";
    public static final String DEVICE_MANUFACTURER_MODEL = "DEVICE_MANUFACTURER_MODEL";
}
