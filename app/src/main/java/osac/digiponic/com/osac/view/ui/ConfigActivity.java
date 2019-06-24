package osac.digiponic.com.osac.view.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import osac.digiponic.com.osac.R;
import osac.digiponic.com.osac.helper.DatabaseHelper;
import osac.digiponic.com.osac.helper.DatabaseHelperAbout;
import osac.digiponic.com.osac.model.DataAbout;

public class ConfigActivity extends AppCompatActivity {

    private EditText phoneNumber, address;
    private DatabaseHelper db;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        phoneNumber = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        submitBtn = findViewById(R.id.config_submit);

        db = new DatabaseHelper(this);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertDataAbout(new DataAbout("1", "OSAC", "ONE STOP AUTO CARE", phoneNumber.getText().toString(), address.getText().toString()));
            }
        });
    }
}
