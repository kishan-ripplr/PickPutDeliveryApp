package in.ripplr.ripplrdistribution.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginResponse implements Parcelable {

    private String refresh;
    private String access;

    protected LoginResponse(Parcel in) {
        refresh = in.readString();
        access = in.readString();
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel in) {
            return new LoginResponse(in);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.refresh);
        dest.writeString(this.access);
    }
}
