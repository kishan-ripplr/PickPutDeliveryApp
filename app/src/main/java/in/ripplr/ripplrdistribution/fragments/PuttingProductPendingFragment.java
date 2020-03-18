package in.ripplr.ripplrdistribution.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import in.ripplr.ripplrdistribution.adapters.PuttingProductPendingListAdapter;
import in.ripplr.ripplrdistribution.model.LoginResponse;
import in.ripplr.ripplrdistribution.model.PickResponse;
import in.ripplr.ripplrdistribution.model.PickSplitResponse;
import in.ripplr.ripplrdistribution.model.PuttingProductListModel;
import in.ripplr.ripplrdistribution.mvvm.ApiResponse;
import in.ripplr.ripplrdistribution.mvvm.MainViewModel;
import in.ripplr.ripplrdistribution.mvvm.ViewModelFactory;
import in.ripplr.ripplrdistribution.utils.AppUtils;
import in.ripplr.ripplrdistribution.utils.ManageSharedPreferences;
import in.ripplr.ripplrdistribution.utils.MyApplication;
import in.ripplr.ripplrdistribution.views.LoginActivity;
import in.ripplr.ripplrdistribution.views.MainActivity;
import in.ripplr.ripplrdistribution.views.PuttingProductTabActivity;
import in.ripplr.ripplrdistribution.views.PuttingStoreTabActivity;

import static in.ripplr.ripplrdistribution.utils.CustomFontActivity.WORKING_PLEASE_WAIT;

public class PuttingProductPendingFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    MainViewModel viewModel;
    private List<PuttingProductListModel> puttingProductListModelList = new ArrayList<>();
    private PuttingProductPendingListAdapter mAdapter;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    ProgressDialog progressDialog;


    private ManageSharedPreferences manageSharedPreferences;
    private LoginResponse loginResponse;


    public PuttingProductPendingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_putting_pending_product_list, container, false);
        ButterKnife.bind(this, view);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        ((MyApplication) getActivity().getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getAPIResponse().observe(getViewLifecycleOwner(), this::consumeResponse);

        manageSharedPreferences = ManageSharedPreferences.newInstance();
        loginResponse = AppUtils.convertJsonToLoginResponse(manageSharedPreferences.
                getString(getActivity().getApplicationContext(), ManageSharedPreferences.USER_INFO));

        mAdapter = new PuttingProductPendingListAdapter(puttingProductListModelList,(item, view1) -> {
            Intent intent=new Intent(getActivity(), PuttingStoreTabActivity.class);
            intent.putExtra("product_id",item.getProduct_id());
            intent.putExtra("product_name",item.getProduct_name());
            startActivity(intent);
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        getPick(loginResponse.getAccess());
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPick(loginResponse.getAccess());
                swiperefreshlayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void renderSuccessResponse(Object response) {
        if (response instanceof PickResponse) {
            puttingProductListModelList.clear();
            ArrayList<PickResponse.Results> results = ((PickResponse) response).getResults();
            for (PickResponse.Results result : results) {

                int pick_split_count = result.getPick_splits().size();
                if (pick_split_count > 0) {
                    ArrayList<PickResponse.Results.PickSplits> pickSplits = result.getPick_splits();
                    String reason = pickSplits.get(pick_split_count - 1).getReason();
                    int pick_split_id = pickSplits.get(pick_split_count - 1).getId();
                    int pick_quantity = 0;
                    for (PickResponse.Results.PickSplits pickSplit : result.getPick_splits())
                        pick_quantity += pickSplit.getPick_quantity();
                    puttingProductListModelList.add(new PuttingProductListModel(result.getProduct(), result.getProduct_name(), "", pick_quantity, 0,true));
                }

            }
            mAdapter.notifyDataSetChanged();

        } else if (response instanceof PickSplitResponse) {
            //TODO remove card by pick_id
            ((PuttingProductTabActivity) getActivity()).getPick(loginResponse.getAccess());
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

    public void getPick(String access_token) {
        viewModel.getPick(access_token);
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

    public List<PuttingProductListModel> getputtingProductListModelList() {
        return puttingProductListModelList;
    }

    public void setputtingProductListModelList(List<PuttingProductListModel> puttingProductListModelList) {
        this.puttingProductListModelList = puttingProductListModelList;
    }

    public SwipeRefreshLayout getSwiperefreshlayout() {
        return swiperefreshlayout;
    }

    public void setSwiperefreshlayout(SwipeRefreshLayout swiperefreshlayout) {
        this.swiperefreshlayout = swiperefreshlayout;
    }

    public PuttingProductPendingListAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(PuttingProductPendingListAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }
}