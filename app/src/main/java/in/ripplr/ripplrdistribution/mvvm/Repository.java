package in.ripplr.ripplrdistribution.mvvm;

import in.ripplr.ripplrdistribution.model.CrateResponse;
import in.ripplr.ripplrdistribution.model.LoginRequest;
import in.ripplr.ripplrdistribution.model.LoginResponse;
import in.ripplr.ripplrdistribution.model.PickResponse;
import in.ripplr.ripplrdistribution.model.PickSplitRequest;
import in.ripplr.ripplrdistribution.model.PickSplitResponse;
import in.ripplr.ripplrdistribution.model.PutAddCrateResponse;
import in.ripplr.ripplrdistribution.model.PutProductsResponse;
import in.ripplr.ripplrdistribution.model.PutRequest;
import in.ripplr.ripplrdistribution.model.PutDateFilterResponse;
import in.ripplr.ripplrdistribution.model.VerifyAuthRequest;
import in.ripplr.ripplrdistribution.model.VerifyAuthResponse;
import io.reactivex.Observable;

public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    Observable<LoginResponse> login(LoginRequest request) {
        return apiCallInterface.login(request);
    }

    Observable<VerifyAuthResponse> verifyAuth(VerifyAuthRequest request) {
        return apiCallInterface.verifyAuth(request);
    }

    Observable<PickResponse> getPick(String access_token) {
        String authorization = "Bearer " + access_token;
        return apiCallInterface.getPick(authorization);
    }

    Observable<PutDateFilterResponse> getPutByDate(String access_token, String date) {
        String authorization = "Bearer " + access_token;
        return apiCallInterface.getPutByDate(authorization,date);
    }

    Observable<PickSplitResponse> addPickSplit(String access_token, PickSplitRequest request) {
        String authorization = "Bearer " + access_token;
        return apiCallInterface.addPickSplit(authorization, request);
    }

    Observable<PickSplitResponse> updatePickSplit(String access_token, int id, PickSplitRequest request) {
        String authorization = "Bearer " + access_token;
        return apiCallInterface.updatePickSplit(authorization, id, request);
    }

    Observable<PutProductsResponse> getPutProducts(String access_token,int product_id) {
        String authorization = "Bearer " + access_token;
        return apiCallInterface.getPutProducts(authorization,product_id);
    }

    Observable<CrateResponse> getCrate(String access_token, String crate_name) {
        String authorization = "Bearer " + access_token;
        return apiCallInterface.getCrate(authorization, crate_name);
    }

    Observable<PutAddCrateResponse> mapUpdateCrate(String access_token, int put_id, PutRequest request) {
        String authorization = "Bearer " + access_token;
        return apiCallInterface.mapUpdateCrate(authorization, put_id, request);
    }

    Observable<PutDateFilterResponse> mapAddCrate(String access_token, PutRequest request) {
        String authorization = "Bearer " + access_token;
        return apiCallInterface.mapAddCrate(authorization, request);
    }
}
