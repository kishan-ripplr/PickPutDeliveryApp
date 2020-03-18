package in.ripplr.ripplrdistribution.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.adapters.PuttingStoreListAdapter;
import in.ripplr.ripplrdistribution.model.LoginResponse;
import in.ripplr.ripplrdistribution.model.PickResponse;
import in.ripplr.ripplrdistribution.model.PickSplitResponse;
import in.ripplr.ripplrdistribution.model.PutProductsResponse;
import in.ripplr.ripplrdistribution.model.StoreListModel;
import in.ripplr.ripplrdistribution.mvvm.ApiResponse;
import in.ripplr.ripplrdistribution.mvvm.MainViewModel;
import in.ripplr.ripplrdistribution.mvvm.TaskProgress;
import in.ripplr.ripplrdistribution.mvvm.ViewModelFactory;
import in.ripplr.ripplrdistribution.utils.AppUtils;
import in.ripplr.ripplrdistribution.utils.ManageSharedPreferences;
import in.ripplr.ripplrdistribution.utils.MyApplication;
import in.ripplr.ripplrdistribution.views.CrateActivity;
import in.ripplr.ripplrdistribution.views.LoginActivity;
import in.ripplr.ripplrdistribution.views.PickingTabActivity;

import static in.ripplr.ripplrdistribution.utils.CustomFontActivity.WORKING_PLEASE_WAIT;

public class PuttingStorePendingFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    MainViewModel viewModel;
    private List<StoreListModel> storeListModelList = new ArrayList<>();
    private PuttingStoreListAdapter mAdapter;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    ProgressDialog progressDialog;
    private PutProductsResponse putProductsResponse;

    int product_id;
    String product_nameStr;
    @BindView(R.id.product_name)
    TextView product_name;


    private ManageSharedPreferences manageSharedPreferences;
    private LoginResponse loginResponse;


    public PuttingStorePendingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_putting_store_list, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        ((MyApplication) getActivity().getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getAPIResponse().observe(getViewLifecycleOwner(), this::consumeResponse);

        manageSharedPreferences = ManageSharedPreferences.newInstance();
        loginResponse = AppUtils.convertJsonToLoginResponse(manageSharedPreferences.
                getString(getActivity().getApplicationContext(), ManageSharedPreferences.USER_INFO));

        mAdapter = new PuttingStoreListAdapter(storeListModelList, (item, v, pos) -> {
            Intent intent = new Intent(getActivity(), CrateActivity.class);
            intent.putExtra("putProductsResponse",putProductsResponse.getPut_products().get(pos));
            intent.putExtra("quantity",item.getQuantity());
            intent.putExtra("store_name",item.getStore_name());
            startActivityForResult(intent,2);
            //startActivity(intent);
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        Bundle b = getActivity().getIntent().getExtras();
        product_id=(int)b.get("product_id");
        product_nameStr=(String) b.get("product_name");
        product_name.setText(product_nameStr);

        getPutProducts(loginResponse.getAccess(), product_id);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPutProducts(loginResponse.getAccess(), product_id);
                swiperefreshlayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void renderSuccessResponse(Object response) {
        if (response instanceof PutProductsResponse) {
            putProductsResponse=(PutProductsResponse) response;
            storeListModelList.clear();
            ArrayList<PutProductsResponse.PutProducts> results = ((PutProductsResponse) response).getPut_products();
            for (PutProductsResponse.PutProducts result : results) {
                storeListModelList.add(new StoreListModel(result.getStore_name(), result.getOrdered_qty(), result.getOrder_id(), false));
            }
            mAdapter.notifyDataSetChanged();
        } else if (response instanceof PickSplitResponse) {
            ((PickingTabActivity) getActivity()).getPick(loginResponse.getAccess());
        }
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
                /*dismissProgress();
                showToast(apiResponse.message);
                showErrorDialog();*/
                break;

            case RELOGIN:
                dismissProgress();
                logout(getString(R.string.session_expired));
                break;

            case ERROR:
                dismissProgress();
                showErrorDialog("Something went wrong...!!");
                break;

            default:
                break;
        }
    }

    public void getPutProducts(String access_token, int product_id) {
        viewModel.getPutProducts(access_token, product_id);
    }

    private void showToast(final String text) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void logout(String message) {
        String json = AppUtils.convertObjectToJson(null);
        manageSharedPreferences.saveString(getActivity().getApplicationContext(), json,
                ManageSharedPreferences.USER_INFO);
        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
        showToast(message);
    }

    private void showErrorDialog(String message) {
        try {
            AppUtils.showCustomOkDialogCancelable(getActivity(),
                    getString(R.string.error),
                    message,
                    getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProgress() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void dismissProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void setProgressText(String text) {
        progressDialog.setMessage(text);
    }

    public List<StoreListModel> getStoreListModelList() {
        return storeListModelList;
    }

    public void setStoreListModelList(List<StoreListModel> storeListModelList) {
        this.storeListModelList = storeListModelList;
    }

    public SwipeRefreshLayout getSwiperefreshlayout() {
        return swiperefreshlayout;
    }

    public void setSwiperefreshlayout(SwipeRefreshLayout swiperefreshlayout) {
        this.swiperefreshlayout = swiperefreshlayout;
    }

    public PuttingStoreListAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(PuttingStoreListAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){
            getPutProducts(loginResponse.getAccess(), product_id);
        }
    }
}