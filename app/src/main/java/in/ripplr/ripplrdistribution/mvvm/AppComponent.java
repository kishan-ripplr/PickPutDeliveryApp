package in.ripplr.ripplrdistribution.mvvm;

import in.ripplr.ripplrdistribution.fragments.PickkingCompletedFragment;
import in.ripplr.ripplrdistribution.fragments.PickkingPendingFragment;
import in.ripplr.ripplrdistribution.fragments.PuttingProductCompletedFragment;
import in.ripplr.ripplrdistribution.fragments.PuttingProductPendingFragment;
import in.ripplr.ripplrdistribution.fragments.PuttingStorePendingFragment;
import in.ripplr.ripplrdistribution.views.CrateActivity;
import in.ripplr.ripplrdistribution.views.LoginActivity;
import javax.inject.Singleton;
import dagger.Component;
import in.ripplr.ripplrdistribution.views.MainActivity;
import in.ripplr.ripplrdistribution.views.PickingActivity;
import in.ripplr.ripplrdistribution.views.PickingTabActivity;
import in.ripplr.ripplrdistribution.views.PuttingProductTabActivity;
import in.ripplr.ripplrdistribution.views.PuttingStoreTabActivity;
import in.ripplr.ripplrdistribution.views.SplashScreen_Activity;

@Component(modules = {AppModule.class, BaseServiceModule.class})
@Singleton
public interface AppComponent {

    void doInjection(LoginActivity loginActivity);
    void doInjection(SplashScreen_Activity splashScreenActivity);
    void doInjection(MainActivity mainActivity);
    void doInjection(PickingActivity pickingActivity);
    void doInjection(PickingTabActivity pickingTabActivity);
    void doInjection(PickkingPendingFragment pickkingPendingFragment);
    void doInjection(PickkingCompletedFragment pickkingCompletedFragment);
    void doInjection(CrateActivity crateActivity);
    void doInjection(PuttingProductTabActivity puttingProductTabActivity);
    void doInjection(PuttingProductPendingFragment puttingProductPendingFragment);
    void doInjection(PuttingProductCompletedFragment puttingProductCompletedFragment);
    void doInjection(PuttingStoreTabActivity puttingStoreTabActivity);
    void doInjection(PuttingStorePendingFragment puttingStorePendingFragment);


}
