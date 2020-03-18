package in.ripplr.ripplrdistribution.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class PutDateFilterResponse implements Parcelable{
    private int count;
    private String next;
    private String previous;
    private ArrayList<Results> results;

    protected PutDateFilterResponse(Parcel in) {
        count = in.readInt();
        next = in.readString();
        previous = in.readString();
        results = in.createTypedArrayList(Results.CREATOR);
    }

    public static final Creator<PutDateFilterResponse> CREATOR = new Creator<PutDateFilterResponse>() {
        @Override
        public PutDateFilterResponse createFromParcel(Parcel in) {
            return new PutDateFilterResponse(in);
        }

        @Override
        public PutDateFilterResponse[] newArray(int size) {
            return new PutDateFilterResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeString(next);
        dest.writeString(previous);
        dest.writeTypedList(results);
    }

    public PutDateFilterResponse(int count, String next, String previous, ArrayList<Results> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public ArrayList<Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }

    public static class Results implements Parcelable {
        private int id;
        private String put_date;

        protected Results(Parcel in) {
            id = in.readInt();
            put_date = in.readString();
        }

        public static final Creator<Results> CREATOR = new Creator<Results>() {
            @Override
            public Results createFromParcel(Parcel in) {
                return new Results(in);
            }

            @Override
            public Results[] newArray(int size) {
                return new Results[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(put_date);
        }

        public Results(int id, String put_date) {
            this.id = id;
            this.put_date = put_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPut_date() {
            return put_date;
        }

        public void setPut_date(String put_date) {
            this.put_date = put_date;
        }
    }

}