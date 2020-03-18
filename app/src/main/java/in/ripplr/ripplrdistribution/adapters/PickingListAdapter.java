package in.ripplr.ripplrdistribution.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.model.PickingListModel;

public class PickingListAdapter extends RecyclerView.Adapter<PickingListAdapter.MyViewHolder> {

    public List<PickingListModel> PickingListModelList;
    /*Map<String, String> reasonMap = new HashMap<String, String>(){{
        put("MG", "Missing");
        put("DG", "Damage");
        put("PL", "Pick Later");
        put("NS", "No Stock");
    }};*/


    public interface OnItemClickListener {
        void onItemClick(PickingListModel item, View view);
    }

    private final OnItemClickListener listener;

    public PickingListAdapter(List<PickingListModel> PickingListModelList, OnItemClickListener listener) {
        this.PickingListModelList = PickingListModelList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.picking_card_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(holder, PickingListModelList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return PickingListModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name)
        TextView product_name;

        @BindView(R.id.status_layout)
        LinearLayout status_layout;

        @BindView(R.id.status_ic)
        ImageView status_ic;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.pick_qty)
        TextView pick_qty;

        @BindView(R.id.pickked_qty)
        EditText pickked_qty;

        @BindView(R.id.reason)
        Spinner reason;

        @BindView(R.id.reason_layout)
        LinearLayout reason_layout;

        @BindView(R.id.add_btn)
        ImageView add_btn;

        @BindView(R.id.remove_btn)
        ImageView remove_btn;

        @BindView(R.id.submit)
        Button submit;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final MyViewHolder holder, final PickingListModel item, final OnItemClickListener listener) {
            product_name.setText(item.getProduct_name());
            pick_qty.setText(String.valueOf(item.getPick_qty()));
            pickked_qty.setText(String.valueOf(item.getPickked_qty()));

            if(item.isStatus()){
                status_layout.setVisibility(View.VISIBLE);
            }else{
                status_layout.setVisibility(View.GONE);
            }

            if (item.getPickked_qty() < item.getPick_qty()) {
                status_ic.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_partial_complete));
                status.setText("Partialy Completed");
            } else {
                status_ic.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_completed));
                status.setText("Completed");
            }

            validate(holder, item);

            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pickked_qty_count = item.getPickked_qty();
                    pickked_qty_count++;
                    pickked_qty.setText(String.valueOf(pickked_qty_count));
                }
            });

            remove_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pickked_qty_count = item.getPickked_qty();
                    if (pickked_qty_count == 0) {
                        return;
                    }
                    pickked_qty_count--;
                    pickked_qty.setText(String.valueOf(pickked_qty_count));
                }
            });

            pickked_qty.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int pickked_qty_count = Integer.parseInt(0 + s.toString());
                    item.setPickked_qty(pickked_qty_count);
                    validate(holder, item);

                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                    // TODO Auto-generated method stub
                }

                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, v);
                }
            });

            //ArrayAdapter<String> reasonSpinnerAdapter = (ArrayAdapter<String>) reason.getAdapter();
            ArrayAdapter reasonSpinnerAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(),
                    R.array.picking_reason_lables, R.layout.spinner_item);

            reason.setAdapter(reasonSpinnerAdapter);
            ArrayAdapter<String> reasonSpinnerAdapterValues = (ArrayAdapter<String>) reason.getAdapter();
            reason.setSelection(reasonSpinnerAdapterValues.getPosition(getReasonValue(item.getReason())));

            reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String reasonKey = getReasonKey(reason.getSelectedItem().toString().trim());
                    item.setReason(reasonKey);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        private void validate(MyViewHolder holder, PickingListModel item) {
            if (item.getPickked_qty() < item.getPick_qty()) {
                holder.reason_layout.setVisibility(View.VISIBLE);
                holder.add_btn.setClickable(true);
                holder.add_btn.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorGreen));
                holder.add_btn.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
            } else {
                holder.reason_layout.setVisibility(View.GONE);
                item.setReason(null);
                holder.add_btn.setClickable(false);
                holder.add_btn.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray));
                holder.add_btn.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.toggle_normal_off), android.graphics.PorterDuff.Mode.SRC_IN);
            }

            if (item.getPickked_qty() == 0) {
                holder.remove_btn.setClickable(false);
                holder.remove_btn.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray));
                holder.remove_btn.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.toggle_normal_off), android.graphics.PorterDuff.Mode.SRC_IN);
            } else {
                holder.remove_btn.setClickable(true);
                holder.remove_btn.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorGreen));
                holder.remove_btn.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }


    }

    private static String getReasonKey(String txt) {
        Map<String, String> reasonMap = new HashMap<String, String>();
        reasonMap.put("MG", "Missing");
        reasonMap.put("DG", "Damage");
        reasonMap.put("PL", "Pick Later");
        reasonMap.put("NS", "No Stock");
        for (Map.Entry<String, String> entry : reasonMap.entrySet()) {
            if (entry.getValue().equals(txt)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static String getReasonValue(String key) {
        Map<String, String> reasonMap = new HashMap<String, String>();
        reasonMap.put("MG", "Missing");
        reasonMap.put("DG", "Damage");
        reasonMap.put("PL", "Pick Later");
        reasonMap.put("NS", "No Stock");
        String key_ac=reasonMap.get(key);
        return reasonMap.get(key);
    }

}