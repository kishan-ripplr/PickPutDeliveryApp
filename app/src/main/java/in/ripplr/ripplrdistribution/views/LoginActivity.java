package in.ripplr.ripplrdistribution.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("MissingPermission")
public class LoginActivity extends CustomFontActivity {

    @Inject
    ViewModelFactory viewModelFactory;
    MainViewModel viewModel;

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    String fcmId;

    private ManageSharedPreferences manageSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ButterKnife.bind(this);

        manageSharedPreferences = ManageSharedPreferences.newInstance();

        setUpUI();

        ((MyApplication) getApplication()).getAppComponent().doInjection(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        viewModel.getAPIResponse().observe(this, this::consumeResponse);

    }

    public void login(View v) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        final String deviceID = "";
        final String usernameStr = username.getText().toString().trim();
        final String passwordStr = password.getText().toString().trim();
        //final String fcmId = FirebaseInstanceId.getInstance().getInstanceId().getResult().getToken();
        if (usernameStr.length() == 0 || passwordStr.length() == 0) {
            showToast(getString(R.string.enter_username_password));
            return;
        } else {

            if (AppUtils.isNetworkAvailable(getApplicationContext())) {
                viewModel.login(usernameStr, passwordStr);
            } else {
                showToast(getString(R.string.connection_lost_try_again));
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, final int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                checkPermissions(true);
                return;
            }
        }
    }

    private static final String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    private boolean checkPermissions(boolean checked) {
        if (checked) {
            ActivityCompat.requestPermissions(this, permissions, 100);
        } else {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, 100);
                    return false;
                }
            }
        }
        return true;
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                Log.e("loading", "loading");
                setProgressText(WORKING_PLEASE_WAIT);
                showProgress();
                break;

            case SUCCESS:
                dismissProgress();
                renderSuccessResponse(apiResponse.data);
                Log.e("loading", "success");
                break;

            case FAILURE:
                dismissProgress();
                Toast.makeText(getApplicationContext(), apiResponse.message, Toast.LENGTH_SHORT).show();
                break;

            case ERROR:
                dismissProgress();
                Log.e("loading", "error");
                try {
                    JSONObject jsonObject = new JSONObject(apiResponse.errorMessage);
                    showToast(jsonObject.getString("detail"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response) {
        if (response instanceof LoginResponse) {
            LoginResponse loginResponse = (LoginResponse) response;

            manageSharedPreferences.saveString(getApplicationContext(), AppUtils.convertObjectToJson(loginResponse),
                    ManageSharedPreferences.USER_INFO);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
