package in.ripplr.ripplrdistribution.model;

public class MapCrateRequest {

    private int order;
    private int product;
    private int crate;
    private int putting_qty;
    private String reason;

    public MapCrateRequest(int order, int product, int crate, int putting_qty, String reason) {
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
