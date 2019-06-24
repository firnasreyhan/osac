package osac.digiponic.com.osac.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import osac.digiponic.com.osac.R;
import osac.digiponic.com.osac.model.DataVehicle;

public class VehicleRVAdapter extends RecyclerView.Adapter<VehicleRVAdapter.ViewHolder> {

    private Context mContext;
    private List<DataVehicle> mDataVehicle;
    ItemClickListener mClickListener;

    public VehicleRVAdapter(Context mContext, List<DataVehicle> mDataVehicle) {
        this.mContext = mContext;
        this.mDataVehicle = mDataVehicle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vehicle_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DataVehicle data = mDataVehicle.get(i);
//        viewHolder._vehicleImage._vehicleImage
        Picasso.get().load(data.getGambar()).into(viewHolder._vehicleImage);
        Log.d("gambarvehicle", data.getGambar());
        viewHolder._vehicleName.setText(data.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return mDataVehicle.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onVehicleItemClick(View view, int position);
    }

    public String getID(int position) {
        return mDataVehicle.get(position).getId();
    }

    public String getJenisKendaraan(int position) {
        return mDataVehicle.get(position).getId_jenis_kendaraan();
    }

    public String getNamaKendaraan(int position) {
        return mDataVehicle.get(position).getKeterangan();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView _vehicleName;
        public ImageView _vehicleImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            _vehicleImage = itemView.findViewById(R.id.vehicle_image);
            _vehicleName = itemView.findViewById(R.id.vehicle_name_item);
        }


        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onVehicleItemClick(v, getAdapterPosition());
        }
    }

}
