package osac.digiponic.com.osac.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import osac.digiponic.com.osac.R;
import osac.digiponic.com.osac.model.DataMember;

public class MemberRVAdapter extends RecyclerView.Adapter<MemberRVAdapter.ViewHolder> {

    private Context mContext;
    private List<DataMember> mDataMember;
    ItemClickListener mClickListener;

    public MemberRVAdapter(Context mContext, List<DataMember> mDataMember) {
        this.mContext = mContext;
        this.mDataMember = mDataMember;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.member_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DataMember data = mDataMember.get(i);
        viewHolder.alpabhetName.setText(data.getAlpabhetName());
        viewHolder.memberName.setText(data.getMemberName());
        viewHolder.memberEmail.setText(data.getMemberEmail());
        viewHolder.memberPhone.setText(data.getMemberPhone());
        // TODO: Response when item clicked
    }

    @Override
    public int getItemCount() {
        return mDataMember.size();
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView alpabhetName, memberName, memberEmail, memberPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            alpabhetName = itemView.findViewById(R.id.alpabet_name);
            memberName = itemView.findViewById(R.id.member_name);
            memberEmail = itemView.findViewById(R.id.member_email);
            memberPhone = itemView.findViewById(R.id.member_phone);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void filteredList(ArrayList<DataMember> filteredList) {
        mDataMember = filteredList;
        notifyDataSetChanged();
    }
}
