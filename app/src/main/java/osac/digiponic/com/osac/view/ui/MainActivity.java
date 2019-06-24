package osac.digiponic.com.osac.view.ui;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import osac.digiponic.com.osac.R;
import osac.digiponic.com.osac.helper.DatabaseHelper;
import osac.digiponic.com.osac.model.DataBluetoothDevice;
import osac.digiponic.com.osac.print.DeviceActivity;
import osac.digiponic.com.osac.view.adapter.InvoiceRVAdapter;
import osac.digiponic.com.osac.view.adapter.MenuRVAdapter;
import osac.digiponic.com.osac.model.DataItemMenu;
import osac.digiponic.com.osac.model.DataServiceType;
import osac.digiponic.com.osac.model.DataVehicleType;
import osac.digiponic.com.osac.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements MenuRVAdapter.ItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    // Dataset
    private List<DataItemMenu> mDataItem = new ArrayList<>();
    public static List<DataItemMenu> mDataCart = new ArrayList<>();
    private List<DataItemMenu> dataCarWash = new ArrayList<>();
    private List<DataItemMenu> dataCarCare = new ArrayList<>();
    private List<DataVehicleType> mDataVehicleType = new ArrayList<>();
    private List<DataServiceType> mDataServiceType = new ArrayList<>();

    // View Model
    private MainActivityViewModel mMainActivityViewModel;

    // Content
    private RecyclerView recyclerView_Menu, recyclerView_carWash, recyclerView_carCare, recyclerView_Invoice;
    private ImageView emptyCart;
    private TextView total_tv, date_tv;
    private Dialog completeDialog, incompleteDialog, changeTypeDialog;
    private Button checkOutBtn, pelangganBtn;

    // Adapter
    private MenuRVAdapter menuRVAdapter;
    private MenuRVAdapter carWashRVAdapter;
    private MenuRVAdapter carCareRVAdapter;
    private InvoiceRVAdapter invoiceRVAdapter;

    // Variable
    public static int total = 0;

    // Variable Global
    public static String POLICE_NUMBER = "N123ABC";
    public static String VEHICLE_TYPE;
    public static String BRAND;
    public static String VEHICLE_NAME;
    public static String DISCOUNT_TYPE = "Nominal";
    public static int DISCOUNT = 0;
    public static int TOTALPRICE = total;

    // Constraint
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private ShimmerRecyclerView recommended_shimmer;
    private ShimmerRecyclerView carWash_shimmer;
    private ShimmerRecyclerView carCare_shimmer;

    // Bluetooth Printer
    public static final int RC_BLUETOOTH = 0;
    public static final int RC_CONNECT_DEVICE = 1;
    public static final int RC_ENABLE_BLUETOOTH = 2;

    public static String printerMacAddress;

    private DatabaseHelper db;
    private List<DataBluetoothDevice> device_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lock Screen to Horizontal
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recommended_shimmer = findViewById(R.id.recommended_shimmer_recyclerView);
        carWash_shimmer = findViewById(R.id.car_wash_shimmer_recyclerView);
        carCare_shimmer = findViewById(R.id.car_care_shimmer_recyclerView);

        recommended_shimmer.showShimmerAdapter();
        carWash_shimmer.showShimmerAdapter();
        carCare_shimmer.showShimmerAdapter();

        db = new DatabaseHelper(this);
        device_list.addAll(db.getAllDataDevice());
        if (!device_list.isEmpty()) {
            printerMacAddress = device_list.get(0).getMacAddress();
        }

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            VEHICLE_TYPE = null;
            BRAND = null;
            VEHICLE_NAME = null;
        } else {
            VEHICLE_TYPE = extras.getString("VEHICLE_TYPE");
            BRAND = extras.getString("BRAND");
            VEHICLE_NAME = extras.getString("VEHICLE_NAME");
        }

        // Initialize View Model
        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mMainActivityViewModel.init(VEHICLE_TYPE);

        // set Adapter
        setAdapterRV();

        // Initialize TextView Total
        total_tv = findViewById(R.id.total_textview_main);
        total_tv.setText("Rp. 0");

        // Initialize TextView Date
        date_tv = findViewById(R.id.date);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(date);
        date_tv.setText(formattedDate);

        // Setup Dialog
        completeDialog = new Dialog(this);
        setupCompleteDialog();
        incompleteDialog = new Dialog(this);
        setupUnCompleteDialog();
        changeTypeDialog = new Dialog(this);
        setupChangeTypeDialog();

        // Initialize Invoice Recyclerview Placeholder
        emptyCart = findViewById(R.id.img_emptyCart);

        // Setup Hidden Function
        pelangganBtn = findViewById(R.id.button_invoice);
        pelangganBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMember = new Intent(MainActivity.this, MemberActivity.class);
                startActivity(toMember);
            }
        });

        // Set Checkout Button
        checkOutBtn = findViewById(R.id.btn_checkout);
        checkOutBtn.setOnClickListener(v -> {
            if (mDataCart.size() == 0) {
                incompleteDialog.show();
            } else {
                Intent toPayment = new Intent(MainActivity.this, PoliceNumberInput.class);
                startActivity(toPayment);
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (invoiceRVAdapter != null) {
            invoiceRVAdapter.notifyDataSetChanged();
        }
        if (carCareRVAdapter != null) {
            carCareRVAdapter.notifyDataSetChanged();
        }
        if (carWashRVAdapter != null) {
            carWashRVAdapter.notifyDataSetChanged();
        }
    }

    private void toSetting() {
        startActivityForResult(new Intent(this, DeviceActivity.class), RC_CONNECT_DEVICE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            intentToBrand();
        }
    }

    //==========================================================================================================================

    @Override
    public void onItemClick(View view, int position) {
        Log.d("itempositiondebug", String.valueOf(position));
        Log.d("itempositionname", String.valueOf(menuRVAdapter.getItemName(position) + menuRVAdapter.getItemPrice(position)));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        total = 0;
        if (!menuRVAdapter.isSelected(position)) {
            mDataCart.add(new DataItemMenu(menuRVAdapter.getItemID(position), menuRVAdapter.getItemName(position),
                    menuRVAdapter.getItemPrice(position), menuRVAdapter.getItemVehicleType(position),
                    menuRVAdapter.getItemType(position), menuRVAdapter.getItemImage(position)));
            invoiceRVAdapter.notifyDataSetChanged();
            menuRVAdapter.setSelected(position, true);
            menuRVAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < mDataCart.size(); i++) {
                if (mDataCart.get(i).get_itemName().equalsIgnoreCase(menuRVAdapter.getItemName(position))) {
                    invoiceRVAdapter.removeAt(i);
                    invoiceRVAdapter.notifyItemRemoved(i);
                    menuRVAdapter.setSelected(position, false);
                    menuRVAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
        Log.d("datacartsizeadd", String.valueOf(mDataCart.size()));

        for (DataItemMenu item : mDataCart) {
            total += item.get_itemPrice();
        }
        if (total > 0) {
            total_tv.setText(formatRupiah.format((double) total));
        }
    }

    @Override
    public void onCarWashItemClick(View view, int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        total = 0;
        if (!carWashRVAdapter.isSelected(position)) {
            mDataCart.add(new DataItemMenu(carWashRVAdapter.getItemID(position), carWashRVAdapter.getItemName(position),
                    carWashRVAdapter.getItemPrice(position), carWashRVAdapter.getItemVehicleType(position),
                    carWashRVAdapter.getItemType(position), carWashRVAdapter.getItemImage(position)));
            invoiceRVAdapter.notifyDataSetChanged();
            carWashRVAdapter.setSelected(position, true);
            carWashRVAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < mDataCart.size(); i++) {
                if (mDataCart.get(i).get_itemName().equalsIgnoreCase(carWashRVAdapter.getItemName(position))) {
                    invoiceRVAdapter.removeAt(i);
                    invoiceRVAdapter.notifyItemRemoved(i);
                    carWashRVAdapter.setSelected(position, false);
                    carWashRVAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
        for (DataItemMenu item : mDataCart) {
            total += item.get_itemPrice();
        }
        if (total > 0) {
            total_tv.setText(formatRupiah.format((double) total));
        }
    }

    @Override
    public void onCarCareItemClick(View view, int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        total = 0;
        if (!carCareRVAdapter.isSelected(position)) {
            mDataCart.add(new DataItemMenu(carCareRVAdapter.getItemID(position), carCareRVAdapter.getItemName(position),
                    carCareRVAdapter.getItemPrice(position), carCareRVAdapter.getItemVehicleType(position),
                    carCareRVAdapter.getItemType(position), carCareRVAdapter.getItemImage(position)));
            invoiceRVAdapter.notifyDataSetChanged();
            carCareRVAdapter.setSelected(position, true);
            carCareRVAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < mDataCart.size(); i++) {
                if (mDataCart.get(i).get_itemName().equalsIgnoreCase(carCareRVAdapter.getItemName(position))) {
                    invoiceRVAdapter.removeAt(i);
                    invoiceRVAdapter.notifyItemRemoved(i);
                    carCareRVAdapter.setSelected(position, false);
                    carCareRVAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
        for (DataItemMenu item : mDataCart) {
            total += item.get_itemPrice();
        }
        if (total > 0) {
            total_tv.setText(formatRupiah.format((double) total));
        }
    }

    private void setAdapterRV() {
        // Setup Menu Recyclerview
        recyclerView_Menu = findViewById(R.id.rv_recommended);
        recyclerView_Menu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        menuRVAdapter = new MenuRVAdapter(this, new ArrayList<>());
        menuRVAdapter.setClickListener(this);

        recyclerView_carWash = findViewById(R.id.rv_car_wash);
        recyclerView_carWash.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        carWashRVAdapter = new MenuRVAdapter(this, mMainActivityViewModel.getmMenuDataCare().getValue());
        carWashRVAdapter.setCarWashIitemClickListener(this);

        recyclerView_carCare = findViewById(R.id.rv_car_care);
        recyclerView_carCare.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        carCareRVAdapter = new MenuRVAdapter(this, mMainActivityViewModel.getmMenuDataWash().getValue());
        carCareRVAdapter.setCarCareItemClickListener(this);

        // Load Data to UI
        loadDataRV();

        // Setup Invoice Recyclerview
        recyclerView_Invoice = findViewById(R.id.rv_invoiceItem);
        recyclerView_Invoice.setLayoutManager(new LinearLayoutManager(this));
        invoiceRVAdapter = new InvoiceRVAdapter(this, mDataCart);
        invoiceRVAdapter.setClickListener(this);
        invoiceRVAdapter.notifyDataSetChanged();
        recyclerView_Invoice.setAdapter(invoiceRVAdapter);
        invoiceRVAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkEmpty();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkEmpty();
            }
        });
    }

    private void loadDataRV() {
        new Handler().postDelayed(() -> {
            if (carWashRVAdapter.getItemCount() > 0 && carCareRVAdapter.getItemCount() > 0) {
                // Remove Shimmer
                recommended_shimmer.hideShimmerAdapter();
                carWash_shimmer.hideShimmerAdapter();
                carCare_shimmer.hideShimmerAdapter();

                recyclerView_Menu.setAdapter(menuRVAdapter);
                menuRVAdapter.notifyDataSetChanged();
                recyclerView_carWash.setAdapter(carWashRVAdapter);
                carWashRVAdapter.notifyDataSetChanged();
                recyclerView_carCare.setAdapter(carCareRVAdapter);
                carCareRVAdapter.notifyDataSetChanged();
            } else {
                loadDataRV();
            }
        }, 1000);
    }

    // Post Data
    public class HTTPAsyncTaskPOSTData extends AsyncTask<String, Void, String> {
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
            Intent toPayment = new Intent(MainActivity.this, PaymentActivity.class);
            toPayment.putExtra("TOTAL", total);
            startActivity(toPayment);
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
                        jsonArray = new JSONArray(result.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int jLoop = 0;
                    while (jLoop < jsonArray.length()) {
                        jsonObjectPostResponse = new JSONObject(jsonArray.get(jLoop).toString());

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

        // Get IDs
        int typesID = 0, serviceID = 0;
        String nama;


        int total_price = total;
        int discount = 0;
        if (mDataCart.size() > 0) {
            for (DataItemMenu item : mDataCart) {
                total_price += item.get_itemPrice();
            }
        }

        try {
            // Data Masih Hardcoded
            String jenisKendaraan = null;
            switch (VEHICLE_TYPE) {
                case "25":
                    jenisKendaraan = "Besar";
                    break;
                case "26":
                    jenisKendaraan = "Kecil";
                    break;
                case "27":
                    jenisKendaraan = "Sedang";
                    break;
            }

            // Add Property
            jsonObject.accumulate("nomor_polisi", POLICE_NUMBER);
            jsonObject.accumulate("jenis_kendaraan", jenisKendaraan);
            jsonObject.accumulate("merek_kendaraan", BRAND);
            jsonObject.accumulate("nama_kendaraan", VEHICLE_NAME);
            jsonObject.accumulate("subtotal", total);
            jsonObject.accumulate("diskon_tipe", DISCOUNT_TYPE);
            jsonObject.accumulate("metode_pembayaran", "Tunai");
            jsonObject.accumulate("nominal_bayar", 1000);
            jsonObject.accumulate("diskon", 0);
            jsonObject.accumulate("total", total);

            //JsonArr
            JSONArray jsonArray = new JSONArray();
            for (DataItemMenu item : mDataCart) {
                JSONObject pnObj = new JSONObject();
                pnObj.accumulate("nama", item.get_itemName());
                pnObj.accumulate("harga", item.get_itemPrice());
                pnObj.accumulate("kategori", item.get_itemType());
                jsonArray.put(pnObj);
            }
            jsonObject.accumulate("penjualan_detail", jsonArray);
            Log.d("EXPORTJSON", jsonObject.toString());
        } catch (JsonIOException e) {
            Log.d("JSONERROREX", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /*
        Method bellow for supporting purpose
     */

    private void checkEmpty() {
        emptyCart.setVisibility(invoiceRVAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
        if (invoiceRVAdapter.getItemCount() <= 0) {
            total_tv.setText("0");
        }
    }

    private void setupCompleteDialog() {
        completeDialog.setContentView(R.layout.dialog_done);
        completeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        completeDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button okBtn = completeDialog.findViewById(R.id.dialog_ok_btn);
        okBtn.setOnClickListener(v -> {
            completeDialog.dismiss();
            intentToBrand();
        });
    }

    private void intentToBrand() {
        Intent toBrand = new Intent(MainActivity.this, BrandSelection.class);
        toBrand.putExtra("MAC_ADDRESS", printerMacAddress);
        startActivity(toBrand);
        finish();
    }

    private void setupUnCompleteDialog() {
        incompleteDialog.setContentView(R.layout.dialog_undone);
        incompleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        incompleteDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button okBtn = incompleteDialog.findViewById(R.id.dialog_ok_btn_undone);
        okBtn.setOnClickListener(v -> incompleteDialog.dismiss());
    }

    private void setupChangeTypeDialog() {
        changeTypeDialog.setContentView(R.layout.change_type_dialog);
        changeTypeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changeTypeDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button cancelBtn = changeTypeDialog.findViewById(R.id.dialog_cancel_btn_change);
        Button okBtn = changeTypeDialog.findViewById(R.id.dialog_ok_btn_change);
        cancelBtn.setOnClickListener(v -> {
            changeTypeDialog.dismiss();
        });
        okBtn.setOnClickListener(v -> {
            changeTypeDialog.dismiss();
        });
    }
}
