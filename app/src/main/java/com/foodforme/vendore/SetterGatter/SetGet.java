package com.foodforme.vendore.SetterGatter;

public class SetGet {

    private String rest_name;
    private String order_id;
    private String unique_order_id;
    private String order_total;
    private String payment_type;
    private String user_id;
    private String rest_id;
    private String order_fname;
    private String order_lname;
    private String order_email;
    private String order_tel;
    private String order_address;
    private String order_city;
    private String order_pcode;

    private String order_deliveryadd1;
    private String order_deliverynumber;
    private String order_date_time;
    private String order_status;


    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRest_id() {
        return rest_id;
    }

    public void setRest_id(String rest_id) {
        this.rest_id = rest_id;
    }

    public String getOrder_fname() {
        return order_fname;
    }

    public void setOrder_fname(String order_fname) {
        this.order_fname = order_fname;
    }

    public String getOrder_lname() {
        return order_lname;
    }

    public void setOrder_lname(String order_lname) {
        this.order_lname = order_lname;
    }

    public String getOrder_email() {
        return order_email;
    }

    public void setOrder_email(String order_email) {
        this.order_email = order_email;
    }

    public String getOrder_tel() {
        return order_tel;
    }

    public void setOrder_tel(String order_tel) {
        this.order_tel = order_tel;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    public String getOrder_city() {
        return order_city;
    }

    public void setOrder_city(String order_city) {
        this.order_city = order_city;
    }

    public String getOrder_pcode() {
        return order_pcode;
    }

    public void setOrder_pcode(String order_pcode) {
        this.order_pcode = order_pcode;
    }

    public String getOrder_deliveryadd1() {
        return order_deliveryadd1;
    }


    public void setOrder_deliveryadd1(String order_deliveryadd1) {
        this.order_deliveryadd1 = order_deliveryadd1;
    }

    public String getOrder_deliverynumber() {
        return order_deliverynumber;
    }

    public void setOrder_deliverynumber(String order_deliverynumber) {
        this.order_deliverynumber = order_deliverynumber;
    }

    public String getOrder_date_time() {
        return order_date_time;
    }

    public void setOrder_date_time(String order_date_time) {
        this.order_date_time = order_date_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getUnique_order_id() {
        return unique_order_id;
    }

    public void setUnique_order_id(String unique_order_id) {
        this.unique_order_id = unique_order_id;
    }


    public static class orderdetail {

        private String Order_name, Order_qty, Order_price, instruction;

        public String getOrder_name() {
            return Order_name;
        }

        public void setOrder_name(String order_name) {
            Order_name = order_name;
        }

        public String getOrder_qty() {
            return Order_qty;
        }

        public void setOrder_qty(String order_qty) {
            Order_qty = order_qty;
        }

        public String getOrder_price() {
            return Order_price;
        }

        public void setOrder_price(String order_price) {
            Order_price = order_price;
        }

        public String getInstruction() {
            return instruction;
        }

        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }

    }


    public static class OrderHistory {

        private String rest_name, order_id, unique_order_id, order_date_time, order_status, payment_type;

        public String getRest_name() {
            return rest_name;
        }

        public void setRest_name(String rest_name) {
            this.rest_name = rest_name;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getUnique_order_id() {
            return unique_order_id;
        }

        public void setUnique_order_id(String unique_order_id) {
            this.unique_order_id = unique_order_id;
        }

        public String getOrder_date_time() {
            return order_date_time;
        }

        public void setOrder_date_time(String order_date_time) {
            this.order_date_time = order_date_time;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(String payment_type) {
            this.payment_type = payment_type;
        }
    }

    public static class NotificationList {
        private String noti_id, noti_title, noti_desc, noti_deviceid, created_at;

        public String getNoti_id() {
            return noti_id;
        }

        public void setNoti_id(String noti_id) {
            this.noti_id = noti_id;
        }

        public String getNoti_title() {
            return noti_title;
        }

        public void setNoti_title(String noti_title) {
            this.noti_title = noti_title;
        }

        public String getNoti_desc() {
            return noti_desc;
        }

        public void setNoti_desc(String noti_desc) {
            this.noti_desc = noti_desc;
        }

        public String getNoti_deviceid() {
            return noti_deviceid;
        }

        public void setNoti_deviceid(String noti_deviceid) {
            this.noti_deviceid = noti_deviceid;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

}
