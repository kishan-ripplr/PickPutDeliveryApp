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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCallInterface {

    @POST("auth/token/")
    Observable<LoginResponse> login(@Body LoginRequest request);

    @POST("token/verify/")
    Observable<VerifyAuthResponse> verifyAuth(@Body VerifyAuthRequest request);

    @GET("pick/?pick_date=2020-03-17")
    Observable<PickResponse> getPick(@Header("Authorization") String authorization);

    @GET("put/")
    Observable<PutDateFilterResponse> getPutByDate(@Header("Authorization") String authorization, @Query("put_date") String date);

    @POST("pick_split/")
    Observable<PickSplitResponse> addPickSplit(@Header("Authorization") String authorization, @Body PickSplitRequest request);

    @PUT("pick_split/{id}/")
    Observable<PickSplitResponse> updatePickSplit(@Header("Authorization") String authorization, @Path("id") int id, @Body PickSplitRequest request);

    @GET("put_products/?order_date=2020-03-17")
    Observable<PutProductsResponse> getPutProducts(@Header("Authorization") String authorization, @Query("product_id") int product_id);

    @GET("crate/")
    Observable<CrateResponse> getCrate(@Header("Authorization") String authorization, @Query("name") String name);

    @PUT("put/{id}/")
    Observable<PutAddCrateResponse> mapUpdateCrate(@Header("Authorization") String authorization, @Path("id") int id, @Body PutRequest request);

    @POST("put/")
    Observable<PutDateFilterResponse> mapAddCrate(@Header("Authorization") String authorization, @Body PutRequest request);
}
