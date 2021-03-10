
package com.foodforme.vendore.SetterGatter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Options {

    @SerializedName("menu_catid")
    @Expose
    private String menuCatid;
    @SerializedName("rest_id")
    @Expose
    private String restId;
    @SerializedName("menu_id")
    @Expose
    private String menuId;
    @SerializedName("option_name")
    @Expose
    private String optionName;
    @SerializedName("addon_data")
    @Expose
    private List<AddonDatum> addonData = null;
    @SerializedName("offer_name")
    @Expose
    private String offerName;

    public String getMenuCatid() {
        return menuCatid;
    }

    public void setMenuCatid(String menuCatid) {
        this.menuCatid = menuCatid;
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

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public List<AddonDatum> getAddonData() {
        return addonData;
    }

    public void setAddonData(List<AddonDatum> addonData) {
        this.addonData = addonData;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

}
