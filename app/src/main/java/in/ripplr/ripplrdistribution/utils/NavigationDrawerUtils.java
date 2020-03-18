package in.ripplr.ripplrdistribution.utils;

import android.content.Context;
import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.model.DrawerListModel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for the Navigation drawer..
 */
public class NavigationDrawerUtils {


    private Context mContext = null;
    private Map<Integer, String> mMapItems = new LinkedHashMap<>();

    //public static final int  USER_PROFILE = 0;
    public static final int  LOGOUT = 0;


    public NavigationDrawerUtils(Context context){
        this.mContext = context;
    }


    /**
     * Create the drawer utils.
     * @return
     */
    public Map getDrawerUtils(){

        //mMapItems.put(USER_PROFILE , "User Profile");
        mMapItems.put(LOGOUT , "Logout");

        return mMapItems;
    }


    /**
     * Method to get the list of DrawerListModel
     * @return
     */
    public List<DrawerListModel> getDrawerListModel(){

         List<DrawerListModel> mDrawerListModelList = new ArrayList<>();
        Map<Integer, String> mMap = getDrawerUtils();

        DrawerListModel drawerListModel = null;
        for (Map.Entry entry : mMap.entrySet()) {


            String value = (String) entry.getValue();
            int key = (int) entry.getKey();
            drawerListModel = null;

            switch (key) {

                /*case NavigationDrawerUtils.USER_PROFILE:

                    drawerListModel = new DrawerListModel(R.drawable.ic_user_profile,value);

                    break;*/

                case NavigationDrawerUtils.LOGOUT:

                    drawerListModel = new DrawerListModel(R.drawable.ic_logout,value);

                    break;

                default:break;
            }


            if(drawerListModel != null){
                mDrawerListModelList.add(drawerListModel);
            }

        }


        cleanUp();

        return mDrawerListModelList;
    }

    /**
     * Method to cleanup.
     */
    public void cleanUp(){
        if(mMapItems != null){
            mMapItems.clear();
            mMapItems = null;
        }
    }


}
