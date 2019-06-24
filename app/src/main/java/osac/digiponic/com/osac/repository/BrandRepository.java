package osac.digiponic.com.osac.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import osac.digiponic.com.osac.model.DataBrand;
import osac.digiponic.com.osac.model.DataItemMenu;
import osac.digiponic.com.osac.model.DataVehicle;
import osac.digiponic.com.osac.rest.ApiClient;
import osac.digiponic.com.osac.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static osac.digiponic.com.osac.view.ui.MainActivity.CONNECTION_TIMEOUT;
import static osac.digiponic.com.osac.view.ui.MainActivity.READ_TIMEOUT;

public class BrandRepository {

    private static BrandRepository instance;
    private ArrayList<DataBrand> dataSetBrand = new ArrayList<>();
    private ArrayList<DataVehicle> vehicleList = new ArrayList<>();

    // Retrofit
    private Retrofit retrofit;
    private ApiInterface apiInterface;

    public void initRetrofit() {
        retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static BrandRepository getInstance() {
        if (instance == null) {
            instance = new BrandRepository();
        }
        return instance;
    }

    public MutableLiveData<List<DataVehicle>> getDataVehicle(String brandID) {
        new Async_GetDataVehicle().execute(brandID);
        MutableLiveData<List<DataVehicle>> dataVehicle = new MutableLiveData<>();

        Log.d("isidatavehicleBrandID", brandID);
//        apiInterface.getVehicle(brandID).enqueue(new Callback<List<DataVehicle>>() {
//            @Override
//            public void onResponse(Call<List<DataVehicle>> call, Response<List<DataVehicle>> response) {
//                assert response.body() != null;
//                vehicleList.addAll(response.body());
//                for (DataVehicle vehicle : vehicleList) {
//                    Log.d("isidatavehicle", vehicle.getKeterangan());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DataVehicle>> call, Throwable t) {
//
//            }
//        });

        dataVehicle.setValue(vehicleList);
        return dataVehicle;
    }

    public MutableLiveData<List<DataBrand>> getDataBrand() {
        MutableLiveData<List<DataBrand>> dataBrand = new MutableLiveData<>();

//         Get Data
        apiInterface.getBrand().enqueue(new Callback<List<DataBrand>>() {
            @Override
            public void onResponse(Call<List<DataBrand>> call, Response<List<DataBrand>> response) {
                dataSetBrand.clear();
                if (response.body() != null) {
                    dataSetBrand.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<DataBrand>> call, Throwable t) {

            }
        });

        dataBrand.setValue(dataSetBrand);
        return dataBrand;
    }

    private class Async_GetDataVehicle extends AsyncTask<String, String, String> {

        // Variable
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            vehicleList.clear();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            String brandID = strings[0];

            try {
                url = new URL("http://app.digiponic.co.id/osac/apiosac/api/merek?id_merek=" + brandID);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {
                    //Read data sent from server
                    Log.d("ResponseCode", String.valueOf(response_code));
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    String resultFromServer = "";
                    JSONObject jsonObject = null;
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(result.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int jLoop = 0;
                    while (jLoop < jsonArray.length()) {
                        jsonObject = new JSONObject(jsonArray.get(jLoop).toString());
                        vehicleList.add(new DataVehicle(jsonObject.getString("id"), jsonObject.getString("kode_general"),
                                jsonObject.getString("kode"), jsonObject.getString("jenis_kendaraan"), jsonObject.getString("keterangan"), jsonObject.getString("gambar")));
                        jLoop += 1;
                    }
                } else {
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }


            return null;
        }
    }
}
