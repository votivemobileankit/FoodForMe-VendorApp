package com.foodforme.vendore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.foodforme.vendore.R;
import com.foodforme.vendore.SetterGatter.AddonDatum;

import java.util.List;

public class AddonAdapter extends RecyclerView.Adapter<AddonAdapter.RecyclerViewViewHolder> {

    List<AddonDatum> mFoodAddonList;
    private View mView;
    Context mContext;

    @NonNull
    @Override
    public AddonAdapter.RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.addone_layout, parent, false);
        AddonAdapter.RecyclerViewViewHolder viewHolder = new AddonAdapter.RecyclerViewViewHolder(mView);
        return viewHolder;
    }


    public AddonAdapter(Context m, List<AddonDatum> mList) {
        this.mFoodAddonList = mList;
        this.mContext = m;

    }

    @Override
    public void onBindViewHolder(@NonNull AddonAdapter.RecyclerViewViewHolder holder, int position) {
        holder.addon_name.setText(mFoodAddonList.get(position).getName());
        holder.addonPrice.setText(mFoodAddonList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return mFoodAddonList.size();
    }

    public class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        TextView addon_name, addonPrice;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            addon_name = itemView.findViewById(R.id.addon_name);
            addonPrice = itemView.findViewById(R.id.addonPrice);

        }
    }
}
