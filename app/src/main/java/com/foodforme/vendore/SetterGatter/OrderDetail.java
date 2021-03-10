
package com.foodforme.vendore.SetterGatter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_restcartid")
    @Expose
    private String orderRestcartid;
    @SerializedName("order_uniqueid")
    @Expose
    private String orderUniqueid;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("rest_id")
    @Expose
    private String restId;
    @SerializedName("rest_name")
    @Expose
    private String restName;
    @SerializedName("rest_address")
    @Expose
    private String restAddress;
    @SerializedName("rest_logo")
    @Expose
    private String restLogo;
    @SerializedName("order_fname")
    @Expose
    private String orderFname;
    @SerializedName("order_lname")
    @Expose
    private String orderLname;
    @SerializedName("order_email")
    @Expose
    private String orderEmail;
    @SerializedName("order_tel")
    @Expose
    private String orderTel;
    @SerializedName("order_address")
    @Expose
    private String orderAddress;
    @SerializedName("order_city")
    @Expose
    private String orderCity;
    @SerializedName("order_pcode")
    @Expose
    private String orderPcode;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("order_typetime")
    @Expose
    private String orderTypetime;
    @SerializedName("order_tablepic")
    @Expose
    private String orderTablepic;
    @SerializedName("order_pickdate")
    @Expose
    private String orderPickdate;
    @SerializedName("order_picktime")
    @Expose
    private String orderPicktime;
    @SerializedName("order_picktime_formate")
    @Expose
    private String orderPicktimeFormate;
    @SerializedName("order_pickarea")
    @Expose
    private String orderPickarea;
    @SerializedName("order_instruction")
    @Expose
    private String orderInstruction;
    @SerializedName("order_cancel_reason")
    @Expose
    private String orderCancelReason;
    @SerializedName("order_create")
    @Expose
    private String orderCreate;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("order_deliveryfee")
    @Expose
    private String orderDeliveryfee;
    @SerializedName("order_deliveryadd1")
    @Expose
    private String orderDeliveryadd1;
    @SerializedName("order_deliveryadd2")
    @Expose
    private String orderDeliveryadd2;
    @SerializedName("order_deliverysurbur")
    @Expose
    private String orderDeliverysurbur;
    @SerializedName("order_deliverypcode")
    @Expose
    private String orderDeliverypcode;
    @SerializedName("order_deliverynumber")
    @Expose
    private String orderDeliverynumber;
    @SerializedName("order_min_delivery")
    @Expose
    private String orderMinDelivery;
    @SerializedName("order_remaning_delivery")
    @Expose
    private String orderRemaningDelivery;
    @SerializedName("order_subtotal_amt")
    @Expose
    private String orderSubtotalAmt;
    @SerializedName("order_carditem")
    @Expose
    private List<OrderCarditem> orderCarditem = null;
    @SerializedName("order_promo_mode")
    @Expose
    private String orderPromoMode;
    @SerializedName("order_promo_val")
    @Expose
    private String orderPromoVal;
    @SerializedName("order_promo_cal")
    @Expose
    private String orderPromoCal;
    @SerializedName("order_promo_detail")
    @Expose
    private List<Object> orderPromoDetail = null;
    @SerializedName("order_service_tax")
    @Expose
    private String orderServiceTax;
    @SerializedName("order_pmt_type")
    @Expose
    private String orderPmtType;
    @SerializedName("order_partial_percent")
    @Expose
    private String orderPartialPercent;
    @SerializedName("order_partial_payment")
    @Expose
    private String orderPartialPayment;
    @SerializedName("order_partial_remain")
    @Expose
    private String orderPartialRemain;
    @SerializedName("order_pmt_method")
    @Expose
    private String orderPmtMethod;
    @SerializedName("order_total")
    @Expose
    private String orderTotal;
    @SerializedName("order_tip")
    @Expose
    private String orderTip;
    @SerializedName("order_grand_total")
    @Expose
    private String orderGrandTotal;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderRestcartid() {
        return orderRestcartid;
    }

    public void setOrderRestcartid(String orderRestcartid) {
        this.orderRestcartid = orderRestcartid;
    }

    public String getOrderUniqueid() {
        return orderUniqueid;
    }

    public void setOrderUniqueid(String orderUniqueid) {
        this.orderUniqueid = orderUniqueid;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getRestAddress() {
        return restAddress;
    }

    public void setRestAddress(String restAddress) {
        this.restAddress = restAddress;
    }

    public String getRestLogo() {
        return restLogo;
    }

    public void setRestLogo(String restLogo) {
        this.restLogo = restLogo;
    }

    public String getOrderFname() {
        return orderFname;
    }

    public void setOrderFname(String orderFname) {
        this.orderFname = orderFname;
    }

    public String getOrderLname() {
        return orderLname;
    }

    public void setOrderLname(String orderLname) {
        this.orderLname = orderLname;
    }

    public String getOrderEmail() {
        return orderEmail;
    }

    public void setOrderEmail(String orderEmail) {
        this.orderEmail = orderEmail;
    }

    public String getOrderTel() {
        return orderTel;
    }

    public void setOrderTel(String orderTel) {
        this.orderTel = orderTel;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderCity() {
        return orderCity;
    }

    public void setOrderCity(String orderCity) {
        this.orderCity = orderCity;
    }

    public String getOrderPcode() {
        return orderPcode;
    }

    public void setOrderPcode(String orderPcode) {
        this.orderPcode = orderPcode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypetime() {
        return orderTypetime;
    }

    public void setOrderTypetime(String orderTypetime) {
        this.orderTypetime = orderTypetime;
    }

    public String getOrderTablepic() {
        return orderTablepic;
    }

    public void setOrderTablepic(String orderTablepic) {
        this.orderTablepic = orderTablepic;
    }

    public String getOrderPickdate() {
        return orderPickdate;
    }

    public void setOrderPickdate(String orderPickdate) {
        this.orderPickdate = orderPickdate;
    }

    public String getOrderPicktime() {
        return orderPicktime;
    }

    public void setOrderPicktime(String orderPicktime) {
        this.orderPicktime = orderPicktime;
    }

    public String getOrderPicktimeFormate() {
        return orderPicktimeFormate;
    }

    public void setOrderPicktimeFormate(String orderPicktimeFormate) {
        this.orderPicktimeFormate = orderPicktimeFormate;
    }

    public String getOrderPickarea() {
        return orderPickarea;
    }

    public void setOrderPickarea(String orderPickarea) {
        this.orderPickarea = orderPickarea;
    }

    public String getOrderInstruction() {
        return orderInstruction;
    }

    public void setOrderInstruction(String orderInstruction) {
        this.orderInstruction = orderInstruction;
    }

    public String getOrderCancelReason() {
        return orderCancelReason;
    }

    public void setOrderCancelReason(String orderCancelReason) {
        this.orderCancelReason = orderCancelReason;
    }

    public String getOrderCreate() {
        return orderCreate;
    }

    public void setOrderCreate(String orderCreate) {
        this.orderCreate = orderCreate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDeliveryfee() {
        return orderDeliveryfee;
    }

    public void setOrderDeliveryfee(String orderDeliveryfee) {
        this.orderDeliveryfee = orderDeliveryfee;
    }

    public String getOrderDeliveryadd1() {
        return orderDeliveryadd1;
    }

    public void setOrderDeliveryadd1(String orderDeliveryadd1) {
        this.orderDeliveryadd1 = orderDeliveryadd1;
    }

    public String getOrderDeliveryadd2() {
        return orderDeliveryadd2;
    }

    public void setOrderDeliveryadd2(String orderDeliveryadd2) {
        this.orderDeliveryadd2 = orderDeliveryadd2;
    }

    public String getOrderDeliverysurbur() {
        return orderDeliverysurbur;
    }

    public void setOrderDeliverysurbur(String orderDeliverysurbur) {
        this.orderDeliverysurbur = orderDeliverysurbur;
    }

    public String getOrderDeliverypcode() {
        return orderDeliverypcode;
    }

    public void setOrderDeliverypcode(String orderDeliverypcode) {
        this.orderDeliverypcode = orderDeliverypcode;
    }

    public String getOrderDeliverynumber() {
        return orderDeliverynumber;
    }

    public void setOrderDeliverynumber(String orderDeliverynumber) {
        this.orderDeliverynumber = orderDeliverynumber;
    }

    public String getOrderMinDelivery() {
        return orderMinDelivery;
    }

    public void setOrderMinDelivery(String orderMinDelivery) {
        this.orderMinDelivery = orderMinDelivery;
    }

    public String getOrderRemaningDelivery() {
        return orderRemaningDelivery;
    }

    public void setOrderRemaningDelivery(String orderRemaningDelivery) {
        this.orderRemaningDelivery = orderRemaningDelivery;
    }

    public String getOrderSubtotalAmt() {
        return orderSubtotalAmt;
    }

    public void setOrderSubtotalAmt(String orderSubtotalAmt) {
        this.orderSubtotalAmt = orderSubtotalAmt;
    }

    public List<OrderCarditem> getOrderCarditem() {
        return orderCarditem;
    }

    public void setOrderCarditem(List<OrderCarditem> orderCarditem) {
        this.orderCarditem = orderCarditem;
    }

    public String getOrderPromoMode() {
        return orderPromoMode;
    }

    public void setOrderPromoMode(String orderPromoMode) {
        this.orderPromoMode = orderPromoMode;
    }

    public String getOrderPromoVal() {
        return orderPromoVal;
    }

    public void setOrderPromoVal(String orderPromoVal) {
        this.orderPromoVal = orderPromoVal;
    }

    public String getOrderPromoCal() {
        return orderPromoCal;
    }

    public void setOrderPromoCal(String orderPromoCal) {
        this.orderPromoCal = orderPromoCal;
    }

    public List<Object> getOrderPromoDetail() {
        return orderPromoDetail;
    }

    public void setOrderPromoDetail(List<Object> orderPromoDetail) {
        this.orderPromoDetail = orderPromoDetail;
    }

    public String getOrderServiceTax() {
        return orderServiceTax;
    }

    public void setOrderServiceTax(String orderServiceTax) {
        this.orderServiceTax = orderServiceTax;
    }

    public String getOrderPmtType() {
        return orderPmtType;
    }

    public void setOrderPmtType(String orderPmtType) {
        this.orderPmtType = orderPmtType;
    }

    public String getOrderPartialPercent() {
        return orderPartialPercent;
    }

    public void setOrderPartialPercent(String orderPartialPercent) {
        this.orderPartialPercent = orderPartialPercent;
    }

    public String getOrderPartialPayment() {
        return orderPartialPayment;
    }

    public void setOrderPartialPayment(String orderPartialPayment) {
        this.orderPartialPayment = orderPartialPayment;
    }

    public String getOrderPartialRemain() {
        return orderPartialRemain;
    }

    public void setOrderPartialRemain(String orderPartialRemain) {
        this.orderPartialRemain = orderPartialRemain;
    }

    public String getOrderPmtMethod() {
        return orderPmtMethod;
    }

    public void setOrderPmtMethod(String orderPmtMethod) {
        this.orderPmtMethod = orderPmtMethod;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderTip() {
        return orderTip;
    }

    public void setOrderTip(String orderTip) {
        this.orderTip = orderTip;
    }

    public String getOrderGrandTotal() {
        return orderGrandTotal;
    }

    public void setOrderGrandTotal(String orderGrandTotal) {
        this.orderGrandTotal = orderGrandTotal;
    }

}
