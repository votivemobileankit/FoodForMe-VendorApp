
package com.foodforme.vendore.SetterGatter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodSizeDetail implements Serializable {

    @SerializedName("menu_cat_itm_id")
    @Expose
    private String menuCatItmId;
    @SerializedName("rest_id")
    @Expose
    private String restId;
    @SerializedName("menu_id")
    @Expose
    private String menuId;
    @SerializedName("area_id")
    @Expose
    private String areaId;
    @SerializedName("menu_category")
    @Expose
    private String menuCategory;
    @SerializedName("menu_item_title")
    @Expose
    private String menuItemTitle;
    @SerializedName("menu_item_price")
    @Expose
    private String menuItemPrice;
    @SerializedName("menu_item_status")
    @Expose
    private String menuItemStatus;
    @SerializedName("menu_cat_itm_price")
    @Expose
    private String menuCatItmPrice;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getMenuCatItmId() {
        return menuCatItmId;
    }

    public void setMenuCatItmId(String menuCatItmId) {
        this.menuCatItmId = menuCatItmId;
    }

    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(String menuCategory) {
        this.menuCategory = menuCategory;
    }

    public String getMenuItemTitle() {
        return menuItemTitle;
    }

    public void setMenuItemTitle(String menuItemTitle) {
        this.menuItemTitle = menuItemTitle;
    }

    public String getMenuItemPrice() {
        return menuItemPrice;
    }

    public void setMenuItemPrice(String menuItemPrice) {
        this.menuItemPrice = menuItemPrice;
    }

    public String getMenuItemStatus() {
        return menuItemStatus;
    }

    public void setMenuItemStatus(String menuItemStatus) {
        this.menuItemStatus = menuItemStatus;
    }

    public String getMenuCatItmPrice() {
        return menuCatItmPrice;
    }

    public void setMenuCatItmPrice(String menuCatItmPrice) {
        this.menuCatItmPrice = menuCatItmPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
