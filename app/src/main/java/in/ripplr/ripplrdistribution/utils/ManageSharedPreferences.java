package in.ripplr.ripplrdistribution.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ManageSharedPreferences {

    public static final String USER_INFO = "USER_INFO";
    public static final String CRATE_DETAILS = "CRATE_DETAILS";


    private static ManageSharedPreferences mManageSharedPreferences = null;


    private ManageSharedPreferences() {

    }

    public static synchronized ManageSharedPreferences newInstance(){
        if(mManageSharedPreferences == null){
            mManageSharedPreferences = new ManageSharedPreferences();
        }

        return mManageSharedPreferences;
    }

    public void saveString(Context context, String text , String key) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(key, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(key, text); //3
        editor.commit(); //4
    }

    public String getString(Context context, String key) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(key, Context.MODE_PRIVATE); //1
        text = settings.getString(key, null); //2
        return text;
    }

    /**
     * Method to save the boolean value
     * @param context
     * @param value
     * @param key
     */
    public void saveBoolean(Context context, boolean value , String key) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(key, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putBoolean(key, value); //3
        editor.commit(); //4
    }


    /**
     * Method to get the string value.
     * @param context
     * @return
     */
    public boolean getBoolean(Context context, String key) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(key, Context.MODE_PRIVATE); //1
        return settings.getBoolean(key, false); //2;
    }

}
