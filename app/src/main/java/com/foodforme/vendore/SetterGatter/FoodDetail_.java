
package com.foodforme.vendore.SetterGatter;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodDetail_ {

    @SerializedName("food_id")
    @Expose
    private String foodId;
    @SerializedName("food_popular")
    @Expose
    private String foodPopular;
    @SerializedName("food_diet")
    @Expose
    private String foodDiet;
    @SerializedName("food_name")
    @Expose
    private String foodName;
    @SerializedName("food_price")
    @Expose
    private String foodPrice;
    @SerializedName("food_desc")
    @Expose
    private String foodDesc;
    @SerializedName("food_subitem")
    @Expose
    private String foodSubitem;
    @SerializedName("food_subitem_title")
    @Expose
    private String foodSubitemTitle;
    @SerializedName("food_size_detail")
    @Expose
    private List<FoodSizeDetail_> foodSizeDetail = null;
    @SerializedName("food_addon")
    @Expose
    private List<FoodAddon> foodAddon = null;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodPopular() {
        return foodPopular;
    }

    public void setFoodPopular(String foodPopular) {
        this.foodPopular = foodPopular;
    }

    public String getFoodDiet() {
        return foodDiet;
    }

    public void setFoodDiet(String foodDiet) {
        this.foodDiet = foodDiet;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public String getFoodSubitem() {
        return foodSubitem;
    }

    public void setFoodSubitem(String foodSubitem) {
        this.foodSubitem = foodSubitem;
    }

    public String getFoodSubitemTitle() {
        return foodSubitemTitle;
    }

    public void setFoodSubitemTitle(String foodSubitemTitle) {
        this.foodSubitemTitle = foodSubitemTitle;
    }

    public List<FoodSizeDetail_> getFoodSizeDetail() {
        return foodSizeDetail;
    }

    public void setFoodSizeDetail(List<FoodSizeDetail_> foodSizeDetail) {
        this.foodSizeDetail = foodSizeDetail;
    }

    public List<FoodAddon> getFoodAddon() {
        return foodAddon;
    }

    public void setFoodAddon(List<FoodAddon> foodAddon) {
        this.foodAddon = foodAddon;
    }

}
