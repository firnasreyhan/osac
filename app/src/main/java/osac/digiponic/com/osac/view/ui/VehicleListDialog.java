package osac.digiponic.com.osac.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import osac.digiponic.com.osac.R;
import osac.digiponic.com.osac.model.DataVehicle;
import osac.digiponic.com.osac.view.adapter.VehicleRVAdapter;
import osac.digiponic.com.osac.viewmodel.BrandActivityViewModel;
import osac.digiponic.com.osac.viewmodel.VehicleDialogViewModel;

public class VehicleListDialog extends DialogFragment implements VehicleRVAdapter.ItemClickListener {

    private RecyclerView recyclerView_Vehicle;
    private VehicleRVAdapter vehicleRVAdapter;
    private List<DataVehicle> mDataSet = new ArrayList<>();

    private BrandActivityViewModel brandActivityViewModel;
    private VehicleDialogViewModel vehicleDialogViewModel;

    private ShimmerRecyclerView shimmerRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.vehicle_list_dialog, container);

        shimmerRecyclerView = rootView.findViewById(R.id.vehicle_shimmer_recyclerView);
        shimmerRecyclerView.showShimmerAdapter();

        vehicleDialogViewModel = ViewModelProviders.of(this).get(VehicleDialogViewModel.class);
        vehicleDialogViewModel.init(BrandSelection.BrandID);

        recyclerView_Vehicle = rootView.findViewById(R.id.rv_vehicle_list);
        recyclerView_Vehicle.setLayoutManager(new GridLayoutManager(this.getActivity(), 4));

//        Toast.makeText(getContext(), BrandSelection.BrandID, Toast.LENGTH_SHORT).show();

        vehicleRVAdapter = new VehicleRVAdapter(this.getActivity(), vehicleDialogViewModel.getVehicleData().getValue());
        vehicleRVAdapter.setClickListener(this);
        setupRV();

        this.getDialog().setTitle("Pilih Kendaraaan");

        return rootView;
    }

    private void setupRV() {
        new Handler().postDelayed(() -> {
            Log.d("datamaindialog", vehicleRVAdapter.getItemCount() + "");
            if (vehicleRVAdapter.getItemCount() > 0) {
                shimmerRecyclerView.hideShimmerAdapter();
                recyclerView_Vehicle.setAdapter(vehicleRVAdapter);
                vehicleRVAdapter.notifyDataSetChanged();
            } else {
                setupRV();
            }
        }, 1000);
    }


    @Override
    public void onVehicleItemClick(View view, int position) {
//        Toast.makeText(this.getContext(), vehicleRVAdapter.getID(position), Toast.LENGTH_SHORT).show();
        Intent toMainActivity = new Intent(this.getContext(), MainActivity.class);
        toMainActivity.putExtra("VEHICLE_TYPE", vehicleRVAdapter.getJenisKendaraan(position));
        toMainActivity.putExtra("BRAND", BrandSelection.BrandName);
        toMainActivity.putExtra("VEHICLE_NAME", vehicleRVAdapter.getNamaKendaraan(position));
//        toMainActivity.putExtra("MAC_ADDRESS", BrandSelection.PRINTER_MAC_ADDRESS);
        Log.d("sebelumdiangirima", vehicleRVAdapter.getJenisKendaraan(position));
        Log.d("sebelumdiangirimbc", BrandSelection.BrandName);
        Log.d("sebelumdiangirimc", vehicleRVAdapter.getNamaKendaraan(position));

        startActivity(toMainActivity);
        Objects.requireNonNull(this.getActivity()).finish();
    }
}
