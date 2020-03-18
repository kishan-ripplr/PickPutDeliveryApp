package in.ripplr.ripplrdistribution.model;

public class PickSplitRequest {

    private int id;
    private int pick;
    private int pick_quantity;
    private String reason;

    public PickSplitRequest(int pick,int id, int pick_quantity,String reason) {
        this.id = id;
        this.pick = pick;
        this.pick_quantity = pick_quantity;
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPick() {
        return pick;
    }

    public void setPick(int pick) {
        this.pick = pick;
    }

    public int getPick_quantity() {
        return pick_quantity;
    }

    public void setPick_quantity(int pick_quantity) {
        this.pick_quantity = pick_quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
