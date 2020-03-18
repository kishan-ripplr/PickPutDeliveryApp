package in.ripplr.ripplrdistribution.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProviders;

import org.json.JSONObject;

import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.model.LoginResponse;
import in.ripplr.ripplrdistribution.mvvm.ApiResponse;
import in.ripplr.ripplrdistribution.mvvm.MainViewModel;
import in.ripplr.ripplrdistribution.mvvm.ViewModelFactory;
import in.ripplr.ripplrdistribution.utils.AppUtils;
import in.ripplr.ripplrdistribution.utils.CustomFontActivity;
import in.ripplr.ripplrdistribution.utils.ManageSharedPreferences;
import in.ripplr.ripplrdistribution.utils.MyApplication;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashScreen_Activity extends CustomFontActivity {

    @Inject
    ViewModelFactory viewModelFactory;
    MainViewModel viewModel;
    private ManageSharedPreferences manageSharedPreferences;

    private static int SPLASH_SCREEN_TIME_OUT = 500;
    public static String TAG = "SplashScreen";
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 200;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_splash_screen);

        ((MyApplication) getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getAPIResponse().observe(this, this::consumeResponse);
        ButterKnife.bind(this);
        manageSharedPreferences = ManageSharedPreferences.newInstance();

        loginResponse = AppUtils.convertJsonToLoginResponse(manageSharedPreferences.
                getString(getApplicationContext(), ManageSharedPreferences.USER_INFO));

        navigateToApp();

    }

    private void showErrorDialog() {
        try {
            AppUtils.showCustomOkDialogCancelable(SplashScreen_Activity.this,
                    getString(R.string.connection_lost),
                    getString(R.string.connection_lost_try_again),
                    getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verify_auth();
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                Log.e("loading", "loading");
                break;

            case SUCCESS:
                renderSuccessResponse(apiResponse.data);
                break;

            case FAILURE:
                showToast(apiResponse.message);
                showErrorDialog();
                break;

            case RELOGIN:
                logout(getString(R.string.session_expired));
                break;

            case ERROR:
                if (apiResponse.data == null) {
                    logout(getString(R.string.session_expired));
                } else {
                    try {
                        JSONObject jsonObject = (JSONObject) apiResponse.data;
                        showToast(jsonObject.getString("detail"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;

            default:
                break;
        }
    }


    private void logout(String message) {
        String json = AppUtils.convertObjectToJson(null);
        manageSharedPreferences.saveString(getApplicationContext(), json,
                ManageSharedPreferences.USER_INFO);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        showToast(message);
    }

    private void renderSuccessResponse(Object data) {
        Log.d("login", "login successfull");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToApp() {

        if(loginResponse!=null){
            Log.d("login", "login successfull");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            /*if(AppUtils.isNetworkAvailable(getApplicationContext())){
                verify_auth();
            }
            else {
                showErrorDialog();
            }*/

        }
        else {
            Intent i=new Intent(SplashScreen_Activity.this,
                    LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void verify_auth() {
        String token = loginResponse.getAccess();

        if (TextUtils.isEmpty(token)) {
            logout("Please login to continue");
            return;
        }

        if (AppUtils.isNetworkAvailable(getApplicationContext())) {
            viewModel.verifyAuth(token);
        } else {
            showToast(getString(R.string.connection_lost_try_again));
        }
    }

}
