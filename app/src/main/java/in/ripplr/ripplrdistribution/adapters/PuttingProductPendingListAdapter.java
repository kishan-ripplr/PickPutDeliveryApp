package in.ripplr.ripplrdistribution.adapters;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.model.PuttingProductListModel;
import in.ripplr.ripplrdistribution.views.MainActivity;

public class PuttingProductPendingListAdapter extends RecyclerView.Adapter<PuttingProductPendingListAdapter.MyViewHolder> {

    public List<PuttingProductListModel> puttingProductListModel;

    public interface OnItemClickListener {
        void onItemClick(PuttingProductListModel item, View view);
    }

    private final OnItemClickListener listener;

    public PuttingProductPendingListAdapter(List<PuttingProductListModel> PuttingProductListModel, OnItemClickListener listener) {
        this.puttingProductListModel = PuttingProductListModel;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.putting_pending_product_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(holder, puttingProductListModel.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return puttingProductListModel.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name)
        TextView product_name;

        @BindView(R.id.qty)
        TextView qty;

        /*@BindView(R.id.put_qty)
        EditText put_qty;*/

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(final MyViewHolder holder, final PuttingProductListModel item, final OnItemClickListener listener) {
            product_name.setText(item.getProduct_name());
            qty.setText(String.valueOf(item.getQuantity()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item,itemView);
                }
            });
            //put_qty.setText(String.valueOf(item.getPut_quantity()));


            /*put_qty.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int put_qty_count = Integer.parseInt(0 + s.toString());
                    item.setPut_quantity(put_qty_count);
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                    // TODO Auto-generated method stub
                }

                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                }
            });*/
        }

    }
}