package osac.digiponic.com.osac.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import osac.digiponic.com.osac.model.DataVehicle;
import osac.digiponic.com.osac.repository.BrandRepository;

public class VehicleDialogViewModel extends ViewModel {

    private MutableLiveData<List<DataVehicle>> mVehicleData;
    private BrandRepository brandRepository;

    public void init(String brandID) {
        if (mVehicleData != null) {
            return;
        }

        brandRepository = BrandRepository.getInstance();
        brandRepository.initRetrofit();
        mVehicleData = brandRepository.getDataVehicle(brandID);
    }

    public LiveData<List<DataVehicle>> getVehicleData() {
        return mVehicleData;
    }

}
