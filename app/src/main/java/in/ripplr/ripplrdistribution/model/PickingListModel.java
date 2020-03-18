package in.ripplr.ripplrdistribution.model;

public class PickingListModel {

    private int pick_id;
    private int pick_split_id;
    private String product_name;
    private int pick_qty;
    private int pickked_qty;
    private String reason;
    private boolean status;

    public PickingListModel(int pick_id, int pick_split_id, String product_name, int pick_qty, int pickked_qty, String reason,boolean status) {
        this.pick_id = pick_id;
        this.pick_split_id = pick_split_id;
        this.product_name = product_name;
        this.pick_qty = pick_qty;
        this.pickked_qty = pickked_qty;
        this.reason = reason;
        this.status=status;
    }

    public int getPick_id() {
        return pick_id;
    }

    public void setPick_id(int pick_id) {
        this.pick_id = pick_id;
    }

    public int getPick_split_id() {
        return pick_split_id;
    }

    public void setPick_split_id(int pick_split_id) {
        this.pick_split_id = pick_split_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPick_qty() {
        return pick_qty;
    }

    public void setPick_qty(int pick_qty) {
        this.pick_qty = pick_qty;
    }

    public int getPickked_qty() {
        return pickked_qty;
    }

    public void setPickked_qty(int pickked_qty) {
        this.pickked_qty = pickked_qty;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
