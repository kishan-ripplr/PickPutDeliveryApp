package in.ripplr.ripplrdistribution.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PickSplitResponse implements Parcelable {
    private int id;
    private int pick;
    private int pick_quantity;
    private String reason;

    protected PickSplitResponse(Parcel in) {
        this.id = in.readInt();
        this.pick = in.readInt();
        this.pick_quantity = in.readInt();
        this.reason = in.readString();
    }

    public static final Creator<PickSplitResponse> CREATOR = new Creator<PickSplitResponse>() {
        @Override
        public PickSplitResponse createFromParcel(Parcel in) {
            return new PickSplitResponse(in);
        }

        @Override
        public PickSplitResponse[] newArray(int size) {
            return new PickSplitResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.pick);
        dest.writeInt(this.pick_quantity);
        dest.writeString(this.reason);
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
