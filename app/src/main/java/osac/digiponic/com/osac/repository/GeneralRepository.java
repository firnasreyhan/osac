package osac.digiponic.com.osac.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

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

import osac.digiponic.com.osac.model.DataItemMenu;
import osac.digiponic.com.osac.model.DataServiceType;
import osac.digiponic.com.osac.model.DataVehicleType;

public class GeneralRepository {

    // Constraint
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;
    private static GeneralRepository instance;
    private ArrayList<DataServiceType> dataSetService = new ArrayList<>();
    private ArrayList<DataVehicleType> dataSetVehicle = new ArrayList<>();
    private MutableLiveData<List<DataServiceType>> dataService = new MutableLiveData<>();
    private MutableLiveData<List<DataVehicleType>> dataVehicle = new MutableLiveData<>();

    public static GeneralRepository getInstance() {
        if (instance == null) {
            instance = new GeneralRepository();
        }
        return instance;
    }

    public MutableLiveData<List<DataServiceType>> getServiceData() {
        new Async_GetDataGeneralServiceType().execute("types", "Service");
        dataService.setValue(dataSetService);
        return dataService;
    }

    public MutableLiveData<List<DataVehicleType>> getVehicleData() {
        new Async_GetDataGeneralVehicleType().execute("types", "Vehicle");
        dataVehicle.setValue(dataSetVehicle);
        return dataVehicle;
    }


    private class Async_GetDataGeneralServiceType extends AsyncTask<String, String, String> {

        // Variable
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // Background process, Fetching data from API
            String getBy = params[0];
            String value = params[1];
            try {
                url = new URL("http://app.digiponic.co.id/osac/api/public/generals?" + getBy + "=" + value);
                Log.d("ConenctionTest", "connected url : " + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("ConenctionTest", "error url");
                return "exception";
            }
            try {
                // Setup HttpURLConnection
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.connect();
                Log.d("ConenctionTest", "connected");
            } catch (IOException e1) {
                e1.printStackTrace();
                Log.d("ConenctionTest", "not connected");
                return e1.toString();
            }
            try {
                int response_code = conn.getResponseCode();

                // Check Response Code
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
                        Log.d("resultdariserver", result.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int jLoop = 0;
                    while (jLoop < jsonArray.length()) {
                        jsonObject = new JSONObject(jsonArray.get(jLoop).toString());
                        dataSetService.add(new DataServiceType(jsonObject.getString("id"),
                                jsonObject.getString("type"), jsonObject.getString("name"),
                                jsonObject.getString("desc")));
                        jLoop += 1;
                    }
                    return (resultFromServer);
                } else {
                    return ("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            setAdapterRV();
//            blackLayout.setVisibility(View.GONE);
//            dataFetched = true;
        }
    }

    private class Async_GetDataGeneralVehicleType extends AsyncTask<String, String, String> {

        // Variable
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // Background process, Fetching data from API
            String getBy = params[0];
            String value = params[1];
            try {
                url = new URL("http://app.digiponic.co.id/osac/api/public/generals?" + getBy + "=" + value);
                Log.d("ConenctionTest", "connected url : " + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("ConenctionTest", "error url");
                return "exception";
            }
            try {
                // Setup HttpURLConnection
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.connect();
                Log.d("ConenctionTest", "connected");
            } catch (IOException e1) {
                e1.printStackTrace();
                Log.d("ConenctionTest", "not connected");
                return e1.toString();
            }
            try {
                int response_code = conn.getResponseCode();

                // Check Response Code
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
                        Log.d("resultdariserver", result.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int jLoop = 0;
                    while (jLoop < jsonArray.length()) {
                        jsonObject = new JSONObject(jsonArray.get(jLoop).toString());
                        dataSetVehicle.add(new DataVehicleType(jsonObject.getString("id"),
                                jsonObject.getString("type"), jsonObject.getString("name"),
                                jsonObject.getString("desc")));
                        jLoop += 1;
                    }
                    return (resultFromServer);
                } else {
                    return ("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            setAdapterRV();
//            blackLayout.setVisibility(View.GONE);
//            dataFetched = true;
        }
    }


}
