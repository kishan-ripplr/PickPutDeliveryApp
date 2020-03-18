package in.ripplr.ripplrdistribution.mvvm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.ArrayList;

import in.ripplr.ripplrdistribution.model.LoginRequest;
import in.ripplr.ripplrdistribution.model.MapCrateRequest;
import in.ripplr.ripplrdistribution.model.PickRequest;
import in.ripplr.ripplrdistribution.model.PickSplitRequest;
import in.ripplr.ripplrdistribution.model.PutProductsResponse;
import in.ripplr.ripplrdistribution.model.PutRequest;
import in.ripplr.ripplrdistribution.model.VerifyAuthRequest;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();


    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ApiResponse> getAPIResponse() {
        return responseLiveData;
    }


    public void login(String username, String password) {

        LoginRequest request = new LoginRequest(username, password);

        disposables.add(repository.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result, "", 200,"")),
                        throwable -> responseLiveData.setValue(ApiResponse.error(
                                Status.ERROR,
                                ((HttpException) throwable).response().errorBody(),
                                throwable,
                                ((HttpException) throwable).message(),
                                ((HttpException) throwable).code()
                                )
                        )
                ));

    }

    public void verifyAuth(String token) {

        VerifyAuthRequest request = new VerifyAuthRequest(token);

        disposables.add(repository.verifyAuth(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result, null, 200,"")),
                        throwable -> {
                            HttpException httpException = (HttpException) throwable;
                            responseLiveData.setValue(ApiResponse.error(Status.ERROR, httpException.response().errorBody(),
                                    throwable, httpException.message(), httpException.code()));
                        }
                ));

    }

    public void getPick(String access_token) {

        disposables.add(repository.getPick(access_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result, null, 200,"")),
                        throwable -> {
                            HttpException httpException = (HttpException) throwable;
                            responseLiveData.setValue(ApiResponse.error(Status.ERROR, httpException.response().errorBody(),
                                    throwable, httpException.message(), httpException.code()));
                        }
                ));

    }

    public void addPickSplit(String access_token, int pick, int pick_split_id, int pick_quantity, String reason) {

        PickSplitRequest request = new PickSplitRequest(pick, pick_split_id, pick_quantity, reason);

        disposables.add(repository.addPickSplit(access_token, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result, null, 200,"")),
                        throwable -> {
                            HttpException httpException = (HttpException) throwable;
                            Status error = Status.ERROR;
                            if (httpException.code() == 403) {
                                error = Status.RELOGIN;
                            }
                            responseLiveData.setValue(ApiResponse.error(error, httpException.response().errorBody(),
                                    throwable, httpException.message(), httpException.code()));
                        }
                ));

    }

    public void updatePickSplit(String access_token, int pick, int id, int pick_quantity, String reason) {

        PickSplitRequest request = new PickSplitRequest(pick, id, pick_quantity, reason);

        disposables.add(repository.updatePickSplit(access_token, id, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result, null, 200,"")),
                        throwable -> {
                            HttpException httpException = (HttpException) throwable;
                            Status error = Status.ERROR;
                            if (httpException.code() == 403) {
                                error = Status.RELOGIN;
                            }
                            responseLiveData.setValue(ApiResponse.error(error, httpException.response().errorBody(),
                                    throwable, httpException.message(), httpException.code()));
                        }
                ));

    }

    public void getPutProducts(String access_token, int product_id) {

        disposables.add(repository.getPutProducts(access_token, product_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result, null, 200,"")),
                        throwable -> {
                            HttpException httpException = (HttpException) throwable;
                            Status error = Status.ERROR;
                            if (httpException.code() == 403) {
                                error = Status.RELOGIN;
                            }
                            responseLiveData.setValue(ApiResponse.error(error, httpException.response().errorBody(),
                                    throwable, httpException.message(), httpException.code()));
                        }
                ));

    }

    public void getCrate(String access_token, String crate_name) {

        disposables.add(repository.getCrate(access_token, crate_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result, null, 200,"")),
                        throwable -> {
                            HttpException httpException = (HttpException) throwable;
                            Status error = Status.ERROR;
                            if (httpException.code() == 403) {
                                error = Status.RELOGIN;
                            }
                            responseLiveData.setValue(ApiResponse.error(error, httpException.response().errorBody(),
                                    throwable, httpException.message(), httpException.code()));
                        }
                ));

    }

    public void mapUpdateCrate(String access_token, int put_id, String date, ArrayList<PutRequest.PutDetail> putDetails) {

        PutRequest request = new PutRequest(date, putDetails);
        disposables.add(repository.mapUpdateCrate(access_token, put_id, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result, null, 200,"")),
                        throwable -> {
                            HttpException httpException = (HttpException) throwable;
                            Status error = Status.ERROR;
                            if (httpException.code() == 403) {
                                error = Status.RELOGIN;
                            }
                            responseLiveData.setValue(ApiResponse.error(error, httpException.response().errorBody(),
                                    throwable, httpException.message(), httpException.code()));
                        }
                ));

    }

    public void mapAddCrate(String access_token, String date, ArrayList<PutRequest.PutDetail> putDetails) {
        PutRequest request = new PutRequest(date, putDetails);
        disposables.add(repository.mapAddCrate(access_token, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result, null, 200,"")),
                        throwable -> {
                            HttpException httpException = (HttpException) throwable;
                            Status error = Status.ERROR;
                            if (httpException.code() == 403) {
                                error = Status.RELOGIN;
                            }
                            responseLiveData.setValue(ApiResponse.error(error, httpException.response().errorBody(),
                                    throwable, httpException.message(), httpException.code()));
                        }
                ));

    }

    public void getPutByDate(String access_token, String date) {

        disposables.add(repository.getPutByDate(access_token, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result, null, 200,"")),
                        throwable -> {
                            HttpException httpException = (HttpException) throwable;
                            responseLiveData.setValue(ApiResponse.error(Status.ERROR, httpException.response().errorBody(),
                                    throwable, httpException.message(), httpException.code()));
                        }
                ));

    }

}
