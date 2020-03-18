package in.ripplr.ripplrdistribution.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CrateResponse implements Parcelable {

    private int count;
    private String next;
    private String previous;
    private ArrayList<Results> results;

    public CrateResponse(int count, String next, String previous, ArrayList<Results> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    protected CrateResponse(Parcel in) {
        count = in.readInt();
        next = in.readString();
        previous = in.readString();
        results = in.createTypedArrayList(Results.CREATOR);
    }

    public static final Creator<CrateResponse> CREATOR = new Creator<CrateResponse>() {
        @Override
        public CrateResponse createFromParcel(Parcel in) {
            return new CrateResponse(in);
        }

        @Override
        public CrateResponse[] newArray(int size) {
            return new CrateResponse[size];
        }
    };

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

    public static class Results implements Parcelable{
        private int id;
        private String name;
        private String colour_name;
        private String colour_code;
        private String crate_status;
        private String comment;

        public Results(int id, String name, String colour_name, String colour_code, String crate_status, String comment) {
            this.id = id;
            this.name = name;
            this.colour_name = colour_name;
            this.colour_code = colour_code;
            this.crate_status = crate_status;
            this.comment = comment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColour_name() {
            return colour_name;
        }

        public void setColour_name(String colour_name) {
            this.colour_name = colour_name;
        }

        public String getColour_code() {
            return colour_code;
        }

        public void setColour_code(String colour_code) {
            this.colour_code = colour_code;
        }

        public String getCrate_status() {
            return crate_status;
        }

        public void setCrate_status(String crate_status) {
            this.crate_status = crate_status;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        protected Results(Parcel in) {
            id = in.readInt();
            name = in.readString();
            colour_name = in.readString();
            colour_code = in.readString();
            crate_status = in.readString();
            comment = in.readString();
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
            dest.writeString(name);
            dest.writeString(colour_name);
            dest.writeString(colour_code);
            dest.writeString(crate_status);
            dest.writeString(comment);
        }
    }
}
