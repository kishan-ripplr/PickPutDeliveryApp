package in.ripplr.ripplrdistribution.model;

public class StoreListModel {
    private String store_name;
    private int quantity;
    private int put_quantity;
    private boolean status;

    public StoreListModel(String store_name, int quantity, int put_quantity, boolean status) {
        this.store_name = store_name;
        this.quantity = quantity;
        this.put_quantity = put_quantity;
        this.status = status;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPut_quantity() {
        return put_quantity;
    }

    public void setPut_quantity(int put_quantity) {
        this.put_quantity = put_quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
