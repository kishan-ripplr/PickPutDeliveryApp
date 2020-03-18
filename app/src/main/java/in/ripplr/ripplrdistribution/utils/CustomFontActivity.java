package in.ripplr.ripplrdistribution.utils;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import in.ripplr.ripplrdistribution.R;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CustomFontActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Toolbar toolbar;
    //public static final String serverLoc = "http://nebulaapi.digilogx.com/appnativeservice/";
    public static final String serverLoc = "http://vtsapi.digilogx.com/appnativeservice/";
    public static final String WORKING_PLEASE_WAIT = "Working, please wait....";
    public SharedPreferences prefs, prefsvehicle;
    public String userId;
    public String authToken;
    public String vendorId;
    public String userType, imgLink, loginTime, vendorName, cityName, Name, driverPhone, drivingLicense, flag, message, vehicleType, vehicleTypeId, userNameAsLoginId;
    SimpleDateFormat datetoday = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
    int today = Integer.parseInt(datetoday.format(Calendar.getInstance().getTime()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("erroorr", "yes");
    }

    protected void setUpUI() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        userId = prefs.getString("user_id", "");
        userNameAsLoginId = prefs.getString("user_name", "");
        authToken = prefs.getString("auth_token", "");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Working, please wait....");
        pDialog.setCancelable(false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void setProgressDialog() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Working, please wait....");
        pDialog.setCancelable(false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void setActivityTitle(final String title, final String subTitle) {
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                if (title != null) {
                    getSupportActionBar().setTitle(title);
                } else {
                    getSupportActionBar().setTitle("");
                }
                if (subTitle != null) {
                    getSupportActionBar().setSubtitle(subTitle);
                }
            }
        });
    }


    protected void setUpBackBtn() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    protected CustomFontActivity getContext() {
        return this;
    }

    protected void showProgress() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    protected void dismissProgress() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    protected void setProgressText(String text) {
        pDialog.setMessage(text);
    }

    protected void showToast(final String text) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    protected void showConnectionErrorDialog(boolean close) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("Please try again.").setTitle("Connection Error");
        if (close) {
            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setCancelable(false);
        } else {
            builder.setPositiveButton("Close", null);
        }
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            builder.create().show();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    builder.create().show();
                }
            });
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
