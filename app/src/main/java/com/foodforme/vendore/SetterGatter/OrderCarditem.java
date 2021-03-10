
package com.foodforme.vendore.SetterGatter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCarditem {

    @SerializedName("rowId")
    @Expose
    private String rowId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("instruction")
    @Expose
    private String instruction;
    @SerializedName("options")
    @Expose
    private Options options;
    @SerializedName("food_size_detail")
    @Expose
    private List<FoodSizeDetail> foodSizeDetail = null;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public List<FoodSizeDetail> getFoodSizeDetail() {
        return foodSizeDetail;
    }

    public void setFoodSizeDetail(List<FoodSizeDetail> foodSizeDetail) {
        this.foodSizeDetail = foodSizeDetail;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

}
