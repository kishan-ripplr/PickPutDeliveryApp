package in.ripplr.ripplrdistribution.model;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;

public class PutAddCrateResponse implements Parcelable {
    private int id;
    private String put_date;
    private ArrayList<PutDetail> put_detail;

    public PutAddCrateResponse(int id, String put_date, ArrayList<PutDetail> put_detail) {
        this.id = id;
        this.put_date = put_date;
        this.put_detail = put_detail;
    }

    protected PutAddCrateResponse(Parcel in) {
        id = in.readInt();
        put_date = in.readString();
        put_detail = in.createTypedArrayList(PutDetail.CREATOR);
    }

    public static final Creator<PutAddCrateResponse> CREATOR = new Creator<PutAddCrateResponse>() {
        @Override
        public PutAddCrateResponse createFromParcel(Parcel in) {
            return new PutAddCrateResponse(in);
        }

        @Override
        public PutAddCrateResponse[] newArray(int size) {
            return new PutAddCrateResponse[size];
        }
    };

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

    public ArrayList<PutDetail> getPut_detail() {
        return put_detail;
    }

    public void setPut_detail(ArrayList<PutDetail> put_detail) {
        this.put_detail = put_detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(put_date);
        dest.writeTypedList(put_detail);
    }

    public static class PutDetail implements Parcelable {
        private int id;
        private int put;
        private int order;
        private int product;
        private String product_code;
        private String product_name;
        private int crate;
        private String crate_name;
        private String crate_colour_code;
        private int putting_qty;
        private String reason;
        private int put_by;


        public PutDetail(int id, int put, int order, int product, String product_code, String product_name, int crate, String crate_name, String crate_colour_code, int putting_qty, String reason, int put_by) {
            this.id = id;
            this.put = put;
            this.order = order;
            this.product = product;
            this.product_code = product_code;
            this.product_name = product_name;
            this.crate = crate;
            this.crate_name = crate_name;
            this.crate_colour_code = crate_colour_code;
            this.putting_qty = putting_qty;
            this.reason = reason;
            this.put_by = put_by;
        }

        protected PutDetail(Parcel in) {
            id = in.readInt();
            put = in.readInt();
            order = in.readInt();
            product = in.readInt();
            product_code = in.readString();
            product_name = in.readString();
            crate = in.readInt();
            crate_name = in.readString();
            crate_colour_code = in.readString();
            putting_qty = in.readInt();
            reason = in.readString();
            put_by = in.readInt();
        }

        public static final Creator<PutDetail> CREATOR = new Creator<PutDetail>() {
            @Override
            public PutDetail createFromParcel(Parcel in) {
                return new PutDetail(in);
            }

            @Override
            public PutDetail[] newArray(int size) {
                return new PutDetail[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPut() {
            return put;
        }

        public void setPut(int put) {
            this.put = put;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getProduct() {
            return product;
        }

        public void setProduct(int product) {
            this.product = product;
        }

        public String getProduct_code() {
            return product_code;
        }

        public void setProduct_code(String product_code) {
            this.product_code = product_code;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getCrate() {
            return crate;
        }

        public void setCrate(int crate) {
            this.crate = crate;
        }

        public String getCrate_name() {
            return crate_name;
        }

        public void setCrate_name(String crate_name) {
            this.crate_name = crate_name;
        }

        public String getCrate_colour_code() {
            return crate_colour_code;
        }

        public void setCrate_colour_code(String crate_colour_code) {
            this.crate_colour_code = crate_colour_code;
        }

        public int getPutting_qty() {
            return putting_qty;
        }

        public void setPutting_qty(int putting_qty) {
            this.putting_qty = putting_qty;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getPut_by() {
            return put_by;
        }

        public void setPut_by(int put_by) {
            this.put_by = put_by;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(put);
            dest.writeInt(order);
            dest.writeInt(product);
            dest.writeString(product_code);
            dest.writeString(product_name);
            dest.writeInt(crate);
            dest.writeString(crate_name);
            dest.writeString(crate_colour_code);
            dest.writeInt(putting_qty);
            dest.writeString(reason);
            dest.writeInt(put_by);
        }
    }
}
