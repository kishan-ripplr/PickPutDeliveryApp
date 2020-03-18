package in.ripplr.ripplrdistribution.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.model.DrawerListModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for the navigation drawer list.
 */
public class DrawerListAdapter extends BaseAdapter {

    /**
     * Context of the activity.
     */
    private Context mContext = null;

    /**
     * Drawer list items.
     */
    private List<DrawerListModel> mListModel = new ArrayList<>();


    /**
     * Constructor of this class.
     * @param context
     * @param list
     */
    public DrawerListAdapter(Context context , List<DrawerListModel> list){
        this.mContext = context;
        this.mListModel = list;
    }



    @Override
    public int getCount() {
        return mListModel.size();
    }

    @Override
    public Object getItem(int position) {
        return mListModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerListModel model = mListModel.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.row_drawer_list , null);
            viewHolder.mTxtTitle = (TextView) convertView.findViewById(R.id.txt_rowtitle);
            viewHolder.mIvIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.lnr_main_layout = convertView.findViewById(R.id.lnr_main_layout);

            convertView.setTag(viewHolder);

            viewHolder.mTxtTitle.setText(model.getTitle());
            viewHolder.mIvIcon.setImageResource(model.getImgRes());


        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    static class ViewHolder{
        TextView mTxtTitle = null;
        ImageView mIvIcon = null;
        LinearLayout lnr_main_layout = null;
    }

}
