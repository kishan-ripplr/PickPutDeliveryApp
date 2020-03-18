package in.ripplr.ripplrdistribution.utils;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import in.ripplr.ripplrdistribution.mvvm.AppComponent;
import in.ripplr.ripplrdistribution.mvvm.DaggerAppComponent;

public class MyApplication extends MultiDexApplication {

	private static Context mContext = null;
	private static MyApplication mInstance;
	AppComponent appComponent;

	@Override
	public void onCreate() {
		super.onCreate();

		this.mContext = this;
		appComponent = DaggerAppComponent.builder().build();
		mInstance = this;
	}

	public static synchronized MyApplication getInstance() {
		return mInstance;
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	public static Context getAppContext(){
		return mContext;
	}

	public AppComponent getAppComponent() {
		return appComponent;
	}

}
