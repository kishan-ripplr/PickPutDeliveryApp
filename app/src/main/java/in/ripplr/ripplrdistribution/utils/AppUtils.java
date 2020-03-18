package in.ripplr.ripplrdistribution.utils;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;

import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.model.LoginResponse;

public class AppUtils {

    public static Dialog dialog_ok_dialog;

    private static final String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    public static boolean isLocationPermissionEnabled(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return false;
        }

        return true;
    }

    public static boolean isCameraPermissionEnabled(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            return false;
        }

        return true;
    }

    public static boolean isStoragePermissionEnabled(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            return false;
        }

        return true;
    }

    public static boolean isLocationEnabled(Context context) {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }

    }


    public static String convertObjectToJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static LoginResponse convertJsonToLoginResponse(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, LoginResponse.class);
    }


    /**
     * Method to check if a device is connected to internet or not
     *
     * @param context
     * @return true - if the device connected to internet; false - otherwise
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static Dialog showCustomOkDialogCancelable(Context context, String title, String message,
                                                      String positiveBtnTxt,
                                                      final View.OnClickListener positivecallback) {

        dialog_ok_dialog = new Dialog(context, R.style.custompopup_style);
        dialog_ok_dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog_ok_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog_ok_dialog.setContentView(R.layout.custom_ok_layout);
        dialog_ok_dialog.setCancelable(false);
        TextView txtTitle = (TextView) dialog_ok_dialog.findViewById(R.id.tv_custompopup_title);
        TextView txtMessage = (TextView) dialog_ok_dialog.findViewById(R.id.tv_custompopup_msg);
        txtMessage.setMovementMethod(new ScrollingMovementMethod());
        TextView txtPositive = (TextView) dialog_ok_dialog.findViewById(R.id.txt_custompopup_postive);

        txtTitle.setText(title);
        txtMessage.setText(message);
        txtPositive.setText(positiveBtnTxt);

        txtPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_ok_dialog.dismiss();
                if (positivecallback != null) {
                    positivecallback.onClick(v);
                }
            }
        });

        dialog_ok_dialog.show();

        return null;
    }

    public static Dialog showCustomOkCancelDialogCancelable(Context context, String title, String message,
                                                            String positiveBtnTxt, String negativeBtnTxt,
                                                            final View.OnClickListener positivecallback) {

        dialog_ok_dialog = new Dialog(context, R.style.custompopup_style);
        dialog_ok_dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog_ok_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog_ok_dialog.setContentView(R.layout.custom_ok_cancel_layout);
        dialog_ok_dialog.setCancelable(false);
        TextView txtTitle = (TextView) dialog_ok_dialog.findViewById(R.id.tv_custompopup_title);
        TextView txtMessage = (TextView) dialog_ok_dialog.findViewById(R.id.tv_custompopup_msg);
        txtMessage.setMovementMethod(new ScrollingMovementMethod());
        TextView txtPositive = (TextView) dialog_ok_dialog.findViewById(R.id.txt_custompopup_postive);
        TextView txt_custompopup_negative = dialog_ok_dialog.findViewById(R.id.txt_custompopup_negative);

        txtTitle.setText(title);
        txtMessage.setText(message);
        txtPositive.setText(positiveBtnTxt);
        txt_custompopup_negative.setText(negativeBtnTxt);

        txtPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_ok_dialog.dismiss();
                if (positivecallback != null) {
                    positivecallback.onClick(v);
                }
            }
        });

        txt_custompopup_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_ok_dialog.dismiss();
            }
        });

        dialog_ok_dialog.show();

        return null;
    }


}
