package osac.digiponic.com.osac.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import osac.digiponic.com.osac.R;
import osac.digiponic.com.osac.model.DataBrand;

public class BrandRVAdapter extends RecyclerView.Adapter<BrandRVAdapter.ViewHolder> {

    private Context mContext;
    private List<DataBrand> mDataBrand;
    ItemClickListener mClickListener;

    public BrandRVAdapter(Context mContext, List<DataBrand> mDataBrand) {
        this.mContext = mContext;
        this.mDataBrand = mDataBrand;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DataBrand data = mDataBrand.get(i);

//        viewHolder._brandImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.logo));
        Picasso.get().load(data.getGambar()).into(viewHolder._brandImage);
    }

    @Override
    public int getItemCount() {
        return mDataBrand.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public int getVehicleId(int position) {
        return Integer.parseInt(mDataBrand.get(position).getId());
    }

    public String getBrandName(int position) {
        return mDataBrand.get(position).getKeterangan();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView mCardView;
        public TextView _brandName;
        public ImageView _brandImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mCardView = itemView.findViewById(R.id.cardView_brand);
            _brandImage = itemView.findViewById(R.id.image_brand);
        }


        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

}
