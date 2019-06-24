package osac.digiponic.com.osac.view.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import osac.digiponic.com.osac.R;

public class PoliceNumberInput extends AppCompatActivity {

    private EditText numberInput;
    private Button btnSubmit;
    private Dialog memberDialog;


    public static String policeNumber;

    private boolean isMember = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_number_input);

        numberInput = findViewById(R.id.police_number_input);
        btnSubmit = findViewById(R.id.police_number_submit);
        memberDialog = new Dialog(this);

        policeNumber = numberInput.getText().toString();

        setupMemberDialog();

        memberCheck();

        btnSubmit.setOnClickListener(v -> {
            if (numberInput.getText() != null) {
                toPayment();
            } else {
                Toast.makeText(PoliceNumberInput.this, "Nomor Polisi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupMemberDialog() {
        memberDialog.setContentView(R.layout.dialog_member_offering);
        memberDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        memberDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button yesBtn = memberDialog.findViewById(R.id.dialog_yes_member);
        Button noBtn = memberDialog.findViewById(R.id.dialog_no_member);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMember();
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberDialog.dismiss();
            }
        });
    }

    private void toMember() {
        Intent toMember = new Intent(PoliceNumberInput.this, MemberRegistration.class);
        startActivity(toMember);
    }

    private void memberCheck() {

    }

    private void toPayment() {
        Intent toPayment = new Intent(PoliceNumberInput.this, PaymentActivity.class);
        toPayment.putExtra("POLICE_NUMBER", policeNumber);
        startActivity(toPayment);
    }
}
