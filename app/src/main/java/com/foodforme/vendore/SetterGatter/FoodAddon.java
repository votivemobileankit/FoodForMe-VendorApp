
package com.foodforme.vendore.SetterGatter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodAddon {

    @SerializedName("addon_gropname")
    @Expose
    private String addonGropname;
    @SerializedName("addon_type")
    @Expose
    private String addonType;
    @SerializedName("addon_mandatory_or_not")
    @Expose
    private Integer addonMandatoryOrNot;
    @SerializedName("addon_detail")
    @Expose
    private List<AddonDetail> addonDetail = null;

    public String getAddonGropname() {
        return addonGropname;
    }

    public void setAddonGropname(String addonGropname) {
        this.addonGropname = addonGropname;
    }

    public String getAddonType() {
        return addonType;
    }

    public void setAddonType(String addonType) {
        this.addonType = addonType;
    }

    public Integer getAddonMandatoryOrNot() {
        return addonMandatoryOrNot;
    }

    public void setAddonMandatoryOrNot(Integer addonMandatoryOrNot) {
        this.addonMandatoryOrNot = addonMandatoryOrNot;
    }

    public List<AddonDetail> getAddonDetail() {
        return addonDetail;
    }

    public void setAddonDetail(List<AddonDetail> addonDetail) {
        this.addonDetail = addonDetail;
    }

}
