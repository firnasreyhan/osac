package osac.digiponic.com.osac.view.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import osac.digiponic.com.osac.R;
import osac.digiponic.com.osac.model.DataItemMenu;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindView();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProcess();
            }
        });
    }

    private void bindView() {
        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_password);
        submit = findViewById(R.id.button_login);
    }

    private void loginProcess() {
        new HTTPAsyncTaskPOSTData().execute("http://app.digiponic.co.id/osac/apiosac/api/cabang");
    }

    public class HTTPAsyncTaskPOSTData extends AsyncTask<String, Void, String> {
        private boolean loggedIn = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return HttpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (loggedIn) {
                Intent toPayment = new Intent(LoginActivity.this, BrandSelection.class);
                startActivity(toPayment);
            } else {
                Toast.makeText(LoginActivity.this, "Email atau password Salah", Toast.LENGTH_SHORT).show();
            }

        }

        private String HttpPost(String myUrl) throws IOException, JSONException {
            String result = "";
            URL url = new URL(myUrl);

            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // 2. build JSON object
            JSONObject jsonObject = createJSON();

            // 3. add JSON content to POST request body
            setPostRequestContent(conn, jsonObject);

            // 4. make POST request to the given URL
            conn.connect();

            Log.d("responseCodePost", String.valueOf(conn.getResponseCode()));

            // Getting Response
            try {
                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read Response

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder resultPost = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        resultPost.append(line);
                    }
                    String resultFromServer = "";
                    JSONObject jsonObjectPostResponse = null;
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(resultPost.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int jLoop = 0;
                    while (jLoop < jsonArray.length()) {
                        jsonObjectPostResponse = new JSONObject(jsonArray.get(jLoop).toString());
                        Log.d("EXPORTJSONRESPONSE", jsonObjectPostResponse.toString());
                        loggedIn = true;
                        jLoop += 1;
                    }
                }
                return ("success");
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

        private void setPostRequestContent(HttpURLConnection conn,
                                           JSONObject jsonObject) throws IOException {
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            Log.i(MainActivity.class.toString(), jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();
        }
    }

    private JSONObject createJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            // Add Property
            jsonObject.accumulate("email", username.getText().toString());
            jsonObject.accumulate("pin", password.getText().toString());

            Log.d("EXPORTJSON", jsonObject.toString());
        } catch (
                JsonIOException e) {
            Log.d("JSONERROREX", e.toString());
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
