package in.ripplr.ripplrdistribution.model;

public class PuttingProductListModel {
    private int product_id;
    private String product_name;
    private String brand_name;
    private int quantity;
    private int put_quantity;
    private boolean status;

    public PuttingProductListModel(int product_id, String product_name, String brand_name, int quantity, int put_quantity, boolean status) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.brand_name = brand_name;
        this.quantity = quantity;
        this.put_quantity = put_quantity;
        this.status = status;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
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
