package in.ripplr.ripplrdistribution.model;

public class CrateListModel {
    private String crate_num;
    private String crate_name;
    private int quantity;
    private int put_quantity;
    private String reason;
    private String colour_code;

    public CrateListModel(String crate_num, String crate_name, int quantity, int put_quantity, String reason, String colour_code) {
        this.crate_num = crate_num;
        this.crate_name = crate_name;
        this.quantity = quantity;
        this.put_quantity = put_quantity;
        this.reason = reason;
        this.colour_code = colour_code;
    }

    public String getCrate_num() {
        return crate_num;
    }

    public void setCrate_num(String crate_num) {
        this.crate_num = crate_num;
    }

    public String getCrate_name() {
        return crate_name;
    }

    public void setCrate_name(String crate_name) {
        this.crate_name = crate_name;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getColour_code() {
        return colour_code;
    }

    public void setColour_code(String colour_code) {
        this.colour_code = colour_code;
    }
}
