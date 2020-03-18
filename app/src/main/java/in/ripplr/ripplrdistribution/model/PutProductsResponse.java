package in.ripplr.ripplrdistribution.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PutProductsResponse implements Parcelable {

    private ArrayList<PutProducts> put_products;

    public PutProductsResponse(ArrayList<PutProducts> put_products) {
        this.put_products = put_products;
    }


    protected PutProductsResponse(Parcel in) {
        put_products = in.createTypedArrayList(PutProducts.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(put_products);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PutProductsResponse> CREATOR = new Creator<PutProductsResponse>() {
        @Override
        public PutProductsResponse createFromParcel(Parcel in) {
            return new PutProductsResponse(in);
        }

        @Override
        public PutProductsResponse[] newArray(int size) {
            return new PutProductsResponse[size];
        }
    };

    public ArrayList<PutProducts> getPut_products() {
        return put_products;
    }

    public void setPut_products(ArrayList<PutProducts> put_products) {
        this.put_products = put_products;
    }



    public static class PutProducts implements Parcelable{
        private int product_id;
        private int ordered_qty;
        private int order_id;
        private int store_id;
        private String store_name;
        private String product_name;

        public PutProducts(int product_id, int ordered_qty, int order_id, int store_id, String store_name, String product_name) {
            this.product_id = product_id;
            this.ordered_qty = ordered_qty;
            this.order_id = order_id;
            this.store_id = store_id;
            this.store_name = store_name;
            this.product_name = product_name;
        }

        protected PutProducts(Parcel in) {
            product_id = in.readInt();
            ordered_qty = in.readInt();
            order_id = in.readInt();
            store_id = in.readInt();
            store_name = in.readString();
            product_name = in.readString();
        }

        public static final Creator<PutProducts> CREATOR = new Creator<PutProducts>() {
            @Override
            public PutProducts createFromParcel(Parcel in) {
                return new PutProducts(in);
            }

            @Override
            public PutProducts[] newArray(int size) {
                return new PutProducts[size];
            }
        };

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getOrdered_qty() {
            return ordered_qty;
        }

        public void setOrdered_qty(int ordered_qty) {
            this.ordered_qty = ordered_qty;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(product_id);
            dest.writeInt(ordered_qty);
            dest.writeInt(order_id);
            dest.writeInt(store_id);
            dest.writeString(store_name);
            dest.writeString(product_name);
        }
    }
}
