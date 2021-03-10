
package com.foodforme.vendore.SetterGatter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddonDetail {

    @SerializedName("addon_id")
    @Expose
    private String addonId;
    @SerializedName("addon_menucatid")
    @Expose
    private String addonMenucatid;
    @SerializedName("addon_groupname")
    @Expose
    private String addonGroupname;
    @SerializedName("addon_option")
    @Expose
    private String addonOption;
    @SerializedName("addon_name")
    @Expose
    private String addonName;
    @SerializedName("addon_price")
    @Expose
    private String addonPrice;
    @SerializedName("addon_status")
    @Expose
    private String addonStatus;

    public String getAddonId() {
        return addonId;
    }

    public void setAddonId(String addonId) {
        this.addonId = addonId;
    }

    public String getAddonMenucatid() {
        return addonMenucatid;
    }

    public void setAddonMenucatid(String addonMenucatid) {
        this.addonMenucatid = addonMenucatid;
    }

    public String getAddonGroupname() {
        return addonGroupname;
    }

    public void setAddonGroupname(String addonGroupname) {
        this.addonGroupname = addonGroupname;
    }

    public String getAddonOption() {
        return addonOption;
    }

    public void setAddonOption(String addonOption) {
        this.addonOption = addonOption;
    }

    public String getAddonName() {
        return addonName;
    }

    public void setAddonName(String addonName) {
        this.addonName = addonName;
    }

    public String getAddonPrice() {
        return addonPrice;
    }

    public void setAddonPrice(String addonPrice) {
        this.addonPrice = addonPrice;
    }

    public String getAddonStatus() {
        return addonStatus;
    }

    public void setAddonStatus(String addonStatus) {
        this.addonStatus = addonStatus;
    }

}
