package osac.digiponic.com.osac.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import osac.digiponic.com.osac.model.DataItemMenu;
import osac.digiponic.com.osac.R;

public class MenuRVAdapter extends RecyclerView.Adapter<MenuRVAdapter.ViewHolder> {

    private List<DataItemMenu> mDataItem;
    private Context mContext;
    ItemClickListener mClickListener;
    ItemClickListener carWashIitemClickListener;
    ItemClickListener carCareItemClickListener;

    public MenuRVAdapter(Context mContext, List<DataItemMenu> mDataItem) {
        this.mDataItem = mDataItem;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        DataItemMenu data = mDataItem.get(i);
        viewHolder._itemName.setText(data.get_itemName());
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        viewHolder._itemPrice.setText(formatRupiah.format((double) data.get_itemPrice()));
        viewHolder._itemName.setSelected(true);
        viewHolder._itemPrice.setSelected(true);
        if (isSelected(i)) {
            viewHolder._deleteLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder._deleteLayout.setVisibility(View.GONE);
        }
        Picasso.get().load(data.get_itemImage()).into(viewHolder._itemImage);
        Log.d("ImagesimagesDEBUg", String.valueOf(viewHolder._itemImage));

    }

    @Override
    public int getItemCount() {
        if (mDataItem != null) {
            return mDataItem.size();
        }
        return 0;
    }

    public String getItemID(int id) {
        return String.valueOf(mDataItem.get(id).get_itemID());
    }

    public String getItemName(int id) {
        return String.valueOf(mDataItem.get(id).get_itemName());
    }

    public String getItemPrice(int id) {
        return String.valueOf(mDataItem.get(id).get_itemPrice());
    }

    public String getItemVehicleType(int id) {
        return String.valueOf(mDataItem.get(id).get_itemVehicleType());
    }

    public String getItemType(int id) {
        return String.valueOf(mDataItem.get(id).get_itemType());
    }

    public String getItemImage(int id) {
        return String.valueOf(mDataItem.get(id).get_itemImage());

    }

    public void setSelected(int id, boolean input) {
        mDataItem.get(id).setSelected(input);
    }

    public boolean isSelected(int id) {
        return mDataItem.get(id).isSelected();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void setCarWashIitemClickListener(ItemClickListener itemClickListener) {
        this.carWashIitemClickListener = itemClickListener;
    }

    public void setCarCareItemClickListener(ItemClickListener itemClickListener) {
        this.carCareItemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onCarWashItemClick(View view, int position);
        void onCarCareItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView mCardView;
        public TextView _itemName, _itemPrice;
        public LinearLayout _deleteLayout;
        public ImageView _itemImage;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            _deleteLayout = v.findViewById(R.id.layout_invoice_delete);
            mCardView = v.findViewById(R.id.cardView_itemMenu);
            _itemName = v.findViewById(R.id.text_itemName);
            _itemPrice = v.findViewById(R.id.text_itemPrice);
            _itemImage = v.findViewById(R.id.img_itemImg);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
            if (carCareItemClickListener != null) carCareItemClickListener.onCarCareItemClick(v, getAdapterPosition());
            if (carWashIitemClickListener != null) carWashIitemClickListener.onCarWashItemClick(v, getAdapterPosition());
        }
    }


}
