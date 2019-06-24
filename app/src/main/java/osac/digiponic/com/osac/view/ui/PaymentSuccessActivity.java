package osac.digiponic.com.osac.view.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zj.btsdk.BluetoothService;

import net.glxn.qrgen.android.QRCode;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import osac.digiponic.com.osac.R;
import osac.digiponic.com.osac.helper.DatabaseHelper;
import osac.digiponic.com.osac.model.DataAbout;
import osac.digiponic.com.osac.model.DataBluetoothDevice;
import osac.digiponic.com.osac.model.DataItemMenu;
import osac.digiponic.com.osac.print.DeviceActivity;
import osac.digiponic.com.osac.print.PrinterCommands;
import osac.digiponic.com.osac.print.Utils;

public class PaymentSuccessActivity extends AppCompatActivity {

    private Button btnDone, btnCetak;
    private TextView textView_amount_ps, textView_ps_1;

    private Locale localeID = new Locale("in", "ID");
    private DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(localeID);
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    // Bluetooth Printer
    public static final int RC_BLUETOOTH = 0;
    public static final int RC_CONNECT_DEVICE = 1;
    public static final int RC_ENABLE_BLUETOOTH = 2;
    private BluetoothService mService = null;
    private boolean isPrinterReady = false;

    public static String printerMacAddress;

    private DatabaseHelper db;
    private List<DataBluetoothDevice> device_list = new ArrayList<>();

    private long kembalian;

    private List<DataAbout> dataAbout = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        formatRupiah.setMaximumFractionDigits(3);

        formatRupiah.setMaximumFractionDigits(3);
        textView_amount_ps = findViewById(R.id.text_amount_ps);
        textView_ps_1 = findViewById(R.id.text_ps_1);

        mService = BrandSelection.mService;
        printerMacAddress = BrandSelection.printerMacAddress;
        isPrinterReady = BrandSelection.isPrinterReady;

        db = new DatabaseHelper(this);
        dataAbout = db.getAllDataAbout();

        if (PaymentActivity.state == 0) {
            textView_ps_1.setText("Pembayaran dengan Tunai berhasil dilakukan");
        } else {
            textView_ps_1.setText("Pembayaran dengan Debit berhasil dilakukan");
        }

        if (NumpadDialog.amount != null && !NumpadDialog.amount.equals("")) {
            kembalian = Long.valueOf(NumpadDialog.amount) - (long) PaymentActivity.TOTAL;
        } else {
            kembalian = 0;
        }
        textView_amount_ps.setText(String.valueOf(formatRupiah.format(kembalian)));

        btnDone = findViewById(R.id.btn_selesai);
        btnCetak = findViewById(R.id.btn_cetak);

        btnCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printInvoice();
                toBrandNew();
            }
        });

        btnDone.setOnClickListener(v -> {
            toBrandNew();
        });
    }

    public void printInvoice() {
        if (!mService.isAvailable()) {
            return;
        }
        if (printerMacAddress == null) {
            Toast.makeText(this, "Printer belum dikonfigurasi", Toast.LENGTH_SHORT).show();
        }
        if (isPrinterReady || printerMacAddress != null) {
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            SimpleDateFormat monthName = new SimpleDateFormat("dd MMMM yyyy");
            try {
                Date formatDate = dateFormat.parse(PaymentActivity.dataPOSTResponse.getWaktu());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            // Print
            printNewLine();
            printNewLine();
            printImage(R.drawable.downloadwhite);
            printNewLine();
            printTextNew(dataAbout.get(0).getAddress());
            printNewLine();
            printHeader();
            printData(MainActivity.mDataCart);
            printTotal();
            printTextNew("Terima Kasih Atas Pesanan Anda");
            printTextNew("Ditunggu kedatangan selanjutnya");
            printNewLine();
            printNewLine();

        } else {
            if (mService.isBTopen())
                startActivityForResult(new Intent(this, DeviceActivity.class), RC_CONNECT_DEVICE);
            else
                requestBluetooth();
        }
    }

    public void printNewLine() {
        mService.write(PrinterCommands.FEED_LINE);
    }

    public void printTextNew(String text) {
        if (!mService.isAvailable()) {
            return;
        }
        if (isPrinterReady) {
            if (text == null) {
                Toast.makeText(this, "Cant print null text", Toast.LENGTH_SHORT).show();
                return;
            }
            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.sendMessage(text, "");
            mService.write(PrinterCommands.ESC_ENTER);
        } else {
            if (mService.isBTopen())
                startActivityForResult(new Intent(this, DeviceActivity.class), RC_CONNECT_DEVICE);
            else
                requestBluetooth();
        }
    }

    private void printData(List<DataItemMenu> dataSet) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat nf = NumberFormat.getCurrencyInstance(localeID);
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);

        if (!mService.isAvailable()) {
            return;
        }
        if (isPrinterReady) {
            printDivider();
            for (DataItemMenu item : dataSet) {
                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(item.get_itemName(), "");
                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                String output = String.format("%-12s %19s", "1 X " + item.get_itemPrice(), nf.format(item.get_itemPrice()).trim());
                mService.sendMessage(output, "");
            }
            printDivider();
        } else {
            if (mService.isBTopen())
                startActivityForResult(new Intent(this, DeviceActivity.class), RC_CONNECT_DEVICE);
            else
                requestBluetooth();
        }
    }

    private void printHeader() {
        mService.write(PrinterCommands.ESC_ALIGN_CENTER);
        String kode = String.format("%-12s %19s", "Kode", PaymentActivity.dataPOSTResponse.getKode_transaksi());
        String waktu = String.format("%-12s %19s", "Waktu", PaymentActivity.dataPOSTResponse.getWaktu());
        String pembayaran = String.format("%-12s %19s", "Pembayaran", PaymentActivity.dataPOSTResponse.getMetode_pembayaran());
        mService.sendMessage(kode, "");
        mService.sendMessage(waktu, "");
        mService.sendMessage(pembayaran, "");
    }

    private void printTotal() {
        Locale localeID = new Locale("in", "ID");
        NumberFormat nf = NumberFormat.getCurrencyInstance(localeID);
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);

        mService.write(PrinterCommands.ESC_ALIGN_CENTER);
        String subTotal = String.format("%-12s %19s", "Sub Total", nf.format(Integer.parseInt(PaymentActivity.dataPOSTResponse.getSubtotal())).trim());
        String pajak = String.format("%-12s %19s", "Pajak", nf.format(0));
        String diskon = String.format("%-12s %19s", "Diskon", nf.format(Integer.parseInt(PaymentActivity.dataPOSTResponse.getDiskon())));
        String grandTotal = String.format("%-12s %19s", "Grand Total", nf.format(Integer.parseInt(PaymentActivity.dataPOSTResponse.getSubtotal())));
        String nominalBayar = String.format("%-12s %18s", "Nominal Bayar", nf.format(Integer.parseInt(NumpadDialog.amount)));
        String kembalian = String.format("%-12s %19s", "Kembalian", nf.format(Integer.parseInt(NumpadDialog.amount) - Integer.parseInt(PaymentActivity.dataPOSTResponse.getSubtotal())));
        mService.sendMessage(subTotal, "");
        mService.sendMessage(pajak, "");
        mService.sendMessage(diskon, "");
        mService.sendMessage(grandTotal, "");
        mService.sendMessage(nominalBayar, "");
        mService.sendMessage(kembalian, "");
        printNewLine();
        printDivider();
    }

    private void printImage(int img) {
        if (isPrinterReady) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    img);
            Bitmap resizedImage = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * 0.8), (int) (bmp.getHeight() * 0.8), true);
            if (resizedImage != null) {
                byte[] command = Utils.decodeBitmap(resizedImage);
                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                mService.write(command);
            }

        } else {
            if (mService.isBTopen())
                startActivityForResult(new Intent(this, DeviceActivity.class), RC_CONNECT_DEVICE);
            else
                requestBluetooth();
        }
    }

    public void printQRCode(String text) {
        try {
            Bitmap bmp = QRCode.from(text).bitmap();
            Bitmap resizedImage = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * 2), (int) (bmp.getHeight() * 2), true);
            if (resizedImage != null) {
                byte[] command = Utils.decodeBitmap(resizedImage);
                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                mService.write(command);
            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    //print unicode
    public void printUnicode() {
        mService.write(PrinterCommands.ESC_ALIGN_CENTER);
        printTextNew("#####################");
    }

    private void printDivider() {
        mService.write(PrinterCommands.ESC_ALIGN_CENTER);
        printTextNew("--------------------------------");
    }


    private void requestBluetooth() {
        if (mService != null) {
            if (!mService.isBTopen()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, RC_ENABLE_BLUETOOTH);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (NumpadDialog.amount != null) {
            NumpadDialog.amount = "";
        }
        Intent toBrand = new Intent(PaymentSuccessActivity.this, BrandSelection.class);
        toBrand.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        toBrand.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toBrand);
        finish();
    }

    private void toBrandNew() {
        Intent toBrand = new Intent(PaymentSuccessActivity.this, BrandSelection.class);
        NumpadDialog.amount = "0";
        toBrand.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        toBrand.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toBrand);
        PaymentActivity.dataPOSTResponse = null;
        finish();
    }
}
