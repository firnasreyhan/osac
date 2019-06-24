package osac.digiponic.com.osac.view.ui;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import osac.digiponic.com.osac.R;
import osac.digiponic.com.osac.model.DataMember;
import osac.digiponic.com.osac.view.adapter.MemberRVAdapter;
import osac.digiponic.com.osac.viewmodel.MemberActivityViewModel;

public class MemberActivity extends AppCompatActivity {

    private RecyclerView memberList;
    private MemberRVAdapter memberRVAdapter;
    private MemberActivityViewModel memberActivityViewModel;
    private EditText searchEditText;
    private Button addMember;


    private FragmentManager fragmentManager;
    private MemberRegistration memberRegistrationDialog;

    public MemberActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        memberRegistrationDialog = new MemberRegistration();
        addMember = findViewById(R.id.add_member_button);
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberRegistrationDialog.show(fragmentManager, "");
            }
        });

        setViewModel();
        setupRV();

    }

    private void setViewModel() {
        memberActivityViewModel = ViewModelProviders.of(this).get(MemberActivityViewModel.class);
        memberActivityViewModel.init();
    }

    private void setupRV() {
        memberList = findViewById(R.id.rv_member);
        memberList.setLayoutManager(new LinearLayoutManager(this));
        memberRVAdapter = new MemberRVAdapter(this, memberActivityViewModel.getMember().getValue());
        // TODO: Set Click Listener
        memberList.setAdapter(memberRVAdapter);
        searchEditText = findViewById(R.id.search_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

//        checkInternet();
    }

    private void checkInternet() {

    }

    private void filter(String text) {
        ArrayList<DataMember> filteredList = new ArrayList<>();
        for(DataMember item : memberActivityViewModel.getMember().getValue()) {
            if (item.getMemberName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        memberRVAdapter.filteredList(filteredList);
    }

}
