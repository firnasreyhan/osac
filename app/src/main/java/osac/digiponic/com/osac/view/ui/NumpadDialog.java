package osac.digiponic.com.osac.view.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fxn769.Numpad;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import osac.digiponic.com.osac.R;

public class NumpadDialog extends DialogFragment {

    private Button one, two, three, four, five, six, seven, eight, nine, zero, doubleZero, delete, submit;
    private TextView amount_TextView, amount_total_TextView;
    public static String amount = "";
    private Locale localeID = new Locale("in", "ID");
    private DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(localeID);
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_numpad_dialog, container, false);
        formatRupiah.setMaximumFractionDigits(3);

        amount_total_TextView = rootView.findViewById(R.id.amount_total);
        amount_total_TextView.setText("Total : " + formatRupiah.format(PaymentActivity.TOTAL));


        amount_TextView = rootView.findViewById(R.id.amount_custom);
        amount_TextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        one = rootView.findViewById(R.id.btn_one);
        two = rootView.findViewById(R.id.btn_two);
        three = rootView.findViewById(R.id.btn_three);
        four = rootView.findViewById(R.id.btn_four);
        five = rootView.findViewById(R.id.btn_five);
        six = rootView.findViewById(R.id.btn_six);
        seven = rootView.findViewById(R.id.btn_seven);
        eight = rootView.findViewById(R.id.btn_eight);
        nine = rootView.findViewById(R.id.btn_nine);
        zero = rootView.findViewById(R.id.btn_zero);
        doubleZero = rootView.findViewById(R.id.btn_double_zero);
        delete = rootView.findViewById(R.id.btn_delete);
        submit = rootView.findViewById(R.id.btn_submit);

        submit.setEnabled(false);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "1";
                amountSetTextView(amount);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "2";
                amountSetTextView(amount);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "3";
                amountSetTextView(amount);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "4";
                amountSetTextView(amount);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "5";
                amountSetTextView(amount);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "6";
                amountSetTextView(amount);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "7";
                amountSetTextView(amount);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "8";
                amountSetTextView(amount);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "9";
                amountSetTextView(amount);
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "0";
                amountSetTextView(amount);
            }
        });
        doubleZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += "00";
                amountSetTextView(amount);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.length() > 0) {
                    StringBuilder builder = new StringBuilder(amount);
                    builder.deleteCharAt(amount.length() - 1);
                    amount = builder.toString();
                    amountSetTextView(amount);
                } else {
                    amount_TextView.setText("0");
                }
            }
        });
        delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                amount = "0";
                amountSetTextView(amount);
                return true;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(amount) >= PaymentActivity.TOTAL) {
                    continuePayment();
                }
            }
        });
        return rootView;
    }

    private void amountSetTextView(String value) {
        Log.d("debugtextsebelum", amount);
        if (value.length() >= 10) {
            StringBuilder builder = new StringBuilder(amount);
            builder.deleteCharAt(amount.length() - 1);
            amount = builder.toString();
            Log.d("kelebihan", "kelebihan : " + amount);
            return;
        }
        if (amount.length() < 10 && !value.equals("")) {
            amount_TextView.setText(formatRupiah.format(Long.valueOf(value)));
        } else if (value.equals("")){
            amount_TextView.setText("0");
            return;
        }
        if (Long.valueOf(value) >= PaymentActivity.TOTAL) {
            submit.setEnabled(true);
            submit.setTextColor(this.getResources().getColor(R.color.White));
            submit.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
        } else {
            submit.setEnabled(false);
        }
        Log.d("debugtextsesudah", amount);
    }

    private void continuePayment() {
        new PaymentActivity.HTTPAsyncTaskPOSTData().execute("http://app.digiponic.co.id/osac/apiosac/api/transaksi");
        Intent toPaymentContinue = new Intent(this.getContext(), PaymentSuccessActivity.class);
        toPaymentContinue.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(toPaymentContinue);
        getActivity().finish();
    }
}
