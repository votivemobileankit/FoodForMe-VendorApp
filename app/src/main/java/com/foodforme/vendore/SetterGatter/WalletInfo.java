package com.foodforme.vendore.SetterGatter;

public class WalletInfo
{
    public String total_pay_amount;
    public String vend_id;
    public String vendor_amount;
    public String id;
    public String updated_at;
    public String txn_method;
    public String commission_amount;

    public String getTotal_pay_amount() {
        return total_pay_amount;
    }

    public void setTotal_pay_amount(String total_pay_amount) {
        this.total_pay_amount = total_pay_amount;
    }

    public String getVend_id() {
        return vend_id;
    }

    public void setVend_id(String vend_id) {
        this.vend_id = vend_id;
    }

    public String getVendor_amount() {
        return vendor_amount;
    }

    public void setVendor_amount(String vendor_amount) {
        this.vendor_amount = vendor_amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getTxn_method() {
        return txn_method;
    }

    public void setTxn_method(String txn_method) {
        this.txn_method = txn_method;
    }

    public String getCommission_amount() {
        return commission_amount;
    }

    public void setCommission_amount(String commission_amount) {
        this.commission_amount = commission_amount;
    }

    public String getAdmin_commission_per() {
        return admin_commission_per;
    }

    public void setAdmin_commission_per(String admin_commission_per) {
        this.admin_commission_per = admin_commission_per;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTransfer_status() {
        return transfer_status;
    }

    public void setTransfer_status(String transfer_status) {
        this.transfer_status = transfer_status;
    }

    public String admin_commission_per;
    public String created_at;
    public String transfer_status;



}
