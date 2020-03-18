package in.ripplr.ripplrdistribution.views;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.ripplr.ripplrdistribution.R;
import in.ripplr.ripplrdistribution.adapters.CrateListAdapter;
import in.ripplr.ripplrdistribution.fragments.PuttingStorePendingFragment;
import in.ripplr.ripplrdistribution.model.CrateListModel;
import in.ripplr.ripplrdistribution.model.CrateResponse;
import in.ripplr.ripplrdistribution.model.LoginResponse;
import in.ripplr.ripplrdistribution.model.PutProductsResponse;
import in.ripplr.ripplrdistribution.model.PutRequest;
import in.ripplr.ripplrdistribution.model.PutDateFilterResponse;
import in.ripplr.ripplrdistribution.mvvm.ApiResponse;
import in.ripplr.ripplrdistribution.mvvm.MainViewModel;
import in.ripplr.ripplrdistribution.mvvm.TaskProgress;
import in.ripplr.ripplrdistribution.mvvm.ViewModelFactory;
import in.ripplr.ripplrdistribution.utils.AppUtils;
import in.ripplr.ripplrdistribution.utils.CustomFontActivity;
import in.ripplr.ripplrdistribution.utils.ManageSharedPreferences;
import in.ripplr.ripplrdistribution.utils.MyApplication;

public class CrateActivity extends CustomFontActivity {
    @Inject
    ViewModelFactory viewModelFactory;
    MainViewModel viewModel;
    private ManageSharedPreferences manageSharedPreferences;
    private LoginResponse loginResponse;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.store_name)
    TextView store_name;
    @BindView(R.id.quantity)
    TextView quantity;
    @BindView(R.id.finish_btn)
    Button finish_btn;
    private List<CrateListModel> crateListModelList = new ArrayList<>();
    private CrateListAdapter mAdapter;
    int total_qty;
    private Menu mOptionsMenu;
    SearchView searchView;
    String searchTxt = "";
    String store_nameStr = "";
    int put_id;
    PutProductsResponse.PutProducts put_products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crate);
        ((MyApplication) getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getAPIResponse().observe(this, this::consumeResponse);
        ButterKnife.bind(this);
        manageSharedPreferences = ManageSharedPreferences.newInstance();

        loginResponse = AppUtils.convertJsonToLoginResponse(manageSharedPreferences.
                getString(getApplicationContext(), ManageSharedPreferences.USER_INFO));

        put_products = getIntent().getExtras().getParcelable("putProductsResponse");
        //viewModel.getPutByDate(loginResponse.getAccess(), "2020-03-17");

        mAdapter = new CrateListAdapter(crateListModelList, (item, view, pos) -> {
            if (view.getId() == R.id.close_btn) {
                searchTxt = "";
                crateListModelList.remove(pos);
                mAdapter.notifyDataSetChanged();
            } else if (view.getId() == R.id.submit) {
                try {
                    //viewModel.mapUpdateCrate(loginResponse.getAccess(), put_id, put_products.getOrder_id(), put_products.getProduct_id(), Integer.parseInt(item.getCrate_num()), item.getPut_quantity(), item.getReason());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(mAdapter);

        //crateListModelList.add(new CrateListModel("1123",120,30,"sdfsd"));

        setUpUI();
        setUpBackBtn();
        setActivityTitle("Putting", "Crate");

        Bundle b = getIntent().getExtras();
        store_nameStr = (String) b.get("store_name");
        total_qty = (int) b.get("quantity");
        store_name.setText(store_nameStr);
        quantity.setText("" + total_qty);

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total_crates = crateListModelList.size();
                int total_put_qty = 0;
                ArrayList<PutRequest.PutDetail> putDetails = new ArrayList<>();
                if (total_crates > 0) {

                    for (CrateListModel crateListModel : crateListModelList) {
                        putDetails.add(new PutRequest.PutDetail(put_products.getOrder_id(), put_products.getProduct_id(), Integer.parseInt(crateListModel.getCrate_num()), crateListModel.getPut_quantity(), crateListModel.getReason()));
                        total_put_qty += crateListModel.getPut_quantity();
                    }

                    if (total_put_qty <= total_qty) {
                        String date = "2020-03-17";
                        viewModel.mapAddCrate(loginResponse.getAccess(), date, putDetails);
                    }
                } else {
                    Toast.makeText(CrateActivity.this, "Please add at least one crate", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        mOptionsMenu = menu;
        inflater.inflate(R.menu.options_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        MenuItem menuSearch = mOptionsMenu.findItem(R.id.search);
        menuSearch.expandActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                if (!isCrateNameExists(query) && !isCrateQuantityExceed()) {
                    viewModel.getCrate(loginResponse.getAccess(), query);
                } else {
                    //Toast.makeText(CrateActivity.this, "Put Quantity can not more than total quantity", Toast.LENGTH_SHORT).show();
                }

                searchTxt = query;

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return true;

            }

        });

        return true;
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                Log.e("loading", "loading");
                /*setProgressText(WORKING_PLEASE_WAIT);
                showProgress();*/
                break;

            case SUCCESS:
                //dismissProgress();
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
//                logout(getString(R.string.session_expired));
                break;

            case ERROR:
                if (apiResponse.errorCode == 400) {
                    try {
                        JSONObject responseObject = new JSONObject(apiResponse.errorMessage);
                        JSONArray put_dateArray = responseObject.getJSONArray("put_date");
                        String error_msg = (String) put_dateArray.get(0);
                        if (error_msg.equals("Put with this put date already exists.")) {
                            viewModel.getPutByDate(loginResponse.getAccess(), "2020-03-17");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                dismissProgress();
                break;

            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response) {
        if (response instanceof PutDateFilterResponse) {
            put_id = ((PutDateFilterResponse) response).getResults().get(0).getId();
            int total_crates = crateListModelList.size();
            int total_put_qty = 0;
            ArrayList<PutRequest.PutDetail> putDetails = new ArrayList<>();
            if (total_crates > 0) {

                for (CrateListModel crateListModel : crateListModelList) {
                    putDetails.add(new PutRequest.PutDetail(this.put_products.getOrder_id(), put_products.getProduct_id(), Integer.parseInt(crateListModel.getCrate_num()), crateListModel.getPut_quantity(), crateListModel.getReason()));
                    total_put_qty += crateListModel.getPut_quantity();
                }
            }
            if (total_put_qty <= total_qty) {
                String date = "2020-03-17";
                viewModel.mapUpdateCrate(loginResponse.getAccess(), put_id, date, putDetails);

            }
        } else if (response instanceof CrateResponse) {
            ArrayList<CrateResponse.Results> results = ((CrateResponse) response).getResults();
            int total_put_qty = 0;
            int total_crates = crateListModelList.size();
            if (total_crates > 0) {
                for (CrateListModel crateListModel : crateListModelList) {
                    total_put_qty += crateListModel.getPut_quantity();
                }
            }
            if (results != null && results.size() > 0) {
                CrateResponse.Results result = results.get(0);
                if (result.getId() != 0) {
                    crateListModelList.add(new CrateListModel("" + result.getId(), result.getName(), total_qty, total_qty - total_put_qty, "", result.getColour_code()));
                }

                mAdapter.notifyDataSetChanged();
                //searchView.setIconified(true);
                searchView.clearFocus();
                MenuItem menuSearch = mOptionsMenu.findItem(R.id.search);
                menuSearch.collapseActionView();
            } else {
                Toast.makeText(this, "The crate is not available", Toast.LENGTH_SHORT).show();
            }

        } else {
            Intent intent=new Intent();
            setResult(2,intent);
            finish();
        }
    }

    private boolean isCrateQuantityExceed() {
        int total_crates = crateListModelList.size();
        int total_put_qty = 0;
        if (total_crates > 0) {
            for (CrateListModel crateListModel : crateListModelList) {
                total_put_qty += crateListModel.getPut_quantity();
            }
        }
        if (total_put_qty >= total_qty) {
            return true;
        }
        return false;
    }

    private boolean isCrateNameExists(String crate_name) {
        int total_crates = crateListModelList.size();

        if (total_crates > 0)
            for (CrateListModel crateListModel : crateListModelList)
                if (crate_name.equals(crateListModel.getCrate_name())) return true;

        return false;
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }*/
}
