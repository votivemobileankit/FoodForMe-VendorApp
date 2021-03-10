
package com.foodforme.vendore.SetterGatter;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_detail")
    @Expose
    private OrderDetail orderDetail;
    @SerializedName("Food_detail")
    @Expose
    private List<FoodDetail> foodDetail = null;
    @SerializedName("offer_aplied")
    @Expose
    private String offerAplied;
    @SerializedName("food_offer_aplied")
    @Expose
    private String foodOfferAplied;
    @SerializedName("ok")
    @Expose
    private Integer ok;
    @SerializedName("message")
    @Expose
    private String message;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public List<FoodDetail> getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(List<FoodDetail> foodDetail) {
        this.foodDetail = foodDetail;
    }

    public String getOfferAplied() {
        return offerAplied;
    }

    public void setOfferAplied(String offerAplied) {
        this.offerAplied = offerAplied;
    }

    public String getFoodOfferAplied() {
        return foodOfferAplied;
    }

    public void setFoodOfferAplied(String foodOfferAplied) {
        this.foodOfferAplied = foodOfferAplied;
    }

    public Integer getOk() {
        return ok;
    }

    public void setOk(Integer ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
