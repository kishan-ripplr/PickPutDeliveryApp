package in.ripplr.ripplrdistribution.views;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.adapters.DrawerListAdapter;
import in.ripplr.ripplrdistribution.model.DrawerListModel;
import in.ripplr.ripplrdistribution.model.LoginResponse;
import in.ripplr.ripplrdistribution.mvvm.ApiResponse;
import in.ripplr.ripplrdistribution.mvvm.MainViewModel;
import in.ripplr.ripplrdistribution.mvvm.ViewModelFactory;
import in.ripplr.ripplrdistribution.utils.AppUtils;
import in.ripplr.ripplrdistribution.utils.CustomFontActivity;
import in.ripplr.ripplrdistribution.utils.ManageSharedPreferences;
import in.ripplr.ripplrdistribution.utils.MyApplication;
import in.ripplr.ripplrdistribution.utils.NavigationDrawerUtils;

public class MainActivity extends CustomFontActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    ViewModelFactory viewModelFactory;
    MainViewModel viewModel;
    private ManageSharedPreferences manageSharedPreferences;
    private LoginResponse loginResponse;
    private List<DrawerListModel> mDrawerListModelList = new ArrayList<>();
    private DrawerListAdapter mDrawerListAdapter = null;

    @BindView(R.id.img_menu)
    ImageView img_menu;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.list_drawer)
    ListView list_drawer;

    @BindView(R.id.txt_navi_user_name)
    TextView txt_navi_user_name;

    @BindView(R.id.txt_navi_version_number)
    TextView txt_navi_version_number;

    @BindView(R.id.picking)
    MaterialCardView picking;

    @BindView(R.id.putting)
    MaterialCardView putting;

    @BindView(R.id.delivery)
    MaterialCardView delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MyApplication) getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getAPIResponse().observe(this, this::consumeResponse);
        ButterKnife.bind(this);
        manageSharedPreferences = ManageSharedPreferences.newInstance();

        loginResponse = AppUtils.convertJsonToLoginResponse(manageSharedPreferences.
                getString(getApplicationContext(),ManageSharedPreferences.USER_INFO));

        txt_navi_user_name.setText("");
        PackageInfo pinfo = null;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        txt_navi_version_number.setText("v " + pinfo.versionName);

        img_menu.setOnClickListener(this);
        list_drawer.setOnItemClickListener(this);
        picking.setOnClickListener(this);
        putting.setOnClickListener(this);
        delivery.setOnClickListener(this);

        setUpDrawerList();
    }

    void setUpDrawerList() {
        final NavigationDrawerUtils navigationDrawerUtils = new NavigationDrawerUtils(this);
        mDrawerListModelList = navigationDrawerUtils.getDrawerListModel();
        mDrawerListAdapter = new DrawerListAdapter(MainActivity.this, mDrawerListModelList);
        list_drawer.setAdapter(mDrawerListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_menu:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.picking:
                //ToDo
                Intent intent=new Intent(this,PickingTabActivity.class);
                startActivity(intent);
                break;

            case R.id.putting:
                //ToDo
                //intent putting
                Intent intent1=new Intent(this,PuttingProductTabActivity.class);
                startActivity(intent1);
                break;

            case R.id.delivery:
                //ToDo
                //intent delivery
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {

            case R.id.list_drawer:
                //Closing drawer on item click
                mDrawerLayout.closeDrawers();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeDrawerFragment(position);
                    }
                }, 100);
                break;
        }
    }

    void changeDrawerFragment(int fragment_type) {
        switch (fragment_type) {
            /*case NavigationDrawerUtils.USER_PROFILE:
                break;
*/
            case NavigationDrawerUtils.LOGOUT:
                logout(getString(R.string.logged_out));
                break;
        }
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                Log.e("loading","loading");
                setProgressText(WORKING_PLEASE_WAIT);
                showProgress();
                break;

            case SUCCESS:
                dismissProgress();
                renderSuccessResponse(apiResponse.data);
                Log.e("loading","success");
//                Toast.makeText(getApplicationContext(),apiResponse.message,Toast.LENGTH_SHORT).show();
                break;

            case FAILURE:
                dismissProgress();
                showToast(apiResponse.message);
                showErrorDialog();
                break;

            case RELOGIN:
                logout(getString(R.string.session_expired));
                break;

            case ERROR:
                dismissProgress();
                logout(getString(R.string.session_expired));
                break;

            default:
                break;
        }
    }

    private void logout(String message) {
        String json = AppUtils.convertObjectToJson(null);
        manageSharedPreferences.saveString(getApplicationContext(), json,
                ManageSharedPreferences.USER_INFO);
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        showToast(message);

    }

    private void renderSuccessResponse(Object data) {

    }

    private void showErrorDialog() {
        try {
            AppUtils.showCustomOkDialogCancelable(MainActivity.this,
                    getString(R.string.connection_lost),
                    getString(R.string.check_internet_connection),
                    getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
