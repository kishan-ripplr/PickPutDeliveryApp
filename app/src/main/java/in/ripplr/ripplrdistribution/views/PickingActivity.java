package in.ripplr.ripplrdistribution.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.adapters.PickingListAdapter;
import in.ripplr.ripplrdistribution.model.LoginResponse;
import in.ripplr.ripplrdistribution.model.PickingListModel;
import in.ripplr.ripplrdistribution.mvvm.ApiResponse;
import in.ripplr.ripplrdistribution.mvvm.MainViewModel;
import in.ripplr.ripplrdistribution.mvvm.ViewModelFactory;
import in.ripplr.ripplrdistribution.utils.AppUtils;
import in.ripplr.ripplrdistribution.utils.CustomFontActivity;
import in.ripplr.ripplrdistribution.utils.ManageSharedPreferences;
import in.ripplr.ripplrdistribution.utils.MyApplication;

public class PickingActivity extends CustomFontActivity {

    @Inject
    ViewModelFactory viewModelFactory;
    MainViewModel viewModel;
    private ManageSharedPreferences manageSharedPreferences;
    private LoginResponse loginResponse;
    @BindView(R.id.list_picking)
    RecyclerView list_picking;
    private List<PickingListModel> pickingListModelList = new ArrayList<>();
    private PickingListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_picking_list);

        ((MyApplication) getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getAPIResponse().observe(this, this::consumeResponse);
        ButterKnife.bind(this);
        manageSharedPreferences = ManageSharedPreferences.newInstance();

        loginResponse = AppUtils.convertJsonToLoginResponse(manageSharedPreferences.
                getString(getApplicationContext(), ManageSharedPreferences.USER_INFO));

        mAdapter = new PickingListAdapter(pickingListModelList, (item, view) -> {
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list_picking.setLayoutManager(mLayoutManager);
        list_picking.setItemAnimator(new DefaultItemAnimator());
        list_picking.setAdapter(mAdapter);

        /*pickingListModelList.add(new PickingListModel("TATA Salt 1KG Pack", 200, 200, ""));
        pickingListModelList.add(new PickingListModel("Chakhna Shot Jalapeno Twist ", 200, 0, ""));
        pickingListModelList.add(new PickingListModel("Product 1", 200, 150, ""));
        pickingListModelList.add(new PickingListModel("Product 2", 200, 200, ""));*/

        renderSuccessResponse(pickingListModelList);
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
                break;

            default:
                break;
        }
    }

    private void renderSuccessResponse(Object data) {

        mAdapter.notifyDataSetChanged();
    }

    private void showErrorDialog() {
        try {
            AppUtils.showCustomOkDialogCancelable(PickingActivity.this,
                    getString(R.string.connection_lost),
                    getString(R.string.check_internet_connection),
                    getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
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


}
