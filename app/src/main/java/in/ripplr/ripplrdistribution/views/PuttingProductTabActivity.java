package in.ripplr.ripplrdistribution.views;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.fragments.PickkingCompletedFragment;
import in.ripplr.ripplrdistribution.fragments.PickkingPendingFragment;
import in.ripplr.ripplrdistribution.fragments.PuttingProductCompletedFragment;
import in.ripplr.ripplrdistribution.fragments.PuttingProductPendingFragment;
import in.ripplr.ripplrdistribution.model.LoginResponse;
import in.ripplr.ripplrdistribution.model.PickResponse;
import in.ripplr.ripplrdistribution.model.PickSplitResponse;
import in.ripplr.ripplrdistribution.model.PickingListModel;
import in.ripplr.ripplrdistribution.mvvm.ApiResponse;
import in.ripplr.ripplrdistribution.mvvm.MainViewModel;
import in.ripplr.ripplrdistribution.mvvm.ViewModelFactory;
import in.ripplr.ripplrdistribution.utils.AppUtils;
import in.ripplr.ripplrdistribution.utils.CustomFontActivity;
import in.ripplr.ripplrdistribution.utils.ManageSharedPreferences;
import in.ripplr.ripplrdistribution.utils.MyApplication;

public class PuttingProductTabActivity extends CustomFontActivity {

    @Inject
    ViewModelFactory viewModelFactory;
    MainViewModel viewModel;
    private ManageSharedPreferences manageSharedPreferences;
    private LoginResponse loginResponse;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabs;
    ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putting_product_tab);

        ((MyApplication) getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getAPIResponse().observe(this, this::consumeResponse);
        ButterKnife.bind(this);
        manageSharedPreferences = ManageSharedPreferences.newInstance();

        loginResponse = AppUtils.convertJsonToLoginResponse(manageSharedPreferences.
                getString(getApplicationContext(), ManageSharedPreferences.USER_INFO));
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PuttingProductPendingFragment(), "Pending");
        adapter.addFragment(new PuttingProductCompletedFragment(), "Completed");
        viewPager.setAdapter(adapter);

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        setUpUI();
        setUpBackBtn();
        getSupportActionBar().setTitle("Putting");

        //((PickkingPendingFragment) adapter.getFragment(0)).getListPicking();

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public Fragment getFragment(int index) {
            return mFragmentList.get(index);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
                dismissProgress();
                showToast(apiResponse.message);
                //showErrorDialog();
                break;

            case RELOGIN:
//                logout(getString(R.string.session_expired));
                break;

            case ERROR:
                dismissProgress();
                break;

            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response) {
        PuttingProductPendingFragment puttingProductPendingFragment=(PuttingProductPendingFragment) adapter.getFragment(0);
        PickkingCompletedFragment pickkingCompletedFragment=(PickkingCompletedFragment) adapter.getFragment(1);
        if(puttingProductPendingFragment.getSwiperefreshlayout().isRefreshing()){
            puttingProductPendingFragment.getSwiperefreshlayout().setRefreshing(false);
        }
        if(pickkingCompletedFragment.getSwiperefreshlayout().isRefreshing()){
            pickkingCompletedFragment.getSwiperefreshlayout().setRefreshing(false);
        }
        if (response instanceof PickResponse) {
            puttingProductPendingFragment.getputtingProductListModelList().clear();
            pickkingCompletedFragment.getPickingListModelList().clear();
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
                    pickkingCompletedFragment.getPickingListModelList().add(new PickingListModel(result.getId(), pick_split_id, result.getProduct_name(), result.getTotal_quantity(), pick_quantity, reason,true));
                }else{
                    //puttingProductPendingFragment.getputtingProductListModelList().add(new PickingListModel(result.getId(),0, result.getProduct_name(), result.getTotal_quantity(), result.getTotal_quantity(), "",false));
                }
            }

            puttingProductPendingFragment.getmAdapter().notifyDataSetChanged();
            pickkingCompletedFragment.getmAdapter().notifyDataSetChanged();
        } else if (response instanceof PickSplitResponse) {
            //TODO remove card by pick_id
            getPick(loginResponse.getAccess());
        }

        if(puttingProductPendingFragment.getProgressDialog().isShowing())
            puttingProductPendingFragment.getProgressDialog().hide();

        if(pickkingCompletedFragment.getProgressDialog().isShowing())
            pickkingCompletedFragment.getProgressDialog().hide();
    }

    public void addPickSplit(String access_token, int pick, int pick_split_id, int pick_quantity, String reason){
        viewModel.addPickSplit(access_token,pick,pick_split_id, pick_quantity, reason);
    }

    public void getPick(String access_token) {
        viewModel.getPick(access_token);
    }

}