package in.ripplr.ripplrdistribution.mvvm;

//import android.arch.lifecycle.ViewModel;
//import android.arch.lifecycle.ViewModelProvider;
//import android.support.annotation.NonNull;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import javax.inject.Inject;
import io.reactivex.annotations.NonNull;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
