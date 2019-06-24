package osac.digiponic.com.osac.repository;

import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import osac.digiponic.com.osac.model.DataMember;
import osac.digiponic.com.osac.rest.ApiClient;
import osac.digiponic.com.osac.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MemberRepository {

    private static MemberRepository instance;
    private ArrayList<DataMember> dataSetMember = new ArrayList<>();

    private Retrofit retrofit;
    private ApiInterface apiInterface;

    public void initRetrofit() {
        retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static MemberRepository getInstance() {
        if (instance == null) {
            instance = new MemberRepository();
        }
        return instance;
    }

    public MutableLiveData<List<DataMember>> getAllDataMember() {
        MutableLiveData<List<DataMember>> dataMember = new MutableLiveData<>();

        List<DataMember> dataSet = new ArrayList<>();
        dataSet.add(new DataMember("N", "Naufal", "naufal@digiponic.com", "081313131313"));
        dataSet.add(new DataMember("A", "Afif", "Afif@digiponic.com", "0813"));
        dataSet.add(new DataMember("", "Na", "naufal@digiponic.com", "0"));
        dataSet.add(new DataMember("NAAA", "NaufalAfifBUnyaminNaufalAfifBUnyamin", "naufal@digiponic.com", "012301230130103012031"));
        dataMember.setValue(dataSet);
//        apiInterface.getMember().enqueue(new Callback<List<DataMember>>() {
//            @Override
//            public void onResponse(Call<List<DataMember>> call, Response<List<DataMember>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<List<DataMember>> call, Throwable t) {
//
//            }
//        });

        return dataMember;
    }

}
