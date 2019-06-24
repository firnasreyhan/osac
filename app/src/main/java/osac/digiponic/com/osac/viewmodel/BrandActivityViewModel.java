package osac.digiponic.com.osac.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import osac.digiponic.com.osac.model.DataBrand;
import osac.digiponic.com.osac.model.DataVehicle;
import osac.digiponic.com.osac.repository.BrandRepository;

public class BrandActivityViewModel extends ViewModel {

    private MutableLiveData<List<DataBrand>> mBrandData;
    private BrandRepository brandRepository;
    private MutableLiveData<List<DataVehicle>> mVehicleData;

    public void init() {
        if (mBrandData != null || brandRepository != null) {
            Log.d("brandsudahada", "brandsudahada");
            return;
        }

        brandRepository = BrandRepository.getInstance();
        brandRepository.initRetrofit();
        mBrandData = brandRepository.getDataBrand();
    }

    public LiveData<List<DataBrand>> getBrandData() {
        return mBrandData;
    }

}
