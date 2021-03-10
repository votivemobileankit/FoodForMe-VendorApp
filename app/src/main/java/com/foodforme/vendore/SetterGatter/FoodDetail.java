
package com.foodforme.vendore.SetterGatter;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodDetail {

    @SerializedName("menu_id")
    @Expose
    private String menuId;
    @SerializedName("menu_name")
    @Expose
    private String menuName;
    @SerializedName("food_counter")
    @Expose
    private Integer foodCounter;
    @SerializedName("food_detail")
    @Expose
    private FoodDetail_ foodDetail;
    @SerializedName("offer_on_item")
    @Expose
    private String offerOnItem;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getFoodCounter() {
        return foodCounter;
    }

    public void setFoodCounter(Integer foodCounter) {
        this.foodCounter = foodCounter;
    }

    public FoodDetail_ getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(FoodDetail_ foodDetail) {
        this.foodDetail = foodDetail;
    }

    public String getOfferOnItem() {
        return offerOnItem;
    }

    public void setOfferOnItem(String offerOnItem) {
        this.offerOnItem = offerOnItem;
    }

}
