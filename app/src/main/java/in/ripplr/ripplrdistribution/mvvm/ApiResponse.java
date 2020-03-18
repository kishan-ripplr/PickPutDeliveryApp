package in.ripplr.ripplrdistribution.mvvm;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import static in.ripplr.ripplrdistribution.mvvm.Status.ERROR;
import static in.ripplr.ripplrdistribution.mvvm.Status.FAILURE;
import static in.ripplr.ripplrdistribution.mvvm.Status.LOADING;
import static in.ripplr.ripplrdistribution.mvvm.Status.RELOGIN;
import static in.ripplr.ripplrdistribution.mvvm.Status.SUCCESS;

public class ApiResponse {

    public final Status status;

    @Nullable
    public final Object data;

    @Nullable
    public final Throwable error;

    public String message = null;
    public int errorCode;
    public String errorMessage="";

    public String wallet;

    private ApiResponse(Status status, @Nullable Object data, @Nullable Throwable error, String message, int errorCode,String errorMessage) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.message = message;
        this.errorCode = errorCode;
        this.errorMessage=errorMessage;
    }


    public static ApiResponse loading(int errorCode) {
        return new ApiResponse(LOADING, null, null, null, errorCode,"");
    }

    public static ApiResponse success(@NonNull Object data, String message, int errorCode,String errorMessage) {
        if(errorCode==200){
            return new ApiResponse(SUCCESS, data, null, message, errorCode,errorMessage);
        }
        else if(errorCode==401){
            return new ApiResponse(RELOGIN, data, null, message, errorCode,errorMessage);
        }
        else {
            return new ApiResponse(FAILURE, data, null, message, errorCode,errorMessage);
        }
//        else {
//            if(data instanceof EndRideResponse){
//                if(errorCode==400){
//                    return new ApiResponse(WRONG_ODOMETER_READING, data, null, message, errorCode);
//                }
//                else {
//                    return new ApiResponse(FAILURE, data, null, message, errorCode);
//                }
//            }
//            return new ApiResponse(FAILURE, data, null, message, errorCode);
//        }
    }

    public static ApiResponse error(Status status, @Nullable Object data, @Nullable Throwable error, String message, int errorCode) {
        HttpException httpException=(HttpException) error;
        JSONObject jsonResponse=null;
        String errorMessage="";
        try{
            errorMessage=httpException.response().errorBody().string();
            jsonResponse=new JSONObject(httpException.response().errorBody().string());
        }catch (Exception e){
            e.printStackTrace();
        }
        Status error_status= ERROR;
        if(errorCode==403){
            error_status= RELOGIN;
        }
        return new ApiResponse(error_status, httpException.response().errorBody(), error, null,errorCode,errorMessage);
    }

}
