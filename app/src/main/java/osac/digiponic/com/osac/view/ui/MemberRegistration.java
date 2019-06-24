package osac.digiponic.com.osac.view.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import osac.digiponic.com.osac.R;

public class MemberRegistration extends DialogFragment {

   private EditText namaEdit, emailEdit, noTelpEdit;
   private Button daftarBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_member_registration, container);

        namaEdit = rootView.findViewById(R.id.name_member_regist);
        emailEdit = rootView.findViewById(R.id.email_member_regist);
        noTelpEdit = rootView.findViewById(R.id.phone_member_regist);
        daftarBtn = rootView.findViewById(R.id.btn_member_regist);



        return rootView;
    }
}
