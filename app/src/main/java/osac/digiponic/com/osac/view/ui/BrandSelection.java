package osac.digiponic.com.osac.view.ui;

import android.Manifest;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.zj.btsdk.BluetoothService;

import java.util.ArrayList;
import java.util.List;

import osac.digiponic.com.osac.R;
import osac.digiponic.com.osac.helper.DatabaseHelper;
import osac.digiponic.com.osac.model.DataBluetoothDevice;
import osac.digiponic.com.osac.model.DataBrand;
import osac.digiponic.com.osac.print.BluetoothHandler;
import osac.digiponic.com.osac.print.DeviceActivity;
import osac.digiponic.com.osac.view.adapter.BrandRVAdapter;
import osac.digiponic.com.osac.view.adapter.VehicleRVAdapter;
import osac.digiponic.com.osac.viewmodel.BrandActivityViewModel;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class BrandSelection extends AppCompatActivity implements BrandRVAdapter.ItemClickListener, VehicleRVAdapter.ItemClickListener, EasyPermissions.PermissionCallbacks, BluetoothHandler.HandlerInterface {

    private RecyclerView recyclerView_Brand;
    private RecyclerView recyclerView_Vehicle;

    public static String BrandID = null;
    public static String BrandName = null;

    private BrandRVAdapter brandRVAdapter;
    private VehicleRVAdapter vehicleRVAdapter;
    private BrandActivityViewModel brandActivityViewModel;

    private List<DataBrand> mDataSet = new ArrayList<>();

    private Dialog vehicleDialog;

    private FragmentManager fragmentManager;
    private VehicleListDialog vehicleListDialog;

    private ShimmerRecyclerView shimmerRecyclerView;

    private boolean doubleBackToExitPressedOnce = false;

    // Bluetooth Printer
    public static final int RC_BLUETOOTH = 0;
    public static final int RC_CONNECT_DEVICE = 1;
    public static final int RC_ENABLE_BLUETOOTH = 2;
    public static BluetoothService mService = null;
    public static boolean isPrinterReady = false;
    private BluetoothAdapter bluetoothAdapter;

    public static String printerMacAddress = "";

    private DatabaseHelper db;
    private List<DataBluetoothDevice> device_list = new ArrayList<>();

    // Variable
    private String VEHICLE_TYPE;
    private String VEHICLE_BRAND;

    private ImageView logoBrand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_selection);

        // Lock Screen to Horizontal
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        logoBrand = findViewById(R.id.logo_brand);
        logoBrand.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent toSetting = new Intent(BrandSelection.this, ConfigActivity.class);
                startActivity(toSetting);
                return false;
            }
        });
        db = new DatabaseHelper(this);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        setupBluetooth();
        if (!isPrinterReady) {
            if (bluetoothAdapter != null) {
                if (mService.isBTopen())
                    startActivityForResult(new Intent(this, DeviceActivity.class), RC_CONNECT_DEVICE);
                else
                    requestBluetooth();
            } else {
                Toast.makeText(this, "No Bluetooth Supported", Toast.LENGTH_SHORT).show();
            }

        }
        if (!printerMacAddress.equals("") && mService != null) {
            BluetoothDevice mDevice = mService.getDevByMac(printerMacAddress);
            mService.connect(mDevice);
        }
        shimmerRecyclerView = findViewById(R.id.brand_shimmer_recyclerView);
        shimmerRecyclerView.showShimmerAdapter();

        if (MainActivity.mDataCart != null) {
            dataCartClear();
        }

        fragmentManager = getSupportFragmentManager();
        vehicleListDialog = new VehicleListDialog();

        brandActivityViewModel = ViewModelProviders.of(this).get(BrandActivityViewModel.class);
        brandActivityViewModel.init();

        brandActivityViewModel.getBrandData().observe(this, dataBrands -> {
            brandRVAdapter.notifyDataSetChanged();
        });

        setRV();
    }

    // Method bellow are used to support printing for thermal printer

    @AfterPermissionGranted(RC_BLUETOOTH)
    private void setupBluetooth() {
        String[] params = {Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};

        if (!EasyPermissions.hasPermissions(this, params)) {
            EasyPermissions.requestPermissions(this, "You need bluetooth permission",
                    RC_BLUETOOTH, params);
            return;
        }
        mService = new BluetoothService(this, new BluetoothHandler(this));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_ENABLE_BLUETOOTH:
                if (resultCode == RESULT_OK) {
                } else
                    break;
            case RC_CONNECT_DEVICE:
                if (resultCode == RESULT_OK) {
//                    db_device.insertData(new DataBluetoothDevice(data.getExtras().getString(DeviceActivity.EXTRA_DEVICE_ADDRESS), "BluetoothPrinter"));
                    printerMacAddress = data.getExtras().getString(DeviceActivity.EXTRA_DEVICE_ADDRESS);
                    if (printerMacAddress != null) {
                        BluetoothDevice mDevice = mService.getDevByMac(printerMacAddress);
                        mService.connect(mDevice);
                    }
                }
                break;
        }
    }

    private void requestBluetooth() {
        if (mService != null) {
            if (!mService.isBTopen()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, RC_ENABLE_BLUETOOTH);
            }
        }
    }

    private void dataCartClear() {
        MainActivity.mDataCart.clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            finishAffinity();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan tombol kembali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    private void setRV() {
        recyclerView_Brand = findViewById(R.id.rv_brands);
        recyclerView_Brand.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView_Brand.setHasFixedSize(true);
        brandRVAdapter = new BrandRVAdapter(this, brandActivityViewModel.getBrandData().getValue());
        Log.d("brandvalue", brandActivityViewModel.getBrandData().getValue().toString());
        brandRVAdapter.setClickListener(this);
        checkInternetData();

        Log.d("datasize", String.valueOf(brandRVAdapter.getItemCount()));
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @Override
    public void onDeviceConnected() {
        isPrinterReady = true;
//        tvStatus.setText("Terhubung dengan perangkat");
    }

    @Override
    public void onDeviceConnecting() {
//        tvStatus.setText("Sedang menghubungkan...");
    }

    @Override
    public void onDeviceConnectionLost() {
        isPrinterReady = false;
//        tvStatus.setText("Koneksi perangkat terputus");
    }

    @Override
    public void onDeviceUnableToConnect() {
//        tvStatus.setText("Tidak dapat terhubung ke perangkat");
    }


    private void checkInternetData() {
        new Handler().postDelayed(() -> {
            Log.d("datamainbrand    ", brandRVAdapter.getItemCount() + "");
            if (brandRVAdapter.getItemCount() > 0) {
                shimmerRecyclerView.hideShimmerAdapter();
                recyclerView_Brand.setAdapter(brandRVAdapter);
            } else {
                checkInternetData();
            }
        }, 1000);
    }

    @Override
    public void onItemClick(View view, int position) {
        BrandID = String.valueOf(brandRVAdapter.getVehicleId(position));
        BrandName = String.valueOf(brandRVAdapter.getBrandName(position));
        vehicleListDialog.show(fragmentManager, "Daftar Kendaraan");
    }

    @Override
    public void onVehicleItemClick(View view, int position) {
    }

    private void toSetting() {
//        Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
//        this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
        startActivityForResult(new Intent(this, DeviceActivity.class), RC_CONNECT_DEVICE);
    }
}
