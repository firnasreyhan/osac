package osac.digiponic.com.osac.rest;

import java.util.List;

import osac.digiponic.com.osac.model.DataBrand;
import osac.digiponic.com.osac.model.DataMember;
import osac.digiponic.com.osac.model.DataVehicle;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/osac/apiosac/api/merek")
    Call<List<DataBrand>> getBrand();

    @GET("/osac/apiosac/api/merek")
    Call<List<DataVehicle>> getVehicle(@Query("id_merek") String id);

    @GET("/osac/apiosac/api/member")
    Call<List<DataMember>> getMember();

}
