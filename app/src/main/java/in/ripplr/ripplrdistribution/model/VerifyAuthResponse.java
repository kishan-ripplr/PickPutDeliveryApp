package in.ripplr.ripplrdistribution.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VerifyAuthResponse implements Parcelable {


    protected VerifyAuthResponse(Parcel in) {

    }

    public static final Creator<VerifyAuthResponse> CREATOR = new Creator<VerifyAuthResponse>() {
        @Override
        public VerifyAuthResponse createFromParcel(Parcel in) {
            return new VerifyAuthResponse(in);
        }

        @Override
        public VerifyAuthResponse[] newArray(int size) {
            return new VerifyAuthResponse[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
