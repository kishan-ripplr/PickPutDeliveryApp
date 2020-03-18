package in.ripplr.ripplrdistribution.model;

import java.util.ArrayList;

public class PickResponse {
    private int count;
    private String next;
    private String previous;
    private ArrayList<Results> results;

    public PickResponse(int count, String next, String previous, ArrayList<Results> results) {
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

    public class Results {

        private int id;
        private int product;
        private int total_quantity;
        private String pick_date;
        ArrayList<PickSplits> pick_splits;
        private String product_name;

        public Results(int id, int product, int total_quantity, String pick_date, ArrayList<PickSplits> pick_splits, String product_name) {
            this.id = id;
            this.product = product;
            this.total_quantity = total_quantity;
            this.pick_date = pick_date;
            this.pick_splits = pick_splits;
            this.product_name = product_name;
        }

        public class PickSplits {

            private int id;
            private int pick;
            private int pick_quantity;
            private String reason;

            public PickSplits(int id, int pick, int pick_quantity, String reason) {
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProduct() {
            return product;
        }

        public void setProduct(int product) {
            this.product = product;
        }

        public int getTotal_quantity() {
            return total_quantity;
        }

        public void setTotal_quantity(int total_quantity) {
            this.total_quantity = total_quantity;
        }

        public String getPick_date() {
            return pick_date;
        }

        public void setPick_date(String pick_date) {
            this.pick_date = pick_date;
        }

        public ArrayList<PickSplits> getPick_splits() {
            return pick_splits;
        }

        public void setPick_splits(ArrayList<PickSplits> pick_splits) {
            this.pick_splits = pick_splits;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }
    }
}