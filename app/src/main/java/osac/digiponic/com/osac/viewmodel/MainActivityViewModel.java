package osac.digiponic.com.osac.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import osac.digiponic.com.osac.model.DataItemMenu;
import osac.digiponic.com.osac.model.DataServiceType;
import osac.digiponic.com.osac.model.DataVehicle;
import osac.digiponic.com.osac.model.DataVehicleType;
import osac.digiponic.com.osac.repository.GeneralRepository;
import osac.digiponic.com.osac.repository.MenuRepository;
import osac.digiponic.com.osac.view.adapter.MenuRVAdapter;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<DataItemMenu>> mMenuData;
    private MutableLiveData<List<DataItemMenu>> mMenuDataWash;
    private MutableLiveData<List<DataItemMenu>> mMenuDataCare;
    private MutableLiveData<List<DataVehicleType>> mVehicleData;
    private MutableLiveData<List<DataServiceType>> mServiceData;
    private GeneralRepository generalRepository;
    private MenuRepository menuRepository;

    public void init(String jenis_kendaraan) {
        if (mMenuData != null || mServiceData != null || mVehicleData != null || mMenuDataWash != null || mMenuDataCare != null) {
            return;
        }
        menuRepository = MenuRepository.getInstance();
//        mMenuData = menuRepository.getData("", jenis_kendaraan);
        mMenuDataWash = menuRepository.getDataWash("29", jenis_kendaraan);
        mMenuDataCare = menuRepository.getDataCare("28", jenis_kendaraan);

        generalRepository = GeneralRepository.getInstance();
        mVehicleData = generalRepository.getVehicleData();
//        mServiceData = generalRepository.getServiceData();
    }

    public LiveData<List<DataItemMenu>> getmMenuData() {
        return mMenuData;
    }

    public LiveData<List<DataServiceType>> getmServiceData() {
        return mServiceData;
    }

    public LiveData<List<DataVehicleType>> getmVehicleData() {
        return mVehicleData;
    }

    public LiveData<List<DataItemMenu>> getmMenuDataWash() {
        return mMenuDataWash;
    }

    public LiveData<List<DataItemMenu>> getmMenuDataCare() {
        return mMenuDataCare;
    }

}
