package in.ripplr.ripplrdistribution.model;

import java.util.ArrayList;

public class PutRequest {
    private String put_date;
    private ArrayList<PutDetail> put_detail;

    public PutRequest(String put_date, ArrayList<PutDetail> put_detail) {
        this.put_date = put_date;
        this.put_detail = put_detail;
    }

    public String getPut_date() {
        return put_date;
    }

    public void setPut_date(String put_date) {
        this.put_date = put_date;
    }

    public ArrayList<PutDetail> getPut_detail() {
        return put_detail;
    }

    public void setPut_detail(ArrayList<PutDetail> put_detail) {
        this.put_detail = put_detail;
    }

    public static class PutDetail {
        private int order;
        private int product;
        private int crate;
        private int putting_qty;
        private String reason;

        public PutDetail(int order, int product, int crate, int putting_qty, String reason) {
            this.order = order;
            this.product = product;
            this.crate = crate;
            this.putting_qty = putting_qty;
            this.reason = reason;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getProduct() {
            return product;
        }

        public void setProduct(int product) {
            this.product = product;
        }

        public int getCrate() {
            return crate;
        }

        public void setCrate(int crate) {
            this.crate = crate;
        }

        public int getPutting_qty() {
            return putting_qty;
        }

        public void setPutting_qty(int putting_qty) {
            this.putting_qty = putting_qty;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
