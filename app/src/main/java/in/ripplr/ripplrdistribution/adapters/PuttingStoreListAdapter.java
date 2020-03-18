package in.ripplr.ripplrdistribution.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.model.StoreListModel;

public class PuttingStoreListAdapter extends RecyclerView.Adapter<PuttingStoreListAdapter.MyViewHolder> {

    public List<StoreListModel> storeListModels;

    public interface OnItemClickListener {
        void onItemClick(StoreListModel item, View view, int position);
    }

    private final OnItemClickListener listener;

    public PuttingStoreListAdapter(List<StoreListModel> StoreListModel, OnItemClickListener listener) {
        this.storeListModels = StoreListModel;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.putting_store_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(holder, storeListModels.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return storeListModels.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.status_layout)
        LinearLayout status_layout;

        @BindView(R.id.status_ic)
        ImageView status_ic;

        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.store_name)
        TextView store_name;

        @BindView(R.id.qty)
        TextView qty;

        @BindView(R.id.put_qty)
        TextView put_qty;

        /*@BindView(R.id.put_qty)
        EditText put_qty;*/

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(final MyViewHolder holder, final StoreListModel item, final OnItemClickListener listener) {
            store_name.setText(item.getStore_name());
            qty.setText(String.valueOf(item.getQuantity()));
            put_qty.setText(String.valueOf(item.getQuantity()));
            if (item.isStatus()) {
                status_layout.setVisibility(View.VISIBLE);
            } else {
                status_layout.setVisibility(View.GONE);
            }

            if (item.getPut_quantity() < item.getQuantity()) {
                status_ic.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_partial_complete));
                status.setText("Partialy Completed");
            } else {
                status_ic.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_completed));
                status.setText("Completed");
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, itemView, getLayoutPosition());
                }
            });
        }

    }
}